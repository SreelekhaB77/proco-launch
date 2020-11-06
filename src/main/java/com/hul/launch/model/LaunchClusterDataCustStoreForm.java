package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

public class LaunchClusterDataCustStoreForm {
	private MultipartFile file;
	private String Cluster;
	private String Account_L1;
	private String Account_L2;
	private String Customer_Store_Format;
	private String Launch_planned;
	private String error;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getCluster() {
		return Cluster;
	}

	public void setCluster(String cluster) {
		Cluster = cluster;
	}

	public String getAccount_L1() {
		return Account_L1;
	}

	public void setAccount_L1(String account_L1) {
		Account_L1 = account_L1;
	}

	public String getAccount_L2() {
		return Account_L2;
	}

	public void setAccount_L2(String account_L2) {
		Account_L2 = account_L2;
	}

	public String getCustomer_Store_Format() {
		return Customer_Store_Format;
	}

	public void setCustomer_Store_Format(String customer_Store_Format) {
		Customer_Store_Format = customer_Store_Format;
	}

	public String getLaunch_planned() {
		return Launch_planned;
	}

	public void setLaunch_planned(String launch_planned) {
		Launch_planned = launch_planned;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}