package com.hul.proco.controller.promocr;

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
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory, 
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active,String roleId) {

		return promoCrDAO.getPromoTableList(pageDisplayStart, pageDisplayLength, cagetory, brand, 
				basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active,roleId);
	}

	@Override
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1, String custChainL2, 
			String geography, String offerType, String modality, String year, String moc, String userId, int active,String roleId) {
		return promoCrDAO.getPromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2, geography, offerType, modality, year, moc, userId, active,roleId);
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
	
	
}
