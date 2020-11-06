package com.hul.launch.service;

import java.util.List;

import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;

public interface LaunchServiceCoe {
	public List<TblLaunchMaster> getAllLaunchData(List<String> launchIds);

	public List<TblLaunchMaster> getLaunchArtworkPackshotsDocument(List<String> listOfLaunchData);

	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdCoe(List<String> listOfLaunchData,
			String userId);

	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdTme(String userId);
}