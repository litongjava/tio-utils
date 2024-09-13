package com.litongjava.tio.utils.type;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TioTypeReference<T> {
  private final Type type;

  protected TioTypeReference() {
    Type superclass = getClass().getGenericSuperclass();
    if (superclass instanceof ParameterizedType) {
      this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    } else {
      throw new RuntimeException("TypeReference should be used with generic types");
    }
  }

  public Type getType() {
    return this.type;
  }
}
