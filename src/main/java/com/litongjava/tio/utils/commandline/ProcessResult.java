package com.litongjava.tio.utils.commandline;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProcessResult {
  private int exitCode;
  private String stdOut;
  private String stdErr;
  private Long elapsed;

  private Boolean cached;

  private Long sessionIdPrt;
  private Long sessionId;
  private Long taskId;

  private String executeCode;

  private String output;
  private String json;
  private String message;
  private File file;
  private String text;
  private String subtitle;
  private String image;
  private String audio;
  private String video;
  private Double video_length;
  private List<String> texts;
  private List<String> images;
  private List<String> audios;
  private List<String> videos;
  private Object data;

  public ProcessResult(String output) {
    this.output = output;
  }

  public ProcessResult(String output, boolean cached) {
    this.output = output;
    this.cached = cached;
  }

  public ProcessResult(File file) {
    this.file = file;
  }

  public ProcessResult(File file, boolean cached) {
    this.file = file;
    this.cached = cached;
  }

  public static ProcessResult buildMessage(String message) {
    return new ProcessResult().setMessage(message);
  }
}