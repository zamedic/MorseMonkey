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
    HttpHost proxy = new HttpHost("pchop81.standardbank.co.za", 8067);
    defaultBotOptions.setRequestConfig(RequestConfig.custom().setProxy(proxy).build());

    return defaultBotOptions;
  }

}
