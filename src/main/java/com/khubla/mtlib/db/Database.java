package com.khubla.mtlib.db;

import com.khubla.mtlib.db.config.DatabaseEntryIterator;
import com.khubla.mtlib.util.MTLibException;

import java.util.Set;

public interface Database {
   byte[] get(long key);

   byte[] get(byte[] key);

   byte[] get(String key);

   long size();

   void set(String key, byte[] value);

   void set(byte[] key, byte[] value);

   void remove(String key);

   Set<String> allKeys();

   void iterateMapEntries(DatabaseEntryIterator databaseEntryIterator) throws MTLibException;
}
