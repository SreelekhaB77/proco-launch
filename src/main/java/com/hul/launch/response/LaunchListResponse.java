package com.hul.launch.response;

import java.util.List;

public class LaunchListResponse {
	private List<LaunchDataResponse> listOfLaunchData;

	public List<LaunchDataResponse> getListOfLaunchData() {
		return listOfLaunchData;
	}

	public void setListOfLaunchData(List<LaunchDataResponse> listOfLaunchData) {
		this.listOfLaunchData = listOfLaunchData;
	}
}