package com.khubla.mtlib.db.redis;

import com.khubla.mtlib.db.config.DatabaseConfig;
import com.khubla.mtlib.db.config.DatabaseEntryIterator;
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
public class RedisDatabase implements com.khubla.mtlib.db.Database {
   private static final int PAGESIZE = 100;
   private final DatabaseConfig databaseConfig;
   private final JedisPool jedisPool;

   public RedisDatabase(DatabaseConfig databaseConfig) {
      super();
      this.databaseConfig = databaseConfig;
      this.jedisPool = new JedisPool(databaseConfig.getHostname(), databaseConfig.getPort());
   }

   @Override
   public byte[] get(long key) {
      return get(Long.toString(key));
   }

   @Override
   public byte[] get(byte[] key) {
      return get(new String(key));
   }

   @Override
   public byte[] get(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hget(databaseConfig.getHash().getBytes(), key.getBytes());
      }
   }

   @Override
   public long size() {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         return jedis.hlen(databaseConfig.getHash());
      }
   }

   @Override
   public void set(String key, byte[] value) {
      set(key.getBytes(), value);
   }

   @Override
   public void set(byte[] key, byte[] value) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         jedis.hset(databaseConfig.getHash().getBytes(), key, value);
      }
   }

   @Override
   public void remove(String key) {
      try (Jedis jedis = jedisPool.getResource()) {
         // auth
         jedis.auth(databaseConfig.getPassword());
         // return
         jedis.hdel(databaseConfig.getHash(), key);
      }
   }

   @Override
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
   @Override
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
