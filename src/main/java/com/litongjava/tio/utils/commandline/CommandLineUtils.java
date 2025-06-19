package com.litongjava.tio.utils.commandline;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandLineUtils {

  public static CommandLineResult execute(File outDir, ProcessBuilder pb) throws IOException, InterruptedException {
    if (outDir != null && !outDir.exists()) {
      outDir.mkdirs();
    }
    
    // 定义日志文件路径，存放在与 scriptPath 相同的目录
    File stdoutFile = new File(outDir, "stdout.log");
    File stderrFile = new File(outDir, "stderr.log");

    // 将输出和错误流重定向到对应的日志文件
    pb.redirectOutput(stdoutFile);
    pb.redirectError(stderrFile);

    Process process = pb.start();

    // 等待40秒，如果超过40秒仍未响应，则强制终止进程
    boolean finished = process.waitFor(120, TimeUnit.SECONDS);
    int exitCode;
    if (!finished) {
      log.error("process did not respond within 120 seconds. Forcibly terminating...");
      process.destroyForcibly();
      exitCode = -1; // 特殊退出码表示超时
    } else {
      exitCode = process.exitValue();
    }

    // 读取日志文件内容，返回给客户端（如果需要实时返回，可用其他方案监控文件变化）
    String stdoutContent = new String(Files.readAllBytes(stdoutFile.toPath()), StandardCharsets.UTF_8);
    String stderrContent = new String(Files.readAllBytes(stderrFile.toPath()), StandardCharsets.UTF_8);

    CommandLineResult result = new CommandLineResult();
    result.setExitCode(exitCode);
    result.setStdOut(stdoutContent);
    result.setStdErr(stderrContent);
    return result;
  }
}
