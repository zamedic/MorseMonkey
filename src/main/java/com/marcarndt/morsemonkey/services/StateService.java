package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.UserChatState;
import java.util.ArrayList;
import java.util.Arrays;
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
    setState(userid, chatid, state, new ArrayList<String>());
  }

  public void setState(int userid, long chatid, State state, List<String> parameters) {
    checkAndDeleteState(userid, chatid);

    UserChatState userChatState = new UserChatState(userid, chatid, state, parameters);
    mongoService.getDatastore().save(userChatState);
  }

  private void checkAndDeleteState(int userid, long chatid) {
    UserChatState userChatState = getUserChatState(userid, chatid);
    if (userChatState != null) {
      mongoService.getDatastore().delete(userChatState);
    }
  }

  /**
   * @param userid Telegram user ID
   * @param chatid Telegram Chat ID
   * @param state New State
   * @param parameters additional parameters
   */
  public void setState(int userid, long chatid, State state, String... parameters) {
    setState(userid, chatid, state, Arrays.asList(parameters));
  }

  public List<String> getParameters(int userId, long chatId) {
    UserChatState userChatState = getUserChatState(userId, chatId);
    return userChatState.getFields();
  }

  private UserChatState getUserChatState(int userid, long chatid) {
    return mongoService.getDatastore().createQuery(UserChatState.class).field("userId")
        .equal(userid).field("chatId").equal(chatid).get();
  }

  public void deleteState(Integer id, Long chatId) {
    checkAndDeleteState(id, chatId);
  }


  public enum State {
    CONFIGURE,
    USER_ADMIN,
    USER_ADMIN_ADD_ROLE_SELECT_USER,
    USER_ADMIN_DELETE_ROLE_SELECT_USER,
    USER_ADMIN_DELETE_USER_SELECT_USER,
    USER_ADMIN_DELETE_ROLE_SELECT_ROLE, USER_ADMIN_ADD_ROLE_SELECT_ROLE, CONFIGURE_HTTP, CONFIGURE_HTTP_ADD_NAME, CONFIGURE_HTTP_ADD_URL, CONFIGURE_APPLICATION, CONFIGURE_USER, CONFIGURE_USER_ADD, CONFIGURE_USER_DELETE, CONFIGURE_USER_ADD_ROLE, CONFIGURE_HTTP_ADD_BODY, CONFIGURE_HTTP_ADD_GETPOST, CONFIGURE_HTTP_ADD_GETPOST_RESPONSE, CONFIGURE_HTTP_ADD_POST_BODY, VCM, VCM_ADD, VCM_ADD_COMMAND, VCM_EXECUTE, VCM_DELETE, CONFIGURE_ADD_APPLICATION, CONFIGURE_EDIT_APPLICATION, CONFIGURE_EDIT_APPLICATION_OPTIONS, CONFIGURE_EDIT_APPLICATION_APPDYNAMICS, CONFIGURE_EDIT_APPLICATION_APPDYNAMICS_ADD, CONFIGURE_AUTH, CONFIGURE_AUTH_ADD, CONFIGURE_AUTH_ADD_USER, CONFIGURE_AUTH_ADD_TYPE, CONFIGURE_AUTH_ADD_KEY, CONFIGURE_AUTH_ADD_PASSWORD, CONFIGURE_COMMAND, CONFIGURE_COMMAND_ADD, CONFIGURE_COMMAND_ADD_COMMAND, EXECUTE, CONFIGURE_EDIT_APPLICATION_RECIPE, CONFIGURE_EDIT_APPLICATION_RECIPE_ADD, CONFIGURE_EDIT_APPLICATION_RECIPE_COOKBOOK, EXECUTE_APPLICATION, EXECUTE_COOKBOOK, EXECUTE_ENVIRONMENT, EXECUTE_NODE, CONFIGURE_CHEF, CONFIGURE_CHEF_ORG, CONFIGURE_CHEF_KEY, CONFIGURE_CHEF_SERVER, CONFIGURE_CHEF_USER, CONFIGURE_FILE, CONFIGURE_FILE_ADD, CONFIGURE_FILE_ADD_DETAILS, FILE, FILE_APPLICATION, FILE_COOKBOOK, FILE_NODE, NODE_LIST_FOR_RECIPE
  }

}
