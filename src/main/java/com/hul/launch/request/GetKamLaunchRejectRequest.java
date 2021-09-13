package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class GetKamLaunchRejectRequest {
	private String launchId;
	private String mocAccount;

	public String getMocAccount() {
		return mocAccount;
	}

	public void setMocAccount(String mocAccount) {
		this.mocAccount = mocAccount;
	}

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