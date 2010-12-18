package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortMapperService {
   private final Logger log = LoggerFactory.getLogger(PortMapperService.class);
   private final ExecutorService pool;
   private final Map<Integer, PortMapping> portMapping;

   private final ServerSocket serverSocket;

   public PortMapperService(ServerSocket socket, int poolSize, Map<Integer, PortMapping> portMapping) {
      serverSocket = socket;
      pool = Executors.newFixedThreadPool(poolSize);
      this.portMapping = portMapping;
   }

   public void serve() {
      log.debug("Serving: {}", serverSocket);
      try {
         for (;;) {
            Socket client = serverSocket.accept();
            log.debug("Connection from: {}", client);
            pool.execute(new PortMapperHandler(client, portMapping));
         }
      } catch (IOException ex) {
         pool.shutdown();
      }
   }
}