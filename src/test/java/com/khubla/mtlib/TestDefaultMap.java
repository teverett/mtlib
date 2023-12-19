package com.khubla.mtlib;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.map.DefaultMap;
import com.khubla.mtlib.map.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
   public void testBlockCoords1() {
      try {
         Map map = new DefaultMap(new TestingDatabaseConfig());
         Coord coord = DefaultMap.getBlockCoord(new Coord(0, 0, 0));
         assertNotNull(coord);
         assertEquals(0, coord.getX());
         assertEquals(0, coord.getY());
         assertEquals(0, coord.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testBlockCoords2() {
      try {
         Map map = new DefaultMap(new TestingDatabaseConfig());
         Coord coord = DefaultMap.getBlockCoord(new Coord(16, 16, 16));
         assertNotNull(coord);
         assertEquals(16, coord.getX());
         assertEquals(16, coord.getY());
         assertEquals(16, coord.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testBlockCoords3() {
      try {
         Map map = new DefaultMap(new TestingDatabaseConfig());
         Coord coord = DefaultMap.getBlockCoord(new Coord(15, 15, 15));
         assertNotNull(coord);
         assertEquals(0, coord.getX());
         assertEquals(0, coord.getY());
         assertEquals(0, coord.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   @Disabled
   public void testBlockCoords4() {
      try {
         Map map = new DefaultMap(new TestingDatabaseConfig());
         Coord coord = DefaultMap.getBlockCoord(new Coord(-15, -15, -15));
         assertNotNull(coord);
         assertEquals(-16, coord.getX());
         assertEquals(-16, coord.getY());
         assertEquals(-16, coord.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

