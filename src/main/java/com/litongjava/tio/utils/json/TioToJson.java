package com.litongjava.tio.utils.json;

import com.litongjava.tio.utils.json.TioJsonKit.JsonResult;

public interface TioToJson<T> {
  void toJson(T value, int depth, JsonResult ret);
}