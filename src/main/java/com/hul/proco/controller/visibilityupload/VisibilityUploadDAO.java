package com.hul.proco.controller.visibilityupload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface VisibilityUploadDAO {

	public String uploadVisibilityData(VisibilityBean[] beanArray, String userId) throws Exception;
	
	public List<VisibilityBean> readVisibilityBean(String excelFilePath) throws FileNotFoundException, IOException;
}
