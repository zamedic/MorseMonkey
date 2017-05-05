package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.file;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/05.
 */
@Stateless
public class AddFileDetails implements Commandlet {

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_FILE_ADD);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyMessage(message,"Enter file name and path");

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_FILE_ADD_DETAILS;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return Arrays.asList(message.getText());
  }
}
