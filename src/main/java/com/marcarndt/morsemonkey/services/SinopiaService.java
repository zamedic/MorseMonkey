package com.marcarndt.morsemonkey.services;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/12.
 */
@Stateless
public class SinopiaService {


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
    return true;
  }

  private void bounceSinopiaServers() {

    /*telegramService.getMorseBot()
        .sendAlertMessage("Detected Sinopia prod is down. Restarting it. ", MorseBot.ProdGroup);
    telegramService.getMorseBot()
        .sendAlertMessage("Starting with pchop82 ", MorseBot.ProdGroup);
    SSHResponse response = scpService.runSSHCommand("pchop82", "service sinopia restart");
    telegramService.getMorseBot()
        .sendAlertMessage("Restarting sinopia on pchop84 ", MorseBot.ProdGroup);
    scpService.runSSHCommand("pchop84", "service sinopia restart");
*/
  }

}
