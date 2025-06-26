package com.litongjava.tio.utils.hutool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import jodd.util.ClassLoaderUtil;

/**
 * 资源工具类
 */
public class ResourceUtil {

  private static String CLASSPATH_PRE = "classpath:";

  /**
   * 获取ClassPath绝对路径
   * @param path classpath路径
   * @return 绝对路径
   */
  public static String getAbsolutePath(String path) {
    return getDecodedPath(getResource(path));
  }

  /**
   * 获得资源相对路径对应的URL
   * 
   * @param path 资源相对路径
   * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
   * @return {@link URL}
   */
  public static URL getResource(String path) {
    if (StrUtil.startWithIgnoreCase(path, CLASSPATH_PRE)) {
      path = path.substring(CLASSPATH_PRE.length());
    }
    return getClassLoader().getResource(path);
  }

  /**
   * 获取ClassPath下的资源做为流
   * 
   * @param path 相对于ClassPath路径，可以以classpath:开头
   * @return {@link InputStream}资源
   */
  public static InputStream getResourceAsStream(String path) {
    if (StrUtil.startWithIgnoreCase(path, CLASSPATH_PRE)) {
      path = path.substring(CLASSPATH_PRE.length());
    }
    return getClassLoader().getResourceAsStream(path);
  }

  /**
   * 获取{@link ClassLoader}<br>
   * 获取顺序如下：<br>
   * 
   * <pre>
   * 1、获取当前线程的ContextClassLoader
   * 2、获取{@link ClassLoaderUtil}类对应的ClassLoader
   * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
   * </pre>
   * 
   * @return 类加载器
   */
  private static ClassLoader getClassLoader() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = ResourceUtil.class.getClassLoader();
      if (null == classLoader) {
        classLoader = ClassLoader.getSystemClassLoader();
      }
    }
    return classLoader;
  }

  /**
   * 从URL对象中获取不被编码的路径Path<br>
   * 对于本地路径，URL对象的getPath方法对于包含中文或空格时会被编码，导致本读路径读取错误。<br>
   * 此方法将URL转为URI后获取路径用于解决路径被编码的问题
   * 
   * @param url {@link URL}
   * @return 路径
   */
  private static String getDecodedPath(URL url) {
    if (null == url) {
      return null;
    }

    String path = null;
    try {
      // URL对象的getPath方法对于包含中文或空格的问题
      path = url.toURI().getPath();
    } catch (URISyntaxException e) {
      // ignore
    }
    return (null != path) ? path : url.getPath();
  }

  /**
   * 列出类路径下指定目录中的所有资源。
   * @param dirPath 目录路径，例如 "sql-templates"
   * @param fileExtension 文件扩展名，例如 ".sql"
   * @return 资源路径列表
   */
  public static List<String> listResources(String dirPath, String fileExtension) {
    List<String> result = new ArrayList<>();
    Enumeration<URL> dirUrls;
    try {
      dirUrls = Thread.currentThread().getContextClassLoader().getResources(dirPath);
    } catch (IOException e) {
      // 在类加载阶段，如果目录扫描失败，这通常是致命的配置错误
      throw new RuntimeException("Failed to get resources for directory: " + dirPath, e);
    }

    while (dirUrls.hasMoreElements()) {
      URL dirUrl = dirUrls.nextElement();
      String protocol = dirUrl.getProtocol();

      if ("file".equals(protocol)) {
        // 在文件系统中运行
        try {
          // === 问题修复点在这里 ===
          String urlPath = dirUrl.getPath();
          // 在Windows上，url.getPath() 可能会返回 /C:/... 这样的路径，需要去掉开头的'/'
          if (System.getProperty("os.name").toLowerCase().contains("win") && urlPath.startsWith("/")) {
            urlPath = urlPath.substring(1);
          }

          Path dirPathObj = Paths.get(urlPath);
          // =======================

          try (Stream<Path> stream = Files.walk(dirPathObj)) {
            stream.filter(path -> path.toString().endsWith(fileExtension)).forEach(path -> {
              // 将文件系统路径转换回类路径资源路径
              String relativePath = dirPathObj.relativize(path).toString().replace("\\", "/");
              result.add(dirPath + "/" + relativePath);
            });
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to walk file tree for: " + dirUrl, e);
        }
      } else if ("jar".equals(protocol)) {
        // 在JAR包中运行
        // jar:file:/path/to/your.jar!/sql-templates
        String jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name()))) {
          Enumeration<JarEntry> entries = jar.entries();
          while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 确保条目在指定目录下，并且是所需类型的文件
            if (name.startsWith(dirPath + "/") && name.endsWith(fileExtension) && !entry.isDirectory()) {
              result.add(name);
            }
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to read JAR file: " + jarPath, e);
        }
      }
    }
    return result;
  }

  /**
   * 列出类路径下指定目录中的所有资源。
   * @param dirPath 目录路径，例如 "sql-templates"
   * @param fileExtension 文件扩展名，例如 ".sql"
   * @return 资源路径列表
   */
  public static List<String> listResources(String dirPath) {
    List<String> result = new ArrayList<>();
    Enumeration<URL> dirUrls;
    try {
      dirUrls = Thread.currentThread().getContextClassLoader().getResources(dirPath);
    } catch (IOException e) {
      // 在类加载阶段，如果目录扫描失败，这通常是致命的配置错误
      throw new RuntimeException("Failed to get resources for directory: " + dirPath, e);
    }

    while (dirUrls.hasMoreElements()) {
      URL dirUrl = dirUrls.nextElement();
      String protocol = dirUrl.getProtocol();

      if ("file".equals(protocol)) {
        // 在文件系统中运行
        try {
          // === 问题修复点在这里 ===
          String urlPath = dirUrl.getPath();
          // 在Windows上，url.getPath() 可能会返回 /C:/... 这样的路径，需要去掉开头的'/'
          if (System.getProperty("os.name").toLowerCase().contains("win") && urlPath.startsWith("/")) {
            urlPath = urlPath.substring(1);
          }

          Path dirPathObj = Paths.get(urlPath);
          // =======================

          try (Stream<Path> stream = Files.walk(dirPathObj)) {
            stream.forEach(path -> {
              // 将文件系统路径转换回类路径资源路径
              String relativePath = dirPathObj.relativize(path).toString().replace("\\", "/");
              result.add(dirPath + "/" + relativePath);
            });
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to walk file tree for: " + dirUrl, e);
        }
      } else if ("jar".equals(protocol)) {
        // 在JAR包中运行
        // jar:file:/path/to/your.jar!/sql-templates
        String jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name()))) {
          Enumeration<JarEntry> entries = jar.entries();
          while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 确保条目在指定目录下，并且是所需类型的文件
            if (name.startsWith(dirPath + "/") && !entry.isDirectory()) {
              result.add(name);
            }
          }
        } catch (IOException e) {
          throw new RuntimeException("Failed to read JAR file: " + jarPath, e);
        }
      }
    }
    return result;
  }

}
