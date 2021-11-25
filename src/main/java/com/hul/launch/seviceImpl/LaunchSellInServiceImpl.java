package com.hul.launch.seviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchSellInDao;
import com.hul.launch.model.LaunchSellIn;
import com.hul.launch.request.DownloadSellInRequestList;
import com.hul.launch.request.SaveLaunchSellInRequest;
import com.hul.launch.request.SaveLaunchSellInRequestList;
import com.hul.launch.response.LaunchSellInReponse;
import com.hul.launch.service.LaunchSellInService;

@Service
@Transactional
public class LaunchSellInServiceImpl implements LaunchSellInService {

	@Autowired
	LaunchSellInDao launchSellInDao;
	
	@Override
	public LaunchSellInReponse getLaunchSellIn(String launchId, String userId) {
		return launchSellInDao.getLaunchSellInDetails(launchId, userId);
	}

	@Override
	public String saveLaunchSellIn(SaveLaunchSellInRequestList saveLaunchSellInRequest, String userId) {
		return launchSellInDao.saveLaunchSellIn(saveLaunchSellInRequest, userId);
	}

	@Override
	public List<ArrayList<String>> getSellInDump(String userId,
			DownloadSellInRequestList downloadLaunchSellInRequest) {
		return launchSellInDao.getSellInDump(userId,downloadLaunchSellInRequest);
	}
	
	// Added By Harsha for downloading ErrorList
	@Override
	public List<ArrayList<String>> getErrorSellInDump(String userId,
			DownloadSellInRequestList downloadLaunchSellInRequest) {
		return launchSellInDao.getErrorSellInDump(userId,downloadLaunchSellInRequest);
	}
	
	@Override
	public String saveSellInByUpload(List<Object> saveLaunchSellIn, String userID, String string, boolean b, boolean c,
			String launchId) {
		SaveLaunchSellInRequestList saveLaunchSellInRequestList = new SaveLaunchSellInRequestList();
		List<SaveLaunchSellInRequest> listsellIn = new ArrayList<>();
		Iterator<Object> iterator = saveLaunchSellIn.iterator();
		while (iterator.hasNext()) {
			LaunchSellIn obj = (LaunchSellIn) iterator.next();
			SaveLaunchSellInRequest saveLaunchSellInRequest = new SaveLaunchSellInRequest();
			saveLaunchSellInRequest.setL1Chain(obj.getL1_CHAIN());
			saveLaunchSellInRequest.setL2Chain(obj.getL2_CHAIN());
			saveLaunchSellInRequest.setStoreFormat(obj.getSTORE_FORMAT());
			saveLaunchSellInRequest.setStoresPlanned(obj.getSTORES_PLANNED());
			saveLaunchSellInRequest.setSku1(obj.getSKU1_SELLIN());
			saveLaunchSellInRequest.setSku2(obj.getSKU2_SELLIN());
			saveLaunchSellInRequest.setSku3(obj.getSKU3_SELLIN());
			saveLaunchSellInRequest.setSku4(obj.getSKU4_SELLIN());
			saveLaunchSellInRequest.setSku5(obj.getSKU5_SELLIN());
			saveLaunchSellInRequest.setSku6(obj.getSKU6_SELLIN());
			saveLaunchSellInRequest.setSku7(obj.getSKU7_SELLIN());
			saveLaunchSellInRequest.setSku8(obj.getSKU8_SELLIN());
			saveLaunchSellInRequest.setSku9(obj.getSKU9_SELLIN());
			saveLaunchSellInRequest.setSku10(obj.getSKU10_SELLIN());
			saveLaunchSellInRequest.setSku11(obj.getSKU11_SELLIN());
			saveLaunchSellInRequest.setSku12(obj.getSKU12_SELLIN());
			saveLaunchSellInRequest.setSku13(obj.getSKU13_SELLIN());
			saveLaunchSellInRequest.setSku14(obj.getSKU14_SELLIN());
			saveLaunchSellInRequest.setRotations(obj.getROTATIONS());
			saveLaunchSellInRequest.setUpliftN1(obj.getUPLIFT_N1());
			saveLaunchSellInRequest.setUpliftN2(obj.getUPLIFT_N2());
			listsellIn.add(saveLaunchSellInRequest);
		}

		saveLaunchSellInRequestList.setListOfSellIns(listsellIn);
		saveLaunchSellInRequestList.setLaunchId(launchId);
		String result = launchSellInDao.saveLaunchSellIn(saveLaunchSellInRequestList, userID);
		if (result.equals("Saved Successfully")) {
			return "SUCCESS_FILE";
		} else {
			return "ERROR";
		}
	}
	// Added By Harsha for saving in TEMP DB
	@Override
	public String validateSellInByUpload(List<Object> saveLaunchSellIn, String userID, String string, boolean b, boolean c,
			String launchId) {
		return launchSellInDao.validateSellInByUploadImpl( saveLaunchSellIn,  userID,  string,  b,  c, launchId);
	}
	
	// Added By Harsha for saving in TEMP DB
		@Override
		public int getCountofErrorMessage(String launchId) {
			return launchSellInDao.getCountofErrorMessage( launchId);
		}


}
