package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.UserChatState;

import java.util.List;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/17.
 */
public class StateService {

  @Inject
  MongoService mongoService;

  /**
   * @param userid Telegram user id
   * @param chatid Telegram chat id
   */
  public State getUserState(int userid, long chatid) throws MorseMonkeyException {
    UserChatState userChatState = getUserChatState(userid, chatid);
    if (userChatState == null) {
      throw new MorseMonkeyException("I am not sure how to handle that.");
    }
    return userChatState.getState();
  }


  public void setState(int userid, long chatid, State state) {
    setState(userid, chatid, state, null);
  }

  /**
   * @param userid Telegram user ID
   * @param chatid Telegram Chat ID
   * @param state New State
   * @param parameters additional parameters
   */
  public void setState(int userid, long chatid, State state, String... parameters) {
    UserChatState userChatState = getUserChatState(userid, chatid);
    if (userChatState != null) {
      mongoService.getDatastore().delete(userChatState);
    }

    userChatState = new UserChatState(userid, chatid, state, parameters);
    mongoService.getDatastore().save(userChatState);
  }

  public List<String> getParameters(int userId, long chatId) {
    UserChatState userChatState = getUserChatState(userId, chatId);
    return userChatState.getFields();
  }

  private UserChatState getUserChatState(int userid, long chatid) {
    return mongoService.getDatastore().createQuery(UserChatState.class).field("userId")
        .equal(userid).field("chatId").equal(chatid).get();
  }


  public enum State {
    CONFIGURE,
    USER_ADMIN,
    USER_ADMIN_ADD_ROLE_SELECT_USER,
    USER_ADMIN_DELETE_ROLE_SELECT_USER,
    USER_ADMIN_DELETE_USER_SELECT_USER,
    USER_ADMIN_DELETE_ROLE_SELECT_ROLE, USER_ADMIN_ADD_ROLE_SELECT_ROLE, NODE_LIST_FOR_RECIPE
  }

}
