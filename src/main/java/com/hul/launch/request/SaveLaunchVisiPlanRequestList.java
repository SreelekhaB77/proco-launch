package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchVisiPlanRequestList {
	private List<SaveLaunchVisiPlanRequest> listOfVisiPlans;
	private int launchId;

	public List<SaveLaunchVisiPlanRequest> getListOfVisiPlans() {
		return listOfVisiPlans;
	}

	public void setListOfVisiPlans(List<SaveLaunchVisiPlanRequest> listOfVisiPlans) {
		this.listOfVisiPlans = listOfVisiPlans;
	}

	public int getLaunchId() {
		return launchId;
	}

	public void setLaunchId(int launchId) {
		this.launchId = launchId;
	}
}