package com.khubla.mtlib.domain;

public class Block {
   private byte flags;
   private int m_lighting_complete;
   private byte content_width;
   private byte params_width;
   private byte[] bulk_data;
   private byte[]  metadata;
   private byte [] objects;
   private long timestamp;
   private byte[] nimap;
   private byte[] node_timers;
}
