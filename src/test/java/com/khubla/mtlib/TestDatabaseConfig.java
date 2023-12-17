package com.khubla.mtlib;

import com.khubla.mtlib.db.DatabaseConfig;

public class TestDatabaseConfig extends DatabaseConfig {
   public TestDatabaseConfig() {
      super("minetest", 6379, "mt", "abc123!!");
   }
}
