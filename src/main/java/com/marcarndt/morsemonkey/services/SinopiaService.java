package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
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
public class SinopiaService {

  @Inject
  HTMLService htmlService;

  @Inject
  TelegramService telegramService;

  @Inject
  SCPService scpService;

  private static Logger LOG = Logger.getLogger(SinopiaService.class.getName());

  public void runChecks() {
    LOG.info("Checking Sinopia");
    if (!checkSinopia()) {
      bounceSinopiaServers();
    }
    LOG.info("Done checking Sinopia");

  }

  private boolean checkSinopia() {
    return htmlService.checkResponse("http://npmjs.standardbank.co.za/");
  }

  private void bounceSinopiaServers() {

    telegramService.getAlertBot()
        .sendAlertMessage("Detected Sinopia prod is down. Restarting it. ", AlertBot.ProdGroup);
    telegramService.getAlertBot()
        .sendAlertMessage("Starting with pchop82 ", AlertBot.ProdGroup);
    SSHResponse response = scpService.runSSHCommand("pchop82", "service sinopia restart");
    telegramService.getAlertBot()
        .sendAlertMessage("Restarting sinopia on pchop84 ", AlertBot.ProdGroup);
    scpService.runSSHCommand("pchop84", "service sinopia restart");

  }

}
