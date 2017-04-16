package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/11.
 */
public class SSHCommand extends BaseCommand {

  String command;

  /**
   * Construct a command
   *
   * @param commandIdentifier the unique identifier of this command (e.g. the command string to
   * enter into chat)
   * @param description the description of this command
   */
  public SSHCommand(String commandIdentifier, String description, String command,
      TelegramService telegramService) {
    super(commandIdentifier, description, telegramService);
    this.command = command;
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

    SSHResponse response = telegramService.getScpService().runSSHCommand(arguments[0], command);
    if (response.isSuccessful()) {
      sendMessage(absSender, chat,
          "Successfully completed " + command + " on node " + arguments[0]);
    } else {
      sendErrorDocument(absSender, chat, response);
    }


  }


}
