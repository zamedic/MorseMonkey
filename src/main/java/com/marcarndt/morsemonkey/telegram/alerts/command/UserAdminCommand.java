package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * Steps
 * USER_ADMIN - Select Add to Group, Remove from Group, Delete User
 */
public class UserAdminCommand extends BaseCommand {

  private static final Logger LOG = Logger.getLogger(UserAdminCommand.class.getName());

  private final String addUserToRole = "Add user to role";
  private final String removeUserFromRole = "Remove user from role";
  private final String deleteUser = "Delete User";

  @Inject
  StateService stateService;
  @Inject
  UserService userService;
  @Inject
  MorseBot morseBot;


  @Override
  public String getCommandIdentifier() {
    return "user_admin";
  }

  @Override
  public String getDescription() {
    return "administer a user";
  }

  @Override
  protected Role getRole() {
    return Role.USER_ADMIN;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chat.getId());

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboad(true);

    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow keyboardRow = new KeyboardRow();
    keyboardRow.add(new KeyboardButton(addUserToRole));
    keyboardRow.add(new KeyboardButton(removeUserFromRole));
    keyboardRow.add(new KeyboardButton(deleteUser));

    keyboardRows.add(keyboardRow);
    replyKeyboardMarkup.setKeyboard(keyboardRows);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);

    sendMessage.setText("Select operation @" + user.getUserName());

    try {
      absSender.sendMessage(sendMessage);
      stateService.setState(user.getId(), chat.getId(), State.USER_ADMIN);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "failed to send message", e);
    }
  }

  @Override
  public List<State> canHandleStates() {
    return Arrays.asList(State.USER_ADMIN, State.USER_ADMIN_ADD_ROLE_SELECT_USER,
        State.USER_ADMIN_DELETE_ROLE_SELECT_USER, State.USER_ADMIN_DELETE_USER_SELECT_USER,
        State.USER_ADMIN_DELETE_ROLE_SELECT_ROLE, State.USER_ADMIN_ADD_ROLE_SELECT_ROLE);
  }

  @Override
  public void handleState(Message message, State state) {
    switch (state) {
      case USER_ADMIN:
        sendUserList(message, state);
        break;
      case USER_ADMIN_DELETE_USER_SELECT_USER:
        break;
      case USER_ADMIN_ADD_ROLE_SELECT_USER:
      case USER_ADMIN_DELETE_ROLE_SELECT_USER:
        sendUserRoleList(message, state);
        break;
      case USER_ADMIN_DELETE_ROLE_SELECT_ROLE:
        deleteUserFromRome(message, state);
        break;
      case USER_ADMIN_ADD_ROLE_SELECT_ROLE:
        addUserToRole(message, state);
        break;

    }
  }

  private void sendUserList(Message message, State command) {
    String request = message.getText();
    State newRole;
    if (request.equals(addUserToRole)) {
      newRole = State.USER_ADMIN_ADD_ROLE_SELECT_USER;
    } else if (request.equals(deleteUser)) {
      newRole = State.USER_ADMIN_DELETE_ROLE_SELECT_USER;
    } else if (request.equals(removeUserFromRole)) {
      newRole = State.USER_ADMIN_DELETE_USER_SELECT_USER;
    } else {
      morseBot.sendMessage("I dont know that command", message.getChatId().toString());
      return;
    }

    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.getChatId());

    ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();

    List<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow keyboardRow = new KeyboardRow();

    List<com.marcarndt.morsemonkey.services.data.User> users = userService.getAllUsers();
    for (com.marcarndt.morsemonkey.services.data.User user : users) {
      keyboardRow.add(new KeyboardButton(user.getName()));
    }

    keyboardRows.add(keyboardRow);
    replyKeyboardMarkup.setKeyboard(keyboardRows);
    sendMessage.setReplyMarkup(replyKeyboardMarkup);
    sendMessage.setText("Select User @" + message.getFrom().getUserName());

    try {
      morseBot.sendMessage(sendMessage);
      stateService.setState(message.getFrom().getId(), message.getChatId(), newRole);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private void handleDeleteUser(Message message, State state) {

  }

  private void deleteUserFromRome(Message message, State state) {
    List<String> parameters = stateService
        .getParameters(message.getFrom().getId(), message.getChatId());
    try {
      userService.removeUserFromRole(parameters.get(0), message.getText());
      morseBot.sendMessage(parameters.get(0) + " removed from " + message.getText(),
          message.getChatId().toString());
    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }
  }

  private void addUserToRole(Message message, State state) {
    List<String> parameters = stateService
        .getParameters(message.getFrom().getId(), message.getChatId());
    try {
      userService.addUserToRole(parameters.get(0), message.getText());

      morseBot.sendMessage(parameters.get(0) + " added to role " + message.getText(),
          message.getChatId().toString());

    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }
  }

  private void sendUserRoleList(Message message, State state) {
    String name = message.getText();

    try {
      com.marcarndt.morsemonkey.services.data.User user = userService.getUserByName(name);

      SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(message.getChatId());

      ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();

      List<KeyboardRow> keyboardRows = new ArrayList<>();
      KeyboardRow keyboardRow = new KeyboardRow();

      List<Role> roles = userService.getUserRoles(user.getUserId());
      for (Role role : Role.values()) {
        if (!roles.contains(role)) {
          keyboardRow.add(new KeyboardButton(role.toString()));
        }
      }

      keyboardRows.add(keyboardRow);
      replyKeyboardMarkup.setKeyboard(keyboardRows);
      sendMessage.setReplyMarkup(replyKeyboardMarkup);
      sendMessage.setText("Select Role @" + message.getFrom().getUserName());

      morseBot.sendMessage(sendMessage);
      State newState = state;

      switch (state) {
        case USER_ADMIN_DELETE_ROLE_SELECT_USER:
          newState = State.USER_ADMIN_DELETE_ROLE_SELECT_ROLE;
          break;
        case USER_ADMIN_ADD_ROLE_SELECT_USER:
          newState = State.USER_ADMIN_ADD_ROLE_SELECT_ROLE;
          break;
      }
      stateService
          .setState(message.getFrom().getId(), message.getChatId(), newState, name);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "cannot send message", e);
    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }
  }


}
