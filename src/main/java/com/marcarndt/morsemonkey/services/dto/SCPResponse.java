package com.marcarndt.morsemonkey.services.dto;

/**
 * Created by arndt on 2017/04/12.
 */
public class SCPResponse extends SSHResponse{

  String fileName;

  public SCPResponse(boolean successful, String log, String fileName) {
    super(successful,log);
    this.fileName = fileName;;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
