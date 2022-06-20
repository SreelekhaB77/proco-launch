package com.hul.proco.controller.promostatustracker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String moc, String searchParameter) {
		return procoStatusTrackerDao.getPromoTableList(pageDisplayStart, pageDisplayLength, moc,searchParameter);
	}

	@Override
	public int getPromoListRowCount(String moc) {
		return procoStatusTrackerDao.getPromoListRowCount(moc);
	}

	@Override
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList, String moc, String userId) {
		return procoStatusTrackerDao.getPromotionStatusTracker(headerList,moc,userId);
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
			headerList.add("PROMO ID");
			headerList.add("START DATE");
			headerList.add("END DATE");
			headerList.add("MOC");
			headerList.add("PPM ACCOUNT");
			headerList.add("BASEPACK");
			headerList.add("OFFER DESCRIPTION");
			headerList.add("OFFER TYPE");
			headerList.add("OFFER MODALITY");
			headerList.add("GEOGRAPHY");
			headerList.add("QUANTITY");
			headerList.add("OFFER VALUE");
			headerList.add("STATUS");
			headerList.add("REMARK");
			headerList.add("USER ID");
			headerList.add("INVESTMENT TYPES");
			headerList.add("SOL CODE ");
			headerList.add("SOL CODE DESCRIPTION");
			headerList.add("PROMOTION MECHANICS");
			headerList.add("SOL CODE STATUS");
		} else {
			headerList = getHeaderListForPromoStatusTracker();
		}
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

	

}
