package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.http;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.HttpChecker;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class HttpAddGet implements Commandlet {

  @Inject
  HttpChecker httpChecker;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_HTTP_ADD_GETPOST) && message.getText().equals(
        ConfigureCommand.httpGetRequest);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String name = parameters.get(0);
    String url = parameters.get(1);
    try {
      httpChecker.addHttpGetEndpoint(name, url);
      morseBot.sendMessage("Added " + name + " on " + url + " to my checks",
          message.getChatId().toString());
    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(), message.getChatId().toString());
    }
  }

  @Override
  public State getNewState(Message message, State command) {
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
