package com.hul.proco.controller.promocr;

import java.util.List;

public class PromoCrJsonObject {

	int iTotalRecords;
	int iTotalDisplayRecords;
	String sEcho;
	String sColumns;
	private List<PromoCrBean> aaData;

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

	public List<PromoCrBean> getJsonBean() {
		return aaData;
	}

	public void setJsonBean(List<PromoCrBean> aaData) {
		this.aaData = aaData;
	}

}
