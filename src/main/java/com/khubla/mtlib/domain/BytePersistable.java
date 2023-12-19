package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

public interface BytePersistable {
   void read(byte[] b) throws MTLibException;

   byte[] write() throws MTLibException;
}
