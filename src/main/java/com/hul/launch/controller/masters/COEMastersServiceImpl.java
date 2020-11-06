package com.hul.launch.controller.masters;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class COEMastersServiceImpl implements COEMastersService {

	@Autowired
	private COEMastersDAO coeMastersDao;

	@Override
	public String insertStoreMaster(StoreMasterBean[] bean, String userId) {
		return coeMastersDao.insertStoreMaster(bean, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForStoreMasterTemplateDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("HUL_OUTLET_CODE");
		headerList.add("CUSTOMER_CODE");
		headerList.add("SERVICING_TYPE");
		headerList.add("CUSTOMER_CHAIN_L1");
		headerList.add("HUL_STORE_FORMAT");
		headerList.add("CUSTOMER_STORE_FORMAT");
		headerList.add("BRANCH");
		headerList.add("STATE");
		headerList.add("TOWN_NAME");
		headerList.add("HUL_DEPOT");
		headerList.add("CLUSTER");
		headerList.add("ACTIVE(1/0)");
		return headerList;
	}

	@Override
	public ArrayList<String> getHeaderListForStoreMasterErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("HUL_OUTLET_CODE");
		headerList.add("CUSTOMER_CODE");
		headerList.add("SERVICING_TYPE");
		headerList.add("CUSTOMER_CHAIN_L1");
		headerList.add("HUL_STORE_FORMAT");
		headerList.add("CUSTOMER_STORE_FORMAT");
		headerList.add("BRANCH");
		headerList.add("STATE");
		headerList.add("TOWN_NAME");
		headerList.add("HUL_DEPOT");
		headerList.add("CLUSTER");
		headerList.add("ACTIVE(1/0)");
		headerList.add("ERROR_MSG");
		return headerList;
	}

	@Override
	public List<ArrayList<String>> getStoreMasterErrorDetails(ArrayList<String> headerList, String userId) {
		return coeMastersDao.getStoreMasterErrorDetails(headerList, userId);
	}

	@Override
	public ArrayList<String> getHeaderListLaunchPlanClassificationTemplateDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("CUSTOMER_CHAIN_L1");
		headerList.add("CUSTOMER_STORE_FORMAT");
		headerList.add("GOLD");
		headerList.add("SILVER");
		headerList.add("BRONZE");
		headerList.add("VISIBILITY_ELIGIBILITY");
		return headerList;
	}

	@Override
	public ArrayList<String> getHeaderListForLaunchPlanClassificationErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("CUSTOMER_CHAIN_L1");
		headerList.add("CUSTOMER_STORE_FORMAT");
		headerList.add("GOLD");
		headerList.add("SILVER");
		headerList.add("BRONZE");
		headerList.add("VISIBILITY_ELIGIBILITY");
		headerList.add("ERROR_MSG");
		return headerList;
	}

	@Override
	public List<ArrayList<String>> getLaunchPlanClassificationErrorDetails(ArrayList<String> headerList,
			String userId) {
		return coeMastersDao.getLaunchPlanClassificationErrorDetails(headerList, userId);
	}

	@Override
	public String insertLaunchPlanClassificationMaster(ClassificationBean[] bean, String userId) {
		return coeMastersDao.insertLaunchPlanClassificationMaster(bean, userId);
	}

}
