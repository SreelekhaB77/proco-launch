package com.hul.proco.controller.createpromo;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class CreateBeanRegular {

	private MultipartFile file;

	@Column(name = "Channel")
	private String channel;

	@Column(name = "MOC")
	private String moc;

	@Column(name = "Scondary channel")
	private String secondary_channel;

	@Column(name = "PPM Account")
	private String ppm_account;

	@Column(name = "Promo Timeperiod")
	private String promo_time_period;

	@Column(name = "AB CREATION")
	private String ab_creation;

	@Column(name = "Basepack Code")
	private String basepack_code;

	@Column(name = "Basepack Description")
	private String baseback_desc;

	@Column(name = "Child Pack Code")
	private String c_pack_code;

	@Column(name = "Offer description")
	private String offer_desc;

	@Column(name = "Offer Type")
	private String ofr_type;

	@Column(name = "Offer Modality")
	private String offer_mod;

	@Column(name = "Price off")
	private String price_off;

	@Column(name = "BUDGET")
	private String budget;

	@Column(name = "Branch")
	private String branch;

	@Column(name = "CLUSTER")
	private String cluster;

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
	
	
}
