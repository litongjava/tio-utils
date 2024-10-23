package com.litongjava.tio.utils.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tong Li
 *
 */
public class TioThreadUtils {
  private static volatile ExecutorService fixedThreadPool;

  private static ThreadFactory namedThreadFactory(String baseName) {
    AtomicInteger threadNumber = new AtomicInteger(1);
    return r -> {
      Thread thread = new Thread(r);
      thread.setName(baseName + "-" + threadNumber.getAndIncrement());
      return thread;
    };
  }

  public static ExecutorService getFixedThreadPool() {
    if (fixedThreadPool == null) {
      synchronized (TioThreadUtils.class) {
        if (fixedThreadPool == null) {
          fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4, namedThreadFactory("TioThread"));
        }
      }
    }
    return fixedThreadPool;
  }

  public static <T> Future<T> submit(Callable<T> task) {
    return getFixedThreadPool().submit(task);
  }

  public static <T> Future<T> submit(Runnable task, T result) {
    return getFixedThreadPool().submit(task, result);
  }

  public static Future<?> submit(Runnable task) {
    return getFixedThreadPool().submit(task);
  }

  public static void stop() {
    if (fixedThreadPool != null) {
      synchronized (TioThreadUtils.class) {
        if (fixedThreadPool != null) {
          fixedThreadPool.shutdownNow();
          fixedThreadPool = null;
        }
      }
    }
  }
}
