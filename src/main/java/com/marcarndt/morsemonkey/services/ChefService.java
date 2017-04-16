package com.marcarndt.morsemonkey.services;


import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
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
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/10.
 */
@Singleton
public class ChefService {
  static Logger LOG = Logger.getLogger(ChefService.class.getName());

  ChefApiClient chefClient;

  @Inject
  @ConfigurationValue("chef.key")
  String chefKey;

  @PostConstruct
  public void setup() {
    chefClient = new ChefApiClient("bender", chefKey,
        "https://pchfsvr1v.standardbank.co.za");
  }

  public String getSearchURL() {
    ApiMethod response = chefClient.get("/organizations/chopchop/search").execute();
    return response.getResponseBodyAsString();
  }

  public List<Node> recipeSearch(String recipe) {
    LOG.info("Fetching Nodes from chef.");
    ApiMethod response = chefClient.get("/organizations/chopchop/search/node?q=recipe:" + recipe)
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
    ApiMethod response = chefClient.get("/organizations/chopchop/nodes/" + node).execute();
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

  public static void main(String[] args) {
    ChefService chefService = new ChefService();
    chefService.chefKey = "D:/workspace/sbsa/morsemonkey_chef.key";
    chefService.setup();
    Node node = null;
    try {
      node = chefService.getNode("dsbis01v");
    } catch (MorseMonkeyException e) {
      e.printStackTrace();
    }
    System.out.println(node.getName() + " - " + node.getEnvironment() + " - " + node.getPlatform());
    System.out.println(chefService.getSearchURL());
    System.out.println(chefService.recipeSearch("sbis-web-cookbook"));

  }

  public enum Recipe {
    IBF("IB", "ibf-app"),
    SBISWEB("SBIS Web", "sbis-web-cookbook"),
    SBISAPP("SBIS App", "sbis-app-cookbook"),
    HAPROXY("HA Proxy", "virtual-channels-ha-proxy-cookbook"),
    MORSE_MONKEY("Morse Monkey", "sbsa_morse_monkey_cookbook");

    String recipe;
    String description;

    Recipe(String description, String recipe) {
      this.recipe = recipe;
      this.description = description;
    }

    public String getRecipe() {
      return recipe;
    }

    public String getDescription() {
      return description;
    }
  }


}
