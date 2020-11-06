package com.hul.launch.response;

public class LaunchVisiPlanResponse {
	private String account;
	private String format;
	private String storeAvailable;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	private String error;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getStoreAvailable() {
		return storeAvailable;
	}

	public void setStoreAvailable(String storeAvailable) {
		this.storeAvailable = storeAvailable;
	}
}