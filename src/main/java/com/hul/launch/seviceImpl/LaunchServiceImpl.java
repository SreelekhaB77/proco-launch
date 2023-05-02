package com.hul.launch.seviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchDao;
import com.hul.launch.dao.LaunchDaoKam;
import com.hul.launch.dao.LaunchSellInDao;
import com.hul.launch.dao.LaunchVisiPlanDao;
import com.hul.launch.model.LaunchClusterData;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveLaunchSubmitRequest;
import com.hul.launch.response.CoeDocDownloadResponse;
import com.hul.launch.response.CoeLaunchStoreListResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.launch.response.LaunchCoeClusterResponse;
import com.hul.launch.response.LaunchCoeFinalPageResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamBasepackResponse;
import com.hul.launch.response.LaunchKamInputsResponse;
import com.hul.launch.response.LaunchKamQueriesAnsweredResponse;
import com.hul.launch.response.LaunchSellInEditReponse;
import com.hul.launch.service.LaunchService;

@Service
@Transactional
public class LaunchServiceImpl implements LaunchService {

	@Autowired
	LaunchDao launchDao;

	@Autowired
	LaunchDaoKam launchDaoKam;

	@Autowired
	LaunchSellInDao lauchSellInDao;

	@Autowired
	LaunchVisiPlanDao launchVisiPlanDao;

	/*
	 * @Override public List<LaunchDataResponse> getAllLaunchData() { return
	 * launchDao.getAllLaunchData(); }
	 */
	
	/*
	 * @Override public List<LaunchDataResponse> getAllLaunchData(String userid) {
	 * return launchDao.getAllLaunchData(userid); }
	 */

	@Override
	public LaunchDataResponse getSpecificLaunchData(String launchId) {
		return launchDao.getSpecificLaunchData(launchId);
	}

	@Override
	public String updateLaunchData(LaunchDataResponse launchDataResponse, String userID) {
		return launchDao.updateLaunchData(launchDataResponse, userID);
	}

	@Override
	public String updateArdWorkLaunchData(LaunchDataResponse launchDataResonse, String userID) {
		return launchDao.updateArdWorkLaunchData(launchDataResonse, userID);
	}

	@Override
	public String updateMdgDocData(LaunchDataResponse launchDataResonse, String userID) {
		return launchDao.updateMdgDocData(launchDataResonse, userID);
	}

	@Override
	public Map<String, String> saveLaunchSubmit(SaveLaunchSubmitRequest saveLaunchSubmitRequest, String userId) {
		return launchDao.saveLaunchSubmit(saveLaunchSubmitRequest, userId);
	}
//Q2 sprint  march 2021 kavitha
	@Override
	public List<LaunchDataResponse> getAllCompletedLaunchData(String coeMOC){
	//public List<LaunchDataResponse> getAllCompletedLaunchData() {
		return launchDao.getAllCompletedLaunchData(coeMOC);
	}

	@Override
	public List<LaunchCoeBasePackResponse> getAllCompletedLaunchData(List<String> listOfLaunchData) {
		return launchDao.getAllCompletedLaunchData(listOfLaunchData);
	}

	@Override
	public List<LaunchCoeFinalPageResponse> getAllCompletedLaunchFinalData(List<String> listOfLaunchData) {
		return launchDao.getAllCompletedLaunchFinalData(listOfLaunchData);
	}

	@Override
	public List<LaunchCoeClusterResponse> getAllCompletedListingTracker(List<String> listOfLaunchData) {
		return launchDao.getAllCompletedListingTracker(listOfLaunchData);
	}
	

	@Override
	public List<CoeDocDownloadResponse> getCoeDocDownloadUrl(List<String> listOfLaunchData) {
		return launchDao.getCoeDocDownloadUrl(listOfLaunchData);
	}

	@Override
	public List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getbasepackDump(headerDetail, userId, listOfLaunchData);
	}

	@Override
	public List<ArrayList<String>> getBuildUpDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getBuildUpDump(headerDetail, userId, listOfLaunchData);
	}

	@Override
	public List<ArrayList<String>> getListingTrackerDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getListingTrackerDump(headerDetail, userId, listOfLaunchData);
	}

	@Override
	public List<ArrayList<String>> getMSTNClearanceDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getMSTNClearanceDump(headerDetail, userId, listOfLaunchData);
	}

	@Override
	public List<CoeLaunchStoreListResponse> getLaunchStoreListDumpPagination(List<String> listOfLaunchData,int pageDisplayStart, int pageDisplayLength){
		return launchDao.getLaunchStoreListDumpPagination(listOfLaunchData,pageDisplayStart,pageDisplayLength);
	}
	@Override
	public int getLaunchListRowCountGrid(List<String> listOfLaunchData) {
		return launchDao.getLaunchListRowCountGrid(listOfLaunchData);
	}
	
	@Override
	public List<ArrayList<String>> getLaunchStoreListDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getLaunchStoreListDump(headerDetail, userId, listOfLaunchData);
	}
	// Added By Harsha As part of sprint 8 starts
	@Override
	public List<ArrayList<String>> getLaunchStoreListLimitDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		return launchDao.getLaunchStoreLimitListDump(headerDetail, userId, listOfLaunchData);
	}//sprint 8 Ends

	@Override
	public String saveLaunchStatus(String result, String userId) {
		return launchDao.saveLaunchStatus(result, userId);
	}

	@Override
	public String updateLaunchStatus(String pageName, String userId, String launchId) {
		return launchDao.updateLaunchStatus(launchId, userId, pageName);
	}

	@Override
	public Map<String, Object> getAllLaunchDataByLaunchId(String launchId) {
		String launchStatus = launchDao.getLaunchStatus(launchId);
		Map<String, Object> entirePageData = new HashMap<>();
		String[] launchIds = launchId.split(",");
		List<String> listOfLaunchIds = Arrays.asList(launchIds);

		if (launchStatus.equals("LAUNCH_DETAILS")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);
		}
		if (launchStatus.equals("LAUNCH_BASEPACKS")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);
		}
		if (launchStatus.equals("LAUNCH_STORES")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);

			LaunchClusterData launchClusterData = launchDao.getClusterDataByLaunchId(launchId);
			entirePageData.put("LAUNCH_CLUSTER", launchClusterData);
		}
		if (launchStatus.equals("LAUNCH_SELL_IN")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);

			LaunchClusterData launchClusterData = launchDao.getClusterDataByLaunchId(launchId);
			entirePageData.put("LAUNCH_CLUSTER", launchClusterData);

			LaunchSellInEditReponse launchSellInEditReponse = lauchSellInDao.getSellInByLaunchId(launchId);
			entirePageData.put("LAUNCH_SELL_INS", launchSellInEditReponse);
		}
		if (launchStatus.equals("LAUNCH_VISI_PLANNING")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);

			LaunchClusterData launchClusterData = launchDao.getClusterDataByLaunchId(launchId);
			entirePageData.put("LAUNCH_CLUSTER", launchClusterData);

			LaunchSellInEditReponse listOfLaunchSellIns = lauchSellInDao.getSellInByLaunchId(launchId);
			entirePageData.put("LAUNCH_SELL_INS", listOfLaunchSellIns);

			List<LaunchVisiPlanning> getVisiByVisiId = launchVisiPlanDao.getVisiByLaunchId(launchId);
			entirePageData.put("LAUNCH_VISI_PLANNING", getVisiByVisiId);
		}
		if (launchStatus.equals("LAUNCH_FINAL_BUILDUP")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);

			LaunchClusterData launchClusterData = launchDao.getClusterDataByLaunchId(launchId);
			entirePageData.put("LAUNCH_CLUSTER", launchClusterData);

			LaunchSellInEditReponse listOfLaunchSellIns = lauchSellInDao.getSellInByLaunchId(launchId);
			entirePageData.put("LAUNCH_SELL_INS", listOfLaunchSellIns);

			List<LaunchVisiPlanning> getVisiByVisiId = launchVisiPlanDao.getVisiByLaunchId(launchId);
			entirePageData.put("LAUNCH_VISI_PLANNING", getVisiByVisiId);

			List<LaunchFinalPlanResponse> listOfFinalResponse = launchDaoKam.getLaunchBuildUpByLaunchIdTme(launchId);
			entirePageData.put("LAUNCH_FINAL_BUILDUP", listOfFinalResponse);
		}
		if (launchStatus.equals("LAUNCH_SUBMIT")) {
			LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_DETAILS", launchDataResponse);

			List<LaunchKamBasepackResponse> listOfBasepackData = launchDaoKam.getTmeBasepackData(listOfLaunchIds);
			entirePageData.put("LAUNCH_BASEPACKS", listOfBasepackData);

			LaunchClusterData launchClusterData = launchDao.getClusterDataByLaunchId(launchId);
			entirePageData.put("LAUNCH_CLUSTER", launchClusterData);

			LaunchSellInEditReponse listOfLaunchSellIns = lauchSellInDao.getSellInByLaunchId(launchId);
			entirePageData.put("LAUNCH_SELL_INS", listOfLaunchSellIns);

			List<LaunchVisiPlanning> getVisiByVisiId = launchVisiPlanDao.getVisiByLaunchId(launchId);
			entirePageData.put("LAUNCH_VISI_PLANNING", getVisiByVisiId);

			List<LaunchFinalPlanResponse> listOfFinalResponse = launchDaoKam.getLaunchBuildUpByLaunchIdTme(launchId);
			entirePageData.put("LAUNCH_FINAL_BUILDUP", listOfFinalResponse);

			LaunchDataResponse launchSubmitResponse = launchDao.getSpecificLaunchData(launchId);
			entirePageData.put("LAUNCH_SUBMIT", launchSubmitResponse);
		}
		return entirePageData;
	}

	@Override
	public List<LaunchKamInputsResponse> getLaunchKamInputs(String userId) {
		return launchDao.getLaunchKamInputs(userId);
	}

	@Override
	public String rejectKamInputs(RejectByTmeRequest rejectByTme, String userId) {
		return launchDao.rejectKamInputs(rejectByTme, userId);
	}

	@Override
	public String acceptKamInputs(AcceptByTmeRequest acceptByTme, String userId) {
		return launchDao.acceptKamInputs(acceptByTme, userId);
	}

	@Override
	public List<LaunchKamQueriesAnsweredResponse> getLaunchQueriesAnswered(String userId) {
		return launchDao.getLaunchQueriesAnswered(userId);
	}

	@Override
	public String deleteAllNextPageData(String launchId, String currentPage, String userId) {
		return launchDao.deleteAllNextPageData(launchId, currentPage, userId);
	}

	@Override
	public String deleteAllKamData(String launchId) {
		return launchDao.deleteAllKamData(launchId);
	}
	//Q1 sprint feb 2021 kavitha
	@Override
	@Transactional
	public List<String> getAllMoc(String userId) {
		return launchDao.getAllMoc(userId);
	}
	
	//Q2 sprint feb 2021 kavitha
	@Override
	public List<LaunchDataResponse> getAllLaunchData(String userid, String launchMOC,String launchName) {
		return launchDao.getAllLaunchData(userid,launchMOC,launchName);
	}
	
	//Q2 sprint feb 2021 kavitha
		@Override
		@Transactional
		public List<String> getAllLaunchName(String userId,String tmeMoc) {
			return launchDao.getAllLaunchName(userId,tmeMoc);
		}
		
		//Q2 sprint feb 2021 kavitha
		@Override
		@Transactional
		public List<String> getAllCOEMoc() {
			return launchDao.getAllCOEMoc();
		}
		//Q2 sprint feb 2021 kavitha
				@Override
				@Transactional
				public List<String> getLaunchNameBasedOnMoc(String userId,String tmeMoc)
				{
					return launchDao.getLaunchNameBasedOnMoc(userId,tmeMoc);
				}
		
}