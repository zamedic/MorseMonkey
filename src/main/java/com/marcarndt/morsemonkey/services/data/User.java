package com.marcarndt.morsemonkey.services.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/04/17.
 */
@Entity
public class User {

  @Id
  private ObjectId objectId;
  Integer userId;
  String name;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    return objectId.equals(user.objectId);
  }


  @Override
  public int hashCode() {
    if(objectId == null){
      return userId;
    }
    return objectId.hashCode();
  }
}
