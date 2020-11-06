package com.hul.launch.response;

public class LaunchKamInputsResponse {
	private String reqId;
	private String launchId;
	private String launchName;
	private String launchMoc;
	private String account;
	private String name;
	private String changesRequired;
	private String kamRemarks;
	private String requestDate;
	private String error;

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	private String requestStatus;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getLaunchMoc() {
		return launchMoc;
	}

	public void setLaunchMoc(String launchMoc) {
		this.launchMoc = launchMoc;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChangesRequired() {
		return changesRequired;
	}

	public void setChangesRequired(String changesRequired) {
		this.changesRequired = changesRequired;
	}

	public String getKamRemarks() {
		return kamRemarks;
	}

	public void setKamRemarks(String kamRemarks) {
		this.kamRemarks = kamRemarks;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

}