package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.services.data.AuthDetails;
import com.marcarndt.morsemonkey.services.data.AuthDetails.AuthTypes;
import com.marcarndt.morsemonkey.services.data.ChefFile;
import com.marcarndt.morsemonkey.services.data.Command;
import com.marcarndt.morsemonkey.services.dto.Node;
import com.marcarndt.morsemonkey.services.dto.SCPResponse;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import com.marcarndt.morsemonkey.utils.ExceptionUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/04/10.
 */
@Stateless
public class SSHService {

  @Inject
  MongoService mongoService;

  @Inject
  ChefService chefService;


  private String sshNoKey = "-oStrictHostKeyChecking=no";
  private String noKnownHostFile = "-oUserKnownHostsFile=/dev/null";
  private static Logger LOG = Logger.getLogger(SSHService.class.getName());

  public void addKeyAuth(String name, String user, String keyfile) throws MorseMonkeyException {
    File file = new File(keyfile);
    if (!file.exists()) {
      throw new MorseMonkeyException("Could not find key: " + keyfile);
    }

    AuthDetails authDetails = new AuthDetails(name, user, AuthTypes.KEY, keyfile);
    mongoService.getDatastore().save(authDetails);

  }

  public void addPassword(String name, String user, String password) {
    AuthDetails authDetails = new AuthDetails(name, user, AuthTypes.PASSWORD, password);
    mongoService.getDatastore().save(authDetails);
  }

  public void addCommand(String name, String command) {
    Command commandOnbject = new Command(name, command);
    mongoService.getDatastore().save(commandOnbject);
  }

  public List<String> getCommandNames() {
    return mongoService.getDatastore().createQuery(Command.class).asList().stream()
        .map(command -> command.getDescription()).collect(
            Collectors.toList());
  }

  public void addFile(String description, String filePath) {
    ChefFile chefFile = new ChefFile(description, filePath);
    mongoService.getDatastore().save(chefFile);
  }

  public List<String> getFileDescriptions() {
    return mongoService.getDatastore().createQuery(ChefFile.class).asList().stream()
        .map(chefFile -> chefFile.getDescription()).collect(
            Collectors.toList());
  }

  public ChefFile getFile(String fileDescription) {
    return mongoService.getDatastore().createQuery(ChefFile.class).field("description")
        .equal(fileDescription).get();
  }


  public SCPResponse fetchFile(String nodeName, String sourceFilename) {
    StringBuilder stringBuilder = new StringBuilder();
    LOG.info("Fetching" + sourceFilename + "for " + nodeName);
    stringBuilder.append("Fetching").append(sourceFilename).append("for ").append(nodeName)
        .append("\n");
    String filename = "/tmp/" + nodeName + ".log";

    String sudoCommand = "chmod a+r " + sourceFilename;

    try {
      Node node = chefService.getNode(nodeName);

      if (!performSSH(sudoCommand, node.getIpAddress(), stringBuilder)) {
        return new SCPResponse(false, stringBuilder.toString(), null);
      }

      SCPResponse scpResponse = downloadFile(nodeName, filename, sourceFilename);
      scpResponse.setLog(stringBuilder.append(scpResponse.getLog()).toString());
      return scpResponse;
    } catch (MorseMonkeyException e) {
      stringBuilder.append(e.getMessage()).append("\n");
      return new SCPResponse(false, stringBuilder.toString(), null);
    }
  }

  public SSHResponse runSSHCommand(String nodeName, String command) {
    Command commandObject = mongoService.getDatastore().createQuery(Command.class)
        .field("description").equal(command).get();
    Node node;
    try {
      node = chefService.getNode(nodeName);
    } catch (MorseMonkeyException e) {
      return new SSHResponse(false, e.getMessage());
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Executing command ").append(command).append(" on node ").append(nodeName)
        .append("\n");

    if (performSSH(commandObject.getCommand(), node.getIpAddress(), stringBuilder)) {
      return new SSHResponse(true, stringBuilder.toString());
    }
    return new SSHResponse(false, stringBuilder.toString());
  }

  private boolean performSSH(String command, String ipaddress, StringBuilder stringBuilder) {
    List<AuthDetails> authDetailss = mongoService.getDatastore().createQuery(AuthDetails.class)
        .asList();
    for (AuthDetails authDetails : authDetailss) {
      SSHResponse sshResponse = null;
      switch (authDetails.getAuthTypes()) {
        case KEY:
          sshResponse = keySshCommand(ipaddress, command,
              stringBuilder, authDetails.getDetails(),
              authDetails.getUsername());
          break;
        case PASSWORD:
          sshResponse = passwordSSH(ipaddress, command, stringBuilder,
              authDetails.getDetails(),
              authDetails.getUsername());
          break;
      }
      stringBuilder.append(sshResponse.getLog()).append("\n");
      if (sshResponse.isSuccessful()) {
        return true;
      }
    }
    return false;
  }

  private SSHResponse keySshCommand(String nodeName, String command, StringBuilder stringBuilder,
      String sshkeyfile, String sshUser) {
    String[] keyCommand =
        {"ssh", "-i", sshkeyfile, sshNoKey, noKnownHostFile, sshUser + "@"
            + nodeName, "sudo " + command};
    stringBuilder.append("Executing ssh command using key ").append(Arrays.toString(keyCommand));
    SSHResponse sshResponse = exec(keyCommand);
    stringBuilder.append(sshResponse.getLog());
    return sshResponse;
  }

  private SSHResponse passwordSSH(String nodeName, String command, StringBuilder stringBuilder,
      String password, String user) {
    String[] commandNoKey = {"sshpass", "-p", password, "ssh", sshNoKey, noKnownHostFile,
        user + "@" + nodeName, command};
    stringBuilder.append("Executing ssh command using password: ").append(commandNoKey)
        .append("\n");
    return exec(commandNoKey);
  }


  private SCPResponse downloadFile(String nodeName, String filename, String source) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Downloading ").append(source).append(" from ").append(nodeName)
        .append(" to ").append(filename).append("\n");
    File tmpFile = new File(filename);
    if (tmpFile.exists()) {
      stringBuilder.append("Found file ").append(filename).append(" on local node. Deleting it");
      tmpFile.delete();
    }

    List<AuthDetails> authDetailss = mongoService.getDatastore().createQuery(AuthDetails.class)
        .asList();
    for (AuthDetails authDetails : authDetailss) {
      SSHResponse sshResponse = null;
      switch (authDetails.getAuthTypes()) {
        case KEY:
          sshResponse = keySCP(nodeName, source, filename, authDetails.getDetails(),
              authDetails.getUsername());
          break;
        case PASSWORD:
          sshResponse = passwordSCP(nodeName, source, filename, authDetails.getDetails(),
              authDetails.getUsername());
          break;
      }
      stringBuilder.append(sshResponse.getLog()).append("\n");
      if (sshResponse.isSuccessful()) {
        return new SCPResponse(true, stringBuilder.toString(), filename);
      }
    }
    return new SCPResponse(false, stringBuilder.toString(), null);

  }

  private SSHResponse keySCP(String nodeName, String source, String target, String sshKeyFile,
      String sshKeyUser) {
    String command = "scp -i " + sshKeyFile
        + " " + sshNoKey + " " + noKnownHostFile + " " + sshKeyUser + "@" + nodeName
        + ":" + source + " " + target;
    return exec(command);
  }


  private SSHResponse passwordSCP(String nodeName, String source, String target, String sshPassword,
      String sshUserName) {
    String[] command = {"sshpass", "-p", sshPassword, "scp",
        sshNoKey, noKnownHostFile, sshUserName + "@" + nodeName
        + ":" + source, target};
    return exec(command);
  }

  private SSHResponse exec(String[] command) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      LOG.info("Executing: " + Arrays.toString(command));
      stringBuilder.append("Executing: " + Arrays.toString(command)).append("\n");
      Process process = Runtime.getRuntime().exec(command);
      SSHResponse response = handleProcess(process);
      response.setLog(stringBuilder.append(response.getLog().toString()).toString());
      return response;
    } catch (IOException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
      stringBuilder.append("Exception caught attempting to execute. ").append("\n");
      stringBuilder.append(ExceptionUtils.exceptionStacktraceToString(e));
      return new SSHResponse(false, stringBuilder.toString());
    }
  }

  private SSHResponse exec(String command) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Executing ssh command ").append(command).append("\n");
    try {
      LOG.info("Executing: " + command);
      Process process = Runtime.getRuntime().exec(command);
      SSHResponse response = handleProcess(process);
      response.setLog(stringBuilder.append(response.getLog().toString()).toString());
      return response;
    } catch (IOException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
      stringBuilder.append("Exception caught executing command").append("\n")
          .append(ExceptionUtils.exceptionStacktraceToString(e));
      return new SSHResponse(false, stringBuilder.toString());
    }
  }


  private SSHResponse handleProcess(Process process) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      process.waitFor();
    } catch (InterruptedException e) {
      LOG.log(Level.SEVERE, e.getMessage(), e);
      stringBuilder.append("Caught Exception when executing process. \n");
      stringBuilder.append(ExceptionUtils.exceptionStacktraceToString(e)).append("\n");
      return new SSHResponse(false, stringBuilder.toString());
    }
    String error = processStream(process.getErrorStream());
    String info = processStream(process.getInputStream());
    LOG.info("Error: " + error);
    LOG.info("Output: " + info);
    stringBuilder.append("Error Output").append("\n").append(error).append("\n");
    return new SSHResponse(process.exitValue() == 0, stringBuilder.toString());

  }

  private String processStream(InputStream inputStream) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();

  }

  private boolean verifyHost(String nodeName) {
    return true;
  }


}
