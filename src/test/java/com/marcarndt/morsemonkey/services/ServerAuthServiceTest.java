package com.marcarndt.morsemonkey.services;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.marcarndt.morsemonkey.services.dto.AuthDetails;
import java.net.URL;
import org.junit.Test;

/**
 * Created by arndt on 2017/04/16.
 */
public class ServerAuthServiceTest {

  @Test
  public void setupAuthRecords() throws Exception {

    URL path = this.getClass().getClassLoader().getResource("testAuthFile.txt");
    ServerAuthService serverAuthService = new ServerAuthService();
    serverAuthService.authFile = path.toURI().toString().replaceAll("file:/", "");
    serverAuthService.setupAuthRecords();

    AuthDetails authDetails = serverAuthService.getAuthDetails("a");
    assertNotNull(authDetails);
    assertEquals("aUser", authDetails.getUserId());
    assertEquals("aPassword", authDetails.getPassword());
    assertEquals("aKey", authDetails.getKeyPath());
  }

}