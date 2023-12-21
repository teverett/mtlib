package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.worldmap.BlockIterator;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestReadWrite extends BaseTest implements BlockIterator {
   DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, this);
   Database database = new Database(propertiesFileDatabaseConfig);

   @Test
   @Disabled
   public void testFullReadWrite() {
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
      /*
       get redis bytes
       */
      byte[] originalBytes = database.get(block.getKey());
      assertNotNull(originalBytes);
      /*
       read to a block
       */
      Block blok = map.getBlock(coord);
      assertNotNull(blok);
      /*
       write to a output stream
       */
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      byte[] writtenBytes = blok.write();
      assertNotNull(writtenBytes);
      // check that the byte arrays are the same size
      assertEquals(writtenBytes.length, originalBytes.length);
      /*
       * check byte by byte
       */
      int l = writtenBytes.length > originalBytes.length ? writtenBytes.length : originalBytes.length;
      for (int i = 0; i < l; i++) {
         byte orig = originalBytes[i];
         byte written = writtenBytes[i];
         if (orig != written) {
            System.out.println("difference at index: " + i);
         }
      }
      /*
       read it back, useful for finding issues
       */
      Block block2 = new Block("");
      block2.read(writtenBytes);
   }
}
