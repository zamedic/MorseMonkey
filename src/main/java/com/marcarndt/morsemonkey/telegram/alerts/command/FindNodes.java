package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.ChefService.Recipe;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.telegram.alerts.command.UserCommandService.Command;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/13.
 */
public class FindNodes extends BaseCommand {

  /**
   * Construct a command
   */
  public FindNodes(TelegramService telegramService) {
    super("find_nodes", "Finds nodes for an application", telegramService);
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    int count = 0;
    KeyboardRow keyboardRow = new KeyboardRow();
    List<KeyboardRow> keyboardRowList = new ArrayList<>();

    for (Recipe recipe : Recipe.values()) {
      keyboardRow.add(new KeyboardButton(recipe.getDescription()));
      count++;
      if (count % 3 == 0) {
        keyboardRowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
      }
    }
    if (keyboardRow.size() > 0) {
      keyboardRowList.add(keyboardRow);
    }

    replyKeyboardMarkup.setKeyboard(keyboardRowList);

    sendMessage.setReplyMarkup(replyKeyboardMarkup);
    sendMessage.setText("Select Application");
    sendMessage.setChatId(chat.getId());


    try {
      absSender.sendMessage(sendMessage);
      telegramService.getUserCommandService().addCommand(user.getId(), Command.NODE_LIST);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }


  }
}
