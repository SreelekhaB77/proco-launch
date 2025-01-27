package com.hul.launch.service;

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
import com.hul.launch.response.StoreDetailsResponse;

public interface LaunchServiceKam {

	//public List<LaunchDataResponse> getAllCompletedLaunchData(String userId);
	public List<LaunchDataResponse> getAllCompletedLaunchData(String userId, String launchMOC);  //Sarin Changes - QiSprint Feb2021

	public List<LaunchKamBasepackResponse> getKamBasepackData(List<String> launchIds, String userId);

	public List<String> getUpcomingLaunchMocByLaunchIdsKam(String launchId);

	public String rejectLaunchByLaunchIdKam(GetKamLaunchRejectRequest getKamLaunchRejectRequest, String userId);

	public String requestChengeMocByLaunchIdKam(ChangeMocRequestKam changeMocRequestKam, String userId);

	public String rejectBasepacksByBasepackIdsKam(RejectBasepackRequestKam rejectBasepackRequestKam, String userId);

	public List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdKam(String launchId, String userId);

	public List<SaveLaunchStoreList> getLaunchStoresBuildUpByLaunchIdKam(String launchId, String userId);

	public String saveLaunchStores(SaveLaunchStore saveFinalLaunchListRequest, String userId, String launchId);

	public List<ArrayList<String>> getUpdatedBaseFile(ArrayList<String> headerList, String launchId, String userId);
	
	public List<ArrayList<String>> getStoreLimitFile(ArrayList<String> headerList, String launchId, String userId);

	public ArrayList<String> getHeaderListForBaseFile();
	
	public ArrayList<String> getHeaderListForStorelimitFile(); // Added By Harsha

	public String saveStoreListByUpload(List<Object> list, String userID, String string, boolean b, boolean c,
			String launchId) throws Exception;

	public List<LaunchVisiPlanning> getLaunchVisiListByLaunchIdKam(String launchId);

	public String saveLaunchVisiListByLaunchIdKam(SaveVisiRequestVatKamList saveVisiRequestVatKamList, String userId);

	public String missingDetailsKamInput(MissingDetailsKamInput missingDetailsKamInput, String userId);
	
	//public List<KamChangeReqRemarks> getApprovalStatusKam(String userId);
	
	//Q2 sprint feb 2021 kavitha
	public List<KamChangeReqRemarks> getApprovalStatusKam(String userId,String approvalLaunchMOC,String approvalKamStauts, int FromApproval);
	
	public String updateLaunchSampleShared(SampleSharedReqKam sampleSharedReqKam, String userId);

	public LaunchDataResponse getSpecificLaunchDataKam(String launchId, String userId);

	public List<LaunchMstnClearanceResponseKam> getMstnClearanceByLaunchIdKam(String launchId, String userId);

	public List<ArrayList<String>> getUpdatedVisiFile(String launchId);
	
	public List<String> getLaunchAccounts(String launchId, String userId);
	
	public List<String> getAllMoc(List<LaunchDataResponse> listOfLaunch);// Modified By Harsha as part of Q7 sprint
	
	//Q2 sprint feb 2021
	public List<String> getAllMocApprovalStatus(String userId);
	
	public List<String>  getKamApprovalStatus(String userId);

	// Added by Harsha for Q4 Sprint
	public List<String> getLaunchAccountsforRejection(String launchId, String userId);
	//Added by Kavitha D-SPRINT 7 DEC 2021
	public String getLaunchName(String launchId);

	
}