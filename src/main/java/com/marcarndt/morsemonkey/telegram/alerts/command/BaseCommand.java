package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/11.
 */
public abstract class BaseCommand extends BotCommand {

  @Inject
  UserService userService;

  private static Logger LOG = Logger.getLogger(BaseCommand.class.getName());


  /**
   * Construct a command
   */


  protected void handleException(AbsSender absSender, Chat chat, MorseMonkeyException e) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(e.getMessage());
    sendMessage.setChatId(chat.getId());
    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e1) {
      LOG.log(Level.SEVERE, e1.getMessage(), e);
    }
  }

  protected void sendMessage(AbsSender absSender, Chat chat, String message) {
    LOG.info("Sending Message: " + message);
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableHtml(true);
    sendMessage.setText(message);
    sendMessage.setChatId(chat.getId());

    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  protected void sendErrorDocument(AbsSender absSender, Chat chat, SSHResponse response) {
    if (response.getLog().length() > 100) {
      SendDocument sendDocument = new SendDocument();
      sendDocument.setChatId(chat.getId());
      sendDocument.setCaption("Download Failed. See attached log.");
      sendDocument
          .setNewDocument("errorlog.log", new ByteArrayInputStream(response.getLog().getBytes(
              StandardCharsets.UTF_8)));
      try {
        absSender.sendDocument(sendDocument);
      } catch (TelegramApiException e) {
        LOG.log(Level.SEVERE, e.getMessage(), e);
      }
    } else {
      sendMessage(absSender, chat, "Download Failed: " + response.getLog());
    }
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    if (!userService.validateUser(user.getId(), getRole())) {
      sendMessage(absSender, chat,
          "User not allowed to perform the " + getCommandIdentifier() + " transaction");
      LOG.warning("User " + user.getId() + " - " + user.getFirstName() + " attempted to perform "
          + getCommandIdentifier() + " but was denied");
      return;
    }
    performCommand(absSender, user, chat, arguments);

  }

  protected ReplyKeyboardMarkup getReplyKeyboardMarkup() {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setOneTimeKeyboad(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setSelective(true);
    return replyKeyboardMarkup;
  }

  protected abstract Role getRole();

  protected abstract void performCommand(AbsSender absSender, User user, Chat chat,
      String[] arguments);

  public abstract List<State> canHandleStates();

  public abstract void handleState(Message message, State state);
}
