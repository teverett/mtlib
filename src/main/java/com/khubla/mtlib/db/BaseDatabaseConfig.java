package com.khubla.mtlib.db;

public class BaseDatabaseConfig implements DatabaseConfig {
   private String hostname;
   private int port;
   private String hash;
   private String password;

   @Override
   public String getHostname() {
      return hostname;
   }

   public void setHostname(String hostname) {
      this.hostname = hostname;
   }

   @Override
   public int getPort() {
      return port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   @Override
   public String getHash() {
      return hash;
   }

   public void setHash(String hash) {
      this.hash = hash;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
