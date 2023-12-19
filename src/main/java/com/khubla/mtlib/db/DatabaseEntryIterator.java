package com.khubla.mtlib.db;

import com.khubla.mtlib.util.MTLibException;

public interface DatabaseEntryIterator {
   void entry(String key, byte[] value) throws MTLibException;
}

