package com.khubla.mtlib.domain.staticobject;

import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringPersistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class StaticObject implements StreamPersistable {
   private static final int FIXEDPOINT_FACTOR = 1000;
   private byte type;
   private V3f v3f;
   private String data;

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         byte type = dis.readByte();
         if ((type < 0) || (type > 7)) {
            throw new MTLibException("Invalid type: " + type);
         }
         v3f = this.readV3F1000(dis);
         data = StringPersistence.read16(dis);
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   private V3f readV3F1000(DataInputStream dis) throws MTLibException {
      V3f v3f = new V3f();
      v3f.x = readF1000(dis);
      v3f.y = readF1000(dis);
      v3f.z = readF1000(dis);
      return v3f;
   }

   private void writeV3F1000(DataOutputStream dos, V3f v3f) throws MTLibException {
      writeF1000(dos, v3f.x);
      writeF1000(dos, v3f.y);
      writeF1000(dos, v3f.z);
   }

   private int readF1000(DataInputStream dis) throws MTLibException {
      try {
         return dis.readInt() / FIXEDPOINT_FACTOR;
      } catch (Exception e) {
         throw new MTLibException("Exception in readF1000", e);
      }
   }

   private void writeF1000(DataOutputStream dos, int i) throws MTLibException {
      try {
         dos.writeInt(i * FIXEDPOINT_FACTOR);
      } catch (Exception e) {
         throw new MTLibException("Exception in readF1000", e);
      }
   }

   @Override
   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         dos.writeByte(this.type);
         this.writeV3F1000(dos, this.v3f);
         StringPersistence.write16(dos, data);
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }

   public class V3f {
      public int x;
      public int y;
      public int z;
   }
}
