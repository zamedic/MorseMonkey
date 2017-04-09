package com.marcarndt.morsemonkey.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by arndt on 2017/04/03.
 */

@ApplicationPath("/rest")
public class MorseMonkeyRestApplication extends Application {

  public MorseMonkeyRestApplication() {
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(Alerts.class);
    classes.add(Fines.class);
    return classes;
  }
}
