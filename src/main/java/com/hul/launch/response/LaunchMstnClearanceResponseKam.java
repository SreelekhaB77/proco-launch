package com.hul.launch.response;

public class LaunchMstnClearanceResponseKam {
	private String launchName;
	private String channel;
	private String bpCode;
	private String bpDescription;
	private String moc;
	private String cluster;
	private String depot;
	private String mstnCleared;
	private String finalCldN;
	private String finalCldN1;
	private String finalCldN2;
	private String currentEstimates;
	private String error;
	private String clearanceDate;

	public String getClearanceDate() {
		return clearanceDate;
	}

	public void setClearanceDate(String clearanceDate) {
		this.clearanceDate = clearanceDate;
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

	public String getFinalCldN() {
		return finalCldN;
	}

	public void setFinalCldN(String finalCldN) {
		this.finalCldN = finalCldN;
	}

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

	public String getCurrentEstimates() {
		return currentEstimates;
	}

	public void setCurrentEstimates(String currentEstimates) {
		this.currentEstimates = currentEstimates;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}