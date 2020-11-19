package com.hul.launch.model;

public class LaunchFinalCalVO {
	
	public String getDepoBasePack() {
		return depoBasePack;
	}
	public void setDepoBasePack(String depoBasePack) {
		this.depoBasePack = depoBasePack;
	}
	public String getLaunchId() {
		return launchId;
	}
	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}
	public LaunchBuildUpTemp getLaunchBuildUpTemp() {
		return launchBuildUpTemp;
	}
	public void setLaunchBuildUpTemp(LaunchBuildUpTemp launchBuildUpTemp) {
		this.launchBuildUpTemp = launchBuildUpTemp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String depoBasePack;
	private String launchId;
	private LaunchBuildUpTemp launchBuildUpTemp;
	private String userId;

	
	

}
