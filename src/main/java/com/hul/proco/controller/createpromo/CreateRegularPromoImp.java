package com.hul.proco.controller.createpromo;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

@Repository
public class CreateRegularPromoImp implements CreatePromoRegular {

	private Logger logger = Logger.getLogger(CreatePromoRegular.class);

	private String error_msg = "";
	private int flag = 0;
	private int globle_flag = 0;

	@Autowired
	private SessionFactory sessionFactory;

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2"
			+ "(CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,ERROR_MSG,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY)"
			+ "VALUES"
			+ "(?0, ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15,?16,?17,?18,?19,?20,?21)";

	private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,PROMO_ID,PID,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY)"
			+ " VALUES (?0,?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16,?17,?18,?19,?20,?21,?22)";

	private static String Pid = "SELECT (CASE WHEN MAX(PID) IS NULL THEN '000001' ELSE LPAD(CAST(MAX(CAST(PID AS UNSIGNED)) + 1 AS CHAR),6,0) END) AS PID FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC=?0 ";

	public String createPromotion(CreateBeanRegular[] beans, String uid, String template) {
		// TODO Auto-generated method stub

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", uid);
		queryToDelete.executeUpdate();

		Query query = (Query) sessionFactory.getCurrentSession()
				.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
		if (!template.equalsIgnoreCase("CR")) {
			for (CreateBeanRegular bean : beans) {
				query.setString(0, bean.getChannel());
				query.setString(1, bean.getMoc());
				query.setString(2, bean.getSecondary_channel());
				query.setString(3, bean.getPpm_account());
				query.setString(4, bean.getPromo_time_period());
				query.setString(5, bean.getAb_creation());
				query.setString(6, bean.getBasepack_code());
				query.setString(7, bean.getBaseback_desc());
				query.setString(8, bean.getC_pack_code());
				query.setString(9, bean.getOffer_desc());
				query.setString(10, bean.getOfr_type());
				query.setString(11, bean.getOffer_mod());
				// query.setString(12, bean.getPrice_off());
				query.setString(13, bean.getBudget());
				query.setString(14, bean.getBranch());
				query.setString(15, bean.getCluster());
				query.setString(16, uid);
				query.setString(20, template);

				if (template.equalsIgnoreCase("new")) {
					query.setString(21, bean.getQuantity());
				} else if (template.equalsIgnoreCase("regular")) {
					query.setString(21, "");
				}

				if (!isChannelAvail(bean.getChannel())) {
					error_msg = error_msg + "Invalid Channel";
					flag = 1;
				}
				if (!isPPMValid(bean.getPpm_account()) || bean.getPpm_account().contains(",")) {

					if (flag == 1)
						error_msg = error_msg + ",Invalid Account";
					else {
						error_msg = error_msg + "Invalid Account";
						flag = 1;
					}
				}

				if (!isABCreationValid(bean.getAb_creation())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid AB creation";
					else {
						error_msg = error_msg + "Invalid AB creation";
						flag = 1;
					}
				}

				if (!isBasepackValid(bean.getBasepack_code())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Parent basepack";
					else {
						error_msg = error_msg + "Invalid parent baseback";
						flag = 1;
					}
				}

				if (!isBasepackValid(bean.getC_pack_code())) {
					if (!bean.getC_pack_code().isEmpty()) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid child basepack";
						else {
							error_msg = error_msg + "Invalid child baseback";
							flag = 1;
						}
					}
				}

				if (!isOfferTypeValid(bean.getOfr_type())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Offer Type";
					else {
						error_msg = error_msg + "Invalid Offer Type";
						flag = 1;
					}
				}

				if (!isOfferModalityValid(bean.getOffer_mod())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Offer Modality";
					else {
						error_msg = error_msg + "Invalid Offer Modality";
						flag = 1;
					}
				}

				if (!isValidBranch(bean.getBranch())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Branch";
					else {
						error_msg = error_msg + "Invalid Branch";
						flag = 1;
					}
				}

				if (!isValidSec(bean.getSecondary_channel()) || bean.getSecondary_channel().contains(",")) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Secondary Channel";
					else {
						error_msg = error_msg + "Invalid Secondary Channel";
						flag = 1;
					}
				}

				if (!isValidCluster(bean.getCluster()) || bean.getCluster().contains(",")) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Cluster";
					else {
						error_msg = error_msg + "Invalid Cluster";
						flag = 1;
					}
				}

				if (!bean.getMoc().isEmpty()) {
					if (!isMocInFormat(bean.getMoc())) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid MOC format";
						else {
							error_msg = error_msg + "Invalid MOC format";
							flag = 1;
						}
					}
				} else {
					error_msg = error_msg + "Error MOC";
					flag = 1;
				}

				if (bean.getOffer_desc().isEmpty() || bean.getOffer_desc().equals("")) {
					if (flag == 1) {
						error_msg = error_msg + ",Offer description should not be empty";
						flag = 1;
					} else {
						error_msg = error_msg + "Offer description should not be empty";
						flag = 1;
					}

				}

				String new_moc = bean.getMoc();

				if (bean.getPromo_time_period().isEmpty() // || bean.getPromo_time_period().isBlank()
						|| bean.getPromo_time_period() == "") {
					if (!isMocInFormat(new_moc) || !isPPMValid(bean.getPpm_account())
							|| bean.getPpm_account().contains(",")) {
						query.setString(18, ""); // start date
						query.setString(19, ""); // end date
					} else {
						List<Object[]> se_date = getStartEndDate(
								new_moc.substring(4, 6).concat(new_moc.substring(0, 4)), bean.getPpm_account());
						if (!se_date.isEmpty()) {
							query.setString(18, se_date.get(0)[0].toString()); // start date
							query.setString(19, se_date.get(0)[1].toString()); // end date
						} else {
							if (flag == 1) {
								error_msg = error_msg + ",Can not obtain start/end date";
								flag = 1;
							}

							query.setString(18, ""); // start date
							query.setString(19, ""); // end date
							flag = 1;
						}
					}

				} else {

					try {

						if (isPromoTimeisValid(bean.getPromo_time_period())) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
							String new_date = sdf2.format(sdf.parse(bean.getPromo_time_period().replace("/", "-")));
							if (!isMocInFormat(new_moc) || !isPPMValid(bean.getPpm_account())
									|| bean.getPpm_account().contains(",")) {
								query.setString(18, "");
								query.setString(19, "");
								if (flag == 1) {
									error_msg = error_msg + ",Can't obtain Start date";
								} else {
									error_msg = error_msg + "Can't obtain Start date";
								}
							} else {
								String startDate = getStartDate(new_moc.substring(4, 6).concat(new_moc.substring(0, 4)),
										bean.getPpm_account(), new_date);
								if (startDate.isEmpty() || startDate.equals("")) {
									error_msg = error_msg + "Invalid promo time period";
									query.setString(18, "");
									query.setString(19, "");
								} else {

									SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy");
									SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy/MM/dd");
									query.setString(18, sdf_1.format(sdf_2.parse(startDate.replace("-", "/"))));
									query.setString(19, bean.getPromo_time_period());
								}
							}
						} else {
							if (flag == 1) {
								error_msg = error_msg + ",Invalid promo time period";
							} else {
								error_msg = error_msg + "Invalid promo time period";
							}
							flag = 1;
							query.setString(18, "");
							query.setString(19, "");
						}
					} catch (Exception e) {
						logger.debug("Exception: ", e);
					}

				}

				String price_off = bean.getPrice_off();

				if (bean.getOfr_type().equalsIgnoreCase("STPR")
						|| bean.getOfr_type().equalsIgnoreCase("STPR Liquidation")) {
					if (price_off.isEmpty()) {
						if (flag == 1) {
							error_msg = error_msg + ",Invalid Price Off";
							flag = 1;
						} else {
							error_msg = error_msg + "Invalid Price Off";
							flag = 1;
						}

						query.setString(12, "");

					} else {
						if (price_off.endsWith("%") && isStringNumber(price_off.split("%")[0])) {
							query.setString(12, bean.getPrice_off());
						} else if (isStringNumber(price_off)) {
							query.setString(12, bean.getPrice_off());

						} else {
							if (flag == 1) {
								error_msg = error_msg + ",Price off invalid for STPR/STPR Liquidation";
								flag = 1;
							} else {
								error_msg = error_msg + " Price off invalid for STPR/STPR Liquidation";
								flag = 1;
							}
							query.setString(12, "");
						}

					}

				} else {
					query.setString(12, bean.getPrice_off());
					if (bean.getBudget().isEmpty() || !isStringNumber(bean.getBudget())) {
						if (flag == 1) {
							error_msg = error_msg + ",Empty Budget/not number";
							flag = 1;
						}else
						{
						error_msg = error_msg + "Empty Budget/not number";
						flag = 1;
						}
					}

				}

				if (flag == 1)
					globle_flag = 1;

				query.setString(17, error_msg);
				query.executeUpdate();
				error_msg = "";
				flag = 0;
			}

		}

		if (globle_flag == 0) {

			saveTomainTable(beans, uid, template);
			globle_flag = 0;
			return "EXCEL_UPLOADED";

		} else {

			globle_flag = 0;
			return "EXCEL_NOT_UPLOADED";
		}

	}

	private String getStartDate(String concat, String ppm_account, String promo_time_period) {
		String start_date_c = "SELECT count(START_DATE) FROM TBL_VAT_MOC_GROUP_ACCOUNT_MASTER A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND" + " '" + promo_time_period
				+ "' BETWEEN START_DATE AND END_DATE AND B.MOC='" + concat + "' AND A.ACCOUNT_NAME='" + ppm_account
				+ "'";

		String start_date_1 = "SELECT START_DATE FROM TBL_VAT_MOC_GROUP_ACCOUNT_MASTER A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND" + " '" + promo_time_period
				+ "' BETWEEN START_DATE AND END_DATE AND B.MOC='" + concat + "' AND A.ACCOUNT_NAME='" + ppm_account
				+ "'";

		int startdate = excuteValidationQuery(start_date_c);
		if (startdate > 0) {
			return sessionFactory.getCurrentSession().createNativeQuery(start_date_1).uniqueResult().toString();
		}
		return "";
	}

	private boolean isStringNumber(String string) {

		if (string.matches("-?\\d+(\\.\\d+)?")) {
			return true;
		} else {
			return false;
		}

	}

	private boolean isValidSec(String secondary_channel) {
		String ppm_qury = "select count(1) from TBL_PROCO_CUSTOMER_MASTER where CUSTOMER_CHAIN_L1='" + secondary_channel
				+ "' AND ACTIVE=1";

		if (excuteValidationQuery(ppm_qury) > 0)
			return true;
		else
			return false;

	}

	private List<Object[]> getStartEndDate(String moc, String ppm_account) {
		String se_query = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_GROUP_ACCOUNT_MASTER A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND A.ACCOUNT_NAME='" + ppm_account
				+ "' AND MOC='" + moc + "'";
		return sessionFactory.getCurrentSession().createNativeQuery(se_query).list();
	}

	private void saveTomainTable(CreateBeanRegular[] beans, String uid, String template) {

		Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROMO_TABLE);

		for (CreateBeanRegular bean : beans) {
			query.setString(0, bean.getChannel());
			query.setString(1, bean.getMoc());
			query.setString(2, bean.getSecondary_channel());
			query.setString(3, bean.getPpm_account());
			query.setString(4, bean.getPromo_time_period());
			query.setString(5, bean.getAb_creation());
			query.setString(6, bean.getBasepack_code());
			query.setString(7, bean.getBaseback_desc());
			query.setString(8, bean.getC_pack_code());
			query.setString(9, bean.getOffer_desc());
			query.setString(10, bean.getOfr_type());
			query.setString(11, bean.getOffer_mod());
			query.setString(12, bean.getPrice_off());
			query.setString(13, bean.getBudget());
			query.setString(14, bean.getBranch());
			query.setString(15, bean.getCluster());
			query.setString(16, uid);
			query.setString(21, template);

			if (template.equalsIgnoreCase("new")) {
				query.setString(22, bean.getQuantity());
			} else if (template.equalsIgnoreCase("regular")) {
				query.setString(22, "");
			}

			List<Object[]> promo_list = getPromoId(bean.getMoc(), bean.getPpm_account(), bean.getOffer_desc());

			if (promo_list == null || promo_list.size() == 0) {
				query.setString(17, createNewPromoId(bean.getMoc()));
				query.setString(18, getPID(bean.getMoc()));
			} else {
				for (int i = 0; i < promo_list.size(); i++) {
					query.setString(17, promo_list.get(i)[0].toString());
					query.setString(18, promo_list.get(i)[1].toString()); // getting same PID
				}
			}

			if (bean.getPromo_time_period().isEmpty() // || bean.getPromo_time_period().isBlank()
					|| bean.getPromo_time_period() == "") {

				String new_moc = bean.getMoc();

				List<Object[]> se_date = getStartEndDate(new_moc.substring(4, 6).concat(new_moc.substring(0, 4)),
						bean.getPpm_account());

				query.setString(19, se_date.get(0)[0].toString()); // start date
				query.setString(20, se_date.get(0)[1].toString()); // end date

			} else {

				try {

					if (isPromoTimeisValid(bean.getPromo_time_period())) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
						String new_date = sdf2.format(sdf.parse(bean.getPromo_time_period().replace("/", "-")));
						String startDate = getStartDate(
								bean.getMoc().substring(4, 6).concat(bean.getMoc().substring(0, 4)),
								bean.getPpm_account(), new_date);
						if (startDate.isEmpty() || startDate.equals("")) {
							query.setString(19, "");
							query.setString(20, "");
						}

						SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy/MM/dd");
						query.setString(19, sdf_1.format(sdf_2.parse(startDate.replace("-", "/"))));
						query.setString(20, bean.getPromo_time_period());
					} else {
						query.setString(19, "");
						query.setString(20, "");
					}

				} catch (Exception e) {
					logger.debug("Exception: ", e);

				}

			}

			query.executeUpdate();
		}

	}

	private String createNewPromoId(String moc) {

		return "PID_" + moc + "_" + getPID(moc);
	}

	private String getPID(String moc) {
		String pid = sessionFactory.getCurrentSession().createNativeQuery(Pid).setString(flag, moc).uniqueResult()
				.toString();

		return pid;
	}

	private List<Object[]> getPromoId(String moc, String ppm_account, String offer_desc) {

		String checkPromoID = "select count(PROMO_ID) from TBL_PROCO_PROMOTION_MASTER_V2 where moc='" + moc
				+ "' AND CUSTOMER_CHAIN_L2='" + ppm_account + "' AND OFFER_DESC='" + offer_desc + "'";
		List<Object[]> list = null;
		if (excuteValidationQuery(checkPromoID) == 0) {
			return list;
		} else {
			String promo_id = "SELECT DISTINCT PROMO_ID,PID from TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC='" + moc
					+ "' AND CUSTOMER_CHAIN_L2='" + ppm_account + "' AND OFFER_DESC='" + offer_desc + "'";
			list = sessionFactory.getCurrentSession().createNativeQuery(promo_id).list();
			return list;
		}

	}

	private boolean isMocInFormat(String moc) {
		Date date = null;
		String format = "yyyyMM";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(moc);
			if (!moc.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	private boolean isPromoTimeisValid(String promotime) {
		Date date = null;
		String format = "dd/MM/yyyy";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(promotime);
			if (!promotime.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	private boolean isValidCluster(String cluster) {
		String cluster_query = "select count(1) from TBL_PROCO_CUSTOMER_MASTER where CLUSTER='" + cluster
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(cluster_query) > 0) {
			return true;
		}
		return false;

	}

	private boolean isValidBranch(String branch) {
		String branch_query = "SELECT COUNT(1) from TBL_PROCO_CUSTOMER_MASTER WHERE BRANCH='" + branch
				+ "' AND ACTIVE=1";
		if (excuteValidationQuery(branch_query) > 0) {
			return true;
		}
		return false;

	}

	private boolean isOfferModalityValid(String offer_mod) {
		String mod_query = "SELECT COUNT(1) from TBL_PROCO_OFFER_MODALITY_MASTER WHERE MODALITY_NAME='" + offer_mod
				+ "' AND ACTIVE=1";
		if (excuteValidationQuery(mod_query) == 1) {
			return true;
		}
		return false;
	}

	private boolean isOfferTypeValid(String ofr_type) {
		String str_query = "SELECT COUNT(1) from TBL_PROCO_OFFER_TYPE_V2 WHERE OFFER_TYPE='" + ofr_type
				+ "' AND ACTIVE=1";
		if (excuteValidationQuery(str_query) == 1) {
			return true;
		}
		return false;
	}

	private boolean isBasepackValid(String basepack_code) {
		String basepack = "SELECT COUNT(1) from TBL_PROCO_PRODUCT_MASTER WHERE BASEPACK='" + basepack_code
				+ "' AND ACTIVE=1";
		if (excuteValidationQuery(basepack) == 1) {
			return true;
		}
		return false;
	}

	private boolean isABCreationValid(String ab_creation) {
		String ab_query = "SELECT COUNT(1) from  TBL_PROCO_AB_CREATION_MASTER where AB_CREATION_NAME='" + ab_creation
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(ab_query) == 1)
			return true;
		else
			return false;

	}

	private boolean isPPMValid(String ppm_account) {
		String ppm_qury = "select count(1) from TBL_PROCO_CUSTOMER_MASTER where CUSTOMER_CHAIN_L2='" + ppm_account
				+ "' AND ACTIVE=1";
		if (excuteValidationQuery(ppm_qury) > 0)
			return true;
		else if (excuteValidationQuery(ppm_qury) == 0)
			return false;
		else
			return false;

	}

	private boolean isChannelAvail(String channel) {

		if (excuteValidationQuery(
				"select count(1) from TBL_PROCO_CHANNEL_MASTER where CHANNEL_NAME='" + channel + "' AND ACTIVE=1") == 1)
			return true;
		else
			return false;
	}

	private int excuteValidationQuery(String query) {
		Query inner_query = (Query) sessionFactory.getCurrentSession().createNativeQuery(query);
		Integer recCount = ((BigInteger) inner_query.uniqueResult()).intValue();
		return recCount;
	}

	// Added by Kavitha D for download promo regular template starts-SPRINT-9

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, List<List<String>>> getMastersForRegularTemplate() {
		Map<String, List<List<String>>> downloadDataMap = new HashMap<>();
		List<List<String>> clusterList = new ArrayList<>();
		List<List<String>> customerList = new ArrayList<>();
		List<List<String>> abcreationList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<List<String>> offertypeList = new ArrayList<>();
		List<List<String>> channelList = new ArrayList<>();


		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();
		
		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");


			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";

			
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

			query = sessionFactory.getCurrentSession().createNativeQuery(abcreationQry);

			abcreationList.add(abcreationHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				abcreationList.add(dataObj);
			}

			downloadDataMap.put("AB CREATION", abcreationList);

			query = sessionFactory.getCurrentSession().createNativeQuery(offertypeQry);

			offertypeList.add(offertypeHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				offertypeList.add(dataObj);
			}

			downloadDataMap.put("OFFER TYPE", offertypeList);

			query = sessionFactory.getCurrentSession().createNativeQuery(modalityQry);

			modalityList.add(modalityHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				modalityList.add(dataObj);
			}

			downloadDataMap.put("MODALITY", modalityList);
			
			query = sessionFactory.getCurrentSession().createNativeQuery(channelQry);

			channelList.add(channelHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				channelList.add(dataObj);
			}

			downloadDataMap.put("CHANNEL", channelList);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap; // Added by Kavitha D for download promo regular template ends-SPRINT-9

	}

	// Added by Kavitha D for download promo new template starts-SPRINT-9

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, List<List<String>>> getMastersForNewTemplate() {
		Map<String, List<List<String>>> downloadDataMap = new HashMap<>();
		List<List<String>> clusterList = new ArrayList<>();
		List<List<String>> customerList = new ArrayList<>();
		List<List<String>> abcreationList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<List<String>> offertypeList = new ArrayList<>();
		List<List<String>> channelList = new ArrayList<>();


		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();

		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");


			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";

			
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

			query = sessionFactory.getCurrentSession().createNativeQuery(abcreationQry);

			abcreationList.add(abcreationHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				abcreationList.add(dataObj);
			}

			downloadDataMap.put("AB CREATION", abcreationList);

			query = sessionFactory.getCurrentSession().createNativeQuery(offertypeQry);

			offertypeList.add(offertypeHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				offertypeList.add(dataObj);
			}

			downloadDataMap.put("OFFER TYPE", offertypeList);

			query = sessionFactory.getCurrentSession().createNativeQuery(modalityQry);

			modalityList.add(modalityHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				modalityList.add(dataObj);
			}

			downloadDataMap.put("MODALITY", modalityList);
			
			query = sessionFactory.getCurrentSession().createNativeQuery(channelQry);

			channelList.add(channelHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				channelList.add(dataObj);
			}

			downloadDataMap.put("CHANNEL", channelList);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;

	}
	// Added by Kavitha D for download promo new template ends-SPRINT-9

	// Added by Kavitha D for download promo CR template starts-SPRINT-9
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Map<String, List<List<String>>> getMastersForCrTemplate() {
		Map<String, List<List<String>>> downloadDataMap = new HashMap<>();
		List<List<String>> clusterList = new ArrayList<>();
		List<List<String>> customerList = new ArrayList<>();
		List<List<String>> abcreationList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<List<String>> offertypeList = new ArrayList<>();
		List<List<String>> channelList = new ArrayList<>();


		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();

		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");


			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";

			
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

			query = sessionFactory.getCurrentSession().createNativeQuery(abcreationQry);

			abcreationList.add(abcreationHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				abcreationList.add(dataObj);
			}

			downloadDataMap.put("AB CREATION", abcreationList);

			query = sessionFactory.getCurrentSession().createNativeQuery(offertypeQry);

			offertypeList.add(offertypeHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				offertypeList.add(dataObj);
			}

			downloadDataMap.put("OFFER TYPE", offertypeList);

			query = sessionFactory.getCurrentSession().createNativeQuery(modalityQry);

			modalityList.add(modalityHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				modalityList.add(dataObj);
			}

			downloadDataMap.put("MODALITY", modalityList);
			
			query = sessionFactory.getCurrentSession().createNativeQuery(channelQry);

			channelList.add(channelHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				channelList.add(dataObj);
			}

			downloadDataMap.put("CHANNEL", channelList);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;

	}
	// Added by Kavitha D for download promo Cr template ends-SPRINT-9

	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerDetail, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L1 as 'SECONDARY CHANNEL',CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',AB_CREATION as 'AB CREATION (ONLY FOR KA Accounts)',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,BRANCH,CLUSTER,QUANTITY,ERROR_MSG as 'ERROR MESSAGE',TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID' FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter(0, userId);
			Iterator itr = query.list().iterator();
			downloadDataList.add(headerDetail);
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
			logger.debug(e);
		}
		return downloadDataList;
	}

	@Override
	public Map<String, List<List<String>>> getMastersForTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

}
