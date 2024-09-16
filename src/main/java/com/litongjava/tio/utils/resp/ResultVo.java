package com.litongjava.tio.utils.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultVo {
  public static final int FAIL_CODE = 1000;
  private int code = 200;
  private String message;
  private Object data;

  public ResultVo(Object data) {
    this.data = data;
  }

  public ResultVo(int code, Object data) {
    this.code = code;
    this.data = data;
  }

  public ResultVo(String message, Object data) {
    this.message = message;
    this.data = data;
  }

  public static ResultVo ok() {
    return new ResultVo();
  }

  public static ResultVo ok(Object data) {
    return new ResultVo(data);
  }

  public static ResultVo ok(String message, Object data) {
    return new ResultVo(message, data);
  }

  public static ResultVo ok(int code, String message, Object data) {
    return new ResultVo(code, message, data);
  }

  public static ResultVo ok(int code, Object data) {
    return new ResultVo(code, data);
  }

  public static ResultVo fail() {
    return new ResultVo(FAIL_CODE);
  }

  public static ResultVo fail(String message) {
    return new ResultVo(FAIL_CODE, message);
  }

  public static ResultVo fail(String message, Object data) {
    return new ResultVo(FAIL_CODE, message, data);
  }

  public static ResultVo fail(int code, String message, Object data) {
    return new ResultVo(code, message, data);
  }

}
