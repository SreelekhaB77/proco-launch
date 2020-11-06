package com.hul.launch.response;

import java.util.List;

import com.hul.launch.model.LaunchSellIn;

public class LaunchSellInEditReponse {
	private List<LaunchSellIn> launchSellInEditResponse;

	private List<String> basepacksCreated;

	public List<LaunchSellIn> getLaunchSellInEditResponse() {
		return launchSellInEditResponse;
	}

	public void setLaunchSellInEditResponse(List<LaunchSellIn> launchSellInEditResponse) {
		this.launchSellInEditResponse = launchSellInEditResponse;
	}

	public List<String> getBasepacksCreated() {
		return basepacksCreated;
	}

	public void setBasepacksCreated(List<String> basepacksCreated) {
		this.basepacksCreated = basepacksCreated;
	}

}
