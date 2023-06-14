package com.hul.proco.controller.promocr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PromoCrServiceImpl implements PromoCrService {
	
	@Autowired
	private PromoCrDAO promoCrDAO;
	
	@Override
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String searchParameter) {

		return promoCrDAO.getPromoTableList(pageDisplayStart, pageDisplayLength, userId,roleId,moc,searchParameter);
	}

	@Override
	public int getPromoListRowCount(String userId, String roleId,String moc) {
		return promoCrDAO.getPromoListRowCount(userId,roleId,moc);
	}

	@Override
	public String approveCr(String promoId, String userId,String roleId) {
		return promoCrDAO.approveCr(promoId, userId, roleId);
	}

	@Override
	public String rejectCr(String promoId, String userId, String roleId, String reason) {
		return promoCrDAO.rejectCr(promoId, userId, roleId, reason);
	}

	@Override
	public List<String> getAllCategories() {
		return promoCrDAO.getAllCategories();
	}

	@Override
	public List<String> getAllBrands() {
		return promoCrDAO.getAllBrands();
	}

	@Override
	public List<String> getAllBasepacks() {
		return promoCrDAO.getAllBasepacks();
	}

	//Sarin Changes Performance
	@Override
	public List<List<String>> getAllProductMaster() {
		return promoCrDAO.getAllProductMaster();
	}
	
	//Harsha's changes 
	public String insertToportalUsage(String userId, String roleID, String module) {
		return promoCrDAO.insertToportalUsage( userId,  roleID,  module);
	}
	//Added by Kavitha D starts-SPRINT 10
	@Override
	public ArrayList<String> getHeaderListForPromoDownloadCrListing() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("CHANNEL"); //Added by Kavitha D-SPRINT15 changes
		headerList.add("MOC");
		headerList.add("SALES CATEGORY");
		headerList.add("PPM ACCOUNT");
		headerList.add("PROMO_ID");
		headerList.add("OFFER_DESCRIPTION");
		headerList.add("BASEPACK CODE");
		headerList.add("OFFER MODALITY");
		headerList.add("PRICE OFF");
		headerList.add("REGULAR PROMO QUANTITY");
		headerList.add("QUANTITY");
		headerList.add("SALES CLUSTER");
		headerList.add("PROMO ENTRY TYPE");
		headerList.add("SOL TYPE");
		headerList.add("REMARKS");
		return headerList;
	}
	
	public List<ArrayList<String>> getPromotionListingCrDownload(ArrayList<String> headerList, String userId,String moc, String roleId){
		return promoCrDAO.getPromotionListingCrDownload(headerList,userId,moc,roleId);
		
	}
	
	public String uploadApprovalData(PromoCrBean[] beanArray, String userId) throws Exception {
		return promoCrDAO.uploadApprovalData(beanArray,userId);
	}
	//Added by Kavitha D ends-SPRINT 10

}
