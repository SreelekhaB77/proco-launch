package com.hul.launch.dao;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.SaveLaunchVisiPlanRequestList;
import com.hul.launch.response.LaunchVisiPlanResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchVisiPlanDao {
	List<String> getLaunchAssetType();

	String saveLaunchVisiPlan(SaveLaunchVisiPlanRequestList saveLaunchVisiPlanRequest, String userId);

	List<ArrayList<String>> getVisiPlanDump(ArrayList<String> headerDetail, String userId,
			SaveLaunchVisiPlanRequestList downloadLaunchVisiPlanRequest);

	List<LaunchVisiPlanResponse> getVisiPlanLandingScreen(String launchId);

	String getVisiByLaunch(String launchId);

	int getShelvesByVisiName(String visiName);

	List<String> getVisiByLaunchInd(String launchId);

	List<LaunchVisiPlanning> getVisiByLaunchId(String launchId);

	String saveLaunchNoVisiPlan(String launchId, String userId);
}