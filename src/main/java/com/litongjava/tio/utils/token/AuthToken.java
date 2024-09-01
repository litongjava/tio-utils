package com.litongjava.tio.utils.token;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken implements Serializable {

  private static final long serialVersionUID = -3667914001133777991L;

  private Object userId;
  private String token;
  // -1 表示永不过期
  private long expirationTime;

  /**
   * expirationTime 不予考虑, 因为就算 expirationTime 不同也认为是相同的 token.
   */
  public int hashCode() {
    return userId.hashCode();
  }

  public boolean equals(Object object) {
    if (object instanceof AuthToken) {
      return ((AuthToken) object).userId.equals(this.userId);
    }
    return false;
  }

  public AuthToken(Object userId, long expirationTime) {
    this.userId = userId;
    this.expirationTime = expirationTime;
  }
}