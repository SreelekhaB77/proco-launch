package com.hul.proco.controller.promostatustracker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;

@Service
@Transactional
public class ProcoStatusTrackerServiceImpl implements ProcoStatusTrackerService {

	@Autowired
	private ProcoStatusTrackerDAO procoStatusTrackerDao;
	
	@Override
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster, String searchParameter,String fromDate,String toDate) {
		return procoStatusTrackerDao.getPromoTableList(pageDisplayStart, pageDisplayLength, moc,promobasepack,ppmaccount,procochannel,prococluster,searchParameter,fromDate,toDate);
	}

	@Override
	public int getPromoListRowCount(String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String fromDate,String toDate) {
		return procoStatusTrackerDao.getPromoListRowCount(moc,promobasepack,ppmaccount,procochannel,prococluster,fromDate,toDate);
	}

	@Override
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList, String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster, String userId,String fromDate,String toDate) {
		return procoStatusTrackerDao.getPromotionStatusTracker(headerList,moc,promobasepack,ppmaccount,procochannel,prococluster,userId,fromDate,toDate);
	}
	
	//Added by Kajal G in SPRINT-12
	@Override
	public List<ArrayList<String>> getVisiDownloadedData(ArrayList<String> headerList, String moc) {
		return procoStatusTrackerDao.getVisiDownloadedData(headerList,moc);
	}
	
	@Override
	public List<ArrayList<String>> getPromotionStatusTrackerCustomerPortal(ArrayList<String> headerList, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String promoId) {
		return procoStatusTrackerDao.getPromotionStatusTrackerCustomerPortal(headerList, cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active, promoId);
	}
	
	
	@Override
	public String disaggrigateQty(String promoId, String cluster, String baseback) {
		return procoStatusTrackerDao.disaggrigateQty(promoId, cluster, baseback);
	}
	
	public boolean batchCustomerStatusTrackerReport(List<ArrayList<String>> dataObj) {
		return procoStatusTrackerDao.batchCustomerStatusTrackerReport(dataObj);
	}
	
	/**
	 * @return
	 */
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues(){
		return procoStatusTrackerDao.getProcoStatusMasterValues();
	}
	
	/***
	 * @param Moc Year
	 * @param Moc Month
	 * @return list of measure report
	 */
	public List<Object[]> procoExportMeasureReport(String MocYear, String Moc) {
		String yearQry = "";
		String mocQry = "";
		String[] yearSpt = MocYear.split(",");
		String[] MocSpt = Moc.split(",");
		for( int i = 0; i < yearSpt.length; i++ ) {
			if(!yearQry.equals("")) {
				yearQry += " OR ";
			}
			yearQry += "MOC_YEAR like '"+yearSpt[i]+"%'";
		}
		
		for( int i = 0; i < MocSpt.length; i++ ) {
			if(!mocQry.equals("")) {
				mocQry += " OR ";
			}
			mocQry += "MOC like '"+MocSpt[i]+"%'";
		}
		
		List<Object[]> Measurelist = procoStatusTrackerDao.getMeasureReportByMoc(yearQry, mocQry);
		return Measurelist;
	}
	
	@Override 
	public ArrayList<String> getHeaderListForPromoStatusTracker(String UserId, boolean isCron){
		ArrayList<String> headerList = new ArrayList<String>();
		if(procoStatusTrackerDao.getUserRoleId(UserId) == 2 || isCron) {
			/*headerList.add("PROMO ID");
			headerList.add("START DATE");
			headerList.add("END DATE");
			headerList.add("MOC");
			headerList.add("PPM ACCOUNT");
			headerList.add("BASEPACK");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("GEOGRAPHY");
			headerList.add("PROMO TEMPLATE");
			headerList.add("CREATED DATE");	
			headerList.add("QUANTITY");
			headerList.add("OFFER VALUE");
			headerList.add("STATUS");
			headerList.add("REMARK");
			headerList.add("USER ID");
			headerList.add("INVESTMENT TYPES");
			headerList.add("SOL CODE ");
			headerList.add("SOL CODE DESCRIPTION");
			headerList.add("PROMOTION MECHANICS");
			headerList.add("SOL CODE STATUS");*/
			//Added by Kavitha D-SPRINT 9
			headerList.add("CHANNEL");
			headerList.add("YEAR");
			headerList.add("MOC");
			headerList.add("ACCOUNT TYPE");
			headerList.add("CLAIM TYPE");
			//headerList.add("SECONDARY_CHANNEL"); //commented headers secondary channel,moc cycle and childpack-SPRINT 14 by kavitha D
			headerList.add("PPM ACCOUNT");
			headerList.add("PROMO ID");
			headerList.add("SOL CODE");
			//headerList.add("BM/MOC CYLCLE");
			headerList.add("PARENT SOL CODE");//Added by Kavitha D-SPRINT 15 
			headerList.add("CUST PROMO CYCLE");//Renaming header names by Kavitha D-SPRINT 18
			headerList.add("SOL RELEASE TYPE");
			headerList.add("START DATE");
			headerList.add("END DATE");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("PPM DESCRIPTION");
			headerList.add("BP CODE");
			headerList.add("BP DESC");
			//headerList.add("CHILDPACK");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("PRICE OFF");
			headerList.add("PARENT SOL QTY");
			headerList.add("TME CR QTY");
			headerList.add("SC APPROVED QTY");
			headerList.add("QUANTITY");
			headerList.add("PARENT SOL BUDGET");
			headerList.add("TME CR BUDGET");	
			headerList.add("SC APPROVED BUDGET");
			headerList.add("FIXED BUDGET");
			headerList.add("BRANCH");
			headerList.add("SALES CLUSTER");
			headerList.add("PPM CUSTOMER");
			headerList.add("SALES CATEGORY");
			//headerList.add("PSA CATEGORY");
			headerList.add("PROMOTION STATUS");
			headerList.add("PPM PROMOTION CREATOR");
			headerList.add("PROMOTION MECHANICS");
			headerList.add("INVESTMENT TYPE");
			//headerList.add("SALES CLUSTER CODE");
			headerList.add("BRAND");
			headerList.add("SUB BRAND");
			headerList.add("PPM BUDGET HOLDER");
			headerList.add("FUND TYPE");
			headerList.add("INVESTMENT AMOUNT");
			headerList.add("PROMO ENTRY TYPE");
			//headerList.add("PROMO USER NAME");
			headerList.add("CMM NAME");
			headerList.add("TME USER NAME");
			headerList.add("PROCO CREATION TIME");
			headerList.add("PPM APPROVED DATE");
			headerList.add("PPM CREATION DATE");
			//headerList.add("NON UNIFY");
			headerList.add("PPM SUBMISSION DATE");
			headerList.add("PPM MODIFIED DATE");
			headerList.add("COE REMARKS");
			headerList.add("CU REMARKS");
			headerList.add("TME SUBMIT DATE");
			headerList.add("AUDITOR SUBMIT DATE");
			headerList.add("MRP");
			headerList.add("AB STATUS");
			//headerList.add("BUDGET");
			headerList.add("CURRENT STATUS"); //Added by Kavitha D-SPRINT 10 Changes
			headerList.add("SOL TYPE");
			headerList.add("SOL TYPE SHORTKEY");
			headerList.add("CSP APPROVAL (Y/N)"); //Added by Kavitha D-SPRINT 17 changes
			headerList.add("SC APPROVAL(Y/N)");
			headerList.add("NCMM REMARKS");
			headerList.add("SC REMARKS");
			headerList.add("NCMM DOA");
			headerList.add("SC DOA");
		
		} else {
			headerList = getHeaderListForPromoStatusTracker();
		}
		return headerList;
	}
	
	//Added by Kajal G-SPRINT 12
	@Override 
	public ArrayList<String> getVisiHeaderListForPromoStatusTracker(){
		ArrayList<String> headerList = new ArrayList<String>();
			headerList.add("VISI REF NO.");
			headerList.add("SOL CODE");
			headerList.add("START DATE");
			headerList.add("END DATE");
			headerList.add("MOC");
			headerList.add("HFS CONNECTIVITY");
			headerList.add("NEW/CONTINUED");
			headerList.add("MADE BY");
			headerList.add("ACCOUNT NAME");
			headerList.add("SPLIT REQUIRE");
			headerList.add("PPM ACCOUNT NAME - EXCEPT NMT/RC");
			headerList.add("DESCRIPTION 1");
			headerList.add("PPM DESC");
			headerList.add("REGION");
			headerList.add("STATE");
			headerList.add("CITY");
			headerList.add("BASEPACK");
			headerList.add("BASEPACK DESC");
			headerList.add("VISIBILITY DESC");
			headerList.add("ASSET DESCRIPTION");
			headerList.add("ASSET TYPE");
			headerList.add("ASSET REMARK");
			headerList.add("POP-CLASS");
			headerList.add("UNIT PER STORE");
			headerList.add("NO. OF STORES");
			headerList.add("AMOUNT PER STORE PER MOC");
			headerList.add("AMOUNT PER BASEPACK PER MOC");
			headerList.add("COMMENTS");
			headerList.add("HHT TRACKING");
			headerList.add("CATEGORY");
			headerList.add("MIGRATED CATEGORY");
			headerList.add("SUB ELEMENTS");
			headerList.add("MBQ");
			headerList.add("BRAND");
			headerList.add("TOTAL NO. OF ASSET");
			headerList.add("VISIBILITY AMOUNT");
			headerList.add("OUTLET CODE");
			headerList.add("OUTLET NAME");
			headerList.add("MAPPED POP-CLASS");
			headerList.add("STATUS");
			headerList.add("DATE OF CREATION");
			headerList.add("LAST EDITED");
			headerList.add("CLASSIFICATION");
			headerList.add("EDIT/DELETE REASON");
			headerList.add("VISIBILITY_PAYOUT_CODE");
		return headerList;
	}

	@Override
	public ArrayList<String> getHeaderListForPromoStatusTracker() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Original Id");
		headerList.add("Start Date");
		headerList.add("End Date");
		headerList.add("MOC");
		headerList.add("Customer Chain L1");
		headerList.add("Customer Chain L2");
		headerList.add("Basepack");
		headerList.add("Offer Description");
		headerList.add("Offer Type");
		headerList.add("Offer Modality");
		headerList.add("Geography");
		headerList.add("Quantity");
		headerList.add("UOM");
		headerList.add("Offer Value");
		headerList.add("Kitting Value");
		headerList.add("Status");
		headerList.add("Reason For Edit");
		headerList.add("Remark");
		headerList.add("Change Date");
		headerList.add("Investment Type");
		headerList.add("SolCode");
		headerList.add("Promotion Mechanics");
		headerList.add("SolCode Status");
		return headerList;
	}
	
	@Override
	public String getMocList() {
		return procoStatusTrackerDao.getMocList();
	}
	
	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray) throws Exception {
		return procoStatusTrackerDao.uploadPromoStatusTracker(promoListingBeanArray);
	}

	@Override
	public String uploadPromoMeasureReport(PromoMeasureReportBean[] promoMeasureReportBeanArray) throws Exception{
			return procoStatusTrackerDao.uploadPromoMeasureReport(promoMeasureReportBeanArray);
	}

	@Override
	public List<PromoListingBean> readProcoStatusTracker(String excelFilePath) throws IOException, ParseException {
		return procoStatusTrackerDao.readProcoStatusTracker(excelFilePath);
	}
	
	@Override
	public List<PromoMeasureReportBean> readPromoMeasureReport(String excelFilePath) throws IOException, ParseException {
		return procoStatusTrackerDao.readPromoMeasureReport(excelFilePath);
	}

	@Override
	public ArrayList<String> getHeaderListForProcoMeasureReport() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promotion ID");	
		headerList.add("Promotion Name");		
		headerList.add("Created By");		
		headerList.add("Promotion Mechanics");		
		headerList.add("Promotion Start Date");		
		headerList.add("Promotion End Date");		
		headerList.add("Customer");		
		headerList.add("Product");	   
		headerList.add("Promotion Status");		
		headerList.add("Category");		
		headerList.add("Investment Type");		
		headerList.add("MOC");		
		headerList.add("Submission Date");		
		headerList.add("Promotion Type");		
		headerList.add("Promotion Volume During");		
		headerList.add("Planned Investment Amount");	
		headerList.add("Error Msg");
		return headerList;
	}

	@Override
	public List<ArrayList<String>> getProcoMeasureReportErrorDetails(ArrayList<String> headerList, String userId) {
		return procoStatusTrackerDao.getProcoMeasureReportErrorDetails(headerList, userId);
	}
	
	//Added by Kavitha D for promo measure template-SPRINT 9
	public ArrayList<String> getPromoMeasureDownload(){
		return procoStatusTrackerDao.getPromoMeasureDownload();	
	}
	public List<ArrayList<String>> ppmCoeRemarksDownload(ArrayList<String> headerList){
		return procoStatusTrackerDao.ppmCoeRemarksDownload(headerList);
	}
	
	@Override
	public ArrayList<String> ppmCoeRemarksDownloadHeaderList(){
		return procoStatusTrackerDao.ppmCoeRemarksDownloadHeaderList();
		
	}
	
	public ArrayList<String> getPpmDownloadHeaders(){
		return procoStatusTrackerDao.getPpmDownloadHeaders();

	}
	//Added by kavitha D for downloading ppm upload file starts-SPRINT 9

	public List<ArrayList<String>> getPpmDownloadData(ArrayList<String> headers, String selMOC){
		return procoStatusTrackerDao.getPpmDownloadData(headers,selMOC);
	}

	public List<String> getMOCforCoedownload(){
		return procoStatusTrackerDao.getMOCforCoedownload();
	}
	//Added by kavitha D for downloading ppm upload file ends-SPRINT 9



}
