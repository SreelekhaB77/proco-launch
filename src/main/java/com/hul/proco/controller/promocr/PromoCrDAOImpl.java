package com.hul.proco.controller.promocr;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
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
	public List<PromoCrBean> getPromoTableList(int pageDisplayStart, int pageDisplayLength, String cagetory,
			String brand, String basepack, String custChainL1, String custChainL2, String geography, String offerType,
			String modality, String year, String moc, String userId, int active, String roleId) {

		List<PromoCrBean> promoList = new ArrayList<>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - rownumber changes
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, VARCHAR_FORMAT(A.START_DATE, 'DD/MM/YYYY'), VARCHAR_FORMAT(A.END_DATE, 'DD/MM/YYYY'),A.REASON,A.REMARK,A.CHANGES_MADE, "
					+ "A.UPDATE_STAMP,ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT FROM TBL_PROCO_PROMOTION_MASTER AS A "
					+ " INNER JOIN TBL_PROCO_PRODUCT_MASTER AS C ON A.P1_BASEPACK=C.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS E ON A.STATUS=E.STATUS_ID WHERE A.ACTIVE=1 ";*/
			//Garima - changes for VARCHAR_FORMAT
			promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.P1_BASEPACK, A.OFFER_DESC, A.OFFER_TYPE, A.OFFER_MODALITY, A.GEOGRAPHY, A.QUANTITY, A.UOM, A.OFFER_VALUE, A.MOC, A.CUSTOMER_CHAIN_L1, A.KITTING_VALUE, E.STATUS, DATE_FORMAT(A.START_DATE,'%d/%m/%Y'), DATE_FORMAT(A.END_DATE,'%d/%m/%Y'),A.REASON,A.REMARK,A.CHANGES_MADE, "
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
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP ORDER BY PROMO_TEMP.UPDATE_STAMP DESC";
			} else {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength + " ORDER BY PROMO_TEMP.UPDATE_STAMP DESC";
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);

			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoCrBean promoBean = new PromoCrBean();
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
				promoBean.setStatus(obj[12] == null ? "" : obj[12].toString());
				promoBean.setStartDate(obj[13] == null ? "" : obj[13].toString());
				promoBean.setEndDate(obj[14] == null ? "" : obj[14].toString());
				promoBean.setReason(obj[15] == null ? "" : obj[15].toString());
				promoBean.setRemark(obj[16] == null ? "" : obj[16].toString());
				promoBean.setChangesMade(obj[17] == null ? "" : obj[17].toString());
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
		String res = "";
		int status = 0;
		try {
			if (roleId.equalsIgnoreCase("NCMM")) {
				status = 9;
			} else {
				status = 10;
			}
			String[] split = promoId.split(",");
			for (int i = 0; i < split.length; i++) {
				String promo = split[i];
				createPromoDaoImpl.saveStatusInStatusTracker(promo, status, "", userId);
			}
			res = "SUCCESS";
		} catch (Exception e) {
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
}
