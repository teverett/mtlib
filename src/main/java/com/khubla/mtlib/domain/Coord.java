package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import static com.khubla.mtlib.domain.Constants.*;

public class Coord {
   private long x;
   private long y;
   private long z;

   public Coord() {
      x = 0;
      y = 0;
      z = 0;
   }

   public Coord(String key) {
      fromKey(key);
   }

   public Coord(long x, long y, long z) throws MTLibException {
      validate(x, y, z);
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Coord(Coord coord) {
      this.x = coord.x;
      this.y = coord.y;
      this.z = coord.z;
   }

   /*
    * get the coords of the block that holds a specific node
    */
   // https://minetest.gitlab.io/minetest/map-terminology-and-coordinates/
   public static Coord calculateNodeBlockCoords(Coord nodeCoords) {
      Coord blockCoord = new Coord();
      blockCoord.setX((long) Math.floor(nodeCoords.getX() / BLOCK_SIZE));
      blockCoord.setY((long) Math.floor(nodeCoords.getY() / BLOCK_SIZE));
      blockCoord.setZ((long) Math.floor(nodeCoords.getZ() / BLOCK_SIZE));
      return blockCoord;
   }

   /*
    * a little check
    */
   public static void validateRelativeCoords(Coord nodeCoord, Coord blockCoord, Coord relativeCoord) throws MTLibException {
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

   /*
    * return the coords of the block for any arbitrary coords
    */
   public static Coord getBlockCoord(Coord coord) throws MTLibException {
      if (null != coord) {
         Coord ret = coord.scalarDivide(BLOCK_SIZE);
         if (coord.getX() < 0) {
            ret.setX(ret.getX() - 1);
         }
         if (coord.getY() < 0) {
            ret.setY(ret.getY() - 1);
         }
         if (coord.getZ() < 0) {
            ret.setZ(ret.getZ() - 1);
         }
         return ret;
      }
      return null;
   }

   /*
    * calculate a relatives offsets of Node in its host Block
    */
   // nodeCoord is in map coordinates, blockCoords is in block coordinates
   public static Coord calculateNodeRelativeCoords(Coord nodeCoords, Coord blockCoords) throws MTLibException {
      Coord blockOriginBlockCoords = blockCoords.scalarMultiply(BLOCK_SIZE);
      Coord ret = nodeCoords.subtract(blockOriginBlockCoords);
      if ((ret.getX() < 0) || (ret.getX() > BLOCK_SIZE)) {
         throw new MTLibException("Invalid x relative Coord: " + ret.getX());
      }
      if ((ret.getY() < 0) || (ret.getY() > BLOCK_SIZE)) {
         throw new MTLibException("Invalid y relative Coord" + ret.getY());
      }
      if ((ret.getZ() < 0) || (ret.getZ() > BLOCK_SIZE)) {
         throw new MTLibException("Invalid z relative Coord" + ret.getZ());
      }
      return ret;
   }

   public boolean eq(Coord coord) {
      return (this.x == coord.x) && (this.y == coord.y) && (this.z == coord.z);
   }

   public long getY() {
      return y;
   }

   public void setY(long y) {
      this.y = y;
   }

   public long getZ() {
      return z;
   }

   public void setZ(long z) {
      this.z = z;
   }

   public long getX() {
      return x;
   }

   public void setX(long x) {
      this.x = x;
   }

   public String toString() {
      return "x: " + x + " y:" + y + " z:" + z;
   }

   public void fromKey(byte[] key) {
      fromKey(new String(key));
   }

   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public void fromKey(String key) {
      long ll = Long.parseLong(key);
      this.x = ll % 4096;
      ll = (ll - x) / 4096;
      this.y = ll % 4096;
      ll = (ll - y) / 4096;
      this.z = ll % 4096;
   }

   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public String toKey() {
      return Long.toString(z * 0x1000000 + y * 0x1000 + x);
   }

   private void validate(long x, long y, long z) throws MTLibException {
      if ((x < MIN_EXTENT) || (x > +MAX_EXTENT)) {
         throw new MTLibException("Invalid x:" + x);
      }
      if ((y < MIN_EXTENT) || (y > +MAX_EXTENT)) {
         throw new MTLibException("Invalid y:" + y);
      }
      if ((z < MIN_EXTENT) || (z > +MAX_EXTENT)) {
         throw new MTLibException("Invalid z:" + z);
      }
   }

   public Coord subtract(Coord operand) {
      Coord ret = new Coord(this);
      ret.x = ret.x - operand.x;
      ret.y = ret.y - operand.y;
      ret.z = ret.z - operand.z;
      return ret;
   }

   public Coord add(Coord operand) {
      Coord ret = new Coord(this);
      ret.x = ret.x + operand.x;
      ret.y = ret.y + operand.y;
      ret.z = ret.z + operand.z;
      return ret;
   }

   public Coord scalarMultiply(long i) {
      Coord ret = new Coord(this);
      ret.x = ret.x * i;
      ret.y = ret.y * i;
      ret.z = ret.z * i;
      return ret;
   }

   public Coord scalarDivide(long i) {
      Coord ret = new Coord(this);
      ret.x = ret.x / i;
      ret.y = ret.y / i;
      ret.z = ret.z / i;
      return ret;
   }
}
