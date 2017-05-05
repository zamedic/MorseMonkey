package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.vdurmont.emoji.EmojiManager;
import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by arndt on 2017/04/02.
 */
@Path("/delivery")
@RequestScoped
public class Delivery {


  @Inject
  MorseBot morseBot;

  private final Logger LOG = Logger.getLogger(Delivery.class.getName());

  @POST
  public void deliveryUpdate(String body) {
    LOG.info(body);

    JsonReader jsonReader = Json.createReader(new StringReader(body));
    JsonObject jsonObject = jsonReader.readObject();
    String text = jsonObject.getString("text");
    StringTokenizer stringTokenizer = new StringTokenizer(text, "\n");
    String line1 = stringTokenizer.nextToken();
    String message = stringTokenizer.nextToken();
    line1 = line1.replace("<", "");
    line1 = line1.replace(">", "");
    StringTokenizer tokenizerPipe = new StringTokenizer(line1, "|");
    String url = tokenizerPipe.nextToken();
    String description = tokenizerPipe.nextToken();

    JsonArray jsonArray = jsonObject.getJsonArray("attachments");
    JsonObject arrayValue = (JsonObject) jsonArray.get(0);

    JsonArray fields = arrayValue.getJsonArray("fields");

    StringBuilder fieldSting = new StringBuilder();

    String project = "";

    for (JsonValue field : fields) {
      JsonObject jsonField = (JsonObject) field;
      String title = jsonField.getString("title");
      String value = jsonField.getString("value");

      if ("Project:".equals(title)) {
        project = value;
      } else {
        fieldSting.append(title).append(value).append("\n");
      }
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(EmojiManager.getForAlias("truck").getUnicode());
    stringBuilder.append("<b>Chef Delivery</b>\n").append("Project: " + project).append("\n")
        .append(message).append("\n").append("<a href=\"").append(url).append("\">")
        .append(description).append("</a>").append("\n").append(fieldSting.toString());

    morseBot.sendAlertMessage(stringBuilder.toString(),true);

  }


}
