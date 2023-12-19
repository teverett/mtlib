package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class NodeTimer implements StreamPersistable {
   private short position; //  ((z*16*16 + y*16 + x))
   private int timeout;
   private int elapsed;

   public int getTimeout() {
      return timeout;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public int getElapsed() {
      return elapsed;
   }

   public void setElapsed(int elapsed) {
      this.elapsed = elapsed;
   }

   public short getPosition() {
      return position;
   }

   public void setPosition(short position) {
      this.position = position;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         this.position = dis.readShort();
         this.timeout = dis.readInt();
         this.elapsed = dis.readInt();
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
   }
}
