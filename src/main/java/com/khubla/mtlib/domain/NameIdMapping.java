package com.khubla.mtlib.domain;

import com.khubla.mtlib.map.StringSZ;
import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NameIdMapping implements StreamPersistable {
   private final HashMap<String, Short> nameToIdMap = new HashMap<String, Short>();
   private final HashMap<Short, String> idToNameMap = new HashMap<Short, String>();

   public void read(DataInputStream dis) throws MTLibException {
      try {
         byte zero = dis.readByte();
         if (zero == 0) {
            short size = dis.readShort();
            for (int i = 0; i < size; i++) {
               short id = dis.readShort();
               String s = StringSZ.read(dis);
               nameToIdMap.put(s, id);
               idToNameMap.put(id, s);
            }
         } else {
            throw new MTLibException("Unexpected zero byte was not zero " + zero);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   public void write(DataOutputStream dos) throws MTLibException {
      try {
         dos.writeByte((byte) 0);
         dos.writeShort(nameToIdMap.size());
         for (Map.Entry<Short, String> entry : idToNameMap.entrySet()) {
            dos.writeShort(entry.getKey());
            StringSZ.write(dos, entry.getValue());
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
