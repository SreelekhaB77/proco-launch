package com.hul.proco.controller.procoTimeline;

public class UserBean {
	
	private String	userId;
	private String	password;
	private String	emailId;
	private String	mobileNumber;
	private int		account_Lock;
	private String	userRole;
	private String	active;
	private String	firstName;
	private String	lastName;
	private String	lastLogin;
	private String	user_role_id;
	private String	role;
	private int		roleId;
	private String	status;
	private String	accountName;
	private String	categoryName;
	private String	planTransferFlag;
	private String	existingTME;
	private String tmeLock;
	private String kamLock;
	private int tmeLockDays;
	private int kamLockDays;
	private String userLock;
	
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
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(String user_role_id) {
		this.user_role_id = user_role_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	public String getTmeLock() {
		return tmeLock;
	}
	public void setTmeLock(String tmeLock) {
		this.tmeLock = tmeLock;
	}
	public String getKamLock() {
		return kamLock;
	}
	public void setKamLock(String kamLock) {
		this.kamLock = kamLock;
	}
	public int getTmeLockDays() {
		return tmeLockDays;
	}
	public void setTmeLockDays(int tmeLockDays) {
		this.tmeLockDays = tmeLockDays;
	}
	public int getKamLockDays() {
		return kamLockDays;
	}
	public void setKamLockDays(int kamLockDays) {
		this.kamLockDays = kamLockDays;
	}
	public String getUserLock() {
		return userLock;
	}
	public void setUserLock(String userLock) {
		this.userLock = userLock;
	}
	
	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", password=" + password + ", emailId=" + emailId + ", mobileNumber="
				+ mobileNumber + ", account_Lock=" + account_Lock + ", userRole=" + userRole + ", active=" + active
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", lastLogin=" + lastLogin + ", user_role_id="
				+ user_role_id + ", role=" + role + ", roleId=" + roleId + ", status=" + status + ", accountName="
				+ accountName + ", categoryName=" + categoryName + ", planTransferFlag=" + planTransferFlag
				+ ", existingTME=" + existingTME + ", tmeLock=" + tmeLock + ", kamLock=" + kamLock + ", tmeLockDays="
				+ tmeLockDays + ", kamLockDays=" + kamLockDays + ", userLock=" + userLock + "]";
	}
	
	

}
