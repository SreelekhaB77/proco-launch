package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchClustersRequest {
	private String clusterString;
	private String accountString;
	private String storeFormat;
	private String customerStoreFormat;
	private String totalStoresToLaunch;
	private String launchId;
	private String launchPlanned;

	public String getLaunchPlanned() {
		return launchPlanned;
	}

	public void setLaunchPlanned(String launchPlanned) {
		this.launchPlanned = launchPlanned;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getClusterString() {
		return clusterString;
	}

	public void setClusterString(String clusterString) {
		this.clusterString = clusterString;
	}

	public String getAccountString() {
		return accountString;
	}

	public void setAccountString(String accountString) {
		this.accountString = accountString;
	}

	public String getStoreFormat() {
		return storeFormat;
	}

	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getCustomerStoreFormat() {
		return customerStoreFormat;
	}

	public void setCustomerStoreFormat(String customerStoreFormat) {
		this.customerStoreFormat = customerStoreFormat;
	}

	public String getTotalStoresToLaunch() {
		return totalStoresToLaunch;
	}

	public void setTotalStoresToLaunch(String totalStoresToLaunch) {
		this.totalStoresToLaunch = totalStoresToLaunch;
	}
}