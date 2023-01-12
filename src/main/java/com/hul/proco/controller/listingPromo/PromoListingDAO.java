package com.hul.proco.controller.listingPromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.controller.promocr.PromoCrBean;

public interface PromoListingDAO {

	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active,String roleId,String searchParameter);

	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active,String roleId);

	public List<CreatePromotionBean> getPromotions(String promoId);

	public void deletePromotion(String promoId,String userId,String remark);

	public String updatePromotion(CreatePromotionBean[] bean, String userId,boolean isFromUi) throws Exception;

	public List<ArrayList<String>> getPromotionDump(ArrayList<String> headerList, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active);

	public String promoEditUpload(CreatePromotionBean[] bean, String userId,boolean isFromUi) throws Exception;
	
	////Added by Kajal G for KAM Volumn Upload starts-SPRINT 10
	public String kamVolumeUpload(List<List<String>> excelData, String userId);

	//Added by Kajal G for KAM Volume Error download starts-SPRINT 10
	public List<ArrayList<String>> getKAMErrorDetails(String userId);
		
	public List<ArrayList<String>> getPromoEditErrorDetails(ArrayList<String> headerList, String userId);
	
	public List<String> getReasonListForEdit();
	
	public List<String> getChangesMadeListForEdit();
	
	public Map<String,List<List<String>>> getMastersForTemplate();
	
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String moc, String userId,String roleId,String searchParameter );

	public int getDeletePromoListRowCount(String moc,String userId,String roleId);
	
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList, String moc, String userId,String roleId);
	
	public String promoDeleteDate(String Id);
	
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String roleId, String[] kamAccounts); //Added by Kavitha D for promo listing download-SPRINT 9

	//Added by Kajal for KAM Volume download-SPRINT 10
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList,String moc, String primaryAcc); 
	
	//Added by Kavitha D for promo listing download-SPRINT 9

	public List<String> getPPMAccount(String primaryAccount);
	
	//Added by Kavitha D for promo listing Grid dispaly starts-SPRINT 9
	public int getPromoListRowCountGrid(String userId, String roleId,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String[] kamaccount);

	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId,String roleId,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String searchParameter, String[] kamAccounts);

	public List<String> getPromoMoc();
	//Added by Kavitha D for promo listing Grid dispaly ends-SPRINT 9

	public List<String> getPromoPrimaryChannels(String[] kamAccountsArr); //Added By Sarin - Sprint10
	public String uploadDroppedOfferApprovalData(PromoCrBean[] beanArray, String userId) throws Exception;

	//Added by kavitha D-SPRINT 11 changes starts
	public List<String> getProcoBasepack();

	public List<String> getPpmAccount();

	public List<String> getProcoChannel();

	public List<String> getProcoCluster();
	
	//Added by kavitha D-SPRINT 11 changes ends



	
}
