package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.authDetails;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class AddAuthName implements Commandlet {

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_AUTH_ADD);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message,"Username");

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_AUTH_ADD_USER;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return Arrays.asList(message.getText());
  }
}
