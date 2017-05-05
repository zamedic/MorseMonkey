package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/20.
 */
public class VCMCommand extends BaseCommand {

  public static String execute = "ExecuteSSH a VCM command";
  public static String add = "Add a new VCM command";
  public static String delete = "Delete a VCM command";

  private final Logger LOG = Logger.getLogger(VCMCommand.class.getName());

  @Inject
  StateService stateService;

  @Inject
  MorseBot morseBot;

  @Override
  public String getCommandIdentifier() {
    return "vcm";
  }

  @Override
  public String getDescription() {
    return "Execute Virtual Channels Mainframe commands";
  }

  @Override
  protected Role getRole() {
    return Role.VCM;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    morseBot
        .sendReplyKeyboardMessage(user, chat, "How would you like to proceed",
            execute, add, delete);

    stateService.setState(user.getId(), chat.getId(), State.VCM);


  }
}
