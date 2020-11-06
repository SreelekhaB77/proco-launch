package com.hul.launch.seviceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public List<LaunchDataResponse> getAllCompletedLaunchData(String account) {
		return launchDaoKam.getAllCompletedKamLaunchData(account);
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

	@Override
	public ArrayList<String> getHeaderListForBaseFile() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("L1_CHAIN");
		headerList.add("L2_CHAIN");
		headerList.add("STORE_FORMAT");
		headerList.add("CLUSTER");
		headerList.add("HUL_OL_CODE");
		headerList.add("KAM_REMARKS");
		return headerList;
	}

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
	public List<KamChangeReqRemarks> getApprovalStatusKam(String userId) {
		return launchDaoKam.getApprovalStatusKam(userId);
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

}