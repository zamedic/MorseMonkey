package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by arndt on 2017/04/11.
 */
@Stateless
public class HelpCommand extends BaseCommand {
  private static Logger LOG = Logger.getLogger(HelpCommand.class.getName());

  @Inject
  MorseBot morseBot;

  @Override
  public void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<b>Bender Bot</b>\n");
    stringBuilder.append("----------------------\n");

    for (BotCommand command: morseBot.getRegisteredCommands()) {
      stringBuilder.append(command.toString()).append("\n");
    }
    stringBuilder.append(Quotes.getRandomQuote());

    sendMessage(absSender, chat, stringBuilder.toString());


  }


  @Override
  public String getCommandIdentifier() {
    return "help";
  }

  @Override
  public String getDescription() {
    return "Get help";
  }
}
