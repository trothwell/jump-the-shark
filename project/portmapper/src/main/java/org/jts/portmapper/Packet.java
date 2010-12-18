package org.jts.portmapper;

/**
 * Packet of data.
 */
public interface Packet {
   /**
    * @return .
    */
   byte[] getBytes();

   /**
    * @return .
    */
   int getLength();

   /**
    * @return .
    */
   int getOffset();

   /**
    * @return .
    */
   Packet setLength(int length);

   /**
    * @return .
    */
   Packet setOffset(int offset);
}
