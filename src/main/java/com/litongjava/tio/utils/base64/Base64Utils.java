package com.litongjava.tio.utils.base64;

import com.litongjava.tio.utils.encoder.ImageVo;

public class Base64Utils {

  /**
   * This array is a lookup table that translates 6-bit positive integer
   * index values into their "Base64 Alphabet" equivalents as specified in
   * Table 1 of RFC 2045.
   */
  private static final char intToBase64[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
      'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

  /**
   * This array is a lookup table that translates 6-bit positive integer
   * index values into their "Alternate Base64 Alphabet" equivalents. This is
   * NOT the real Base64 Alphabet as per in Table 1 of RFC 2045. This
   * alternate alphabet does not use the capital letters. It is designed for
   * use in environments where "case folding" occurs.
   */
  private static final char intToAltBase64[] = { '!', '"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd',
      'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '?' };

  /**
   * This array is a lookup table that translates unicode characters drawn
   * from the "Base64 Alphabet" (as specified in Table 1 of RFC 2045) into
   * their 6-bit positive integer equivalents. Characters that are not in the
   * Base64 alphabet but fall within the bounds of the array are translated
   * to -1.
   */
  private static final byte base64ToInt[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
      24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };

  /**
   * This array is the analogue of base64ToInt, but for the nonstandard
   * variant that avoids the use of uppercase alphabetic characters.
   */
  private static final byte altBase64ToInt[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5,
      6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
      -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 22, 23, 24, 25 };

  /**
   * Translates the specified "alternate representation" Base64 string into a
   * byte array.
   */
  public static byte[] altDecodeToBytes(String s) {
    return decodeToBytes(s, true);
  }

  /**
   * Translates the specified Base64 string (as per Preferences.get(byte[]))
   * into a byte array.
   */
  public static byte[] decodeToBytes(String s) {
    return decodeToBytes(s, false);
  }

  private static byte[] decodeToBytes(String s, boolean alternate) {
    byte[] alphaToInt = alternate ? altBase64ToInt : base64ToInt;
    int sLen = s.length();
    int numGroups = sLen / 4;
    if (4 * numGroups != sLen) {
      throw new IllegalArgumentException("String length must be a multiple of four. len=" + sLen);
    }
    int missingBytesInLastGroup = 0;
    int numFullGroups = numGroups;
    if (sLen != 0) {
      if (s.charAt(sLen - 1) == '=') {
        missingBytesInLastGroup++;
        numFullGroups--;
      }
      if (s.charAt(sLen - 2) == '=') {
        missingBytesInLastGroup++;
      }
    }
    byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];

    // Translate all full groups from base64 to byte array elements
    int inCursor = 0, outCursor = 0;
    for (int i = 0; i < numFullGroups; i++) {
      int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
      int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
      int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
      int ch3 = base64toInt(s.charAt(inCursor++), alphaToInt);
      result[outCursor++] = (byte) (ch0 << 2 | ch1 >> 4);
      result[outCursor++] = (byte) (ch1 << 4 | ch2 >> 2);
      result[outCursor++] = (byte) (ch2 << 6 | ch3);
    }

    // Translate partial group, if present
    if (missingBytesInLastGroup != 0) {
      int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
      int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
      result[outCursor++] = (byte) (ch0 << 2 | ch1 >> 4);

      if (missingBytesInLastGroup == 1) {
        int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
        result[outCursor++] = (byte) (ch1 << 4 | ch2 >> 2);
      }
    }
    // assert inCursor == s.length()-missingBytesInLastGroup;
    // assert outCursor == result.length;
    return result;
  }

  /**
   * Translates the specified character, which is assumed to be in the
   * "Base 64 Alphabet" into its equivalent 6-bit positive integer.
   *
   * @throw IllegalArgumentException or ArrayOutOfBoundsException if c is not
   *        in the Base64 Alphabet.
   */
  private static int base64toInt(char c, byte[] alphaToInt) {
    int result = alphaToInt[c];
    if (result < 0) {
      throw new IllegalArgumentException("Illegal character " + c);
    }
    return result;
  }

  /**
   * Translates the specified byte array into an "alternate representation"
   * Base64 string. This non-standard variant uses an alphabet that does not
   * contain the uppercase alphabetic characters, which makes it suitable for
   * use in situations where case-folding occurs.
   */
  public static String byteArrayToAltBase64(byte[] a) {
    return encodeToString(a, true);
  }

  /**
   * Translates the specified byte array into a Base64 string as per
   * Preferences.put(byte[]).
   */
  public static String encodeToString(byte[] a) {
    return encodeToString(a, false);
  }

  private static String encodeToString(byte[] a, boolean alternate) {
    int aLen = a.length;
    int numFullGroups = aLen / 3;
    int numBytesInPartialGroup = aLen - 3 * numFullGroups;
    int resultLen = 4 * ((aLen + 2) / 3);
    StringBuilder result = new StringBuilder(resultLen);
    char[] intToAlpha = alternate ? intToAltBase64 : intToBase64;

    // Translate all full groups from byte array elements to Base64
    int inCursor = 0;
    for (int i = 0; i < numFullGroups; i++) {
      int byte0 = a[inCursor++] & 0xff;
      int byte1 = a[inCursor++] & 0xff;
      int byte2 = a[inCursor++] & 0xff;
      result.append(intToAlpha[byte0 >> 2]);
      result.append(intToAlpha[byte0 << 4 & 0x3f | byte1 >> 4]);
      result.append(intToAlpha[byte1 << 2 & 0x3f | byte2 >> 6]);
      result.append(intToAlpha[byte2 & 0x3f]);
    }

    // Translate partial group if present
    if (numBytesInPartialGroup != 0) {
      int byte0 = a[inCursor++] & 0xff;
      result.append(intToAlpha[byte0 >> 2]);
      if (numBytesInPartialGroup == 1) {
        result.append(intToAlpha[byte0 << 4 & 0x3f]);
        result.append("==");
      } else {
        // assert numBytesInPartialGroup == 2;
        int byte1 = a[inCursor++] & 0xff;
        result.append(intToAlpha[byte0 << 4 & 0x3f | byte1 >> 4]);
        result.append(intToAlpha[byte1 << 2 & 0x3f]);
        result.append('=');
      }
    }
    // assert inCursor == a.length;
    // assert result.length() == resultLen;
    return result.toString();
  }

  /**
   * Encode the given schoolId to Base64 string.
   * 
   * @param schoolId The school ID to encode
   * @return Encoded Base64 string
   */
  public static String encodeToString(String str) {
    return encodeToString(str.getBytes());
  }

  /**
   * Decode the given Base64 string to original schoolId.
   * 
   * @param base64SchoolId The Base64 encoded string
   * @return The original school ID
   */
  public static String decodeToString(String encoded) {
    return new String(decodeToBytes(encoded));
  }

  /**
   * Encodes the given image byte array into a Base64 string with the specified MIME type.
   * The resulting string is formatted as "data:{mimeType};base64,{Base64EncodedData}".
   *
   * @param imageBytes The byte array of the image to encode
   * @param mimeType   The MIME type of the image (e.g., "image/jpeg", "image/png")
   * @return A Base64-encoded string representing the image with its MIME type
   * @throws IllegalArgumentException If the imageBytes array is null or empty, or if mimeType is null or empty
   */
  public static String encodeImage(byte[] imageBytes, String mimeType) {
    if (imageBytes == null || imageBytes.length == 0) {
      throw new IllegalArgumentException("Image byte array must not be null or empty.");
    }
    if (mimeType == null || mimeType.trim().isEmpty()) {
      throw new IllegalArgumentException("MIME type must not be null or empty.");
    }

    String base64Encoded = encodeToString(imageBytes);
    return "data:" + mimeType + ";base64," + base64Encoded;
  }

  /**
   * 
   * @param imgageBase64Str "data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/"
   * @return
   */
  public static ImageVo decodeImage(String imgageBase64Str) {
    // 提取 MIME 类型和图片数据部分
    String[] parts = imgageBase64Str.split(",");
    String mimeType = parts[0].split(":")[1].split(";")[0];

    return new ImageVo(mimeType, decodeToBytes(parts[1]));
  }

}