package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NameIdMapping {
   private final HashMap<String, Short> nameToIdMap = new HashMap<String, Short>();
   private final HashMap<Short, String> idToNameMap = new HashMap<Short, String>();

   public void read(DataInputStream dis) throws MTLibException {
      try {
         byte zero = dis.readByte();
         if (zero == 0) {
            short size = dis.readShort();
            for (int i = 0; i < size; i++) {
               short id = dis.readShort();
               String s = readStringSZ(dis);
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
            writeStringSZ(dos, entry.getValue());
         }
      } catch (Exception e) {
         throw new MTLibException("Exception in read", e);
      }
   }

   /**
    * read a null terminated string
    */
   private String readStringSZ(DataInputStream dis) throws IOException {
      StringBuilder sb = new StringBuilder();
      byte b = dis.readByte();
      while (b != 0) {
         sb.append((char) b);
         b = dis.readByte();
      }
      return sb.toString();
   }

   private void writeStringSZ(DataOutputStream dos, String str) throws IOException {
      for (int i = 0; i < str.length(); i++) {
         dos.writeByte((byte) str.charAt(i));
      }
      dos.writeByte((byte) 0);
   }
}
