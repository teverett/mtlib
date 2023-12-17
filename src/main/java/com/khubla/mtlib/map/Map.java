package com.khubla.mtlib.map;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.db.DatabaseConfig;
import com.khubla.mtlib.db.MapEntryIterator;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

/**
 * Minetest database
 */
public class Map implements MapEntryIterator {
   private final Database database;
   private final BlockIterator blockIterator;

   public Map(DatabaseConfig databaseConfig, BlockIterator blockIterator) {
      this.blockIterator = blockIterator;
      this.database = new Database(databaseConfig);
   }

   public long size() {
      return database.size();
   }

   public Block get(Coord coord) throws MTLibException {
      String blockData = database.get(coord.writeToString());
      if (null != blockData) {
         Block block = new Block();
         block.readFromString(blockData);
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
   public void mapEntry(String key, String value) throws MTLibException {
      Coord coord = new Coord();
      coord.readFromString(key);
      Block block = new Block();
      block.readFromString(value);
      this.blockIterator.block(coord, block);
   }
}
