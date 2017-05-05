package com.marcarndt.morsemonkey.services;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/16.
 */
@Singleton
public class MongoService {

  @Inject
  @ConfigurationValue("mongo.address")
  String mongoAddress;

  @Inject
  @ConfigurationValue("mongo.db")
  String mongoDb;

  private Datastore datastore;

  @PostConstruct
  public void connect() {
    Morphia morphia = new Morphia();
    morphia.mapPackage("com.marcarndt.morsemonkey.service.data");

    datastore  = morphia.createDatastore(new MongoClient(mongoAddress), mongoDb);
    datastore.ensureIndexes();

  }

  public Datastore getDatastore() {
    return datastore;
  }
}
