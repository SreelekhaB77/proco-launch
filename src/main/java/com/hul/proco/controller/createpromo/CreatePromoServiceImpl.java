package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePromoServiceImpl implements CreatePromoService {

	@Autowired
	private CreatePromoDAO createPromoDAO;

	@Override
	@Transactional
	public List<String> getCustomerChainL1() {
		return createPromoDAO.getCustomerChainL1();
	}

	@Override
	@Transactional
	public List<String> getCustomerChainL2(String customerChainL1) {
		return createPromoDAO.getCustomerChainL2(customerChainL1);
	}

	@Override
	@Transactional
	public List<String> getOfferTypes() {
		return createPromoDAO.getOfferTypes();
	}

	@Override
	@Transactional
	public String getGeography(boolean isCreatePage) {
		return createPromoDAO.getGeography(isCreatePage);
	}

	@Override
	@Transactional
	public Map<Integer, String> getModality() {
		return createPromoDAO.getModality();
	}

	@Override
	@Transactional
	public Map<String, Object> getYearAndMoc(boolean isCreatePage) {
		return createPromoDAO.getYearAndMoc(isCreatePage);
	}

	@Override
	@Transactional
	public Map<String, String> getBasepackDetails(String basepack, String userId) {
		return createPromoDAO.getBasepackDetails(basepack, userId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public String createPromotion(CreatePromotionBean[] bean, String userId, String status, boolean isCreate,
			boolean isFromUi) throws Exception {
		return createPromoDAO.createPromotion(bean, userId, status, isCreate, isFromUi);
	}

	@Override
	public ArrayList<String> getHeaderListForPromotionErrorDownload() {
		ArrayList<String> headerList = new ArrayList<String>();
//		headerList.add("Start Date(DD/MM/YYYY)");
//		headerList.add("End Date(DD/MM/YYYY)");
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
		headerList.add("ERROR MSG");
		return headerList;
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerList, String userId) {
		return createPromoDAO.getPromotionErrorDetails(headerList, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForPromotionTemplateDownload() {
		ArrayList<String> headerList = new ArrayList<String>();
//		headerList.add("Start Date(DD/MM/YYYY)");
//		headerList.add("End Date(DD/MM/YYYY)");
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
		return headerList;
	}

	@Override
	@Transactional
	public List<String> getAllCategories(String userId) {
		return createPromoDAO.getAllCategories(userId);
	}

	@Override
	@Transactional
	public List<String> getAllBrands(String userId) {
		return createPromoDAO.getAllBrands(userId);
	}

	@Override
	@Transactional
	public List<String> getAllBasepacks(String userId) {
		return createPromoDAO.getAllBasepacks(userId);
	}

	@Override
	@Transactional
	public String getErrorMsg(String userId) {
		return createPromoDAO.getErrorMsg(userId);
	}

	@Override
	@Transactional
	public List<String> getPromoIds() {
		return createPromoDAO.getPromoIds();
	}

	@Override
	@Transactional
	public int getDifferenceInDays(String moc) {
		return createPromoDAO.getDifferenceInDays(moc);
	}

	@Override
	@Transactional
	public Map<String, String> getChildBasepackDetails(String basepack) {
		return createPromoDAO.getChildBasepackDetails(basepack);
	}

	@Override
	@Transactional
	public List<String> getCustomerChainL1(String userId) {
		return createPromoDAO.getCustomerChainL1(userId);
	}

	@Override
	@Transactional
	public String getCustomerChainL2WithCluster(List<String> liClusterCode) {
		return createPromoDAO.getCustomerChainL2WithCluster(liClusterCode);
	}

	@Override
	@Transactional
	public String getCustomerChainL2WithCluster() {
		return createPromoDAO.getCustomerChainL2WithCluster();
	}

	@Override
	@Transactional
	public Map<String, List<String>> getCustomerChainL2WithCluster(String launchNature2) {
		return createPromoDAO.getCustomerChainL2WithCluster(launchNature2);
	}
	
	@Override
	@Transactional
	public Map<String, List<String>> getL2WithClusterStore(String launchNature2) {
		return createPromoDAO.getL2WithClusterStore(launchNature2);
	}

	@Override
	@Transactional
	public String getClusterOnCustomer(List<String> customerList) {
		return createPromoDAO.getRegionOnCustomer(customerList);
	}
	
	@Override
	@Transactional
	public List<String> getClusterOnCustomerList(List<String> customerList) {
		return createPromoDAO.getRegionOnCustomerList(customerList);
	}

	@Override
	@Transactional
	public Map<String, List<String>> getL2WithClusterTown(String launchNature2) {
		return createPromoDAO.getL2WithClusterTown(launchNature2);
	}
	
	@Override
	@Transactional
	public String getL2WithClusterTownJson(String launchNature2) {
		return createPromoDAO.getL2WithClusterTownJson(launchNature2);
	}

	@Override
	@Transactional
	public String getL2WithClusterStoreJson(String launchNature2) {
		return createPromoDAO.getL2WithClusterStoreJson(launchNature2);
	}

	@Override
	@Transactional
	public String getCustomerChainL2WithClusterJson(String launchNature2) {
		return createPromoDAO.getCustomerChainL2WithClusterJson(launchNature2);
	}

	@Override
	@Transactional
	public String getCustomerDataOnRegion(List<String> liClusterCode) {
		return createPromoDAO.getCustomerDataOnRegion(liClusterCode);
	}

	@Override
	@Transactional
	public List<String> getCustomerChainL1ForLaunch() {
		return createPromoDAO.getCustomerChainL1ForLaunch();
	}

}
