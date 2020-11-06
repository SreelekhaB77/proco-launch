package com.hul.launch.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchDaoDp;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchDpBasepackResponse;
import com.hul.launch.response.LaunchDpFinalBuildUpResponse;
import com.hul.launch.service.LaunchServiceDp;

@Service
@Transactional
public class LaunchServiceDpImpl implements LaunchServiceDp {

	@Autowired
	LaunchDaoDp launchDaoDp;

	@Override
	public List<LaunchDataResponse> getAllCompletedLaunchData() {
		return launchDaoDp.getAllCompletedDpLaunchData();
	}

	@Override
	public List<LaunchDpBasepackResponse> getDpBasepackData(List<String> listOfLaunchData) {
		return launchDaoDp.getDpBasepackData(listOfLaunchData);
	}

	@Override
	public List<LaunchDpFinalBuildUpResponse> getDpBuildUpData(List<String> listOfLaunchData) {
		return launchDaoDp.getDpBuildUpData(listOfLaunchData);
	}

}