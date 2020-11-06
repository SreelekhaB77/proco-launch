package com.hul.launch.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveUploadededLaunchStore {
	private MultipartFile file;
	private String L1_Chain;
	private String L2_Chain;
	private String StoreFormat;
	private String Cluster;
	private String HUL_OL_Code;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String Kam_Remarks;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getKam_Remarks() {
		return Kam_Remarks;
	}
	public void setKam_Remarks(String kam_Remarks) {
		Kam_Remarks = kam_Remarks;
	}
	public String getL1_Chain() {
		return L1_Chain;
	}
	public void setL1_Chain(String l1_Chain) {
		L1_Chain = l1_Chain;
	}
	public String getL2_Chain() {
		return L2_Chain;
	}
	public void setL2_Chain(String l2_Chain) {
		L2_Chain = l2_Chain;
	}
	public String getStoreFormat() {
		return StoreFormat;
	}
	public void setStoreFormat(String storeFormat) {
		StoreFormat = storeFormat;
	}
	public String getCluster() {
		return Cluster;
	}
	public void setCluster(String cluster) {
		Cluster = cluster;
	}
	public String getHUL_OL_Code() {
		return HUL_OL_Code;
	}
	public void setHUL_OL_Code(String hUL_OL_Code) {
		HUL_OL_Code = hUL_OL_Code;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
