package org.jts.so;

public class Q7698861 {
  public static class JVM {
    public static void main(String... args) throws InterruptedException {
      for (java.util.Enumeration<?> e = System.getProperties().propertyNames(); e.hasMoreElements();) {
        String prp = (String) e.nextElement();
        if (prp.startsWith("java.vm") || prp.startsWith("os.")) {
          System.out.format("[%s]=%s%n", prp, System.getProperty(prp));
        }
      }
      java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss.SSS");
      for (;;) {
        System.out.format("%s Sampling current threads...%n", df.format(new java.util.Date()));
        java.util.Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
        System.out.format("> Thread Count: %d%n", stacks.size());
        for (java.util.Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
          Thread thread = entry.getKey();
          System.out.format("> Thread: %s%n", thread.getName());
          // StackTraceElement[] stack = entry.getValue();
          // Throwable t = new Throwable("Thread: " + thread.getName());
          // t.setStackTrace(stack);
          // t.printStackTrace(System.out);
        }
        java.util.concurrent.TimeUnit.SECONDS.sleep(10);
      }
    }
  }
}
