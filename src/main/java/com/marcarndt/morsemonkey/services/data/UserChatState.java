package com.marcarndt.morsemonkey.services.data;

import com.marcarndt.morsemonkey.services.StateService.State;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/17.
 */
@Entity
public class UserChatState {

  @Id
  private ObjectId objectId;
  private int userId;
  long chatId;
  State state;
  List<String> fields;

  public UserChatState() {
  }

  public UserChatState(int userId, long chatId,
      State state, List<String> parameters) {
    this.userId = userId;
    this.chatId = chatId;
    this.state = state;
    this.fields = parameters;
  }


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }
}

