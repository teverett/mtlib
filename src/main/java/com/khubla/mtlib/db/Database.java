package com.khubla.mtlib.db;

import com.khubla.mtlib.util.MTLibException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Redis database, in Redis format
 */
// https://javadoc.io/doc/redis.clients/jedis/latest/index.html
public class Database {
   private static final int PAGESIZE = 100;
   private final DatabaseConfig databaseConfig;
   private final JedisPool jedisPool;

   public Database(DatabaseConfig databaseConfig) {
      super();
      this.databaseConfig = databaseConfig;
      this.jedisPool = new JedisPool(databaseConfig.getHostname(), databaseConfig.getPort());
   }

   public byte[] get(long key) {
      return get(Long.toString(key));
   }

   public byte[] get(byte[] key) {
      return get(new String(key));
   }

   public byte[] get(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hget(databaseConfig.getHash().getBytes(), key.getBytes());
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

   public void set(String key, byte[] value) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         jedis.hset(databaseConfig.getHash().getBytes(), key.getBytes(), value);
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

   /**
    * paged iterate of map entries
    */
   public void iterateMapEntries(DatabaseEntryIterator databaseEntryIterator) throws MTLibException {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         ScanParams scanParams = new ScanParams().count(PAGESIZE);
         String cur = ScanParams.SCAN_POINTER_START;
         boolean done = false;
         while (!done) {
            ScanResult<Map.Entry<String, String>> scanResult = jedis.hscan(databaseConfig.getHash(), cur, scanParams);
            List<Map.Entry<String, String>> result = scanResult.getResult();
            for (Map.Entry<String, String> e : result) {
               byte[] bb = this.get(e.getKey());
               databaseEntryIterator.entry(e.getKey(), bb);
            }
            cur = scanResult.getCursor();
            if (cur.equals("0")) {
               done = true;
            }
         }
      }
   }
}
