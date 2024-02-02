package com.litongjava.tio.utils.cache;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.tio.utils.hutool.StrUtil;

/**
 * @author tanyaowu 
 * 2018年10月21日 下午3:45:26
 */
public abstract class AbsCache implements ICache {
  @SuppressWarnings("unused")
  private static Logger log = LoggerFactory.getLogger(AbsCache.class);

  protected String cacheName = null;

  /**
   * Time to Live (TTL)：条目在被创建后固定时间内有效，之后会被自动删除。
   */
  private Long timeToLiveSeconds;

  /**
   * Time to Idle (TTI)：条目在最后一次被访问后固定时间内有效，如果在此期间没有被再次访问，则会被删除。
   */
  private Long timeToIdleSeconds;

  /**
   * 
   * @author tanyaowu
   */
  public AbsCache(String cacheName) {
    if (StrUtil.isBlank(cacheName)) {
      throw new RuntimeException("cacheName不允许为空");
    }
    this.setCacheName(cacheName);
  }

  public AbsCache(String cacheName, Long timeToLiveSeconds, Long timeToIdleSeconds) {
    if (StrUtil.isBlank(cacheName)) {
      throw new RuntimeException("cacheName不允许为空");
    }
    this.setCacheName(cacheName);
    this.setTimeToLiveSeconds(timeToLiveSeconds);
    this.setTimeToIdleSeconds(timeToIdleSeconds);
  }

  /**
   * @return the cacheName
   */
  public String getCacheName() {
    return cacheName;
  }

  /**
   * @param cacheName the cacheName to set
   */
  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  public Long getTimeToLiveSeconds() {
    return timeToLiveSeconds;
  }

  public void setTimeToLiveSeconds(Long timeToLiveSeconds) {
    this.timeToLiveSeconds = timeToLiveSeconds;
  }

  public Long getTimeToIdleSeconds() {
    return timeToIdleSeconds;
  }

  public void setTimeToIdleSeconds(Long timeToIdleSeconds) {
    this.timeToIdleSeconds = timeToIdleSeconds;
  }

  /**
   * 根据key获取value
   * @param key
   * @return
   * @author tanyaowu
   */
  public Serializable get(String key) {
    Serializable obj = _get(key);
    if (obj instanceof ICache.NullClass) {
      return null;
    }
    return obj;
  }

  /**
   * 根据key获取value
   * @param key
   * @param clazz
   * @return
   * @author: tanyaowu
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String key, Class<T> clazz) {
    return (T) get(key);
  }

  public abstract Serializable _get(String key);
}
