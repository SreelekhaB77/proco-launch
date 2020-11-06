package com.hul.launch.response;

public class LaunchBasePackResponse {
	private int bpCode;
	private String bpDesc;
	private int bpMrp;
	private int bpTur;
	private int bpGsv;
	private int bpCldConfig;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	private String error;

	public int getBpCldConfig() {
		return bpCldConfig;
	}

	public void setBpCldConfig(int bpCldConfig) {
		this.bpCldConfig = bpCldConfig;
	}

	private int bpGrammage;
	private String bpClassification;

	public int getBpCode() {
		return bpCode;
	}

	public void setBpCode(int bpCode) {
		this.bpCode = bpCode;
	}

	public String getBpDesc() {
		return bpDesc;
	}

	public void setBpDesc(String bpDesc) {
		this.bpDesc = bpDesc;
	}

	public int getBpMrp() {
		return bpMrp;
	}

	public void setBpMrp(int bpMrp) {
		this.bpMrp = bpMrp;
	}

	public int getBpTur() {
		return bpTur;
	}

	public void setBpTur(int bpTur) {
		this.bpTur = bpTur;
	}

	public int getBpGsv() {
		return bpGsv;
	}

	public void setBpGsv(int bpGsv) {
		this.bpGsv = bpGsv;
	}

	public int getBpGrammage() {
		return bpGrammage;
	}

	public void setBpGrammage(int bpGrammage) {
		this.bpGrammage = bpGrammage;
	}

	public String getBpClassification() {
		return bpClassification;
	}

	public void setBpClassification(String bpClassification) {
		this.bpClassification = bpClassification;
	}
}