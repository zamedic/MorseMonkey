package com.marcarndt.morsemonkey.services;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/09.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MorseBot.class, AbsSender.class})
public class ScheduledTasksTest {

  @Mock
  private transient MorseBot morseBot;

  @Mock
  private transient TelegramService telegramService;

  @InjectMocks
  private transient ScheduledTasks scheduledTasks;

  @Before
  public void setUp() {
    when(telegramService.getMorseBot()).thenReturn(morseBot);
  }

  @Test
  public void remindStanup() {
    Message message = new Message();
    try {
      when(morseBot.sendMessage(any(SendMessage.class))).thenReturn(message);
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

    ArgumentMatcher<SendMessage> matcher = new ArgumentMatcher<SendMessage>() {
      @Override
      public boolean matches(SendMessage sendMessage) {
        return sendMessage.getText().startsWith("Standup in 15 minutes.");
      }
    };

    scheduledTasks.remindStanup();

    try {
      verify(morseBot).sendMessage(argThat(matcher));
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void startStandup() {
    Message message = new Message();
    try {
      when(morseBot.sendMessage(any(SendMessage.class))).thenReturn(message);
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

    ArgumentMatcher<SendMessage> matcher = new ArgumentMatcher<SendMessage>() {
      @Override
      public boolean matches( SendMessage sendMessage) {
        return sendMessage.getText().startsWith("If you are not at Standup, you are officially late.");
      }
    };

    scheduledTasks.startStandup();

    try {
      verify(morseBot).sendMessage(argThat(matcher));
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }


  }

  public MorseBot getMorseBot() {
    return morseBot;
  }

  public TelegramService getTelegramService() {
    return telegramService;
  }

  public ScheduledTasks getScheduledTasks() {
    return scheduledTasks;
  }
}