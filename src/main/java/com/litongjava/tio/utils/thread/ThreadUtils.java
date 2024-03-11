package com.litongjava.tio.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author tanyaowu 
 * 2017年10月19日 上午9:41:46
 */
public class ThreadUtils {
  private static ExecutorService fixedThreadPool;

  public static String stackTrace() {
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    StringBuilder buf = new StringBuilder();
    for (StackTraceElement element : elements) {
      buf.append("\r\n	").append(element.getClassName()).append(".").append(element.getMethodName()).append("(")
          .append(element.getFileName()).append(":").append(element.getLineNumber()).append(")");
    }
    return buf.toString();
  }

  public static ExecutorService newFixedThreadPool(int i) {
    fixedThreadPool = Executors.newFixedThreadPool(10);
    return fixedThreadPool;
  }

  public static ExecutorService getFixedThreadPool() {
    return fixedThreadPool;
  }

}
