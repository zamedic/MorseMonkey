package com.marcarndt.morsemonkey.telegram;

import com.marcarndt.morsemonkey.telegram.commands.fines.FineCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;

/**
 * Created by arndt on 2017/04/02.
 */
public class FinesBot extends TelegramLongPollingCommandBot {

  public FinesBot() {
    register(new FineCommand());
  }

  public void processNonCommandUpdate(Update update) {

  }

  public String getBotUsername() {
    return null;
  }

  public String getBotToken() {
    return null;
  }
}
