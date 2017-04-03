package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.telegram.fines.FinesBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Created by arndt on 2017/04/02.
 */

@Startup
@Singleton
public class TelegramService {

  Logger LOG = Logger.getLogger(TelegramService.class.getName());

  private TelegramBotsApi api = new TelegramBotsApi();

  @Inject
  FinesBot finesBot;

  public TelegramService() {

  }

  @PostConstruct
  public void setup() {
    try {
      ApiContextInitializer.init();
      getApi().registerBot(finesBot);
    } catch (TelegramApiRequestException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
    }
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
