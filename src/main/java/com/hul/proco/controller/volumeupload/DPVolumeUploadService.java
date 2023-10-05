package com.hul.proco.controller.volumeupload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.hul.proco.controller.createpromo.CreateBeanRegular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DPVolumeUploadService {
	
	@Autowired
	DPVolumeUpload dpVolumeUpload;

	public ArrayList<String> getHeaderForDPVolumeUpload() {
		 ArrayList<String> headerList=new ArrayList<String>();
		 
		 headerList.add("CHANNEL");
		 headerList.add("MOC");
		 headerList.add("YEAR");
		 headerList.add("MOC NAME");
		 headerList.add("PROMO ID");
		 headerList.add("PPM Account");
		 headerList.add("CUST PROMO CYCLE");
		 headerList.add("BP CODE");
		 headerList.add("BP DESC");
		 headerList.add("Child Pack Code");
		 headerList.add("Offer description");
		 headerList.add("Offer Type");
		 headerList.add("Offer Modality");
		 headerList.add("Price off");
		 headerList.add("BUDGET");
		 headerList.add("QUANTITY");
		 headerList.add("Branch");
		 headerList.add("CLUSTER");
		 headerList.add("Remark");
		return headerList;
	}

	@Transactional(rollbackFor = { Exception.class })
	public List<ArrayList<String>> getDetailsofDP(ArrayList<String> headerDetail) {
		// TODO Auto-generated method stub
		return dpVolumeUpload.getDetailsofDP(headerDetail);
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public String uploadVolumeData(CreateBeanRegular[] beanArray, String userId) {
		
		return dpVolumeUpload.uploadVolumeData(beanArray, userId);
	}
	


	
	
}
