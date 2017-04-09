package com.marcarndt.morsemonkey.utils;

/**
 * Created by arndt on 2017/04/07.
 */
public class BotConfig {

  public static String getBotName(){
    return System.getProperty("botname");
  }

  public static String getBotAPI(){
    return System.getProperty("botkey");

  }

}
