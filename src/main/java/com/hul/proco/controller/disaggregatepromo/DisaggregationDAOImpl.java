package com.hul.proco.controller.disaggregatepromo;

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
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreatePromoDAOImpl;

@Repository
public class DisaggregationDAOImpl implements DisaggregationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CreatePromoDAOImpl createPromoDAOImpl;

	private Logger logger = Logger.getLogger(DisaggregationDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public int getDisaggregationRowCount(String cagetory, String brand, String basepack, String custChainL1,
			String custChainL2, String offerType, String modality, String year, String moc, String userId) {
		//kiran - bigint to int changes
		//List<Integer> list = null;
		List<BigInteger> list = null;
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		try {
			//kiran - changes from ROWNUMBER() to ROW_NUMBER()
			/*String rowCount = "SELECT COUNT(1) FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
					+ "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '', ROWNUMBER() OVER (ORDER BY PROMO_ID DESC) AS ROW_NEXT  "
					+ "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
					+ "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
					+ "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND F.USER_ID='" + userId
					+ "' ";*/
			String rowCount = "SELECT COUNT(1) FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
					+ "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '', ROW_NUMBER() OVER (ORDER BY PROMO_ID DESC) AS ROW_NEXT  "
					+ "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
					+ "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
					+ "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND F.USER_ID='" + userId
					+ "' ";
			rowCount += " AND A.STATUS IN(3,4,13,14,23,24,33,34,12,22,32,37) ";

			if (!cagetory.equalsIgnoreCase("All")) {
				rowCount += "AND B.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				rowCount += "AND B.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				rowCount += "AND A.P1_BASEPACK = '" + basepack + "' ";
			}
			if (!custChainL1.equalsIgnoreCase("All")) {
				if (custL1.size() == 1) {
					rowCount += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(0) + "' )";
				} else if (custL1.size() > 1) {
					for (int i = 0; i < custL1.size(); i++) {
						if (i == 0) {
							rowCount += "AND (A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' ";
						} else if (i < custL1.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "' ";
						} else {
							rowCount += "OR A.CUSTOMER_CHAIN_L1 = '" + custL1.get(i) + "') ";
						}
					}
				}
			}
			if (!custChainL2.equalsIgnoreCase("All")) {
				if (custL2.size() == 1) {
					rowCount += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(0) + "%') ";
				} else if (custL2.size() > 1) {
					for (int i = 0; i < custL2.size(); i++) {
						if (i == 0) {
							rowCount += "AND (A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else if (i < custL2.size() - 1) {
							rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%' ";
						} else {
							rowCount += "OR A.CUSTOMER_CHAIN_L2 LIKE '%" + custL2.get(i) + "%') ";
						}
					}
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
					rowCount += "AND (A.MOC = 'MOC1' OR A.MOC='MOC2' OR A.MOC='MOC3') ";
				} else if (moc.equals("Q2")) {
					rowCount += "AND (A.MOC = 'MOC4' OR A.MOC='MOC5' OR A.MOC='MOC6') ";
				} else if (moc.equals("Q3")) {
					rowCount += "AND (A.MOC = 'MOC7' OR A.MOC='MOC8' OR A.MOC='MOC9') ";
				} else if (moc.equals("Q4")) {
					rowCount += "AND (A.MOC = 'MOC10' OR A.MOC='MOC11' OR A.MOC='MOC12') ";
				} else {
					rowCount += "AND A.MOC = '" + moc + "' ";
				}
			}
			//rowCount += " AND A.START_DATE>=CURRENT_DATE ";
			rowCount += ") AS PLAN_TEMP";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
			// query.setParameter("userId", userId);
			list = query.list();
		} catch (Exception ex) {
			logger.error("Exception: ", ex);
		}
		//return (list != null && list.size() > 0) ? list.get(0) : 0;
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DisaggregationBean> getDisaggregationTableList(int pageDisplayStart, int pageDisplayLength,
			String cagetory, String brand, String basepack, String custChainL1, String custChainL2, String offerType,
			String modality, String year, String moc, String userId) {
		List<DisaggregationBean> promoList = new ArrayList<>();
		List<String> custL1 = new ArrayList<String>(Arrays.asList(custChainL1.split(",")));
		List<String> custL2 = new ArrayList<String>(Arrays.asList(custChainL2.split(",")));
		String promoQuery = "";
		try {
			//kiran - changes from ROWNUMBER() to ROW_NUMBER()
			/*promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
					+ "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', A.KITTING_VALUE, D.STATUS, A.UPDATE_STAMP, ROWNUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT  "
					+ "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
					+ "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
					+ "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND F.USER_ID='" + userId
					+ "' ";*/
			promoQuery = "SELECT * FROM (SELECT A.PROMO_ID, A.MOC, A.CUSTOMER_CHAIN_L1, A.CUSTOMER_CHAIN_L2, B.CATEGORY, B.BRAND, B.BASEPACK, "
					+ "B.BASEPACK_DESC, OFFER_TYPE, OFFER_MODALITY, OFFER_DESC, OFFER_VALUE, UOM, GEOGRAPHY, A.QUANTITY, '100.00%', A.KITTING_VALUE, D.STATUS, A.UPDATE_STAMP, ROW_NUMBER() OVER (ORDER BY A.UPDATE_STAMP DESC) AS ROW_NEXT  "
					+ "FROM TBL_PROCO_PROMOTION_MASTER AS A INNER JOIN TBL_PROCO_PRODUCT_MASTER AS B ON A.P1_BASEPACK=B.BASEPACK INNER JOIN TBL_PROCO_STATUS_MASTER AS D ON A.STATUS=D.STATUS_ID "
					+ "INNER JOIN TBL_PROCO_TME_MAPPING AS F ON B.CATEGORY=F.CATEGORY AND B.PRICE_LIST=F.PRICE_LIST "
					+ "WHERE (A.QUANTITY IS NOT NULL AND A.QUANTITY<>'') AND A.ACTIVE = 1 AND F.USER_ID='" + userId
					+ "' ";
			promoQuery += " AND A.STATUS IN(3,4,13,14,23,24,33,34,12,22,32,37) ";

			if (!cagetory.equalsIgnoreCase("All")) {
				promoQuery += "AND B.CATEGORY = '" + cagetory + "' ";
			}
			if (!brand.equalsIgnoreCase("All")) {
				promoQuery += "AND B.BRAND = '" + brand + "' ";
			}
			if (!basepack.equalsIgnoreCase("All")) {
				promoQuery += "AND A.P1_BASEPACK = '" + basepack + "' ";
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
			//promoQuery += " AND A.START_DATE>=CURRENT_DATE ";
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP ORDER BY PROMO_TEMP.UPDATE_STAMP DESC ";
			} else {
				promoQuery += " ORDER BY A.UPDATE_STAMP DESC) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart
						+ " AND " + pageDisplayLength + " ORDER BY PROMO_TEMP.UPDATE_STAMP DESC";
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);
			// query.setParameter("userId", userId);

			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				DisaggregationBean bean = new DisaggregationBean();
				bean.setPromo_id(obj[0].toString());
				bean.setMoc(obj[1].toString());
				bean.setCustomerChainL1(obj[2].toString());
				// bean.setCustomerChainL2(obj[3].toString());
				bean.setSalesCategory(obj[4].toString());
				bean.setBrand(obj[5].toString());
				bean.setBasepack(obj[6].toString());
				bean.setBasepackDesc(obj[7].toString());
				bean.setOffer_type(obj[8].toString());
				bean.setOffer_modality(obj[9].toString());
				bean.setOffer_desc(obj[10].toString());
				bean.setOffer_value(obj[11].toString());
				bean.setUom(obj[12].toString());
				bean.setGeography(obj[13].toString());
				bean.setPlannedQty(obj[14].toString());
				bean.setNational(obj[15].toString());
				bean.setKitting_value(obj[16].toString());
				bean.setStatus(obj[17] == null ? "" : obj[17].toString());
				promoList.add(bean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
		return promoList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String disaggregatePromos(String promoId[], String[] mocs, String userId) {
		String res = "";
		String res1 = "";
		String basepack = "";
		double total = 0.0;
		String qry = "";
		//Integer qty = null;
		double qty = 0.0;
		Map<String, DisaggregationQtyBean> map = new HashMap<String, DisaggregationQtyBean>();
		List<DisaggregationQtyBean> list1 = new ArrayList<>();
		Integer totalOfDepot = 0;
		Double totalPer = 0.0;
		String mocsToStoreInDb = "";
		Set<String> yearSet = new HashSet<String>();
		AddDepotBean addDepotBean = new AddDepotBean();
		boolean isDepotAdded = false;
		long startTime = System.currentTimeMillis();
		try {
			for (int pi = 0; pi < promoId.length; pi++) {
				totalOfDepot = 0;
				total = 0.0;
				totalPer = 0.0;
				map = new HashMap<>();
				list1 = new ArrayList<>();
				List<String> depotListWithoutDis = new ArrayList<>();
				mocsToStoreInDb = "";
				String customerChainL1 = "";
				String PromoIdBasePack[]= promoId[pi].split(",");
				Query QueryTogetDetails = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2,P1_BASEPACK,QUANTITY FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId AND ACTIVE=1 AND P1_BASEPACK=:P1BasePack");
				QueryTogetDetails.setParameter("promoId", PromoIdBasePack[0]);
				QueryTogetDetails.setParameter("P1BasePack",PromoIdBasePack[1]);
				Iterator iterator = QueryTogetDetails.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					basepack = obj[2].toString();
					//qty = Integer.parseInt(obj[3].toString());
					qty = Double.parseDouble(obj[3].toString());
					customerChainL1 = obj[0].toString();
				}
				// select depot from add depot
				Query qryTogetDepotFromAddDepot = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT BRANCH,CLUSTER,DEPOT,QUANTITY FROM TBL_PROCO_ADD_DEPOT_TEMP WHERE PROMO_ID=:promoId AND USER_ID=:userId");
				qryTogetDepotFromAddDepot.setString("promoId", PromoIdBasePack[0]);
				qryTogetDepotFromAddDepot.setString("userId", userId);
				Iterator getDepotFromAddDepot = qryTogetDepotFromAddDepot.list().iterator();
				while (getDepotFromAddDepot.hasNext()) {
					Object[] obj = (Object[]) getDepotFromAddDepot.next();
					isDepotAdded = true;
					addDepotBean.setBranch(obj[0].toString());
					addDepotBean.setCluster(obj[1].toString());
					addDepotBean.setDepot(obj[2].toString());
					addDepotBean.setDepotQty(Integer.parseInt(obj[3].toString()));
				}
				if (isDepotAdded) {
					qty = qty - addDepotBean.getDepotQty(); // subtract Added
															// depot qty from
															// main qty
				}

				Query QueryTogetDepots = sessionFactory.getCurrentSession().createNativeQuery(
						"select C.DEPOT from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND  B.BASEPACK = A.P1_BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 GROUP BY C.DEPOT");
				QueryTogetDepots.setString("promoId", PromoIdBasePack[0]);
				List depotList = QueryTogetDepots.list();

				Query QueryTogetBranches = sessionFactory.getCurrentSession().createNativeQuery(
						"select BRANCH from TBL_PROCO_PROMOTION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basepack GROUP BY BRANCH");
				QueryTogetBranches.setString("promoId", PromoIdBasePack[0]);
				QueryTogetBranches.setString("basepack", basepack);
				List branchList = QueryTogetBranches.list();

				Query QueryTogetCLusters = sessionFactory.getCurrentSession().createNativeQuery(
						"select CLUSTER from TBL_PROCO_PROMOTION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basepack GROUP BY CLUSTER");
				QueryTogetCLusters.setString("promoId", PromoIdBasePack[0]);
				QueryTogetCLusters.setString("basepack", basepack);
				List clusterList = QueryTogetCLusters.list();

				mocsToStoreInDb = "";
				if (depotList != null && depotList.size() > 0) {
					String year = "";
					for (int k = 0; k < mocs.length; k++) {
						String mocStr = mocs[k];
						if (k < mocs.length - 1) {
							mocsToStoreInDb = mocsToStoreInDb + mocStr + ",";
						} else {
							mocsToStoreInDb = mocsToStoreInDb + mocStr;
						}
						year = "20" + mocStr.substring(mocStr.indexOf("'") + 1);
						yearSet.add(year);
					}
					String year1 = "";
					Iterator<String> iterator3 = yearSet.iterator();
					while (iterator3.hasNext()) {
						String year2 = iterator3.next();
						List<String> list12 = new ArrayList();
						for (int j = 0; j < mocs.length; j++) {
							String mocStr = mocs[j];
							String moc = mocStr.substring(4, mocStr.indexOf("'"));
							year1 = "20" + mocStr.substring(mocStr.indexOf('\'') + 1);
							if (year1.equals(year2)) {
								list12.add(moc);
							}
						}
						if (list12.size() == 1) {
							String moc = list12.get(0);
							qry += "SELECT A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE,SUM(MOC" + moc + ")";
						} else if (list12.size() > 1) {
							for (int j = 0; j < list12.size(); j++) {
								String moc = list12.get(j);
								if (j == 0) {
									qry += "SELECT A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE,SUM(MOC" + moc;
								} else if (j < list12.size() - 1) {
									qry += "+MOC" + moc;
								} else if (j == list12.size() - 1) {
									qry += "+MOC" + moc + ")";
								}
							}
						}
						qry += " FROM TBL_PROCO_SALES_MASTER AS A  WHERE A.YEAR=:year AND A.DEPOT IN(:depot) AND A.BASEPACK=:basepack "
								+ "AND A.CUSTOMER_CHAIN_L1=:customerChainL1 AND A.BRANCH_CODE IN(:branch) AND A.CLUSTER_CODE IN(:cluster) GROUP BY A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE";
						Query QueryTogetSales = sessionFactory.getCurrentSession().createNativeQuery(qry);
						QueryTogetSales.setParameterList("depot", depotList);
						QueryTogetSales.setString("basepack", basepack);
						QueryTogetSales.setString("year", year2);
						QueryTogetSales.setString("customerChainL1", customerChainL1);
						QueryTogetSales.setParameterList("branch", branchList);
						QueryTogetSales.setParameterList("cluster", clusterList);
						Iterator iterator2 = QueryTogetSales.list().iterator();
						while (iterator2.hasNext()) {
							DisaggregationQtyBean bean = new DisaggregationQtyBean();
							Object[] obj = (Object[]) iterator2.next();
							if (map.containsKey(
									obj[0].toString() + ":" + obj[1].toString() + ":" + obj[2].toString())) {
								if (obj[1] != null && obj[3] != null) {
									if (Double.parseDouble(obj[3].toString()) > 0) {
										bean = map.get(
												obj[0].toString() + ":" + obj[1].toString() + ":" + obj[2].toString());
										Double depotTotal = Double.parseDouble(obj[3].toString());
										Double previousTotal = bean.getSaleTotalQty();
										bean.setSaleTotalQty(depotTotal + previousTotal);
										total += Double.parseDouble(obj[3].toString());
										map.put(obj[0].toString() + ":" + obj[1].toString() + ":" + obj[2].toString(),
												bean);
									}
								}
							} else {
								if (obj[1] != null && obj[3] != null) {
									if (Double.parseDouble(obj[3].toString()) > 0) {
										bean.setDepot(obj[0].toString());
										bean.setBranch(obj[1].toString());
										bean.setCluster(obj[2].toString());
										Double depotTotal = Double.parseDouble(obj[3].toString());
										bean.setSaleTotalQty(depotTotal);
										total += Double.parseDouble(obj[3].toString());
										if (Double.parseDouble(obj[3].toString()) > 0) {
											map.put(obj[0].toString() + ":" + obj[1].toString() + ":"
													+ obj[2].toString(), bean);
										}
									}

								}
							}
						}
						qry = "";
					}

				} else {
					res = res + " " + promoId[pi];
				}

				if (map == null || map.size() == 0) {
					res1 = res1 + promoId[pi] + ", ";
				}

				if (map != null && map.size() > 0) {
					Iterator<String> iterator2 = map.keySet().iterator();
					DecimalFormat df = new DecimalFormat("#.###");
					while (iterator2.hasNext()) {
						DisaggregationQtyBean dbean = map.get(iterator2.next());
						Double val = dbean.getSaleTotalQty();
						Double per = (val / total) * 100;
						totalPer += per;
						String f = String.valueOf(per);
						f = f.substring(f.indexOf(".") + 1);
						if (f.length() > 1) {
							f = f.substring(0, 2);
						}
						Integer p = Integer.parseInt(f);
						if (p >= 50) {
							Double qt1 = (double) qty;
							Double quantity = (qt1 / 100);
							Double qtt = (quantity * per);
							//Double qt = (double) Math.ceil(qtt);
							Double qt = (double) qtt;
							dbean.setQty(qt);
							dbean.setPercentage(per);
							list1.add(dbean);
							totalOfDepot += qt.intValue();
						} else {
							Double qt1 = (double) qty;
							Double quantity = (qt1 / 100);
							Double qtt = (quantity * per);
							//Integer qt = (int) Math.floor(qtt);
						//	Double qt = (double) Math.floor(qtt);
							Double qt = (double) qtt;
							dbean.setQty(qt);
							dbean.setPercentage(per);
							list1.add(dbean);
							totalOfDepot += qt.intValue();
						}
					}
					Query query = sessionFactory.getCurrentSession().createNativeQuery(
							"INSERT INTO TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL(PROMO_ID,BASEPACK,DEPOT,TOTAL_SALE,PERCENTAGE,QUANTITY,MOCS,ACTIVE,BRANCH,CLUSTER) VALUES(?0,?1,?2,?3,?4,?5,?6,1,?7,?8)");
					Query queryToCheck = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND BASEPACK=:basepack");

					Query QueryTogetDepotsForCl = sessionFactory.getCurrentSession().createNativeQuery(
							"select DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(C.DEPOT,':'),C.BRANCH),':'),C.CLUSTER) from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND B.BASEPACK = A.P1_BASEPACK  INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 AND C.BRANCH IN(:branch) AND C.CLUSTER IN(:cluster)");
					QueryTogetDepotsForCl.setString("promoId", PromoIdBasePack[0]);
					QueryTogetDepotsForCl.setParameterList("branch", branchList);
					QueryTogetDepotsForCl.setParameterList("cluster", clusterList);
					depotListWithoutDis = QueryTogetDepotsForCl.list();

					Query queryToDelete = sessionFactory.getCurrentSession().createNativeQuery(
							"DELETE FROM TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND BASEPACK=:basepack ");
					queryToCheck.setString("promoId", PromoIdBasePack[0]);
					queryToCheck.setString("basepack", basepack);
					//kiran - big data to int changes
					//Integer count = (Integer) queryToCheck.uniqueResult();
					Integer count = ((BigInteger)queryToCheck.uniqueResult()).intValue();
					if (count != null && count.intValue() > 0) {
						queryToDelete.setString("promoId", PromoIdBasePack[0]);
						queryToDelete.setString("basepack", basepack);
						queryToDelete.executeUpdate();
					}
					logger.info(list1);
					// check if depot is added and insert if added

					if (isDepotAdded) {
						query.setString(0, PromoIdBasePack[0]);
						query.setString(1, basepack);
						query.setString(2, addDepotBean.getDepot());
						query.setString(3, "0");
						query.setString(4, "0");
						query.setString(5, String.valueOf(addDepotBean.getDepotQty()));
						query.setString(6, mocsToStoreInDb);
						query.setString(7, addDepotBean.getBranch());
						query.setString(8, addDepotBean.getCluster());
						query.executeUpdate();
					}

					for (int i = 0; i < list1.size(); i++) {
						DisaggregationQtyBean bean = list1.get(i);
						query.setString(0, PromoIdBasePack[0]);
						query.setString(1, basepack);
						query.setString(2, bean.getDepot());
						// remove depot for which sale is there
						depotListWithoutDis.remove(bean.getDepot() + ":" + bean.getBranch() + ":" + bean.getCluster());
						//
						query.setString(3, String.valueOf(df.format(bean.getSaleTotalQty())));
						query.setString(4, String.valueOf(df.format(bean.getPercentage())));
						query.setString(5, String.valueOf(df.format(bean.getQty())));
						query.setString(6, mocsToStoreInDb);
						query.setString(7, bean.getBranch());
						query.setString(8, bean.getCluster());
						query.executeUpdate();
					}

					for (String dep : depotListWithoutDis) {
						String[] split = dep.split(":");
						query.setString(0, PromoIdBasePack[0]);
						query.setString(1, basepack);
						query.setString(2, split[0]);
						query.setString(3, "0");
						query.setString(4, "0");
						query.setString(5, "0");
						query.setString(6, mocsToStoreInDb);
						query.setString(7, split[1]);
						query.setString(8, split[2]);
						query.executeUpdate();
					}
				}
			}
			long endForTime = System.currentTimeMillis();
			logger.info("duration of for loop PromoDisaggregation: "+(endForTime-startTime));
			if (!res1.equals("")) {
				res1 = "Failed to disaggregate " + res1 + " promos. No sales data.";
			}
			if (!res.equals("")) {
				res = "Failed to disaggregate " + res + " promos. No depots.";
			}
			if (!res1.equals("") && res.equals("")) {
				res = res1;
			} else if (!res1.equals("")) {
				res = res + ", " + res1;
			}
			if (res.equals("")) {
				res = disaggregatePromosL2(promoId, mocs, userId);
			}
			if (res.equals("")) {
				long startTimeSave = System.currentTimeMillis();
				for (int i = 0; i < promoId.length; i++) {
					//createPromoDAOImpl.saveStatusInStatusTracker(promoId[0], 4, "", userId);
					String PromoIdBasePack[]= promoId[i].split(",");
					createPromoDAOImpl.saveStatusInStatusTracker(PromoIdBasePack[0], 4, "", userId,PromoIdBasePack[1]);
				}
				long endTimeSave = System.currentTimeMillis();
				logger.info("duration of PromoDisaggregation Save: "+(endTimeSave-startTimeSave));
			}
			long endTime = System.currentTimeMillis();
			logger.info("duration of PromoDisaggregation: "+(endTime-startTime));
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return e.getMessage();
		}
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String disaggregatePromosL2(String promoId[], String[] mocs, String userId) {
		String res = "";
		String res1 = "";
		String basepack = "";
		String customerChainL1 = "";
		double total = 0.0;
		String qry = "";
		String qryForDepot = "";
		//Integer qty = null;
		double qty = 0.0;
		Map<String, DisaggregationL2Bean> map = new ConcurrentHashMap<String, DisaggregationL2Bean>();
		List<DisaggregationL2Bean> list1 = new ArrayList<>();
		Integer totalOfDepot = 0;
		Double totalPer = 0.0;
		String mocsToStoreInDb = "";
		AddDepotBean addDepotBean = new AddDepotBean();
		boolean isDepotAdded = false;
		Set<String> yearSet = new HashSet<String>();
		boolean saveL2LevelDisaggregation = false;
		try {
			for (int pi = 0; pi < promoId.length; pi++) {
				totalOfDepot = 0;
				total = 0.0;
				totalPer = 0.0;
				map = new HashMap<>();
				list1 = new ArrayList<>();
				mocsToStoreInDb = "";
				String promoIdBasePack[]= promoId[pi].split(",");
				Query QueryTogetVolume = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT QUANTITY,P1_BASEPACK,CUSTOMER_CHAIN_L1 FROM TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId AND ACTIVE=1 AND P1_BASEPACK=:basepack ");
				QueryTogetVolume.setString("promoId", promoIdBasePack[0]);
				QueryTogetVolume.setString("basepack", promoIdBasePack[1]);
				Iterator iterator = QueryTogetVolume.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					qty = Double.parseDouble(obj[0].toString());
					basepack = obj[1].toString();
					customerChainL1 = obj[2].toString();
				}

				// select depot from add depot
				Query qryTogetDepotFromAddDepot = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT BRANCH,CLUSTER,DEPOT,QUANTITY FROM TBL_PROCO_ADD_DEPOT_TEMP WHERE PROMO_ID=:promoId AND USER_ID=:userId");
				qryTogetDepotFromAddDepot.setString("promoId", promoIdBasePack[0]);
				qryTogetDepotFromAddDepot.setString("userId", userId);
				Iterator getDepotFromAddDepot = qryTogetDepotFromAddDepot.list().iterator();
				while (getDepotFromAddDepot.hasNext()) {
					Object[] obj = (Object[]) getDepotFromAddDepot.next();
					isDepotAdded = true;
					addDepotBean.setBranch(obj[0].toString());
					addDepotBean.setCluster(obj[1].toString());
					addDepotBean.setDepot(obj[2].toString());
					addDepotBean.setDepotQty(Integer.parseInt(obj[3].toString()));
				}
				if (isDepotAdded) {
					qty = qty - addDepotBean.getDepotQty(); // subtract Added
															// depot qty from
															// main qty
				}

				Query QueryTogetL2 = sessionFactory.getCurrentSession().createNativeQuery(
						"select C.CUSTOMER_CHAIN_L2 from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND A.P1_BASEPACK = B.BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 GROUP BY C.CUSTOMER_CHAIN_L2");
				QueryTogetL2.setString("promoId", promoIdBasePack[0]);
				List<String> l2List = new ArrayList<String>();
				l2List = QueryTogetL2.list();

				Query QueryTogetBranches = sessionFactory.getCurrentSession().createNativeQuery(
						"select BRANCH from TBL_PROCO_PROMOTION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basepack GROUP BY BRANCH");
				QueryTogetBranches.setString("promoId", promoIdBasePack[0]);
				QueryTogetBranches.setString("basepack", promoIdBasePack[1]);
				List branchList = QueryTogetBranches.list();

				Query QueryTogetCLusters = sessionFactory.getCurrentSession().createNativeQuery(
						"select CLUSTER from TBL_PROCO_PROMOTION_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND ACTIVE=1 AND BASEPACK=:basepack GROUP BY CLUSTER");
				QueryTogetCLusters.setString("promoId", promoIdBasePack[0]);
				QueryTogetCLusters.setString("basepack", promoIdBasePack[1] );
				List clusterList = QueryTogetCLusters.list();

				Query QueryTogetDepots = sessionFactory.getCurrentSession().createNativeQuery(
						"select C.DEPOT from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND C.CUSTOMER_CHAIN_L2=:customerChainL2 AND A.ACTIVE=1 GROUP BY C.DEPOT");
				for (int i = 0; i < l2List.size(); i++) { // for every l2
					mocsToStoreInDb = "";
					List<String> listOfDepot = new ArrayList<String>();
					String customerChainL2 = l2List.get(i);
					QueryTogetDepots.setString("promoId", promoIdBasePack[0]);
					QueryTogetDepots.setString("customerChainL2", customerChainL2);
					listOfDepot = QueryTogetDepots.list();
					String year = "";
					for (int k = 0; k < mocs.length; k++) {
						String mocStr = mocs[k];
						if (k < mocs.length - 1) {
							mocsToStoreInDb = mocsToStoreInDb + mocStr + ",";
						} else {
							mocsToStoreInDb = mocsToStoreInDb + mocStr;
						}
						year = "20" + mocStr.substring(mocStr.indexOf("'") + 1);
						yearSet.add(year);
					}
					String year1 = "";
					Iterator<String> iterator3 = yearSet.iterator();
					while (iterator3.hasNext()) { // iterate year
						String year2 = iterator3.next();
						List<String> list12 = new ArrayList();
						for (int j = 0; j < mocs.length; j++) {
							String mocStr = mocs[j];
							String moc = mocStr.substring(4, mocStr.indexOf("'"));
							year1 = "20" + mocStr.substring(mocStr.indexOf('\'') + 1);
							if (year1.equals(year2)) {
								list12.add(moc);
							}
						}
						if (list12.size() == 1) {
							String moc = list12.get(0);
							qry += "SELECT CUSTOMER_CHAIN_L2,SUM(MOC" + moc + ")";
							qryForDepot += "SELECT A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE,SUM(MOC" + moc + ")";
						} else if (list12.size() > 1) {
							for (int j = 0; j < list12.size(); j++) {
								String moc = list12.get(j);
								if (j == 0) {
									qry += "SELECT CUSTOMER_CHAIN_L2,SUM(MOC" + moc;
									qryForDepot += "SELECT A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE,SUM(MOC" + moc;
								} else if (j < list12.size() - 1) {
									qry += "+MOC" + moc;
									qryForDepot += "+MOC" + moc;
								} else if (j == list12.size() - 1) {
									qry += "+MOC" + moc + ")";
									qryForDepot += "+MOC" + moc + ")";
								}
							}
						}
						qry += " FROM TBL_PROCO_SALES_MASTER WHERE YEAR=:year AND DEPOT IN(:depot) AND BASEPACK=:basepack AND "
								+ "CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_CHAIN_L2=:customerChainL2 AND BRANCH_CODE IN(:branch) AND CLUSTER_CODE IN(:cluster) GROUP BY CUSTOMER_CHAIN_L2";
						qryForDepot += " FROM TBL_PROCO_SALES_MASTER AS A WHERE A.YEAR=:year AND A.DEPOT IN(:depot) AND A.BASEPACK=:basepack "
								+ "AND A.CUSTOMER_CHAIN_L1=:customerChainL1 AND A.CUSTOMER_CHAIN_L2=:customerChainL2 AND A.BRANCH_CODE IN(:branch) AND A.CLUSTER_CODE IN(:cluster) GROUP BY A.DEPOT,A.BRANCH_CODE,A.CLUSTER_CODE";
						Query QueryTogetSales = sessionFactory.getCurrentSession().createNativeQuery(qry);
						QueryTogetSales.setParameterList("depot", listOfDepot);
						QueryTogetSales.setString("basepack", basepack);
						QueryTogetSales.setString("year", year2);
						QueryTogetSales.setString("customerChainL1", customerChainL1);
						QueryTogetSales.setString("customerChainL2", customerChainL2);
						QueryTogetSales.setParameterList("branch", branchList);
						QueryTogetSales.setParameterList("cluster", clusterList);
						Iterator iterator2 = QueryTogetSales.list().iterator();
						while (iterator2.hasNext()) {
							Object[] obj = (Object[]) iterator2.next();
							if (map.containsKey(obj[0].toString())) {
								if (obj[1] != null) {
									if (Double.parseDouble(obj[1].toString()) > 0) {
										DisaggregationL2Bean bean = map.get(obj[0].toString());
										Double custL2Total = Double.parseDouble(obj[1].toString());
										Double previousTotal = bean.getSaleTotalQty();
										bean.setSaleTotalQty(custL2Total + previousTotal);
										total += Double.parseDouble(obj[1].toString());
										ConcurrentHashMap<String, DisaggregationQtyBean> depotMap = (ConcurrentHashMap<String, DisaggregationQtyBean>) bean
												.getDepotMap();
										depotMap = getDepotLevelSalesDataForL2(qryForDepot, promoIdBasePack[0],
												customerChainL1, customerChainL2, basepack, year2, depotMap, branchList,
												clusterList);
										bean.setDepotMap(depotMap);
										map.put(obj[0].toString(), bean);
									}

								}
							} else {
								if (obj[1] != null) {
									if (Double.parseDouble(obj[1].toString()) > 0) {
										DisaggregationL2Bean bean = new DisaggregationL2Bean();
										Map<String, DisaggregationQtyBean> depotMap = new ConcurrentHashMap<String, DisaggregationQtyBean>();
										bean.setCustomerChainL2(obj[0].toString());
										Double custTotal = Double.parseDouble(obj[1].toString());
										bean.setSaleTotalQty(custTotal);
										total += Double.parseDouble(obj[1].toString());
										ConcurrentHashMap<String, DisaggregationQtyBean> depotLevelSalesDataForL2 = getDepotLevelSalesDataForL2(
												qryForDepot, promoIdBasePack[0], customerChainL1, customerChainL2, basepack,
												year2, (ConcurrentHashMap<String, DisaggregationQtyBean>) depotMap,
												branchList, clusterList);
										bean.setDepotMap(depotLevelSalesDataForL2);
										map.put(obj[0].toString(), bean);
									}

								}
							}
						}
						qry = "";
						qryForDepot = "";
					}
				}
				// calculate quantity
				if (map != null && map.size() > 0) {
					Iterator<String> iterator2 = map.keySet().iterator();
					while (iterator2.hasNext()) {
						DisaggregationL2Bean dbean = map.get(iterator2.next());
						Double val = dbean.getSaleTotalQty();
						Double per = (val / total) * 100;
						totalPer += per;
						String f = String.valueOf(per);
						f = f.substring(f.indexOf(".") + 1);
						if (f.length() > 1) {
							f = f.substring(0, 2);
						}
						Integer p = Integer.parseInt(f);
						if (p >= 50) {
							Double qt1 = (double) qty;
							Double quantity = (qt1 / 100);
							Double qtt = (quantity * per);
							//Double qt = (double) Math.ceil(qtt);
							Double qt = (double)qtt;
							dbean.setQty(qt);
							dbean.setPercentage(per);
							ConcurrentHashMap<String, DisaggregationQtyBean> depotMap = (ConcurrentHashMap<String, DisaggregationQtyBean>) dbean
									.getDepotMap();
							if (depotMap != null && depotMap.size() > 0) {
								Iterator<String> depotMapItr = depotMap.keySet().iterator();
								while (depotMapItr.hasNext()) {
									DisaggregationQtyBean bean = depotMap.get(depotMapItr.next());
									Double depotSale = bean.getSaleTotalQty();
									Double depotSalePer = (depotSale / val) * 100;
									Double depotQt = (qt * depotSalePer) / 100;
									bean.setPercentage(depotSalePer);
									bean.setQty((int) depotQt.intValue());
									depotMap.put(bean.getDepot(), bean);
								}
							}
							list1.add(dbean);
							totalOfDepot += qt.intValue();
						} else {
							Double qt1 = (double) qty;
							Double quantity = (qt1 / 100);
							Double qtt = (quantity * per);
							//Double qt = (double) Math.floor(qtt);
							Double qt = (double)qtt;
							dbean.setQty(qt);
							dbean.setPercentage(per);
							ConcurrentHashMap<String, DisaggregationQtyBean> depotMap = (ConcurrentHashMap<String, DisaggregationQtyBean>) dbean
									.getDepotMap();
							if (depotMap != null && depotMap.size() > 0) {
								Iterator<String> depotMapItr = depotMap.keySet().iterator();
								while (depotMapItr.hasNext()) {
									DisaggregationQtyBean bean = depotMap.get(depotMapItr.next());
									Double depotSale = bean.getSaleTotalQty();
									Double depotSalePer = (depotSale / val) * 100;
									Double depotQt = (qt * depotSalePer) / 100;
									bean.setPercentage(depotSalePer);
									bean.setQty((double) depotQt.doubleValue());
									depotMap.put(bean.getDepot(), bean);
								}
							}
							list1.add(dbean);
							totalOfDepot += qt.intValue();
						}
					}
				}
				logger.info(list1);
				// save L2 level in table
				saveL2LevelDisaggregation = saveL2LevelDisaggregation(promoIdBasePack[0], basepack, list1, mocsToStoreInDb,
						addDepotBean, isDepotAdded, userId, branchList, clusterList);

			}
			if (!res1.equals("")) {
				res1 = "Failed to disaggregate " + res1 + " promos. No sales data.";
			}
			if (!res.equals("")) {
				res = "Failed to disaggregate " + res + " promos. No depots.";
			}
			if (!res1.equals("") && res.equals("")) {
				res = res1;
			} else if (!res1.equals("")) {
				res = res + ", " + res1;
			}
			if (!saveL2LevelDisaggregation) {
				res = "Error occured while disaggregating promos.";
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return e.getMessage();
		}
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ConcurrentHashMap<String, DisaggregationQtyBean> getDepotLevelSalesDataForL2(String query, String promoId,
			String customerChainL1, String customerChainL2, String basepack, String year,
			ConcurrentHashMap<String, DisaggregationQtyBean> depotMap, List<String> branchList,
			List<String> clusterList) {
		List<String> listOfDepot = new ArrayList<String>();
		try {
			Query queryTogetDepots = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT C.DEPOT from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND A.P1_BASEPACK = B.BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND C.CUSTOMER_CHAIN_L2=:customerChainL2 AND A.ACTIVE=1");
			queryTogetDepots.setString("promoId", promoId);
			queryTogetDepots.setString("customerChainL2", customerChainL2);
			listOfDepot = queryTogetDepots.list();

			if (listOfDepot != null && listOfDepot.size() > 0) {
				Query queryTogetSaleOfDepots = sessionFactory.getCurrentSession().createNativeQuery(query);
				queryTogetSaleOfDepots.setString("year", year);
				queryTogetSaleOfDepots.setString("basepack", basepack);
				queryTogetSaleOfDepots.setString("customerChainL1", customerChainL1);
				queryTogetSaleOfDepots.setString("customerChainL2", customerChainL2);
				queryTogetSaleOfDepots.setParameterList("branch", branchList);
				queryTogetSaleOfDepots.setParameterList("cluster", clusterList);
				queryTogetSaleOfDepots.setParameterList("depot", listOfDepot);
				Iterator iterator = queryTogetSaleOfDepots.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					if (obj[1] != null) {
						if (depotMap.containsKey(obj[0].toString())) {
							DisaggregationQtyBean bean = depotMap.get(obj[0].toString());
							Double depotSale = bean.getSaleTotalQty();
							if (Double.parseDouble(obj[3].toString()) > 0) {
								depotSale += Double.parseDouble(obj[3].toString());
								bean.setSaleTotalQty(depotSale);
								bean.setBranch(obj[1].toString());
								bean.setCluster(obj[2].toString());
								depotMap.put(obj[0].toString(), bean);
							}

						} else {
							if (Double.parseDouble(obj[3].toString()) > 0) {
								DisaggregationQtyBean bean = new DisaggregationQtyBean();
								bean.setDepot(obj[0].toString());
								bean.setBranch(obj[1].toString());
								bean.setCluster(obj[2].toString());
								bean.setSaleTotalQty(Double.parseDouble(obj[3].toString()));
								depotMap.put(obj[0].toString(), bean);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return depotMap;
	}

	@SuppressWarnings("unchecked")
	private boolean saveL2LevelDisaggregation(String promoId, String basepack, List<DisaggregationL2Bean> listOfCustL2,
			String mocsToStoreInDb, AddDepotBean addDepotBean, boolean isDepotAdded, String userId,
			List<String> branchList, List<String> clusterList) {
		boolean res = true;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_PROMO_DISAGG_L2_LEVEL(PROMO_ID,BASEPACK,CUSTOMER_CHAIN_L2,TOTAL_SALE,PERCENTAGE,QUANTITY,MOCS,ACTIVE) VALUES(?0,?1,?2,?3,?4,?5,?6,1)");
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(1) FROM TBL_PROCO_PROMO_DISAGG_L2_LEVEL WHERE PROMO_ID=:promoId AND BASEPACK=:basepack ");

			Query queryToDelete = sessionFactory.getCurrentSession()
					.createNativeQuery("DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_LEVEL WHERE PROMO_ID=:promoId AND BASEPACK=:basepack ");
			queryToCheck.setString("promoId", promoId);
			queryToCheck.setString("basepack", basepack);
			//kiran - bigint to int changes
			//Integer count = (Integer) queryToCheck.uniqueResult();
			Integer count = ((BigInteger)queryToCheck.uniqueResult()).intValue();
			if (count != null && count.intValue() > 0) {
				queryToDelete.setString("promoId", promoId);
				queryToDelete.setString("basepack", basepack);
				queryToDelete.executeUpdate();
			}

			Query queryToDeleteL2DepotLevel = sessionFactory.getCurrentSession()
					.createNativeQuery("DELETE FROM TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL WHERE PROMO_ID=:promoId AND BASEPACK=:basepack ");
			queryToDeleteL2DepotLevel.setString("promoId", promoId);
			queryToDeleteL2DepotLevel.setString("basepack", basepack);
			queryToDeleteL2DepotLevel.executeUpdate();
			
			Query QueryTogetL2 = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT C.CUSTOMER_CHAIN_L2 from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID  AND B.BASEPACK = A.P1_BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1");
			QueryTogetL2.setString("promoId", promoId);
			List<String> l2List = new ArrayList<String>();
			l2List = QueryTogetL2.list();

			for (int i1 = 0; i1 < listOfCustL2.size(); i1++) {
				DisaggregationL2Bean bean = listOfCustL2.get(i1);
				query.setString(0, promoId);
				query.setString(1, basepack);
				query.setString(2, bean.getCustomerChainL2());
				l2List.remove(bean.getCustomerChainL2()); // remove existing l2
				query.setString(3, String.valueOf(bean.getSaleTotalQty()));
				query.setString(4, String.valueOf(bean.getPercentage()));
				query.setString(5, String.valueOf(bean.getQty()));
				query.setString(6, mocsToStoreInDb);
				query.executeUpdate();
			}

			Query QueryTogetDepotsForCl = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(C.DEPOT,':'),C.BRANCH),':'),C.CLUSTER) from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND B.BASEPACK = A.P1_BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 AND C.CUSTOMER_CHAIN_L2=:customerChainL2 AND C.BRANCH IN(:branch) AND C.CLUSTER IN(:cluster)");

			
			Query queryToInsertL2DepotLevel = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL(PROMO_ID,CUSTOMER_CHAIN_L2,DEPOT,TOTAL_SALE,PERCENTAGE,QUANTITY,ACTIVE,BRANCH,CLUSTER,BASEPACK) VALUES(?0,?1,?2,?3,?4,?5,1,?6,?7,?8)");  //Sarin - Added Parameters position

			for (String l2 : l2List) {
				query.setString(0, promoId);
				query.setString(1, basepack);
				query.setString(2, l2);
				query.setString(3, "0");
				query.setString(4, "0");
				query.setString(5, "0");
				query.setString(6, mocsToStoreInDb);
				query.executeUpdate();

				QueryTogetDepotsForCl.setString("customerChainL2", l2);
				QueryTogetDepotsForCl.setString("promoId", promoId);
				QueryTogetDepotsForCl.setParameterList("branch", branchList);
				QueryTogetDepotsForCl.setParameterList("cluster", clusterList);
				List<String> list = QueryTogetDepotsForCl.list();
				
				for (String depot : list) {
					String[] split = depot.split(":");
					queryToInsertL2DepotLevel.setString(0, promoId);
					queryToInsertL2DepotLevel.setString(1, l2);
					queryToInsertL2DepotLevel.setString(2, split[0]);
					queryToInsertL2DepotLevel.setString(3, "0");
					queryToInsertL2DepotLevel.setString(4, "0");
					queryToInsertL2DepotLevel.setString(5, "0");
					queryToInsertL2DepotLevel.setString(6, split[1]);
					queryToInsertL2DepotLevel.setString(7, split[2]);
					queryToInsertL2DepotLevel.setString(8, basepack);
					queryToInsertL2DepotLevel.executeUpdate();
				}

			}
			res = saveL2DepotLevelDisaggregation(promoId, listOfCustL2, addDepotBean, isDepotAdded, userId, branchList,
					clusterList, basepack);
		} catch (Exception e) {
			logger.debug("Exception:", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private boolean saveL2DepotLevelDisaggregation(String promoId, List<DisaggregationL2Bean> listOfCustL2,
			AddDepotBean addDepotBean, boolean isDepotAdded, String userId, List<String> branchList,
			List<String> clusterList, String basepack) {
		boolean res = true;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL(PROMO_ID,CUSTOMER_CHAIN_L2,DEPOT,TOTAL_SALE,PERCENTAGE,QUANTITY,ACTIVE,BRANCH,CLUSTER,BASEPACK) VALUES(?0,?1,?2,?3,?4,?5,1,?6,?7,?8)");

			Query QueryTogetDepotsForCl = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(C.DEPOT,':'),C.BRANCH),':'),C.CLUSTER) from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID AND B.BASEPACK = A.P1_BASEPACK INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 AND C.CUSTOMER_CHAIN_L2=:customerChainL2 AND C.BRANCH IN(:branch) AND C.CLUSTER IN(:cluster)");

			for (int i1 = 0; i1 < listOfCustL2.size(); i1++) {
				DisaggregationL2Bean bean = listOfCustL2.get(i1);
				Map<String, DisaggregationQtyBean> depotMap = bean.getDepotMap();
				Iterator<String> iterator = depotMap.keySet().iterator();
				if (isDepotAdded) {
					double percentage = bean.getPercentage();
					double addDepotPer = (percentage / 100) * addDepotBean.getDepotQty();
					int addDepotQty = (int) addDepotPer;
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, addDepotBean.getDepot());
					query.setString(3, "0");
					query.setString(4, "0");
					query.setString(5, String.valueOf(addDepotQty));
					query.setString(6, addDepotBean.getBranch());
					query.setString(7, addDepotBean.getCluster());
					query.setString(8, basepack);
					query.executeUpdate();
				}

				QueryTogetDepotsForCl.setString("customerChainL2", bean.getCustomerChainL2());
				QueryTogetDepotsForCl.setString("promoId", promoId);
				QueryTogetDepotsForCl.setParameterList("branch", branchList);
				QueryTogetDepotsForCl.setParameterList("cluster", clusterList);
				List<String> list = QueryTogetDepotsForCl.list();

				while (iterator.hasNext()) {
					DisaggregationQtyBean depotBean = depotMap.get(iterator.next());
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, depotBean.getDepot());
					list.remove(depotBean.getDepot() + ":" + depotBean.getBranch() + ":" + depotBean.getCluster()); // remove
																													// depot
																													// from
																													// list
					query.setString(3, String.valueOf(depotBean.getSaleTotalQty()));
					query.setString(4, String.valueOf(depotBean.getPercentage()));
					query.setString(5, String.valueOf(depotBean.getQty()));
					query.setString(6, depotBean.getBranch());
					query.setString(7, depotBean.getCluster());
					query.setString(8, basepack);
					query.executeUpdate();
				}

				for (String depot : list) {
					String[] split = depot.split(":");
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, split[0]);
					query.setString(3, "0");
					query.setString(4, "0");
					query.setString(5, "0");
					query.setString(6, split[1]);
					query.setString(7, split[2]);
					query.setString(8, basepack);
					query.executeUpdate();
				}
			}
			Query queryToDeleteFromAddDepotTemp = sessionFactory.getCurrentSession()
					.createNativeQuery("DELETE FROM TBL_PROCO_ADD_DEPOT_TEMP WHERE USER_ID=:userId");
			queryToDeleteFromAddDepotTemp.setString("userId", userId);
			queryToDeleteFromAddDepotTemp.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception:", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private boolean saveL2DepotLevelDisaggregation(String promoId, List<DisaggregationL2Bean> listOfCustL2,
			AddDepotBean addDepotBean, boolean isDepotAdded, String userId, List<String> branchList,
			List<String> clusterList) {
		boolean res = true;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_PROMO_DISAGG_L2_DEPOT_LEVEL(PROMO_ID,CUSTOMER_CHAIN_L2,DEPOT,TOTAL_SALE,PERCENTAGE,QUANTITY,ACTIVE,BRANCH,CLUSTER) VALUES(?0,?1,?2,?3,?4,?5,1,?6,?7)");  //Sarin - Added Parameters position

			Query QueryTogetDepotsForCl = sessionFactory.getCurrentSession().createNativeQuery(
					"select DISTINCT CONCAT(CONCAT(CONCAT(CONCAT(C.DEPOT,':'),C.BRANCH),':'),C.CLUSTER) from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS B INNER JOIN TBL_PROCO_PROMOTION_MASTER AS A ON A.PROMO_ID = B.PROMO_ID INNER JOIN TBL_PROCO_CUSTOMER_MASTER AS C ON A.CUSTOMER_CHAIN_L1 = C.CUSTOMER_CHAIN_L1 AND B.DEPOT = C.DEPOT WHERE A.PROMO_ID=:promoId AND A.ACTIVE=1 AND C.CUSTOMER_CHAIN_L2=:customerChainL2 AND C.BRANCH IN(:branch) AND C.CLUSTER IN(:cluster)");

			for (int i1 = 0; i1 < listOfCustL2.size(); i1++) {
				DisaggregationL2Bean bean = listOfCustL2.get(i1);
				Map<String, DisaggregationQtyBean> depotMap = bean.getDepotMap();
				Iterator<String> iterator = depotMap.keySet().iterator();
				if (isDepotAdded) {
					double percentage = bean.getPercentage();
					double addDepotPer = (percentage / 100) * addDepotBean.getDepotQty();
					int addDepotQty = (int) addDepotPer;
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, addDepotBean.getDepot());
					query.setString(3, "0");
					query.setString(4, "0");
					query.setString(5, String.valueOf(addDepotQty));
					query.setString(6, addDepotBean.getBranch());
					query.setString(7, addDepotBean.getCluster());
					query.executeUpdate();
				}

				QueryTogetDepotsForCl.setString("customerChainL2", bean.getCustomerChainL2());
				QueryTogetDepotsForCl.setString("promoId", promoId);
				QueryTogetDepotsForCl.setParameterList("branch", branchList);
				QueryTogetDepotsForCl.setParameterList("cluster", clusterList);
				List<String> list = QueryTogetDepotsForCl.list();

				while (iterator.hasNext()) {
					DisaggregationQtyBean depotBean = depotMap.get(iterator.next());
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, depotBean.getDepot());
					list.remove(depotBean.getDepot() + ":" + depotBean.getBranch() + ":" + depotBean.getCluster()); // remove
																													// depot
																													// from
																													// list
					query.setString(3, String.valueOf(depotBean.getSaleTotalQty()));
					query.setString(4, String.valueOf(depotBean.getPercentage()));
					query.setString(5, String.valueOf(depotBean.getQty()));
					query.setString(6, depotBean.getBranch());
					query.setString(7, depotBean.getCluster());
					query.executeUpdate();
				}

				for (String depot : list) {
					String[] split = depot.split(":");
					query.setString(0, promoId);
					query.setString(1, bean.getCustomerChainL2());
					query.setString(2, split[0]);
					query.setString(3, "0");
					query.setString(4, "0");
					query.setString(5, "0");
					query.setString(6, split[1]);
					query.setString(7, split[2]);
					query.executeUpdate();
				}
			}
			Query queryToDeleteFromAddDepotTemp = sessionFactory.getCurrentSession()
					.createNativeQuery("DELETE FROM TBL_PROCO_ADD_DEPOT_TEMP WHERE USER_ID=:userId");
			queryToDeleteFromAddDepotTemp.setString("userId", userId);
			queryToDeleteFromAddDepotTemp.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception:", e);
			return false;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDepotForAddDepot(String promoId, String branch, String cluster) {
		List<String> depotListOfPromo = new ArrayList<>();
		List<String> depotList = new ArrayList<>();
		try {
			String qryTogeToGetPromoDepot = "select DISTINCT A.DEPOT from TBL_PROCO_PROMOTION_DEPOT_LEVEL AS A INNER JOIN TBL_PROCO_PROMOTION_MASTER AS B ON A.PROMO_ID=B.PROMO_ID WHERE A.PROMO_ID=:promoId AND B.ACTIVE=1";
			String qryTogeToGetPromoCcL1 = "select CUSTOMER_CHAIN_L1 from TBL_PROCO_PROMOTION_MASTER WHERE PROMO_ID=:promoId AND ACTIVE=1";
			String qryTogetAllDepots = "select DISTINCT DEPOT FROM TBL_PROCO_CUSTOMER_MASTER WHERE DEPOT NOT IN(:depot) AND CUSTOMER_CHAIN_L1=:ccl1 AND BRANCH_CODE=:branchCode AND CLUSTER_CODE=:clusterCode";
			Query queryToGetAllDepot = sessionFactory.getCurrentSession().createNativeQuery(qryTogetAllDepots);
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qryTogeToGetPromoDepot);
			String branchCode = branch.substring(0, branch.indexOf(":")).trim();
			String clusterCode = cluster.substring(0, cluster.indexOf(":")).trim();
			query.setString("promoId", promoId);
			Query queryToGetCcL1 = sessionFactory.getCurrentSession().createNativeQuery(qryTogeToGetPromoCcL1);
			queryToGetCcL1.setString("promoId", promoId);
			String customerChainL1 = (String) queryToGetCcL1.uniqueResult();
			depotListOfPromo = query.list();
			if (depotListOfPromo.size() > 0) {
				queryToGetAllDepot.setParameterList("depot", depotListOfPromo);
				queryToGetAllDepot.setString("ccl1", customerChainL1);
				queryToGetAllDepot.setString("branchCode", branchCode);
				queryToGetAllDepot.setString("clusterCode", clusterCode);
				depotList = queryToGetAllDepot.list();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return depotList;
		}
		return depotList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBranchForAddDepot() {
		List<String> branchList = new ArrayList<>();
		try {
			String qryTogetAllBranches = "select DISTINCT CONCAT(CONCAT(BRANCH_CODE,':'),BRANCH) FROM TBL_PROCO_CUSTOMER_MASTER";
			Query queryToGetAllBranches = sessionFactory.getCurrentSession().createNativeQuery(qryTogetAllBranches);
			branchList = queryToGetAllBranches.list();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return branchList;
		}
		return branchList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getClusterForAddDepot(String branch) {
		List<String> clusterList = new ArrayList<>();
		try {
			if (branch != null && !branch.equals("")) {
				String branchCode = branch.substring(0, branch.indexOf(":")).trim();
				String qryTogetClusters = "select DISTINCT CONCAT(CONCAT(CLUSTER_CODE,':'),CLUSTER) FROM TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH_CODE=:branchCode";
				Query queryToGetClusters = sessionFactory.getCurrentSession().createNativeQuery(qryTogetClusters);
				queryToGetClusters.setString("branchCode", branchCode);
				clusterList = queryToGetClusters.list();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return clusterList;
		}
		return clusterList;
	}

	@Override
	public String saveDepotForAddDepot(String promoId, String branch, String cluster, String depot, int quantity,
			String userId) {
		String res = "";
		try {
			Query queryToDelete = sessionFactory.getCurrentSession()
					.createNativeQuery("DELETE FROM TBL_PROCO_ADD_DEPOT_TEMP WHERE USER_ID=:userId");
			queryToDelete.setString("userId", userId);
			queryToDelete.executeUpdate();
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"INSERT INTO TBL_PROCO_ADD_DEPOT_TEMP(PROMO_ID,BRANCH,CLUSTER,DEPOT,QUANTITY,USER_ID) VALUES(?0,?1,?2,?3,?4,?5)");  //Sarin - Added Parameters position
			query.setString(0, promoId);
			query.setString(1, branch.substring(branch.indexOf(":") + 1).trim());
			query.setString(2, cluster.substring(cluster.indexOf(":") + 1).trim());
			query.setString(3, depot);
			query.setInteger(4, quantity);
			query.setString(5, userId);
			int executeUpdate = query.executeUpdate();
			if (executeUpdate > 0) {
				res = "SUCCESS";
			} else {
				res = "ERROR";
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			return "ERROR";
		}
		return res;
	}
	
	// Added by Harsha for Disaggregation by DP
	
	public String updateKamsubmitStatus() {
		String res = "";
		try {
			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(
					" Update TBL_PROCO_PROMOTION_MASTER A INNER JOIN TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL B " + 
					" ON A.PROMO_ID = B.PROMO_ID AND A.P1_BASEPACK = B.BASEPACK set A.STATUS = 37 WHERE B.SUBMIT_KAM_STATUS IS NULL;");  //Added by harsha
			int executeUpdate1 = query1.executeUpdate();
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					" update TBL_PROCO_PROMOTION_DISAGGREGATION_DEPOT_LEVEL set SUBMIT_KAM_STATUS = 1 where SUBMIT_KAM_STATUS IS NULL; " );  //Added by harsha
			int executeUpdate2 = query2.executeUpdate();
			if (executeUpdate1 > 0 && executeUpdate2>0) {
				res = "";
			} else {
				res = "ERROR";
			}
		} catch (Exception e) {
			logger.debug("Exception:", e);
			return "ERROR";
		}
		return res;
	}
	
	
	
}
