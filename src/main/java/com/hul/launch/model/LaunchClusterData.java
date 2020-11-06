package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public class LaunchClusterData {
	private MultipartFile file;
	private String Cluster;
	private String Account_L1;
	private String Account_L2;
	private String Store_Format;
	private String Customer_Store_Format;
	private String Launch_planned;
	private String account_string;
	private String totalStoresToLaunch;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTotalStoresToLaunch() {
		return totalStoresToLaunch;
	}

	public void setTotalStoresToLaunch(String totalStoresToLaunch) {
		this.totalStoresToLaunch = totalStoresToLaunch;
	}

	public String getAccount_string() {
		return account_string;
	}

	public void setAccount_string(String account_string) {
		this.account_string = account_string;
	}

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

	public String getStore_Format() {
		return Store_Format;
	}

	public void setStore_Format(String store_Format) {
		Store_Format = store_Format;
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
}