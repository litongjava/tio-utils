package com.litongjava.tio.utils.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.litongjava.tio.utils.IoUtils;
import com.litongjava.tio.utils.environment.EnvironmentUtils;

public class Wangyi126Mail implements EMail {

  /**
   * 发送邮件
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   * @paaram isDebug 是否开启debug模式
   */
  public void send(String to, String subject, String content, boolean isDebug) {
    String mailHost = EnvironmentUtils.get("mail.host");
    String mailTransportProtocol = EnvironmentUtils.get("mail.protocol");

    Integer smtpPort = EnvironmentUtils.getInt("mail.smpt.port");
    String user = EnvironmentUtils.get("mail.user");
    String password = EnvironmentUtils.get("mail.password");
    String from = EnvironmentUtils.get("mail.from");

    Properties prop = new Properties();
    // 邮件服务器
    prop.setProperty("mail.host", mailHost);
    // 传输协议
    prop.setProperty("mail.transport.protocol", mailTransportProtocol);
    // 开启验证
    prop.setProperty("mail.smtp.auth", "true");
    // 设置端口
    prop.setProperty("mail.smtp.port", smtpPort + "");
    prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.setProperty("mail.smtp.socketFactory.fallback", "false");
    prop.setProperty("mail.smtp.socketFactory.port", smtpPort + "");

    // 获取session
    Session session = Session.getInstance(prop);
    // 开启debug模式时
    session.setDebug(isDebug);
    // 获取 tranport
    Transport ts = null;
    MimeMessage message = new MimeMessage(session);
    try {
      ts = session.getTransport();
      ts.connect(mailHost, smtpPort, user, password);

      // 设置消息
      message.setFrom(new InternetAddress(from));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(content);

      // 发送消息
      ts.sendMessage(message, message.getAllRecipients());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IoUtils.closeQuietly(ts);
    }
  }
}
