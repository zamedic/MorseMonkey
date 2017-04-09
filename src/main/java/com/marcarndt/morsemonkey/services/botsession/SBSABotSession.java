package com.marcarndt.morsemonkey.services.botsession;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Created by arndt on 2017/04/06.
 */
public class SBSABotSession extends DefaultBotSession {

  public SBSABotSession(){
    super();
    DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
    HttpHost proxy = new HttpHost("websenseproxy.standardbank.co.za",8080);
    defaultBotOptions.setRequestConfig(RequestConfig.custom().setProxy(proxy).build());
    setOptions(defaultBotOptions);
  }


}
