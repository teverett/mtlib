package com.khubla.mtlib.map;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;

import java.io.IOException;

public interface BlockIterator {
   void block(Coord coord, Block block) throws IOException;
}
