package com.litongjava.tio.utils.commandline;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandLineResult {
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
  private String imeage;
  private String video;
  private double viode_length;
  private List<String> images;
  private List<String> videos;

  public CommandLineResult(String output) {
    this.output = output;
  }

  public CommandLineResult(String output, boolean cached) {
    this.output = output;
    this.cached = cached;
  }

  public CommandLineResult(File file) {
    this.file = file;
  }

  public CommandLineResult(File file, boolean cached) {
    this.file = file;
    this.cached = cached;
  }
}