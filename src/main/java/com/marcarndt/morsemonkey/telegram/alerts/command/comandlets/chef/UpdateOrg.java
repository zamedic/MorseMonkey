package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.chef;

import com.marcarndt.morsemonkey.services.ChefService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class UpdateOrg implements Commandlet {


  @Inject
  ChefService chefService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_CHEF_ORG);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String org = message.getText();
    chefService.updateOrg(org);
    morseBot.sendMessage("Updated org",message.getChatId().toString());


  }

  @Override
  public State getNewState(Message message, State command) {
    return null;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
