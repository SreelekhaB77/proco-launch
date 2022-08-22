package com.hul.proco.controller.createpromo;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class CreateBeanRegular {

	private MultipartFile file;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "MOC")
	private String moc;

	@Column(name = "SECONDARY CHANNEL")
	private String secondary_channel;

	@Column(name = "PPM ACCOUNT")
	private String ppm_account;

	@Column(name = "PROMO TIMEPERIOD")
	private String promo_time_period;

	@Column(name = "AB CREATION (ONLY FOR KA Accounts)")
	private String ab_creation;

	@Column(name = "BASEPACK CODE")
	private String basepack_code;

	@Column(name = "BASEPACK DESCRIPTION")
	private String baseback_desc;

	@Column(name = "CHILDPACK CODE")
	private String c_pack_code;

	@Column(name = "OFFER DESCRIPTION")
	private String offer_desc;

	@Column(name = "OFFER TYPE")
	private String ofr_type;

	@Column(name = "OFFER MODALITY")
	private String offer_mod;

	@Column(name = "PRICE OFF")
	private String price_off;

	@Column(name = "BUDGET")
	private String budget;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "CLUSTER")
	private String cluster;

	@Column(name = "QUANTITY")
	private String quantity;

	@Column(name = "CR TYPE")
	private String sol_type;

	@Column(name = "END DATE")
	private String end_date;

	@Column(name = "CLUSTER SELECTION")
	private String cluster_selection;

	@Column(name = "BASEPACK ADDITION")
	private String basepack_addition;

	@Column(name = "TOPUP")
	private String topup;

	@Column(name = "ADDITIONAL QUANTITY")
	private String additional_QTY;

	@Column(name = "PROMO ID")
	private String promo_id;

	@Column(name = "Remark")
	private String Remark;

	@Column(name = "SOL WILL RELEASE ON")
	private String sol_release_on;

	@Column(name = "ADDITIONAL BUDGET")
	private String addition_budget;

	@Column(name = "PPM SOL Code Reference")
	private String sol_code_ref;

	@Column(name = "YEAR")
	private String year;

	@Column(name = "MOC")
	private String moc_name;

	
	
	public String getYear() {
		return year;
	}

	public String getMoc_name() {
		return moc_name;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMoc_name(String moc_name) {
		this.moc_name = moc_name;
	}

	public String getSol_code_ref() {
		return sol_code_ref;
	}

	public void setSol_code_ref(String sol_code_ref) {
		this.sol_code_ref = sol_code_ref;
	}

	public String getAddition_budget() {
		return addition_budget;
	}

	public void setAddition_budget(String addition_budget) {
		this.addition_budget = addition_budget;
	}

	public String getSol_release_on() {
		return sol_release_on;
	}

	public void setSol_release_on(String sol_release_on) {
		this.sol_release_on = sol_release_on;
	}

	private String investmentType;
	private String solCodeStatus;
	private String userId;
	private String customer_chain_l1;
	private String status;
	private String promotionMechanics;
	private String solCode;
	private String originalId;
	private String start_Date;

	public String getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(String start_Date) {
		this.start_Date = start_Date;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
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

	public String getCustomer_chain_l1() {
		return customer_chain_l1;
	}

	public void setCustomer_chain_l1(String customer_chain_l1) {
		this.customer_chain_l1 = customer_chain_l1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPromotionMechanics() {
		return promotionMechanics;
	}

	public void setPromotionMechanics(String promotionMechanics) {
		this.promotionMechanics = promotionMechanics;
	}

	public String getSolCode() {
		return solCode;
	}

	public void setSolCode(String solCode) {
		this.solCode = solCode;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getPromo_id() {
		return promo_id;
	}

	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}

	public String getSol_type() {
		return sol_type;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getCluster_selection() {
		return cluster_selection;
	}

	public String getBasepack_addition() {
		return basepack_addition;
	}

	public String getTopup() {
		return topup;
	}

	public String getAdditional_QTY() {
		return additional_QTY;
	}

	public void setSol_type(String sol_type) {
		this.sol_type = sol_type;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setCluster_selection(String cluster_selection) {
		this.cluster_selection = cluster_selection;
	}

	public void setBasepack_addition(String basepack_addition) {
		this.basepack_addition = basepack_addition;
	}

	public void setTopup(String topup) {
		this.topup = topup;
	}

	public void setAdditional_QTY(String additional_QTY) {
		this.additional_QTY = additional_QTY;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MultipartFile getFile() {
		return file;
	}

	public String getChannel() {
		return channel;
	}

	public String getMoc() {
		return moc;
	}

	public String getSecondary_channel() {
		return secondary_channel;
	}

	public String getPpm_account() {
		return ppm_account;
	}

	public String getPromo_time_period() {
		return promo_time_period;
	}

	public String getAb_creation() {
		return ab_creation;
	}

	public String getBasepack_code() {
		return basepack_code;
	}

	public String getBaseback_desc() {
		return baseback_desc;
	}

	public String getC_pack_code() {
		return c_pack_code;
	}

	public String getOffer_desc() {
		return offer_desc;
	}

	public String getOfr_type() {
		return ofr_type;
	}

	public String getOffer_mod() {
		return offer_mod;
	}

	public String getPrice_off() {
		return price_off;
	}

	public String getBudget() {
		return budget;
	}

	public String getBranch() {
		return branch;
	}

	public String getCluster() {
		return cluster;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public void setSecondary_channel(String secondary_channel) {
		this.secondary_channel = secondary_channel;
	}

	public void setPpm_account(String ppm_account) {
		this.ppm_account = ppm_account;
	}

	public void setPromo_time_period(String promo_time_period) {
		this.promo_time_period = promo_time_period;
	}

	public void setAb_creation(String ab_creation) {
		this.ab_creation = ab_creation;
	}

	public void setBasepack_code(String basepack_code) {
		this.basepack_code = basepack_code;
	}

	public void setBaseback_desc(String baseback_desc) {
		this.baseback_desc = baseback_desc;
	}

	public void setC_pack_code(String c_pack_code) {
		this.c_pack_code = c_pack_code;
	}

	public void setOffer_desc(String offer_desc) {
		this.offer_desc = offer_desc;
	}

	public void setOfr_type(String ofr_type) {
		this.ofr_type = ofr_type;
	}

	public void setOffer_mod(String offer_mod) {
		this.offer_mod = offer_mod;
	}

	public void setPrice_off(String price_off) {
		this.price_off = price_off;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "CreateBeanRegular [file=" + file + ", channel=" + channel + ", moc=" + moc + ", secondary_channel="
				+ secondary_channel + ", ppm_account=" + ppm_account + ", promo_time_period=" + promo_time_period
				+ ", ab_creation=" + ab_creation + ", basepack_code=" + basepack_code + ", baseback_desc="
				+ baseback_desc + ", c_pack_code=" + c_pack_code + ", offer_desc=" + offer_desc + ", ofr_type="
				+ ofr_type + ", offer_mod=" + offer_mod + ", price_off=" + price_off + ", budget=" + budget
				+ ", branch=" + branch + ", cluster=" + cluster + ", quantity=" + quantity + ", sol_type=" + sol_type
				+ ", end_date=" + end_date + ", cluster_selection=" + cluster_selection + ", basepack_addition="
				+ basepack_addition + ", topup=" + topup + ", additional_QTY=" + additional_QTY + ", promo_id="
				+ promo_id + ", Remark=" + Remark + ", investmentType=" + investmentType + ", solCodeStatus="
				+ solCodeStatus + ", userId=" + userId + ", customer_chain_l1=" + customer_chain_l1 + ", status="
				+ status + ", promotionMechanics=" + promotionMechanics + ", solCode=" + solCode + ", originalId="
				+ originalId + ", start_Date=" + start_Date + "]";
	}

}
