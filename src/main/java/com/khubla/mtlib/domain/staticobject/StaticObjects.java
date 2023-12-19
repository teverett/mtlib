package com.khubla.mtlib.domain.staticobject;

import com.khubla.mtlib.domain.StreamPersistable;
import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StaticObjects implements StreamPersistable {
   private final List<StaticObject> staticObjects = new ArrayList<StaticObject>();
   private short count;

   public short getCount() {
      return count;
   }

   public void setCount(short count) {
      this.count = count;
   }

   @Override
   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         byte ver = dis.readByte();
         if (0 != ver) {
            throw new MTLibException("Invalid ver: " + ver);
         }
         this.count = dis.readShort();
         for (int i = 0; i < count; i++) {
            StaticObject staticObject = new StaticObject();
            staticObject.read(dis, version);
            staticObjects.add(staticObject);
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
