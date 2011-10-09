package org.jts.so;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Q7698499 {
  @Test
  public void test() {
    String input = "abcd  | e | fg | hijk | lmnop | |   | qrs |   t| uv| w |||||x   y|  z";
    int expectedSize = 17;
    List<String> expected = new ArrayList<String>(Arrays.asList("abcd  ", " e ", " fg ", " hijk ", " lmnop ", " ", "   ", " qrs ", "   t",
        " uv", " w ", "", "", "", "", "x   y", "  z"));

    List<String> matches = new ArrayList<String>();

    // Pattern pattern = Pattern.compile("(?:\\||^)([^\\|]*)");
    Pattern pattern = Pattern.compile("(?:_?\\||^)([^\\|]*?)(?=_?\\||$)"); // Edit: allows _| or | as delim

    for (Matcher matcher = pattern.matcher(input); matcher.find();) {
      matches.add(matcher.group(1));
    }

    for (int idx = 0, len = matches.size(); idx < len; idx++) {
      System.out.format("[%-2d] \"%s\"%n", idx + 1, matches.get(idx));
    }

    assertSame(expectedSize, matches.size());
    assertEquals(expected, matches);
  }
}
