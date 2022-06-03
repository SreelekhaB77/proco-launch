package com.hul.proco.controller.promostatustracker;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class PPMLinkageBean {
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Column(name="versionedPromotionId")
	private String version_promo_id;

	@Column(name="ChartByType")
	private String chart_by_type;
	
	@Column(name="PromotionCreator")
	private String promo_creator;
	
	@Column(name="PromotionStatus")
	private String promo_status;
	
	@Column(name="promotionid")
	private String promo_id;
	
	@Column(name="PromotionName")
	private String promo_name;
	
	@Column(name="sell-in start date")
	private String sell_in_start_date;
	
	@Column(name="sell-in end date")
	private String sell_in_end_date;
	
	@Column(name="Promotion Mechanics")
	private String promo_mechanics;
	
	@Column(name="Investment Type")
	private String investment_type;
	
	@Column(name="Cluster Code")
	private String cluster_code;
	
	@Column(name="Cluster Name")
	private String cluster_name;
	
	@Column(name="Basepack Code")
	private String basepack_code;
	
	@Column(name="Basepack Name")
	private String basepack_name;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Brand")
	private String brand;
	
	@Column(name="Sub-Brand")
	private String sub_brand;
	
	@Column(name="UOM")
	private String uom;
	
	@Column(name="Tax")
	private String tax;
	
	@Column(name="Discount")
	private String discount;
	
	@Column(name="List Price")
	private String list_price;
	
	@Column(name="percent promoted volume")
	private String percent_promoted_volume;
	
	@Column(name="Quantity")
	private String quantity;
	
	@Column(name="BudgetHolderName")
	private String budget_holder_name;
	
	@Column(name="FundType")
	private String fund_type;
	
	public String getVersion_promo_id() {
		return version_promo_id;
	}

	

	public String getChart_by_type() {
		return chart_by_type;
	}

	public String getPromo_creator() {
		return promo_creator;
	}

	public String getPromo_status() {
		return promo_status;
	}

	public String getPromo_id() {
		return promo_id;
	}

	public String getPromo_name() {
		return promo_name;
	}

	public String getSell_in_start_date() {
		return sell_in_start_date;
	}

	public String getSell_in_end_date() {
		return sell_in_end_date;
	}

	public String getPromo_mechanics() {
		return promo_mechanics;
	}

	public String getInvestment_type() {
		return investment_type;
	}

	public String getCluster_code() {
		return cluster_code;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public String getBasepack_code() {
		return basepack_code;
	}

	public String getBasepack_name() {
		return basepack_name;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public String getSub_brand() {
		return sub_brand;
	}

	public String getUom() {
		return uom;
	}

	public String getTax() {
		return tax;
	}

	public String getDiscount() {
		return discount;
	}

	public String getList_price() {
		return list_price;
	}

	public String getPercent_promoted_volume() {
		return percent_promoted_volume;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getBudget_holder_name() {
		return budget_holder_name;
	}

	public String getFund_type() {
		return fund_type;
	}

	public String getMoc() {
		return moc;
	}

	public String getInvestment_amount() {
		return investment_amount;
	}

	public void setVersion_promo_id(String version_promo_id) {
		this.version_promo_id = version_promo_id;
	}

	public void setChart_by_type(String chart_by_type) {
		this.chart_by_type = chart_by_type;
	}

	public void setPromo_creator(String promo_creator) {
		this.promo_creator = promo_creator;
	}

	public void setPromo_status(String promo_status) {
		this.promo_status = promo_status;
	}

	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}

	public void setPromo_name(String promo_name) {
		this.promo_name = promo_name;
	}

	public void setSell_in_start_date(String sell_in_start_date) {
		this.sell_in_start_date = sell_in_start_date;
	}

	public void setSell_in_end_date(String sell_in_end_date) {
		this.sell_in_end_date = sell_in_end_date;
	}

	public void setPromo_mechanics(String promo_mechanics) {
		this.promo_mechanics = promo_mechanics;
	}

	public void setInvestment_type(String investment_type) {
		this.investment_type = investment_type;
	}

	public void setCluster_code(String cluster_code) {
		this.cluster_code = cluster_code;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public void setBasepack_code(String basepack_code) {
		this.basepack_code = basepack_code;
	}

	public void setBasepack_name(String basepack_name) {
		this.basepack_name = basepack_name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setSub_brand(String sub_brand) {
		this.sub_brand = sub_brand;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setList_price(String list_price) {
		this.list_price = list_price;
	}

	public void setPercent_promoted_volume(String percent_promoted_volume) {
		this.percent_promoted_volume = percent_promoted_volume;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setBudget_holder_name(String budget_holder_name) {
		this.budget_holder_name = budget_holder_name;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public void setInvestment_amount(String investment_amount) {
		this.investment_amount = investment_amount;
	}

	@Column(name="MOC")
	private String moc;
	
	@Column(name="InvestmentAmount")
	private String investment_amount;
	
	
}
