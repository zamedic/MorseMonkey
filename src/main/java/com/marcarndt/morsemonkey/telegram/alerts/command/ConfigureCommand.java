package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
@Stateless
public class ConfigureCommand extends BaseCommand {

  private static Logger LOG = Logger.getLogger(ConfigureCommand.class.getName());

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setOneTimeKeyboad(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setSelective(true);
    List<KeyboardRow> keyboardRows = new ArrayList<>();

    KeyboardRow keyboardRow = new KeyboardRow();
    keyboardRow.add(new KeyboardButton("Set Group"));

    keyboardRows.add(keyboardRow);
    replyKeyboardMarkup.setKeyboard(keyboardRows);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);

    sendMessage.setText("Select an item to configure");

    try {
      absSender.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "unable to send messaga", e);
    }
  }

  public void handleUpdate(Message message, State command) {
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
