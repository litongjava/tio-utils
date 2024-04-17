package com.litongjava.tio.utils.json;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GsonJson extends Json {

  private Gson gson = new Gson();

  @Override
  public String toJson(Object object) {
    return gson.toJson(object);
  }

  @Override
  public <T> T parse(String jsonString, Class<T> type) {
    return gson.fromJson(jsonString, type);
  }

  @Override
  public Map<?, ?> parseToMap(String json) {
    Type mapType = new TypeToken<Map<?, ?>>() {
    }.getType();
    return gson.fromJson(json, mapType);
  }

  @Override
  public <K, V> Map<K, V> parseToMap(String json, Class<K> kType, Class<V> vType) {
    Type mapType = new TypeToken<Map<K, V>>() {
    }.getType();
    return gson.fromJson(json, mapType);
  }

  @Override
  public Object parseObject(String jsonString) {
    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(jsonString);
    return jsonElement.getAsJsonObject();
  }

  @Override
  public Object parseArray(String jsonString) {
    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(jsonString);
    return jsonElement.getAsJsonArray();
  }
}
