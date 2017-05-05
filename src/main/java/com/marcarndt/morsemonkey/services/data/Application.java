package com.marcarndt.morsemonkey.services.data;

import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/05/03.
 */
@Entity
public class Application {
  @Id
  private ObjectId objectId;
  String application;
  @Reference
  List<AppdynamicsApplication> appdynamicsApplicationList;
  @Reference
  List<Cookbook> cookbooks;

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public List<AppdynamicsApplication> getAppdynamicsApplicationList() {
    return appdynamicsApplicationList;
  }

  public void setAppdynamicsApplicationList(
      List<AppdynamicsApplication> appdynamicsApplicationList) {
    this.appdynamicsApplicationList = appdynamicsApplicationList;
  }

  public List<Cookbook> getCookbooks() {
    return cookbooks;
  }

  public void setCookbooks(List<Cookbook> cookbooks) {
    this.cookbooks = cookbooks;
  }
}
