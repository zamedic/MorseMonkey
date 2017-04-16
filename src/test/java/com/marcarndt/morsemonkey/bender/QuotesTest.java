package com.marcarndt.morsemonkey.bender;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/04/10.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Quotes.class)
public class QuotesTest {

  @Mock
  Random random;

  @Test
  public void testRandomQuote() {
    try {
      whenNew(Random.class).withNoArguments().thenReturn(random);
      when(random.nextInt(38)).thenReturn(2);
      String result = Quotes.getRandomQuote();

      assertEquals("Hahahahaha. Oh wait youre serious. Let me laugh even harder.",result);
    } catch (Exception e) {
      fail(e.getMessage());
    }


  }


}