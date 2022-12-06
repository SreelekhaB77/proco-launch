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
	
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String moc, String userId,String roleId,String searchParameter);
	
	public int getDeletePromoListRowCount( String moc, String userId,String roleId);
	
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList, String moc, String userId,String roleId);
	
	public String promoDeleteDate(String Id);
	
	//Added by Kavitha D for promo listing download starts-SPRINT 9
	public ArrayList<String> getHeaderListForPromoDownloadListing();
	
	//Added by Kajal G for KAM Volume Error download starts-SPRINT 10
	public List<ArrayList<String>> getKAMErrorDetails(String userId);
	
	//Added by Kajal G for KAM Volume download starts-SPRINT 10
	public ArrayList<String> getHeaderForPromoDownloadListing(String primaryAccount);
	
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc,String roleId, String[] kamAccounts);
	//Added by Kavitha D for promo listing download ends-SPRINT 9

	//Added by Kajal G for KAM Volume download Start-SPRINT 10
	public List<ArrayList<String>> getPromotionListDownload(ArrayList<String> headerList, String moc, String primaryAccount);
	
	public int getPromoListRowCountGrid(String userId, String roleId,String moc,String[] kamAccountsArr);

	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String searchParameter, String[] kamAccounts);

	public List<String> getPromoMoc();
	
	//Added by Kajal G for KAM Volume Upload ends-SPRINT 10
	public String kamVolumeUpload(List<List<String>> excelData, String userId);
	
	public List<String> getPromoPrimaryChannels(); //Added By Sarin - Sprint10

	
}
