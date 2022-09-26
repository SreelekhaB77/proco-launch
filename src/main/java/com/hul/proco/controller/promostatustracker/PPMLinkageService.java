package com.hul.proco.controller.promostatustracker;

import java.util.ArrayList;
import java.util.List;

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
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getBasedonMOC() {
		// TODO Auto-generated method stub
		
		return dao.getBasedonMOC();
	}
	@Transactional(rollbackFor = { Exception.class })
	public List<ArrayList<String>> getDownloadData(List<String> headers,String moc) {
		// TODO Auto-generated method stub
		return dao.getDownloadData(headers,moc);
	}
	
	//Added by KAvitha D-SPRINT 9
	@Transactional(rollbackFor = { Exception.class })
	public String ppmCoeRemarks(PPMLinkageBean[] beanArray) {
		// TODO Auto-generated method stub
		return dao.ppmCoeRemarks(beanArray);
	}

	
}
