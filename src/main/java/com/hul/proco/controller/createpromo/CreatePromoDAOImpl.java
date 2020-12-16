package com.hul.proco.controller.createpromo;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.hul.launch.web.util.CommonUtils;

@Repository
public class CreatePromoDAOImpl implements CreatePromoDAO {

	private Logger logger = Logger.getLogger(CreatePromoDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP(START_DATE,END_DATE,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,USER_ID,ROW_NO,PROMO_ID,REASON,REMARK,CHANGES_MADE,MOC) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43)";  //Sarin - Added Parameters position

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER = "INSERT INTO TBL_PROCO_PROMOTION_MASTER(YEAR,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,USER_ID,PROMO_ID,PID,ACTIVE,START_DATE,END_DATE,ORIGINAL_ID,REASON,REMARK,CHANGES_MADE) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43,?44,?45,?46)";  //Sarin - Added Parameters position

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerChainL1() {
		List<String> customerChainL1 = new ArrayList<>();
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT CUSTOMER_CHAIN_L1 FROM TBL_PROCO_CUSTOMER_MASTER GROUP BY CUSTOMER_CHAIN_L1 ORDER BY CUSTOMER_CHAIN_L1");
			customerChainL1 = queryToGetCustomeChainL1.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return customerChainL1;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerChainL2(String customerChainL1) {
		List<String> customerChainL2 = new ArrayList<>();
		try {
			String[] split = customerChainL1.split(",");
			List<String> customerChainL1List = new ArrayList<String>();
			for (int i = 0; i < split.length; i++) {
				customerChainL1List.add(split[i].trim());
			}
			Query queryToGetCustomeChainL2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1 IN(:customerChainL1) ORDER BY CUSTOMER_CHAIN_L2");
			queryToGetCustomeChainL2.setParameterList("customerChainL1", customerChainL1List);
			customerChainL2 = queryToGetCustomeChainL2.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return customerChainL2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOfferTypes() {
		List<String> offerTypes = new ArrayList<>();
		try {
			Query queryToGetOfferTypes = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE");
			offerTypes = queryToGetOfferTypes.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return offerTypes;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public String getGeography(boolean isCreatePage) {
		String jsonOfGeography = "";
		Gson gson = new Gson();
		try {
			// GeographyBean geographyBean = new GeographyBean();
			// geographyBean.setTitle("ALL INDIA");
			Query queryToGetBranches = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT BRANCH_CODE,BRANCH FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY BRANCH_CODE,BRANCH");
			Iterator branchIterator = queryToGetBranches.list().iterator();
			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				Object[] obj = (Object[]) branchIterator.next();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj[0].toString() + ":" + obj[1].toString());
				listOfBranchBean.add(branchBean);
				Query queryToGetClusters = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH=:branch ORDER BY CLUSTER_CODE,CLUSTER ");
				queryToGetClusters.setString("branch", obj[1].toString());
				Iterator clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<ClusterBean>();
				while (clusterIterator.hasNext()) {
					Object[] clusterObj = (Object[]) clusterIterator.next();
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterObj[0].toString() + ":" + clusterObj[1].toString());
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			// geographyBean.setSubs(listOfBranchBean);
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL INDIA");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			jsonOfGeography = e.toString();
		}
		return jsonOfGeography;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, String> getModality() {
		Map<Integer, String> modality = new TreeMap<Integer, String>();
		try {
			Query queryToGetModality = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT MODALITY_NO,MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE ACTIVE=1 ORDER BY MODALITY_NO");
			Iterator iterator = queryToGetModality.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				modality.put(Integer.valueOf(obj[0].toString()), obj[1].toString());
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return modality;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearAndMoc(boolean isCreatePage) {
		Map<String, Object> map = new HashMap<>();
		Gson gson = new Gson();
		try {
			Query queryToGetYears = sessionFactory.getCurrentSession().createNativeQuery(
					"select distinct MOC_YEAR from TBL_VAT_MOC_MASTER where MOC_YEAR >= (SELECT MOC_YEAR from TBL_VAT_MOC_MASTER where status ='Y' AND MOC_GROUP ='GROUP_ONE') AND MOC_GROUP ='GROUP_ONE'  ");
			List<String> yearsList = queryToGetYears.list();
			if (yearsList != null) {
				map.put("years", yearsList);
			}
			Query queryToGetQuarter = sessionFactory.getCurrentSession()
					.createNativeQuery("select distinct MOC_QUARTER from TBL_VAT_MOC_MASTER WHERE MOC_GROUP ='GROUP_ONE'");
			List<String> quartersList = queryToGetQuarter.list();
			if (quartersList != null) {
				List<QuarterBean> listOfQuarterBean = new ArrayList<QuarterBean>();
				for (int i = 0; i < quartersList.size(); i++) {
					QuarterBean quarterBean = new QuarterBean();
					quarterBean.setTitle(quartersList.get(i));
					Query queryToGetMoc = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT distinct MOC_NAME FROM TBL_VAT_MOC_MASTER WHERE MOC_QUARTER=:quarter AND MOC_GROUP ='GROUP_ONE' ");
					queryToGetMoc.setString("quarter", quartersList.get(i));
					List<String> mocList = queryToGetMoc.list();
					if (mocList != null) {
						List<MocBean> listOfMocBean = new ArrayList<>();
						for (int j = 0; j < mocList.size(); j++) {
							MocBean mocBean = new MocBean();
							mocBean.setTitle(mocList.get(j));
							listOfMocBean.add(mocBean);
						}
						quarterBean.setSubs(listOfMocBean);
					}
					listOfQuarterBean.add(quarterBean);
				}
				if (isCreatePage == false) {
					QuarterBean bean = new QuarterBean();
					bean.setTitle("FULL YEAR");
					listOfQuarterBean.add(0, bean);
				}
				String mocJson = gson.toJson(listOfQuarterBean);
				map.put("moc", mocJson);
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> getBasepackDetails(String basepack, String userId) {
		Map<String, String> basepackDetailsMap = new HashMap<>();
		try {
			Query queryToGetBasepackDeatails = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT REPLACE ( A.BASEPACK_DESC, '''', ''),A.CATEGORY,A.BRAND FROM TBL_PROCO_PRODUCT_MASTER AS A INNER JOIN TBL_PROCO_TME_MAPPING AS B ON A.CATEGORY=B.CATEGORY AND  A.PRICE_LIST=B.PRICE_LIST WHERE A.BASEPACK=:basepack AND A.ACTIVE=1 AND B.USER_ID=:userId");
			queryToGetBasepackDeatails.setString("basepack", basepack);
			queryToGetBasepackDeatails.setString("userId", userId);
			Iterator iterator = queryToGetBasepackDeatails.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				basepackDetailsMap.put("basepackDesc", obj[0].toString());
				basepackDetailsMap.put("category", obj[1].toString());
				basepackDetailsMap.put("brand", obj[2].toString());
			}

			if (basepackDetailsMap.isEmpty()) {
				basepackDetailsMap.put("basepackDesc", "PLEASE ENTER CORRECT BASEPACK");
				basepackDetailsMap.put("category", "PLEASE ENTER CORRECT BASEPACK");
				basepackDetailsMap.put("brand", "PLEASE ENTER CORRECT BASEPACK");
			}

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return basepackDetailsMap;
	}

	@Override
	public synchronized String createPromotion(CreatePromotionBean[] bean, String userId, String status,
			boolean isCreate, boolean isFromUi) throws Exception {
		String response = null;
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_PROMOTION_MASTER_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			//kiran - bigint to int changes
			//Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();
			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
			for (int i = 0; i < bean.length; i++) {
				query.setString(0, bean[i].getStartDate());
				query.setString(1, bean[i].getEndDate());
				query.setString(2, bean[i].getCustomerChainL1());
				query.setString(3, bean[i].getCustomerChainL2());
				/* If customer chain l2 is empty want to add all the l2 customer based on l1*/
				if(!bean[i].getCustomerChainL1().equals("") && bean[i].getCustomerChainL2().equals("")) {
					String CustomerChainL2Str = "ALL CUSTOMERS";
					/*Query queryToGetCusL2 = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:cuschainone");
					queryToGetCusL2.setString("cuschainone", bean[i].getCustomerChainL1());
					@SuppressWarnings("unchecked")
					List<Object[]> CusChainL2ListStr = queryToGetCusL2.list();
					if(CusChainL2ListStr != null) {
						for (int l = 0; l < CusChainL2ListStr.size(); l++) {
							CustomerChainL2Str += CustomerChainL2Str.equals("") ? "" : ",";
							CustomerChainL2Str += CusChainL2ListStr.get(l)[0].toString();
						}
					}*/
					bean[i].setCustomerChainL2(CustomerChainL2Str);
					query.setString(3, CustomerChainL2Str);
				}
				
				query.setString(4, bean[i].getPromoDesc());
				query.setString(5, bean[i].getBasepack1());
				query.setString(6, bean[i].getRatio1());
				query.setString(7, bean[i].getBasepack2());
				query.setString(8, bean[i].getRatio2());
				query.setString(9, bean[i].getBasepack3());
				query.setString(10, bean[i].getRatio3());
				query.setString(11, bean[i].getBasepack4());
				query.setString(12, bean[i].getRatio4());
				query.setString(13, bean[i].getBasepack5());
				query.setString(14, bean[i].getRatio5());
				query.setString(15, bean[i].getBasepack6());
				query.setString(16, bean[i].getRatio6());
				query.setString(17, bean[i].getChildBasepack1());
				query.setString(18, bean[i].getChildRatio1());
				query.setString(19, bean[i].getChildBasepack2());
				query.setString(20, bean[i].getChildRatio2());
				query.setString(21, bean[i].getChildBasepack3());
				query.setString(22, bean[i].getChildRatio3());
				query.setString(23, bean[i].getChildBasepack4());
				query.setString(24, bean[i].getChildRatio4());
				query.setString(25, bean[i].getChildBasepack5());
				query.setString(26, bean[i].getChildRatio5());
				query.setString(27, bean[i].getBasepack6());
				query.setString(28, bean[i].getChildRatio6());
				query.setString(29, bean[i].getThirdPartyDesc());
				query.setString(30, bean[i].getThirdPartyRatio());
				query.setString(31, bean[i].getOfferType());
				query.setString(32, bean[i].getModality());
				query.setString(33, bean[i].getOfferValue());
				query.setString(34, bean[i].getKittingValue());
				
				
				/*******/
				String geographyRaw = bean[i].getGeography();
				String ClusterString = "";
				if(!geographyRaw.equalsIgnoreCase("ALL INDIA")) {
				String[] geographyList = geographyRaw.split(",");
					for(int z = 0; z < geographyList.length; z++) {
						String geography =  geographyList[z];
						if (geography.toUpperCase().startsWith("B")) {
							String branch = "";
							if(geography.indexOf(':') != -1) {
								branch = geography.substring(0, geography.indexOf(':'));
							} else {
								branch = geography;
							}
							Query queryToGetCluster = sessionFactory.getCurrentSession().createNativeQuery(
									"SELECT DISTINCT CLUSTER_CODE, CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branch");
							queryToGetCluster.setString("branch", branch);
							@SuppressWarnings("unchecked")
							List<Object[]> clusterListStr = queryToGetCluster.list();
							if(clusterListStr != null) {
								for (int p = 0; p < clusterListStr.size(); p++) {
									ClusterString += ClusterString.equals("") ? "" : ",";
									ClusterString += clusterListStr.get(p)[0].toString() + ":" + clusterListStr.get(p)[1].toString();
								}
							}
						} else if(geography.toUpperCase().startsWith("C")){
							String cluster = "";
							if(geography.indexOf(':') != -1) {
								cluster = geography.substring(0, geography.indexOf(':'));
							} else {
								cluster = geography;
							}
							Query queryToGetCluster = sessionFactory.getCurrentSession().createNativeQuery(
									"SELECT DISTINCT CLUSTER_CODE, CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER WHERE CLUSTER_CODE=:cluster");
							queryToGetCluster.setString("cluster", cluster);
							@SuppressWarnings("unchecked")
							List<Object[]> clusterListStr = queryToGetCluster.list();
							if(clusterListStr != null) {
								for (int p = 0; p < clusterListStr.size(); p++) {
									ClusterString += ClusterString.equals("") ? "" : ",";
									ClusterString += clusterListStr.get(p)[0].toString() + ":" + clusterListStr.get(p)[1].toString();
								}
							}
						}
					}
				} else {
					Query queryToGetCluster = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT CLUSTER_CODE, CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER" );
					@SuppressWarnings("unchecked")
					List<Object[]> clusterListStr = queryToGetCluster.list();
					if(clusterListStr != null) {
						for (int p = 0; p < clusterListStr.size(); p++) {
							ClusterString += ClusterString.equals("") ? "" : ",";
							ClusterString += clusterListStr.get(p)[0].toString() + ":" + clusterListStr.get(p)[1].toString();
						}
					}
				}
				
				bean[i].setGeography(ClusterString);
				query.setString(35, ClusterString);
				
				/*******/
				
				query.setString(36, bean[i].getUom());
				query.setString(37, userId);
				query.setInteger(38, i);
				query.setString(39, bean[i].getUniqueId());
				query.setString(40, bean[i].getReason());
				query.setString(41, bean[i].getRemark());
				if (!isCreate) {
					query.setString(42, bean[i].getChangesMade());
				} else {
					query.setString(42, "");
				}
				query.setString(43, bean[i].getMoc());
				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateRecord(bean[i], userId, i, isCreate, isFromUi);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}
			if (!responseList.contains("ERROR_FILE")) {
				boolean savePromotionToMainTable = savePromotionToMainTable(bean, userId, status, isFromUi, isCreate);
				if (!savePromotionToMainTable) {
					response = "ERROR";
				} else {
					response = "SUCCESS_FILE";
				}
			} else {
				response = "ERROR_FILE";
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return response;
	}

	public synchronized String validateRecord(CreatePromotionBean bean, String userId, int row, boolean isCreate,
			boolean isFromUi) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		boolean atleastOneParentBasepackEntered = false;
		boolean atleastOneChildBasepackEntered = false;
		int differenceInDays = 0;
		try {
			if (!isFromUi) {
				boolean isStartEndDateValid = validateStartEndDate(bean, userId, row);
				if (!isStartEndDateValid) {
					res = "ERROR_FILE";
				}
			}
			if (!isFromUi) {
				String mocMonthYearForProco = CommonUtils.getMocMonthYearForProco(bean.getStartDate(),
						bean.getEndDate());
				differenceInDays = getDifferenceInDays(mocMonthYearForProco);
			} else {
				differenceInDays = getDifferenceInDays(bean.getMoc());
			}

			if (differenceInDays <= 60) {
				if (StringUtils.isEmpty(bean.getReason())) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Please enter reason.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				} else {
					Query queryToCheckReason = sessionFactory.getCurrentSession()
							.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_EDIT_REASON WHERE REASON=:reason");
					queryToCheckReason.setString("reason", bean.getReason().toUpperCase());
					//kiran - bigint to int changes
					//Integer reasonCount = (Integer) queryToCheckReason.uniqueResult();
					Integer reasonCount = ((BigInteger)queryToCheckReason.uniqueResult()).intValue();
					if (reasonCount != null && reasonCount.intValue() == 0) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "Invalid reason.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					} else {
						if (bean.getReason().equalsIgnoreCase("OTHERS") && StringUtils.isEmpty(bean.getRemark())) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Remark cannot be blank for entered Reason.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						} else if (!bean.getReason().equalsIgnoreCase("OTHERS")
								&& StringUtils.isNotEmpty(bean.getRemark())) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Remark should be blank for entered Reason.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}
				}
			}

			if (StringUtils.isEmpty(bean.getOfferType())) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter Offer Type.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			} else {
				Query queryToOfferType = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_OFFER_TYPE WHERE OFFER_TYPE=:offerType");
				queryToOfferType.setString("offerType", bean.getOfferType().toUpperCase());
				//kiran - big int to int changes
				//Integer offerTypeCount = (Integer) queryToOfferType.uniqueResult();
				Integer offerTypeCount = ((BigInteger)queryToOfferType.uniqueResult()).intValue();
				if (offerTypeCount != null && offerTypeCount.intValue() == 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid Offer Type.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			}
			if (bean.getModality().matches("\\d+")) {
				Query queryToCheckModality = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_MODALITY_MASTER WHERE MODALITY_NO=:modality");
				queryToCheckModality.setString("modality", bean.getModality());
				//kiran - bigint to int changes
				//Integer modalityCount = (Integer) queryToCheckModality.uniqueResult();
				Integer modalityCount = ((BigInteger)queryToCheckModality.uniqueResult()).intValue();
				if (modalityCount != null && modalityCount.intValue() == 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid Modality.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				} else {
					if (bean.getModality().equals("1") || bean.getModality().equals("2")
							|| bean.getModality().equals("9") || bean.getModality().equals("10")) {
						if (bean.getOfferType().equalsIgnoreCase("GTCP")) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Invalid modality for GTCP offer type.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}
					if (bean.getModality().equals("1") || bean.getModality().equals("4")
							|| bean.getModality().equals("9")) {
						if (!bean.getOfferValue().equals("")) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Offer value should be blank for selected modality.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}
					// if (bean.getModality().equals("3") || bean.getModality().equals("5") ||
					// bean.getModality().equals("7") || bean.getModality().equals("8")) {
					if (bean.getModality().equals("7") || bean.getModality().equals("8")) {
						boolean isChildPackBlank = checkForBlankChildPack(bean);
						if (isChildPackBlank == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Cannot enter Child Basepack for selected modality.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}

					// if(bean.getModality().equals("1")|| bean.getModality().equals("2")||
					// bean.getModality().equals("9")|| bean.getModality().equals("10")
					if (bean.getModality().equals("2") //|| bean.getModality().equals("3")|| bean.getModality().equals("5") 
							|| bean.getModality().equals("6")
							|| bean.getModality().equals("7") || bean.getModality().equals("8")
							|| bean.getModality().equals("10")) {
						if (bean.getOfferValue().equals("")) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Offer value should not be blank for selected modality.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						} else {
							String offerValue = bean.getOfferValue();

							if (offerValue.toUpperCase().endsWith("ABS") || offerValue.toUpperCase().endsWith("%")) {
								// logger.info("Offer value should be ends with ABS or % .^");
							} else {
								res = "ERROR_FILE";
								errorMsg = errorMsg + "Offer value should be ends with ABS or % .^";
								updateErrorMessageInTemp(errorMsg, userId, row);
							}

							String numericValue = offerValue.replaceAll("\\D", "");
							// String complexString = "3ifhuq023hjk@jka$ksoap";
							// boolean isOfferValueValid =
							// bean.getOfferValue().matches("^[1-9]\\d*(\\.\\d+)?$");
							boolean isOfferValueValid = numericValue.matches("^[0-9]\\d*(\\.\\d+)?$");
							if (isOfferValueValid == false) {
								res = "ERROR_FILE";
								errorMsg = errorMsg + "Offer value should be numeric .^";
								updateErrorMessageInTemp(errorMsg, userId, row);
							}
						}
					}

					if (bean.getModality().equals("1") || bean.getModality().equals("2")
							//|| bean.getModality().equals("3") || bean.getModality().equals("5")
							|| bean.getModality().equals("9") || bean.getModality().equals("10")) {
						atleastOneChildBasepackEntered = validateChildBasepack(bean, userId, row);
						if (atleastOneChildBasepackEntered == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Please enter atleast one Child Basepack.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}

					if (bean.getModality().equals("4") || bean.getModality().equals("6")
							|| bean.getModality().equals("9") || bean.getModality().equals("10")) {
						atleastOneChildBasepackEntered = validateChildBasepack(bean, userId, row);
						if (atleastOneChildBasepackEntered == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Please enter atleast one Child Basepack.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					} else if (bean.getModality().equals("11")) { // stickered
																	// stock
						atleastOneChildBasepackEntered = validateChildBasepack(bean, userId, row);
						if (bean.getOfferValue().equals("") && atleastOneChildBasepackEntered == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Please enter either offer value (or) child pack.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						} else {
							if (!bean.getOfferValue().equals("")) {
								boolean isOfferValueValid = bean.getOfferValue().matches("^[1-9]\\d*(\\.\\d+)?$");
								if (isOfferValueValid == false) {
									res = "ERROR_FILE";
									errorMsg = errorMsg + "Offer value should be numeric.^";
									updateErrorMessageInTemp(errorMsg, userId, row);
								}
							}
						}
					} else if (bean.getModality().equals("1") || bean.getModality().equals("2")) { // MT
																									// Kitting
						atleastOneChildBasepackEntered = validateChildBasepack(bean, userId, row);
						if (bean.getKittingValue().equals("") && atleastOneChildBasepackEntered == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Please enter either Kitting value (or) child pack.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						} else {
							if (!bean.getKittingValue().equals("")) {
								boolean isKittingValueValid = bean.getKittingValue().matches("^[1-9]\\d*(\\.\\d+)?$");
								if (isKittingValueValid == false) {
									res = "ERROR_FILE";
									errorMsg = errorMsg + "Kitting value should be numeric.^";
									updateErrorMessageInTemp(errorMsg, userId, row);
								}
							}
						}
					}
				}
			} else {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Modality.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
			if (!bean.getBasepack1().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack1(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio1());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P1 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P1 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else if (!bean.getBasepack2().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack2(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio2());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P2 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P2 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else if (!bean.getBasepack3().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack3(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio3());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P3 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P3 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else if (!bean.getBasepack4().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack4(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio4());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P4 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P4 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else if (!bean.getBasepack5().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack5(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio5());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P5 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P5 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else if (!bean.getBasepack6().equals("")) {
				atleastOneParentBasepackEntered = true;
				boolean isBasepackValid = validateBasepack(bean.getBasepack6(), userId);
				if (isBasepackValid) {
					boolean isRatioValid = validateRatio(bean.getRatio6());
					if (!isRatioValid) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "P6 Pack Ratio should be integer.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				} else {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Invalid P6 Basepack.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			}
			if (atleastOneParentBasepackEntered == false) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter atleast one Parent Basepack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
			if (!bean.getUom().equalsIgnoreCase("Singles") && !bean.getUom().equalsIgnoreCase("Combies")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter UOM in(Singles/Combies).^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

			/*
			 * if (bean.getPromoDesc().equals("")) { res = "ERROR_FILE"; errorMsg = errorMsg
			 * + "Please enter Offer description.^"; updateErrorMessageInTemp(errorMsg,
			 * userId, row); }
			 */

			if (!bean.getGeography().equals("")) {
				if (!bean.getGeography().contains("ALL INDIA")) {
					if (!bean.getGeography().equalsIgnoreCase("ALL INDIA")) {
						boolean validateGeography = validateGeography(bean.getGeography());
						if (validateGeography == false) {
							res = "ERROR_FILE";
							errorMsg = errorMsg + "Invalid Geography.^";
							updateErrorMessageInTemp(errorMsg, userId, row);
						}
					}
				} else {
					bean.setGeography("ALL INDIA");
				}
			} else {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter Geography.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

			if (!bean.getCustomerChainL1().equals("")) {
				if (!bean.getCustomerChainL1().equalsIgnoreCase("ALL CUSTOMERS")) {
					boolean isCustomerChainL1Valid = validateCustomerChainL1(bean.getCustomerChainL1());
					if (isCustomerChainL1Valid == false) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "Invalid Customer Chain L1.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				}
			} else {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter Customer Chain L1.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

			if (!bean.getCustomerChainL2().equals("")) {
				if (!bean.getCustomerChainL2().equalsIgnoreCase("ALL CUSTOMERS")) {
					boolean isCustomerChainL2Valid = validateCustomerChainL2(bean.getCustomerChainL2());
					if (isCustomerChainL2Valid == false) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "Invalid Customer Chain L2.^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				}
			} else {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter Customer Chain L2.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

			if (!isFromUi && bean.getPromoDesc().equals("")) {
				String desc = "";
				String parentBasepackval = bean.getBasepack1().concat(bean.getBasepack2()).concat(bean.getBasepack3())
						.concat(bean.getBasepack4()).concat(bean.getBasepack5()).concat(bean.getBasepack6());
				String parentRatio = bean.getRatio1().concat(bean.getRatio2()).concat(bean.getRatio3())
						.concat(bean.getRatio4()).concat(bean.getRatio5()).concat(bean.getRatio6());

				String childBasepackval = bean.getChildBasepack1().concat(bean.getChildBasepack2())
						.concat(bean.getChildBasepack3()).concat(bean.getChildBasepack4())
						.concat(bean.getChildBasepack5()).concat(bean.getChildBasepack6());
				String childRatio = bean.getChildRatio1().concat(bean.getChildRatio2()).concat(bean.getChildRatio3())
						.concat(bean.getChildRatio4()).concat(bean.getChildRatio5()).concat(bean.getChildRatio6());

				String basepackDesc = "";

				if (parentBasepackval.trim().length() == 5) {
					basepackDesc = getBaspackDesc(parentBasepackval);
				}

				String childBasepackDesc = "";

				if (childBasepackval.trim().length() == 5) {
					childBasepackDesc = getBaspackDesc(childBasepackval);
				}

				if (parentBasepackval.trim().length() == 5) {
					String offerValue = bean.getOfferValue();
					String numericValue = offerValue.replaceAll("\\D", "");
					if (bean.getModality().equals("3") || bean.getModality().equals("7")
							|| bean.getModality().equals("8") || bean.getModality().equals("5")
							|| bean.getModality().equals("11")) {
						if (offerValue.equals("")) {
							desc = "BUY " + parentRatio + " " + basepackDesc.trim() + " AND GET " + childRatio + " "
									+ childBasepackDesc + " FREE";
						} else if (offerValue.toUpperCase().endsWith("ABS")) {
							desc = "BUY " + parentRatio + " " + basepackDesc.trim() + " AND GET RS " + numericValue
									+ " OFF";
						} else {
							desc = "BUY " + parentRatio + " " + basepackDesc.trim() + " AND GET " + numericValue
									+ "% OFF";
						}
					} else if (bean.getModality().equals("1") || bean.getModality().equals("2")
							|| bean.getModality().equals("9") || bean.getModality().equals("10")) {
						if (bean.getKittingValue().equals("") || bean.getKittingValue().equals("0")) {
							desc = "BUY " + parentRatio + " " + basepackDesc + " AND GET " + childRatio + " "
									+ childBasepackDesc + " FREE";
						} else {
							if (offerValue.toUpperCase().endsWith("ABS")) {
								desc = "BUY " + parentRatio + " " + basepackDesc.trim() + " AND GET " + childRatio + " "
										+ childBasepackDesc.trim() + " FREE WITH RS " + bean.getKittingValue() + " OFF";
							} else {
								desc = "BUY " + parentRatio + " " + basepackDesc.trim() + " AND GET " + childRatio + " "
										+ childBasepackDesc.trim() + " FREE WITH " + bean.getKittingValue() + "% OFF";
							}
						}
					}

				} else {
					String offerValue = bean.getOfferValue();
					String numericValue = offerValue.replaceAll("\\D", "");
					String bpDesc1 = getBaspackDesc(bean.getBasepack1());
					String bpDesc2 = getBaspackDesc(bean.getBasepack2());
					String bpDesc3 = getBaspackDesc(bean.getBasepack3());
					String bpDesc4 = getBaspackDesc(bean.getBasepack4());
					String bpDesc5 = getBaspackDesc(bean.getBasepack5());
					String bpDesc6 = getBaspackDesc(bean.getBasepack6());
					String cpDesc1 = getBaspackDesc(bean.getChildBasepack1());
					String cpDesc2 = getBaspackDesc(bean.getChildBasepack2());
					String cpDesc3 = getBaspackDesc(bean.getChildBasepack3());
					String cpDesc4 = getBaspackDesc(bean.getChildBasepack4());
					String cpDesc5 = getBaspackDesc(bean.getChildBasepack5());
					String cpDesc6 = getBaspackDesc(bean.getChildBasepack6());
					if (bean.getModality().equals("3") || bean.getModality().equals("7")
							|| bean.getModality().equals("8") || bean.getModality().equals("5")
							|| bean.getModality().equals("11")) {
						if (offerValue.toUpperCase().endsWith("ABS")) {
							if (bean.getBasepack1().length() > 0) {
								desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim();
							}
							if (bean.getBasepack2().length() > 0) {
								desc += " ," + bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim();
							}
							if (bean.getBasepack3().length() > 0) {
								desc += " ," + bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim();
							}
							if (bean.getBasepack4().length() > 0) {
								desc += " ," + bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim();
							}
							if (bean.getBasepack5().length() > 0) {
								desc += " ," + bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim();
							}
							if (bean.getBasepack6().length() > 0) {
								desc += " ," + bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
							}
							desc += " AND GET RS " + numericValue + " OFF";
							// Remove commas
						} else {
							// desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim() +
							// ","+bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim() +
							// ","+bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim()+
							// ","+bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim()+
							// ","+bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim()+
							// ","+bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
							if (bean.getBasepack1().length() > 0) {
								desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim();
							}
							if (bean.getBasepack2().length() > 0) {
								desc += " ," + bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim();
							}
							if (bean.getBasepack3().length() > 0) {
								desc += " ," + bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim();
							}
							if (bean.getBasepack4().length() > 0) {
								desc += " ," + bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim();
							}
							if (bean.getBasepack5().length() > 0) {
								desc += " ," + bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim();
							}
							if (bean.getBasepack6().length() > 0) {
								desc += " ," + bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
							}
							desc += " AND GET " + numericValue + " % OFF";
						}
					} else if (bean.getModality().equals("1") || bean.getModality().equals("2")
							|| bean.getModality().equals("9") || bean.getModality().equals("10")) {
						if (bean.getKittingValue().equals("") || bean.getKittingValue().equals("0")) {

							if (bean.getBasepack1().length() > 0) {
								desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim();
							}
							if (bean.getBasepack2().length() > 0) {
								desc += " ," + bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim();
							}
							if (bean.getBasepack3().length() > 0) {
								desc += " ," + bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim();
							}
							if (bean.getBasepack4().length() > 0) {
								desc += " ," + bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim();
							}
							if (bean.getBasepack5().length() > 0) {
								desc += " ," + bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim();
							}
							if (bean.getBasepack6().length() > 0) {
								desc += " ," + bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
							}
							// desc += " AND GET " + bean.getChildRatio1() + cpDesc1 + " FREE";
							// Remove commas
							desc += " AND GET ";
							if (bean.getChildBasepack1().length() > 0) {
								desc += " " + bean.getChildRatio1().trim() + " " + cpDesc1.trim();
							}
							if (bean.getChildBasepack2().length() > 0) {
								desc += " ," + bean.getChildRatio2().trim() + " " + cpDesc2.trim();
							}
							if (bean.getChildBasepack3().length() > 0) {
								desc += " ," + bean.getChildRatio3().trim() + " " + cpDesc3.trim();
							}
							if (bean.getChildBasepack4().length() > 0) {
								desc += " ," + bean.getChildRatio4().trim() + " " + cpDesc4.trim();
							}
							if (bean.getChildBasepack5().length() > 0) {
								desc += " ," + bean.getChildRatio5().trim() + " " + cpDesc5.trim();
							}
							if (bean.getChildBasepack6().length() > 0) {
								desc += " ," + bean.getChildRatio6().trim() + " " + cpDesc6.trim();
							}
							desc += " FREE";
						} else {
							if (offerValue.toUpperCase().endsWith("ABS")) {

								if (bean.getBasepack1().length() > 0) {
									desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim();
								}
								if (bean.getBasepack2().length() > 0) {
									desc += " ," + bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim();
								}
								if (bean.getBasepack3().length() > 0) {
									desc += " ," + bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim();
								}
								if (bean.getBasepack4().length() > 0) {
									desc += " ," + bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim();
								}
								if (bean.getBasepack5().length() > 0) {
									desc += " ," + bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim();
								}
								if (bean.getBasepack6().length() > 0) {
									desc += " ," + bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
								}
								// desc += " AND GET " + bean.getChildRatio1() + cpDesc1 + " FREE WITH RS
								// "+bean.getKittingValue() + " OFF";
								// Remove commas
								desc += " AND GET";
								if (bean.getChildBasepack1().length() > 0) {
									desc += "" + bean.getChildRatio1().trim() + " " + cpDesc1.trim();
								}
								if (bean.getChildBasepack2().length() > 0) {
									desc += " ," + bean.getChildRatio2().trim() + " " + cpDesc2.trim();
								}
								if (bean.getChildBasepack3().length() > 0) {
									desc += " ," + bean.getChildRatio3().trim() + " " + cpDesc3.trim();
								}
								if (bean.getChildBasepack4().length() > 0) {
									desc += " ," + bean.getChildRatio4().trim() + " " + cpDesc4.trim();
								}
								if (bean.getChildBasepack5().length() > 0) {
									desc += " ," + bean.getChildRatio5().trim() + " " + cpDesc5.trim();
								}
								if (bean.getChildBasepack6().length() > 0) {
									desc += " ," + bean.getChildRatio6().trim() + " " + cpDesc6.trim();
								}
								desc += " FREE WITH RS " + bean.getKittingValue() + " OFF";

							} else {

								if (bean.getBasepack1().length() > 0) {
									desc = "BUY " + bean.getRatio1().trim() + " UNIT OF " + bpDesc1.trim();
								}
								if (bean.getBasepack2().length() > 0) {
									desc += " ," + bean.getRatio2().trim() + " UNIT OF " + bpDesc2.trim();
								}
								if (bean.getBasepack3().length() > 0) {
									desc += " ," + bean.getRatio3().trim() + " UNIT OF " + bpDesc3.trim();
								}
								if (bean.getBasepack4().length() > 0) {
									desc += " ," + bean.getRatio4().trim() + " UNIT OF " + bpDesc4.trim();
								}
								if (bean.getBasepack5().length() > 0) {
									desc += " ," + bean.getRatio5().trim() + " UNIT OF " + bpDesc5.trim();
								}
								if (bean.getBasepack6().length() > 0) {
									desc += " ," + bean.getRatio6().trim() + " UNIT OF " + bpDesc6.trim();
								}
								desc += " AND GET";
								if (bean.getChildBasepack1().length() > 0) {
									desc += "" + bean.getChildRatio1().trim() + " " + cpDesc1.trim();
								}
								if (bean.getChildBasepack2().length() > 0) {
									desc += " ," + bean.getChildRatio2().trim() + " " + cpDesc2.trim();
								}
								if (bean.getChildBasepack3().length() > 0) {
									desc += " ," + bean.getChildRatio3().trim() + " " + cpDesc3.trim();
								}
								if (bean.getChildBasepack4().length() > 0) {
									desc += " ," + bean.getChildRatio4().trim() + " " + cpDesc4.trim();
								}
								if (bean.getChildBasepack5().length() > 0) {
									desc += " ," + bean.getChildRatio5().trim() + " " + cpDesc5.trim();
								}
								if (bean.getChildBasepack6().length() > 0) {
									desc += " ," + bean.getChildRatio6().trim() + " " + cpDesc6.trim();
								}
								// desc += " AND GET " + bean.getChildRatio1() + cpDesc1 ;
								// Remove commas
								desc += " FREE WITH " + bean.getKittingValue() + " %OFF";

							}
						}

					}
				}

				updateDescInTemp(desc, userId, row);
				bean.setPromoDesc(desc);
			}
		} catch (

		Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public String getBaspackDesc(String bacepack) {
		Query query = sessionFactory.getCurrentSession().createNativeQuery(
				"SELECT CASE WHEN BASEPACK_DESC LIKE '%:%' THEN SUBSTR(BASEPACK_DESC, 9) ELSE BASEPACK_DESC END FROM MODTRD.TBL_VAT_PRODUCT_MASTER WHERE STATUS = 'Y' and BASEPACK=:bacepack");
		query.setString("bacepack", bacepack);
		List<String> list = query.list();
		return ((list != null && list.size() > 0) ? list.get(0) : "");
	}

	public int updateErrorMessageInTemp(String errorMsg, String userId, int row) {
		try {
			String qry = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}

	public int updateDescInTemp(String desc, String userId, int row) {
		try {
			String qry = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP SET OFFER_DESC=:desc WHERE USER_ID=:userId AND ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("desc", desc);
			query.setString("userId", userId);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_l2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO, P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO, P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO, C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO, OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,REASON,REMARK,ERROR_MSG FROM TBL_PROCO_PROMOTION_MASTER_TEMP WHERE USER_ID=:userId";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter("userId", userId);
			Iterator itr = query.list().iterator();
			downloadDataList.add(headerList);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}

	private synchronized boolean validateBasepack(String basepack, String userId) {
		boolean res = false;
		try {
			Query queryToCheckBasepack = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_PRODUCT_MASTER AS A INNER JOIN TBL_PROCO_TME_MAPPING AS B ON A.CATEGORY = B.CATEGORY AND A.PRICE_LIST = B.PRICE_LIST WHERE A.BASEPACK=:basepack AND B.USER_ID=:userId");
			queryToCheckBasepack.setString("basepack", basepack);
			queryToCheckBasepack.setString("userId", userId);
			//kiran - bigint to int changes
			//Integer basepackCount = (Integer) queryToCheckBasepack.uniqueResult();
			Integer basepackCount = ((BigInteger)queryToCheckBasepack.uniqueResult()).intValue();
			if (basepackCount != null && basepackCount.intValue() > 0) {
				res = true;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private synchronized boolean validateBasepack(String basepack) {
		boolean res = false;
		try {
			Query queryToCheckBasepack = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_PRODUCT_MASTER WHERE BASEPACK=:basepack");
			queryToCheckBasepack.setString("basepack", basepack);
			//kiran - bigint to int changes
			//Integer basepackCount = (Integer) queryToCheckBasepack.uniqueResult();
			Integer basepackCount = ((BigInteger)queryToCheckBasepack.uniqueResult()).intValue();
			if (basepackCount != null && basepackCount.intValue() > 0) {
				res = true;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private synchronized boolean validateGeography(String geography) {
		boolean res = false;
		try {
			String[] geographyArray = geography.split(",");
			boolean[] resArray = new boolean[geographyArray.length];
			for (int i = 0; i < geographyArray.length; i++) {
				String geo = geographyArray[i].trim();
				if (geo.startsWith("B")) {
					String branch = "";
					if (geo.contains(":")) {
						branch = geo.substring(0, geo.indexOf(':'));
					} else {
						branch = geo;
					}
					Query queryToCheckBranch = sessionFactory.getCurrentSession()
							.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branch");
					queryToCheckBranch.setString("branch", branch);
					//kiran - bigint to int changes
					//Integer branchCount = (Integer) queryToCheckBranch.uniqueResult();
					Integer branchCount = ((BigInteger)queryToCheckBranch.uniqueResult()).intValue();
					if (branchCount != null && branchCount.intValue() > 0) {
						resArray[i] = true;
						res = true;
					} else {
						resArray[i] = false;
					}
				} else if (geo.startsWith("CL")) {
					String cluster = "";
					if (geo.contains(":")) {
						cluster = geo.substring(0, geo.indexOf(':'));
					} else {
						cluster = geo;
					}
					Query queryToCheckCluster = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_PROCO_CUSTOMER_MASTER WHERE CLUSTER_CODE=:cluster");
					queryToCheckCluster.setString("cluster", cluster);
					//kiran - big int to int changes
					//Integer clusterCount = (Integer) queryToCheckCluster.uniqueResult();
					Integer clusterCount = ((BigInteger)queryToCheckCluster.uniqueResult()).intValue();
					if (clusterCount != null && clusterCount.intValue() > 0) {
						resArray[i] = true;
						res = true;
					} else {
						resArray[i] = false;
					}
				} /*
					 * else if (geo.startsWith("ST")) { String state = ""; if (geo.contains(":")) {
					 * state = geo.substring(0, geo.indexOf(':')); } else { state = geo; } Query
					 * queryToCheckState = sessionFactory.getCurrentSession()
					 * .createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_CUSTOMER_MASTER WHERE STATE_CODE=:state"
					 * ); queryToCheckState.setString("state", state); Integer stateCount =
					 * (Integer) queryToCheckState.uniqueResult(); if (stateCount != null &&
					 * stateCount.intValue() > 0) { resArray[i] = true; res = true; } else {
					 * resArray[i] = false; } }
					 */
			}
			for (boolean b : resArray) {
				if (!b) {
					res = false;
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	/*
	 * private synchronized boolean validateMoc(CreatePromotionBean bean, String
	 * userId, int row) { int yearInt = Calendar.getInstance().get(Calendar.YEAR);
	 * int month = Calendar.getInstance().get(Calendar.MONTH) + 1; boolean res =
	 * true; String errorMsg = ""; try { if (Integer.parseInt(bean.getYear()) ==
	 * yearInt) { String moc = bean.getMoc(); if (!moc.startsWith("Q")) { String
	 * mocNo = moc.substring(3); if (Integer.parseInt(mocNo) <= month) { errorMsg =
	 * errorMsg + "Invalid MOC.^"; res = false; updateErrorMessageInTemp(errorMsg,
	 * userId, row); } } } String moc = ""; if (bean.getMoc().startsWith("Q")) { if
	 * (bean.getMoc().equalsIgnoreCase("Q1")) { moc = "MOC3"; } else if
	 * (bean.getMoc().equalsIgnoreCase("Q2")) { moc = "MOC6"; } else if
	 * (bean.getMoc().equalsIgnoreCase("Q3")) { moc = "MOC9"; } else if
	 * (bean.getMoc().equalsIgnoreCase("Q4")) { moc = "MOC12"; }
	 * 
	 * } else { moc = bean.getMoc(); } int mocNo =
	 * Integer.parseInt(moc.substring(3)); String mon = ""; if (mocNo < 10) { mon =
	 * "0" + mocNo; } else { mon = mon + mocNo; } int day =
	 * Calendar.getInstance().get(Calendar.DAY_OF_MONTH); LocalDateTime mocSelected
	 * = LocalDateTime .parse(Integer.parseInt(bean.getYear()) + "-" + mon + "-" +
	 * day + "T00:00:00"); LocalDateTime now = LocalDateTime.now(); Duration
	 * duration = Duration.between(now, mocSelected); long diff =
	 * Math.abs(duration.toDays()); // System.out.println(diff); if (diff > 90) {
	 * res = false; errorMsg = errorMsg +
	 * "Promotion can be created only for 90 days from current date.^";
	 * updateErrorMessageInTemp(errorMsg, userId, row); } } catch (Exception e) {
	 * logger.debug("Exception", e); return false; } return res; }
	 */

	private synchronized boolean validateRatio(String ratio) {
		boolean res = false;
		try {
			res = ratio.matches("\\d+");
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private synchronized boolean validateCustomerChainL1(String customerChainL1) {
		boolean res = false;
		try {
			String[] customerChainL1Split = customerChainL1.split(",");
			boolean[] resArray = new boolean[customerChainL1Split.length];
			Query queryToCheckCustomerChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1");
			for (int i = 0; i < customerChainL1Split.length; i++) {
				queryToCheckCustomerChainL1.setString("customerChainL1", customerChainL1Split[i].trim());
				//kiran - bigint to int changes
				//Integer customerChainL1Count = (Integer) queryToCheckCustomerChainL1.uniqueResult();
				Integer customerChainL1Count = ((BigInteger)queryToCheckCustomerChainL1.uniqueResult()).intValue();
				if (customerChainL1Count != null && customerChainL1Count.intValue() > 0) {
					resArray[i] = true;
					res = true;
				} else {
					resArray[i] = false;
				}
			}
			for (boolean b : resArray) {
				if (!b) {
					res = false;
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private synchronized boolean validateCustomerChainL2(String customerChainL2) {
		boolean res = false;
		try {
			String[] customerChainL2Split = customerChainL2.split(",");
			boolean[] resArray = new boolean[customerChainL2Split.length];
			Query queryToCheckCustomerChainL2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L2=:customerChainL2");
			for (int i = 0; i < customerChainL2Split.length; i++) {
				queryToCheckCustomerChainL2.setString("customerChainL2", customerChainL2Split[i].trim());
				//kiran - bigint to int changes
				//Integer customerChainL2Count = (Integer) queryToCheckCustomerChainL2.uniqueResult();
				Integer customerChainL2Count = ((BigInteger)queryToCheckCustomerChainL2.uniqueResult()).intValue();
				if (customerChainL2Count != null && customerChainL2Count.intValue() > 0) {
					resArray[i] = true;
					res = true;
				} else {
					resArray[i] = false;
				}
			}
			for (boolean b : resArray) {
				if (!b) {
					res = false;
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized boolean savePromotionToMainTable(CreatePromotionBean[] bean, String userId, String status,
			boolean isFromUi, boolean isCreate) throws Exception {
		boolean res = true;
		List<Boolean> resList = new ArrayList<Boolean>();
		String promoId = "";
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER);
			Query queryToGetL2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_CHAIN_L2 IN(:customerChainL2)");
			Query queryToGetAllL2ForL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1");

			//Garima - chanegs for VARCHAR_FORMAT
			//"SELECT DISTINCT VARCHAR_FORMAT(START_DATE,'MM/DD/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			Query queryToStartDate = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT DATE_FORMAT(START_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			//Garima - changes for VARCHAR_FORMAT
			//"SELECT DISTINCT VARCHAR_FORMAT(END_DATE,'MM/DD/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			Query queryToEndDate = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT DATE_FORMAT(END_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			
			//Garima - changes for VARCHAR_FORMAT
			//"SELECT DISTINCT VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			Query queryToStartDateUi = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT DATE_FORMAT(START_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			//Garima  - changes for VARCHAR_FORMAT
			//"SELECT DISTINCT VARCHAR_FORMAT(END_DATE,'DD/MM/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			Query queryToEndDateUi = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT DATE_FORMAT(END_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC=:moc AND MOC_GROUP ='GROUP_ONE' ");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			//SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			List<String> changesList = new ArrayList<String>();
			List<String> basePackList = new ArrayList<String>();
			//Commented By Sarin - Change in Cast - 20Nov2020
			//String pidGenerateQuery="select (case when max(pid) is null then '000001' else lpad(varchar((max(cast(pid as integer))+1)),6,0) end) AS " + 
			String pidGenerateQuery="select (case when max(pid) is null then '000001' else LPAD(CAST(MAX(CAST(PID AS UNSIGNED)) + 1 AS CHAR),6,0) END) AS " + 
					" PID FROM TBL_PROCO_PROMOTION_MASTER WHERE MOC=:moc and YEAR=:year";
			for (int i = 0; i < bean.length; i++) {
				boolean offerDropped = false;
				if(!isCreate && !isFromUi) {
				String mocMonth = CommonUtils.getMocMonthYearForProco(bean[i].getStartDate(), bean[i].getEndDate());
				bean[i].setMoc(mocMonth);
				}
				String[] mocSplit = bean[i].getMoc().split(",");
				if(bean[i].getChangesMade()!=null && !bean[i].getChangesMade().isEmpty()) {
				String[] ChangesSplit = bean[i].getChangesMade().split(",");
				changesList = Arrays.asList(ChangesSplit);
				}
				if(changesList.size()!=0) {
				if(changesList.contains("OFFER DROPPED")) {
					offerDropped = true;
				}
				}
				for (int l = 0; l < mocSplit.length; l++) {
					String rawMoc = mocSplit[l].trim();
					
					if(bean[i].getStartDate()==null || bean[i].getStartDate().equals("") || bean[i].getEndDate()==null || bean[i].getEndDate().equals("")) {
						queryToStartDateUi.setString("moc", rawMoc);
						String startDate = (String) queryToStartDateUi.uniqueResult();
						bean[i].setStartDate(String.valueOf(startDate));
						queryToEndDateUi.setString("moc", rawMoc);
						String endDate = (String) queryToEndDateUi.uniqueResult();
						bean[i].setEndDate(String.valueOf(endDate));
					}
					String mocSubstr = "";
					if (rawMoc.startsWith("0")) {
						mocSubstr = "MOC" + rawMoc.substring(1, 2);
					} else {
						mocSubstr = "MOC" + rawMoc.substring(0, 2);
					}
					if (mocSplit.length == 1) {
						java.util.Date startDate = sdf.parse(bean[i].getStartDate());
						String format = sdf2.format(startDate);
						bean[i].setStartDateForDb(format);
						java.util.Date endDate = sdf.parse(bean[i].getEndDate());
						String format2 = sdf2.format(endDate);
						bean[i].setEndDateForDb(format2);
					} else if (mocSplit.length > 1 && l == 0) {
						java.util.Date startDate = sdf.parse(bean[i].getStartDate());
						String format = sdf2.format(startDate);
						bean[i].setStartDateForDb(format);
						queryToEndDate.setString("moc", rawMoc);
						String endDateString = (String) queryToEndDate.uniqueResult();
						bean[i].setEndDateForDb(String.valueOf(endDateString));
					} else if (mocSplit.length > 1 && l > 0 && l != mocSplit.length - 1) {
						queryToStartDate.setString("moc", rawMoc);
						String startDateString = (String) queryToStartDate.uniqueResult();
						bean[i].setStartDateForDb(String.valueOf(startDateString));
						queryToEndDate.setString("moc", rawMoc);
						String endDateString = (String) queryToEndDate.uniqueResult();
						bean[i].setEndDateForDb(String.valueOf(endDateString));
					} else if (mocSplit.length > 1 && l == mocSplit.length - 1) {
						queryToStartDate.setString("moc", rawMoc);
						String startDateString = (String) queryToStartDate.uniqueResult();
						bean[i].setStartDateForDb(String.valueOf(startDateString));
						java.util.Date endDate = sdf.parse(bean[i].getEndDate());
						String format2 = sdf2.format(endDate);
						bean[i].setEndDateForDb(format2);
					}

					bean[i].setYear(rawMoc.substring(2));

					if (!bean[i].getCustomerChainL1().equalsIgnoreCase("ALL CUSTOMERS")) {
						String[] custL1 = bean[i].getCustomerChainL1().split(",");
						for (int j = 0; j < custL1.length; j++) {
							String customerChainL1 = custL1[j].trim();
							String customerChainL2 = "";
							if (!bean[i].getCustomerChainL2().equalsIgnoreCase("ALL CUSTOMERS")) {
								String[] custL2 = bean[i].getCustomerChainL2().split(",");
								ArrayList<String> l2List = new ArrayList<>();
								for (int k = 0; k < custL2.length; k++) {
									l2List.add(custL2[k]);
								}
								queryToGetL2.setString("customerChainL1", customerChainL1);
								queryToGetL2.setParameterList("customerChainL2", l2List);
								List list = queryToGetL2.list();
								for (int k = 0; k < list.size(); k++) {
									if (k < list.size() - 1) {
										customerChainL2 += list.get(k) + ",";
									} else {
										customerChainL2 += list.get(k);
									}
								}
								if(!offerDropped) {
								Query qry = sessionFactory.getCurrentSession().createNativeQuery(pidGenerateQuery);
								qry.setString("moc", mocSubstr);
								qry.setString("year", bean[i].getYear());
								String pid = (String) qry.uniqueResult();
								promoId = "PID_" + bean[i].getYear() + mocSubstr + "_" + pid;
								
								basePackList.clear();
								if(bean[i].getBasepack1()!=null && !(bean[i].getBasepack1().equals(""))) {
									basePackList.add(bean[i].getBasepack1());
								}
								if(bean[i].getBasepack2()!=null && !(bean[i].getBasepack2().equals(""))) {
									basePackList.add(bean[i].getBasepack2());
								}
								if(bean[i].getBasepack3()!=null && !(bean[i].getBasepack3().equals(""))) {
									basePackList.add(bean[i].getBasepack3());
								}
								if(bean[i].getBasepack4()!=null && !(bean[i].getBasepack4().equals(""))) {
									basePackList.add(bean[i].getBasepack4());
								}
								if(bean[i].getBasepack5()!=null && !(bean[i].getBasepack5().equals(""))) {
									basePackList.add(bean[i].getBasepack5());
								}
								
								if(bean[i].getBasepack6()!=null && !(bean[i].getBasepack6().equals(""))) {
									basePackList.add(bean[i].getBasepack6());
								}
								
								for(int z=0 ; z<basePackList.size();z++) {
									bean[i].setBasepack1(basePackList.get(z));
									
									query = getQuery(query, bean[i], userId, promoId, pid, customerChainL1, customerChainL2,
												mocSubstr, status, isCreate);
									query.executeUpdate();
									boolean savePromotionDepotLevel = savePromotionDepotLevel(bean[i], userId, promoId,
											customerChainL1, list);
									if (savePromotionDepotLevel) {
										resList.add(true);
										if (isCreate) {
											saveStatusInStatusTracker(promoId, 1, "", userId, bean[i].getBasepack1());
										} else {
											saveStatusInStatusTracker(promoId, 2, "", userId, bean[i].getBasepack1());
										}
									} else {
										resList.add(false);
									}
								}
								}

							} else {
								queryToGetAllL2ForL1.setString("customerChainL1", customerChainL1);
								List list = queryToGetAllL2ForL1.list();
								for (int k = 0; k < list.size(); k++) {
									if (k < list.size() - 1) {
										customerChainL2 += list.get(k) + ",";
									} else {
										customerChainL2 += list.get(k);
									}
								}
								if(!offerDropped) {
								Query qry = sessionFactory.getCurrentSession().createNativeQuery(pidGenerateQuery);
								qry.setString("moc", mocSubstr);
								qry.setString("year", bean[i].getYear());
								String pid = (String) qry.uniqueResult();
								promoId = "PID_" + bean[i].getYear() + mocSubstr + "_" + pid;
								
								basePackList.clear();
								if(bean[i].getBasepack1()!=null && !(bean[i].getBasepack1().equals(""))) {
									basePackList.add(bean[i].getBasepack1());
								}
								
								if(bean[i].getBasepack2()!=null && !(bean[i].getBasepack2().equals(""))) {
									basePackList.add(bean[i].getBasepack2());
								}
								if(bean[i].getBasepack3()!=null && !(bean[i].getBasepack3().equals(""))) {
									basePackList.add(bean[i].getBasepack3());
								}
								if(bean[i].getBasepack4()!=null && !(bean[i].getBasepack4().equals(""))) {
									basePackList.add(bean[i].getBasepack4());
								}
								if(bean[i].getBasepack5()!=null && !(bean[i].getBasepack5().equals(""))) {
									basePackList.add(bean[i].getBasepack5());
								}
								
								if(bean[i].getBasepack6()!=null && !(bean[i].getBasepack6().equals(""))) {
									basePackList.add(bean[i].getBasepack6());
								}
								
								for(int z=0 ; z<basePackList.size();z++) {
									bean[i].setBasepack1(basePackList.get(z));
									query = getQuery(query, bean[i], userId, promoId, pid, customerChainL1, customerChainL2,
												mocSubstr, status, isCreate);
									query.executeUpdate();
	
									boolean savePromotionDepotLevel = savePromotionDepotLevel(bean[i], userId, promoId,
											customerChainL1, list);
									if (savePromotionDepotLevel) {
										resList.add(true);
										if (isCreate) {
											saveStatusInStatusTracker(promoId, 1, "", userId, bean[i].getBasepack1());
										} else {
											saveStatusInStatusTracker(promoId, 2, "", userId, bean[i].getBasepack1());
										}
									} else {
										resList.add(false);
									}
								}
								}
							}
						}
					} else {
						Query queryToGetAllL1 = sessionFactory.getCurrentSession()
								.createNativeQuery("SELECT DISTINCT CUSTOMER_CHAIN_L1 FROM TBL_PROCO_CUSTOMER_MASTER");
						List<String> custL1List = queryToGetAllL1.list();
						Query queryToGetAllL2 = sessionFactory.getCurrentSession().createNativeQuery(
								"SELECT DISTINCT CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1");
						for (int j = 0; j < custL1List.size(); j++) {
							String custL1 = custL1List.get(j);
							queryToGetAllL2.setString("customerChainL1", custL1);
							List<String> custL2List = queryToGetAllL2.list();
							String customerChainL2 = "";
							for (int k = 0; k < custL2List.size(); k++) {
								if (k < custL2List.size() - 1) {
									customerChainL2 += custL2List.get(k) + ",";
								} else {
									customerChainL2 += custL2List.get(k);
								}
							}
							if(!offerDropped) {
							Query qry = sessionFactory.getCurrentSession().createNativeQuery(pidGenerateQuery);
							qry.setString("moc", mocSubstr);
							qry.setString("year", bean[i].getYear());
							String pid = (String) qry.uniqueResult();
							promoId = "PID_" + bean[i].getYear() + mocSubstr + "_" + pid;

							basePackList.clear();
							if(bean[i].getBasepack1()!=null && !(bean[i].getBasepack1().equals(""))) {
								basePackList.add(bean[i].getBasepack1());
							}
							
							if(bean[i].getBasepack2()!=null && !(bean[i].getBasepack2().equals(""))) {
								basePackList.add(bean[i].getBasepack2());
							}
							if(bean[i].getBasepack3()!=null && !(bean[i].getBasepack3().equals(""))) {
								basePackList.add(bean[i].getBasepack3());
							}
							if(bean[i].getBasepack4()!=null && !(bean[i].getBasepack4().equals(""))) {
								basePackList.add(bean[i].getBasepack4());
							}
							if(bean[i].getBasepack5()!=null && !(bean[i].getBasepack5().equals(""))) {
								basePackList.add(bean[i].getBasepack5());
							}
							
							if(bean[i].getBasepack6()!=null && !(bean[i].getBasepack6().equals(""))) {
								basePackList.add(bean[i].getBasepack6());
							}
							
							for(int z=0 ; z<basePackList.size();z++) {
								bean[i].setBasepack1(basePackList.get(z));	
								query = getQuery(query, bean[i], userId, promoId, pid, custL1, customerChainL2, mocSubstr,
										status, isCreate);
								query.executeUpdate();
	
								boolean savePromotionDepotLevel = savePromotionDepotLevel(bean[i], userId, promoId, custL1,
										custL2List);
								if (savePromotionDepotLevel) {
									resList.add(true);
									if (isCreate) {
										saveStatusInStatusTracker(promoId, 1, "", userId, bean[i].getBasepack1());
									} else {
										saveStatusInStatusTracker(promoId, 2, "", userId, bean[i].getBasepack1());
									}
								} else {
									resList.add(false);
								}
							}
							}
						}
					}
					bean[i].setStartDate("");
					bean[i].setEndDate("");
				}
				if (!isCreate) {
					if(offerDropped) {
						String remark = bean[i].getRemark()== null ?"":bean[i].getRemark();
					Query queryToDeleteFromMasterTable = sessionFactory.getCurrentSession()
							.createNativeQuery("UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=6 , DELETE_REMARKS =:remark WHERE PROMO_ID =:promoId AND ACTIVE=1");
					
					queryToDeleteFromMasterTable.setString("promoId", bean[i].getUniqueId());
					queryToDeleteFromMasterTable.setString("remark", remark);
					queryToDeleteFromMasterTable.executeUpdate();
					}else{
					Query queryToDeleteFromMasterTable = sessionFactory.getCurrentSession()
							.createNativeQuery("UPDATE TBL_PROCO_PROMOTION_MASTER SET ACTIVE=0 WHERE PROMO_ID =:promoId");
					queryToDeleteFromMasterTable.setString("promoId", bean[i].getUniqueId());
					queryToDeleteFromMasterTable.executeUpdate();

					Query queryToDeleteFromDepotTable = sessionFactory.getCurrentSession().createNativeQuery(
							"UPDATE TBL_PROCO_PROMOTION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID =:promoId");
					queryToDeleteFromDepotTable.setString("promoId", bean[i].getUniqueId());
					queryToDeleteFromDepotTable.executeUpdate();

					saveStatusInStatusTracker(bean[i].getUniqueId(), 2, "", userId);
					}
				}
			}

			if (resList.contains(false)) {
				res = false;
				throw new Exception("No depot for selected combination or unhandled error.");
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	public synchronized Query getQuery(Query query, CreatePromotionBean bean, String userId, String promoId, String pid,
			String customerChainL1, String customerChainL2, String moc, String status, boolean isCreate)
			throws Exception {
		try {
			query.setString(0, bean.getYear());
			query.setString(1, moc);
			query.setString(2, customerChainL1);
			query.setString(3, customerChainL2);
			query.setString(4, bean.getPromoDesc());
			query.setString(5, bean.getBasepack1());
			query.setString(6, bean.getRatio1());
			query.setString(7, bean.getBasepack2());
			query.setString(8, bean.getRatio2());
			query.setString(9, bean.getBasepack3());
			query.setString(10, bean.getRatio3());
			query.setString(11, bean.getBasepack4());
			query.setString(12, bean.getRatio4());
			query.setString(13, bean.getBasepack5());
			query.setString(14, bean.getRatio5());
			query.setString(15, bean.getBasepack6());
			query.setString(16, bean.getRatio6());
			query.setString(17, bean.getChildBasepack1());
			query.setString(18, bean.getChildRatio1());
			query.setString(19, bean.getChildBasepack2());
			query.setString(20, bean.getChildRatio2());
			query.setString(21, bean.getChildBasepack3());
			query.setString(22, bean.getChildRatio3());
			query.setString(23, bean.getChildBasepack4());
			query.setString(24, bean.getChildRatio4());
			query.setString(25, bean.getChildBasepack5());
			query.setString(26, bean.getChildRatio5());
			query.setString(27, bean.getChildBasepack6());
			query.setString(28, bean.getChildRatio6());
			query.setString(29, bean.getThirdPartyDesc());
			query.setString(30, bean.getThirdPartyRatio());
			query.setString(31, bean.getOfferType().toUpperCase());
			Query queryToGetModality = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE MODALITY_NO=:modality");
			queryToGetModality.setInteger("modality", Integer.parseInt(bean.getModality()));
			String modality = (String) queryToGetModality.uniqueResult();
			query.setString(32, modality);
			query.setString(33, bean.getOfferValue());
			query.setString(34, bean.getKittingValue());
			String geo = getGeographyWithoutBranch(bean.getGeography());
			query.setString(35, geo.toUpperCase());
			query.setString(36, WordUtils.capitalize(bean.getUom()));
			query.setString(37, userId);
			query.setString(38, promoId);
			query.setString(39, pid);
			// query.setInteger(40, 1);
			query.setInteger(40, 1);
			//kiran - changes from string to date
			query.setDate(41, new SimpleDateFormat("dd/MM/yyyy").parse(bean.getStartDateForDb()));
			query.setDate(42, new SimpleDateFormat("dd/MM/yyyy").parse(bean.getEndDateForDb()));
			if (isCreate) {
				query.setString(43, promoId);
			} else {
				Query queryToGetOriginalId = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT ORIGINAL_ID FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId");
				queryToGetOriginalId.setString("promoId", bean.getUniqueId());
				String originalId = (String) queryToGetOriginalId.uniqueResult();
				query.setString(43, originalId);
			}
			query.setString(44, bean.getReason().toUpperCase());
			query.setString(45, bean.getRemark());
			if (isCreate) {
				query.setString(46, "");
			} else {
				query.setString(46, bean.getChangesMade().toUpperCase());
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return query;
	}

	private synchronized boolean checkForBlankChildPack(CreatePromotionBean bean) {
		boolean res = true;
		try {
			if (!bean.getChildBasepack1().isEmpty() && !bean.getChildRatio1().isEmpty()) {
				res = false;
			}
			if (!bean.getChildBasepack2().isEmpty() && !bean.getChildRatio2().isEmpty()) {
				res = false;
			}
			if (!bean.getChildBasepack3().isEmpty() && !bean.getChildRatio3().isEmpty()) {
				res = false;
			}
			if (!bean.getChildBasepack4().isEmpty() && !bean.getChildRatio4().isEmpty()) {
				res = false;
			}
			if (!bean.getChildBasepack5().isEmpty() && !bean.getChildRatio5().isEmpty()) {
				res = false;
			}
			if (!bean.getChildBasepack6().isEmpty() && !bean.getChildRatio6().isEmpty()) {
				res = false;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private synchronized boolean validateStartEndDate(CreatePromotionBean bean, String userId, int row) {
		List<Boolean> result = new ArrayList<>();
		boolean res = false;
		String errorMsg = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.parse(bean.getStartDate());
			sdf.parse(bean.getEndDate());
			Calendar cal = Calendar.getInstance();

			Date now = cal.getTime();

			String[] startDateArray = bean.getStartDate().split("/");
			String[] endDateArray = bean.getEndDate().split("/");

			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(startDateArray[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(startDateArray[2]));
			Date startDate = cal.getTime();

			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDateArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(endDateArray[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(endDateArray[2]));
			Date endDate = cal.getTime();

			long diff = startDate.getTime() - now.getTime();
			long diff1 = endDate.getTime() - now.getTime();
			long diff3 = endDate.getTime() - startDate.getTime();

			long diffInDays = diff / 1000 / 60 / 60 / 24;
			long diffInDays1 = diff1 / 1000 / 60 / 60 / 24;
			long diffInDays3 = diff3 / 1000 / 60 / 60 / 24;

			if (diffInDays < 0) {
				errorMsg = errorMsg + "Backdated ops cannot be created. Please select the correct Start date.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
				result.add(false);
			}

			if (diffInDays >= 1 && diffInDays <= 90) {
				result.add(true);
			} else if (diffInDays > 0 && diffInDays >= 1 && diffInDays <= 90) {
				errorMsg = errorMsg + "Start date of the ops cannot exceed 180 days from today.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
				result.add(false);
			}

			if (diffInDays1 >= 1 && diffInDays1 <= 211) {
				result.add(true);
			} else {
				errorMsg = errorMsg + "End date of the ops cannot exceed 180 days from today.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
				result.add(false);
			}
			if (diffInDays3 >= 1) {
				result.add(true);
			} else {
				errorMsg = errorMsg + "End date should be greater than Start date.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
				result.add(false);
			}
			if (result.contains(false)) {
				res = false;
			} else {
				res = true;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			errorMsg = errorMsg + "Invalid start/end date.^";
			updateErrorMessageInTemp(errorMsg, userId, row);
			return false;
		}
		return res;
	}

	private synchronized boolean validateChildBasepack(CreatePromotionBean bean, String userId, int row) {
		boolean result = false;
		String errorMsg = "";
		if (!bean.getChildBasepack1().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack1());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio1());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C1 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C1 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} else if (!bean.getChildBasepack2().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack2());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio2());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C2 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C2 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} else if (!bean.getChildBasepack3().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack3());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio3());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C3 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C3 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} else if (!bean.getChildBasepack4().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack4());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio4());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C4 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C4 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} else if (!bean.getChildBasepack5().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack5());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio5());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C5 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C5 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} else if (!bean.getChildBasepack6().equals("")) {
			result = true;
			boolean isBasepackValid = validateBasepack(bean.getChildBasepack6());
			if (isBasepackValid) {
				boolean isRatioValid = validateRatio(bean.getChildRatio6());
				if (!isRatioValid) {
					errorMsg = errorMsg + "C6 Child Pack Ratio should be integer.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			} else {
				errorMsg = errorMsg + "Invalid C6 Child Pack.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized boolean savePromotionDepotLevel(CreatePromotionBean bean, String userId, String promoId,
			String customerChainL1, List<String> customerChainL2) throws Exception {
		boolean res = true;
		String qryTogetDepot = "";
		String geography = "";
		Set<String> depotSet = new HashSet<>();
		List<Boolean> results = new ArrayList<Boolean>();
		try {
			Query queryToSavePromotionDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_PROMOTION_DEPOT_LEVEL(PROMO_ID,DEPOT,BRANCH,CLUSTER,ACTIVE,CUSTOMER_CHAIN_L1,BASEPACK) VALUES(?0,?1,?2,?3,?4,?5,?6)");  //Sarin - Added Parameters position
			if (!bean.getGeography().equals("ALL INDIA")) {
				qryTogetDepot = "SELECT DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(DEPOT,':'),BRANCH),':'),CLUSTER) FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_CHAIN_L2 IN(:customerChainL2) AND ACTIVE=1 ";
				String[] geographyArray = bean.getGeography().split(",");
				for (int k = 0; k < geographyArray.length; k++) {
					String geo = geographyArray[k].trim();
					if (k == 0) {
						if (geo.startsWith("B")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " AND (BRANCH_CODE='" + geography + "'";
						} else if (geo.startsWith("CL")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " AND (CLUSTER_CODE='" + geography + "'";
						} /*
							 * else if (geo.startsWith("ST")) { if (geo.contains(":")) { geography =
							 * geo.substring(0, geo.indexOf(':')); } else { geography = geo; } qryTogetDepot
							 * = qryTogetDepot + " AND (STATE_CODE='" + geography + "'"; }
							 */
					}
					if (k > 0 && k < geographyArray.length - 1) {
						if (geo.startsWith("B")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " OR BRANCH_CODE='" + geography + "'";
						} else if (geo.startsWith("CL")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " OR CLUSTER_CODE='" + geography + "'";
						} /*
							 * else if (geo.startsWith("ST")) { if (geo.contains(":")) { geography =
							 * geo.substring(0, geo.indexOf(':')); } else { geography = geo; } qryTogetDepot
							 * = qryTogetDepot + " OR STATE_CODE='" + geography + "'"; }
							 */
					}
					if (k == geographyArray.length - 1) {
						if (geo.startsWith("B")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " OR BRANCH_CODE='" + geography + "')";
						} else if (geo.startsWith("CL")) {
							if (geo.contains(":")) {
								geography = geo.substring(0, geo.indexOf(':'));
							} else {
								geography = geo;
							}
							qryTogetDepot = qryTogetDepot + " OR CLUSTER_CODE='" + geography + "')";
						} /*
							 * else if (geo.startsWith("ST")) { if (geo.contains(":")) { geography =
							 * geo.substring(0, geo.indexOf(':')); } else { geography = geo; } qryTogetDepot
							 * = qryTogetDepot + " OR STATE_CODE='" + geography + "')"; }
							 */
					}
				}
				Query queryTogetDepot = sessionFactory.getCurrentSession().createNativeQuery(qryTogetDepot);
				queryTogetDepot.setString("customerChainL1", customerChainL1);
				queryTogetDepot.setParameterList("customerChainL2", customerChainL2);
				depotSet.addAll(queryTogetDepot.list());
			} else {
				qryTogetDepot = "SELECT DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(DEPOT,':'),BRANCH),':'),CLUSTER) FROM TBL_PROCO_CUSTOMER_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_CHAIN_L2 IN(:customerChainL2) AND ACTIVE=1";
				Query queryTogetDepot = sessionFactory.getCurrentSession().createNativeQuery(qryTogetDepot);
				queryTogetDepot.setString("customerChainL1", customerChainL1);
				queryTogetDepot.setParameterList("customerChainL2", customerChainL2);
				depotSet.addAll(queryTogetDepot.list());
			}
			Iterator iterator = depotSet.iterator();
			while (iterator.hasNext()) {
				String str = (String) iterator.next();
				String[] split = str.split(":");
				queryToSavePromotionDepotLevel.setString(0, promoId);
				queryToSavePromotionDepotLevel.setString(1, split[0]);
				queryToSavePromotionDepotLevel.setString(2, split[1]);
				queryToSavePromotionDepotLevel.setString(3, split[2]);
				queryToSavePromotionDepotLevel.setInteger(4, 1);
				queryToSavePromotionDepotLevel.setString(5, customerChainL1);
				queryToSavePromotionDepotLevel.setString(6, bean.getBasepack1());
				int executeUpdate = queryToSavePromotionDepotLevel.executeUpdate();
				if (executeUpdate > 0) {
					results.add(true);
				}
			}
			if (results.contains(false)) {
				res = false;
			} else {
				res = true;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw e;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCategories(String userId) {
		List<String> catList = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT(A.CATEGORY) FROM TBL_PROCO_PRODUCT_MASTER AS A INNER JOIN TBL_PROCO_TME_MAPPING AS B ON A.CATEGORY=B.CATEGORY AND A.PRICE_LIST=B.PRICE_LIST WHERE A.ACTIVE = 1 AND B.USER_ID=:userId");
			query.setString("userId", userId);
			catList = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return catList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBrands(String userId) {
		List<String> brandList = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT(A.BRAND) FROM TBL_PROCO_PRODUCT_MASTER AS A INNER JOIN TBL_PROCO_TME_MAPPING AS B ON A.CATEGORY=B.CATEGORY AND A.PRICE_LIST=B.PRICE_LIST WHERE A.ACTIVE = 1 AND B.USER_ID=:userId");
			query.setString("userId", userId);
			brandList = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return brandList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBasepacks(String userId) {
		List<String> list = null;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT REPLACE ( BASEPACK_DESC, '''', '') FROM TBL_PROCO_PRODUCT_MASTER AS A INNER JOIN TBL_PROCO_TME_MAPPING AS B ON A.CATEGORY=B.CATEGORY AND A.PRICE_LIST=B.PRICE_LIST WHERE A.ACTIVE = 1 AND B.USER_ID=:userId");
			query.setString("userId", userId);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return list;
	}

	@Override
	public synchronized String getErrorMsg(String userId) {
		String errorMsg = "";
		try {
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT ERROR_MSG FROM TBL_PROCO_PROMOTION_MASTER_TEMP WHERE USER_ID = :userId");
			query.setString("userId", userId);
			errorMsg = (String) query.uniqueResult();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return errorMsg;
	}

	private String getGeographyWithoutBranch(String geography) {
		String geo = "";
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT CONCAT(CONCAT(CLUSTER_CODE,':'), CLUSTER) from TBL_PROCO_CUSTOMER_MASTER WHERE CLUSTER_CODE=:clusterCode ORDER BY CONCAT(CONCAT(CLUSTER_CODE,':'), CLUSTER)");
			if (!geography.equalsIgnoreCase("ALL INDIA")) {
				String[] split = geography.split(",");
				for (int i = 0; i < split.length; i++) {
					if (i < split.length - 1) {
						String g = split[i].trim();
						if (g.startsWith("CL")) {
							if (!g.contains(":")) {
								query.setString("clusterCode", g);
								g = (String) query.uniqueResult();
								geo = geo + g + ",";
							} else {
								geo = geo + g + ",";
							}
						}
					} else if (i == split.length - 1) {
						String g = split[i].trim();
						if (g.startsWith("CL")) {
							if (!g.contains(":")) {
								query.setString("clusterCode", g);
								g = (String) query.uniqueResult();
								geo = geo + g;
							} else {
								geo = geo + g;
							}
						}
					}
				}

			} else {
				geo = geography;
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return geo;
	}

	@SuppressWarnings("rawtypes")
	public boolean saveStatusInStatusTracker(String promoId, int status, String remark, String userId, String basePack) {
		boolean res = true;
		try {
			String year = "";
			String moc = "";
			int oldStatus = 0;
			String originalId = "";
			Integer opsCount = 0;
			Query qryTogetMocAndYear = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT MOC,YEAR,STATUS,ORIGINAL_ID FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId");
			qryTogetMocAndYear.setString("promoId", promoId);
			Iterator iterator = qryTogetMocAndYear.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				moc = obj[0].toString();
				year = obj[1].toString();
				oldStatus = (obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
				originalId = obj[3].toString();
			}

			Query qryTogetMinStatusOfOriginalOps = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT MIN(STATUS_ID) FROM TBL_PROCO_STATUS_TRACKER WHERE PROMO_ID=:promoId");
			qryTogetMinStatusOfOriginalOps.setString("promoId", originalId);
			Integer minStatus = (Integer)qryTogetMinStatusOfOriginalOps.uniqueResult();

			if (!promoId.equals(originalId)) {
				Query qryTogetCountOfOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						"select count(ORIGINAL_ID) from TBL_PROCO_PROMOTION_MASTER where ORIGINAL_ID=:promoId");
				qryTogetCountOfOriginalOps.setString("promoId", originalId);
				opsCount = ((BigInteger) qryTogetCountOfOriginalOps.uniqueResult()).intValue();
			} else {
				opsCount = 1;
			}

			Query qryTogetStartDateOfMoc = sessionFactory.getCurrentSession().createNativeQuery(
					//Sarin Date Format Changes
					//"SELECT VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
					"SELECT DATE_FORMAT(START_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
			qryTogetStartDateOfMoc.setString("moc", moc);
			qryTogetStartDateOfMoc.setString("year", year);
			String startDateStr = (String) qryTogetStartDateOfMoc.uniqueResult();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.parse(startDateStr);

			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			String[] startDateArray = startDateStr.split("/");

			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(startDateArray[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(startDateArray[2]));
			Date startDate = cal.getTime();

			long diff = startDate.getTime() - now.getTime();
			long diffInDays = diff / 1000 / 60 / 60 / 24;

			if (diffInDays >= 45 && diffInDays <= 60) { // CR1
				if (status == 1) {
					status = 7;
				}
				if (status == 2) {
					status = 8;
				}
				if (status == 9 && oldStatus == 10) {
					status = 11;
				} else if (status == 10 && oldStatus == 9) {
					status = 11;
				}
				if (status == 3) {
					status = 13;
				}
				if (status == 4) {
					status = 14;
				}
				if (status == 5) {
					status = 15;
				}
				if (status == 12 && minStatus == 1) {
					status = 12;
				} else if (status == 12 && minStatus == 7 && opsCount > 1) {
					status = 12;
				} else if (status == 12 && minStatus == 7 && opsCount == 1) {
					status = 16;
				}
			} else if (diffInDays >= 30 && diffInDays < 45) { // CR2
				if (status == 1) {
					status = 17;
				}
				if (status == 2) {
					status = 18;
				}
				if (status == 3) {
					status = 23;
				}

				if (status == 9) {
					status = 19;
				}
				if (status == 10) {
					status = 20;
				}

				if (status == 19 && oldStatus == 20) {
					status = 21;
				} else if (status == 20 && oldStatus == 19) {
					status = 21;
				}
				if (status == 4) {
					status = 24;
				}
				if (status == 5) {
					status = 25;
				}
				if (status == 12 && minStatus == 1) {
					status = 22;
				} else if (status == 12 && minStatus == 17 && opsCount > 1) {
					status = 22;
				} else if (status == 12 && minStatus == 17 && opsCount == 1) {
					status = 26;
				}
			} else if (diffInDays < 30) { // ADHOC
				if (status == 1) {
					status = 27;
				}
				if (status == 2) {
					status = 28;
				}
				if (status == 3) {
					status = 33;
				}
				if (status == 4) {
					status = 34;
				}
				if (status == 5) {
					status = 35;
				}
				if (status == 9) {
					status = 29;
				}
				if (status == 10) {
					status = 30;
				}
				if (status == 29 && oldStatus == 30) {
					status = 31;
				} else if (status == 30 && oldStatus == 29) {
					status = 31;
				}
				if (status == 12 && minStatus == 1) {
					status = 32;
				} else if (status == 12 && minStatus == 27) {
					status = 36;
				}
			}

			if (status == 6 || status == 16 || status == 26 || status == 36) { // drop
																				// offer
				Query qryToUpdateOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						// "UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=0 WHERE
						// PROMO_ID=:promoId AND ACTIVE=1");
						// Change to check Drop Promo
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=1 AND P1_BASEPACK=:basePack");
				qryToUpdateOriginalOps.setString("promoId", promoId);
				qryToUpdateOriginalOps.setInteger("status", status);
				qryToUpdateOriginalOps.setString("basePack", basePack);
				
				qryToUpdateOriginalOps.executeUpdate();

				Query qryToUpdateOriginalOpsInDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDisDepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisDepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2Level = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basePack ");
				qryToUpdateOriginalOpsInDisL2Level.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisL2Level.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisL2Level.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2DepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisL2DepotLevel.executeUpdate();
			} else if (status == 12 || status == 22 || status == 32) { // rejected.revert to original ops

				Query qryTogetMaxStatusOfOriginalOps = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT MAX(STATUS_ID) FROM TBL_PROCO_STATUS_TRACKER WHERE PROMO_ID=:promoId AND BASEPACK=:basePack ");
				qryTogetMaxStatusOfOriginalOps.setString("promoId", originalId);
				qryTogetMaxStatusOfOriginalOps.setString("basePack", basePack);
				Integer maxStatus = (Integer) qryTogetMaxStatusOfOriginalOps.uniqueResult();

				Query qryToUpdateOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=1 WHERE PROMO_ID=ORIGINAL_ID AND ORIGINAL_ID=:promoId AND ACTIVE=0 AND P1_BASEPACK=:basePack");
				qryToUpdateOriginalOps.setString("promoId", originalId);
				qryToUpdateOriginalOps.setInteger("status", maxStatus);
				qryToUpdateOriginalOps.setString("basePack", basePack);
				qryToUpdateOriginalOps.executeUpdate();

				Query qryToDeleteOps = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=0 WHERE PROMO_ID<>ORIGINAL_ID AND ORIGINAL_ID=:promoId AND P1_BASEPACK=:basePack");
				qryToDeleteOps.setString("promoId", originalId);
				qryToDeleteOps.setInteger("status", status);
				qryToDeleteOps.setString("basePack", basePack);
				qryToDeleteOps.executeUpdate();

				Query qryToUpdateOriginalOpsInDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDisDepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisDepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2Level = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDisL2Level.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisL2Level.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisL2Level.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2DepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0 AND BASEPACK=:basePack");
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("basePack", basePack);
				qryToUpdateOriginalOpsInDisL2DepotLevel.executeUpdate();
			} else {
				Query qryToUpdateStatus = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status WHERE PROMO_ID=:promoId AND P1_BASEPACK=:basePack ");
				qryToUpdateStatus.setString("promoId", promoId);
				qryToUpdateStatus.setInteger("status", status);
				qryToUpdateStatus.setString("basePack", basePack);
				qryToUpdateStatus.executeUpdate();
			}

			Query qryToSaveStatus = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_STATUS_TRACKER(PROMO_ID,STATUS_ID,REMARK,USER_ID,CHANGE_DATE,BASEPACK) VALUES(?0,?1,?2,?3,?4,?5)");  //Sarin - Added Parameters position
			qryToSaveStatus.setString(0, promoId);
			qryToSaveStatus.setInteger(1, status);
			qryToSaveStatus.setString(2, remark);
			qryToSaveStatus.setString(3, userId);
			//Sarin Changes 21Nov20
			//String date = CommonUtils.getDateInMmDdYyyyFormat();
			String date = CommonUtils.getCurrentDateInddMMyyyy();
			qryToSaveStatus.setDate(4, new SimpleDateFormat("dd/MM/yyyy").parse(date));
			qryToSaveStatus.setString(5, basePack);
			qryToSaveStatus.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean saveStatusInStatusTracker(String promoId, int status, String remark, String userId) {
		boolean res = true;
		try {
			String year = "";
			String moc = "";
			int oldStatus = 0;
			String originalId = "";
			Integer opsCount = 0;
			Query qryTogetMocAndYear = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT MOC,YEAR,STATUS,ORIGINAL_ID FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId");
			qryTogetMocAndYear.setString("promoId", promoId);
			Iterator iterator = qryTogetMocAndYear.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				moc = obj[0].toString();
				year = obj[1].toString();
				oldStatus = (obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
				originalId = obj[3].toString();
			}

			Query qryTogetMinStatusOfOriginalOps = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT MIN(STATUS_ID) FROM TBL_PROCO_STATUS_TRACKER WHERE PROMO_ID=:promoId");
			qryTogetMinStatusOfOriginalOps.setString("promoId", originalId);
			Integer minStatus = (Integer) qryTogetMinStatusOfOriginalOps.uniqueResult();

			if (!promoId.equals(originalId)) {
				Query qryTogetCountOfOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						"select count(ORIGINAL_ID) from TBL_PROCO_PROMOTION_MASTER where ORIGINAL_ID=:promoId");
				qryTogetCountOfOriginalOps.setString("promoId", originalId);
				//kiran - bigint to int changes
				//opsCount = (Integer) qryTogetCountOfOriginalOps.uniqueResult();
				opsCount = ((BigInteger)qryTogetCountOfOriginalOps.uniqueResult()).intValue();
			} else {
				opsCount = 1;
			}

			//Garima - changes for VARCHAR_FORMAT
			//"SELECT VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
			Query qryTogetStartDateOfMoc = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DATE_FORMAT(START_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
			qryTogetStartDateOfMoc.setString("moc", moc);
			qryTogetStartDateOfMoc.setString("year", year);
			String startDateStr = (String) qryTogetStartDateOfMoc.uniqueResult();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.parse(startDateStr);

			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			String[] startDateArray = startDateStr.split("/");

			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(startDateArray[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(startDateArray[2]));
			Date startDate = cal.getTime();

			long diff = startDate.getTime() - now.getTime();
			long diffInDays = diff / 1000 / 60 / 60 / 24;

			if (diffInDays >= 45 && diffInDays <= 60) { // CR1
				if (status == 1) {
					status = 7;
				}
				if (status == 2) {
					status = 8;
				}
				if (status == 9 && oldStatus == 10) {
					status = 11;
				} else if (status == 10 && oldStatus == 9) {
					status = 11;
				}
				if (status == 3) {
					status = 13;
				}
				if (status == 4) {
					status = 14;
				}
				if (status == 5) {
					status = 15;
				}
				if (status == 12 && minStatus == 1) {
					status = 12;
				} else if (status == 12 && minStatus == 7 && opsCount > 1) {
					status = 12;
				} else if (status == 12 && minStatus == 7 && opsCount == 1) {
					status = 16;
				}
			} else if (diffInDays >= 30 && diffInDays < 45) { // CR2
				if (status == 1) {
					status = 17;
				}
				if (status == 2) {
					status = 18;
				}
				if (status == 3) {
					status = 23;
				}

				if (status == 9) {
					status = 19;
				}
				if (status == 10) {
					status = 20;
				}

				if (status == 19 && oldStatus == 20) {
					status = 21;
				} else if (status == 20 && oldStatus == 19) {
					status = 21;
				}
				if (status == 4) {
					status = 24;
				}
				if (status == 5) {
					status = 25;
				}
				if (status == 12 && minStatus == 1) {
					status = 22;
				} else if (status == 12 && minStatus == 17 && opsCount > 1) {
					status = 22;
				} else if (status == 12 && minStatus == 17 && opsCount == 1) {
					status = 26;
				}
			} else if (diffInDays < 30) { // ADHOC
				if (status == 1) {
					status = 27;
				}
				if (status == 2) {
					status = 28;
				}
				if (status == 3) {
					status = 33;
				}
				if (status == 4) {
					status = 34;
				}
				if (status == 5) {
					status = 35;
				}
				if (status == 9) {
					status = 29;
				}
				if (status == 10) {
					status = 30;
				}
				if (status == 29 && oldStatus == 30) {
					status = 31;
				} else if (status == 30 && oldStatus == 29) {
					status = 31;
				}
				if (status == 12 && minStatus == 1) {
					status = 32;
				} else if (status == 12 && minStatus == 27) {
					status = 36;
				}
			}

			if (status == 6 || status == 16 || status == 26 || status == 36) { // drop
																				// offer
				Query qryToUpdateOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						// "UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=0 WHERE
						// PROMO_ID=:promoId AND ACTIVE=1");
						// Change to check Drop Promo
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=1");
				qryToUpdateOriginalOps.setString("promoId", promoId);
				qryToUpdateOriginalOps.setInteger("status", status);
				qryToUpdateOriginalOps.executeUpdate();

				Query qryToUpdateOriginalOpsInDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1");
				qryToUpdateOriginalOpsInDepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1");
				qryToUpdateOriginalOpsInDisDepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2Level = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1");
				qryToUpdateOriginalOpsInDisL2Level.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisL2Level.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2DepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID=:promoId AND ACTIVE=1");
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("promoId", promoId);
				qryToUpdateOriginalOpsInDisL2DepotLevel.executeUpdate();
			} else if (status == 12 || status == 22 || status == 32) { // rejected.revert to original ops

				Query qryTogetMaxStatusOfOriginalOps = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT MAX(STATUS_ID) FROM TBL_PROCO_STATUS_TRACKER WHERE PROMO_ID=:promoId");
				qryTogetMaxStatusOfOriginalOps.setString("promoId", originalId);
				Integer maxStatus = (Integer) qryTogetMaxStatusOfOriginalOps.uniqueResult();

				Query qryToUpdateOriginalOps = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=1 WHERE PROMO_ID=ORIGINAL_ID AND ORIGINAL_ID=:promoId AND ACTIVE=0");
				qryToUpdateOriginalOps.setString("promoId", originalId);
				qryToUpdateOriginalOps.setInteger("status", maxStatus);
				qryToUpdateOriginalOps.executeUpdate();

				Query qryToDeleteOps = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status,ACTIVE=0 WHERE PROMO_ID<>ORIGINAL_ID AND ORIGINAL_ID=:promoId");
				qryToDeleteOps.setString("promoId", originalId);
				qryToDeleteOps.setInteger("status", status);
				qryToDeleteOps.executeUpdate();

				Query qryToUpdateOriginalOpsInDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0");
				qryToUpdateOriginalOpsInDepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisDepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0");
				qryToUpdateOriginalOpsInDisDepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisDepotLevel.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2Level = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0");
				qryToUpdateOriginalOpsInDisL2Level.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisL2Level.executeUpdate();

				Query qryToUpdateOriginalOpsInDisL2DepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET ACTIVE=1 WHERE PROMO_ID=:promoId AND ACTIVE=0");
				qryToUpdateOriginalOpsInDisL2DepotLevel.setString("promoId", originalId);
				qryToUpdateOriginalOpsInDisL2DepotLevel.executeUpdate();
			} else {
				Query qryToUpdateStatus = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=:status WHERE PROMO_ID=:promoId");
				qryToUpdateStatus.setString("promoId", promoId);
				qryToUpdateStatus.setInteger("status", status);
				qryToUpdateStatus.executeUpdate();
			}

			Query qryToSaveStatus = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_STATUS_TRACKER(PROMO_ID,STATUS_ID,REMARK,USER_ID,CHANGE_DATE) VALUES(?0,?1,?2,?3,?4)");  //Sarin - Added Parameters position
			qryToSaveStatus.setString(0, promoId);
			qryToSaveStatus.setInteger(1, status);
			qryToSaveStatus.setString(2, remark);
			qryToSaveStatus.setString(3, userId);
			//Sarin Changes 21Nov20
			//String date = CommonUtils.getDateInMmDdYyyyFormat();
			//qryToSaveStatus.setString(4, date);
			String date = CommonUtils.getCurrentDateInddMMyyyy();
			qryToSaveStatus.setDate(4, new SimpleDateFormat("dd/MM/yyyy").parse(date));
			qryToSaveStatus.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPromoIds() {
		List<String> promoIds = new ArrayList<>();
		try {
			Query queryToGetPromoIds = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT PROMO_ID FROM TBL_PROCO_STATUS_TRACKER GROUP BY PROMO_ID");
			promoIds = queryToGetPromoIds.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return promoIds;
	}

	@Override
	public int getDifferenceInDays(String moc) {
		try {
			String[] split = moc.split(",");
			String month = split[0].substring(0, 2);
			String year = split[0].substring(2);
			String mon = "";
			if (month.startsWith("0")) {
				mon = month.substring(1);
			} else {
				mon = month;
			}
			String mocName = "MOC" + mon;

			//Garima - chanegs for VARCHAR_FORMAT
			//"SELECT VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
			Query qryTogetStartDateOfMoc = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DATE_FORMAT(START_DATE,'%d/%m/%Y') FROM TBL_VAT_MOC_MASTER WHERE MOC_NAME=:moc AND MOC_YEAR=:year AND MOC_GROUP ='GROUP_ONE'");
			qryTogetStartDateOfMoc.setString("moc", mocName);
			qryTogetStartDateOfMoc.setString("year", year);
			String startDateStr = (String) qryTogetStartDateOfMoc.uniqueResult();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.parse(startDateStr);

			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			String[] startDateArray = startDateStr.split("/");

			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateArray[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(startDateArray[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(startDateArray[2]));
			Date startDate = cal.getTime();

			long diff = startDate.getTime() - now.getTime();
			long diffInDays = diff / 1000 / 60 / 60 / 24;
			return (int) diffInDays;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> getChildBasepackDetails(String basepack) {
		Map<String, String> basepackDetailsMap = new HashMap<>();
		try {
			Query queryToGetBasepackDeatails = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT BASEPACK_DESC,CATEGORY,BRAND FROM TBL_PROCO_PRODUCT_MASTER WHERE BASEPACK=:basepack AND ACTIVE=1");
			queryToGetBasepackDeatails.setString("basepack", basepack);
			Iterator iterator = queryToGetBasepackDeatails.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				basepackDetailsMap.put("basepackDesc", obj[0].toString());
				basepackDetailsMap.put("category", obj[1].toString());
				basepackDetailsMap.put("brand", obj[2].toString());
			}

			if (basepackDetailsMap.isEmpty()) {
				basepackDetailsMap.put("basepackDesc", "PLEASE ENTER CORRECT BASEPACK");
				basepackDetailsMap.put("category", "PLEASE ENTER CORRECT BASEPACK");
				basepackDetailsMap.put("brand", "PLEASE ENTER CORRECT BASEPACK");
			}

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return basepackDetailsMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerChainL1(String userId) {
		List<String> customerChainL1 = new ArrayList<>();
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT A.CUSTOMER_CHAIN_L1 FROM TBL_PROCO_CUSTOMER_MASTER AS A INNER JOIN TBL_PROCO_KAM_MAPPING AS B ON A.CUSTOMER_CHAIN_L1=B.CUSTOMER_CHAIN_L1 WHERE B.USER_ID=:userId ORDER BY A.CUSTOMER_CHAIN_L1");
			queryToGetCustomeChainL1.setString("userId", userId);
			customerChainL1 = queryToGetCustomeChainL1.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return customerChainL1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> getCustomerChainL2WithCluster(String customerChainL1) {
		List<String> customerChainL1List = new ArrayList<String>();
		List<String> customerChainL1List2 = new ArrayList<String>();
		if (customerChainL1.contains("ALL CUSTOMERS")) {
			Query queryToGetBranches = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER ORDER BY ACCOUNT_NAME");
			customerChainL1List = queryToGetBranches.list();
		} else {
			String[] split = customerChainL1.split(",");
			for (int i = 0; i < split.length; i++) {
				customerChainL1List.add(split[i].trim());
			}
		}

		Map<String, List<String>> l2CustomersInClusterdFormat = new HashMap<>();
		try {
			//Garima - changes for concatenation
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT ACCOUNT_NAME, CONCAT(ACCOUNT_NAME, ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
							+ " TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN(:customerChainL1) ORDER BY ACCOUNT_NAME ");
			//		"SELECT DISTINCT ACCOUNT_NAME, ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
			//		+ "TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN(:customerChainL1)");
			queryToGetCustomeChainL1.setParameterList("customerChainL1", customerChainL1List);
			customerChainL1List2 = queryToGetCustomeChainL1.list();
			for (Object string : customerChainL1List2) {
				Object[] obj = (Object[]) string;
				String key = (String) obj[0];
				String value = (String) obj[1];
				if (l2CustomersInClusterdFormat.containsKey(key)) {
					l2CustomersInClusterdFormat.get(key).add(value);
				} else {
					List<String> list = new ArrayList<>();
					list.add(value);
					l2CustomersInClusterdFormat.put(key, list);
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return l2CustomersInClusterdFormat;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getCustomerChainL2WithCluster() {

		String jsonOfGeography = "";
		Gson gson = new Gson();
		try {
			// GeographyBean geographyBean = new GeographyBean();
			// geographyBean.setTitle("ALL INDIA");
			Query queryToGetBranches = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != '' ORDER BY ACCOUNT_NAME ");
			Iterator branchIterator = queryToGetBranches.list().iterator();
			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				String obj = branchIterator.next().toString();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj);
				listOfBranchBean.add(branchBean);
				//Garima - changes for concatenation
				Query queryToGetClusters = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
								+ " TBL_VAT_COMM_OUTLET_MASTER tpcm WHERE ACCOUNT_NAME = :customerL1");
				//		.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
				queryToGetClusters.setString("customerL1", obj);
				Iterator clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<ClusterBean>();
				while (clusterIterator.hasNext()) {
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterIterator.next().toString());
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			// geographyBean.setSubs(listOfBranchBean);
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL CUSTOMERS");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			jsonOfGeography = e.toString();
		}
		return jsonOfGeography;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getCustomerChainL2WithCluster(List<String> liClusterCode) {
		String jsonOfGeography = "";
		Gson gson = new Gson();
		try {
			String finalquery = "SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != ''";
			if (!liClusterCode.get(0).equals("ALL INDIA")) {
				finalquery = finalquery + " AND CLUSTER_CODE IN(:clusterCode)";
			}
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(finalquery);
			if (!liClusterCode.get(0).equals("ALL INDIA")) {
				queryToGetCustomeChainL1.setParameterList("clusterCode", liClusterCode);
			}

			Iterator branchIterator = queryToGetCustomeChainL1.list().iterator();
			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				String obj = branchIterator.next().toString();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj);
				listOfBranchBean.add(branchBean);
				//Garima - changes for concatenation
				Query queryToGetClusters = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
								+ "TBL_VAT_COMM_OUTLET_MASTER tpcm WHERE ACCOUNT_NAME = :customerL1");
				//		.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
				queryToGetClusters.setString("customerL1", obj);
				Iterator clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<>();
				while (clusterIterator.hasNext()) {
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterIterator.next().toString());
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL CUSTOMERS");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return jsonOfGeography;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> getL2WithClusterStore(String launchNature2) {
		List<String> customerChainL1List2 = new ArrayList<>();
		Map<String, List<String>> l2CustomersInClusterdFormat = new HashMap<>();
		try {
			//Garima - changes for concatenation
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT ACCOUNT_NAME, CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
							+ "TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN( SELECT DISTINCT ACCOUNT_NAME FROM "
							+ "TBL_VAT_COMM_OUTLET_MASTER WHERE UPPER(CURRENT_STORE_FORMAT) = '" + launchNature2
							+ "')");
			//		"SELECT DISTINCT ACCOUNT_NAME, ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
			customerChainL1List2 = queryToGetCustomeChainL1.list();
			for (Object string : customerChainL1List2) {
				Object[] obj = (Object[]) string;
				String key = (String) obj[0];
				String value = (String) obj[1];
				if (l2CustomersInClusterdFormat.containsKey(key)) {
					l2CustomersInClusterdFormat.get(key).add(value);
				} else {
					List<String> list = new ArrayList<>();
					list.add(value);
					l2CustomersInClusterdFormat.put(key, list);
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return l2CustomersInClusterdFormat;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getL2WithClusterStoreJson(String launchNature2) {
		String jsonOfGeography = "";
		Gson gson = new Gson();
		try {
			String finalquery = "SELECT DISTINCT ACCOUNT_NAME,DP_CHAIN FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN(SELECT DISTINCT ACCOUNT_NAME AS L1Cust FROM TBL_VAT_COMM_OUTLET_MASTER WHERE UPPER(CURRENT_STORE_FORMAT) = "
					+ "'" + launchNature2 + "')";

			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(finalquery);

			Iterator branchIterator = queryToGetCustomeChainL1.list().iterator();
			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				Object[] obj = (Object[]) branchIterator.next();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj[0].toString() + ":" + obj[1].toString());
				listOfBranchBean.add(branchBean);
				//Garima - changes for concatenation
				Query queryToGetClusters = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT CONCAT(ACCOUNT_NAME ,':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
								+ "TBL_VAT_COMM_OUTLET_MASTER tpcm WHERE ACCOUNT_NAME = :customerL1");
				//		.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
				queryToGetClusters.setString("customerL1", obj[1].toString());
				Iterator clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<ClusterBean>();
				while (clusterIterator.hasNext()) {
					Object[] clusterObj = (Object[]) clusterIterator.next();
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterObj[0].toString() + ":" + clusterObj[1].toString());
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			// geographyBean.setSubs(listOfBranchBean);
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL CUSTOMERS");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return jsonOfGeography;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRegionOnCustomer(List<String> customerList) {
		List<String> customerChainL1List2 = new ArrayList<String>();
		String toReturn = "";
		try {
			//Garima - changes for concatenation
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT CONCAT(tpcm.CLUSTER_CODE,':',CLUSTER) AS CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER tpcm, TBL_VAT_COMM_OUTLET_MASTER tvcm WHERE "
							+ "tvcm.FINAL_CLUSTER = tpcm.CLUSTER AND tvcm.ACCOUNT_NAME IN (:l1CustomerList)");
			//		"SELECT DISTINCT tpcm.CLUSTER_CODE||':'||CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER tpcm, TBL_VAT_COMM_OUTLET_MASTER tvcm WHERE "
			queryToGetCustomeChainL1.setParameterList("l1CustomerList", customerList);
			customerChainL1List2 = queryToGetCustomeChainL1.list();
			for (String string : customerChainL1List2) {
				toReturn = toReturn + string + ",";
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}

		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> getL2WithClusterTown(String launchNature2) {
		List<String> customerChainL1List2 = new ArrayList<String>();
		Map<String, List<String>> l2CustomersInClusterdFormat = new HashMap<>();
		try {
			//Garima - changes for concatenation
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT ACCOUNT_NAME, CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER WHERE "
							+ "ACCOUNT_NAME IN (SELECT DISTINCT ACCOUNT_NAME AS L1Cust FROM TBL_VAT_COMM_OUTLET_MASTER "
							+ "WHERE TOWN_CLASSIFICATION = '" + launchNature2 + "')");
			//		"SELECT DISTINCT ACCOUNT_NAME, ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER WHERE "
			customerChainL1List2 = queryToGetCustomeChainL1.list();
			for (Object string : customerChainL1List2) {
				Object[] obj = (Object[]) string;
				String key = (String) obj[0];
				String value = (String) obj[1];
				if (l2CustomersInClusterdFormat.containsKey(key)) {
					l2CustomersInClusterdFormat.get(key).add(value);
				} else {
					List<String> list = new ArrayList<>();
					list.add(value);
					l2CustomersInClusterdFormat.put(key, list);
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return l2CustomersInClusterdFormat;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getL2WithClusterTownJson(String launchNature2) {
		String jsonOfGeography = "";
		Gson gson = new Gson();
		try {
			String finalquery = "SELECT DISTINCT ACCOUNT_NAME,DP_CHAIN FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN(SELECT DISTINCT ACCOUNT_NAME AS L1Cust FROM TBL_VAT_COMM_OUTLET_MASTER WHERE TOWN_CLASSIFICATION = '"
					+ launchNature2 + "')";

			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(finalquery);

			Iterator branchIterator = queryToGetCustomeChainL1.list().iterator();
			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				Object[] obj = (Object[]) branchIterator.next();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj[0].toString() + ":" + obj[1].toString());
				listOfBranchBean.add(branchBean);
				//Garima - changes for concatenation
				Query queryToGetClusters = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
								+ "TBL_VAT_COMM_OUTLET_MASTER tpcm WHERE ACCOUNT_NAME = :customerL1");
				//		.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
				queryToGetClusters.setString("customerL1", obj[1].toString());
				Iterator clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<ClusterBean>();
				while (clusterIterator.hasNext()) {
					Object[] clusterObj = (Object[]) clusterIterator.next();
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterObj[0].toString() + ":" + clusterObj[1].toString());
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			// geographyBean.setSubs(listOfBranchBean);
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL CUSTOMERS");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return jsonOfGeography;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCustomerChainL2WithClusterJson(String customerChainL1) {
		String jsonOfGeography = "";
		Gson gson = new Gson();
		List<String> customerChainL1List = new ArrayList<String>();

		try {
			Query queryToGetCustomeChainL1 = null;
			Iterator<Object> branchIterator = null;
			if (customerChainL1.contains("All selected")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != ''");
				branchIterator = queryToGetCustomeChainL1.list().iterator();
			} else {
				String[] split = customerChainL1.split(",");
				for (int i = 0; i < split.length; i++) {
					customerChainL1List.add(split[i].trim());
				}
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME IN(:customerChainL1)");
				queryToGetCustomeChainL1.setParameterList("customerChainL1", customerChainL1List);
				branchIterator = queryToGetCustomeChainL1.list().iterator();
			}

			List<BranchBean> listOfBranchBean = new ArrayList<BranchBean>();
			while (branchIterator.hasNext()) {
				String obj = branchIterator.next().toString();
				BranchBean branchBean = new BranchBean();
				branchBean.setTitle(obj);
				listOfBranchBean.add(branchBean);
				//Garima - changes for concatenation
				Query queryToGetClusters = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM "
								+ "TBL_VAT_COMM_OUTLET_MASTER tpcm WHERE ACCOUNT_NAME = :customerL1");
				//		.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM "
				queryToGetClusters.setString("customerL1", obj);
				Iterator<Object> clusterIterator = queryToGetClusters.list().iterator();
				List<ClusterBean> listOfClusterBean = new ArrayList<ClusterBean>();
				while (clusterIterator.hasNext()) {
					String clusterObj = clusterIterator.next().toString();
					ClusterBean clusterBean = new ClusterBean();
					clusterBean.setTitle(clusterObj);
					listOfClusterBean.add(clusterBean);
				}
				branchBean.setSubs(listOfClusterBean);
			}
			// geographyBean.setSubs(listOfBranchBean);
			BranchBean bean = new BranchBean();
			bean.setTitle("ALL CUSTOMERS");
			listOfBranchBean.add(0, bean);
			jsonOfGeography = gson.toJson(listOfBranchBean);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return jsonOfGeography;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCustomerDataOnRegion(List<String> liClusterCode) {
		String toReturn = "";
		try {
			Query queryToGetClusters;

			if (!liClusterCode.get(0).equals("ALL INDIA")) {
				//Garima - changes for concatenation
				queryToGetClusters = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ "tpcm WHERE FINAL_CLUSTER IN (:liClusterCode) ORDER BY CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) ");
				//		"SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER "
				queryToGetClusters.setParameterList("liClusterCode", liClusterCode);
			} else {
				//Garima - changes for concatenation
				queryToGetClusters = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER tpcm ORDER BY CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) ");
				//		"SELECT DISTINCT ACCOUNT_NAME || ':' || DP_CHAIN AS CUSTOMER_CHAIN_L2 FROM TBL_VAT_COMM_OUTLET_MASTER "
			}
			Iterator<Object> clusterIterator = queryToGetClusters.list().iterator();
			while (clusterIterator.hasNext()) {
				String data = clusterIterator.next().toString();
				ClusterBean clusterBean = new ClusterBean();
				clusterBean.setTitle(data);
				toReturn = toReturn + data + ",";
			}

		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRegionOnCustomerList(List<String> customerList) {
		try {
			//Garima - changes for concatenation
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT CONCAT(tpcm.CLUSTER_CODE,':',CLUSTER) AS CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER tpcm, "
							+ " TBL_VAT_COMM_OUTLET_MASTER tvcm WHERE tvcm.FINAL_CLUSTER = tpcm.CLUSTER AND tvcm.ACCOUNT_NAME IN "
							+ "(:l1CustomerList)");
			//		"SELECT DISTINCT tpcm.CLUSTER_CODE||':'||CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER tpcm, "
			queryToGetCustomeChainL1.setParameterList("l1CustomerList", customerList);
			return queryToGetCustomeChainL1.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerChainL1ForLaunch() {
		List<String> customerChainL1 = new ArrayList<>();
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME CUSTOMER_CHAIN_L1 FROM TBL_VAT_COMM_OUTLET_MASTER ORDER BY ACCOUNT_NAME");
			customerChainL1 = queryToGetCustomeChainL1.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return customerChainL1;
	}
	

}
