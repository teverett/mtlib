package com.khubla.mtlib;

import com.khubla.mtlib.map.ZCompression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestZCompression {
   private static final String TEST_STRING = "We were very tired, we were very merry,\n" + "We had gone back and forth all night on the ferry";

   @Test
   public void testZCompression() {
      try {
         byte[] compressed = ZCompression.compress(TEST_STRING.getBytes());
         assertNotNull(compressed);
         assertTrue(compressed.length < TEST_STRING.length());
         String decompressed = new String(ZCompression.decompress(compressed));
         assertNotNull(decompressed);
         assertEquals(decompressed.length(), TEST_STRING.length());
         assertEquals(0, TEST_STRING.compareTo(decompressed));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
