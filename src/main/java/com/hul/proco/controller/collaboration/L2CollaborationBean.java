package com.hul.proco.controller.collaboration;

import com.hul.proco.excelreader.exom.annotation.Column;

public class L2CollaborationBean {

	@Column(name="Promo Id")
	private String promoId="";
	
	@Column(name="Geography")
	private String geography="";
	
	@Column(name="Basepack")
	private String basepack="";
	
	@Column(name="Customer Chain L1")
	private String customerChainL1="";
	
	@Column(name="L1 DP Per Split")
	private String l1DpPerSplit="";
	
	@Column(name="L1 DP Qty Split")
	private String l1DpQtySplit="";
	
	@Column(name="Customer Chain L2")
	private String customerChainL2="";
	
	@Column(name="L2 DP Per Split")
	private String l2DpPerSplit="";
	
	@Column(name="L2 DP Qty Split")
	private String l2DpQtySplit="";
	
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

	public String getL1DpPerSplit() {
		return l1DpPerSplit;
	}

	public void setL1DpPerSplit(String l1DpPerSplit) {
		this.l1DpPerSplit = l1DpPerSplit;
	}

	public String getL1DpQtySplit() {
		return l1DpQtySplit;
	}

	public void setL1DpQtySplit(String l1DpQtySplit) {
		this.l1DpQtySplit = l1DpQtySplit;
	}

	public String getCustomerChainL2() {
		return customerChainL2;
	}

	public void setCustomerChainL2(String customerChainL2) {
		this.customerChainL2 = customerChainL2;
	}

	public String getL2DpPerSplit() {
		return l2DpPerSplit;
	}

	public void setL2DpPerSplit(String l2DpPerSplit) {
		this.l2DpPerSplit = l2DpPerSplit;
	}

	public String getL2DpQtySplit() {
		return l2DpQtySplit;
	}

	public void setL2DpQtySplit(String l2DpQtySplit) {
		this.l2DpQtySplit = l2DpQtySplit;
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

	
	@Override
	public String toString() {
		return "L2CollaborationBean [promoId=" + promoId + ", geography=" + geography + ", basepack=" + basepack
				+ ", customerChainL1=" + customerChainL1 + ", l1DpPerSplit=" + l1DpPerSplit + ", l1DpQtySplit="
				+ l1DpQtySplit + ", customerChainL2=" + customerChainL2 + ", l2DpPerSplit=" + l2DpPerSplit
				+ ", l2DpQtySplit=" + l2DpQtySplit + ", depot=" + depot + ", branch=" + branch + ", cluster=" + cluster
				+ ", depotPer=" + depotPer + ", depotQty=" + depotQty + ", kamSplit=" + kamSplit + "]";
	}
	
}
