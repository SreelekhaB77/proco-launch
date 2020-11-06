package com.hul.proco.controller.volumeupload;

import java.util.ArrayList;
import java.util.List;

public interface VolumeUploadService {
	public List<ArrayList<String>> getPromoTableList(ArrayList<String> headerList,String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active);
	
	public ArrayList<String> getHeaderListForVolumeUploadTemplateDownload();
	
	public String volumeUploadForPromotion(VolumeUploadBean[] bean,String userId) throws Exception;
	
	public List<ArrayList<String>> getVolumeErrorDetails(ArrayList<String> headerList, String userId);
	
	public ArrayList<String> getHeaderListForVolumeErrorFileDownload();
}
