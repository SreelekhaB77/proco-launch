package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class StoreCountRequest {
	private String storeFormat;
	private String custStoreFormat;
	private String accountList;
	private String cluster;
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

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getAccountList() {
		return accountList;
	}

	public void setAccountList(String accountList) {
		this.accountList = accountList;
	}

	public String getStoreFormat() {
		return storeFormat;
	}

	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getCustStoreFormat() {
		return custStoreFormat;
	}

	public void setCustStoreFormat(String custStoreFormat) {
		this.custStoreFormat = custStoreFormat;
	}
}