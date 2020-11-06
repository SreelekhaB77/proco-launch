package com.hul.launch.request;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class LaunchClusterRequest {
	private String launchId;
	private MultipartHttpServletRequest multi;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public MultipartHttpServletRequest getMulti() {
		return multi;
	}

	public void setMulti(MultipartHttpServletRequest multi) {
		this.multi = multi;
	}
}