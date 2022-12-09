package com.hul.proco.controller.collaboration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreatePromoDAOImpl;

@Repository
public class CollaborationDAOImpl implements CollaborationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CreatePromoDAOImpl createPromoDAOImpl;

	private Logger logger = Logger.getLogger(CollaborationDAOImpl.class);

	private static final String SQL_QUERY_INSERT_INTO_KAM_L1_TEMP = "INSERT INTO TBL_PROCO_KAM_L1_TEMP(PROMO_ID,GEOGRAPHY,BASEPACK,CUSTOMER_CHAIN_L1,DP_PER_SPLIT,DP_QTY_SPLIT,DEPOT,BRANCH,CLUSTER,DEPOT_PER,DEPOT_QTY,USER_ID,KAM_SPLIT) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)"; // Sarin
																																																																								// -
																																																																								// Added
																																																																								// Parameters
																																																																								// position

	private static final String SQL_QUERY_INSERT_INTO_KAM_L2_TEMP = "INSERT INTO TBL_PROCO_KAM_L2_TEMP(PROMO_ID,GEOGRAPHY,BASEPACK,CUSTOMER_CHAIN_L1,L1_DP_PER_SPLIT,L1_DP_QTY_SPLIT,CUSTOMER_CHAIN_L2,L2_DP_PER_SPLIT,L2_DP_QTY_SPLIT,DEPOT,BRANCH,CLUSTER,DEPOT_PER,DEPOT_QTY,USER_ID,KAM_SPLIT) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15)"; // Sarin
																																																																																									// -
																																																																																									// Added
																																																																																									// Parameters
																																																																																									// position

	@SuppressWarnings("unchecked")
	@Override
	public int getCollaborationRowCount(/*
										 * String cagetory, String brand, String basepack, String custChainL1, String
										 * custChainL2, String offerType, String modality, String year,
										 */ String moc, String userId,String [] kamAccountsArr) {
		// kiran - big int to int changes
		// List<Integer> list = null;
		List<BigInteger> list = null;
		/*
		 * Mayur Commented for sprint 9 List<String> custL1 = new
		 * ArrayList<String>(Arrays.asList(custChainL1.split(","))); List<String> custL2
		 * = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		 */
		try {
			// kiran - changes for rownumber() replaced with ROW_NUMBER()
			/*
			 * String rowCount =
			 * "SELECT COUNT(1) FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
			 * +
			 * "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', ROWNUMBER() OVER (ORDER BY PROMO_ID ASC) AS ROW_NEXT  "
			 * +
			 * "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
			 * +
			 * "INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 "
			 * +
			 * "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND  A.PROMO_ID IN (SELECT DISTINCT PROMO_ID FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL) "
			 * + "AND F.USER_ID='" + userId + "' "
			 * +" AND A.STATUS IN(4,5,14,15,24,25,34,35,12,22,32)";
			 */

			/*
			 * mayur commented for sprint 9 String rowCount =
			 * "SELECT COUNT(1) FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
			 * +
			 * "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', ROW_NUMBER() OVER (ORDER BY PROMO_ID ASC) AS ROW_NEXT  "
			 * +
			 * "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
			 * +
			 * "INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 "
			 * +
			 * "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND  A.PROMO_ID IN (SELECT DISTINCT PROMO_ID FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE SUBMIT_KAM_STATUS = 1 ) "
			 * + "AND F.USER_ID='" + userId + "' "
			 * +" AND A.STATUS IN(4,5,14,15,24,25,34,35,12,22,32,37)"; if
			 * (!cagetory.equalsIgnoreCase("All")) { rowCount += "AND B.CATEGORY = '" +
			 * cagetory + "' "; } if (!brand.equalsIgnoreCase("All")) { rowCount +=
			 * "AND B.BRAND = '" + brand + "' "; } if (!basepack.equalsIgnoreCase("All")) {
			 * rowCount += "AND A.P1_BASEPACK = '" + basepack + "' "; } if
			 * (!custChainL1.equalsIgnoreCase("All")) { if (custL1.size() == 1) { rowCount
			 * += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(0) + "' )"; } else if
			 * (custL1.size() > 1) { for (int i = 0; i < custL1.size(); i++) { if (i == 0) {
			 * rowCount += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' "; } else if
			 * (i < custL1.size() - 1) { rowCount += "OR A.CUSTOMER_CHAIN_L1 = '" +
			 * custL1.get(i) + "' "; } else { rowCount += "OR A.CUSTOMER_CHAIN_L1 = '" +
			 * custL1.get(i) + "') "; } } } } if (!custChainL2.equalsIgnoreCase("All")) { if
			 * (custL2.size() == 1) { rowCount += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" +
			 * custL2.get(0) + "%') "; } else if (custL2.size() > 1) { for (int i = 0; i <
			 * custL2.size(); i++) { if (i == 0) { rowCount +=
			 * "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' "; } else if (i <
			 * custL2.size() - 1) { rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" +
			 * custL2.get(i) + "%' "; } else { rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%"
			 * + custL2.get(i) + "%') "; } } } } if (!offerType.equalsIgnoreCase("All")) {
			 * rowCount += "AND A.OFFER_TYPE = '" + offerType + "' "; } if
			 * (!modality.equalsIgnoreCase("All")) { rowCount += "AND A.OFFER_MODALITY = '"
			 * + modality + "' "; } if (!year.equalsIgnoreCase("All")) { rowCount +=
			 * "AND A.YEAR = '" + year + "' "; } if (!moc.equalsIgnoreCase("All")) { if
			 * (moc.equals("Q1")) { rowCount +=
			 * "AND (A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') "; } else if
			 * (moc.equals("Q2")) { rowCount +=
			 * "AND (A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') "; } else if
			 * (moc.equals("Q3")) { rowCount +=
			 * "AND (A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') "; } else if
			 * (moc.equals("Q4")) { rowCount +=
			 * "AND (A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') "; } else { rowCount
			 * += "AND A.MOC = '" + moc + "' "; } } //rowCount +=
			 * " AND A.START_DATE>=CURRENT_DATE "; rowCount += ") AS PLAN_TEMP";
			 */
			// Mayur's Change start Sprint 9
			String rowCount = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					+ "FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					+ "LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					+ "LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE WHERE PM.MOC='" + moc
					+ "' AND PM.PPM_ACCOUNT IN (";
			   for(String kam:kamAccountsArr)
			   {
				   rowCount+="'"+kam+"',";
			   }
			   rowCount = rowCount.substring(0, rowCount.length()-1)+")";
			   
			   
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
			// query.setParameter("userId", userId);//Mayur's Change end

			list = query.list();

		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		// kiran - big int to int changes
		// return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DisplayCollaborationBean> getCollaborationTableList(int pageDisplayStart, int pageDisplayLength,
			/*
			 * String cagetory, String brand, String basepack, String custChainL1, String
			 * custChainL2, String offerType, String modality, String year,
			 */ String moc, String userId, String[] kamAccounts) {
		List<DisplayCollaborationBean> promoList = new ArrayList<>();
		/*
		 * List<String> custL1 = new
		 * ArrayList<String>(Arrays.asList(custChainL1.split(","))); List<String> custL2
		 * = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		 */
		String promoQuery = "";
		try {
			// kiran - changes
			/*
			 * promoQuery =
			 * "SELECT * FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
			 * +
			 * "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', A.KITTING_VALUE, D.STATUS, A.UPDATE_STAMP, ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT  "
			 * +
			 * "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
			 * +
			 * "INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 "
			 * +
			 * "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND A.PROMO_ID IN (SELECT DISTINCT PROMO_ID FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL) "
			 * + "AND F.USER_ID='" + userId + "'"
			 * +" AND A.STATUS IN(4,5,14,15,24,25,34,35,12,22,32)";
			 */
			/*
			 * promoQuery =
			 * "SELECT * FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
			 * +
			 * "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', A.KITTING_VALUE, D.STATUS, A.UPDATE_STAMP, ROW_NUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT  "
			 * +
			 * "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
			 * +
			 * "INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 "
			 * +
			 * "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND A.PROMO_ID IN (SELECT DISTINCT PROMO_ID FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE SUBMIT_KAM_STATUS = 1) "
			 * + "AND F.USER_ID='" + userId + "'"
			 * +" AND A.STATUS IN(4,5,14,15,24,25,34,35,12,22,32,37)";
			 * 
			 * if (!cagetory.equalsIgnoreCase("All")) { promoQuery += "AND B.CATEGORY = '" +
			 * cagetory + "' "; } if (!brand.equalsIgnoreCase("All")) { promoQuery +=
			 * "AND B.BRAND = '" + brand + "' "; } if (!basepack.equalsIgnoreCase("All")) {
			 * promoQuery += "AND A.P1_BASEPACK = '" + basepack + "' "; } if
			 * (!custChainL1.equalsIgnoreCase("All")) { if (custL1.size() == 1) { promoQuery
			 * += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(0) + "' )"; } else if
			 * (custL1.size() > 1) { for (int i = 0; i < custL1.size(); i++) { if (i == 0) {
			 * promoQuery += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' "; } else
			 * if (i < custL1.size() - 1) { promoQuery += "OR A.CUSTOMER_CHAIN_L1 = '" +
			 * custL1.get(i) + "' "; } else { promoQuery += "OR A.CUSTOMER_CHAIN_L1 = '" +
			 * custL1.get(i) + "') "; } } } } if (!custChainL2.equalsIgnoreCase("All")) { if
			 * (custL2.size() == 1) { promoQuery += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" +
			 * custL2.get(0) + "%') "; } else if (custL2.size() > 1) { for (int i = 0; i <
			 * custL2.size(); i++) { if (i == 0) { promoQuery +=
			 * "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' "; } else if (i <
			 * custL2.size() - 1) { promoQuery += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" +
			 * custL2.get(i) + "%' "; } else { promoQuery +=
			 * "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%') "; } } } } if
			 * (!offerType.equalsIgnoreCase("All")) { promoQuery += "AND A.OFFER_TYPE = '" +
			 * offerType + "' "; } if (!modality.equalsIgnoreCase("All")) { promoQuery +=
			 * "AND A.OFFER_MODALITY = '" + modality + "' "; } if
			 * (!year.equalsIgnoreCase("All")) { promoQuery += "AND A.YEAR = '" + year +
			 * "' "; } if (!moc.equalsIgnoreCase("All")) { if (moc.equals("Q1")) {
			 * promoQuery += "AND (A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') "; } else
			 * if (moc.equals("Q2")) { promoQuery +=
			 * "AND (A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') "; } else if
			 * (moc.equals("Q3")) { promoQuery +=
			 * "AND (A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') "; } else if
			 * (moc.equals("Q4")) { promoQuery +=
			 * "AND (A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') "; } else {
			 * promoQuery += "AND A.MOC = '" + moc + "' "; } }
			 */
			// promoQuery += " AND A.START_DATE>=CURRENT_DATE ";

			promoQuery = "SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.PROMO_ID AS ORIGINAL_ID,PM.START_DATE,PM.END_DATE, "
					+ "PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC, "
					+ "PM.OFFER_TYPE,PM.OFFER_MODALITY, PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,PSM.STATUS, "
					//+ "PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE,PR.PROMOTION_MECHANICS,PR.PROMOTION_STATUS AS SOL_CODE_STATUS, "
					+ " 'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS, "
					+ "ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ "FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS "
					//+ "LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					//+ "FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					//+ "LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					//+ "LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE "
					+ "WHERE PM.MOC='"+ moc + "'";

			if (kamAccounts != null) {
				promoQuery = promoQuery + " AND PM.PPM_ACCOUNT IN (:kamAccount) ";
			}
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP";
			} else {
				promoQuery += " ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength + "";
			}

			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			if (kamAccounts != null) {
				query.setParameterList("kamAccount", kamAccounts);
			}
			
			// query.setParameter("userId", userId);
			List<Object[]> list = query.list();
			
			for (Object[] obj : list) {
				DisplayCollaborationBean bean = new DisplayCollaborationBean();
				bean.setPromo_id(obj[0].toString());
				bean.setStart_date(obj[2].toString());
				bean.setEnd_date(obj[3].toString());
				bean.setMoc(obj[4].toString());

				// bean.setCustomerChainL1(obj[2].toString());
				bean.setCustomerChainL2(obj[5].toString());
				// bean.setSalesCategory(obj[4].toString());
				// bean.setBrand(obj[5].toString());
				bean.setBasepack(obj[6].toString());
				bean.setOffer_desc(obj[7].toString());
				bean.setOffer_type(obj[8] == null ? "" : obj[8].toString());
				bean.setOffer_modality(obj[9] == null ? "" : obj[9].toString());
				bean.setGeography(obj[10] == null ? "" : obj[10].toString());
				bean.setQuantity(obj[11] == null ? "" : obj[11].toString());
				bean.setOffer_value(obj[12] == null ? "" : obj[12].toString());
				// bean.setUom(obj[12].toString());

				// bean.setPlannedQty(obj[14].toString());
				// bean.setNational(obj[15].toString());
				// bean.setKitting_value(obj[16].toString());
				bean.setStatus(obj[13] == null ? "" : obj[13].toString());
				bean.setInvestment_type(obj[14] == null ? "" : obj[14].toString());
				bean.setSol_code(obj[15] == null ? "" : obj[15].toString());
				bean.setPromotion_mechanics(obj[16] == null ? "" : obj[16].toString());
				bean.setSol_code_status(obj[17] == null ? "" : obj[17].toString());

				promoList.add(bean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getL1DepotDisaggregation(ArrayList<String> headerList, String userId,
			String[] promoId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		double dpPerSplit = 0;
		double dpQtySplit = 0;
		try {
			downloadDataList.add(headerList);
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT A.PROMO_ID,B.GEOGRAPHY,A.BASEPACK,B.CUSTOMER_CHAIN_L1,A.DEPOT,C.BRANCH,C.CLUSTER,CAST(A.PERCENTAGE AS DECIMAL(5,2)), A.QUANTITY, CASE WHEN A.KAM_SPLIT_STATUS = 0 THEN A.QUANTITY ELSE A.KAM_SPLIT END AS KAM_SPLIT from TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID AND A.BASEPACK = B.P1_BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.DEPOT = C.DEPOT AND B.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND C.BRANCH=A.BRANCH AND C.CLUSTER=A.CLUSTER WHERE A.PROMO_ID=:promoId AND B.ACTIVE=1");
			for (int i = 0; i < promoId.length; i++) {
				dpPerSplit = 0;
				dpQtySplit = 0;
				query.setString("promoId", promoId[i]);
				List list = query.list();
				for (int j = 0; j < list.size(); j++) {
					Object[] obj = (Object[]) list.get(j);
					ArrayList<String> dataObj = new ArrayList<String>();
					dpPerSplit += Double.parseDouble(obj[7].toString());
					dpQtySplit += Double.parseDouble(obj[8].toString());
					if (j < list.size() - 1) {
						dataObj.add(obj[0].toString());
						dataObj.add(obj[1].toString());
						dataObj.add(obj[2].toString());
						dataObj.add(obj[3].toString());
						dataObj.add("");
						dataObj.add("");
						dataObj.add(obj[4].toString());
						dataObj.add(obj[5].toString());
						dataObj.add(obj[6].toString());
						dataObj.add(obj[7].toString());
						dataObj.add(obj[8].toString());
						dataObj.add(obj[9].toString());
					} else {
						dataObj.add(obj[0].toString());
						dataObj.add(obj[1].toString());
						dataObj.add(obj[2].toString());
						dataObj.add(obj[3].toString());
						dataObj.add(String.valueOf(Math.round(dpPerSplit)));
						dataObj.add(String.valueOf(dpQtySplit));
						dataObj.add(obj[4].toString());
						dataObj.add(obj[5].toString());
						dataObj.add(obj[6].toString());
						dataObj.add(obj[7].toString());
						dataObj.add(obj[8].toString());
						dataObj.add(obj[9].toString());
					}
					obj = null;
					downloadDataList.add(dataObj);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception :", ex);
			return null;
		}
		return downloadDataList;
	}

	@Override
	public String uploadKamL1(L1CollaborationBean[] bean, String userId) throws Exception {
		String response = null;
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_KAM_L1_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			// kiran - big int to int changes
			// Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger) queryToCheck.uniqueResult()).intValue();
			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_KAM_L1_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}
			DecimalFormat df = new DecimalFormat("##.###");
			Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_KAM_L1_TEMP);
			for (int i = 0; i < bean.length; i++) {

				query.setString(0, bean[i].getPromoId());
				query.setString(1, bean[i].getGeography());
				query.setString(2, bean[i].getBasepack());
				query.setString(3, bean[i].getCustomerChainL1());
				if (!bean[i].getDpPerSplit().equals("") && bean[i].getDpPerSplit().endsWith("%")) {
					String dpPerSplit = bean[i].getDpPerSplit().substring(0, bean[i].getDpPerSplit().indexOf('%'));
					query.setString(4, dpPerSplit);
				} else {
					query.setString(4, "");
				}
				if (!bean[i].getDpQtySplit().equalsIgnoreCase("")) {
					query.setString(5, df.format(Double.parseDouble(bean[i].getDpQtySplit())));
				} else {
					query.setString(5, bean[i].getDpQtySplit());
				}
				query.setString(6, bean[i].getDepot());
				query.setString(7, bean[i].getBranch());
				query.setString(8, bean[i].getCluster());
				if (!bean[i].getDepotPer().equals("") && bean[i].getDepotPer().endsWith("%")) {
					String depotPer = bean[i].getDepotPer().substring(0, bean[i].getDepotPer().indexOf('%'));
					query.setString(9, depotPer);
				} else {
					query.setString(9, "");
				}
				query.setString(10, df.format(Double.parseDouble(bean[i].getDepotQty())));
				query.setString(11, userId);
				query.setString(12, df.format(Double.parseDouble(bean[i].getKamSplit())));
				query.executeUpdate();
			}
			response = validateRecordKamL1(bean, userId);
			if (response.equals("SUCCESS_FILE")) {
				updateDepotPercentageAndQuantityKamL1(bean, userId);
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			throw new Exception();
		}
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private String validateRecordKamL1(L1CollaborationBean[] bean, String userId) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			/*
			 * Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
			 * "SELECT A.PROMO_ID,B.QUANTITY,SUM(CASE WHEN A.DP_PER_SPLIT<>'' THEN CAST(A.DP_PER_SPLIT AS DECIMAL(4,1)) ELSE 0 END),SUM(CASE WHEN A.DP_QTY_SPLIT<>'' THEN CAST(A.DP_QTY_SPLIT AS INTEGER) ELSE 0 END), SUM(CAST(A.DEPOT_PER AS DECIMAL(4,1))),SUM(CAST(A.DEPOT_QTY AS INTEGER)) FROM TBL_PROCO_KAM_L1_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY"
			 * );
			 */
			/*
			 * Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
			 * "SELECT A.PROMO_ID,B.QUANTITY,SUM(CASE WHEN A.DP_PER_SPLIT<>'' THEN CAST(A.DP_PER_SPLIT AS DECIMAL(4,1)) ELSE 0 END),SUM(CASE WHEN A.DP_QTY_SPLIT<>'' THEN CAST(A.DP_QTY_SPLIT AS INTEGER) ELSE 0 END), SUM(CAST(A.DEPOT_PER AS DECIMAL(4,1))),SUM(CAST(ROUND(SUBSTR(A.DEPOT_QTY,1,15),10) AS DECIMAL (15,2))) FROM TBL_PROCO_KAM_L1_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY"
			 * );
			 */

			Query getMasterDepoQtyquery = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT  CAST(ROUND(QUANTITY,10) AS DECIMAL (15,2)) FROM  TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE  PROMO_ID=:promoId AND DEPOT=:depo AND BRANCH=:branch AND CLUSTER=:cluster");
			for (int i = 0; i < bean.length; i++) {
				getMasterDepoQtyquery.setString("promoId", bean[i].getPromoId());
				getMasterDepoQtyquery.setString("depo", bean[i].getDepot());
				getMasterDepoQtyquery.setString("branch", bean[i].getBranch());
				getMasterDepoQtyquery.setString("cluster", bean[i].getCluster());
				BigDecimal DepQuantity = (BigDecimal) getMasterDepoQtyquery.uniqueResult();
				float kamSplitQty = Float.parseFloat(bean[i].getKamSplit());
				if (DepQuantity != null && DepQuantity.compareTo(BigDecimal.ZERO) == 0 && kamSplitQty != 0) {
					res = "ERROR_FILE";
					updateQtyErrorMessageInTempKamL1("KAM Split is not allowed for this DEPOT.", userId, bean[i]);
				}
			}

			Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
					// Commented By Sarin - Change in Cast - 20Nov2020
					// "SELECT A.PROMO_ID,B.QUANTITY,SUM(CASE WHEN A.DP_PER_SPLIT<>'' THEN
					// CAST(A.DP_PER_SPLIT AS DECIMAL(4,1)) ELSE 0 END),SUM(CASE WHEN
					// A.DP_QTY_SPLIT<>'' THEN CAST(A.DP_QTY_SPLIT AS INTEGER) ELSE 0 END),
					// SUM(CAST(A.DEPOT_PER AS
					// DECIMAL(4,1))),SUM(CAST(ROUND(SUBSTR(A.KAM_SPLIT,1,15),10) AS DECIMAL
					// (15,2))) FROM TBL_PROCO_KAM_L1_TEMP AS A INNER JOIN
					// TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<>
					// '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY");
					"SELECT A.PROMO_ID,B.QUANTITY,SUM(CASE WHEN A.DP_PER_SPLIT<>'' THEN CAST(A.DP_PER_SPLIT AS DECIMAL(4,1)) ELSE 0 END),SUM(CASE WHEN A.DP_QTY_SPLIT<>'' THEN CAST(A.DP_QTY_SPLIT AS UNSIGNED) ELSE 0 END), SUM(CAST(A.DEPOT_PER AS DECIMAL(4,1))),SUM(CAST(ROUND(SUBSTR(A.KAM_SPLIT,1,15),10) AS DECIMAL (15,2))) FROM TBL_PROCO_KAM_L1_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY");

			queryToPromoId.setString("userId", userId);
			Iterator iterator = queryToPromoId.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				String promoId = obj[0].toString();
				int quantity = Integer.parseInt(obj[1].toString());
				double dpPerSplit = Math.round(Double.parseDouble(obj[2].toString()));
				int dpQtySPlit = Integer.parseInt(obj[3].toString());
				double dpQtySPlitValue = Double.parseDouble(obj[3].toString());
				double totalDepotPer = Math.round(Double.parseDouble(obj[4].toString()));
				// int totalDepotQty = Integer.parseInt(obj[5].toString());
				double totalDepotQtyValue = Double.parseDouble(obj[5].toString());

				/*
				 * if (quantity != dpQtySPlit) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "PROMO VOLUME(" + quantity + ") and DP_QTY_SPLIT(" + dpQtySPlit +
				 * ") does not match.^"; updateErrorMessageInTempKamL1(errorMsg, userId,
				 * promoId); }
				 * 
				 * if (dpPerSplit != 100) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "PROMO DP_PER_SPLIT is not 100%.^"; updateErrorMessageInTempKamL1(errorMsg,
				 * userId, promoId); }
				 * 
				 * if (totalDepotPer != 100) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "Total depot percentage is not 100%.^";
				 * updateErrorMessageInTempKamL1(errorMsg, userId, promoId); }
				 */

				// if (dpQtySPlit != totalDepotQty) {
				if ((dpQtySPlitValue - totalDepotQtyValue) > 1) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "KAM_SPLIT does not match total depot quantity.^";
					updateErrorMessageInTempKamL1(errorMsg, userId, promoId);
				}
				if ((dpQtySPlitValue - totalDepotQtyValue) < -1) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "KAM_SPLIT does not match total depot quantity.^";
					updateErrorMessageInTempKamL1(errorMsg, userId, promoId);
				}
				/*
				 * if (dpQtySPlitValue != totalDepotQtyValue) { res = "ERROR_FILE"; errorMsg =
				 * errorMsg + "DP_QTY_SPLIT does not match total depot quantity.^";
				 * updateErrorMessageInTempKamL1(errorMsg, userId, promoId); }
				 */
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	private void updateQtyErrorMessageInTempKamL1(String errorMsg, String userId, L1CollaborationBean bean) {
		try {
			String qry = "UPDATE TBL_PROCO_KAM_L1_TEMP SET ERROR_MSG=:errorMsg WHERE PROMO_ID=:promoId AND DEPOT=:depo AND BRANCH=:branch AND CLUSTER=:cluster";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);

			query.setString("promoId", bean.getPromoId());
			query.setString("depo", bean.getDepot());
			query.setString("branch", bean.getBranch());
			query.setString("cluster", bean.getCluster());
			query.setString("errorMsg", errorMsg);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}

	private void updateErrorMessageInTempKamL1(String errorMsg, String userId, String promoId) {
		try {
			String qry = "UPDATE TBL_PROCO_KAM_L1_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND PROMO_ID=:promoId AND DP_PER_SPLIT<>'' AND DP_QTY_SPLIT<>''";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setString("promoId", promoId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}

	private void updateQtyErrorMessageInTempKamL2(String errorMsg, String userId, L2CollaborationBean bean) {
		try {
			String qry = "UPDATE TBL_PROCO_KAM_L2_TEMP SET ERROR_MSG=:errorMsg WHERE CUSTOMER_CHAIN_L2=:custchainl2 AND PROMO_ID=:promoId AND DEPOT=:depo AND BRANCH=:branch AND CLUSTER=:cluster";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);

			query.setString("promoId", bean.getPromoId());
			query.setString("depo", bean.getDepot());
			query.setString("branch", bean.getBranch());
			query.setString("cluster", bean.getCluster());
			query.setString("custchainl2", bean.getCustomerChainL2());
			query.setString("errorMsg", errorMsg);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}

	private boolean updateDepotPercentageAndQuantityKamL1(L1CollaborationBean[] bean, String userId) {
		boolean res = true;
		DecimalFormat df = new DecimalFormat("##.###");
		try {
			Set<String> promoIdSet = new HashSet<String>();
			List<String> promoIdList = new ArrayList<String>();
			String queryToDelete = "DELETE FROM TBL_PROCO_KAM_L1_TEMP WHERE USER_ID=:userId";
			Query qryToDelete = sessionFactory.getCurrentSession().createNativeQuery(queryToDelete);
			// String qry = "UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET
			// PERCENTAGE=:per,QUANTITY=:qty,USER_ID=:userId WHERE PROMO_ID=:promoId AND
			// DEPOT=:depot";
			String qry = "UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET KAM_SPLIT=:qty,USER_ID=:userId, KAM_SPLIT_STATUS=1 WHERE PROMO_ID=:promoId AND DEPOT=:depot AND BRANCH=:branch AND CLUSTER=:cluster";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			for (int i = 0; i < bean.length; i++) {
				// query.setString("per", bean[i].getDepotPer().substring(0,
				// bean[i].getDepotPer().indexOf('%')));
				// query.setString("qty", bean[i].getDepotQty());
				query.setString("qty", df.format(Double.parseDouble(bean[i].getKamSplit())));
				query.setString("userId", userId);
				query.setString("promoId", bean[i].getPromoId());
				query.setString("depot", bean[i].getDepot());
				query.setString("branch", bean[i].getBranch());
				query.setString("cluster", bean[i].getCluster());
				query.executeUpdate();
				promoIdSet.add(bean[i].getPromoId());
			}
			qryToDelete.setString("userId", userId);
			qryToDelete.executeUpdate();
			promoIdList.addAll(promoIdSet);
			for (int i = 0; i < promoIdList.size(); i++) {
				String promoId = promoIdList.get(0);
				createPromoDAOImpl.saveStatusInStatusTracker(promoId, 5, "", userId);
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getKamL1ErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "select PROMO_ID,GEOGRAPHY,BASEPACK,CUSTOMER_CHAIN_L1,DP_PER_SPLIT,DP_QTY_SPLIT,DEPOT,BRANCH,CLUSTER,DEPOT_PER,DEPOT_QTY,KAM_SPLIT,ERROR_MSG from TBL_PROCO_KAM_L1_TEMP WHERE USER_ID=:userId ORDER BY PROMO_ID";
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

					// dataObj.add(value);
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getL2DepotDisaggregation(ArrayList<String> headerList, String userId,
			String[] promoId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		double dpPerSplit = 0;
		double dpQtySplit = 0;
		try {
			downloadDataList.add(headerList);
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT A.PROMO_ID ,C.GEOGRAPHY ,C.P1_BASEPACK ,C.CUSTOMER_CHAIN_L1 ,B.CUSTOMER_CHAIN_L2 ,CAST(B.PERCENTAGE AS DECIMAL(5, 2)) AS L2PER ,B.QUANTITY AS L2QTY ,E.DEPOT ,E.BRANCH ,E.CLUSTER ,CAST(A.PERCENTAGE AS DECIMAL(5, 2)) AS DEPOT_PER ,A.QUANTITY AS DEPOT_QTY, CASE WHEN A.KAM_SPLIT_STATUS = 0 THEN A.QUANTITY ELSE A.KAM_SPLIT END AS KAM_SPLIT FROM TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL AS A INNER JOIN TBL_PROCO_PROMO_DISAGG_L2_LEVEL AS B ON A.PROMO_ID = A.PROMO_ID AND A.CUSTOMER_CHAIN_L2 = B.CUSTOMER_CHAIN_L2 INNER JOIN TBL_PROCO_PROMOTION_MASTER AS C ON B.PROMO_ID = C.PROMO_ID AND A.PROMO_ID=C.PROMO_ID AND A.BASEPACK=C.P1_BASEPACK AND B.BASEPACK=C.P1_BASEPACK INNER JOIN TBL_PROCO_PROMOTION_DEPOT_LEVEL AS E ON A.DEPOT = E.DEPOT AND A.BRANCH = E.BRANCH AND A.CLUSTER = E.CLUSTER WHERE A.PROMO_ID =:promoId AND C.ACTIVE=1 ORDER BY B.CUSTOMER_CHAIN_L2");
			for (int i = 0; i < promoId.length; i++) {
				dpPerSplit = 0;
				dpQtySplit = 0;
				Map<String, Double> l2Per = new HashMap<String, Double>();
				Map<String, Double> l2Qty = new HashMap<String, Double>();
				String l2 = "";
				String l21 = "";
				query.setString("promoId", promoId[i]);
				List list = query.list();
				for (int j = 0; j < list.size(); j++) {
					Object[] obj = (Object[]) list.get(j);
					ArrayList<String> dataObj = new ArrayList<String>();

					if (!l2Per.containsKey(obj[4].toString())) {
						l2Per.put(obj[4].toString(), Double.parseDouble(obj[5].toString()));
					}

					if (!l2Qty.containsKey(obj[4].toString())) {
						l2Qty.put(obj[4].toString(), Double.parseDouble(obj[6].toString()));
					}
					l21 = obj[4].toString();
					if (j < list.size() - 1) {
						dataObj.add(obj[0].toString());
						dataObj.add(obj[1].toString());
						dataObj.add(obj[2].toString());
						dataObj.add(obj[3].toString());
						dataObj.add("");
						dataObj.add("");
						dataObj.add(obj[4].toString());
						if (l2.equals(l21)) {
							dataObj.add("");
							dataObj.add("");
						} else {
							dataObj.add(obj[5].toString());
							dataObj.add(obj[6].toString());
						}
						dataObj.add(obj[7].toString());
						dataObj.add(obj[8].toString());
						dataObj.add(obj[9].toString());
						dataObj.add(obj[10].toString());
						dataObj.add(obj[11].toString());
						dataObj.add(obj[12].toString());
					} else {
						dataObj.add(obj[0].toString());
						dataObj.add(obj[1].toString());
						dataObj.add(obj[2].toString());
						dataObj.add(obj[3].toString());
						Iterator<Double> iterator = l2Per.values().iterator();
						while (iterator.hasNext()) {
							Double next = iterator.next();
							dpPerSplit += next;
						}
						Iterator<Double> iterator1 = l2Qty.values().iterator();
						while (iterator1.hasNext()) {
							Double next = iterator1.next();
							dpQtySplit += next;
						}
						DecimalFormat df = new DecimalFormat("##.##");
						String format = df.format(dpPerSplit);
						dataObj.add(String.valueOf(Math.round(Double.parseDouble(format))));
						dataObj.add(String.valueOf(dpQtySplit));
						dataObj.add(obj[4].toString());
						if (l2.equals(l21)) {
							dataObj.add("");
							dataObj.add("");
						} else {
							dataObj.add(obj[5].toString());
							dataObj.add(obj[6].toString());
						}
						dataObj.add(obj[7].toString());
						dataObj.add(obj[8].toString());
						dataObj.add(obj[9].toString());
						dataObj.add(obj[10].toString());
						dataObj.add(obj[11].toString());
						dataObj.add(obj[12].toString());
					}
					l2 = l21;
					obj = null;
					downloadDataList.add(dataObj);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception :", ex);
			return null;
		}
		return downloadDataList;
	}

	@Override
	public String uploadKamL2(L2CollaborationBean[] bean, String userId) throws Exception {
		String response = null;
		DecimalFormat df = new DecimalFormat("##.###");
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_KAM_L2_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			// kiran - bigint to int changes
			// Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger) queryToCheck.uniqueResult()).intValue();
			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_KAM_L2_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}

			Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_KAM_L2_TEMP);
			for (int i = 0; i < bean.length; i++) {
				query.setString(0, bean[i].getPromoId());
				query.setString(1, bean[i].getGeography());
				query.setString(2, bean[i].getBasepack());
				query.setString(3, bean[i].getCustomerChainL1());
				if (!bean[i].getL1DpPerSplit().equals("") && bean[i].getL1DpPerSplit().endsWith("%")) {
					String dpPerSplit = bean[i].getL1DpPerSplit().substring(0, bean[i].getL1DpPerSplit().indexOf('%'));
					query.setString(4, dpPerSplit);
				} else {
					query.setString(4, "");
				}
				// --query.setString(5, bean[i].getL1DpQtySplit());

				if (!bean[i].getL1DpQtySplit().equalsIgnoreCase("")) {
					query.setString(5, df.format(Double.parseDouble(bean[i].getL1DpQtySplit())));
				} else {
					query.setString(5, bean[i].getL1DpQtySplit());
				}

				query.setString(6, bean[i].getCustomerChainL2());
				if (!bean[i].getL2DpPerSplit().equals("") && bean[i].getL2DpPerSplit().endsWith("%")) {
					String dpPerSplit = bean[i].getL2DpPerSplit().substring(0, bean[i].getL2DpPerSplit().indexOf('%'));
					query.setString(7, dpPerSplit);
				} else {
					query.setString(7, "");
				}
				// -- query.setString(8, bean[i].getL2DpQtySplit());

				if (!bean[i].getL2DpQtySplit().equalsIgnoreCase("")) {
					query.setString(8, df.format(Double.parseDouble(bean[i].getL2DpQtySplit())));
				} else {
					query.setString(8, bean[i].getL2DpQtySplit());
				}
				query.setString(9, bean[i].getDepot());
				query.setString(10, bean[i].getBranch());
				query.setString(11, bean[i].getCluster());
				if (!bean[i].getDepotPer().equals("") && bean[i].getDepotPer().endsWith("%")) {
					String depotPer = bean[i].getDepotPer().substring(0, bean[i].getDepotPer().indexOf('%'));
					query.setString(12, depotPer);
				} else {
					query.setString(12, "");
				}
				// query.setString(13, bean[i].getDepotQty());
				query.setString(13, df.format(Double.parseDouble(bean[i].getDepotQty())));
				query.setString(14, userId);
				query.setString(15, df.format(Double.parseDouble(bean[i].getKamSplit())));
				query.executeUpdate();
			}
			response = validateRecordKamL2(bean, userId);
			if (response.equals("SUCCESS_FILE")) {
				updateDepotPercentageAndQuantityKamL2(bean, userId);
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			throw new Exception();
		}
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private String validateRecordKamL2(L2CollaborationBean[] bean, String userId) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			/*
			 * Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
			 * "SELECT A.PROMO_ID,B.QUANTITY, SUM(CASE WHEN A.L1_DP_PER_SPLIT<>'' THEN CAST(A.L1_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) AS L1_DP_PER_SPLIT, SUM(CASE WHEN A.L1_DP_QTY_SPLIT<>'' THEN CAST(A.L1_DP_QTY_SPLIT AS INTEGER) ELSE 0 END) L1_DP_QTY_SPLIT, SUM(CASE WHEN A.L2_DP_PER_SPLIT<>'' THEN CAST(A.L2_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) L2_DP_PER_SPLIT, SUM(CASE WHEN A.L2_DP_QTY_SPLIT<>'' THEN CAST(A.L2_DP_QTY_SPLIT AS INTEGER) ELSE 0 END) L2_DP_QTY_SPLIT, SUM(CAST(A.DEPOT_QTY AS INTEGER)) AS DEPOT_QTY FROM TBL_PROCO_KAM_L2_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY"
			 * );
			 */
			/*
			 * Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
			 * "SELECT A.PROMO_ID,B.QUANTITY, SUM(CASE WHEN A.L1_DP_PER_SPLIT<>'' THEN CAST(A.L1_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) AS L1_DP_PER_SPLIT, SUM(CASE WHEN A.L1_DP_QTY_SPLIT<>'' THEN CAST(A.L1_DP_QTY_SPLIT AS INTEGER) ELSE 0 END) L1_DP_QTY_SPLIT, SUM(CASE WHEN A.L2_DP_PER_SPLIT<>'' THEN CAST(A.L2_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) L2_DP_PER_SPLIT, SUM(CASE WHEN A.L2_DP_QTY_SPLIT<>'' THEN CAST(A.L2_DP_QTY_SPLIT AS INTEGER) ELSE 0 END) L2_DP_QTY_SPLIT, SUM(CAST(ROUND(SUBSTR(A.DEPOT_QTY,1,15),10) AS DECIMAL  (10,2))) AS DEPOT_QTY FROM TBL_PROCO_KAM_L2_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY"
			 * );
			 */

			Query getMasterDepoQtyquery = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT  CAST(ROUND(QUANTITY,10) AS DECIMAL (15,2)) FROM  TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL WHERE CUSTOMER_CHAIN_L2=:custchainl2 AND PROMO_ID=:promoId AND DEPOT=:depo AND BRANCH=:branch AND CLUSTER=:cluster");
			for (int i = 0; i < bean.length; i++) {
				getMasterDepoQtyquery.setString("promoId", bean[i].getPromoId());
				getMasterDepoQtyquery.setString("depo", bean[i].getDepot());
				getMasterDepoQtyquery.setString("branch", bean[i].getBranch());
				getMasterDepoQtyquery.setString("cluster", bean[i].getCluster());
				getMasterDepoQtyquery.setString("custchainl2", bean[i].getCustomerChainL2());
				BigDecimal DepQuantity = (BigDecimal) getMasterDepoQtyquery.uniqueResult();
				float kamSplitQty = Float.parseFloat(bean[i].getKamSplit());
				if (DepQuantity != null && DepQuantity.compareTo(BigDecimal.ZERO) == 0 && kamSplitQty != 0) {
					res = "ERROR_FILE";
					updateQtyErrorMessageInTempKamL2("KAM Split is not allowed for this DEPOT.", userId, bean[i]);
				}
			}

			Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
					// Commented By Sarin - Change in Cast - 20Nov2020
					// "SELECT A.PROMO_ID,B.QUANTITY, SUM(CASE WHEN A.L1_DP_PER_SPLIT<>'' THEN
					// CAST(A.L1_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) AS L1_DP_PER_SPLIT,
					// SUM(CASE WHEN A.L1_DP_QTY_SPLIT<>'' THEN CAST(A.L1_DP_QTY_SPLIT AS INTEGER)
					// ELSE 0 END) L1_DP_QTY_SPLIT, SUM(CASE WHEN A.L2_DP_PER_SPLIT<>'' THEN
					// CAST(A.L2_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) L2_DP_PER_SPLIT, SUM(CASE
					// WHEN A.L2_DP_QTY_SPLIT<>'' THEN CAST(A.L2_DP_QTY_SPLIT AS INTEGER) ELSE 0
					// END) L2_DP_QTY_SPLIT, SUM(CAST(ROUND(SUBSTR(A.KAM_SPLIT,1,15),10) AS DECIMAL
					// (10,2))) AS DEPOT_QTY FROM TBL_PROCO_KAM_L2_TEMP AS A INNER JOIN
					// TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<>
					// '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY");
					"SELECT A.PROMO_ID,B.QUANTITY, SUM(CASE WHEN A.L1_DP_PER_SPLIT<>'' THEN CAST(A.L1_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) AS L1_DP_PER_SPLIT, SUM(CASE WHEN A.L1_DP_QTY_SPLIT<>'' THEN CAST(A.L1_DP_QTY_SPLIT AS UNSIGNED) ELSE 0 END) L1_DP_QTY_SPLIT, SUM(CASE WHEN A.L2_DP_PER_SPLIT<>'' THEN CAST(A.L2_DP_PER_SPLIT AS DECIMAL(5,2)) ELSE 0 END) L2_DP_PER_SPLIT, SUM(CASE WHEN A.L2_DP_QTY_SPLIT<>'' THEN CAST(A.L2_DP_QTY_SPLIT AS UNSIGNED) ELSE 0 END) L2_DP_QTY_SPLIT, SUM(CAST(ROUND(SUBSTR(A.KAM_SPLIT,1,15),10) AS DECIMAL  (10,2))) AS DEPOT_QTY FROM TBL_PROCO_KAM_L2_TEMP AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID = B.PROMO_ID WHERE B.QUANTITY<> '' AND A.USER_ID =:userId AND B.ACTIVE=1 GROUP BY A.PROMO_ID,B.QUANTITY");

			// ,SUM(CAST(ROUND(SUBSTR(A.DEPOT_QTY,1,15),10) AS DECIMAL))
			queryToPromoId.setString("userId", userId);
			Iterator iterator = queryToPromoId.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				String promoId = obj[0].toString();
				int quantity = Integer.parseInt(obj[1].toString());
				double l1DpPerSplit = Math.round(Double.parseDouble(obj[2].toString()));
				int l1DpQtySPlit = Integer.parseInt(obj[3].toString());
				double l2DpPerSplit = Math.round(Double.parseDouble(obj[4].toString()));
				int l2DpQtySPlit = Integer.parseInt(obj[5].toString());
				// int totalDepotQty = Integer.parseInt(obj[6].toString());
				int totalDepotQty = (int) Math.round(Double.parseDouble((obj[6].toString())));
				// Commented By Sarin - Change in Cast - 20Nov2020
				// Query
				// qryToGetAddDepotQty=sessionFactory.getCurrentSession().createNativeQuery("SELECT
				// CAST(DEPOT_QTY AS INTEGER) FROM TBL_PROCO_KAM_L2_TEMP WHERE DEPOT_PER=0 AND
				// USER_ID=:userId AND PROMO_ID=:promoId");
				Query qryToGetAddDepotQty = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT CAST(DEPOT_QTY AS UNSIGNED) FROM TBL_PROCO_KAM_L2_TEMP WHERE DEPOT_PER=0 AND USER_ID=:userId AND PROMO_ID=:promoId");
				qryToGetAddDepotQty.setString("userId", userId);
				qryToGetAddDepotQty.setString("promoId", promoId);
				Integer addDepotQty = (Integer) qryToGetAddDepotQty.uniqueResult();
				if (addDepotQty != null) {
					l1DpQtySPlit += addDepotQty;
					l2DpQtySPlit += addDepotQty;
				}

				/*
				 * if (quantity != l1DpQtySPlit) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "PROMO VOLUME(" + quantity + ") and L1_DP_QTY_SPLIT(" + l1DpQtySPlit +
				 * ") does not match.^"; updateErrorMessageInTempKamL2(errorMsg, userId,
				 * promoId); }
				 */

				/*
				 * if (l1DpPerSplit != 100) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "PROMO L1_DP_PER_SPLIT is not 100%.^";
				 * updateErrorMessageInTempKamL2(errorMsg, userId, promoId); }
				 */

				/*
				 * if (l1DpQtySPlit != totalDepotQty) { res = "ERROR_FILE"; errorMsg = errorMsg
				 * + "L1_DP_QTY_SPLIT does not match total depot quantity.^";
				 * updateErrorMessageInTempKamL2(errorMsg, userId, promoId); }
				 */

				/*
				 * if (l2DpQtySPlit != totalDepotQty) { res = "ERROR_FILE"; errorMsg = errorMsg
				 * + "L2_DP_QTY_SPLIT does not match total depot quantity.^";
				 * updateErrorMessageInTempKamL2(errorMsg, userId, promoId); }
				 */

				if ((l2DpQtySPlit - totalDepotQty) > 1) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "KAM_SPLIT does not match total depot quantity.^";
					updateErrorMessageInTempKamL2(errorMsg, userId, promoId);
				}
				if ((l2DpQtySPlit - totalDepotQty) < -1) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "KAM_SPLIT does not match total depot quantity.^";
					updateErrorMessageInTempKamL2(errorMsg, userId, promoId);
				}

				/*
				 * if (l1DpPerSplit != l2DpPerSplit) { res = "ERROR_FILE"; errorMsg = errorMsg +
				 * "L1_DP_PER_SPLIT does not match total L2_DP_PER_SPLIT quantity.^";
				 * updateErrorMessageInTempKamL2(errorMsg, userId, promoId); }
				 */

			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	private void updateErrorMessageInTempKamL2(String errorMsg, String userId, String promoId) {
		try {
			String qry = "UPDATE TBL_PROCO_KAM_L2_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND PROMO_ID=:promoId AND L1_DP_PER_SPLIT<>'' AND L1_DP_QTY_SPLIT<>''";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setString("promoId", promoId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}

	private boolean updateDepotPercentageAndQuantityKamL2(L2CollaborationBean[] bean, String userId) {
		boolean res = true;
		try {
			Set<String> promoIdSet = new HashSet<String>();
			List<String> promoIdList = new ArrayList<String>();
			DecimalFormat df = new DecimalFormat("##.###");
			String queryToDelete = "DELETE FROM TBL_PROCO_KAM_L2_TEMP WHERE USER_ID=:userId";
			Query qryToDelete = sessionFactory.getCurrentSession().createNativeQuery(queryToDelete);

			/*
			 * String qryToUpdateL2 =
			 * "UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET PERCENTAGE=:per,QUANTITY=:qty,USER_ID=:userId WHERE PROMO_ID=:promoId AND CUSTOMER_CHAIN_L2=:customerChainL2"
			 * ; Query queryToUpdateL2 =
			 * sessionFactory.getCurrentSession().createNativeQuery(qryToUpdateL2);
			 */

			String qryToUpdateL2 = "UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET KAM_SPLIT=:qty, KAM_SPLIT_STATUS=1,USER_ID=:userId WHERE PROMO_ID=:promoId AND CUSTOMER_CHAIN_L2=:customerChainL2";
			Query queryToUpdateL2 = sessionFactory.getCurrentSession().createNativeQuery(qryToUpdateL2);
// KAM_SPLIT:qty, KAM_SPLIT_STATUS=1
			/*
			 * String qryToUpdateDepot =
			 * "UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET PERCENTAGE=:per,QUANTITY=:qty WHERE PROMO_ID=:promoId AND CUSTOMER_CHAIN_L2=:customerChainL2 AND DEPOT=:depot"
			 * ; Query queryToUpdateDepot =
			 * sessionFactory.getCurrentSession().createNativeQuery(qryToUpdateDepot);
			 */
			String qryToUpdateDepot = "UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET KAM_SPLIT=:qty, KAM_SPLIT_STATUS=1 WHERE PROMO_ID=:promoId AND CUSTOMER_CHAIN_L2=:customerChainL2 AND DEPOT=:depot AND BRANCH=:branch AND CLUSTER=:cluster";
			Query queryToUpdateDepot = sessionFactory.getCurrentSession().createNativeQuery(qryToUpdateDepot);

			for (int i = 0; i < bean.length; i++) {
				if (bean[i].getL2DpPerSplit() != null && !bean[i].getL2DpPerSplit()
						.equals("")) {/*
										 * queryToUpdateL2.setString("per", bean[i].getL2DpPerSplit().substring(0,
										 * bean[i].getL2DpPerSplit().indexOf('%'))); queryToUpdateL2.setString("qty",
										 * bean[i].getL2DpQtySplit()); queryToUpdateL2.setString("userId", userId);
										 * queryToUpdateL2.setString("promoId", bean[i].getPromoId());
										 * queryToUpdateL2.setString("customerChainL2", bean[i].getCustomerChainL2());
										 * queryToUpdateL2.executeUpdate();
										 */

					queryToUpdateL2.setString("qty", df.format(Double.parseDouble(bean[i].getKamSplit())));
					queryToUpdateL2.setString("userId", userId);
					queryToUpdateL2.setString("promoId", bean[i].getPromoId());
					queryToUpdateL2.setString("customerChainL2", bean[i].getCustomerChainL2());
					queryToUpdateL2.executeUpdate();

				}
				promoIdSet.add(bean[i].getPromoId());
			}
			for (int i = 0; i < bean.length; i++) {/*
													 * queryToUpdateDepot.setString("per",
													 * bean[i].getDepotPer().substring(0,
													 * bean[i].getDepotPer().indexOf('%')));
													 * queryToUpdateDepot.setString("qty", bean[i].getDepotQty());
													 * queryToUpdateDepot.setString("promoId", bean[i].getPromoId());
													 * queryToUpdateDepot.setString("customerChainL2",
													 * bean[i].getCustomerChainL2());
													 * queryToUpdateDepot.setString("depot", bean[i].getDepot());
													 * queryToUpdateDepot.executeUpdate();
													 */

				queryToUpdateDepot.setString("qty", df.format(Double.parseDouble(bean[i].getKamSplit())));
				queryToUpdateDepot.setString("promoId", bean[i].getPromoId());
				queryToUpdateDepot.setString("customerChainL2", bean[i].getCustomerChainL2());
				queryToUpdateDepot.setString("depot", bean[i].getDepot());
				queryToUpdateDepot.setString("branch", bean[i].getBranch());
				queryToUpdateDepot.setString("cluster", bean[i].getCluster());
				queryToUpdateDepot.executeUpdate();

			}
			qryToDelete.setString("userId", userId);
			qryToDelete.executeUpdate();
			promoIdList.addAll(promoIdSet);
			for (int i = 0; i < promoIdList.size(); i++) {
				String promoId = promoIdList.get(0);
				createPromoDAOImpl.saveStatusInStatusTracker(promoId, 4, "", userId);
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getKamL2ErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "select PROMO_ID,GEOGRAPHY,BASEPACK,CUSTOMER_CHAIN_L1,L1_DP_PER_SPLIT,L1_DP_QTY_SPLIT,CUSTOMER_CHAIN_L2,L2_DP_PER_SPLIT,L2_DP_QTY_SPLIT,DEPOT,BRANCH,CLUSTER,DEPOT_PER,DEPOT_QTY,KAM_SPLIT,ERROR_MSG from TBL_PROCO_KAM_L2_TEMP WHERE USER_ID=:userId ORDER BY PROMO_ID,L2_DP_PER_SPLIT DESC";
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
