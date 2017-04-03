package com.marcarndt.morsemonkey.telegram.fines.command;

import java.util.logging.Logger;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by arndt on 2017/04/02.
 */
public class PayCommand extends BotCommand {
  Logger LOG = Logger.getLogger(PayCommand.class.getName());

  public PayCommand() {
    super("pay", "To be used by the treasurer when a team member pays for their transgressions");
  }

  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
      LOG.info(absSender.toString());
  }
}
