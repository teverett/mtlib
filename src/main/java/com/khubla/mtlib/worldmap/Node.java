package com.khubla.mtlib.worldmap;

import com.khubla.mtlib.domain.Coord;

/**
 * Note that Node is not a persistent class.  It exists for convenience
 */
public class Node {
   private byte param2;
   private byte param1;
   private short param0;
   private String nodeType;
   private Coord blockCoord;
   private Coord nodeCoord;

   public Coord getBlockCoord() {
      return blockCoord;
   }

   public void setBlockCoord(Coord blockCoord) {
      this.blockCoord = blockCoord;
   }

   public Coord getNodeCoord() {
      return nodeCoord;
   }

   public void setNodeCoord(Coord nodeCoord) {
      this.nodeCoord = nodeCoord;
   }

   public String getNodeType() {
      return nodeType;
   }

   public void setNodeType(String nodeType) {
      this.nodeType = nodeType;
   }

   public byte getParam2() {
      return param2;
   }

   public void setParam2(byte param2) {
      this.param2 = param2;
   }

   public byte getParam1() {
      return param1;
   }

   public void setParam1(byte param1) {
      this.param1 = param1;
   }

   public short getParam0() {
      return param0;
   }

   public void setParam0(short param0) {
      this.param0 = param0;
   }
}
