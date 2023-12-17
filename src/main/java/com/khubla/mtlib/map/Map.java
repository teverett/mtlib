package com.khubla.mtlib.map;

import com.khubla.mtlib.db.Database;
import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;

import java.util.Set;

public class Map {
   private final Database database;

   public Map(String hostname, int port, String hash, String password) {
      database = new Database(hostname, port, hash, password);
   }

   public long size() {
      return database.size();
   }

   public Block get(Coord coord) {
      return null;
   }

   public void set(Coord coord, Block block) {
   }

   public void iterateCoords(CoordCallback coordCallback) {
      Set<String> coords = database.keys();
      for (String s : coords) {
         coordCallback.coord(null);
      }
   }
}
