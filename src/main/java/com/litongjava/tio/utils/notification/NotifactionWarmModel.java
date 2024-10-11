package com.litongjava.tio.utils.notification;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifactionWarmModel {
  private Date time;
  private String appEnv;

  private String appGroupName;
  private String appName;
  private String warningName;
  private String level;
  private String deviceName;
  private String content;

}
