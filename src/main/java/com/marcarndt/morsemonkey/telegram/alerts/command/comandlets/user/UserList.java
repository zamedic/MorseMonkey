package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.user;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
@Stateless
public class UserList implements Commandlet {

  @Inject
  UserService userService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_USER) && (
        message.getText().equals(ConfigureCommand.addUserToRole) || message.getText()
            .equals(ConfigureCommand.removeUserFromRole));
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    List<com.marcarndt.morsemonkey.services.data.User> users = userService.getAllUsers();
    List<String> names = users.stream().map(user -> user.getName()).collect(Collectors.toList());
    morseBot
        .sendReplyKeyboardMessage(message, "Select User", names);

  }

  @Override
  public State getNewState(Message message, State command) {
    if (message.getText().equals(ConfigureCommand.addUserToRole)) {
      return State.CONFIGURE_USER_ADD;
    } else {
      return State.CONFIGURE_USER_DELETE;
    }

  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> params) {
    return null;
  }
}
