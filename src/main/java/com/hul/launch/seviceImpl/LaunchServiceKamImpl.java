package com.hul.launch.seviceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchDaoKam;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.ChangeMocRequestKam;
import com.hul.launch.request.GetKamLaunchRejectRequest;
import com.hul.launch.request.MissingDetailsKamInput;
import com.hul.launch.request.RejectBasepackRequestKam;
import com.hul.launch.request.SampleSharedReqKam;
import com.hul.launch.request.SaveLaunchStore;
import com.hul.launch.request.SaveLaunchStoreList;
import com.hul.launch.request.SaveVisiRequestVatKamList;
import com.hul.launch.response.GetLaunchMocForKamResponse;
import com.hul.launch.response.KamChangeReqRemarks;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamBasepackResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseKam;
import com.hul.launch.service.LaunchServiceKam;

@Service
@Transactional
public class LaunchServiceKamImpl implements LaunchServiceKam {

	@Autowired
	LaunchDaoKam launchDaoKam;

	@Override
	//public List<LaunchDataResponse> getAllCompletedLaunchData(String account) {
	public List<LaunchDataResponse> getAllCompletedLaunchData(String account, String launchMOC) {  //Sarin Changes - QiSprint Feb2021
		//return launchDaoKam.getAllCompletedKamLaunchData(account);
		return launchDaoKam.getAllCompletedKamLaunchData(account, launchMOC);
	}

	@Override
	public List<LaunchKamBasepackResponse> getKamBasepackData(List<String> launchIds, String userId) {
		return launchDaoKam.getKamBasepackData(launchIds, userId);
	}

	@Override
	public List<String> getUpcomingLaunchMocByLaunchIdsKam(String launchId) {
		List<String> listOfUpcomingMoc = new ArrayList<>();
		try {
			String currentDate = launchDaoKam.getUpcomingLaunchMocByLaunchIdsKam(launchId);
			Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);
			LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			for (int i = 1; i <= 2; i++) {
				LocalDate futureDate = localDate.plusMonths(i);
				String date[] = futureDate.toString().split("-");
				listOfUpcomingMoc.add(date[1] + date[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfUpcomingMoc;
	}
	
	@Override
	public String rejectLaunchByLaunchIdKam(GetKamLaunchRejectRequest getKamLaunchRejectRequest, String userId) {
		return launchDaoKam.rejectLaunchByLaunchIdKam(getKamLaunchRejectRequest, userId);
	}

	@Override
	public String requestChengeMocByLaunchIdKam(ChangeMocRequestKam changeMocRequestKam, String userId) {
		return launchDaoKam.requestChengeMocByLaunchIdKam(changeMocRequestKam, userId);
	}

	@Override
	public String rejectBasepacksByBasepackIdsKam(RejectBasepackRequestKam rejectBasepackRequestKam, String userId) {
		return launchDaoKam.rejectBasepacksByBasepackIdsKam(rejectBasepackRequestKam, userId);
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdKam(String launchId, String userId) {
		return launchDaoKam.getLaunchBuildUpByLaunchIdKam(launchId, userId);
	}

	@Override
	public List<LaunchVisiPlanning> getLaunchVisiListByLaunchIdKam(String launchId) {
		return launchDaoKam.getLaunchVisiListByLaunchIdKam(launchId);
	}

	@Override
	public String saveLaunchVisiListByLaunchIdKam(SaveVisiRequestVatKamList saveVisiRequestVatKamList, String userId) {
		return launchDaoKam.saveLaunchVisiListByLaunchIdKam(saveVisiRequestVatKamList, userId);
	}

	public List<SaveLaunchStoreList> getLaunchStoresBuildUpByLaunchIdKam(String launchId, String userId) {
		return launchDaoKam.getLaunchStoresBuildUpByLaunchIdKam(launchId, userId);
	}
	
	

	@Override
	public String saveLaunchStores(SaveLaunchStore saveFinalLaunchListRequest, String userId, String launchId) {
		return launchDaoKam.saveLaunchStores(userId, saveFinalLaunchListRequest, launchId);
	}
	
	@Override
	public List<ArrayList<String>> getUpdatedBaseFile(ArrayList<String> headerList, String launchId, String userId) {
		return launchDaoKam.getUpdatedBaseFile(headerList, launchId, userId);

	}

	
	// Added By Harsha for sprint 8 -- start

	@Override
	public List<ArrayList<String>> getStoreLimitFile(ArrayList<String> headerList, String launchId, String userId) {
		return launchDaoKam.getStoreslimitFile(headerList, launchId, userId);
	}
	//Harsha changes End's
	

	@Override
	public ArrayList<String> getHeaderListForBaseFile() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("LAUNCH_NAME");
		headerList.add("LAUNCH_MOC");
		headerList.add("L1_CHAIN");
		headerList.add("L2_CHAIN");
		headerList.add("STORE_FORMAT");
		headerList.add("CLUSTER");
		headerList.add("HUL_OL_CODE");
		headerList.add("KAM_REMARKS");
		return headerList;
	}
	
	// Added By Harsha For storeslimit header files -- Starts 

	public ArrayList<String> getHeaderListForStorelimitFile() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("CLUSTER_REGION");
		headerList.add("CLUSTER_ACCOUNT_L1");
		headerList.add("CLUSTER_ACCOUNT_L2");
		headerList.add("CLUSTER_STORE_FORMAT");
		headerList.add("CLUSTER_CUST_STORE_FORMAT");
		headerList.add("LAUNCH_PLANNED");
		headerList.add("TOTAL_STORES_TO_LAUNCH");
		headerList.add("TOTAL_TME_STORES_PLANED");
		return headerList;
	}
	// Added By Harsha For storeslimit header files -- Ends

	@Override
	public String saveStoreListByUpload(List<Object> list, String userID, String string, boolean b, boolean c,
			String launchId) throws Exception {
		return launchDaoKam.saveStoreListByUpload(list, userID, string, b, c, launchId);
	}

	@Override
	public String missingDetailsKamInput(MissingDetailsKamInput missingDetailsKamInput, String userId) {
		return launchDaoKam.missingDetailsKamInput(missingDetailsKamInput, userId);
	}

	@Override
	//Q2 sprint feb 2021 kavitha
	public List<KamChangeReqRemarks> getApprovalStatusKam(String userId,String approvalLaunchMOC,String approvalKamStauts, int FromApproval)
	//public List<KamChangeReqRemarks> getApprovalStatusKam(String userId)
	 {
		//return launchDaoKam.getApprovalStatusKam(userId);
		return launchDaoKam.getApprovalStatusKam(userId,approvalLaunchMOC,approvalKamStauts, FromApproval);
	}

	@Override
	public String updateLaunchSampleShared(SampleSharedReqKam sampleSharedReqKam, String userId) {
		return launchDaoKam.updateLaunchSampleShared(sampleSharedReqKam, userId);
	}

	@Override
	public LaunchDataResponse getSpecificLaunchDataKam(String launchId, String userId) {
		return launchDaoKam.getSpecificLaunchDataKam(launchId, userId);
	}

	@Override
	public List<LaunchMstnClearanceResponseKam> getMstnClearanceByLaunchIdKam(String launchId, String userId) {
		return launchDaoKam.getMstnClearanceByLaunchIdKam(launchId, userId);
	}

	@Override
	public List<ArrayList<String>> getUpdatedVisiFile(String launchId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	@Transactional
	public List<String> getLaunchAccounts(String launchId, String userId) {

		return launchDaoKam.getLaunchAccounts(launchId, userId);

	}
	
	@Override
	@Transactional
	public List<String> getAllMoc(List<LaunchDataResponse> listOfLaunch) {// Modified by Harsha as part of Q7 sprint
		return launchDaoKam.getAllMoc(listOfLaunch);
	}

	//Q2 sprint feb 2021 Kavitha
	@Override
	@Transactional
	public List<String> getAllMocApprovalStatus(String userId) {
		return launchDaoKam.getAllMocApprovalStatus(userId);
	}
	
	@Override
	@Transactional
	public List<String> getKamApprovalStatus(String userId) {
		return launchDaoKam.getKamApprovalStatus(userId);
	}

	// Removing Rejected acoounts added by Harsha 
	@Override
	public List<String> getLaunchAccountsforRejection(String launchId, String userId) {
		return launchDaoKam.getLaunchAccountsforRejection( launchId,  userId);
	}
	//Added by Kavitha D-SPRINT 7 DEC 2021
	@Override
	public String getLaunchName(String launchId) {
		return launchDaoKam.getLaunchName(launchId);
	}
	


}