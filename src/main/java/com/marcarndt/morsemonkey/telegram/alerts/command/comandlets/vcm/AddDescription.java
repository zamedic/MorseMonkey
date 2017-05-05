package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.vcm;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/20.
 */
public class AddDescription implements Commandlet {

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.VCM_ADD);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message, "Enter the VCM Command to run");

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.VCM_ADD_COMMAND;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return Arrays.asList(message.getText());
  }
}
