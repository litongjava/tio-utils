package com.litongjava.tio.utils.http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.hutool.StrUtil;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author tanyaowu
 */
public class HttpUtils {

  public static final MediaType MEDIATYPE_JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");

  /**
   * 
   * @param url
   * @param headerMap
   * @return
   * @throws Exception
   */
  public static Response get(String url, Map<String, String> headerMap) throws Exception {
    Builder builder = new Request.Builder().url(url);
    if (headerMap != null) {
      Headers headers = Headers.of(headerMap);
      builder.headers(headers);
    }
    builder.get();

    Request request = builder.build();
    OkHttpClient client = OkHttpClientPool.getHttpClient();
    Response response = client.newCall(request).execute();
    return response;
  }

  /**
   * 
   * @param url
   * @return
   * @throws Exception
   */
  public static ResponseVo get(String url) {
    Request request = new Request.Builder().url(url).get().build();
    return call(request);
  }

  public static ResponseVo uploadImage(String url, File imageFile) {
    // Create the request body with file and image media type
    @SuppressWarnings("deprecation")
    RequestBody fileBody = RequestBody.create(MediaType.parse("image"), imageFile);

    // Create MultipartBody
    okhttp3.MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    builder.addFormDataPart("file", imageFile.getName(), fileBody);

    RequestBody requestBody = builder.build();

    // Create request
    Request request = new Request.Builder().url(url).post(requestBody).build();

    ResponseVo responseVo = HttpUtils.call(request);
    return responseVo;
  }

  /**
   * 
   * @param url
   * @param headerMap
   * @param mediaType
   * @param bodyString
   * @param paramMap
   * @param paramNames
   * @param paramValues
   * @return
   * @throws Exception
   */
  private static Response post(String url, Map<String, String> headerMap, MediaType mediaType, String bodyString, Map<String, String> paramMap, List<String> paramNames, List<String> paramValues) {
    Request.Builder builder = new Request.Builder().url(url);
    if (headerMap != null) {
      Headers headers = Headers.of(headerMap);
      builder.headers(headers);
    }

    if (false == StrUtil.isBlank(bodyString)) { // 提交bodyString
      if (mediaType == null) {
        mediaType = MEDIATYPE_JSON_UTF8;
      }
      @SuppressWarnings("deprecation")
      RequestBody body = RequestBody.create(mediaType, bodyString);
      builder.post(body);
    } else { // 提交form表单
      FormBody.Builder formBodyBuilder = new FormBody.Builder();
      if (paramMap != null && paramMap.size() > 0) {
        Set<Entry<String, String>> set = paramMap.entrySet();
        for (Entry<String, String> entry : set) {
          formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
      } else if (paramNames != null) {
        int xx = paramNames.size();
        if (xx > 0) {
          for (int i = 0; i < xx; i++) {
            formBodyBuilder.add(paramNames.get(i), paramValues.get(i));
          }
        }
      }
      RequestBody formBody = formBodyBuilder.build();
      builder.post(formBody);
    }
    Request request = builder.build();
    Response response = null;
    OkHttpClient client = OkHttpClientPool.getHttpClient();
    try {
      response = client.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return response;
  }

  /**
   * 
   * @param url
   * @param headerMap
   * @param paramNames
   * @param paramValues
   * @return
   * @throws Exception
   */
  public static Response post(String url, Map<String, String> headerMap, List<String> paramNames, List<String> paramValues) throws Exception {
    return post(url, headerMap, (MediaType) null, null, null, paramNames, paramValues);
  }

  /**
   * 
   * @param url
   * @param headerMap
   * @param paramMap
   * @return
   * @throws Exception
   */
  public static Response post(String url, Map<String, String> headerMap, Map<String, String> paramMap) throws Exception {
    return post(url, headerMap, (MediaType) null, null, paramMap, null, null);
  }

  /**
   * 
   * @param url
   * @param headerMap
   * @param bodyString
   * @return
   * @throws Exception
   */
  public static Response post(String url, Map<String, String> headerMap, String bodyString) {
    return post(url, headerMap, (MediaType) null, bodyString, null, null, null);
  }

  /**
   * 
   * @param url
   * @param headerMap
   * @return
   * @throws Exception
   */
  public static Response post(String url, Map<String, String> headerMap) throws Exception {
    return post(url, headerMap, (MediaType) null, null, null, null, null);
  }

  /**
   * 
   * @param url
   * @return
   * @throws Exception
   */
  public static Response post(String url) throws Exception {
    return post(url, null);
  }

  
  public static ResponseVo call(Request request) {
    Call call = OkHttpClientPool.getHttpClient().newCall(request);
    try (Response response = call.execute()) {
      Headers headers = response.headers();
      String body = response.body().string();

      if (response.isSuccessful()) {
        return ResponseVo.ok(headers, body);
      } else {
        return ResponseVo.fail(headers, body);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
