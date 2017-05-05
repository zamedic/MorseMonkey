package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.execute;

import com.marcarndt.morsemonkey.services.ApplicationService;
import com.marcarndt.morsemonkey.services.ChefService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.data.Cookbook;
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
public class NodeList implements Commandlet {

  @Inject
  ChefService chefService;

  @Inject
  ApplicationService applicationService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.EXECUTE_COOKBOOK) || state.equals(State.FILE_COOKBOOK);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    Cookbook cookbook = applicationService.getCookbook(message.getText());
    List<String> nodes = chefService.recipeSearch(cookbook.getCookbook()).stream()
        .map(node -> node.getEnvironment().startsWith("acceptance") ? "acceptance"
            : node.getEnvironment() + " - " + node.getName()).collect(
            Collectors.toList());
    morseBot.sendReplyKeyboardMessage(message, "Select Node", nodes);

  }

  @Override
  public State getNewState(Message message, State command) {
    switch (command) {
      case EXECUTE_COOKBOOK:
        return State.EXECUTE_NODE;
      case FILE_COOKBOOK:
        return State.FILE_NODE;
    }
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    parameters.add(message.getText());
    return parameters;
  }
}
