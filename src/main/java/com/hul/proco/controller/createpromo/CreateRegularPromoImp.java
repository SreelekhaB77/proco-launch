package com.hul.proco.controller.createpromo;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

@Repository
public class CreateRegularPromoImp implements CreatePromoRegular {

	private Logger logger = Logger.getLogger(CreatePromoRegular.class);

	private String error_msg = "";
	private int flag = 0;
	private int globle_flag = 0;
	Map<String, String> promo_map = new HashMap<String, String>();
	@Autowired
	private SessionFactory sessionFactory;

	private String moc_from_db;
	@Autowired
	DataFromTable datafromtable;
	
	

	/*
	 * private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP =
	 * "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2" +
	 * "(CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,ERROR_MSG,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY"
	 * +
	 * ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_ADDITIONAL_QTY,CR_BUDGET,SOL_RELEASE_ON,PROMOTION_ID)"
	 * + "VALUES" +
	 * "(?0, ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15,?16,?17,?18,?19,?20,?21,"
	 * + "?22,?23,?24,?25,?26,?27,?28,?29,?30)";
	 * 
	 */
	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 "
			+ "(CHANNEL_NAME,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,MOC_YEAR,"
			+ "BRANCH,USER_ID,TEMPLATE_TYPE,QUANTITY,AB_CREATION,START_DATE,END_DATE,ERROR_MSG,MOC,CR_SOL_TYPE,PROMOTION_ID,SALES_CATEGORY) " + "VALUES "
			+ "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14,"
			+ "?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26)";

	/*
	 * private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE =
	 * "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,PROMO_ID,PID,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY,STATUS,ACTIVE"
	 * +
	 * ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_ADDITIONAL_QTY,CR_BUDGET,PROMOTION_ID,CREATED_BY,CREATED_DATE)"
	 * +
	 * " VALUES (?0,?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16,?17,?18,?19,?20,?21,?22,?23,?24"
	 * + ",?25,?26,?27,?28,?29,?30,?31,?32,?33,?34)";
	 
	private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 "
			+ "(CHANNEL_NAME,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,MOC_YEAR,BRANCH,USER_ID,TEMPLATE_TYPE,QUANTITY,AB_CREATION,START_DATE,END_DATE,MOC"
			+ ",CREATED_BY,CREATED_DATE,STATUS,ACTIVE) "
			+ "VALUES ( ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22,?23,?24,?25,?26)";*/

	private static String Pid = "SELECT (CASE WHEN MAX(PID) IS NULL THEN '000001' ELSE LPAD(CAST(MAX(CAST(PID AS UNSIGNED))+1 AS CHAR),6,0) END) AS PID FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC_NAME=?0 AND MOC_YEAR=?1"; // ONLY
	private static String PidTemp = "SELECT (CASE WHEN MAX(PID) IS NULL THEN '000001' ELSE LPAD(CAST(MAX(CAST(PID AS UNSIGNED)) + 1 AS CHAR),6,0) END) AS PID FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE MOC_NAME=?0 AND MOC_YEAR=?1 AND USER_ID=?2"; // ONLY
	
	
	public String createPromotion(CreateBeanRegular[] beans, String uid, String template, String categories) {
		// TODO Auto-generated method stub
		
		Map<String,String> pid_map= new HashMap<String, String>();
		
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", uid);
		queryToDelete.executeUpdate();

		Map<String, String> datehandle = datafromtable.handleDates();

		Map<String, ArrayList<String>> clusterandppm = new HashMap<String, ArrayList<String>>();
		datafromtable.getAllClusterBasedOnPPM(clusterandppm);

		Query query = (Query) sessionFactory.getCurrentSession()
				.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
		Map<String, String> branchmap = getValidBranch();
		Map<String, String> abmap = getValidAbcreation();
		Map<String, String> offer_mod_map = datafromtable.getAllOffetTypeAndOfferMod();
		
		Map<String, ArrayList<String>> validationmap = datafromtable.getAllValidationRecords();

		// Mayur Adding changes for mapping of TME user for category
		Map<String, String> duplicateMap = new HashMap<String, String>();
		List<String> listofcategory = Arrays.asList(categories.split(",")).stream().map(String::trim)
				.collect(Collectors.toList());

		if (categories.contains("'")) {
			int i = 0;
			for (String category : listofcategory) {

				if (category.contains("'")) {
					listofcategory.set(i, "\"" + category + "\"");

				}
				i++;
			}
		}
		Map<String, String> basepackmap = getAllCategory(listofcategory);
		Map<String, String> promotimemap = getAllTDPTimeperiod();
		Map<String, String> commanmap = new HashMap<String, String>();
		datafromtable.getConbination(commanmap);
		datafromtable.mapPPMandChannel(commanmap);
		datafromtable.basePackAndSaleCategory(commanmap);
		for (CreateBeanRegular bean : beans) {

			if (template.equalsIgnoreCase("new") || template.equalsIgnoreCase("regular")) {
				if (!duplicateMap.containsKey(
						bean.getMoc_name() + bean.getYear() + bean.getPpm_account() + bean.getBasepack_code()+bean.getCluster())) {
					duplicateMap.put(
							bean.getMoc_name() + bean.getYear() + bean.getPpm_account() + bean.getBasepack_code()+bean.getCluster(), "");
					if (template.equalsIgnoreCase("new")) {
						// MOC_NAME-YEAR – Basepack – Account – Cluster
						if (commanmap.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase()
								+ bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase()
								+ bean.getCluster().toUpperCase())) {
							
							if (flag == 1)
								error_msg = error_msg + ",promo entry already exists against promo ID, created by "
										+ commanmap.get(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase()
												+ bean.getPpm_account().toUpperCase()
												+ bean.getBasepack_code().toUpperCase()
												+ bean.getCluster().toUpperCase())
										+ " ";
							else
								error_msg = error_msg + "promo entry already exists against promo ID, created by "
										+ commanmap.get(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase()
												+ bean.getPpm_account().toUpperCase()
												+ bean.getBasepack_code().toUpperCase()
												+ bean.getCluster().toUpperCase())
										+ " ";
							flag=1;
						}
					
						String budget=bean.getBudget().isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100);
						String quantity=bean.getQuantity().isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100);
					
						
						if(bean.getPrice_off().contains("%"))
						{
							if(!isStringNumber(bean.getPrice_off().split("%")[0]))
							{
								if (flag == 1)
									error_msg = error_msg + ",Invalid price off";
								else
									error_msg = error_msg + "Invalid price off";
								flag=1;
								query.setString(12,bean.getBudget());
							}
						}
						else if(!isStringNumber(bean.getPrice_off()))
						{
							if (flag == 1)
								error_msg = error_msg + ",Invalid price off";
							else
								error_msg = error_msg + "Invalid price off";
							flag=1;
							query.setString(12,bean.getBudget());
						}else
						    query.setString(12,datafromtable.calculateBudget(bean.getChannel(), quantity, bean.getPrice_off(), budget, bean.getBasepack_code(), commanmap));
						
						
						
						query.setString(17, "NE");
						if (bean.getQuantity().isEmpty() || Integer.parseInt(bean.getQuantity()) <= 9) {
							if (flag == 1)
								error_msg = error_msg + ",Mandatory input for Quantity, Min Qty criteria not met";
							else
								error_msg = error_msg + "Mandatory input for Quantity, Min Qty criteria not met";
							flag = 1;
						}
						if (isStringNumber(bean.getQuantity()))
							query.setString(18, bean.getQuantity().isEmpty() ? ""
									: String.valueOf(
											(double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100));
						else {
							query.setString(18, bean.getQuantity());
						}
					} else
						query.setString(21, "");
					query.setString(19, abmap.get(bean.getPpm_account().toUpperCase()));
					if(clusterandppm.containsKey(bean.getPpm_account().toUpperCase())) {
					if (!clusterandppm.get(bean.getPpm_account().toUpperCase())
							.contains(bean.getCluster().toUpperCase())) {
						if (flag == 1) {
							error_msg = error_msg + "Invalid " + bean.getPpm_account() + " for " + bean.getCluster();
						} else
							error_msg = error_msg + ",Invalid " + bean.getPpm_account() + " for " + bean.getCluster();

						flag = 1;

					}
					}else
					{
						if (flag == 1) {
							error_msg = error_msg + "Invalid " + bean.getPpm_account() + " for " + bean.getCluster();
						} else
							error_msg = error_msg + ",Invalid " + bean.getPpm_account() + " for " + bean.getCluster();

						flag = 1;
					}
					
					if ((bean.getChannel().equalsIgnoreCase("CNC")
							|| bean.getChannel().equalsIgnoreCase("HUL3")) && (bean.getBudget().isEmpty()
							|| bean.getBudget() == null))
					{
						if (flag == 1)
							error_msg = error_msg + ",Budget entry mandatory for HUL3 and CNC channel";
						else
							error_msg = error_msg + "Budget entry mandatory for HUL3 and CNC channel";
						flag=1;
					}
					
					query.setString(1, bean.getChannel());
					query.setString(2, bean.getMoc_name());
					query.setString(3, bean.getPpm_account());
					query.setString(4, bean.getPromo_time_period());
					query.setString(5, bean.getBasepack_code());
					query.setString(6, bean.getBaseback_desc());
					query.setString(7, bean.getC_pack_code());
					query.setString(8, bean.getOffer_desc());
					query.setString(9, bean.getOfr_type());
					query.setString(10, bean.getOffer_mod());
					query.setString(11, bean.getPrice_off());
					if(template.equalsIgnoreCase("regular"))
					   query.setString(12, bean.getBudget());
					query.setString(13, bean.getCluster());
					query.setString(14, bean.getYear());
					query.setString(24, "");
					query.setString(25, "");
					query.setString(26, commanmap.get(bean.getBasepack_code()));
					if (datafromtable.validateYear(bean.getYear(), bean.getMoc_name())) {
						query.setString(23, bean.getYear());
					} else {
						error_msg += "Invalid Year";
						flag = 1;
						query.setString(23, "");
					}
					try {
						if (flag == 0) {
							if (commanmap.containsKey(bean.getMoc_name() + bean.getYear())) {
								query.setString(23, commanmap.get(bean.getMoc_name() + bean.getYear()));
							} else {
								moc_from_db = datafromtable.getMOC(bean.getMoc_name(), bean.getYear());
								query.setString(23, moc_from_db);
								commanmap.put(bean.getMoc_name() + bean.getYear(), moc_from_db);
							}

						}
					} catch (Exception e) {
						logger.error(e);
					}
					
					
					if(template.equalsIgnoreCase("regular"))
					if (isStringNumber(bean.getBudget()))
						query.setString(12,
								bean.getBudget().isEmpty() ? ""
										: String.valueOf(
												(double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100));
					else
						query.setString(12, bean.getBudget());
					
					
					query.setString(15, branchmap.get(bean.getCluster().toUpperCase()));
					query.setString(13, bean.getCluster());
					query.setString(16, uid);
					if(template.equalsIgnoreCase("regular"))
					      query.setString(18, "");
					if(template.equalsIgnoreCase("regular"))
					{
						 query.setString(17, "R");
						 if(bean.getPrice_off().contains("%"))
							{
								if(!isStringNumber(bean.getPrice_off().split("%")[0]))
								{
									if (flag == 1)
										error_msg = error_msg + ",Invalid price off";
									else
										error_msg = error_msg + "Invalid price off";
									flag=1;
									query.setString(12,"");
								}
							}
							else if(!isStringNumber(bean.getPrice_off()))
							{
								if (flag == 1)
									error_msg = error_msg + ",Invalid price off";
								else
									error_msg = error_msg + "Invalid price off";
								flag=1;
							}
					}
					  
					
					if (!validationmap.get("baseback").contains(bean.getBasepack_code())) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid Parent basepack";
						else {
							error_msg = error_msg + "Invalid parent baseback";
							flag = 1;
						}
					}

					if(bean.getOffer_desc().length()>121)
					{
						if (flag == 1)
							error_msg = error_msg + ",Length should not greater than 121";
						else {
							error_msg = error_msg + "Length should not greater than 121";
							flag = 1;
						}
					}
					
					if(datafromtable.specialChar(bean.getOffer_desc()))
					{
						if (flag == 1)
							error_msg = error_msg + ",description contain special charactor";
						else {
							error_msg = error_msg + "description contain special charactor";
							flag = 1;
						}
					}
					
					if (flag == 0) {
											
						
						if (commanmap
								.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase() + bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase())) {
							if (!template.equalsIgnoreCase("regular")) {
								if (flag == 1) {

									error_msg = error_msg + ",promo ID against other clusters exist, give entry as CR";
								} else {
									error_msg = error_msg + "promo ID against other clusters exist, give entry as CR";
								}

								flag = 1;
							} else {
								if (flag == 1) {

									error_msg = error_msg + ",Promo entry already exist against promo ID, created by "
											+ commanmap.get(bean.getMoc_name().toUpperCase()
													+ bean.getYear().toUpperCase() + bean.getPpm_account().toUpperCase()
													+ bean.getBasepack_code().toUpperCase())
											+ " " + " ,Request to give entry as CR";
								} else {
									error_msg = error_msg + "Promo entry already exist against promo ID, created by "
											+ commanmap.get(bean.getMoc_name().toUpperCase()
													+ bean.getYear().toUpperCase() + bean.getPpm_account().toUpperCase()
													+ bean.getBasepack_code().toUpperCase())
											+ " " + " ,Request to give entry as CR";
								}

								flag = 1;
							}

						}

					}
										
					if (!validationmap.get("offer type").contains(bean.getOfr_type().toUpperCase())) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid Offer Type";
						else {
							error_msg = error_msg + "Invalid Offer Type";
							flag = 1;
						}
					}
					
										
					if (!validationmap.get("Offer modality").contains(bean.getOffer_mod().toUpperCase())) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid Offer Modality";
						else {
							error_msg = error_msg + "Invalid Offer Modality";
							flag = 1;
						}
					}
					

					// If "Offer Modality" user input = "MT Kitting" then basepack code entry in the
					// field is mandatory
					if (bean.getOffer_mod().equalsIgnoreCase("MT Kitting") && bean.getC_pack_code().isEmpty()) {
						if (flag == 1) {
							error_msg = error_msg
									+ ",Mandatory childpack code for kitting promo type, invalid Child basepack";
						} else
							error_msg = error_msg + "Mandatory childpack code for kitting promo type, invalid Child basepack";
						flag = 1;
					}

					// Adding new changes for start date and end date
					
					if(!datehandle.containsKey(bean.getChannel().toUpperCase()+bean.getPpm_account().toUpperCase()))
					{
						if (flag == 1) {
							error_msg = error_msg
									+ ",Channel name and ppm account not matching";
						} else
							error_msg = error_msg + "Channel name and ppm account not matching";
						flag = 1;
					}
					
					String moc_group = datehandle
							.get(bean.getChannel().toUpperCase() + "_" + bean.getPpm_account().toUpperCase());
					String moc_name = bean.getMoc_name().toUpperCase(), moc_year = bean.getYear().toUpperCase();
					String start_key = moc_name + moc_year + moc_group + "_start_date";
					String end_key = moc_name + moc_year + moc_group + "_end_date";
					
					if (flag == 0) {
						if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
							if (flag == 1)
								error_msg += ",Can not obtain start date and end date,Invalid moc or year";
							else
								error_msg += "Can not obtain start date and end date,Invalid moc or year";

							flag = 1;
						}
					}
					// change end

					// Mayur Added changes for promo time period

					String promotime = bean.getPromo_time_period();
					if (promotime.isEmpty()) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid promo timeperiod entry";
						else {
							error_msg = error_msg + "Invalid promo timeperiod entry";
							flag = 1;
						}
						query.setString(20, "");
						query.setString(21, "");
					} else {
						if (promotime.equalsIgnoreCase("bm") || promotime.equalsIgnoreCase("moc")
								|| promotime.equalsIgnoreCase("26 to 25")) {

							if (!promotimemap.containsKey(moc_from_db + bean.getPromo_time_period() + "start_date")
									&& !promotimemap
											.containsKey(moc_from_db + bean.getPromo_time_period() + "end_date")) {
								String new_moc = moc_from_db;
								String promString = "";
								if (promotime.equalsIgnoreCase("bm")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_THREE' AND MOC_YEAR=" + moc_year;
								} else if (promotime.equalsIgnoreCase("moc")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_ONE' AND MOC_YEAR=" + moc_year;
								} else if (promotime.equalsIgnoreCase("26 to 25")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_TWO' AND MOC_YEAR=" + moc_year;
								}

								List<Object[]> promolist = sessionFactory.getCurrentSession()
										.createNativeQuery(promString).list();
								if (promolist.size() == 0) {
									if (flag == 0) {
										if (flag == 1)
											error_msg = error_msg + ",Invalid promo timeperiod entry";
										else
											error_msg = error_msg + "Invalid promo timeperiod entry";
										flag = 1;
									}
									query.setString(20, "");
									query.setString(21, "");

								} else {
									for (Object[] a : promolist) {
										promotimemap.put(moc_from_db + bean.getPromo_time_period() + "start_date",
												a[0].toString());
										promotimemap.put(moc_from_db + bean.getPromo_time_period() + "end_date",
												a[1].toString());
										if (datehandle.containsKey(moc_name + moc_year + "Y")) {
											query.setString(20, datehandle.get(moc_name + moc_year + "Y"));
										} else {
											query.setString(20, a[0].toString());
										}

										query.setString(21, a[1].toString());
									}

								}

							} else {
								query.setString(20,
										promotimemap.get(moc_from_db + bean.getPromo_time_period() + "start_date"));
								query.setString(21,
										promotimemap.get(moc_from_db + bean.getPromo_time_period() + "end_date"));
							}

						} else {
							/*
							 * String converted_moc = moc_from_db.length() == 6 ? moc_from_db.substring(4,
							 * moc_from_db.length()) + moc_from_db.substring(0, 4) : moc_from_db;
							 */
							String converted_moc = moc_from_db;
							if (!promotimemap.containsKey(
									converted_moc + bean.getPromo_time_period().toUpperCase() + "start_date")
									&& !promotimemap.containsKey(
											converted_moc + bean.getPromo_time_period().toUpperCase() + "end_date")) {
								if (flag == 0) {
									if (flag == 1)
										error_msg = error_msg + ",Invalid promo timeperiod entry";
									else {
										error_msg = error_msg + "Invalid promo timeperiod entry";
										flag = 1;
									}
								}
								query.setString(20, "");
								query.setString(21, "");
							} else {

								if (datehandle.containsKey(moc_name + moc_year + "Y")) {
									query.setString(20, datehandle.get(moc_name + moc_year + "Y"));
								} else {
									query.setString(20, promotimemap.get(
											converted_moc + bean.getPromo_time_period().toUpperCase() + "start_date"));
								}

								query.setString(21, promotimemap
										.get(converted_moc + bean.getPromo_time_period().toUpperCase() + "end_date"));
							}
						}
					}
					// change end
					String price_off = bean.getPrice_off();
					if (price_off.isEmpty()) {
						if (flag == 1)
							error_msg = error_msg + ",Mandatory input for Price off";
						else
							error_msg = error_msg + "Mandatory input for Price off";
					}
					
					if (bean.getOfr_type().equalsIgnoreCase("STPR")
							|| bean.getOfr_type().equalsIgnoreCase("STPR Liquidation")) {
						if (price_off.isEmpty()) {
							if (flag == 1) {
								error_msg = error_msg + ",Mandatory input for Price off";
								flag = 1;
							} else {
								error_msg = error_msg + "Mandatory input for Price off";
								flag = 1;
							}

							query.setString(11, "");

						} else {
							if (price_off.endsWith("%") && isStringNumber(price_off.split("%")[0])) {
								query.setString(11, bean.getPrice_off().isEmpty() ? ""
										: String.valueOf(
												(double) Math.round(Double.parseDouble(price_off.split("%")[0]) * 100)
														/ 100)
												+ "%");
							} else if (isStringNumber(price_off)) {
								query.setString(11, bean.getPrice_off().isEmpty() ? ""
										: String.valueOf(
												(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100)
														/ 100));

							} else {
								if (flag == 1) {
									error_msg = error_msg + ",Price off mismatch with description";
									query.setString(11, bean.getPrice_off());
									flag = 1;
								} else {
									error_msg = error_msg + "Price off mismatch with description";
									query.setString(11, bean.getPrice_off());
									flag = 1;
								}

							}

						}

					} else {
						if (isStringNumber(bean.getPrice_off())) {
							query.setString(11, bean.getPrice_off().isEmpty() ? ""
									: String.valueOf(
											(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100) / 100));
						} else {
							query.setString(11, bean.getPrice_off());
						}
						/*
						if (bean.getBudget().isEmpty() || !isStringNumber(bean.getBudget())) {
							if (flag == 1) {
								error_msg = error_msg + ",Empty Budget/not number";
								flag = 1;
							} else {
								error_msg = error_msg + "Empty Budget/not number";
								flag = 1;
							}
						}*/
					}

					if (!validationmap.get("PPM Account").contains(bean.getPpm_account().toUpperCase())
							|| bean.getPpm_account().contains(",")) {

						if (flag == 1)
							error_msg = error_msg + ",Invalid Account/Inactive";
						else {
							error_msg = error_msg + "Invalid Account/Inactive";
							flag = 1;
						}
					}

					if (!basepackmap.containsKey(bean.getBasepack_code())) {
						if (flag == 1)
							error_msg = error_msg + ",basepack doesn't belong to TME's category";
						else {
							error_msg = error_msg + "basepack doesn't belong to TME's category";
							flag = 1;
						}
					}

					if (!validationmap.get("cluster").contains(bean.getCluster().toUpperCase())
							|| bean.getCluster().contains(",")) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid Cluster";
						else {
							error_msg = error_msg + "Invalid Cluster";
							flag = 1;
						}
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
					
					if(!offer_mod_map.get(bean.getOffer_mod().toUpperCase()).equalsIgnoreCase(bean.getOfr_type()))
					{
						if (flag == 1) {
							error_msg = error_msg + ",Mismatch in OFFER TYPE and OFFER MODALITY ";
							flag = 1;
						} else {
							error_msg = error_msg + "Mismatch in OFFER TYPE and OFFER MODALITY";
							flag = 1;
						}
					}
					
					
					if (flag == 1)
						globle_flag = 1;

					query.setString(22, error_msg);
					query.executeUpdate();

					error_msg = "";
					flag = 0;

				}
			}
			
			if(template.equalsIgnoreCase("cr"))
			{
				Map<String, String> crEntries = new HashMap<String, String>();
				Map<String, String> date_extensionMap = new HashMap<String, String>();
				datafromtable.getCREntries(crEntries);
				datafromtable.getAllSOLtype(crEntries);
				datafromtable.getAllSOLCodeAndPromoId(crEntries,date_extensionMap);
			
				if (commanmap
						.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase()
								+ bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase()
								+ bean.getCluster().toUpperCase() + bean.getSol_type().toUpperCase())
						&& !bean.getSol_type().trim().equalsIgnoreCase("Top Up"))
				{
					if (flag == 1)
						error_msg = error_msg + ",promo entry already exists";
					else
						error_msg = error_msg + "promo entry already exists";
					flag = 1;
				}
				
				String budget=bean.getBudget().isEmpty() ? ""
						: String.valueOf(
								(double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100);
				String quantity=bean.getQuantity().isEmpty() ? ""
						: String.valueOf(
								(double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100);
				
				query.setString(12,datafromtable.calculateBudget(bean.getChannel(), quantity, bean.getPrice_off(), budget, bean.getBasepack_code(), commanmap));
				
				query.setString(26, commanmap.get(bean.getBasepack_code().toUpperCase()));
				
				if (!duplicateMap.containsKey(bean.getMoc_name().toUpperCase() +bean.getYear().toUpperCase()+ bean.getBasepack_code().toUpperCase()
						+ bean.getPpm_account().toUpperCase() + bean.getCluster().toUpperCase() + bean.getSol_type().trim().toUpperCase())) {

					duplicateMap.put(bean.getMoc_name().toUpperCase() +bean.getYear().toUpperCase()+ bean.getBasepack_code().toUpperCase()
							+ bean.getPpm_account().toUpperCase() + bean.getCluster().toUpperCase() + bean.getSol_type().trim().toUpperCase(), "");

					if (bean.getQuantity().isEmpty() || Integer.parseInt(bean.getQuantity()) <= 9) {
						if (flag == 1)
							error_msg = error_msg + ",Mandatory input for Quantity, Min Qty criteria not met";
						else
							error_msg = error_msg + "Mandatory input for Quantity, Min Qty criteria not met";
						flag = 1;
					}
					
					if (bean.getOffer_mod().equalsIgnoreCase("MT Kitting") && bean.getC_pack_code().isEmpty()) {
						if (flag == 1) {
							error_msg = error_msg
									+ ",Mandatory childpack code for kitting promo type, invalid Child basepack";
						} else
							error_msg = error_msg + "Mandatory childpack code for kitting promo type, invalid Child basepack";
						flag = 1;
					}
					
					if ((bean.getChannel().equalsIgnoreCase("CNC")
							|| bean.getChannel().equalsIgnoreCase("HUL3")) && (bean.getBudget().isEmpty()
							|| bean.getBudget() == null))
					{
						if (flag == 1)
							error_msg = error_msg + ",Budget entry mandatory for HUL3 and CNC channel";
						else
							error_msg = error_msg + "Budget entry mandatory for HUL3 and CNC channel";
						flag=1;
					}
					
					if(!offer_mod_map.get(bean.getOffer_mod().toUpperCase()).equalsIgnoreCase(bean.getOfr_type()))
					{
						if (flag == 1) {
							error_msg = error_msg + ",Mismatch in OFFER TYPE and OFFER MODALITY ";
							flag = 1;
						} else {
							error_msg = error_msg + "Mismatch in OFFER TYPE and OFFER MODALITY";
							flag = 1;
						}
					}
					
					if (bean.getOffer_mod().equalsIgnoreCase("Ground ops in %")
							|| bean.getOffer_mod().equalsIgnoreCase("Ground ops in rs")
							|| bean.getOffer_mod().equalsIgnoreCase("Ground ops in rs.")
									&& bean.getSol_type().trim().equalsIgnoreCase("topup")
							|| bean.getSol_type().trim().equalsIgnoreCase("top up")) {
						if (bean.getPrice_off().isEmpty()) {
							if (flag == 1)
								error_msg = error_msg + ",Mandatory input for Price off";
							else
								error_msg = error_msg + "Mandatory input for Price off"; 
							flag = 1;
						}
					}
					String price_off="";
					if(flag==0 && isStringNumber(bean.getPrice_off()))
					{
						price_off=bean.getPrice_off().isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100)
												/ 100);
					}else
					{
						if(bean.getPrice_off().contains("%"))
						{
							price_off= bean.getPrice_off().split("%")[0].isEmpty()? "": String.valueOf(
									(double) Math.round(Double.parseDouble(bean.getPrice_off().split("%")[0]) * 100)
									/ 100);
						}
					}
					
					if(!crEntries.containsKey(bean.getSol_type().trim().toUpperCase()))
					{
						if (flag == 1)
							error_msg = error_msg + ",SOL type does not exist";
						else
							error_msg = error_msg + "SOL type does not exist";
						flag = 1;
					}
					
					if(!crEntries.containsKey(bean.getSol_code_ref().toUpperCase()))
					{
						if (flag == 1)
							error_msg = error_msg + ","+bean.getSol_code_ref()+" SOL does not exist";
						else
							error_msg = error_msg + bean.getSol_code_ref()+" SOL does not exist";
						flag = 1;
					}

					if(bean.getSol_type().trim().equalsIgnoreCase("Basepack Addition"))
					{
						if(crEntries.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toString() + bean.getPpm_account().toUpperCase()+bean.getBasepack_code().toUpperCase()+bean.getPrice_off().toUpperCase() + bean.getCluster().toUpperCase() )) {
							if (flag == 1)
								error_msg = error_msg + ",Promo entry exists for basepack " + bean.getBasepack_code() + ", provide different basepack";
							else
								error_msg = error_msg + ",Promo entry exists for basepack " + bean.getBasepack_code() + ", provide different basepack";
							flag = 1;
						} else {
							
							//MOC_NAME-2,MOC_YEAR-10,PPM_ACCOUNT-3,CLUSTER-5,PRICE_OFF-6
							if(!crEntries.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toString() + bean.getPpm_account().toUpperCase()+bean.getCluster().toUpperCase()+bean.getPrice_off().toUpperCase())) {
								if (flag == 1)
									error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, cluster, price off ";
								else
									error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, cluster, price off";
								flag = 1;
							}
						}
					}
					//moc_name,year,PPM_ACCOUNT,BASEPACK_CODE,CLUSTER
					if(bean.getSol_type().trim().equalsIgnoreCase("TOP UP"))
					{
						if (!crEntries.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toUpperCase()
								+ bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase()
								+ bean.getCluster())) {
							if (flag == 1)
								error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, basepack and cluster for TOP UP";
							else
								error_msg = error_msg + "Promo entry does not exists for moc, ppm account, basepack and cluster for TOP UP";
							flag = 1;
						}
					}

					if(bean.getSol_type().trim().equalsIgnoreCase("Missing Geo"))
					{
						//System.out.println("key:"+bean.getMoc_name().toUpperCase() + bean.getYear().toString() + bean.getPpm_account().toUpperCase()+bean.getBasepack_code().toUpperCase()+bean.getPrice_off().toUpperCase() + bean.getCluster().toUpperCase());
						//System.out.println("map:"+crEntries);
						if(crEntries.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toString() + bean.getPpm_account().toUpperCase()+bean.getBasepack_code().toUpperCase()+bean.getPrice_off().toUpperCase() + bean.getCluster().toUpperCase() )) {
							if (flag == 1)
								error_msg = error_msg + ",Promo entry exists for Cluster " + bean.getCluster() + ", provide different cluster";
							else
								error_msg = error_msg + ",Promo entry exists for Cluster " + bean.getCluster() + ", provide different cluster";
							flag = 1;
						} else {
							
							if(!crEntries.containsKey(bean.getMoc_name().toUpperCase() + bean.getYear().toString() + bean.getPpm_account().toUpperCase()+bean.getBasepack_code().toUpperCase()+bean.getPrice_off().toUpperCase() )) {
								if (flag == 1)
									error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, basepack";
								else
									error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, basepack";
								flag = 1;
							}
							
						}
					}

					if(bean.getPrice_off().contains("%"))
					{
						if(!isStringNumber(bean.getPrice_off().split("%")[0]) &&  bean.getOffer_mod().equalsIgnoreCase("Ground ops in %")|| !bean.getOffer_mod().equalsIgnoreCase("Ground ops in Rs"))
						{
						
							if (flag == 1)
								error_msg = error_msg + ",Offer modality must be either Ground ops in % or Rs";
							else
								error_msg = error_msg + "Offer modality must be either Ground ops in % or Rs";
							flag = 1;
						}
					}
					
					
					//MOC_NAME-2, PPM_ACCOUNT-3, BASEPACK_CODE-4, CLUSTER-5,PRICE_OFF-6, year-10
					if (bean.getSol_type().trim().equalsIgnoreCase("Budget Extension")
							|| bean.getSol_type().trim().equalsIgnoreCase("Additional Quantity")
							|| bean.getSol_type().trim().equalsIgnoreCase("Liquidation"))
					{
						if (!crEntries
								.containsKey(bean.getMoc_name().toUpperCase() + bean.getPpm_account().toUpperCase()
										+ bean.getBasepack_code().toUpperCase() + bean.getCluster().toUpperCase()
										+ bean.getPrice_off().toUpperCase() + bean.getYear())) {
							if (flag == 1)
								error_msg = error_msg + ",Promo entry does not exists for moc, ppm account, basepack and price off";
							else
								error_msg = error_msg + "Promo entry does not exists for moc, ppm account, basepack and price off";
							flag = 1;
						}
					}
					
					
					
					//System.out.println("Key:"+bean.getSol_code_ref().toUpperCase() + bean.getMoc_name().toUpperCase()
						//			+ bean.getPpm_account().toUpperCase()  + "_start_date");
					//System.out.println("Map:"+crEntries);
					query.setString(20,
							crEntries.get(bean.getSol_code_ref().toUpperCase() + bean.getMoc_name().toUpperCase()
									+ bean.getPpm_account().toUpperCase()  + "_start_date")); // setting start date and end date
					query.setString(21,
							crEntries.get(bean.getSol_code_ref().toUpperCase() + bean.getMoc_name().toUpperCase()
									+ bean.getPpm_account().toUpperCase() + "_end_date"));
					
					String moc = datafromtable.getMOC(bean.getMoc_name(), bean.getYear());
					
					if (bean.getSol_type().trim().equalsIgnoreCase("Date Extension")
							|| bean.getSol_type().trim().equalsIgnoreCase("Old Month")
					/* || bean.getSol_type().trim().equalsIgnoreCase("Specific Date") */) {
						
						// for tdp check
						String tdp_key = bean.getYear().toUpperCase() + bean.getMoc_name()
								+ bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase()
								+ bean.getCluster().toUpperCase();
						
						if (date_extensionMap.containsKey(tdp_key)) {
							if (bean.getPromo_time_period().toUpperCase().trim()
									.equalsIgnoreCase(date_extensionMap.get(tdp_key))) {

								if (flag == 1)
									error_msg = error_msg + ",Promo entry already does exists";
								else
									error_msg = error_msg + "Promo entry already does exists";
								flag = 1;

							} else {
								String promo_time = bean.getPromo_time_period().toUpperCase();
								String map_promo_time = date_extensionMap.get(tdp_key);
								// to check future tdp or not
								if (promo_time.contains("TDP") && map_promo_time.contains("TDP")) {
									
									int numberpromo_time_int = Integer.parseInt(promo_time.replaceAll("[^0-9]", "")); // 37 enter in excel
									int mappromo_time_int = Integer.parseInt(map_promo_time.replaceAll("[^0-9]", "")); // 31 map
									
									if (numberpromo_time_int < mappromo_time_int) {
										if (flag == 1)
											error_msg = error_msg + ",Back dated Promotime period";
										else
											error_msg = error_msg +"Back dated Promotime period";
										flag = 1;
									} else {
										String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_"
												+ bean.getPpm_account().toUpperCase());
										
										String moc_name = bean.getMoc_name().toUpperCase(),
												moc_year = bean.getYear().toUpperCase();
										String start_key = moc_name + moc_year + moc_group + "_start_date";
										String end_key = moc_name + moc_year + moc_group + "_end_date";
										
										// check if tdp mapped with moc and year
										if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
											if (flag == 1)
												error_msg += ",Invalid " + bean.getPromo_time_period() + "for"
														+ bean.getMoc_name() + " and " + bean.getYear();
											else
												error_msg += "Invalid " + bean.getPromo_time_period() + "for"
														+ bean.getMoc_name() + " and " + bean.getYear();

											flag = 1;
										} else {

											query.setString(20, promotimemap.get(moc+bean.getPromo_time_period()+"start_date" ));
											query.setString(21, promotimemap.get(moc+bean.getPromo_time_period()+"end_date" ));

										}

									}

								} else // for checking other promotime period other than TDP
								{
									String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_"
											+ bean.getPpm_account().toUpperCase());
									String moc_name = bean.getMoc_name().toUpperCase(),
											moc_year = bean.getYear().toUpperCase();
									String start_key = moc_name + moc_year + moc_group + "_start_date";
									String end_key = moc_name + moc_year + moc_group + "_end_date";
									// check if tdp mapped with moc and year
									if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
										if (flag == 1)
											error_msg += ",Invalid " + bean.getPromo_time_period() + "for"
													+ bean.getMoc_name() + " and " + bean.getYear();
										else
											error_msg += "Invalid " + bean.getPromo_time_period() + "for"
													+ bean.getMoc_name() + " and " + bean.getYear();

										flag = 1;
									} else {

										query.setString(20, promotimemap.get(moc+bean.getPromo_time_period()+"start_date" ));
										query.setString(21, promotimemap.get(moc+bean.getPromo_time_period()+"end_date" ));

									}
								}
							}
						}else
						{
							
						   	String key_from_map=date_extensionMap.get(bean.getSol_code_ref().toUpperCase().trim()) ;
						   	//PPM_ACCOUNT,BASEPACK_CODE,CLUSTER
						   	String keywithexcel=bean.getPpm_account().toUpperCase().trim()+bean.getBasepack_code().toUpperCase().trim()+bean.getCluster().toUpperCase().trim();
						   	if(!key_from_map.equalsIgnoreCase(keywithexcel))
						   	{
						   		if (flag == 1)
									error_msg += ",Promo entry does not exists";
								else
									error_msg += "Promo entry does not exists";

								flag = 1;
						   	}else
						   	{
						   		int moc_frommap_cr=Integer.parseInt(date_extensionMap.get(keywithexcel+"_MOC_NAME").replaceAll("[^0-9]", "")) ; // MOC9 --> 9
						   		
						   		int year_frommap_cr=Integer.parseInt(date_extensionMap.get(keywithexcel+"_MOC_YEAR")) ; // 2022
						   		
						   		String moc_in_number=bean.getMoc_name().replaceAll("[^0-9]", ""); // excel moc10 --> String 10
						   		
						   		int moc_name_fromexcel=Integer.parseInt(moc_in_number); //int 10
						   		
						   		int yearfromexcel=Integer.parseInt(bean.getYear().toUpperCase().trim()); // 2022

						   		//if year is equal and check if moc is back dated
						   		if(year_frommap_cr == yearfromexcel)
						   		{
						   			if(datafromtable.getFutureDatedMOC(moc_frommap_cr, moc_name_fromexcel))
						   			{
						   				if (flag == 1)
											error_msg += ",MOC  must be future dated.";
										else
											error_msg += "MOC must be future dated.";

										flag = 1;
						   			}else
						   			{
						   				String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_"
												+ bean.getPpm_account().toUpperCase());
										String moc_name = bean.getMoc_name().toUpperCase(),
												moc_year = bean.getYear().toUpperCase();
										String start_key = moc_name + moc_year + moc_group + "_start_date";
										String end_key = moc_name + moc_year + moc_group + "_end_date";
										// check if tdp mapped with moc and year
										if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
											if (flag == 1)
												error_msg += ",Invalid " + bean.getPromo_time_period() + "for"
														+ bean.getMoc_name() + " and " + bean.getYear();
											else
												error_msg += "Invalid " + bean.getPromo_time_period() + "for"
														+ bean.getMoc_name() + " and " + bean.getYear();

											flag = 1;
										} else {
											//moc_from_db + bean.getPromo_time_period() + "start_date"
											query.setString(20, promotimemap.get(moc+bean.getPromo_time_period()+"start_date" ));
											query.setString(21, promotimemap.get(moc+bean.getPromo_time_period()+"end_date" ));

										}
						   			}
						   		}else if(yearfromexcel>year_frommap_cr)
						   		{

						   			String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_"
											+ bean.getPpm_account().toUpperCase());
									String moc_name = bean.getMoc_name().toUpperCase(),
											moc_year = bean.getYear().toUpperCase();
									String start_key = moc_name + moc_year + moc_group + "_start_date";
									String end_key = moc_name + moc_year + moc_group + "_end_date";
									// check if tdp mapped with moc and year
									if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
										if (flag == 1)
											error_msg += ",Invalid " + bean.getPromo_time_period() + "for"
													+ bean.getMoc_name() + " and " + bean.getYear();
										else
											error_msg += "Invalid " + bean.getPromo_time_period() + "for"
													+ bean.getMoc_name() + " and " + bean.getYear();

										flag = 1;
									} else {

										query.setString(20, promotimemap.get(moc+bean.getPromo_time_period()+"start_date" ));
										query.setString(21, promotimemap.get(moc+bean.getPromo_time_period()+"end_date" ));

									}
						   		}else
						   		{
						   			if (flag == 1)
										error_msg += ",Invalid year for Date extension";
									else
										error_msg += "Invalid year for Date extension";

									flag = 1;
						   		}
						   		
						   	}
						}
						//tdp check end here
						
					}
					
					if(bean.getSol_type().trim().equalsIgnoreCase("Specific Date"))
					{
						if (datafromtable.isPromoTimeisValid(bean.getPromo_time_period().trim())) {
							
							
							String promo_time_sd = bean.getYear().toUpperCase() + bean.getMoc_name()
									+ bean.getPpm_account().toUpperCase() + bean.getBasepack_code().toUpperCase()
									+ bean.getCluster().toUpperCase();

							if (!date_extensionMap.containsKey(promo_time_sd)) {
								if (flag == 1)
									error_msg += ",Promo entry does not exists for Specific Date";
								else
									error_msg += "Promo entry does not exists for Specific Date";

								flag = 1;
							} else {
								
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								Date date = new Date();
								String currentdate = formatter.format(date);
								if (datafromtable.compareDates(bean.getPromo_time_period().trim(), currentdate)) {
									
									String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_"
											+ bean.getPpm_account().toUpperCase());
									String moc_name = bean.getMoc_name().toUpperCase(),
											moc_year = bean.getYear().toUpperCase();
									String start_key = moc_name + moc_year + moc_group + "_start_date";
									
									// check if tdp mapped with moc and year
									if (!datehandle.containsKey(start_key)) {
										if (flag == 1)
											error_msg += ",Can't obtain start date";
										else
											error_msg += "Can't obtain start date";

										flag = 1;
									} else {
																														
										if (datafromtable.compareDates(bean.getPromo_time_period().trim(),
											datehandle.get(start_key).replace('-', '/'))) {
											//query.setString(20, datehandle.get(start_key));
											////PROMOTION_ID-0, PROMO_ID-1, MOC_NAME-2, PPM_ACCOUNT-3 _start_date
											query.setString(20, crEntries.get(bean.getSol_code_ref().toUpperCase()+ bean.getMoc_name().toUpperCase()+bean.getPpm_account()+"_start_date"));
											query.setString(21, 
													bean.getPromo_time_period().trim().replace('/', '-'));
										} else {
											
											if (flag == 1)
												error_msg += ",Enter Future date";
											else
												error_msg += "Enter Future date";

											flag = 1;
										}
									}
								   
									
								} else {
									if (flag == 1)
										error_msg += ",Enter Future date";
									else
										error_msg += "Enter Future date";

									flag = 1;
								}
							}
						}else
						{
							if (flag == 1)
								error_msg += ",Invalid Promotime period";
							else
								error_msg += "Invalid Promotime period";

							flag = 1;
						}
					}
					
					query.setString(1, bean.getChannel());
					query.setString(2, bean.getMoc_name());
					query.setString(3, bean.getPpm_account());
					query.setString(4, bean.getPromo_time_period());
					query.setString(5, bean.getBasepack_code());
					query.setString(6, bean.getBaseback_desc());
					query.setString(7, bean.getC_pack_code());
					query.setString(8, bean.getOffer_desc());
					query.setString(9, bean.getOfr_type());
					query.setString(10, bean.getOffer_mod());
					if(!bean.getPrice_off().contains("%"))
					query.setString(11, bean.getPrice_off().isEmpty() ? ""
							: String.valueOf(
									(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100)
											/ 100));
					else
						query.setString(11, bean.getPrice_off().split("%")[0].isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getPrice_off().split("%")[0]) * 100)
												/ 100)+"%");
					query.setString(13, bean.getCluster());
					query.setString(14, bean.getYear());
					query.setString(15, bean.getBranch());
					query.setString(16, uid);
					query.setString(17, "CR");
					query.setString(18, bean.getQuantity());
					query.setString(19, abmap.get(bean.getPpm_account().toUpperCase()));
									
					query.setString(23, moc);
					query.setString(24, bean.getSol_type().trim());
					query.setString(25, bean.getSol_code_ref());
					
					if (flag == 1)
						globle_flag = 1;

					query.setString(22, error_msg);
					query.executeUpdate();

					error_msg = "";
					flag = 0;
				}
			}
			
			
		}
		
		if (flag == 0) {
			datafromtable.updatePPMDescStage(uid,template);
			
			Session session = sessionFactory.getCurrentSession();
			StoredProcedureQuery proc = session.createStoredProcedureQuery("PROC_PROCO_GENERATE_PROMO_ID");
			proc.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
			proc.setParameter(0, uid);
			proc.execute();
			/*
			LocalDate l = LocalDate.now();

			Month currentMonth = l.getMonth();
			int month = currentMonth.getValue();
			int day = l.getDayOfMonth();

			String sMonth = month < 10 ? "0" + month : String.valueOf(month);
			String sDay = day < 10 ? "0" + day : String.valueOf(day);
			
			int flagtemp=0;
			
			datafromtable.getppmDescStage(commanmap,uid);
			
			for (CreateBeanRegular bean : beans) {

				String ppm_desc_stage = commanmap
						.get(bean.getChannel() + bean.getMoc_name() + bean.getPpm_account() + bean.getBasepack_code());
				String Sale_cate=commanmap
						.get(bean.getChannel() + bean.getMoc_name() + bean.getPpm_account() + bean.getBasepack_code()+"sale");
				
				if (pid_map.containsKey(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage)) {
					
					String pid = pid_map.get(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage);
		
					String promoid = promo_map.get(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage);
					
					datafromtable.updatePromoIdInTemp(promoid, bean.getMoc_name(), bean.getPpm_account(),
							bean.getBasepack_code(), pid, bean.getYear());
				} else {
					
					String last2digit = bean.getYear().substring(bean.getYear().length() - 2, bean.getYear().length());
					
					if (flagtemp == 0) {
						String pid = getPID(bean.getMoc_name(), bean.getYear());
						
						String pomoid = createNewPromoId(template, sDay + sMonth, last2digit + bean.getMoc_name(), pid);
						
						datafromtable.updatePromoIdInTemp(pomoid, bean.getMoc_name(), bean.getPpm_account(),
								bean.getBasepack_code(), pid, bean.getYear());
						promo_map.put(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage, pomoid);
						
						pid_map.put(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage, pid);
						
						flagtemp = 1;
					}else {

						String pidtmp = getTempId(bean.getMoc_name(), bean.getYear(), uid);
						
						String pomoid = createNewPromoId(template, sDay + sMonth, last2digit + bean.getMoc_name(),
								pidtmp);

						promo_map.put(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage, pomoid);
						
						pid_map.put(bean.getYear() + bean.getMoc_name() + Sale_cate + ppm_desc_stage, pidtmp);
						
						datafromtable.updatePromoIdInTemp(pomoid, bean.getMoc_name(), bean.getPpm_account(),
								bean.getBasepack_code(), pidtmp, bean.getYear());
					}
				}
			}*/
		}
		
		
		if (globle_flag == 0) {

			// saveTomainTable(beans, uid, template,
			// promotimemap,branchmap,abmap,datehandle);
			saveToMain(uid,template);
			globle_flag = 0;
			return "EXCEL_UPLOADED";

		} else {
			globle_flag = 0;
			return "EXCEL_NOT_UPLOADED";
		}

	}
	
	
	private String getTempId(String moc,String year,String uid)
	{
		Query q = sessionFactory.getCurrentSession().createNativeQuery(PidTemp);
		q.setString(0, moc);
		q.setString(1, year);
		q.setString(2, uid);
		String pid = q.uniqueResult().toString();

		return pid;
	}
	
	public void saveToMain(String uid,String tempString) {
		String insertString= "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, MOC_NAME, MOC_YEAR, PPM_ACCOUNT, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER, PROMO_ID,  PID,START_DATE, END_DATE, TEMPLATE_TYPE, USER_ID, PPM_DESC_STAGE, PPM_DESC,STATUS,ACTIVE,CREATED_BY,PROMOTION_ID,CR_SOL_TYPE,SALES_CATEGORY,QUANTITY)\r\n"
				+ "SELECT CHANNEL_NAME, MOC, MOC_NAME, MOC_YEAR, PPM_ACCOUNT, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER, PROMO_ID,  PID,START_DATE, END_DATE, TEMPLATE_TYPE, USER_ID, PPM_DESC_STAGE, PPM_DESC,'1','1','"
				+ uid + "',PROMOTION_ID,CR_SOL_TYPE,SALES_CATEGORY,QUANTITY\r\n" + "FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID='" + uid + "'";
		
		sessionFactory.getCurrentSession().createNativeQuery(insertString).executeUpdate();

	}

	private Map<String, String> getAllTDPTimeperiod() {
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String next_year = String.valueOf(Integer.parseInt(year) + 1);
		String stringQuery = "SELECT MOC,TDP,START_DATE,END_DATE FROM TBL_VAT_MOC_TDP_MASTER WHERE MOC_YEAR in ('"
				+ year + "', '" + next_year + "')";
		List<Object[]> promolist = sessionFactory.getCurrentSession().createNativeQuery(stringQuery).list();
		Map<String, String> promomap = new HashMap<String, String>();
		for (Object obj[] : promolist) {
			promomap.put(obj[0].toString() + obj[1].toString() + "start_date", obj[2].toString());
			promomap.put(obj[0].toString() + obj[1].toString() + "end_date", obj[3].toString());
		}
		return promomap;
	}

	private Map<String, String> getAllCategory(List<String> listofcategory) {
		
		String query = "SELECT DISTINCT A.BASEPACK, IFNULL(B.CATEGORY_DESC, A.SALES_CATEGORY) AS CATEGORY_DESC FROM TBL_PROCO_PRODUCT_MASTER_V2 A LEFT JOIN TBL_VAT_PRODUCT_MASTER B ON A.BASEPACK = B.BASEPACK AND B.CATEGORY_DESC IN(";
		for (String s1 : listofcategory) {
			if (s1.contains("'")) {
				query += s1 + ",";
			} else {
				query += "\"" + s1 + "\",";
			}
		}

		List<Object[]> basepacklist = sessionFactory.getCurrentSession()
				.createNativeQuery(query.substring(0, query.length() - 1).concat(")")).list();
		Map<String, String> map = new HashMap<String, String>();
		for (Object obj[] : basepacklist) {
			map.put(obj[0].toString(), obj[1].toString());
		}
		return map;
	}

	private String getStartDate(String concat, String ppm_account, String promo_time_period) {
		String start_date_c = "SELECT count(START_DATE) FROM TBL_PROCO_CUSTOMER_MASTER_V2 A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND" + " '" + promo_time_period
				+ "' BETWEEN START_DATE AND END_DATE AND B.MOC='" + concat + "' AND A.PPM_ACCOUNT='" + ppm_account
				+ "'";

		String start_date_1 = "SELECT START_DATE FROM TBL_PROCO_CUSTOMER_MASTER_V2 A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND" + " '" + promo_time_period
				+ "' BETWEEN START_DATE AND END_DATE AND B.MOC='" + concat + "' AND A.PPM_ACCOUNT='" + ppm_account
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

	private ArrayList<String> getValidSec() {
		String sec_q = "SELECT DISTINCT CUSTOMER_CHAIN_L1 FROM TBL_PROCO_CUSTOMER_MASTER WHERE ACTIVE='1'";

		return (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(sec_q).list();

	}

	private List<Object[]> getStartEndDate(String moc, String ppm_account) {
		String se_query = "SELECT START_DATE,END_DATE FROM TBL_PROCO_CUSTOMER_MASTER_V2 A"
				+ " INNER JOIN TBL_VAT_MOC_MASTER B ON A.MOC_GROUP=B.MOC_GROUP AND A.PPM_ACCOUNT='" + ppm_account
				+ "' AND MOC='" + moc + "'";
		return sessionFactory.getCurrentSession().createNativeQuery(se_query).list();
	}

	private String createNewPromoId(String template, String creation_date, String mocandyear, String pid) {
		if (template.equalsIgnoreCase("regular"))
			return "R" + creation_date + "P" + mocandyear + pid;
		else if (template.equalsIgnoreCase("new"))
			return "N" + creation_date + "P" + mocandyear + pid;
		else
			return "CR" + creation_date + "P" + mocandyear + pid;
	}

	private String getPID(String moc, String year) {
		Query q = sessionFactory.getCurrentSession().createNativeQuery(Pid);
		q.setString(0, moc);
		q.setString(1, year);
		String pid = q.uniqueResult().toString();

		return pid;
	}

	private List<Object[]> getPromoId(String moc, String ppm_account, String offer_desc) {

		List<Object[]> list = null;
		if (promo_map.containsKey(moc + ppm_account + offer_desc)) {
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

	private Map<String, String> getValidBranch() {

		Map<String, String> map = new HashMap<String, String>();
		ArrayList<String> clusters = datafromtable.getValidCluster();
		for (String cluster : clusters) {
			String branch_query = "SELECT DISTINCT BRANCH FROM TBL_PROCO_CUSTOMER_MASTER WHERE ACTIVE='1' AND CLUSTER"
					+ "= '" + cluster + "'";
			ArrayList<String> blist = (ArrayList<String>) sessionFactory.getCurrentSession()
					.createNativeQuery(branch_query).list();
			for (String brach : blist) {
				map.put(cluster.toUpperCase(), brach);
			}
		}

		return map;

	}

	private Map<String, String> getValidAbcreation() {

		Map<String, String> map = new HashMap<String, String>();

		ArrayList<String> ppmaccounts = datafromtable.getValidPPMAccount();

		for (String ppmac : ppmaccounts) {
			String ab_query = "SELECT DISTINCT AB_CREATION FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y' AND"
					+ " PPM_ACCOUNT='" + ppmac + "'";
			List<String> ablist = sessionFactory.getCurrentSession().createNativeQuery(ab_query).list();
			;
			for (String ab : ablist) {
				map.put(ppmac.toUpperCase(), ab);
			}
		}

		return map;
	}

	private Map<String, String> getValidSecondaryChannel() {

		Map<String, String> map = new HashMap<String, String>();

		ArrayList<String> ppmaccounts = datafromtable.getValidPPMAccount();
		for (String ppmac : ppmaccounts) {
			String ab_query = "SELECT DISTINCT SECONDARY_CHANNEL FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y' AND"
					+ " PPM_ACCOUNT='" + ppmac + "'";
			ArrayList<String> seclist = (ArrayList<String>) sessionFactory.getCurrentSession()
					.createNativeQuery(ab_query).list();
			for (String sec : seclist) {
				map.put(ppmac.toUpperCase(), sec);
			}
		}

		return map;
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
		// List<List<String>> abcreationList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<List<String>> offertypeList = new ArrayList<>();
		List<List<String>> channelList = new ArrayList<>();
		List<List<String>> tdpList = new ArrayList<>();

		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		// List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();
		List<String> tdpHeaders = new ArrayList<String>();

		try {
			clusterHeaders.add("CHANNEL NAME");
			clusterHeaders.add("PPM ACCOUNT");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER");
			// customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT (Strictly as per comm rule)");
			// abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("CHANNEL NAME");
			offertypeHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");
			tdpHeaders.add("PROMO TIMEPERIOD");

			String clusterQry = "SELECT DISTINCT CHANNEL_NAME, PPM_ACCOUNT, BRANCH, CLUSTER FROM TBL_PROCO_CLUSTER_MASTER_V2 WHERE IS_ACTIVE=1";
			String customerQry = "SELECT DISTINCT PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y'ORDER BY PPM_ACCOUNT";
			// String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM
			// TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = "SELECT DISTINCT CHANNEL_NAME, OFFER_MODALITY, OFFER_TYPE FROM TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 WHERE IS_ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";
			String tdpQry = " SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";

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
			/*
			 * ArrayList<String> allIndia = new ArrayList<String>();
			 * allIndia.add("ALL INDIA"); clusterList.add(allIndia);
			 */
			downloadDataMap.put("CLUSTER", clusterList);

			query = sessionFactory.getCurrentSession().createNativeQuery(customerQry);

			customerList.add(customerHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				customerList.add(dataObj);
			}
			downloadDataMap.put("CUSTOMER", customerList);

			/*
			 * query = sessionFactory.getCurrentSession().createNativeQuery(abcreationQry);
			 * 
			 * abcreationList.add(abcreationHeaders); itr = query.list().iterator(); while
			 * (itr.hasNext()) { String obj = (String) itr.next(); ArrayList<String> dataObj
			 * = new ArrayList<String>(); String value = ""; value = (obj == null) ? "" :
			 * obj.toString(); dataObj.add(value.replaceAll("\\^", ",")); obj = null;
			 * abcreationList.add(dataObj); }
			 * 
			 * downloadDataMap.put("AB CREATION", abcreationList);
			 */

			query = sessionFactory.getCurrentSession().createNativeQuery(offertypeQry);

			offertypeList.add(offertypeHeaders);
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
				offertypeList.add(dataObj);
			}
			/*
			 * while (itr.hasNext()) { String obj = (String) itr.next(); ArrayList<String>
			 * dataObj = new ArrayList<String>(); String value = ""; value = (obj == null) ?
			 * "" : obj.toString(); dataObj.add(value.replaceAll("\\^", ",")); obj = null;
			 * offertypeList.add(dataObj); }
			 */
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

			query = sessionFactory.getCurrentSession().createNativeQuery(tdpQry);

			tdpList.add(tdpHeaders);

			ArrayList<String> addTdpMoc = new ArrayList<String>();
			addTdpMoc.add("MOC");
			tdpList.add(addTdpMoc);

			ArrayList<String> addTdpBm = new ArrayList<String>();
			addTdpBm.add("BM");
			tdpList.add(addTdpBm);

			ArrayList<String> addTdp = new ArrayList<String>();
			addTdp.add("26 to 25");
			tdpList.add(addTdp);

			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				tdpList.add(dataObj);
			}

			downloadDataMap.put("TDP", tdpList);

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
		// List<List<String>> abcreationList = new ArrayList<>();
		List<List<String>> modalityList = new ArrayList<>();
		List<List<String>> offertypeList = new ArrayList<>();
		List<List<String>> channelList = new ArrayList<>();
		List<List<String>> tdpList = new ArrayList<>();

		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		// List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();
		List<String> tdpHeaders = new ArrayList<String>();

		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			// customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT (Strictly as per comm rule)");
			// abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");
			tdpHeaders.add("PROMO TIMEPERIOD");

			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y'ORDER BY PPM_ACCOUNT";
			// String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM
			// TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";
			String tdpQry = " SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";

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
			/*
			 * ArrayList<String> allIndia = new ArrayList<String>();
			 * allIndia.add("ALL INDIA"); clusterList.add(allIndia);
			 */

			downloadDataMap.put("CLUSTER", clusterList);

			query = sessionFactory.getCurrentSession().createNativeQuery(customerQry);

			customerList.add(customerHeaders);
			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				customerList.add(dataObj);
			}

			downloadDataMap.put("CUSTOMER", customerList);
			/*
			 * query = sessionFactory.getCurrentSession().createNativeQuery(abcreationQry);
			 * 
			 * abcreationList.add(abcreationHeaders); itr = query.list().iterator(); while
			 * (itr.hasNext()) { String obj = (String) itr.next(); ArrayList<String> dataObj
			 * = new ArrayList<String>(); String value = ""; value = (obj == null) ? "" :
			 * obj.toString(); dataObj.add(value.replaceAll("\\^", ",")); obj = null;
			 * abcreationList.add(dataObj); }
			 * 
			 * downloadDataMap.put("AB CREATION", abcreationList);
			 */

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

			query = sessionFactory.getCurrentSession().createNativeQuery(tdpQry);

			tdpList.add(tdpHeaders);

			ArrayList<String> addTdpMoc = new ArrayList<String>();
			addTdpMoc.add("MOC");
			tdpList.add(addTdpMoc);

			ArrayList<String> addTdpBm = new ArrayList<String>();
			addTdpBm.add("BM");
			tdpList.add(addTdpBm);

			ArrayList<String> addTdp = new ArrayList<String>();
			addTdp.add("26 to 25");
			tdpList.add(addTdp);

			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				tdpList.add(dataObj);
			}

			downloadDataMap.put("TDP", tdpList);

		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;

	}
	// Added by Kavitha D for download promo new template ends-SPRINT-9

	// Added by Kavitha D for download promo CR template starts-SPRINT-9

	@SuppressWarnings("unchecked")
	@Override
	public List<ArrayList<String>> getPromotionDownloadCR(ArrayList<String> headerDetail, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = " SELECT PM.CHANNEL_NAME, PM.MOC, CM.SECONDARY_CHANNEL, CM.PPM_ACCOUNT, PR.PROMOTION_ID AS SOL_CODE, CM.AB_CREATION,"
					+ " CM.SOL_RELEASE_ON, PM.BASEPACK_CODE, PR.PROMOTION_NAME, PM.PRICE_OFF, PM.BRANCH, PM.CLUSTER, PM.QUANTITY, PM.BUDGET "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ " INNER JOIN (SELECT PROMOTION_ID, PROMOTION_NAME, PROMO_ID FROM TBL_PROCO_MEASURE_MASTER_V2 GROUP BY PROMOTION_ID, PROMOTION_NAME, PROMO_ID) PR ON PR.PROMO_ID = PM.PROMO_ID "
					+ " INNER JOIN (SELECT MOC FROM TBL_VAT_MOC_MASTER WHERE STATUS = 'Y' LIMIT 1) MM ON PM.MOC >= concat(substr(MM.MOC, 3, 4), substr(MM.MOC, 1, 2)) "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME WHERE PM.USER_ID=:userId ";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);

			query.setString("userId", userId);

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
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}

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
		List<List<String>> solList = new ArrayList<>();
		List<List<String>> tdpList = new ArrayList<>();

		List<String> clusterHeaders = new ArrayList<String>();
		List<String> customerHeaders = new ArrayList<String>();
		List<String> abcreationHeaders = new ArrayList<String>();
		List<String> modalityHeaders = new ArrayList<String>();
		List<String> offertypeHeaders = new ArrayList<String>();
		List<String> channelHeaders = new ArrayList<String>();
		List<String> solHeaders = new ArrayList<String>();
		List<String> tdpHeaders = new ArrayList<String>();

		try {
			clusterHeaders.add("BRANCH CODE");
			clusterHeaders.add("BRANCH");
			clusterHeaders.add("CLUSTER CODE");
			clusterHeaders.add("CLUSTER");
			customerHeaders.add("SECONDARY CHANNEL");
			customerHeaders.add("PPM ACCOUNT (Strictly as per comm rule)");
			abcreationHeaders.add("AB CREATION NAME");
			modalityHeaders.add("OFFER MODALITY");
			offertypeHeaders.add("OFFER TYPE");
			channelHeaders.add("CHANNEL");
			solHeaders.add("SOL TYPE");
			solHeaders.add("SOL REMARK");
			tdpHeaders.add("PROMO TIMEPERIOD");

			String clusterQry = "SELECT DISTINCT BRANCH_CODE, BRANCH, CLUSTER_CODE,CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER";
			String customerQry = "SELECT DISTINCT SECONDARY_CHANNEL,PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y' ORDER BY SECONDARY_CHANNEL";
			String abcreationQry = "SELECT DISTINCT AB_CREATION_NAME FROM TBL_PROCO_AB_CREATION_MASTER WHERE ACTIVE=1";
			String modalityQry = "SELECT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE=1";
			String offertypeQry = " SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE=1";
			String channelQry = " SELECT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER WHERE ACTIVE=1";
			String solQry = " SELECT SOL_TYPE,SOL_REMARK FROM TBL_PROCO_SOL_TYPE WHERE IS_ACTIVE=1";
			String tdpQry = " SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";

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
			/*
			 * ArrayList<String> allIndia = new ArrayList<String>();
			 * allIndia.add("ALL INDIA"); clusterList.add(allIndia);
			 */
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

			query = sessionFactory.getCurrentSession().createNativeQuery(solQry);

			itr = query.list().iterator();
			solList.add(solHeaders);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value.replaceAll("\\^", ","));
				}
				obj = null;
				solList.add(dataObj);
			}
			downloadDataMap.put("SOLTYPE", solList);

			query = sessionFactory.getCurrentSession().createNativeQuery(tdpQry);

			tdpList.add(tdpHeaders);

			ArrayList<String> addTdpMoc = new ArrayList<String>();
			addTdpMoc.add("MOC");
			tdpList.add(addTdpMoc);

			ArrayList<String> addTdpBm = new ArrayList<String>();
			addTdpBm.add("BM");
			tdpList.add(addTdpBm);

			ArrayList<String> addTdp = new ArrayList<String>();
			addTdp.add("26 to 25");
			tdpList.add(addTdp);

			itr = query.list().iterator();
			while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				tdpList.add(dataObj);
			}

			downloadDataMap.put("TDP", tdpList);

		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataMap;

	}
	// Added by Kavitha D for download promo Cr template ends-SPRINT-9

	public List<ArrayList<String>> getPromotionErrorDetails(ArrayList<String> headerDetail, String userId,
			String error_template, String roleid) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "";
			if (error_template.equalsIgnoreCase("cr")) {
				qry = "SELECT CHANNEL_NAME,MOC_YEAR,MOC_NAME,PROMO_TIMEPERIOD,PROMOTION_ID,PPM_ACCOUNT,OFFER_DESC,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,QUANTITY,PRICE_OFF,BUDGET,BRANCH,CLUSTER,CR_SOL_TYPE,TEMPLATE_TYPE,USER_ID,ERROR_MSG "
						+ "FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
			} else if (error_template.equalsIgnoreCase("R")) {
				if (roleid.equalsIgnoreCase("dp")) {
					qry = "SELECT CHANNEL_NAME,MOC,MOC_YEAR,MOC_NAME,PROMO_ID,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,QUANTITY,BRANCH,CLUSTER,TEMPLATE_TYPE,USER_ID,ERROR_MSG"
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
				}
				else
					qry = "SELECT CHANNEL_NAME,MOC_YEAR,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,OFFER_DESC,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,TEMPLATE_TYPE,USER_ID,ERROR_MSG"
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";

			} else if (error_template.equalsIgnoreCase("ne")) {
				qry = /*"SELECT CHANNEL_NAME,MOC_YEAR,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,QUANTITY,TEMPLATE_TYPE,USER_ID,ERROR_MSG"
						+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";*/
						
						//Kavitha D changes for error file-SPRINT 9
						" SELECT CHANNEL_NAME,MOC_YEAR,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,OFFER_DESC,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,QUANTITY,BUDGET,CLUSTER,TEMPLATE_TYPE,USER_ID,ERROR_MSG "
						+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";

			}
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

	@Override
	public String getTemplateType(String uid) {
		String templateString = "SELECT TEMPLATE_TYPE FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2  WHERE USER_ID='" + uid
				+ "' LIMIT 1";

		return (String) sessionFactory.getCurrentSession().createNativeQuery(templateString).uniqueResult();
	}

}
