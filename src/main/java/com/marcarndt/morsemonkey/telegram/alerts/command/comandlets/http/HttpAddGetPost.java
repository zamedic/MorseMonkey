package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.http;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class HttpAddGetPost implements Commandlet {

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_HTTP_ADD_URL);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot
        .sendReplyKeyboardMessage(message, "Get or Post request", ConfigureCommand.httpGetRequest,
            ConfigureCommand.httpPostRequest);

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_HTTP_ADD_GETPOST_RESPONSE;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return parameters;
  }
}
