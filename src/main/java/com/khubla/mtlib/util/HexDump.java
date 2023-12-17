package com.khubla.mtlib.util;

public class HexDump {
   private static final int PAGE_WIDTH = 8;

   public static void dump(byte[] bytes, int limit) {
      int w = 0;
      int count = 0;
      System.out.printf("0x%02X: ", 0);
      //      StringBuilder s = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
         System.out.printf("0x%02X ", bytes[i]);
         //       s.append((char) bytes[i]);
         w = w + 1;
         count = count + 1;
         if (count >= limit) {
            break;
         }
         if (w == PAGE_WIDTH) {
            w = 0;
            //       System.out.println(" " + s);
            //        s = new StringBuilder();
            System.out.println();
            System.out.printf("0x%02X: ", i);
         }
      }
      System.out.println();
   }

   public static void dump(byte[] bytes) {
      dump(bytes, bytes.length + 1);
   }
}
