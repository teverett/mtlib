package com.khubla.mtlib;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.map.BlockIterator;
import com.khubla.mtlib.map.DefaultMap;
import com.khubla.mtlib.util.MTLibException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMapDump implements BlockIterator {
   DefaultMap map = new DefaultMap(new TestingDatabaseConfig(), this);

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
      System.out.println(coord.toString());
      Block b = map.getBlock(coord);
      assertNotNull(b);
   }
}
