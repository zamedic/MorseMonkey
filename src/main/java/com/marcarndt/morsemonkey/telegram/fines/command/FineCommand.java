package com.marcarndt.morsemonkey.telegram.fines.command;

import com.marcarndt.morsemonkey.rest.Fines;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by arndt on 2017/04/02.
 */
public class FineCommand extends BotCommand {
  Logger LOG = Logger.getLogger(FineCommand.class.getName());

  public FineCommand() {
    super("fine", "Fines a team member for a breach of standup rules");
  }

  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    LOG.info(absSender.toString());
  }
}
