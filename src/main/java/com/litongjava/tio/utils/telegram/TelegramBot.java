package com.litongjava.tio.utils.telegram;

import java.util.HashMap;
import java.util.Map;

import com.litongjava.tio.utils.http.Http;
import com.litongjava.tio.utils.http.ResponseVo;
import com.litongjava.tio.utils.json.JsonUtils;

public class TelegramBot {

  private String botToken;
  private String name;

  public TelegramBot(String botToken) {
    this.name = "main";
    this.botToken = botToken;
  }

  public TelegramBot(String name, String botToken) {
    this.name = name;
    this.botToken = botToken;
  }

  public String getName() {
    return name;
  }

  public ResponseVo sendMessage(String chatId, String message) {
    String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage";
    Map<String, String> map = new HashMap<>();
    map.put("chat_id", chatId);
    map.put("text", message);
    String payload = JsonUtils.toJson(map);

    return Http.postJson(urlString, payload);
  }

  public TelegramBot withToken(String botToken) {
    this.botToken = botToken;
    return this;
  }
}