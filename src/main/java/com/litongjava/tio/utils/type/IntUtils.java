package com.litongjava.tio.utils.type;

public class IntUtils {

  public static Integer ifNull(Integer i) {
    if (i == null) {
      i = 20;
    }
    return i;
  }

  public static Integer ifNull(int i) {
    if (i == 0) {
      i = 20;
    }
    return i;
  }
}
