package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NodeTimers implements StreamPersistable {
   private static final byte NODE_TIME_LENGTH = 10;
   private final List<NodeTimer> nodeTimers = new ArrayList<NodeTimer>();
   private byte timer_len;
   private short num_of_timers;

   public List<NodeTimer> getNodeTimers() {
      return nodeTimers;
   }

   public byte getTimer_len() {
      return timer_len;
   }

   public void setTimer_len(byte timer_len) {
      this.timer_len = timer_len;
   }

   public short getNum_of_timers() {
      return num_of_timers;
   }

   public void setNum_of_timers(short num_of_timers) {
      this.num_of_timers = num_of_timers;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         this.timer_len = dis.readByte();
         if (this.timer_len != 0) {
            if (this.timer_len != NODE_TIME_LENGTH) {
               throw new MTLibException("Invalid timer_len: " + timer_len);
            }
            this.num_of_timers = dis.readShort();
            for (int i = 0; i < num_of_timers; i++) {
               NodeTimer nodeTimer = new NodeTimer();
               nodeTimer.read(dis, version);
               this.nodeTimers.add(nodeTimer);
            }
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         dos.writeByte(NODE_TIME_LENGTH);
         dos.writeShort((short) nodeTimers.size());
         for (int i = 0; i < nodeTimers.size(); i++) {
            NodeTimer nodeTimer = new NodeTimer();
            nodeTimer.write(dos, version);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
