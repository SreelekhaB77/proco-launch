package com.hul.proco.controller.listingPromo;

import org.springframework.web.multipart.MultipartFile;

public class PromoMeasureReportBean {

	private String promotionId;
	private String promotionName;
	private String createdBy;
	private String promotionMechanics;
	private String promotionStartDate;
	private String promotionEndDate;
	private String customer;
	private String product;
	private String promotionStatus;
	private String category;
	private String investmentType;
	private String moc;
	private String submissionDate;
	private String promotionType;
	private String promotionVolumeDuring;
	private String plannedInvestmentAmount;
	private String subBrand;
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPromotionMechanics() {
		return promotionMechanics;
	}

	public void setPromotionMechanics(String promotionMechanics) {
		this.promotionMechanics = promotionMechanics;
	}

	public String getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public String getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionVolumeDuring() {
		return promotionVolumeDuring;
	}

	public void setPromotionVolumeDuring(String promotionVolumeDuring) {
		this.promotionVolumeDuring = promotionVolumeDuring;
	}

	public String getPlannedInvestmentAmount() {
		return plannedInvestmentAmount;
	}

	public void setPlannedInvestmentAmount(String plannedInvestmentAmount) {
		this.plannedInvestmentAmount = plannedInvestmentAmount;
	}
	
	public String getSubBrand() {
		return subBrand;
	}

	public void setSubBrand(String subBrand) {
		this.subBrand = subBrand;
	}
	
}
