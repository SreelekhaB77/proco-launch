package com.hul.proco.controller.volumeupload;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreatePromoDAOImpl;

@Repository
public class VolumeUploadDAOImpl implements VolumeUploadDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CreatePromoDAOImpl createPromoDAO;
	
	private Logger logger = Logger.getLogger(VolumeUploadDAOImpl.class);

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_VOLUME_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_VOLUME_TEMP(YEAR,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,USER_ID,ROW_NO,PROMO_ID,QUANTITY) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40)";  //Sarin - Added Parameters position

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromoTableList(ArrayList<String> headerList, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			/*
			 * promoQuery =
			 * "SELECT PROMO_ID, YEAR, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, B.CATEGORY, OFFER_DESC, P1_BASEPACK, "
			 * +
			 * "P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, "
			 * +
			 * "P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, "
			 * +
			 * "C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, "
			 * +
			 * "C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, "
			 * +
			 * "THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, OFFER_MODALITY, OFFER_VALUE, KITTING_VALUE, "
			 * +
			 * "GEOGRAPHY, UOM, QUANTITY  from TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B "
			 * +
			 * "ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY "
			 * + "INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID " +
			 * "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
			 * +
			 * "WHERE A.ACTIVE=1 AND A.STATUS IN(1,2,3,4,5,11,12,13,14,15,21,22,23,24,25,31,32,33,34,35) "
			 * + "AND F.USER_ID='"+userId+"' ";
			 */
			// Kavitha D changes for SPRINT 5-SEP21
			promoQuery = "SELECT PROMO_ID, YEAR, MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, OFFER_DESC, P1_BASEPACK, "
					+ "P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, "
					+ "P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, "
					+ "C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, "
					+ "C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, "
					+ "THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, OFFER_MODALITY, OFFER_VALUE, KITTING_VALUE, "
					+ "GEOGRAPHY, UOM, QUANTITY,CASE WHEN ((GEOGRAPHY = 'ALL INDIA') OR (SELECT COUNT(DISTINCT BRANCH) FROM TBL_PROCO_PROMOTION_DEPOT_LEVEL DL WHERE DL.PROMO_ID = A.PROMO_ID) = 5) THEN 'ALL INDIA' "
					+ "ELSE (SELECT GROUP_CONCAT(DISTINCT BRANCH) FROM TBL_PROCO_PROMOTION_DEPOT_LEVEL DL WHERE DL.PROMO_ID = A.PROMO_ID) END AS ACTUAL_GEOGRAPHY  "
					+ "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B "
					+ "ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY "
					+ "INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
					+ "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
					+ "WHERE A.ACTIVE=1 AND A.STATUS IN(1,2,3,4,5,11,12,13,14,15,21,22,23,24,25,31,32,33,34,35) "
					+ "AND F.USER_ID='"+userId+"' ";	
			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND B.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND B.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
			}
			if (!custChainL1.equalsIgnoreCase("All")) {
				if (custL1.size() == 1) {
					promoQuery += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(0) + "' )";
				} else if (custL1.size() > 1) {
					for (int i = 0; i < custL1.size(); i++) {
						if (i == 0) {
							promoQuery += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' ";
						} else if (i < custL1.size() - 1) {
							promoQuery += "OR A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' ";
						} else {
							promoQuery += "OR A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "') ";
						}
					}
				}
			}
			if (!custChainL2.equalsIgnoreCase("All")) {
				if (custL2.size() == 1) {
					promoQuery += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(0) + "%') ";
				} else if (custL2.size() > 1) {
					for (int i = 0; i < custL2.size(); i++) {
						if (i == 0) {
							promoQuery += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i < custL2.size() - 1) {
							promoQuery += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else {
							promoQuery += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%') ";
						}
					}
				}
			}
			if (!geography.equalsIgnoreCase("All")) {
				if (geography.startsWith("B")) {
					String branch = geography.substring(0, geography.indexOf(':'));
					Query queryToGetCluster = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT CLUSTER_CODE FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branch");
					queryToGetCluster.setString("branch", branch);
					List clusterList = queryToGetCluster.list();
					promoQuery += "AND (A.GEOGRAPHY like '%" + branch + "%' ";
					if (clusterList != null) {
						for (int i = 0; i < clusterList.size(); i++) {
							if (i < clusterList.size() - 1) {
								promoQuery += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%' ";
							} else if (i == clusterList.size() - 1) {
								promoQuery += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%') ";
							}
						}
					}
				} else if (geography.startsWith("CL")) {
					String cluster = geography.substring(0, geography.indexOf(':'));
					promoQuery += "AND (A.GEOGRAPHY like '%" + cluster + "%') ";
				} else {
					geography = geography.substring(0, geography.indexOf(':'));
					promoQuery += "AND A.GEOGRAPHY like '%" + geography + "%' ";
				}
			}
			if (!offerType.equalsIgnoreCase("All")) {
				promoQuery += "AND A.OFFER_TYPE = '" + offerType + "' ";
			}
			if (!modality.equalsIgnoreCase("All")) {
				promoQuery += "AND C.MODALITY_NO = '" + modality + "' ";
			}
			if (!year.equalsIgnoreCase("All")) {
				promoQuery += "AND A.YEAR = '" + year + "' ";
			}
			if (!moc.equalsIgnoreCase("All")) {
				if (moc.equals("Q1")) {
					promoQuery += "AND (A.MOC='Q1' OR A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') ";
				} else if (moc.equals("Q2")) {
					promoQuery += "AND (A.MOC='Q2' OR A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') ";
				} else if (moc.equals("Q3")) {
					promoQuery += "AND (A.MOC='Q3' OR A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') ";
				} else if (moc.equals("Q4")) {
					promoQuery += "AND (A.MOC='Q4' OR A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') ";
				} else {
					promoQuery += "AND A.MOC = '" + moc + "' ";
				}
			}
			promoQuery += " AND A.START_DATE>=CURRENT_DATE ";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
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
		} catch (Exception ex) {
			logger.error("Exception :", ex);
			return null;
		}
	}

	@Override
	public String volumeUploadForPromotion(VolumeUploadBean[] bean, String userId) throws Exception {
		String response = null;
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_PROMOTION_VOLUME_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			//kiran - bigint to int changes
			//Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();

			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_VOLUME_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}

			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_VOLUME_TEMP);
			for (int i = 0; i < bean.length; i++) {
				query.setString(0, bean[i].getYear());
				query.setString(1, bean[i].getMoc());
				query.setString(2, bean[i].getCustomerChainL1());
				query.setString(3, bean[i].getCustomerChainL2());
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
				query.setString(35, bean[i].getGeography());
				query.setString(36, bean[i].getUom());
				query.setString(37, userId);
				query.setInteger(38, i);
				query.setString(39, bean[i].getPromoId());
				query.setString(40, bean[i].getQuantity());
				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateRecord(bean[i], userId, i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}

			if (!responseList.contains("ERROR_FILE")) {
				boolean updateUomAndQuantity = updateUomAndQuantity(bean, userId);
				if (!updateUomAndQuantity) {
					response = "ERROR";
				} else {
					response = "SUCCESS_FILE";
				}
			} else {
				response = "ERROR_FILE";
			}

		} catch (Exception e) {
			logger.debug("Exception:", e);
			throw new Exception();
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	private boolean updateUomAndQuantity(VolumeUploadBean[] bean, String userId) {
		boolean res = false;
		ArrayList<Boolean> resList = new ArrayList<Boolean>();
		try {
			String p1Basepack = "";
			String c1Basepack = "";
			int p1PackRatio = 0;
			int c1PackRatio = 0;
			int quantity = 0;
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					//"UPDATE TBL_PROCO_PROMOTION_MASTER SET UOM=:uom,QUANTITY=:quantity WHERE PROMO_ID=:promoId AND ACTIVE=1");
					"UPDATE TBL_PROCO_PROMOTION_MASTER SET UOM=:uom,QUANTITY=:quantity WHERE PROMO_ID=:promoId AND ACTIVE=1 AND P1_BASEPACK=:P1Basepack ");
			Query queryToGetBasepacksAndRatio = sessionFactory.getCurrentSession().createNativeQuery(
					//"SELECT P1_BASEPACK, P1_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO  FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId");
					"SELECT P1_BASEPACK, P1_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO  FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId AND P1_BASEPACK=:P1Basepack");

			for (int i = 0; i < bean.length; i++) {
				queryToGetBasepacksAndRatio.setString("promoId", bean[i].getPromoId());
				queryToGetBasepacksAndRatio.setString("P1Basepack", bean[i].getBasepack1());
				Iterator iterator = queryToGetBasepacksAndRatio.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					p1Basepack = (obj[0] == null ? "" : obj[0].toString());
					p1PackRatio = ((obj[1] == null || obj[1].toString().equals("")) ? 0
							: Integer.parseInt(obj[1].toString()));
					c1Basepack = (obj[2] == null ? "" : obj[2].toString());
					c1PackRatio = ((obj[3] == null || obj[3].toString().equals("")) ? 0
							: Integer.parseInt(obj[3].toString()));
				}
				if (p1Basepack.equals(c1Basepack) && bean[i].getUom().equalsIgnoreCase("Singles")) {
					quantity = Integer.parseInt(bean[i].getQuantity());
					quantity = quantity / (p1PackRatio + c1PackRatio);
					query.setString("uom", "Combies");
					query.setString("quantity", String.valueOf(quantity));
				} else if (!p1Basepack.equals(c1Basepack) && bean[i].getUom().equalsIgnoreCase("Singles")) {
					quantity = Integer.parseInt(bean[i].getQuantity());
					quantity = quantity / p1PackRatio;
					query.setString("uom", "Combies");
					query.setString("quantity", String.valueOf(quantity));
				} else if (!p1Basepack.equals(c1Basepack) && bean[i].getUom().equalsIgnoreCase("Combies")) {
					quantity = Integer.parseInt(bean[i].getQuantity());
					quantity = quantity * p1PackRatio;
					query.setString("uom", "Singles");
					query.setString("quantity", String.valueOf(quantity));
				} else if (p1Basepack.equals(c1Basepack) && bean[i].getUom().equalsIgnoreCase("Combies")) {
					quantity = Integer.parseInt(bean[i].getQuantity());
					quantity = quantity * (p1PackRatio + c1PackRatio);
					query.setString("uom", "Singles");
					query.setString("quantity", String.valueOf(quantity));
				}
				query.setString("promoId", bean[i].getPromoId());
				query.setString("P1Basepack", bean[i].getBasepack1());
				query.executeUpdate();
				resList.add(true);
				
				Query queryToDeleteFromDisaggregationTable = sessionFactory.getCurrentSession().createNativeQuery(
						//"DELETE FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE PROMO_ID =:promoId");
						"DELETE FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE PROMO_ID =:promoId AND BASEPACK=:basePack ");
				queryToDeleteFromDisaggregationTable.setParameter("promoId", bean[i].getPromoId());
				queryToDeleteFromDisaggregationTable.setParameter("basePack", bean[i].getBasepack1());
				queryToDeleteFromDisaggregationTable.executeUpdate();

				Query queryToDeleteFromDisaggregationL2 = sessionFactory.getCurrentSession().createNativeQuery(
						//"DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_LEVEL WHERE PROMO_ID =:promoId");
						"DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_LEVEL WHERE PROMO_ID =:promoId AND BASEPACK=:basePack");
				queryToDeleteFromDisaggregationL2.setParameter("promoId", bean[i].getPromoId());
				queryToDeleteFromDisaggregationL2.setParameter("basePack", bean[i].getBasepack1());
				queryToDeleteFromDisaggregationL2.executeUpdate();

				Query queryToDeleteFromDisaggregationL2Depot = sessionFactory.getCurrentSession().createNativeQuery(
						//"DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL WHERE PROMO_ID =:promoId");
						"DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL WHERE PROMO_ID =:promoId AND BASEPACK=:basePack ");
				queryToDeleteFromDisaggregationL2Depot.setParameter("promoId", bean[i].getPromoId());
				queryToDeleteFromDisaggregationL2Depot.setParameter("basePack", bean[i].getBasepack1());
				queryToDeleteFromDisaggregationL2Depot.executeUpdate();
				
				//createPromoDAO.saveStatusInStatusTracker(bean[i].getPromoId(), 3, "", userId);
				createPromoDAO.saveStatusInStatusTracker(bean[i].getPromoId(), 3, "", userId,bean[i].getBasepack1() );
				
			}
			if (resList.contains(false)) {
				res = false;
			} else {
				res = true;
				
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	private String validateRecord(VolumeUploadBean bean, String userId, int row) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId and ACTIVE=1");
			queryToPromoId.setString("promoId", bean.getPromoId());
			//kiran - bigint to int changes
			//Integer promoIdCount = (Integer) queryToPromoId.uniqueResult();
			Integer promoIdCount = ((BigInteger)queryToPromoId.uniqueResult()).intValue();
			if (promoIdCount != null && promoIdCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promo Id.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
			if (!bean.getUom().equalsIgnoreCase("Singles") && !bean.getUom().equalsIgnoreCase("Combies")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid UOM (Should be Singles/Combies).^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
			if (!bean.getQuantity().matches("\\d+")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Quantity should be Integer.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	private int updateErrorMessageInTemp(String errorMsg, String userId, int row) {
		try {
			String qry = "UPDATE TBL_PROCO_PROMOTION_VOLUME_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getVolumeErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT PROMO_ID, YEAR, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, OFFER_MODALITY, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, QUANTITY, ERROR_MSG from TBL_PROCO_PROMOTION_VOLUME_TEMP WHERE USER_ID=:userId";
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
			logger.error("Exception: ", e);
		}
		return downloadDataList;
	}
}
