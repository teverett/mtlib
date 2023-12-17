package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.map.CoordCallback;
import com.khubla.mtlib.map.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMapDump implements CoordCallback {
   Map map = new Map(new TestingDatabaseConfig());

   @Test
   @Disabled
   public void testMapDump() {
      try {
         map.iterateCoords(this);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void coord(Coord coord) {
      System.out.println(coord.toString());
      Block b = map.get(coord);
      assertNotNull(b);
   }

}
