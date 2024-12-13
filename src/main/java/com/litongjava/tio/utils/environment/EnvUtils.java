package com.litongjava.tio.utils.environment;

import java.util.HashMap;
import java.util.Map;

import com.litongjava.constants.ServerConfigKeys;
import com.litongjava.tio.utils.hutool.ResourceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvUtils {
  private static String[] args;
  private static Map<String, String> cmdArgsMap = new HashMap<>();
  private static Map<String, String> appMap = new HashMap<>();
  public static final String defaultFilename = "app.properties";
  public static final String envKey = "app.env";

  public static String[] getArgs() {
    return args;
  }

  public Map<String, String> getCmdArgsMap() {
    return cmdArgsMap;
  }

  public static Map<String, String> buildCmdArgsMap(String[] args) {
    EnvUtils.args = args;
    Map<String, String> result = new HashMap<>();
    for (String arg : args) {
      if (arg.startsWith("--")) {
        String[] parts = arg.substring(2).split("=", 2);
        if (parts.length == 2) {
          result.put(parts[0], parts[1]);
        }
      }
    }
    cmdArgsMap = result;
    return result;
  }

  public static String getStr(String key) {
    String value = appMap.get(key);
    if (value != null) {
      return value;
    }
    // comamdn line
    value = cmdArgsMap.get(key);

    if (value != null) {
      return value;
    }

    // java env
    value = System.getProperty(key);
    if (value != null) {
      return value;
    }
    // system env
    value = System.getenv(key);
    if (value != null) {
      return value;
    }

    value = System.getenv(key.replace(".", "_").toUpperCase());
    if (value != null) {
      return value;
    }
    // config file
    if (PropUtils.isLoad()) {
      value = PropUtils.get(key);
    }
    return value;
  }

  /**
   * 
   * @param key
   * @return
   */
  public static String get(String key) {
    return getStr(key);
  }
  
  /**
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  public static String get(String key, String defaultValue) {
    String value = get(key);
    if (value != null) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * 
   * @param key
   * @return
   */
  public static Integer getInt(String key) {
    String value = getStr(key);
    if (value != null) {
      return Integer.valueOf(value);
    } else {
      return null;
    }
  }

  /**
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  public static Integer getInt(String key, Integer defaultValue) {
    String value = get(key);
    if (value != null) {
      return Integer.valueOf(value);
    } else {
      return defaultValue;
    }
  }

  /**
   * 
   * @param key
   * @return
   */
  public static Long getLong(String key) {
    String value = getStr(key);
    if (value != null) {
      return Long.valueOf(value);
    } else {
      return null;
    }
  }
  
  /**
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  public static Long getInt(String key, Long defaultValue) {
    String value = get(key);
    if (value != null) {
      return Long.valueOf(value);
    } else {
      return defaultValue;
    }
  }


  public static boolean getBoolean(String key) {
    return Boolean.parseBoolean(get(key));
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    String value = get(key);
    if (value != null) {
      return Boolean.parseBoolean(value);
    } else {
      return defaultValue;
    }
  }

  public static String getEnv() {
    return getStr(envKey);
  }

  public static String env() {
    return getStr(envKey);
  }

  public static boolean isDev() {
    return "dev".equals(getStr(envKey));
  }

  public static boolean isTest() {
    return "test".equals(getStr(envKey));
  }

  public static boolean isProd() {
    return "prod".equals(getStr(envKey));
  }

  public static void load(String fileName) {
    PropUtils.use(fileName);
  }

  public static void load(String env, String filename) {
    PropUtils.use(filename, env);
  }

  public static void set(String key, String value) {
    appMap.put(key, value);
  }

  public static void load() {
    String env = get("app.env");
    if (ResourceUtil.getResource(defaultFilename) != null) {
      PropUtils.use(defaultFilename, env);
    } else {
      if (env != null) {
        String fileName = "app-" + env + ".properties";
        PropUtils.use(fileName);
      } else {
        // crate file
        PropUtils.use(defaultFilename);

      }
    }

    if (ResourceUtil.getResource(".env") != null) {
      PropUtils.append(".env");
    }

    log.info("app.env:{}", EnvUtils.env());
    log.info("app.name:{}", EnvUtils.get(ServerConfigKeys.APP_NAME));

  }
}
