package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.HTTPEndpoint;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by arndt on 2017/04/12.
 */
@Stateless
public class HttpChecker {

  @Inject
  TelegramService telegramService;
  @Inject
  MongoService mongoService;
  @Inject
  MorseBot morseBot;


  public void runChecks() {
    List<HTTPEndpoint> httpNodesList = mongoService.getDatastore().createQuery(HTTPEndpoint.class)
        .asList();
    for (HTTPEndpoint node : httpNodesList) {
      try {
        checkNode(node);
      } catch (MorseMonkeyException e) {
        morseBot.sendAlertMessage(
            e.getMessage());
      }
    }
  }

  public void addHttpPostEndpoint(String description, String url, String body)
      throws MorseMonkeyException {
    HTTPEndpoint httpEndpoint = new HTTPEndpoint(description, url, body, "POST");
    checkNode(httpEndpoint);
    mongoService.getDatastore().save(httpEndpoint);
  }

  public void addHttpGetEndpoint(String name, String url) throws MorseMonkeyException {
    HTTPEndpoint httpEndpoint = new HTTPEndpoint(name, url, null, "GET");
    checkNode(httpEndpoint);
    mongoService.getDatastore().save(httpEndpoint);
  }

  public String checkNode(HTTPEndpoint httpEndpoint) throws MorseMonkeyException {
    CloseableHttpResponse response;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      if(httpEndpoint.getMethod() == null){
        httpEndpoint.setMethod("GET");
        mongoService.getDatastore().save(httpEndpoint);
      }
      if (httpEndpoint.getMethod().equals("POST")) {
        HttpPost httpPost = new HttpPost(httpEndpoint.getURL());
        httpPost.setEntity(new StringEntity(httpEndpoint.getBody(), ContentType.APPLICATION_JSON));
        response =httpClient.execute(httpPost);
      } else {
        HttpGet httpGet = new HttpGet(httpEndpoint.getURL());
        response = httpClient.execute(httpGet);
      }
      InputStream in = response.getEntity().getContent();
      String body = IOUtils.toString(in, "UTF-8");

      if (response.getStatusLine().getStatusCode() != 200) {
        throw new MorseMonkeyException(
            "Response code from " + httpEndpoint.getName() + " on URL " + httpEndpoint.getURL()
                + " was " + response
                .getStatusLine().getStatusCode() + " which is not the expected 200. " + body);
      } else {
        return body;
      }
    } catch (IOException e) {
      throw new MorseMonkeyException(
          "Error encoutnered checking node " + httpEndpoint.getName() + " on URL " + httpEndpoint
              .getURL()
              + ". Error Encountered " + e.getMessage());
    }
  }

}
