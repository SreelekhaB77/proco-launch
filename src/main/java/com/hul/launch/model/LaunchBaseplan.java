package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public class LaunchBaseplan {
	private MultipartFile file;
	private String PSACATEGORY;
	private String BRAND;
	private String CODE;
	private String DESCRIPTION;
	private String MRP;
	private String TUR;
	private String GSV;
	private String CLDCONFIG;
	private String GRAMMAGE;
	private String CLASSIFICATION;
	private String SALESCATEGORY;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPSACATEGORY() {
		return PSACATEGORY;
	}

	public void setPSACATEGORY(String pSACATEGORY) {
		PSACATEGORY = pSACATEGORY;
	}

	public String getBRAND() {
		return BRAND;
	}

	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getMRP() {
		return MRP;
	}

	public void setMRP(String mRP) {
		MRP = mRP;
	}

	public String getTUR() {
		return TUR;
	}

	public void setTUR(String tUR) {
		TUR = tUR;
	}

	public String getGSV() {
		return GSV;
	}

	public void setGSV(String gSV) {
		GSV = gSV;
	}

	public String getCLDCONFIG() {
		return CLDCONFIG;
	}

	public void setCLDCONFIG(String cLDCONFIG) {
		CLDCONFIG = cLDCONFIG;
	}

	public String getGRAMMAGE() {
		return GRAMMAGE;
	}

	public void setGRAMMAGE(String gRAMMAGE) {
		GRAMMAGE = gRAMMAGE;
	}

	public String getCLASSIFICATION() {
		return CLASSIFICATION;
	}

	public void setCLASSIFICATION(String cLASSIFICATION) {
		CLASSIFICATION = cLASSIFICATION;
	}

	public String getSALESCATEGORY() {
		return SALESCATEGORY;
	}

	public void setSALESCATEGORY(String sALESCATEGORY) {
		SALESCATEGORY = sALESCATEGORY;
	}

}