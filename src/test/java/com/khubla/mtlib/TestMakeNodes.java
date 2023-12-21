package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import com.khubla.mtlib.worldmap.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMakeNodes extends BaseTest {
   DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, null);
   Database database = new Database(propertiesFileDatabaseConfig);

   protected void getNode(long x, long y, long z) {
      try {
         Coord coord = new Coord(x, y, z);
         System.out.println(coord + " key:" + coord.toKey());
         Node node = map.getNode(coord);
         assertNotNull(node);
         System.out.println(node.getNodeType());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   //  @Disabled
   public void testUncompressedOneBlock() {
      try {
         getNode(-315, 3, 116);
         //         getNode(-192, -64, 12);
         //         getNode(12, -64, -192);
         //        getNode(12, -192, -64);
         //        getNode(-64, -192, 12);
         //        getNode(-64, 12, -192);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
