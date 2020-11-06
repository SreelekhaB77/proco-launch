package com.hul.launch.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class LaunchBuildUpTemp implements Serializable {

	private static final long serialVersionUID = 1L;
	private int FINAL_BUILDUP_ID;
	private int LAUNCH_ID;
	private String REPORTING_CODE;
	private String HFS_CODE;
	private String FMCG_CSP_CODE;
	private String FMCG_SITE_CODE;
	private String MODIFIED_CHAIN;
	private String CHANNEL;
	private String ACCOUNT_FORMAT_NAME;
	private String ACCOUNT_NAME;
	private String HUL_STORE_FORMAT;
	private String CUSTOMER_STORE_FORMAT;
	private String UKEY;
	private String FMCG_SUPPLY_TYPE;
	private String BRANCH_CODE;
	private String STATE_CODE;
	private String TOWN_NAME;
	private String DEPOT;
	private String CLUSTER;
	private String UNIT_OF_MEASUREMENT;
	private String SKU_COUNT;
	private String SKU_NAME;
	private String CLD_SIZE;
	private String MRP;
	private String GSV;
	private String BASEPACK_CODE;
	private String REMARKS;
	private String ROTATIONS;
	private String VISI_CHECK;
	private String VISI_ELEMENT_1;
	private String VISI_ELEMENT_2;
	private String VISI_ELEMENT_3;
	private String VISI_ELEMENT_4;
	private String VISI_ELEMENT_5;
	private String UPLIFTN1;
	private String UPLIFTN2;
	private String SELLIN_N;
	private String SELLIN_N1;
	private String SELLIN_N2;
	private String VISI_SELLIN_N;
	private String VISI_SELLIN_N1;
	private String VISI_SELLIN_N2;
	private String REVISED_SELLIN_FOR_STORE_N;
	private String REVISED_SELLIN_FOR_STORE_N1;
	private String REVISED_SELLIN_FOR_STORE_N2;
	private String SELLIN_VALUE_N;
	private String SELLIN_VALUE_N1;
	private String SELLIN_VALUE_N2;
	private String SELLIN_VALUE_CLD_N;
	private String SELLIN_VALUE_CLD_N1;
	private String SELLIN_VALUE_CLD_N2;
	private String SELLIN_UNITS_N;
	private String SELLIN_UNITS_N1;
	private String SELLIN_UNITS_N2;
	private String CREATED_BY;
	private Date CREATED_DATE;
	private String UPDATED_BY;
	private Date UPDATED_DATE;
	private String STORE_COUNT;
	private String error;
	private String LAUNCH_NAME;

	public String getSTORE_COUNT() {
		return STORE_COUNT;
	}

	public void setSTORE_COUNT(String sTORE_COUNT) {
		STORE_COUNT = sTORE_COUNT;
	}

	public String getLAUNCH_NAME() {
		return LAUNCH_NAME;
	}

	public void setLAUNCH_NAME(String lAUNCH_NAME) {
		LAUNCH_NAME = lAUNCH_NAME;
	}

	public String getSELLIN_VALUE_CLD_N() {
		return SELLIN_VALUE_CLD_N;
	}

	public void setSELLIN_VALUE_CLD_N(String sELLIN_VALUE_CLD_N) {
		SELLIN_VALUE_CLD_N = sELLIN_VALUE_CLD_N;
	}

	public String getSELLIN_VALUE_CLD_N1() {
		return SELLIN_VALUE_CLD_N1;
	}

	public void setSELLIN_VALUE_CLD_N1(String sELLIN_VALUE_CLD_N1) {
		SELLIN_VALUE_CLD_N1 = sELLIN_VALUE_CLD_N1;
	}

	public String getSELLIN_VALUE_CLD_N2() {
		return SELLIN_VALUE_CLD_N2;
	}

	public void setSELLIN_VALUE_CLD_N2(String sELLIN_VALUE_CLD_N2) {
		SELLIN_VALUE_CLD_N2 = sELLIN_VALUE_CLD_N2;
	}

	public String getSELLIN_UNITS_N() {
		return SELLIN_UNITS_N;
	}

	public void setSELLIN_UNITS_N(String sELLIN_UNITS_N) {
		SELLIN_UNITS_N = sELLIN_UNITS_N;
	}

	public String getSELLIN_UNITS_N1() {
		return SELLIN_UNITS_N1;
	}

	public void setSELLIN_UNITS_N1(String sELLIN_UNITS_N1) {
		SELLIN_UNITS_N1 = sELLIN_UNITS_N1;
	}

	public String getSELLIN_UNITS_N2() {
		return SELLIN_UNITS_N2;
	}

	public void setSELLIN_UNITS_N2(String sELLIN_UNITS_N2) {
		SELLIN_UNITS_N2 = sELLIN_UNITS_N2;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getFINAL_BUILDUP_ID() {
		return FINAL_BUILDUP_ID;
	}

	public void setFINAL_BUILDUP_ID(int fINAL_BUILDUP_ID) {
		FINAL_BUILDUP_ID = fINAL_BUILDUP_ID;
	}

	public int getLAUNCH_ID() {
		return LAUNCH_ID;
	}

	public void setLAUNCH_ID(int lAUNCH_ID) {
		LAUNCH_ID = lAUNCH_ID;
	}

	public String getREPORTING_CODE() {
		return REPORTING_CODE;
	}

	public void setREPORTING_CODE(String rEPORTING_CODE) {
		REPORTING_CODE = rEPORTING_CODE;
	}

	public String getHFS_CODE() {
		return HFS_CODE;
	}

	public void setHFS_CODE(String hFS_CODE) {
		HFS_CODE = hFS_CODE;
	}

	public String getFMCG_CSP_CODE() {
		return FMCG_CSP_CODE;
	}

	public void setFMCG_CSP_CODE(String fMCG_CSP_CODE) {
		FMCG_CSP_CODE = fMCG_CSP_CODE;
	}

	public String getFMCG_SITE_CODE() {
		return FMCG_SITE_CODE;
	}

	public void setFMCG_SITE_CODE(String fMCG_SITE_CODE) {
		FMCG_SITE_CODE = fMCG_SITE_CODE;
	}

	public String getMODIFIED_CHAIN() {
		return MODIFIED_CHAIN;
	}

	public void setMODIFIED_CHAIN(String mODIFIED_CHAIN) {
		MODIFIED_CHAIN = mODIFIED_CHAIN;
	}

	public String getCHANNEL() {
		return CHANNEL;
	}

	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}

	public String getACCOUNT_FORMAT_NAME() {
		return ACCOUNT_FORMAT_NAME;
	}

	public void setACCOUNT_FORMAT_NAME(String aCCOUNT_FORMAT_NAME) {
		ACCOUNT_FORMAT_NAME = aCCOUNT_FORMAT_NAME;
	}

	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}

	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}

	public String getHUL_STORE_FORMAT() {
		return HUL_STORE_FORMAT;
	}

	public void setHUL_STORE_FORMAT(String hUL_STORE_FORMAT) {
		HUL_STORE_FORMAT = hUL_STORE_FORMAT;
	}

	public String getCUSTOMER_STORE_FORMAT() {
		return CUSTOMER_STORE_FORMAT;
	}

	public void setCUSTOMER_STORE_FORMAT(String cUSTOMER_STORE_FORMAT) {
		CUSTOMER_STORE_FORMAT = cUSTOMER_STORE_FORMAT;
	}

	public String getUKEY() {
		return UKEY;
	}

	public void setUKEY(String uKEY) {
		UKEY = uKEY;
	}

	public String getFMCG_SUPPLY_TYPE() {
		return FMCG_SUPPLY_TYPE;
	}

	public void setFMCG_SUPPLY_TYPE(String fMCG_SUPPLY_TYPE) {
		FMCG_SUPPLY_TYPE = fMCG_SUPPLY_TYPE;
	}

	public String getBRANCH_CODE() {
		return BRANCH_CODE;
	}

	public void setBRANCH_CODE(String bRANCH_CODE) {
		BRANCH_CODE = bRANCH_CODE;
	}

	public String getSTATE_CODE() {
		return STATE_CODE;
	}

	public void setSTATE_CODE(String sTATE_CODE) {
		STATE_CODE = sTATE_CODE;
	}

	public String getTOWN_NAME() {
		return TOWN_NAME;
	}

	public void setTOWN_NAME(String tOWN_NAME) {
		TOWN_NAME = tOWN_NAME;
	}

	public String getDEPOT() {
		return DEPOT;
	}

	public void setDEPOT(String dEPOT) {
		DEPOT = dEPOT;
	}

	public String getCLUSTER() {
		return CLUSTER;
	}

	public void setCLUSTER(String cLUSTER) {
		CLUSTER = cLUSTER;
	}

	public String getUNIT_OF_MEASUREMENT() {
		return UNIT_OF_MEASUREMENT;
	}

	public void setUNIT_OF_MEASUREMENT(String uNIT_OF_MEASUREMENT) {
		UNIT_OF_MEASUREMENT = uNIT_OF_MEASUREMENT;
	}

	public String getSKU_COUNT() {
		return SKU_COUNT;
	}

	public void setSKU_COUNT(String sKU_COUNT) {
		SKU_COUNT = sKU_COUNT;
	}

	public String getSKU_NAME() {
		return SKU_NAME;
	}

	public void setSKU_NAME(String sKU_NAME) {
		SKU_NAME = sKU_NAME;
	}

	public String getCLD_SIZE() {
		return CLD_SIZE;
	}

	public void setCLD_SIZE(String cLD_SIZE) {
		CLD_SIZE = cLD_SIZE;
	}

	public String getMRP() {
		return MRP;
	}

	public void setMRP(String mRP) {
		MRP = mRP;
	}

	public String getGSV() {
		return GSV;
	}

	public void setGSV(String gSV) {
		GSV = gSV;
	}

	public String getBASEPACK_CODE() {
		return BASEPACK_CODE;
	}

	public void setBASEPACK_CODE(String bASEPACK_CODE) {
		BASEPACK_CODE = bASEPACK_CODE;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getROTATIONS() {
		return ROTATIONS;
	}

	public void setROTATIONS(String rOTATIONS) {
		ROTATIONS = rOTATIONS;
	}

	public String getVISI_CHECK() {
		return VISI_CHECK;
	}

	public void setVISI_CHECK(String vISI_CHECK) {
		VISI_CHECK = vISI_CHECK;
	}

	public String getVISI_ELEMENT_1() {
		return VISI_ELEMENT_1;
	}

	public void setVISI_ELEMENT_1(String vISI_ELEMENT_1) {
		VISI_ELEMENT_1 = vISI_ELEMENT_1;
	}

	public String getVISI_ELEMENT_2() {
		return VISI_ELEMENT_2;
	}

	public void setVISI_ELEMENT_2(String vISI_ELEMENT_2) {
		VISI_ELEMENT_2 = vISI_ELEMENT_2;
	}

	public String getVISI_ELEMENT_3() {
		return VISI_ELEMENT_3;
	}

	public void setVISI_ELEMENT_3(String vISI_ELEMENT_3) {
		VISI_ELEMENT_3 = vISI_ELEMENT_3;
	}

	public String getVISI_ELEMENT_4() {
		return VISI_ELEMENT_4;
	}

	public void setVISI_ELEMENT_4(String vISI_ELEMENT_4) {
		VISI_ELEMENT_4 = vISI_ELEMENT_4;
	}

	public String getVISI_ELEMENT_5() {
		return VISI_ELEMENT_5;
	}

	public void setVISI_ELEMENT_5(String vISI_ELEMENT_5) {
		VISI_ELEMENT_5 = vISI_ELEMENT_5;
	}

	public String getUPLIFTN1() {
		return UPLIFTN1;
	}

	public void setUPLIFTN1(String uPLIFTN1) {
		UPLIFTN1 = uPLIFTN1;
	}

	public String getUPLIFTN2() {
		return UPLIFTN2;
	}

	public void setUPLIFTN2(String uPLIFTN2) {
		UPLIFTN2 = uPLIFTN2;
	}

	public String getSELLIN_N() {
		return SELLIN_N;
	}

	public void setSELLIN_N(String sELLIN_N) {
		SELLIN_N = sELLIN_N;
	}

	public String getSELLIN_N1() {
		return SELLIN_N1;
	}

	public void setSELLIN_N1(String sELLIN_N1) {
		SELLIN_N1 = sELLIN_N1;
	}

	public String getSELLIN_N2() {
		return SELLIN_N2;
	}

	public void setSELLIN_N2(String sELLIN_N2) {
		SELLIN_N2 = sELLIN_N2;
	}

	public String getVISI_SELLIN_N() {
		return VISI_SELLIN_N;
	}

	public void setVISI_SELLIN_N(String vISI_SELLIN_N) {
		VISI_SELLIN_N = vISI_SELLIN_N;
	}

	public String getVISI_SELLIN_N1() {
		return VISI_SELLIN_N1;
	}

	public void setVISI_SELLIN_N1(String vISI_SELLIN_N1) {
		VISI_SELLIN_N1 = vISI_SELLIN_N1;
	}

	public String getVISI_SELLIN_N2() {
		return VISI_SELLIN_N2;
	}

	public void setVISI_SELLIN_N2(String vISI_SELLIN_N2) {
		VISI_SELLIN_N2 = vISI_SELLIN_N2;
	}

	public String getREVISED_SELLIN_FOR_STORE_N() {
		return REVISED_SELLIN_FOR_STORE_N;
	}

	public void setREVISED_SELLIN_FOR_STORE_N(String rEVISED_SELLIN_FOR_STORE_N) {
		REVISED_SELLIN_FOR_STORE_N = rEVISED_SELLIN_FOR_STORE_N;
	}

	public String getREVISED_SELLIN_FOR_STORE_N1() {
		return REVISED_SELLIN_FOR_STORE_N1;
	}

	public void setREVISED_SELLIN_FOR_STORE_N1(String rEVISED_SELLIN_FOR_STORE_N1) {
		REVISED_SELLIN_FOR_STORE_N1 = rEVISED_SELLIN_FOR_STORE_N1;
	}

	public String getREVISED_SELLIN_FOR_STORE_N2() {
		return REVISED_SELLIN_FOR_STORE_N2;
	}

	public void setREVISED_SELLIN_FOR_STORE_N2(String rEVISED_SELLIN_FOR_STORE_N2) {
		REVISED_SELLIN_FOR_STORE_N2 = rEVISED_SELLIN_FOR_STORE_N2;
	}

	public String getSELLIN_VALUE_N() {
		return SELLIN_VALUE_N;
	}

	public void setSELLIN_VALUE_N(String sELLIN_VALUE_N) {
		SELLIN_VALUE_N = sELLIN_VALUE_N;
	}

	public String getSELLIN_VALUE_N1() {
		return SELLIN_VALUE_N1;
	}

	public void setSELLIN_VALUE_N1(String sELLIN_VALUE_N1) {
		SELLIN_VALUE_N1 = sELLIN_VALUE_N1;
	}

	public String getSELLIN_VALUE_N2() {
		return SELLIN_VALUE_N2;
	}

	public void setSELLIN_VALUE_N2(String sELLIN_VALUE_N2) {
		SELLIN_VALUE_N2 = sELLIN_VALUE_N2;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public Date getUPDATED_DATE() {
		return UPDATED_DATE;
	}

	public void setUPDATED_DATE(Date uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}

}