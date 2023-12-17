package com.khubla.mtlib.util;

public class HexDump {
   private static final int PAGE_WIDTH = 8;

   public static void dump(byte[] bytes) {
      int w = 0;
      System.out.printf("0x%02X: ", 0);
      for (int i = 0; i < bytes.length; i++) {
         System.out.printf("0x%02X ", bytes[i]);
         w = w + 1;
         if (w == PAGE_WIDTH) {
            w = 0;
            System.out.println();
            System.out.printf("0x%02X: ", i);
         }
      }
      System.out.println();
   }
}
