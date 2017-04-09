package com.marcarndt.morsemonkey.telegram.alerts;

import com.marcarndt.morsemonkey.telegram.alerts.command.StartCommand;
import com.marcarndt.morsemonkey.utils.BotConfig;
import com.marcarndt.morsemonkey.utils.ProxyBotOptions;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/06.
 */
public class AlertBot extends TelegramLongPollingCommandBot {

  private static Logger LOG = Logger.getLogger(AlertBot.class.getName());

  public AlertBot() {
    super(ProxyBotOptions.getProxyBotOptions());
    register(new StartCommand());
  }


  @Override
  public void processNonCommandUpdate(Update update) {

  }


  @Override
  public String getBotUsername() {
    return BotConfig.getBotName();
  }

  @Override
  public String getBotToken() {
    return BotConfig.getBotAPI();
  }

  public void sendAlertMessage(String message, String chatId) throws TelegramApiException {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);

    super.sendMessage(sendMessage);

  }

}
