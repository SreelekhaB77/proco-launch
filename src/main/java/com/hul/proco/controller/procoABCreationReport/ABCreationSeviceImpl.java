package com.hul.proco.controller.procoABCreationReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ABCreationSeviceImpl implements ABCreationService{

	@Autowired
	ABCreationDAO abCreation;
	
	//Kajal G Added for SPRINT 12
	@Transactional(rollbackFor = { Exception.class })
	public String uploadABCreationReport(ABCreationBean[] beanArray, String userId) throws Exception {
		return abCreation.uploadABCreationReport(beanArray, userId);
	}

}
