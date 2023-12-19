package com.khubla.mtlib;

import com.khubla.mtlib.compress.ZLibCompression;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestZLibCompression {
   private static final String TEST_STRING = "We were very tired, we were very merry,\n" + "We had gone back and forth all night on the ferry";

   @Test
   public void testZCompression() {
      try {
         //      System.out.println(TEST_STRING.length());
         byte[] compressed = ZLibCompression.compress(TEST_STRING.getBytes(StandardCharsets.UTF_8));
         assertNotNull(compressed);
         byte[] decompressed = ZLibCompression.decompress(compressed);
         String s = new String(decompressed, StandardCharsets.UTF_8);
         assertNotNull(decompressed);
         assertEquals(s.length(), TEST_STRING.length());
         assertEquals(0, TEST_STRING.compareTo(s));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
