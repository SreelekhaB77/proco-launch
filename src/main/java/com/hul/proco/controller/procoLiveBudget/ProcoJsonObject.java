package com.hul.proco.controller.procoLiveBudget;

import java.util.List;

import com.hul.proco.controller.promocr.PromoCrBean;

public class ProcoJsonObject {


	int iTotalRecords;
	int iTotalDisplayRecords;
	String sEcho;
	String sColumns;
	private List<BudgetHolderBean> aaData;
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
	public List<BudgetHolderBean> getAaData() {
		return aaData;
	}
	public void setAaData(List<BudgetHolderBean> aaData) {
		this.aaData = aaData;
	}
	@Override
	public String toString() {
		return "ProcoJsonObject [iTotalRecords=" + iTotalRecords + ", iTotalDisplayRecords=" + iTotalDisplayRecords
				+ ", sEcho=" + sEcho + ", sColumns=" + sColumns + ", aaData=" + aaData + "]";
	}
	public List<BudgetHolderBean> getJsonBean() {
		return aaData;
	}

	public void setJsonBean(List<BudgetHolderBean> aaData) {
		this.aaData = aaData;
	}
	
	
}
