package com.hul.launch.response;

import java.util.List;

public class ListMstnClearanceScResponse {
	private List<LaunchScMstnClearanceResponse> launchScMstnClearanceResponseList;
	private List<String> getMoc;

	

	public List<String> getGetMoc() {
		return getMoc;
	}

	public void setGetMoc(List<String> getMoc) {
		this.getMoc = getMoc;
	}

	public List<LaunchScMstnClearanceResponse> getLaunchScMstnClearanceResponseList() {
		return launchScMstnClearanceResponseList;
	}

	public void setLaunchScMstnClearanceResponseList(
			List<LaunchScMstnClearanceResponse> launchScMstnClearanceResponseList) {
		this.launchScMstnClearanceResponseList = launchScMstnClearanceResponseList;
	}
}