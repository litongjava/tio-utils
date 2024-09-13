package com.litongjava.tio.utils.json;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * JFinalJson 与 FastJson 混合做 json 转换 toJson 用 JFinalJson，parse 用 FastJson
 * <p>
 * 注意： 1：需要添加 fastjson 相关 jar 包 2：parse 方法转对象依赖于 setter 方法
 */
public class MixedJson extends Json {

  private TioJson tioJson;
  private FastJson2 fastJson;

  public static MixedJson getJson() {
    return new MixedJson();
  }

  public String toJson(Object object) {
    return getJFinalJson().toJson(object);
  }

  public <T> T parse(String jsonString, Class<T> type) {
    return getFastJson().parse(jsonString, type);
  }

  private TioJson getJFinalJson() {
    if (tioJson == null) {
      tioJson = TioJson.getJson();
    }
    if (datePattern != null) {
      tioJson.setDatePattern(datePattern);
    }
    return tioJson;
  }

  private FastJson2 getFastJson() {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    if (datePattern != null) {
      fastJson.setDatePattern(datePattern);
    }
    return fastJson;
  }

  @Override
  public Map<?, ?> parseToMap(String json) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parseToMap(json);
  }

  @Override
  public <K, V> Map<K, V> parseToMap(String json, Class<K> kType, Class<V> vType) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parseToMap(json, kType, vType);
  }

  @Override
  public Object parseObject(String jsonString) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parseObject(jsonString);
  }

  @Override
  public Object parseArray(String jsonString) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parseArray(jsonString);
  }

  @Override
  public <K, V> List<Map<K, V>> parseToListMap(String stringValue, Class<K> kType, Class<V> vType) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parseToListMap(stringValue, kType, vType);
  }

  @Override
  public Object parse(String stringValue) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parse(stringValue);
  }

  @Override
  public byte[] toJsonBytes(Object object) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.toJsonBytes(object);
  }

  @Override
  public <T> T parse(String body, Type type) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parse(body, type);
  }

  @Override
  public <T> T parse(byte[] body, Type type) {
    if (fastJson == null) {
      fastJson = FastJson2.getJson();
    }
    return fastJson.parse(body, type);
  }
}
