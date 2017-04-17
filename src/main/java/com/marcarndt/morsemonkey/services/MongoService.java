package com.marcarndt.morsemonkey.services;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by arndt on 2017/04/16.
 */
@Singleton
public class MongoService {

  private Datastore datastore;

  @PostConstruct
  public void connect() {
    Morphia morphia = new Morphia();
    morphia.mapPackage("com.marcarndt.morsemonkey.service.data");

    datastore  = morphia.createDatastore(new MongoClient(), "morse_monkey");
    datastore.ensureIndexes();

  }

  public Datastore getDatastore() {
    return datastore;
  }
}
