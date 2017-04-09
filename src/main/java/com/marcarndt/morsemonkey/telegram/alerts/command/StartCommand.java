package com.marcarndt.morsemonkey.telegram.alerts.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import sun.rmi.runtime.Log;

/**
 * Created by arndt on 2017/04/06.
 */
public class StartCommand extends BotCommand {
  private static Logger LOG = Logger.getLogger(StartCommand.class.getName());

  /**
   * Construct a command
   *
   */
  public StartCommand() {
    super("start", "get the chat API details");
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("For me to send alerts, please use group chat ID: "+chat.getId());
    sendMessage.setChatId(chat.getId());
    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.WARNING,"Error sending response",e);
    }
  }
}
