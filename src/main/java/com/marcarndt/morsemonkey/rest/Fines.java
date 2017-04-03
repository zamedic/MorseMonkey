package com.marcarndt.morsemonkey.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by arndt on 2017/04/02.
 */

@Path("/fines")
@RequestScoped
public class Fines {

  @GET
  public void getFines(){

  }

}
