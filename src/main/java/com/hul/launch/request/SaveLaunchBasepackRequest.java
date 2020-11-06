package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchBasepackRequest {
	private String psaCategory;
	private String brand;
	private String code;
	private String description;
	private String mrp;
	private String tur;
	private String gsv;
	private String cldConfig;
	private String grammage;
	private String classification;
	private String salesCategory;
	private String launchId;

	@Override
	public String toString() {
		return "SaveLaunchBasepackRequest [psaCategory=" + psaCategory + ", brand=" + brand + ", code=" + code
				+ ", description=" + description + ", mrp=" + mrp + ", tur=" + tur + ", gsv=" + gsv + ", cldConfig="
				+ cldConfig + ", grammage=" + grammage + ", classification=" + classification + ", salesCategory="
				+ salesCategory + "]";
	}

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSalesCategory() {
		return salesCategory;
	}

	public void setSalesCategory(String salesCategory) {
		this.salesCategory = salesCategory;
	}
}