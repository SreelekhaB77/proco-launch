package com.hul.launch.response;

public class KamChangeReqRemarks {
	private String launchName;
	private String launchMoc;
	private String launchAccounts;
	private String reqDate;
	private String changeRequested;
	private String kamRemarks;
	private String cmm;
	private String responseDate;
	private String approvalStatus;
	private String cmmRemarks;
	private String error;
	private String account;
	private String launchReadStatus;

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

	public String getLaunchAccounts() {
		return launchAccounts;
	}

	public void setLaunchAccounts(String launchAccounts) {
		this.launchAccounts = launchAccounts;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getChangeRequested() {
		return changeRequested;
	}

	public void setChangeRequested(String changeRequested) {
		this.changeRequested = changeRequested;
	}

	public String getKamRemarks() {
		return kamRemarks;
	}

	public void setKamRemarks(String kamRemarks) {
		this.kamRemarks = kamRemarks;
	}

	public String getCmm() {
		return cmm;
	}

	public void setCmm(String cmm) {
		this.cmm = cmm;
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

	public String getCmmRemarks() {
		return cmmRemarks;
	}

	public void setCmmRemarks(String cmmRemarks) {
		this.cmmRemarks = cmmRemarks;
	}

	public String getLaunchReadStatus() {
		return launchReadStatus;
	}

	public void setLaunchReadStatus(String launchReadStatus) {
		this.launchReadStatus = launchReadStatus;
	}

}