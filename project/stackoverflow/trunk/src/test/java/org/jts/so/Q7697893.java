package org.jts.so;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class Q7697893 {
  @Test
  public void test() {
    Pattern pattern = Pattern.compile("^\\d{1,3}(?::\\d)?$");

    List<String> samples = Arrays.asList("0", "00", "000", "0:0", "00:0", "000:0");
    List<String> failSamples = Arrays.asList("0000", "0000:0", "0:00", "00:00", "000:00");
    for (String sample : samples) {
      assertTrue(sample, pattern.matcher(sample).matches());
    }
    for (String failSample : failSamples) {
      assertFalse(failSample, pattern.matcher(failSample).matches());
    }
  }
}
