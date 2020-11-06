package com.hul.launch.model;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class TblLaunchCluster {
	private String clusterId;
	private String launchId;
	private String clusterString;
	private String accountString;
	private String storeFormat;
	private String customerStoreFormat;
	private String totalStoresToLaunch;

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
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