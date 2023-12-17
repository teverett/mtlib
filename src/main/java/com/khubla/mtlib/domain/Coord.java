package com.khubla.mtlib.domain;

public class Coord implements StringSerializable {
   private long x;
   private long y;
   private long z;

   public Coord() {
      x = 0;
      y = 0;
      z = 0;
   }

   public Coord(long x, long y, long z) {
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

   @Override
   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public void readFromString(String s) {
      long ll = Long.parseLong(s);
      this.x = ll % 4096;
      ll = (ll - x) / 4096;
      this.y = ll % 4096;
      ll = (ll - y) / 4096;
      this.z = ll % 4096;
   }

   @Override
   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public String writeToString() {
      long ll = z * 0x1000000 + y * 0x1000 + x;
      return Long.toString(ll);
   }
}
