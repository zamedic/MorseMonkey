package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.user;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
@Stateless
public class UserRoleAdd implements Commandlet {

  @Inject
  UserService userService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_USER_ADD_ROLE);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {

    try {
      userService.addUserToRole(parameters.get(0), message.getText());
      morseBot.sendMessage("USer " + parameters.get(0) + " added to role " + message.getText(),
          message.getChatId().toString());
    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(),message.getChatId().toString());
    }

  }

  @Override
  public State getNewState(Message message, State command) {
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> params) {
    return null;
  }
}
