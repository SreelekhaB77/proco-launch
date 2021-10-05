package com.hul.proco.controller.disaggregatepromo;

import java.util.List;

public interface DisaggregationDAO {

	public int getDisaggregationRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String offerType, String modality, String year, String moc, String userId);

	public List<DisaggregationBean> getDisaggregationTableList(int pageDisplayStart, int pageDisplayLength,
			String cagetory, String brand, String basepack, String custChainL1, String custChainL2, String offerType,
			String modality, String year, String moc, String userId);

	public String disaggregatePromos(String promoId[], String[] mocs,String userId);

	public List<String> getDepotForAddDepot(String promoId,String branch,String cluster);

	public List<String> getBranchForAddDepot();

	public List<String> getClusterForAddDepot(String branch);
	
	public String saveDepotForAddDepot(String promoId,String branch,String cluster,String depot,int quantity,String userId);
	public String updateKamsubmitStatus();

}
