package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/17.
 */
@Entity
public class Recipe {

  @Id
  private ObjectId objectId;
  private String userFriendlyName;
  private String recipeName;

  public Recipe() {
  }

  public Recipe(String userFriendlyName, String recipeName) {
    this.userFriendlyName = userFriendlyName;
    this.recipeName = recipeName;
  }

  public String getUserFriendlyName() {
    return userFriendlyName;
  }

  public void setUserFriendlyName(String userFriendlyName) {
    this.userFriendlyName = userFriendlyName;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }
}
