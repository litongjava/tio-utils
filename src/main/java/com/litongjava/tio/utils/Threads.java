package com.litongjava.tio.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import com.litongjava.tio.utils.thread.pool.DefaultThreadFactory;
import com.litongjava.tio.utils.thread.pool.SynThreadPoolExecutor;
import com.litongjava.tio.utils.thread.pool.TioCallerRunsPolicy;

/**
 *
 * @author tanyaowu 2017年7月7日 上午11:12:03
 * groupExecutor 已经弃用,不在作为默认的TioServer线程是使用
 */
public class Threads {
  public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
  public static final int CORE_POOL_SIZE = AVAILABLE_PROCESSORS * 1;
  public static final int MAX_POOL_SIZE_FOR_TIO = Integer.getInteger("TIO_MAX_POOL_SIZE_FOR_TIO", Math.max(CORE_POOL_SIZE * 3, 64));
  public static final int MAX_POOL_SIZE_FOR_GROUP = Integer.getInteger("TIO_MAX_POOL_SIZE_FOR_GROUP", Math.max(CORE_POOL_SIZE * 16, 256));
  public static final long KEEP_ALIVE_TIME = 0L; // 360000L;
  public static final String GROUP_THREAD_NAME = "tio-group";
  public static final String WORKER_THREAD_NAME = "tio-worker";
  @SuppressWarnings("unused")
  private static final int QUEUE_CAPACITY = 1000000;
  private static ThreadPoolExecutor groupExecutor = null;
  private static SynThreadPoolExecutor tioExecutor = null;

  /**
   * 
   * @return
   * @author tanyaowu
   */
  public static ThreadPoolExecutor getGroupExecutor() {
    if (groupExecutor != null) {
      return groupExecutor;
    }

    return newGruopExecutor();
  }

  public static ThreadPoolExecutor newGruopExecutor() {
    synchronized (Threads.class) {
      LinkedBlockingQueue<Runnable> runnableQueue = new LinkedBlockingQueue<>();
      // ArrayBlockingQueue<Runnable> groupQueue = new
      // ArrayBlockingQueue<>(QUEUE_CAPACITY);
      DefaultThreadFactory threadFactory = DefaultThreadFactory.getInstance(GROUP_THREAD_NAME, Thread.MAX_PRIORITY);
      CallerRunsPolicy callerRunsPolicy = new TioCallerRunsPolicy();
      groupExecutor = new ThreadPoolExecutor(MAX_POOL_SIZE_FOR_GROUP, MAX_POOL_SIZE_FOR_GROUP, KEEP_ALIVE_TIME, TimeUnit.SECONDS, runnableQueue, threadFactory, callerRunsPolicy);
      // groupExecutor = new ThreadPoolExecutor(AVAILABLE_PROCESSORS * 2,
      // Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
      // defaultThreadFactory);

      groupExecutor.prestartCoreThread();
      // groupExecutor.prestartAllCoreThreads();
      return groupExecutor;
    }
  }

  /**
   * 
   * @return
   * @author tanyaowu
   */
  public static SynThreadPoolExecutor getTioExecutor() {
    if (tioExecutor != null) {
      return tioExecutor;
    }

    return newTioExecutor();
  }

  public static SynThreadPoolExecutor newTioExecutor() {
    synchronized (Threads.class) {
      LinkedBlockingQueue<Runnable> runnableQueue = new LinkedBlockingQueue<>();
      // ArrayBlockingQueue<Runnable> tioQueue = new
      // ArrayBlockingQueue<>(QUEUE_CAPACITY);
      DefaultThreadFactory defaultThreadFactory = DefaultThreadFactory.getInstance(WORKER_THREAD_NAME, Thread.MAX_PRIORITY);
      CallerRunsPolicy callerRunsPolicy = new TioCallerRunsPolicy();
      tioExecutor = new SynThreadPoolExecutor(MAX_POOL_SIZE_FOR_TIO, MAX_POOL_SIZE_FOR_TIO, KEEP_ALIVE_TIME, runnableQueue, defaultThreadFactory, WORKER_THREAD_NAME, callerRunsPolicy);
      // tioExecutor = new SynThreadPoolExecutor(AVAILABLE_PROCESSORS * 2,
      // Integer.MAX_VALUE, 60, new SynchronousQueue<Runnable>(),
      // defaultThreadFactory, tioThreadName);

      tioExecutor.prestartCoreThread();
      // tioExecutor.prestartAllCoreThreads();
      return tioExecutor;
    }
  }

  public static StringBuffer status() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(printThreadPoolStatus(groupExecutor, GROUP_THREAD_NAME));
    stringBuffer.append(printThreadPoolStatus(tioExecutor, WORKER_THREAD_NAME));
    return stringBuffer;
  }

  public static StringBuffer printThreadPoolStatus(ThreadPoolExecutor executor, String poolName) {
    int corePoolSize = executor.getCorePoolSize(); // 获取核心线程数
    int maximumPoolSize = executor.getMaximumPoolSize(); // 获取最大线程数
    int poolSize = executor.getPoolSize(); // 获取当前线程池的线程数（包括空闲线程）
    int activeCount = executor.getActiveCount(); // 获取当前活跃线程数
    int queueSize = executor.getQueue().size(); // 当前队列中的任务数
    long taskCount = executor.getTaskCount(); // 获取线程池已执行和未执行的任务总数
    long completedTaskCount = executor.getCompletedTaskCount(); // 获取已完成的任务数
    RejectedExecutionHandler handler = executor.getRejectedExecutionHandler();
    int remainingCapacity = executor.getQueue().remainingCapacity();

    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("线程池名称: " + poolName).append("\n");
    stringBuffer.append("核心线程数: " + corePoolSize).append("\n");
    stringBuffer.append("最大线程数: " + maximumPoolSize).append("\n");
    stringBuffer.append("当前线程数: " + poolSize).append("\n");
    stringBuffer.append("活跃线程数: " + activeCount).append("\n");
    stringBuffer.append("当前队列中的任务数: " + queueSize).append("\n");
    stringBuffer.append("已执行任务总数: " + taskCount).append("\n");
    stringBuffer.append("已完成任务数: " + completedTaskCount).append("\n");
    stringBuffer.append("当前拒绝策略: " + handler.getClass().getSimpleName()).append("\n");
    stringBuffer.append("队列剩余容量: " + remainingCapacity).append("\n");
    return stringBuffer;
  }

  public static void close() {
    groupExecutor.shutdown();
    tioExecutor.shutdown();
    groupExecutor = null;
    tioExecutor = null;
  }

  /**
   *
   */
  private Threads() {
  }
}
