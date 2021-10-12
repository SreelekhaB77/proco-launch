package com.hul.launch.response;

import java.util.Date;

public class LaunchScMstnClearanceResponse {
	private String channel;
	private String launchName;
	private String launchId;
	private String launchMoc;
	private String basepackCode;
	private String basepackDesc;
	private String depot;
	private String mstnCleared;
	private String cluster;
	private String finalCldN;
	private String finalCldN1;
	private String finalCldN2;
	private String account;
	private String remarks; // Added by harsha for Q5

	public String getAccount() {
		return account;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClearanceDate() {
		return clearanceDate;
	}

	public void setClearanceDate(String clearanceDate) {
		this.clearanceDate = clearanceDate;
	}

	private String clearanceDate;

	public String getFinalCldN1() {
		return finalCldN1;
	}

	public void setFinalCldN1(String finalCldN1) {
		this.finalCldN1 = finalCldN1;
	}

	public String getFinalCldN2() {
		return finalCldN2;
	}

	public void setFinalCldN2(String finalCldN2) {
		this.finalCldN2 = finalCldN2;
	}

	private String currentEstimates;
	private Date currentDate;

	public String getFinalCldN() {
		return finalCldN;
	}

	public void setFinalCldN(String finalCldN) {
		this.finalCldN = finalCldN;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
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

	public String getBasepackDesc() {
		return basepackDesc;
	}

	public void setBasepackDesc(String basepackDesc) {
		this.basepackDesc = basepackDesc;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getMstnCleared() {
		return mstnCleared;
	}

	public void setMstnCleared(String mstnCleared) {
		this.mstnCleared = mstnCleared;
	}

	public String getCurrentEstimates() {
		return currentEstimates;
	}

	public void setCurrentEstimates(String currentEstimates) {
		this.currentEstimates = currentEstimates;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

}