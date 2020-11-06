package com.hul.launch.dao;

import java.util.List;

import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchDaoCoe {
	public List<TblLaunchMaster> getAllLaunchData(List<String> launchIds);

	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdCoe(List<String> listOfLaunchData, String userId);

	public List<LaunchMstnClearanceResponseCoe> getCoeMstnClearanceData(List<String> listOfLaunchData);

	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdTme(String userId);
}