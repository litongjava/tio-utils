package com.litongjava.tio.utils.json;

public class JsonUtils {

  public static String toJson(Object object) {
    return Json.getJson().toJson(object);
  }

  public static <T> T parse(String jsonString, Class<T> type) {
    return Json.getJson().parse(jsonString, type);
  }
}
