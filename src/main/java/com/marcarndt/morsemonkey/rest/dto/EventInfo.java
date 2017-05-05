package com.marcarndt.morsemonkey.rest.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by arndt on 2017/05/03.
 */
public class EventInfo {

  String entiytyType;
  long id;
  String guid;
  String eventTypeKey;
  Date eventTime;
  String displayName;
  String summaryMessage;
  String eventMessage;
  EntityInfo application;
  EntityInfo tier;
  EntityInfo node;
  List<EntityInfo> affectedEntities;
  boolean healthRuleEvent;
  EntityInfo healthRule; // * Only defined when healthRuleEvent == true
  EntityInfo incident;// * Only defined when healthRuleEvent == true
  boolean healthRuleViolationEvent;
  NotificationSeverity severity;
  ImageInfo severityImage;
  boolean btPerformanceEvent;// * true when eventType matches one of the BT performance event types
  String deepLink;

  public enum NotificationSeverity { INFO, WARN, ERROR }

  public String getEntiytyType() {
    return entiytyType;
  }

  public void setEntiytyType(String entiytyType) {
    this.entiytyType = entiytyType;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String guid) {
    this.guid = guid;
  }

  public String getEventTypeKey() {
    return eventTypeKey;
  }

  public void setEventTypeKey(String eventTypeKey) {
    this.eventTypeKey = eventTypeKey;
  }

  public Date getEventTime() {
    return eventTime;
  }

  public void setEventTime(Date eventTime) {
    this.eventTime = eventTime;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getSummaryMessage() {
    return summaryMessage;
  }

  public void setSummaryMessage(String summaryMessage) {
    this.summaryMessage = summaryMessage;
  }

  public String getEventMessage() {
    return eventMessage;
  }

  public void setEventMessage(String eventMessage) {
    this.eventMessage = eventMessage;
  }

  public EntityInfo getApplication() {
    return application;
  }

  public void setApplication(EntityInfo application) {
    this.application = application;
  }

  public EntityInfo getTier() {
    return tier;
  }

  public void setTier(EntityInfo tier) {
    this.tier = tier;
  }

  public EntityInfo getNode() {
    return node;
  }

  public void setNode(EntityInfo node) {
    this.node = node;
  }

  public List<EntityInfo> getAffectedEntities() {
    return affectedEntities;
  }

  public void setAffectedEntities(
      List<EntityInfo> affectedEntities) {
    this.affectedEntities = affectedEntities;
  }

  public boolean isHealthRuleEvent() {
    return healthRuleEvent;
  }

  public void setHealthRuleEvent(boolean healthRuleEvent) {
    this.healthRuleEvent = healthRuleEvent;
  }

  public EntityInfo getHealthRule() {
    return healthRule;
  }

  public void setHealthRule(EntityInfo healthRule) {
    this.healthRule = healthRule;
  }

  public EntityInfo getIncident() {
    return incident;
  }

  public void setIncident(EntityInfo incident) {
    this.incident = incident;
  }

  public boolean isHealthRuleViolationEvent() {
    return healthRuleViolationEvent;
  }

  public void setHealthRuleViolationEvent(boolean healthRuleViolationEvent) {
    this.healthRuleViolationEvent = healthRuleViolationEvent;
  }

  public NotificationSeverity getSeverity() {
    return severity;
  }

  public void setSeverity(NotificationSeverity severity) {
    this.severity = severity;
  }

  public ImageInfo getSeverityImage() {
    return severityImage;
  }

  public void setSeverityImage(ImageInfo severityImage) {
    this.severityImage = severityImage;
  }

  public boolean isBtPerformanceEvent() {
    return btPerformanceEvent;
  }

  public void setBtPerformanceEvent(boolean btPerformanceEvent) {
    this.btPerformanceEvent = btPerformanceEvent;
  }

  public String getDeepLink() {
    return deepLink;
  }

  public void setDeepLink(String deepLink) {
    this.deepLink = deepLink;
  }
}
