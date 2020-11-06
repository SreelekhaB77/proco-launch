package com.hul.launch.dao;

import java.util.List;

import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchDpBasepackResponse;
import com.hul.launch.response.LaunchDpFinalBuildUpResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchDaoDp {
	List<LaunchDataResponse> getAllCompletedDpLaunchData();

	List<LaunchDpBasepackResponse> getDpBasepackData(List<String> listOfLaunchData);

	List<LaunchDpFinalBuildUpResponse> getDpBuildUpData(List<String> listOfLaunchData);
}