package com.hul.launch.response;

import java.util.List;
import java.util.Map;

public class TMELaunchPlanEditResponse {

	private List<String> moc;
	private Map<String, Object> allEditData;
	private String customerJson;
	private String geographyJson;
	private List<String> customerChainL1;
	private List<String> storeFormat;
	private List<String> townSpcific;

	private Boolean status;
	
	public List<String> getCustomerChainL1() {
		return customerChainL1;
	}
	public void setCustomerChainL1(List<String> customerChainL1) {
		this.customerChainL1 = customerChainL1;
	}
	public List<String> getTownSpcific() {
		return townSpcific;
	}
	public void setTownSpcific(List<String> townSpcific) {
		this.townSpcific = townSpcific;
	}
	public List<String> getStoreFormat() {
		return storeFormat;
	}
	public void setStoreFormat(List<String> storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getGeographyJson() {
		return geographyJson;
	}
	public void setGeographyJson(String geographyJson) {
		this.geographyJson = geographyJson;
	}
	public Map<String, Object> getAllEditData() {
		return allEditData;
	}
	public void setAllEditData(Map<String, Object> allEditData) {
		this.allEditData = allEditData;
	}
	public String getCustomerJson() {
		return customerJson;
	}
	public void setCustomerJson(String customerJson) {
		this.customerJson = customerJson;
	}
	public List<String> getMoc() {
		return moc;
	}
	public void setMoc(List<String> moc) {
		this.moc = moc;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
