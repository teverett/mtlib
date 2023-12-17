package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCoord {
   @Test
   public void testCoord() {
      try {
         Database database = new Database(new TestDatabaseConfig());

         
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
