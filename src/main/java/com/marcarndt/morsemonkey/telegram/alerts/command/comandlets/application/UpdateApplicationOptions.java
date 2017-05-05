package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.application;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class UpdateApplicationOptions implements Commandlet {
  public static String appDynamics = "App Dynamics";
  public static String recipes = "Recipes";

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_EDIT_APPLICATION);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message,"Select Option",appDynamics, recipes);

  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_EDIT_APPLICATION_OPTIONS;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return Arrays.asList(message.getText());
  }
}
