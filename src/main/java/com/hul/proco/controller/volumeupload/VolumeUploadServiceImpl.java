package com.hul.proco.controller.volumeupload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VolumeUploadServiceImpl implements VolumeUploadService {

	@Autowired
	private VolumeUploadDAO volumeUploadDAO;

	@Override
	@Transactional
	public List<ArrayList<String>> getPromoTableList(ArrayList<String> headerList,String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active) {
		return volumeUploadDAO.getPromoTableList(headerList,cagetory, brand, basepack, custChainL1, custChainL2, geography,
				offerType, modality, year, moc, userId, active);
	}

	
	@Override
	public ArrayList<String> getHeaderListForVolumeUploadTemplateDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Year");
		headerList.add("MOC");
		headerList.add("Customer Chain L1");
		headerList.add("Customer Chain L2");
		headerList.add("Sales Category");
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
		headerList.add("Quantity");
		return headerList;
	}


	@Override
	@Transactional
	public String volumeUploadForPromotion(VolumeUploadBean[] bean, String userId) throws Exception {
		return volumeUploadDAO.volumeUploadForPromotion(bean, userId);
	}


	@Override
	@Transactional
	public List<ArrayList<String>> getVolumeErrorDetails(ArrayList<String> headerList, String userId) {
		return volumeUploadDAO.getVolumeErrorDetails(headerList, userId);
	}
	
	@Override
	public ArrayList<String> getHeaderListForVolumeErrorFileDownload() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("Promo Id");
		headerList.add("Year");
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
		headerList.add("Quantity");
		headerList.add("Error Msg");
		return headerList;
	}
}
