package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.chef;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class User implements Commandlet {

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_CHEF) && message.getText().equals(ChefCommands.user);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message,"Enter user");
  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_CHEF_USER;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
