package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

class PortMapperHandler implements Runnable {
   private final Socket client;
   private final Logger log = LoggerFactory.getLogger(PortMapperHandler.class);
   private final Map<Integer, PortMapping> portMappingMap;

   PortMapperHandler(Socket client, Map<Integer, PortMapping> portMapping) {
      this.client = client;
      this.portMappingMap = portMapping;
   }

   @Override
   public void run() {
      int localPort = client.getLocalPort();
      PortMapping portMapping = portMappingMap.get(localPort);
      if (portMapping == null) {
         log.error("Failed to determine mapping from port: {}", localPort);
         quietClose(client);
         return;
      }

      InetAddress remoteAddress = portMapping.getRemote().getAddress();
      int remotePort = portMapping.getRemote().getPort();

      Socket remote;
      try {
         log.debug("Attempting remote socket: {}:{}", remoteAddress, remotePort);
         remote = new Socket(remoteAddress, remotePort);
      } catch (IOException e) {
         log.error("Failed establishing connection to remote.", e);
         quietClose(client);
         return;
      }

      SocketStreamCopier copier;
      try {
         log.debug("Creating copier for socket: {}", client);
         copier = new SocketStreamCopier(client, remote);
      } catch (IOException e) {
         log.warn("Error", e);
         quietClose(client);
         quietClose(remote);
         return;
      }

      copier.run(); // run in same thread
   }

   private void quietClose(Socket socket) {
      try {
         socket.close();
      } catch (IOException e) {
      }
   }
}