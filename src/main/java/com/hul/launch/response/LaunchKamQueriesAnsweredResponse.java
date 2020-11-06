package com.hul.launch.response;

public class LaunchKamQueriesAnsweredResponse {
	private String launchName;
	private String launchMoc;
	private String launchAccount;
	private String Name;
	private String reqDate;
	private String changesRequired;
	private String kamRemarks;
	private String responseDate;
	private String approvalStatus;
	private String tmeRemarks;
	private String error;
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

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

	public String getLaunchMoc() {
		return launchMoc;
	}

	public void setLaunchMoc(String launchMoc) {
		this.launchMoc = launchMoc;
	}

	public String getLaunchAccount() {
		return launchAccount;
	}

	public void setLaunchAccount(String launchAccount) {
		this.launchAccount = launchAccount;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
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

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getTmeRemarks() {
		return tmeRemarks;
	}

	public void setTmeRemarks(String tmeRemarks) {
		this.tmeRemarks = tmeRemarks;
	}

}