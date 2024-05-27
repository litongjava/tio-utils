package com.litongjava.tio.utils.dsn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdbcInfo {
  //jdbc:postgresql://192.168.3.9/uh_courses
  private String url, user, pswd;
  
  /**
   * mysql,oracle,postgresql,sqlite
   * @return
   */
  public String getDbType() {
    return url.split(":")[1];
  }
}
