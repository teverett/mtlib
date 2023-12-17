package com.khubla.mtlib.domain;

import java.io.IOException;

public interface StringSerializable {
   void readFromString(String s) throws IOException;

   String writeToString();
}
