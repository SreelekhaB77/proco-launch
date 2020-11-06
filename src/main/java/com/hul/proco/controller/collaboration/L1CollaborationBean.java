package com.hul.proco.controller.collaboration;

import org.springframework.web.multipart.MultipartFile;

import com.hul.proco.excelreader.exom.annotation.Column;

public class L1CollaborationBean {
	
	private MultipartFile file;

	@Column(name="Promo Id")
	private String promoId="";
	
	@Column(name="Geography")
	private String geography="";
	
	@Column(name="Basepack")
	private String basepack="";
	
	@Column(name="Customer Chain L1")
	private String customerChainL1="";
	
	@Column(name="DP Per Split")
	private String dpPerSplit="";
	
	@Column(name="DP Qty Split")
	private String dpQtySplit="";
	
	@Column(name="Depot")
	private String depot="";
	
	@Column(name="Branch")
	private String branch="";
	
	@Column(name="Cluster")
	private String cluster="";
	
	@Column(name="Depot Per")
	private String depotPer="";
	
	@Column(name="Depot Qty")
	private String depotQty="";
	
	
	@Column(name="KAM Split")
	private String kamSplit="";
	

	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getGeography() {
		return geography;
	}
	public void setGeography(String geography) {
		this.geography = geography;
	}
	public String getBasepack() {
		return basepack;
	}
	public void setBasepack(String basepack) {
		this.basepack = basepack;
	}
	public String getCustomerChainL1() {
		return customerChainL1;
	}
	public void setCustomerChainL1(String customerChainL1) {
		this.customerChainL1 = customerChainL1;
	}
	public String getDpPerSplit() {
		return dpPerSplit;
	}
	public void setDpPerSplit(String dpPerSplit) {
		this.dpPerSplit = dpPerSplit;
	}
	public String getDpQtySplit() {
		return dpQtySplit;
	}
	public void setDpQtySplit(String dpQtySplit) {
		this.dpQtySplit = dpQtySplit;
	}
	public String getDepot() {
		return depot;
	}
	public void setDepot(String depot) {
		this.depot = depot;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getDepotPer() {
		return depotPer;
	}
	public void setDepotPer(String depotPer) {
		this.depotPer = depotPer;
	}
	public String getDepotQty() {
		return depotQty;
	}
	public void setDepotQty(String depotQty) {
		this.depotQty = depotQty;
	}

	public String getKamSplit() {
		return kamSplit;
	}
	public void setKamSplit(String kamSplit) {
		this.kamSplit = kamSplit;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
