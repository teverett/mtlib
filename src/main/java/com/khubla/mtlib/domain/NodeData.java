package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class NodeData implements StreamPersistable {
   public final static int NODES_PER_BLOCK = 4096; // 16*16*16
   private final short[] param0 = new short[4096];
   private final byte[] param1 = new byte[4096];
   private final byte[] param2 = new byte[4096];

   public short[] getParam0() {
      return param0;
   }

   public byte[] getParam1() {
      return param1;
   }

   public byte[] getParam2() {
      return param2;
   }

   public int nodeIndex(short x, short y, short z) {
      return (z * 16 * 16 + y * 16 + x);
   }

   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            param0[i] = dis.readShort();
         }
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            param1[i] = dis.readByte();
         }
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            param2[i] = dis.readByte();
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   public void write(DataOutputStream dos, byte version) throws MTLibException {
      //      try {
      //         dos.writeShort(param0);
      //         dos.writeShort(param1);
      //         dos.writeShort(param2);
      //      } catch (Exception e) {
      //         throw new MTLibException("Exception in write", e);
      //      }
   }
}
