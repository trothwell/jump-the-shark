package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketStreamCopier implements Runnable {

   private final Socket client;
   private final InputStream clientIn;
   private final OutputStream clientOut;
   private final Logger log = LoggerFactory.getLogger(SocketStreamCopier.class);
   private final long pollIntervalMs = TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS);
   private final Socket remote;
   private final InputStream remoteIn;
   private final OutputStream remoteOut;

   SocketStreamCopier(Socket client, Socket remote) throws IOException {
      this.clientIn = client.getInputStream();
      this.remoteIn = remote.getInputStream();
      this.clientOut = client.getOutputStream();
      this.remoteOut = remote.getOutputStream();
      this.client = client;
      this.remote = remote;
   }

   @Override
   public void run() {
      log.debug("Running copier: client={}, remote={}", client, remote);

      BlockingQueue<Packet> toClientFilled = new ArrayBlockingQueue<Packet>(512);
      BlockingQueue<Packet> toClientEmpty = createEmptyPackets(512, 64);
      BlockingQueue<Packet> toRemoteFilled = new ArrayBlockingQueue<Packet>(512);
      BlockingQueue<Packet> toRemoteEmpty = createEmptyPackets(512, 64);

      OperationResult clientReaderResult = OperationResults.createNew();
      OperationResult remoteReaderResult = OperationResults.createNew();
      OperationResult clientWriterResult = OperationResults.createNew();
      OperationResult remoteWriterResult = OperationResults.createNew();

      StreamReader clientReader = new StreamReader(clientReaderResult, toRemoteFilled, toRemoteEmpty, clientIn);
      StreamWriter remoteWriter = new StreamWriter(remoteWriterResult, toRemoteFilled, toRemoteEmpty, remoteOut);

      StreamReader remoteReader = new StreamReader(remoteReaderResult, toClientFilled, toClientEmpty, remoteIn);
      StreamWriter clientWriter = new StreamWriter(clientWriterResult, toClientFilled, toClientEmpty, clientOut);

      ExecutorService service = Executors.newFixedThreadPool(4);
      service.execute(clientReader);
      service.execute(clientWriter);
      service.execute(remoteReader);
      service.execute(remoteWriter);

      try {
         while (true) {
            if (clientReaderResult.hasError()) {
               log.info("Client reader exception", clientReaderResult.getError());
               return;
            } else if (clientWriterResult.hasError()) {
               log.info("Client writer exception", clientWriterResult.getError());
               return;
            } else if (remoteReaderResult.hasError()) {
               log.info("Remote reader exception", remoteReaderResult.getError());
               return;
            } else if (remoteWriterResult.hasError()) {
               log.info("Remote writer exception", remoteWriterResult.getError());
               return;
            }
            // Sleep
            Thread.sleep(pollIntervalMs);
            continue;
         }
      } catch (InterruptedException e) {
         log.info("Interrupted", e);
      } finally {
         service.shutdownNow();
         quietClose(client);
         quietClose(remote);
      }
   }

   /**
    * @incomplete trothwell
    * 
    * @param i
    * @param j
    * @return
    */
   private BlockingQueue<Packet> createEmptyPackets(int count, int length) {
      BlockingQueue<Packet> retval = new ArrayBlockingQueue<Packet>(count);
      for (int i = 0; true; i++) {
         Packet p = Packets.createNew(length);
         if (!retval.offer(p)) {
            return retval;
         }
      }
   }

   private void quietClose(Socket socket) {
      try {
         socket.close();
      } catch (IOException e) {
      }
   }

}
