package com.hul.proco.controller.promostatustracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.hul.proco.controller.listingPromo.PromoListingBean;
import com.hul.proco.controller.listingPromo.PromoMeasureReportBean;

@Repository
public class ProcoStatusTrackerDAOImpl implements ProcoStatusTrackerDAO {

	Logger logger = Logger.getLogger(ProcoStatusTrackerDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int actives, String promoId,String searchParameter) {
		List<PromoListingBean> promoList = new ArrayList<>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - row number changes
			//promoQuery = "SELECT * FROM ( SELECT A.PROMO_ID ,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE ,A.MOC ,A.CUSTOMER_CHAIN_L1 ,A.KITTING_VALUE ,E.STATUS ,VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY') ,VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY') ,A.CUSTOMER_CHAIN_L2, D.REMARK AS REM,A.REASON ,A.REMARK ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY') AS CHANGE_DATE,A.ORIGINAL_ID,A.CHANGES_MADE,D.USER_ID,A.UPDATE_STAMP,A.INVESTMENT_TYPE,A.SOL_CODE,A.SOL_CODE_DESC ,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROWNUMBER() OVER ( ORDER BY A.UPDATE_STAMP ASC ) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE A.START_DATE>=CURRENT_DATE ";
			//Garima - changes for VARCHAR_FORMAT
			promoQuery = "SELECT * FROM ( SELECT A.PROMO_ID ,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE ,A.MOC ,A.CUSTOMER_CHAIN_L1 ,A.KITTING_VALUE ,E.STATUS ,DATE_FORMAT(A.START_DATE,'%d/%m/%Y') ,DATE_FORMAT(A.END_DATE,'%d/%m/%Y') ,A.CUSTOMER_CHAIN_L2, D.REMARK AS REM,A.REASON ,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y') AS CHANGE_DATE,A.ORIGINAL_ID,A.CHANGES_MADE,D.USER_ID,A.UPDATE_STAMP,A.INVESTMENT_TYPE,A.SOL_CODE,A.SOL_CODE_DESC ,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROW_NUMBER() OVER ( ORDER BY A.UPDATE_STAMP ASC ) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE A.START_DATE>=CURRENT_DATE ";

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
			}
			if(searchParameter!=null && searchParameter.length()>0){
				promoQuery +=" AND UCASE(A.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
			}
			if (pageDisplayLength == 0) {
				promoQuery += " ) AS PROMO_TEMP ORDER BY PROMO_TEMP.UPDATE_STAMP ASC";
			} else {
				promoQuery += " ) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND "
						+ pageDisplayLength + "" + " ORDER BY PROMO_TEMP.UPDATE_STAMP ASC";
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoListingBean promoBean = new PromoListingBean();
				promoBean.setPromo_id(obj[0].toString());
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
				promoBean.setSolCodeStatus(obj[28] == null ? "" : obj[28].toString());
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
					//"SELECT DISTINCT SIGNED_OFF_SHARED_DATE, PROMOTION_ID, PROMOTION_NAME, SCOPE, PROCESS, IS_CLAIMED, PRODUCT, CLIAM_VALUE, PROMO_VOLUMN_IN_THOUSAND, TOTAL_INVESTMENT_IN_LACS, SUPPORING_REQUIRED, PROMOTION_START_DATE, PROMOTION_END_DATE, SIGNED_OPS_STATUS FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE ("+MocYear+") AND ("+Moc+")");
					"SELECT DISTINCT SIGNED_OFF_SHARED_DATE, PROMOTION_ID,PARENT_ACCOUNT_NAME, PROMOTION_NAME, SCOPE, PROCESS, IS_CLAIMED, PRODUCT,SUB_BRAND, CLIAM_VALUE, PROMO_VOLUMN_IN_THOUSAND, TOTAL_INVESTMENT_IN_LACS, SUPPORING_REQUIRED, PROMOTION_START_DATE, PROMOTION_END_DATE, SIGNED_OPS_STATUS FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE ("+MocYear+") AND ("+Moc+")");
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
			Query queryGetMoc = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT MOC, MOC_YEAR FROM TBL_PROCO_MEASURE_REPORT_MASTER");
			List<Object[]> list = queryGetMoc.list();
			List<ProcoMocModel> moc = new ArrayList<ProcoMocModel>();
			for (Object[] obj : list) {
				ProcoMocModel MoCMod = new ProcoMocModel();
				if(obj[0] != null && obj[1] != null) {
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
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active, String promoId) {
		//kiran - bigint to int changes
		//List<Integer> list = null;
		List<BigInteger> list = null;
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {

			String rowCount = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID WHERE A.START_DATE > CURRENT_DATE ";

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

					/*
					 * Query queryToGetState =
					 * sessionFactory.getCurrentSession().createNativeQuery(
					 * "SELECT DISTINCT STATE_CODE FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branch"
					 * ); queryToGetState.setString("branch", branch); List
					 * stateList = queryToGetState.list(); if (stateList !=
					 * null) { for (int i = 0; i < stateList.size(); i++) { if
					 * (i < stateList.size() - 1) { rowCount +=
					 * "OR A.GEOGRAPHY like '%" + stateList.get(i).toString() +
					 * "%' "; } if (i == stateList.size() - 1) { rowCount +=
					 * "OR A.GEOGRAPHY like '%" + stateList.get(i).toString() +
					 * "%') "; } } }
					 */
				} else if (geography.startsWith("CL")) {
					String cluster = geography.substring(0, geography.indexOf(':'));
					/*
					 * Query queryToGetState =
					 * sessionFactory.getCurrentSession().createNativeQuery(
					 * "SELECT DISTINCT STATE_CODE FROM TBL_PROCO_CUSTOMER_MASTER WHERE CLUSTER_CODE=:cluster"
					 * ); queryToGetState.setString("cluster", cluster); List
					 * stateList = queryToGetState.list();
					 */
					rowCount += "AND (A.GEOGRAPHY like '%" + cluster + "%') ";
					/*
					 * if (stateList != null) { for (int i = 0; i <
					 * stateList.size(); i++) { if (i < stateList.size() - 1) {
					 * rowCount += "OR A.GEOGRAPHY like '%" +
					 * stateList.get(i).toString() + "%' "; } if (i ==
					 * stateList.size() - 1) { rowCount +=
					 * "OR A.GEOGRAPHY like '%" + stateList.get(i).toString() +
					 * "%') "; } } }
					 */
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
			if (!promoId.equalsIgnoreCase("All")) {
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
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		//kiran - bigint to int changes
		//return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}
	
	public int getUserRoleId(String userId) {
		int UserRoleId = 0;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery("SELECT USER_ROLE_ID FROM TBL_VAT_USER_DETAILS WHERE USERID=:userid"); 
			query.setString("userid", userId);
			UserRoleId = (int) query.uniqueResult();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return UserRoleId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromotionStatusTracker(ArrayList<String> headerList, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String promoId) {
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String promoQuery = "";
		try {
			int userRole = getUserRoleId(userId);
			if(userRole == 2) {
				//Garima - changes for VARCHAR_FORMAT
				//promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY') as start_date1,VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,c.BASEPACK_DESC,replace(A.USER_ID,'','') as Created_By,replace(c.CATEGORY,'','') as SALES_CATEGORY,replace(c.BRAND,'','') as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY'),A.INVESTMENT_TYPE, A.SOL_CODE, A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
				promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,DATE_FORMAT(A.START_DATE,'%d/%m/%Y') as start_date1,DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,C.BASEPACK_DESC,replace(A.USER_ID,'','') as Created_By,replace(C.CATEGORY,'','') as SALES_CATEGORY,replace(C.BRAND,'','') as Sales_Brand,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,replace(A.quantity,'','') as Sales_value,'' as estimated_spend,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y'),A.INVESTMENT_TYPE, A.SOL_CODE, A.SOL_CODE_DESC,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
			} else {
				//Garima - changes for VARCHAR_FORMAT
				//promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY'),VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,VARCHAR_FORMAT(D.CHANGE_DATE,'DD/MM/YYYY'),A.INVESTMENT_TYPE, A.SOL_CODE, A.PROMOTION_MECHANICS, A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
				promoQuery = "SELECT A.PROMO_ID, A.ORIGINAL_ID,DATE_FORMAT(A.START_DATE,'%d/%m/%Y'),DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.MOC,A.CUSTOMER_CHAIN_L1 ,A.CUSTOMER_CHAIN_L2,A.P1_BASEPACK ,A.OFFER_DESC ,A.OFFER_TYPE ,A.OFFER_MODALITY ,A.GEOGRAPHY ,A.QUANTITY ,A.UOM ,A.OFFER_VALUE , A.KITTING_VALUE ,(CASE WHEN D.REMARK<>'' THEN CONCAT(CONCAT(CONCAT(E.STATUS,' ('),D.REMARK),')') ELSE E.STATUS END) ,A.REASON,A.REMARK ,DATE_FORMAT(D.CHANGE_DATE,'%d/%m/%Y'),A.INVESTMENT_TYPE, A.SOL_CODE, A.PROMOTION_MECHANICS, A.SOL_CODE_STATUS FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_TRACKER AS D ON A.PROMO_ID = D.PROMO_ID INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON E.STATUS_ID = D.STATUS_ID INNER JOIN TBL_PROCO_MODALITY_MASTER AS F ON A.OFFER_MODALITY=F.MODALITY WHERE (A.ACTIVE=1 OR A.ACTIVE=0) ";
			}
			
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
			}
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
				
				if( userRole == 2 && !dataObj.get(16).trim().equals("")) {
					try {
						/* get Basepack */
						String BasePack = dataObj.get(7).trim(); 
						/* get Quantity */
						if(isBigDecimal(dataObj.get(16))) {
							BigDecimal Quantity = new BigDecimal( dataObj.get(16) );
							Query MaxTurQuery = sessionFactory.getCurrentSession().createNativeQuery("SELECT GSV FROM TBL_PROCO_MAX_TUR WHERE BASEPACK='"+BasePack+"'");
							BigDecimal GSV =  (BigDecimal) MaxTurQuery.uniqueResult();
							if(GSV != null) {
								/* Set Sales Value */
								BigDecimal SalesVal = GSV.multiply(Quantity);
								dataObj.set(20, SalesVal.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
								BigDecimal EstimSpend = new BigDecimal(BigInteger.ZERO,  2);
								if(!dataObj.get(18).trim().equals("")) {
									String offerStr = dataObj.get(18).trim(); 
									if(offerStr.toUpperCase().indexOf("ABS") != -1) {
										String offerVal = offerStr.replace("ABS", "");
										if(isBigDecimal(offerVal)) {
											BigDecimal amount = new BigDecimal( offerVal );
											EstimSpend = amount.multiply(Quantity);
										}
									} else if(offerStr.toUpperCase().indexOf("%") != -1){
										String offerVal = offerStr.replace("%", "");
										if(isBigDecimal(offerVal)) {
											BigDecimal Hundred = new BigDecimal(100);
											BigDecimal amount =  new BigDecimal( offerVal );
											EstimSpend = amount.multiply( SalesVal );
											EstimSpend = EstimSpend.divide(Hundred);
										}
									}
								} else if(!dataObj.get(19).trim().equals("")){
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
									/* set Estimated Spend */
									dataObj.set(21, EstimSpend.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
								}
							}
						}
					} catch (Exception ex) {
						logger.debug("Exception :", ex);
					}
				}
				
				obj = null;
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception ex) {
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

	public String uploadPromoStatusTracker(PromoListingBean[] promoListingBeanArray) throws Exception{
		String response=null;
		try {
		Query query = sessionFactory.getCurrentSession().createNativeQuery("UPDATE TBL_PROCO_PROMOTION_MASTER SET INVESTMENT_TYPE=?0, SOL_CODE=?1,SOL_CODE_DESC=?2, PROMOTION_MECHANICS=?3, SOL_CODE_STATUS=?4 WHERE PROMO_ID=?5 AND ORIGINAL_ID=?6");  //Sarin - Added Parameters position

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
		ArrayList<String> responseList = new ArrayList<String>();
		try {

			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_MEASURE_REPORT_TEMP");
			Integer recCount = (Integer) queryToCheck.uniqueResult();

			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_PROCO_MEASURE_REPORT_TEMP");
				queryToDelete.executeUpdate();
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP(PROMOTION_ID,PROMOTION_NAME,CREATED_BY,PROMOTION_MECHANICS,PROMOTION_START_DATE,PROMOTION_END_DATE,CUSTOMER,PRODUCT,PROMOTION_STATUS,CATEGORY,INVESTMENT_TYPE,MOC,SUBMISSION_DATE,PROMOTION_TYPE,PROMOTION_VOLUME_DURING,PLANNED_INVESTMENT_AMOUNT,ROW_NO,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND) VALUES (?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19)");  //Sarin - Added Parameters position
			for (int i = 0; i < promoMeasureReportBeanArray.length; i++) {
				query.setString(0, promoMeasureReportBeanArray[i].getPromotionId());
				query.setString(1, promoMeasureReportBeanArray[i].getPromotionName());
				query.setString(2, promoMeasureReportBeanArray[i].getCreatedBy());
				query.setString(3, promoMeasureReportBeanArray[i].getPromotionMechanics());
				query.setString(4, promoMeasureReportBeanArray[i].getPromotionStartDate());
				query.setString(5, promoMeasureReportBeanArray[i].getPromotionEndDate());
				query.setString(6, promoMeasureReportBeanArray[i].getCustomer());
				//query.setString(7, promoMeasureReportBeanArray[i].getProduct());
				query.setString(7, promoMeasureReportBeanArray[i].getProduct().replace(",", ";"));
				query.setString(8, promoMeasureReportBeanArray[i].getPromotionStatus());
				query.setString(9, promoMeasureReportBeanArray[i].getCategory());
				query.setString(10, promoMeasureReportBeanArray[i].getInvestmentType());
				query.setString(11, promoMeasureReportBeanArray[i].getMoc());
				query.setString(12, promoMeasureReportBeanArray[i].getSubmissionDate());
				query.setString(13, promoMeasureReportBeanArray[i].getPromotionType());
				query.setString(14, promoMeasureReportBeanArray[i].getPromotionVolumeDuring());
				query.setString(15, promoMeasureReportBeanArray[i].getPlannedInvestmentAmount());
				query.setInteger(16, i);
				query.setString(17, "");
				query.setString(18, "");

				query.setString(19,promoMeasureReportBeanArray[i].getSubBrand());
				

				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateRecord(promoMeasureReportBeanArray[i], i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}

			if(!responseList.contains("ERROR_FILE")){

				updateProcessIsClaim();

				updateClaimAndVolume();

				updateScope();

				//updateVolumeInThousand();

				updateTotalInvestmentInLacs();

				updateSupportingRequired();

			}

			if (!responseList.contains("ERROR_FILE")) {
				String userId="";
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
		if(responseList.contains("ERROR_FILE")) {
			response = "ERROR_FILE";
		}
		return response;
	}
	
	public synchronized boolean saveTotMainTable(String userId) {
		try {
			insertIntoMaster(userId);
			return true;
		}catch (HibernateException e) {
			logger.error("Inside ProcoStatusTrackerDAOImpl: saveToMainTable() : HibernateException : " + e.getMessage());
			return false;

		}
	}
	
	@Transactional(rollbackOn = {Exception.class})
	public void insertIntoMaster(String userId) {
		try {
			
			//kiran - rownumber changes and TBL_PROCO_MEASURE_REPORT_temp(changed TBL_PROCO_MEASURE_REPORT_TEMP)

			/*String removeDublicateSql="delete  FROM  (SELECT ROWNUMBER() OVER (PARTITION BY PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,"
					+ "PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,"
					+ "MOC,MOC_YEAR) AS RN FROM TBL_PROCO_MEASURE_REPORT_temp) AS A WHERE RN > 1";*/
			
			String removeDublicateSql="delete  FROM  (SELECT ROW_NUMBER() OVER (PARTITION BY PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,"
					+ "PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,"
					+ "MOC,MOC_YEAR) AS RN FROM TBL_PROCO_MEASURE_REPORT_TEMP) AS A WHERE RN > 1";
			updateAccountName();
			
			String crateNewSql="INSERT INTO TBL_PROCO_MEASURE_REPORT_MASTER (PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS," +  
					"IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE," + 
					"SIGNED_OFF_SHARED_DATE,SIGNED_OPS_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND) SELECT DISTINCT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND," + 
					"TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND FROM TBL_PROCO_MEASURE_REPORT_TEMP " + 
					"WHERE  PROMOTION_STATUS = 'Approved'"; //OR  PROMOTION_STATUS = 'Amend Submitted' OR  PROMOTION_STATUS = 'Submitted'";
			//Garima - changed Merge query
			/* String updateSql="MERGE INTO TBL_PROCO_MEASURE_REPORT_MASTER AS A " + 
					"USING (SELECT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS," + 
					"SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE  " + 
					"PROMOTION_STATUS = 'Financial Closed' OR PROMOTION_STATUS = 'Amend Approved') B " + 
					"ON (A.PROMOTION_ID = B.PROMOTION_ID) " + 
					"WHEN MATCHED THEN " + 
					"UPDATE SET " + 
					"A.PROMOTION_ID = B.PROMOTION_ID , A.PROMOTION_NAME=B.PROMOTION_NAME, A.SCOPE=B.SCOPE,A.PROCESS=B.PROCESS,A.IS_CLAIMED=B.IS_CLAIMED," + 
					"A.PRODUCT=B.PRODUCT,A.CLIAM_VALUE=B.CLIAM_VALUE,A.PROMO_VOLUMN_IN_THOUSAND=B.PROMO_VOLUMN_IN_THOUSAND,A.TOTAL_INVESTMENT_IN_LACS=B.TOTAL_INVESTMENT_IN_LACS," + 
					"A.SUPPORING_REQUIRED=B.SUPPORING_REQUIRED,A.PROMOTION_START_DATE=B.PROMOTION_START_DATE,A.PROMOTION_END_DATE=B.PROMOTION_END_DATE,A.SIGNED_OFF_SHARED_DATE=B.SIGNED_OFF_SHARED_DATE," + 
					"A.SIGNED_OPS_STATUS=B.PROMOTION_STATUS,A.MOC=B.MOC,A.MOC_YEAR=B.MOC_YEAR"; */
			String updateSql ="UPDATE TBL_PROCO_MEASURE_REPORT_MASTER AS A INNER JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, SCOPE,PROCESS,IS_CLAIMED,PRODUCT,CLIAM_VALUE,PROMO_VOLUMN_IN_THOUSAND,TOTAL_INVESTMENT_IN_LACS,SUPPORING_REQUIRED,PROMOTION_START_DATE,PROMOTION_END_DATE,SIGNED_OFF_SHARED_DATE,PROMOTION_STATUS,MOC,MOC_YEAR,CHILD_ACCOUNT_NAME,PARENT_ACCOUNT_NAME,SUB_BRAND FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_STATUS = 'Financial Close' OR PROMOTION_STATUS = 'AmendApproved') B ON A.PROMOTION_ID = B.PROMOTION_ID SET A.PROMOTION_ID = B.PROMOTION_ID, A.PROMOTION_NAME = B.PROMOTION_NAME, A.SCOPE = B.SCOPE, A.PROCESS = B.PROCESS,A.IS_CLAIMED = B.IS_CLAIMED, A.PRODUCT = B.PRODUCT, A.CLIAM_VALUE = B.CLIAM_VALUE, A.PROMO_VOLUMN_IN_THOUSAND = B.PROMO_VOLUMN_IN_THOUSAND, A.TOTAL_INVESTMENT_IN_LACS = B.TOTAL_INVESTMENT_IN_LACS, A.SUPPORING_REQUIRED = B.SUPPORING_REQUIRED, A.PROMOTION_START_DATE = B.PROMOTION_START_DATE, A.PROMOTION_END_DATE = B.PROMOTION_END_DATE, A.SIGNED_OFF_SHARED_DATE = B.SIGNED_OFF_SHARED_DATE, A.SIGNED_OPS_STATUS = B.PROMOTION_STATUS, A.MOC = B.MOC,A.MOC_YEAR = B.MOC_YEAR,A.CHILD_ACCOUNT_NAME=B.CHILD_ACCOUNT_NAME,A.PARENT_ACCOUNT_NAME=B.PARENT_ACCOUNT_NAME ,A.SUB_BRAND=B.SUB_BRAND";
			
			Query queryRemoveDublicate = sessionFactory.getCurrentSession().createNativeQuery(removeDublicateSql);
			queryRemoveDublicate.executeUpdate();

			Query queryCreateNew = sessionFactory.getCurrentSession().createNativeQuery(crateNewSql);
			queryCreateNew.executeUpdate();

			Query queryUpdateExisting = sessionFactory.getCurrentSession().createNativeQuery(updateSql);
			queryUpdateExisting.executeUpdate();


		} catch (Exception e) {
			logger.error("Error in com.hul.proco.controller.promostatustracker.ProcoStatusTrackerDAOImpl.insertIntoTotMaster(String)", e);
		}

	}
	
	private int updateAccountName() {
		try {
			int executeUpdate=0;
			String qry1 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'MORE' WHERE (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ABRL SUPER%') OR(SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ADIT%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ABRL HYPER%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ADIH%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%MORE%')";
			String qry2 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Reliance' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE RETAIL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELI%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%SHAKARI BHANDAR%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE TRENDS%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE CNC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RECC%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%RELIANCE C&C%')";
			String qry3 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'GARL' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GARL%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%GODREJ%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%AADHAR RETAIL%')";
			String qry4 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'D MART' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%D MART%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DMART%') OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%ASML%')";
			String qry5 = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'DNCP' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DNCP%'";
			String qry6= "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PARENT_ACCOUNT_NAME = 'Dabur' WHERE SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%DABUR%' OR (SUBSTRING(UCASE(PROMOTION_NAME),1,35) LIKE '%HBSL%')";
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
			//Commented By Sarin - 20Nov2020
			//String qry = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROCESS='Non Kitting',IS_CLAIMED='Yes', MOC_YEAR = (SELECT  CAST(YEAR(CURRENT_DATE)AS VARCHAR(4)) FROM SYSIBM.SYSDUMMY1)";
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
			int executeUpdate=0;
			String getSolcodeSql ="SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			//String updateClaimAndVolumnSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =(SELECT DEC(SUM(PLANNED_INVESTMENT_AMOUNT),15,0)  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1),PROMO_VOLUMN_IN_THOUSAND='0' WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-VI-%'";
			//String updateClaimSqlForNonDIVI ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =:claimValue  WHERE PROMOTION_ID=:solcode  AND  REPLACE(PROMOTION_NAME,' ','') NOT LIKE 'DI-VI-%'";

			//String updateVolumnSqlForNonDIVI="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE 'DI-VI-%'";
			//String getPromotionNameSql ="SELECT DISTINCT PROMOTION_NAME FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode";
			
			String updateClaimSqlForDIVI = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =(SELECT DEC(SUM(PLANNED_INVESTMENT_AMOUNT),15,0)  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(INVESTMENT_TYPE,' ','') IN ('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String updateVolumnSqlForDIVI ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND='0'  WHERE PROMOTION_ID=:solcode  AND REPLACE(PROMOTION_NAME,' ','') LIKE '%-VI%' ";
			String updateClaimSqlForNonDIVI ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET CLIAM_VALUE =:claimValue  WHERE PROMOTION_ID=:solcode  AND REPLACE(INVESTMENT_TYPE,' ','') NOT IN('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-ExclusiveConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-ExclusiveConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String updateVolumnSqlForNonDIVI="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE '%-VI%'";
			String getPromotionNameSql ="SELECT DISTINCT PROMOTION_NAME FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode";
			
			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList =getSolcodeQuery.list();
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					/*Query updateClaimAndVolumnQueryForDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateClaimAndVolumnSqlForDIVI);
					updateClaimAndVolumnQueryForDIVI.setString("solcode1", solcode);
					updateClaimAndVolumnQueryForDIVI.setString("solcode2", solcode);
					executeUpdate = updateClaimAndVolumnQueryForDIVI.executeUpdate(); */
					Query updateClaimQueryForDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateClaimSqlForDIVI);
					updateClaimQueryForDIVI.setString("solcode1", solcode);
					updateClaimQueryForDIVI.setString("solcode2", solcode);
					executeUpdate = updateClaimQueryForDIVI.executeUpdate();
					
					if(executeUpdate == 0) {

						/* Query updateVolumnQueryForNonDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateVolumnSqlForNonDIVI);
						updateVolumnQueryForNonDIVI.setString("solcode1", solcode);
						updateVolumnQueryForNonDIVI.setString("solcode2", solcode);
						executeUpdate = updateVolumnQueryForNonDIVI.executeUpdate(); */


						Query getPromotionNameQuery = sessionFactory.getCurrentSession().createNativeQuery(getPromotionNameSql);
						getPromotionNameQuery.setString("solcode", solcode);
						List<String> promotionNameList =getPromotionNameQuery.list();

						if(promotionNameList.size()!=0) {
							String startWord="GET";
							String endword="OFF";
							String claimValue="";
							String promoName="";
							for(String name:promotionNameList) {
								/* promoName =name.toUpperCase();
								claimValue = promoName.substring(promoName.indexOf(startWord)+3, promoName.lastIndexOf(endword)+3);
								Query updateClaimQueryForNonDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateClaimSqlForNonDIVI);
								updateClaimQueryForNonDIVI.setString("solcode", solcode);
								updateClaimQueryForNonDIVI.setString("claimValue", claimValue);
								executeUpdate = updateClaimQueryForNonDIVI.executeUpdate(); */
								
								try {
									promoName =name.toUpperCase();
									if(promoName.contains(startWord) && promoName.contains(endword)) {
										claimValue = promoName.substring(promoName.indexOf(startWord)+3, promoName.lastIndexOf(endword)+3);
									}else {
										claimValue ="NA";
									}
								
									Query updateClaimQueryForNonDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateClaimSqlForNonDIVI);
									updateClaimQueryForNonDIVI.setString("solcode", solcode);
									updateClaimQueryForNonDIVI.setString("claimValue", claimValue);
									executeUpdate = updateClaimQueryForNonDIVI.executeUpdate();
								}catch(Exception e) {
									logger.debug("Exception: ", e);
									logger.error("Inside ProcoStatusTrackerDAOImpl: updateClaimAndVolume() : " + e.getMessage());
								}

							}
						}
					}
				}
			}
			
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					Query updateVolumnQueryForDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateVolumnSqlForDIVI);
					updateVolumnQueryForDIVI.setString("solcode", solcode);
					executeUpdate = updateVolumnQueryForDIVI.executeUpdate();
					
					
					if(executeUpdate == 0) {

						Query updateVolumnQueryForNonDIVI = sessionFactory.getCurrentSession().createNativeQuery(updateVolumnSqlForNonDIVI);
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
			int executeUpdate=0;

			String getSolcodeSql ="SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			String updateRegionForAlphabeticCustomerSql = "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SCOPE=:scope WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%' ";
			/*String getAlphabeticCustomerSql ="SELECT CUSTOMER  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%' ";
			String updateWestRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='West' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='1'"; 
			String updateEastRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='East' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='2'";
			String updateNorthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='North' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='3'";
			String updateSouthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='South' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(A.CUSTOMER,' ',''),5,1) ='4'";
			String updateAllIndiaRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='All India' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND REPLACE(A.PROMOTION_NAME,' ','') LIKE 'DI-VI%' AND SUBSTRING(REPLACE(A.INVESTMENT_TYPE,' ',''),1,3) ='CnC'";
			*/
			String getAlphabeticCustomerSql ="SELECT CUSTOMER  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode AND REPLACE(CUSTOMER,' ','') NOT LIKE '0000%'AND LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' ";
			String updateWestRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='West' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='1') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='1') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='1')"; 
			String updateEastRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='East' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='2') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='2') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='2')";
			String updateNorthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='North' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='3') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='3') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='3')";
			String updateSouthRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='South' WHERE(LEFT(CUSTOMER , 1) BETWEEN '0' and '9' AND CUSTOMER NOT LIKE '0000%' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),1,1) ='4') OR( REPLACE(CUSTOMER,' ','') LIKE '0000%' AND SUBSTRING(REPLACE(CUSTOMER,' ',''),5,1) ='4') OR (LEFT(CUSTOMER , 1) NOT BETWEEN '0' and '9' AND  SUBSTRING(REPLACE(CUSTOMER,' ',''),2,1) ='4')";
			String updateAllIndiaRegionForNumericCustomerSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP A SET SCOPE='All India' WHERE REPLACE(A.CUSTOMER,' ','') LIKE '0000%' AND REPLACE(A.PROMOTION_NAME,' ','') LIKE '%-VI%' AND SUBSTRING(REPLACE(A.INVESTMENT_TYPE,' ',''),1,3) ='CnC'";
			String updateScopesql="SELECT DISTINCT SCOPE FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode"; 
			String updateConsolidatedScopeSql="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SCOPE=:scope WHERE PROMOTION_ID=:solcode";
			

			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList =getSolcodeQuery.list();
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					Query getAlphabeticCustomerQuery = sessionFactory.getCurrentSession().createNativeQuery(getAlphabeticCustomerSql);
					getAlphabeticCustomerQuery.setString("solcode", solcode);
					List<String> alphabeticCustomerList =getAlphabeticCustomerQuery.list();
					String scope =String.join(",", alphabeticCustomerList);
					executeUpdate=0;
					if(!alphabeticCustomerList.isEmpty()) {
						Query customerUpdateQuery = sessionFactory.getCurrentSession().createNativeQuery(updateRegionForAlphabeticCustomerSql);
						customerUpdateQuery.setString("scope", scope);
						customerUpdateQuery.setString("solcode", solcode);
						executeUpdate = customerUpdateQuery.executeUpdate();

					}

				}
				/*Query updateWestRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateWestRegionForNumericCustomerSql);
				executeUpdate = updateWestRegionQuery.executeUpdate();
				Query updateEastRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateEastRegionForNumericCustomerSql);
				executeUpdate = updateEastRegionQuery.executeUpdate();
				Query updateNorthRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateNorthRegionForNumericCustomerSql);
				executeUpdate = updateNorthRegionQuery.executeUpdate();
				Query updateSouthRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateSouthRegionForNumericCustomerSql);
				executeUpdate = updateSouthRegionQuery.executeUpdate();
				Query updateAllIndiaRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateAllIndiaRegionForNumericCustomerSql);
				executeUpdate = updateAllIndiaRegionQuery.executeUpdate();*/

			}
			
			Query updateWestRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateWestRegionForNumericCustomerSql);
			executeUpdate = updateWestRegionQuery.executeUpdate();
			Query updateEastRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateEastRegionForNumericCustomerSql);
			executeUpdate = updateEastRegionQuery.executeUpdate();
			Query updateNorthRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateNorthRegionForNumericCustomerSql);
			executeUpdate = updateNorthRegionQuery.executeUpdate();
			Query updateSouthRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateSouthRegionForNumericCustomerSql);
			executeUpdate = updateSouthRegionQuery.executeUpdate();
			Query updateAllIndiaRegionQuery = sessionFactory.getCurrentSession().createNativeQuery(updateAllIndiaRegionForNumericCustomerSql);
			executeUpdate = updateAllIndiaRegionQuery.executeUpdate();
			
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					Query getAllScopesQuery = sessionFactory.getCurrentSession().createNativeQuery(updateScopesql);
					getAllScopesQuery.setString("solcode", solcode);
					List<String> scopeList =getAllScopesQuery.list();
					String scope =String.join(",", scopeList);
					executeUpdate=0;
					if(!scopeList.isEmpty()) {
						Query updateConsolidatedScopeQuery = sessionFactory.getCurrentSession().createNativeQuery(updateConsolidatedScopeSql);
						if((scope.contains("West") && scope.contains("East") && scope.contains("North")&& scope.contains("South")) || scope.contains("All India")) {
							scope="All India";
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
	@SuppressWarnings("unchecked")
	private int updateVolumeInThousand() {

		try {
			int executeUpdate=0;
			String getSolcodeSql ="SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			String updateVolumeInThousandSql ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET PROMO_VOLUMN_IN_THOUSAND =(SELECT DEC(SUM(PROMOTION_VOLUME_DURING),15,2)  FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1) WHERE PROMOTION_ID=:solcode2 AND REPLACE(PROMOTION_NAME,' ','') NOT LIKE 'DI-VI-%'";
			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList =getSolcodeQuery.list();
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					Query updateVolumnInThousandQuery = sessionFactory.getCurrentSession().createNativeQuery(updateVolumeInThousandSql);
					updateVolumnInThousandQuery.setString("solcode1", solcode);
					updateVolumnInThousandQuery.setString("solcode2", solcode);
					executeUpdate = updateVolumnInThousandQuery.executeUpdate();

				}
			}
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			logger.error("Inside ProcoStatusTrackerDAOImpl: updateVolumeInThousand() : " + e.getMessage());
			return 0;
		}
	}
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> getProcoStatusMasterValues(){
		ArrayList<ArrayList<String>> ResList = new ArrayList<ArrayList<String>>();
		try {
			String getStatus ="SELECT * FROM TBL_PROCO_SOLCODE_STATUS_MASTER";
			Query Stquery = sessionFactory.getCurrentSession().createNativeQuery(getStatus);

			ArrayList<String> list = (ArrayList<String>)Stquery.list();

			ResList.add(list);

			String getMechMst = "SELECT * FROM TBL_PROCO_MECHANICS_MASTER";
			Query Mechquery = sessionFactory.getCurrentSession().createNativeQuery(getMechMst);

			ArrayList<String> Mechlist = (ArrayList<String>)Mechquery.list();
			ResList.add(Mechlist);
			
			String getInvType = "SELECT * FROM TBL_PROCO_INVESTMENT_TYPE_MASTER";
			Query InTyquery = sessionFactory.getCurrentSession().createNativeQuery(getInvType);

			ArrayList<String> Inlist = (ArrayList<String>)InTyquery.list();

			ResList.add(Inlist);

			String getCreatedByMasterSql = "SELECT * FROM TBL_PROCO_CREATED_BY_MASTER";
            Query getCreatedByMasterquery = sessionFactory.getCurrentSession().createNativeQuery(getCreatedByMasterSql);

            ArrayList<String> createdByList = (ArrayList<String>)getCreatedByMasterquery.list();
            ResList.add(createdByList);


		} catch(Exception ex) {
			logger.debug("Exception: ", ex);
		}
		return ResList;
	}
	
	
	@SuppressWarnings("unchecked")
	private int updateTotalInvestmentInLacs() {

		try {
			int executeUpdate=0;
			String getSolcodeSql ="SELECT DISTINCT PROMOTION_ID FROM TBL_PROCO_MEASURE_REPORT_TEMP";
			String updateTotalInvestmentInLacSql ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET TOTAL_INVESTMENT_IN_LACS=(SELECT CAST( ROUND((DEC(SUM(PLANNED_INVESTMENT_AMOUNT),15,2)/100000),2) AS DECIMAL(15,2)) FROM TBL_PROCO_MEASURE_REPORT_TEMP WHERE PROMOTION_ID=:solcode1)WHERE PROMOTION_ID=:solcode2";
			Query getSolcodeQuery = sessionFactory.getCurrentSession().createNativeQuery(getSolcodeSql);
			List<String> solcodeList =getSolcodeQuery.list();
			if(solcodeList.size()!=0) {
				for(String solcode:solcodeList) {
					Query updateTotalInvestmentInLacQuery = sessionFactory.getCurrentSession().createNativeQuery(updateTotalInvestmentInLacSql);
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
	private void updateSupportingRequired(){

		try {
			/*String sqlForDIVI= "UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Signed document from the customer for amt recd' WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-VI-%'";
			String sqlForDIPO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Offtake'  WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-PO-%'";
			String sqlForDIPOMETRO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Primary Sales'  WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'DI-PO-METRO%'";
			String sqlForKAPO="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Sales' WHERE  REPLACE(PROMOTION_NAME,' ','') LIKE 'KA-PO-%'";

			Query supporingQueryForDIVI = sessionFactory.getCurrentSession().createNativeQuery(sqlForDIVI);
			int executeUpdateForDIVI = supporingQueryForDIVI.executeUpdate();

			Query supporingQueryForDIPO = sessionFactory.getCurrentSession().createNativeQuery(sqlForDIPO);
			int executeUpdateForDIPO = supporingQueryForDIPO.executeUpdate();

			Query supporingQueryForDIPOMETRO = sessionFactory.getCurrentSession().createNativeQuery(sqlForDIPOMETRO);
			int executeUpdateForDIPOMETRO = supporingQueryForDIPOMETRO.executeUpdate();

			Query supporingQueryForKAPO = sessionFactory.getCurrentSession().createNativeQuery(sqlForKAPO);
			int executeUpdateForKAPO = supporingQueryForKAPO.executeUpdate();*/
			
			String sqlSignedDocumentFromTheCustomerForAmtRecd ="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Signed document from the customer for amt recd' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN('CnC-ExclusiveConsumerPromotionSamples','CnC-ExclusiveCustomDisplay','KA-Exclusive ConsumerPromotionSamples-Trade','KA-ExclusiveSalesPromotionIncentive-Trade','KA-SalesPromotionIncentivePayoutNonCRM','KAPEOthersVendorsales','KAPEOthersVendorServices','MT-Exclusive ConsumerPromotionSamples','MT-ExclusiveCustomDisplay')";
			String sqlSecondaryOfftake="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Offtake'  WHERE  REPLACE(INVESTMENT_TYPE ,' ','') IN('MT-ExclusiveCustomCPPriceOff','MT-ExclusiveCustomSecondaryTPR-GST%12','MT-ExclusiveCustomSecondaryTPR-GST%18','MT-ExclusiveCustomSecondaryTPR-GST%5','MT-PTPR-%off','MT-PTPR-Rs.off')";
			String sqlPrimarySales="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Primary Sales'  WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN('CnC-ExclusiveCustomSecondaryTPR-GST%12','CnC-ExclusiveCustomSecondaryTPR-GST%18','CnC-ExclusiveCustomSecondaryTPR-GST%5')";
			String sqlSecondarySales="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Secondary Sales' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN ('KA-ExclusiveCustomCPPriceOff','KA-ExclusiveCustomSecondaryTPR-GST%12','KA-ExclusiveCustomSecondaryTPR-GST%18','KA-ExclusiveCustomSecondaryTPR-GST%5')";
			String sqlNotApplicable="UPDATE TBL_PROCO_MEASURE_REPORT_TEMP SET SUPPORING_REQUIRED='Not Applicable' WHERE  REPLACE(INVESTMENT_TYPE,' ','') IN ('CnCKittingGiftoff','CnCKittingPriceOff','MT-MTKittingGiftoff','MT-MTKittingPriceoff')";
			
			Query QuerySignedDocumentFromTheCustomerForAmtRecd = sessionFactory.getCurrentSession().createNativeQuery(sqlSignedDocumentFromTheCustomerForAmtRecd);
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
	
	
	
	private synchronized String validateRecord(PromoMeasureReportBean bean,  int row) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			Query queryToCheckInvestmentType = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_INVESTMENT_TYPE_MASTER WHERE INVESTMENT_TYPE=:investmentType");
			queryToCheckInvestmentType.setString("investmentType", bean.getInvestmentType());
			//kiran - bigint to int changes
			//Integer investmentTypeCount = (Integer) queryToCheckInvestmentType.uniqueResult();
			Integer investmentTypeCount = ((BigInteger)queryToCheckInvestmentType.uniqueResult()).intValue();

			Query queryToCheckPromotionMechanics = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_MECHANICS_MASTER WHERE PROMO_MECHANICS=:promotionMechanics");
			queryToCheckPromotionMechanics.setString("promotionMechanics", bean.getPromotionMechanics());
			//kiran - bigint to int changes
			//Integer promotionMechanicsCount = (Integer) queryToCheckPromotionMechanics.uniqueResult();
			Integer promotionMechanicsCount = ((BigInteger)queryToCheckPromotionMechanics.uniqueResult()).intValue();

			Query queryToCheckPromotionStatus = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_SOLCODE_STATUS_MASTER WHERE SOLCODE_STATUS=:promotionStatus");
			queryToCheckPromotionStatus.setString("promotionStatus", bean.getPromotionStatus());
			//kiran - bigint to int changes
			//Integer promotionStatusCount = (Integer) queryToCheckPromotionStatus.uniqueResult();
			Integer promotionStatusCount = ((BigInteger)queryToCheckPromotionStatus.uniqueResult()).intValue();

			Query queryToCheckPromotionIdExisting = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_MEASURE_REPORT_MASTER WHERE PROMOTION_ID=:promotionId");
			queryToCheckPromotionIdExisting.setString("promotionId", bean.getPromotionId());
			//kiran - bigint to int changes
			//Integer existingPromotionIdCount = (Integer) queryToCheckPromotionIdExisting.uniqueResult();
			Integer existingPromotionIdCount = ((BigInteger)queryToCheckPromotionIdExisting.uniqueResult()).intValue();

			Query queryToCheckCreatedBy = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_CREATED_BY_MASTER WHERE CREATED_BY=:createdBy");
			queryToCheckCreatedBy.setString("createdBy", bean.getCreatedBy());
			Integer createdByCount = (Integer) queryToCheckCreatedBy.uniqueResult();
			
			if(investmentTypeCount!=null && investmentTypeCount == 0){
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid InvestmentType entered.^";
				updateErrorMessageInTemp(errorMsg,  row);
			}

			if (promotionMechanicsCount != null && promotionMechanicsCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Mechanics entered.^";
				updateErrorMessageInTemp(errorMsg,  row);
			}

			if (promotionStatusCount != null && promotionStatusCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promotion Status entered.^";
				updateErrorMessageInTemp(errorMsg,  row);
			} 
			
			if (createdByCount != null && createdByCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Created By entered.^";
				updateErrorMessageInTemp(errorMsg,  row);
			} 

			if(bean.getPromotionId().length() > 8 || bean.getPromotionId().length()< 8 || !isNumeric(bean.getPromotionId())) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Solcode(promotionId) shuld be 8 digit number.^";
				updateErrorMessageInTemp(errorMsg,  row);

			}

			if(!bean.getPromotionStatus().equals("Financial Close") && !bean.getPromotionStatus().equals("AmendApproved")) {
				if(existingPromotionIdCount!=null && existingPromotionIdCount > 0){
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Solcode(promotionId) already exist, So we can't create again as per status  ^";
					updateErrorMessageInTemp(errorMsg,  row);
				}	
			}

			if(bean.getPromotionStatus().equals("Financial Close") || bean.getPromotionStatus().equals("AmendApproved")) {
				if(existingPromotionIdCount!=null && existingPromotionIdCount == 0){
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Solcode(promotionId) doen't exist, So we can't update/drop as per as per status  ^";
					updateErrorMessageInTemp(errorMsg,  row);
				}	
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}
	
	public static boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	
	
	private synchronized int updateErrorMessageInTemp(String errorMsg,  int row) {
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
	public  List<PromoListingBean> readProcoStatusTracker(String excelFilePath)
			throws IOException, ParseException {

		List<PromoListingBean> list = new ArrayList<PromoListingBean>();
		FileInputStream inputStream = new FileInputStream(new File(	excelFilePath));
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

						String valueOfCell="";
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
	
	public static Workbook getWorkbook(FileInputStream inputStream,
			String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException(
					"The specified file is not Excel file");
		}
		return workbook;
	}
	
	@Override
	public List<PromoMeasureReportBean> readPromoMeasureReport(String excelFilePath)
			throws IOException, ParseException {

		List<PromoMeasureReportBean> list = new ArrayList<PromoMeasureReportBean>();
		FileInputStream inputStream = new FileInputStream(new File(	excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		//int noOfColumns = firstSheet.getRow(0).getLastCellNum();
		Iterator<Row> rows = firstSheet.rowIterator();
		//if (noOfColumns == 62) {
			while (rows.hasNext()) {

				Row nextRow = rows.next();

				PromoMeasureReportBean promoMeasureReportBean = new PromoMeasureReportBean();
				if (nextRow.getRowNum() != 0) {
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell nextCell = cellIterator.next();
						int columnIndex = nextCell.getColumnIndex();

						String valueOfCell="";
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
						/*case 23: 
							promoMeasureReportBean.setPromotionStatus(valueOfCell);
							break;*/
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
		//}	
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
			//query.setParameter("userId", userId);
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
