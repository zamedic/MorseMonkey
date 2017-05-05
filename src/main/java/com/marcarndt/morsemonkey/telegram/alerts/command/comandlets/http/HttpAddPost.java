package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.http;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.HttpChecker;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class HttpAddPost implements Commandlet {

  @Inject
  HttpChecker httpChecker;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_HTTP_ADD_POST_BODY);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String body = message.getText();
    String name = parameters.get(0);
    String url = parameters.get(1);
    try {
      httpChecker.addHttpPostEndpoint(name, url,body);
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
