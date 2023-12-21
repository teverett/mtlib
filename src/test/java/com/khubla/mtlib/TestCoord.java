package com.khubla.mtlib;

import com.khubla.mtlib.domain.Coord;
import org.junit.jupiter.api.Test;

import static com.khubla.mtlib.domain.Constants.BLOCK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCoord {
   @Test
   public void testCoord() {
      try {
         Coord coord1 = new Coord(10, 200, 4000);
         String c = coord1.toKey();
         Coord coord2 = new Coord();
         coord2.fromKey(c);
         assertEquals(coord1.getX(), coord2.getX());
         assertEquals(coord1.getY(), coord2.getY());
         assertEquals(coord1.getZ(), coord2.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testCoordAdd() {
      try {
         Coord coord1 = new Coord(10, 200, 4000);
         Coord coord2 = new Coord(1, 2, 4);
         Coord coord3 = coord1.add(coord2);
         assertEquals(11, coord3.getX());
         assertEquals(202, coord3.getY());
         assertEquals(4004, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testCoordSubtract() {
      try {
         Coord coord1 = new Coord(10, 200, 4000);
         Coord coord2 = new Coord(1, 2, 4);
         Coord coord3 = coord1.subtract(coord2);
         assertEquals(9, coord3.getX());
         assertEquals(198, coord3.getY());
         assertEquals(3996, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testCoordScalarMultiply() {
      try {
         Coord coord1 = new Coord(10, 200, 4000);
         Coord coord3 = coord1.scalarMultiply(16);
         assertEquals(160, coord3.getX());
         assertEquals(3200, coord3.getY());
         assertEquals(64000, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testCoordScalarDivide() {
      try {
         Coord coord1 = new Coord(16, 320, 6400);
         Coord coord3 = coord1.scalarDivide(16);
         assertEquals(1, coord3.getX());
         assertEquals(20, coord3.getY());
         assertEquals(400, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // zero block
   @Test
   public void testGetBlockCoord1() {
      try {
         Coord coord1 = new Coord(0, 0, 0);
         Coord coord3 = Coord.getBlockCoord(coord1);
         assertEquals(0, coord3.getX());
         assertEquals(0, coord3.getY());
         assertEquals(0, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   //  all positive cooords
   @Test
   public void testGetBlockCoord2() {
      try {
         Coord coord1 = new Coord(0, 31, 64);
         Coord coord3 = Coord.getBlockCoord(coord1);
         assertEquals(0, coord3.getX());
         assertEquals(1, coord3.getY());
         assertEquals(4, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // just some weird numbers (results validated via a calculator)
   @Test
   public void testGetBlockCoord3() {
      try {
         Coord coord1 = new Coord(111, 1234, 777);
         Coord coord3 = Coord.getBlockCoord(coord1);
         assertEquals(6, coord3.getX());
         assertEquals(77, coord3.getY());
         assertEquals(48, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // negative numbers
   // https://minetest.gitlab.io/minetest/map-terminology-and-coordinates/
   @Test
   public void testGetBlockCoord4() {
      try {
         Coord coord1 = new Coord(-10, -31, -200);
         Coord coord3 = Coord.getBlockCoord(coord1);
         assertEquals(-1, coord3.getX());
         assertEquals(-2, coord3.getY());
         assertEquals(-13, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // hm
   // https://minetest.gitlab.io/minetest/map-terminology-and-coordinates/
   @Test
   public void testGetBlockCoord5() {
      try {
         Coord coord1 = new Coord(-1, 0, 0);
         Coord coord3 = Coord.getBlockCoord(coord1);
         assertEquals(-1, coord3.getX());
         assertEquals(0, coord3.getY());
         assertEquals(0, coord3.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // zeros
   @Test
   public void testCalculateNodeRelativeCoords1() {
      try {
         Coord nodeCoord = new Coord(5, 6, 7);
         Coord blockCoord = Coord.getBlockCoord(nodeCoord);
         assertEquals(0, blockCoord.getX());
         assertEquals(0, blockCoord.getY());
         assertEquals(0, blockCoord.getZ());
         Coord relativeCoords = Coord.calculateNodeRelativeCoords(nodeCoord, blockCoord);
         assertEquals(5, relativeCoords.getX());
         assertEquals(6, relativeCoords.getY());
         assertEquals(7, relativeCoords.getZ());
         // add it up to make sure
         Coord testCoord = blockCoord.scalarMultiply(BLOCK_SIZE).add(relativeCoords);
         assertTrue(testCoord.eq(nodeCoord));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // positive
   @Test
   public void testCalculateNodeRelativeCoords2() {
      try {
         Coord nodeCoord = new Coord(25, 36, 77);
         Coord blockCoord = Coord.getBlockCoord(nodeCoord);
         assertEquals(1, blockCoord.getX());
         assertEquals(2, blockCoord.getY());
         assertEquals(4, blockCoord.getZ());
         Coord relativeCoords = Coord.calculateNodeRelativeCoords(nodeCoord, blockCoord);
         assertEquals(9, relativeCoords.getX());
         assertEquals(4, relativeCoords.getY());
         assertEquals(13, relativeCoords.getZ());
         // add it up to make sure
         Coord testCoord = blockCoord.scalarMultiply(BLOCK_SIZE).add(relativeCoords);
         assertTrue(testCoord.eq(nodeCoord));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // negative
   @Test
   public void testCalculateNodeRelativeCoords3() {
      try {
         Coord nodeCoord = new Coord(-25, 36, 77);
         Coord blockCoord = Coord.getBlockCoord(nodeCoord);
         assertEquals(-2, blockCoord.getX());
         assertEquals(2, blockCoord.getY());
         assertEquals(4, blockCoord.getZ());
         Coord relativeCoords = Coord.calculateNodeRelativeCoords(nodeCoord, blockCoord);
         assertEquals(7, relativeCoords.getX());
         assertEquals(4, relativeCoords.getY());
         assertEquals(13, relativeCoords.getZ());
         // add it up to make sure
         Coord testCoord = blockCoord.scalarMultiply(BLOCK_SIZE).add(relativeCoords);
         assertTrue(testCoord.eq(nodeCoord));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

