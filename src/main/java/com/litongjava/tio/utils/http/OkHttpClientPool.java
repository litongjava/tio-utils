package com.litongjava.tio.utils.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.brotli.BrotliInterceptor;

public enum OkHttpClientPool {
  INSTANCE;

  static okhttp3.OkHttpClient.Builder builder30Second;
  static okhttp3.OkHttpClient.Builder builder60Second;
  static okhttp3.OkHttpClient.Builder builder120Second;
  static okhttp3.OkHttpClient.Builder builder300Second;
  static okhttp3.OkHttpClient.Builder builder600Second;
  static okhttp3.OkHttpClient.Builder builder1000Second;
  static okhttp3.OkHttpClient.Builder builder1200Second;
  static okhttp3.OkHttpClient.Builder builder3600Second;
  static {
    builder30Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    ;
    // 连接池
    builder30Second.connectionPool(pool());
    // 信任连接
    builder30Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder30Second.connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).build();

    builder60Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    ;
    // 连接池
    builder60Second.connectionPool(pool());
    // 信任连接
    builder60Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder60Second.connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).build();

    builder120Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    ;
    // 连接池
    builder120Second.connectionPool(pool());
    // 信任连接
    builder120Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder120Second.connectTimeout(120L, TimeUnit.SECONDS).readTimeout(120L, TimeUnit.SECONDS).build();

    builder300Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    // 连接池
    builder300Second.connectionPool(pool());
    // 信任连接
    builder300Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder300Second.connectTimeout(300L, TimeUnit.SECONDS).readTimeout(300L, TimeUnit.SECONDS).build();

    //builder600Second
    builder600Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    ;
    builder600Second.connectionPool(pool());
    builder600Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    builder600Second.connectTimeout(600L, TimeUnit.SECONDS).readTimeout(600L, TimeUnit.SECONDS).build();

    builder1000Second = new OkHttpClient().newBuilder().addInterceptor(BrotliInterceptor.INSTANCE);
    ;
    //builder1000Second
    builder1000Second.connectionPool(pool());
    builder1000Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    builder1000Second.connectTimeout(1000L, TimeUnit.SECONDS).readTimeout(1000L, TimeUnit.SECONDS).build();

    builder1200Second = new OkHttpClient().newBuilder();
    //builder1000Second
    builder1200Second.connectionPool(pool());
    builder1200Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    builder1200Second.connectTimeout(1000L, TimeUnit.SECONDS).readTimeout(1000L, TimeUnit.SECONDS).build();

    builder3600Second = new OkHttpClient().newBuilder();
    // 连接池
    builder3600Second.connectionPool(pool());
    // 信任连接
    builder3600Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder3600Second.connectTimeout(3600L, TimeUnit.SECONDS).readTimeout(3600L, TimeUnit.SECONDS).build();
  }

  public static OkHttpClient getHttpClient() {
    return builder30Second.build();
  }

  public static OkHttpClient get60HttpClient() {
    return builder60Second.build();
  }

  public static OkHttpClient get120HttpClient() {
    return builder120Second.build();
  }

  public static OkHttpClient get300HttpClient() {
    return builder300Second.build();
  }

  public static OkHttpClient get600HttpClient() {
    return builder600Second.build();
  }

  public static OkHttpClient get1000HttpClient() {
    return builder1000Second.build();
  }

  public static OkHttpClient get1200HttpClient() {
    return builder1200Second.build();
  }

  public static OkHttpClient get3600HttpClient() {
    return builder3600Second.build();
  }

  private static ConnectionPool pool() {
    return new ConnectionPool(200, 5, TimeUnit.MINUTES);
  }

  public static X509TrustManager x509TrustManager() {
    return new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }
    };
  }

  public static SSLSocketFactory sslSocketFactory() {
    try {
      // 信任任何链接
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, new TrustManager[] { x509TrustManager() }, new SecureRandom());
      return sslContext.getSocketFactory();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }
    return null;
  }

}