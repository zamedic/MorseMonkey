package com.marcarndt.morsemonkey.services.data;

import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by arndt on 2017/04/17.
 */
@Entity
public class UserRole {
  @Id
  private ObjectId objectId;
  private String role;
  @Reference
  private List<User> users;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
