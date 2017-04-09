package com.marcarndt.morsemonkey.telegram.fines;

import com.marcarndt.morsemonkey.rest.Fines;
import com.marcarndt.morsemonkey.telegram.fines.command.AddStaffCommand;
import com.marcarndt.morsemonkey.telegram.fines.command.FineCommand;
import com.marcarndt.morsemonkey.telegram.fines.command.PayCommand;
import com.marcarndt.morsemonkey.utils.ProxyBotOptions;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;

/**
 * Created by arndt on 2017/04/02.
 */
public class FinesBot extends TelegramLongPollingCommandBot {

  Logger LOG = Logger.getLogger(Fines.class.getName());

  public void setupBot() {

    register(new FineCommand());
    register(new PayCommand());
    register(new AddStaffCommand());
    //register(new HelpCommand(this));
  }

  public FinesBot() {
    super(ProxyBotOptions.getProxyBotOptions());
  }

  public void processNonCommandUpdate(Update update) {
    LOG.info(update.toString());
  }

  public String getBotUsername() {
    return "TestFinesBot";
  }

  public String getBotToken() {
    return "304945577:AAGtjIl_cl9X65RjgiPqakgtbvL81p9b5Ws";
  }


}
