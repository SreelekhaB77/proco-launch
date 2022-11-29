package com.hul.proco.controller.promocr;

import java.util.ArrayList;
import java.util.List;

public interface PromoCrService {
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String searchParameter);

	public int getPromoListRowCount(String userId, String roleId,String moc);


	public String approveCr(String promoId, String userId, String roleId);

	public String rejectCr(String promoId, String userId, String roleId, String reason);

	public List<String> getAllCategories();

	public List<String> getAllBrands();

	public List<String> getAllBasepacks();
	
	public List<List<String>> getAllProductMaster();  //Sarin Changes Performance
	// Harsha's Changes
	public String insertToportalUsage(String userId, String roleID, String module);

	public ArrayList<String> getHeaderListForPromoDownloadCrListing();

	public List<ArrayList<String>> getPromotionListingCrDownload(ArrayList<String> headerList, String userId,String moc, String roleId);

	public String uploadApprovalData(PromoCrBean[] beanArray, String userId) throws Exception;
}
