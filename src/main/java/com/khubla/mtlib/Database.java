package com.khubla.mtlib;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

public class Database {
   private final String hostname;
   private final int port;
   private final String hash;
   private final String password;
   private final JedisPool jedisPool;

   public Database(String hostname, int port, String hash, String password) {
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
