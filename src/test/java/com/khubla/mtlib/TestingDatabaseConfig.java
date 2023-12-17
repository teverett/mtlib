package com.khubla.mtlib;

import com.khubla.mtlib.db.DatabaseConfig;

public class TestingDatabaseConfig extends DatabaseConfig {
   public TestingDatabaseConfig() {
      super("minetest", 6379, "mt", "abc123!!");
   }
}
