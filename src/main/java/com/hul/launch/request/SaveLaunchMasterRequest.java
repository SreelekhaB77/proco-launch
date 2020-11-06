package com.hul.launch.request;

import java.io.Serializable;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchMasterRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String launchName;
	private String launchNature;
	private String launchNature2;
	private String launchId;
	private String launchBusinessCase;
	private String categorySize;
	private String classification;
	private String launchMoc;
	private String launchDate;

	public String getLaunchMoc() {
		return launchMoc;
	}

	public void setLaunchMoc(String launchMoc) {
		this.launchMoc = launchMoc;
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getLaunchNature2() {
		return launchNature2;
	}

	public void setLaunchNature2(String launchNature2) {
		this.launchNature2 = launchNature2;
	}

	public String getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getLaunchNature() {
		return launchNature;
	}

	public void setLaunchNature(String launchNature) {
		this.launchNature = launchNature;
	}

	public String getLaunchBusinessCase() {
		return launchBusinessCase;
	}

	public void setLaunchBusinessCase(String launchBusinessCase) {
		this.launchBusinessCase = launchBusinessCase;
	}

	public String getCategorySize() {
		return categorySize;
	}

	public void setCategorySize(String categorySize) {
		this.categorySize = categorySize;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
}