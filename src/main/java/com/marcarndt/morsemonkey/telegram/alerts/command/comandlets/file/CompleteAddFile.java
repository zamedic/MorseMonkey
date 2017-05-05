package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.file;

import com.marcarndt.morsemonkey.services.SSHService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/05.
 */
@Stateless
public class CompleteAddFile implements Commandlet {

  @Inject
  SSHService sshService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.CONFIGURE_FILE_ADD_DETAILS);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String description = parameters.get(0);
    String path = message.getText();
    sshService.addFile(description, path);
    morseBot.sendMessage("Added file " + description + " with path " + path,
        message.getChatId().toString());
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
