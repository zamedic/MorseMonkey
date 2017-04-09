package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.bender.Quotes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/07.
 */
@Startup
@Singleton
public class ScheduledTasks {
  private static Logger LOG = Logger.getLogger(ScheduledTasks.class.getName());


  @Inject
  TelegramService telegramService;


  @Schedule(hour = "9",minute = "15", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")
  public void remindStanup(){
    sendMessage("Standup in 15 minutes. "+ Quotes.getRandomQuote());

  }

  private void sendMessage(String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId("-206812535");
    sendMessage.setText(message);
    try {
      telegramService.getAlertBot().sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.WARNING,e.getMessage(),e);
    }
  }

  @Schedule(hour = "9",minute = "30", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")
  public void startStandup(){
    sendMessage("If you are not at Standup, you are officially late.");
  }



}
