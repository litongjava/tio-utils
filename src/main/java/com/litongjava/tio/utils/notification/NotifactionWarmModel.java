package com.litongjava.tio.utils.notification;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.litongjava.tio.utils.json.JsonUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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

  public String format() {
    StringBuilder sb = new StringBuilder();
    // Alarm Time
    ZonedDateTime time = this.getTime();
    if (time != null) {
      sb.append(String.format("- Time : %s\n", time.format(dateTimeFormatter)));
    }
    // App Env
    if (this.getAppEnv() != null) {
      sb.append(String.format("- App Env : %s\n", this.getAppEnv()));
    }
    // App Group Name
    if (this.getAppGroupName() != null) {
      sb.append(String.format("- App Group Name : %s\n", this.getAppGroupName()));
    }
    // App Name
    if (this.getAppName() != null) {
      sb.append(String.format("- App Name : %s\n", this.getAppName()));
    }
    // Alarm Name (Warning Name)
    if (this.getWarningName() != null) {
      sb.append(String.format("- Name : %s\n", this.getWarningName()));
    }
    // Alarm Level
    if (this.getLevel() != null) {
      sb.append(String.format("- Level : %s\n", this.getLevel()));
    }
    // Alarm Device
    if (this.getDeviceName() != null) {
      sb.append(String.format("- Device : %s\n", this.getDeviceName()));
    }
    // User Ip
    if (this.getUserIp() != null) {
      sb.append(String.format("- User Ip : %s\n", this.getUserIp()));
    }

    // user Id
    if (this.getUserId() != null) {
      sb.append(String.format("- User Id : %s\n", this.getUserId()));
    }

    // Request Id
    if (this.getRequestId() != null) {
      sb.append(String.format("- Request Id : %s\n", this.getRequestId()));
    }

    // Request Line
    if (this.getRequestLine() != null) {
      sb.append(String.format("- Request Line : %s\n", this.getRequestLine()));
    }
    // Host
    if (this.getHost() != null) {
      sb.append(String.format("- Host : %s\n", this.getHost()));
    }
    // Refer
    if (this.getReferer() != null) {
      sb.append(String.format("- Referer : %s\n", this.getReferer()));
    }
    //
    if (this.getUserAgent() != null) {
      sb.append(String.format("- UserAgent : %s\n", this.getUserAgent()));
    }
    if (this.getHeaders() != null) {
      sb.append(String.format("- UserAgent : %s\n", JsonUtils.toJson(this.getHeaders())));
    }
    if (this.getParams() != null) {
      sb.append(String.format("- UserAgent : %s\n", JsonUtils.toJson(this.getParams())));
    }
    // Request Body
    if (this.getRequestBody() != null) {
      sb.append(String.format("- Request Body : %s\n", this.getRequestBody()));
    }

 // Request Body
    if (this.getStatusCode() != null) {
      sb.append(String.format("- Status Code : %d\n", this.getStatusCode()));
    }
    
    if (this.getExceptionId() != null) {
      sb.append(String.format("- Exception Id : %s\n", this.getExceptionId()));
    }
    // Stack Trace
    if (this.getStackTrace() != null) {
      sb.append(String.format("- Stack Trace : %s\n", this.getStackTrace()));
    }
    // Alarm Content
    if (this.getContent() != null) {
      sb.append("- Content : \n");
      sb.append(String.format("%s\n", this.getContent()));
    }
    return sb.toString();
  }

}
