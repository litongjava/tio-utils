package com.litongjava.tio.utils.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.Headers;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseVo {
  private boolean ok;
  private Headers headers;
  private byte[] bytes;
  private String body;

  public ResponseVo(boolean ok) {
    this.ok = ok;
  }

  public ResponseVo(boolean ok, Headers headers) {
    this.ok = ok;
    this.headers = headers;
  }

  public ResponseVo(boolean ok, Headers headers, byte[] bytes) {
    this.ok = ok;
    this.headers = headers;
    this.bytes = bytes;
  }

  public static ResponseVo ok(Headers headers, byte[] bytes) {
    return new ResponseVo(true, headers, bytes);
  }

  public ResponseVo(boolean ok, Headers headers, String input) {
    this.ok = ok;
    this.headers = headers;
    this.body = input;
  }

  public static ResponseVo fail(Headers headers, byte[] bytes) {
    return new ResponseVo(false, headers, bytes);
  }

  public static ResponseVo ok(Headers headers, String input) {
    return new ResponseVo(true, headers, input);
  }

  public static ResponseVo fail(Headers headers, String input) {
    return new ResponseVo(false, headers, input);
  }

}
