package com.hul.launch.response;

import java.util.List;

public class LaunchVisiPlanList {
	private List<LaunchVisiPlanResponse> listOfVisiPlans;
	private List<String> listOfAssetType;

	public List<LaunchVisiPlanResponse> getListOfVisiPlans() {
		return listOfVisiPlans;
	}

	public void setListOfVisiPlans(List<LaunchVisiPlanResponse> listOfVisiPlans) {
		this.listOfVisiPlans = listOfVisiPlans;
	}

	public List<String> getListOfAssetType() {
		return listOfAssetType;
	}

	public void setListOfAssetType(List<String> listOfAssetType) {
		this.listOfAssetType = listOfAssetType;
	}

}