package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.HTTPEndpoint;
import com.marcarndt.morsemonkey.services.data.VCMCommand;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/20.
 */
@Stateless
public class VCMService {

  @Inject
  MongoService mongoService;

  @Inject
  HttpChecker httpChecker;

  public void addVcmCommand(String description, String command) throws MorseMonkeyException {
    VCMCommand vcmCommand = new VCMCommand(description, command);
    RunVcmCommand(vcmCommand);
    mongoService.getDatastore().save(vcmCommand);
  }

  public String RunVcmCommand(VCMCommand vcmCommand) throws MorseMonkeyException {
    HTTPEndpoint endpoint = new HTTPEndpoint("VCM",
        "http://rc14.sbic.co.za:3650/vbapps/VBTelegram/v1/",
        "{\"telegram_details\":{\"command\": \"" + vcmCommand.getCommand() + "\"}}", "POST");
    return httpChecker.checkNode(endpoint);
  }

  public List<String> getCommands() {
    return mongoService.getDatastore().createQuery(VCMCommand.class).asList().stream()
        .map(vcmCommand -> vcmCommand.getDescription()).collect(
            Collectors.toList());
  }

  public String execute(String command) throws MorseMonkeyException {
    VCMCommand vcmCommand = getVcmCommand(command);
    return RunVcmCommand(vcmCommand);

  }

  private VCMCommand getVcmCommand(String command) {
    return mongoService.getDatastore().createQuery(VCMCommand.class).field("description").equal(command).get();
  }

  public void delete(String command) {
    VCMCommand vcmCommand = getVcmCommand(command);
    mongoService.getDatastore().delete(vcmCommand);
  }
}
