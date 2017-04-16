package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.services.RestService;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
    LOG.info("Received alert: "+body);
    if (restService.getAlertBot().sendAlertMessage(body,groupId)) {
      return Response.ok().build();
    } else {
      return Response.serverError().build();
    }
  }
}

