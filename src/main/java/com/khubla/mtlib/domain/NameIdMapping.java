package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringPersistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NameIdMapping implements StreamPersistable {
   private final HashMap<String, Short> nameToIdMap = new HashMap<String, Short>();
   private final HashMap<Short, String> idToNameMap = new HashMap<Short, String>();
   private final short count;

   public NameIdMapping(short count) {
      this.count = count;
   }

   public HashMap<String, Short> getNameToIdMap() {
      return nameToIdMap;
   }

   public HashMap<Short, String> getIdToNameMap() {
      return idToNameMap;
   }

   public short getCount() {
      return count;
   }

   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         for (int i = 0; i < count; i++) {
            short id = dis.readShort();
            String s = StringPersistence.read16(dis);
            nameToIdMap.put(s, id);
            idToNameMap.put(id, s);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         dos.writeByte((byte) 0);
         dos.writeShort(nameToIdMap.size());
         for (Map.Entry<Short, String> entry : idToNameMap.entrySet()) {
            dos.writeShort(entry.getKey());
            StringPersistence.write16(dos, entry.getValue());
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in write", e);
      }
   }
}
