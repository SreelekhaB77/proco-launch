package com.hul.launch.service;

import java.util.List;

import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchDpBasepackResponse;
import com.hul.launch.response.LaunchDpFinalBuildUpResponse;

public interface LaunchServiceDp {
	public List<LaunchDataResponse> getAllCompletedLaunchData();

	public List<LaunchDpBasepackResponse> getDpBasepackData(List<String> listOfLaunchData);

	public List<LaunchDpFinalBuildUpResponse> getDpBuildUpData(List<String> listOfLaunchData);
}