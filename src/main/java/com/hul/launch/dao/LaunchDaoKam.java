package com.hul.launch.dao;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.ChangeMocRequestKam;
import com.hul.launch.request.GetKamLaunchRejectRequest;
import com.hul.launch.request.MissingDetailsKamInput;
import com.hul.launch.request.RejectBasepackRequestKam;
import com.hul.launch.request.SampleSharedReqKam;
import com.hul.launch.request.SaveLaunchStore;
import com.hul.launch.request.SaveLaunchStoreList;
import com.hul.launch.request.SaveVisiRequestVatKamList;
import com.hul.launch.response.KamChangeReqRemarks;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamBasepackResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseKam;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchDaoKam {

	List<LaunchKamBasepackResponse> getKamBasepackData(List<String> listOfLaunchData, String userId);

	List<LaunchDataResponse> getAllCompletedKamLaunchData(String account);

	String getUpcomingLaunchMocByLaunchIdsKam(String launchId);

	String rejectLaunchByLaunchIdKam(GetKamLaunchRejectRequest getKamLaunchRejectRequest, String userId);

	String requestChengeMocByLaunchIdKam(ChangeMocRequestKam changeMocRequestKam, String userId);

	String rejectBasepacksByBasepackIdsKam(RejectBasepackRequestKam rejectBasepackRequestKam, String userId);

	List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdKam(String launchId, String userId);

	List<SaveLaunchStoreList> getLaunchStoresBuildUpByLaunchIdKam(String launchId, String userId);

	String saveLaunchStores(String userId, SaveLaunchStore saveFinalLaunchListRequest, String launchId);

	List<ArrayList<String>> getUpdatedBaseFile(ArrayList<String> headerList, String launchId, String userId);

	String saveStoreListByUpload(List<Object> list, String userID, String string, boolean b, boolean c, String launchId)
			throws Exception;

	List<LaunchVisiPlanning> getLaunchVisiListByLaunchIdKam(String launchId);

	String saveLaunchVisiListByLaunchIdKam(SaveVisiRequestVatKamList saveVisiRequestVatKamList, String userId);

	String missingDetailsKamInput(MissingDetailsKamInput missingDetailsKamInput, String userId);

	List<KamChangeReqRemarks> getApprovalStatusKam(String userId);

	String updateLaunchSampleShared(SampleSharedReqKam sampleSharedReqKam, String userId);

	LaunchDataResponse getSpecificLaunchDataKam(String launchId, String userId);

	List<LaunchKamBasepackResponse> getTmeBasepackData(List<String> listOfLaunchData);

	List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdTme(String launchId);

	List<LaunchMstnClearanceResponseKam> getMstnClearanceByLaunchIdKam(String launchId, String userId);

}