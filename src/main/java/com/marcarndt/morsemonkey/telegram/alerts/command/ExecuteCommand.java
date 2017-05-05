package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.SSHService;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/05/04.
 */
@Stateless
public class ExecuteCommand extends BaseCommand {

  @Inject
  MorseBot morseBot;

  @Inject
  SSHService sshService;

  @Inject
  StateService stateService;

  @Override
  public String getCommandIdentifier() {
    return "execute";
  }

  @Override
  public String getDescription() {
    return "Execute a predefined SSH Command";
  }

  @Override
  protected Role getRole() {
    return Role.TRUSTED;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select command", sshService.getCommandNames());
    stateService.setState(user.getId(),chat.getId(), State.EXECUTE);

  }
}
