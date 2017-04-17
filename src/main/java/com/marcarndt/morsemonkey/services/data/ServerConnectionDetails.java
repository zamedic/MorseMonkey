package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/16.
 */
@Entity()
public class ServerConnectionDetails {

  @Id
  private ObjectId objectId;
  private String authId;
  private String userId;
  private String keyPath;
  private String password;
  private boolean requireSudo;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getKeyPath() {
    return keyPath;
  }

  public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isRequireSudo() {
    return requireSudo;
  }

  public void setRequireSudo(boolean requireSudo) {
    this.requireSudo = requireSudo;
  }
}
