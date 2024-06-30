package com.litongjava.tio.utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;

/**
 * Json 转换 fastjson 实现.
 */
public class FastJson2 extends Json {

  public static FastJson2 getJson() {
    return new FastJson2();
  }

  public String toJson(Object object) {

    return JSON.toJSONString(object);
  }

  /**
   * 支持传入更多 SerializerFeature
   * <p>
   * 例如：
   * SerializerFeature.WriteMapNullValue 支持对 null 值字段的转换
   */
  public String toJson(Object object, JSONWriter.Feature... features) {
    return JSON.toJSONString(object, features);
  }

  public <T> T parse(String jsonString, Class<T> type) {
    return JSON.parseObject(jsonString, type);
  }

  @Override
  public Map<?, ?> parseToMap(String json) {
    return JSON.parseObject(json, Map.class);
  }

  @Override
  public <K, V> Map<K, V> parseToMap(String json, Class<K> kType, Class<V> vType) {
    TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
    };

    Map<K, V> map = JSON.parseObject(json, typeReference);
    return map;
  }

  @Override
  public Object parseObject(String jsonString) {
    return JSON.parseObject(jsonString);
  }

  @Override
  public Object parseArray(String jsonString) {
    return JSON.parseArray(jsonString);
  }

  @Override
  public <K, V> List<Map<K, V>> parseToListMap(String stringValue, Class<K> kType, Class<V> vType) {
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
