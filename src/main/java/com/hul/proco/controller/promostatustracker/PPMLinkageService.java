package com.hul.proco.controller.promostatustracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PPMLinkageService {

	@Autowired
	PPMLinkageDAO dao;

	@Transactional(rollbackFor = { Exception.class })
	public String addTotempTable(PPMLinkageBean[] beanArray, String userId) {
		// TODO Auto-generated method stub
		return dao.addTotempTable(beanArray, userId);
	}

}
