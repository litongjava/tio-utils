package com.litongjava.tio.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import com.litongjava.model.http.response.ResponseVo;

public class Http {

  public static ResponseVo postJson(String serverUrl, String payload) {
    return postJson(serverUrl, payload, null);
  }

  public static ResponseVo postJson(String serverUrl, String payload, Map<String, String> headers) {
    URL url = null;
    try {
      url = new URL(serverUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
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

      if (responseCode == HttpURLConnection.HTTP_OK) {
        String responseBody = null;
        try (InputStream inputStream = conn.getInputStream()) {
          byte[] readInputStream = readInputStream(inputStream);
          responseBody = new String(readInputStream, StandardCharsets.UTF_8);
        }
        return ResponseVo.ok(responseCode, responseBody);
      } else {
        String errorBody = null;
        try (InputStream errorStream = conn.getErrorStream()) {
          if (errorStream != null) { // 有时候errorStream可能为空
            byte[] readErrorStream = readInputStream(errorStream);
            errorBody = new String(readErrorStream, StandardCharsets.UTF_8);
          }
        }
        return ResponseVo.fail(responseCode, errorBody != null ? errorBody : "No error message");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
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
