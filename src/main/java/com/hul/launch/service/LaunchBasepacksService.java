package com.hul.launch.service;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.TblLaunchBasebacks;
import com.hul.launch.request.AccountRequest;
import com.hul.launch.request.DownloadLaunchClusterRequest;
import com.hul.launch.request.SaveLaunchBasepacksListReq;
import com.hul.launch.request.SaveLaunchClustersRequest;
import com.hul.launch.request.SaveLaunchMasterRequest;
import com.hul.launch.response.LaunchBasePackResponse;
import com.hul.launch.response.StoreDetailsResponse;

public interface LaunchBasepacksService {

	public List<String> getSalesCategory();

	public List<String> getBpClassification();

	public List<TblLaunchBasebacks> getPsaCategory(String psaCat);

	public List<TblLaunchBasebacks> getBrandPsaCategory(String psaCat, String salesCat);

	public String saveLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId);

	public int saveLaunchBasepacks(SaveLaunchBasepacksListReq tblLaunchbasePacks, String userId);

	public String saveBasepackByUpload(List<Object> list, String userId, String status, boolean isCreate,
			boolean isFromUi, String launchId) throws Exception;

	public List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId);

	public StoreDetailsResponse getLaunchStores(String launchId);

	public List<String> getLaunchStores(List<String> accountList);

	public List<String> getCustomerStoreFormat(List<String> launchStores);

	public String getStoreCount(List<AccountRequest> list);

	public List<String> getStoreFormat();

	public List<String> getTownSpecificData();

	public int saveLaunchClustersAndAcc(SaveLaunchClustersRequest saveLaunchClustersRequest, String userId);

	public String updateLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId);

	public String saveClusterByUpload(List<Object> list, String userID, String string, boolean b, boolean c,
			String launchId);

	public List<LaunchBasePackResponse> getLaunchBasePackDetails(String basepackCode);

	public List<String> getLaunchStores(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection

	public Object getCustomerStoreFormat(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection

	public String getStoreCountOnCust(String custStoreFormat, List<String> accountl1String,
			List<String> accountl2String, List<String> liClusterName, String classification
			, boolean isCustomStoreFormat);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection

	public String getStoreCountOnStore(String storeFormat, List<String> accountl1String, List<String> accountl2String,
			List<String> liClusterName, String classification
			, boolean isCustomStoreFormat);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection

	//Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
	//public String getStoreCountByClass(List<String> liClusterName, List<String> accountl1String,List<String> accountl2String, String classification);
	public String getStoreCountByClass(List<String> liClusterName, List<String> accountl1String, 
			List<String> accountl2String, String classification, boolean isCustomStoreFormat);

	List<String> getBasepackCodeOnLaunchId(String launchId);

	List<ArrayList<String>> getClusterDumpForStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest);

	List<ArrayList<String>> getClusterDumpforCustomerStoreformat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest);

	public List<ArrayList<String>> getKamInputDumpForLaunch(ArrayList<String> headerDetail, String userId);

	public String saveKamRequestByUpload(List<Object> list, String userID);

	public String saveClusterByUploadForCluster(List<Object> list, String userID, String string, boolean b, boolean c,
			String launchId);
	
	// Added By Harsha 
	public String saveClusterByUploadForCustomerStoreFormateCluster(List<Object> saveLaunchCluster, String userID, String string, boolean b,
			boolean c, String launchId);
	public String countofErrormsginCustStoreformate( String launchId); //countofFixedStores 
	public String countofErrormsginStoreformate( String launchId);
	public String countofFixedStores( String launchId);
	
	
	List<ArrayList<String>> getClusterErrorDumpforCustomerStoreformat(ArrayList<String> headerDetail, String userId,
			String launchId);
// Store formate 
	public String saveClusterByUploadForStoreFormateCluster(List<Object> saveLaunchCluster, String userID, String string, boolean b,
			boolean c, String launchId);

	
	public List<ArrayList<String>> getClusterErrorDumpforStoreformat(ArrayList<String> headerDetail, String userId,
			String LaunchID);
	

}