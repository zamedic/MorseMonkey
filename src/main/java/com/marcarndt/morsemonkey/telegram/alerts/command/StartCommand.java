package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/06.
 */
@Stateless
public class StartCommand extends BotCommand {

  @Inject
  UserService userService;

  private static Logger LOG = Logger.getLogger(StartCommand.class.getName());

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("For me to send alerts, please use group chat ID: " + chat.getId());
    sendMessage.setChatId(chat.getId());

    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.WARNING, "Error sending response", e);
    }

    if(!userService.adminUserExists()){
      userService.addUser(user.getId(),user.getFirstName(), Role.USER_ADMIN);
      sendMessage = new SendMessage();
      sendMessage.setText("You have been added as a user administrator");
      sendMessage.setChatId(chat.getId());
      try {
        absSender.sendMessage(sendMessage);
      } catch (TelegramApiException e) {
        LOG.log(Level.WARNING, "Error sending response", e);
      }
    }
  }

  @Override
  public String getCommandIdentifier() {
    return "start";
  }

  @Override
  public String getDescription() {
    return "start talking to the bot";
  }

}
