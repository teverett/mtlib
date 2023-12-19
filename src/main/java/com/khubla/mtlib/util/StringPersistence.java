package com.khubla.mtlib.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringPersistence {
   /**
    * read a null terminated string
    */
   public static String read16(DataInputStream dis) throws IOException {
      short string_len = dis.readShort();
      byte[] string = new byte[string_len];
      dis.read(string, 0, string_len);
      return new String(string);
   }

   public static String read32(DataInputStream dis) throws IOException {
      int string_len = dis.readInt();
      byte[] string = new byte[string_len];
      dis.read(string, 0, string_len);
      return new String(string);
   }

   public static void write16(DataOutputStream dos, String str) throws IOException {
      dos.writeShort(str.length());
      dos.write(str.getBytes(), 0, str.length());
   }

   public static void write32(DataOutputStream dos, String str) throws IOException {
      dos.writeInt(str.length());
      dos.write(str.getBytes(), 0, str.length());
   }

   public static String readSz(DataInputStream dis) throws IOException {
      StringBuilder sb = new StringBuilder();
      byte b = dis.readByte();
      while (0 != b) {
         sb.append(b);
         b = dis.readByte();
      }
      return sb.toString();
   }
}
