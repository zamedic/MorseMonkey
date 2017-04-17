package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.data.Recipe;
import com.marcarndt.morsemonkey.services.dto.Node;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/16.
 */
@Stateless
public class RecipeService {

  @Inject
  MongoService mongoService;
  @Inject
  ChefService chefService;

  Map<String, String> recipes;

  @PostConstruct
  public void setup() {
    recipes = mongoService.getDatastore().createQuery(Recipe.class).asList().stream()
        .collect(Collectors.toMap(Recipe::getUserFriendlyName, Recipe::getRecipeName));
  }

  public List<String> getRecipeDescriptions() {
    return recipes.keySet().stream().sorted().collect(Collectors.toList());
  }

  public String getRecipeName(String description) {
    return recipes.get(description);
  }

  public List<Node> getNodesForRecipe(String description) {
    List<Node> nodes = chefService.recipeSearch(recipes.get(description));
    return nodes;
  }

}
