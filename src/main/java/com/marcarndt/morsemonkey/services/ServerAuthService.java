package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.data.ServerConnectionDetails;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/16.
 */
@Stateless
public class ServerAuthService {

  @Inject
  MongoService mongoService;

  @PostConstruct
  public void setupAuthRecords() {

  }

  public ServerConnectionDetails getAuthDetails(String key) {
    return mongoService.getDatastore().createQuery(ServerConnectionDetails.class).field("authId")
        .equal(key).get();
  }
}
