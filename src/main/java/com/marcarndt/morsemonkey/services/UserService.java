package com.marcarndt.morsemonkey.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/13.
 */
@Singleton
public class UserService {

  private static Logger LOG = Logger.getLogger(UserService.class.getName());

  @Inject
  @ConfigurationValue("users.file")
  String usersFile;


  HashMap<Integer, String> frontEnd = new HashMap<>();

  @PostConstruct
  public void setupUsers() {
    String line = null;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(usersFile));
      while ((line = reader.readLine()) != null) {
        StringTokenizer stringTokenizer = new StringTokenizer(line, ":");
        String name = stringTokenizer.nextToken();
        Integer id = Integer.parseInt(stringTokenizer.nextToken());
        frontEnd.put(id, name);
      }
    } catch (FileNotFoundException e) {
      LOG.log(Level.SEVERE,"Error loading user file",e);
    } catch (IOException e) {
      LOG.log(Level.SEVERE,"Error loading user file",e);
    }
  }

  public boolean validateFrontend(Integer id) {
    return frontEnd.containsKey(id);
  }

}
