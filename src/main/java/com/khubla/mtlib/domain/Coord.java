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
}
