package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class DownloadLaunchClusterRequest {
	private String regionCluster;
	private String l1l2Cluster;
	private String storeFormat;
	private String custStoreFormat;

	public String getRegionCluster() {
		return regionCluster;
	}

	public void setRegionCluster(String regionCluster) {
		this.regionCluster = regionCluster;
	}

	public String getL1l2Cluster() {
		return l1l2Cluster;
	}

	public void setL1l2Cluster(String l1l2Cluster) {
		this.l1l2Cluster = l1l2Cluster;
	}

	public String getStoreFormat() {
		return storeFormat;
	}

	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

	public String getCustStoreFormat() {
		return custStoreFormat;
	}

	public void setCustStoreFormat(String custStoreFormat) {
		this.custStoreFormat = custStoreFormat;
	}
}