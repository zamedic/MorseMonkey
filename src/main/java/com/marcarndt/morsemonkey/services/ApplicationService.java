package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.data.AppdynamicsApplication;
import com.marcarndt.morsemonkey.services.data.Application;
import com.marcarndt.morsemonkey.services.data.Cookbook;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class ApplicationService {

  private Logger logger = Logger.getLogger(Application.class.getName());

  @Inject
  MongoService mongoService;

  public void addApplication(String application) {
    Application applicationObject = new Application();
    applicationObject.setApplication(application);
    mongoService.getDatastore().save(applicationObject);
  }

  public List<Application> getApplications() {
    return mongoService.getDatastore().createQuery(Application.class).asList();
  }


  public void addAppDynamicsToApplication(String application, String appDynmamics) {
    Application applicationObject = mongoService.getDatastore().createQuery(Application.class)
        .field("application").equal(application).get();
    List<AppdynamicsApplication> appdynamicsApplications = applicationObject
        .getAppdynamicsApplicationList();
    if (appdynamicsApplications == null) {
      appdynamicsApplications = new ArrayList<>();
    }

    AppdynamicsApplication appdynamicsApplication = new AppdynamicsApplication();
    appdynamicsApplication.setAppdynamicsApplication(appDynmamics);
    appdynamicsApplication.setApplication(applicationObject);

    mongoService.getDatastore().save(appdynamicsApplication);

    appdynamicsApplications.add(appdynamicsApplication);
    applicationObject.setAppdynamicsApplicationList(appdynamicsApplications);

    mongoService.getDatastore().save(applicationObject);
  }

  public void addRecipeToApplication(String application, String description, String recipe) {
    Application applicationObject = mongoService.getDatastore().createQuery(Application.class)
        .field("application").equal(application).get();

    List<Cookbook> cookbooks = applicationObject.getCookbooks();

    if (cookbooks == null) {
      cookbooks = new ArrayList<>();
    }

    Cookbook cookbook = new Cookbook(description, recipe, applicationObject);
    mongoService.getDatastore().save(cookbook);
    cookbooks.add(cookbook);

    applicationObject.setCookbooks(cookbooks);
    mongoService.getDatastore().save(applicationObject);
  }

  public Application getApplication(String name) {
    return mongoService.getDatastore().createQuery(Application.class).field("application")
        .equal(name).get();
  }

  public Cookbook getCookbook(String description) {
    return mongoService.getDatastore().createQuery(Cookbook.class).field("description")
        .equal(description).get();
  }

  public boolean isMonitored(String appplication) {
    boolean found = mongoService.getDatastore().createQuery(AppdynamicsApplication.class)
        .field("appdynamicsApplication").equal(appplication).asList().size() > 0;
    if (!found) {
      logger.info("Ignoring alert for " + appplication);
    }
    return found;
  }
}
