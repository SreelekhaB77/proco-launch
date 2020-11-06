package com.hul.launch.response;

import java.util.List;

public class StoreDetailsResponse {
	private String launchId;
	private String launchNature;
	private String launchNature2;
	private String regionCluster;
	private String customerData;
	private List<String> launchFormat;
	private List<String> customerStoreFormat;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCustomerData() {
		return customerData;
	}

	public void setCustomerData(String customerData) {
		this.customerData = customerData;
	}

	public String getRegionCluster() {
		return regionCluster;
	}

	public void setRegionCluster(String regionCluster) {
		this.regionCluster = regionCluster;
	}

	public List<String> getCustomerStoreFormat() {
		return customerStoreFormat;
	}

	public void setCustomerStoreFormat(List<String> customerStoreFormat) {
		this.customerStoreFormat = customerStoreFormat;
	}

	public int getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(int storeCount) {
		this.storeCount = storeCount;
	}

	private int storeCount;

	public List<String> getLaunchFormat() {
		return launchFormat;
	}

	public void setLaunchFormat(List<String> launchFormat) {
		this.launchFormat = launchFormat;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchNature() {
		return launchNature;
	}

	public void setLaunchNature(String launchNature) {
		this.launchNature = launchNature;
	}

	public String getLaunchNature2() {
		return launchNature2;
	}

	public void setLaunchNature2(String launchNature2) {
		this.launchNature2 = launchNature2;
	}

}