package com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.application;

import com.marcarndt.morsemonkey.services.ApplicationService;
import com.marcarndt.morsemonkey.services.StateService.State;
import com.marcarndt.morsemonkey.services.data.Application;
import com.marcarndt.morsemonkey.telegram.alerts.MorseBot;
import com.marcarndt.morsemonkey.telegram.alerts.command.ConfigureCommand;
import com.marcarndt.morsemonkey.telegram.alerts.command.comandlets.Commandlet;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by arndt on 2017/05/03.
 */
@Stateless
public class UpdateApplication implements Commandlet {

  @Inject
  ApplicationService applicationService;

  @Override
  public boolean canHandleCommand(Message message, State state) {
    return message.getText().equals(ConfigureCommand.editApplication);
  }

  @Override
  public void handleCommand(Message message, State state, List<String> parameters,
      MorseBot morseBot) {
    List<Application> applications = applicationService.getApplications();

    morseBot.sendReplyKeyboardMessage(message, "Select Application",
        applications.stream().map(application -> application.getApplication()).collect(
            Collectors.toList()));


  }

  @Override
  public State getNewState(Message message, State command) {
    return State.CONFIGURE_EDIT_APPLICATION;
  }

  @Override
  public List<String> getNewStateParams(Message message, State state, List<String> parameters) {
    return null;
  }
}
