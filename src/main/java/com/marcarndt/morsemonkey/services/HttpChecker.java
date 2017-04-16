package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.dto.HttpNodes;
import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/12.
 */
@Stateless
public class HttpChecker {

  List<HttpNodes> httpNodesList = new ArrayList<>();

  @PostConstruct
  public void setUp() {
    httpNodesList.add(new HttpNodes("https://insurance-dev.standardbank.co.za", "SBIS Dev Web"));
    httpNodesList
        .add(new HttpNodes("https://insurance-dev.standardbank.co.za/rest/test", "SBIS Dev App"));
    httpNodesList.add(new HttpNodes("https://insurance-test.standardbank.co.za", "SBIS Test Web"));
    httpNodesList
        .add(new HttpNodes("https://insurance-test.standardbank.co.za/rest/test", "SBIS Test App"));


  }

  @Inject
  HTMLService htmlService;

  @Inject
  TelegramService telegramService;

  public void runChecks() {
    for (HttpNodes node : httpNodesList) {
      if (!htmlService.checkResponse(node.getUrl())) {
        telegramService.getAlertBot().sendAlertMessage(
            "Could not get a successful response for " + node.getDescription() + " - " + node
                .getUrl(), AlertBot.DevGroup);
      }
    }
  }

}
