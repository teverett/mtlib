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
}

