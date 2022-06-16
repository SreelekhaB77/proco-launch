package com.hul.proco.controller.listingPromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromotionBean;

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

	public List<ArrayList<String>> getPromoEditErrorDetails(ArrayList<String> headerList, String userId);
	
	public List<String> getReasonListForEdit();
	
	public List<String> getChangesMadeListForEdit();
	
	public Map<String,List<List<String>>> getMastersForTemplate();
	
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String moc, String userId,String roleId,String searchParameter );

	public int getDeletePromoListRowCount(String moc,String userId,String roleId);
	
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList, String moc, String userId,String roleId);
	
	public String promoDeleteDate(String Id);
	
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc,String roleid); //Added by Kavitha D for promo listing download-SPRINT 9

	//Added by Kavitha D for promo listing Grid dispaly starts-SPRINT 9
	public int getPromoListRowCountGrid(String userId, String roleId,String moc);

	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId,String roleId,String moc,String searchParameter);

	public List<String> getPromoMoc();
	//Added by Kavitha D for promo listing Grid dispaly ends-SPRINT 9



	
}
