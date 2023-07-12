package com.hul.proco.controller.promocr;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class PromoCrBean {

	private MultipartFile file;
	
	@Column(name = "PROMO_ID")
	private String promo_id;
	@Column(name="BASEPACK CODE")
	private String basepack;
	@Column(name = "OFFER_DESCRIPTION")
	private String offer_desc;
	@Column(name="OFFER TYPE")
	private String offer_type;
	@Column(name="OFFER MODALITY")
	private String offer_modality;
	private String offer_value;
	private String geography;
	@Column(name="QUANTITY")
	private String quantity;
	private String uom;
	private String moc;
	
	private String customer_chain_l1;
	private String kitting_value;
	private String status;
	private String startDate;
	private String endDate;
	private String reason;
	
	@Column(name = "REMARKS")
	private String remark;
	
	private String changesMade;
	//Added by Kavitha D-SPRINT 10 Changes
	private String originalId;
	private String userId;
	private String changeDate;
	private String investmentType;
	private String solCode;
	private String promotionMechanics;
	private String solCodeStatus;
	//Added by Kavitha D-SPRINT 15 changes
	private String channel;
	@Column(name="SALES CATEGORY")
	private String category;
	@Column(name="PRICE OFF")
	private String priceoff;
	private String dpquantity;
	@Column(name="SALES CLUSTER")
	private String cluster;
	@Column(name="SOL TYPE")
	private String soltype;
	@Column(name="PROMO ENTRY TYPE")
	private String templatetype;
	@Column(name = "PPM_ACCOUNT")
	private String ppmaccount;
	//Added by Kavitha D-SPRINT 16
	@Column(name="FIXED BUDGET")
	private String budget;
	@Column(name="REGULAR PROMO QUANTITY")
	private String regularPromoQuantity;
	@Column(name="REGULAR PROMO BUDGET")
	private String regularPromoBudget;
	@Column(name="INCREMENTAL BUDGET REQUIRED")
	private String incrementalBudget;
	@Column(name = "REQUIRE STOCK AVAILABILITY CONFIRMATION")
	private String stockAvailability;
	@Column(name = "SIGNED OFF WITH CM")
	private String signedOffWithCM;
	
	
	public String getSignedOffWithCM() {
		return signedOffWithCM;
	}

	public void setSignedOffWithCM(String signedOffWithCM) {
		this.signedOffWithCM = signedOffWithCM;
	}

	public String getRegularPromoQuantity() {
		return regularPromoQuantity;
	}

	public void setRegularPromoQuantity(String regularPromoQuantity) {
		this.regularPromoQuantity = regularPromoQuantity;
	}

	
	
	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getRegularPromoBudget() {
		return regularPromoBudget;
	}

	public void setRegularPromoBudget(String regularPromoBudget) {
		this.regularPromoBudget = regularPromoBudget;
	}

	public String getIncrementalBudget() {
		return incrementalBudget;
	}

	public void setIncrementalBudget(String incrementalBudget) {
		this.incrementalBudget = incrementalBudget;
	}

	public String getStockAvailability() {
		return stockAvailability;
	}

	public void setStockAvailability(String stockAvailability) {
		this.stockAvailability = stockAvailability;
	}

	public String getChangesMade() {
		return changesMade;
	}

	public void setChangesMade(String changesMade) {
		this.changesMade = changesMade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
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

	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriceoff() {
		return priceoff;
	}

	public void setPriceoff(String priceoff) {
		this.priceoff = priceoff;
	}

	public String getDpquantity() {
		return dpquantity;
	}

	public void setDpquantity(String dpquantity) {
		this.dpquantity = dpquantity;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getSoltype() {
		return soltype;
	}

	public void setSoltype(String soltype) {
		this.soltype = soltype;
	}

	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;
	}

	
	public String getPpmaccount() {
		return ppmaccount;
	}

	public void setPpmaccount(String ppmaccount) {
		this.ppmaccount = ppmaccount;
	}

	@Override
	public String toString() {
		return "PromoCrBean [file=" + file + ", promo_id=" + promo_id + ", basepack=" + basepack + ", offer_desc="
				+ offer_desc + ", offer_type=" + offer_type + ", offer_modality=" + offer_modality + ", offer_value="
				+ offer_value + ", geography=" + geography + ", quantity=" + quantity + ", uom=" + uom + ", moc=" + moc
				+ ", customer_chain_l1=" + customer_chain_l1 + ", kitting_value=" + kitting_value + ", status=" + status
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", reason=" + reason + ", remark=" + remark
				+ ", changesMade=" + changesMade + ", originalId=" + originalId + ", userId=" + userId + ", changeDate="
				+ changeDate + ", investmentType=" + investmentType + ", solCode=" + solCode + ", promotionMechanics="
				+ promotionMechanics + ", solCodeStatus=" + solCodeStatus + ", channel=" + channel + ", category="
				+ category + ", priceoff=" + priceoff + ", dpquantity=" + dpquantity + ", cluster=" + cluster
				+ ", soltype=" + soltype + ", templatetype=" + templatetype + ", ppmaccount=" + ppmaccount + ", budget="
				+ budget + ", regularPromoQuantity=" + regularPromoQuantity + ", regularPromoBudget="
				+ regularPromoBudget + ", incrementalBudget=" + incrementalBudget + ", stockAvailability="
				+ stockAvailability + ", signedOffWithCM=" + signedOffWithCM + "]";
	}

}
