/**
 * @incomplete trothwell
 */
package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @incomplete trothwell
 * 
 */
public class StreamWriter implements Runnable {
   private final BlockingQueue<Packet> emptyPackets;
   private final BlockingQueue<Packet> filledPackets;
   @SuppressWarnings("unused")
   private final Logger log = LoggerFactory.getLogger(StreamWriter.class);
   private final OutputStream out;
   private final OperationResult result;

   /**
    * @param runnable .
    * @param result .
    */
   public StreamWriter(OperationResult result, BlockingQueue<Packet> filledPackets, BlockingQueue<Packet> emptyPackets, OutputStream out) {
      this.result = result;
      this.filledPackets = filledPackets;
      this.emptyPackets = emptyPackets;
      this.out = out;
   }

   @Override
   public void run() {
      try {
         while (true) {
            Packet p = filledPackets.poll(365, TimeUnit.DAYS);
            out.write(p.getBytes(), p.getOffset(), p.getLength());
            emptyPackets.put(p);
         }
      } catch (IOException e) {
         result.withError(e);
         return;
      } catch (InterruptedException e) {
         result.withError(e);
         return;
      }
   }
}
