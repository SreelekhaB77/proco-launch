package com.hul.launch.controller.masters;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class StoreMasterBean {
	
	private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Column(name = "HUL_OUTLET_CODE")
	private String hulOutletCode = "";
	@Column(name = "CUSTOMER_CODE")
	private String customerCode = "";
	@Column(name = "SERVICING_TYPE")
	private String servicingType = "";
	@Column(name = "CUSTOMER_CHAIN_L1")
	private String customerChainL1 = "";
	@Column(name = "HUL_STORE_FORMAT")
	private String hulStoreFormat = "";
	@Column(name = "CUSTOMER_STORE_FORMAT")
	private String customerStoreFormat = "";
	@Column(name = "BRANCH")
	private String branch = "";
	@Column(name = "STATE")
	private String state = "";
	@Column(name = "TOWN_NAME")
	private String townName = "";
	@Column(name = "HUL_DEPOT")
	private String hulDepot = "";
	@Column(name = "CLUSTER")
	private String cluster = "";
	@Column(name = "ACTIVE(1/0)")
	private String active = "";

	public String getHulOutletCode() {
		return hulOutletCode;
	}

	public void setHulOutletCode(String hulOutletCode) {
		this.hulOutletCode = hulOutletCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getServicingType() {
		return servicingType;
	}

	public void setServicingType(String servicingType) {
		this.servicingType = servicingType;
	}

	public String getCustomerChainL1() {
		return customerChainL1;
	}

	public void setCustomerChainL1(String customerChainL1) {
		this.customerChainL1 = customerChainL1;
	}

	public String getHulStoreFormat() {
		return hulStoreFormat;
	}

	public void setHulStoreFormat(String hulStoreFormat) {
		this.hulStoreFormat = hulStoreFormat;
	}

	public String getCustomerStoreFormat() {
		return customerStoreFormat;
	}

	public void setCustomerStoreFormat(String customerStoreFormat) {
		this.customerStoreFormat = customerStoreFormat;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getHulDepot() {
		return hulDepot;
	}

	public void setHulDepot(String hulDepot) {
		this.hulDepot = hulDepot;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "StoreMasterBean [hulOutletCode=" + hulOutletCode + ", customerCode=" + customerCode + ", servicingType="
				+ servicingType + ", customerChainL1=" + customerChainL1 + ", hulStoreFormat=" + hulStoreFormat
				+ ", customerStoreFormat=" + customerStoreFormat + ", branch=" + branch + ", state=" + state
				+ ", townName=" + townName + ", hulDepot=" + hulDepot + ", active=" + active + "]";
	}

}
