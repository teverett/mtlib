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

   public NameIdMapping() {
   }

   public Short addMapping(String name) throws MTLibException {
      Short id = nameToIdMap.get(name);
      if (null != id) {
         throw new MTLibException("name " + name + " already exists with id: " + id);
      }
      // next index
      id = (short) idToNameMap.size();
      this.addMapping(name, id);
      return id;
   }

   public void addMapping(String name, Short id) {
      nameToIdMap.put(name, id);
      idToNameMap.put(id, name);
   }

   public Short getId(String name) {
      return nameToIdMap.get(name);
   }

   public String getName(Short id) {
      return idToNameMap.get(id);
   }

   public HashMap<String, Short> getNameToIdMap() {
      return nameToIdMap;
   }

   public HashMap<Short, String> getIdToNameMap() {
      return idToNameMap;
   }

   public void read(DataInputStream dis, byte version) throws MTLibException {
      try {
         byte name_id_mapping_version = dis.readByte();
         if (0 != name_id_mapping_version) {
            throw new MTLibException("Unexpected name_id_mapping_version: " + name_id_mapping_version);
         }
         short num_name_id_mappings = dis.readShort();
         for (int i = 0; i < num_name_id_mappings; i++) {
            short id = dis.readShort();
            String s = StringPersistence.read16(dis);
            addMapping(s, id);
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   public void write(DataOutputStream dos, byte version) throws MTLibException {
      try {
         dos.writeByte((byte) 0); // name_id_mapping_version, always 0
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
