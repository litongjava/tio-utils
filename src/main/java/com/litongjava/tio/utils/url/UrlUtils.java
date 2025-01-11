package com.litongjava.tio.utils.url;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class UrlUtils {

  // 按 RFC 3986 定义，未保留字符集合
  private static final String UNRESERVED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
  // 创建一个 BitSet 标识未保留字符的字节值
  private static final BitSet UNRESERVED_CHARACTERS = new BitSet(256);
  static {
    for (char c : UNRESERVED.toCharArray()) {
      UNRESERVED_CHARACTERS.set(c);
    }
  }

  /**
   * 将字符串按照 RFC 3986 进行 URL 编码
   * @param value 要编码的字符串
   * @return 编码后的字符串
   */
  public static String encode(String value) {
    if (value == null) {
      return null;
    }
    StringBuilder encoded = new StringBuilder();
    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
    for (byte b : bytes) {
      // 将字节转换为无符号的整数值
      int c = b & 0xFF;
      if (UNRESERVED_CHARACTERS.get(c)) {
        // 未保留字符直接追加
        encoded.append((char) c);
      } else {
        // 其他字符编码为 %HH 格式
        encoded.append(String.format("%%%02X", c));
      }
    }
    return encoded.toString();
  }

  /**
   * 将按照 RFC 3986 编码的字符串解码
   * @param value 已编码的字符串
   * @return 解码后的字符串
   */
  public static String decode(String value) {
    if (value == null) {
      return null;
    }
    // 使用 ByteArrayOutputStream 来处理解码后的字节
    int length = value.length();
    byte[] buffer = new byte[length];
    int pos = 0;
    for (int i = 0; i < length; i++) {
      char c = value.charAt(i);
      if (c == '%') {
        if (i + 2 < length) {
          // 将后两位视为十六进制数
          String hex = value.substring(i + 1, i + 3);
          buffer[pos++] = (byte) Integer.parseInt(hex, 16);
          i += 2;
        } else {
          throw new IllegalArgumentException("Invalid percent-encoding in: " + value);
        }
      } else {
        // 直接存储普通字符的字节
        buffer[pos++] = (byte) c;
      }
    }
    return new String(buffer, 0, pos, StandardCharsets.UTF_8);
  }
}
