package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import com.marcarndt.morsemonkey.telegram.fines.FinesBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;

/**
 * Created by arndt on 2017/04/02.
 */
@Startup
@Singleton
public class TelegramService {

  FinesBot finesBot;
  AlertBot alertBot;

  Logger LOG = Logger.getLogger(TelegramService.class.getName());

  private TelegramBotsApi api = new TelegramBotsApi();

  public TelegramService() {
  }


  @PostConstruct
  public void setup() {
    LOG.info("Starting to initialize bots");
    try {
      ApiContextInitializer.init();
      alertBot = new AlertBot();
      getApi().registerBot(alertBot);
      LOG.info("Bot initialization completed");
    } catch (TelegramApiRequestException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  @PreDestroy
  public void destroy() {
    BotSession session = (BotSession) ApiContext.getInstance(BotSession.class);
    session.stop();
  }


  public FinesBot getFinesBot() {
    return finesBot;
  }

  public AlertBot getAlertBot() {
    return alertBot;
  }


  public boolean getStatus() {
    return true;
  }

  private TelegramBotsApi getApi() {
    return api;
  }

  protected void setApi(TelegramBotsApi api) {
    this.api = api;
  }
}
