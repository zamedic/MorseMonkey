package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.application;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class Recipes implements Commandlet {
  public static String add = "Add a recipe";
  public static String delete = "Delete a recipe";

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_EDIT_APPLICATION_OPTIONS) && message.getText().equals(UpdateApplicationOptions.recipes);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message,"Select Option", add, delete);
  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_EDIT_APPLICATION_RECIPE;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return parameters;
  }
}
