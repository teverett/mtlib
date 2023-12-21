package com.khubla.mtlib;

import com.khubla.mtlib.domain.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

