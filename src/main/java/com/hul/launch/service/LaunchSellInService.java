package com.hul.launch.service;

import java.util.ArrayList;
import java.util.List;
import com.hul.launch.request.DownloadSellInRequestList;
import com.hul.launch.request.SaveLaunchSellInRequestList;
import com.hul.launch.response.LaunchSellInReponse;

public interface LaunchSellInService {
	public LaunchSellInReponse getLaunchSellIn(String launchId, String userId);

	public String saveLaunchSellIn(SaveLaunchSellInRequestList saveLaunchSellInRequest, String userId);

	public List<ArrayList<String>> getSellInDump(String userId,
			DownloadSellInRequestList downloadLaunchSellInRequest);

	public String saveSellInByUpload(List<Object> saveLaunchSellIn, String userID, String string, boolean b, boolean c,
			String launchId);

	public String validateSellInByUpload(List<Object> saveLaunchSellIn, String userID, String string, boolean b, boolean c,
			String launchId);

	public int getCountofErrorMessage(String launchId);

	// Added By Harsha for downloading error file 
	public List<ArrayList<String>> getErrorSellInDump(String userId, DownloadSellInRequestList downloadLaunchSellInRequest);
}