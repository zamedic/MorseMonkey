package com.marcarndt.morsemonkey.telegram.commands.fines;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by arndt on 2017/04/02.
 */
public class PayCommand extends BotCommand {

  public PayCommand() {
    super("pay", "To be used by the treasurer when a team member pays for their transgressions");
  }

  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

  }
}
