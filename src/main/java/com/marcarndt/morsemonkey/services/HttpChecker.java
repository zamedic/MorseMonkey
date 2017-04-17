package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.HTTPEndpoint;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by arndt on 2017/04/12.
 */
@Stateless
public class HttpChecker {

  List<HTTPEndpoint> httpNodesList = new ArrayList<>();

  @Inject
  TelegramService telegramService;
  @Inject
  MongoService mongoService;
  @Inject
  MorseBot morseBot;


  @PostConstruct
  public void setUp() {
    httpNodesList = mongoService.getDatastore().createQuery(HTTPEndpoint.class).asList();
  }


  public void runChecks() {
    for (HTTPEndpoint node : httpNodesList) {
      try {
        checkNode(node);
      } catch (MorseMonkeyException e) {
        morseBot.sendAlertMessage(
            e.getMessage());
      }
    }
  }

  public void addHttpEndpoint(String description, String url){
    HTTPEndpoint httpEndpoint = new HTTPEndpoint(description,url);
    mongoService.getDatastore().save(httpEndpoint);
    httpNodesList.add(httpEndpoint);
  }

  public void checkNode(HTTPEndpoint httpEndpoint) throws MorseMonkeyException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(httpEndpoint.getURL());
    try {
      CloseableHttpResponse response = httpClient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new MorseMonkeyException(
            "Response code from " + httpEndpoint.getName() + " on URL " + httpEndpoint.getURL()
                + " was " + response
                .getStatusLine().getStatusCode() + " which is not the expected 200");
      }
    } catch (IOException e) {
      throw new MorseMonkeyException(
          "Error encoutnered checking node " + httpEndpoint.getName() + " on URL " + httpEndpoint
              .getURL()
              + ". Error Encountered " + e.getMessage());
    }
  }

}
