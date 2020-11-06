package com.hul.launch.request;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/
public class RejectBasepackRequestKam {
	private String[] basePackIds;
	private String basepackRejectComment;
	private String launchId;

	public String getLaunchId() {
		return launchId;
	}

	public void setLaunchId(String launchId) {
		this.launchId = launchId;
	}

	public String getBasepackRejectComment() {
		return basepackRejectComment;
	}

	public String[] getBasePackIds() {
		return basePackIds;
	}

	public void setBasePackIds(String[] basePackIds) {
		this.basePackIds = basePackIds;
	}

	public void setBasepackRejectComment(String basepackRejectComment) {
		this.basepackRejectComment = basepackRejectComment;
	}
}