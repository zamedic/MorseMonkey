package com.marcarndt.morsemonkey.rest.dto;

/**
 * Created by arndt on 2017/05/03.
 */
public class EntityInfo {
  String entityType;;
  String entityTypeDisplayName;
  long id;
  String name;

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public String getEntityTypeDisplayName() {
    return entityTypeDisplayName;
  }

  public void setEntityTypeDisplayName(String entityTypeDisplayName) {
    this.entityTypeDisplayName = entityTypeDisplayName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
