package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.RecipeService;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import java.util.ArrayList;
import java.util.List;
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
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class FindNodes extends BaseCommand {

  @Inject
  RecipeService recipeService;
  @Inject
  StateService stateService;

  @Override
  protected Role getRole() {
    return Role.USER;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    int count = 0;
    KeyboardRow keyboardRow = new KeyboardRow();
    List<KeyboardRow> keyboardRowList = new ArrayList<>();

    List<String> recipes = recipeService.getRecipeDescriptions();

    for (String recipe : recipes) {
      keyboardRow.add(new KeyboardButton(recipe));
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
      stateService.setState(user.getId(), chat.getId(), State.NODE_LIST_FOR_RECIPE);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<State> canHandleStates() {
    return new ArrayList<>();
  }

  @Override
  public void handleState(Message message, State state) {

  }

  @Override
  public String getCommandIdentifier() {
    return "find";
  }

  @Override
  public String getDescription() {
    return "Find a node";
  }
}
