package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class ClusterRequest {
	private String cluster;
	private String account;
	private String launchId;
	//Sarin changes - Added Q1Sprint Feb2021 - Starts
	private boolean IscustomstoreformatChecked = false;
	
	public boolean isIscustomstoreformatChecked() {
		return IscustomstoreformatChecked;
	}

	public void setIscustomstoreformatChecked(boolean iscustomstoreformatChecked) {
		IscustomstoreformatChecked = iscustomstoreformatChecked;
	}
	//Sarin changes - Added Q1Sprint Feb2021 - Starts

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

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