package com.marcarndt.morsemonkey.services;

import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


import com.marcarndt.morsemonkey.services.data.BotDetails;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;

/**7
 * Created by arndt on 2017/04/03.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Logger.class, ApiContextInitializer.class, MorseBot.class, TelegramService.class})
public class TelegramServiceTest {

  @Mock
  Logger logger;
  @Mock
  TelegramBotsApi telegramBotsApi;
  @Mock
  BotSession botSession;
  @Mock
  MorseBot morseBot;
  @Mock
  MongoService mongoService;
  @Mock
  InitialContext initialContext;
  @Mock
  Datastore datastore;
  @Mock
  Query query;

  @InjectMocks
  TelegramService telegramService;

  @Before
  public void setUp() {
    PowerMockito.mockStatic(Logger.class, ApiContextInitializer.class);
    when(Logger.getLogger("com.marcarndt.morsemonkey.services.TelegramService")).thenReturn(logger);
    when(mongoService.getDatastore()).thenReturn(datastore);
    when(datastore.createQuery(BotDetails.class)).thenReturn(query);
    when(query.count()).thenReturn(0l);

    try {
      whenNew(MorseBot.class).withArguments(telegramService).thenReturn(morseBot);
      whenNew(InitialContext.class).withNoArguments().thenReturn(initialContext);

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSuccess() {
    try {
      when(telegramBotsApi.registerBot(morseBot)).thenReturn(botSession);
    } catch (TelegramApiRequestException e) {
      fail(e.getMessage());
    }

    telegramService.setup();
    verify(logger, never()).log(Level.SEVERE, any(String.class), any(Throwable.class));

  }

  @Test
  public void testError() {
    TelegramApiRequestException exception = new TelegramApiRequestException("Test Exception");
    try {
      when(telegramBotsApi.registerBot(morseBot)).thenThrow(exception);
    } catch (TelegramApiRequestException e) {
      fail(e.getMessage());
    }
    telegramService.setApi(telegramBotsApi);
    telegramService.setup();
    verify(logger).log(Level.SEVERE, "Test Exception", exception);
  }
}