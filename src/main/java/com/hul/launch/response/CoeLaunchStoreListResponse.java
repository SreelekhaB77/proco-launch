package com.hul.launch.response;

public class CoeLaunchStoreListResponse {
	private String launchName;
	private String basepackCode;
	private String basepackDisc;
	private String launchMoc;
	private String l1Chain;
	private String l2Chain;
	private String depot;
	private String storeFormat;
	private String cluster;
	private String hulOlCode;
	private String customerCode;
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getBasepackCode() {
		return basepackCode;
	}

	public void setBasepackCode(String basepackCode) {
		this.basepackCode = basepackCode;
	}

	public String getBasepackDisc() {
		return basepackDisc;
	}

	public void setBasepackDisc(String basepackDisc) {
		this.basepackDisc = basepackDisc;
	}

	public String getLaunchMoc() {
		return launchMoc;
	}

	public void setLaunchMoc(String launchMoc) {
		this.launchMoc = launchMoc;
	}

	public String getL1Chain() {
		return l1Chain;
	}

	public void setL1Chain(String l1Chain) {
		this.l1Chain = l1Chain;
	}

	public String getL2Chain() {
		return l2Chain;
	}

	public void setL2Chain(String l2Chain) {
		this.l2Chain = l2Chain;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getStoreFormat() {
		return storeFormat;
	}

	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getHulOlCode() {
		return hulOlCode;
	}

	public void setHulOlCode(String hulOlCode) {
		this.hulOlCode = hulOlCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
}