package com.litongjava.tio.utils.lock;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 改进版：支持无锁模式，GraalVM Native Image 友好
 * 
 * @author tanyaowu
 */
public class ObjWithLock<T> implements Serializable {
  private static final long serialVersionUID = -3048283373239453901L;
  private static Logger log = LoggerFactory.getLogger(ObjWithLock.class);

  private T obj = null;
  private ReentrantReadWriteLock lock = null;

  // 标记：当前实例是否使用线程安全容器（在构造时确定，避免每次判断）
  private final boolean isThreadSafeContainer;

  /**
   * 默认构造：不创建锁（高性能模式）
   */
  public ObjWithLock(T obj) {
    this.obj = obj;
    this.lock = null;
    // 检查是否实现了 ThreadSafeContainer 接口
    this.isThreadSafeContainer = (this instanceof ThreadSafeContainer);
  }

  /**
   * 显式指定锁（向后兼容）
   */
  public ObjWithLock(T obj, ReentrantReadWriteLock lock) {
    this.obj = obj;
    this.lock = lock;
    this.isThreadSafeContainer = (this instanceof ThreadSafeContainer);
  }

  /**
   * 获取锁（延迟创建）
   */
  public ReentrantReadWriteLock getLock() {
    if (lock == null) {
      synchronized (this) {
        if (lock == null) {
          lock = new ReentrantReadWriteLock();
        }
      }
    }
    return lock;
  }

  public WriteLock writeLock() {
    return getLock().writeLock();
  }

  public ReadLock readLock() {
    return getLock().readLock();
  }

  public T getObj() {
    return obj;
  }

  public void setObj(T obj) {
    this.obj = obj;
  }

  /**
   * 带读锁的操作 如果是 ThreadSafeContainer（MapWithLock/SetWithLock），则不加锁
   */
  public void handle(ReadLockHandler<T> readLockHandler) {
    if (isThreadSafeContainer) {
      // 线程安全容器，直接执行，不加锁
      try {
        readLockHandler.handler(obj);
      } catch (Throwable e) {
        log.error(e.getMessage(), e);
      }
    } else {
      // 非线程安全容器，使用读锁
      ReadLock readLock = readLock();
      readLock.lock();
      try {
        readLockHandler.handler(obj);
      } catch (Throwable e) {
        log.error(e.getMessage(), e);
      } finally {
        readLock.unlock();
      }
    }
  }

  /**
   * 带写锁的操作 如果是 ThreadSafeContainer（MapWithLock/SetWithLock），则不加锁
   */
  public void handle(WriteLockHandler<T> writeLockHandler) {
    if (isThreadSafeContainer) {
      // 线程安全容器，直接执行，不加锁
      try {
        writeLockHandler.handler(obj);
      } catch (Throwable e) {
        log.error(e.getMessage(), e);
      }
    } else {
      // 非线程安全容器，使用写锁
      WriteLock writeLock = writeLock();
      writeLock.lock();
      try {
        writeLockHandler.handler(obj);
      } catch (Throwable e) {
        log.error(e.getMessage(), e);
      } finally {
        writeLock.unlock();
      }
    }
  }
}