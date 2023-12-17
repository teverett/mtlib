package com.khubla.mtlib.db;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

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

   public String get(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(password);
         // return
         return jedis.hget(hash, key);
      }
   }

  public long size() {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(password);
         // return
         return jedis.hlen(hash);
      }
   }

  public void set(String key, String value) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(password);
         // return
         jedis.hset(hash, key, value);
      }
   }

   public Set<String> keys (){
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(password);
         // return
       return  jedis.hkeys(hash);
      }
   }
}
