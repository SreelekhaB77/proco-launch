package com.hul.launch.response;

import java.util.Date;

public class LaunchMstnClearanceResponse {
	private String launchName;
	private String channel;
	private String bpCode;
	private String bpDescription;
	private String moc;
	private String cluster;
	private String account;
	private String depot;
	private String mstnCleared;
	private String requiredEstimates;
	private String currentEstimates;
	private String error;
	private Date clearanceData;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBpCode() {
		return bpCode;
	}

	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}

	public String getBpDescription() {
		return bpDescription;
	}

	public void setBpDescription(String bpDescription) {
		this.bpDescription = bpDescription;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getRequiredEstimates() {
		return requiredEstimates;
	}

	public void setRequiredEstimates(String requiredEstimates) {
		this.requiredEstimates = requiredEstimates;
	}

	public String getCurrentEstimates() {
		return currentEstimates;
	}

	public void setCurrentEstimates(String currentEstimates) {
		this.currentEstimates = currentEstimates;
	}

	public Date getClearanceData() {
		return clearanceData;
	}

	public void setClearanceData(Date clearanceData) {
		this.clearanceData = clearanceData;
	}

}