package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/17.
 */
public class RegisterCommand extends BaseCommand {

  private static Logger LOG = Logger.getLogger(RegisterCommand.class.getName());

  @Inject
  UserService userService;

  @Override
  public String getCommandIdentifier() {
    return "register";
  }

  @Override
  public String getDescription() {
    return "Register yourself with the bot";
  }

  @Override
  protected Role getRole() {
    return Role.UNAUTHENTICATED;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    LOG.info("Adding User " + user.getId() + " - " + user.getFirstName());
    userService.addUser(user.getId(), user.getFirstName(), user.getLastName());
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chat.getId());
    sendMessage.setText("The bot is now aware of you " + user.getFirstName());
    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "error sending message", e);
    }

  }


}
