package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * Created by arndt on 2017/04/07.
 */
@Startup
@Singleton
public class ScheduledTasks {

  private static Logger LOG = Logger.getLogger(ScheduledTasks.class.getName());

  @Inject
  TelegramService telegramService;

  @Inject
  MorseBot morseBot;

  @Inject
  HttpChecker httpChecker;


  @Schedule(hour = "9", minute = "15", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")
  public void remindStanup() {
    sendMessage("Standup in 15 minutes. " + Quotes.getRandomQuote());

  }

  private void sendMessage(String message) {

    morseBot.sendAlertMessage(message);
  }

  @Schedule(hour = "9", minute = "30", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")
  public void startStandup() {
    sendMessage("If you are not at Standup, you are officially late.");
  }

  @Schedule(hour = "*", minute = "*/10")
  public void checkSbisDev() {
    httpChecker.runChecks();
  }


}
