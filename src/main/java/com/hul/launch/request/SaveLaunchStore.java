package com.hul.launch.request;

import java.util.List;

public class SaveLaunchStore {
	private List<SaveLaunchStoreList> listOfFinalLaunchStores;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	private String launchId;

	public List<SaveLaunchStoreList> getListOfFinalLaunch() {
		return listOfFinalLaunchStores;
	}

	public void setListOfFinalBuildUps(List<SaveLaunchStoreList> listOfFinalLaunchStores) {
		this.listOfFinalLaunchStores = listOfFinalLaunchStores;
	}
}