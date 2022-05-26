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
				query.setString(12, bean.getPrice_off());
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
				if (!isPPMValid(bean.getPpm_account())) {
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
					if (flag == 1)
						error_msg = error_msg + ",Invalid child basepack";
					else {
						error_msg = error_msg + "Invalid child baseback";
						flag = 1;
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

				if (!isValidSec(bean.getSecondary_channel())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Secondary Channel";
					else {
						error_msg = error_msg + "Invalid Secondary Channel";
						flag = 1;
					}
				}

				if (!isValidCluster(bean.getCluster())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Cluster";
					else {
						error_msg = error_msg + "Invalid Cluster";
						flag = 1;
					}
				}

				if (!isMocInFormat(bean.getMoc())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid MOC format";
					else {
						error_msg = error_msg + "Invalid MOC format";
						flag = 1;
					}
				}

				if (bean.getOffer_desc().isEmpty() || bean.getOffer_desc().equals("")) {
					error_msg = error_msg + "Offer description should not be empty";
					flag = 1;
				}

				if (bean.getPromo_time_period().isEmpty() // || bean.getPromo_time_period().isBlank()
						|| bean.getPromo_time_period() == "") {

					String new_moc = bean.getMoc();

					List<Object[]> se_date = getStartEndDate(new_moc.substring(4, 6).concat(new_moc.substring(0, 4)),
							bean.getPpm_account());

					query.setString(18, se_date.get(0)[0].toString()); // start date
					query.setString(19, se_date.get(0)[1].toString()); // end date

				} else {
					query.setString(18, "");
					query.setString(19, "");
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
			System.out.println("Can not upload excel, Excel contain error..!");
			globle_flag = 0;
			return "EXCEL_NOT_UPLOADED";
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
				query.setString(19, "");
				query.setString(20, "");
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
			String promo_id = "SELECT DISTINCT PROMO_ID,PID from TBL_PROCO_PROMOTION_MASTER_V2 where moc='" + moc
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

	private boolean isValidCluster(String cluster) {
		String cluster_query = "select count(1) from TBL_PROCO_CUSTOMER_MASTER where CLUSTER='" + cluster
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(cluster_query) > 0) {
			return true;
		}
		return false;

	}

	private boolean isValidBranch(String branch) {
		String branch_query = "select count(1) from TBL_PROCO_CUSTOMER_MASTER where BRANCH='" + branch
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(branch_query) > 0) {
			return true;
		}
		return false;

	}

	private boolean isOfferModalityValid(String offer_mod) {
		String mod_query = "select count(1) from TBL_PROCO_OFFER_MODALITY_MASTER where MODALITY_NAME='" + offer_mod
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(mod_query) == 1) {
			return true;
		}
		return false;
	}

	private boolean isOfferTypeValid(String ofr_type) {
		String str_query = "select count(1) from TBL_PROCO_OFFER_TYPE_V2 where OFFER_TYPE='" + ofr_type
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(str_query) == 1) {
			return true;
		}
		return false;
	}

	private boolean isBasepackValid(String basepack_code) {
		String basepack = "select count(1) from TBL_PROCO_PRODUCT_MASTER where basepack='" + basepack_code
				+ "' and ACTIVE=1";
		if (excuteValidationQuery(basepack) == 1) {
			return true;
		}
		return false;
	}

	private boolean isABCreationValid(String ab_creation) {
		String ab_query = "select count(1) from  TBL_PROCO_AB_CREATION_MASTER where AB_CREATION_NAME='" + ab_creation
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
		
		
		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("CUSTOMER CHAIN L1");
			customerHeaders.add("CUSTOMER CHAIN L2");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("MODALITY_NO");
			modalityHeaders.add("MODALITY");
			offertypeHeaders.add("OFFER TYPE");

			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NO,MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";

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
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;				// Added by Kavitha D for download promo regular template ends-SPRINT-9

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
		
		
		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("CUSTOMER CHAIN L1");
			customerHeaders.add("CUSTOMER CHAIN L2");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("MODALITY_NO");
			modalityHeaders.add("MODALITY");
			offertypeHeaders.add("OFFER TYPE");

			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NO,MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";

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
			
			
			List<String> clusterHeaders = new ArrayList<String>();
			List<String> customerHeaders = new ArrayList<String>();
			List<String> abcreationHeaders = new ArrayList<String>();
			List<String> modalityHeaders = new ArrayList<String>();
			List<String> offertypeHeaders = new ArrayList<String>();
			try {
				clusterHeaders.add("BRANCH CODE");
				clusterHeaders.add("BRANCH");
				clusterHeaders.add("CLUSTER CODE");
				clusterHeaders.add("CLUSTER");
				customerHeaders.add("CUSTOMER CHAIN L1");
				customerHeaders.add("CUSTOMER CHAIN L2");
				abcreationHeaders.add("AB CREATION NAME");
				modalityHeaders.add("MODALITY_NO");
				modalityHeaders.add("MODALITY");
				offertypeHeaders.add("OFFER TYPE");

				String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
				String customerQry = "SELECT DISTINCT CUSTOMER_CHAIN_L1,CUSTOMER_CHAIN_L2 FROM TBL_PROCO_CUSTOMER_MASTER ORDER BY CUSTOMER_CHAIN_L1";
				String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
				String modalityQry = "SELECT MODALITY_NO,MODALITY FROM TBL_PROCO_MODALITY_MASTER WHERE ACTIVE=1";
				String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";

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
			} catch (Exception e) {
				logger.debug("Exception: ", e);
			}
			return downloadDataMap;
				
		}
		// Added by Kavitha D for download promo Cr template ends-SPRINT-9


}


