package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.worldmap.BlockIterator;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestUncompressedReadWrite extends BaseTest implements BlockIterator {
   DefaultWorldMap map = new DefaultWorldMap(propertiesFileDatabaseConfig, this);
   Database database = new Database(propertiesFileDatabaseConfig);

   @Test
   //  @Disabled
   public void testUncompressedReadWrite() {
      try {
         map.iterateBlocks();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   // TODO, write teh block data to console
   @Override
   public void block(Coord coord, Block block) throws MTLibException {
      try {
         System.out.println(coord.toString() + " key:" + block.getKey());
         /*
          * get redis bytes
          */
         byte[] originalBytes = database.get(block.getKey());
         assertNotNull(originalBytes);

         /*
          * read to a block
          */
         Block blok = map.getBlock(coord);
         assertNotNull(blok);
         /*
          * get uncompressed original data
          */
         byte[] unCompressedDataFromRedis = blok.getUncompressedData(originalBytes);
         assertNotNull(unCompressedDataFromRedis);
         /*
          * uncompressed write to a output stream
          */
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         blok.writeToDataOutputStream(dos);
         baos.flush();
         byte[] writtenBytes = baos.toByteArray();
         assertNotNull(writtenBytes);
         /*
          * check for differences
          */
         assertEquals(unCompressedDataFromRedis.length, writtenBytes.length);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
