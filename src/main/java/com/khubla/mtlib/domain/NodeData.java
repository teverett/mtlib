package com.khubla.mtlib.domain;

public class NodeData {
   public final static int NODES_PER_BLOCK = 4096; // 16*16*16

   public int nodeIndex(short x, short y, short z) {
      return (z * 16 * 16 + y * 16 + x);
   }
}
