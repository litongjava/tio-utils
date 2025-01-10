package com.litongjava.tio.utils.type;

public class IntUtils {

  public static Integer ifNull(Integer i, Integer defaultValue) {
    return i != null ? i : defaultValue;
  }

  public static Integer ifNull(Integer i, int defaultValue) {
    return i != null ? i : defaultValue;
  }

  public static Integer ifNull(int i, int defaultValue) {
    return i != 0 ? i : defaultValue;
  }
}
