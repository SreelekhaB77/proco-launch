package com.hul.proco.controller.createpromo;

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
	
}
