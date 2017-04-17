package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.UserChatState;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/17.
 */
public class StateService {

  @Inject
  MongoService mongoService;

  public State getUserState(int userid, long chatid) throws MorseMonkeyException {
    UserChatState userChatState = getUserChatState(userid, chatid);
    if (userChatState == null) {
      throw new MorseMonkeyException("I am not sure how to handle that.");
    }
    return userChatState.getState();
  }

  private UserChatState getUserChatState(int userid, long chatid) {
    return mongoService.getDatastore().createQuery(UserChatState.class).field("userId")
        .equal(userid).field("chatId").equal(chatid).get();
  }

  public void setState(int userid, long chatid, State state) {
    UserChatState userChatState = getUserChatState(userid, chatid);
    if (userChatState != null) {
      mongoService.getDatastore().delete(userChatState);
    }

    userChatState = new UserChatState(userid, chatid, state);
    mongoService.getDatastore().save(userChatState);
  }


  public enum State {CONFIGURE,NODE_LIST_FOR_RECIPE}

}
