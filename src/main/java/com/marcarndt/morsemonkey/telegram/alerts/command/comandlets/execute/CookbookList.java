package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.execute;

import com.marcarndt.morsemonkey.services.ApplicationService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.data.Application;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class CookbookList implements Commandlet {

  @Inject
  ApplicationService applicationService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.EXECUTE_APPLICATION) || state.equals(State.FILE_APPLICATION);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    Application application = applicationService.getApplication(message.getText());
    List<String> cookbooks = application.getCookbooks().stream()
        .map(cookbook -> cookbook.getDescription()).collect(
            Collectors.toList());
    if (cookbooks.size() == 0) {
      morseBot.sendMessage("Application " + application
              + " does not have cookbooks assiciated with it. Please have your administrator add some cookbooks for the applciation",
          message.getChatId().toString());
    }
    morseBot.sendReplyKeyboardMessage(message, "Which cookbook", cookbooks);
  }

  @Override
  public State getNewState(Message message, State command) {
    switch (command) {
      case EXECUTE_APPLICATION:
        return State.EXECUTE_COOKBOOK;
      case FILE_APPLICATION:
        return State.FILE_COOKBOOK;
    }
    return null;

  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    parameters.add(message.getText());
    return parameters;
  }
}
