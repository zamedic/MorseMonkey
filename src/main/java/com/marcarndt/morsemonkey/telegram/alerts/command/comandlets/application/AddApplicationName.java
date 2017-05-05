package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.application;

import com.marcarndt.morsemonkey.services.ApplicationService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class AddApplicationName implements Commandlet {

  @Inject
  ApplicationService applicationService;


  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_ADD_APPLICATION);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String applicationName = message.getText();
    applicationService.addApplication(applicationName);
    morseBot.sendMessage("Added " + applicationName, message.getChatId().toString());
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
