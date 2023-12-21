package com.khubla.mtlib;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDefaultWorldMap extends BaseTest {
   @Test
   public void testSize() {
      try {
         DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, null);
         Block block = map.getBlock(new Coord(0, 0, 0));
         assertNotNull(block);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

