package com.litongjava.tio.utils;

import com.litongjava.tio.utils.environment.EnvUtils;

public class DomainUtils {

  public static String append(String endpoint) {
    String domain = EnvUtils.getStr("app.domain");
    String apiPrefixUrl = "//" + domain + endpoint;
    return apiPrefixUrl;
  }

}
