package com.litongjava.tio.utils.commandline;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResult {
  private int exitCode;
  private String stdOut;
  private String stdErr;

  private boolean cached;

  private long sessionIdPrt;
  private long sessionId;
  private long taskId;

  private String executeCode;

  private String output;
  private File file;
  private String text;
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
}