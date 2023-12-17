package com.khubla.mtlib;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Map {
   private final String hostname;
   private final int port;
   private final String hash;
   private final String password;
   private final JedisPool jedisPool;

   public Map(String hostname, int port, String hash, String password) {
      super();
      this.hostname = hostname;
      this.port = port;
      this.hash = hash;
      this.password = password;
      this.jedisPool = new JedisPool(hostname, port);
   }

   String get(String key){
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(password);
         // return
        return  jedis.hget(hash, key);
      }
   }

  long size() {
     try (Jedis jedis = jedisPool.getResource()) {
        // auth
        jedis.auth(password);
        // return
        return  jedis.hlen(hash);
     }
  }
}
