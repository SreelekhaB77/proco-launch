package com.hul.proco.controller.volumeupload;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class VolumeUploadBean {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Column(name = "Promo Id")
	private String promoId;
	@Column(name = "Quantity")
	private String quantity;

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	@Column(name = "Sales Category")
	private String salesCategory;
	
	@Column(name = "Offer Value")
	private String offerValue = "";

	@Column(name = "Offer Type")
	private String offerType = "";

	@Column(name = "Year")
	private String year = "";

	@Column(name = "MOC")
	private String moc = "";

	@Column(name = "Offer Modality")
	private String modality = "";

	@Column(name = "Geography")
	private String geography = "";

	@Column(name = "Kitting Value")
	private String kittingValue = "";

	@Column(name = "Customer Chain L1")
	private String customerChainL1 = "";

	@Column(name = "Customer Chain L2")
	private String customerChainL2 = "";

	@Column(name = "UOM")
	private String uom = "";

	@Column(name = "3rd Party Description")
	private String thirdPartyDesc = "";

	@Column(name = "3rd Party Pack Ratio")
	private String thirdPartyRatio = "";

	@Column(name = "Offer Description")
	private String promoDesc = "";

	@Column(name = "P1 Basepack")
	private String basepack1 = "";

	private String category1 = "";
	private String brand1 = "";
	private String basepackDesc1 = "";
	@Column(name = "P1 Pack Ratio")
	private String ratio1 = "";

	@Column(name = "P2 Basepack")
	private String basepack2 = "";
	private String category2 = "";
	private String brand2 = "";
	private String basepackDesc2 = "";
	@Column(name = "P2 Pack Ratio")
	private String ratio2 = "";

	@Column(name = "P3 Basepack")
	private String basepack3 = "";
	private String category3 = "";
	private String brand3 = "";
	private String basepackDesc3 = "";
	@Column(name = "P3 Pack Ratio")
	private String ratio3 = "";

	@Column(name = "P4 Basepack")
	private String basepack4 = "";
	private String category4 = "";
	private String brand4 = "";
	private String basepackDesc4 = "";
	@Column(name = "P4 Pack Ratio")
	private String ratio4 = "";

	@Column(name = "P5 Basepack")
	private String basepack5 = "";
	private String category5 = "";
	private String brand5 = "";
	private String basepackDesc5 = "";
	@Column(name = "P5 Pack Ratio")
	private String ratio5 = "";

	@Column(name = "P6 Basepack")
	private String basepack6 = "";
	private String category6 = "";
	private String brand6 = "";
	private String basepackDesc6 = "";
	@Column(name = "P6 Pack Ratio")
	private String ratio6 = "";

	@Column(name = "C1 Child Pack")
	private String childBasepack1 = "";
	private String childCategory1 = "";
	private String childBrand1 = "";
	private String childBasepackDesc1 = "";
	@Column(name = "C1 Child Pack Ratio")
	private String childRatio1 = "";

	@Column(name = "C2 Child Pack")
	private String childBasepack2 = "";
	private String childCategory2 = "";
	private String childBrand2 = "";
	private String childBasepackDesc2 = "";
	@Column(name = "C2 Child Pack Ratio")
	private String childRatio2 = "";

	@Column(name = "C3 Child Pack")
	private String childBasepack3 = "";
	private String childCategory3 = "";
	private String childBrand3 = "";
	private String childBasepackDesc3 = "";
	@Column(name = "C3 Child Pack Ratio")
	private String childRatio3 = "";

	@Column(name = "C4 Child Pack")
	private String childBasepack4 = "";
	private String childCategory4 = "";
	private String childBrand4 = "";
	private String childBasepackDesc4 = "";
	@Column(name = "C4 Child Pack Ratio")
	private String childRatio4 = "";

	@Column(name = "C5 Child Pack")
	private String childBasepack5 = "";
	private String childCategory5 = "";
	private String childBrand5 = "";
	private String childBasepackDesc5 = "";
	@Column(name = "C5 Child Pack Ratio")
	private String childRatio5 = "";

	@Column(name = "C6 Child Pack")
	private String childBasepack6 = "";
	private String childCategory6 = "";
	private String childBrand6 = "";
	private String childBasepackDesc6 = "";
	@Column(name = "C6 Child Pack Ratio")
	private String childRatio6 = "";

	public String getRatio1() {
		return ratio1;
	}

	public void setRatio1(String ratio1) {
		this.ratio1 = ratio1;
	}

	public String getBasepack1() {
		return basepack1;
	}

	public void setBasepack1(String basepack1) {
		this.basepack1 = basepack1;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getBrand1() {
		return brand1;
	}

	public void setBrand1(String brand1) {
		this.brand1 = brand1;
	}

	public String getBasepackDesc1() {
		return basepackDesc1;
	}

	public void setBasepackDesc1(String basepackDesc1) {
		this.basepackDesc1 = basepackDesc1;
	}

	public String getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(String offerValue) {
		this.offerValue = offerValue;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getGeography() {
		return geography;
	}

	public void setGeography(String geography) {
		this.geography = geography;
	}

	public String getKittingValue() {
		return kittingValue;
	}

	public void setKittingValue(String kittingValue) {
		this.kittingValue = kittingValue;
	}

	public String getCustomerChainL1() {
		return customerChainL1;
	}

	public void setCustomerChainL1(String customerChainL1) {
		this.customerChainL1 = customerChainL1;
	}

	public String getCustomerChainL2() {
		return customerChainL2;
	}

	public void setCustomerChainL2(String customerChainL2) {
		this.customerChainL2 = customerChainL2;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getPromoDesc() {
		return promoDesc;
	}

	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	public String getThirdPartyDesc() {
		return thirdPartyDesc;
	}

	public void setThirdPartyDesc(String thirdPartyDesc) {
		this.thirdPartyDesc = thirdPartyDesc;
	}

	public String getThirdPartyRatio() {
		return thirdPartyRatio;
	}

	public void setThirdPartyRatio(String thirdPartyRatio) {
		this.thirdPartyRatio = thirdPartyRatio;
	}

	public String getBasepack2() {
		return basepack2;
	}

	public void setBasepack2(String basepack2) {
		this.basepack2 = basepack2;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getBrand2() {
		return brand2;
	}

	public void setBrand2(String brand2) {
		this.brand2 = brand2;
	}

	public String getBasepackDesc2() {
		return basepackDesc2;
	}

	public void setBasepackDesc2(String basepackDesc2) {
		this.basepackDesc2 = basepackDesc2;
	}

	public String getRatio2() {
		return ratio2;
	}

	public void setRatio2(String ratio2) {
		this.ratio2 = ratio2;
	}

	public String getBasepack3() {
		return basepack3;
	}

	public void setBasepack3(String basepack3) {
		this.basepack3 = basepack3;
	}

	public String getCategory3() {
		return category3;
	}

	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	public String getBrand3() {
		return brand3;
	}

	public void setBrand3(String brand3) {
		this.brand3 = brand3;
	}

	public String getBasepackDesc3() {
		return basepackDesc3;
	}

	public void setBasepackDesc3(String basepackDesc3) {
		this.basepackDesc3 = basepackDesc3;
	}

	public String getRatio3() {
		return ratio3;
	}

	public void setRatio3(String ratio3) {
		this.ratio3 = ratio3;
	}

	public String getBasepack4() {
		return basepack4;
	}

	public void setBasepack4(String basepack4) {
		this.basepack4 = basepack4;
	}

	public String getCategory4() {
		return category4;
	}

	public void setCategory4(String category4) {
		this.category4 = category4;
	}

	public String getBrand4() {
		return brand4;
	}

	public void setBrand4(String brand4) {
		this.brand4 = brand4;
	}

	public String getBasepackDesc4() {
		return basepackDesc4;
	}

	public void setBasepackDesc4(String basepackDesc4) {
		this.basepackDesc4 = basepackDesc4;
	}

	public String getRatio4() {
		return ratio4;
	}

	public void setRatio4(String ratio4) {
		this.ratio4 = ratio4;
	}

	public String getBasepack5() {
		return basepack5;
	}

	public void setBasepack5(String basepack5) {
		this.basepack5 = basepack5;
	}

	public String getCategory5() {
		return category5;
	}

	public void setCategory5(String category5) {
		this.category5 = category5;
	}

	public String getBrand5() {
		return brand5;
	}

	public void setBrand5(String brand5) {
		this.brand5 = brand5;
	}

	public String getBasepackDesc5() {
		return basepackDesc5;
	}

	public void setBasepackDesc5(String basepackDesc5) {
		this.basepackDesc5 = basepackDesc5;
	}

	public String getRatio5() {
		return ratio5;
	}

	public void setRatio5(String ratio5) {
		this.ratio5 = ratio5;
	}

	public String getBasepack6() {
		return basepack6;
	}

	public void setBasepack6(String basepack6) {
		this.basepack6 = basepack6;
	}

	public String getCategory6() {
		return category6;
	}

	public void setCategory6(String category6) {
		this.category6 = category6;
	}

	public String getBrand6() {
		return brand6;
	}

	public void setBrand6(String brand6) {
		this.brand6 = brand6;
	}

	public String getBasepackDesc6() {
		return basepackDesc6;
	}

	public void setBasepackDesc6(String basepackDesc6) {
		this.basepackDesc6 = basepackDesc6;
	}

	public String getRatio6() {
		return ratio6;
	}

	public void setRatio6(String ratio6) {
		this.ratio6 = ratio6;
	}

	public String getChildBasepack1() {
		return childBasepack1;
	}

	public void setChildBasepack1(String childBasepack1) {
		this.childBasepack1 = childBasepack1;
	}

	public String getChildCategory1() {
		return childCategory1;
	}

	public void setChildCategory1(String childCategory1) {
		this.childCategory1 = childCategory1;
	}

	public String getChildBrand1() {
		return childBrand1;
	}

	public void setChildBrand1(String childBrand1) {
		this.childBrand1 = childBrand1;
	}

	public String getChildBasepackDesc1() {
		return childBasepackDesc1;
	}

	public void setChildBasepackDesc1(String childBasepackDesc1) {
		this.childBasepackDesc1 = childBasepackDesc1;
	}

	public String getChildRatio1() {
		return childRatio1;
	}

	public void setChildRatio1(String childRatio1) {
		this.childRatio1 = childRatio1;
	}

	public String getChildBasepack2() {
		return childBasepack2;
	}

	public void setChildBasepack2(String childBasepack2) {
		this.childBasepack2 = childBasepack2;
	}

	public String getChildCategory2() {
		return childCategory2;
	}

	public void setChildCategory2(String childCategory2) {
		this.childCategory2 = childCategory2;
	}

	public String getChildBrand2() {
		return childBrand2;
	}

	public void setChildBrand2(String childBrand2) {
		this.childBrand2 = childBrand2;
	}

	public String getChildBasepackDesc2() {
		return childBasepackDesc2;
	}

	public void setChildBasepackDesc2(String childBasepackDesc2) {
		this.childBasepackDesc2 = childBasepackDesc2;
	}

	public String getChildRatio2() {
		return childRatio2;
	}

	public void setChildRatio2(String childRatio2) {
		this.childRatio2 = childRatio2;
	}

	public String getChildBasepack3() {
		return childBasepack3;
	}

	public void setChildBasepack3(String childBasepack3) {
		this.childBasepack3 = childBasepack3;
	}

	public String getChildCategory3() {
		return childCategory3;
	}

	public void setChildCategory3(String childCategory3) {
		this.childCategory3 = childCategory3;
	}

	public String getChildBrand3() {
		return childBrand3;
	}

	public void setChildBrand3(String childBrand3) {
		this.childBrand3 = childBrand3;
	}

	public String getChildBasepackDesc3() {
		return childBasepackDesc3;
	}

	public void setChildBasepackDesc3(String childBasepackDesc3) {
		this.childBasepackDesc3 = childBasepackDesc3;
	}

	public String getChildRatio3() {
		return childRatio3;
	}

	public void setChildRatio3(String childRatio3) {
		this.childRatio3 = childRatio3;
	}

	public String getChildBasepack4() {
		return childBasepack4;
	}

	public void setChildBasepack4(String childBasepack4) {
		this.childBasepack4 = childBasepack4;
	}

	public String getChildCategory4() {
		return childCategory4;
	}

	public void setChildCategory4(String childCategory4) {
		this.childCategory4 = childCategory4;
	}

	public String getChildBrand4() {
		return childBrand4;
	}

	public void setChildBrand4(String childBrand4) {
		this.childBrand4 = childBrand4;
	}

	public String getChildBasepackDesc4() {
		return childBasepackDesc4;
	}

	public void setChildBasepackDesc4(String childBasepackDesc4) {
		this.childBasepackDesc4 = childBasepackDesc4;
	}

	public String getChildRatio4() {
		return childRatio4;
	}

	public void setChildRatio4(String childRatio4) {
		this.childRatio4 = childRatio4;
	}

	public String getChildBasepack5() {
		return childBasepack5;
	}

	public void setChildBasepack5(String childBasepack5) {
		this.childBasepack5 = childBasepack5;
	}

	public String getChildCategory5() {
		return childCategory5;
	}

	public void setChildCategory5(String childCategory5) {
		this.childCategory5 = childCategory5;
	}

	public String getChildBrand5() {
		return childBrand5;
	}

	public void setChildBrand5(String childBrand5) {
		this.childBrand5 = childBrand5;
	}

	public String getChildBasepackDesc5() {
		return childBasepackDesc5;
	}

	public void setChildBasepackDesc5(String childBasepackDesc5) {
		this.childBasepackDesc5 = childBasepackDesc5;
	}

	public String getChildRatio5() {
		return childRatio5;
	}

	public void setChildRatio5(String childRatio5) {
		this.childRatio5 = childRatio5;
	}

	public String getChildBasepack6() {
		return childBasepack6;
	}

	public void setChildBasepack6(String childBasepack6) {
		this.childBasepack6 = childBasepack6;
	}

	public String getChildCategory6() {
		return childCategory6;
	}

	public void setChildCategory6(String childCategory6) {
		this.childCategory6 = childCategory6;
	}

	public String getChildBrand6() {
		return childBrand6;
	}

	public void setChildBrand6(String childBrand6) {
		this.childBrand6 = childBrand6;
	}

	public String getChildBasepackDesc6() {
		return childBasepackDesc6;
	}

	public void setChildBasepackDesc6(String childBasepackDesc6) {
		this.childBasepackDesc6 = childBasepackDesc6;
	}

	public String getChildRatio6() {
		return childRatio6;
	}

	public void setChildRatio6(String childRatio6) {
		this.childRatio6 = childRatio6;
	}

	@Override
	public String toString() {
		return "CreatePromotionBean [offerValue=" + offerValue + ", offerType=" + offerType + ", year=" + year
				+ ", moc=" + moc + ", modality=" + modality + ", geography=" + geography + ", kittingValue="
				+ kittingValue + ", customerChainL1=" + customerChainL1 + ", customerChainL2=" + customerChainL2
				+ ", uom=" + uom + "]";
	}

}
