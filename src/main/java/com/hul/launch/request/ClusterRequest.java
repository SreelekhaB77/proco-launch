package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class ClusterRequest {
	private String cluster;
	private String account;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	private String launchId;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
}