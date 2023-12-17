package com.khubla.mtlib.db;

import com.khubla.mtlib.util.MTLibException;

public interface MapEntryIterator {
   void mapEntry(String key, String value) throws MTLibException;
}

