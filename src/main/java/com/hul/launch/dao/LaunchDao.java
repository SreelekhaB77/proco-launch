package com.hul.launch.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.launch.model.LaunchClusterData;
import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.AcceptByTmeRequestByUpload;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveLaunchSubmitRequest;
import com.hul.launch.response.CoeDocDownloadResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.launch.response.LaunchCoeClusterResponse;
import com.hul.launch.response.LaunchCoeFinalPageResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchKamInputsResponse;
import com.hul.launch.response.LaunchKamQueriesAnsweredResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchDao {
	//List<LaunchDataResponse> getAllLaunchData();
	//List<LaunchDataResponse> getAllLaunchData(String userid);

	LaunchDataResponse getSpecificLaunchData(String userID);

	String updateLaunchData(LaunchDataResponse launchDataResponse, String launchId);

	String updateArdWorkLaunchData(LaunchDataResponse launchDataResonse, String userID);

	String updateMdgDocData(LaunchDataResponse launchDataResonse, String userID);

	Map<String, String> saveLaunchSubmit(SaveLaunchSubmitRequest saveLaunchSubmitRequest, String userId);

	List<LaunchDataResponse> getAllCompletedLaunchData();

	List<LaunchCoeBasePackResponse> getAllCompletedLaunchData(List<String> listOfLaunchData);

	List<LaunchCoeFinalPageResponse> getAllCompletedLaunchFinalData(List<String> listOfLaunchData);

	List<LaunchCoeClusterResponse> getAllCompletedListingTracker(List<String> listOfLaunchData);

	List<CoeDocDownloadResponse> getCoeDocDownloadUrl(List<String> listOfLaunchData);

	List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	List<ArrayList<String>> getBuildUpDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	List<ArrayList<String>> getListingTrackerDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	List<ArrayList<String>> getMSTNClearanceDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	List<ArrayList<String>> getLaunchStoreListDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData);

	String saveLaunchStatus(String result, String userId);

	String updateLaunchStatus(String launchId, String userId, String pageName);

	String getLaunchStatus(String launchId);

	LaunchClusterData getClusterDataByLaunchId(String launchId);

	List<LaunchKamInputsResponse> getLaunchKamInputs(String userId);

	String rejectKamInputs(RejectByTmeRequest rejectByTme, String userId);

	String acceptKamInputs(AcceptByTmeRequest acceptByTme, String userId);

	List<LaunchKamQueriesAnsweredResponse> getLaunchQueriesAnswered(String userId);

	String acceptKamInputsByUpload(AcceptByTmeRequestByUpload acceptByTme, String userId);

	String deleteAllNextPageData(String launchId, String currentPage, String userId);

	String deleteAllKamData(String launchId);
	
	public List<String> getAllMoc();
	//Q1 sprint feb 2021 kavitha
	public List<LaunchDataResponse> getAllLaunchData(String userId, String launchMOC);
}