package com.khubla.mtlib.db;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * The Redis database, in Redis format
 */
// https://www.javadoc.io/doc/redis.clients/jedis/1.4.0/redis/clients/jedis/Jedis.html
public class Database {
   private final DatabaseConfig databaseConfig;
   private final JedisPool jedisPool;

   public Database(DatabaseConfig databaseConfig) {
      super();
      this.databaseConfig = databaseConfig;
      this.jedisPool = new JedisPool(databaseConfig.getHostname(), databaseConfig.getPort());
   }

   public String get(long key) {
      return get(Long.toString(key));
   }

   public String get(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hget(databaseConfig.getHash(), key);
      }
   }

   public long size() {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hlen(databaseConfig.getHash());
      }
   }

   public void set(String key, String value) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         jedis.hset(databaseConfig.getHash(), key, value);
      }
   }

   public void remove(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         jedis.hdel(databaseConfig.getHash(), key);
      }
   }

   public Set<String> allKeys() {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hkeys(databaseConfig.getHash());
      }
   }
}
