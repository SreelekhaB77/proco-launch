package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CreatePromoRegular  {
	
	public String createPromotion(CreateBeanRegular bean[],String uid,String template);

	//Added by Kavitha D for Promo templates starts-SPRINT 9
	public Map<String, List<List<String>>> getMastersForRegularTemplate();

	public Map<String, List<List<String>>> getMastersForNewTemplate();

	public Map<String, List<List<String>>> getMastersForCrTemplate();
    //Added by Kavitha D for promo templates ends-SPRINT 9
}
