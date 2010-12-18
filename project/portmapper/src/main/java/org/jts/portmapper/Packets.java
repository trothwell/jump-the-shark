package org.jts.portmapper;

/**
 * Packet of data.
 */
public class Packets {
   private static class PacketImpl implements Packet {
      private final byte[] bytes;
      private int length;
      private int offset;

      private PacketImpl(byte[] bytes) {
         this.bytes = bytes;
      }

      @Override
      public byte[] getBytes() {
         return bytes;
      }

      @Override
      public int getLength() {
         return length;
      }

      @Override
      public int getOffset() {
         return offset;
      }

      @Override
      public Packet setLength(int length) {
         this.length = length;
         return this;
      }

      @Override
      public Packet setOffset(int offset) {
         this.offset = offset;
         return this;
      }
   }

   static Packet createNew(int size) {
      return new PacketImpl(new byte[size]);
   }
}
