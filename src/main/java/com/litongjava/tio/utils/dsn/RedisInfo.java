package com.litongjava.tio.utils.dsn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisInfo {
  private String host;
  private Integer port;
  private String pswd;
}
