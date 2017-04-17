package com.marcarndt.morsemonkey.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * Created by arndt on 2017/04/06.
 */
public class ProxyBotOptions {

  public static DefaultBotOptions getProxyBotOptions() {
    DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
    if(BotConfig.getProxyUrl() != null && BotConfig.getProxyPort() != null) {
      HttpHost proxy = new HttpHost(BotConfig.getProxyUrl(), BotConfig.proxyPort);
      defaultBotOptions.setRequestConfig(RequestConfig.custom().setProxy(proxy).build());
    }

    return defaultBotOptions;
  }

}
