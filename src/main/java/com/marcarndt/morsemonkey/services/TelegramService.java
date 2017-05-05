package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.data.BotDetails;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
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
import org.mongodb.morphia.query.Query;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/02.
 */
@Startup
@Singleton
public class TelegramService {

  Logger LOG = Logger.getLogger(TelegramService.class.getName());
  private TelegramBotsApi api = new TelegramBotsApi();


  @Inject
  MorseBot morseBot;

  @Inject
  MongoService mongoService;

  @Inject
  @ConfigurationValue("bot.name")
  String botName;

  @Inject
  @ConfigurationValue("bot.key")
  String botKey;



  public TelegramService() {

  }


  @PostConstruct
  public void setup() {
    LOG.info("Starting to initialize bots");
    BotConfig.setUsername(botName);
    BotConfig.setKey(botKey);

    try {
      ApiContextInitializer.init();
      api.registerBot(morseBot);
      LOG.info("Bot initialization completed");
    } catch (TelegramApiRequestException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
    }
  }

}
