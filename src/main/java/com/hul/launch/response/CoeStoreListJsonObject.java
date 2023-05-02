package com.hul.launch.response;

import java.util.ArrayList;
import java.util.List;

import com.hul.proco.controller.listingPromo.PromoListingBean;

public class CoeStoreListJsonObject {
	
	int iTotalRecords;
	int iTotalDisplayRecords;
	String sEcho;
	String sColumns;
	private List<CoeLaunchStoreListResponse> aaData;
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public List<CoeLaunchStoreListResponse> getJsonBean() {
		return aaData;
	}
	
	public void setJsonBean(List<CoeLaunchStoreListResponse> aaData) {
		this.aaData = aaData;
	}
	
	
	@Override
	public String toString() {
		return "CoeStoreListJsonObject [iTotalRecords=" + iTotalRecords + ", iTotalDisplayRecords="
				+ iTotalDisplayRecords + ", sEcho=" + sEcho + ", sColumns=" + sColumns + ", aaData=" + aaData + "]";
	}
	
	
}
