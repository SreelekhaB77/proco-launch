package com.hul.launch.seviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchDaoCoe;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;
import com.hul.launch.service.LaunchServiceCoe;

@Service
@Transactional
public class LaunchServiceCoeImpl implements LaunchServiceCoe {

	@Autowired
	LaunchDaoCoe launchDaoCoe;

	@Override
	public List<TblLaunchMaster> getAllLaunchData(List<String> launchIds) {
		return launchDaoCoe.getAllLaunchData(launchIds);
	}

	@Override
	public List<TblLaunchMaster> getLaunchArtworkPackshotsDocument(List<String> listOfLaunchData) {
		return launchDaoCoe.getAllLaunchData(listOfLaunchData);
	}

	@Override
	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdCoe(List<String> listOfLaunchData,
			String userId) {
		return launchDaoCoe.getMstnClearanceByLaunchIdCoe(listOfLaunchData, userId);
	}

	@Override
	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdTme(String userId) {
		return launchDaoCoe.getMstnClearanceByLaunchIdTme(userId);
	}

}