package com.litongjava.tio.utils.notification;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationTemplate {

  // yyyy-MM-dd HH:mm:ssXXX  -> e.g. 2025-07-08 10:30:00+08:00
  public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

  public static String format(NotifactionWarmModel model) {
    StringBuilder sb = new StringBuilder();
    // Alarm Time
    ZonedDateTime time = model.getTime();
    if (time != null) {
      sb.append(String.format("- Alarm Time : %s\n", time.format(dateTimeFormatter)));
    }
    // App Env
    if (model.getAppEnv() != null) {
      sb.append(String.format("- App Env : %s\n", model.getAppEnv()));
    }
    // App Group Name
    if (model.getAppGroupName() != null) {
      sb.append(String.format("- App Group Name : %s\n", model.getAppGroupName()));
    }
    // App Name
    if (model.getAppName() != null) {
      sb.append(String.format("- App Name : %s\n", model.getAppName()));
    }
    // Alarm Name (Warning Name)
    if (model.getWarningName() != null) {
      sb.append(String.format("- Alarm Name : %s\n", model.getWarningName()));
    }
    // Alarm Level
    if (model.getLevel() != null) {
      sb.append(String.format("- Alarm Level : %s\n", model.getLevel()));
    }
    // Alarm Device
    if (model.getDeviceName() != null) {
      sb.append(String.format("- Alarm Device : %s\n", model.getDeviceName()));
    }
    // User Ip
    if (model.getUserIp() != null) {
      sb.append(String.format("- User Ip : %s\n", model.getUserIp()));
    }

    // user Id
    if (model.getUserId() != null) {
      sb.append(String.format("- User Id : %s\n", model.getUserId()));
    }

    // Request Id
    if (model.getRequestId() != null) {
      sb.append(String.format("- Request Id : %s\n", model.getRequestId()));
    }

    // Host
    if (model.getHost() != null) {
      sb.append(String.format("- Host : %s\n", model.getHost()));
    }
    // Request Line
    if (model.getRequestLine() != null) {
      sb.append(String.format("- Request Line : %s\n", model.getRequestLine()));
    }
    // Request Body
    if (model.getRequestBody() != null) {
      sb.append(String.format("- Request Body : %s\n", model.getRequestBody()));
    }

    if (model.getExceptionId() != null) {
      sb.append(String.format("- Exception Id : %s\n", model.getExceptionId()));
    }
    // Stack Trace
    if (model.getStackTrace() != null) {
      sb.append(String.format("- Stack Trace : %s\n", model.getStackTrace()));
    }
    // Alarm Content
    if (model.getContent() != null) {
      sb.append("- Alarm Content : \n");
      sb.append(String.format("%s\n", model.getContent()));
    }
    return sb.toString();
  }
}
