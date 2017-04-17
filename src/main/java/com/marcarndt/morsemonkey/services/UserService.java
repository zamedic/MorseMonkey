package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.data.User;
import com.marcarndt.morsemonkey.services.data.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/13.
 */
@Stateless
public class UserService {

  private static Logger LOG = Logger.getLogger(UserService.class.getName());

  @Inject
  MongoService mongoService;

  public boolean validateUser(Integer id, Role role) {
    if (role.equals(Role.UNAUTHENTICATED)) {
      return true;
    }

    User user = mongoService.getDatastore().createQuery(User.class).field("userId").equal(id).get();
    if (role.equals(Role.USER) && user != null) {
      return true;
    }
    if (user == null) {
      return false;
    }

    UserRole userRole = getUserRole(role);
    return userRole.getUsers().contains(user);
  }

  public boolean adminUserExists() {

    UserRole userRole = getUserRole(Role.USER_ADMIN);
    return userRole != null && !(userRole.getUsers() == null || userRole.getUsers().size() == 0);

  }

  private UserRole getUserRole(Role role) {
    return mongoService.getDatastore().createQuery(UserRole.class).field("role")
        .equal(role.toString()).get();
  }

  public void addUser(int id, String name, Role... roles) {
    User user = mongoService.getDatastore().createQuery(User.class).field("userId").equal(id).get();
    if (user == null) {
      user = new User();
    }
    user.setName(name);
    user.setUserId(id);
    mongoService.getDatastore().save(user);

    for (Role role : roles) {
      addUserToRole(user, role);
    }
  }

  private void addUserToRole(User user, Role role) {
    UserRole userRole = getUserRole(role);
    if (userRole == null) {
      userRole = new UserRole();
    }
    List<User> users = userRole.getUsers();
    if (users == null) {
      users = new ArrayList<>();
    }
    if (!users.contains(user)) {
      users.add(user);
    }
    userRole.setUsers(users);
    mongoService.getDatastore().save(userRole);
  }

  public enum Role {
    USER_ADMIN, COMMAND, USER, UNAUTHENTICATED, ADMINISTRATOR
  }

}
