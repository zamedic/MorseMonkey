package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/16.
 */
public class AuthDetails {

  String userId;
  String keyPath;
  String password;

  public AuthDetails(String userId, String keyPath, String password) {
    this.userId = userId;
    this.keyPath = keyPath;
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public String getKeyPath() {
    return keyPath;
  }

  public String getPassword() {
    return password;
  }
}
