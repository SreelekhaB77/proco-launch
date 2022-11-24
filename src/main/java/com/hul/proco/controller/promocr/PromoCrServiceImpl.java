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
	
	
	
}
