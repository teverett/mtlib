package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import static com.khubla.mtlib.domain.Constants.MAX_EXTENT;
import static com.khubla.mtlib.domain.Constants.MIN_EXTENT;

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
}
