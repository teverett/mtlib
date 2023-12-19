package com.khubla.mtlib.domain;

import com.khubla.mtlib.util.MTLibException;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface StreamPersistable {
   void read(DataInputStream dis, byte version) throws MTLibException;

   void write(DataOutputStream dos, byte version) throws MTLibException;
}
