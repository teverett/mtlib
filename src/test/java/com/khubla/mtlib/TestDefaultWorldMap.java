package com.khubla.mtlib;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import com.khubla.mtlib.worldmap.WorldMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

   @Test
   public void testBlockCoords1() {
      try {
         WorldMap worldMap = new DefaultWorldMap(propertiesFileDatabaseConfig);
         Coord coord = Coord.getBlockCoord(new Coord(0, 0, 0));
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
         WorldMap worldMap = new DefaultWorldMap(propertiesFileDatabaseConfig);
         Coord coord = Coord.getBlockCoord(new Coord(16, 16, 16));
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
         WorldMap worldMap = new DefaultWorldMap(propertiesFileDatabaseConfig);
         Coord coord = Coord.getBlockCoord(new Coord(15, 15, 15));
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
         WorldMap worldMap = new DefaultWorldMap(propertiesFileDatabaseConfig);
         Coord coord = Coord.getBlockCoord(new Coord(-15, -15, -15));
         assertNotNull(coord);
         assertEquals(-16, coord.getX());
         assertEquals(-16, coord.getY());
         assertEquals(-16, coord.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

