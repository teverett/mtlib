package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.map.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMapDump {
   @Test
   public void testMapDump() {
      try {
         Map map = new Map(new TestingDatabaseConfig());
         map.iterateCoords(new ListCoordsCoordCallback());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

}
