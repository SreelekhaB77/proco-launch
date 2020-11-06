package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class DownloadSellInRequest {
	private String l1Chain;
	private String l2Chain;
	private String storeFormat;
	private String storesPlanned;

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

	public String getStoreFormat() {
		return storeFormat;
	}

	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getStoresPlanned() {
		return storesPlanned;
	}

	public void setStoresPlanned(String storesPlanned) {
		this.storesPlanned = storesPlanned;
	}
}