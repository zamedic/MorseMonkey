package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/05/04.
 */
@Entity
public class AuthDetails {
  @Id
  ObjectId objectId;
  String name;
  String username;
  AuthTypes authTypes;
  String details;

  public AuthDetails() {
  }

  public AuthDetails(String name, String username,
      AuthTypes authTypes, String details) {
    this.name = name;
    this.username = username;
    this.authTypes = authTypes;
    this.details = details;
  }

  public enum AuthTypes {
    KEY,PASSWORD
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthTypes getAuthTypes() {
    return authTypes;
  }

  public void setAuthTypes(AuthTypes authTypes) {
    this.authTypes = authTypes;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
