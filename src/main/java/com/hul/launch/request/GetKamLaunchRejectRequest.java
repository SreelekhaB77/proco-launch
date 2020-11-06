package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class GetKamLaunchRejectRequest {
	private String launchId;
	private String launchRejectRemark;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchRejectRemark() {
		return launchRejectRemark;
	}

	public void setLaunchRejectRemark(String launchRejectRemark) {
		this.launchRejectRemark = launchRejectRemark;
	}
}