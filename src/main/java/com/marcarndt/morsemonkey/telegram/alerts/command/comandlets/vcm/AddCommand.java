package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.vcm;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.VCMService;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.BaseCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class AddCommand implements Commandlet{

  @Inject
  VCMService vcmService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.VCM_ADD_COMMAND);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String description = parameters.get(0);
    String command = message.getText();
    try {
      vcmService.addVcmCommand(description,command);
      morseBot.sendMessage("Command "+command+" with description "+description+"has been added",message.getChatId().toString());
    } catch (MorseMonkeyException e) {
      morseBot.sendMessage(e.getMessage(),message.getChatId().toString());
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
