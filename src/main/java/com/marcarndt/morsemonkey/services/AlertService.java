package com.marcarndt.morsemonkey.services;

import com.marcarndt.morsemonkey.exception.MorseMonkeyException;
import com.marcarndt.morsemonkey.rest.dto.AlertMessage;
import com.marcarndt.morsemonkey.rest.dto.EventInfo;
import com.vdurmont.emoji.EmojiManager;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/05/03.
 */

@Stateless
public class AlertService {

  @Inject
  ApplicationService applicationService;

  public String generateAlertMessage(AlertMessage alertMessage) throws MorseMonkeyException {

    List<EventInfo> filteredEvents = alertMessage.getEventInfoList().stream()
        .filter(eventInfo -> applicationService.isMonitored(eventInfo.getApplication().getName()))
        .collect(
            Collectors.toList());

    if (filteredEvents.size() == 0) {
      throw new MorseMonkeyException("No events found that are enabled for monitoring");
    }

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<b>New Alerts received from App Dynamics</b>\n");
    stringBuilder.append("Triggered: ").append(alertMessage.getActionInfo().getTriggerTime())
        .append("\n");
    stringBuilder.append("\n");
    for (EventInfo eventInfo : filteredEvents) {
      if("INFO".equals(eventInfo.getSeverity())){
        stringBuilder.append(EmojiManager.getForAlias("green_book").getUnicode());
      } else if ("WARN".equals(eventInfo.getSeverity())){
        stringBuilder.append(EmojiManager.getForAlias("warning").getUnicode());
      } else if ("ERROR".equals(eventInfo.getSeverity())){
        stringBuilder.append(EmojiManager.getForAlias("skull").getUnicode());
      } else {
        stringBuilder.append(EmojiManager.getForAlias("grey_question").getUnicode());
      }
      stringBuilder.append(eventInfo.getSeverity()).append(" ")
          .append(eventInfo.getApplication().getName())
          .append(" ").append(eventInfo.getTier().getName()).append(" ")
          .append(eventInfo.getNode().getName())
          .append("\n");
      stringBuilder.append(eventInfo.getEventMessage().replaceAll("<br>", "\n"));
      stringBuilder.append("\n").append("\n");
    }

    return stringBuilder.toString();
  }

}
