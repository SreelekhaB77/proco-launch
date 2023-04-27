package com.hul.launch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveLaunchSubmitRequest;
import com.hul.launch.response.CoeDocDownloadResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.launch.response.LaunchCoeClusterResponse;
import com.hul.launch.response.LaunchCoeFinalPageResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchKamInputsResponse;
import com.hul.launch.response.LaunchKamQueriesAnsweredResponse;

public interface LaunchService {
	//public List<LaunchDataResponse> getAllLaunchData();
	//public List<LaunchDataResponse> getAllLaunchData(String userId);

	public LaunchDataResponse getSpecificLaunchData(String launchId);

	public String updateLaunchData(LaunchDataResponse launchDataResonse, String userID);

	public String updateArdWorkLaunchData(LaunchDataResponse launchDataResonse, String userID);

	public String updateMdgDocData(LaunchDataResponse launchDataResonse, String userID);

	public Map<String, String> saveLaunchSubmit(SaveLaunchSubmitRequest saveLaunchSubmitRequest, String userId);

	//public List<LaunchDataResponse> getAllCompletedLaunchData();
	//Q2 sprint march 2021 kavitha
	public List<LaunchDataResponse> getAllCompletedLaunchData(String coeMOC);
	
	public List<LaunchCoeBasePackResponse> getAllCompletedLaunchData(List<String> listOfLaunchData);

	public List<LaunchCoeFinalPageResponse> getAllCompletedLaunchFinalData(List<String> listOfLaunchData);

	public List<LaunchCoeClusterResponse> getAllCompletedListingTracker(List<String> listOfLaunchData);

	public List<CoeDocDownloadResponse> getCoeDocDownloadUrl(List<String> listOfLaunchData);

	public List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	public List<ArrayList<String>> getBuildUpDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	public String deleteAllNextPageData(String launchId, String currentPage, String userId);

	public List<ArrayList<String>> getListingTrackerDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	public List<ArrayList<String>> getMSTNClearanceDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	public List<ArrayList<String>> getLaunchStoreListDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);
	
	public List<ArrayList<String>> getLaunchStoreListDumpPagination(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData,int pageDisplayStart, int pageDisplayLength, String searchParameter);
	
	
	public String saveLaunchStatus(String result, String userId);

	public String updateLaunchStatus(String string, String userId, String launchId);

	public Map<String, Object> getAllLaunchDataByLaunchId(String launchId);

	public List<LaunchKamInputsResponse> getLaunchKamInputs(String userId);

	public String rejectKamInputs(RejectByTmeRequest rejectByTme, String userId);

	public String acceptKamInputs(AcceptByTmeRequest acceptByTme, String userId);

	public List<LaunchKamQueriesAnsweredResponse> getLaunchQueriesAnswered(String userId);

	public String deleteAllKamData(String launchId);
	
	//Q1 sprint feb 2021 kavitha
	public List<String> getAllMoc(String userId);
	
	//Q2 sprint feb 2021 kavitha
	public List<LaunchDataResponse> getAllLaunchData(String userId, String launchMOC, String launchName);
	
	//Q2 sprint feb 2021 kavitha
		public List<String> getAllLaunchName(String userId,String tmeMoc);
		
	//Q2 sprint feb 2021 kavitha
	public List<String> getAllCOEMoc();
	
	//Q2 sprint feb 2021 kavitha
	public List<String> getLaunchNameBasedOnMoc(String userId,String tmeMoc);

	// Added By Harsha
	List<ArrayList<String>> getLaunchStoreListLimitDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	public int getLaunchListRowCountGrid(List<String> listOfLaunchData, String searchParameter);

	
}