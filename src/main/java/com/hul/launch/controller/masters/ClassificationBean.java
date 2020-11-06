package com.hul.launch.controller.masters;

import org.springframework.web.multipart.MultipartFile;

public class ClassificationBean {

private MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	private String customerChainL1;
	private String customerStoreFormat;
	private String gold;
	private String silver;
	private String bronze;
	private String visibilityEligibility;

	public String getCustomerChainL1() {
		return customerChainL1;
	}

	public void setCustomerChainL1(String customerChainL1) {
		this.customerChainL1 = customerChainL1;
	}

	public String getCustomerStoreFormat() {
		return customerStoreFormat;
	}

	public void setCustomerStoreFormat(String customerStoreFormat) {
		this.customerStoreFormat = customerStoreFormat;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getSilver() {
		return silver;
	}

	public void setSilver(String silver) {
		this.silver = silver;
	}

	public String getBronze() {
		return bronze;
	}

	public void setBronze(String bronze) {
		this.bronze = bronze;
	}

	public String getVisibilityEligibility() {
		return visibilityEligibility;
	}

	public void setVisibilityEligibility(String visibilityEligibility) {
		this.visibilityEligibility = visibilityEligibility;
	}

	@Override
	public String toString() {
		return "ClassificationBean [customerChainL1=" + customerChainL1 + ", customerStoreFormat=" + customerStoreFormat
				+ ", gold=" + gold + ", silver=" + silver + ", bronze=" + bronze + ", visibilityEligibility="
				+ visibilityEligibility + "]";
	}

}
