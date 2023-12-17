package com.khubla.mtlib;

import com.khubla.mtlib.db.MapEntryIterator;

public class TestingMapEntryIterator implements MapEntryIterator {
   @Override
   public void mapEntry(String key, String value) {
      System.out.println(key);
   }
}
