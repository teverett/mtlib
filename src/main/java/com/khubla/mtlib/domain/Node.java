package com.khubla.mtlib.domain;

/**
 * Note that Node is not a persistent class.  It exists for convenience
 */
public class Node {
   private byte param2;
   private byte param1;
   private short param0;

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
