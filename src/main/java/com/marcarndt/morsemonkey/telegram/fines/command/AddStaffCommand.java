package com.marcarndt.morsemonkey.telegram.fines.command;

import java.util.logging.Logger;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;

/**
 * Created by arndt on 2017/04/02.
 */
public class AddStaffCommand extends BotCommand {

  Logger LOG = Logger.getLogger(AddStaffCommand.class.getName());

  public AddStaffCommand() {
    super("addMember", "Add a Team Member");
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    LOG.info("Sender: "+absSender);
  }
}
