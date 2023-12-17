package com.khubla.mtlib.db;

import java.io.IOException;

public interface MapEntryIterator {
   void mapEntry(String key, String value) throws IOException;
}

