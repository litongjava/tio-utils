package com.litongjava.tio.utils.base;

import com.litongjava.db.OkResult;

public class LongUtils {

  @SuppressWarnings("unchecked")
  public static OkResult<Long> valueOf(String strId) {
    try {
      return OkResult.ok(Long.valueOf(strId));
    } catch (Exception e) {
      return OkResult.exception(e);
    }
  }

}
