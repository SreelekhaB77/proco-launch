package com.hul.launch.response;

public class LaunchCoeBasePackResponse {
	private String launchName;
	private String salesCat;
	private String psaCat;
	private String brand;
	private String bpCode;
	private String bpDisc;
	private String mrp;
	private String tur;
	private String gsv;
	private String cldConfig;
	private String grammage;
	private String classification;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	private String error;

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getSalesCat() {
		return salesCat;
	}

	public void setSalesCat(String salesCat) {
		this.salesCat = salesCat;
	}

	public String getPsaCat() {
		return psaCat;
	}

	public void setPsaCat(String psaCat) {
		this.psaCat = psaCat;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBpCode() {
		return bpCode;
	}

	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}

	public String getBpDisc() {
		return bpDisc;
	}

	public void setBpDisc(String bpDisc) {
		this.bpDisc = bpDisc;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getTur() {
		return tur;
	}

	public void setTur(String tur) {
		this.tur = tur;
	}

	public String getGsv() {
		return gsv;
	}

	public void setGsv(String gsv) {
		this.gsv = gsv;
	}

	public String getCldConfig() {
		return cldConfig;
	}

	public void setCldConfig(String cldConfig) {
		this.cldConfig = cldConfig;
	}

	public String getGrammage() {
		return grammage;
	}

	public void setGrammage(String grammage) {
		this.grammage = grammage;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
}
