package com.hul.proco.controller.promoScApproval;

import java.util.ArrayList;
import java.util.List;

import com.hul.proco.controller.promocr.PromoCrBean;

public interface PromoApproval {
	
	public String insertToportalUsage(String id, String roleId, String string);

	public int getPromoListScRowCount(String userId, String roleId, String moc);

	
	public List<PromoCrBean> getPromoScTableList(int pageDisplayStart, int pageDisplayLength, String userId,String roleId, String moc, String searchParameter);

	public String approvePromoSc(String promoId, String userId, String roleId);

	public List<ArrayList<String>> getPromotionListingScDownload(ArrayList<String> headerList, String userId,
			String moc, String roleId);

	public String uploadScApprovalData(PromoCrBean[] beanArray, String userId) throws Exception;

	public List<ArrayList<String>> getPromotionApprovalScErrorDetails(ArrayList<String> headerList, String userId,
			String roleId);

}
