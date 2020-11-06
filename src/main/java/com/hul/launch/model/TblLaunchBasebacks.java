package com.hul.launch.model;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class TblLaunchBasebacks {
	private String SalesCategory;
	private String psaCategory;
	private String brand;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSalesCategory() {
		return SalesCategory;
	}

	public void setSalesCategory(String salesCategory) {
		SalesCategory = salesCategory;
	}

	public String getPsaCategory() {
		return psaCategory;
	}

	public void setPsaCategory(String psaCategory) {
		this.psaCategory = psaCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}