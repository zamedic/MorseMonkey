package com.marcarndt.morsemonkey.telegram.alerts;

import com.marcarndt.morsemonkey.bender.Quotes;
import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.ConfigureService;
import com.marcarndt.morsemonkey.services.StateService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.telegram.alerts.command.BaseCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.VCMCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import com.marcarndt.morsemonkey.utils.BotConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/06.
 */
@Singleton
public class MorseBot extends TelegramLongPollingCommandBot {

  private static Logger LOG = Logger.getLogger(MorseBot.class.getName());

  @Inject
  StateService stateService;
  @Inject
  ConfigureService configureService;

  @Inject
  @Any
  Instance<Commandlet> commandlets;

  @Inject
  @Any
  Instance<BaseCommand> commands;

  public MorseBot() {
    super();
  }

  public void sendReplyKeyboardMessage(User user, Chat chat, String text,
      List<String> buttons) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chat.getId());
    sendMessage.setText(text + " @" + BaseCommand.getUsername(user));
    sendMessage.setReplyMarkup(sendReplyKeyboardMarkup(buttons, chat.isGroupChat()));
    sendMessage(sendMessage);
  }

  public void sendReplyKeyboardMessage(User user, Chat chat, String text,
      String... buttons) {
    sendReplyKeyboardMessage(user, chat, text, Arrays.asList(buttons));
  }

  public void sendReplyKeyboardMessage(Message message, String text,
      List<String> buttons) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.getChatId());
    sendMessage.setText(text + " @" + BaseCommand.getUsername(message));
    sendMessage.setReplyMarkup(sendReplyKeyboardMarkup(buttons, message.isGroupMessage()));
    sendMessage(sendMessage);
  }

  public void sendReplyKeyboardMessage(Message message, String text,
      String... buttons) {
    sendReplyKeyboardMessage(message, text, Arrays.asList(buttons));
  }

  public ReplyKeyboardMarkup sendReplyKeyboardMarkup(List<String> buttons, boolean isGroup) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setOneTimeKeyboad(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setSelective(isGroup);

    List<KeyboardRow> keyboardRows = new ArrayList<>();
    int count = 0;
    KeyboardRow keyboardRow = new KeyboardRow();
    for (String button : buttons) {
      keyboardRow.add(new KeyboardButton(button));
      count++;
      if (count % 2 == 0) {
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
      }
    }
    if (keyboardRow.size() > 0) {
      keyboardRows.add(keyboardRow);
    }

    replyKeyboardMarkup.setKeyboard(keyboardRows);

    return replyKeyboardMarkup;
  }

  public void sendReplyMessage(Message message, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(message.getChatId());
    sendMessage.setText(text + " @" + BaseCommand.getUsername(message));
    ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
    forceReplyKeyboard.setSelective(true);
    sendMessage.setReplyMarkup(forceReplyKeyboard);
    sendMessage(sendMessage);
  }

  @PostConstruct
  public void setup() {
    for (BaseCommand baseCommand : commands) {
      register(baseCommand);
    }




  }

  @Override
  public void processNonCommandUpdate(Update update) {
    LOG.info("Receives update");
    if (update.getMessage().getNewChatMember() != null) {
      sendMessage("Ahhh Yeah, one more human to worship me. I am Bender!",
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
      State state = stateService
          .getUserState(update.getMessage().getFrom().getId(), update.getMessage().getChatId());
      handleUpdate(update.getMessage(), state);
      return;
    } catch (MorseMonkeyException e) {
      LOG.info("No state found for user");
    }

    sendMessage("I dont know that command human. " + Quotes.getRandomQuote(),
        update.getMessage().getChatId().toString());

  }

  @Override
  public Message sendMessage(SendMessage sendMessage) {
    try {
      return super.sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      LOG.log(Level.SEVERE, "Could not send message", e);
      return null;
    }
  }

  private void handleUpdate(Message message, State command) {
    LOG.info(
        "Searching for commandlet to handle state " + command.toString() + " message " + message
            .getText());
    for (Commandlet commandlet : commandlets) {
      if (commandlet.canHandleCommand(message, command)) {
        LOG.info("Executing class " + commandlet.getClass().getName());
        commandlet.handleCommand(message, command,
            stateService.getParameters(message.getFrom().getId(), message.getChatId()),
            this);
        State newState = commandlet.getNewState(message, command);
        if (newState == null) {
          stateService.deleteState(message.getFrom().getId(), message.getChatId());
        } else {
          stateService.setState(message.getFrom().getId(), message.getChatId(), newState,
              commandlet.getNewStateParams(message, command,
                  stateService.getParameters(message.getFrom().getId(), message.getChatId())));
        }
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

  public boolean sendAlertMessage(String message, boolean html) {
    try {
      return sendMessage(message, configureService.getGroupKey(), html);
    } catch (MorseMonkeyException e) {
      LOG.log(Level.WARNING, "Error sending alert", e);
      return false;
    }
  }

  public boolean sendAlertMessage(String message) {
    return sendAlertMessage(message, false);
  }


  public boolean sendMessage(String message, String key, boolean html) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableHtml(html);
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

  public boolean sendMessage(String message, String key) {
    return sendMessage(message, key, false);
  }

}


