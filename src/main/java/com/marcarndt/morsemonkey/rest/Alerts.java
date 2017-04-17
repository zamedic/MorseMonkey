package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by arndt on 2017/04/02.
 */
@Path("/alert")
@RequestScoped
public class Alerts {

  private static Logger LOG = Logger.getLogger(Alerts.class.getName());

  @Inject
  MorseBot morseBot;

  @Path("{groupId}")
  @POST
  @Consumes("text/plain")
  public Response sendAlertToGroup(String body) {
    LOG.info("Received alert: " + body);
    if (morseBot.sendAlertMessage(body)) {
      return Response.ok().build();
    } else {
      return Response.serverError().build();
    }
  }
}

