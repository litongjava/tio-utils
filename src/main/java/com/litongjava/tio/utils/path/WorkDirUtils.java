package com.litongjava.tio.utils.path;

import java.io.File;

public class WorkDirUtils {

  public static final String workingDir = new File(".").getAbsolutePath();
  public static final String workingDataDir = new File("data").getAbsolutePath();
  public static final String workingCacheDir = new File("cache").getAbsolutePath();
  public static final String workingResourcesDir = new File("resources").getAbsolutePath();
  public static final String workingPagesDir = new File("pages").getAbsolutePath();
  public static final String workingScriptsDir = new File("scripts").getAbsolutePath();

  public static String getWorkingDir(String subPath) {
    File file = new File(subPath);
    if (!file.exists()) {
      file.mkdirs();
    }
    return file.getAbsolutePath();
  }

  public static String getWorkingDir() {
    File file = new File(".");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingDir;
  }

  public static String workingDataDir() {
    File file = new File("data");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingDataDir;
  }

  public static String workingResourcesDir() {
    File file = new File("resources");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingResourcesDir;
  }

  public static String workingPagesDir() {
    File file = new File("pages");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingPagesDir;
  }

  public static String workingScriptsDir() {
    File file = new File("scripts");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingScriptsDir;
  }

  public static String workingCacheDir() {
    File file = new File("cache");
    if (!file.exists()) {
      file.mkdirs();
    }
    return workingCacheDir;
  }
}
