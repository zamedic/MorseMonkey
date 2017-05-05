package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.configure;

import com.marcarndt.morsemonkey.services.ConfigureService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
@Stateless
public class SetGroup implements Commandlet {

  @Inject
  ConfigureService configureService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE) && message.getText().equals(ConfigureCommand.setGroup);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    configureService.setGroupKey(message.getChatId().toString());
    morseBot.sendMessage("Group chat key set", message.getChatId().toString());
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
