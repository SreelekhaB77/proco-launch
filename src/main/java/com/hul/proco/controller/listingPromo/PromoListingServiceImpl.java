package com.hul.proco.controller.listingPromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromotionBean;

@Service
@Transactional
public class PromoListingServiceImpl implements PromoListingService {

	@Autowired
	private PromoListingDAO promoListingDAO;

	@Override
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory, 
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active,String roleId,String searchParameter) {

		return promoListingDAO.getPromoTableList(pageDisplayStart, pageDisplayLength, cagetory, brand, 
				basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active,roleId,searchParameter);
	}

	@Override
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1, String custChainL2, 
			String geography, String offerType, String modality, String year, String moc, String userId, int active,String roleId) {
		return promoListingDAO.getPromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active,roleId);
	}

	@Override
	public List<CreatePromotionBean> getPromotions(String promoId) {
		return promoListingDAO.getPromotions(promoId) ;
	}

	@Override
	public void deletePromotion(String promoId,String userId,String remark) {
		promoListingDAO.deletePromotion(promoId,userId,remark);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String updatePromotion(CreatePromotionBean[] bean, String userId,boolean isFromUi) throws Exception {
		return promoListingDAO.updatePromotion(bean, userId,isFromUi);
	}

	@Override
	public List<ArrayList<String>> getPromotionDump(ArrayList<String> headerList, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active) {
		return promoListingDAO.getPromotionDump(headerList, cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active);
	}

	@Override
	public ArrayList<String> getHeaderListForPromoDumpDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Start Date(DD/MM/YYYY)");
		headerList.add("End Date(DD/MM/YYYY)");
		headerList.add("MOC");
		headerList.add("Customer Chain L1");
		headerList.add("Customer Chain L2");
		headerList.add("Offer Description");
		headerList.add("P1 Basepack");
		headerList.add("P1 Pack Ratio");
		headerList.add("P2 Basepack");
		headerList.add("P2 Pack Ratio");
		headerList.add("P3 Basepack");
		headerList.add("P3 Pack Ratio");
		headerList.add("P4 Basepack");
		headerList.add("P4 Pack Ratio");
		headerList.add("P5 Basepack");
		headerList.add("P5 Pack Ratio");
		headerList.add("P6 Basepack");
		headerList.add("P6 Pack Ratio");
		headerList.add("C1 Child Pack");
		headerList.add("C1 Child Pack Ratio");
		headerList.add("C2 Child Pack");
		headerList.add("C2 Child Pack Ratio");
		headerList.add("C3 Child Pack");
		headerList.add("C3 Child Pack Ratio");
		headerList.add("C4 Child Pack");
		headerList.add("C4 Child Pack Ratio");
		headerList.add("C5 Child Pack");
		headerList.add("C5 Child Pack Ratio");
		headerList.add("C6 Child Pack");
		headerList.add("C6 Child Pack Ratio");
		headerList.add("3rd Party Description");
		headerList.add("3rd Party Pack Ratio");
		headerList.add("Offer Type");
		headerList.add("Offer Modality");
		headerList.add("Offer Value");
		headerList.add("Kitting Value");
		headerList.add("Geography");
		headerList.add("UOM");
		headerList.add("Reason");
		headerList.add("Remark");
		headerList.add("Changes Made");
		return headerList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String promoEditUpload(CreatePromotionBean[] bean, String userId,boolean isFromUi) throws Exception {
		return promoListingDAO.promoEditUpload(bean, userId,isFromUi);
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getPromoEditErrorDetails(ArrayList<String> headerList, String userId) {
		return promoListingDAO.getPromoEditErrorDetails(headerList, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForPromoEditErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Start Date(DD/MM/YYYY)");
		headerList.add("End Date(DD/MM/YYYY)");
		headerList.add("MOC");
		headerList.add("Customer Chain L1");
		headerList.add("Customer Chain L2");
		headerList.add("Offer Description");
		headerList.add("P1 Basepack");
		headerList.add("P1 Pack Ratio");
		headerList.add("P2 Basepack");
		headerList.add("P2 Pack Ratio");
		headerList.add("P3 Basepack");
		headerList.add("P3 Pack Ratio");
		headerList.add("P4 Basepack");
		headerList.add("P4 Pack Ratio");
		headerList.add("P5 Basepack");
		headerList.add("P5 Pack Ratio");
		headerList.add("P6 Basepack");
		headerList.add("P6 Pack Ratio");
		headerList.add("C1 Child Pack");
		headerList.add("C1 Child Pack Ratio");
		headerList.add("C2 Child Pack");
		headerList.add("C2 Child Pack Ratio");
		headerList.add("C3 Child Pack");
		headerList.add("C3 Child Pack Ratio");
		headerList.add("C4 Child Pack");
		headerList.add("C4 Child Pack Ratio");
		headerList.add("C5 Child Pack");
		headerList.add("C5 Child Pack Ratio");
		headerList.add("C6 Child Pack");
		headerList.add("C6 Child Pack Ratio");
		headerList.add("3rd Party Description");
		headerList.add("3rd Party Pack Ratio");
		headerList.add("Offer Type");
		headerList.add("Offer Modality");
		headerList.add("Offer Value");
		headerList.add("Kitting Value");
		headerList.add("Geography");
		headerList.add("UOM");
		headerList.add("Reason");
		headerList.add("Remark");
		headerList.add("Changes Made");
		headerList.add("ERROR MSG");
		return headerList;
	}

	@Override
	public List<String> getReasonListForEdit() {
		return promoListingDAO.getReasonListForEdit();
	}

	@Override
	public List<String> getChangesMadeListForEdit() {
		return promoListingDAO.getChangesMadeListForEdit();
	}

	@Override
	public Map<String,List<List<String>>> getMastersForTemplate() {
		return promoListingDAO.getMastersForTemplate();
	}

	@Override
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String roleId,String searchParameter) {
		return promoListingDAO.getDeletePromoTableList(pageDisplayStart, pageDisplayLength, cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active, roleId, searchParameter);
	}

	@Override
	public int getDeletePromoListRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active, String roleId) {
		return promoListingDAO.getDeletePromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active, roleId);
	}

	

	@Override
	public String promoDeleteDate(String Id) {
		return promoListingDAO.promoDeleteDate(Id);
	}

	@Override
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String role) {
		return promoListingDAO.getDeletePromotionDump(headerList, cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active, role);
	}

	//Added by Kavitha D for promo listing download starts-SPRINT 9
	@Override
	public ArrayList<String> getHeaderListForPromoDownloadListing() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("CHANNEL");
		headerList.add("MOC");
		headerList.add("ACCOUNT_TYPE");
		headerList.add("POS_ONINVOICE");
		headerList.add("SECONDARY_CHANNEL");
		headerList.add("PPM_ACCOUNT");
		headerList.add("PROMO_ID");
		headerList.add("EXISTING SOL CODE");
		headerList.add("BM/MOC CYLCLE");
		headerList.add("PROMO TIMEPERIOD");
		headerList.add("AB CREATION");
		headerList.add("SOL WILL RELEASE ON");
		headerList.add("START DATE");
		headerList.add("START END");
		headerList.add("OFFER DESCRIPTION");
		headerList.add("PPM DESCRIPTION");
		headerList.add("BASEPACK");
		headerList.add("CHILDPACK");
		headerList.add("OFFER TYPE");
		headerList.add("OFFER MODALITY");
		headerList.add("PRICE OFF");
		headerList.add("QUANTITY");
		headerList.add("BUDGET");
		headerList.add("BRANCH");
		headerList.add("CLUSTER");
		headerList.add("SOL TYPE");
		headerList.add("REMARK");
		headerList.add("CMM NAME");
		headerList.add("TME NAME");
		headerList.add("SALES CATEGORY");
		headerList.add("PSA CATEGORY");
		
		return headerList;
	}
	
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId){
		return promoListingDAO.getPromotionListingDownload(headerList,userId );
	}
	//Added by Kavitha D for promo listing download ends-SPRINT 9

	//Added by Kavitha D for promo listing Grid dispaly starts-SPRINT 9

	@Override
	public int getPromoListRowCountGrid(String userId,String roleId,String moc) {
		return promoListingDAO.getPromoListRowCountGrid(userId,roleId,moc);
	}

	@Override
	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId,String roleId,
			String moc,String searchParameter) {
		return promoListingDAO.getPromoTableListGrid(pageDisplayStart,pageDisplayLength,userId,roleId,moc,searchParameter);
	}


	@Override
	public List<String> getPromoMoc() {
		return promoListingDAO.getPromoMoc();
	}
	//Added by Kavitha D for promo listing grid display ends-SPRINT 9


}
