package com.hul.launch.dao;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

import com.hul.launch.model.LaunchBuildUpTemp;
import com.hul.launch.model.LaunchFinalCalVO;
import com.hul.launch.model.LaunchStoreData;
import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.response.LaunchFinalPlanResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchFinalDao {
	List<LaunchFinalPlanResponse> getLaunchFinalRespose(String launchId);

	String saveLaunchFinalBuildUp(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId);

	String saveLaunchBuildUpTemp(List<List<LaunchStoreData>> listLaunchStoreData, String launchId, String userId);

	LaunchBuildUpTemp getFinalBuildUpDepoLevel(String depoCombo, String launchId);

	List<String> getFinalBuildUpDepoLevelDistinct(String launchId);

	LaunchBuildUpTemp getFinalBuildUpDepoLevelAll(String depoBasepack, String launchId);

	LaunchBuildUpTemp getCldForDepoBasepack(String depoBasepack, String launchId);

	LaunchBuildUpTemp getGsvForDepoBasepack(String substr, String launchId);

	int updateFinalValue(String depoCombo, String launchId, LaunchBuildUpTemp launchBuildUpTemp, String userId);
	
	public int updateFinalValue(List<LaunchFinalCalVO> launchFinalVoList); //Sarin Changes 18Nov2020

	LaunchFinalPlanResponse getSumOfForDepoBasepack(String basepack, String launchId);

	String saveFinalValue(String depoBasepackFmcgModifiedChainCombo, String launchId,
			LaunchBuildUpTemp launchBuildUpTemp, String userId);

	void deleteAllBuildUp(String launchId);

	List<ArrayList<String>> getFinalBuildUpDumptNew(String userId, String launchId);

	List<ArrayList<String>> getFinalBuildUpDumptNew(String userId, String[] launchId,String[] launchMoc);

	List<ArrayList<String>> getFinalBuildUpDump(String userId, String[] launchId);

	List<LaunchFinalPlanResponse> getLaunchFinalResposeKAM(String launchId, String forWhichKam);

	List<String> getFinalBuildUpDepoLevelDistinctKAM(String launchId, String forWhichKam);

	LaunchBuildUpTemp getFinalBuildUpDepoLevelAllKAM(String depoBasepack, String launchId, String forWhichKam);

	LaunchBuildUpTemp getCldForDepoBasepackKAM(String depoBasepack, String launchId, String forWhichKam);

	int updateFinalValueKAM(String depoCombo, String launchId, LaunchBuildUpTemp launchBuildUpTemp, String userId);

	LaunchFinalPlanResponse getSumOfForDepoBasepackKAM(String basepack, String launchId, String forWhichKam);

	void deleteAllBuildUpKAM(String launchId, String forWhichKam);

	LaunchBuildUpTemp getFinalBuildUpDepoLevelKAM(String depoCombo, String launchId, String forWhichAcc);

	String saveLaunchBuildUpTempKAM(List<List<LaunchStoreData>> listLaunchStoreData, String launchId,
			String forWhichKam);

	String saveFinalValueKAM(String depoBasepackFmcgModifiedChainCombo, String launchId,
			LaunchBuildUpTemp launchBuildUpTemp, String forWhichKam);

	void deleteAllTempCalKam(String launchId, String forWhichKam);

	void deleteAllTempCal(String launchId);

	LaunchBuildUpTemp getGsvForDepoBasepackKAM(String depoBasepack, String launchId, String forWhichKam);

	String saveLaunchFinalBuildUpKAM(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId,
			String forWhichKam);

	List<ArrayList<String>> getFinalBuildUpDumpNewKam(String userId, String launchId);

	List<String> getKamAccount(String userId);
	List<String> getKamAccount(String userId, String LaunchId);  //Added By Sarin - Sprint4Aug21 - for Launch Account wise Rejection

	void deleteAllBuildUpKAM(String launchId, List<String> listOfKamAccounts);

	void deleteAllBuildUpKAMBp(String launchId, List<String> listOfKamAccounts, List<String> bpCodes);

	void deleteAllBuildUpKAMStore(String launchId, List<String> listOfKamAccounts, List<String> storeIds);

	List<ArrayList<String>> getMstnClearanceDataDump(String userId, List<String> listOfLaunchData);

	List<ArrayList<String>> getMstnClearanceDataDumpCoe(String userId, List<String> listOfLaunchData);
	
	public String saveFinalValue(List<LaunchFinalCalVO> launchFinalVoList);
	
	public List<LaunchBuildUpTemp> getFinalBuildUpDepoLevelAllList(List<String> depoBasePackList, String launchId);
	
	public List<LaunchBuildUpTemp> getCldForDepoBasepackList(List<String> depoBasePackList, String launchId);
	
	public List<LaunchBuildUpTemp> getFinalBuildUpDepoLeveList(List<String> depoBasepackFmcgModifiedChainClusComboList, String launchId);
	
	//Sarin Changes - Launch Issue Feb2021 
	public Map<String, String> getCldGsvForDepoBasepack(String launchId);
	public List<LaunchBuildUpTemp> getFinalBuildUpDepoLevelList(String launchId);

	List<ArrayList<String>> getDisaggregatedByDp(String[] promoId);

	
}