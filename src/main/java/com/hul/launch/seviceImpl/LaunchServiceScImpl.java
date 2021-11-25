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

	//Q2 Sprint feb 2021 kavitha
	@Override
	
	//public List<LaunchDataResponse> getAllCompletedLaunchData() {
	public List<LaunchDataResponse> getAllCompletedLaunchData(String scMoc)
	{
		//return launchDaoSc.getAllCompletedScLaunchData();
		return launchDaoSc.getAllCompletedScLaunchData(scMoc);
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
	
	// Harsha's changes Q6
	@Override
	public List<String> getScMstnClearanceDataChange(List<String> listOfLaunchData) {
		return launchDaoSc.getMocDates(listOfLaunchData);
	}
	
	// Added By Harsha for getting MSTN cleared Data in Q6
	@Override
	public List<LaunchScMstnClearanceResponse> getScMstnClearanceDataByfilter(List<String> listOfLaunchData, List<String> getdistinctMOC) {
		return launchDaoSc.getScMstnClearanceDataFilter(listOfLaunchData,  getdistinctMOC);
	}
	


	@Override
	public String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId) {
		return launchDaoSc.saveMstnClearanceByLaunchIdsSc(requestMstnClearanceList, userId);
	}

	@Override
	public String uploadMstnClearanceByLaunchIdSc(List<Object> list, String userID, String string, boolean b, boolean c) {
		return launchDaoSc.uploadMstnClearanceByLaunchIdSc(list, userID);
	}
	

	//Q2 Sprint feb 2021 kavitha
	@Override
	@Transactional
	public List<String> getAllMoc() {
		return launchDaoSc.getAllMoc();
	}

	
}