package com.litongjava.tio.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class Http {

  public static ResponseVo postJson(String serverUrl, String payload) {
    return postJson(serverUrl, payload, null);
  }

  public static ResponseVo postJson(String serverUrl, String payload, Map<String, String> headers) {
    try {
      URL url = new URL(serverUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }

      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = payload.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      int responseCode = conn.getResponseCode();
      String responseBody = new String(readInputStream(conn.getInputStream()), StandardCharsets.UTF_8);

      return responseCode == HttpURLConnection.HTTP_OK ? ResponseVo.ok(responseCode, responseBody) : ResponseVo.fail(responseCode, responseBody);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static ResponseVo post(String serverUrl, Map<String, String> params) {
    return post(serverUrl, params, null);
  }

  public static ResponseVo post(String serverUrl, Map<String, String> params, Map<String, String> headers) {
    try {
      URL url = new URL(serverUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }

      String formParams = params.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));

      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = formParams.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      int responseCode = conn.getResponseCode();
      String responseBody = new String(readInputStream(conn.getInputStream()), StandardCharsets.UTF_8);

      return responseCode == HttpURLConnection.HTTP_OK ? ResponseVo.ok(responseCode, responseBody) : ResponseVo.fail(responseCode, responseBody);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static ResponseVo get(String serverUrl) {
    return get(serverUrl, null);
  }

  public static ResponseVo get(String serverUrl, Map<String, String> headers) {
    try {
      URL url = new URL(serverUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }

      int responseCode = conn.getResponseCode();
      String responseBody = new String(readInputStream(conn.getInputStream()), StandardCharsets.UTF_8);

      return responseCode == HttpURLConnection.HTTP_OK ? ResponseVo.ok(responseCode, responseBody) : ResponseVo.fail(responseCode, responseBody);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] readInputStream(InputStream inputStream) throws Exception {
    try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
      int nRead;
      byte[] data = new byte[1024];
      while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }
      return buffer.toByteArray();
    }
  }
}
