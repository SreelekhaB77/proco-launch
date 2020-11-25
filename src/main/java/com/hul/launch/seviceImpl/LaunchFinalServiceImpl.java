package com.hul.launch.seviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.LaunchBasePacksDao;
import com.hul.launch.dao.LaunchDao;
import com.hul.launch.dao.LaunchFinalDao;
import com.hul.launch.dao.LaunchSellInDao;
import com.hul.launch.dao.LaunchVisiPlanDao;
import com.hul.launch.model.LaunchBuildUpTemp;
import com.hul.launch.model.LaunchFinalCalVO;
import com.hul.launch.model.LaunchSellIn;
import com.hul.launch.model.LaunchStoreData;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.service.LaunchFinalService;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED)
public class LaunchFinalServiceImpl implements LaunchFinalService {
	private static final Logger logger = Logger.getLogger(LaunchFinalServiceImpl.class.getName());

	@Autowired
	LaunchFinalDao launchFinalDao;

	@Autowired
	LaunchSellInDao launchSellInDao;

	@Autowired
	LaunchVisiPlanDao launchVisiPlanDao;

	@Autowired
	LaunchBasePacksDao launchBasePacksDao;

	@Autowired
	LaunchDao launchDao;

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalRespose(String launchId, String userId) {
		long startTime = System.currentTimeMillis();
		List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);
		LaunchDataResponse launchDataResonse = launchDao.getSpecificLaunchData(launchId);
		List<LaunchVisiPlanning> visiSkuCalModel = launchVisiPlanDao.getVisiByLaunchId(launchId);
		List<LaunchSellIn> listOfSellIn = launchSellInDao.getSellInForSellInNFinal(Integer.parseInt(launchId),
				Integer.toString(listOfFinal.size()));
		String clusterData = launchSellInDao.getClusterOnLaunchId(launchId);
		String[] splittedString = clusterData.split(",");
		List<String> liClusterName = new ArrayList<>();
		for (String string2 : splittedString) {
			if (null != string2) {
				if (string2.split(":").length > 1) {
					liClusterName.add(string2.split(":")[1]);
				} else {
					liClusterName.add("ALL INDIA");
				}
			}
		}
		// Final data table
		int whichVisi = 0;
		List<List<LaunchStoreData>> listOfAllLaunchStoreData = new ArrayList<>();
		for (LaunchSellIn launchSellIn : listOfSellIn) {
			LaunchVisiPlanning launchVisiPlanning = null;
			if ((whichVisi < listOfSellIn.size()) && (whichVisi < visiSkuCalModel.size())) {
				launchVisiPlanning = visiSkuCalModel.get(whichVisi);
			} else {
				launchVisiPlanning = new LaunchVisiPlanning();
				launchVisiPlanning.setACCOUNT("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5("");
				launchVisiPlanning.setSTORES_PLANNED("0");
				launchVisiPlanning.setSTORES_AVAILABLE("1");
				launchVisiPlanning.setVISI_ASSET_1("");
				launchVisiPlanning.setVISI_ASSET_2("");
				launchVisiPlanning.setVISI_ASSET_3("");
				launchVisiPlanning.setVISI_ASSET_4("");
				launchVisiPlanning.setVISI_ASSET_5("");
			}

			listOfAllLaunchStoreData.add(launchSellInDao.getListStoreData(launchSellIn, listOfFinal, launchVisiPlanning,
					launchDataResonse.getClassification(), liClusterName));
			whichVisi++;
		}
		//launchFinalDao.deleteAllBuildUp(launchId);  //Sarin Changes 18Nov2020
		// Save Build temp data
		long startTime1 = System.currentTimeMillis();
		launchFinalDao.saveLaunchBuildUpTemp(listOfAllLaunchStoreData, launchId, userId);
		long endTime1 = System.currentTimeMillis();
		logger.info("duration of saveLaunchBuildUpTemp method "+(endTime1-startTime1));
		long startTime2 = System.currentTimeMillis();
		List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);
		long endTime2 = System.currentTimeMillis();
		logger.info("duration of allDistinctFinalBuildsCombo method "+(endTime2-startTime2));
		Set<String> setOfStrings = new HashSet<>();
		for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
			String substr = "";
			List<Integer> list = new ArrayList<>();
			char character = ',';
			for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
				if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
					list.add(i);
				}
			}
			substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
			setOfStrings.add(substr);
		}

		Map<String, Map<String, String>> finalData = new HashMap<>();
		List<String> depoBasePackList = new ArrayList<String>();
		for (String depoBasepack : setOfStrings) {
			depoBasePackList.add(depoBasepack);
		}
		//for (String depoBasepack : setOfStrings) {
		Map<String, String> depobasepackCalculation = new HashMap<>();
		long startTime3 = System.currentTimeMillis();
		List<LaunchBuildUpTemp> listOfBuildUpDepo = launchFinalDao.getFinalBuildUpDepoLevelAllList(depoBasePackList, launchId);
		long endTime3 = System.currentTimeMillis();
		logger.info("duration of getFinalBuildUpDepoLevelAllList method "+(endTime3-startTime3));
		long startTime4 = System.currentTimeMillis();
		List<LaunchBuildUpTemp> cldDepo = launchFinalDao.getCldForDepoBasepackList(depoBasePackList, launchId);
		long endTime4 = System.currentTimeMillis();
		logger.info("duration of getCldForDepoBasepackList method "+(endTime4-startTime4));
		for(int i=0;i<depoBasePackList.size();i++){
			double originalCldN = Double.parseDouble(listOfBuildUpDepo.get(i).getREVISED_SELLIN_FOR_STORE_N())
					/ Double.parseDouble(cldDepo.get(i).getCLD_SIZE());
			depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
			double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN)).setScale(0, BigDecimal.ROUND_UP)
					.doubleValue();
			depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
			double factorN = maxOfOriginalCldN / originalCldN;
			depobasepackCalculation.put("factorN", Double.toString(factorN));

			double originalCldN1 = Double.parseDouble(listOfBuildUpDepo.get(i).getREVISED_SELLIN_FOR_STORE_N1())
					/ Double.parseDouble(cldDepo.get(i).getCLD_SIZE());
			depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
			double maxOfOriginalCldN1 = BigDecimal
					.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
					.setScale(0, BigDecimal.ROUND_UP).doubleValue();

			depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
			double factorN1 = maxOfOriginalCldN1 / originalCldN1;
			depobasepackCalculation.put("factorN1", Double.toString(factorN1));

			double originalCldN2 = Double.parseDouble(listOfBuildUpDepo.get(i).getREVISED_SELLIN_FOR_STORE_N2())
					/ Double.parseDouble(cldDepo.get(i).getCLD_SIZE());
			depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
			double maxOfOriginalCldN2 = BigDecimal
					.valueOf(Math.max(0,
							(originalCldN + originalCldN1 + originalCldN2) - (maxOfOriginalCldN + maxOfOriginalCldN1)))
					.setScale(0, BigDecimal.ROUND_UP).doubleValue();

			depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
			double factorN2 = maxOfOriginalCldN2 / originalCldN2;
			depobasepackCalculation.put("factorN2", Double.toString(factorN2));
			finalData.put(depoBasePackList.get(i), depobasepackCalculation);
		}
		//}
		long startTime5 = System.currentTimeMillis();
		launchFinalDao.deleteAllTempCal(launchId);
		long endTime5 = System.currentTimeMillis();
		logger.info("duration of deleteAllTempCal method "+(endTime5-startTime5));
		List<String> depoBasepackFmcgModifiedChainClusComboList = new ArrayList<String>();
		List<LaunchFinalCalVO> launchDataList = new ArrayList<>();
		List<String> substrList = new ArrayList<>();
		char character = ',';
		for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
			List<Integer> list = new ArrayList<>();
			depoBasepackFmcgModifiedChainClusComboList.add(depoBasepackFmcgModifiedChainClusCombo);
			for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
				if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
					list.add(i);
				}
			}
			String substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
			substrList.add(substr);
		}
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		long startTime6 = System.currentTimeMillis();
		List<LaunchBuildUpTemp> listOfBuildUpsList = launchFinalDao
				.getFinalBuildUpDepoLeveList(depoBasepackFmcgModifiedChainClusComboList, launchId);
		long endTime6 = System.currentTimeMillis();
		logger.info("duration of getFinalBuildUpDepoLeveList method "+(endTime6-startTime6));
		long startTime7 = System.currentTimeMillis();
		List<LaunchBuildUpTemp> cldValueList = launchFinalDao.getCldForDepoBasepackList(substrList, launchId);
		long endTime7 = System.currentTimeMillis();
		logger.info("duration of getCldForDepoBasepackList method "+(endTime7-startTime7));
		long startTime8 = System.currentTimeMillis();
		for(int i=0;i<depoBasepackFmcgModifiedChainClusComboList.size();i++) {
			Map<String, String> calculationData = finalData.get(substrList.get(i));
			double cldWithFactorsN = (Double.parseDouble(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N())
					/ Double.parseDouble(cldValueList.get(i).getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN"));

			double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N1())
					/ Double.parseDouble(cldValueList.get(i).getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN1"));

			double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N2())
					/ Double.parseDouble(cldValueList.get(i).getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN2"));

			double finalCldN = Math.ceil(cldWithFactorsN);
			double finalCldN1 = Math.ceil(cldWithFactorsN1);
			double finalCldN2 = Math.ceil(cldWithFactorsN2);

			double finalUnitsN = finalCldN * Double.parseDouble(cldValueList.get(i).getCLD_SIZE());
			double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValueList.get(i).getCLD_SIZE());
			double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValueList.get(i).getCLD_SIZE());

			LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substrList.get(i), launchId);
			double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
			double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
			double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N());
			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N1());
			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUpsList.get(i).getREVISED_SELLIN_FOR_STORE_N2());

			launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
			launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
			launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

			launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
			launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
			launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

			launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
			launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
			launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
			launchBuildUpTemp.setSTORE_COUNT(listOfBuildUpsList.get(i).getSTORE_COUNT());
			launchBuildUpTemp.setCLUSTER(listOfBuildUpsList.get(i).getCLUSTER());
			LaunchFinalCalVO  launchFinalCalVO = new LaunchFinalCalVO();
			launchFinalCalVO.setDepoBasePack(depoBasepackFmcgModifiedChainClusComboList.get(i));
			launchFinalCalVO.setLaunchId(launchId);
			launchFinalCalVO.setLaunchBuildUpTemp(launchBuildUpTemp);
			launchFinalCalVO.setUserId(userId);
			launchDataList.add(launchFinalCalVO);
			/*launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp, userId);
			launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
					userId);*/
		}
		long endTime8 = System.currentTimeMillis();
		logger.info("duration of getGsvForDepoBasepack method "+(endTime8-startTime8));
		long startTime9 = System.currentTimeMillis();
		launchFinalDao.saveFinalValue(launchDataList);
		long endTime9 = System.currentTimeMillis();
		logger.info("duration of saveFinalValue method "+(endTime9-startTime9));
		long startTime10 = System.currentTimeMillis();
		launchFinalDao.updateFinalValue(launchDataList);
		long endTime10 = System.currentTimeMillis();
		logger.info("duration of saveFinalValue method "+(endTime10-startTime10));
		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		long startTime11 = System.currentTimeMillis();
		for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
			LaunchFinalPlanResponse toReturn = launchFinalDao
					.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
			toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
			toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
			listOfFinalFinal.add(toReturn);
		}
		long endTime11 = System.currentTimeMillis();
		logger.info("duration of getSumOfForDepoBasepack method "+(endTime11-startTime11));
		long endTime = System.currentTimeMillis();
		logger.info("duration of getLaunchFinalRespose method "+(endTime-startTime));
		return listOfFinalFinal;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeEdit(String launchId, String userId) {
		List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);
		LaunchDataResponse launchDataResonse = launchDao.getSpecificLaunchData(launchId);
		List<LaunchVisiPlanning> visiSkuCalModel = launchVisiPlanDao.getVisiByLaunchId(launchId);
		List<LaunchSellIn> listOfSellIn = launchSellInDao.getSellInForSellInNFinalEdit(Integer.parseInt(launchId),
				listOfFinal);

		List<LaunchFinalPlanResponse> listOfFinalForEdit = new ArrayList<>();
		for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
			String bpStatus = launchFinalPlanResponse.getBpStatus();
			if (null == bpStatus || !bpStatus.equals("REJECTED BY TME")) {
				LaunchFinalPlanResponse launchFinalPlanResInside = new LaunchFinalPlanResponse();
				BeanUtils.copyProperties(launchFinalPlanResponse, launchFinalPlanResInside);
				listOfFinalForEdit.add(launchFinalPlanResInside);
			}
		}
		String clusterData = launchSellInDao.getClusterOnLaunchId(launchId);
		String[] splittedString = clusterData.split(",");
		List<String> liClusterName = new ArrayList<>();
		for (String string2 : splittedString) {
			if (null != string2) {
				if (string2.split(":").length > 1) {
					liClusterName.add(string2.split(":")[1]);
				} else {
					liClusterName.add("ALL INDIA");
				}
			}
		}
		// Final data table
		int whichVisi = 0;
		List<List<LaunchStoreData>> listOfAllLaunchStoreData = new ArrayList<>();
		for (LaunchSellIn launchSellIn : listOfSellIn) {
			LaunchVisiPlanning launchVisiPlanning = null;
			if ((whichVisi < listOfSellIn.size()) && (whichVisi < visiSkuCalModel.size())) {
				launchVisiPlanning = visiSkuCalModel.get(whichVisi);
			} else {
				launchVisiPlanning = new LaunchVisiPlanning();
				launchVisiPlanning.setACCOUNT("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4("");
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5("");
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5("");
				launchVisiPlanning.setSTORES_PLANNED("0");
				launchVisiPlanning.setSTORES_AVAILABLE("1");
				launchVisiPlanning.setVISI_ASSET_1("");
				launchVisiPlanning.setVISI_ASSET_2("");
				launchVisiPlanning.setVISI_ASSET_3("");
				launchVisiPlanning.setVISI_ASSET_4("");
				launchVisiPlanning.setVISI_ASSET_5("");
			}

			listOfAllLaunchStoreData.add(launchSellInDao.getListStoreData(launchSellIn, listOfFinalForEdit,
					launchVisiPlanning, launchDataResonse.getClassification(), liClusterName));
			whichVisi++;
		}
		launchFinalDao.deleteAllBuildUp(launchId);
		// Save Build temp data
		launchFinalDao.saveLaunchBuildUpTemp(listOfAllLaunchStoreData, launchId, userId);

		List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);

		Set<String> setOfStrings = new HashSet<>();
		for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
			String substr = "";
			List<Integer> list = new ArrayList<>();
			char character = ',';
			for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
				if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
					list.add(i);
				}
			}
			substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
			setOfStrings.add(substr);
		}

		Map<String, Map<String, String>> finalData = new HashMap<>();
		for (String depoBasepack : setOfStrings) {
			Map<String, String> depobasepackCalculation = new HashMap<>();
			LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAll(depoBasepack, launchId);
			LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(depoBasepack, launchId);
			double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
					/ Double.parseDouble(cldValue.getCLD_SIZE());
			depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
			double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN)).setScale(0, BigDecimal.ROUND_UP)
					.doubleValue();
			depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
			double factorN = maxOfOriginalCldN / originalCldN;
			depobasepackCalculation.put("factorN", Double.toString(factorN));

			double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
					/ Double.parseDouble(cldValue.getCLD_SIZE());
			depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
			double maxOfOriginalCldN1 = BigDecimal
					.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
					.setScale(0, BigDecimal.ROUND_UP).doubleValue();

			depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
			double factorN1 = maxOfOriginalCldN1 / originalCldN1;
			depobasepackCalculation.put("factorN1", Double.toString(factorN1));

			double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
					/ Double.parseDouble(cldValue.getCLD_SIZE());
			depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
			double maxOfOriginalCldN2 = BigDecimal
					.valueOf(Math.max(0,
							(originalCldN + originalCldN1 + originalCldN2) - (maxOfOriginalCldN + maxOfOriginalCldN1)))
					.setScale(0, BigDecimal.ROUND_UP).doubleValue();

			depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
			double factorN2 = maxOfOriginalCldN2 / originalCldN2;
			depobasepackCalculation.put("factorN2", Double.toString(factorN2));
			finalData.put(depoBasepack, depobasepackCalculation);
		}

		launchFinalDao.deleteAllTempCal(launchId);
		for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
			LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
			LaunchBuildUpTemp listOfBuildUps = launchFinalDao
					.getFinalBuildUpDepoLevel(depoBasepackFmcgModifiedChainClusCombo, launchId);
			String substr = "";
			List<Integer> list = new ArrayList<>();
			char character = ',';
			for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
				if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
					list.add(i);
				}
			}
			substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
			LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(substr, launchId);
			Map<String, String> calculationData = finalData.get(substr);
			double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
					/ Double.parseDouble(cldValue.getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN"));

			double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
					/ Double.parseDouble(cldValue.getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN1"));

			double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
					/ Double.parseDouble(cldValue.getCLD_SIZE())) * Double.parseDouble(calculationData.get("factorN2"));

			double finalCldN = Math.ceil(cldWithFactorsN);
			double finalCldN1 = Math.ceil(cldWithFactorsN1);
			double finalCldN2 = Math.ceil(cldWithFactorsN2);

			double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
			double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
			double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

			LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substr, launchId);
			double finalValueN = finalUnitsN * Integer.parseInt(gsvValue.getGSV());
			double finalValueN1 = finalUnitsN1 * Integer.parseInt(gsvValue.getGSV());
			double finalValueN2 = finalUnitsN2 * Integer.parseInt(gsvValue.getGSV());

			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
			launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

			launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
			launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
			launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

			launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
			launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
			launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

			launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
			launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
			launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
			launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
			launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
			launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp, userId);
			launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
					userId);
		}

		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalForEdit) {
			LaunchFinalPlanResponse toReturn = launchFinalDao
					.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
			toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
			toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
			listOfFinalFinal.add(toReturn);
		}

		return listOfFinalFinal;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKAM(String launchId, String forWhichKam) {
		List<LaunchFinalPlanResponse> listOfFinalFinal = null;
		try {
			List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalResposeKAM(launchId, forWhichKam);
			LaunchDataResponse launchDataResonse = launchDao.getSpecificLaunchData(launchId);
			List<LaunchVisiPlanning> visiSkuCalModel = launchVisiPlanDao.getVisiByLaunchId(launchId);
			List<LaunchSellIn> listOfSellIn = launchSellInDao.getSellInForSellInNFinalEdit(Integer.parseInt(launchId),
					listOfFinal);

			String clusterData = launchSellInDao.getClusterOnLaunchId(launchId);
			String[] splittedString = clusterData.split(",");
			List<String> liClusterName = new ArrayList<>();
			for (String string2 : splittedString) {
				if (null != string2) {
					if (string2.split(":").length > 1) {
						liClusterName.add(string2.split(":")[1]);
					} else {
						liClusterName.add("ALL INDIA");
					}
				}
			}
			// Final data table
			int whichVisi = 0;
			List<List<LaunchStoreData>> listOfAllLaunchStoreData = new ArrayList<>();
			for (LaunchSellIn launchSellIn : listOfSellIn) {
				LaunchVisiPlanning launchVisiPlanning = null;
				if ((whichVisi < listOfSellIn.size()) && (whichVisi < visiSkuCalModel.size())) {
					launchVisiPlanning = visiSkuCalModel.get(whichVisi);
				} else {
					launchVisiPlanning = new LaunchVisiPlanning();
					launchVisiPlanning.setACCOUNT("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5("");
					launchVisiPlanning.setSTORES_PLANNED("0");
					launchVisiPlanning.setSTORES_AVAILABLE("1");
					launchVisiPlanning.setVISI_ASSET_1("");
					launchVisiPlanning.setVISI_ASSET_2("");
					launchVisiPlanning.setVISI_ASSET_3("");
					launchVisiPlanning.setVISI_ASSET_4("");
					launchVisiPlanning.setVISI_ASSET_5("");
				}

				listOfAllLaunchStoreData.add(launchSellInDao.getListStoreDataKAM(launchSellIn, listOfFinal,
						launchVisiPlanning, launchDataResonse.getClassification(), liClusterName, forWhichKam));
				whichVisi++;
			}
			launchFinalDao.deleteAllBuildUpKAM(launchId, forWhichKam);
			// Save Build temp data
			launchFinalDao.saveLaunchBuildUpTempKAM(listOfAllLaunchStoreData, launchId, forWhichKam);

			List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinctKAM(launchId,
					forWhichKam);

			Set<String> setOfStrings = new HashSet<>();
			for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				setOfStrings.add(substr);
			}

			Map<String, Map<String, String>> finalData = new HashMap<>();
			for (String depoBasepack : setOfStrings) {
				Map<String, String> depobasepackCalculation = new HashMap<>();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAllKAM(depoBasepack, launchId,
						forWhichKam);
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepackKAM(depoBasepack, launchId,
						forWhichKam);
				double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
				double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
				double factorN = maxOfOriginalCldN / originalCldN;
				depobasepackCalculation.put("factorN", Double.toString(factorN));

				double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
				double maxOfOriginalCldN1 = BigDecimal
						.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
				double factorN1 = maxOfOriginalCldN1 / originalCldN1;
				depobasepackCalculation.put("factorN1", Double.toString(factorN1));

				double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
				double maxOfOriginalCldN2 = BigDecimal
						.valueOf(Math.max(0,
								(originalCldN + originalCldN1 + originalCldN2)
								- (maxOfOriginalCldN + maxOfOriginalCldN1)))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
				double factorN2 = maxOfOriginalCldN2 / originalCldN2;
				depobasepackCalculation.put("factorN2", Double.toString(factorN2));
				finalData.put(depoBasepack, depobasepackCalculation);
			}

			launchFinalDao.deleteAllTempCalKam(launchId, forWhichKam);
			for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao
						.getFinalBuildUpDepoLevelKAM(depoBasepackFmcgModifiedChainClusCombo, launchId, forWhichKam);
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepackKAM(substr, launchId, forWhichKam);
				Map<String, String> calculationData = finalData.get(substr);
				double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN"));

				double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN1"));

				double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN2"));

				double finalCldN = Math.ceil(cldWithFactorsN);
				double finalCldN1 = Math.ceil(cldWithFactorsN1);
				double finalCldN2 = Math.ceil(cldWithFactorsN2);

				double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

				LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepackKAM(substr, launchId, forWhichKam);
				double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
				double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
				double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

				launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
				launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
				launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

				launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

				launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
				launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
				launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
				launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
				launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
				launchFinalDao.saveFinalValueKAM(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						forWhichKam);
				launchFinalDao.updateFinalValueKAM(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						forWhichKam);
			}

			listOfFinalFinal = new ArrayList<>();
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				LaunchFinalPlanResponse toReturn = launchFinalDao
						.getSumOfForDepoBasepackKAM(launchFinalPlanResponse.getSkuName(), launchId, forWhichKam);
				toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
				listOfFinalFinal.add(toReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfFinalFinal;
	}

	@Override
	public String saveLaunchFinalBuildUp(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId) {
		return launchFinalDao.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
	}

	@Override
	public List<ArrayList<String>> getFinalBuildUpDumpNew(String userId, String launchId) {
		return launchFinalDao.getFinalBuildUpDumptNew(userId, launchId);
	}

	@Override
	public List<ArrayList<String>> getFinalBuildUpDumpNew(String userId, String[] launchId) {
		return launchFinalDao.getFinalBuildUpDumptNew(userId, launchId);
	}

	@Override
	public List<ArrayList<String>> getFinalBuildUpDumpNewKam(String userId, String launchId) {
		return launchFinalDao.getFinalBuildUpDumpNewKam(userId, launchId);
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKamAfterRejBp(String launchId, String userId,
			String kamAcc) {
		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		try {
			List<String> listOfKamAccounts = launchFinalDao.getKamAccount(kamAcc);
			launchFinalDao.deleteAllBuildUpKAM(launchId, listOfKamAccounts);
			List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);
			Set<String> setOfStrings = new HashSet<>();
			for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				setOfStrings.add(substr);
			}

			Map<String, Map<String, String>> finalData = new HashMap<>();
			for (String depoBasepack : setOfStrings) {
				Map<String, String> depobasepackCalculation = new HashMap<>();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAll(depoBasepack, launchId);
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(depoBasepack, launchId);
				double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
				double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
				double factorN = maxOfOriginalCldN / originalCldN;
				depobasepackCalculation.put("factorN", Double.toString(factorN));

				double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
				double maxOfOriginalCldN1 = BigDecimal
						.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
				double factorN1 = maxOfOriginalCldN1 / originalCldN1;
				depobasepackCalculation.put("factorN1", Double.toString(factorN1));

				double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
				double maxOfOriginalCldN2 = BigDecimal
						.valueOf(Math.max(0,
								(originalCldN + originalCldN1 + originalCldN2)
								- (maxOfOriginalCldN + maxOfOriginalCldN1)))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
				double factorN2 = maxOfOriginalCldN2 / originalCldN2;
				depobasepackCalculation.put("factorN2", Double.toString(factorN2));
				finalData.put(depoBasepack, depobasepackCalculation);
			}

			launchFinalDao.deleteAllTempCal(launchId);
			for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao
						.getFinalBuildUpDepoLevel(depoBasepackFmcgModifiedChainClusCombo, launchId);
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(substr, launchId);
				Map<String, String> calculationData = finalData.get(substr);
				double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN"));

				double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN1"));

				double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN2"));

				double finalCldN = Math.ceil(cldWithFactorsN);
				double finalCldN1 = Math.ceil(cldWithFactorsN1);
				double finalCldN2 = Math.ceil(cldWithFactorsN2);

				double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

				LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substr, launchId);
				double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
				double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
				double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

				launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
				launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
				launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

				launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

				launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
				launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
				launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
				launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
				launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
				launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
				launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
			}
			List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);

			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				LaunchFinalPlanResponse toReturn = launchFinalDao
						.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
				toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
				listOfFinalFinal.add(toReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfFinalFinal;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeEditKamAfterLaunchRej(String launchId, String userId,
			String kamAcc) {
		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		try {
			List<String> listOfKamAccounts = launchFinalDao.getKamAccount(kamAcc);
			launchFinalDao.deleteAllBuildUpKAM(launchId, listOfKamAccounts);
			List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);
			Set<String> setOfStrings = new HashSet<>();
			for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				setOfStrings.add(substr);
			}

			Map<String, Map<String, String>> finalData = new HashMap<>();
			for (String depoBasepack : setOfStrings) {
				Map<String, String> depobasepackCalculation = new HashMap<>();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAll(depoBasepack, launchId);
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(depoBasepack, launchId);
				double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
				double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
				double factorN = maxOfOriginalCldN / originalCldN;
				depobasepackCalculation.put("factorN", Double.toString(factorN));

				double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
				double maxOfOriginalCldN1 = BigDecimal
						.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
				double factorN1 = maxOfOriginalCldN1 / originalCldN1;
				depobasepackCalculation.put("factorN1", Double.toString(factorN1));

				double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
				double maxOfOriginalCldN2 = BigDecimal
						.valueOf(Math.max(0,
								(originalCldN + originalCldN1 + originalCldN2)
								- (maxOfOriginalCldN + maxOfOriginalCldN1)))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
				double factorN2 = maxOfOriginalCldN2 / originalCldN2;
				depobasepackCalculation.put("factorN2", Double.toString(factorN2));
				finalData.put(depoBasepack, depobasepackCalculation);
			}

			launchFinalDao.deleteAllTempCal(launchId);
			for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao
						.getFinalBuildUpDepoLevel(depoBasepackFmcgModifiedChainClusCombo, launchId);
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(substr, launchId);
				Map<String, String> calculationData = finalData.get(substr);
				double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN"));

				double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN1"));

				double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN2"));

				double finalCldN = Math.ceil(cldWithFactorsN);
				double finalCldN1 = Math.ceil(cldWithFactorsN1);
				double finalCldN2 = Math.ceil(cldWithFactorsN2);

				double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

				LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substr, launchId);
				double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
				double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
				double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

				launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
				launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
				launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

				launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

				launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
				launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
				launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
				launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
				launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
				launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
				launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
			}
			List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);

			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				LaunchFinalPlanResponse toReturn = launchFinalDao
						.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
				toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
				listOfFinalFinal.add(toReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfFinalFinal;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeAfterBPReject(String launchId, String userId,
			String kamAcc, String bpId) {
		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		try {
			List<String> bpIds = Arrays.asList(bpId.split(","));
			List<String> bpCodes = launchBasePacksDao.getBasepackCodeOnBpId(bpIds, launchId);
			List<String> listOfKamAccounts = launchFinalDao.getKamAccount(kamAcc);
			launchFinalDao.deleteAllBuildUpKAMBp(launchId, listOfKamAccounts, bpCodes);
			List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);
			Set<String> setOfStrings = new HashSet<>();
			for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				setOfStrings.add(substr);
			}

			Map<String, Map<String, String>> finalData = new HashMap<>();
			for (String depoBasepack : setOfStrings) {
				Map<String, String> depobasepackCalculation = new HashMap<>();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAll(depoBasepack, launchId);
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(depoBasepack, launchId);
				double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
				double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
				double factorN = maxOfOriginalCldN / originalCldN;
				depobasepackCalculation.put("factorN", Double.toString(factorN));

				double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
				double maxOfOriginalCldN1 = BigDecimal
						.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
				double factorN1 = maxOfOriginalCldN1 / originalCldN1;
				depobasepackCalculation.put("factorN1", Double.toString(factorN1));

				double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
				double maxOfOriginalCldN2 = BigDecimal
						.valueOf(Math.max(0,
								(originalCldN + originalCldN1 + originalCldN2)
								- (maxOfOriginalCldN + maxOfOriginalCldN1)))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
				double factorN2 = maxOfOriginalCldN2 / originalCldN2;
				depobasepackCalculation.put("factorN2", Double.toString(factorN2));
				finalData.put(depoBasepack, depobasepackCalculation);
			}

			launchFinalDao.deleteAllTempCal(launchId);
			for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao
						.getFinalBuildUpDepoLevel(depoBasepackFmcgModifiedChainClusCombo, launchId);
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(substr, launchId);
				Map<String, String> calculationData = finalData.get(substr);
				double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN"));

				double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN1"));

				double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN2"));

				double finalCldN = Math.ceil(cldWithFactorsN);
				double finalCldN1 = Math.ceil(cldWithFactorsN1);
				double finalCldN2 = Math.ceil(cldWithFactorsN2);

				double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

				LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substr, launchId);
				double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
				double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
				double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

				launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
				launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
				launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

				launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

				launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
				launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
				launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
				launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
				launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
				launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
				launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
			}
			List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);

			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				LaunchFinalPlanResponse toReturn = launchFinalDao
						.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
				toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
				listOfFinalFinal.add(toReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfFinalFinal;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeAfterStoreReject(String launchId, String userId,
			String kamAcc, String storeId) {

		List<LaunchFinalPlanResponse> listOfFinalFinal = new ArrayList<>();
		try {
			List<String> storeIds = Arrays.asList(storeId.split(","));

			List<String> listOfKamAccounts = launchFinalDao.getKamAccount(kamAcc);

			launchFinalDao.deleteAllBuildUpKAMStore(launchId, listOfKamAccounts, storeIds);

			List<String> allDistinctFinalBuildsCombo = launchFinalDao.getFinalBuildUpDepoLevelDistinct(launchId);
			Set<String> setOfStrings = new HashSet<>();
			for (String deboBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < deboBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (deboBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = deboBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				setOfStrings.add(substr);
			}

			Map<String, Map<String, String>> finalData = new HashMap<>();
			for (String depoBasepack : setOfStrings) {
				Map<String, String> depobasepackCalculation = new HashMap<>();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao.getFinalBuildUpDepoLevelAll(depoBasepack, launchId);
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(depoBasepack, launchId);
				double originalCldN = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN", Double.toString(originalCldN));
				double maxOfOriginalCldN = BigDecimal.valueOf(Math.max(5, originalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				depobasepackCalculation.put("maxOfOriginalCldN", Double.toString(maxOfOriginalCldN));
				double factorN = maxOfOriginalCldN / originalCldN;
				depobasepackCalculation.put("factorN", Double.toString(factorN));

				double originalCldN1 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN1", Double.toString(originalCldN1));
				double maxOfOriginalCldN1 = BigDecimal
						.valueOf(Math.max(0, (originalCldN + originalCldN1) - maxOfOriginalCldN))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN1", Double.toString(maxOfOriginalCldN1));
				double factorN1 = maxOfOriginalCldN1 / originalCldN1;
				depobasepackCalculation.put("factorN1", Double.toString(factorN1));

				double originalCldN2 = Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE());
				depobasepackCalculation.put("originalCldN2", Double.toString(originalCldN2));
				double maxOfOriginalCldN2 = BigDecimal
						.valueOf(Math.max(0,
								(originalCldN + originalCldN1 + originalCldN2)
								- (maxOfOriginalCldN + maxOfOriginalCldN1)))
						.setScale(0, BigDecimal.ROUND_UP).doubleValue();

				depobasepackCalculation.put("maxOfOriginalCldN2", Double.toString(maxOfOriginalCldN2));
				double factorN2 = maxOfOriginalCldN2 / originalCldN2;
				depobasepackCalculation.put("factorN2", Double.toString(factorN2));
				finalData.put(depoBasepack, depobasepackCalculation);
			}

			launchFinalDao.deleteAllTempCal(launchId);
			for (String depoBasepackFmcgModifiedChainClusCombo : allDistinctFinalBuildsCombo) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				LaunchBuildUpTemp listOfBuildUps = launchFinalDao
						.getFinalBuildUpDepoLevel(depoBasepackFmcgModifiedChainClusCombo, launchId);
				String substr = "";
				List<Integer> list = new ArrayList<>();
				char character = ',';
				for (int i = 0; i < depoBasepackFmcgModifiedChainClusCombo.length(); i++) {
					if (depoBasepackFmcgModifiedChainClusCombo.charAt(i) == character) {
						list.add(i);
					}
				}
				substr = depoBasepackFmcgModifiedChainClusCombo.substring(0, list.get(1));
				LaunchBuildUpTemp cldValue = launchFinalDao.getCldForDepoBasepack(substr, launchId);
				Map<String, String> calculationData = finalData.get(substr);
				double cldWithFactorsN = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN"));

				double cldWithFactorsN1 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN1"));

				double cldWithFactorsN2 = (Double.parseDouble(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2())
						/ Double.parseDouble(cldValue.getCLD_SIZE()))
						* Double.parseDouble(calculationData.get("factorN2"));

				double finalCldN = Math.ceil(cldWithFactorsN);
				double finalCldN1 = Math.ceil(cldWithFactorsN1);
				double finalCldN2 = Math.ceil(cldWithFactorsN2);

				double finalUnitsN = finalCldN * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN1 = finalCldN1 * Double.parseDouble(cldValue.getCLD_SIZE());
				double finalUnitsN2 = finalCldN2 * Double.parseDouble(cldValue.getCLD_SIZE());

				LaunchBuildUpTemp gsvValue = launchFinalDao.getGsvForDepoBasepack(substr, launchId);
				double finalValueN = finalUnitsN * Double.parseDouble(gsvValue.getGSV());
				double finalValueN1 = finalUnitsN1 * Double.parseDouble(gsvValue.getGSV());
				double finalValueN2 = finalUnitsN2 * Double.parseDouble(gsvValue.getGSV());

				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N1());
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(listOfBuildUps.getREVISED_SELLIN_FOR_STORE_N2());

				launchBuildUpTemp.setSELLIN_VALUE_N(Double.toString(finalValueN));
				launchBuildUpTemp.setSELLIN_VALUE_N1(Double.toString(finalValueN1));
				launchBuildUpTemp.setSELLIN_VALUE_N2(Double.toString(finalValueN2));

				launchBuildUpTemp.setSELLIN_VALUE_CLD_N(Double.toString(finalCldN));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N1(Double.toString(finalCldN1));
				launchBuildUpTemp.setSELLIN_VALUE_CLD_N2(Double.toString(finalCldN2));

				launchBuildUpTemp.setSELLIN_UNITS_N(Double.toString(finalUnitsN));
				launchBuildUpTemp.setSELLIN_UNITS_N1(Double.toString(finalUnitsN1));
				launchBuildUpTemp.setSELLIN_UNITS_N2(Double.toString(finalUnitsN2));
				launchBuildUpTemp.setSTORE_COUNT(listOfBuildUps.getSTORE_COUNT());
				launchBuildUpTemp.setCLUSTER(listOfBuildUps.getCLUSTER());
				launchFinalDao.saveFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
				launchFinalDao.updateFinalValue(depoBasepackFmcgModifiedChainClusCombo, launchId, launchBuildUpTemp,
						userId);
			}
			List<LaunchFinalPlanResponse> listOfFinal = launchFinalDao.getLaunchFinalRespose(launchId);

			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				if(launchFinalPlanResponse.getError() != null) {
					if (launchFinalPlanResponse.getError().length() != 0) {
						throw new Exception(launchFinalPlanResponse.getError());
					}
				}
				LaunchFinalPlanResponse toReturn = launchFinalDao
						.getSumOfForDepoBasepack(launchFinalPlanResponse.getSkuName(), launchId);
				toReturn.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				toReturn.setSkuName(launchFinalPlanResponse.getSkuName());
				listOfFinalFinal.add(toReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchFinalPlanResponse toReturn = new LaunchFinalPlanResponse();
			toReturn.setError(e.toString());
			listOfFinalFinal.add(toReturn);
			return listOfFinalFinal;
		}
		return listOfFinalFinal;

	}

	@Override
	public List<ArrayList<String>> getMstnClearanceDataDump(String userId, List<String> listOfLaunchData) {
		return launchFinalDao.getMstnClearanceDataDump(userId, listOfLaunchData);
	}

	@Override
	public List<ArrayList<String>> getMstnClearanceDataDumpCoe(String userId, List<String> listOfLaunchData) {
		return launchFinalDao.getMstnClearanceDataDumpCoe(userId, listOfLaunchData);
	}

}