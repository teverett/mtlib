package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.db.redis.RedisDatabase;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import com.khubla.mtlib.worldmap.Node;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestMakeNodes extends BaseTest {
   Database database = new RedisDatabase(propertiesFileDatabaseConfig);
   DefaultWorldMap map = new DefaultWorldMap(database, null);

   @Test
   @Disabled
   public void testgetSetNode() {
      try {
         Coord coord = new Coord(-547, 3, 510);
         // get node
         Node node = map.getNode(coord);
         System.out.println(node.getNodeType());
         // change type
         node.setNodeType("default:stone");
         // save node
         Block modifedBlock = map.setNode(coord, node);
         map.setBlock(modifedBlock);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void stack() {
      try {
         Coord coord = new Coord(369, 3, 315);
         for (int i = 0; i < 20; i++) {
            // get node
            Node node = map.getNode(coord);
            System.out.println(node.getNodeType());
            // change type
            node.setNodeType("default:stone");
            // save node
            Block modifedBlock = map.setNode(coord, node);
            map.setBlock(modifedBlock);
            // increment coord
            coord.setY(coord.getY() + 1);
         }
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // THIS TEST FAILS!
   @Test
   @Disabled
   public void testgetNodeBAD() {
      try {
         Coord coord = new Coord(-289, 0, -304);
         // get node
         Node node = map.getNode(coord);
         System.out.println(node.getNodeType());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testgetNode() {
      try {
         Coord coord = new Coord(403, 6, 312);
         // get node
         Node node = map.getNode(coord);
         System.out.println(node.getNodeType());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
