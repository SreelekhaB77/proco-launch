package com.hul.proco.controller.collaboration;

import java.util.List;

public class CollaborationJsonObject {

	int iTotalRecords;
	int iTotalDisplayRecords;
	String sEcho;
	String sColumns;
	private List<DisplayCollaborationBean> aaData;

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

	public List<DisplayCollaborationBean> getAaData() {
		return aaData;
	}

	public void setAaData(List<DisplayCollaborationBean> aaData) {
		this.aaData = aaData;
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
	
}
