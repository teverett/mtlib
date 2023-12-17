package com.khubla.mtlib;

import com.khubla.mtlib.domain.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCoord {
   @Test
   public void testCoord() {
      try {
         Coord coord1 = new Coord(10, 200, 4000);
         System.out.println(coord1.toString());
         long ll = coord1.toRedisLong();
         String c= Long.toString(ll);
         Coord coord2 = Coord.parseRedisLong(c);
         System.out.println(coord2.toString());
         assertTrue(coord1.getX() == coord2.getX());
         assertTrue(coord1.getY() == coord2.getY());
         assertTrue(coord1.getZ() == coord2.getZ());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
