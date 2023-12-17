package com.khubla.mtlib.db;

import com.khubla.mtlib.domain.Block;
import com.khubla.mtlib.domain.Coord;

public class Map {
   private Database database;

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
}
