package com.hul.proco.controller.disaggregatepromo;

public class AddDepotBean {

	String branch = "";
	String cluster = "";
	String depot = "";
	int depotQty = 0;

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

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public int getDepotQty() {
		return depotQty;
	}

	public void setDepotQty(int depotQty) {
		this.depotQty = depotQty;
	}

}
