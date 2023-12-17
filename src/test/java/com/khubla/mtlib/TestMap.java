package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMap {
   @Test
   public void testSize() {
      try {
         Database database = new Database(new TestingDatabaseConfig());
         long size = database.size();
         assertTrue(size != 0);
         System.out.println("Map size:" + size);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
