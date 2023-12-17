package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDatabase {
   private static final String HOSTNAME = "minetest";
   private static final int PORT = 6379;
   private static final String HASH = "mt";
   private static final String PASSWORD = "abc123!!";

   @Test
   public void testGet() {
      try {
         Database database = new Database(HOSTNAME, PORT, HASH, PASSWORD);
         String block = database.get("16777205");
         assertNotNull(block);
         System.out.println(block);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSize() {
      try {
         Database database = new Database(HOSTNAME, PORT, HASH, PASSWORD);
         long size = database.size();
         assertTrue(size != 0);
         System.out.println("Database size:"+size);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testKeys() {
      try {
         Database database = new Database(HOSTNAME, PORT, HASH, PASSWORD);
         long size = database.size();
         assertTrue(size != 0);
         Set<String> keys = database.keys();
         assertTrue(size == keys.size());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
