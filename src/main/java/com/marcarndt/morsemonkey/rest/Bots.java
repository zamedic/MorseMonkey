package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.rest.dto.Status;
import com.marcarndt.morsemonkey.services.TelegramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by arndt on 2017/04/03.
 */
@RequestScoped
@Path("/bots")
@Produces("application/json")
@Api(value = "bots")
public class Bots {

  @Inject
  TelegramService telegramService;

  @GET
  @ApiOperation(value = "Get Bot Status")
  public Status getBots() {
    Status status = new Status();
    status.setStatus(telegramService.getStatus());
    return status;
  }

}
