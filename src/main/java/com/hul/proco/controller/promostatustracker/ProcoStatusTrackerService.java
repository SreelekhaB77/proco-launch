package com.hul.proco.controller.promostatustracker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;

public interface ProcoStatusTrackerService {
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String searchParameter,String fromDate,String toDate);
	
	public int getPromoListRowCount(String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String fromDate,String toDate);
	
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList, String moc, String promobasepack,String ppmaccount,String procochannel,String prococluster,String userId,String fromDate,String toDate);
	public List<ArrayList<String>> getPromotionStatusTrackerCustomerPortal(ArrayList<String> headerList, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String promoId);
	
	public ArrayList<String> getHeaderListForPromoStatusTracker();
	public ArrayList<String> getHeaderListForPromoStatusTracker(String userId, boolean isCron);
	
	public String disaggrigateQty(String promoId, String cluster, String baseback);
	
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues();
	
	public String getMocList();
	
	public List<Object[]> procoExportMeasureReport(String MocYear, String Moc);
	
	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray)throws Exception;
	
	public String uploadPromoMeasureReport(PromoMeasureReportBean[] promoMeasureReportBeanArray)throws Exception;
	
	public List<PromoListingBean>readProcoStatusTracker(String excelFilePath)throws IOException, ParseException;
	
	public List<PromoMeasureReportBean>readPromoMeasureReport(String excelFilePath)throws IOException, ParseException;
	
	public ArrayList<String> getHeaderListForProcoMeasureReport();
	
	public List<ArrayList<String>> getProcoMeasureReportErrorDetails(ArrayList<String> headerList, String userId);

	public boolean batchCustomerStatusTrackerReport(List<ArrayList<String>> downloadedData);
	
	public ArrayList<String> getPromoMeasureDownload(); //Added by Kavitha D for promo measure template-SPRINT 9

	public List<ArrayList<String>> ppmCoeRemarksDownload(ArrayList<String> headerList); //Added by Kavitha D for PPM coe remarks download-SPRINT 9

	public ArrayList<String> ppmCoeRemarksDownloadHeaderList();

	public ArrayList<String> getPpmDownloadHeaders();

	public List<ArrayList<String>> getPpmDownloadData(ArrayList<String> headers, String selMOC);

	public List<String> getMOCforCoedownload();

	

}
