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
	public String createCRPromo(CreateBeanRegular[] bean,String uid,String template) throws Exception {
		return createCRPromo.createPromotion(bean,uid,template);
	}
	
	
	//Added by Kavitha D for downloading promo regular template starts-SPRINT 9
	
		public ArrayList<String> getHeaderListForPromotionRegularTemplateDownload() {
			ArrayList<String> headerList = new ArrayList<String>();
			headerList.add("CHANNEL");
			headerList.add("MOC");
			headerList.add("SECONDARY CHANNEL");
			headerList.add("PPM ACCOUNT");
			headerList.add("PROMO TIMEPERIOD");
			headerList.add("AB CREATION (ONLY FOR KA Accounts)");
			headerList.add("BASEPACK CODE");
			headerList.add("BASEPACK DESCRIPTION");
			headerList.add("CHILDPACK CODE");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("PRICE OFF");
			headerList.add("BUDGET");
			headerList.add("BRANCH");
			headerList.add("CLUSTER");
			
			return headerList;
		}
		
		@Transactional(rollbackFor = { Exception.class })
		public Map<String,List<List<String>>> getMastersForRegularTemplate(){
			return createCRPromo.getMastersForRegularTemplate();
		
		}
		//Added by Kavitha D for downloading promo regular template ends-SPRINT 9
		
		
		//Added by Kavitha D for downloading promo new template starts-SPRINT 9
		
			public ArrayList<String> getHeaderListForPromotionNewTemplateDownload() {
				ArrayList<String> headerList = new ArrayList<String>();
				headerList.add("CHANNEL");
				headerList.add("MOC");
				headerList.add("SECONDARY CHANNEL");
				headerList.add("PPM ACCOUNT");
				headerList.add("PROMO TIMEPERIOD");
				headerList.add("AB CREATION (ONLY FOR KA Accounts)");
				headerList.add("BASEPACK CODE");
				headerList.add("CHILDPACK CODE");
				headerList.add("PROMO DESCRIPTION");
				headerList.add("OFFER TYPE");
				headerList.add("OFFER MODALITY");
				headerList.add("PRICE OFF");
				headerList.add("BUDGET");
				headerList.add("BRANCH");
				headerList.add("CLUSTER");
				headerList.add("QTY");

				
				return headerList;
			}
			
			@Transactional(rollbackFor = { Exception.class })
			public Map<String,List<List<String>>> getMastersForNewTemplate(){
				return createCRPromo.getMastersForNewTemplate();
			
			}
			//Added by Kavitha D for downloading promo new template ends-SPRINT 9
			

			//Added by Kavitha D for downloading promo CR template starts-SPRINT 9
			@Transactional(rollbackFor = { Exception.class })
			public Map<String,List<List<String>>> getMastersForCrTemplate(){
				return createCRPromo.getMastersForCrTemplate();
			
			}
			
			public ArrayList<String> getHeaderListForPromotionCrTemplateDownload() {
				ArrayList<String> headerList = new ArrayList<String>();
				headerList.add("CHANNEL");
				headerList.add("MOC");
				headerList.add("SECONDARY CHANNEL");
				headerList.add("PPM ACCOUNT");
				headerList.add("EXISTING SOL CODE");
				headerList.add("AB CREATION");
				headerList.add("SOL WILL RELEASE ON");
				headerList.add("BASEPACK CODE");
				headerList.add("PPM DESCRIPTION");
				headerList.add("PRICE OFF");
				headerList.add("BRANCH");
				headerList.add("CLUSTER CODE");
				headerList.add("CLUSTER");
				headerList.add("QTY");
				headerList.add("BUDGET");
				headerList.add("SOL TYPE");
				headerList.add("REMARK");
				headerList.add("END DATE");
				headerList.add("CLUSTER SELECTION");
				headerList.add("BASEPACK ADDITION");
				headerList.add("TOPUP");
				headerList.add("ADDITIONAL QTY");
				headerList.add("ADDITIONAL BUDGET");

				return headerList;
			}
}			//Added by Kavitha D for downloading promo CR template ends-SPRINT 9

