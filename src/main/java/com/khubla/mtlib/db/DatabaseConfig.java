package com.khubla.mtlib.db;

public class DatabaseConfig {
   private final String hostname;
   private final int port;
   private final String hash;
   private final String password;

   public DatabaseConfig(String hostname, int port, String hash, String password) {
      this.hostname = hostname;
      this.port = port;
      this.hash = hash;
      this.password = password;
   }

   public String getHostname() {
      return hostname;
   }

   public int getPort() {
      return port;
   }

   public String getHash() {
      return hash;
   }

   public String getPassword() {
      return password;
   }
}
