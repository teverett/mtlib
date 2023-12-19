package com.khubla.mtlib.map;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

public interface Map {
   long size();

   Block get(Coord coord) throws MTLibException;

   void set(Coord coord, Block block) throws MTLibException;

   void iterateBlocks() throws MTLibException;
}
