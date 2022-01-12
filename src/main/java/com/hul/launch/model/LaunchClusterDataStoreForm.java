package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

public class LaunchClusterDataStoreForm {
	private MultipartFile file;
	private String Cluster;
	private String Account_L1;
	private String Account_L2;
	private String Store_Format;
	private String Launch_planned;
	private String Total_Stores;// Added By Harsha for US 7 Ja22
	private String Minimum_Target_Stores;
	private String Error_Msg;	
	private String error;

	public String getTotal_Stores() {
		return Total_Stores;
	}

	public void setTotal_Stores(String total_Stores) {
		Total_Stores = total_Stores;
	}

	public String getMinimum_Target_Stores() {
		return Minimum_Target_Stores;
	}

	public void setMinimum_Target_Stores(String minimum_Target_Stores) {
		Minimum_Target_Stores = minimum_Target_Stores;
	}

	public String getError_Msg() {
		return Error_Msg;
	}

	public void setError_Msg(String error_Msg) {
		Error_Msg = error_Msg;
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