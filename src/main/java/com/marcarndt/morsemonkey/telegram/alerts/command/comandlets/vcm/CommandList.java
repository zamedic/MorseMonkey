package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.vcm;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.VCMService;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.VCMCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class CommandList implements Commandlet {

  @Inject
  VCMService vcmService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.VCM) && ((message.getText().equals(VCMCommand.delete) || (message
        .getText().equals(VCMCommand.execute))));
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    List<String> commands = vcmService.getCommands();
    morseBot
        .sendReplyKeyboardMessage(message, "Please select a command", commands);
  }

  @Override
  public State getNewState(Message message, State command) {
    if(message.getText().equals(VCMCommand.execute)){
      return State.VCM_EXECUTE;
    } else {
      return State.VCM_DELETE;
    }
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }


}
