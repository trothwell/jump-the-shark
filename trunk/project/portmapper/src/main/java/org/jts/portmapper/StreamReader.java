/**
 * @incomplete trothwell
 */
package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @incomplete trothwell
 * 
 */
public class StreamReader implements Runnable {

   private final BlockingQueue<Packet> emptyPackets;
   private final BlockingQueue<Packet> filledPackets;
   private final InputStream in;
   @SuppressWarnings("unused")
   private final Logger log = LoggerFactory.getLogger(StreamReader.class);
   private final long pollIntervalMs = Const.STREAM_COPY_POLL_INTERVAL_MS;
   private final OperationResult result;

   /**
    * @param runnable .
    * @param result .
    */
   public StreamReader(OperationResult result, BlockingQueue<Packet> filledPackets, BlockingQueue<Packet> emptyPackets, InputStream in) {
      this.result = result;
      this.filledPackets = filledPackets;
      this.emptyPackets = emptyPackets;
      this.in = in;
   }

   @Override
   public void run() {
      try {
         while (true) {
            while (true) {
               Packet p = emptyPackets.poll(365, TimeUnit.DAYS);
               int len = in.read(p.getBytes(), 0, 64);
               if (len == -1) {
                  break; // no data
               }
               p.setLength(len).setOffset(0);
               filledPackets.put(p);
            }

            // Sleep
            Thread.sleep(pollIntervalMs);
         }
      } catch (InterruptedException e) {
         result.withError(e);
         return;
      } catch (IOException e) {
         result.withError(e);
         return;
      }
   }
}
