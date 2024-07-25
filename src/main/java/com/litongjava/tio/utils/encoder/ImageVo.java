package com.litongjava.tio.utils.encoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageVo {
  private String mimeType;
  private byte[] data;

  public String getExtension() {
    return mimeType.split("/")[1];
  }
}
