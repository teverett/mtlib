package com.khubla.mtlib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test1 {
   private static final String HOSTNAME = "minetest";
   private static final int PORT = 6379;
   private static final String HASH = "mt";
   private static final String PASSWORD = "abc123!!";

   @Test
   public void testGet() {
      try {
         Map map = new Map(HOSTNAME, PORT, HASH, PASSWORD);
         String block = map.get("16777205");
         assertNotNull(block);
         System.out.println(block);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSize() {
      try {
         Map map = new Map("minetest", 6379, "mt", "abc123!!");
         long size = map.size();
         assertTrue(size != 0);
         System.out.println(size);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
