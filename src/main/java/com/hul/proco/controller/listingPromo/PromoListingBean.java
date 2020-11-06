package com.hul.proco.controller.listingPromo;

import org.springframework.web.multipart.MultipartFile;

public class PromoListingBean {

	private String promo_id;
	private String basepack;
	private String offer_desc;
	private String offer_type;
	private String offer_modality;
	private String offer_value;
	private String geography;
	private String quantity;
	private String uom;

	private String moc;
	private String customer_chain_l1;
	private String kitting_value;
	
	private String status;
	
	private String startDate;
	private String endDate;
	private String reason;
	private String remark;
	private String customerChainL2;
	private String changeDate;
	private String changesMade;
	private String userId;
	
	private String investmentType;
	private String solCode;
	private String solCodeDescription;
	private String promotionMechanics;
	private String solCodeStatus;
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	public String getSolCode() {
		return solCode;
	}

	public void setSolCode(String solCode) {
		this.solCode = solCode;
	}

	
	public String getSolCodeDescription() {
		return solCodeDescription;
	}

	public void setSolCodeDescription(String solCodeDescription) {
		this.solCodeDescription = solCodeDescription;
	}

	public String getPromotionMechanics() {
		return promotionMechanics;
	}

	public void setPromotionMechanics(String promotionMechanics) {
		this.promotionMechanics = promotionMechanics;
	}

	public String getSolCodeStatus() {
		return solCodeStatus;
	}

	public void setSolCodeStatus(String solCodeStatus) {
		this.solCodeStatus = solCodeStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChangesMade() {
		return changesMade;
	}

	public void setChangesMade(String changesMade) {
		this.changesMade = changesMade;
	}

	private String originalId;
	
	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getCustomerChainL2() {
		return customerChainL2;
	}

	public void setCustomerChainL2(String customerChainL2) {
		this.customerChainL2 = customerChainL2;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getCustomer_chain_l1() {
		return customer_chain_l1;
	}

	public void setCustomer_chain_l1(String customer_chain_l1) {
		this.customer_chain_l1 = customer_chain_l1;
	}

	public String getKitting_value() {
		return kitting_value;
	}

	public void setKitting_value(String kitting_value) {
		this.kitting_value = kitting_value;
	}

	public String getPromo_id() {
		return promo_id;
	}

	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}

	public String getBasepack() {
		return basepack;
	}

	public void setBasepack(String basepack) {
		this.basepack = basepack;
	}

	public String getOffer_desc() {
		return offer_desc;
	}

	public void setOffer_desc(String offer_desc) {
		this.offer_desc = offer_desc;
	}

	public String getOffer_type() {
		return offer_type;
	}

	public void setOffer_type(String offer_type) {
		this.offer_type = offer_type;
	}

	public String getOffer_modality() {
		return offer_modality;
	}

	public void setOffer_modality(String offer_modality) {
		this.offer_modality = offer_modality;
	}

	
	public String getOffer_value() {
		return offer_value;
	}

	public void setOffer_value(String offer_value) {
		this.offer_value = offer_value;
	}

	public String getGeography() {
		return geography;
	}

	public void setGeography(String geography) {
		this.geography = geography;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

}
