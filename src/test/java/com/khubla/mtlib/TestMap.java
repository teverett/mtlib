package com.khubla.mtlib;

import com.khubla.mtlib.map.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMap {
   @Test
   public void testSize() {
      try {
         Map map = new Map(new TestingDatabaseConfig(), null);
         long size = map.size();
         assertTrue(size != 0);
         System.out.println("Map size:" + size);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
