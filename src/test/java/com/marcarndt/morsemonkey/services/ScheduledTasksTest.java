package com.marcarndt.morsemonkey.services;

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


  @Test
  public void remindStanup() {
    Message message = new Message();

    when(morseBot.sendMessage(any(SendMessage.class))).thenReturn(message);

    ArgumentMatcher<String> matcher = new ArgumentMatcher<String>() {
      @Override
      public boolean matches(String sendMessage) {
        return sendMessage.startsWith("Standup in 15 minutes.");
      }
    };

    scheduledTasks.remindStanup();

    verify(morseBot).sendAlertMessage(argThat(matcher));


  }

  @Test
  public void startStandup() {
    Message message = new Message();

    when(morseBot.sendMessage(any(SendMessage.class))).thenReturn(message);

    scheduledTasks.startStandup();

    verify(morseBot).sendAlertMessage("If you are not at Standup, you are officially late.");


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