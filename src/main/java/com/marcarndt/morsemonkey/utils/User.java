package com.marcarndt.morsemonkey.utils;

/**
 * Created by arndt on 2017/04/13.
 */
public class User {
  String name;
  Long id;

  public User(String name, Long id) {
    this.name = name;
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
