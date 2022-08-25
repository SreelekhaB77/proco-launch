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
	
	@Column(name="PROMO_ID")
	private String PROMO_ID;
	
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
	
	@Column(name="BRAND")
	private String brand;
	
	@Column(name="SUB BRAND")
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
	
	
	
	@Column(name="PROMOTION ID")
	private String 	PROMOTION_ID;

	@Column(name="PROMOTION NAME")
	private String PROMOTION_NAME;
	
	@Column(name="CREATED BY")
	private String CREATED_BY;
	
	@Column(name="CREATED ON")
	private String CREATED_ON;
	
	@Column(name="PROJECT ID")
	private String PROJECT_ID;
	
	@Column(name="PROJECT NAME")
	private String PROJECT_NAME;
	
	@Column(name="BUNDLE ID")
	private String BUNDLE_ID;
	
	@Column(name="BUNDLE NAME")
	private String BUNDLE_NAME;
	
	@Column(name="PROMOTION QUALIFICATION")
	private String PROMOTION_QUALIFICATION;
	
	@Column(name="PROMOTION  OBJECTIVE")
	private String PROMOTION_OBJECTIVE;
	
	@Column(name="MARKETING OBJECTIVE")
	private String MARKETING_OBJECTIVE;
	
	@Column(name="PROMOTION MECHANICS")
	private String PROMOTION_MECHANICS;
	
	@Column(name="PROMOTION STARTDATE")
	private String PROMOTION_START_DATE;
	
	@Column(name="PROMOTION ENDDATE")
	private String PROMOTION_END_DATE;
	
	@Column(name="MOC")
	private String moc;
	
	@Column(name="InvestmentAmount")
	private String investment_amount;

	@Column(name="PRE DIP STARTDATE")
	private String PRE_DIP_START_DATE;
	
	@Column(name="POST DIP ENDDATE")
	private String POST_DIP_END_DATE;
	
	@Column(name="CUSTOMER")
	private String CUSTOMER;
	
	@Column(name="BUSINESS")
	private String BUSINESS;

	@Column(name="DIVISION")
	private String DIVISION;
	
	@Column(name="PRODUCT")
	private String PRODUCT;
	
	@Column(name="CATEGORY")
	private String CATEGORY;
	
	@Column(name="PROMOTION STATUS")
	private String PROMOTION_STATUS;
	
	@Column(name="INVESTMENT TYPE")
	private String INVESTMENT_TYPE;
	
	@Column(name="MOC")
	private String MOC;
	
	@Column(name="SUBMISSION DATE")
	private String SUBMISSION_DATE;
	
	@Column(name="APPROVED DATE")
	private String APPROVED_DATE;
	
	@Column(name="MODIFIED DATE")
	private String MODIFIED_DATE;
	
	@Column(name="PROMOTION TYPE")
	private String PROMOTION_TYPE;
	
	@Column(name="DURATION")
	private String DURATION;
	
	@Column(name="FREE PRODUCT NAME")
	private String FREE_PRODUCT_NAME;
	
	@Column(name="PRICE OFF")
	private String PRICE_OFF;
	
	@Column(name="BASELINE QUANTITY")
	private String BASELINE_QUANTITY;
	
	@Column(name="BASELINE GSV")
	private String BASELINE_GSV;
	
	@Column(name="BASELINE TURNOVER")
	private String BASELINE_TURNOVER;
	
	@Column(name="BASELINE GROSS PROFIT")
	private String BASELINE_GROSS_PROFIT;
	
	@Column(name="PROMOTION VOLUME BEFORE")
	private String PROMOTION_VOLUME_BEFORE;
	
	@Column(name="PROMOTION VOLUME DURING")
	private String PROMOTION_VOLUME_DURING;
	
	@Column(name="PROMOTION VOLUME AFTER")
	private String PROMOTION_VOLUME_AFTER;
	
	@Column(name="PLANNED GSV")
	private String PLANNED_GSV;
	
	@Column(name="PLANNED TURNOVER")
	private String PLANNED_TURNOVER;
	
	@Column(name="PLANNED INVESTMENT AMOUNT")
	private String PLANNED_INVESTMENT_AMOUNT;
	
	@Column(name="PLANNED UPLIFT")
	private String PLANNED_UPLIFT;

	
	@Column(name="PLANNED INCREMENTAL GROSS PROFIT")
	private String PLANNED_INCREMENTAL_GROSS_PROFIT;

	@Column(name="PLANNED GROSS PROFIT")
	private String PLANNED_GROSS_PROFIT;
	
	@Column(name="PLANNED INCREMENTAL TURNOVER")
	private String PLANNED_INCREMENTAL_TURNOVER;
	
	@Column(name="PLANNED CUSTOMER ROI")
	private String PLANNED_CUSTOMER_ROI;
	
	@Column(name="PLANNED COST PRICE BASED ROI")
	private String PLANNED_COST_PRICE_BASED_ROI;
	
	@Column(name="PLANNED PROMOTION ROI")
	private String PLANNED_PROMOTION_ROI;
	
	@Column(name="ACTUAL QUANTITY")
	private String ACTUAL_QUANTITY;
	
	@Column(name="ACTUAL GSV")
	private String ACTUAL_GSV;
	
	@Column(name="ACTUAL TURNOVER")
	private String ACTUAL_TURNOVER;
	
	@Column(name="ACTUAL INVESTMENT AMOUNT")
	private String ACTUAL_INVESTMENT_AMOUNT;
	
	@Column(name="ACTUAL UPLIFT")
	private String ACTUAL_UPLIFT;
	
	@Column(name="ACTUAL INCREMENTAL GROSS PROFIT")
	private String ACTUAL_INCREMENTAL_GROSS_PROFIT;
	
	@Column(name="ACTUAL GROSS PROFIT")
	private String ACTUAL_GROSS_PROFIT;
	
	@Column(name="ACTUAL INCREMENTAL TURNOVER")
	private String ACTUAL_INCREMENTAL_TURNOVER;
	
	@Column(name="ACTUAL CUSTOMER ROI")
	private String ACTUAL_CUSTOMER_ROI;
	
	@Column(name="ACTUAL COST PRICE BASED ROI")
	private String ACTUAL_COST_PRICE_BASED_ROI;
	
	@Column(name="ACTUAL PROMOTION ROI")
	private String ACTUAL_PROMOTION_ROI;
	
	@Column(name="UPLOAD REFERENCE NUMBER")
	private String UPLOAD_REFERENCE_NUMBER;
	
	@Column(name="IS DUPLICATE")
	private String IS_DUPLICATE;
	
	@Column(name="ERROR_MSG")
	private String ERROR_MSG;
	
	@Column(name="ROW_NO")
	private String ROW_NO;
	
	@Column(name="USER_ID")
	private String USER_ID;
	
	@Column(name="COE_REMARKS")
	private String COE_REMARKS;
	
	
	
	public String getCOE_REMARKS() {
		return COE_REMARKS;
	}

	public void setCOE_REMARKS(String cOE_REMARKS) {
		COE_REMARKS = cOE_REMARKS;
	}

	public String getPROMO_ID() {
		return PROMO_ID;
	}

	public void setPROMO_ID(String pROMO_ID) {
		PROMO_ID = pROMO_ID;
	}

	public String getPROMOTION_ID() {
		return PROMOTION_ID;
	}

	public void setPROMOTION_ID(String pROMOTION_ID) {
		PROMOTION_ID = pROMOTION_ID;
	}

	public String getPROMOTION_NAME() {
		return PROMOTION_NAME;
	}

	public void setPROMOTION_NAME(String pROMOTION_NAME) {
		PROMOTION_NAME = pROMOTION_NAME;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getCREATED_ON() {
		return CREATED_ON;
	}

	public void setCREATED_ON(String cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}

	public String getPROJECT_ID() {
		return PROJECT_ID;
	}

	public void setPROJECT_ID(String pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}

	public String getPROJECT_NAME() {
		return PROJECT_NAME;
	}

	public void setPROJECT_NAME(String pROJECT_NAME) {
		PROJECT_NAME = pROJECT_NAME;
	}

	public String getBUNDLE_ID() {
		return BUNDLE_ID;
	}

	public void setBUNDLE_ID(String bUNDLE_ID) {
		BUNDLE_ID = bUNDLE_ID;
	}

	public String getBUNDLE_NAME() {
		return BUNDLE_NAME;
	}

	public void setBUNDLE_NAME(String bUNDLE_NAME) {
		BUNDLE_NAME = bUNDLE_NAME;
	}

	public String getPROMOTION_QUALIFICATION() {
		return PROMOTION_QUALIFICATION;
	}

	public void setPROMOTION_QUALIFICATION(String pROMOTION_QUALIFICATION) {
		PROMOTION_QUALIFICATION = pROMOTION_QUALIFICATION;
	}

	public String getPROMOTION_OBJECTIVE() {
		return PROMOTION_OBJECTIVE;
	}

	public void setPROMOTION_OBJECTIVE(String pROMOTION_OBJECTIVE) {
		PROMOTION_OBJECTIVE = pROMOTION_OBJECTIVE;
	}

	public String getMARKETING_OBJECTIVE() {
		return MARKETING_OBJECTIVE;
	}

	public void setMARKETING_OBJECTIVE(String mARKETING_OBJECTIVE) {
		MARKETING_OBJECTIVE = mARKETING_OBJECTIVE;
	}

	public String getPROMOTION_MECHANICS() {
		return PROMOTION_MECHANICS;
	}

	public void setPROMOTION_MECHANICS(String pROMOTION_MECHANICS) {
		PROMOTION_MECHANICS = pROMOTION_MECHANICS;
	}

	public String getPROMOTION_START_DATE() {
		return PROMOTION_START_DATE;
	}

	public void setPROMOTION_START_DATE(String pROMOTION_START_DATE) {
		PROMOTION_START_DATE = pROMOTION_START_DATE;
	}

	public String getPRE_DIP_START_DATE() {
		return PRE_DIP_START_DATE;
	}

	public void setPRE_DIP_START_DATE(String pRE_DIP_START_DATE) {
		PRE_DIP_START_DATE = pRE_DIP_START_DATE;
	}

	public String getPOST_DIP_END_DATE() {
		return POST_DIP_END_DATE;
	}

	public void setPOST_DIP_END_DATE(String pOST_DIP_END_DATE) {
		POST_DIP_END_DATE = pOST_DIP_END_DATE;
	}

	public String getCUSTOMER() {
		return CUSTOMER;
	}

	public void setCUSTOMER(String cUSTOMER) {
		CUSTOMER = cUSTOMER;
	}

	public String getBUSINESS() {
		return BUSINESS;
	}

	public void setBUSINESS(String bUSINESS) {
		BUSINESS = bUSINESS;
	}

	public String getDIVISION() {
		return DIVISION;
	}

	public void setDIVISION(String dIVISION) {
		DIVISION = dIVISION;
	}

	public String getPRODUCT() {
		return PRODUCT;
	}

	public void setPRODUCT(String pRODUCT) {
		PRODUCT = pRODUCT;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getPROMOTION_STATUS() {
		return PROMOTION_STATUS;
	}

	public void setPROMOTION_STATUS(String pROMOTION_STATUS) {
		PROMOTION_STATUS = pROMOTION_STATUS;
	}

	public String getINVESTMENT_TYPE() {
		return INVESTMENT_TYPE;
	}

	public void setINVESTMENT_TYPE(String iNVESTMENT_TYPE) {
		INVESTMENT_TYPE = iNVESTMENT_TYPE;
	}

	public String getMOC() {
		return MOC;
	}

	public void setMOC(String mOC) {
		MOC = mOC;
	}

	public String getSUBMISSION_DATE() {
		return SUBMISSION_DATE;
	}

	public void setSUBMISSION_DATE(String sUBMISSION_DATE) {
		SUBMISSION_DATE = sUBMISSION_DATE;
	}

	public String getAPPROVED_DATE() {
		return APPROVED_DATE;
	}

	public void setAPPROVED_DATE(String aPPROVED_DATE) {
		APPROVED_DATE = aPPROVED_DATE;
	}

	public String getMODIFIED_DATE() {
		return MODIFIED_DATE;
	}

	public void setMODIFIED_DATE(String mODIFIED_DATE) {
		MODIFIED_DATE = mODIFIED_DATE;
	}

	public String getPROMOTION_TYPE() {
		return PROMOTION_TYPE;
	}

	public void setPROMOTION_TYPE(String pROMOTION_TYPE) {
		PROMOTION_TYPE = pROMOTION_TYPE;
	}

	public String getDURATION() {
		return DURATION;
	}

	public void setDURATION(String dURATION) {
		DURATION = dURATION;
	}

	public String getFREE_PRODUCT_NAME() {
		return FREE_PRODUCT_NAME;
	}

	public void setFREE_PRODUCT_NAME(String fREE_PRODUCT_NAME) {
		FREE_PRODUCT_NAME = fREE_PRODUCT_NAME;
	}

	public String getPRICE_OFF() {
		return PRICE_OFF;
	}

	public void setPRICE_OFF(String pRICE_OFF) {
		PRICE_OFF = pRICE_OFF;
	}

	public String getBASELINE_QUANTITY() {
		return BASELINE_QUANTITY;
	}

	public void setBASELINE_QUANTITY(String bASELINE_QUANTITY) {
		BASELINE_QUANTITY = bASELINE_QUANTITY;
	}

	public String getBASELINE_GSV() {
		return BASELINE_GSV;
	}

	public void setBASELINE_GSV(String bASELINE_GSV) {
		BASELINE_GSV = bASELINE_GSV;
	}

	public String getBASELINE_TURNOVER() {
		return BASELINE_TURNOVER;
	}

	public void setBASELINE_TURNOVER(String bASELINE_TURNOVER) {
		BASELINE_TURNOVER = bASELINE_TURNOVER;
	}

	public String getBASELINE_GROSS_PROFIT() {
		return BASELINE_GROSS_PROFIT;
	}

	public void setBASELINE_GROSS_PROFIT(String bASELINE_GROSS_PROFIT) {
		BASELINE_GROSS_PROFIT = bASELINE_GROSS_PROFIT;
	}

	public String getPROMOTION_VOLUME_BEFORE() {
		return PROMOTION_VOLUME_BEFORE;
	}

	public void setPROMOTION_VOLUME_BEFORE(String pROMOTION_VOLUME_BEFORE) {
		PROMOTION_VOLUME_BEFORE = pROMOTION_VOLUME_BEFORE;
	}

	public String getPROMOTION_VOLUME_DURING() {
		return PROMOTION_VOLUME_DURING;
	}

	public void setPROMOTION_VOLUME_DURING(String pROMOTION_VOLUME_DURING) {
		PROMOTION_VOLUME_DURING = pROMOTION_VOLUME_DURING;
	}

	public String getPROMOTION_VOLUME_AFTER() {
		return PROMOTION_VOLUME_AFTER;
	}

	public void setPROMOTION_VOLUME_AFTER(String pROMOTION_VOLUME_AFTER) {
		PROMOTION_VOLUME_AFTER = pROMOTION_VOLUME_AFTER;
	}

	public String getPLANNED_GSV() {
		return PLANNED_GSV;
	}

	public void setPLANNED_GSV(String pLANNED_GSV) {
		PLANNED_GSV = pLANNED_GSV;
	}

	public String getPLANNED_TURNOVER() {
		return PLANNED_TURNOVER;
	}

	public void setPLANNED_TURNOVER(String pLANNED_TURNOVER) {
		PLANNED_TURNOVER = pLANNED_TURNOVER;
	}

	public String getPLANNED_INVESTMENT_AMOUNT() {
		return PLANNED_INVESTMENT_AMOUNT;
	}

	public void setPLANNED_INVESTMENT_AMOUNT(String pLANNED_INVESTMENT_AMOUNT) {
		PLANNED_INVESTMENT_AMOUNT = pLANNED_INVESTMENT_AMOUNT;
	}

	public String getPLANNED_UPLIFT() {
		return PLANNED_UPLIFT;
	}

	public void setPLANNED_UPLIFT(String pLANNED_UPLIFT) {
		PLANNED_UPLIFT = pLANNED_UPLIFT;
	}

	public String getPLANNED_INCREMENTAL_GROSS_PROFIT() {
		return PLANNED_INCREMENTAL_GROSS_PROFIT;
	}

	public void setPLANNED_INCREMENTAL_GROSS_PROFIT(String pLANNED_INCREMENTAL_GROSS_PROFIT) {
		PLANNED_INCREMENTAL_GROSS_PROFIT = pLANNED_INCREMENTAL_GROSS_PROFIT;
	}

	public String getPLANNED_GROSS_PROFIT() {
		return PLANNED_GROSS_PROFIT;
	}

	public void setPLANNED_GROSS_PROFIT(String pLANNED_GROSS_PROFIT) {
		PLANNED_GROSS_PROFIT = pLANNED_GROSS_PROFIT;
	}

	public String getPLANNED_INCREMENTAL_TURNOVER() {
		return PLANNED_INCREMENTAL_TURNOVER;
	}

	public void setPLANNED_INCREMENTAL_TURNOVER(String pLANNED_INCREMENTAL_TURNOVER) {
		PLANNED_INCREMENTAL_TURNOVER = pLANNED_INCREMENTAL_TURNOVER;
	}

	public String getPLANNED_CUSTOMER_ROI() {
		return PLANNED_CUSTOMER_ROI;
	}

	public void setPLANNED_CUSTOMER_ROI(String pLANNED_CUSTOMER_ROI) {
		PLANNED_CUSTOMER_ROI = pLANNED_CUSTOMER_ROI;
	}

	public String getPLANNED_COST_PRICE_BASED_ROI() {
		return PLANNED_COST_PRICE_BASED_ROI;
	}

	public void setPLANNED_COST_PRICE_BASED_ROI(String pLANNED_COST_PRICE_BASED_ROI) {
		PLANNED_COST_PRICE_BASED_ROI = pLANNED_COST_PRICE_BASED_ROI;
	}

	public String getPLANNED_PROMOTION_ROI() {
		return PLANNED_PROMOTION_ROI;
	}

	public void setPLANNED_PROMOTION_ROI(String pLANNED_PROMOTION_ROI) {
		PLANNED_PROMOTION_ROI = pLANNED_PROMOTION_ROI;
	}

	public String getACTUAL_QUANTITY() {
		return ACTUAL_QUANTITY;
	}

	public void setACTUAL_QUANTITY(String aCTUAL_QUANTITY) {
		ACTUAL_QUANTITY = aCTUAL_QUANTITY;
	}

	public String getACTUAL_GSV() {
		return ACTUAL_GSV;
	}

	public void setACTUAL_GSV(String aCTUAL_GSV) {
		ACTUAL_GSV = aCTUAL_GSV;
	}

	public String getACTUAL_TURNOVER() {
		return ACTUAL_TURNOVER;
	}

	public void setACTUAL_TURNOVER(String aCTUAL_TURNOVER) {
		ACTUAL_TURNOVER = aCTUAL_TURNOVER;
	}

	public String getACTUAL_INVESTMENT_AMOUNT() {
		return ACTUAL_INVESTMENT_AMOUNT;
	}

	public void setACTUAL_INVESTMENT_AMOUNT(String aCTUAL_INVESTMENT_AMOUNT) {
		ACTUAL_INVESTMENT_AMOUNT = aCTUAL_INVESTMENT_AMOUNT;
	}

	public String getACTUAL_UPLIFT() {
		return ACTUAL_UPLIFT;
	}

	public void setACTUAL_UPLIFT(String aCTUAL_UPLIFT) {
		ACTUAL_UPLIFT = aCTUAL_UPLIFT;
	}

	public String getACTUAL_INCREMENTAL_GROSS_PROFIT() {
		return ACTUAL_INCREMENTAL_GROSS_PROFIT;
	}

	public void setACTUAL_INCREMENTAL_GROSS_PROFIT(String aCTUAL_INCREMENTAL_GROSS_PROFIT) {
		ACTUAL_INCREMENTAL_GROSS_PROFIT = aCTUAL_INCREMENTAL_GROSS_PROFIT;
	}

	public String getACTUAL_GROSS_PROFIT() {
		return ACTUAL_GROSS_PROFIT;
	}

	public void setACTUAL_GROSS_PROFIT(String aCTUAL_GROSS_PROFIT) {
		ACTUAL_GROSS_PROFIT = aCTUAL_GROSS_PROFIT;
	}

	public String getACTUAL_INCREMENTAL_TURNOVER() {
		return ACTUAL_INCREMENTAL_TURNOVER;
	}

	public void setACTUAL_INCREMENTAL_TURNOVER(String aCTUAL_INCREMENTAL_TURNOVER) {
		ACTUAL_INCREMENTAL_TURNOVER = aCTUAL_INCREMENTAL_TURNOVER;
	}

	public String getACTUAL_CUSTOMER_ROI() {
		return ACTUAL_CUSTOMER_ROI;
	}

	public void setACTUAL_CUSTOMER_ROI(String aCTUAL_CUSTOMER_ROI) {
		ACTUAL_CUSTOMER_ROI = aCTUAL_CUSTOMER_ROI;
	}

	public String getACTUAL_COST_PRICE_BASED_ROI() {
		return ACTUAL_COST_PRICE_BASED_ROI;
	}

	public void setACTUAL_COST_PRICE_BASED_ROI(String aCTUAL_COST_PRICE_BASED_ROI) {
		ACTUAL_COST_PRICE_BASED_ROI = aCTUAL_COST_PRICE_BASED_ROI;
	}

	public String getACTUAL_PROMOTION_ROI() {
		return ACTUAL_PROMOTION_ROI;
	}

	public void setACTUAL_PROMOTION_ROI(String aCTUAL_PROMOTION_ROI) {
		ACTUAL_PROMOTION_ROI = aCTUAL_PROMOTION_ROI;
	}

	public String getUPLOAD_REFERENCE_NUMBER() {
		return UPLOAD_REFERENCE_NUMBER;
	}

	public void setUPLOAD_REFERENCE_NUMBER(String uPLOAD_REFERENCE_NUMBER) {
		UPLOAD_REFERENCE_NUMBER = uPLOAD_REFERENCE_NUMBER;
	}

	public String getIS_DUPLICATE() {
		return IS_DUPLICATE;
	}

	public void setIS_DUPLICATE(String iS_DUPLICATE) {
		IS_DUPLICATE = iS_DUPLICATE;
	}

	public String getERROR_MSG() {
		return ERROR_MSG;
	}

	public void setERROR_MSG(String eRROR_MSG) {
		ERROR_MSG = eRROR_MSG;
	}

	public String getROW_NO() {
		return ROW_NO;
	}

	public void setROW_NO(String rOW_NO) {
		ROW_NO = rOW_NO;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

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

	
	

	public String getPROMOTION_END_DATE() {
		return PROMOTION_END_DATE;
	}

	public void setPROMOTION_END_DATE(String pROMOTION_END_DATE) {
		PROMOTION_END_DATE = pROMOTION_END_DATE;
	}

	@Override
	public String toString() {
		return "PPMLinkageBean [file=" + file + ", version_promo_id=" + version_promo_id + ", chart_by_type="
				+ chart_by_type + ", promo_creator=" + promo_creator + ", promo_status=" + promo_status + ", PROMO_ID="
				+ PROMO_ID + ", promo_name=" + promo_name + ", sell_in_start_date=" + sell_in_start_date
				+ ", sell_in_end_date=" + sell_in_end_date + ", promo_mechanics=" + promo_mechanics
				+ ", investment_type=" + investment_type + ", cluster_code=" + cluster_code + ", cluster_name="
				+ cluster_name + ", basepack_code=" + basepack_code + ", basepack_name=" + basepack_name + ", category="
				+ category + ", brand=" + brand + ", sub_brand=" + sub_brand + ", uom=" + uom + ", tax=" + tax
				+ ", discount=" + discount + ", list_price=" + list_price + ", percent_promoted_volume="
				+ percent_promoted_volume + ", quantity=" + quantity + ", budget_holder_name=" + budget_holder_name
				+ ", fund_type=" + fund_type + ", PROMOTION_ID=" + PROMOTION_ID + ", PROMOTION_NAME=" + PROMOTION_NAME
				+ ", CREATED_BY=" + CREATED_BY + ", CREATED_ON=" + CREATED_ON + ", PROJECT_ID=" + PROJECT_ID
				+ ", PROJECT_NAME=" + PROJECT_NAME + ", BUNDLE_ID=" + BUNDLE_ID + ", BUNDLE_NAME=" + BUNDLE_NAME
				+ ", PROMOTION_QUALIFICATION=" + PROMOTION_QUALIFICATION + ", PROMOTION_OBJECTIVE="
				+ PROMOTION_OBJECTIVE + ", MARKETING_OBJECTIVE=" + MARKETING_OBJECTIVE + ", PROMOTION_MECHANICS="
				+ PROMOTION_MECHANICS + ", PROMOTION_START_DATE=" + PROMOTION_START_DATE + ", PROMOTION_END_DATE="
				+ PROMOTION_END_DATE + ", moc=" + moc + ", investment_amount=" + investment_amount
				+ ", PRE_DIP_START_DATE=" + PRE_DIP_START_DATE + ", POST_DIP_END_DATE=" + POST_DIP_END_DATE
				+ ", CUSTOMER=" + CUSTOMER + ", BUSINESS=" + BUSINESS + ", DIVISION=" + DIVISION + ", PRODUCT="
				+ PRODUCT + ", CATEGORY=" + CATEGORY + ", PROMOTION_STATUS=" + PROMOTION_STATUS + ", INVESTMENT_TYPE="
				+ INVESTMENT_TYPE + ", MOC=" + MOC + ", SUBMISSION_DATE=" + SUBMISSION_DATE + ", APPROVED_DATE="
				+ APPROVED_DATE + ", MODIFIED_DATE=" + MODIFIED_DATE + ", PROMOTION_TYPE=" + PROMOTION_TYPE
				+ ", DURATION=" + DURATION + ", FREE_PRODUCT_NAME=" + FREE_PRODUCT_NAME + ", PRICE_OFF=" + PRICE_OFF
				+ ", BASELINE_QUANTITY=" + BASELINE_QUANTITY + ", BASELINE_GSV=" + BASELINE_GSV + ", BASELINE_TURNOVER="
				+ BASELINE_TURNOVER + ", BASELINE_GROSS_PROFIT=" + BASELINE_GROSS_PROFIT + ", PROMOTION_VOLUME_BEFORE="
				+ PROMOTION_VOLUME_BEFORE + ", PROMOTION_VOLUME_DURING=" + PROMOTION_VOLUME_DURING
				+ ", PROMOTION_VOLUME_AFTER=" + PROMOTION_VOLUME_AFTER + ", PLANNED_GSV=" + PLANNED_GSV
				+ ", PLANNED_TURNOVER=" + PLANNED_TURNOVER + ", PLANNED_INVESTMENT_AMOUNT=" + PLANNED_INVESTMENT_AMOUNT
				+ ", PLANNED_UPLIFT=" + PLANNED_UPLIFT + ", PLANNED_INCREMENTAL_GROSS_PROFIT="
				+ PLANNED_INCREMENTAL_GROSS_PROFIT + ", PLANNED_GROSS_PROFIT=" + PLANNED_GROSS_PROFIT
				+ ", PLANNED_INCREMENTAL_TURNOVER=" + PLANNED_INCREMENTAL_TURNOVER + ", PLANNED_CUSTOMER_ROI="
				+ PLANNED_CUSTOMER_ROI + ", PLANNED_COST_PRICE_BASED_ROI=" + PLANNED_COST_PRICE_BASED_ROI
				+ ", PLANNED_PROMOTION_ROI=" + PLANNED_PROMOTION_ROI + ", ACTUAL_QUANTITY=" + ACTUAL_QUANTITY
				+ ", ACTUAL_GSV=" + ACTUAL_GSV + ", ACTUAL_TURNOVER=" + ACTUAL_TURNOVER + ", ACTUAL_INVESTMENT_AMOUNT="
				+ ACTUAL_INVESTMENT_AMOUNT + ", ACTUAL_UPLIFT=" + ACTUAL_UPLIFT + ", ACTUAL_INCREMENTAL_GROSS_PROFIT="
				+ ACTUAL_INCREMENTAL_GROSS_PROFIT + ", ACTUAL_GROSS_PROFIT=" + ACTUAL_GROSS_PROFIT
				+ ", ACTUAL_INCREMENTAL_TURNOVER=" + ACTUAL_INCREMENTAL_TURNOVER + ", ACTUAL_CUSTOMER_ROI="
				+ ACTUAL_CUSTOMER_ROI + ", ACTUAL_COST_PRICE_BASED_ROI=" + ACTUAL_COST_PRICE_BASED_ROI
				+ ", ACTUAL_PROMOTION_ROI=" + ACTUAL_PROMOTION_ROI + ", UPLOAD_REFERENCE_NUMBER="
				+ UPLOAD_REFERENCE_NUMBER + ", IS_DUPLICATE=" + IS_DUPLICATE + ", ERROR_MSG=" + ERROR_MSG + ", ROW_NO="
				+ ROW_NO + ", USER_ID=" + USER_ID + ", COE_REMARKS=" + COE_REMARKS + "]";
	}
	
	
}
