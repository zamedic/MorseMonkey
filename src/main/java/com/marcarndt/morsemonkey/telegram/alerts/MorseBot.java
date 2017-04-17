package com.marcarndt.morsemonkey.telegram.alerts;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.ConfigureService;
import com.marcarndt.morsemonkey.services.RecipeService;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.dto.Node;
import com.marcarndt.morsemonkey.telegram.alerts.command.ChefNodeBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.FindNodes;
import com.marcarndt.morsemonkey.telegram.alerts.command.HelpCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.StartCommand;
import com.marcarndt.morsemonkey.utils.BotConfig;
import com.marcarndt.morsemonkey.utils.ProxyBotOptions;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/06.
 */
@Singleton
public class MorseBot extends TelegramLongPollingCommandBot {

  private static Logger LOG = Logger.getLogger(MorseBot.class.getName());
  @Inject
  StartCommand startCommand;
  @Inject
  ChefNodeBot chefNodeBot;
  @Inject
  FindNodes findNodes;
  @Inject
  HelpCommand helpCommand;
  @Inject
  ConfigureCommand configureCommand;
  @Inject
  StateService stateService;
  @Inject
  ConfigureService configureService;
  @Inject
  RecipeService recipeService;


  public MorseBot() {
    super(ProxyBotOptions.getProxyBotOptions());
  }

  @PostConstruct
  public void setup() {
    register(startCommand);
    register(configureCommand);
    register(chefNodeBot);
    register(findNodes);
    register(helpCommand);
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    LOG.info("Receives update");
    if (update.getMessage().getNewChatMember() != null) {
      sendMessage("Ahhh Yeah,e one more human to worship me. I am Bender!",
          update.getMessage().getChatId().toString());
      return;
    }
    if (update.getMessage().getLeftChatMember() != null) {
      sendMessage("Sad. Guess bender was just too much awesome.",
          update.getMessage().getChatId().toString());
      return;
    }
    if (update.getMessage().getContact() != null) {
      Contact contact = update.getMessage().getContact();
      sendMessage(
          contact.getFirstName() + " - " + contact.getLastName() + " " + contact.getUserID(),
          update.getMessage().getChatId().toString());
      return;
    }
    try {
      State state =stateService.getUserState(update.getMessage().getFrom().getId(),update.getMessage().getChatId());
      handleUpdate(update.getMessage(), state);
      return;
    } catch (MorseMonkeyException e) {
      LOG.info("No state found for user");
    }

    sendMessage("I dont know that command human. " + Quotes.getRandomQuote(),
        update.getMessage().getChatId().toString());

  }

  private void handleUpdate(Message message, State command) {
    switch (command) {
      case CONFIGURE:
        configureCommand.handleUpdate(message,command);
      case NODE_LIST_FOR_RECIPE:
        handle_node_list(message);
        break;
    }
  }

  private void handle_node_list(Message message) {

    List<Node> nodes = recipeService.getNodesForRecipe(message.getText());
    if (nodes.size() == 0) {
      sendMessage("No nodes found for " + message.getText(), message.getChatId().toString());
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      for (Node node : nodes) {
        stringBuilder.append(node.toString() + "\n");
      }
      sendMessage(stringBuilder.toString(), message.getChatId().toString());
    }
  }


  @Override
  public String getBotUsername() {
    return BotConfig.getUsername();
  }

  @Override
  public String getBotToken() {
    return BotConfig.getKey();
  }


  public boolean sendAlertMessage(String message) {
    try {
      return sendMessage(message, configureService.getGroupKey());
    } catch (MorseMonkeyException e) {
      LOG.log(Level.WARNING,"Error sending alert",e);
      return false;
    }
  }

  public boolean sendMessage(String message, String key) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableHtml(true);
    sendMessage.setChatId(key);
    sendMessage.setText(message);
    sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
    try {
      super.sendMessage(sendMessage);
      return true;
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
      return false;
    }
  }

}


