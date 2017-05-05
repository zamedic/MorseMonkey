package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.http;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
@Stateless
public class HttpEndpoint implements Commandlet {


  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE) && message.getText()
        .equals(ConfigureCommand.httpEndpoints);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot
        .sendReplyKeyboardMessage(message, "What would you like to do",
            ConfigureCommand.addHttpEndpoint, ConfigureCommand.deleteHttpEndpoint);


  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_HTTP;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> params) {
    return null;
  }
}
