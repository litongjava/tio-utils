package com.litongjava.tio.utils.hutool;

import java.io.File;

public class FilenameUtils {

  public static String getSuffix(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }
    int dotIndex = filename.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == filename.length() - 1) {
      return "";
    }
    return filename.substring(dotIndex + 1);
  }

  public static String getBaseName(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }
    // 获取文件名，不包含路径
    filename = new File(filename).getName();
    int dotIndex = filename.lastIndexOf('.');
    if (dotIndex == -1) {
      return filename;
    }
    return filename.substring(0, dotIndex);
  }
}
