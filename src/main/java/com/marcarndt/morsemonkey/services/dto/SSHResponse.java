package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/12.
 */
public class SSHResponse {

  boolean successful;
  String log;

  public SSHResponse(boolean successful, String log) {
    this.successful = successful;
    this.log = log;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  public String getLog() {
    return log;
  }

  public void setLog(String log) {
    this.log = log;
  }


}
