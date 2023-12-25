package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.db.redis.RedisDatabase;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.worldmap.DefaultWorldMap;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUncompressedOneBlock extends BaseTest {
   Database database = new RedisDatabase(propertiesFileDatabaseConfig);
   DefaultWorldMap map = new DefaultWorldMap(database, null);

   @Test
   //  @Disabled
   public void testUncompressedOneBlock() {
      try {
         Coord coord = new Coord(-22, -2, -5);
         System.out.println(coord + " key:" + coord.toKey());
         /*
          * get redis bytes
          */
         byte[] originalBytes = database.get(coord.toKey());
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
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
