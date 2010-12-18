package org.jts.portmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
   private static final Logger LOG = LoggerFactory.getLogger(Main.class);

   public static void main(String[] args) {
      if (args.length != 1) {
         System.out.println("Missing properties filename");
      }
      String fileName = args[0];

      Properties prp = new Properties();
      try {
         InputStream in = new FileInputStream(fileName);
         prp.load(in);
      } catch (FileNotFoundException e) {
         System.out.println("Failed to find file: " + fileName);
         LOG.error("Load file error: " + fileName, e);
         return;
      } catch (IOException e) {
         System.out.println("Failed to load file: " + fileName);
         LOG.error("Load file error: " + fileName, e);
         return;
      }

      Map<String, String> properties = new LinkedHashMap<String, String>();
      for (Map.Entry<Object, Object> entry : prp.entrySet()) {
         properties.put((String) entry.getKey(), (String) entry.getValue());
      }

      try {
         List<ServerSocket> localServerSockets = new ArrayList<ServerSocket>();

         Map<String, InetAddress> addressCache = new HashMap<String, InetAddress>();
         final Map<Integer, PortMapping> remotePortMapping = new HashMap<Integer, PortMapping>();
         final Map<Integer, PortMapping> remotePortMappingNoModify = Collections.unmodifiableMap(remotePortMapping);

         for (int i = 0; true; i++) {
            String key = String.format("portmap.%s", i);
            String portMap = properties.get(key);
            if (portMap == null) {
               break;
            }

            PortMapping portMapping = parsePortMap(portMap, addressCache);
            remotePortMapping.put(portMapping.getLocal().getPort(), portMapping);

            ServerSocket localServerSocket = new ServerSocket();
            LOG.info("Binding local: {} for remote: {}", portMapping.getLocal(), portMapping.getRemote());
            localServerSocket.bind(portMapping.getLocal(), 1);
            localServerSockets.add(localServerSocket);
         }

         // TODO: detect cycles in port mapping's
         // e.g. port 80 -> 81 -> 80 -> 81...

         ExecutorService es = Executors.newFixedThreadPool(localServerSockets.size());
         for (final ServerSocket local : localServerSockets) {
            es.execute(new Runnable() {
               @Override
               public void run() {
                  PortMapperService service = new PortMapperService(local, 10, remotePortMappingNoModify);
                  service.serve();
               }
            });
         }

      } catch (NumberFormatException e) {
         LOG.error("Failed to parse int.", e);
         System.out.println("Error with configuration.");
         return;
      } catch (UnknownHostException e) {
         LOG.error("Failed to determine localAddress.", e);
         System.out.println("Error with configuration.");
         return;
      } catch (IOException e) {
         LOG.error("Failed to configure ports.", e);
         System.out.println("Error initializing ports.");
         return;
      }

   }

   private static PortMapping parsePortMap(String portMap, Map<String, InetAddress> addressCache) throws UnknownHostException,
            IllegalArgumentException {
      Pattern regex = Pattern.compile("^([^:]+):([^\\^]+)\\^([^:]+):(.+)$");
      Matcher m = regex.matcher(portMap);
      if (!m.matches()) {
         throw new IllegalArgumentException("Failed parse. " + portMap);
      }
      try {
         String localHost = m.group(1);
         String remoteHost = m.group(3);
         int localPort = Integer.parseInt(m.group(2));
         int remotePort = Integer.parseInt(m.group(4));

         InetAddress local = addressCache.get(localHost);
         if (local == null) {
            local = InetAddress.getByName(localHost);
            addressCache.put(localHost, local);
         }
         InetAddress remote = addressCache.get(remoteHost);
         if (remote == null) {
            remote = InetAddress.getByName(remoteHost);
            addressCache.put(remoteHost, remote);
         }

         return PortMappings.createNew(new InetSocketAddress(local, localPort), new InetSocketAddress(remote, remotePort));
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException(e);
      }
   }
}
