package org.jts.portmapper;

import java.net.InetSocketAddress;

public interface PortMapping {
   InetSocketAddress getLocal();

   InetSocketAddress getRemote();
}
