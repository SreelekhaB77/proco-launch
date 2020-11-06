package com.hul.launch.request;

public class MissingDetailsKamInput {
	private String launchId;
	private String missingDetails;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getMissingDetails() {
		return missingDetails;
	}

	public void setMissingDetails(String missingDetails) {
		this.missingDetails = missingDetails;
	}
}