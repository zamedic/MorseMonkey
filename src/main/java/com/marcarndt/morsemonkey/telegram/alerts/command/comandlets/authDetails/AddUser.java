package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.authDetails;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class AddUser implements Commandlet {
  public static String key = "Key Based";
  public static String password = "Password Based";

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_AUTH_ADD_USER);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message,"Auth type",key,password);
  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_AUTH_ADD_TYPE;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    parameters.add(message.getText());
    return parameters;
  }
}
