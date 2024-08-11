package com.litongjava.tio.utils.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Tong Li
 */
public class TioThreadUtils {
  private static ExecutorService fixedThreadPool;

  static {
    fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  public static String stackTrace() {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    StringBuilder buf = new StringBuilder();
    for (StackTraceElement element : elements) {
      buf.append("\r\n	").append(element.getClassName()).append(".").append(element.getMethodName()).append("(").append(element.getFileName())
          .append(":").append(element.getLineNumber()).append(")");
    }
    return buf.toString();
  }

  public static ExecutorService getFixedThreadPool() {
    return fixedThreadPool;
  }

  public static <T> Future<T> submit(Callable<T> task) {
    return fixedThreadPool.submit(task);
  }

  public static <T> Future<T> submit(Runnable task, T result) {
    return fixedThreadPool.submit(task, result);
  }

  public static Future<?> submit(Runnable task) {
    return fixedThreadPool.submit(task);
  }
}
