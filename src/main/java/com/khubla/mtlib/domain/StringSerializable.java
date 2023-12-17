package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

public interface StringSerializable {
   void readFromString(String s) throws MTLibException;

   String writeToString() throws MTLibException;
}
