package com.hul.proco.controller.visibilityupload;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisibilityUploadServiceImpl implements VisibilityUploadService{

	@Autowired
	VisibilityUploadDAO visiUpload;
	
	//Kajal G Added for SPRINT 12
	@Transactional(rollbackFor = { Exception.class })
	public String uploadVisibilityData(VisibilityBean[] beanArray, String userId) throws Exception {
		return visiUpload.uploadVisibilityData(beanArray, userId);
	}
	
	//Kajal G Added for SPRINT 12
	@Override
	public List<VisibilityBean> readVisibilityBean(String excelFilePath) throws IOException {
		return visiUpload.readVisibilityBean(excelFilePath);
	}
	
}
