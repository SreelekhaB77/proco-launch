package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveVisiRequestVatKamList {
	private List<SaveVisiRequestVatKam> listOfSaveVisiRequestVatKam;
	private int launchId;

	public int getLaunchId() {
		return launchId;
	}

	public void setLaunchId(int launchId) {
		this.launchId = launchId;
	}

	public List<SaveVisiRequestVatKam> getListOfSaveVisiRequestVatKam() {
		return listOfSaveVisiRequestVatKam;
	}

	public void setListOfSaveVisiRequestVatKam(List<SaveVisiRequestVatKam> listOfSaveVisiRequestVatKam) {
		this.listOfSaveVisiRequestVatKam = listOfSaveVisiRequestVatKam;
	}
}