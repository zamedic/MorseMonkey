package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/13.
 */
public class Node {

  String name;
  String environment;
  String platform;
  String ipAddress;

  public Node(String name, String environment, String platform, String ipAddress) {
    this.name = name;
    this.environment = environment;
    this.platform = platform;
    this.ipAddress = ipAddress;
  }

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

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @Override
  public String toString() {
    return  "name='" + name + '\'' +
        ", environment='" + environment + '\'' +
        ", platform='" + platform + '\'';

  }
}
