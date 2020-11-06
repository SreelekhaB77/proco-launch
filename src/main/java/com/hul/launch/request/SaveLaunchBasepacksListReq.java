package com.hul.launch.request;

import java.util.List;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class SaveLaunchBasepacksListReq {
	@Override
	public String toString() {
		return "SaveLaunchBasepacksListReq [launchId=" + launchId + ", listBasePacks=" + listBasePacks + "]";
	}

	private String launchId;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	private List<SaveLaunchBasepackRequest> listBasePacks;

	public List<SaveLaunchBasepackRequest> getListBasePacks() {
		return listBasePacks;
	}

	public void setListBasePacks(List<SaveLaunchBasepackRequest> listBasePacks) {
		this.listBasePacks = listBasePacks;
	}
}