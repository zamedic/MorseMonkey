package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/13.
 */
public class HttpNodes {

  String url;
  String description;

  public HttpNodes(String url, String description) {
    this.url = url;
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }
}
