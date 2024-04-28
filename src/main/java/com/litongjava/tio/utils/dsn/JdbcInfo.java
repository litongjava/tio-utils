package com.litongjava.tio.utils.dsn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdbcInfo {
  private String url, user, pswd;
}
