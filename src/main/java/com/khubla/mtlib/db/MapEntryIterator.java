package com.khubla.mtlib.db;

import com.khubla.mtlib.util.MTLibException;

public interface MapEntryIterator {
   void mapEntry(String key, byte[] value) throws MTLibException;
}

