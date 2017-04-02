package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.telegram.FinesBot;
import javax.ejb.Startup;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Created by arndt on 2017/04/02.
 */

@Startup
public class TelegramService {

  public TelegramService(){
    TelegramBotsApi api = new TelegramBotsApi();
    try {
      api.registerBot(new FinesBot());
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }

  }

}
