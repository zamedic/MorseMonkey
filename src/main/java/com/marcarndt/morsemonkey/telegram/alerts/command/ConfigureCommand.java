package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.ConfigureService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/17.
 */
;

@Stateless
public class ConfigureCommand extends BaseCommand {

  private final String setGroup = "Set group to this group";

  private static Logger LOG = Logger.getLogger(ConfigureCommand.class.getName());

  @Inject
  ConfigureService configureService;
  @Inject
  MorseBot morseBot;

  @Override
  protected Role getRole() {
    return Role.ADMINISTRATOR;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setOneTimeKeyboad(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setSelective(true);
    List<KeyboardRow> keyboardRows = new ArrayList<>();

    KeyboardRow keyboardRow = new KeyboardRow();
    keyboardRow.add(new KeyboardButton(setGroup));

    keyboardRows.add(keyboardRow);
    replyKeyboardMarkup.setKeyboard(keyboardRows);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);

    sendMessage.setText("Select an item to configure");

    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "unable to send message", e);
    }
  }

  @Override
  public List<State> canHandleStates() {
    return null;
  }

  @Override
  public void handleState(Message message, State state) {

  }

  public void handleUpdate(Message message, State command) {
    String messageText = message.getText();
    if (messageText.equals(setGroup)) {
      configureService.setGroupKey(message.getChatId().toString());
      morseBot.sendMessage("Group chat key set", message.getChatId().toString());
    }
  }

  @Override
  public String getCommandIdentifier() {
    return "configure";
  }

  @Override
  public String getDescription() {
    return "description";
  }
}
