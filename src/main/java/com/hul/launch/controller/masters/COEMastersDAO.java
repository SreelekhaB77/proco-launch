package com.hul.launch.controller.masters;

import java.util.ArrayList;
import java.util.List;

public interface COEMastersDAO {
	public String insertStoreMaster(StoreMasterBean[] bean, String userId);

	public List<ArrayList<String>> getStoreMasterErrorDetails(ArrayList<String> headerList, String userId);
	
	public String insertLaunchPlanClassificationMaster(ClassificationBean[] bean, String userId);
	
	public List<ArrayList<String>> getLaunchPlanClassificationErrorDetails(ArrayList<String> headerList,String userId);
}
