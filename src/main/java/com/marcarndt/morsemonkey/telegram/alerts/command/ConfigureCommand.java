package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.services.ConfigureService;
import com.marcarndt.morsemonkey.services.HttpChecker;
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
 * Created by arndt on 2017/04/17.
 */
;

@Stateless
public class ConfigureCommand extends BaseCommand {

  public static final String setGroup = "Set group to this group";
  public static final String application = "Applications";
  public static final String httpEndpoints = "HTTP Endpoint monitoring";
  public static final String manageUsers = "Manage Users";


  public static final String addHttpEndpoint = "Add a new HTTP Endpoint";
  public static final String deleteHttpEndpoint = "Delete an existing http endpoint";
  public static final String httpGetRequest = "GET";
  public static final String httpPostRequest = "POST";

  public static final String addApplication = "Add a new application";
  public static final String deleteApplication = "Delete an application";
  public static String editApplication = "Update Application";

  public static final String addUserToRole = "Add user to role";
  public static final String removeUserFromRole = "Remove user from role";
  public static final String deleteUser = "Delete User";

  public static final String authDetails = "Authentication Details";
  public static final String command = "Commands";
  public static final String file = "Files";

  public static final String chef = "Chef";


  public static Logger LOG = Logger.getLogger(ConfigureCommand.class.getName());


  @Inject
  ConfigureService configureService;
  @Inject
  MorseBot morseBot;
  @Inject
  StateService stateService;
  @Inject
  HttpChecker httpChecker;

  @Override
  protected Role getRole() {
    return Role.ADMINISTRATOR;
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {

    morseBot
        .sendReplyKeyboardMessage(user, chat, "Select an item to configure", setGroup,
            httpEndpoints,
            application, manageUsers, authDetails, command, file, chef);
    stateService.setState(user.getId(), chat.getId(), State.CONFIGURE);
  }

  @Override
  public String getCommandIdentifier() {
    return "configure";
  }

  @Override
  public String getDescription() {
    return "Configure the bot.";
  }
}
