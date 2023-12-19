package com.khubla.mtlib.map;

import com.khubla.mtlib.db.DatabaseConfig;
import com.khubla.mtlib.db.DatabaseEntryIterator;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

/**
 * Minetest database
 */
public class Map implements DatabaseEntryIterator {
   private final com.khubla.mtlib.db.Database database;
   private final BlockIterator blockIterator;

   public Map(DatabaseConfig databaseConfig, BlockIterator blockIterator) {
      this.blockIterator = blockIterator;
      this.database = new com.khubla.mtlib.db.Database(databaseConfig);
   }

   public long size() {
      return database.size();
   }

   public Block get(Coord coord) throws MTLibException {
      byte[] blockData = database.get(coord.write());
      if (null != blockData) {
         Block block = new Block();
         block.read(blockData);
         return block;
      } else {
         throw new MTLibException("No block at: " + coord);
      }
   }

   public void set(Coord coord, Block block) {
      // TODO
   }

   public void iterateBlocks() throws MTLibException {
      if (null != this.blockIterator) {
         database.iterateMapEntries(this);
      }
   }

   @Override
   public void entry(String key, byte[] value) throws MTLibException {
      Coord coord = new Coord();
      coord.read(key.getBytes());
      Block block = new Block();
      block.read(value);
      this.blockIterator.block(coord, block);
   }
}
