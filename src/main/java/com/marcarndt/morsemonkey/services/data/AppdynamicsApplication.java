package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/05/03.
 */
@Entity
public class AppdynamicsApplication {

  @Id
  ObjectId objectId;
  String appdynamicsApplication;
  @Reference
  Application application;

  public String getAppdynamicsApplication() {
    return appdynamicsApplication;
  }

  public void setAppdynamicsApplication(String appdynamicsApplication) {
    this.appdynamicsApplication = appdynamicsApplication;
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }
}
