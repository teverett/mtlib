package com.khubla.mtlib;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.worldmap.BlockIterator;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestWorldMapDump extends BaseTest implements BlockIterator {
   DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, this);

   @Test
   //@Disabled
   public void testMapDump() {
      try {
         map.iterateBlocks();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // TODO, write teh block data to console
   @Override
   public void block(Coord coord, Block block) throws MTLibException {
      System.out.println(coord.toString() + " key:" + block.getKey());
      Block b = map.getBlock(coord);
      assertNotNull(b);
   }
}
