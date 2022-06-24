package com.hul.proco.controller.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollaborationServiceImpl implements CollaborationService {

	
	@Autowired
	private CollaborationDAO collaborationDao;
	
	@Transactional
	@Override
	public int getCollaborationRowCount(/*String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String offerType, String modality, String year, */String moc, String userId) {
		return collaborationDao.getCollaborationRowCount(/*cagetory, brand, basepack, custChainL1, custChainL2, offerType, modality, year,*/ moc, userId);
	}

	@Transactional
	@Override
	public List<DisplayCollaborationBean> getCollaborationTableList(int pageDisplayStart, int pageDisplayLength,
			/*String cagetory, String brand, String basepack, String custChainL1, String custChainL2, String offerType,
			String modality, String year, */ String moc, String userId, String[] kamAccounts) {
		return collaborationDao.getCollaborationTableList(pageDisplayStart, pageDisplayLength, /*cagetory, brand, basepack, custChainL1, custChainL2, offerType, modality, year, */moc, userId, kamAccounts);
	}

	/*@Override
	@Transactional
	public DisplayCollaborationBean getDisplayCollaboration(String promoId) {
		return collaborationDao.getDisplayCollaboration(promoId);
	}*/

	@Override
	public ArrayList<String> getHeaderListForKamTemplateDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Geography");
		headerList.add("Basepack");
		headerList.add("Customer Chain L1");
		headerList.add("DP Per Split");
		headerList.add("DP Qty Split");
		headerList.add("Depot");
		headerList.add("Branch");
		headerList.add("Cluster");
		headerList.add("Depot Per");
		headerList.add("Depot Qty");
		headerList.add("KAM Split");
		return headerList;
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getL1DepotDisaggregation(ArrayList<String> headerList,String userId,String[] promoId) {
		return collaborationDao.getL1DepotDisaggregation(headerList,userId, promoId);
	}

	@Override
	@Transactional
	public String uploadKamL1(L1CollaborationBean[] bean, String userId) throws Exception {
		return collaborationDao.uploadKamL1(bean, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForKamL1ErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Geography");
		headerList.add("Basepack");
		headerList.add("Customer Chain L1");
		headerList.add("DP Per Split");
		headerList.add("DP Qty Split");
		headerList.add("Depot");
		headerList.add("Branch");
		headerList.add("Cluster");
		headerList.add("Depot Per");
		headerList.add("Depot Qty");
		headerList.add("KAM Split");
		headerList.add("Error Msg");
		return headerList;
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getKamL1ErrorDetails(ArrayList<String> headerList, String userId) {
		return collaborationDao.getKamL1ErrorDetails(headerList, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForKamL2TemplateDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Geography");
		headerList.add("Basepack");
		headerList.add("Customer Chain L1");
		headerList.add("L1 DP Per Split");
		headerList.add("L1 DP Qty Split");
		headerList.add("Customer Chain L2");
		headerList.add("L2 DP Per Split");
		headerList.add("L2 DP Qty Split");
		headerList.add("Depot");
		headerList.add("Branch");
		headerList.add("Cluster");
		headerList.add("Depot Per");
		headerList.add("Depot Qty");
		headerList.add("KAM Split");
		return headerList;
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getL2DepotDisaggregation(ArrayList<String> headerList, String userId,
			String[] promoId) {
		return collaborationDao.getL2DepotDisaggregation(headerList, userId, promoId);
	}

	@Override
	@Transactional
	public String uploadKamL2(L2CollaborationBean[] bean, String userId) throws Exception {
		return collaborationDao.uploadKamL2(bean, userId);
	}

	@Override
	public ArrayList<String> getHeaderListForKamL2ErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Geography");
		headerList.add("Basepack");
		headerList.add("Customer Chain L1");
		headerList.add("L1 DP Per Split");
		headerList.add("L1 DP Qty Split");
		headerList.add("Customer Chain L2");
		headerList.add("L2 DP Per Split");
		headerList.add("L2 DP Qty Split");
		headerList.add("Depot");
		headerList.add("Branch");
		headerList.add("Cluster");
		headerList.add("Depot Per");
		headerList.add("Depot Qty");
		headerList.add("KAM Split");
		headerList.add("Error Msg");
		return headerList;
	}

	@Override
	@Transactional
	public List<ArrayList<String>> getKamL2ErrorDetails(ArrayList<String> headerList, String userId) {
		return collaborationDao.getKamL2ErrorDetails(headerList, userId);
	}

}
