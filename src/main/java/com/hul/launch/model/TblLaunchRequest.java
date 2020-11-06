package com.hul.launch.model;

public class TblLaunchRequest {
	private String REQ_ID;
	private String LAUNCH_ID;
	private String CHANGES_REQUIRED;
	private String KAM_REMARKS;
	private String REQ_DATE;
	private String REQ_TYPE;
	private String CREATED_DATE;
	private String CREATED_BY;
	private String UPDATED_DATE;
	private String UPDATED_BY;
	private String FINAL_STATUS;
	private String TME_REMARKS;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getREQ_ID() {
		return REQ_ID;
	}

	public void setREQ_ID(String rEQ_ID) {
		REQ_ID = rEQ_ID;
	}

	public String getLAUNCH_ID() {
		return LAUNCH_ID;
	}

	public void setLAUNCH_ID(String lAUNCH_ID) {
		LAUNCH_ID = lAUNCH_ID;
	}

	public String getCHANGES_REQUIRED() {
		return CHANGES_REQUIRED;
	}

	public void setCHANGES_REQUIRED(String cHANGES_REQUIRED) {
		CHANGES_REQUIRED = cHANGES_REQUIRED;
	}

	public String getKAM_REMARKS() {
		return KAM_REMARKS;
	}

	public void setKAM_REMARKS(String kAM_REMARKS) {
		KAM_REMARKS = kAM_REMARKS;
	}

	public String getREQ_DATE() {
		return REQ_DATE;
	}

	public void setREQ_DATE(String rEQ_DATE) {
		REQ_DATE = rEQ_DATE;
	}

	public String getREQ_TYPE() {
		return REQ_TYPE;
	}

	public void setREQ_TYPE(String rEQ_TYPE) {
		REQ_TYPE = rEQ_TYPE;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public String getUPDATED_DATE() {
		return UPDATED_DATE;
	}

	public void setUPDATED_DATE(String uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public String getFINAL_STATUS() {
		return FINAL_STATUS;
	}

	public void setFINAL_STATUS(String fINAL_STATUS) {
		FINAL_STATUS = fINAL_STATUS;
	}

	public String getTME_REMARKS() {
		return TME_REMARKS;
	}

	public void setTME_REMARKS(String tME_REMARKS) {
		TME_REMARKS = tME_REMARKS;
	}
}