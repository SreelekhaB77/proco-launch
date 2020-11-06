package com.hul.launch.request;

import java.util.List;

public class AcceptByTmeRequestByUpload {
	private List<String> acceptRemark;
	private List<String> reqIds;

	public List<String> getAcceptRemark() {
		return acceptRemark;
	}

	public void setAcceptRemark(List<String> acceptRemark) {
		this.acceptRemark = acceptRemark;
	}

	public List<String> getReqIds() {
		return reqIds;
	}

	public void setReqIds(List<String> reqIds) {
		this.reqIds = reqIds;
	}
}