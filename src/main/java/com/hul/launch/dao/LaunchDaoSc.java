package com.hul.launch.dao;

import java.util.List;

import com.hul.launch.request.RequestMstnClearanceList;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchScBasepackResponse;
import com.hul.launch.response.LaunchScFinalBuildUpResponse;
import com.hul.launch.response.LaunchScMstnClearanceResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchDaoSc {
	List<LaunchDataResponse> getAllCompletedScLaunchData();
	//List<LaunchDataResponse> getAllCompletedScLaunchData();
	
	//Q2 sprint feb 2021 kavitha
	List<LaunchDataResponse> getAllCompletedScLaunchData(String scMoc);

	List<LaunchScBasepackResponse> getScBasepackData(List<String> launchIds);

	List<LaunchScFinalBuildUpResponse> getScBuildUpData(List<String> listOfLaunchData);

	List<LaunchScMstnClearanceResponse> getScMstnClearanceData(List<String> listOfLaunchData);

	String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId);

	String uploadMstnClearanceByLaunchIdSc(List<Object> list, String userID);

	List<LaunchScMstnClearanceResponse> getScMstnClearanceDataDump(List<String> listOfLaunchData);
	
	//Q2 sprint feb 2021 kavitha
	public List<String> getAllMoc();
}