package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/13.
 */
public class Node {

  public Node(String name, String environment, String platform) {
    this.name = name;
    this.environment = environment;
    this.platform = platform;
  }

  String name;
  String environment;
  String platform;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  @Override
  public String toString() {
    return  "name='" + name + '\'' +
        ", environment='" + environment + '\'' +
        ", platform='" + platform + '\'';

  }
}
