package com.hul.launch.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchDaoSc;
import com.hul.launch.request.RequestMstnClearanceList;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchScBasepackResponse;
import com.hul.launch.response.LaunchScFinalBuildUpResponse;
import com.hul.launch.response.LaunchScMstnClearanceResponse;
import com.hul.launch.service.LaunchServiceSc;

@Service
@Transactional
public class LaunchServiceScImpl implements LaunchServiceSc {

	@Autowired
	LaunchDaoSc launchDaoSc;

	@Override
	public List<LaunchDataResponse> getAllCompletedLaunchData() {
		return launchDaoSc.getAllCompletedScLaunchData();
	}

	@Override
	public List<LaunchScBasepackResponse> getScBasepackData(List<String> launchIds) {
		return launchDaoSc.getScBasepackData(launchIds);
	}

	@Override
	public List<LaunchScFinalBuildUpResponse> getScBuildUpData(List<String> listOfLaunchData) {
		return launchDaoSc.getScBuildUpData(listOfLaunchData);
	}

	@Override
	public List<LaunchScMstnClearanceResponse> getScMstnClearanceData(List<String> listOfLaunchData) {
		return launchDaoSc.getScMstnClearanceData(listOfLaunchData);
	}

	@Override
	public String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId) {
		return launchDaoSc.saveMstnClearanceByLaunchIdsSc(requestMstnClearanceList, userId);
	}

	@Override
	public String uploadMstnClearanceByLaunchIdSc(List<Object> list, String userID, String string, boolean b, boolean c) {
		return launchDaoSc.uploadMstnClearanceByLaunchIdSc(list, userID);
	}
}