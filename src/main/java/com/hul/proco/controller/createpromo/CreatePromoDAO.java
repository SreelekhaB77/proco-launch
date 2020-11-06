package com.hul.proco.controller.createpromo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CreatePromoDAO {

	public List<String> getCustomerChainL1();

	public List<String> getCustomerChainL1(String userId);

	public List<String> getCustomerChainL2(String customerChainL1);

	public List<String> getOfferTypes();

	public List<String> getPromoIds();

	public String getGeography(boolean isCreatePage);

	public Map<Integer, String> getModality();

	public Map<String, Object> getYearAndMoc(boolean isCreatePage);

	public Map<String, String> getBasepackDetails(String basepack, String userId);

	public Map<String, String> getChildBasepackDetails(String basepack);

	public String createPromotion(CreatePromotionBean[] bean, String userId, String status, boolean isCreate,
			boolean isFromUi) throws Exception;

	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerList, String userId);

	public List<String> getAllCategories(String userId);

	public List<String> getAllBrands(String userId);

	public List<String> getAllBasepacks(String userId);

	public String getErrorMsg(String userId);

	public int getDifferenceInDays(String moc);

	public Map<String, List<String>> getCustomerChainL2WithCluster(String launchNature2);

	public String getCustomerChainL2WithCluster(List<String> liClusterCode);

	public Map<String, List<String>> getL2WithClusterStore(String launchNature2);

	public String getL2WithClusterStoreJson(String launchNature2);

	public String getRegionOnCustomer(List<String> customerList);

	public Map<String, List<String>> getL2WithClusterTown(String launchNature2);

	public String getL2WithClusterTownJson(String launchNature2);

	public String getCustomerChainL2WithClusterJson(String launchNature2);

	String getCustomerChainL2WithCluster();

	public String getCustomerDataOnRegion(List<String> liClusterCode);

	public List<String> getRegionOnCustomerList(List<String> customerList);

	List<String> getCustomerChainL1ForLaunch();
}
