package com.marcarndt.morsemonkey.telegram.alerts.command;

import java.util.HashMap;
import javax.ejb.Singleton;

/**
 * Created by arndt on 2017/04/13.
 */
@Singleton
public class UserCommandService {

  HashMap<Integer, Command> userCommand = new HashMap<>();

  public enum Command  { NODE_LIST }

  public void addCommand(int userId, Command command){
    userCommand.put(userId,command);
  }

  public boolean hasCommmand(int userId){
    return userCommand.containsKey(userId);
  }

  public Command getCommand(int userId){
    return userCommand.remove(userId);
  }


}
