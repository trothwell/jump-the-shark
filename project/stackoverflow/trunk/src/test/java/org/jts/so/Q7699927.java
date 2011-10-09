package org.jts.so;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * <code>http://stackoverflow.com/questions/7699927</code>
 */
public class Q7699927 {
  private static final String pattern = "^(\\d{2}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})" // date
      + "[ ]+(SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST)" // level
      + "[ ]+([^:]+):(\\d+)" // location name, location line
      + "[ ]+-[ ]+([^:]+): (.*?)" // exception name, exception message
      + "[ ]+(?:(?:Dump: )([a-zA-Z0-9\\./]+))?" // dump
      + "$";

  @Test
  public void testWithDump() {
    Pattern regex = Pattern.compile(pattern);
    String input = "09-22-11 12:58:40       SEVERE       ...ractBlobAodCommand:104           -   IllegalStateException: version:1316719189017 not found in recent history                             Dump: /data1/aafghani/dev/devamir/logs/dumps/22i125840.dump";
    Matcher m = regex.matcher(input);
    assertTrue(m.matches());
    System.out.format("> Input: \"%s\"%n", input);
    for (int i = 1; i <= m.groupCount(); i++) {
      System.out.format("[%d] \"%s\"%n", i, m.group(i));
    }
    assertEquals("09-22-11 12:58:40", m.group(1));
    assertEquals("SEVERE", m.group(2));
    assertEquals("...ractBlobAodCommand", m.group(3));
    assertEquals("104", m.group(4));
    assertEquals("IllegalStateException", m.group(5));
    assertEquals("version:1316719189017 not found in recent history", m.group(6));
    assertEquals("/data1/aafghani/dev/devamir/logs/dumps/22i125840.dump", m.group(7));
  }

  @Test
  public void testWithoutDump() {
    Pattern regex = Pattern.compile(pattern);
    String input = "09-22-11 12:58:40       SEVERE       ...ractBlobAodCommand:104           -   IllegalStateException: version:1316719189017 not found in recent history                             ";
    Matcher m = regex.matcher(input);
    assertTrue(m.matches());
    System.out.format("> Input: \"%s\"%n", input);
    for (int i = 1; i <= m.groupCount(); i++) {
      System.out.format("[%d] \"%s\"%n", i, m.group(i));
    }
    assertEquals("09-22-11 12:58:40", m.group(1));
    assertEquals("SEVERE", m.group(2));
    assertEquals("...ractBlobAodCommand", m.group(3));
    assertEquals("104", m.group(4));
    assertEquals("IllegalStateException", m.group(5));
    assertEquals("version:1316719189017 not found in recent history", m.group(6));
    assertEquals(null, m.group(7));
  }
}
