package com.hul.proco.controller.promostatustracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;
import com.hul.proco.controller.listingPromo.PromoListingDAOImpl;

@Repository
public class ProcoStatusTrackerDAOImpl implements ProcoStatusTrackerDAO {

	Logger logger = Logger.getLogger(ProcoStatusTrackerDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	private NativeQuery qry;
	
	@Autowired
	private PromoListingDAOImpl PromoListingDAOImpl;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String moc,String promobasepack,
			String ppmaccount,String procochannel,String prococluster,String searchParameter,String fromDate,String toDate) {
		List<PromoListingBean> promoList = new ArrayList<>();
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
		
			
			// kiran - row number changes
			// promoQuery = "SELECT * FROM ( SELECT A.PROMO_ID ,A.P1_BASEPACK ,A.OFFER_DESC
			// ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM
			// ,A.OFFER_VALUE ,A.MOC ,A.CUSTOMER_CHAIN_L1 ,A.KITTING_VALUE ,E.STATUS
			// ,VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY') ,VARCHAR_FORMAT(A.END_DATE,
			// 'DD/MM/YYYY') ,A.CUSTOMER_CHAIN_L2, D.REMARK AS REM,A.REASON ,A.REMARK
			// ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY') AS
			// CHANGE_DATE,A.ORIGINAL_ID,A.CHANGES_MADE,D.USER_ID,A.UPDATE_STAMP,A.INVESTMENT_TYPE,A.SOL_CODE,A.SOL_CODE_DESC
			// ,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROWNUMBER() OVER ( ORDER BY
			// A.UPDATE_STAMP ASC ) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A INNER
			// JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN
			// TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN
			// TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE
			// A.START_DATE>=CURRENT_DATE ";
			// Garima - changes for VARCHAR_FORMAT
			//promoQuery = "SELECT * FROM ( SELECT A.PROMO_ID ,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE ,A.MOC ,A.CUSTOMER_CHAIN_L1 ,A.KITTING_VALUE ,E.STATUS ,DATE_FORMAT(A.START_DATE,'%d/%m/%Y') ,DATE_FORMAT(A.END_DATE,'%d/%m/%Y') ,A.CUSTOMER_CHAIN_L2, D.REMARK AS REM,A.REASON ,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y') AS CHANGE_DATE,A.ORIGINAL_ID,A.CHANGES_MADE,D.USER_ID,A.UPDATE_STAMP,A.INVESTMENT_TYPE,A.SOL_CODE,A.SOL_CODE_DESC ,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROW_NUMBER() OVER ( ORDER BY A.UPDATE_STAMP ASC ) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE A.START_DATE>=CURRENT_DATE ";

			//Added by kavitha D for SPRINT 9 changes
			
			promoQuery =" SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.START_DATE,PM.END_DATE,"
					+ "  PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC, "
					+ "  PM.OFFER_TYPE,PM.OFFER_MODALITY, PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE, "
					//+ "  PSM.STATUS,CASE WHEN ST.SOL_REMARK IS NULL THEN 'Regular' ELSE ST.SOL_REMARK END AS REMARK,PM.CREATED_BY AS USER_ID,PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE,"
					+ "  PSM.STATUS, PM.TEMPLATE_TYPE AS REMARK,PM.CREATED_BY AS USER_ID,"
					//+ "  PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE, PR.PROMOTION_NAME AS SOL_CODE_DESCRIPTION,PR.PROMOTION_MECHANICS, PR.PROMOTION_STATUS AS SOL_CODE_STATUS, "
					+ "  'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS SOL_CODE_DESCRIPTION, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS, "
					+ "  ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ "  FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "  INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "  INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS WHERE ";
					//+ "  LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					//+ "  FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR  ON PR.PROMO_ID = PM.PROMO_ID "
					//+ "  LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					//+ "  LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE "
					//+ "  WHERE PM.MOC='"+moc+"' ";
			
			//Kavitha D changes for filter-SPRINT 11 starts	

			if(fromDate==null && toDate==null) {
					//promoQuery +=	" PM.MOC='"+moc+"'";
					String mocVal = moc.replaceAll("^|$", "'").replaceAll(",", "','");
					promoQuery += " PM.MOC IN ("+mocVal+")"; //Added by Kavitha D-SPrint 13
					}else {
					promoQuery +="  PM.START_DATE>='"+fromDate+"' AND PM.END_DATE<='"+toDate+"'";	
					}
						
			if(!promobasepack.equalsIgnoreCase("ALL")) {
				if(!promobasepack.equalsIgnoreCase("SELECT BASEPACK")) {
				//promoQuery +=	"AND PM.BASEPACK_CODE='"+promobasepack+"'";	
				String promobasepackVal = promobasepack.replaceAll("^|$", "'").replaceAll(",", "','");
				promoQuery +=	" AND PM.BASEPACK_CODE IN ("+promobasepackVal+")";// Added by Kavitha D- sprint 13

				}
			}
			if(!ppmaccount.equalsIgnoreCase("ALL")) {
				if(!ppmaccount.equalsIgnoreCase("SELECT PPM ACCOUNT")){
				//promoQuery +=	"AND PM.PPM_ACCOUNT='"+ppmaccount+"'";
				String ppmaccountVal = ppmaccount.replaceAll("^|$", "'").replaceAll(",", "','");
				promoQuery +=	" AND PM.PPM_ACCOUNT IN ("+ppmaccountVal+")";//Added by Kavitha D-Sprint13

				}
			}
			
			if(!procochannel.equalsIgnoreCase("ALL")) {
				if(!procochannel.equalsIgnoreCase("SELECT CHANNEL")) {
				//promoQuery +=	"AND PM.CHANNEL_NAME='"+procochannel+"'";	
				String procochannelVal = procochannel.replaceAll("^|$", "'").replaceAll(",", "','");
				promoQuery +=	" AND PM.CHANNEL_NAME IN ("+procochannelVal+")";// Added by Kavitha D-Sprint13

				}
			}
			
			if(!prococluster.equalsIgnoreCase("ALL")) {
				if(!prococluster.equalsIgnoreCase("SELECT CLUSTER")) {
				//promoQuery +=	"AND PM.CLUSTER='"+prococluster+"'";
				String prococlusterVal = prococluster.replaceAll("^|$", "'").replaceAll(",", "','");
				promoQuery +=	" AND PM.CLUSTER IN ("+prococlusterVal+")";// Added by Kavitha D-Sprint13

				}
			}
					
			//Kavitha D changes for filter-SPRINT 11 ends	

			/*if (!cagetory.equalsIgnoreCase("All")) {
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
				promoQuery += "AND A.OFFER_MODALITY = '" + modality + "' ";
			}
			if (!year.equalsIgnoreCase("All")) {
				promoQuery += "AND A.YEAR = '" + year + "' ";
			}

			if (!promoId.equalsIgnoreCase("All")) {
				promoQuery += "AND A.PROMO_ID = '" + promoId + "' ";
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
			} */
			
			if (searchParameter != null && searchParameter.length() > 0) {
				promoQuery += " AND UCASE(PM.PROMO_ID) LIKE UCASE('%" + searchParameter + "%')";
			}
			//Sarin Changes Performances
			//promoQuery += " GROUP BY A.PROMO_ID,A.P1_BASEPACK,A.OFFER_DESC,A.OFFER_TYPE,A.OFFER_MODALITY,A.GEOGRAPHY,A.QUANTITY,A.UOM,A.OFFER_VALUE,A.MOC,A.CUSTOMER_CHAIN_L1,A.KITTING_VALUE,E.STATUS,DATE_FORMAT(A.START_DATE,'%d/%m/%Y'),DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.CUSTOMER_CHAIN_L2, D.REMARK,A.REASON,A.REMARK,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y'),A.ORIGINAL_ID,A.CHANGES_MADE,D.USER_ID,A.UPDATE_STAMP,A.INVESTMENT_TYPE,A.SOL_CODE,A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS ";
			if (pageDisplayLength == 0) {
				//promoQuery += " ) AS PROMO_TEMP ORDER BY PROMO_TEMP.UPDATE_STAMP ASC";
				//promoQuery += " ) AS PROMO_TEMP ";
				
				promoQuery += " ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP ";
			} else {
				/*promoQuery += " ) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND "
						//+ pageDisplayLength + "" + " ORDER BY PROMO_TEMP.UPDATE_STAMP ASC";
						+ pageDisplayLength + "";*/
				
				promoQuery += "ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND "
						//+ pageDisplayLength + "" + " ORDER BY PROMO_TEMP.UPDATE_STAMP ASC";
						+ pageDisplayLength + "";
				
				
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			//System.out.println(promoQuery);
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoListingBean promoBean = new PromoListingBean();
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
				if (obj[16] != null && !obj[16].toString().equals("")) {
					String status = obj[12].toString() + " (" + obj[16].toString() + ")";
					promoBean.setStatus(status);
				} else {
					promoBean.setStatus(obj[12].toString());
				}
				promoBean.setStartDate(obj[13] == null ? "" : obj[13].toString());
				promoBean.setEndDate(obj[14] == null ? "" : obj[14].toString());
				promoBean.setCustomerChainL2(obj[15] == null ? "" : obj[15].toString());
				promoBean.setReason(obj[17] == null ? "" : obj[17].toString());
				promoBean.setRemark(obj[18] == null ? "" : obj[18].toString());
				promoBean.setChangeDate(obj[19] == null ? "" : obj[19].toString());
				promoBean.setOriginalId(obj[20] == null ? "" : obj[20].toString());
				promoBean.setChangesMade(obj[21] == null ? "" : obj[21].toString());
				promoBean.setUserId(obj[22] == null ? "" : obj[22].toString());
				promoBean.setInvestmentType(obj[24] == null ? "" : obj[24].toString());
				promoBean.setSolCode(obj[25] == null ? "" : obj[25].toString());
				promoBean.setSolCodeDescription(obj[26] == null ? "" : obj[26].toString());
				promoBean.setPromotionMechanics(obj[27] == null ? "" : obj[27].toString());
				promoBean.setSolCodeStatus(obj[28] == null ? "" : obj[28].toString());*/
				//Added by Kavitha D-SPRINT 9
				promoBean.setPromo_id(obj[0]== null ? "" :obj[0].toString());
				promoBean.setStartDate(obj[1]== null ? "" : obj[1].toString());
				promoBean.setEndDate(obj[2]== null ? "" : obj[2].toString());
				promoBean.setMoc(obj[3]== null ? "" : obj[3].toString());
				promoBean.setCustomerChainL2(obj[4]== null ? "" :obj[4].toString());
				promoBean.setBasepack(obj[5]== null ? "" :obj[5].toString());
				promoBean.setOffer_desc(obj[6]== null ? "" :obj[6].toString());
				promoBean.setOffer_type(obj[7]== null ? "" :obj[7].toString());
				promoBean.setOffer_modality(obj[8]== null ? "" :obj[8].toString());
				promoBean.setGeography(obj[9]== null ? "" :obj[9].toString());
				promoBean.setQuantity((obj[10] == null || obj[10].toString().equals("")) ? "" : obj[10].toString());
				promoBean.setOffer_value(obj[11]== null ? "" :obj[11].toString());
				promoBean.setStatus(obj[12]== null ? "" :obj[12].toString());
				promoBean.setRemark(obj[13]== null ? "" :obj[13].toString());
				promoBean.setUserId(obj[14]== null ? "" :obj[14].toString());
				promoBean.setInvestmentType(obj[15]== null ? "" :obj[15].toString());
				promoBean.setSolCode(obj[16]== null ? "" :obj[16].toString());
				promoBean.setSolCodeDescription(obj[17]== null ? "" :obj[17].toString());
				promoBean.setPromotionMechanics(obj[18]== null ? "" :obj[18].toString());
				promoBean.setSolCodeStatus(obj[19]== null ? "" :obj[19].toString());
				promoList.add(promoBean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getMeasureReportByMoc(String MocYear, String Moc) {
		List<Object[]> resList = new ArrayList<Object[]>();
		try {
			Query queryGetMoc = sessionFactory.getCurrentSession().createNativeQuery(
					// "SELECT DISTINCT SIGNED_OFF_SHARED_DATE, PROMOTION_ID, PROMOTION_NAME, SCOPE,
					// PROCESS, IS_CLAIMED, PRODUCT, CLIAM_VALUE, PROMO_VOLUMN_IN_THOUSAND,
					// TOTAL_INVESTMENT_IN_LACS, SUPPORING_REQUIRED, PROMOTION_START_DATE,
					// PROMOTION_END_DATE, SIGNED_OPS_STATUS FROM TBL_PROCO_MEASURE_REPORT_MASTER
					// WHERE ("+MocYear+") AND ("+Moc+")");
					"SELECT DISTINCT SIGNED_OFF_SHARED_DATE, PROMOTION_ID,PARENT_ACCOUNT_NAME, PROMOTION_NAME, SCOPE, PROCESS, IS_CLAIMED, PRODUCT,SUB_BRAND, CLIAM_VALUE, PROMO_VOLUMN_IN_THOUSAND, TOTAL_INVESTMENT_IN_LACS, SUPPORING_REQUIRED, PROMOTION_START_DATE, PROMOTION_END_DATE, SIGNED_OPS_STATUS FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE ("
							+ MocYear + ") AND (" + Moc + ")");
			resList = queryGetMoc.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		return resList;
	}

	@SuppressWarnings("unchecked")
	public String getMocList() {
		Gson gson = new Gson();

		String response = "[]";
		try {
			Query queryGetMoc = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT MOC, MOC_YEAR FROM TBL_PROCO_MEASURE_REPORT_MASTER");
			List<Object[]> list = queryGetMoc.list();
			List<ProcoMocModel> moc = new ArrayList<ProcoMocModel>();
			for (Object[] obj : list) {
				ProcoMocModel MoCMod = new ProcoMocModel();
				if (obj[0] != null && obj[1] != null) {
					MoCMod.setMocMonth(obj[0].toString());
					MoCMod.setMocYear(obj[1].toString());
				}
				moc.add(MoCMod);
			}
			response = gson.toJson(moc);
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}

		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoListRowCount(String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster,String fromDate,String toDate) {
		// kiran - bigint to int changes
		// List<Integer> list = null;
		List<BigInteger> list = null;
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {
			//Commented by Kavitha D for SPRINT 9 changes
			//String rowCount = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE A.START_DATE > CURRENT_DATE ";

			String rowCount = " SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME WHERE ";
					//+ " LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					//+ " FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					//+ " LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					//+ " LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE "
					//+ "  WHERE PM.MOC='"+moc+"'";
			
			//Kavitha D changes for filter-SPRINT 11 starts	
			
			if(fromDate==null && toDate==null) {
				//rowCount +=	" PM.MOC='"+moc+"'";
				String mocVal = moc.replaceAll("^|$", "'").replaceAll(",", "','");
				rowCount += " PM.MOC IN ("+mocVal+")"; //Added by Kavitha D-SPrint 13
				
				}else {
				rowCount +="  PM.START_DATE>='"+fromDate+"' AND PM.END_DATE<='"+toDate+"'";	
				}
			if(!promobasepack.equalsIgnoreCase("ALL")) {
				if(!promobasepack.equalsIgnoreCase("SELECT BASEPACK")) {
					//rowCount +=	"AND PM.BASEPACK_CODE='"+promobasepack+"'";
					String promobasepackVal = promobasepack.replaceAll("^|$", "'").replaceAll(",", "','");
					rowCount +=	" AND PM.BASEPACK_CODE IN ("+promobasepackVal+")";// Added by Kavitha D- sprint 13

				}
			}
			if(!ppmaccount.equalsIgnoreCase("ALL")) {
				if(!ppmaccount.equalsIgnoreCase("SELECT PPM ACCOUNT")){
					//rowCount +=	"AND PM.PPM_ACCOUNT='"+ppmaccount+"'";	
					String ppmaccountVal = ppmaccount.replaceAll("^|$", "'").replaceAll(",", "','");
					rowCount +=	" AND PM.PPM_ACCOUNT IN ("+ppmaccountVal+")";//Added by Kavitha D-Sprint13

				}
			}
			
			if(!procochannel.equalsIgnoreCase("ALL")) {
				if(!procochannel.equalsIgnoreCase("SELECT CHANNEL")) {
					//rowCount +=	"AND PM.CHANNEL_NAME='"+procochannel+"'";
					String procochannelVal = procochannel.replaceAll("^|$", "'").replaceAll(",", "','");
					rowCount +=	" AND PM.CHANNEL_NAME IN ("+procochannelVal+")";// Added by Kavitha D-Sprint13

				}
			}
			
			if(!prococluster.equalsIgnoreCase("ALL")) {
				if(!prococluster.equalsIgnoreCase("SELECT CLUSTER")) {
					//rowCount +=	"AND PM.CLUSTER='"+prococluster+"'";
					String prococlusterVal = prococluster.replaceAll("^|$", "'").replaceAll(",", "','");
					rowCount +=	" AND PM.CLUSTER IN ("+prococlusterVal+")";// Added by Kavitha D-Sprint13

				}
			}
			
			//Kavitha D changes for filter-SPRINT 11 ends

			
			/*if (!cagetory.equalsIgnoreCase("All")) {
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
					} */

					/*
					 * Query queryToGetState = sessionFactory.getCurrentSession().createNativeQuery(
					 * "SELECT DISTINCT STATE_CODE FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branch"
					 * ); queryToGetState.setString("branch", branch); List stateList =
					 * queryToGetState.list(); if (stateList != null) { for (int i = 0; i <
					 * stateList.size(); i++) { if (i < stateList.size() - 1) { rowCount +=
					 * "OR A.GEOGRAPHY like '%" + stateList.get(i).toString() + "%' "; } if (i ==
					 * stateList.size() - 1) { rowCount += "OR A.GEOGRAPHY like '%" +
					 * stateList.get(i).toString() + "%') "; } } }
					 */
				/*} else if (geography.startsWith("CL")) {
					String cluster = geography.substring(0, geography.indexOf(':')); */
					/*
					 * Query queryToGetState = sessionFactory.getCurrentSession().createNativeQuery(
					 * "SELECT DISTINCT STATE_CODE FROM TBL_PROCO_CUSTOMER_MASTER WHERE CLUSTER_CODE=:cluster"
					 * ); queryToGetState.setString("cluster", cluster); List stateList =
					 * queryToGetState.list();
					 */
					//rowCount += "AND (A.GEOGRAPHY like '%" + cluster + "%') ";
					/*
					 * if (stateList != null) { for (int i = 0; i < stateList.size(); i++) { if (i <
					 * stateList.size() - 1) { rowCount += "OR A.GEOGRAPHY like '%" +
					 * stateList.get(i).toString() + "%' "; } if (i == stateList.size() - 1) {
					 * rowCount += "OR A.GEOGRAPHY like '%" + stateList.get(i).toString() + "%') ";
					 * } } }
					 */
				
				/*} else {
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
			} */
			/*if (!promoId.equalsIgnoreCase("All")) {
				rowCount += "AND A.PROMO_ID = '" + promoId + "' ";
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
			}*/
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
			//System.out.println(rowCount);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		// kiran - bigint to int changes
		// return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	public int getUserRoleId(String userId) {
		int UserRoleId = 0;
		try {
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT USER_ROLE_ID FROM TBL_VAT_USER_DETAILS WHERE USERID=:userid");
			query.setString("userid", userId);
			UserRoleId = (int) query.uniqueResult();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return UserRoleId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList,String moc,String promobasepack,String ppmaccount,String procochannel,String prococluster, String userId,String fromDate,String toDate) {
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String promoQuery = "";
		String query = "";
		try {
			
			  int userRole = getUserRoleId(userId); 
			  if(userRole == 2) { 
				  //Garima - changes //for VARCHAR_FORMAT //promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY') as start_date1,VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,c.BASEPACK_DESC,replace(A.USER_ID,'','') as Created_By,replace(c.CATEGORY,'','') as SALES_CATEGORY,replace(c.BRAND,'','') as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY'),A.INVESTMENT_TYPE, A.SOL_CODE, A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) "; 
				 // promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,DATE_FORMAT(A.START_DATE,'%d/%m/%Y') as start_date1,DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,C.BASEPACK_DESC,replace(A.USER_ID,'','') as Created_By,replace(C.CATEGORY,'','') as SALES_CATEGORY,replace(C.BRAND,'','') as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y'),A.INVESTMENT_TYPE, A.SOL_CODE, A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
				  /*promoQuery =" SELECT PM.PROMO_ID AS UNIQUE_ID,PM.START_DATE,PM.END_DATE,"
				  		+ "  PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,"
				  		+ "  PM.OFFER_TYPE,PM.OFFER_MODALITY, PM.CLUSTER AS GEOGRAPHY,PM.TEMPLATE_TYPE AS PROMO_TEMPLATE, SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,"
				  		+ "  PSM.STATUS,CASE WHEN ST.SOL_REMARK IS NULL THEN 'Regular' ELSE ST.SOL_REMARK END AS REMARK,PM.CREATED_BY,PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE,"
				  		+ "  PR.PROMOTION_NAME AS SOL_CODE_DESCRIPTION,PR.PROMOTION_MECHANICS, PR.PROMOTION_STATUS AS SOL_CODE_STATUS "
				  		+ "  FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
				  		+ "  INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME  "
				  		+ "  INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS "
				  		+ "  LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
				  		+ "  FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR  ON PR.PROMO_ID = PM.PROMO_ID "
				  		+ "  LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
				  		+ "  LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE WHERE PM.MOC='"+moc+"'"; */
				  //Added by Kavitha D -SPRINT 9
				    //qry = sessionFactory.getCurrentSession().createNativeQuery("CALL PROMO_LISTING_DOWNLOAD(:moc)");
				    
				  try {
					  qry = sessionFactory.getCurrentSession().createNativeQuery("CALL PROMO_LISTING_DOWNLOAD(:moc,:fromDate,:toDate)"); //Added by Kavitha D-SPRINT 11
						qry.setParameter("moc", moc);
						qry.setParameter("fromDate", fromDate);
						qry.setParameter("toDate", toDate);

						qry.executeUpdate();  
				  }catch (Exception ex) {
						logger.debug("Exception :", ex);
						return null;
				  	}
					
					/*query= " SELECT CHANNEL,YEAR,MOC ,ACCOUNT_TYPE,CLAIM_SETTLEMENT_TYPE,SECONDARY_CHANNEL,PPM_ACCOUNT,PROMO_ID,SOLCODE,MOC_CYCLE,PROMO_TIMEPERIOD,SOL_RELEASE_ON,"
							+ " START_DATE,END_DATE,OFFER_DESC,PPM_DESC,BASEPACK_CODE,BASEPACK_DESC,CHILDPACK,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,QUANTITY,FIXED_BUDGET ,BRANCH,"
							+ " SALES_CLUSTER ,PPM_CUSTOMER,CMM_NAME,TME_NAME,SALES_CATEGORY,PSA_CATEGORY,PROMOTION_STATUS,PPM_PROMOTION_CREATOR ,PROMOTION_MECHANICS,INVESTMENT_TYPE,"
							+ " SALES_CLUSTER_CODE ,BRAND,SUB_BRAND,PPM_BUDGET_HOLDER_NAME ,FUND_TYPE,INVESTMENT_AMOUNT ,PROMO_ENTRY_TYPE ,PROMO_USER_NAME ,PROMO_USER_TIME ,"
							+ " PPM_APPROVED_DATE ,PPM_CREATION_DATE ,NON_UNIFY,PPM_SUBMISSION_DATE ,PPM_MODIFIED_DATE ,COE_REMARKS,MRP ,AB_CREATION ,BUDGET ,CURRENT_STATUS,SOL_TYPE,SOL_TYPE_SHORTKEY " //Added current_status,sol type,sol type short key-SPRINT 10 "
							+ " FROM TBL_PROCO_PROMO_LISTING_REPORT  LR  WHERE ";*/
							//+ " WHERE LR.MOC=:moc " ;
				
				  //Changed by Kajal G In SPRINT-12
				  query="SELECT LR.CHANNEL,LR.YEAR,LR.MOC,LR.ACCOUNT_TYPE,LR.CLAIM_SETTLEMENT_TYPE,LR.PPM_ACCOUNT,"
				  		+ "LR.PROMO_ID,LR.SOLCODE,LR.PROMO_TIMEPERIOD,LR.SOL_RELEASE_ON,LR.START_DATE,LR.END_DATE,LR.OFFER_DESC,"
				  		+ "LR.PPM_DESC,LR.BASEPACK_CODE,LR.BASEPACK_DESC,LR.OFFER_TYPE,LR.OFFER_MODALITY,LR.PRICE_OFF,LR.QUANTITY,"
				  		+ "LR.FIXED_BUDGET,LR.BRANCH,LR.SALES_CLUSTER ,LR.PPM_CUSTOMER,LR.CMM_NAME,LR.TME_NAME,LR.SALES_CATEGORY,"
				  		+ "LR.PSA_CATEGORY,LR.PROMOTION_STATUS,LR.PPM_PROMOTION_CREATOR ,LR.PROMOTION_MECHANICS,LR.INVESTMENT_TYPE,"
				  		+ "LR.SALES_CLUSTER_CODE,LR.BRAND,LR.SUB_BRAND,LR.PPM_BUDGET_HOLDER_NAME,LR.FUND_TYPE,LR.INVESTMENT_AMOUNT,LR.PROMO_ENTRY_TYPE,"
				  		+ "LR.PROMO_USER_NAME,LR.PROMO_USER_TIME,LR.PPM_APPROVED_DATE,LR.PPM_CREATION_DATE,LR.NON_UNIFY,LR.PPM_SUBMISSION_DATE,"
				  		+ "LR.PPM_MODIFIED_DATE,LR.COE_REMARKS,IF (AB.STATUS_IN_CENTRAL_UNIFY IS NULL, '-', AB.STATUS_IN_CENTRAL_UNIFY) AS STATUS_IN_CENTRAL_UNIFY,"
				  		+ "IF (AB.TME_SUBMIT_DATE IS NULL, '-', AB.TME_SUBMIT_DATE) AS TME_SUBMIT_DATE,IF (AB.AUDITOR_SUBMIT_DATE IS NULL, '-', AB.AUDITOR_SUBMIT_DATE) AS AUDITOR_SUBMIT_DATE,"
				  		+ "LR.MRP,LR.AB_CREATION,LR.BUDGET,LR.CURRENT_STATUS,LR.SOL_TYPE,LR.SOL_TYPE_SHORTKEY FROM TBL_PROCO_PROMO_LISTING_REPORT LR "
				  		+ "LEFT JOIN TBL_PROCO_AB_CREATION_REPORT_MASTER AB ON AB.ACTIVITY_CODE=LR.SOLCODE WHERE LR.COE_REMARKS='' AND "; //Added coe remarks column by Kavitha D-SPRINT 14
					
				  //Kavitha D changes for filter-SPRINT 11 starts	
					if(fromDate.equals("null")|| toDate.equals("null")) {
						query += " LR.MOC='"+moc+"'";
					}
					
					else if(fromDate.equalsIgnoreCase("2000-01-20") && toDate.equalsIgnoreCase("2000-02-21")){
						 
						query += " LR.MOC='"+moc+"'";	
						
					}
					
					else {
						query +=" LR.START_DATE>='"+fromDate+"' AND LR.END_DATE<='"+toDate+"'";	
					}
					
					if(!promobasepack.equalsIgnoreCase("ALL")) {
						if(!promobasepack.equalsIgnoreCase("SELECT BASEPACK")) {
							//query +=	" AND LR.BASEPACK_CODE='"+promobasepack+"'";
							String promobasepackVal = promobasepack.replaceAll("^|$", "'").replaceAll(",", "','");
							query +=	" AND LR.BASEPACK_CODE IN ("+promobasepackVal+")";// Added by Kavitha D- sprint 13					
						}
					}
					if(!ppmaccount.equalsIgnoreCase("ALL")) {
						if(!ppmaccount.equalsIgnoreCase("SELECT PPM ACCOUNT")){
							//query +=	" AND LR.PPM_ACCOUNT='"+ppmaccount+"'";
							String ppmaccountVal = ppmaccount.replaceAll("^|$", "'").replaceAll(",", "','");
							query +=	" AND LR.PPM_ACCOUNT IN ("+ppmaccountVal+")";//Added by Kavitha D-Sprint13			
						}
					}
					
					if(!procochannel.equalsIgnoreCase("ALL")) {
						if(!procochannel.equalsIgnoreCase("SELECT CHANNEL")) {
							//query +=	" AND LR.CHANNEL='"+procochannel+"'";
							String procochannelVal = procochannel.replaceAll("^|$", "'").replaceAll(",", "','");
							query +=	" AND LR.CHANNEL IN ("+procochannelVal+")";// Added by Kavitha D-Sprint13				
						}
					}
					
					if(!prococluster.equalsIgnoreCase("ALL")) {
						if(!prococluster.equalsIgnoreCase("SELECT CLUSTER")) {
							//query +=	" AND LR.SALES_CLUSTER='"+prococluster+"'";	
							String prococlusterVal = prococluster.replaceAll("^|$", "'").replaceAll(",", "','");
							query +=	" AND LR.SALES_CLUSTER IN ("+prococlusterVal+")";// Added by Kavitha D-Sprint13					
						}
					}	
							
					//Kavitha D changes for filter-SPRINT 11 ends
			  
			  } else {
			 
			// Garima - changes for VARCHAR_FORMAT
			// promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,VARCHAR_FORMAT(A.START_DATE,
			// 'DD/MM/YYYY'),VARCHAR_FORMAT(A.END_DATE,
			// 'DD/MM/YYYY'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK
			// ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM
			// ,A.OFFER_VALUE , A.KITTING_VALUE ,(CASE WHEN D.REMARK<>'' THEN
			// CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END)
			// ,A.REASON,A.REMARK
			// ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY'),A.INVESTMENT_TYPE, A.SOL_CODE,
			// A.PROMOTION_MECHANICS, A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A
			// INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER
			// JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN
			// TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN
			// TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE
			// (A.ACTIVE=1 OR A.ACTIVE=0) ";
			promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,DATE_FORMAT(A.START_DATE,'%d/%m/%Y'),DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y'),A.INVESTMENT_TYPE, A.SOL_CODE, A.PROMOTION_MECHANICS, A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
			}

			
			/*if (!cagetory.equalsIgnoreCase("All")) {
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
				promoQuery += "AND F.MODALITY_NO = '" + modality + "' ";
			}
			if (!year.equalsIgnoreCase("All")) {
				promoQuery += "AND A.YEAR = '" + year + "' ";
			}

			if (!promoId.equalsIgnoreCase("All")) {
				promoQuery += "AND A.PROMO_ID = '" + promoId + "' ";
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
			}*/
			  //System.out.println("PromoStatusTracker:"+query);
			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(query);
			//query1.setString("moc", moc);

			
			Iterator itr = query1.list().iterator();
			downloadDataList.add(headerList);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				ArrayList<String> downCusData = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}

				
				  /*if( userRole == 2 && !dataObj.get(16).trim().equals("")) {
					  try {
						  	//get Basepack
						  	String BasePack = dataObj.get(7).trim(); 
						  	//get Quantity
						  	if(isBigDecimal(dataObj.get(16))) { 
						  		BigDecimal Quantity = new BigDecimal(dataObj.get(16) ); 
						  		Query MaxTurQuery = sessionFactory.getCurrentSession().
						  				createNativeQuery("SELECT GSV FROM TBL_PROCO_MAX_TUR WHERE BASEPACK='" +BasePack+"'"); 
						  		BigDecimal GSV = (BigDecimal) MaxTurQuery.uniqueResult();
						  		if(GSV != null) { 
						  			//Set Sales Value 
						  			BigDecimal SalesVal = GSV.multiply(Quantity); 
						  			dataObj.set(20, SalesVal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString()); 
						  			BigDecimal EstimSpend = new BigDecimal(BigInteger.ZERO, 2); 
						  			if(!dataObj.get(18).trim().equals("")) {
						  					String offerStr = dataObj.get(18).trim();
						  					if(offerStr.toUpperCase().indexOf("ABS") != -1) { 
						  						String offerVal = offerStr.replace("ABS", ""); 
						  						if(isBigDecimal(offerVal)) { 
						  							BigDecimal amount = new BigDecimal( offerVal ); 
						  							EstimSpend = amount.multiply(Quantity); 
						  						} 
						  					} else
						  						if(offerStr.toUpperCase().indexOf("%") != -1){ 
						  							String offerVal = offerStr.replace("%", ""); 
						  							if(isBigDecimal(offerVal)) { 
						  								BigDecimal Hundred = new BigDecimal(100); 
						  								BigDecimal amount = new BigDecimal( offerVal );
						  								EstimSpend = amount.multiply( SalesVal ); 
						  								EstimSpend = EstimSpend.divide(Hundred); 
						  							} 
						  						} 
						  					} else
						  						if(!dataObj.get(19).trim().equals("")){ 
						  							String kittingStr = dataObj.get(19).trim(); 
						  							if(kittingStr.toUpperCase().indexOf("ABS") != -1) {
						  								String kittVal = kittingStr.replace("ABS", ""); 
						  								if(isBigDecimal(kittVal)) {
						  									BigDecimal amount = new BigDecimal( kittVal ); 
						  									EstimSpend = amount.multiply(Quantity); 
						  								} 
						  							} else if(kittingStr.toUpperCase().indexOf("%") != -1) { 
						  								String kittVal = kittingStr.replace("%", "");
						  								if(isBigDecimal(kittVal)) { 
						  									BigDecimal amount = new BigDecimal(kittVal);
						  									BigDecimal Hundred = new BigDecimal(100); 
						  									EstimSpend = amount.multiply(SalesVal); 
						  									EstimSpend = EstimSpend.divide(Hundred); 
						  								} 
						  							} 
						  						}
						  						if(EstimSpend.compareTo(BigDecimal.ZERO) != 0) { 
						  							//set Estimated Spend
						  							dataObj.set(21, EstimSpend.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString()); 
						  						} 
						  					} 
						  		} 
						  } catch (Exception ex) {
							  logger.debug("Exception :", ex); 
						  } 
				  }*/
				
				obj = null;
				downloadDataList.add(dataObj);		
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	//Added by Kajal G in SPRINT -12
	@Override
	public List<ArrayList<String>> getVisiDownloadedData(ArrayList<String> headerList,String moc){
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String query = "";
		query = "SELECT DISTINCT A.VISI_REF_NO,B.PROMOTION_ID AS SOL_CODE,A.START_DATE,A.END_DATE,A.MOC,A.HFS_CONNECTIVITY,A.NEW_CONTINUED,A.MADE_BY,A.ACCOUNT_NAME,"
				+ "A.SPLIT_REQUIRE,A.PPM_ACCOUNT_NAME,A.DESCRIPTION_1,A.PPM_DESC,REGION,A.STATE,CITY,A.BASEPACK,A.BASEPACK_DESC,"
				+ "A.VISIBILITY_DESC,A.ASSET_DESC,A.ASSET_TYPE,A.ASSET_REMARK,A.POP_CLASS,A.UNIT_PER_STORE,A.NO_OF_STORES,"
				+ "A.AMOUNT_PER_STORE_PER_MOC,A.AMOUNT_PER_BASEPACK_PER_MOC,A.COMMENTS,A.HHT_TRACKING,A.CATEGORY,A.MIGRATED_CATEGORY,"
				+ "A.SUB_ELEMENTS,A.MBQ,A.BRAND,A.TOTAL_NO_OF_ASSET,A.VISIBILITY_AMOUNT,A.OUTLET_CODE,A.OUTLET_NAME,A.MAPPED_POP_CLASS,"
				+ "A.STATUS,A.DATE_OF_CREATION,A.LAST_EDITED,A.CLASSIFICATION,A.EDIT_DELETE_REASON,A.VISIBILITY_PAYOUT_CODE "
				+ "FROM TBL_PROCO_VISIBILITY_MASTER AS A INNER JOIN TBL_PROCO_MEASURE_MASTER_V2 AS B ON "
				+ "A.VISI_REF_NO = SUBSTRING_INDEX(B.PROMOTION_NAME, ':', 1)";
		
		if(!moc.equalsIgnoreCase("null")) {
			query += " WHERE A.MOC='"+moc+"'";
		}
		try {
			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(query);
			Iterator itr = query1.list().iterator();
			downloadDataList.add(headerList);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				ArrayList<String> downCusData = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		}catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
		
	}
	
	public static boolean isBigDecimal(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray) throws Exception {
		String response = null;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_PROCO_PROMOTION_MASTER SET INVESTMENT_TYPE=?0, SOL_CODE=?1,SOL_CODE_DESC=?2, PROMOTION_MECHANICS=?3, SOL_CODE_STATUS=?4 WHERE PROMO_ID=?5 AND ORIGINAL_ID=?6"); // Sarin
																																																// -
																																																// Added
																																																// Parameters
																																																// position

			for (int i = 0; i < promoListingBeanArray.length; i++) {

				query.setString(0, promoListingBeanArray[i].getInvestmentType());
				query.setString(1, promoListingBeanArray[i].getSolCode());
				query.setString(2, promoListingBeanArray[i].getSolCodeDescription());
				query.setString(3, promoListingBeanArray[i].getPromotionMechanics());
				query.setString(4, promoListingBeanArray[i].getSolCodeStatus());
				query.setString(5, promoListingBeanArray[i].getPromo_id());
				query.setString(6, promoListingBeanArray[i].getOriginalId());

				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = "SUCCESS_FILE";
				}
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			throw new Exception();
		}
		return response;
	}

	@Override
	public String uploadPromoMeasureReport(PromoMeasureReportBean[] promoMeasureReportBeanArray) throws Exception {
		String response = null;
		List<String> responseResult = null;
		ArrayList<String> responseList = new ArrayList<String>();
		PreparedStatement query = null;
		try {

			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_MEASURE_REPORT_TEMP");
			//Sarin - changes BigInt to Int
			//Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();

			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_MEASURE_REPORT_TEMP");
				queryToDelete.executeUpdate();
			}
			
			//Sarin added - starts
			Query queryToCheckInvestmentType = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT INVESTMENT_TYPE FROM TBL_PROCO_INVESTMENT_TYPE_MASTER");
			List<String> lstInvestmentType = queryToCheckInvestmentType.list();

			Query queryToCheckPromotionMechanics = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT PROMO_MECHANICS FROM TBL_PROCO_MECHANICS_MASTER");
			List<String> lstPromoMechanics = queryToCheckPromotionMechanics.list();

			Query queryToCheckPromotionStatus = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT SOLCODE_STATUS FROM TBL_PROCO_SOLCODE_STATUS_MASTER");
			List<String> lstSolCodeStatus = queryToCheckPromotionStatus.list();

			Query queryToCheckCreatedBy = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT CREATED_BY FROM TBL_PROCO_CREATED_BY_MASTER");
			List<String> lstCreatedBy = queryToCheckCreatedBy.list();
			
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			query = sessionImpl.connection().prepareStatement(
						"INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP(PROMOTION_ID,PROMOTION_NAME,CREATED_BY,PROMOTION_MECHANICS,PROMOTION_START_DATE,PROMOTION_END_DATE,CUSTOMER,PRODUCT,PROMOTION_STATUS,CATEGORY,INVESTMENT_TYPE,MOC,SUBMISSION_DATE,PROMOTION_TYPE,PROMOTION_VOLUME_DURING,PLANNED_INVESTMENT_AMOUNT,ROW_NO,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND,ERROR_MSG) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//ends
				
			//Query query = sessionFactory.getCurrentSession().createNativeQuery(
			//		"INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP(PROMOTION_ID,PROMOTION_NAME,CREATED_BY,PROMOTION_MECHANICS,PROMOTION_START_DATE,PROMOTION_END_DATE,CUSTOMER,PRODUCT,PROMOTION_STATUS,CATEGORY,INVESTMENT_TYPE,MOC,SUBMISSION_DATE,PROMOTION_TYPE,PROMOTION_VOLUME_DURING,PLANNED_INVESTMENT_AMOUNT,ROW_NO,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND) VALUES (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19)"); // Sarin Added Parameter position
			
			for (int i = 0; i < promoMeasureReportBeanArray.length; i++) {
				query.setString(1, promoMeasureReportBeanArray[i].getPromotionId());
				query.setString(2, promoMeasureReportBeanArray[i].getPromotionName());
				query.setString(3, promoMeasureReportBeanArray[i].getCreatedBy());
				query.setString(4, promoMeasureReportBeanArray[i].getPromotionMechanics());
				query.setString(5, promoMeasureReportBeanArray[i].getPromotionStartDate());
				query.setString(6, promoMeasureReportBeanArray[i].getPromotionEndDate());
				query.setString(7, promoMeasureReportBeanArray[i].getCustomer());
				// query.setString(7, promoMeasureReportBeanArray[i].getProduct());
				query.setString(8, promoMeasureReportBeanArray[i].getProduct().replace(",", ";"));
				query.setString(9, promoMeasureReportBeanArray[i].getPromotionStatus());
				query.setString(10, promoMeasureReportBeanArray[i].getCategory());
				query.setString(11, promoMeasureReportBeanArray[i].getInvestmentType());
				query.setString(12, promoMeasureReportBeanArray[i].getMoc());
				query.setString(13, promoMeasureReportBeanArray[i].getSubmissionDate());
				query.setString(14, promoMeasureReportBeanArray[i].getPromotionType());
				query.setString(15, promoMeasureReportBeanArray[i].getPromotionVolumeDuring());
				query.setString(16, promoMeasureReportBeanArray[i].getPlannedInvestmentAmount());
				//query.setInteger(16, i);
				query.setInt(17, i);
				query.setString(18, "");
				query.setString(19, "");

				query.setString(20, promoMeasureReportBeanArray[i].getSubBrand());
				
				responseResult = validateRecord(promoMeasureReportBeanArray[i], i, lstInvestmentType, lstPromoMechanics, lstSolCodeStatus, lstCreatedBy);
				response = responseResult.get(0);
				String errormsg = responseResult.get(1);
				if (response.equals("ERROR_FILE")) {
					responseList.add(response);
				}
				
				if (errormsg.length() > 0) {
					query.setString(21, errormsg);
				} else {
					query.setString(21, null);
				}
				
				query.addBatch();
				if(i==promoMeasureReportBeanArray.length - 1 || i%50==0) {
					query.executeBatch();
				}
				/*int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateRecord(promoMeasureReportBeanArray[i], i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}*/
			}

			if (!responseList.contains("ERROR_FILE")) {

				updateProcessIsClaim();

				updateClaimAndVolume();

				updateScope();

				// updateVolumeInThousand();

				updateTotalInvestmentInLacs();

				updateSupportingRequired();

			}

			if (!responseList.contains("ERROR_FILE")) {
				String userId = "";
				if (!this.saveTotMainTable(userId)) {
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
		if (responseList.contains("ERROR_FILE")) {
			response = "ERROR_FILE";
		}
		return response;
	}

	public synchronized boolean saveTotMainTable(String userId) {
		try {
			insertIntoMaster(userId);
			return true;
		} catch (HibernateException e) {
			logger.error(
					"Inside ProcoStatusTrackerDAOImpl: saveToMainTable() : HibernateException : " + e.getMessage());
			return false;

		}
	}

	@Transactional(rollbackOn = { Exception.class })
	public void insertIntoMaster(String userId) {
		try {

			// kiran - rownumber changes and TBL_PROCO_MEASURE_REPORT_temp(changed
			// TBL_PROCO_MEASURE_REPORT_TEMP)

			/*
			 * String
			 * removeDublicateSql="delete  FROM  (SELECT ROWNUMBER() OVER (PARTITION BY PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,"
			 * +
			 * "PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,"
			 * +
			 * "MOC,MOC_YEAR) AS RN FROM TBL_PROCO_MEASURE_REPORT_temp) AS A WHERE RN > 1";
			 */
			
			//Sarin Changes - MySQL Compatible - Added below
			/*String removeDublicateSql = "delete  FROM  (SELECT ROW_NUMBER() OVER (PARTITION BY PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,"
					+ "PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,"
					+ "MOC,MOC_YEAR) AS RN FROM TBL_PROCO_MEASURE_REPORT_TEMP) AS A WHERE RN > 1"; */
			String removeDublicateSql = "DELETE A FROM TBL_PROCO_MEASURE_REPORT_TEMP A INNER JOIN (SELECT ROW_NUMBER() OVER (PARTITION BY PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR) AS RN, PROMOTION_ID, CUSTOMER FROM TBL_PROCO_MEASURE_REPORT_TEMP) B ON B.CUSTOMER = A.CUSTOMER AND B.PROMOTION_ID = A.PROMOTION_ID AND B.RN > 1";
			
			updateAccountName();

			String crateNewSql = "INSERT INTO TBL_PROCO_MEASURE_REPORT_MASTER (PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,"
					+ "IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,"
					+ "SIGNED_OFF_SHARED_DATE,SIGNED_OPS_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND) SELECT DISTINCT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,"
					+ "TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND FROM TBL_PROCO_MEASURE_REPORT_TEMP "
					+ "WHERE  PROMOTION_STATUS = 'Approved'"; // OR PROMOTION_STATUS = 'Amend Submitted' OR
																// PROMOTION_STATUS = 'Submitted'";
			// Garima - changed Merge query
			/*
			 * String updateSql="MERGE INTO TBL_PROCO_MEASURE_REPORT_MASTER AS A " +
			 * "USING (SELECT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,"
			 * +
			 * "SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE  "
			 * +
			 * "PROMOTION_STATUS = 'Financial Closed' OR PROMOTION_STATUS = 'Amend Approved') B "
			 * + "ON (A.PROMOTION_ID = B.PROMOTION_ID) " + "WHEN MATCHED THEN " +
			 * "UPDATE SET " +
			 * "A.PROMOTION_ID = B.PROMOTION_ID , A.PROMOTION_NAME=B.PROMOTION_NAME, A.SCOPE=B.SCOPE,A.PROCESS=B.PROCESS,A.IS_CLAIMED=B.IS_CLAIMED,"
			 * +
			 * "A.PRODUCT=B.PRODUCT,A.CLIAM_VALUE=B.CLIAM_VALUE,A.PROMO_VOLUMN_IN_THOUSAND=B.PROMO_VOLUMN_IN_THOUSAND,A.TOTAL_INVESTMENT_IN_LACS=B.TOTAL_INVESTMENT_IN_LACS,"
			 * +
			 * "A.SUPPORING_REQUIRED=B.SUPPORING_REQUIRED,A.PROMOTION_START_DATE=B.PROMOTION_START_DATE,A.PROMOTION_END_DATE=B.PROMOTION_END_DATE,A.SIGNED_OFF_SHARED_DATE=B.SIGNED_OFF_SHARED_DATE,"
			 * + "A.SIGNED_OPS_STATUS=B.PROMOTION_STATUS,A.MOC=B.MOC,A.MOC_YEAR=B.MOC_YEAR";
			 */
			String updateSql = "UPDATE TBL_PROCO_MEASURE_REPORT_MASTER AS A INNER JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_STATUS = 'Financial Close' OR PROMOTION_STATUS = 'AmendApproved') B ON A.PROMOTION_ID = B.PROMOTION_ID SET A.PROMOTION_ID = B.PROMOTION_ID, A.PROMOTION_NAME = B.PROMOTION_NAME, A.SCOPE = B.SCOPE, A.PROCESS = B.PROCESS,A.IS_CLAIMED = B.IS_CLAIMED, A.PRODUCT = B.PRODUCT, A.CLIAM_VALUE = B.CLIAM_VALUE, A.PROMO_VOLUMN_IN_THOUSAND = B.PROMO_VOLUMN_IN_THOUSAND, A.TOTAL_INVESTMENT_IN_LACS = B.TOTAL_INVESTMENT_IN_LACS, A.SUPPORING_REQUIRED = B.SUPPORING_REQUIRED, A.PROMOTION_START_DATE = B.PROMOTION_START_DATE, A.PROMOTION_END_DATE = B.PROMOTION_END_DATE, A.SIGNED_OFF_SHARED_DATE = B.SIGNED_OFF_SHARED_DATE, A.SIGNED_OPS_STATUS = B.PROMOTION_STATUS, A.MOC = B.MOC,A.MOC_YEAR = B.MOC_YEAR,A.CHILD_ACCOUNT_NAME=B.CHILD_ACCOUNT_NAME,A.PARENT_ACCOUNT_NAME=B.PARENT_ACCOUNT_NAME ,A.SUB_BRAND=B.SUB_BRAND";

			Query queryRemoveDublicate = sessionFactory.getCurrentSession().createNativeQuery(removeDublicateSql);
			queryRemoveDublicate.executeUpdate();

			Query queryCreateNew = sessionFactory.getCurrentSession().createNativeQuery(crateNewSql);
			queryCreateNew.executeUpdate();

			Query queryUpdateExisting = sessionFactory.getCurrentSession().createNativeQuery(updateSql);
			queryUpdateExisting.executeUpdate();

		} catch (Exception e) {
			logger.error(
					"Error in com.hul.proco.controller.promostatustracker.ProcoStatusTrackerDAOImpl.insertIntoTotMaster(String)",
					e);
		}

	}

	private int updateAccountName() {
		try {
			int executeUpdate = 0;
			String qry1 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'MORE' WHERE (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ABRL SUPER%') OR(SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ADIT%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ABRL HYPER%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ADIH%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MORE%')";
			String qry2 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Reliance' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE RETAIL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELI%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SHAKARI BHANDAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE TRENDS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE CNC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RECC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE C&C%')";
			String qry3 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'GARL' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GARL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GODREJ%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AADHAR RETAIL%')";
			String qry4 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'D MART' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%D MART%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DMART%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ASML%')";
			String qry5 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'DNCP' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DNCP%'";
			String qry6 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Dabur' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DABUR%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HBSL%')";
			String qry7 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'FRL' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FRL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HYPERCITY%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HYPE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HERITAGE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HERI%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FOODBAZAAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FOOD BAZAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BIG BAZAR%')";
			String qry8 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'BHRL' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BHARTI RETAIL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BHRL%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WMRT%')";
			String qry9 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'FDWD' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FDWD%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FOOD WORLD%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FOODWORLD%')";
			String qry10 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'HEALTH AND GLOW' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HEALTH AND GLOW%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HNG%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%H&G%')";
			String qry11 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'MAX HYPER' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MAX%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MAX HYPER%')";
			String qry12 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'LULU' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%LULU%'";
			String qry13 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'NMT' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%NMT%'";
			String qry14 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Nilgiris' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%NILGIRIS%'";
			String qry15 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Lifestyle' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%LIFESTYLE%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%LSIP%')";
			String qry16 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Westside' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WEST SIDE%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WESTSIDE%')";
			String qry17 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Pantaloon' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PANTALOON%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PANTALOONS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PANT%')";
			String qry18 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'CENTRAL' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%CENTRAL%'";
			String qry19 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'SHOPPERS STOP' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SHOPPERS STOP%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%STOPPER STOP%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SHOPPER%')";
			String qry20 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Spencer' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SPENCER%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SPENCERS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SPEN%')";
			String qry21 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'TESCO' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%TESCO%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%STAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%TRENT%')";
			String qry22 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'VISHAL MEGA MART' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%VISHAL MEGA MART%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%VMM%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%VISHAL MM%')";
			String qry23 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Amazon CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AMEC%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AMAZON%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AMAZON C&C%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AMAZON CNC%')";
			String qry24 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Booker CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BOOKER CNC%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BOOKER%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BIPL%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%BOOKER C&C%')";
			String qry25 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Metro CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%METRO%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%METRO CNC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%METRO C&C%')";
			String qry26 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Walmart CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WALMART CNC%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WRMC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WALMART C&C%')";
			String qry27 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'LCCP CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%LCCP%'";
			String qry28 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'JBTL CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%JBTL%'";
			String qry29 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'CPWI CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%CPWI%'";
			String qry30 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'SMGS CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SMGS%'";
			String qry31 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'UDAAN CNC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%UDAAN%'";
			String qry32 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'QPS' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%QPS PAYOUT%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%QPS PROVISION%')";
			String qry33 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'MT-RC' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ANNACHI%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ARAMBAGH%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DORABJEE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FEMINA SHOPPING MALL%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GRACE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HAIKO%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%KANNAN%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MK%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '% MK RETAIL%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%NEW MODERN BAZAAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ONDOOR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PAZHAMUDHIR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RATANDEEP%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ROYAL MART%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SARAVANA%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%USHODAYA%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%V MART%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%V2 RETAIL%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%VIJETHA%' OR SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MT-RC%')";
			String qry34 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'MT-Pharma' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%98.4%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%APOLLO PHARMACY%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%APOLLO%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%FORTIS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GUARDIAN%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MEDPLUS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PHARMA CHAIN%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%PHARMA%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WELLNESS FOREVER%') OR  (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%WELLNESS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MT-PHARMA%')";
			String qry35 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Elasticrun' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ELASTICRUN%'";
			String qry36 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'SHOPX' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SHOPX%'";

			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(qry1);
			executeUpdate = query1.executeUpdate();
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(qry2);
			executeUpdate = query2.executeUpdate();
			Query query3 = sessionFactory.getCurrentSession().createNativeQuery(qry3);
			executeUpdate = query3.executeUpdate();
			Query query4 = sessionFactory.getCurrentSession().createNativeQuery(qry4);
			executeUpdate = query4.executeUpdate();
			Query query5 = sessionFactory.getCurrentSession().createNativeQuery(qry5);
			executeUpdate = query5.executeUpdate();
			Query query6 = sessionFactory.getCurrentSession().createNativeQuery(qry6);
			executeUpdate = query6.executeUpdate();
			Query query7 = sessionFactory.getCurrentSession().createNativeQuery(qry7);
			executeUpdate = query7.executeUpdate();
			Query query8 = sessionFactory.getCurrentSession().createNativeQuery(qry8);
			executeUpdate = query8.executeUpdate();
			Query query9 = sessionFactory.getCurrentSession().createNativeQuery(qry9);
			executeUpdate = query9.executeUpdate();
			Query query10 = sessionFactory.getCurrentSession().createNativeQuery(qry10);
			executeUpdate = query10.executeUpdate();
			Query query11 = sessionFactory.getCurrentSession().createNativeQuery(qry11);
			executeUpdate = query11.executeUpdate();
			Query query12 = sessionFactory.getCurrentSession().createNativeQuery(qry12);
			executeUpdate = query12.executeUpdate();
			Query query13 = sessionFactory.getCurrentSession().createNativeQuery(qry13);
			executeUpdate = query13.executeUpdate();
			Query query14 = sessionFactory.getCurrentSession().createNativeQuery(qry14);
			executeUpdate = query14.executeUpdate();
			Query query15 = sessionFactory.getCurrentSession().createNativeQuery(qry15);
			executeUpdate = query15.executeUpdate();
			Query query16 = sessionFactory.getCurrentSession().createNativeQuery(qry16);
			executeUpdate = query16.executeUpdate();
			Query query17 = sessionFactory.getCurrentSession().createNativeQuery(qry17);
			executeUpdate = query17.executeUpdate();
			Query query18 = sessionFactory.getCurrentSession().createNativeQuery(qry18);
			executeUpdate = query18.executeUpdate();
			Query query19 = sessionFactory.getCurrentSession().createNativeQuery(qry19);
			executeUpdate = query19.executeUpdate();
			Query query20 = sessionFactory.getCurrentSession().createNativeQuery(qry20);
			executeUpdate = query20.executeUpdate();
			Query query21 = sessionFactory.getCurrentSession().createNativeQuery(qry21);
			executeUpdate = query21.executeUpdate();
			Query query22 = sessionFactory.getCurrentSession().createNativeQuery(qry22);
			executeUpdate = query22.executeUpdate();
			Query query23 = sessionFactory.getCurrentSession().createNativeQuery(qry23);
			executeUpdate = query23.executeUpdate();
			Query query24 = sessionFactory.getCurrentSession().createNativeQuery(qry24);
			executeUpdate = query24.executeUpdate();
			Query query25 = sessionFactory.getCurrentSession().createNativeQuery(qry25);
			executeUpdate = query25.executeUpdate();
			Query query26 = sessionFactory.getCurrentSession().createNativeQuery(qry26);
			executeUpdate = query26.executeUpdate();
			Query query27 = sessionFactory.getCurrentSession().createNativeQuery(qry27);
			executeUpdate = query27.executeUpdate();
			Query query28 = sessionFactory.getCurrentSession().createNativeQuery(qry28);
			executeUpdate = query28.executeUpdate();
			Query query29 = sessionFactory.getCurrentSession().createNativeQuery(qry29);
			executeUpdate = query29.executeUpdate();
			Query query30 = sessionFactory.getCurrentSession().createNativeQuery(qry30);
			executeUpdate = query30.executeUpdate();
			Query query31 = sessionFactory.getCurrentSession().createNativeQuery(qry31);
			executeUpdate = query31.executeUpdate();
			Query query32 = sessionFactory.getCurrentSession().createNativeQuery(qry32);
			executeUpdate = query32.executeUpdate();
			Query query33 = sessionFactory.getCurrentSession().createNativeQuery(qry33);
			executeUpdate = query33.executeUpdate();
			Query query34 = sessionFactory.getCurrentSession().createNativeQuery(qry34);
			executeUpdate = query34.executeUpdate();
			Query query35 = sessionFactory.getCurrentSession().createNativeQuery(qry35);
			executeUpdate = query35.executeUpdate();
			Query query36 = sessionFactory.getCurrentSession().createNativeQuery(qry36);
			executeUpdate = query36.executeUpdate();

			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}

	private int updateProcessIsClaim() {
		try {
			// Commented By Sarin - 20Nov2020
			// String qry = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROCESS='Non
			// Kitting',IS_CLAIMED='Yes', MOC_YEAR = (SELECT CAST(YEAR(CURRENT_DATE)AS
			// VARCHAR(4)) FROM SYSIBM.SYSDUMMY1)";
			String qry = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROCESS='Non Kitting',IS_CLAIMED='Yes', MOC_YEAR = (SELECT DATE_FORMAT(NOW(), '%Y'))";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	private int updateClaimAndVolume() {
		try {
			int executeUpdate = 0;
			String getSolcodeSql = "SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			// String updateClaimAndVolumnSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP
			// SET CLIAM_VALUE =(SELECT DEC(SUM(PLANNED_INVESTMENT_AMOUNT),15,0) FROM
			// TBL_PROCO_MEASURE_REPORT_TEMP WHERE
			// PROMOTION_ID=:solcode1),PROMO_VOLUMN_IN_THOUSAND='0' WHERE
			// PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-VI-%'";
			// String updateClaimSqlForNonDIVI ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET
			// CLIAM_VALUE =:claimValue WHERE PROMOTION_ID=:solcode AND
			// REPLACE(PROMOTION_NAME,' ','') NOT LIKE 'DI-VI-%'";

			// String updateVolumnSqlForNonDIVI="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET
			// PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)FROM
			// TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE
			// PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE
			// 'DI-VI-%'";
			// String getPromotionNameSql ="SELECT DISTINCT PROMOTION_NAME FROM
			// TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode";

			// Sarin Changes commented below line and added agian for dec()
			// String updateClaimSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET
			// CLIAM_VALUE =(SELECT DEC(SUM(PLANNED_INVESTMENT_AMOUNT),15,0) FROM
			// TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE
			// PROMOTION_ID=:solcode2 AND REPLACE(INVESTMENT_TYPE,' ','') IN
			// ('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			//Sarin Changes.
			//String updateClaimSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =(SELECT CAST(SUM(PLANNED_INVESTMENT_AMOUNT) AS DECIMAL(15,0))  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(INVESTMENT_TYPE,' ','') IN ('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String updateClaimSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP AS A INNER JOIN (SELECT CAST(SUM(PLANNED_INVESTMENT_AMOUNT) AS DECIMAL(15,0)) AS CLAIMVALUE FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) AS B ON 1=1 SET A.CLIAM_VALUE = B.CLAIMVALUE WHERE A.PROMOTION_ID=:solcode2 AND REPLACE(INVESTMENT_TYPE,' ','') IN ('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay', 'KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM', 'KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String updateVolumnSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND='0'  WHERE PROMOTION_ID=:solcode  AND REPLACE(PROMOTION_NAME,' ','') LIKE '%-VI%' ";
			String updateClaimSqlForNonDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =:claimValue  WHERE PROMOTION_ID=:solcode  AND REPLACE(INVESTMENT_TYPE,' ','') NOT IN('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			// Sarin Changes commented below line and added agian for dec()
			// String updateVolumnSqlForNonDIVI="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET
			// PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)FROM
			// TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE
			// PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE '%-VI%'";
			
			//Sarin Changes
			//String updateVolumnSqlForNonDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND =(SELECT CAST(SUM(PROMOTION_VOLUME_DURING) AS DECIMAL(15,2)) FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE '%-VI%'";
			String updateVolumnSqlForNonDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP AS A INNER JOIN (SELECT CAST(SUM(PROMOTION_VOLUME_DURING) AS DECIMAL(15,2)) AS POMOVOLUME FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) B ON 1=1 SET A.PROMO_VOLUMN_IN_THOUSAND = B.POMOVOLUME WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE '%-VI%'";
			String getPromotionNameSql = "SELECT DISTINCT PROMOTION_NAME FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode";

			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList = getSolcodeQuery.list();
			if (solcodeList.size() != 0) {
				for (String solcode : solcodeList) {
					/*
					 * Query updateClaimAndVolumnQueryForDIVI =
					 * sessionFactory.getCurrentSession().createNativeQuery(
					 * updateClaimAndVolumnSqlForDIVI);
					 * updateClaimAndVolumnQueryForDIVI.setString("solcode1", solcode);
					 * updateClaimAndVolumnQueryForDIVI.setString("solcode2", solcode);
					 * executeUpdate = updateClaimAndVolumnQueryForDIVI.executeUpdate();
					 */
					Query updateClaimQueryForDIVI = sessionFactory.getCurrentSession()
							.createNativeQuery(updateClaimSqlForDIVI);
					updateClaimQueryForDIVI.setString("solcode1", solcode);
					updateClaimQueryForDIVI.setString("solcode2", solcode);
					executeUpdate = updateClaimQueryForDIVI.executeUpdate();

					if (executeUpdate == 0) {

						/*
						 * Query updateVolumnQueryForNonDIVI =
						 * sessionFactory.getCurrentSession().createNativeQuery(
						 * updateVolumnSqlForNonDIVI); updateVolumnQueryForNonDIVI.setString("solcode1",
						 * solcode); updateVolumnQueryForNonDIVI.setString("solcode2", solcode);
						 * executeUpdate = updateVolumnQueryForNonDIVI.executeUpdate();
						 */

						Query getPromotionNameQuery = sessionFactory.getCurrentSession()
								.createNativeQuery(getPromotionNameSql);
						getPromotionNameQuery.setString("solcode", solcode);
						List<String> promotionNameList = getPromotionNameQuery.list();

						if (promotionNameList.size() != 0) {
							String startWord = "GET";
							String endword = "OFF";
							String claimValue = "";
							String promoName = "";
							for (String name : promotionNameList) {
								/*
								 * promoName =name.toUpperCase(); claimValue =
								 * promoName.substring(promoName.indexOf(startWord)+3,
								 * promoName.lastIndexOf(endword)+3); Query updateClaimQueryForNonDIVI =
								 * sessionFactory.getCurrentSession().createNativeQuery(updateClaimSqlForNonDIVI
								 * ); updateClaimQueryForNonDIVI.setString("solcode", solcode);
								 * updateClaimQueryForNonDIVI.setString("claimValue", claimValue); executeUpdate
								 * = updateClaimQueryForNonDIVI.executeUpdate();
								 */

								try {
									promoName = name.toUpperCase();
									if (promoName.contains(startWord) && promoName.contains(endword)) {
										claimValue = promoName.substring(promoName.indexOf(startWord) + 3,
												promoName.lastIndexOf(endword) + 3);
									} else {
										claimValue = "NA";
									}

									Query updateClaimQueryForNonDIVI = sessionFactory.getCurrentSession()
											.createNativeQuery(updateClaimSqlForNonDIVI);
									updateClaimQueryForNonDIVI.setString("solcode", solcode);
									updateClaimQueryForNonDIVI.setString("claimValue", claimValue);
									executeUpdate = updateClaimQueryForNonDIVI.executeUpdate();
								} catch (Exception e) {
									logger.debug("Exception: ", e);
									logger.error("Inside ProcoStatusTrackerDAOImpl: updateClaimAndVolume() : "
											+ e.getMessage());
								}

							}
						}
					}
				}
			}

			if (solcodeList.size() != 0) {
				for (String solcode : solcodeList) {
					Query updateVolumnQueryForDIVI = sessionFactory.getCurrentSession()
							.createNativeQuery(updateVolumnSqlForDIVI);
					updateVolumnQueryForDIVI.setString("solcode", solcode);
					executeUpdate = updateVolumnQueryForDIVI.executeUpdate();

					if (executeUpdate == 0) {

						Query updateVolumnQueryForNonDIVI = sessionFactory.getCurrentSession()
								.createNativeQuery(updateVolumnSqlForNonDIVI);
						updateVolumnQueryForNonDIVI.setString("solcode1", solcode);
						updateVolumnQueryForNonDIVI.setString("solcode2", solcode);
						executeUpdate = updateVolumnQueryForNonDIVI.executeUpdate();

					}
				}
			}

			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			logger.error("Inside ProcoStatusTrackerDAOImpl: updateClaimAndVolume() : " + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	private int updateScope() {
		try {
			int executeUpdate = 0;

			String getSolcodeSql = "SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			String updateRegionForAlphabeticCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SCOPE=:scope WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%' ";
			/*
			 * String getAlphabeticCustomerSql
			 * ="SELECT CUSTOMER  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%' "
			 * ; String
			 * updateWestRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='West' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='1'"
			 * ; String
			 * updateEastRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='East' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='2'"
			 * ; String
			 * updateNorthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='North' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='3'"
			 * ; String
			 * updateSouthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='South' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='4'"
			 * ; String
			 * updateAllIndiaRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='All India' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND REPLACE(A.PROMOTION_NAME,' ','') LIKE 'DI-VI%' AND SUBSTRING(REPLACE(A.INVESTMENT_TYPE,' ',''),1,3) ='CnC'"
			 * ;
			 */
			String getAlphabeticCustomerSql = "SELECT CUSTOMER  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%'AND LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' ";
			String updateWestRegionForNumericCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='West' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='1') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='1') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='1')";
			String updateEastRegionForNumericCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='East' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='2') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='2') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='2')";
			String updateNorthRegionForNumericCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='North' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='3') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='3') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='3')";
			String updateSouthRegionForNumericCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='South' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='4') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='4') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='4')";
			String updateAllIndiaRegionForNumericCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='All India' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND REPLACE(A.PROMOTION_NAME,' ','') LIKE '%-VI%' AND SUBSTRING(REPLACE(A.INVESTMENT_TYPE,' ',''),1,3) ='CnC'";
			String updateScopesql = "SELECT DISTINCT SCOPE FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode";
			String updateConsolidatedScopeSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SCOPE=:scope WHERE PROMOTION_ID=:solcode";

			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList = getSolcodeQuery.list();
			if (solcodeList.size() != 0) {
				for (String solcode : solcodeList) {
					Query getAlphabeticCustomerQuery = sessionFactory.getCurrentSession()
							.createNativeQuery(getAlphabeticCustomerSql);
					getAlphabeticCustomerQuery.setString("solcode", solcode);
					List<String> alphabeticCustomerList = getAlphabeticCustomerQuery.list();
					String scope = String.join(",", alphabeticCustomerList);
					executeUpdate = 0;
					if (!alphabeticCustomerList.isEmpty()) {
						Query customerUpdateQuery = sessionFactory.getCurrentSession()
								.createNativeQuery(updateRegionForAlphabeticCustomerSql);
						customerUpdateQuery.setString("scope", scope);
						customerUpdateQuery.setString("solcode", solcode);
						executeUpdate = customerUpdateQuery.executeUpdate();

					}

				}
				/*
				 * Query updateWestRegionQuery =
				 * sessionFactory.getCurrentSession().createNativeQuery(
				 * updateWestRegionForNumericCustomerSql); executeUpdate =
				 * updateWestRegionQuery.executeUpdate(); Query updateEastRegionQuery =
				 * sessionFactory.getCurrentSession().createNativeQuery(
				 * updateEastRegionForNumericCustomerSql); executeUpdate =
				 * updateEastRegionQuery.executeUpdate(); Query updateNorthRegionQuery =
				 * sessionFactory.getCurrentSession().createNativeQuery(
				 * updateNorthRegionForNumericCustomerSql); executeUpdate =
				 * updateNorthRegionQuery.executeUpdate(); Query updateSouthRegionQuery =
				 * sessionFactory.getCurrentSession().createNativeQuery(
				 * updateSouthRegionForNumericCustomerSql); executeUpdate =
				 * updateSouthRegionQuery.executeUpdate(); Query updateAllIndiaRegionQuery =
				 * sessionFactory.getCurrentSession().createNativeQuery(
				 * updateAllIndiaRegionForNumericCustomerSql); executeUpdate =
				 * updateAllIndiaRegionQuery.executeUpdate();
				 */

			}

			Query updateWestRegionQuery = sessionFactory.getCurrentSession()
					.createNativeQuery(updateWestRegionForNumericCustomerSql);
			executeUpdate = updateWestRegionQuery.executeUpdate();
			Query updateEastRegionQuery = sessionFactory.getCurrentSession()
					.createNativeQuery(updateEastRegionForNumericCustomerSql);
			executeUpdate = updateEastRegionQuery.executeUpdate();
			Query updateNorthRegionQuery = sessionFactory.getCurrentSession()
					.createNativeQuery(updateNorthRegionForNumericCustomerSql);
			executeUpdate = updateNorthRegionQuery.executeUpdate();
			Query updateSouthRegionQuery = sessionFactory.getCurrentSession()
					.createNativeQuery(updateSouthRegionForNumericCustomerSql);
			executeUpdate = updateSouthRegionQuery.executeUpdate();
			Query updateAllIndiaRegionQuery = sessionFactory.getCurrentSession()
					.createNativeQuery(updateAllIndiaRegionForNumericCustomerSql);
			executeUpdate = updateAllIndiaRegionQuery.executeUpdate();

			if (solcodeList.size() != 0) {
				for (String solcode : solcodeList) {
					Query getAllScopesQuery = sessionFactory.getCurrentSession().createNativeQuery(updateScopesql);
					getAllScopesQuery.setString("solcode", solcode);
					List<String> scopeList = getAllScopesQuery.list();
					String scope = String.join(",", scopeList);
					executeUpdate = 0;
					if (!scopeList.isEmpty()) {
						Query updateConsolidatedScopeQuery = sessionFactory.getCurrentSession()
								.createNativeQuery(updateConsolidatedScopeSql);
						if ((scope.contains("West") && scope.contains("East") && scope.contains("North")
								&& scope.contains("South")) || scope.contains("All India")) {
							scope = "All India";
						}
						updateConsolidatedScopeQuery.setString("scope", scope);
						updateConsolidatedScopeQuery.setString("solcode", solcode);
						executeUpdate = updateConsolidatedScopeQuery.executeUpdate();

					}

				}
			}

			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			logger.error("Inside ProcoStatusTrackerDAOImpl: updateScope() : " + e.getMessage());
			return 0;
		}
	}

	/*
	 * @SuppressWarnings("unchecked") private int updateVolumeInThousand() {
	 * 
	 * try { int executeUpdate=0; String getSolcodeSql
	 * ="SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP"; String
	 * updateVolumeInThousandSql
	 * ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE 'DI-VI-%'"
	 * ; Query getSolcodeQuery =
	 * sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
	 * List<String> solcodeList =getSolcodeQuery.list(); if(solcodeList.size()!=0) {
	 * for(String solcode:solcodeList) { Query updateVolumnInThousandQuery =
	 * sessionFactory.getCurrentSession().createNativeQuery(
	 * updateVolumeInThousandSql); updateVolumnInThousandQuery.setString("solcode1",
	 * solcode); updateVolumnInThousandQuery.setString("solcode2", solcode);
	 * executeUpdate = updateVolumnInThousandQuery.executeUpdate();
	 * 
	 * } } return executeUpdate; } catch (Exception e) { logger.debug("Exception: ",
	 * e);
	 * logger.error("Inside ProcoStatusTrackerDAOImpl: updateVolumeInThousand() : "
	 * + e.getMessage()); return 0; } }
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues() {
		ArrayList<ArrayList<String>> ResList = new ArrayList<ArrayList<String>>();
		try {
			String getStatus = "SELECT * FROM TBL_PROCO_SOLCODE_STATUS_MASTER";
			Query Stquery = sessionFactory.getCurrentSession().createNativeQuery(getStatus);

			ArrayList<String> list = (ArrayList<String>) Stquery.list();

			ResList.add(list);

			String getMechMst = "SELECT * FROM TBL_PROCO_MECHANICS_MASTER";
			Query Mechquery = sessionFactory.getCurrentSession().createNativeQuery(getMechMst);

			ArrayList<String> Mechlist = (ArrayList<String>) Mechquery.list();
			ResList.add(Mechlist);

			String getInvType = "SELECT * FROM TBL_PROCO_INVESTMENT_TYPE_MASTER";
			Query InTyquery = sessionFactory.getCurrentSession().createNativeQuery(getInvType);

			ArrayList<String> Inlist = (ArrayList<String>) InTyquery.list();

			ResList.add(Inlist);

			String getCreatedByMasterSql = "SELECT * FROM TBL_PROCO_CREATED_BY_MASTER";
			Query getCreatedByMasterquery = sessionFactory.getCurrentSession().createNativeQuery(getCreatedByMasterSql);

			ArrayList<String> createdByList = (ArrayList<String>) getCreatedByMasterquery.list();
			ResList.add(createdByList);

		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		return ResList;
	}

	@SuppressWarnings("unchecked")
	private int updateTotalInvestmentInLacs() {

		try {
			int executeUpdate = 0;
			String getSolcodeSql = "SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			//Sarin changes migration
			//String updateTotalInvestmentInLacSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET TOTAL_INVESTMENT_IN_LACS=(SELECT CAST(ROUND((CAST(SUM(PLANNED_INVESTMENT_AMOUNT) AS DECIMAL(15,2))/100000),2) AS DECIMAL(15,2)) FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1)WHERE PROMOTION_ID=:solcode2";
			String updateTotalInvestmentInLacSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP AS A INNER JOIN (SELECT CAST(ROUND((CAST(SUM(PLANNED_INVESTMENT_AMOUNT) AS DECIMAL(15,2))/100000),2) AS DECIMAL(15,2)) AS PLAN_INVEST FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) AS B ON 1=1 SET A.TOTAL_INVESTMENT_IN_LACS = B.PLAN_INVEST WHERE A.PROMOTION_ID=:solcode2";
			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList = getSolcodeQuery.list();
			if (solcodeList.size() != 0) {
				for (String solcode : solcodeList) {
					Query updateTotalInvestmentInLacQuery = sessionFactory.getCurrentSession()
							.createNativeQuery(updateTotalInvestmentInLacSql);
					updateTotalInvestmentInLacQuery.setString("solcode1", solcode);
					updateTotalInvestmentInLacQuery.setString("solcode2", solcode);
					executeUpdate = updateTotalInvestmentInLacQuery.executeUpdate();

				}
			}
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			logger.error("Inside ProcoStatusTrackerDAOImpl: updateVolumeInThousand() : " + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unused")
	private void updateSupportingRequired() {

		try {
			/*
			 * String sqlForDIVI=
			 * "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Signed document from the customer for amt recd' WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-VI-%'"
			 * ; String
			 * sqlForDIPO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Offtake'  WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-PO-%'"
			 * ; String
			 * sqlForDIPOMETRO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Primary Sales'  WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-PO-METRO%'"
			 * ; String
			 * sqlForKAPO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Sales' WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'KA-PO-%'"
			 * ;
			 * 
			 * Query supporingQueryForDIVI =
			 * sessionFactory.getCurrentSession().createNativeQuery(sqlForDIVI); int
			 * executeUpdateForDIVI = supporingQueryForDIVI.executeUpdate();
			 * 
			 * Query supporingQueryForDIPO =
			 * sessionFactory.getCurrentSession().createNativeQuery(sqlForDIPO); int
			 * executeUpdateForDIPO = supporingQueryForDIPO.executeUpdate();
			 * 
			 * Query supporingQueryForDIPOMETRO =
			 * sessionFactory.getCurrentSession().createNativeQuery(sqlForDIPOMETRO); int
			 * executeUpdateForDIPOMETRO = supporingQueryForDIPOMETRO.executeUpdate();
			 * 
			 * Query supporingQueryForKAPO =
			 * sessionFactory.getCurrentSession().createNativeQuery(sqlForKAPO); int
			 * executeUpdateForKAPO = supporingQueryForKAPO.executeUpdate();
			 */

			String sqlSignedDocumentFromTheCustomerForAmtRecd = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Signed document from the customer for amt recd' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-Exclusive ConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-Exclusive ConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String sqlSecondaryOfftake = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Offtake'  WHERE  REPLACE(INVESTMENT_TYPE ,' ','') IN('MT-ExclusiveCustomCPPriceOff','MT-ExclusiveCustomSecondaryTPR-GST%12','MT-ExclusiveCustomSecondaryTPR-GST%18','MT-ExclusiveCustomSecondaryTPR-GST%5','MT-PTPR-%off','MT-PTPR-Rs.off')";
			String sqlPrimarySales = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Primary Sales'  WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN('CnC-ExclusiveCustomSecondaryTPR-GST%12','CnC-ExclusiveCustomSecondaryTPR-GST%18','CnC-ExclusiveCustomSecondaryTPR-GST%5')";
			String sqlSecondarySales = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Sales' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN ('KA-ExclusiveCustomCPPriceOff','KA-ExclusiveCustomSecondaryTPR-GST%12','KA-ExclusiveCustomSecondaryTPR-GST%18','KA-ExclusiveCustomSecondaryTPR-GST%5')";
			String sqlNotApplicable = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Not Applicable' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN ('CnCKittingGiftoff','CnCKittingPriceOff','MT-MTKittingGiftoff','MT-MTKittingPriceoff')";

			Query QuerySignedDocumentFromTheCustomerForAmtRecd = sessionFactory.getCurrentSession()
					.createNativeQuery(sqlSignedDocumentFromTheCustomerForAmtRecd);
			int executeUpdateSD = QuerySignedDocumentFromTheCustomerForAmtRecd.executeUpdate();

			Query querySecondaryOfftake = sessionFactory.getCurrentSession().createNativeQuery(sqlSecondaryOfftake);
			int executeUpdateSO = querySecondaryOfftake.executeUpdate();

			Query queryPrimarySales = sessionFactory.getCurrentSession().createNativeQuery(sqlPrimarySales);
			int executeUpdatePS = queryPrimarySales.executeUpdate();

			Query querySecondarySales = sessionFactory.getCurrentSession().createNativeQuery(sqlSecondarySales);
			int executeUpdateSS = querySecondarySales.executeUpdate();

			Query queryNotApplicable = sessionFactory.getCurrentSession().createNativeQuery(sqlNotApplicable);
			int executeUpdateNA = queryNotApplicable.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			logger.error("Inside ProcoStatusTrackerDAOImpl: updateSupportingRequired() : " + e.getMessage());
		}

	}

	/* private synchronized String validateRecord(PromoMeasureReportBean bean, int row) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			Query queryToCheckInvestmentType = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_INVESTMENT_TYPE_MASTER WHERE INVESTMENT_TYPE=:investmentType");
			queryToCheckInvestmentType.setString("investmentType", bean.getInvestmentType());
			// kiran - bigint to int changes
			// Integer investmentTypeCount = (Integer)
			// queryToCheckInvestmentType.uniqueResult();
			Integer investmentTypeCount = ((BigInteger) queryToCheckInvestmentType.uniqueResult()).intValue();

			Query queryToCheckPromotionMechanics = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_MECHANICS_MASTER WHERE PROMO_MECHANICS=:promotionMechanics");
			queryToCheckPromotionMechanics.setString("promotionMechanics", bean.getPromotionMechanics());
			// kiran - bigint to int changes
			// Integer promotionMechanicsCount = (Integer)
			// queryToCheckPromotionMechanics.uniqueResult();
			Integer promotionMechanicsCount = ((BigInteger) queryToCheckPromotionMechanics.uniqueResult()).intValue();

			Query queryToCheckPromotionStatus = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_SOLCODE_STATUS_MASTER WHERE SOLCODE_STATUS=:promotionStatus");
			queryToCheckPromotionStatus.setString("promotionStatus", bean.getPromotionStatus());
			// kiran - bigint to int changes
			// Integer promotionStatusCount = (Integer)
			// queryToCheckPromotionStatus.uniqueResult();
			Integer promotionStatusCount = ((BigInteger) queryToCheckPromotionStatus.uniqueResult()).intValue();

			Query queryToCheckPromotionIdExisting = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE PROMOTION_ID=:promotionId");
			queryToCheckPromotionIdExisting.setString("promotionId", bean.getPromotionId());
			// kiran - bigint to int changes
			// Integer existingPromotionIdCount = (Integer)
			// queryToCheckPromotionIdExisting.uniqueResult();
			Integer existingPromotionIdCount = ((BigInteger) queryToCheckPromotionIdExisting.uniqueResult()).intValue();

			Query queryToCheckCreatedBy = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_CREATED_BY_MASTER WHERE CREATED_BY=:createdBy");
			queryToCheckCreatedBy.setString("createdBy", bean.getCreatedBy());
			// Sarin - bigint to int changes
			//Integer createdByCount = (Integer) queryToCheckCreatedBy.uniqueResult();
			Integer createdByCount = ((BigInteger)queryToCheckCreatedBy.uniqueResult()).intValue();

			if (investmentTypeCount != null && investmentTypeCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid InvestmentType entered.^";
				updateErrorMessageInTemp(errorMsg, row);
			}

			if (promotionMechanicsCount != null && promotionMechanicsCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Mechanics entered.^";
				updateErrorMessageInTemp(errorMsg, row);
			}

			if (promotionStatusCount != null && promotionStatusCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Status entered.^";
				updateErrorMessageInTemp(errorMsg, row);
			}

			if (createdByCount != null && createdByCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Created By entered.^";
				updateErrorMessageInTemp(errorMsg, row);
			}

			if (bean.getPromotionId().length() > 8 || bean.getPromotionId().length() < 8
					|| !isNumeric(bean.getPromotionId())) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Solcode(promotionId) shuld be 8 digit number.^";
				updateErrorMessageInTemp(errorMsg, row);

			}

			if (!bean.getPromotionStatus().equals("Financial Close")
					&& !bean.getPromotionStatus().equals("AmendApproved")) {
				if (existingPromotionIdCount != null && existingPromotionIdCount > 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg
							+ "Solcode(promotionId) already exist, So we can't create again as per status  ^";
					updateErrorMessageInTemp(errorMsg, row);
				}
			}

			if (bean.getPromotionStatus().equals("Financial Close")
					|| bean.getPromotionStatus().equals("AmendApproved")) {
				if (existingPromotionIdCount != null && existingPromotionIdCount == 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg
							+ "Solcode(promotionId) doen't exist, So we can't update/drop as per as per status  ^";
					updateErrorMessageInTemp(errorMsg, row);
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	} */
	
	private synchronized List<String> validateRecord(PromoMeasureReportBean bean, int row, List<String> lstInvestmentType, List<String> lstPromoMechanics, 
			List<String> lstSolCodeStatus, List<String> lstCreatedBy) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		List<String> lstResult = new ArrayList<String>();
		Integer investmentTypeCount = 0;
		Integer promotionMechanicsCount = 0;
		Integer promotionStatusCount = 0;
		Integer createdByCount = 0;
		try {
			if (lstInvestmentType != null && lstInvestmentType.size() > 0) {
				for (String sInvType: lstInvestmentType) {
					if (bean.getInvestmentType().equals(sInvType)) {
						investmentTypeCount = 1;
					}
				}
			}

			if (lstPromoMechanics != null && lstPromoMechanics.size() > 0) {
				for (String sPromoMech: lstPromoMechanics) {
					if (bean.getPromotionMechanics().equals(sPromoMech)) {
						promotionMechanicsCount = 1;
					}
				}
			}

			if (lstSolCodeStatus != null && lstSolCodeStatus.size() > 0) {
				for (String sSolStatus: lstSolCodeStatus) {
					if (bean.getPromotionStatus().equals(sSolStatus)) {
						promotionStatusCount = 1;
					}
				}
			}

			Query queryToCheckPromotionIdExisting = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE PROMOTION_ID=:promotionId");
			queryToCheckPromotionIdExisting.setString("promotionId", bean.getPromotionId());
			Integer existingPromotionIdCount = ((BigInteger) queryToCheckPromotionIdExisting.uniqueResult()).intValue();

			if (lstCreatedBy != null && lstCreatedBy.size() > 0) {
				for (String sCreatedBy: lstCreatedBy) {
					if (bean.getCreatedBy().equals(sCreatedBy)) {
						createdByCount = 1;
					}
				}
			}

			if (investmentTypeCount != null && investmentTypeCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid InvestmentType entered.^";
				//updateErrorMessageInTemp(errorMsg, row);
			}

			if (promotionMechanicsCount != null && promotionMechanicsCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Mechanics entered.^";
				//updateErrorMessageInTemp(errorMsg, row);
			}

			if (promotionStatusCount != null && promotionStatusCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Status entered.^";
				//updateErrorMessageInTemp(errorMsg, row);
			}

			if (createdByCount != null && createdByCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Created By entered.^";
				//updateErrorMessageInTemp(errorMsg, row);
			}

			if (bean.getPromotionId().length() > 8 || bean.getPromotionId().length() < 8
					|| !isNumeric(bean.getPromotionId())) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Solcode(promotionId) shuld be 8 digit number.^";
				//updateErrorMessageInTemp(errorMsg, row);

			}

			if (!bean.getPromotionStatus().equals("Financial Close")
					&& !bean.getPromotionStatus().equals("AmendApproved")) {
				if (existingPromotionIdCount != null && existingPromotionIdCount > 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg
							+ "Solcode(promotionId) already exist, So we can't create again as per status  ^";
					//updateErrorMessageInTemp(errorMsg, row);
				}
			}

			if (bean.getPromotionStatus().equals("Financial Close")
					|| bean.getPromotionStatus().equals("AmendApproved")) {
				if (existingPromotionIdCount != null && existingPromotionIdCount == 0) {
					res = "ERROR_FILE";
					errorMsg = errorMsg
							+ "Solcode(promotionId) doen't exist, So we can't update/drop as per as per status  ^";
					//updateErrorMessageInTemp(errorMsg, row);
				}
			}
			lstResult.add(res);
			lstResult.add(errorMsg);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		//return res;
		return lstResult;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private synchronized int updateErrorMessageInTemp(String errorMsg, int row) {
		try {
			String qry = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET ERROR_MSG=:errorMsg WHERE ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
//			query.setString("userId", userId);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}

	@Override
	public List<PromoListingBean> readProcoStatusTracker(String excelFilePath) throws IOException, ParseException {

		List<PromoListingBean> list = new ArrayList<PromoListingBean>();
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rows = firstSheet.rowIterator();

		while (rows.hasNext()) {

			Row nextRow = rows.next();

			PromoListingBean promoListingBean = new PromoListingBean();
			if (nextRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					String valueOfCell = "";
					nextCell.setCellType(Cell.CELL_TYPE_STRING);
					valueOfCell = nextCell.toString();

					switch (columnIndex) {

					case 0:
						promoListingBean.setPromo_id(valueOfCell);
						break;
					case 1:
						promoListingBean.setOriginalId(valueOfCell);
						break;
					case 26:
						promoListingBean.setInvestmentType(valueOfCell);
						break;
					case 27:
						promoListingBean.setSolCode(valueOfCell);
						break;
					case 28:
						promoListingBean.setSolCodeDescription(valueOfCell);
						break;
					case 29:
						promoListingBean.setPromotionMechanics(valueOfCell);
						break;
					case 30:
						promoListingBean.setSolCodeStatus(valueOfCell);
						break;
					}
				}
				list.add(promoListingBean);
			}
		}

		return list;
	}

	public static Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}

	@Override
	public List<PromoMeasureReportBean> readPromoMeasureReport(String excelFilePath)
			throws IOException, ParseException {

		List<PromoMeasureReportBean> list = new ArrayList<PromoMeasureReportBean>();
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		// int noOfColumns = firstSheet.getRow(0).getLastCellNum();
		Iterator<Row> rows = firstSheet.rowIterator();
		// if (noOfColumns == 62) {
		while (rows.hasNext()) {

			Row nextRow = rows.next();

			PromoMeasureReportBean promoMeasureReportBean = new PromoMeasureReportBean();
			if (nextRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					String valueOfCell = "";
					nextCell.setCellType(Cell.CELL_TYPE_STRING);
					valueOfCell = nextCell.toString();

					switch (columnIndex) {

					case 0:
						promoMeasureReportBean.setPromotionId(valueOfCell);
						break;
					case 1:
						promoMeasureReportBean.setPromotionName(valueOfCell);
						break;
					case 2:
						promoMeasureReportBean.setCreatedBy(valueOfCell);
						break;
					case 11:
						promoMeasureReportBean.setPromotionMechanics(valueOfCell);
						break;
					case 12:
						promoMeasureReportBean.setPromotionStartDate(valueOfCell);
						break;
					case 13:
						promoMeasureReportBean.setPromotionEndDate(valueOfCell);
						break;
					case 16:
						promoMeasureReportBean.setCustomer(valueOfCell);
						break;
					case 19:
						promoMeasureReportBean.setProduct(valueOfCell);
						break;
					/*
					 * case 23: promoMeasureReportBean.setPromotionStatus(valueOfCell); break;
					 */
					case 20:
						promoMeasureReportBean.setCategory(valueOfCell);
						break;
					case 22:
						promoMeasureReportBean.setSubBrand(valueOfCell);
						break;
					case 23:
						promoMeasureReportBean.setPromotionStatus(valueOfCell);
						break;
					case 24:
						promoMeasureReportBean.setInvestmentType(valueOfCell);
						break;
					case 25:
						promoMeasureReportBean.setMoc(valueOfCell);
						break;
					case 26:
						promoMeasureReportBean.setSubmissionDate(valueOfCell);
						break;
					case 29:
						promoMeasureReportBean.setPromotionType(valueOfCell);
						break;
					case 38:
						promoMeasureReportBean.setPromotionVolumeDuring(valueOfCell);
						break;
					case 42:
						promoMeasureReportBean.setPlannedInvestmentAmount(valueOfCell);
						break;
					}
				}
				list.add(promoMeasureReportBean);
			}
		}
		// }
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getProcoMeasureReportErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT PROMOTION_ID ,PROMOTION_NAME,CREATED_BY ,PROMOTION_MECHANICS ,"
					+ "PROMOTION_START_DATE,PROMOTION_END_DATE,CUSTOMER,PRODUCT,PROMOTION_STATUS,CATEGORY ,"
					+ "INVESTMENT_TYPE,MOC ,SUBMISSION_DATE,PROMOTION_TYPE,PROMOTION_VOLUME_DURING,PLANNED_INVESTMENT_AMOUNT,"
					+ "ERROR_MSG FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			// query.setParameter("userId", userId);
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromotionStatusTrackerCustomerPortal(ArrayList<String> headerList,
			String cagetory, String brand, String basepack, String custChainL1, String custChainL2, String geography,
			String offerType, String modality, String year, String moc, String userId, int active, String promoId) {
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String promoQuery = "";
		try {

			// promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,VARCHAR_FORMAT(A.START_DATE,
			// 'DD/MM/YYYY') as start_date1,VARCHAR_FORMAT(A.END_DATE,
			// 'DD/MM/YYYY'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK
			// ,c.BASEPACK_DESC,replace(A.USER_ID,'','') as
			// Created_By,replace(c.CATEGORY,'','') as SALES_CATEGORY,replace(c.BRAND,'','')
			// as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY
			// ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE
			// ,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN
			// D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE
			// E.STATUS END) ,A.REASON,A.REMARK
			// ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY'),A.INVESTMENT_TYPE, A.SOL_CODE,
			// A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM
			// TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON
			// A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON
			// A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON
			// E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON
			// A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
			promoQuery = "SELECT DISTINCT A.PROMO_ID, A.ORIGINAL_ID, DATE_FORMAT(A.START_DATE, '%d/%m/%Y') as start_date1, DATE_FORMAT(A.END_DATE, '%d/%m/%Y'),(CASE WHEN LENGTH(REPLACE(A.MOC, 'MOC', '')) = 1 THEN CONCAT( 'MOC0', REPLACE(A.MOC, 'MOC', '') ) ELSE A.MOC END),A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,C.BASEPACK_DESC,replace(A.USER_ID,'','') as Created_By,replace(C.CATEGORY,'','') as SALES_CATEGORY,replace(C.BRAND,'','') as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE ,  (CASE WHEN A.KITTING_VALUE = '' THEN '0' ELSE A.KITTING_VALUE END) AS KITTING_VALUE,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,DATE_FORMAT((SELECT MAX(CHANGE_DATE) FROM TBL_PROCO_STATUS_TRACKER WHERE A.PROMO_ID = PROMO_ID AND A.P1_BASEPACK = BASEPACK AND A.STATUS = STATUS_ID),'%d/%m/%Y'),A.INVESTMENT_TYPE, (CASE WHEN A.SOL_CODE = '' OR A.SOL_CODE IS NULL THEN '0' ELSE A.SOL_CODE END) AS SOL_CODE, A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID AND A.P1_BASEPACK = D.BASEPACK AND A.STATUS = D.STATUS_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) AND A.STATUS = D.STATUS_ID ";

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
				promoQuery += "AND F.MODALITY_NO = '" + modality + "' ";
			}

			if (!promoId.equalsIgnoreCase("All")) {
				promoQuery += "AND A.PROMO_ID = '" + promoId + "' ";
			}

			promoQuery += "AND (" + moc + ") ";

			// promoQuery += "AND A.PROMO_ID = 'PID_2021MOC1_000005'";

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
				try {
					String GeoClust = dataObj.get(15);
					if (GeoClust != null) {
						String splitClust[] = GeoClust.split(",");
						for (int z = 0; z < splitClust.length; z++) {
							dataObj.set(15, splitClust[z].replaceAll("CL.*:", ""));
							downloadDataList.addAll(splitBasedOnAccount(dataObj));
							// downloadDataList.add(downSingCusDub);
						}
					} else {
						downloadDataList.addAll(splitBasedOnAccount(dataObj));
					}
				} catch (Exception ex) {
					logger.debug("Exception :", ex);
				}
				obj = null;
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	public List<ArrayList<String>> splitBasedOnAccount(ArrayList<String> dataObj) {
		String cusChain = dataObj.get(6);
		List<ArrayList<String>> resDt = new ArrayList<ArrayList<String>>();
		if (cusChain != null && !cusChain.equals("")) {
			String splitCus[] = cusChain.split(",");
			ArrayList<String> splitRow = new ArrayList<String>(dataObj);
			for (int q = 0; q < splitCus.length; q++) {
				splitRow.set(6, splitCus[q]);
				splitRow.set(16, disaggrigateQty(splitRow.get(0), splitRow.get(15), splitRow.get(7), splitRow.get(6)));
				ArrayList<String> splitCustomerRw = new ArrayList<String>(splitRow);
				splitCustomerRw = calcSalesAndEstimate(splitCustomerRw);
				resDt.add(splitCustomerRw);
			}
		} else {
			resDt.add(dataObj);
		}
		return resDt;
	}

	public boolean batchCustomerStatusTrackerReport(List<ArrayList<String>> dataObj) {
		PreparedStatement batchUpdate = null;
		int[] batchInsert = null;
		boolean batchInsertSuccess = true;
		int rowcount = 0;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;

		String insertQry = "INSERT INTO TBL_PROCO_STATUS_TACKER_CUSTOMER_PORTAL_EXPORT (PROMO_ID,ORGINAL_ID,START_DATA,END_DATE,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,BASEPACK,BASEPACK_DESCRIPTION,CREATE_BY,SALES_CATEGORY,SALES_BRAND,OFFER_DESCRIPTION,OFFER_TYPE,OFFER_MODALITY,GEOGRAPHY,QUANTITY,UOM,OFFER_VALUE,KITTING_VALUE,SALES_VALUE,ESTIMATE_SPEND,STATUS,REASON,REMARK,CHANGE_DATE,INVESTMENT_TYPE,SOL_CODE,SOL_CODE_DESCRIPTION,PROMOTION_MECH,SOL_CODE_STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {

			PreparedStatement removeOldData = sessionImpl.connection()
					.prepareStatement("DELETE FROM TBL_PROCO_STATUS_TACKER_CUSTOMER_PORTAL_EXPORT");
			removeOldData.executeUpdate();

			batchUpdate = null;
			List<int[]> batchResult = new ArrayList<int[]>();

			batchUpdate = sessionImpl.connection().prepareStatement(insertQry);
			for (List<String> NewRow : dataObj) {
				for (int countCol = 0; countCol < NewRow.size(); countCol++) {
					batchUpdate.setString((countCol + 1), NewRow.get(countCol));
				}
				rowcount++;
				batchUpdate.executeUpdate();
				batchResult.add(batchUpdate.executeBatch());
			}

			if (rowcount > 0) {
				for (int i = 0; i < batchResult.size(); i++) {
					batchInsert = batchResult.get(i);
					for (int j = 0; j < batchInsert.length; j++) {
						if (batchInsert[j] < 0) {
							batchInsertSuccess = false;
							break;
						}
					}
				}
			}
			if (batchInsertSuccess == true) {
				return true;
			} else {
				logger.error("Insert Customer portal status tracker batch error  ");
				return false;
			}
		} catch (Exception ex) {
			logger.error("Insert Customer portal status tracker error : ", ex);
		}
		return true;
	}

	public ArrayList<String> calcSalesAndEstimate(ArrayList<String> dataObj) {
		if (!dataObj.get(16).trim().equals("")) {
			/* get Basepack */
			String BasePack = dataObj.get(7).trim();
			/* get Quantity */
			if (isBigDecimal(dataObj.get(16))) {
				BigDecimal Quantity = new BigDecimal(dataObj.get(16));
				Query MaxTurQuery = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT GSV FROM TBL_PROCO_MAX_TUR WHERE BASEPACK='" + BasePack + "'");
				BigDecimal GSV = (BigDecimal) MaxTurQuery.uniqueResult();
				if (GSV != null) {
					/* Set Sales Value */
					BigDecimal SalesVal = GSV.multiply(Quantity);
					dataObj.set(20, SalesVal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
					BigDecimal EstimSpend = new BigDecimal(BigInteger.ZERO, 2);

					if (!dataObj.get(18).trim().equals("")) {
						String offerStr = dataObj.get(18).trim();
						if (offerStr.toUpperCase().indexOf("ABS") != -1) {
							String offerVal = offerStr.replace("ABS", "");
							if (isBigDecimal(offerVal)) {
								BigDecimal amount = new BigDecimal(offerVal);
								EstimSpend = amount.multiply(Quantity);
							}
						} else if (offerStr.toUpperCase().indexOf("%") != -1) {
							String offerVal = offerStr.replace("%", "");
							if (isBigDecimal(offerVal)) {
								BigDecimal Hundred = new BigDecimal(100);
								BigDecimal amount = new BigDecimal(offerVal);
								EstimSpend = amount.multiply(SalesVal);
								EstimSpend = EstimSpend.divide(Hundred);
							}
						}
					} else if (!dataObj.get(19).trim().equals("")) {
						String kittingStr = dataObj.get(19).trim();
						if (kittingStr.toUpperCase().indexOf("ABS") != -1) {
							String kittVal = kittingStr.replace("ABS", "");
							if (isBigDecimal(kittVal)) {
								BigDecimal amount = new BigDecimal(kittVal);
								EstimSpend = amount.multiply(Quantity);
							}
						} else if (kittingStr.toUpperCase().indexOf("%") != -1) {
							String kittVal = kittingStr.replace("%", "");
							if (isBigDecimal(kittVal)) {
								BigDecimal amount = new BigDecimal(kittVal);
								BigDecimal Hundred = new BigDecimal(100);
								EstimSpend = amount.multiply(SalesVal);
								EstimSpend = EstimSpend.divide(Hundred);
							}
						}
					}
					if (EstimSpend.compareTo(BigDecimal.ZERO) != 0) {
						/* set Estimated Spend */
						dataObj.set(21, EstimSpend.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
					}
				}
			}
		}
		return dataObj;
	}

	public String disaggrigateQty(String promoId, String cluster, String baseback, String CusL2) {
		String qty = "0";
		try {
			if (!promoId.isEmpty() && !cluster.isEmpty() && !baseback.isEmpty()) {
				Query query = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT CAST(ROUND(SUM(CASE WHEN A.KAM_SPLIT_STATUS = 0 THEN A.QUANTITY ELSE A.KAM_SPLIT END)) AS CHAR) AS DEPOT_QTY FROM TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL AS A INNER JOIN TBL_PROCO_PROMO_DISAGG_L2_LEVEL AS B ON A.PROMO_ID = B.PROMO_ID AND A.CUSTOMER_CHAIN_L2 = B.CUSTOMER_CHAIN_L2 WHERE A.PROMO_ID = '"
								+ promoId + "' AND A.CLUSTER = '" + cluster + "' AND A.CUSTOMER_CHAIN_L2 = '" + CusL2
								+ "' AND A.BASEPACK = '" + baseback + "'");

				qty = (String) query.uniqueResult();
				if (qty == null || qty.equals(null)) {
					qty = "0";
				}
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
		}
		return qty;
	}

	@Override
	public String disaggrigateQty(String promoId, String cluster, String baseback) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Added by Kavitha D for promo measure template starts-SPRINT 9
	public ArrayList<String> getPromoMeasureDownload(){
	ArrayList<String> headerList=new ArrayList<String>();
	/*headerList.add("Versioned Promotion ID");	
	headerList.add("ChartByType");		
	headerList.add("PromotionCreator");		
	headerList.add("PromotionStatus");		
	headerList.add("Promotion ID");		
	headerList.add("PromotionName");		
	headerList.add("Sell-in Start Date");		
	headerList.add("Sell-in End Date");	   
	headerList.add("Promotion Mechanics");		
	headerList.add("Investment Type");		
	headerList.add("Cluster Code");		
	headerList.add("Cluster Name");		
	headerList.add("Basepack Code");		
	headerList.add("Basepack Name");		
	headerList.add("Category");		
	headerList.add("Brand");	
	headerList.add("Sub-Brand");
	headerList.add("UOM");	   
	headerList.add("Tax");		
	headerList.add("Discount");		
	headerList.add("List Price");		
	headerList.add("Percent Promoted Volume");		
	headerList.add("Quantity");		
	headerList.add("BudgetHolderName");		
	headerList.add("FundType");		
	headerList.add("MOC");	
	headerList.add("InvestmentAmount");*/
	//Sprint 9 update code changes-Kavitha D
	headerList.add("PROMOTION ID");	
	headerList.add("PROMOTION NAME");		
	headerList.add("CREATED BY");		
	headerList.add("CREATED ON");		
	headerList.add("PROJECT ID");		
	headerList.add("PROJECT NAME");		
	headerList.add("BUNDLE ID");		
	headerList.add("BUNDLE NAME");	   
	headerList.add("PROMOTION QUALIFICATION");		
	headerList.add("PROMOTION OBJECTIVE");
	headerList.add("MARKETING OBJECTIVE");		
	headerList.add("PROMOTION MECHANICS");
	headerList.add("PROMOTION STARTDATE");	
	headerList.add("PROMOTION ENDDATE");		
	headerList.add("PRE DIP STARTDATE");		
	headerList.add("POST DIP ENDDATE");		
	headerList.add("CUSTOMER");		
	headerList.add("BUSINESS");		
	headerList.add("DIVISION");		
	headerList.add("PRODUCT");	
	headerList.add("CATEGORY");		
	headerList.add("BRAND");		
	headerList.add("SUB BRAND");		
	headerList.add("PROMOTION STATUS");
	headerList.add("INVESTMENT TYPE");	
	headerList.add("MOC");		
	headerList.add("SUBMISSION DATE");		
	headerList.add("APPROVED DATE");		
	headerList.add("MODIFIED DATE");		
	headerList.add("PROMOTION TYPE");	
	headerList.add("DURATION");		
	headerList.add("FREE PRODUCT NAME");	   
	headerList.add("PRICE OFF");		
	headerList.add("BASELINE QUANTITY");		
	headerList.add("BASELINE GSV");		
	headerList.add("BASELINE TURNOVER");
	headerList.add("BASELINE GROSS PROFIT");	
	headerList.add("PROMOTION VOLUME BEFORE");		
	headerList.add("PROMOTION VOLUME DURING");		
	headerList.add("PROMOTION VOLUME AFTER");	
	headerList.add("PLANNED GSV");		
	headerList.add("PLANNED TURNOVER");		
	headerList.add("PLANNED INVESTMENT AMOUNT");		
	headerList.add("PLANNED UPLIFT");	   
	headerList.add("PLANNED INCREMENTAL GROSS PROFIT");		
	headerList.add("PLANNED GROSS PROFIT");		
	headerList.add("PLANNED INCREMENTAL TURNOVER");		
	headerList.add("PLANNED CUSTOMER ROI");
    headerList.add("PLANNED COST PRICE BASED ROI");	
	headerList.add("PLANNED PROMOTION ROI");
	headerList.add("ACTUAL QUANTITY");		
	headerList.add("ACTUAL GSV");		
	headerList.add("ACTUAL TURNOVER");		
	headerList.add("ACTUAL INVESTMENT AMOUNT");		
	headerList.add("ACTUAL UPLIFT");		
	headerList.add("ACTUAL INCREMENTAL GROSS PROFIT");	   
	headerList.add("ACTUAL GROSS PROFIT");		
	headerList.add("ACTUAL INCREMENTAL TURNOVER");		
	headerList.add("ACTUAL CUSTOMER ROI");		
	headerList.add("ACTUAL COST PRICE BASED ROI");
    headerList.add("ACTUAL PROMOTION ROI");	
	headerList.add("UPLOAD REFERENCE NUMBER");		
	headerList.add("IS DUPLICATE");		
	
	return headerList;
	}
	//Added by Kavitha D for promo measure template ends-SPRINT 9

	//Added by Kavitha D for PPM coe remarks download template-SPRINT 9
	public List<ArrayList<String>> ppmCoeRemarksDownload(ArrayList<String> headerList){
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();

		try {
		
		
		String ppmQuery=" SELECT DISTINCT PROMO_ID FROM TBL_PROCO_PROMOTION_MASTER_V2 M "
				+ " WHERE NOT EXISTS (SELECT 1 FROM TBL_PROCO_PPM_COE_REMARKS C WHERE M.PROMO_ID = C.PROMO_ID AND C.COE_REMARKS = 'PPM Submitted') ";
		
		Query query = sessionFactory.getCurrentSession().createNativeQuery(ppmQuery);
		downloadDataList.add(headerList);

		Iterator itr = query.list().iterator();
		//downloadDataList.add(headerList);
		/*while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			ArrayList<String> dataObj = new ArrayList<String>();
			for (Object ob : obj) {
				String value = "";
				value = (ob == null) ? "" : ob.toString();
				dataObj.add(value.replaceAll("\\^", ","));
			}
			obj = null;
			downloadDataList.add(dataObj);
			//return downloadDataList;
		}*/
		
		itr = query.list().iterator();
		while (itr.hasNext()) {
			String obj = (String) itr.next();
			ArrayList<String> dataObj = new ArrayList<String>();
			String value = "";
			value = (obj == null) ? "" : obj.toString();
			dataObj.add(value.replaceAll("\\^", ","));
			obj = null;
			downloadDataList.add(dataObj);
		}
		
		/*List list = query.list();
		for(String str:list.toString()) {
			
		}*/
		//downloadDataList.addAll(query.list());
		return downloadDataList;
	} catch (Exception e) {
		logger.error("Exception: ", e);
	}
	return downloadDataList;
}
	
	@Override
	public ArrayList<String> ppmCoeRemarksDownloadHeaderList() {
		ArrayList<String> headerList=new ArrayList<String>();
		headerList.add("PROMO_ID");	
		headerList.add("COE_REMARKS");	
		return headerList;
	}

	public ArrayList<String> getPpmDownloadHeaders(){
		ArrayList<String> headerList=new ArrayList<String>();
		
		headerList.add("UPLOAD_REFERENCE_NUMBER");		
		headerList.add("PROMOTION_NAME");		
		headerList.add("PROMOTION_PERIOD");		
		headerList.add("SELL_IN_START_DATE");	   
		headerList.add("SELL_IN_END_DATE");		
		headerList.add("CUSTOMER_CODE");		
		headerList.add("PRODUCT_CODE");		
		headerList.add("PROMOTION_MECHANICS");
	    headerList.add("INVESTMENT_TYPE");	
		headerList.add("UoM");
		headerList.add("DISCOUNT");		
		headerList.add("PERCENT_PROMOTED_VOLUME");		
		headerList.add("PLANNED_QUANTITY");		
		headerList.add("INVESTMENT_AMOUNT");
		headerList.add("CUSTOMER_CHAIN_NAME"); //Added by Kavitha D-SPRINT 11 changes
		return headerList;		
	}
	//Added by kavitha D-SPRINT 9
	public List<String> getMOCforCoedownload() {
		String q=" SELECT DISTINCT MOC FROM TBL_PROCO_PROMOTION_MASTER_V2 M WHERE NOT EXISTS (SELECT 1 FROM TBL_PROCO_PPM_COE_REMARKS C WHERE M.PROMO_ID = C.PROMO_ID AND C.COE_REMARKS = 'PPM Submitted') "
				+ "ORDER BY concat(SUBSTRING(MOC, 3, 4), SUBSTRING(MOC, 1, 2))";
		
		return sessionFactory.getCurrentSession().createNativeQuery(q).list();
	}

	//Added by kavitha D for downloading ppm upload file-SPRINT 9

	@SuppressWarnings({ "rawtypes" })
	public List<ArrayList<String>> getPpmDownloadData(ArrayList<String> headers, String selMOC) {
		List<ArrayList<String>> downloadList = new ArrayList<ArrayList<String>>();
		try {
		NativeQuery query = sessionFactory.getCurrentSession().createNativeQuery("CALL PROC_POPULATE_PPM_UPLOADABLE(:selMOC)");
		query.setParameter("selMOC", selMOC);
		query.executeUpdate();
		Iterator itr = query.list().iterator();
		downloadList.add(headers);
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			ArrayList<String> dataObj = new ArrayList<String>();
			for (Object ob : obj) {
				String value = "";
				value = (ob == null) ? "" : ob.toString();
				dataObj.add(value.replaceAll("\\^", ","));
			}
			obj = null;
			downloadList.add(dataObj);
		}
		return downloadList;	
	} catch (Exception e) {
		logger.debug("Exception: ", e);
	}
		return downloadList;
	}
		
	
}
