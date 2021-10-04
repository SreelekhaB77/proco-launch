package com.hul.launch.service;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.response.LaunchFinalPlanResponse;

public interface LaunchFinalService {
	List<LaunchFinalPlanResponse> getLaunchFinalRespose(String launchId, String userId);

	String saveLaunchFinalBuildUp(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId);

	List<ArrayList<String>> getFinalBuildUpDumpNew(String userId, String launchId);
	
//Added below the code
	List<ArrayList<String>> getFinalBuildUpDumpNew(String userId, String[] launchId, String[] launchMoc);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeEdit(String launchId, String userId);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKAM(String launchId, String userId);

	List<ArrayList<String>> getFinalBuildUpDumpNewKam(String userId, String launchId);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKamAfterLaunchRej(String launchId, String userId,
			String kamAcc
			, String launchRequestId);  //Added By Sarin - sprint5Sep2021 - new parameter launchRequestId

	List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKamAfterRejBp(String launchId, String userId, String kamAcc);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeAfterBPReject(String launchId, String userId, String kamAcc,
			String bpId);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeAfterStoreReject(String launchId, String userId, String kamAcc,
			String storeId);

	List<ArrayList<String>> getMstnClearanceDataDump(String userId, List<String> listOfLaunchData);

	List<ArrayList<String>> getMstnClearanceDataDumpCoe(String userId, List<String> listOfLaunchData);

	List<ArrayList<String>> getDisaggregatedByDp(String[] promoId);

	
}