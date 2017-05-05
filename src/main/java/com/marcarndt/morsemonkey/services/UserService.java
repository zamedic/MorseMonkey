package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.User;
import com.marcarndt.morsemonkey.services.data.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

  /**
   * Checks if the user has the role.
   *
   * @param id Telegram sser ID
   * @param role UNAUTHENTICATED - will allow all users USER - Will ensure the user exists on the DB
   * All other roles will be validated
   * @return true if the user has the role
   */
  public boolean validateUser(Integer id, Role role) throws MorseMonkeyException {
    if (role.equals(Role.UNAUTHENTICATED)) {
      return true;
    }

    User user = getUser(id);
    if (user == null) {
      throw new MorseMonkeyException("You are not known to me human. Use /register to make me aware of your presence. ");
    }
    if (role.equals(Role.USER)) {
      return true;
    }

    UserRole userRole = getUserRole(role);
    if (userRole == null) {
      return false;
    }
    return userRole.getUsers().contains(user);
  }

  public boolean adminUserExists() {
    UserRole userRole = getUserRole(Role.ADMINISTRATOR);
    return userRole != null && !(userRole.getUsers() == null || userRole.getUsers().size() == 0);

  }

  private UserRole getUserRole(Role role) {
    return mongoService.getDatastore().createQuery(UserRole.class).field("role")
        .equal(role.toString()).get();
  }

  /**
   * Adds a user, with optional roles.
   *
   * @param id ID for the user
   * @param name Name for the user
   * @param roles List of roes for the user
   */
  public void addUser(int id, String name, String lastname, Role... roles) {
    User user = getUser(id);
    if (user == null) {
      user = new User();
    }
    if (lastname != null) {
      name = name + " " + lastname;
    }
    user.setName(name);
    user.setUserId(id);
    mongoService.getDatastore().save(user);

    for (Role role : roles) {
      addUserToRole(user, role);
    }
  }

  private User getUser(int id) {
    return mongoService.getDatastore().createQuery(User.class).field("userId").equal(id).get();
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
    userRole.setRole(role.toString());
    mongoService.getDatastore().save(userRole);
  }

  public List<Role> getUserRoles(Integer id) {
    return mongoService.getDatastore().createQuery(UserRole.class).asList().stream()
        .filter(userRole ->
            userRole.getUsers().stream().filter(user -> user.getUserId().equals(id)).count() > 0)
        .filter(userRole -> userRole.getRole() != null)
        .map(userRole -> Role.valueOf(userRole.getRole())).collect(
            Collectors.toList());
  }

  public List<User> getAllUsers() {
    return mongoService.getDatastore().createQuery(User.class).asList();
  }

  public User getUserByName(String name) throws MorseMonkeyException {
    User user = mongoService.getDatastore().createQuery(User.class).field("name").equal(name).get();
    if (user == null) {
      throw new MorseMonkeyException("Unable to find user " + name);
    }
    return user;
  }

  public void addUserToRole(String name, String roleName) throws MorseMonkeyException {
    User user = getUserByName(name);
    Role role = Role.valueOf(roleName);

    addUserToRole(user, role);
  }

  /**
   * Remove a user from a role
   *
   * @param name First name of the user
   */
  public void removeUserFromRole(String name, String roleName) throws MorseMonkeyException {
    User user = getUserByName(name);
    Role role = Role.valueOf(roleName);

    UserRole userRole = getUserRole(role);
    userRole.getUsers().remove(user);

    mongoService.getDatastore().save(userRole);
  }

  public enum Role {
     USER, UNAUTHENTICATED, VCM, ADMINISTRATOR, TRUSTED
  }

}
