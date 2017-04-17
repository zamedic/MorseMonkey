package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.SCPService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.services.dto.SCPResponse;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/11.
 */
@Stateless
public class FileCommand extends BaseCommand {

  private static Logger LOG = Logger.getLogger(FileCommand.class.getName());
  String filename;

  @Inject
  MorseBot morseBot;
  @Inject
  SCPService scpService;

  @Override
  protected Role getRole() {
    return Role.USER;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    if (arguments.length != 1) {
      SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(chat.getId());
      sendMessage.setText(
          "to download a file, the correct syntax is /" + getCommandIdentifier() + " <node_name>. "
              + Quotes
              .getRandomQuote());
      return;
    }
    SCPResponse response = scpService.fetchFile(arguments[0], filename);

    if (!response.isSuccessful()) {
      sendErrorDocument(absSender, chat, response);
    }

    File file = new File(response.getFileName());
    SendDocument sendDocument = new SendDocument();
    sendDocument.setNewDocument(file);
    sendDocument.setChatId(chat.getId());
    try {
      absSender.sendDocument(sendDocument);
    } catch (TelegramApiException e) {
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText(e.getMessage());
      sendMessage.setChatId(chat.getId());
      try {
        absSender.sendMessage(sendMessage);
      } catch (TelegramApiException e1) {
        LOG.log(Level.SEVERE, e1.getMessage(), e);
      }
    }

  }

  @Override
  public List<State> canHandleStates() {
    return null;
  }

  @Override
  public void handleState(Message message, State state) {

  }


  @Override
  public String getCommandIdentifier() {
    return "file";
  }

  @Override
  public String getDescription() {
    return "retreives a file";
  }
}
