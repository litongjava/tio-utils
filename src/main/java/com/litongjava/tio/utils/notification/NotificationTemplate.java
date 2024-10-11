package com.litongjava.tio.utils.notification;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationTemplate {

  public static final String alarmTemplate = "- Alarm Time : %s\n"//
      + "- App Env : %s\n" //
      + "- App Group Name : %s\n" //
      + "- App Name : %s\n" //
      + "- Alarm name : %s\n" //
      + "- Alarm Level : %s\n" //
      + "- Alarm Device : %s\n" //
      + "- Alarm Content : \n" //
      + "%s\n";

  public static String format(NotifactionWarmModel model) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = model.getTime();
    String dateString = sdf.format(date);

    String appEnv = model.getAppEnv();

    String appGroupName = model.getAppGroupName();
    String appName = model.getAppName();

    String warningName = model.getWarningName();
    String level = model.getLevel();
    String deviceName = model.getDeviceName();
    String content = model.getContent();

    String text = String.format(NotificationTemplate.alarmTemplate, dateString, appEnv, appGroupName, appName,
        //
        warningName, level, deviceName, content);
    return text;

  }
}
