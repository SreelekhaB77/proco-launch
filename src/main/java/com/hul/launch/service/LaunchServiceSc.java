package com.hul.launch.service;

import java.util.List;

import com.hul.launch.request.RequestMstnClearanceList;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchScBasepackResponse;
import com.hul.launch.response.LaunchScFinalBuildUpResponse;
import com.hul.launch.response.LaunchScMstnClearanceResponse;

public interface LaunchServiceSc {
	
	//public List<LaunchDataResponse> getAllCompletedLaunchData();
	
	//Q2 sprint feb 2021 kavitha
	public List<LaunchDataResponse> getAllCompletedLaunchData(String scMoc);

	public List<LaunchScBasepackResponse> getScBasepackData(List<String> listOfLaunchData);

	public List<LaunchScFinalBuildUpResponse> getScBuildUpData(List<String> listOfLaunchData);

	public List<LaunchScMstnClearanceResponse> getScMstnClearanceData(List<String> listOfLaunchData);

	public String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId);

	public String uploadMstnClearanceByLaunchIdSc(List<Object> list, String userID, String string, boolean b, boolean c);
	
	//Q2 sprint feb 2021 kavitha
	public List<String> getAllMoc();
}