package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveFinalLaunchListRequest {
	private List<SaveFinalLaunchRequest> listOfFinalBuildUps;
	private String launchId;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public List<SaveFinalLaunchRequest> getListOfFinalBuildUps() {
		return listOfFinalBuildUps;
	}

	public void setListOfFinalBuildUps(List<SaveFinalLaunchRequest> listOfFinalBuildUps) {
		this.listOfFinalBuildUps = listOfFinalBuildUps;
	}
}