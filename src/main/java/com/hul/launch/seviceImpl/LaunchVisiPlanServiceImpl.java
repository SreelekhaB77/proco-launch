package com.hul.launch.seviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchVisiPlanDao;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.SaveLaunchVisiPlanRequest;
import com.hul.launch.request.SaveLaunchVisiPlanRequestList;
import com.hul.launch.response.LaunchVisiPlanResponse;
import com.hul.launch.service.LaunchVisiPlanService;

@Service
@Transactional
public class LaunchVisiPlanServiceImpl implements LaunchVisiPlanService {

	@Autowired
	LaunchVisiPlanDao launchVisiPlanDao;

	@Override
	public List<String> getLaunchAssetType() {
		return launchVisiPlanDao.getLaunchAssetType();
	}

	@Override
	public List<ArrayList<String>> getVisiPlanDump(ArrayList<String> headerDetail, String userId,
			SaveLaunchVisiPlanRequestList downloadLaunchVisiPlanRequest) {
		return launchVisiPlanDao.getVisiPlanDump(headerDetail, userId, downloadLaunchVisiPlanRequest);
	}

	@Override
	public String saveLaunchVisiPlan(SaveLaunchVisiPlanRequestList saveLaunchVisiPlanRequest, String userId) {
		return launchVisiPlanDao.saveLaunchVisiPlan(saveLaunchVisiPlanRequest, userId);
	}

	@Override
	public String saveVisiPlanByUpload(List<Object> saveLaunchVisiPlans, String userID, String string, boolean b,
			boolean c, String launchId) {
		try {
			List<SaveLaunchVisiPlanRequest> listVisiPlan = new ArrayList<>();
			Iterator<Object> iterator = saveLaunchVisiPlans.iterator();
			SaveLaunchVisiPlanRequestList saveLaunchVisiPlansListReq = new SaveLaunchVisiPlanRequestList();
			while (iterator.hasNext()) {
				LaunchVisiPlanning obj = (LaunchVisiPlanning) iterator.next();
				SaveLaunchVisiPlanRequest saveLaunchVisiPlanRequest = new SaveLaunchVisiPlanRequest();
				saveLaunchVisiPlanRequest.setAccount(obj.getACCOUNT());
				saveLaunchVisiPlanRequest.setFormat(obj.getFORMAT());
				if(Integer.parseInt(obj.getSTORES_AVAILABLE()) < Integer.parseInt(obj.getSTORES_PLANNED())) {
					throw new Exception("No of stores planned can't be greater than no of stores available");
				}
				saveLaunchVisiPlanRequest.setStoresAvailable(Integer.parseInt(obj.getSTORES_AVAILABLE()));
				saveLaunchVisiPlanRequest.setStoresPlanned(Integer.parseInt(obj.getSTORES_PLANNED()));
				saveLaunchVisiPlanRequest.setVisiAsset1(obj.getVISI_ASSET_1());
				saveLaunchVisiPlanRequest.setVisiAsset2(obj.getVISI_ASSET_2());
				saveLaunchVisiPlanRequest.setVisiAsset3(obj.getVISI_ASSET_3());
				saveLaunchVisiPlanRequest.setVisiAsset4(obj.getVISI_ASSET_4());
				saveLaunchVisiPlanRequest.setVisiAsset5(obj.getVISI_ASSET_5());
				saveLaunchVisiPlanRequest.setFacingsPerShelf1(obj.getFACING_PER_SHELF_PER_SKU1());
				saveLaunchVisiPlanRequest.setDepthPerShelf1(obj.getDEPTH_PER_SHELF_PER_SKU1());
				saveLaunchVisiPlanRequest.setFacingsPerShelf2(obj.getFACING_PER_SHELF_PER_SKU2());
				saveLaunchVisiPlanRequest.setDepthPerShelf2(obj.getDEPTH_PER_SHELF_PER_SKU2());
				saveLaunchVisiPlanRequest.setFacingsPerShelf3(obj.getFACING_PER_SHELF_PER_SKU3());
				saveLaunchVisiPlanRequest.setDepthPerShelf3(obj.getDEPTH_PER_SHELF_PER_SKU3());
				saveLaunchVisiPlanRequest.setFacingsPerShelf4(obj.getFACING_PER_SHELF_PER_SKU4());
				saveLaunchVisiPlanRequest.setDepthPerShelf4(obj.getDEPTH_PER_SHELF_PER_SKU4());
				saveLaunchVisiPlanRequest.setFacingsPerShelf5(obj.getFACING_PER_SHELF_PER_SKU5());
				saveLaunchVisiPlanRequest.setDepthPerShelf5(obj.getDEPTH_PER_SHELF_PER_SKU5());
				listVisiPlan.add(saveLaunchVisiPlanRequest);
			}

			saveLaunchVisiPlansListReq.setListOfVisiPlans(listVisiPlan);
			saveLaunchVisiPlansListReq.setLaunchId(Integer.parseInt(launchId));
			String result = saveLaunchVisiPlan(saveLaunchVisiPlansListReq, userID);
			if (result.equals("Saved Successfully")) {
				return "SUCCESS_FILE";
			} else {
				return "ERROR";
			}
		} catch (Exception e) {
			return e.toString();
		}

	}

	@Override
	public List<LaunchVisiPlanResponse> getVisiPlanLandingScreen(String launchId) {
		return launchVisiPlanDao.getVisiPlanLandingScreen(launchId);
	}

	@Override
	public String saveLaunchNoVisiPlan(String launchId, String userId) {
		return launchVisiPlanDao.saveLaunchNoVisiPlan(launchId,userId);
	}
}