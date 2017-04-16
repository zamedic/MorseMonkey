package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.dto.AuthDetails;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/16.
 */
@Singleton
public class ServerAuthService {

  @Inject
  @ConfigurationValue("")
  String authFile;

  Map<String, AuthDetails> authMap;

  @PostConstruct
  public void setupAuthRecords(){

    try {
      Stream<String> stream = Files.lines(Paths.get(authFile));
      authMap = stream.map(s -> s.split(","))
          .collect(Collectors.toMap(s -> s[0], s -> new AuthDetails(s[1],s[2],s[3])));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public AuthDetails getAuthDetails(String key){
    return authMap.get(key);
  }
}
