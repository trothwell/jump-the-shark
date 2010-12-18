package org.jts.portmapper;

import java.net.InetSocketAddress;

public class PortMappings {
   private static class PortMappingImpl implements PortMapping {
      private final InetSocketAddress local;
      private final InetSocketAddress remote;

      private PortMappingImpl(InetSocketAddress local, InetSocketAddress remote) {
         this.local = local;
         this.remote = remote;
      }

      @Override
      public InetSocketAddress getLocal() {
         return local;
      }

      @Override
      public InetSocketAddress getRemote() {
         return remote;
      }
   }

   static PortMapping createNew(InetSocketAddress local, InetSocketAddress remote) {
      return new PortMappingImpl(local, remote);
   }
}
