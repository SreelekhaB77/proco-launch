package com.hul.proco.controller.disaggregatepromo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisaggregationServiceImpl implements DisaggregationService {
	
	@Autowired
	private DisaggregationDAO disaggregationDAO;

	@Override
	public int getDisaggregationRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String offerType, String modality, String year, String moc, String userId) {
		return  disaggregationDAO.getDisaggregationRowCount(cagetory, brand, basepack, custChainL1, custChainL2, offerType, modality, year, moc, userId);
	}

	@Override
	public List<DisaggregationBean> getDisaggregationTableList(int pageDisplayStart, int pageDisplayLength,
			String cagetory, String brand, String basepack, String custChainL1, String custChainL2, String offerType,
			String modality, String year, String moc, String userId) {
		return disaggregationDAO.getDisaggregationTableList(pageDisplayStart, pageDisplayLength, cagetory, brand, basepack, custChainL1, custChainL2, offerType, modality, year, moc, userId);
	}

	@Override
	public String disaggregatePromos(String promoId[],String[] mocs,String userId) {
		return disaggregationDAO.disaggregatePromos(promoId,mocs,userId);
	}

	@Override
	public List<String> getDepotForAddDepot(String promoId,String branch,String cluster) {
		return disaggregationDAO.getDepotForAddDepot(promoId,branch,cluster);
	}

	@Override
	public List<String> getBranchForAddDepot() {
		return disaggregationDAO.getBranchForAddDepot();
	}

	@Override
	public List<String> getClusterForAddDepot(String branch) {
		return disaggregationDAO.getClusterForAddDepot(branch);
	}

	@Override
	public String saveDepotForAddDepot(String promoId, String branch, String cluster, String depot, int quantity,String userId) {
		return disaggregationDAO.saveDepotForAddDepot(promoId, branch, cluster, depot, quantity,userId);
	}

}
