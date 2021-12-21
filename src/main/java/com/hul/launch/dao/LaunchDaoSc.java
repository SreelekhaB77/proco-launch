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
	
	//List<LaunchDataResponse> getAllCompletedScLaunchData();
	
	//Q2 sprint feb 2021 kavitha
	List<LaunchDataResponse> getAllCompletedScLaunchData(String scMoc);

	List<LaunchScBasepackResponse> getScBasepackData(List<String> launchIds);

	List<LaunchScFinalBuildUpResponse> getScBuildUpData(List<String> listOfLaunchData);

	List<LaunchScMstnClearanceResponse> getScMstnClearanceData(List<String> listOfLaunchData);

	String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId);

	String uploadMstnClearanceByLaunchIdSc(List<Object> list, String userID, boolean c);// Modified by Harsha for Q7 to accept mulitpile file upload 

	List<LaunchScMstnClearanceResponse> getScMstnClearanceDataDump(List<String> listOfLaunchData, String moc); // Added MOC for excel download
	
	//Q2 sprint feb 2021 kavitha
	public List<String> getAllMoc();

	// Added by Harsha as part of Q6
	public List<String> getMocDates(List<String> listOfLaunchData);
	// Added by Harsha as part of Q6
	public List<LaunchScMstnClearanceResponse> getScMstnClearanceDataFilter(List<String> listOfLaunchData, List<String> listOfMOCData);
	
	public String saveMstnClearanceByLaunchIdsScandLaunchMOC(RequestMstnClearanceList requestMstnClearanceList, String userId,boolean multipleFileUpload) ;

}