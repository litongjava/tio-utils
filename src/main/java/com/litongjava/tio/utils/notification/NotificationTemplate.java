package com.litongjava.tio.utils.notification;

public interface NotificationTemplate {

  String alarmTemplate = "**Alarm Time** : %s\n"//
      + "**App Group Name** : %s\n" + "**App Name** : %s\n" //
      + "**Alarm name** : %s\n" + "**Alarm Level** : %s\n" //
      + "**Alarm Device** : %s\n" //
      + "**Alarm Content** : %s\n";
}
