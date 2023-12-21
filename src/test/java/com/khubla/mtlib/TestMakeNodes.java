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

   @Test
   //  @Disabled
   public void testUncompressedOneBlock() {
      try {
         Coord coord = new Coord(-412, 5, 101);
         System.out.println(coord + " key:" + coord.toKey());
         Node node = map.getNode(coord);
         assertNotNull(node);
         //  System.out.println(node.getParam0());
         //  System.out.println(BlockTypes.getInstance().getName(node.getParam0()));
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
