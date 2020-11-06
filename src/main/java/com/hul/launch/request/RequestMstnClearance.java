package com.hul.launch.request;

public class RequestMstnClearance {
	private String launchId;
	private String launchMoc;
	private String basepackCode;
	private String basepackDescription;
	private String depot;
	private String cluster;
	private String mstnCleared;
	private String finalCldForN;
	private String finalCldForN1;
	private String finalCldForN2;
	private String currentEstimates;
	private String clearanceDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	private String account;

	public String getClearanceDate() {
		return clearanceDate;
	}

	public void setClearanceDate(String clearanceDate) {
		this.clearanceDate = clearanceDate;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchMoc() {
		return launchMoc;
	}

	public void setLaunchMoc(String launchMoc) {
		this.launchMoc = launchMoc;
	}

	public String getBasepackCode() {
		return basepackCode;
	}

	public void setBasepackCode(String basepackCode) {
		this.basepackCode = basepackCode;
	}

	public String getBasepackDescription() {
		return basepackDescription;
	}

	public void setBasepackDescription(String basepackDescription) {
		this.basepackDescription = basepackDescription;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getMstnCleared() {
		return mstnCleared;
	}

	public void setMstnCleared(String mstnCleared) {
		this.mstnCleared = mstnCleared;
	}

	public String getFinalCldForN() {
		return finalCldForN;
	}

	public void setFinalCldForN(String finalCldForN) {
		this.finalCldForN = finalCldForN;
	}

	public String getFinalCldForN1() {
		return finalCldForN1;
	}

	public void setFinalCldForN1(String finalCldForN1) {
		this.finalCldForN1 = finalCldForN1;
	}

	public String getFinalCldForN2() {
		return finalCldForN2;
	}

	public void setFinalCldForN2(String finalCldForN2) {
		this.finalCldForN2 = finalCldForN2;
	}

	public String getCurrentEstimates() {
		return currentEstimates;
	}

	public void setCurrentEstimates(String currentEstimates) {
		this.currentEstimates = currentEstimates;
	}
}