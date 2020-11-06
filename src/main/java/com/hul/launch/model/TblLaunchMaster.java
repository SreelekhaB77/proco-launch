package com.hul.launch.model;

import java.sql.Date;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class TblLaunchMaster {
	private String launchName;
	private String launchNature;
	private String launchNature2;
	private String launchBusinessCase;
	private String categorySize;
	private String annexureDocName;
	private String artworkPackshotsDocName;
	private String mdgDecName;
	private String classification;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String sampleShared;
	private String error;

	public String getSampleShared() {
		return sampleShared;
	}

	public void setSampleShared(String sampleShared) {
		this.sampleShared = sampleShared;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getAnnexureDocName() {
		return annexureDocName;
	}

	public void setAnnexureDocName(String annexureDocName) {
		this.annexureDocName = annexureDocName;
	}

	public String getArtworkPackshotsDocName() {
		return artworkPackshotsDocName;
	}

	public void setArtworkPackshotsDocName(String artworkPackshotsDocName) {
		this.artworkPackshotsDocName = artworkPackshotsDocName;
	}

	public String getMdgDecName() {
		return mdgDecName;
	}

	public void setMdgDecName(String mdgDecName) {
		this.mdgDecName = mdgDecName;
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

	public String getLaunchNature2() {
		return launchNature2;
	}

	public void setLaunchNature2(String launchNature2) {
		this.launchNature2 = launchNature2;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}