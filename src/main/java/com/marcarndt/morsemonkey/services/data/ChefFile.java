package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/05/05.
 */
@Entity
public class ChefFile {

  @Id
  ObjectId objectId;
  String description;
  String filePath;

  public ChefFile() {
  }

  public ChefFile(String description, String filePath) {
    this.description = description;
    this.filePath = filePath;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
