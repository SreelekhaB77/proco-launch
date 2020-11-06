package com.hul.launch.controller.masters;

import java.util.ArrayList;
import java.util.List;

public interface COEMastersService {

	public String insertStoreMaster(StoreMasterBean[] bean, String userId);

	public ArrayList<String> getHeaderListForStoreMasterTemplateDownload();

	public ArrayList<String> getHeaderListForStoreMasterErrorFileDownload();
	
	public List<ArrayList<String>> getStoreMasterErrorDetails(ArrayList<String> headerList,String userId);
	
	public String insertLaunchPlanClassificationMaster(ClassificationBean[] bean, String userId);
	
	public ArrayList<String> getHeaderListLaunchPlanClassificationTemplateDownload();

	public ArrayList<String> getHeaderListForLaunchPlanClassificationErrorFileDownload();
	
	public List<ArrayList<String>> getLaunchPlanClassificationErrorDetails(ArrayList<String> headerList,String userId);

}
