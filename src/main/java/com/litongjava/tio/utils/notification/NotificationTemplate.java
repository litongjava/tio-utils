package com.litongjava.tio.utils.notification;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationTemplate {

  public static String format(NotifactionWarmModel model) {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // If time is not null, output Alarm Time
    Date time = model.getTime();
    if (time != null) {
      sb.append(String.format("- Alarm Time : %s\n", sdf.format(time)));
    }
    // If App Env is not null, output App Env
    if (model.getAppEnv() != null) {
      sb.append(String.format("- App Env : %s\n", model.getAppEnv()));
    }
    // If App Group Name is not null, output App Group Name
    if (model.getAppGroupName() != null) {
      sb.append(String.format("- App Group Name : %s\n", model.getAppGroupName()));
    }
    // If App Name is not null, output App Name
    if (model.getAppName() != null) {
      sb.append(String.format("- App Name : %s\n", model.getAppName()));
    }
    // If Warning Name is not null, output Alarm Name
    if (model.getWarningName() != null) {
      sb.append(String.format("- Alarm Name : %s\n", model.getWarningName()));
    }
    // If Level is not null, output Alarm Level
    if (model.getLevel() != null) {
      sb.append(String.format("- Alarm Level : %s\n", model.getLevel()));
    }
    // If Device Name is not null, output Alarm Device
    if (model.getDeviceName() != null) {
      sb.append(String.format("- Alarm Device : %s\n", model.getDeviceName()));
    }
    // If Content is not null, output Alarm Content
    if (model.getContent() != null) {
      sb.append("- Alarm Content : \n");
      sb.append(String.format("%s\n", model.getContent()));
    }

    return sb.toString();
  }
}