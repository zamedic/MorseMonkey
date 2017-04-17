package com.marcarndt.morsemonkey.services;


import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.ChefDetails;
import com.marcarndt.morsemonkey.services.dto.Node;
import edu.tongji.wang.chefapi.ChefApiClient;
import edu.tongji.wang.chefapi.method.ApiMethod;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.mongodb.morphia.query.Query;

/**
 * Created by arndt on 2017/04/10.
 */
@Singleton
public class ChefService {

  static Logger LOG = Logger.getLogger(ChefService.class.getName());

  @Inject
  MongoService mongoService;

  ChefApiClient chefClient;
  ChefDetails chefDetails;

  @PostConstruct
  public void setup() {
    Query<ChefDetails> query = mongoService.getDatastore().createQuery(ChefDetails.class);
    if (query.count() == 0) {
      chefDetails = new ChefDetails();
    } else {
      chefDetails = query.get();
      initializeClient();
    }
  }

  private void initializeClient() {
    chefClient = new ChefApiClient(chefDetails.getUserName(), chefDetails.getKeyPath(),
        chefDetails.getServerUrl());
  }

  public String getSearchURL() {
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search").execute();
    return response.getResponseBodyAsString();
  }

  public List<Node> recipeSearch(String recipe) {
    LOG.info("Fetching Nodes from chef.");
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/search/node?q=recipe:" + recipe)
        .execute();
    JsonReader reader = Json.createReader(new StringReader(response.getResponseBodyAsString()));
    JsonObject rootObject = reader.readObject();
    JsonArray array = rootObject.getJsonArray("rows");
    ArrayList<Node> nodes = new ArrayList<>();
    for (JsonValue value : array) {
      JsonObject jsonStructure = (JsonObject) value;
      nodes.add(getNode(jsonStructure));
    }
    return nodes;

  }

  public Node getNode(String node) throws MorseMonkeyException {
    ApiMethod response = chefClient
        .get("/organizations/" + chefDetails.getOrginisation() + "/nodes/" + node)
        .execute();
    if (response.getReturnCode() != 200) {
      throw new MorseMonkeyException("Could not find node: " + node);
    }
    JsonReader reader = Json.createReader(new StringReader(response.getResponseBodyAsString()));
    JsonObject rootObject = reader.readObject();
    return getNode(rootObject);

  }

  private Node getNode(JsonObject rootObject) {
    String name = rootObject.getString("name");
    String env = rootObject.getString("chef_environment");
    String platform = rootObject.getJsonObject("automatic").getString("platform");

    Node nodeObject = new Node(name, env, platform);

    return nodeObject;
  }
}
