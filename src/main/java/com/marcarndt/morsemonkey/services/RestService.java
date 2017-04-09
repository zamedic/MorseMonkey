package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import com.marcarndt.morsemonkey.telegram.fines.FinesBot;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/06.
 */

@Singleton
public class RestService {

  @Inject
  TelegramService telegramService;

  public FinesBot getFinesBot(){
    return telegramService.getFinesBot();
  }

  public AlertBot getAlertBot(){
    return telegramService.getAlertBot();
  }


}
