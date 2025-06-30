package com.litongjava.tio.utils.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmRequestVo {
  private String fromUser;
  private String toUser;
  private String mailBox;
  private String subject;
  private String body;
}
