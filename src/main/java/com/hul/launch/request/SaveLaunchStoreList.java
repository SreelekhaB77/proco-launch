package com.hul.launch.request;

public class SaveLaunchStoreList {
	private String L1_Chain;
	private String L2_Chain;
	private String mocDate;//Harsha
	private String launchName;//Harsha
	private String StoreFormat;
	private String Cluster;
	private String HUL_OL_Code;
	private String Kam_Remarks;
	
//Harsha Added
	public String getMocDate() {
		return mocDate;
	}

	public void setMocDate(String mocDate) {
		this.mocDate = mocDate;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}
	// Harsha's code end here

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	private String launchId;

	public String getKam_Remarks() {
		return Kam_Remarks;
	}

	public void setKam_Remarks(String kam_Remarks) {
		Kam_Remarks = kam_Remarks;
	}

	public String getL1_Chain() {
		return L1_Chain;
	}

	public void setL1_Chain(String l1_Chain) {
		L1_Chain = l1_Chain;
	}

	public String getL2_Chain() {
		return L2_Chain;
	}

	public void setL2_Chain(String l2_Chain) {
		L2_Chain = l2_Chain;
	}

	public String getStoreFormat() {
		return StoreFormat;
	}

	public void setStoreFormat(String storeFormat) {
		StoreFormat = storeFormat;
	}

	public String getCluster() {
		return Cluster;
	}

	public void setCluster(String cluster) {
		Cluster = cluster;
	}

	public String getHUL_OL_Code() {
		return HUL_OL_Code;
	}

	public void setHUL_OL_Code(String hUL_OL_Code) {
		HUL_OL_Code = hUL_OL_Code;
	}

}
