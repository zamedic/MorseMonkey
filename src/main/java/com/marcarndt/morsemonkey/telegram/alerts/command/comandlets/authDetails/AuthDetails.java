package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.authDetails;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
public class AuthDetails implements Commandlet {
  public static String addAuth = "Add Auth Details";
  public static String deleteAuth = "Delete Auth Details";


  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE) && message.getText().equals(ConfigureCommand.authDetails);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message,"Select function", addAuth,deleteAuth);


  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_AUTH;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
