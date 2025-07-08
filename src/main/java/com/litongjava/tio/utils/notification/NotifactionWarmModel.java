package com.litongjava.tio.utils.notification;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifactionWarmModel {
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
  private String requestLine;
  private String requestBody;
  private String exceptionId;
  private String stackTrace;
  private String content;

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

}
