package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class NodeData2 extends NodeData implements StreamPersistable {
   private short[] param0;
   private byte[] param1;
   private byte[] param2;

   public void read(DataInputStream dis) throws MTLibException {
      //      try {
      //         param0 = dis.readShort();
      //         param1 = dis.readShort();
      //         param2 = dis.readShort();
      //      } catch (Exception e) {
      //         throw new MTLibException("Exception in read", e);
      //      }
   }

   public void write(DataOutputStream dos) throws MTLibException {
      //      try {
      //         dos.writeShort(param0);
      //         dos.writeShort(param1);
      //         dos.writeShort(param2);
      //      } catch (Exception e) {
      //         throw new MTLibException("Exception in write", e);
      //      }
   }
}
