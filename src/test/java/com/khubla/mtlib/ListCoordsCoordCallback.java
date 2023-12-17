package com.khubla.mtlib;

import com.khubla.mtlib.domain.Coord;
import com.khubla.mtlib.map.CoordCallback;

public class ListCoordsCoordCallback implements CoordCallback {
   @Override
   public void coord(Coord coord) {
      System.out.println(coord.toString());
   }
}
