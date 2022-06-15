package com.hul.proco.controller.listingPromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromotionBean;

public interface PromoListingService {
	
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active,String roleId , String searchParameter);
	
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1, String custChainL2, 
			String geography, String offerType, String modality, String year, String moc, String userId, int active,String roleId);

	public List<CreatePromotionBean> getPromotions(String promoId);
	
	public void deletePromotion(String promoId,String userId,String remark);
	
	public String updatePromotion(CreatePromotionBean[] bean,String userId,boolean isFromUi) throws Exception;

	public List<ArrayList<String>> getPromotionDump(ArrayList<String> headerList,String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active);
	
	public ArrayList<String> getHeaderListForPromoDumpDownload();
	
	public String promoEditUpload(CreatePromotionBean[] bean,String userId,boolean isFromUi) throws Exception;
	
	public List<ArrayList<String>> getPromoEditErrorDetails(ArrayList<String> headerList, String userId);
	
	public ArrayList<String> getHeaderListForPromoEditErrorFileDownload();
	
	public List<String> getReasonListForEdit();
	
	public List<String> getChangesMadeListForEdit();
	
	public Map<String,List<List<String>>> getMastersForTemplate();
	
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active,String roleId,String searchParameter);
	
	public int getDeletePromoListRowCount(String cagetory, String brand, String basepack, String custChainL1, String custChainL2, 
			String geography, String offerType, String modality, String year, String moc, String userId, int active,String roleId);
	
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList,String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active,String role);
	
	public String promoDeleteDate(String Id);
	
	//Added by Kavitha D for promo listing download starts-SPRINT 9
	public ArrayList<String> getHeaderListForPromoDownloadListing();
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc);
	//Added by Kavitha D for promo listing download ends-SPRINT 9

	public int getPromoListRowCountGrid(String userId, String roleId,String moc);

	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String searchParameter);

	public List<String> getPromoMoc();

	
}
