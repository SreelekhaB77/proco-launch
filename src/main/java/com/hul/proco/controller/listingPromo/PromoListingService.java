package com.hul.proco.controller.listingPromo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.controller.promocr.PromoCrBean;

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
	
	public ArrayList<String> getHeaderListForPromoDumpDownload(String roleId);
	
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
	
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String roleId, String[] kamAccounts,String fromDate,String toDate);
	//Added by Kavitha D for promo listing download ends-SPRINT 9

	//Added by Kajal G for KAM Volume download Start-SPRINT 10
	public List<ArrayList<String>> getPromotionListDownload(ArrayList<String> headerList, String moc, String primaryAccount);
	
	public int getPromoListRowCountGrid(String userId, String roleId,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String[] kamAccountsArr,String fromDate,String toDate);

	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String promobasepack,String ppmaccount,String procochannel,String prococluster,String searchParameter, String[] kamAccounts,String fromDate,String toDate);

	public List<String> getPromoMoc();
	
	//Added by Kajal G for KAM Volume Upload ends-SPRINT 10
	public String kamVolumeUpload(List<List<String>> excelData, String userId);
	
	public List<String> getPromoPrimaryChannels(String[] kamAccountsArr); //Added By Sarin - Sprint10

	public String uploadDroppedOfferApprovalData(PromoCrBean[] beanArray, String userId) throws Exception;
    
	//Added by kavitha D-SPRINT 11 changes-STARTS
	public List<String> getProcoBasepack();
	
	public List<String> getPpmAccount(String userId,String roleId);

	public List<String> getProcoChannel();

	public List<String> getProcoCluster();
	


	//Added by kavitha D-SPRINT 11 changes-ENDS


	
}
