package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/16.
 */
@Entity
public class HTTPEndpoint {
  @Id
  private ObjectId objectId;

  String name;
  String URL;

  public HTTPEndpoint() {

  }

  public HTTPEndpoint(String name, String URL) {
    this.name = name;
    this.URL = URL;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }
}
