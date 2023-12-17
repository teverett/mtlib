package com.khubla.mtlib.domain;

public interface StringSerializable {
   void readFromString(String s);

   String writeToString();
}
