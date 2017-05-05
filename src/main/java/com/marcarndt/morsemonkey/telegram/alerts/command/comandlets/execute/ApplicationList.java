package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.execute;

import com.marcarndt.morsemonkey.services.ApplicationService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class ApplicationList implements Commandlet {
  @Inject
  ApplicationService applicationService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.EXECUTE) || state.equals(State.FILE);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    List<String> applications =  applicationService.getApplications().stream().map(application -> application.getApplication()).collect(
        Collectors.toList());
    morseBot.sendReplyKeyboardMessage(message,"Which application",applications);
  }

  @Override
  public State getNewState(Message message, State command) {
    switch (command){
      case EXECUTE:
        return State.EXECUTE_APPLICATION;
      case FILE:
        return State.FILE_APPLICATION;
    }
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return Arrays.asList(message.getText());
  }
}
