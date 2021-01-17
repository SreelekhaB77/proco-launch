package com.hul.proco.controller.promocr;

import java.util.List;

public interface PromoCrService {
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int actives, String roleId);

	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active, String roleId);

	public String approveCr(String promoId, String userId, String roleId);

	public String rejectCr(String promoId, String userId, String roleId, String reason);

	public List<String> getAllCategories();

	public List<String> getAllBrands();

	public List<String> getAllBasepacks();
	
	public List<List<String>> getAllProductMaster();  //Sarin Changes Performance
}
