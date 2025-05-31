package com.litongjava.tio.utils.crypto;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author tanyaowu
 */
public class Md5Utils {

  /**
   * @param content
   * @param charset
   * @return
   * @throws SignatureException
   * @throws UnsupportedEncodingException
   */
  private static byte[] toBytes(String content, String charset) {
    if (charset == null || "".equals(charset)) {
      return content.getBytes();
    }
    try {
      return content.getBytes(charset);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("There was an error during the MD5 signature process, the specified character set is incorrect, the character set you currently specified is:" + charset);
    }
  }

  public static String md5Hex(String input) {
    return sign(input, "", "utf-8");
  }
  public static String md5Hex(byte[] fileContent) {
    return DigestUtils.md5Hex(fileContent);
  }

  /**
   * 签名字符串
   * @param text 需要签名的字符串
   * @param key 密钥
   * @param input_charset 编码格式
   * @return 签名结果
   */
  public static String sign(String text, String key, String input_charset) {
    text = text + key;
    return DigestUtils.md5Hex(toBytes(text, input_charset));
  }

  /**
   * 签名字符串
   * @param text 需要签名的字符串
   * @param sign 签名结果
   * @param key 密钥
   * @param input_charset 编码格式
   * @return 签名结果
   */
  public static boolean verify(String text, String sign, String key, String input_charset) {
    text = text + key;
    String mysign = DigestUtils.md5Hex(toBytes(text, input_charset));
    if (mysign.equals(sign)) {
      return true;
    } else {
      return false;
    }
  }


}
