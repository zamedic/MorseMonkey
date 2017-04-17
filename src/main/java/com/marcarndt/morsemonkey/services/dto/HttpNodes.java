package com.marcarndt.morsemonkey.services.dto;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import java.io.IOException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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
