package com.litongjava.tio.utils.string;

public class StrUtils {

  public static boolean isEmpty(String value) {
    return value == null || value.length() == 0;
  }

  public static boolean isNotEmpty(String value) {
    return value != null && value.length() != 0;
  }

}
