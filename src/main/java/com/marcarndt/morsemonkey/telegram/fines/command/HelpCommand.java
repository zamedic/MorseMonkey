package com.marcarndt.morsemonkey.telegram.fines.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/03.
 */
public class HelpCommand extends BotCommand {
  Logger LOG = Logger.getLogger(HelpCommand.class.getName());
  ICommandRegistry iCommandRegistry;

  public HelpCommand(ICommandRegistry commandRegistry) {
    super("help", "Get help with finesbot");
    iCommandRegistry = commandRegistry;

  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
    helpMessageBuilder.append("These are the registered commands for this Bot:\n\n");

    for (BotCommand botCommand : iCommandRegistry.getRegisteredCommands()) {
      helpMessageBuilder.append(botCommand.toString()).append("\n\n");
    }

    SendMessage helpMessage = new SendMessage();
    helpMessage.setChatId(chat.getId().toString());
    helpMessage.enableHtml(true);
    helpMessage.setText(helpMessageBuilder.toString());

    try {
      absSender.sendMessage(helpMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE,e.getMessage(),e);
    }
  }
}
