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

	//List<LaunchDataResponse> getAllCompletedKamLaunchData(String account);
	List<LaunchDataResponse> getAllCompletedKamLaunchData(String account, String launchMOC); //Sarin Changes - QiSprint Feb2021

	String getUpcomingLaunchMocByLaunchIdsKam(String launchId);

	String rejectLaunchByLaunchIdKam(GetKamLaunchRejectRequest getKamLaunchRejectRequest, String userId);

	String requestChengeMocByLaunchIdKam(ChangeMocRequestKam changeMocRequestKam, String userId);

	String rejectBasepacksByBasepackIdsKam(RejectBasepackRequestKam rejectBasepackRequestKam, String userId);

	List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdKam(String launchId, String userId);

	List<SaveLaunchStoreList> getLaunchStoresBuildUpByLaunchIdKam(String launchId, String userId);

	String saveLaunchStores(String userId, SaveLaunchStore saveFinalLaunchListRequest, String launchId);

	List<ArrayList<String>> getUpdatedBaseFile(ArrayList<String> headerList, String launchId, String userId);
	//Added By Harsha As part of Sprint 8
	List<ArrayList<String>> getStoreslimitFile(ArrayList<String> headerList, String launchId, String userId);

	String saveStoreListByUpload(List<Object> list, String userID, String string, boolean b, boolean c, String launchId)
			throws Exception;

	List<LaunchVisiPlanning> getLaunchVisiListByLaunchIdKam(String launchId);

	String saveLaunchVisiListByLaunchIdKam(SaveVisiRequestVatKamList saveVisiRequestVatKamList, String userId);

	String missingDetailsKamInput(MissingDetailsKamInput missingDetailsKamInput, String userId);
	
	//List<KamChangeReqRemarks> getApprovalStatusKam(String userId);
	//Q2 sprint feb 2021 kavitha
	public List<KamChangeReqRemarks> getApprovalStatusKam(String userId,String approvalLaunchMOC,String approvalKamStauts, int FromApproval);
	
	String updateLaunchSampleShared(SampleSharedReqKam sampleSharedReqKam, String userId);

	LaunchDataResponse getSpecificLaunchDataKam(String launchId, String userId);

	List<LaunchKamBasepackResponse> getTmeBasepackData(List<String> listOfLaunchData);

	List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdTme(String launchId);

	List<LaunchMstnClearanceResponseKam> getMstnClearanceByLaunchIdKam(String launchId, String userId);
	
	public List<String> getLaunchAccounts(String launchId, String userId);
	
	public List<String> getAllMoc(List<LaunchDataResponse> listOfLaunch);// Modified by Harsha as part of Q7 sprint
	
	//Q2 sprint feb 2021
	public List<String> getAllMocApprovalStatus(String userId);
	
	public List<String> getKamApprovalStatus(String userId);

	// Added By Harsha for Q4 Sprint
	public List<String> getLaunchAccountsforRejection(String launchId, String userId);
	
	//Added by Kavitha D-SPRINT 7 DEC 2021
	public String getLaunchName(String launchId);

	


}