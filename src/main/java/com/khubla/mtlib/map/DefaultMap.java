package com.khubla.mtlib.map;

import com.khubla.mtlib.db.DatabaseConfig;
import com.khubla.mtlib.db.DatabaseEntryIterator;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.domain.Node;
import com.khubla.mtlib.util.MTLibException;

/**
 * Minetest database
 */
public class DefaultMap implements Map, DatabaseEntryIterator {
   private final com.khubla.mtlib.db.Database database;
   private final BlockIterator blockIterator;

   public DefaultMap(DatabaseConfig databaseConfig, BlockIterator blockIterator) {
      this.blockIterator = blockIterator;
      this.database = new com.khubla.mtlib.db.Database(databaseConfig);
   }

   public DefaultMap(DatabaseConfig databaseConfig) {
      this.blockIterator = null;
      this.database = new com.khubla.mtlib.db.Database(databaseConfig);
   }

   /*
    * return the coords of the block for any arbitrary coords
    */
   public static Coord getBlockCoord(Coord coord) throws MTLibException {
      if (null != coord) {
         long x = coord.getX() / 16;
         long y = coord.getY() / 16;
         long z = coord.getZ() / 16;
         return new Coord(x * 16, y * 16, z * 16);
      }
      return null;
   }

   public long size() {
      return database.size();
   }

   public Block getBlock(Coord coord) throws MTLibException {
      byte[] blockData = database.get(coord.write());
      if (null != blockData) {
         Block block = new Block(coord.toKey());
         block.read(blockData);
         return block;
      } else {
         throw new MTLibException("No block at: " + coord);
      }
   }

   public void setBlock(Coord coord, Block block) throws MTLibException {
      byte[] data = block.write();
      database.set(coord.write(), data);
   }

   public void iterateBlocks() throws MTLibException {
      if (null != this.blockIterator) {
         database.iterateMapEntries(this);
      }
   }

   @Override
   public Node getNode(Coord coord) throws MTLibException {
      return null;
   }

   @Override
   public void setNode(Coord coord, Node node) throws MTLibException {
   }

   @Override
   public void entry(String key, byte[] value) throws MTLibException {
      Coord coord = new Coord();
      coord.read(key.getBytes());
      Block block = new Block(key);
      block.read(value);
      this.blockIterator.block(coord, block);
   }
}
