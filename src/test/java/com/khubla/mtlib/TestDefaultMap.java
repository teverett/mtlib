package com.khubla.mtlib;

import com.khubla.mtlib.map.DefaultMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDefaultMap {
   @Test
   public void testSize() {
      try {
         DefaultMap map = new DefaultMap(new TestingDatabaseConfig(), null);
         long size = map.size();
         assertTrue(size != 0);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

