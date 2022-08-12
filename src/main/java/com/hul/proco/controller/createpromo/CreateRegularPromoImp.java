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

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
			+ "BRANCH,USER_ID,TEMPLATE_TYPE,QUANTITY,AB_CREATION,START_DATE,END_DATE,ERROR_MSG,MOC) " + "VALUES "
			+ "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14,?15,?16,?17,?18,?19,?20,?21,?22,?23)";

	/*
	 * private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE =
	 * "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,PROMO_ID,PID,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY,STATUS,ACTIVE"
	 * +
	 * ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_ADDITIONAL_QTY,CR_BUDGET,PROMOTION_ID,CREATED_BY,CREATED_DATE)"
	 * +
	 * " VALUES (?0,?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16,?17,?18,?19,?20,?21,?22,?23,?24"
	 * + ",?25,?26,?27,?28,?29,?30,?31,?32,?33,?34)";
	 */
	private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 "
			+ "(CHANNEL_NAME,MOC_NAME,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,MOC_YEAR,BRANCH,USER_ID,TEMPLATE_TYPE,QUANTITY,AB_CREATION,START_DATE,END_DATE,MOC"
			+ ",CREATED_BY,CREATED_DATE,STATUS,ACTIVE) "
			+ "VALUES ( ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22,?23,?24,?25,?26)";

	private static String Pid = "SELECT (CASE WHEN MAX(PID) IS NULL THEN '000001' ELSE LPAD(CAST(MAX(CAST(PID AS UNSIGNED)) + 1 AS CHAR),6,0) END) AS PID FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC_NAME=?0 AND MOC_YEAR=?1"; // ONLY

	public String createPromotion(CreateBeanRegular[] beans, String uid, String template, String categories) {
		// TODO Auto-generated method stub

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", uid);
		queryToDelete.executeUpdate();

		Map<String, String> datehandle = datafromtable.handleDates();

		Map<String,ArrayList<String>> clusterandppm=new HashMap<String, ArrayList<String>>();
		datafromtable.getAllClusterBasedOnPPM(clusterandppm);

		Query query = (Query) sessionFactory.getCurrentSession()
				.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
		Map<String, String> branchmap = getValidBranch();
		Map<String, String> abmap = getValidAbcreation();
		Map<String, String> secmap = getValidSecondaryChannel();
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
		datafromtable.getPresentPromo(commanmap);
		datafromtable.mapPPMandChannel(commanmap);
		for (CreateBeanRegular bean : beans) {
			if (!duplicateMap.containsKey(bean.getMoc_name() + bean.getYear() + bean.getPpm_account()+bean.getBasepack_code())) {
				duplicateMap.put(bean.getMoc_name() + bean.getYear() + bean.getPpm_account()+bean.getBasepack_code(),"");
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
				query.setString(12, bean.getBudget());
				query.setString(13, bean.getCluster());
				query.setString(14, bean.getYear());

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

				if (isStringNumber(bean.getBudget()))
					query.setString(12, bean.getBudget().isEmpty() ? ""
							: String.valueOf((double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100));
				else
					query.setString(12, bean.getBudget());
				query.setString(15, branchmap.get(bean.getCluster().toUpperCase()));
				query.setString(13, bean.getCluster());
				query.setString(16, uid);

				query.setString(18, "");
				query.setString(17, "R");
				if (template.equalsIgnoreCase("new") || template.equalsIgnoreCase("regular")) {
					if (template.equalsIgnoreCase("new")) {

						query.setString(17, "NE");
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
					
					
					if (!clusterandppm.get(bean.getPpm_account().toUpperCase()).contains(bean.getCluster().toUpperCase())) {
						if (flag == 1) {
							error_msg = error_msg + "Invalid " + bean.getPpm_account() + " for " + bean.getCluster();
						} else
							error_msg = error_msg + ",Invalid " + bean.getPpm_account() + " for " + bean.getCluster();

						flag = 1;

					}
					
					
					if (commanmap.containsKey(bean.getMoc_name() + bean.getPpm_account() + bean.getYear())) {
						if (flag == 1) {
								error_msg = error_msg + ",Promo entry already exist against promo ID, created by "
										+ commanmap.get(
												bean.getMoc_name() + bean.getPpm_account() + bean.getYear())
										+ " " + " ,Request to give entry as CR";
						} else {
								error_msg = error_msg + "Promo entry already exist against promo ID, created by "
										+ commanmap.get(
												bean.getMoc_name() + bean.getPpm_account() + bean.getYear())
										+ " " + " ,Request to give entry as CR";
						}

						flag = 1;

					}

					if (!validationmap.get("baseback").contains(bean.getC_pack_code())) {
						if (!bean.getC_pack_code().isEmpty()) {
							if (flag == 1)
								error_msg = error_msg + ",Invalid child basepack";
							else {
								error_msg = error_msg + "Invalid child baseback";
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

					if (!validationmap.get("Channel name").contains(bean.getChannel().toUpperCase())
							|| !commanmap.get(bean.getPpm_account().toUpperCase()).equalsIgnoreCase(bean.getChannel().toUpperCase())) {

						if (flag == 1) {
							error_msg = error_msg + ",Invalid Channel";
							flag = 1;
						} else {
							error_msg = error_msg + "Invalid Channel";
							flag = 1;
						}
					}
					
					// Adding new changes for start date and end date
					String moc_group = datehandle.get(bean.getChannel().toUpperCase() + "_" + bean.getPpm_account().toUpperCase());
					String moc_name = bean.getMoc_name().toUpperCase(), moc_year = bean.getYear().toUpperCase();
					String start_key = moc_name + moc_year + moc_group + "_start_date";
					String end_key = moc_name + moc_year + moc_group + "_end_date";
					if (flag == 0) {
						if (!datehandle.containsKey(start_key) && !datehandle.containsKey(end_key)) {
							if (flag == 1)
								error_msg += ",Invalid Back Dated MOC";
							else
								error_msg += "Invalid Back Dated MOC";

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

					if (bean.getOfr_type().equalsIgnoreCase("STPR")
							|| bean.getOfr_type().equalsIgnoreCase("STPR Liquidation")) {
						if (price_off.isEmpty()) {
							if (flag == 1) {
								error_msg = error_msg + ",Price off mismatch with description";
								flag = 1;
							} else {
								error_msg = error_msg + "Price off mismatch with description";
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
						if (bean.getBudget().isEmpty() || !isStringNumber(bean.getBudget())) {
							if (flag == 1) {
								error_msg = error_msg + ",Empty Budget/not number";
								flag = 1;
							} else {
								error_msg = error_msg + "Empty Budget/not number";
								flag = 1;
							}
						}
					}

				} else if (template.equalsIgnoreCase("cr")) {
					if (!validationmap.get("SOL TYPE").contains(bean.getSol_type().toUpperCase())) {
						error_msg = error_msg + "Invalid SOL";
						flag = 1;
					}

					// start
					if (bean.getSol_type().equalsIgnoreCase("_DE_")) {

						if (bean.getEnd_date().isEmpty()) {
							if (flag == 1) {
								error_msg = error_msg + ",for _DE_ SOL, End date empty";
								flag = 1;
							} else {
								error_msg = error_msg + "for _DE_ SOL, End date empty";
								flag = 1;
							}
							query.setString(18, "");
							query.setString(19, "");
						} else {
							if (isPromoTimeisValid(bean.getEnd_date())) { // checking end date in format
								try {

									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
									String new_date = sdf2.format(sdf.parse(bean.getEnd_date().replace("/", "-")));
									if (!isMocInFormat(moc_from_db)
											// || !validationmap.get("AB creation").contains(bean.getAb_creation())
											|| bean.getPpm_account().contains(",")) {
										query.setString(18, "");
										query.setString(19, "");
										if (flag == 1) {
											error_msg = error_msg + ",Invalid Date extension DATE should with in moc ";
											flag = 1;
										} else {
											error_msg = error_msg + "Invalid Date extension DATE should with in moc ";
											flag = 1;
										}
									} else {
										String startDate = getStartDate(
												moc_from_db.substring(4, 6).concat(moc_from_db.substring(0, 4)),
												bean.getPpm_account(), new_date);
										if (startDate.isEmpty() || startDate.equals("")) {
											error_msg = error_msg + "Invalid Date extension DATE should with in moc";
											query.setString(18, "");
											query.setString(19, "");
											flag = 1;
										} else {

											SimpleDateFormat sdf_1 = new SimpleDateFormat("dd/MM/yyyy");
											SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy/MM/dd");
											query.setString(18, sdf_1.format(sdf_2.parse(startDate.replace("-", "/"))));
											query.setString(19, bean.getEnd_date());
										}
									}
								} catch (Exception e) {
									// TODO: handle exception
									logger.error(e);
								}
							} else {

								if (flag == 1) {
									error_msg = error_msg + ",Invalid END date";
									flag = 1;
								} else {
									error_msg = error_msg + "Invalid END date";
									flag = 1;
								}
								query.setString(18, "");
								query.setString(19, "");
							}
						}

						// end

					} else {
						List<Object[]> se_date = getStartEndDate(
								moc_from_db.substring(4, 6).concat(moc_from_db.substring(0, 4)), bean.getPpm_account());
						if (!se_date.isEmpty()) {
							query.setString(18, se_date.get(0)[0].toString()); // start date
							query.setString(19, se_date.get(0)[1].toString()); // end date
						} else {
							if (flag == 1) {
								error_msg = error_msg + "Invalid Date extension DATE should with in moc";
								flag = 1;
							}

							query.setString(18, ""); // start date
							query.setString(19, ""); // end date
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_BE_") && bean.getAddition_budget().isEmpty()
							|| (bean.getSol_type().equalsIgnoreCase("_BE_")
									&& !isStringNumber(bean.getAddition_budget()))) {

						if (flag == 1) {
							error_msg = error_msg + ",for _BE_ SOL,Additional Budget empty/not number";
							flag = 1;
						} else {
							error_msg = error_msg + "for _BE_ SOL,Additional Budget empty/not number";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_AQ_") && bean.getAdditional_QTY().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _AQ_ SOL, Additional QTY empty";
							flag = 1;
						} else {
							error_msg = error_msg + "for _AQ_ SOL, Additional QTY empty";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_MG_") && bean.getCluster_selection().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _MG_ SOL, Cluster selection empty";
							flag = 1;
						} else {
							error_msg = error_msg + "for _MG_ SOL, Cluster selection  empty";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_BPA_") && bean.getBasepack_addition().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _BPA_ SOL, Basepack Addition empty";
							flag = 1;
						} else {
							error_msg = error_msg + "for _BPA_ SOL, Basepack Addition  empty";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_TOPUP_") && bean.getTopup().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _TOPUP_ SOL, TOPUP empty";
							flag = 1;
						} else {
							error_msg = error_msg + " for _TOPUP_ SOL, TOPUP empty";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_TOPUP LC_") && bean.getTopup().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _TOPUP LC_ SOL, TOPUP empty";
							flag = 1;
						} else {
							error_msg = error_msg + " for _TOPUP LC_ SOL, TOPUP empty";
							flag = 1;
						}
					}

					if (bean.getSol_type().equalsIgnoreCase("_OM_") && bean.getAddition_budget().isEmpty()) {

						if (flag == 1) {
							error_msg = error_msg + ", for _OM_, Addition budget empty";
							flag = 1;
						} else {
							error_msg = error_msg + "for _OM_ SOL, Addition budget empty";
							flag = 1;
						}
					}
					query.setString(2, bean.getSecondary_channel());
					query.setString(5, bean.getAb_creation());
					query.setString(12, bean.getPrice_off().isEmpty() ? ""
							: String.valueOf((double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100) / 100));
					if (isStringNumber(bean.getQuantity())) {
						query.setString(21, bean.getQuantity().isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100));
					} else
						query.setString(21, bean.getQuantity());
					query.setString(22, bean.getSol_type());
					query.setString(23, bean.getEnd_date());
					query.setString(24, bean.getCluster_selection());
					query.setString(25, bean.getBasepack_addition());
					query.setString(26, bean.getTopup());
					query.setString(27, bean.getAdditional_QTY());
					query.setString(28, bean.getAddition_budget());
					query.setString(29, bean.getSol_release_on());
					query.setString(30, bean.getExisting_sol_code());
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

				if (!validationmap.get("baseback").contains(bean.getBasepack_code())) {
					if (flag == 1)
						error_msg = error_msg + ",Invalid Parent basepack";
					else {
						error_msg = error_msg + "Invalid parent baseback";
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

				if (flag == 1)
					globle_flag = 1;

				query.setString(22, error_msg);
				query.executeUpdate();

				error_msg = "";
				flag = 0;
			}
		}
		if (flag == 0) {
			datafromtable.updatePPMDescStage(uid);

			String getPPM_DESC_STAGE = "select CHANNEL_NAME,MOC_NAME,PPM_ACCOUNT,BASEPACK_CODE,PPM_DESC_STAGE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID='"
					+ uid + "' ";
			List<Object[]> listofstage = sessionFactory.getCurrentSession().createNativeQuery(getPPM_DESC_STAGE).list();

			for (Object[] obj : listofstage) {
				commanmap.put(String.valueOf(obj[0]) + String.valueOf(obj[1]) + String.valueOf(obj[2])
						+ String.valueOf(obj[3]), String.valueOf(obj[4]));
			}
			LocalDate l = LocalDate.now();

			Month currentMonth = l.getMonth();
			int month = currentMonth.getValue();
			int day = l.getDayOfMonth();

			String sMonth = month < 10 ? "0" + month : String.valueOf(month);
			String sDay = day < 10 ? "0" + day : String.valueOf(day);

			for (CreateBeanRegular bean : beans) {
				String ppm_desc_stage = commanmap
						.get(bean.getChannel() + bean.getMoc_name() + bean.getPpm_account() + bean.getBasepack_code());
				String pid = getPID(bean.getMoc_name(), bean.getYear(), bean.getPpm_account(), bean.getBasepack_code(),
						ppm_desc_stage);
				String last2digit = bean.getYear().substring(bean.getYear().length() - 2, bean.getYear().length());
				String pomoid = createNewPromoId(template, sDay + sMonth, last2digit + bean.getMoc_name(), pid);
				datafromtable.updatePromoIdInTemp(pomoid, bean.getMoc_name(), bean.getPpm_account(),
						bean.getBasepack_code(), pid,bean.getYear());
			}
		}
		if (globle_flag == 0) {

			// saveTomainTable(beans, uid, template,
			// promotimemap,branchmap,abmap,datehandle);
			saveToMain(uid);
			globle_flag = 0;
			return "EXCEL_UPLOADED";

		} else {
			globle_flag = 0;
			return "EXCEL_NOT_UPLOADED";
		}

	}

	public void saveToMain(String uid) {
		String insertString = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, MOC_NAME, MOC_YEAR, PPM_ACCOUNT, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER, PROMO_ID,  PID,START_DATE, END_DATE, TEMPLATE_TYPE, USER_ID, PPM_DESC_STAGE, PPM_DESC,STATUS,ACTIVE,CREATED_BY)\r\n"
				+ "SELECT CHANNEL_NAME, MOC, MOC_NAME, MOC_YEAR, PPM_ACCOUNT, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER, PROMO_ID,  PID,START_DATE, END_DATE, TEMPLATE_TYPE, USER_ID, PPM_DESC_STAGE, PPM_DESC,'1','1','"
				+ uid + "'\r\n" + "FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID='" + uid + "'";
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

		String query = "SELECT BASEPACK,CATEGORY_DESC  FROM TBL_VAT_PRODUCT_MASTER WHERE CATEGORY_DESC in(";
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

	private String getPID(String moc, String year, String ppm_account, String basepack, String ppmdesc) {
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
			/*while (itr.hasNext()) {
				String obj = (String) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				String value = "";
				value = (obj == null) ? "" : obj.toString();
				dataObj.add(value.replaceAll("\\^", ","));
				obj = null;
				offertypeList.add(dataObj);
			}
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
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.CUSTOMER_CHAIN_L2 WHERE PM.USER_ID=:userId ";
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
				qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L1 as 'SECONDARY CHANNEL',CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',AB_CREATION as 'AB CREATION (ONLY FOR KA Accounts)',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,BRANCH,CLUSTER,QUANTITY"
						+ ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_Additional_QTY,CR_BUDGET,ERROR_MSG as 'ERROR MESSAGE',TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID' FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
			} else if (error_template.equalsIgnoreCase("R")) {
				if (roleid.equalsIgnoreCase("dp"))
					qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,CLUSTER,QUANTITY,TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID',ERROR_MSG as 'ERROR MESSAGE' "
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
				else
					qry = "SELECT CHANNEL_NAME,MOC_NAME,MOC_YEAR,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,TEMPLATE_TYPE,USER_ID,ERROR_MSG"
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";

			} else if (error_template.equalsIgnoreCase("ne")) {
				qry = "SELECT CHANNEL_NAME,MOC_NAME,MOC_YEAR,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,BUDGET,CLUSTER,QUANTITY,TEMPLATE_TYPE,USER_ID,ERROR_MSG"
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
