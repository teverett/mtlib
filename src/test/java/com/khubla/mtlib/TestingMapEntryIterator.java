package com.khubla.mtlib;

import com.khubla.mtlib.db.MapEntryIterator;

public class TestingMapEntryIterator implements MapEntryIterator {
   @Override
   public void mapEntry(String key, byte[] value) {
      System.out.println(key);
   }
}

