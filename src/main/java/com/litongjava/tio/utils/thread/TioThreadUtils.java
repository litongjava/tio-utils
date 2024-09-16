package com.litongjava.tio.utils.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author Tong Li
 *
 */
public class TioThreadUtils {
  private static volatile ExecutorService fixedThreadPool;

  public static String stackTrace() {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    StringBuilder buf = new StringBuilder();
    for (StackTraceElement element : elements) {
      buf.append("\r\n\t").append(element.getClassName()).append(".").append(element.getMethodName()).append("(").append(element.getFileName()).append(":").append(element.getLineNumber()).append(")");
    }
    return buf.toString();
  }

  private static ExecutorService getFixedThreadPool() {
    if (fixedThreadPool == null) {
      synchronized (TioThreadUtils.class) {
        if (fixedThreadPool == null) {
          fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
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
