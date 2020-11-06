package com.hul.launch.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public class LaunchVisiPlanning {
	private MultipartFile file;
	private String ACCOUNT;
	private String FORMAT;
	private String STORES_AVAILABLE;
	private String STORES_PLANNED;
	private String VISI_ASSET_1;
	private String VISI_ASSET_2;
	private String VISI_ASSET_3;
	private String VISI_ASSET_4;
	private String VISI_ASSET_5;
	private String FACING_PER_SHELF_PER_SKU1;
	private String DEPTH_PER_SHELF_PER_SKU1;
	private String FACING_PER_SHELF_PER_SKU2;
	private String DEPTH_PER_SHELF_PER_SKU2;
	private String FACING_PER_SHELF_PER_SKU3;
	private String DEPTH_PER_SHELF_PER_SKU3;
	private String FACING_PER_SHELF_PER_SKU4;
	private String DEPTH_PER_SHELF_PER_SKU4;
	private String FACING_PER_SHELF_PER_SKU5;
	private String DEPTH_PER_SHELF_PER_SKU5;

	public String getFACING_PER_SHELF_PER_SKU1() {
		return FACING_PER_SHELF_PER_SKU1;
	}

	public void setFACING_PER_SHELF_PER_SKU1(String fACING_PER_SHELF_PER_SKU1) {
		FACING_PER_SHELF_PER_SKU1 = fACING_PER_SHELF_PER_SKU1;
	}

	public String getDEPTH_PER_SHELF_PER_SKU1() {
		return DEPTH_PER_SHELF_PER_SKU1;
	}

	public void setDEPTH_PER_SHELF_PER_SKU1(String dEPTH_PER_SHELF_PER_SKU1) {
		DEPTH_PER_SHELF_PER_SKU1 = dEPTH_PER_SHELF_PER_SKU1;
	}

	public String getFACING_PER_SHELF_PER_SKU2() {
		return FACING_PER_SHELF_PER_SKU2;
	}

	public void setFACING_PER_SHELF_PER_SKU2(String fACING_PER_SHELF_PER_SKU2) {
		FACING_PER_SHELF_PER_SKU2 = fACING_PER_SHELF_PER_SKU2;
	}

	public String getDEPTH_PER_SHELF_PER_SKU2() {
		return DEPTH_PER_SHELF_PER_SKU2;
	}

	public void setDEPTH_PER_SHELF_PER_SKU2(String dEPTH_PER_SHELF_PER_SKU2) {
		DEPTH_PER_SHELF_PER_SKU2 = dEPTH_PER_SHELF_PER_SKU2;
	}

	public String getFACING_PER_SHELF_PER_SKU3() {
		return FACING_PER_SHELF_PER_SKU3;
	}

	public void setFACING_PER_SHELF_PER_SKU3(String fACING_PER_SHELF_PER_SKU3) {
		FACING_PER_SHELF_PER_SKU3 = fACING_PER_SHELF_PER_SKU3;
	}

	public String getDEPTH_PER_SHELF_PER_SKU3() {
		return DEPTH_PER_SHELF_PER_SKU3;
	}

	public void setDEPTH_PER_SHELF_PER_SKU3(String dEPTH_PER_SHELF_PER_SKU3) {
		DEPTH_PER_SHELF_PER_SKU3 = dEPTH_PER_SHELF_PER_SKU3;
	}

	public String getFACING_PER_SHELF_PER_SKU4() {
		return FACING_PER_SHELF_PER_SKU4;
	}

	public void setFACING_PER_SHELF_PER_SKU4(String fACING_PER_SHELF_PER_SKU4) {
		FACING_PER_SHELF_PER_SKU4 = fACING_PER_SHELF_PER_SKU4;
	}

	public String getDEPTH_PER_SHELF_PER_SKU4() {
		return DEPTH_PER_SHELF_PER_SKU4;
	}

	public void setDEPTH_PER_SHELF_PER_SKU4(String dEPTH_PER_SHELF_PER_SKU4) {
		DEPTH_PER_SHELF_PER_SKU4 = dEPTH_PER_SHELF_PER_SKU4;
	}

	public String getFACING_PER_SHELF_PER_SKU5() {
		return FACING_PER_SHELF_PER_SKU5;
	}

	public void setFACING_PER_SHELF_PER_SKU5(String fACING_PER_SHELF_PER_SKU5) {
		FACING_PER_SHELF_PER_SKU5 = fACING_PER_SHELF_PER_SKU5;
	}

	public String getDEPTH_PER_SHELF_PER_SKU5() {
		return DEPTH_PER_SHELF_PER_SKU5;
	}

	public void setDEPTH_PER_SHELF_PER_SKU5(String dEPTH_PER_SHELF_PER_SKU5) {
		DEPTH_PER_SHELF_PER_SKU5 = dEPTH_PER_SHELF_PER_SKU5;
	}

	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(String aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	public String getFORMAT() {
		return FORMAT;
	}

	public void setFORMAT(String fORMAT) {
		FORMAT = fORMAT;
	}

	public String getSTORES_AVAILABLE() {
		return STORES_AVAILABLE;
	}

	public void setSTORES_AVAILABLE(String sTORES_AVAILABLE) {
		STORES_AVAILABLE = sTORES_AVAILABLE;
	}

	public String getSTORES_PLANNED() {
		return STORES_PLANNED;
	}

	public void setSTORES_PLANNED(String sTORES_PLANNED) {
		STORES_PLANNED = sTORES_PLANNED;
	}

	public String getVISI_ASSET_1() {
		return VISI_ASSET_1;
	}

	public void setVISI_ASSET_1(String vISI_ASSET_1) {
		VISI_ASSET_1 = vISI_ASSET_1;
	}

	public String getVISI_ASSET_2() {
		return VISI_ASSET_2;
	}

	public void setVISI_ASSET_2(String vISI_ASSET_2) {
		VISI_ASSET_2 = vISI_ASSET_2;
	}

	public String getVISI_ASSET_3() {
		return VISI_ASSET_3;
	}

	public void setVISI_ASSET_3(String vISI_ASSET_3) {
		VISI_ASSET_3 = vISI_ASSET_3;
	}

	public String getVISI_ASSET_4() {
		return VISI_ASSET_4;
	}

	public void setVISI_ASSET_4(String vISI_ASSET_4) {
		VISI_ASSET_4 = vISI_ASSET_4;
	}

	public String getVISI_ASSET_5() {
		return VISI_ASSET_5;
	}

	public void setVISI_ASSET_5(String vISI_ASSET_5) {
		VISI_ASSET_5 = vISI_ASSET_5;
	}

}