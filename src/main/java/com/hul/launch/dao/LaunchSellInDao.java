package com.hul.launch.dao;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.LaunchSellIn;
import com.hul.launch.model.LaunchStoreData;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.DownloadSellInRequestList;
import com.hul.launch.request.SaveLaunchSellInRequestList;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchSellInEditReponse;
import com.hul.launch.response.LaunchSellInReponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchSellInDao {
	public String saveLaunchSellIn(SaveLaunchSellInRequestList saveLaunchSellInRequest, String userId);

	public LaunchSellInReponse getLaunchSellInDetails(String launchId, String userId);

	public List<ArrayList<String>> getSellInDump(String userId, DownloadSellInRequestList downloadLaunchSellInRequest);

	public LaunchSellInEditReponse getSellInByLaunchId(String launchId);

	public double getSellInForSellInN(int i, String skuName);

	public double getTotalUplift(String launchId, String whichUplift, String skuName);

	public List<String> getRevisedVisiSellInForSellInN(int parseInt, String string, List<String> visiSkuCal);

	public List<LaunchSellIn> getSellInForSellInNFinal(int launchId, String Data);

	public List<LaunchSellIn> getSellInForSellInNFinalEdit(int launchId, List<LaunchFinalPlanResponse> listOfBasepacks);

	public List<LaunchStoreData> getListStoreData(LaunchSellIn launchSellIn, List<LaunchFinalPlanResponse> listOfFinal,
			LaunchVisiPlanning launchVisiPlanning, String classification, List<String> liClusterName);

	public String getClusterOnLaunchId(String launchId);

	public List<LaunchStoreData> getListStoreDataKAM(LaunchSellIn launchSellIn,
			List<LaunchFinalPlanResponse> listOfFinal, LaunchVisiPlanning launchVisiPlanning, String classification,
			List<String> liClusterName, String forWhichKam);
}