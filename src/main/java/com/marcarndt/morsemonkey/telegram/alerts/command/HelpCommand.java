package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import sun.rmi.runtime.Log;

/**
 * Created by arndt on 2017/04/11.
 */
public class HelpCommand extends BaseCommand {
  private static Logger LOG = Logger.getLogger(HelpCommand.class.getName());

  AlertBot alertBot;


  /**
   * Construct a command
   *
   */
  public HelpCommand(AlertBot alertBot, TelegramService telegramService) {
    super("help", "Get Help", telegramService);
    this.alertBot = alertBot;
  }

  @Override
  public void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<b>Bender Bot</b>\n");
    stringBuilder.append("----------------------\n");

    for (BotCommand command:alertBot.getRegisteredCommands()) {
      stringBuilder.append(command.toString()).append("\n");
    }
    stringBuilder.append(Quotes.getRandomQuote());

    sendMessage(absSender, chat, stringBuilder.toString());


  }


}
