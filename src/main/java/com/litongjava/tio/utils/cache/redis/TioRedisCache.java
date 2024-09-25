package com.litongjava.tio.utils.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.tio.utils.SystemTimer;
import com.litongjava.tio.utils.cache.AbsCache;
import com.litongjava.tio.utils.hutool.StrUtil;

/**
 *
 * @author tanyaowu
 * 2017年8月10日 下午1:35:01
 */
public class TioRedisCache extends AbsCache {
  private static Logger log = LoggerFactory.getLogger(TioRedisCache.class);

  public static final String SPLIT_FOR_CACHENAME = ":";

  public static String cacheKey(String cacheName, String key) {
    return keyPrefix(cacheName) + key;
  }

  public static String keyPrefix(String cacheName) {
    return cacheName + SPLIT_FOR_CACHENAME;
  }

  private RedissonClient redisson = null;

  private Long timeToLiveSeconds = null;

  private Long timeToIdleSeconds = null;

  private Long timeout = null;

  public TioRedisCache(RedissonClient redisson, String cacheName, Long timeToLiveSeconds, Long timeToIdleSeconds) {
    super(cacheName);
    this.redisson = redisson;
    this.timeToLiveSeconds = timeToLiveSeconds;
    this.timeToIdleSeconds = timeToIdleSeconds;
    this.timeout = this.timeToLiveSeconds == null ? this.timeToIdleSeconds : this.timeToLiveSeconds;

  }

  @Override
  public void clear() {
    long start = SystemTimer.currTime;

    RKeys keys = redisson.getKeys();

    // keys.deleteByPattern(keyPrefix(cacheName) + "*");
    keys.deleteByPatternAsync(keyPrefix(cacheName) + "*");

    long end = SystemTimer.currTime;
    long iv = end - start;
    log.info("clear cache {}, cost {}ms", cacheName, iv);
  }

  @Override
  public Serializable _get(String key) {
    if (StrUtil.isBlank(key)) {
      return null;
    }
    RBucket<Serializable> bucket = getBucket(key);
    if (bucket == null) {
      log.error("bucket is null, key:{}", key);
      return null;
    }
    Serializable ret = bucket.get();
    if (timeToIdleSeconds != null) {
      if (ret != null) {
        // bucket.expire(timeout, TimeUnit.SECONDS);
        RedisExpireUpdateTask.add(cacheName, key, timeout);
      }
    }
    return ret;
  }

  public RBucket<Serializable> getBucket(String key) {
    key = cacheKey(cacheName, key);
    RBucket<Serializable> bucket = redisson.getBucket(key);
    return bucket;
  }

  public RedissonClient getRedisson() {
    return redisson;
  }

  public Long getTimeout() {
    return timeout;
  }

  public Long getTimeToIdleSeconds() {
    return timeToIdleSeconds;
  }

  public Long getTimeToLiveSeconds() {
    return timeToLiveSeconds;
  }

  @Override
  public Iterable<String> keys() {
    RKeys keys = redisson.getKeys();
    Iterable<String> allkey = keys.getKeysByPattern(keyPrefix(cacheName) + "*");
    return allkey;
  }

  @Override
  public Collection<String> keysCollection() {
    RKeys keys = redisson.getKeys();
    Iterable<String> allKeys = keys.getKeysByPattern(keyPrefix(cacheName) + "*");
    // Create a new collection to store the keys
    Collection<String> keyCollection = new ArrayList<>();

    // Add all elements from the iterable to the collection
    for (String key : allKeys) {
      keyCollection.add(key);
    }

    return keyCollection;
  }

  @Override
  public void put(String key, Serializable value) {
    if (StrUtil.isBlank(key)) {
      return;
    }
    RBucket<Serializable> bucket = getBucket(key);

    long _timeout = timeout;
    if (timeToLiveSeconds != null && timeToLiveSeconds > 0) { // 是按timeToLiveSeconds来的
      long ttl = ttl(key);
      if (ttl > 0) {
        _timeout = ttl / 1000;
      }
    }

    bucket.set(value, _timeout, TimeUnit.SECONDS);
  }

  @Override
  public void putTemporary(String key, Serializable value) {
    if (StrUtil.isBlank(key)) {
      return;
    }
    RBucket<Serializable> bucket = getBucket(key);
    bucket.set(value, 10, TimeUnit.SECONDS);
  }

  @Override
  public void remove(String key) {
    if (StrUtil.isBlank(key)) {
      return;
    }
    RBucket<Serializable> bucket = getBucket(key);
    bucket.delete();
  }

  @Override
  public long ttl(String key) {
    RBucket<Serializable> bucket = getBucket(key);
    if (bucket == null) {
      return -2L;
    }
    long remainTimeToLive = bucket.remainTimeToLive();
    return remainTimeToLive;
  }

  @Override
  public Map<String, Serializable> asMap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long size() {
    RKeys keys = redisson.getKeys();
    Iterable<String> allkeys = keys.getKeysByPattern(keyPrefix(cacheName) + "*");// .findKeysByPattern(keyPrefix(cacheName) + "*");
    // Initialize a counter
    long count = 0;

    // Iterate over the Iterable and increment the counter for each element
    for (@SuppressWarnings("unused") String key : allkeys) {
      count++;
    }

    return count;
  }

  // @Override
  // public void update(String key, Serializable value) {
  //
  // }
}
