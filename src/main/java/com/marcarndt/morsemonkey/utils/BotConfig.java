package com.marcarndt.morsemonkey.utils;

/**
 * Created by arndt on 2017/04/07.
 */
public class BotConfig {

  static String username;
  static String key;

  public static String getUsername() {
    return username;
  }

  public static void setUsername(String username) {
    BotConfig.username = username;
  }

  public static String getKey() {
    return key;
  }

  public static void setKey(String key) {
    BotConfig.key = key;
  }
}
