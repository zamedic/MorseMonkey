package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.SCPService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/11.
 */
@Stateless
public class SSHCommand extends BaseCommand {

  @Inject
  SCPService scpService;

  String command;


  @Override
  protected Role getRole() {
    return Role.COMMAND;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    if (arguments.length != 1) {
      sendMessage(absSender, chat,
          "to execute a command, the correct syntax is /" + getCommandIdentifier()
              + " <node_name>. " + Quotes
              .getRandomQuote());
      return;
    }
    sendMessage(absSender, chat, "Executing " + command + " on node " + arguments[0]);

    SSHResponse response = scpService.runSSHCommand(arguments[0], command);
    if (response.isSuccessful()) {
      sendMessage(absSender, chat,
          "Successfully completed " + command + " on node " + arguments[0]);
    } else {
      sendErrorDocument(absSender, chat, response);
    }
  }

  @Override
  public List<State> canHandleStates() {
    return new ArrayList<>();
  }

  @Override
  public void handleState(Message message, State state) {

  }

  @Override
  public String getCommandIdentifier() {
    return "ssh";
  }

  @Override
  public String getDescription() {
    return "executes an ssh command";
  }


}
