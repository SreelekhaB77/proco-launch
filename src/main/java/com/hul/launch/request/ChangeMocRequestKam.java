package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class ChangeMocRequestKam {
	private String launchId;
	private String mocToChange;
	private String mocChangeRemark;
	private String mocAccount;
	
	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getMocToChange() {
		return mocToChange;
	}

	public void setMocToChange(String mocToChange) {
		this.mocToChange = mocToChange;
	}

	public String getMocChangeRemark() {
		return mocChangeRemark;
	}

	public void setMocChangeRemark(String mocChangeRemark) {
		this.mocChangeRemark = mocChangeRemark;
	}

	public String getMocAccount() {
		return mocAccount;
	}

	public void setMocAccount(String mocAccount) {
		this.mocAccount = mocAccount;
	}
	
	
}