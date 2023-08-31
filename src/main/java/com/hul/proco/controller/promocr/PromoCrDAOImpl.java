package com.hul.proco.controller.promocr;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreatePromoDAOImpl;

@Repository
public class PromoCrDAOImpl implements PromoCrDAO {

	private Logger logger = Logger.getLogger(PromoCrDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CreatePromoDAOImpl createPromoDaoImpl;
	


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String userId, String roleId,String moc, String searchParameter) {

		List<PromoCrBean> promoList = new ArrayList<>();
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - rownumber changes
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY'), VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.REASON,A.REMARK,A.CHANGES_MADE, "
					+ "A.UPDATE_STAMP,ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID WHERE A.ACTIVE=1 ";*/
			//Garima - changes for VARCHAR_FORMAT
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, DATE_FORMAT(A.START_DATE,'%d/%m/%Y'), DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.REASON,A.REMARK,A.CHANGES_MADE, "
					+ "A.UPDATE_STAMP,ROW_NUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID WHERE A.ACTIVE=1 ";
			
			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
			}
			if (!custChainL1.equalsIgnoreCase("All")) {
				// promoQuery += "AND A.CUSTOMER_CHAIN_L1 IN (:custChainL1) ";
				if (custL1.size() == 1) {
					promoQuery += "AND (A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(0) + "%') ";
				} else {
					for (int i = 0; i < custL1.size(); i++) {
						if (i == 0) {
							promoQuery += "AND (A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%' ";
						} else if (i < custL1.size() - 1) {
							promoQuery += "OR A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%' ";
						} else if (i == custL1.size() - 1) {
							promoQuery += "OR A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%') ";
						}
					}
				}
			}
			if (!custChainL2.equalsIgnoreCase("All")) {
				// promoQuery += "AND A.CUSTOMER_CHAIN_L2 IN (:custChainL2) ";
				if (custL2.size() == 1) {
					promoQuery += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(0) + "%') ";
				} else {
					for (int i = 0; i < custL2.size(); i++) {
						if (i == 0) {
							promoQuery += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i < custL2.size() - 1) {
							promoQuery += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i == custL2.size() - 1) {
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
				promoQuery += "AND A.OFFER_MODALITY = '" + modality + "' ";
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
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE AND ";
			promoQuery += " AND ";
			if (roleId.equalsIgnoreCase("NCMM")) {
				promoQuery += " A.STATUS IN(7,8,10,17,18,20,27,28,30) AND A.STATUS NOT IN(9,19,29) ";
			} else {
				promoQuery += " A.STATUS IN(7,8,9,17,18,19,27,28,29) AND A.STATUS NOT IN(10,20,30) ";
			}
			*/
			//Added by Kavitha D-SPRINT 10 changes
			
			promoQuery = /*" SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.PROMO_ID AS ORIGINAL_ID,PM.START_DATE,PM.END_DATE,"
					+ " PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,PM.OFFER_TYPE,PM.OFFER_MODALITY, "
					+ " PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,PSM.STATUS, PM.CREATED_BY, SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE, PM.TEMPLATE_TYPE AS REMARKS, "
					+ " 'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS "; */
					
					//Changed by Kavitha D-SPRINT 15
					" SELECT * FROM (SELECT PM.PROMO_ID,PM.START_DATE,PM.END_DATE,"
					+ "PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,PM.OFFER_TYPE,PM.OFFER_MODALITY, "
					+ "PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,PSM.STATUS,PM.SALES_CATEGORY,"
					+ "PM.TEMPLATE_TYPE AS PROMO_ENTRY_TYPE,PM.CR_SOL_TYPE,"
					+ "(CASE WHEN PM.STATUS=38 THEN 'ACCEPTED' WHEN PM.STATUS=39 THEN 'REJECTED' WHEN PM.STATUS=43 THEN 'PENDING' END) AS REMARKS"
					+ ",ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ "FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS ";
			
				if (roleId.equalsIgnoreCase("NCMM")) {
				
				//promoQuery += " WHERE (PM.STATUS IN('3','39') OR (PM.STATUS IN ('1','39') AND (PM.TEMPLATE_TYPE = 'NE' OR PM.CR_SOL_TYPE='Additional Quantity'))) AND PM.MOC='"+moc+"' ";// Changed by kajal G for Sprint -11
				promoQuery += " WHERE (PM.STATUS IN('3','39','43','38') OR (PM.STATUS IN ('1','39','43','38') AND (PM.TEMPLATE_TYPE = 'NE' OR PM.TEMPLATE_TYPE = 'CR'))) AND PM.MOC='"+moc+"' ";// Changed by Kavitha D for Sprint -15

				}
			
				if(searchParameter!=null && searchParameter.length()>0){
					promoQuery +="AND UCASE(PM.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
				}
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP";
			} else {
				promoQuery += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND " + pageDisplayLength + " ";
			}
			//System.out.println(promoQuery);

			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);

			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoCrBean promocrBean = new PromoCrBean();
				/*promoBean.setPromo_id(obj[0].toString());
				promoBean.setBasepack(obj[1].toString());
				promoBean.setOffer_desc(obj[2].toString());
				promoBean.setOffer_type(obj[3].toString());
				promoBean.setOffer_modality(obj[4].toString());
				promoBean.setGeography(obj[5].toString());
				promoBean.setQuantity((obj[6] == null || obj[6].toString().equals("")) ? "" : obj[6].toString());
				promoBean.setUom(obj[7].toString());
				promoBean.setOffer_value(obj[8].toString());
				promoBean.setMoc(obj[9].toString());
				promoBean.setCustomer_chain_l1(obj[10].toString());
				promoBean
						.setKitting_value((obj[11] == null || obj[11].toString().equals("")) ? "" : obj[11].toString());
				promoBean.setStatus(obj[12] == null ? "" : obj[12].toString());
				promoBean.setStartDate(obj[13] == null ? "" : obj[13].toString());
				promoBean.setEndDate(obj[14] == null ? "" : obj[14].toString());
				promoBean.setReason(obj[15] == null ? "" : obj[15].toString());
				promoBean.setRemark(obj[16] == null ? "" : obj[16].toString());
				promoBean.setChangesMade(obj[17] == null ? "" : obj[17].toString());*/
				
				promocrBean.setPromo_id(obj[0]== null ? "" :obj[0].toString());
				//promocrBean.setOriginalId(obj[1]== null ? "" :obj[1].toString());
				promocrBean.setStartDate(obj[1]== null ? "" : obj[1].toString());
				promocrBean.setEndDate(obj[2]== null ? "" : obj[2].toString());
				promocrBean.setMoc(obj[3]== null ? "" : obj[3].toString());
				promocrBean.setCustomer_chain_l1(obj[4]== null ? "" :obj[4].toString());
				promocrBean.setBasepack(obj[5]== null ? "" :obj[5].toString());
				promocrBean.setOffer_desc(obj[6]== null ? "" :obj[6].toString());
				promocrBean.setOffer_type(obj[7]== null ? "" :obj[7].toString());
				promocrBean.setOffer_modality(obj[8]== null ? "" :obj[8].toString());
				promocrBean.setGeography(obj[9]== null ? "" :obj[9].toString());
				promocrBean.setQuantity((obj[10] == null || obj[10].toString().equals("")) ? "" : obj[10].toString());
				promocrBean.setOffer_value(obj[11]== null ? "" :obj[11].toString());
				promocrBean.setStatus(obj[12]== null ? "" :obj[12].toString());
				promocrBean.setCategory(obj[13]== null ? "" :obj[13].toString());
				promocrBean.setTemplatetype(obj[14]== null ? "" :obj[14].toString());
				promocrBean.setSoltype(obj[15]== null ? "" :obj[15].toString());
				promocrBean.setRemark(obj[16]== null ? "" :obj[16].toString());
				/*promocrBean.setUserId(obj[14]== null ? "" :obj[14].toString());//Commented and added by Kavitha D-SPRINT 15 changes
				promocrBean.setChangeDate(obj[15]== null ? "" :obj[15].toString());
				promocrBean.setInvestmentType(obj[17]== null ? "" :obj[17].toString());
				promocrBean.setSolCode(obj[18]== null ? "" :obj[18].toString());
				promocrBean.setPromotionMechanics(obj[19]== null ? "" :obj[19].toString());
				promocrBean.setSolCodeStatus(obj[20]== null ? "" :obj[20].toString());*/
				promoList.add(promocrBean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoListRowCount(String userId, String roleId,String moc) {

		//kiran - bigint to int changes
		//List<Integer> list = null;
		List<BigInteger> list = null;
		//Commented by Kavitha D-Sprint 10 changes
		/*List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {

			String rowCount = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ "INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID WHERE A.ACTIVE=1 ";

			if (!cagetory.equalsIgnoreCase("All")) {
				rowCount += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				rowCount += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				rowCount += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
			}
			if (!custChainL1.equalsIgnoreCase("All")) {
				// promoQuery += "AND A.CUSTOMER_CHAIN_L1 IN (:custChainL1) ";
				if (custL1.size() == 1) {
					rowCount += "AND (A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(0) + "%') ";
				} else {
					for (int i = 0; i < custL1.size(); i++) {
						if (i == 0) {
							rowCount += "AND (A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%' ";
						} else if (i < custL1.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%' ";
						} else if (i == custL1.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L1 LIKE '%" + custL1.get(i) + "%') ";
						}
					}
				}
			}
			if (!custChainL2.equalsIgnoreCase("All")) {
				// promoQuery += "AND A.CUSTOMER_CHAIN_L2 IN (:custChainL2) ";
				if (custL2.size() == 1) {
					rowCount += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(0) + "%') ";
				} else {
					for (int i = 0; i < custL2.size(); i++) {
						if (i == 0) {
							rowCount += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i < custL2.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i == custL2.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%') ";
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
					rowCount += "AND (A.GEOGRAPHY like '%" + geography + "%' ";
					if (clusterList != null) {
						for (int i = 0; i < clusterList.size(); i++) {
							if (i < clusterList.size() - 1) {
								rowCount += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%' ";
							} else if (i == clusterList.size() - 1) {
								rowCount += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%') ";
							}
						}
					}
				} else if (geography.startsWith("CL")) {
					String cluster = geography.substring(0, geography.indexOf(':'));
					rowCount += "AND (A.GEOGRAPHY like '%" + cluster + "%') ";
				} else {
					geography = geography.substring(0, geography.indexOf(':'));
					rowCount += "AND A.GEOGRAPHY like '%" + geography + "%' ";
				}
			}
			if (!offerType.equalsIgnoreCase("All")) {
				rowCount += "AND A.OFFER_TYPE = '" + offerType + "' ";
			}
			if (!modality.equalsIgnoreCase("All")) {
				rowCount += "AND A.OFFER_MODALITY = '" + modality + "' ";
			}
			if (!year.equalsIgnoreCase("All")) {
				rowCount += "AND A.YEAR = '" + year + "' ";
			}
			if (!moc.equalsIgnoreCase("All")) {
				if (moc.equals("Q1")) {
					rowCount += "AND (A.MOC='Q1' OR A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') ";
				} else if (moc.equals("Q2")) {
					rowCount += "AND (A.MOC='Q2' OR A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') ";
				} else if (moc.equals("Q3")) {
					rowCount += "AND (A.MOC='Q3' OR A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') ";
				} else if (moc.equals("Q4")) {
					rowCount += "AND (A.MOC='Q4' OR A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') ";
				} else {
					rowCount += "AND A.MOC = '" + moc + "' ";
				}
			}
			//rowCount += " AND A.START_DATE>CURRENT_DATE AND ";
			rowCount += " AND ";
			if (roleId.equalsIgnoreCase("NCMM")) {
				rowCount += " (A.STATUS=7 OR A.STATUS=8 OR A.STATUS=10 OR A.STATUS=17 OR A.STATUS=18 OR A.STATUS=20 OR A.STATUS=27 OR A.STATUS=28 OR A.STATUS=30) AND (A.STATUS<>9 AND A.STATUS<>19 AND A.STATUS<>29) ";
			} else {
				rowCount += " (A.STATUS=7 OR A.STATUS=8 OR A.STATUS=9 OR A.STATUS=17 OR A.STATUS=18 OR A.STATUS=19 OR A.STATUS=27 OR A.STATUS=28 OR A.STATUS=29) AND (A.STATUS<>10 AND A.STATUS<>20 AND A.STATUS<>30) ";
			}
*/
		//Added by Kavitha D-SPRINT 10 Changes
		try {

			String rowCount = " SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS ";
			
			if (roleId.equalsIgnoreCase("NCMM")) {
				
				//rowCount += " WHERE (PM.STATUS IN('3','39') OR (PM.STATUS IN ('1','39') AND (PM.TEMPLATE_TYPE = 'NE' OR PM.CR_SOL_TYPE='Additional Quantity'))) AND PM.MOC='"+moc+"'";// Changed by kajal G for Sprint -11
				rowCount += " WHERE (PM.STATUS IN('3','39','43','38') OR (PM.STATUS IN ('1','39','43','38') AND (PM.TEMPLATE_TYPE = 'NE' OR PM.TEMPLATE_TYPE = 'CR'))) AND PM.MOC='"+moc+"'";// Changed by Kavitha D  for Sprint -15

			}
		
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		//kiran - big int to int changes
		//return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@Override
	public String approveCr(String promoId, String userId, String roleId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		String res = "";
		int status = 0;
		try {
			if (roleId.equalsIgnoreCase("NCMM")) {
			String[] split = promoId.split(",");
			for (int i = 0; i < split.length; i++) {
				String promo = split[i];
				//createPromoDaoImpl.saveStatusInStatusTracker(promo, status, "", userId);
				
				String approvalCrStatus=" UPDATE TBL_PROCO_PROMOTION_MASTER_V2  T1 SET T1.STATUS='38', T1.USER_ID='" + userId + "',T1.UPDATE_STAMP=' "+ dateFormat.format(date) + "'"
						+ " WHERE T1.STATUS IN('1','3','39','43') AND T1.ACTIVE=1 AND T1.PROMO_ID='" + promoId + "' ";
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(approvalCrStatus);
				query.executeUpdate();
				
			}
			res = "SUCCESS";
		}} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return res;
	}
	
	
	@Override
	public String rejectCr(String promoId, String userId, String roleId, String reason) {
		String res = "";
		int status = 0;
		try {
			status = 12;  // reject
			String[] split = promoId.split(",");
			for (int i = 0; i < split.length; i++) {
				String promo = split[i];
				createPromoDaoImpl.saveStatusInStatusTracker(promo, status, reason, userId);
			}
			res = "SUCCESS";
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCategories() {
		List<String> catList = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT CATEGORY FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1 GROUP BY CATEGORY ORDER BY CATEGORY");
			catList = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return catList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBrands() {
		List<String> brandList = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT(BRAND) FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1 ORDER BY BRAND");
			brandList = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return brandList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBasepacks() {
		List<String> list = null;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT REPLACE ( BASEPACK_DESC, '''', '') FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1");
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return list;
	}
	
	//Sarin Changes Performance
	@SuppressWarnings("unchecked")
	@Override
	public List<List<String>> getAllProductMaster() {
		List<List<String>> list = new ArrayList<List<String>>();
		//List<String> listProductMaster = null;
		List<String> listCategory = null;
		List<String> listBrand = null;
		List<String> listBasepack = null;
		String qryCategory = "SELECT CATEGORY FROM (SELECT ROW_NUMBER() OVER (ORDER BY CATEGORY) AS ROW_NO, CATEGORY FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1 GROUP BY CATEGORY) A";
		String qryBrand = "SELECT BRAND FROM (SELECT ROW_NUMBER() OVER (ORDER BY BRAND) AS ROW_NO, BRAND FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1 GROUP BY BRAND) A";
		String qryBasepack = "SELECT BASEPACK_DESC FROM (SELECT ROW_NUMBER() OVER (ORDER BY BASEPACK_DESC) AS ROW_NO, REPLACE(BASEPACK_DESC, '''', '') AS BASEPACK_DESC FROM TBL_PROCO_PRODUCT_MASTER WHERE ACTIVE = 1) A";
		Query queryPM = null;
		try {
			queryPM = sessionFactory.getCurrentSession().createNativeQuery(qryCategory);
			listCategory = queryPM.list();
			list.add(0, listCategory);
			
			queryPM = sessionFactory.getCurrentSession().createNativeQuery(qryBrand);
			listBrand = queryPM.list();
			list.add(1, listBrand);
			
			queryPM = sessionFactory.getCurrentSession().createNativeQuery(qryBasepack);
			listBasepack = queryPM.list();
			list.add(2, listBasepack);
			
			
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return list;
	}
	// Harsha's changes for tracking Portal usage Q5
	@SuppressWarnings("unchecked")
	// Inserting values into table Added By Harsha
	public String insertToportalUsage(String userId, String roleID, String module) {
		String res = "";
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("INSERT INTO TBL_VAT_AUDIT_TRAIL_DETAILS" + "(USER_ID," + "ROLE_ID,"
							+ "LOGGED_IN_TIME," + "MODULE)" + "VALUES(?1,?2,?3,?4)");
			query.setParameter(1, userId);
			query.setParameter(2, roleID);
			query.setParameter(3, dtf.format(now));
			query.setParameter(4, module);
			query.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception:", e);
			res = "500";
		}
		return res;
	}
	//Added by Kavitha D-SPRINT 10 changes
	@SuppressWarnings("unchecked")
	public List<ArrayList<String>> getPromotionListingCrDownload(ArrayList<String> headerList, String userId,String moc, String roleId)
	{
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String downloadCrQuery= /*" SELECT DISTINCT PROMO_ID,PPM_ACCOUNT,OFFER_DESC FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM WHERE (PM.STATUS IN('3','39') OR (PM.STATUS IN ('1','39') "
					+ "AND (PM.TEMPLATE_TYPE = 'NE' OR PM.CR_SOL_TYPE='Additional Quantity'))) AND PM.MOC= '"+moc+"'"; */ // Changed by kajal G for Sprint -11 
			
					/*" SELECT DISTINCT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_MODALITY,PRICE_OFF,DP_QUANTITY,QUANTITY,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM WHERE (PM.STATUS IN('3','39','43') OR (PM.STATUS IN ('1','39','43') "
					+ "AND (PM.TEMPLATE_TYPE = 'NE' OR PM.TEMPLATE_TYPE = 'CR'))) AND PM.MOC= '"+moc+"' ";*/ //Changed by Kavitha D-SPRINT 15 changes
			//Kajal G changes start here for SPRINT-18		
			" CREATE TEMPORARY TABLE TT_NCMM_PROMO_DOWNLOAD SELECT DISTINCT PM.CHANNEL_NAME,PM.MOC,PM.SALES_CATEGORY,PM.PPM_ACCOUNT,"
			+ " PM.PROMO_ID,PM.OFFER_DESC,PM.BASEPACK_CODE,PM.OFFER_TYPE,PM.OFFER_MODALITY,PM.PRICE_OFF, (CASE WHEN (PM.TEMPLATE_TYPE = 'R' OR PM.TEMPLATE_TYPE='NE') THEN '' WHEN PM.TEMPLATE_TYPE='CR' THEN PM.REGULAR_PROMO_QUANTITY END) AS REGULAR_PROMO_QUANTITY,"
			+ " PM.QUANTITY,PM.BUDGET AS FIXED_BUDGET, (CASE WHEN PM.TEMPLATE_TYPE='CR' THEN PM.REGULAR_PROMO_BUDGET WHEN (PM.TEMPLATE_TYPE = 'R' OR PM.TEMPLATE_TYPE='NE') THEN '' END) AS REGULAR_PROMO_BUDGET,PM.CLUSTER,"
//			+ " PM.CLUSTER, PM.TEMPLATE_TYPE,PM.CR_SOL_TYPE," // Commented by KAJAL G in SPRINT-18
			+ " CASE WHEN PM.TEMPLATE_TYPE = 'R' THEN 'Regular' WHEN PM.TEMPLATE_TYPE = 'NE' THEN 'New Entry' ELSE PM.TEMPLATE_TYPE END AS PROMO_ENTRY_TYPE," // Added by KAJAL G in SPRINT-18
			+ " CASE WHEN PM.TEMPLATE_TYPE = 'R' THEN 'Regular' WHEN PM.TEMPLATE_TYPE = 'NE' THEN 'New Entry' ELSE PM.CR_SOL_TYPE END AS CR_SOL_TYPE," // Added by KAJAL G in SPRINT-18
			+ " CASE WHEN PM.TEMPLATE_TYPE = 'R' THEN 'REG' WHEN PM.TEMPLATE_TYPE = 'NE' THEN 'NE' ELSE '     ' END AS CR_SOL_TYPE_SHORTKEY,"  // Added by KAJAL G in SPRINT-18
			+ " 'NO' AS INCREMENTAL_BUDGET_REQUIRED,(CASE WHEN PM.CR_SOL_TYPE IN('Additional Quantity- Primary','Basepack Addition','Budget Extension','Date Extension','Missing Geo') OR PM.TEMPLATE_TYPE IN('R','NE') THEN 'YES' WHEN PM.CR_SOL_TYPE IN('Top Up','Additional Quantity- Customer Closing') THEN 'NA' END) AS REQUIRE_STOCK_AVAILABILITY "
			+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM WHERE (PM.STATUS IN('3','39','43') OR (PM.STATUS IN ('1','39','43') "
			+ "AND (PM.TEMPLATE_TYPE = 'NE' OR PM.TEMPLATE_TYPE = 'CR'))) AND PM.MOC= '"+moc+"'"; //Changed by Kavitha D-Sprint 16 

			Query query = sessionFactory.getCurrentSession().createNativeQuery(downloadCrQuery);
			query.executeUpdate();

			String updateTempTable = "UPDATE TT_NCMM_PROMO_DOWNLOAD LR INNER JOIN TBL_PROCO_SOL_TYPE CR ON CR.SOL_REMARK= LR.CR_SOL_TYPE "
					+ "SET LR.CR_SOL_TYPE_SHORTKEY=CR.SOL_TYPE";
			
			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(updateTempTable);
			query1.executeUpdate();
			
			String downloadNcmmEntry = "SELECT DISTINCT PM.CHANNEL_NAME,PM.MOC,PM.SALES_CATEGORY,PM.PPM_ACCOUNT,PM.PROMO_ID,PM.OFFER_DESC,PM.BASEPACK_CODE,PM.OFFER_TYPE,PM.OFFER_MODALITY,PM.PRICE_OFF,"
					+ " PM.REGULAR_PROMO_QUANTITY,PM.QUANTITY,PM.FIXED_BUDGET,PM.REGULAR_PROMO_BUDGET,PM.CLUSTER,PM.PROMO_ENTRY_TYPE,PM.CR_SOL_TYPE,PM.CR_SOL_TYPE_SHORTKEY,PM.INCREMENTAL_BUDGET_REQUIRED,"
					+ " PM.REQUIRE_STOCK_AVAILABILITY FROM TT_NCMM_PROMO_DOWNLOAD AS PM";
			
			Query query2  = sessionFactory.getCurrentSession().createNativeQuery(downloadNcmmEntry);
			Iterator itr = query2.list().iterator();

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
			Query dropTable = sessionFactory.getCurrentSession().createNativeQuery("DROP TEMPORARY TABLE TT_NCMM_PROMO_DOWNLOAD");
			dropTable.executeUpdate();
			//Kajal G changes END here for SPRINT-18	
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		
		}
	}
	
	
	
	//Added by Kavitha D for PromoApproval Upload starts-SPRINT 10
	@Override
	public String uploadApprovalData(PromoCrBean[] beanArray, String userId) throws Exception{
		String response = null;
		
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:user");
			queryToCheck.setString("user", userId);
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();

			if (recCount.intValue() > 0) {
		
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();
			}
		Query query = sessionFactory.getCurrentSession().createNativeQuery( /*"INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 "
				+ "(CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_MODALITY,PRICE_OFF,DP_QUANTITY,QUANTITY,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,REMARK,STATUS,USER_ID) VALUES(?0,?1, ?2, ?3, ?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16)"*/
				
				" INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 "
				+ "(CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,REGULAR_PROMO_QUANTITY,QUANTITY,BUDGET,REGULAR_PROMO_BUDGET,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,INCREMENTAL_BUDGET,STOCK_AVAILABILITY,REMARK,STATUS,USER_ID) VALUES(?0,?1, ?2, ?3, ?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21)"); 
				
		
		for (int i = 0; i < beanArray.length; i++) {
			if (beanArray[i].getSignedOffWithCM().equalsIgnoreCase("ACCEPTED") || beanArray[i].getSignedOffWithCM().equalsIgnoreCase("APPROVED") || beanArray[i].getSignedOffWithCM().equalsIgnoreCase("REJECTED")||beanArray[i].getSignedOffWithCM().isEmpty() ||beanArray[i].getSignedOffWithCM().isBlank() ||beanArray[i].getSignedOffWithCM()==null){
			
			query.setString(0, beanArray[i].getChannel()); //Changed by Kavitha D-SPRINT 15 changes
			query.setString(1, beanArray[i].getMoc());
			query.setString(2, beanArray[i].getCategory());
			query.setString(3, beanArray[i].getPpmaccount());
			query.setString(4, beanArray[i].getPromo_id());
			query.setString(5, beanArray[i].getOffer_desc());
			query.setString(6, beanArray[i].getBasepack());
			query.setString(7, beanArray[i].getOffer_type());
			query.setString(8, beanArray[i].getOffer_modality());
			query.setString(9, beanArray[i].getPriceoff());
			query.setString(10, beanArray[i].getRegularPromoQuantity());
			query.setString(11, beanArray[i].getQuantity());
			query.setString(12, beanArray[i].getBudget());
			query.setString(13, beanArray[i].getRegularPromoBudget());
			query.setString(14, beanArray[i].getCluster());
			query.setString(15, beanArray[i].getTemplatetype());
			query.setString(16, beanArray[i].getSoltype());
			query.setString(17, beanArray[i].getIncrementalBudget());
			query.setString(18, beanArray[i].getStockAvailability());
			query.setString(19, beanArray[i].getSignedOffWithCM()==null?"":beanArray[i].getSignedOffWithCM());
			if(beanArray[i].getSignedOffWithCM().equalsIgnoreCase("ACCEPTED") || beanArray[i].getSignedOffWithCM().equalsIgnoreCase("APPROVED") ) {
				query.setString(20,"38");
			}
			else if(beanArray[i].getSignedOffWithCM().equalsIgnoreCase("REJECTED")) {
				query.setString(20,"39");
			}
			else if (beanArray[i].getSignedOffWithCM().isEmpty()||beanArray[i].getSignedOffWithCM().isBlank()||beanArray[i].getSignedOffWithCM()==null){ //Added by Kavitha D-SPRINT 15 changes 
				query.setString(20,"43");
			}
			query.setString(21,userId);

			int executeUpdate = query.executeUpdate();
			
	
			if (executeUpdate > 0) {
				response = validatePromo(beanArray[i], i);
				if (response.equals("EXCEL_NOT_UPLOADED")) {
					responseList.add(response);
				}
			}
		}
		}

		if (!responseList.contains("EXCEL_NOT_UPLOADED")) {
			if (!this.saveTotMainTable(userId)) {
				response = "ERROR";
			} else {
				response = "EXCEL_UPLOADED";
			}
		} else {
			response = "EXCEL_NOT_UPLOADED";
		}


	} catch (Exception e) {
		logger.debug("Exception:", e);
		throw new Exception();
	}
	if(responseList.contains("EXCEL_NOT_UPLOADED")) {
		response = "EXCEL_NOT_UPLOADED";
	}
	return response;
}
	
	private synchronized String validatePromo(PromoCrBean bean,  int row) throws Exception {
		String res = "EXCEL_UPLOADED";
		String errorMsg = "";
		try {
			Query queryToCheckvisiRefNo = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE PROMO_ID=:promoId");
			queryToCheckvisiRefNo.setString("promoId", bean.getPromo_id());
			Integer promoid = ((BigInteger) queryToCheckvisiRefNo.uniqueResult()).intValue();			

			if(promoid!=null && promoid == 0){
				res = "EXCEL_NOT_UPLOADED";
				errorMsg = errorMsg + "Entered promoid is not exist..";
				updateErrorMessageInTemp(errorMsg,  row);
			}
			
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}
	private synchronized int updateErrorMessageInTemp(String errorMsg,  int row) {
		try {
			String qry = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 SET ERROR_MSG=:errorMsg WHERE ROW_ID=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}
	

	public synchronized boolean saveTotMainTable(String userId) {
		try {
			insertIntoMaster(userId);
			return true;
		}catch (HibernateException e) {
			logger.error("Inside PROMOCRDAOImpl: saveToMainTable() : HibernateException : " + e.getMessage());
			return false;

		}
	}

	
	@Transactional(rollbackOn = {Exception.class})
	public void insertIntoMaster(String userId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			String updateSql=" UPDATE TBL_PROCO_PROMOTION_MASTER_V2 A INNER JOIN TBL_PROCO_PROMOTION_MASTER_TEMP_V2 B ON A.PROMO_ID = B.PROMO_ID "
					+ " SET A.STATUS=B.STATUS,A.INCREMENTAL_BUDGET=B.INCREMENTAL_BUDGET,A.STOCK_AVAILABILITY=B.STOCK_AVAILABILITY,A.REGULAR_PROMO_BUDGET=B.REGULAR_PROMO_BUDGET,A.REGULAR_PROMO_QUANTITY=B.REGULAR_PROMO_QUANTITY,A.USER_ID='" + userId + "',A.UPDATE_STAMP=' "+ dateFormat.format(date) + "' "
					+ " WHERE B.USER_ID='" + userId + "' " ;
			Query queryUpdateExisting = sessionFactory.getCurrentSession().createNativeQuery(updateSql);
		queryUpdateExisting.executeUpdate();


	} catch (Exception e) {
		logger.error("Error in com.hul.proco.controller.promocrImpl.insertIntoTotMaster(String)", e);
	}

}
	//Added by Kavitha D for PromoApproval Upload ends-SPRINT 10

	


	}