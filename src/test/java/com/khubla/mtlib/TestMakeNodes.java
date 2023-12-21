package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import com.khubla.mtlib.worldmap.Node;
import org.junit.jupiter.api.Test;

public class TestMakeNodes extends BaseTest {
   DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, null);
   Database database = new Database(propertiesFileDatabaseConfig);

   @Test
   //  @Disabled
   public void testgetSetNode() {
      try {
         Coord coord = new Coord(-547, 3, 510);
         // get node
         Node node = map.getNode(coord);
         System.out.println(node.getNodeType());
         // change type
         node.setNodeType("default:dirt");
         // save node
         Block modifedBlock = map.setNode(coord, node);
         map.setBlock(modifedBlock);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
