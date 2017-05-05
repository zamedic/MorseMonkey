package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/05/04.
 */
@Entity
public class Cookbook {
  @Id
  ObjectId objectId;

  String description;
  String cookbook;

  @Reference
  Application application;

  public Cookbook() {
  }

  public Cookbook(String description, String cookbook,
      Application application) {
    this.description = description;
    this.cookbook = cookbook;
    this.application = application;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCookbook() {
    return cookbook;
  }

  public void setCookbook(String cookbook) {
    this.cookbook = cookbook;
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }
}
