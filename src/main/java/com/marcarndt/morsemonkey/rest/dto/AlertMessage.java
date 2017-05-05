package com.marcarndt.morsemonkey.rest.dto;

import java.util.List;

/**
 * Created by arndt on 2017/05/03.
 */
public class AlertMessage {

  PolicyInfo policyInfo;
  ActionInfo actionInfo;
  List<EventInfo> eventInfoList;

  public PolicyInfo getPolicyInfo() {
    return policyInfo;
  }

  public void setPolicyInfo(PolicyInfo policyInfo) {
    this.policyInfo = policyInfo;
  }

  public ActionInfo getActionInfo() {
    return actionInfo;
  }

  public void setActionInfo(ActionInfo actionInfo) {
    this.actionInfo = actionInfo;
  }

  public List<EventInfo> getEventInfoList() {
    return eventInfoList;
  }

  public void setEventInfoList(List<EventInfo> eventInfoList) {
    this.eventInfoList = eventInfoList;
  }
}
