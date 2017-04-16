package com.marcarndt.morsemonkey.telegram.alerts;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.services.ChefService.Recipe;
import com.marcarndt.morsemonkey.services.TelegramService;
import com.marcarndt.morsemonkey.services.dto.Node;
import com.marcarndt.morsemonkey.telegram.alerts.command.ChefNodeBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.FileCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.FindNodes;
import com.marcarndt.morsemonkey.telegram.alerts.command.HelpCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.SSHCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.StartCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.UserCommandService.Command;
import com.marcarndt.morsemonkey.utils.BotConfig;
import com.marcarndt.morsemonkey.utils.ProxyBotOptions;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AlertBot extends TelegramLongPollingCommandBot {

  TelegramService telegramService;
  public static String ProdGroup = "-206812535";
  public static String DevGroup = "-153616507";


  private static Logger LOG = Logger.getLogger(AlertBot.class.getName());

  public AlertBot(TelegramService telegramService) {
    super(ProxyBotOptions.getProxyBotOptions());
    this.telegramService = telegramService;
    register(new StartCommand());
    register(
        new FileCommand("chef_log_file", "Downloads the chef log file", "/var/log/chef-client.log",
            telegramService));
    register(new FileCommand("chef_stacktrace", "Downloads the chef stacktrace file",
        "/var/chef/cache/chef-stacktrace.out", telegramService));
    register(new SSHCommand("converge", "Reruns the chef client on a node", "chef-client",
        telegramService));
    register(new SSHCommand("start_websphere", "Starts Websphere Liberty on a node",
        "su - wlp -c  '/opt/was/wlp/bin/server start'", telegramService));
    register(
        new SSHCommand("cleanup_tmp", "Deletes everything inside the tmp folder", "rm -rf /tmp/*",
            telegramService));
    register(new ChefNodeBot(telegramService));
    register(new FindNodes(telegramService));
    register(new HelpCommand(this, telegramService));

  }


  @Override
  public void processNonCommandUpdate(Update update) {
    LOG.info("Receives update");
    if (update.getMessage().getNewChatMember() != null) {
      sendAlertMessage("Ahhh Yeah, one more human to worship me. I am Bender!", ProdGroup);
      return;
    }
    if (update.getMessage().getLeftChatMember() != null) {
      sendAlertMessage("Sad. Guess bender was just too much awesome.", ProdGroup);
      return;
    }
    if (update.getMessage().getContact() != null) {
      Contact contact = update.getMessage().getContact();
      sendAlertMessage(
          contact.getFirstName() + " - " + contact.getLastName() + " " + contact.getUserID(),
          update.getMessage().getChatId().toString());
      return;
    }

    if (telegramService.getUserCommandService()
        .hasCommmand(update.getMessage().getFrom().getId())) {
      LOG.info("Found User command in the queue");
      handleUpdate(update.getMessage(), telegramService.getUserCommandService()
          .getCommand(update.getMessage().getFrom().getId()));
      return;
    }

    sendAlertMessage("I dont know that command human. " + Quotes.getRandomQuote(),
        update.getMessage().getChatId().toString());

  }

  private void handleUpdate(Message message, Command command) {
    switch (command) {
      case NODE_LIST:
        handle_node_list(message);
        break;
    }
  }

  private void handle_node_list(Message message) {
    String application = message.getText();
    StringBuilder stringBuilder = new StringBuilder();
    for (Recipe recipe : Recipe.values()) {
      if (recipe.getDescription().equals(application)) {
        List<Node> nodes = telegramService.getChefService().recipeSearch(recipe.getRecipe());
        if (nodes.size() == 0) {
          stringBuilder.append("No nodes found for " + recipe.getDescription());
        } else {
          for (Node node : nodes) {
            stringBuilder.append(node.toString() + "\n");
          }
        }
        sendAlertMessage(stringBuilder.toString(), message.getChatId().toString());
        return;
      }
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

  public boolean sendAlertMessage(String message, String chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableHtml(true);
    sendMessage.setChatId(chatId);
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
