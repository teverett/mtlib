package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.worldmap.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import static com.khubla.mtlib.domain.Constants.NODES_PER_BLOCK;

public class NodeData implements StreamPersistable {
   private final short[] param0 = new short[NODES_PER_BLOCK];
   private final byte[] param1 = new byte[NODES_PER_BLOCK];
   private final byte[] param2 = new byte[NODES_PER_BLOCK];

   private int nodeIndex(short x, short y, short z) {
      return (z * 16 * 16 + y * 16 + x);
   }

   public Node getNode(short x, short y, short z) throws MTLibException {
      int idx = nodeIndex(x, y, z);
      Node node = new Node();
      node.setParam0(param0[idx]);
      node.setParam1(param1[idx]);
      node.setParam2(param2[idx]);
      return node;
   }

   public void setNode(short x, short y, short z, Node node) throws MTLibException {
      int idx = nodeIndex(x, y, z);
      param0[idx] = node.getParam0();
      param1[idx] = node.getParam1();
      param2[idx] = node.getParam2();
   }

   public short[] getParam0() {
      return param0;
   }

   public byte[] getParam1() {
      return param1;
   }

   public byte[] getParam2() {
      return param2;
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
      try {
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            dos.writeShort(param0[i]);
         }
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            dos.writeByte(param1[i]);
         }
         for (int i = 0; i < NODES_PER_BLOCK; i++) {
            dos.writeByte(param2[i]);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
