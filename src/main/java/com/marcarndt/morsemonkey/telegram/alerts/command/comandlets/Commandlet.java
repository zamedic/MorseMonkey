package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets;

import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.List;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/18.
 */
public interface Commandlet {

  public boolean canHandleCommand(Message message, State state);

  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot);

  public State getNewState(Message message, State command);

  List<String> getNewStateParams(Message message, State state, List<String> parameters);
}
