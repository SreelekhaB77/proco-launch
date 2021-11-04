package com.hul.launch.request;

import java.util.List;

public class SaveErroredLaunchSellInList {

	private List<SaveErroredLaunchSellInRequest> listOfSellIns;
	private String launchId;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public List<SaveErroredLaunchSellInRequest> getListOfSellIns() {
		return listOfSellIns;
	}

	public void setListOfSellIns(List<SaveErroredLaunchSellInRequest> listOfSellIns) {
		this.listOfSellIns = listOfSellIns;
	}

	


}
