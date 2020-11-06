package com.hul.launch.dao;

import java.util.ArrayList;
import java.util.List;

import com.hul.launch.model.TblLaunchBasebacks;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.model.TblLaunchRequest;
import com.hul.launch.request.DownloadLaunchClusterRequest;
import com.hul.launch.request.SaveLaunchBasepacksListReq;
import com.hul.launch.request.SaveLaunchClustersRequest;
import com.hul.launch.request.SaveLaunchMasterRequest;
import com.hul.launch.response.LaunchBasePackResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;

/** 
 * 
 * @author anshuman.shrivastava
 *
 **/

public interface LaunchBasePacksDao {
	public List<String> getSalesCatData();

	public List<TblLaunchBasebacks> getPsaCatData(String salesCat);

	public List<TblLaunchBasebacks> getBrandOnPsaCatData(String psaCat, String salesCat);

	public String saveLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId);

	public int saveLaunchBasepacks(SaveLaunchBasepacksListReq tblLaunchbasePacks, String userId);

	public List<ArrayList<String>> getLaunchBasepackData(ArrayList<String> headerDetail, String userId);

	public TblLaunchMaster getStoreDetails(String launchId);

	public List<String> getLaunchStores();

	public List<String> getLaunchStores(List<String> accounts);

	public int storeCount(List<String> listOfL2);

	public List<String> getCustomerStoreFormat(List<String> launchStore);

	public String getStoreCount(List<String> liString);

	public List<String> getStoreFormat();

	public List<String> getTownSpecific();

	public int saveLaunchClustersAndAcc(SaveLaunchClustersRequest saveLaunchClustersRequest, String userId);

	public String updateLaunchMaster(SaveLaunchMasterRequest tblLaunchMaster, String userId);

	public List<LaunchBasePackResponse> getLaunchBasePackDetails(String basepackCode);

	public List<LaunchCoeBasePackResponse> getLaunchFinalRespose(List<String> launchIds);

	public int storeCount(List<String> listOfL1, List<String> listOfL2);

	public List<String> getBpClassification();

	public List<String> getLaunchStores(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification);

	public Object getCustomerStoreFormat(List<String> liClusterName, List<String> accountl1String,
			List<String> accountl2String, String classification);

	public String getStoreCountOnCust(String custStoreFormat, List<String> accountl1String,
			List<String> accountl2String, List<String> liClusterName, String classification);

	public String getStoreCountOnStore(String storeFormat, List<String> accountl1String, List<String> accountl2String,
			List<String> liClusterName, String classification);

	public int storeCount(String storeFormat, String accountL1, String accountDataL2, List<String> listOfCustStores,
			List<String> liClusterName);

	public int storeCount(String storeFormat, String accountL1, String accountDataL2, List<String> liClusterName);

	public List<String> getBasepackCodeOnLaunchId(String launchId);

	public List<ArrayList<String>> getLaunchClusterDataforStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest);

	List<ArrayList<String>> getLaunchClusterDataforCustomerStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest);

	public List<ArrayList<String>> getKamInputDumpForLaunch(ArrayList<String> headerDetail, String userId);

	public TblLaunchRequest getReqDataByReqId(String req_Id);

	int storeCountCust(String custStoreFormat, String accountL1, String accountL2, List<String> liCluster);

	public int storeCountByLaunchClass(String classification);

	String getStoreCountByClass(List<String> clusterList, List<String> accountl1String, List<String> accountl2String,
			String classification);

	String getStoreCountOnCustSellIIn(String storeFormat, List<String> liCluster, String classification);

	public List<String> getBasepackCodeOnBpId(List<String> bpIds, String launchId);
}