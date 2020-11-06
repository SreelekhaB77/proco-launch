package com.hul.launch.response;

import java.util.List;

public class LaunchSellInReponse {
	private List<SellInResponse> sellInRecords;

	private List<String> basepacksCreated;

	public List<String> getBasepacksCreated() {
		return basepacksCreated;
	}

	public void setBasepacksCreated(List<String> basepacksCreated) {
		this.basepacksCreated = basepacksCreated;
	}

	public List<SellInResponse> getSellInRecords() {
		return sellInRecords;
	}

	public void setSellInRecords(List<SellInResponse> sellInRecords) {
		this.sellInRecords = sellInRecords;
	}

}