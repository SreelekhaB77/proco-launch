package com.hul.proco.controller.promostatustracker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;

public interface ProcoStatusTrackerDAO {
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String moc,String searchParameter);
	
	public int getPromoListRowCount(String moc);
	
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList, String moc, String userId);
	
	public String getMocList();
	
	public List<ArrayList<String>> getPromotionStatusTrackerCustomerPortal(ArrayList<String> headerList, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String promoId);
	
	public String disaggrigateQty(String promoId, String cluster, String baseback);
	
	public int getUserRoleId(String userId);
	
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues();
	
	public List<Object[]> getMeasureReportByMoc(String MocYear, String Moc);
	
	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray)throws Exception;
	
	public String uploadPromoMeasureReport(PromoMeasureReportBean[] promoMeasureReportBeanArray)throws Exception;
	
	public List<PromoListingBean>readProcoStatusTracker(String excelFilePath)throws IOException, ParseException;
	
	public List<PromoMeasureReportBean>readPromoMeasureReport(String excelFilePath)throws IOException, ParseException;
	
	public List<ArrayList<String>> getProcoMeasureReportErrorDetails(ArrayList<String> headerList, String userId);

	public boolean batchCustomerStatusTrackerReport(List<ArrayList<String>> dataObj);

	public ArrayList<String> getPromoMeasureDownload(); //Added by Kavitha D for promo measure template-SPRINT 9

	public List<ArrayList<String>> ppmCoeRemarksDownload(ArrayList<String> headerList);//Added by kavitha D for PPM coe remarks download-SPRINT 9

	public ArrayList<String> ppmCoeRemarksDownloadHeaderList();

}
