package com.hul.proco.controller.listingPromo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromoDAOImpl;
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.excelreader.exom.annotation.Column;

@Repository
public class PromoListingDAOImpl implements PromoListingDAO {

	private Logger logger = Logger.getLogger(PromoListingDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CreatePromoDAOImpl createPromoDAO;

	private NativeQuery qry;
	
	//Added by Kajal G for Upload KAM Volume SPRINT-10
	private static String SQL_QUERY_INSERT_INTO_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_MASTER = "INSERT INTO TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_MASTER(PROMO_ID,MOC,PRIMARY_CHANNEL,BASEPACK_CODE,OFFER_DESC,PRICE_OFF,CLUSTER,DP_QUANTITY,PPM_ACCOUNT,KAM_QUANTITY,USER_ID,UPDATE_STAMP) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)";
	
	//Added by Kajal G for Upload KAM Volume SPRINT-10
	private static String SQL_QUERY_INSERT_INTO_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP(PROMO_ID,MOC,BASEPACK_CODE,OFFER_DESC,PRICE_OFF,CLUSTER,DP_QUANTITY,PRIMARY_CHANNEL,USER_ID,ERROR_MSG,LOAD_DATE,COLUMN_A,COLUMN_B,COLUMN_C,COLUMN_D,COLUMN_E,COLUMN_F,COLUMN_G,COLUMN_H,COLUMN_I,COLUMN_J,COLUMN_K,COLUMN_L,COLUMN_M,COLUMN_N,COLUMN_O,COLUMN_P,COLUMN_Q,COLUMN_R,COLUMN_S,COLUMN_T,COLUMN_U,COLUMN_V,COLUMN_Z) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33)";

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP(START_DATE,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,USER_ID,ROW_NO,PROMO_ID,END_DATE,REASON,REMARK,CHANGES_MADE) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43)";  //Sarin - Added Parameters position

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoListingBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String roleId , String searchParameter) {
		List<PromoListingBean> promoList = new ArrayList<>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - changes rownumber
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY'), VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.ORIGINAL_ID,A.REASON,A.REMARK,A.UPDATE_STAMP, "
					+ " A.INVESTMENT_TYPE,A.SOL_CODE,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID ";*/

			//Garima - changes for VARCHAR_FORMAT
			promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, DATE_FORMAT(A.START_DATE,'%d/%m/%Y'), DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.ORIGINAL_ID,A.REASON,A.REMARK,A.UPDATE_STAMP, "
					+ " A.INVESTMENT_TYPE,A.SOL_CODE,A.PROMOTION_MECHANICS,A.SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT, C.CATEGORY FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID ";
			
			if (roleId.equalsIgnoreCase("KAM")) {
				promoQuery += " INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 WHERE F.USER_ID='"
						+ userId + "' ";
			}

			if (roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")) {
				promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON C.CATEGORY=D.CATEGORY AND C.PRICE_LIST=D.PRICE_LIST WHERE D.USER_ID='"
						+ userId + "' ";
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

			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
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
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE AND A.ACTIVE=1 ";
			promoQuery += " AND A.ACTIVE=1 AND A.STATUS NOT IN('6')";
			if(searchParameter!=null && searchParameter.length()>0){
				promoQuery +=" AND UCASE(A.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
			}
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP ORDER BY PROMO_TEMP.UPDATE_STAMP DESC";
			} else {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength + " ORDER BY PROMO_TEMP.UPDATE_STAMP DESC ";
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
				promoBean.setKitting_value((obj[11] == null || obj[11].toString().equals("")) ? "" : obj[11].toString());
				promoBean.setStatus(obj[12] == null ? "" : obj[12].toString());
				promoBean.setStartDate(obj[13] == null ? "" : obj[13].toString());
				promoBean.setEndDate(obj[14] == null ? "" : obj[14].toString());
				promoBean.setOriginalId(obj[15] == null ? "" : obj[15].toString());
				promoBean.setReason(obj[16] == null ? "" : obj[16].toString());
				promoBean.setRemark(obj[17] == null ? "" : obj[17].toString());
				promoBean.setInvestmentType(obj[19] == null ? "" : obj[19].toString());
				promoBean.setSolCode(obj[20] == null ? "" : obj[20].toString());
				promoBean.setPromotionMechanics(obj[21] == null ? "" : obj[21].toString());
				promoBean.setSolCodeStatus(obj[22] == null ? "" : obj[22].toString());
				promoBean.setCategory(obj[24] == null ? "" : obj[24].toString());
				
				promoList.add(promoBean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoListRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String geography, String offerType, String modality, String year, String moc,
			String userId, int active, String roleId) {
		//kiran - bigint to int changes
		//List<Integer> list = null;
		List<BigInteger> list = null;
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {
			String promoQuery = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ "INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID ";
			if (roleId.equalsIgnoreCase("KAM")) {
				promoQuery += " INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 WHERE F.USER_ID='"
						+ userId + "' ";
			}

			if (roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")) {
				promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON C.CATEGORY=D.CATEGORY AND C.PRICE_LIST=D.PRICE_LIST WHERE D.USER_ID='"
						+ userId + "' ";
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

			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
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
		//	promoQuery += " AND A.START_DATE>=CURRENT_DATE AND A.ACTIVE=1 ";
			promoQuery += " AND A.ACTIVE=1 AND A.STATUS NOT IN('6')";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		//kiran - big int to int changes
		//return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreatePromotionBean> getPromotions(String promoId) {
		List<CreatePromotionBean> promoList = new ArrayList<>();
		try {
			//Garima - changes for VARCHAR_FORMAT
			//String getPromo = "SELECT PROMO_ID,YEAR,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY'),VARCHAR_FORMAT(END_DATE,'DD/MM/YYYY') FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID =:promoId AND ACTIVE=1";
			String getPromo = "SELECT PROMO_ID,YEAR,MOC,CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,OFFER_DESC,P1_BASEPACK,P1_BASEPACK_RATIO,P2_BASEPACK,P2_BASEPACK_RATIO,P3_BASEPACK,P3_BASEPACK_RATIO,P4_BASEPACK,P4_BASEPACK_RATIO,P5_BASEPACK,P5_BASEPACK_RATIO,P6_BASEPACK,P6_BASEPACK_RATIO,C1_CHILD_PACK,C1_CHILD_PACK_RATIO,C2_CHILD_PACK,C2_CHILD_PACK_RATIO,C3_CHILD_PACK,C3_CHILD_PACK_RATIO,C4_CHILD_PACK,C4_CHILD_PACK_RATIO,C5_CHILD_PACK,C5_CHILD_PACK_RATIO,C6_CHILD_PACK,C6_CHILD_PACK_RATIO,THIRD_PARTY_DESC,THIRD_PARTY_PACK_RATIO,OFFER_TYPE,OFFER_MODALITY,OFFER_VALUE,KITTING_VALUE,GEOGRAPHY,UOM,DATE_FORMAT(START_DATE,'%d/%m/%Y'),DATE_FORMAT(END_DATE,'%d/%m/%Y') FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID =:promoId AND ACTIVE=1";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(getPromo);
			query.setParameter("promoId", promoId);
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				CreatePromotionBean bean = new CreatePromotionBean();
				bean.setUniqueId(obj[0].toString());
				bean.setYear(obj[1].toString());
				bean.setMoc(obj[2].toString());
				bean.setCustomerChainL1(obj[3].toString());
				bean.setCustomerChainL2(obj[4].toString());
				bean.setPromoDesc(obj[5].toString());

				bean.setBasepack1(obj[6].toString());
				if (!obj[6].toString().isEmpty()) {
					Object[] base1 = getBasepackDetails(obj[6].toString());
					bean.setCategory1(base1[0].toString());
					bean.setBrand1(base1[1].toString());
					bean.setBasepackDesc1(base1[2].toString());
				}

				bean.setRatio1(obj[7].toString());
				bean.setBasepack2(obj[8].toString());
				if (!obj[8].toString().isEmpty()) {
					Object[] base2 = getBasepackDetails(obj[8].toString());
					bean.setCategory2(base2[0].toString());
					bean.setBrand2(base2[1].toString());
					bean.setBasepackDesc2(base2[2].toString());
				}

				bean.setRatio2(obj[9].toString());
				bean.setBasepack3(obj[10].toString());
				if (!obj[10].toString().isEmpty()) {
					Object[] base3 = getBasepackDetails(obj[10].toString());
					bean.setCategory3(base3[0].toString());
					bean.setBrand3(base3[1].toString());
					bean.setBasepackDesc3(base3[2].toString());
				}

				bean.setRatio3(obj[11].toString());
				bean.setBasepack4(obj[12].toString());
				if (!obj[12].toString().isEmpty()) {
					Object[] base4 = getBasepackDetails(obj[12].toString());
					bean.setCategory4(base4[0].toString());
					bean.setBrand4(base4[1].toString());
					bean.setBasepackDesc4(base4[2].toString());
				}

				bean.setRatio4(obj[13].toString());
				bean.setBasepack5(obj[14].toString());
				if (!obj[14].toString().isEmpty()) {
					Object[] base5 = getBasepackDetails(obj[14].toString());
					bean.setCategory5(base5[0].toString());
					bean.setBrand5(base5[1].toString());
					bean.setBasepackDesc5(base5[2].toString());
				}

				bean.setRatio5(obj[15].toString());
				bean.setBasepack6(obj[16].toString());
				if (!obj[16].toString().isEmpty()) {
					Object[] base6 = getBasepackDetails(obj[16].toString());
					bean.setCategory6(base6[0].toString());
					bean.setBrand6(base6[1].toString());
					bean.setBasepackDesc6(base6[2].toString());
				}

				bean.setRatio6(obj[17].toString());
				bean.setChildBasepack1(obj[18].toString());
				if (!obj[18].toString().isEmpty()) {
					Object[] childBase1 = getBasepackDetails(obj[18].toString());
					bean.setChildCategory1(childBase1[0].toString());
					bean.setChildBrand1(childBase1[1].toString());
					bean.setChildBasepackDesc1(childBase1[2].toString());
				}

				bean.setChildRatio1(obj[19].toString());
				bean.setChildBasepack2(obj[20].toString());
				if (!obj[20].toString().isEmpty()) {
					Object[] childBase2 = getBasepackDetails(obj[20].toString());
					bean.setChildCategory2(childBase2[0].toString());
					bean.setChildBrand2(childBase2[1].toString());
					bean.setChildBasepackDesc2(childBase2[2].toString());
				}

				bean.setChildRatio2(obj[21].toString());
				bean.setChildBasepack3(obj[22].toString());
				if (!obj[22].toString().isEmpty()) {
					Object[] childBase3 = getBasepackDetails(obj[22].toString());
					bean.setChildCategory3(childBase3[0].toString());
					bean.setChildBrand3(childBase3[1].toString());
					bean.setChildBasepackDesc3(childBase3[2].toString());
				}

				bean.setChildRatio3(obj[23].toString());
				bean.setChildBasepack4(obj[24].toString());
				if (!obj[24].toString().isEmpty()) {
					Object[] childBase4 = getBasepackDetails(obj[24].toString());
					bean.setChildCategory4(childBase4[0].toString());
					bean.setChildBrand4(childBase4[1].toString());
					bean.setChildBasepackDesc4(childBase4[2].toString());
				}

				bean.setChildRatio4(obj[25].toString());
				bean.setChildBasepack5(obj[26].toString());
				if (!obj[26].toString().isEmpty()) {
					Object[] childBase5 = getBasepackDetails(obj[26].toString());
					bean.setChildCategory5(childBase5[0].toString());
					bean.setChildBrand5(childBase5[1].toString());
					bean.setChildBasepackDesc5(childBase5[2].toString());
				}

				bean.setChildRatio5(obj[27].toString());
				bean.setChildBasepack6(obj[28].toString());
				if (!obj[28].toString().isEmpty()) {
					Object[] childBase6 = getBasepackDetails(obj[28].toString());
					bean.setChildCategory6(childBase6[0].toString());
					bean.setChildBrand6(childBase6[1].toString());
					bean.setChildBasepackDesc6(childBase6[2].toString());
				}
				bean.setChildRatio6(obj[29].toString());
				bean.setThirdPartyDesc(obj[30].toString());
				bean.setThirdPartyRatio(obj[31].toString());
				bean.setOfferType(obj[32].toString());
				bean.setModality(obj[33].toString());
				//bean.setOfferValue(obj[34].toString());
				String offer = obj[34].toString();
			
				if(offer.toUpperCase().endsWith("ABS")) {					
					bean.setOfferValue(offer.substring(0,offer.indexOf("A")));
					bean.setOfferValDrop("ABS");
				}
				
				if(offer.toUpperCase().endsWith("%")) {					
					bean.setOfferValue(offer.substring(0,offer.indexOf("%")));
					bean.setOfferValDrop("%");
				}
				bean.setKittingValue(obj[35].toString());
				bean.setGeography(obj[36].toString());
				bean.setUom(obj[37].toString());
				bean.setStartDate(obj[38].toString());
				bean.setEndDate(obj[39].toString());
				promoList.add(bean);
			}
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return promoList;
	}

	@SuppressWarnings("unchecked")
	public Object[] getBasepackDetails(String basepack) {
		Object[] obj = null;
		try {
			Query queryToGetBasepackDeatails = sessionFactory.getCurrentSession().createNativeQuery(
					//"SELECT BASEPACK_DESC,CATEGORY,BRAND FROM TBL_PROCO_PRODUCT_MASTER WHERE BASEPACK=:basepack AND ACTIVE=1");
					"SELECT CATEGORY,BRAND,BASEPACK_DESC FROM TBL_PROCO_PRODUCT_MASTER WHERE BASEPACK=:basepack AND ACTIVE=1");
			queryToGetBasepackDeatails.setString("basepack", basepack);
			List<Object[]> objList = queryToGetBasepackDeatails.list();
			obj = objList.get(0);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return obj;
	}

	@Override
	public void deletePromotion(String promoId, String userId, String remark) {
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_PROCO_PROMOTION_MASTER SET STATUS=6, DELETE_REMARKS=:remark WHERE PROMO_ID =:promoId AND ACTIVE = 1");

			if(promoId.contains(",")) {
				String[] promoIdList = promoId.split(",");
				for(String pId : promoIdList) {
					query.setParameter("promoId", pId);
					query.setString("remark", remark);
					query.executeUpdate();	
					
				}
				
			}else {
			query.setParameter("promoId", promoId);
			query.setString("remark", remark);
			query.executeUpdate();
			}
		//	createPromoDAO.saveStatusInStatusTracker(promoId, 6, remark, userId);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}

	}

	@Override
	public String updatePromotion(CreatePromotionBean[] bean, String userId, boolean isFromUi) throws Exception {
		String response = null;
		try {
			response = updatePromotionInMainTable(bean, userId, isFromUi);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			response = "ERROR";
			throw new Exception();
		}
		return response;
	}

	private synchronized String updatePromotionInMainTable(CreatePromotionBean[] bean, String userId, boolean isFromUi)
			throws Exception {
		logger.info("PromoListingDAOImpl.updatePromotionInMainTable(): " + bean.length);
		String createPromotion = "";
		List<String> promoIdList = new ArrayList<String>();
		try {
			createPromotion = createPromoDAO.createPromotion(bean, userId, "EDITED BY TME", false, isFromUi);
			if (createPromotion.equals("SUCCESS_FILE")) {
				for (int i = 0; i < bean.length; i++) {
					CreatePromotionBean createPromotionBean = bean[i];
					logger.info(createPromotionBean.toString());
					promoIdList.add(createPromotionBean.getUniqueId());
				}

				if (promoIdList.size() > 0) {
					Query queryToDeleteFromDisaggregationTable = sessionFactory.getCurrentSession().createNativeQuery(
							"UPDATE TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID IN(:promoId)");
					queryToDeleteFromDisaggregationTable.setParameterList("promoId", promoIdList);
					queryToDeleteFromDisaggregationTable.executeUpdate();

					Query queryToDeleteFromDisaggregationL2 = sessionFactory.getCurrentSession().createNativeQuery(
							"UPDATE TBL_PROCO_PROMO_DISAGG_L2_LEVEL SET ACTIVE=0 WHERE PROMO_ID IN(:promoId)");
					queryToDeleteFromDisaggregationL2.setParameterList("promoId", promoIdList);
					queryToDeleteFromDisaggregationL2.executeUpdate();

					Query queryToDeleteFromDisaggregationL2Depot = sessionFactory.getCurrentSession().createNativeQuery(
							"UPDATE TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL SET ACTIVE=0 WHERE PROMO_ID IN(:promoId)");
					queryToDeleteFromDisaggregationL2Depot.setParameterList("promoId", promoIdList);
					queryToDeleteFromDisaggregationL2Depot.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			createPromotion = "ERROR";
			throw new Exception();
		}
		return createPromotion;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromotionDump(ArrayList<String> headerList, String cagetory, String brand,
			String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//Garima - changes for VARCHAR_FORMAT
			//promoQuery = "SELECT PROMO_ID, VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY'),VARCHAR_FORMAT(END_DATE,'DD/MM/YYYY'), MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, C.MODALITY_NO, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, REASON, REMARK  from TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY ";
			promoQuery = "SELECT PROMO_ID, DATE_FORMAT(START_DATE,'%d/%m/%Y'),DATE_FORMAT(END_DATE,'%d/%m/%Y'), MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, C.MODALITY_NO, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, REASON, REMARK  from TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY ";
			promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON B.CATEGORY=D.CATEGORY AND B.PRICE_LIST=D.PRICE_LIST WHERE D.USER_ID='"
					+ userId + "' AND A.ACTIVE=1 ";

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
					promoQuery += "AND (A.GEOGRAPHY like '%" + geography + "%' ";
					if (clusterList != null) {
						for (int i = 0; i < clusterList.size(); i++) {
							promoQuery += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%' ";
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
					promoQuery += "AND (A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') ";
				} else if (moc.equals("Q2")) {
					promoQuery += "AND (A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') ";
				} else if (moc.equals("Q3")) {
					promoQuery += "AND (A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') ";
				} else if (moc.equals("Q4")) {
					promoQuery += "AND (A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') ";
				} else {
					promoQuery += "AND A.MOC = '" + moc + "' ";
				}
			}
			promoQuery += " AND A.START_DATE>=CURRENT_DATE AND A.STATUS NOT IN('6') ";
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
			logger.debug("Exception :", ex);
			return null;
		}
	}

	@Override
	public synchronized String promoEditUpload(CreatePromotionBean[] bean, String userId, boolean isFromUi)
			throws Exception {
		logger.info("PromoListingDAOImpl.promoEditUpload(): " + bean.length);
		String response = null;
		ArrayList<String> res = new ArrayList<>();
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_PROMOTION_MASTER_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			//kiran - big int to int changes
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
				query.setString(39, bean[i].getUniqueId());
				query.setString(40, bean[i].getEndDate());
				query.setString(41, bean[i].getReason());
				query.setString(42, bean[i].getRemark());
				query.setString(43, bean[i].getChangesMade());
				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateRecord(bean[i], userId, i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}
			if (!responseList.contains("ERROR_FILE")) {
				String updatePromotionInMainTable = updatePromotionInMainTable(bean, userId, isFromUi);
				if (!updatePromotionInMainTable.equalsIgnoreCase("SUCCESS_FILE")) {
					res.add(updatePromotionInMainTable);
				}
			} else {
				response = "ERROR_FILE";
			}
			if (res.contains("ERROR_FILE")) {
				response = "ERROR_FILE";
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			throw new Exception();
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getPromoEditErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT PROMO_ID, START_DATE,END_DATE, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, OFFER_MODALITY, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, REASON, REMARK, CHANGES_MADE, ERROR_MSG from TBL_PROCO_PROMOTION_MASTER_TEMP WHERE USER_ID=:userId";
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

	private synchronized String validateRecord(CreatePromotionBean bean, String userId, int row) throws Exception {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			Query queryToPromoId = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId and ACTIVE=1");
			queryToPromoId.setString("promoId", bean.getUniqueId());
			//kiran - bigint to int changes
			//Integer promoIdCount = (Integer) queryToPromoId.uniqueResult();
			Integer promoIdCount = ((BigInteger)queryToPromoId.uniqueResult()).intValue();
			
			Query queryToPromoIdInTemp = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(*) FROM TBL_PROCO_PROMOTION_MASTER_TEMP WHERE PROMO_ID=:promoId");
			queryToPromoIdInTemp.setString("promoId", bean.getUniqueId());
			//kiran - bigint to int changes
			//Integer promoIdCountInTemp = (Integer) queryToPromoIdInTemp.uniqueResult();
			Integer promoIdCountInTemp = ((BigInteger)queryToPromoIdInTemp.uniqueResult()).intValue();
			Query queryToCheckReason = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_EDIT_REASON WHERE REASON=:reason");
			queryToCheckReason.setString("reason", bean.getReason().toUpperCase());
			//kiran - bigint to int changes
			//Integer reasonCount = (Integer) queryToCheckReason.uniqueResult();
			Integer reasonCount = ((BigInteger)queryToCheckReason.uniqueResult()).intValue();
			//Garima - CHANGE to CHANG
			Query queryToCheckChange = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_CHANGES_MADE WHERE CHANG=:change");

			if(promoIdCountInTemp!=null && promoIdCountInTemp>1){
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Duplicate Promo Id entered.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}
			
			if (promoIdCount != null && promoIdCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Promo Id.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

			if (reasonCount != null && reasonCount == 0) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Invalid Reason for Edit.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			} else if (reasonCount != null && reasonCount > 0) {
				if (bean.getReason().equalsIgnoreCase("OTHERS") && bean.getRemark().equals("")) {
					res = "ERROR_FILE";
					errorMsg = errorMsg + "Remark cannot be blank for selected Reason.^";
					updateErrorMessageInTemp(errorMsg, userId, row);
				}
			}

			if (bean.getChangesMade().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Please enter atleast 1 changes made option.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			} else {
				String[] split = bean.getChangesMade().split(",");
				for (int i = 0; i < split.length; i++) {
					queryToCheckChange.setString("change", split[i].toUpperCase().trim());
					//kiran - bigint to int changes
					//Integer changeCount = (Integer) queryToCheckChange.uniqueResult();
					Integer changeCount = ((BigInteger)queryToCheckChange.uniqueResult()).intValue();
					if (changeCount != null && changeCount.intValue() == 0) {
						res = "ERROR_FILE";
						errorMsg = errorMsg + "Invalid changes made option " + split[i] + " .^";
						updateErrorMessageInTemp(errorMsg, userId, row);
					}
				}
			}

			if (!bean.getRemark().equals("") && bean.getRemark().length() > 40) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Remark cannot be more than 40 characters.^";
				updateErrorMessageInTemp(errorMsg, userId, row);
			}

		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}

	private synchronized int updateErrorMessageInTemp(String errorMsg, String userId, int row) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getReasonListForEdit() {
		List<String> reasonList = new ArrayList<>();
		try {
			String qryTogetAllBranches = "select REASON FROM TBL_PROCO_EDIT_REASON";
			Query queryToGetAllBranches = sessionFactory.getCurrentSession().createNativeQuery(qryTogetAllBranches);
			reasonList = queryToGetAllBranches.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return reasonList;
		}
		return reasonList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getChangesMadeListForEdit() {
		List<String> changesMadeList = new ArrayList<>();
		try {
			//Garima - CHANGE to CHANG
			String qryTogetAllChangesMade = "select CHANG FROM TBL_PROCO_CHANGES_MADE";
			Query queryToGetAllChangesMade = sessionFactory.getCurrentSession().createNativeQuery(qryTogetAllChangesMade);
			changesMadeList = queryToGetAllChangesMade.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return changesMadeList;
		}
		return changesMadeList;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, List<List<String>>> getMastersForTemplate() {
		Map<String, List<List<String>>> downloadDataMap = new HashMap<>();
		List<List<String>> clusterList = new ArrayList<>();
		List<List<String>> customerList = new ArrayList<>();
		List<List<String>> reasonList = new ArrayList<>();
		List<List<String>> changesList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> reasonHeaders = new ArrayList<String>();
		List<String> changesHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<List<String>> sampleList = new ArrayList<>();
		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("CUSTOMER CHAIN L1");
			customerHeaders.add("CUSTOMER CHAIN L2");
			reasonHeaders.add("REASON");
			changesHeaders.add("CHANGE");
			modalityHeaders.add("MODALITY_NO");
			modalityHeaders.add("MODALITY");

			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER order by CUSTOMER_CHAIN_L1";
			String reasonQry = "SELECT REASON FROM TBL_PROCO_EDIT_REASON";
			//Garima - CHANGE to CHANG
			String changeQry = "SELECT CHANG FROM TBL_PROCO_CHANGES_MADE";
			String modalityQry = "SELECT MODALITY_NO,MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE ACTIVE=1";

			Query query = sessionFactory.getCurrentSession().createNativeQuery(clusterQry);

			Iterator itr = query.list().iterator();
			clusterList.add(clusterHeaders);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				clusterList.add(dataObj);
			}
			ArrayList<String> allIndia = new ArrayList<String>();
			allIndia.add("ALL INDIA");
			clusterList.add(allIndia);
			downloadDataMap.put("CLUSTER", clusterList);

			query = sessionFactory.getCurrentSession().createNativeQuery(customerQry);

			itr = query.list().iterator();
			customerList.add(customerHeaders);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				customerList.add(dataObj);
			}
			downloadDataMap.put("CUSTOMER", customerList);

			query = sessionFactory.getCurrentSession().createNativeQuery(reasonQry);

			reasonList.add(reasonHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				reasonList.add(dataObj);
			}

			downloadDataMap.put("REASON", reasonList);

			query = sessionFactory.getCurrentSession().createNativeQuery(changeQry);

			changesList.add(changesHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				changesList.add(dataObj);
			}

			downloadDataMap.put("CHANGE", changesList);

			query = sessionFactory.getCurrentSession().createNativeQuery(modalityQry);

			modalityList.add(modalityHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				modalityList.add(dataObj);
			}

			downloadDataMap.put("MODALITY", modalityList);
			List<String> sampleData = getSampleData();
			sampleList.add(sampleData);
			downloadDataMap.put("SAMPLE", sampleList);
			
			return downloadDataMap;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;
	}
	private List<String> getSampleData(){
		String[] sampleArray={"052020,062020","ADIH,ADIT","ADIH,ADIT","SAMPLE PROMO","10011","1","","","","","","","","","","","","","","","","","","","","","","","","","GTCP","7","10ABS OR 10%","10ABS OR 10%","CL01,CL02,CL03","Singles","",""};
		List<String> asList = Arrays.asList(sampleArray);
		return  asList;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoListingBean> getDeletePromoTableList(int pageDisplayStart, int pageDisplayLength, String moc, String userId, String roleId, String searchParameter) {
		List<PromoListingBean> promoList = new ArrayList<>();
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - rownumber changes
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY'), VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.ORIGINAL_ID,A.REASON,A.DELETE_REMARKS,A.UPDATE_STAMP, "
					+ "ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID ";*/
			//Garima - changes for VARCHAR_FORMAT
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, DATE_FORMAT(A.START_DATE,'%d/%m/%Y'), DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.ORIGINAL_ID,A.REASON,A.DELETE_REMARKS,A.UPDATE_STAMP, "
					+ "ROW_NUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID "; */
			//Added by kavitha D -SPRINT 9
			promoQuery="  SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.PROMO_ID AS ORIGINAL_ID,PM.START_DATE,PM.END_DATE,"
					+ "  PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,"
					+ "  PM.OFFER_TYPE,PM.OFFER_MODALITY, PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,"
					+ "  PSM.STATUS,PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE,PR.PROMOTION_MECHANICS,"
					+ "  PR.PROMOTION_STATUS AS SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ "  FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "  INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "  INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS "
					+ "  LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					+ "  FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR  ON PR.PROMO_ID = PM.PROMO_ID "
					+ "  LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					+ "  LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE ";
			//if (roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE")) //Commented code and added moc condition for ncmm,sc login-SPRINT 10
			if (roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE") || roleId.equalsIgnoreCase("NCMM") || roleId.equalsIgnoreCase("SC")) {
				//promoQuery += " INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 WHERE F.USER_ID='"
				//		+ userId + "' ";
				promoQuery+=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.MOC='"+moc+"'";
			}

			/*if (roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")) {
				promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON C.CATEGORY=D.CATEGORY AND C.PRICE_LIST=D.PRICE_LIST WHERE D.USER_ID='"
						+ userId + "' ";
			} */
			
			if (roleId.equalsIgnoreCase("TME")) {
				
				promoQuery +=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.CREATED_BY= '"+userId+"' AND PM.MOC='"+moc+"' ";
			}
			
			
			

			/*if (!custChainL1.equalsIgnoreCase("All")) {
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

			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
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
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE AND A.ACTIVE=1 ";
			promoQuery += " AND A.ACTIVE = 1 AND A.STATUS IN('6') ";*/
			
			
			if(searchParameter!=null && searchParameter.length()>0){
				promoQuery +=" AND UCASE(PM.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
			}
			
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP ";
			} else {
				promoQuery += " ORDER BY PM.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength +  " ";
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoListingBean promoBean = new PromoListingBean();				
				promoBean.setPromo_id(obj[0]== null ? "" :obj[0].toString());
				promoBean.setOriginalId(obj[1]== null ? "" :obj[1].toString());
				promoBean.setStartDate(obj[2]== null ? "" : obj[2].toString());
				promoBean.setEndDate(obj[3]== null ? "" : obj[3].toString());
				promoBean.setMoc(obj[4]== null ? "" : obj[4].toString());
				promoBean.setCustomer_chain_l1(obj[5]== null ? "" :obj[5].toString());
				promoBean.setBasepack(obj[6]== null ? "" :obj[6].toString());
				promoBean.setOffer_desc(obj[7]== null ? "" :obj[7].toString());
				promoBean.setOffer_type(obj[8]== null ? "" :obj[8].toString());
				promoBean.setOffer_modality(obj[9]== null ? "" :obj[9].toString());
				promoBean.setGeography(obj[10]== null ? "" :obj[10].toString());
				promoBean.setQuantity((obj[11] == null || obj[11].toString().equals("")) ? "" : obj[11].toString());
				promoBean.setOffer_value(obj[12]== null ? "" :obj[12].toString());
				promoBean.setStatus(obj[13]== null ? "" :obj[13].toString());
				promoBean.setInvestmentType(obj[14]== null ? "" :obj[14].toString());
				promoBean.setSolCode(obj[15]== null ? "" :obj[15].toString());
				promoBean.setPromotionMechanics(obj[16]== null ? "" :obj[16].toString());
				promoBean.setSolCodeStatus(obj[17]== null ? "" :obj[17].toString());
				promoList.add(promoBean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int getDeletePromoListRowCount(String moc,String userId, String roleId) {
		//kiran - bigint to int changes
		//List<Integer> list = null;
		List<BigInteger> list = null;
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {
			/*String promoQuery = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ "INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK = C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID "; */
			//Added by kavitha D-SPRINT 9
			
			String promoQuery =" SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM  "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME  "
					+ " LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS "
					+ " FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					+ " LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					+ " LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE";
			//if (roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE")) //Commented code and added for ncmm,sc login-SPRINT 10
			if (roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE") || roleId.equalsIgnoreCase("NCMM") || roleId.equalsIgnoreCase("SC")) {
				//promoQuery += " INNER JOIN TBL_PROCO_KAM_MAPPING AS F ON A.CUSTOMER_CHAIN_L1=F.CUSTOMER_CHAIN_L1 WHERE F.USER_ID='"
					//	+ userId + "' "; Mayur commented for sprint 9
				
				promoQuery+=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.MOC='"+moc+"'";
				
			}

			/*if (roleId.equalsIgnoreCase("TME") || roleId.equalsIgnoreCase("DP")) {
				promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON C.CATEGORY=D.CATEGORY AND C.PRICE_LIST=D.PRICE_LIST WHERE D.USER_ID='"+ userId + "' ";
			}*/
			
			if (roleId.equalsIgnoreCase("TME")) {
				
				promoQuery +=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.CREATED_BY= '"+userId+"' AND PM.MOC='"+moc+"' ";
			}

			
			/*if (!custChainL1.equalsIgnoreCase("All")) {
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

			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND C.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND C.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack.substring(0, 5) + "' ";
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
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE AND A.ACTIVE=1 ";
			promoQuery += " AND A.ACTIVE = 1 AND A.STATUS IN('6') ";*/
			//A.ACTIVE = 0 AND A.STATUS IN('6','16','26','36') 
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		//kiran - big int to int changes
		//return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getDeletePromotionDump(ArrayList<String> headerList, String moc, String userId,String roleId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		//List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		//List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//Garima - changes for VARCHAR_FORMAT
			//promoQuery = "SELECT DISTINCT PROMO_ID, VARCHAR_FORMAT(START_DATE,'DD/MM/YYYY'),VARCHAR_FORMAT(END_DATE,'DD/MM/YYYY'), MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, C.MODALITY_NO, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, REASON, DELETE_REMARKS  from TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY ";
			//promoQuery = "SELECT DISTINCT PROMO_ID, DATE_FORMAT(START_DATE,'%d/%m/%Y'),DATE_FORMAT(END_DATE,'%d/%m/%Y'), MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, OFFER_DESC, P1_BASEPACK, P1_BASEPACK_RATIO, P2_BASEPACK, P2_BASEPACK_RATIO, P3_BASEPACK, P3_BASEPACK_RATIO, P4_BASEPACK, P4_BASEPACK_RATIO, P5_BASEPACK, P5_BASEPACK_RATIO, P6_BASEPACK, P6_BASEPACK_RATIO, C1_CHILD_PACK, C1_CHILD_PACK_RATIO, C2_CHILD_PACK, C2_CHILD_PACK_RATIO, C3_CHILD_PACK, C3_CHILD_PACK_RATIO, C4_CHILD_PACK, C4_CHILD_PACK_RATIO, C5_CHILD_PACK, C5_CHILD_PACK_RATIO, C6_CHILD_PACK, C6_CHILD_PACK_RATIO, THIRD_PARTY_DESC, THIRD_PARTY_PACK_RATIO, OFFER_TYPE, C.MODALITY_NO, OFFER_VALUE, KITTING_VALUE, GEOGRAPHY, UOM, REASON, DELETE_REMARKS  from TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_MODALITY_MASTER AS C ON A.OFFER_MODALITY=C.MODALITY ";
			//promoQuery += " INNER JOIN TBL_PROCO_TME_MAPPING AS D ON B.CATEGORY=D.CATEGORY AND B.PRICE_LIST=D.PRICE_LIST WHERE  A.ACTIVE=1 AND A.STATUS IN('6') ";
			//Added by Kavitha D -Sprint 9
			promoQuery=" SELECT PM.CHANNEL_NAME, PM.MOC, CM.ACCOUNT_TYPE, CM.POS_ONINVOICE, CM.PPM_ACCOUNT, PM.PROMO_ID,"
					+ "PR.PROMOTION_ID AS SOL_CODE, CASE WHEN MOC_GROUP = 'GROUP_ONE' THEN 'MOC' ELSE CASE WHEN MOC_GROUP = 'GROUP_THREE' THEN 'BM' ELSE '26 to 25' END END AS MOC_BM_CYCLE,"
					+ " PM.PROMO_TIMEPERIOD, CM.AB_CREATION, CM.SOL_RELEASE_ON,"
					+ "PM.START_DATE, PM.END_DATE, PM.OFFER_DESC, PR.PROMOTION_NAME, PM.BASEPACK_CODE, PM.CHILD_BASEPACK_CODE, PM.OFFER_TYPE, PM.OFFER_MODALITY, "
					+ "PM.PRICE_OFF, PM.QUANTITY, PM.BUDGET, PM.BRANCH, PM.CLUSTER, CASE WHEN PM.CR_SOL_TYPE IS NULL THEN 'Regular' ELSE PM.CR_SOL_TYPE END AS SOL_TYPE, "
					+ "CASE WHEN ST.SOL_REMARK IS NULL THEN 'Regular' ELSE ST.SOL_REMARK END AS REMARK, '' AS CMM_NAME, PM.CREATED_BY AS TME_NAME, "
					+ "PRM.CATEGORY AS SALES_CATEGORY, PRM.CATEGORY AS PSA_CATEGORY FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID,PROMOTION_STATUS FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID,PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					+ "LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					+ "LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE ";
			
			if(roleId.equals("TME")) {
				
				promoQuery +=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.CREATED_BY='"+ userId + "'AND PM.MOC='"+ moc + "' " ;
			}
			 //mayur's changes for sprint 9
			//if(roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE")) //Commented by Kavitha D-SPRINT 10

			if(roleId.equalsIgnoreCase("KAM") || roleId.equalsIgnoreCase("DP") || roleId.equalsIgnoreCase("COE") || roleId.equalsIgnoreCase("NCMM") || roleId.equalsIgnoreCase("SC")) //Added for MOC condition to download promo in NCMM,SC login-SPRINT 10
			{
				promoQuery+=" WHERE PR.PROMOTION_STATUS='Financial Close' AND PM.MOC='"+moc+"'";
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
					promoQuery += "AND (A.GEOGRAPHY like '%" + geography + "%' ";
					if (clusterList != null) {
						for (int i = 0; i < clusterList.size(); i++) {
							promoQuery += "OR A.GEOGRAPHY like '%" + clusterList.get(i).toString() + "%' ";
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
					promoQuery += "AND (A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') ";
				} else if (moc.equals("Q2")) {
					promoQuery += "AND (A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') ";
				} else if (moc.equals("Q3")) {
					promoQuery += "AND (A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') ";
				} else if (moc.equals("Q4")) {
					promoQuery += "AND (A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') ";
				} else {
					promoQuery += "AND A.MOC = '" + moc + "' ";
				}
			}
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE "; */
			
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
			logger.debug("Exception :", ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String promoDeleteDate(String Id) {
		//kiran - changes to replace fetch with limit and also CURRENT DATE
		//Query query  =sessionFactory.getCurrentSession().createNativeQuery("SELECT CASE WHEN START_DATE > CURRENT DATE THEN 'BIG' ELSE 'SMALL' END FROM MODTRD.TBL_PROCO_PROMOTION_MASTER WHERE ACTIVE=1 AND PROMO_ID=:Id FETCH FIRST ROW ONLY");
		Query query  =sessionFactory.getCurrentSession().createNativeQuery("SELECT CASE WHEN START_DATE > now() THEN 'BIG' ELSE 'SMALL' END FROM MODTRD.TBL_PROCO_PROMOTION_MASTER WHERE ACTIVE=1 AND PROMO_ID=:Id LIMIT 1");
		query.setString("Id", Id);
		List<String> list =query.list();
		return (String) ((list != null && list.size() > 0) ? list.get(0) : "");
	
	}
	
	//Added by Kajal G for KAM Volumn download starts-SPRINT 10
	@Override
	public List<String> getPPMAccount(String primaryAccount){
		String query = "SELECT DISTINCT PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE SECONDARY_CHANNEL = '"+ primaryAccount +"'"
				+ " AND SECONDARY_CHANNEL <> PPM_ACCOUNT ORDER BY PPM_ACCOUNT";
		Query query1  = sessionFactory.getCurrentSession().createNativeQuery(query);
		List<String> list = query1.list();
		return list;
	}
	
	//Added by Kajal G for KAM Volumn Upload starts-SPRINT 10
	@Override
	public String kamVolumeUpload(List<List<String>> excelData, String userId) {
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();
		
		String error_msg = "";
		int flag = 0;
		int gloabal = 0;
		Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP);
		
		for(int j=0; j<excelData.size(); j++) {
			List list = new ArrayList();
			list = excelData.get(j);
			int k=0;
			int i;
			for(i=0; i<list.size()+3; i++) {
				if(i == 8) 
					query.setString(i, userId);
				else if(i == 9) {
					if(j==0)
						query.setString(i, "Error Msg");
					else
						query.setString(i, error_msg);
				}
				else if(i == 10) 
					query.setTimestamp(i, new Timestamp(new Date().getTime()));
				else {
					query.setString(i, excelData.get(j).get(k));
					k++;
				}
			}
			while(i<34) {
				query.setString(i, null);
				i++;
			}
			query.executeUpdate();
			error_msg = "";
		}

		String CheckDPQty = "UPDATE TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP SET ERROR_MSG = 'KAM QUANTITY IS GREATER THAN DP QUNATITY'"
				+ "WHERE USER_ID = '"+ userId +"' AND PROMO_ID <> 'PROMO ID'"
				+ "AND (IFNULL(COLUMN_A, 0) + IFNULL(COLUMN_B, 0) + IFNULL(COLUMN_C, 0) + IFNULL(COLUMN_D, 0) + IFNULL(COLUMN_E, 0)"
				+ "+ IFNULL(COLUMN_F, 0) + IFNULL(COLUMN_G, 0) + IFNULL(COLUMN_H, 0) + IFNULL(COLUMN_I, 0) + IFNULL(COLUMN_J, 0)"
				+ "+ IFNULL(COLUMN_K, 0) + IFNULL(COLUMN_L, 0) + IFNULL(COLUMN_M, 0) + IFNULL(COLUMN_N, 0) + IFNULL(COLUMN_O, 0)"
				+ "+ IFNULL(COLUMN_P, 0) + IFNULL(COLUMN_Q, 0) + IFNULL(COLUMN_R, 0) + IFNULL(COLUMN_S, 0) + IFNULL(COLUMN_T, 0)"
				+ "+ IFNULL(COLUMN_U, 0) + IFNULL(COLUMN_V, 0) + IFNULL(COLUMN_W, 0) + IFNULL(COLUMN_X, 0) + IFNULL(COLUMN_Y, 0) + IFNULL(COLUMN_Z, 0)) > DP_QUANTITY";
		Query queryToCheckDPQty =  sessionFactory.getCurrentSession().createNativeQuery(CheckDPQty);
		queryToCheckDPQty.executeUpdate();
		
		String ValidateDPQty = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP WHERE USER_ID = '"+ userId +"' AND PROMO_ID <> 'PROMO ID'"
				+ "AND ERROR_MSG <> '' "; 
		Query queryToValidateDPQty  = sessionFactory.getCurrentSession().createNativeQuery(ValidateDPQty);
		List resultList = queryToValidateDPQty.list();
		int errorCount = ((BigInteger) resultList.get(0)).intValue();
		if(errorCount > 0) {
			flag = 1;
		}
		else {
			String getTempRows = "SELECT * FROM TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP WHERE USER_ID = '"+ userId +"'";
			Query queryToGetTempRows  = sessionFactory.getCurrentSession().createNativeQuery(getTempRows);
			List<Object[]> result = queryToGetTempRows.list();
			List<List<String>> finalResult = new ArrayList();
			Iterator itr = queryToGetTempRows.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
					
				}
				obj = null;
				finalResult.add(dataObj);
			}
			
			Query queryInsert = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_MASTER);
			
			for(int i=1; i<finalResult.size();i++) {
				long count = finalResult.get(0).stream().filter(x -> !x.isEmpty()).count();
				
				for(int j=11;j<=count+1;j++) {
					if(finalResult.get(i).get(j).length() > 0) {
						queryInsert.setString(0, finalResult.get(i).get(0));
						queryInsert.setString(1, finalResult.get(i).get(1));
						queryInsert.setString(2, finalResult.get(i).get(2));
						queryInsert.setString(3, finalResult.get(i).get(3));
						queryInsert.setString(4, finalResult.get(i).get(4));
						queryInsert.setString(5, finalResult.get(i).get(5));
						queryInsert.setString(6, finalResult.get(i).get(6));
						queryInsert.setString(7, finalResult.get(i).get(7));
						queryInsert.setString(8, finalResult.get(0).get(j));
						queryInsert.setString(9, finalResult.get(i).get(j));
						queryInsert.setString(10, userId);
						queryInsert.setTimestamp(11, new Timestamp(new Date().getTime()));
						queryInsert.executeUpdate();
					}
					
				}
				
			}
			
		}
		
		if (flag == 1) {
			gloabal = 1;
		}

		if (gloabal == 1) {
			flag = 0;
			gloabal = 0;
			return "EXCEL_NOT_UPLOADED";
		} else {
			flag = 0;
			gloabal = 0;
			return "EXCEL_UPLOADED";
		}

	}
	
	//Added by Kajal G for KAM Volume Error download starts-SPRINT 10
	public List<ArrayList<String>> getKAMErrorDetails(String userId){
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT * FROM TBL_PROCO_PROMOTION_KAM_VOLUME_UPLOAD_TEMP WHERE USER_ID=:userId";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter("userId", userId);
			Iterator itr = query.list().iterator();
//			downloadDataList.add(headerList);
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
	
	//Added by Kavitha D for promo listing download starts-SPRINT 9
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String userId,String moc,String roleId, String[] kamAccounts){
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
		/*String qry=" SELECT PM.CHANNEL_NAME, PM.MOC, CM.ACCOUNT_TYPE, CM.POS_ONINVOICE, CM.PPM_ACCOUNT, PM.PROMO_ID,"
				+ " PR.PROMOTION_ID AS SOL_CODE, CASE WHEN MOC_GROUP = 'GROUP_ONE' THEN 'MOC' ELSE CASE WHEN MOC_GROUP = 'GROUP_THREE' THEN 'BM' ELSE '26 to 25' END END AS MOC_BM_CYCLE,"
				+ " PM.PROMO_TIMEPERIOD, CM.AB_CREATION, CM.SOL_RELEASE_ON,"
				+ " PM.START_DATE, PM.END_DATE, PM.OFFER_DESC, PR.PROMOTION_NAME, PM.BASEPACK_CODE, PM.CHILD_BASEPACK_CODE, PM.OFFER_TYPE, PM.OFFER_MODALITY, "
				+ " PM.PRICE_OFF, PM.QUANTITY, PM.BUDGET, PM.BRANCH, PM.CLUSTER ,PM.TEMPLATE_TYPE AS PROMO_TEMPLATE, SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE ,CASE WHEN PM.CR_SOL_TYPE IS NULL THEN 'Regular' ELSE PM.CR_SOL_TYPE END AS SOL_TYPE, "
				+ " CASE WHEN ST.SOL_REMARK IS NULL THEN 'Regular' ELSE ST.SOL_REMARK END AS REMARK, '' AS CMM_NAME, PM.CREATED_BY AS TME_NAME, "
				+ " PRM.SALES_CATEGORY AS SALES_CATEGORY, PRM.PPM_SALES_CATEGORY AS PSA_CATEGORY FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
				+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
				+ " LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID) PR ON PR.PROMO_ID = PM.PROMO_ID "
				+ " LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
				+ " LEFT JOIN (SELECT DISTINCT BASEPACK,SALES_CATEGORY,PPM_SALES_CATEGORY FROM TBL_PROCO_PRODUCT_MASTER_V2) PRM ON PRM.BASEPACK = PM.BASEPACK_CODE "; */
			//Added by Kavitha D-changes SPRINT 9
		   qry = sessionFactory.getCurrentSession().createNativeQuery("CALL PROMO_LISTING_DOWNLOAD(:moc)");
			qry.setParameter("moc", moc);
			qry.executeUpdate();
			
			String query= " SELECT CHANNEL,YEAR,MOC ,ACCOUNT_TYPE,CLAIM_SETTLEMENT_TYPE,SECONDARY_CHANNEL,PPM_ACCOUNT,PROMO_ID,SOLCODE,MOC_CYCLE,PROMO_TIMEPERIOD,SOL_RELEASE_ON,"
					+ " START_DATE,END_DATE,OFFER_DESC,PPM_DESC,BASEPACK_CODE,BASEPACK_DESC,CHILDPACK,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,QUANTITY,FIXED_BUDGET ,BRANCH,"
					+ " SALES_CLUSTER ,PPM_CUSTOMER,CMM_NAME,TME_NAME,SALES_CATEGORY,PSA_CATEGORY,PROMOTION_STATUS,PPM_PROMOTION_CREATOR ,PROMOTION_MECHANICS,INVESTMENT_TYPE,"
					+ " SALES_CLUSTER_CODE ,BRAND,SUB_BRAND,PPM_BUDGET_HOLDER_NAME ,FUND_TYPE,INVESTMENT_AMOUNT ,PROMO_ENTRY_TYPE ,PROMO_USER_NAME ,PROMO_USER_TIME ,"
					+ " PPM_APPROVED_DATE ,PPM_CREATION_DATE ,NON_UNIFY,PPM_SUBMISSION_DATE ,PPM_MODIFIED_DATE ,COE_REMARKS,MRP ,AB_CREATION ,BUDGET "
					+ " FROM TBL_PROCO_PROMO_LISTING_REPORT  LR";
		
		
		
		if(roleId.equalsIgnoreCase("KAM"))
		{
			query+=" WHERE LR.MOC='"+moc+"' AND LR.PPM_ACCOUNT IN (:kamAccount) ";
		}else
		{
			query+=" WHERE LR.TME_NAME=:userId AND LR.MOC=:moc";		
		}
		
		
		Query query1  =sessionFactory.getCurrentSession().createNativeQuery(query);
		
		if(roleId.equalsIgnoreCase("KAM")) {
			query1.setParameterList("kamAccount", kamAccounts);
			
		} else {
			query1.setString("moc", moc);
			query1.setString("userId", userId);
		}


		Iterator itr = query1.list().iterator();
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
	//Added by Kavitha D for promo listing download ends-SPRINT 9

	//Added by Kavitha D for promo display grid download starts-SPRINT 9
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoListRowCountGrid(String userId,String roleId,String moc, String[] kamAccounts) {
		List<BigInteger> list = null;
		try {
			String promoQueryCount =" SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS ";
					//+ " LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					//+ " LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE"
					//+ " LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE ";	
			
			if (roleId.equalsIgnoreCase("TME")) {
				promoQueryCount += "WHERE PM.CREATED_BY='"+ userId +"'AND PM.MOC='"+moc+"'";
			}
			/*
			if (roleId.equalsIgnoreCase("DP") && (moc== null || moc.isEmpty())) {
				promoQueryCount += " WHERE PM.STATUS = 1 ";
			} */
			
			//Added by Kavitha D NCMM,SC PromoListing starts-SPRINT10 
			if (roleId.equalsIgnoreCase("NCMM")) {
				
				promoQueryCount += " WHERE PM.STATUS IN('3','39') AND PM.MOC='"+moc+"' ";
				}
			if (roleId.equalsIgnoreCase("SC")) {
				
				promoQueryCount += " WHERE PM.STATUS IN('38','41') AND PM.MOC='"+moc+"' ";
			}
			//Added by Kavitha D NCMM,SC PromoListing ends-SPRINT10 

			if (roleId.equalsIgnoreCase("DP")&& (moc!= null || !moc.isEmpty())) {
				if(moc.equalsIgnoreCase("all"))
					promoQueryCount += " WHERE PM.TEMPLATE_TYPE = 'R' AND PM.STATUS = 1";//Added by kavitha D-Sprint 9
				else
				promoQueryCount += " WHERE PM.STATUS IN (1, 3) AND PM.MOC='"+moc+ "' ";
			}
			if (roleId.equalsIgnoreCase("KAM")) {
						
				promoQueryCount += " WHERE PM.MOC='"+moc+ "' AND PM.PPM_ACCOUNT IN ( ";
				
				for(String kamAccount:kamAccounts)
					promoQueryCount+="'"+kamAccount+"',";
					
				 promoQueryCount = promoQueryCount.substring(0, promoQueryCount.length()-1)+")";
				}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQueryCount);
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoListingBean> getPromoTableListGrid(int pageDisplayStart, int pageDisplayLength, String userId,
			String roleId,String moc,String searchParameter, String[] kamAccounts) {
		List<PromoListingBean> promoListDisplay = new ArrayList<>();
		try {
			
			//Mayur changes for SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE to get the date only
			
			String promoQueryGrid=" SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.PROMO_ID AS ORIGINAL_ID,PM.START_DATE,PM.END_DATE,"
					+ " PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,PM.OFFER_TYPE,PM.OFFER_MODALITY,"
					+ " PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,PSM.STATUS, PM.CREATED_BY, SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE, PM.TEMPLATE_TYPE AS REMARKS,"
					//+ " PR.INVESTMENT_TYPE, PR.PROMOTION_ID AS SOL_CODE,PR.PROMOTION_MECHANICS,PR.PROMOTION_STATUS AS SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ " 'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS ";
					//+ " LEFT JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID, INVESTMENT_TYPE, PROMOTION_MECHANICS, PROMOTION_STATUS) PR ON PR.PROMO_ID = PM.PROMO_ID "
					//+ " LEFT JOIN TBL_PROCO_SOL_TYPE ST ON ST.SOL_TYPE = PM.CR_SOL_TYPE "
					//+ " LEFT JOIN TBL_PROCO_PRODUCT_MASTER PRM ON PRM.BASEPACK = PM.BASEPACK_CODE ";
		
			if (roleId.equalsIgnoreCase("TME")) {
				promoQueryGrid += "WHERE PM.CREATED_BY='"+ userId +"' AND PM.MOC='"+moc+"'";
			}
			/*
			if (roleId.equalsIgnoreCase("DP") && (moc== null || moc.isEmpty())) {
				promoQueryGrid += "WHERE PM.STATUS = 1 ";
			} */
			
			//Added by Kavitha D NCMM PromoListing-SPRINT10 
			if (roleId.equalsIgnoreCase("NCMM")) {
				
				promoQueryGrid += " WHERE PM.STATUS IN('3','39') AND PM.MOC='"+moc+"' ";
			}
			if (roleId.equalsIgnoreCase("SC")) {
				
				promoQueryGrid += " WHERE PM.STATUS IN('38','41') AND PM.MOC='"+moc+"' ";
			}
			if (roleId.equalsIgnoreCase("DP") /*&& (moc!= null || !moc.isEmpty())*/) {
				if(moc.equalsIgnoreCase("all"))
					promoQueryGrid += " WHERE PM.TEMPLATE_TYPE = 'R' AND PM.STATUS = 1";  //For DP Volume Upload
				else
					promoQueryGrid += " WHERE PM.STATUS IN (1, 3) AND PM.MOC='"+moc+ "' ";  //For DP Promo Listing
			}
			
			if (roleId.equalsIgnoreCase("KAM")) {
				promoQueryGrid += " WHERE PM.MOC='"+moc+ "' AND PM.PPM_ACCOUNT IN (:kamAccount) ";
			}
			if(searchParameter!=null && searchParameter.length()>0){
				promoQueryGrid +="AND UCASE(PM.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
			}
			if (pageDisplayLength == 0) {
				promoQueryGrid += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP";
			} else {
				promoQueryGrid += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength + "";
			}
			
			//System.out.println("Volume upload promo:"+ promoQueryGrid);
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQueryGrid);
			if (roleId.equalsIgnoreCase("KAM")) {
				query.setParameterList("kamAccount", kamAccounts);
			}
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoListingBean promoBean = new PromoListingBean();
				
				promoBean.setPromo_id(obj[0]== null ? "" :obj[0].toString());
				promoBean.setOriginalId(obj[1]== null ? "" :obj[1].toString());
				promoBean.setStartDate(obj[2]== null ? "" : obj[2].toString());
				promoBean.setEndDate(obj[3]== null ? "" : obj[3].toString());
				promoBean.setMoc(obj[4]== null ? "" : obj[4].toString());
				promoBean.setCustomer_chain_l1(obj[5]== null ? "" :obj[5].toString());
				promoBean.setBasepack(obj[6]== null ? "" :obj[6].toString());
				promoBean.setOffer_desc(obj[7]== null ? "" :obj[7].toString());
				promoBean.setOffer_type(obj[8]== null ? "" :obj[8].toString());
				promoBean.setOffer_modality(obj[9]== null ? "" :obj[9].toString());
				promoBean.setGeography(obj[10]== null ? "" :obj[10].toString());
				promoBean.setQuantity((obj[11] == null || obj[11].toString().equals("")) ? "" : obj[11].toString());
				promoBean.setOffer_value(obj[12]== null ? "" :obj[12].toString());
				promoBean.setStatus(obj[13]== null ? "" :obj[13].toString());
				promoBean.setUserId(obj[14]== null ? "" :obj[14].toString());
				promoBean.setChangeDate(obj[15]== null ? "" :obj[15].toString());
				promoBean.setRemark(obj[16]== null ? "" :obj[16].toString());
				promoBean.setInvestmentType(obj[17]== null ? "" :obj[17].toString());
				promoBean.setSolCode(obj[18]== null ? "" :obj[18].toString());
				promoBean.setPromotionMechanics(obj[19]== null ? "" :obj[19].toString());
				promoBean.setSolCodeStatus(obj[20]== null ? "" :obj[20].toString());			
				promoListDisplay.add(promoBean);
			}
		 }catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoListDisplay;
	}
	//Added by Kavitha D for promo display grid download ends-SPRINT 9
	
	
	//Added by Kavitha D for promo listing MOC filter-SPRINT 9

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPromoMoc() {
		List<String> promoMOC = new ArrayList<String>();
		try {
			Query qryPromoMoc = sessionFactory.getCurrentSession().createNativeQuery(
					//"SELECT DISTINCT PM.MOC FROM TBL_PROCO_PROMOTION_MASTER_V2 PM INNER JOIN TBL_VAT_MOC_MASTER MM ON CONCAT(SUBSTR(MM.MOC, 1, 2), SUBSTR(MM.MOC, 3, 4)) = PM.MOC ORDER BY PM.MOC DESC"
					//" SELECT DISTINCT PM.MOC FROM TBL_PROCO_PROMOTION_MASTER_V2 PM INNER JOIN TBL_VAT_MOC_MASTER MM ON MM.MOC = PM.MOC ORDER BY PM.MOC DESC ");
					" SELECT DISTINCT PM.MOC FROM TBL_PROCO_PROMOTION_MASTER_V2 PM INNER JOIN TBL_VAT_MOC_MASTER MM ON MM.MOC = PM.MOC ORDER BY CONCAT(SUBSTRING(PM.MOC, 3, 4), SUBSTRING(PM.MOC, 1, 2)) DESC ");
					promoMOC = qryPromoMoc.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return promoMOC;
	
		
	}
	
	//Added by Kajal G for KAM Volumne Download starts-SPRINT 10
	@Override
	public List<ArrayList<String>> getPromotionListingDownload(ArrayList<String> headerList, String moc, String primaryAcc){
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		
		try {
		
		String query= "SELECT PROMO_ID, MOC, BASEPACK_CODE, OFFER_DESC, PRICE_OFF, CLUSTER, QUANTITY AS DP_Volume"
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC = '"+ moc +"' AND STATUS = '3'";
		
		Query query1 =sessionFactory.getCurrentSession().createNativeQuery(query);
		Iterator itr = query1.list().iterator();
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
			dataObj.add(primaryAcc);
			downloadDataList.add(dataObj);
		}
		return downloadDataList;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}

	//Added By Sarin - Sprint10
	@Override
	public List<String> getPromoPrimaryChannels() {
		List<String> primaryChannelList = new ArrayList<String>();
		try {
			Query qryPrimary = sessionFactory.getCurrentSession().createNativeQuery("SELECT PRI_CHANNEL_NAME FROM TBL_PROCO_PRIMARY_CHANNEL_MASTER WHERE ACTIVE = 1 ORDER BY PRI_CHANNEL_NAME");
			primaryChannelList = qryPrimary.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
			return null;
		}
		return primaryChannelList;
	}
}
