package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.execute;

import com.marcarndt.morsemonkey.services.SSHService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class ExecuteSSH implements Commandlet {

  private final Logger LOG = Logger.getLogger(ExecuteSSH.class.getName());

  @Inject
  SSHService sshService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.EXECUTE_NODE);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String command = parameters.get(0);
    String nodeDescription = message.getText();
    String nodeName = nodeDescription.substring(nodeDescription.lastIndexOf("-") + 1).trim();

    SSHResponse response = sshService.runSSHCommand(nodeName, command);
    if (response.isSuccessful()) {
      morseBot.sendMessage(
          "Converge completed successfully for command " + command + " on node " + nodeName,
          message.getChatId().toString());
    } else {
      SendDocument sendDocument = new SendDocument();
      sendDocument.setChatId(message.getChatId());
      sendDocument.setNewDocument("Failed to execute " + command + " on node " + nodeName,
          new ByteArrayInputStream(response.getLog().getBytes()));
      try {
        morseBot.sendDocument(sendDocument);
      } catch (TelegramApiException e) {
        LOG.log(Level.SEVERE, "Could not send message", e);
      }
    }


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
