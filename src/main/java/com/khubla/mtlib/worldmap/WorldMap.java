package com.khubla.mtlib.worldmap;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.util.MTLibException;

public interface WorldMap {
   long size();

   Block getBlock(Coord coord) throws MTLibException;

   void setBlock(Coord coord, Block block) throws MTLibException;

   void iterateBlocks() throws MTLibException;

   Node getNode(Coord coord) throws MTLibException;

   void setNode(Coord coord, Node node) throws MTLibException;
}
