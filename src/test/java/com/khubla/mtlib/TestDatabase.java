package com.khubla.mtlib;

import com.khubla.mtlib.db.Database;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
   @Test
   public void testGet() {
      try {
         Database database = new Database(new TestingDatabaseConfig());
         byte[] block = database.get("-184561686");
         assertNotNull(block);
         //         System.out.println(block);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testSize() {
      try {
         Database database = new Database(new TestingDatabaseConfig());
         long size = database.size();
         assertTrue(size != 0);
         //       System.out.println("Database size:" + size);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testKeys() {
      try {
         Database database = new Database(new TestingDatabaseConfig());
         long size = database.size();
         assertTrue(size != 0);
         Set<String> keys = database.allKeys();
         assertEquals(size, keys.size());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   @Disabled
   public void testKeyIterator() {
      try {
         Database database = new Database(new TestingDatabaseConfig());
         database.iterateMapEntries(new TestingMapEntryIterator());
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
