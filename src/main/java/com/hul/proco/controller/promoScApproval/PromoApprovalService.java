package com.hul.proco.controller.promoScApproval;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.proco.controller.promocr.PromoCrBean;

@Service
public class PromoApprovalService {
	
	@Autowired
	PromoApproval promoapproval; 

	
	public String insertToportalUsage(String userId, String roleID, String module) {
		return promoapproval.insertToportalUsage( userId,  roleID,  module);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int getPromoListScRowCount(String userId, String roleId, String moc) {
		return promoapproval.getPromoListScRowCount(userId,roleId,moc);
	}

	@Transactional(rollbackFor = { Exception.class })
	public List<PromoCrBean> getPromoScTableList(int pageDisplayStart, int pageDisplayLength, String userId, String roleId, String moc,	String searchParameter) {
		return promoapproval.getPromoScTableList(pageDisplayStart, pageDisplayLength, userId,roleId,moc,searchParameter);
	}

	@Transactional(rollbackFor = { Exception.class })
	public String approvePromoSc(String promoId, String userId, String roleId) {
		// TODO Auto-generated method stub
		return promoapproval.approvePromoSc(promoId,userId,roleId);
	}

	public ArrayList<String> getHeaderListForPromoDownloadScListing() {
			ArrayList<String> headerList=new ArrayList<String>();
			headerList.add("CHANNEL");
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
	@Transactional(rollbackFor = { Exception.class })
	public List<ArrayList<String>> getPromotionListingScDownload(ArrayList<String> headerList, String userId,
			String moc, String roleId) {
		return promoapproval.getPromotionListingScDownload(headerList,userId,moc,roleId);
	}

	@Transactional(rollbackFor = { Exception.class })
	public String uploadScApprovalData(PromoCrBean[] beanArray, String userId) throws Exception {
		return promoapproval.uploadScApprovalData(beanArray,userId);
	}
	}


