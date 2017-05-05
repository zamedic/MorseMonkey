package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.SSHService;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/11.
 */
@Stateless
public class FileCommand extends BaseCommand {

  private static Logger LOG = Logger.getLogger(FileCommand.class.getName());
  String filename;

  @Inject
  SSHService sshService;

  @Inject
  MorseBot morseBot;

  @Inject
  StateService stateService;

  @Override
  protected Role getRole() {
    return Role.TRUSTED;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    morseBot.sendReplyKeyboardMessage(user, chat, "Select File", sshService.getFileDescriptions());
    stateService.setState(user.getId(), chat.getId(), State.FILE);
  }


  @Override
  public String getCommandIdentifier() {
    return "file";
  }

  @Override
  public String getDescription() {
    return "Download a predefined file";
  }
}
