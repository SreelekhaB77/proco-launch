package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CreatePromoRegular  {
	
	public String createPromotion(CreateBeanRegular bean[],String uid,String template,String listofcategory);

	//Added by Kavitha D for Promo templates starts-SPRINT 9
	public Map<String, List<List<String>>> getMastersForRegularTemplate();

	public Map<String, List<List<String>>> getMastersForNewTemplate();

	public Map<String, List<List<String>>> getMastersForCrTemplate();
	
	public List<ArrayList<String>> getPromotionDownloadCR(ArrayList<String> headerDetail, String userId);

    //Added by Kavitha D for promo templates ends-SPRINT 9

	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerDetail, String userId,String error_template);

	public Map<String, List<List<String>>> getMastersForTemplate();

	public String getTemplateType(String uid);

	
}
