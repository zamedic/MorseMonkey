package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.command;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class Command implements Commandlet {
  public static String add = "Add Command";
  public static String delete = "Delete Command";

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE) && message.getText().equals(ConfigureCommand.command);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    morseBot.sendReplyKeyboardMessage(message,"Option",add,delete);
  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_COMMAND;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
