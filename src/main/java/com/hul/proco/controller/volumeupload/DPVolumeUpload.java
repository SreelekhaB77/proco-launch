package com.hul.proco.controller.volumeupload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.proco.controller.createpromo.CreateBeanRegular;

public interface DPVolumeUpload {


	

	List<ArrayList<String>> getDetailsofDP(ArrayList<String> header);

	String uploadVolumeData(CreateBeanRegular[] beanArray, String userId);

}
