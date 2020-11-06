package com.hul.proco.controller.promostatustracker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;

public interface ProcoStatusTrackerDAO {
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int actives,String promoId,String searchParameter);
	
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1, String custChainL2, 
			String geography, String offerType, String modality, String year, String moc, String userId, int active,String promoId);
	
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList,String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType, String modality, 
			String year, String moc, String userId, int active,String promoId);
	
	public String getMocList();
	
	public int getUserRoleId(String userId);
	
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues();
	
	public List<Object[]> getMeasureReportByMoc(String MocYear, String Moc);
	
	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray)throws Exception;
	
	public String uploadPromoMeasureReport(PromoMeasureReportBean[] promoMeasureReportBeanArray)throws Exception;
	
	public List<PromoListingBean>readProcoStatusTracker(String excelFilePath)throws IOException, ParseException;
	
	public List<PromoMeasureReportBean>readPromoMeasureReport(String excelFilePath)throws IOException, ParseException;
	
	public List<ArrayList<String>> getProcoMeasureReportErrorDetails(ArrayList<String> headerList, String userId);
}
