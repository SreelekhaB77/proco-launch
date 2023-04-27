package com.hul.proco.controller.visibilityupload;

import java.io.IOException;
import java.util.List;


public interface VisibilityUploadService {
	public String uploadVisibilityData(VisibilityBean[] beanArray, String userId) throws Exception;
	
	public List<VisibilityBean> readVisibilityBean(String excelFilePath) throws IOException;
}
