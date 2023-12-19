package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;
import com.khubla.mtlib.util.StringSZ;

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

   public void read(DataInputStream dis) throws MTLibException {
      try {
         for (int i = 0; i < count; i++) {
            short id = dis.readShort();
            short name_len = dis.readShort();
            byte[] name = new byte[name_len];
            dis.read(name, 0, name_len);
            String s = new String(name);
            nameToIdMap.put(s, id);
            idToNameMap.put(id, s);
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
