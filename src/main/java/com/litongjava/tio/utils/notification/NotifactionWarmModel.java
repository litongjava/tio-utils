package com.litongjava.tio.utils.notification;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.litongjava.tio.utils.json.JsonUtils;


public class NotifactionWarmModel {
  //yyyy-MM-dd HH:mm:ssXXX  -> e.g. 2025-07-08 10:30:00+08:00
  public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

  private ZonedDateTime time;
  private String appEnv;
  private String appGroupName;
  private String appName;
  private String warningName;
  private String level;
  private String deviceName;
  private String userIp;
  private String userId;
  private String requestId;
  private String host;
  private String referer;
  private String userAgent;
  private String requestLine;
  private String requestBody;
  private Integer statusCode;
  private String exceptionId;
  private String stackTrace;
  private String content;

  private Map<String, String> headers;
  private Map<String, Object[]> params;

  public NotifactionWarmModel(ZonedDateTime time, String env, String appGroupName, String appName, String warningName, String level, String deviceName, String content) {
    this.time = time;
    this.appEnv = env;
    this.appGroupName = appGroupName;
    this.appName = appName;
    this.warningName = warningName;
    this.level = level;
    this.deviceName = deviceName;
    this.content = content;
  }

  public NotifactionWarmModel() {
  }

  public String format() {
    StringBuilder sb = new StringBuilder();
    // Alarm Time
    if (time != null) {
      sb.append(String.format("- Time : %s\n", time.format(dateTimeFormatter)));
    }
    // App Env
    if (appEnv != null) {
      sb.append(String.format("- App Env : %s\n", appEnv));
    }
    // App Group Name
    if (appGroupName!= null) {
      sb.append(String.format("- App Group Name : %s\n", appGroupName));
    }
    // App Name
    if (appName != null) {
      sb.append(String.format("- App Name : %s\n", appName));
    }
    // Alarm Name (Warning Name)
    if (warningName != null) {
      sb.append(String.format("- Name : %s\n", warningName));
    }
    // Alarm Level
    if (level != null) {
      sb.append(String.format("- Level : %s\n", level));
    }
    // Alarm Device
    if (deviceName != null) {
      sb.append(String.format("- Device : %s\n", deviceName));
    }
    // User Ip
    if (userIp != null) {
      sb.append(String.format("- User Ip : %s\n", userIp));
    }

    // user Id
    if (userId != null) {
      sb.append(String.format("- User Id : %s\n", userId));
    }

    // Request Id
    if (requestId != null) {
      sb.append(String.format("- Request Id : %s\n", requestId));
    }

    // Request Line
    if (requestLine != null) {
      sb.append(String.format("- Request Line : %s\n", requestLine));
    }
    // Host
    if (host != null) {
      sb.append(String.format("- Host : %s\n", host));
    }
    // Refer
    if (referer != null) {
      sb.append(String.format("- Referer : %s\n", referer));
    }
    //
    if (userAgent != null) {
      sb.append(String.format("- UserAgent : %s\n", userAgent));
    }
    if (headers != null) {
      sb.append(String.format("- UserAgent : %s\n", JsonUtils.toJson(headers)));
    }
    if (params!= null) {
      sb.append(String.format("- UserAgent : %s\n", JsonUtils.toJson(params)));
    }
    // Request Body
    if (requestBody != null) {
      sb.append(String.format("- Request Body : %s\n", requestBody));
    }

 // Request Body
    if (statusCode != null) {
      sb.append(String.format("- Status Code : %d\n", statusCode));
    }
    
    if (exceptionId!= null) {
      sb.append(String.format("- Exception Id : %s\n", exceptionId));
    }
    // Stack Trace
    if (stackTrace != null) {
      sb.append(String.format("- Stack Trace : %s\n", stackTrace));
    }
    // Alarm Content
    if (content != null) {
      sb.append("- Content : \n");
      sb.append(String.format("%s\n", content));
    }
    return sb.toString();
  }

  public ZonedDateTime getTime() {
    return time;
  }

  public void setTime(ZonedDateTime time) {
    this.time = time;
  }

  public String getAppEnv() {
    return appEnv;
  }

  public void setAppEnv(String appEnv) {
    this.appEnv = appEnv;
  }

  public String getAppGroupName() {
    return appGroupName;
  }

  public void setAppGroupName(String appGroupName) {
    this.appGroupName = appGroupName;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getWarningName() {
    return warningName;
  }

  public void setWarningName(String warningName) {
    this.warningName = warningName;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getReferer() {
    return referer;
  }

  public void setReferer(String referer) {
    this.referer = referer;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getRequestLine() {
    return requestLine;
  }

  public void setRequestLine(String requestLine) {
    this.requestLine = requestLine;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public String getExceptionId() {
    return exceptionId;
  }

  public void setExceptionId(String exceptionId) {
    this.exceptionId = exceptionId;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, Object[]> getParams() {
    return params;
  }

  public void setParams(Map<String, Object[]> params) {
    this.params = params;
  }

  public static DateTimeFormatter getDatetimeformatter() {
    return dateTimeFormatter;
  }
}
