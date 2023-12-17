package com.khubla.mtlib.domain;

public class Coord {
   private final long x;
   private final long y;
   private final long z;

   public Coord(long x, long y, long z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public long getY() {
      return y;
   }

   public long getZ() {
      return z;
   }

   public long getX() {
      return x;
   }

   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public long toRedisLong() {
      return z * 0x1000000 + y * 0x1000 + x;
   }

   // https://github.com/minetest/minetest/blob/master/src/database/database.cpp
   public static Coord parseRedisLong(String c) {
      long ll = Long.parseLong(c);
      long x = ll % 4096;
      ll = (ll - x) / 4096;
      long y = ll % 4096;
      ll = (ll - y) / 4096;
      long z = ll % 4096;
      return new Coord(x, y, z);
   }

   public String toString() {
      return "x: "+x+" y:"+y+" z:"+z;
   }
}
