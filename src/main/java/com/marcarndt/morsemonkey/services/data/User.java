package com.marcarndt.morsemonkey.services.data;

import com.marcarndt.morsemonkey.services.UserService.Role;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/04/17.
 */
@Entity
public class User {
  @Id
  private ObjectId objectId;
  Integer userId;
  String Name;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
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
    return objectId.hashCode();
  }
}
