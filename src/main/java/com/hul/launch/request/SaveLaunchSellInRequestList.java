package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchSellInRequestList {
	private List<SaveLaunchSellInRequest> listOfSellIns;
	private String launchId;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public List<SaveLaunchSellInRequest> getListOfSellIns() {
		return listOfSellIns;
	}

	public void setListOfSellIns(List<SaveLaunchSellInRequest> listOfSellIns) {
		this.listOfSellIns = listOfSellIns;
	}
}