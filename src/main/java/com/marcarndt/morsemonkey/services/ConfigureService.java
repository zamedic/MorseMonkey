package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.BotDetails;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/04/17.
 */
@Stateless
public class ConfigureService {

  @Inject
  MongoService mongoService;

  public void handleUpdate(Message message) {

  }

  public String getGroupKey() throws MorseMonkeyException {
    BotDetails botDetails = mongoService.getDatastore().createQuery(BotDetails.class).get();
    if (botDetails == null) {
      throw new MorseMonkeyException("Group channel has not been configured");
    }
    return botDetails.getGroupId();

  }


}
