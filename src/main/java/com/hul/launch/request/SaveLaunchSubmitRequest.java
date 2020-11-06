package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchSubmitRequest {
	private String launchId;
	private int isSampleShared;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public int getIsSampleShared() {
		return isSampleShared;
	}

	public void setIsSampleShared(int isSampleShared) {
		this.isSampleShared = isSampleShared;
	}
}