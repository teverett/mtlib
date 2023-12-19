package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.map.DefaultMap;
import com.khubla.mtlib.map.Map;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestDefaultMap {
   @Test
   public void testSize() {
      try {
         DefaultMap map = new DefaultMap(new TestingDatabaseConfig(), null);
         Block block = map.getBlock(new Coord(0, 0, 0));
         assertNotNull(block);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testGetAllKeys() {
      try {
         Map map = new DefaultMap(new TestingDatabaseConfig());
         Database database = new Database(new TestingDatabaseConfig());
         long size = database.size();
         assertTrue(size != 0);
         Set<String> keys = database.allKeys();
         assertEquals(size, keys.size());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

