package com.marcarndt.morsemonkey.services;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcarndt.morsemonkey.telegram.fines.FinesBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;

/**
 * Created by arndt on 2017/04/03.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Logger.class, ApiContextInitializer.class})
public class TelegramServiceTest {

  @Mock
  Logger logger;
  @Mock
  TelegramBotsApi telegramBotsApi;
  @Mock
  BotSession botSession;
  @Mock
  FinesBot finesBot;

  @InjectMocks
  TelegramService telegramService;


  @Before
  public void setup() {
    PowerMockito.mockStatic(Logger.class, ApiContextInitializer.class);
    when(Logger.getLogger("com.marcarndt.morsemonkey.services.TelegramService")).thenReturn(logger);
  }

  @Test
  public void testSuccess() {
    try {
      when(telegramBotsApi.registerBot(finesBot)).thenReturn(botSession);
    } catch (TelegramApiRequestException e) {
      fail(e.getMessage());
    }
    telegramService.setApi(telegramBotsApi);
    telegramService.setup();
    verify(logger, never()).log(Level.SEVERE, any(String.class), any(Throwable.class));

  }

  @Test
  public void testError() {
    TelegramApiRequestException exception = new TelegramApiRequestException("Test Exception");
    try {
      when(telegramBotsApi.registerBot(finesBot)).thenThrow(exception);
    } catch (TelegramApiRequestException e) {
      fail(e.getMessage());
    }
    telegramService.setApi(telegramBotsApi);
    telegramService.setup();
    verify(logger).log(Level.SEVERE, "Test Exception", exception);
  }
}