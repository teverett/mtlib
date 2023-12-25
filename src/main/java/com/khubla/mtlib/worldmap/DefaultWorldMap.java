package com.khubla.mtlib.worldmap;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.db.config.DatabaseEntryIterator;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

/**
 * Minetest World map
 */
public class DefaultWorldMap implements WorldMap, DatabaseEntryIterator {
   // for unit testing purposes
   private static boolean ignoreBlockTimestampChanges = false;
   private final Database database;
   private final BlockIterator blockIterator;

   public DefaultWorldMap(Database database, BlockIterator blockIterator) {
      this.blockIterator = blockIterator;
      this.database = database;
   }

   public DefaultWorldMap(Database database) {
      this.blockIterator = null;
      this.database = database;
   }

   // for unit testing
   public static boolean isIgnoreBlockTimestampChanges() {
      return ignoreBlockTimestampChanges;
   }
   // for unit testing

   public static void setIgnoreBlockTimestampChanges(boolean ignoreBlockTimestampChanges) {
      DefaultWorldMap.ignoreBlockTimestampChanges = ignoreBlockTimestampChanges;
   }

   public long size() {
      return database.size();
   }

   public Block getBlock(Coord coord) throws MTLibException {
      byte[] blockData = database.get(coord.toKey());
      if (null != blockData) {
         Block block = new Block(coord.toKey());
         block.read(blockData);
         return block;
      } else {
         throw new MTLibException("No block at: " + coord);
      }
   }

   public void setBlock(Block block) throws MTLibException {
      byte[] data = block.write();
      database.set(block.getKey().getBytes(), data);
   }

   public void iterateBlocks() throws MTLibException {
      if (null != this.blockIterator) {
         database.iterateMapEntries(this);
      }
   }

   @Override
   public Node getNode(Coord coord) throws MTLibException {
      Coord blockCoord = Coord.getBlockCoord(coord);
      Block block = this.getBlock(blockCoord);
      if (null == block) {
         throw new MTLibException("Unable to find block at coords " + blockCoord + " for node at coords: " + coord);
      }
      Coord relativeCoord = Coord.calculateNodeRelativeCoords(coord, blockCoord);
      Coord.validateRelativeCoords(coord, blockCoord, relativeCoord);
      Node ret = block.getNode(relativeCoord);
      if (null != ret) {
         ret.setNodeCoord(coord);
         ret.setBlockCoord(blockCoord);
         return ret;
      } else {
         throw new MTLibException("Unable to find node");
      }
   }

   @Override
   public Block setNode(Coord coord, Node node) throws MTLibException {
      Coord blockCoord = Coord.getBlockCoord(coord);
      Block block = this.getBlock(blockCoord);
      if (null == block) {
         throw new MTLibException("Unable to find block at coords " + blockCoord + " for node at coords: " + coord);
      }
      Coord relativeCoord = Coord.calculateNodeRelativeCoords(coord, blockCoord);
      Coord.validateRelativeCoords(coord, blockCoord, relativeCoord);
      block.setNode(relativeCoord, node);
      return block;
   }

   @Override
   public void entry(String key, byte[] value) throws MTLibException {
      Coord coord = new Coord();
      coord.fromKey(key);
      Block block = new Block(key);
      block.read(value);
      this.blockIterator.block(coord, block);
   }
}
