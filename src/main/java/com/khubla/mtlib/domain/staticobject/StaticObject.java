package com.khubla.mtlib.domain.staticobject;

import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class StaticObject implements StreamPersistable {
   private byte type;

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         byte type = dis.readByte();
         if ((type < 0) || (type > 7)) {
            throw new MTLibException("Invalid type: " + type);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
