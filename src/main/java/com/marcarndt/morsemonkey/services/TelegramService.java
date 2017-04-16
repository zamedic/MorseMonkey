package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.UserCommandService;
import com.marcarndt.morsemonkey.utils.BotConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/02.
 */
@Startup
@Singleton
public class TelegramService {
  AlertBot alertBot;

  Logger LOG = Logger.getLogger(TelegramService.class.getName());
  private TelegramBotsApi api = new TelegramBotsApi();

  @Inject
  SCPService scpService;

  @Inject
  UserService userService ;

  @Inject
  ChefService chefService;

  @Inject
  UserCommandService userCommandService;

  @Inject
  @ConfigurationValue("bot.key")
  String botKey;

  @Inject
  @ConfigurationValue("bot.name")
  String botName;



  public TelegramService() {
  }


  @PostConstruct
  public void setup() {
    LOG.info("Starting to initialize bots");
    BotConfig.setUsername(botName);
    BotConfig.setKey(botKey);
    try {
      ApiContextInitializer.init();
      alertBot = new AlertBot(this);
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

  @Lock(LockType.READ)
  public SCPService getScpService() {
    return scpService;
  }

  public String getBotKey() {
    return botKey;
  }

  public String getBotName() {
    return botName;
  }

  @Lock(LockType.READ)
  public UserService getUserService() {
    return userService;
  }

  @Lock(LockType.READ)
  public ChefService getChefService() {
    return chefService;
  }

  @Lock(LockType.READ)
  public UserCommandService getUserCommandService() {
    return userCommandService;
  }
}
