package com.khubla.mtlib.map;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

public interface BlockIterator {
   void block(Coord coord, Block block) throws MTLibException;
}
