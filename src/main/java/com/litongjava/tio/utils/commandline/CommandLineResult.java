package com.litongjava.tio.utils.commandline;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandLineResult {
  private long sessionIdPrt;
  private long sessionId;
  private long taskId;
  private int exitCode;
  private String executeCode;
  private String stdOut;
  private String stdErr;
  private String output;
  private String text;
  private String imeage;
  private String video;
  private double viode_length;
  private List<String> images;
  private List<String> videos;
}