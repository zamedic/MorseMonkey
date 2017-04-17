package com.marcarndt.morsemonkey.telegram.alerts.command;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.ChefService;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.UserService.Role;
import com.marcarndt.morsemonkey.services.dto.Node;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class ChefNodeBot extends BaseCommand {

  @Inject
  ChefService chefService;

  @Override
  protected void performCommand(AbsSender absSender, User user, Chat chat, String[] arguments) {
    Node node = null;
    try {
      node = chefService.getNode(arguments[0]);
    } catch (MorseMonkeyException e) {
      handleException(absSender,chat,e);
    }
    sendMessage(absSender,chat,node.getName()+" - "+node.getEnvironment()+" - "+node.getPlatform());
  }

  @Override
  public String getCommandIdentifier() {
    return "chef";
  }

  @Override
  public String getDescription() {
    return "get Chef Node";
  }
}
