package com.hul.launch.service;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.request.SaveLaunchVisiPlanRequestList;
import com.hul.launch.response.LaunchVisiPlanResponse;

public interface LaunchVisiPlanService {
	List<String> getLaunchAssetType();

	String saveVisiPlanByUpload(List<Object> list, String userID, String string, boolean b, boolean c, String launchId);

	List<ArrayList<String>> getVisiPlanDump(ArrayList<String> headerDetail, String userId,
			SaveLaunchVisiPlanRequestList downloadLaunchVisiPlanRequest);

	String saveLaunchVisiPlan(SaveLaunchVisiPlanRequestList saveLaunchVisiPlanRequest, String userId);

	List<LaunchVisiPlanResponse> getVisiPlanLandingScreen(String launchId);

	String saveLaunchNoVisiPlan(String launchId, String userId);
}