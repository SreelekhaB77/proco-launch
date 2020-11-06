package com.hul.launch.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	private String userId;
	private String password;
	private String emailId;
	private String mobileNumber;
	private int account_Lock;
	private String userRole;
	private String active;
	private String firstName;
	private String lastName;
	private String ROLE;
	private int ROLEID;
	private String STATUS;
	private String accountName;
	private String categoryName;
	private String planTransferFlag;
	private String existingTME;
	private String portalName;
	
	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPlanTransferFlag() {
		return planTransferFlag;
	}
	
	public void setPlanTransferFlag(String planTransferFlag) {
		this.planTransferFlag = planTransferFlag;
	}
	
	public String getExistingTME() {
		return existingTME;
	}
	
	public void setExistingTME(String existingTME) {
		this.existingTME = existingTME;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getROLE() {
		return ROLE;
	}
	
	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}
	
	public int getROLEID() {
		return ROLEID;
	}
	
	public void setROLEID(int rOLEID) {
		ROLEID = rOLEID;
	}
	
	public String getSTATUS() {
		return STATUS;
	}
	
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public int getAccount_Lock() {
		return account_Lock;
	}
	
	public void setAccount_Lock(int account_Lock) {
		this.account_Lock = account_Lock;
	}
	
	public String getUserRole() {
		return userRole;
	}
	
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password
				+ ", emailId=" + emailId + ", mobileNumber=" + mobileNumber
				+ ", account_Lock=" + account_Lock + ", userRole=" + userRole
				+ ", active=" + active + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", ROLE=" + ROLE + ", ROLEID="
				+ ROLEID + ", STATUS=" + STATUS + ", accountName="
				+ accountName + ", categoryName=" + categoryName + "]";
	}
	
}