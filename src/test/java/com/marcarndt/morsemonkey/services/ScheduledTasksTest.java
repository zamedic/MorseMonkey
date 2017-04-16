package com.marcarndt.morsemonkey.services;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
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
@PrepareForTest({AlertBot.class, AbsSender.class})
public class ScheduledTasksTest {

  @Mock
  private transient AlertBot alertBot;

  @Mock
  private transient TelegramService telegramService;

  @InjectMocks
  private transient ScheduledTasks scheduledTasks;

  @Before
  public void setUp() {
    when(telegramService.getAlertBot()).thenReturn(alertBot);
  }

  @Test
  public void remindStanup() {
    final Message message = new Message();
    try {
      when(alertBot.sendMessage(any(SendMessage.class))).thenReturn(message);
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

    final ArgumentMatcher<SendMessage> matcher = new ArgumentMatcher<SendMessage>() {
      @Override
      public boolean matches(final SendMessage sendMessage) {
        return sendMessage.getText().startsWith("Standup in 15 minutes.");
      }
    };

    scheduledTasks.remindStanup();

    try {
      verify(alertBot).sendMessage(argThat(matcher));
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void startStandup() {
    final Message message = new Message();
    try {
      when(alertBot.sendMessage(any(SendMessage.class))).thenReturn(message);
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }

    final ArgumentMatcher<SendMessage> matcher = new ArgumentMatcher<SendMessage>() {
      @Override
      public boolean matches( final SendMessage sendMessage) {
        return sendMessage.getText().startsWith("If you are not at Standup, you are officially late.");
      }
    };

    scheduledTasks.startStandup();

    try {
      verify(alertBot).sendMessage(argThat(matcher));
    } catch (TelegramApiException e) {
      fail(e.getMessage());
    }


  }

  public AlertBot getAlertBot() {
    return alertBot;
  }

  public TelegramService getTelegramService() {
    return telegramService;
  }

  public ScheduledTasks getScheduledTasks() {
    return scheduledTasks;
  }
}