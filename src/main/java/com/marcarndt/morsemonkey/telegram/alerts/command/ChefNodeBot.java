package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.dto.Node;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/13.
 */
public class ChefNodeBot extends BaseCommand {

  /**
   * Construct a command
   */

  public ChefNodeBot(TelegramService telegramService) {
    super("node", "get node details", telegramService);
  }

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    Node node = null;
    try {
      node = telegramService.getChefService().getNode(arguments[0]);
    } catch (MorseMonkeyException e) {
      handleException(absSender,chat,e);
    }
    sendMessage(absSender,chat,node.getName()+" - "+node.getEnvironment()+" - "+node.getPlatform());

  }
}
