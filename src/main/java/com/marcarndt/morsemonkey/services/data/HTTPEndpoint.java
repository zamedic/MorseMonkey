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
  String body;
  String method;

  public HTTPEndpoint() {

  }

  public HTTPEndpoint(String name, String URL, String body,String method) {
    this.name = name;
    this.URL = URL;
    this.method = method;
    this.body = body;
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
