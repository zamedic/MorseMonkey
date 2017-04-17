package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/16.
 */
@Entity()
public class ChefDetails {

  @Id
  private ObjectId objectId;
  private String serverUrl;
  private String userName;
  private String keyPath;
  private String orginisation;


  public ChefDetails() {

  }

  public String getServerUrl() {
    return serverUrl;
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getKeyPath() {
    return keyPath;
  }

  public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  public String getOrginisation() {
    return orginisation;
  }

  public void setOrginisation(String orginisation) {
    this.orginisation = orginisation;
  }
}
