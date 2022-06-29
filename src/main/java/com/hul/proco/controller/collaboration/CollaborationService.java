package com.hul.proco.controller.collaboration;

import java.util.ArrayList;
import java.util.List;

public interface CollaborationService {
	public int getCollaborationRowCount(/*String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String offerType, String modality, String year, */ String moc, String userId,String[] kamac);
	
	public List<DisplayCollaborationBean> getCollaborationTableList(int pageDisplayStart, int pageDisplayLength,/* String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String offerType, String modality, 
			String year,*/ String moc, String userId, String[] kamAccounts);
	
	//public DisplayCollaborationBean getDisplayCollaboration(String promoId);
	
	public ArrayList<String> getHeaderListForKamTemplateDownload();
	
	public ArrayList<String> getHeaderListForKamL1ErrorFileDownload();
	
	public ArrayList<String> getHeaderListForKamL2ErrorFileDownload();
	
	public List<ArrayList<String>> getL1DepotDisaggregation(ArrayList<String> headerList, String userId,String[] promoId);
	
	public String uploadKamL1(L1CollaborationBean[] bean,String userId) throws Exception;
	
	public String uploadKamL2(L2CollaborationBean[] bean,String userId) throws Exception;
	
	public List<ArrayList<String>> getKamL1ErrorDetails(ArrayList<String> headerList, String userId);
	
	public List<ArrayList<String>> getKamL2ErrorDetails(ArrayList<String> headerList, String userId);
	
	public ArrayList<String> getHeaderListForKamL2TemplateDownload();
	
	public List<ArrayList<String>> getL2DepotDisaggregation(ArrayList<String> headerList, String userId,String[] promoId);

	
}
