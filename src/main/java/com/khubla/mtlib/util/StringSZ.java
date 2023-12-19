package com.khubla.mtlib.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringSZ {
   /**
    * read a null terminated string
    */
   public static String read(DataInputStream dis) throws IOException {
      StringBuilder sb = new StringBuilder();
      byte b = dis.readByte();
      while (b != 0) {
         sb.append((char) b);
         b = dis.readByte();
      }
      return sb.toString();
   }

   public static void write(DataOutputStream dos, String str) throws IOException {
      for (int i = 0; i < str.length(); i++) {
         dos.writeByte((byte) str.charAt(i));
      }
      dos.writeByte((byte) 0);
   }
}
