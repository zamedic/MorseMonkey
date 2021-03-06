package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.http;

import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
@Stateless
public class AddHttpName implements Commandlet {

  private final Logger LOG = Logger.getLogger(AddHttpName.class.getName());

  @Inject
  MorseBot morseBot;
  @Inject
  StateService stateService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_HTTP_ADD_NAME);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {

    morseBot.sendReplyMessage(message, "Enter the URL to be monitored");

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_HTTP_ADD_URL;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> params) {
    return Arrays.asList(message.getText());
  }
}
