package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.user;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.services.data.User;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
public class UserRoleListAdd implements Commandlet {
  @Inject
  UserService userService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_USER_ADD);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String name = message.getText();

    try {
      User user = userService.getUserByName(name);
      List<Role> roles = userService.getUserRoles(user.getUserId());
      List<String> unusedRoles = new ArrayList<>();
      for (Role role : Role.values()) {
        if (!roles.contains(role)) {
          unusedRoles.add(role.toString());
        }
      }

     morseBot
          .sendReplyKeyboardMessage(message,"Select tole to add",unusedRoles);

    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(),message.getChatId().toString());
    }
  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_USER_ADD_ROLE;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> params) {
    return Arrays.asList(message.getText());
  }
}
