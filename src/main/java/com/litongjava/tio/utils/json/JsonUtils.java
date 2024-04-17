package com.litongjava.tio.utils.json;

public class JsonUtils {

  public static String toJson(Object object) {
    return Json.getJson().toJson(object);
  }

  public static <T> T parse(String jsonString, Class<T> type) {
    return Json.getJson().parse(jsonString, type);
  }

  public static Object parseObject(String jsonString) {
    return Json.getJson().parseObject(jsonString);
  }

  public static Object parseArray(String jsonString) {
    return Json.getJson().parseArray(jsonString);
  }
}
