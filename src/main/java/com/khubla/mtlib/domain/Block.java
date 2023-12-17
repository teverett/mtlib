package com.khubla.mtlib.domain;

public class Block implements StringSerializable {
   private byte flags;
   private int m_lighting_complete;
   private byte content_width;
   private byte params_width;
   private byte[] bulk_data;
   private byte[] metadata;
   private byte[] objects;
   private long timestamp;
   private byte[] nimap;
   private byte[] node_timers;

   @Override
   public void readFromString(String s) {
      System.out.println(s);
   }

   @Override
   public String writeToString() {
      return null;
   }
}
