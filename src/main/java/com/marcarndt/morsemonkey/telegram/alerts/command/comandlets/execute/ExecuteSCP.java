package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.execute;

import com.marcarndt.morsemonkey.services.SSHService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.data.ChefFile;
import com.marcarndt.morsemonkey.services.dto.SCPResponse;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/05/05.
 */
@Stateless
public class ExecuteSCP implements Commandlet {

  private final Logger LOG = Logger.getLogger(ExecuteSCP.class.getName());

  @Inject
  SSHService sshService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return state.equals(State.FILE_NODE);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    String fileDescription = parameters.get(0);
    String nodeDescription = message.getText();
    String nodeName = nodeDescription.substring(nodeDescription.lastIndexOf("-") + 1).trim();

    ChefFile file = sshService.getFile(fileDescription);

    SCPResponse response = sshService.fetchFile(nodeName, file.getFilePath());

    if (response.isSuccessful()) {
      SendDocument sendDocument = new SendDocument();
      sendDocument.setChatId(message.getChatId());
      sendDocument.setNewDocument(new File(response.getFileName()));
      try {
        morseBot.sendDocument(sendDocument);
      } catch (TelegramApiException e) {
        LOG.log(Level.SEVERE, "Unable to send message", e);
      }
    } else {
      morseBot.sendMessage("Unable to download the file. Please see next document for the log file",
          message.getChatId().toString());
      SendDocument sendDocument = new SendDocument();
      sendDocument.setChatId(message.getChatId());
      sendDocument
          .setNewDocument("Error Log.txt", new ByteArrayInputStream(response.getLog().getBytes()));
      try {
        morseBot.sendDocument(sendDocument);
      } catch (TelegramApiException e) {
        LOG.log(Level.SEVERE, "Unable to send message", e);
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
