package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegularPromoService {

	@Autowired
	CreatePromoRegular createCRPromo;
	
	
	@Transactional(rollbackFor = { Exception.class })
	public String createCRPromo(CreateBeanRegular[] bean, String uid, String template,String listofcategory) throws Exception {
		return createCRPromo.createPromotion(bean, uid, template,listofcategory);
	}

	
	// Added by Kavitha D for downloading promo regular template starts-SPRINT 9

	public ArrayList<String> getHeaderListForPromotionRegularTemplateDownload() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("CHANNEL");
		headerList.add("YEAR");
		headerList.add("MOC");
		//headerList.add("SECONDARY CHANNEL");
		headerList.add("PPM ACCOUNT");
		headerList.add("PROMO TIMEPERIOD");
		//headerList.add("AB CREATION (ONLY FOR KA Accounts)");
		headerList.add("OFFER DESCRIPTION");
		headerList.add("BASEPACK CODE");
		headerList.add("BASEPACK DESCRIPTION");
		headerList.add("CHILDPACK CODE");
		
		headerList.add("OFFER TYPE");
		headerList.add("OFFER MODALITY");
		headerList.add("PRICE OFF");
		headerList.add("BUDGET");
		//headerList.add("BRANCH");
		headerList.add("CLUSTER");
		

		return headerList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public Map<String, List<List<String>>> getMastersForRegularTemplate() {
		return createCRPromo.getMastersForRegularTemplate();

	}
	// Added by Kavitha D for downloading promo regular template ends-SPRINT 9

	// Added by Kavitha D for downloading promo new template starts-SPRINT 9

	public ArrayList<String> getHeaderListForPromotionNewTemplateDownload() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("CHANNEL");
		headerList.add("YEAR");
		headerList.add("MOC");
		//headerList.add("SECONDARY CHANNEL");
		headerList.add("PPM ACCOUNT");
		headerList.add("PROMO TIMEPERIOD");
		//headerList.add("AB CREATION (ONLY FOR KA Accounts)");
		headerList.add("OFFER DESCRIPTION");
		headerList.add("BASEPACK CODE");
		headerList.add("BASEPACK DESCRIPTION");
		headerList.add("CHILDPACK CODE");
		
		headerList.add("OFFER TYPE");
		headerList.add("OFFER MODALITY");
		headerList.add("PRICE OFF");
		headerList.add("BUDGET");
		//headerList.add("BRANCH");
		headerList.add("CLUSTER");
		headerList.add("QUANTITY");
		return headerList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public Map<String, List<List<String>>> getMastersForNewTemplate() {
		return createCRPromo.getMastersForNewTemplate();

	}
	// Added by Kavitha D for downloading promo new template ends-SPRINT 9

	// Added by Kavitha D for downloading promo CR template starts-SPRINT 9
	@Transactional(rollbackFor = { Exception.class })
	public Map<String, List<List<String>>> getMastersForCrTemplate() {
		return createCRPromo.getMastersForCrTemplate();

	}

	public ArrayList<String> getHeaderListForPromotionCrTemplateDownload() {
		ArrayList<String> headerList = new ArrayList<String>();
		headerList.add("CHANNEL");
		headerList.add("YEAR");
		headerList.add("MOC");
		headerList.add("Promo Timeperiod");
		headerList.add("PPM SOL Code Reference");
		headerList.add("PPM ACCOUNT");
		headerList.add("OFFER DESCRIPTION");
		headerList.add("BASEPACK CODE");
		headerList.add("BASEPACK DESCRIPTION");
		headerList.add("CHILDPACK CODE");
		headerList.add("OFFER TYPE");
		headerList.add("OFFER MODALITY");
		headerList.add("QUANTITY");
		headerList.add("PRICE OFF");
		headerList.add("BUDGET");
		headerList.add("BRANCH");
		headerList.add("CLUSTER");
		headerList.add("CR TYPE");
		return headerList;
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public List<ArrayList<String>> getPromotionDownloadCR(ArrayList<String> headerDetail, String userId) {
		return createCRPromo.getPromotionDownloadCR(headerDetail,userId);
	}
	// Added by Kavitha D for downloading promo CR template ends-SPRINT 9

	public ArrayList<String> getHeaderListForPromotionErrorDownload(String error_template,String roleid) {
		ArrayList<String> headerList = new ArrayList<String>();
		if(error_template.equalsIgnoreCase("cr"))
		{
			headerList.add("CHANNEL");
			headerList.add("YEAR");
			headerList.add("MOC");
			headerList.add("Promo Timeperiod");
			headerList.add("PPM SOL Code Reference");
			headerList.add("PPM ACCOUNT");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("BASEPACK CODE");
			headerList.add("BASEPACK DESCRIPTION");
			headerList.add("CHILDPACK CODE");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("QUANTITY");
			headerList.add("PRICE OFF");
			headerList.add("BUDGET");
			headerList.add("BRANCH");
			headerList.add("CLUSTER");
			headerList.add("CR TYPE");
			headerList.add("TEMPLATE_TYPE");
			headerList.add("USER_ID");
			headerList.add("ERROR_MSG");
		
		}else if(error_template.equalsIgnoreCase("r") ||
				error_template.equalsIgnoreCase("ne"))
		{
			headerList.add("CHANNEL NAME");
			headerList.add("YEAR");
			headerList.add("MOC");			
			//headerList.add("SECONDARY CHANNEL");
			headerList.add("PPM ACCOUNT");
			headerList.add("PROMO TIMEPERIOD");
			//headerList.add("AB CREATION (ONLY FOR KA Accounts)");
			headerList.add("BASEPACK CODE");
			headerList.add("BASEPACK DESCRIPTION");
			headerList.add("CHILDPACK CODE");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("PRICE OFF");
			headerList.add("BUDGET");
			//headerList.add("BRANCH");
			headerList.add("CLUSTER");
			if(error_template.equalsIgnoreCase("ne") || roleid.equalsIgnoreCase("dp"))
			headerList.add("QUANTITY");
			headerList.add("TEMPLATE_TYPE");
			headerList.add("USER_ID");
			headerList.add("ERROR_MSG");
			
		}
		return headerList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerDetail, String userId,String error_template,String roleID) {

		return createCRPromo.getPromotionErrorDetails(headerDetail, userId,error_template, roleID);
	}

	public Map<String, List<List<String>>> getMastersForTemplate() {
		// TODO Auto-generated method stub
		return createCRPromo.getMastersForTemplate();
	}

	@Transactional(rollbackFor = { Exception.class })
	public String getTemplateType(String uid) {
		// TODO Auto-generated method stub
		return createCRPromo.getTemplateType(uid);
	}

	
	
}
