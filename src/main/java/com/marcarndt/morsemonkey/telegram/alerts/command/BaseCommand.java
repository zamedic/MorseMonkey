package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.dto.SCPResponse;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/11.
 */
public abstract class BaseCommand extends BotCommand {
  private static Logger LOG = Logger.getLogger(Base.class.getName());
  TelegramService telegramService;

  /**
   * Construct a command
   *
   * @param commandIdentifier the unique identifier of this command (e.g. the command string to enter
   * into chat)
   * @param description the description of this command
   */
  public BaseCommand(String commandIdentifier, String description, TelegramService telegramService) {
    super(commandIdentifier, description);
    this.telegramService = telegramService;
  }

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
    LOG.info("Sending Message: "+message);
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableHtml(true);
    sendMessage.setText(message);
    sendMessage.setChatId(chat.getId());

    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE,e.getMessage(),e);
    }
  }

  protected void sendErrorDocument(AbsSender absSender, Chat chat, SSHResponse response) {
    if (response.getLog().length() > 100) {
      SendDocument sendDocument = new SendDocument();
      sendDocument.setChatId(chat.getId());
      sendDocument.setCaption("Download Failed. See attached log.");
      sendDocument.setNewDocument("errorlog.log",new ByteArrayInputStream(response.getLog().getBytes(
          StandardCharsets.UTF_8)));
      try {
        absSender.sendDocument(sendDocument);
      } catch (TelegramApiException e) {
        LOG.log(Level.SEVERE,e.getMessage(),e);
      }
    } else {
      sendMessage(absSender,chat,"Download Failed: "+response.getLog());
    }
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
    if(!telegramService.getUserService().validateFrontend(user.getId())){
      sendMessage(absSender, chat, "User not allowed to perform the "+getCommandIdentifier()+" transaction");
      LOG.warning("User "+user.getId()+" - "+user.getFirstName()+" attempted to perform "+getCommandIdentifier()+" but was denied");
      return;
    }
    performCommand(absSender,user,chat,arguments);

  }

  protected abstract void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments);





}
