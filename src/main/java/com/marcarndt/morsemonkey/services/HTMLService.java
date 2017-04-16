package com.marcarndt.morsemonkey.services;

import java.io.IOException;
import javax.ejb.Stateless;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by arndt on 2017/04/12.
 */
@Stateless
public class HTMLService {

  public boolean checkResponse(String url){
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet get = new HttpGet("http://npmjs.standardbank.co.za/");
    try {
      CloseableHttpResponse response = httpClient.execute(get);
      return response.getStatusLine().getStatusCode() == 200;
    } catch (IOException e) {

      return false;
    }

  }

}
