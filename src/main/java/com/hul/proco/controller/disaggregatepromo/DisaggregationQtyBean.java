package com.hul.proco.controller.disaggregatepromo;

public class DisaggregationQtyBean {

	private String depot;
	private String branch;
	private String cluster;
	private double saleTotalQty;
	private double percentage;
	private double qty;
//	private double qty;
	public double getSaleTotalQty() {
		return saleTotalQty;
	}

	public void setSaleTotalQty(double saleTotalQty) {
		this.saleTotalQty = saleTotalQty;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
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

	@Override
	public String toString() {
		return "DisaggregationQtyBean [depot=" + depot + ", saleTotalQty=" + saleTotalQty + ", percentage=" + percentage
				+ ", qty=" + qty + "]";
	}
	
	

}
