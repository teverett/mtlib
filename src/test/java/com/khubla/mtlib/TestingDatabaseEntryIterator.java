package com.khubla.mtlib;

import com.khubla.mtlib.db.DatabaseEntryIterator;

public class TestingDatabaseEntryIterator implements DatabaseEntryIterator {
   @Override
   public void entry(String key, byte[] value) {
      System.out.println(key);
   }
}

