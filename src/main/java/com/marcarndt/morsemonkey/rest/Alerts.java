package com.marcarndt.morsemonkey.rest;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.rest.dto.ActionInfo;
import com.marcarndt.morsemonkey.rest.dto.AlertMessage;
import com.marcarndt.morsemonkey.rest.dto.EntityInfo;
import com.marcarndt.morsemonkey.rest.dto.EventInfo;
import com.marcarndt.morsemonkey.rest.dto.EventInfo.NotificationSeverity;
import com.marcarndt.morsemonkey.rest.dto.PolicyInfo;
import com.marcarndt.morsemonkey.services.AlertService;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by arndt on 2017/04/02.
 */
@Path("/alert")
@RequestScoped
public class Alerts {

  private static Logger LOG = Logger.getLogger(Alerts.class.getName());

  @Inject
  MorseBot morseBot;

  @Inject
  AlertService alertService;

  @POST
  public Response sendAlertToGroup(String body) {
    try {
      JsonReader jsonReader = Json.createReader(new StringReader(body));
      JsonObject jsonObject = jsonReader.readObject();

      JsonObject policyObject = jsonObject.getJsonObject("policy");
      JsonObject actionObject = jsonObject.getJsonObject("action");
      JsonArray events = jsonObject.getJsonArray("events");

      AlertMessage alertMessage = new AlertMessage();

      PolicyInfo policyInfo = new PolicyInfo();
      ActionInfo actionInfo = new ActionInfo();

      policyInfo
          .setDigestDurationInMins(Integer.valueOf(policyObject.getString("digestDurationInMins")));
      policyInfo.setName(policyObject.getString("name"));

      actionInfo.setTriggerTime(actionObject.getString("triggerTime"));
      actionInfo.setName(actionObject.getString("name"));

      List<EventInfo> eventInfos = new ArrayList<>();

      for (JsonValue eventValue : events) {
        JsonObject eventObject = (JsonObject) eventValue;
        EventInfo eventInfo = new EventInfo();
        eventInfo.setSeverity(NotificationSeverity.valueOf(eventObject.getString("severity")));
        eventInfo.setDisplayName(eventObject.getString("displayName"));
        eventInfo.setEventMessage(eventObject.getString("eventMessage"));
        eventInfo.setApplication(createEntityInfo(eventObject.getJsonObject("application")));
        eventInfo.setTier(createEntityInfo(eventObject.getJsonObject("tier")));
        eventInfo.setNode(createEntityInfo(eventObject.getJsonObject("node")));
        eventInfos.add(eventInfo);
      }

      alertMessage.setActionInfo(actionInfo);
      alertMessage.setEventInfoList(eventInfos);
      alertMessage.setPolicyInfo(policyInfo);

      String generateAlertMessage = null;
      try {
        generateAlertMessage = alertService.generateAlertMessage(alertMessage);
      } catch (MorseMonkeyException e) {
        return Response.serverError().entity(e.getMessage()).build();
      }

      morseBot.sendAlertMessage(generateAlertMessage,true);

      return Response.ok().build();
    } catch (JsonParsingException e) {
      LOG.warning("unable to parse input message " + body);
      return Response.serverError().build();
    }

  }

  private EntityInfo createEntityInfo(JsonObject jsonObject) {
    EntityInfo entityInfo = new EntityInfo();
    entityInfo.setName(jsonObject.getString("name"));
    return entityInfo;
  }
}

