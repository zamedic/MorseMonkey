package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.services.dto.SCPResponse;
import com.marcarndt.morsemonkey.services.dto.SSHResponse;
import com.marcarndt.morsemonkey.utils.ExceptionUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Created by arndt on 2017/04/10.
 */
@Stateless
public class SCPService {

  private String sshNoKey = "-oStrictHostKeyChecking=no";
  private String noKnownHostFile = "-oUserKnownHostsFile=/dev/null";
  public static final String domain = ".standardbank.co.za";
  private static Logger LOG = Logger.getLogger(SCPService.class.getName());

  @Inject
  @ConfigurationValue("ssh.keyfile")
  String sshKeyFile;

  String sshKeyUser = "devopadm";

  @Inject
  @ConfigurationValue("ssh.username")
  String sshUserName;

  @Inject
  @ConfigurationValue("ssh.password")
  String sshPassword;


  public SCPResponse fetchFile(String nodeName, String sourceFilename) {
    StringBuilder stringBuilder = new StringBuilder();
    LOG.info("Fetching" + sourceFilename + "for " + nodeName);
    stringBuilder.append("Fetching").append(sourceFilename).append("for ").append(nodeName)
        .append("\n");
    String filename = "/tmp/" + nodeName + ".log";
    String sudoCommand = "chmod a+r " + sourceFilename;

    SSHResponse sshResponse = runSSHCommand(nodeName, sudoCommand);
    if (!sshResponse.isSuccessful()) {
      return new SCPResponse(false, stringBuilder.append(sshResponse.getLog()).toString(), null);
    }
    SCPResponse scpResponse = downloadFile(nodeName, filename, sourceFilename);
    scpResponse.setLog(stringBuilder.append(scpResponse.getLog()).toString());
    return scpResponse;
  }

  public SSHResponse runSSHCommand(String nodeName, String command) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Executing command ").append(command).append(" on node ").append(nodeName)
        .append("\n");
    String[] keyCommand =
        {"ssh", "-i", sshKeyFile, sshNoKey, noKnownHostFile, sshKeyUser + "@"
            + nodeName + domain, "sudo " + command};
    stringBuilder.append("Executing ssh command using key ").append(Arrays.toString(keyCommand));
    SSHResponse sshResponse = exec(keyCommand);
    stringBuilder.append(sshResponse.getLog());
    if (!sshResponse.isSuccessful()) {
      stringBuilder.append("Key auth failed. Attempting password Auth\n");
      SSHResponse sshResponsePassword = passwordSSH(nodeName, command, stringBuilder, sshPassword);
      if (!sshResponsePassword.isSuccessful()) {
        stringBuilder.append("Password Auth also failed. I dont know how to access this node");
        return new SSHResponse(false, stringBuilder.toString());
      }
    }
    return new SSHResponse(true, stringBuilder.toString());
  }

  private SSHResponse passwordSSH(String nodeName, String command, StringBuilder stringBuilder,
      String password) {
    String[] commandNoKey = {"sshpass", "-p", password, "ssh", sshNoKey, noKnownHostFile,
        sshUserName + "@" + nodeName + domain, command};
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
    SSHResponse keyResponse = keySSH(nodeName, source, filename);
    if (!keyResponse.isSuccessful()) {
      stringBuilder.append(keyResponse.getLog());
      stringBuilder.append("Key Authentication Failed. Falling back to password auth. \n");
      SSHResponse passwordResponse = passwordSSH(nodeName, source, filename);
      stringBuilder.append(passwordResponse.getLog());
      if (!passwordResponse.isSuccessful()) {
        stringBuilder.append(
            "Failed password authentication. I dont know how to download from this node. \n");
        return new SCPResponse(false, stringBuilder.toString(), null);
      }
    }
    return new SCPResponse(true, stringBuilder.toString(), filename);
  }

  private SSHResponse keySSH(String nodeName, String source, String target) {
    String command = "scp -i " + sshKeyFile
        + " " + sshNoKey + " " + noKnownHostFile + " " + sshKeyUser + "@" + nodeName
        + domain + ":" + source + " " + target;
    return exec(command);
  }


  private SSHResponse passwordSSH(String nodeName, String source, String target) {
    String[] command = {"sshpass", "-p", sshPassword, "scp",
        sshNoKey, noKnownHostFile, sshUserName + "@" + nodeName
        + domain + ":" + source, target};
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
