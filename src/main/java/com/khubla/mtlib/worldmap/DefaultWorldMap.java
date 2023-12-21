package com.khubla.mtlib.worldmap;

import com.khubla.mtlib.db.DatabaseConfig;
import com.khubla.mtlib.db.DatabaseEntryIterator;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

import static com.khubla.mtlib.domain.Constants.BLOCK_SIZE;

/**
 * Minetest World map
 */
public class DefaultWorldMap implements WorldMap, DatabaseEntryIterator {
   private final com.khubla.mtlib.db.Database database;
   private final BlockIterator blockIterator;

   public DefaultWorldMap(DatabaseConfig databaseConfig, BlockIterator blockIterator) {
      this.blockIterator = blockIterator;
      this.database = new com.khubla.mtlib.db.Database(databaseConfig);
   }

   public DefaultWorldMap(DatabaseConfig databaseConfig) {
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

   /*
    * get the coords of the block that holds a specific node
    */
   // https://minetest.gitlab.io/minetest/map-terminology-and-coordinates/
   private Coord calculateNodeBlockCoords(Coord nodeCoords) {
      Coord blockCoord = new Coord();
      blockCoord.setX((long) Math.floor(nodeCoords.getX() / BLOCK_SIZE));
      blockCoord.setY((long) Math.floor(nodeCoords.getY() / BLOCK_SIZE));
      blockCoord.setZ((long) Math.floor(nodeCoords.getZ() / BLOCK_SIZE));
      return blockCoord;
   }

   /*
    * calculate a relatives offsets of Node in its host Block
    */
   private Coord calculateNodeRelativeCoords(Coord nodeCoords, Coord blockCoords) throws MTLibException {
      Coord relativeCoord = new Coord();
      relativeCoord.setX(nodeCoords.getX() - blockCoords.getX() * BLOCK_SIZE);
      relativeCoord.setY(nodeCoords.getY() - blockCoords.getY() * BLOCK_SIZE);
      relativeCoord.setZ(nodeCoords.getZ() - blockCoords.getZ() * BLOCK_SIZE);
      if ((Math.abs(relativeCoord.getX()) < 0) || (Math.abs(relativeCoord.getX()) > BLOCK_SIZE)) {
         throw new MTLibException("Invalid X relative Coord: " + relativeCoord.getX());
      }
      if ((Math.abs(relativeCoord.getY()) < 0) || (Math.abs(relativeCoord.getY()) > BLOCK_SIZE)) {
         throw new MTLibException("Invalid Y relative Coord" + relativeCoord.getY());
      }
      if ((Math.abs(relativeCoord.getZ()) < 0) || (Math.abs(relativeCoord.getZ()) > BLOCK_SIZE)) {
         throw new MTLibException("Invalid Z relative Coord" + relativeCoord.getZ());
      }
      return relativeCoord;
   }

   @Override
   public Node getNode(Coord coord) throws MTLibException {
      Coord blockCoord = calculateNodeBlockCoords(coord);
      Block block = this.getBlock(blockCoord);
      if (null == block) {
         throw new MTLibException("Unable to find block at coords " + blockCoord + " for node at coords: " + coord);
      }
      Coord relativeCoord = calculateNodeRelativeCoords(coord, blockCoord);
      validateRelativeCoords(coord, blockCoord, relativeCoord);
      return block.getNode(relativeCoord);
   }

   /*
    * a little check
    */
   private void validateRelativeCoords(Coord nodeCoord, Coord blockCoord, Coord relativeCoord) throws MTLibException {
      long nodeX = blockCoord.getX() * BLOCK_SIZE + relativeCoord.getX();
      if (nodeX != nodeCoord.getX()) {
         throw new MTLibException("Node calculation failed for X");
      }
      long nodeY = blockCoord.getY() * BLOCK_SIZE + relativeCoord.getY();
      if (nodeY != nodeCoord.getY()) {
         throw new MTLibException("Node calculation failed for Y");
      }
      long nodeZ = blockCoord.getZ() * BLOCK_SIZE + relativeCoord.getZ();
      if (nodeZ != nodeCoord.getZ()) {
         throw new MTLibException("Node calculation failed for Z");
      }
   }

   @Override
   public void setNode(Coord coord, Node node) throws MTLibException {
      Coord blockCoord = calculateNodeBlockCoords(coord);
      Block block = this.getBlock(blockCoord);
      if (null == block) {
         throw new MTLibException("Unable to find block at coords " + blockCoord + " for node at coords: " + coord);
      }
      Coord relativeCoord = calculateNodeRelativeCoords(coord, blockCoord);
      validateRelativeCoords(coord, blockCoord, relativeCoord);
      block.setNode(relativeCoord, node);
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
