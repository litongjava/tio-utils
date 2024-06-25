package com.litongjava.tio.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;

/**
 * @author Tong Li
 */
public class FastJson2Utils {

  public static String toJson(Object object) {
    return JSON.toJSONString(object);
  }

  /**
   * 支持传入更多 SerializerFeature
   * <p>
   * 例如：
   * SerializerFeature.WriteMapNullValue 支持对 null 值字段的转换
   */
  public static String toJson(Object object, JSONWriter.Feature... features) {
    return JSON.toJSONString(object, features);
  }

  public static <T> T parse(String jsonString, Class<T> type) {
    return JSON.parseObject(jsonString, type);
  }

  public static JSONObject parseObject(String bodyString) {
    return JSON.parseObject(bodyString);
  }

  public static JSONArray parseArray(String jsonString) {
    return JSON.parseArray(jsonString);
  }

  public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
    return JSON.parseArray(jsonString, clazz);
  }

  public static <K, V> List<Map<K, V>> parseToListMap(String stringValue, Class<K> kType, Class<V> vType) {
    TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
    };
    JSONArray jsonArray = JSON.parseArray(stringValue);
    List<Map<K, V>> listMap = new ArrayList<>();

    for (int i = 0; i < jsonArray.size(); i++) {
      Map<K, V> map = jsonArray.getJSONObject(i).to(typeReference);
      listMap.add(map);
    }

    return listMap;
  }
}
