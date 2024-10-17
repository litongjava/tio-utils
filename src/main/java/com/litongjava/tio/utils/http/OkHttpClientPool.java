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

public enum OkHttpClientPool {
  INSTANCE;

  static okhttp3.OkHttpClient.Builder builder30Second;
  static okhttp3.OkHttpClient.Builder builder3600Second;
  static {
    builder30Second = new OkHttpClient().newBuilder();
    // 连接池
    builder30Second.connectionPool(pool());
    // 信任连接
    builder30Second.sslSocketFactory(sslSocketFactory(), x509TrustManager());
    // 连接超时
    builder30Second.connectTimeout(30L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).build();

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