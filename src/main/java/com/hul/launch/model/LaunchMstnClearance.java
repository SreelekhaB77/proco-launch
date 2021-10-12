package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

public class LaunchMstnClearance {
	private MultipartFile file;
	private String LAUNCH_ID;
	private String CLUSTER;
	private String LAUNCH_NAME;
	private String LAUNCH_MOC;
	private String BASEPACK_CODE;
	private String BASEPACK_DESCRIPTION;
	private String DEPOT;
	private String MSTN_CLEARED;
	private String FINAL_CLD_N;
	private String FINAL_CLD_N1;
	private String FINAL_CLD_N2;
	private String CURRENT_ESTIMATES;
	private String CLEARANCE_DATE;
	private String REMARKS; // Added by harsha as a part of Q5 fix

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(String aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	private String ACCOUNT;

	public String getCLUSTER() {
		return CLUSTER;
	}

	public void setCLUSTER(String cLUSTER) {
		CLUSTER = cLUSTER;
	}

	public String getLAUNCH_ID() {
		return LAUNCH_ID;
	}

	public void setLAUNCH_ID(String lAUNCH_ID) {
		LAUNCH_ID = lAUNCH_ID;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getLAUNCH_NAME() {
		return LAUNCH_NAME;
	}

	public void setLAUNCH_NAME(String lAUNCH_NAME) {
		LAUNCH_NAME = lAUNCH_NAME;
	}

	public String getLAUNCH_MOC() {
		return LAUNCH_MOC;
	}

	public void setLAUNCH_MOC(String lAUNCH_MOC) {
		LAUNCH_MOC = lAUNCH_MOC;
	}

	public String getBASEPACK_CODE() {
		return BASEPACK_CODE;
	}

	public void setBASEPACK_CODE(String bASEPACK_CODE) {
		BASEPACK_CODE = bASEPACK_CODE;
	}

	public String getBASEPACK_DESCRIPTION() {
		return BASEPACK_DESCRIPTION;
	}

	public void setBASEPACK_DESCRIPTION(String bASEPACK_DESCRIPTION) {
		BASEPACK_DESCRIPTION = bASEPACK_DESCRIPTION;
	}

	public String getDEPOT() {
		return DEPOT;
	}

	public void setDEPOT(String dEPOT) {
		DEPOT = dEPOT;
	}

	public String getMSTN_CLEARED() {
		return MSTN_CLEARED;
	}

	public void setMSTN_CLEARED(String mSTN_CLEARED) {
		MSTN_CLEARED = mSTN_CLEARED;
	}

	public String getFINAL_CLD_N() {
		return FINAL_CLD_N;
	}

	public void setFINAL_CLD_N(String fINAL_CLD_N) {
		FINAL_CLD_N = fINAL_CLD_N;
	}

	public String getFINAL_CLD_N1() {
		return FINAL_CLD_N1;
	}

	public void setFINAL_CLD_N1(String fINAL_CLD_N1) {
		FINAL_CLD_N1 = fINAL_CLD_N1;
	}

	public String getFINAL_CLD_N2() {
		return FINAL_CLD_N2;
	}

	public void setFINAL_CLD_N2(String fINAL_CLD_N2) {
		FINAL_CLD_N2 = fINAL_CLD_N2;
	}

	public String getCURRENT_ESTIMATES() {
		return CURRENT_ESTIMATES;
	}

	public void setCURRENT_ESTIMATES(String cURRENT_ESTIMATES) {
		CURRENT_ESTIMATES = cURRENT_ESTIMATES;
	}

	public String getCLEARANCE_DATE() {
		return CLEARANCE_DATE;
	}

	public void setCLEARANCE_DATE(String cLEARANCE_DATE) {
		CLEARANCE_DATE = cLEARANCE_DATE;
	}
}