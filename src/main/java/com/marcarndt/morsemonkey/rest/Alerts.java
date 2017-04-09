package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.services.RestService;
import com.marcarndt.morsemonkey.telegram.alerts.AlertBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by arndt on 2017/04/02.
 */
@Path("/alert")
@RequestScoped
public class Alerts {

  private static Logger LOG = Logger.getLogger(Alerts.class.getName());

  @Inject
  RestService restService;

  @Path("{groupId}")
  @POST
  @Consumes("text/plain")
  public Response sendAlertToGroup(@PathParam("groupId") String groupId, String body) {
    try {
      restService.getAlertBot().sendAlertMessage(body, groupId);
      return Response.ok().build();
    } catch (TelegramApiException e) {
      LOG.log(Level.WARNING, e.getMessage(), e);
      return Response.serverError().entity(e.getMessage()).build();

    }
  }


}
