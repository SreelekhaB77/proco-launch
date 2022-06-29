package com.hul.proco.controller.createpromo;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	Map<String, String> promo_map = new HashMap<String, String>();
	@Autowired
	private SessionFactory sessionFactory;

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2"
			+ "(CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,ERROR_MSG,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY"
			+ ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_ADDITIONAL_QTY,CR_BUDGET,SOL_RELEASE_ON,PROMOTION_ID)"
			+ "VALUES"
			+ "(?0, ?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15,?16,?17,?18,?19,?20,?21,"
			+ "?22,?23,?24,?25,?26,?27,?28,?29,?30)";

	private static String SQL_QUERY_INSERT_INTO_PROMO_TABLE = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_V2 (CHANNEL_NAME, MOC, CUSTOMER_CHAIN_L1, CUSTOMER_CHAIN_L2, PROMO_TIMEPERIOD, AB_CREATION, BASEPACK_CODE, BASEPACK_DESC, CHILD_BASEPACK_CODE, OFFER_DESC, OFFER_TYPE, OFFER_MODALITY, PRICE_OFF, BUDGET, BRANCH, CLUSTER,USER_ID,PROMO_ID,PID,START_DATE,END_DATE,TEMPLATE_TYPE,QUANTITY,STATUS,ACTIVE"
			+ ",CR_SOL_TYPE,CR_END_DATE,CR_CLUSTER,CR_BASEPACK_ADDITION,CR_TOPUP,CR_ADDITIONAL_QTY,CR_BUDGET,PROMOTION_ID,CREATED_BY,CREATED_DATE)"
			+ " VALUES (?0,?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16,?17,?18,?19,?20,?21,?22,?23,?24"
			+ ",?25,?26,?27,?28,?29,?30,?31,?32,?33,?34)";

	private static String Pid = "SELECT (CASE WHEN MAX(PID) IS NULL THEN '000001' ELSE LPAD(CAST(MAX(CAST(PID AS UNSIGNED)) + 1 AS CHAR),6,0) END) AS PID FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE MOC=?0 ";

	public String createPromotion(CreateBeanRegular[] beans, String uid, String template, String categories) {
		// TODO Auto-generated method stub

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", uid);
		queryToDelete.executeUpdate();

		Query query = (Query) sessionFactory.getCurrentSession()
				.createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
		Map<String, String> branchmap = getValidBranch();
		Map<String, String> abmap = getValidAbcreation();
		Map<String, String> secmap = getValidSecondaryChannel();
		Map<String, ArrayList<String>> validationmap = getAllValidationRecords();

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

		for (CreateBeanRegular bean : beans) {
			if (!duplicateMap.containsKey(bean.getPpm_account() + bean.getBasepack_code() + bean.getCluster()
					+ bean.getMoc() + bean.getOffer_desc())) {
				query.setString(0, bean.getChannel());
				query.setString(1, bean.getMoc());

				query.setString(3, bean.getPpm_account());
				query.setString(4, bean.getPromo_time_period());
				query.setString(6, bean.getBasepack_code());
				query.setString(7, bean.getBaseback_desc());
				query.setString(8, bean.getC_pack_code());
				query.setString(9, bean.getOffer_desc());
				query.setString(10, bean.getOfr_type());
				query.setString(11, bean.getOffer_mod());
				// query.setString(12, bean.getPrice_off());
				if (isStringNumber(bean.getBudget()))
					query.setString(13, bean.getBudget().isEmpty() ? ""
							: String.valueOf((double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100));
				else
					query.setString(13, bean.getBudget());
				query.setString(14, branchmap.get(bean.getCluster().toUpperCase()));
				query.setString(15, bean.getCluster());
				query.setString(16, uid);
				query.setString(20, template);

				if (template.equalsIgnoreCase("new") || template.equalsIgnoreCase("regular")) {
					if (template.equalsIgnoreCase("new"))
					{
						if (isStringNumber(bean.getQuantity()))
							query.setString(21, bean.getQuantity().isEmpty() ? ""
									: String.valueOf(
											(double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100));
						else {
							query.setString(21, bean.getQuantity());
						}
					}
					else
						query.setString(21, "");
					query.setString(2, secmap.get(bean.getPpm_account().toUpperCase()));
					query.setString(5, abmap.get(bean.getPpm_account().toUpperCase()));
					query.setString(22, "");
					query.setString(23, "");
					query.setString(24, "");
					query.setString(25, "");
					query.setString(26, "");
					query.setString(27, "");
					query.setString(28, "");
					query.setString(29, "");
					query.setString(30, "");

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
					// Mayur Added changes for promo time period
					String promotime = bean.getPromo_time_period();
					if (promotime.isEmpty()) {
						if (flag == 1)
							error_msg = error_msg + ",Invalid Promo Timeperiod";
						else {
							error_msg = error_msg + "Invalid Promo Timeperiod";
							flag = 1;
						}
						query.setString(18, "");
						query.setString(19, "");
					} else {
						if (promotime.equalsIgnoreCase("bm") || promotime.equalsIgnoreCase("moc")
								|| promotime.equalsIgnoreCase("26 to 25")) {

							if (!promotimemap.containsKey(bean.getMoc() + bean.getPromo_time_period() + "start_date")
									&& !promotimemap
											.containsKey(bean.getMoc() + bean.getPromo_time_period() + "end_date")) {
								String new_moc = bean.getMoc().length() == 6
										? bean.getMoc().substring(4, bean.getMoc().length())
												+ bean.getMoc().substring(0, 4)
										: bean.getMoc();
								String promString = "";
								if (promotime.equalsIgnoreCase("bm")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_THREE'";
								} else if (promotime.equalsIgnoreCase("moc")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_ONE'";
								} else if (promotime.equalsIgnoreCase("26 to 25")) {
									promString = "SELECT START_DATE,END_DATE FROM TBL_VAT_MOC_MASTER WHERE MOC='"
											+ new_moc + "' AND MOC_GROUP='GROUP_TWO'";
								}

								List<Object[]> promolist = sessionFactory.getCurrentSession()
										.createNativeQuery(promString).list();
								if (promolist.size() == 0) {
									error_msg = error_msg + "Start date and end date doesn't exists for " + promotime
											+ " AND " + bean.getMoc() + "";
									flag = 1;
									query.setString(18, "");
									query.setString(19, "");

								} else {
									for (Object[] a : promolist) {
										promotimemap.put(bean.getMoc() + bean.getPromo_time_period() + "start_date",
												a[0].toString());
										promotimemap.put(bean.getMoc() + bean.getPromo_time_period() + "end_date",
												a[1].toString());
										query.setString(18, a[0].toString());
										query.setString(19, a[1].toString());
									}

								}

							} else {
								query.setString(18,
										promotimemap.get(bean.getMoc() + bean.getPromo_time_period() + "start_date"));
								query.setString(19,
										promotimemap.get(bean.getMoc() + bean.getPromo_time_period() + "end_date"));
							}

						} else {
							String converted_moc = bean.getMoc().length() == 6
									? bean.getMoc().substring(4, bean.getMoc().length()) + bean.getMoc().substring(0, 4)
									: bean.getMoc();
							if (!promotimemap.containsKey(
									converted_moc + bean.getPromo_time_period().toUpperCase() + "start_date")
									&& !promotimemap.containsKey(
											converted_moc + bean.getPromo_time_period().toUpperCase() + "end_date")) {
								if (flag == 1)
									error_msg = error_msg + "," + promotime + " is not part of moc:" + bean.getMoc()
											+ ", please enter proper TDP";
								else {
									error_msg = error_msg + "" + promotime + " is not part of moc:" + bean.getMoc()
											+ ", please enter proper TDP";
									flag = 1;
								}
								query.setString(18, "");
								query.setString(19, "");
							} else {
								query.setString(18, promotimemap
										.get(converted_moc + bean.getPromo_time_period().toUpperCase() + "start_date"));
								query.setString(19, promotimemap
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
								error_msg = error_msg + ",Invalid Price Off";
								flag = 1;
							} else {
								error_msg = error_msg + "Invalid Price Off";
								flag = 1;
							}

							query.setString(12, "");

						} else {
							if (price_off.endsWith("%") && isStringNumber(price_off.split("%")[0])) {
								query.setString(12, bean.getPrice_off().isEmpty() ? ""
										: String.valueOf(
												(double) Math.round(Double.parseDouble(price_off.split("%")[0]) * 100)
														/ 100)
												+ "%");
							} else if (isStringNumber(price_off)) {
								query.setString(12, bean.getPrice_off().isEmpty() ? ""
										: String.valueOf(
												(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100)
														/ 100));

							} else {
								if (flag == 1) {
									error_msg = error_msg + ",Price off invalid for STPR/STPR Liquidation";
									query.setString(12, bean.getPrice_off());
									flag = 1;
								} else {
									error_msg = error_msg + " Price off invalid for STPR/STPR Liquidation";
									query.setString(12, bean.getPrice_off());
									flag = 1;
								}

							}

						}

					} else {
						if(isStringNumber(bean.getPrice_off()))
						{
						query.setString(12, bean.getPrice_off().isEmpty() ? ""
								: String.valueOf(
										(double) Math.round(Double.parseDouble(bean.getPrice_off()) * 100) / 100));
						}else
						{
							query.setString(12,bean.getPrice_off());
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
									if (!isMocInFormat(bean.getMoc())
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
												bean.getMoc().substring(4, 6).concat(bean.getMoc().substring(0, 4)),
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
								bean.getMoc().substring(4, 6).concat(bean.getMoc().substring(0, 4)),
								bean.getPpm_account());
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

				if (!validationmap.get("Channel name").contains(bean.getChannel().toUpperCase())) {
					error_msg = error_msg + "Invalid Channel";
					flag = 1;
				}
				if (!validationmap.get("PPM Account").contains(bean.getPpm_account().toUpperCase())
						|| bean.getPpm_account().contains(",")) {

					if (flag == 1)
						error_msg = error_msg + ",Invalid Account";
					else {
						error_msg = error_msg + "Invalid Account";
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

				if (!bean.getMoc().isEmpty()) {
					if (bean.getMoc().length() != 6 || !isMocInFormat(bean.getMoc())) {
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

				if (flag == 1)
					globle_flag = 1;

				query.setString(17, error_msg);
				query.executeUpdate();
				
				error_msg = "";
				flag = 0;

				duplicateMap.put(bean.getPpm_account() + bean.getBasepack_code() + bean.getCluster() + bean.getMoc()
						+ bean.getOffer_desc(), "");

			}
		}

		if (globle_flag == 0) {
			String up_query = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 A INNER JOIN TBL_PROCO_PROMOTION_MASTER_V2  B"
					+ " ON A.MOC=B.MOC AND A.CUSTOMER_CHAIN_L2=B.CUSTOMER_CHAIN_L2 AND A.BASEPACK_CODE=B.BASEPACK_CODE"
					+ " AND A.CLUSTER=B.CLUSTER AND A.OFFER_DESC=B.OFFER_DESC "
					+ " SET A.ERROR_MSG=CONCAT(CONCAT('Duplicate promo entry uploaded by ',B.CREATED_BY),CONCAT(' at ',SUBSTRING(B.CREATED_DATE,1,10)))"
					+ " WHERE A.USER_ID='" + uid + "'";

			// sessionFactory.getCurrentSession().createNativeQuery(up_query).executeUpdate();

			String count_check = "SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE ERROR_MSG LIKE 'Duplicate Promo entry uploaded by%' AND USER_ID='"
					+ uid + "'";
			sessionFactory.getCurrentSession().createNativeQuery(up_query).executeUpdate();
			if (excuteValidationQuery(count_check) > 0) {

				globle_flag = 1;
			}

		}
		if (globle_flag == 0) {

			saveTomainTable(beans, uid, template, promotimemap);
			globle_flag = 0;
			return "EXCEL_UPLOADED";

		} else {
			globle_flag = 0;
			return "EXCEL_NOT_UPLOADED";
		}

	}

	private Map<String, String> getAllTDPTimeperiod() {
		String year = new SimpleDateFormat("yyyy").format(new Date());
		String next_year=String.valueOf(Integer.parseInt(year)+1);
		String stringQuery = "SELECT MOC,TDP,START_DATE,END_DATE FROM TBL_VAT_MOC_TDP_MASTER WHERE MOC_YEAR in ('" + year
				+ "', '"+next_year+"')";
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

	private Map<String, ArrayList<String>> getAllValidationRecords() {
		Map<String, ArrayList<String>> validationmap = new HashMap<String, ArrayList<String>>();
		validationmap.put("SOL TYPE", getSOLType());
		validationmap.put("Channel name", getValidChannels());
		validationmap.put("PPM Account", getValidPPMAccount());
		// validationmap.put("AB creation", getABCreation());
		validationmap.put("baseback", getValidBasepack());
		validationmap.put("offer type", getValidOfferType());
		validationmap.put("Offer modality", getValidOfferModality());
		// validationmap.put("branch", getValidBranch());
		// validationmap.put("Secondary", getValidSec());
		validationmap.put("cluster", getValidCluster());
		return validationmap;
	}

	private ArrayList<String> getSOLType() {
		String SOLType = "SELECT DISTINCT SOL_TYPE FROM TBL_PROCO_SOL_TYPE";

		return (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(SOLType).list();
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

	private void saveTomainTable(CreateBeanRegular[] beans, String uid, String template,
			Map<String, String> promotimemap) {

		Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROMO_TABLE);
		Map<String, String> branchmap = getValidBranch();
		Map<String, String> secondary = getValidSecondaryChannel();
		Map<String, String> ab = getValidAbcreation();
		query.setString(33, uid);
		String cur_date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		try {
			query.setParameter(34, new SimpleDateFormat("dd/MM/yyyy").parse(cur_date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // "STR_TO_DATE("+new SimpleDateFormat("dd/MM/yyyy").format(new
			// Date()).toString()+",'%d/%m/%Y')");
		Map<String, String> dupmap = new HashMap<String, String>();

		for (CreateBeanRegular bean : beans) {
			if (!dupmap.containsKey(bean.getPpm_account() + bean.getBasepack_code() + bean.getCluster() + bean.getMoc()
					+ bean.getOffer_desc())) {
				query.setString(0, bean.getChannel());
				query.setString(1, bean.getMoc());

				query.setString(3, bean.getPpm_account());
				query.setString(4, bean.getPromo_time_period());
				query.setString(6, bean.getBasepack_code());
				query.setString(7, bean.getBaseback_desc());
				query.setString(8, bean.getC_pack_code());
				query.setString(9, bean.getOffer_desc());
				query.setString(10, bean.getOfr_type());
				query.setString(11, bean.getOffer_mod());
				//Mayur changes for handling round off value for price off
				if (bean.getPrice_off().isEmpty()) {
					query.setString(12, "");
				} else {
					if(bean.getPrice_off().endsWith("%") && !isStringNumber(bean.getPrice_off().split("%")[0]))
					{
						query.setString(12,bean.getPrice_off());
					}else
					{
						if(isStringNumber(bean.getPrice_off()) && !bean.getPrice_off().endsWith("%") )
						{
							query.setString(12,String.valueOf((Long) Math.round(Double.parseDouble(bean.getPrice_off()) * 100) / 100));
						}else
						{
							if(isStringNumber(bean.getPrice_off().split("%")[0]))
							{
								query.setString(12,String.valueOf((Long) Math.round(Double.parseDouble(bean.getPrice_off().split("%")[0]) * 100) / 100)+"%");
							}else
							{
							query.setString(12,bean.getPrice_off());
							}
						}
					}
//					query.setString(12, bean.getPrice_off().endsWith("%") ? String.valueOf(
//							(Long) Math.round(Double.parseDouble(bean.getPrice_off().split("%")[0]) * 100) / 100) + "%"
//							: String.valueOf((Long) Math.round(Double.parseDouble(bean.getPrice_off()) * 100) / 100));
				}
				if(isStringNumber(bean.getBudget()))
				query.setString(13, bean.getBudget().isEmpty() ? ""
						: String.valueOf((double) Math.round(Double.parseDouble(bean.getBudget()) * 100) / 100));
				else
					query.setString(13,bean.getBudget());
				query.setString(14, branchmap.get(bean.getCluster().toUpperCase()));
				query.setString(15, bean.getCluster());
				query.setString(16, uid);
				query.setString(21, template);
				query.setString(23, "1");
				query.setString(24, "1");

				if (template.equalsIgnoreCase("new") || template.equalsIgnoreCase("regular")) {
					if (template.equalsIgnoreCase("new"))
						query.setString(22, bean.getQuantity());
					else
						query.setString(22, "");

					query.setString(2, secondary.get(bean.getPpm_account().toUpperCase()));
					query.setString(5, ab.get(bean.getPpm_account().toUpperCase()));
					query.setString(25, "");
					query.setString(26, "");
					query.setString(27, "");
					query.setString(28, "");
					query.setString(29, "");
					query.setString(30, "");
					query.setString(31, "");
					query.setString(32, "");
					// mayur changes for promo time period

					if (bean.getPromo_time_period().equalsIgnoreCase("bm")
							|| bean.getPromo_time_period().equalsIgnoreCase("moc")
							|| bean.getPromo_time_period().equalsIgnoreCase("26 to 25")) {

						query.setString(19,
								promotimemap.get(bean.getMoc() + bean.getPromo_time_period() + "start_date"));
						query.setString(20, promotimemap.get(bean.getMoc() + bean.getPromo_time_period() + "end_date"));

					} else {
						String converted_moc = bean.getMoc().length() == 6
								? bean.getMoc().substring(4, bean.getMoc().length()) + bean.getMoc().substring(0, 4)
								: bean.getMoc();
						query.setString(19, promotimemap
								.get(converted_moc + bean.getPromo_time_period().toUpperCase() + "start_date"));
						query.setString(20, promotimemap
								.get(converted_moc + bean.getPromo_time_period().toUpperCase() + "end_date"));

					}
					// mayur End

				} else if (template.equalsIgnoreCase("cr")) {
					query.setString(22, bean.getQuantity());

					if (bean.getSol_type().equalsIgnoreCase("_DE_")) {
						try {

							if (isPromoTimeisValid(bean.getEnd_date())) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
								SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
								String new_date = sdf2.format(sdf.parse(bean.getEnd_date().replace("/", "-")));
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
								query.setString(20, bean.getEnd_date());
							} else {
								query.setString(19, "");

								query.setString(20, "");
							}

						} catch (Exception e) {
							logger.debug("Exception: ", e);

						}
					} else {
						String new_moc = bean.getMoc();

						List<Object[]> se_date = getStartEndDate(
								new_moc.substring(4, 6).concat(new_moc.substring(0, 4)), bean.getPpm_account());

						query.setString(19, se_date.get(0)[0].toString()); // start date
						query.setString(20, se_date.get(0)[1].toString()); // end date
					}
					query.setString(2, bean.getSecondary_channel());
					query.setString(5, bean.getAb_creation());
					query.setString(25, bean.getSol_type());
					query.setString(26, bean.getEnd_date());
					query.setString(27, bean.getCluster_selection());
					query.setString(28, bean.getBasepack_addition());
					query.setString(29, bean.getTopup());
					query.setString(30, bean.getAdditional_QTY());
					query.setString(31, bean.getAddition_budget());
					query.setString(32, bean.getExisting_sol_code());

				}

				if (promo_map.containsKey(bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc()) && promo_map
						.containsKey("Pid_max_" + bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc())) {
					query.setString(17, promo_map.get(bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc()));
					query.setString(18,
							promo_map.get("Pid_max_" + bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc()));
				} else {

					String new_pid = getPID(bean.getMoc());
					String new_promo_id = createNewPromoId(bean.getMoc()) + new_pid;
					promo_map.put(bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc(), new_promo_id);
					promo_map.put("Pid_max_" + bean.getMoc() + bean.getPpm_account() + bean.getOffer_desc(), new_pid);
					query.setString(17, new_promo_id);
					query.setString(18, new_pid);
				}
				/*
				 * List<Object[]> promo_list = getPromoId(bean.getMoc(), bean.getPpm_account(),
				 * bean.getOffer_desc());
				 * 
				 * if (promo_list == null || promo_list.size() == 0) { query.setString(17,
				 * createNewPromoId(bean.getMoc())); query.setString(18, getPID(bean.getMoc()));
				 * } else { for (int i = 0; i < promo_list.size(); i++) { query.setString(17,
				 * promo_list.get(i)[0].toString()); query.setString(18,
				 * promo_list.get(i)[1].toString()); // getting same PID } }
				 */
				query.executeUpdate();
				dupmap.put(bean.getPpm_account() + bean.getBasepack_code() + bean.getCluster() + bean.getMoc()
						+ bean.getOffer_desc(), "");
			}
		}
	}

	private String createNewPromoId(String moc) {

		return "PID_" + moc + "_";
	}

	private String getPID(String moc) {
		String pid = sessionFactory.getCurrentSession().createNativeQuery(Pid).setString(0, moc).uniqueResult()
				.toString();

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

	private ArrayList<String> getValidCluster() {
		String cluster_query = "SELECT DISTINCT CLUSTER FROM TBL_PROCO_CUSTOMER_MASTER WHERE ACTIVE='1'";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(cluster_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	private Map<String, String> getValidBranch() {

		Map<String, String> map = new HashMap<String, String>();
		ArrayList<String> clusters = getValidCluster();
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

	private ArrayList<String> getValidOfferModality() {
		String mod_query = "SELECT DISTINCT MODALITY_NAME FROM TBL_PROCO_OFFER_MODALITY_MASTER WHERE ACTIVE='1'";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(mod_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	private ArrayList<String> getValidOfferType() {
		String str_query = "SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_OFFER_TYPE_V2 WHERE ACTIVE='1'";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(str_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	private ArrayList<String> getValidBasepack() {
		String basepack = "SELECT DISTINCT BASEPACK FROM TBL_VAT_PRODUCT_MASTER WHERE STATUS='Y'";
		return (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(basepack).list();
	}

	private Map<String, String> getValidAbcreation() {

		Map<String, String> map = new HashMap<String, String>();

		ArrayList<String> ppmaccounts = getValidPPMAccount();

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

		ArrayList<String> ppmaccounts = getValidPPMAccount();
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

	private ArrayList<String> getValidPPMAccount() {
		String ppm_qury = "SELECT DISTINCT PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE  IS_ACTIVE='Y'";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(ppm_qury)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());

	}

	private ArrayList<String> getValidChannels() {

		String query = "SELECT DISTINCT CHANNEL_NAME FROM TBL_PROCO_CHANNEL_MASTER";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(query).list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
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
			String tdpQry=" SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";

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
		List<String> tdpHeaders =new ArrayList<String>();

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
			String tdpQry=" SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";

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
			String tdpQry=" SELECT DISTINCT TDP FROM TBL_VAT_MOC_TDP_MASTER";


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
			} else if (error_template.equalsIgnoreCase("Regular")) {
				if (roleid.equalsIgnoreCase("dp"))
					qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,CLUSTER,QUANTITY,TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID',ERROR_MSG as 'ERROR MESSAGE' "
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";
				else
					qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,CLUSTER,TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID',ERROR_MSG as 'ERROR MESSAGE' "
							+ " FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=?0";

			} else if (error_template.equalsIgnoreCase("new")) {
				qry = "SELECT CHANNEL_NAME,MOC,CUSTOMER_CHAIN_L2 as 'PPM ACCOUNT',PROMO_TIMEPERIOD as 'PROMO TIMEPERIOD',BASEPACK_CODE as 'BASEPACK CODE',BASEPACK_DESC as 'BASEPACK DESCRIPTION',CHILD_BASEPACK_CODE as 'CHILDPACK CODE',OFFER_DESC as 'OFFER DESCRIPTION',OFFER_TYPE as 'OFFER TYPE',OFFER_MODALITY as 'OFFER MODALITY',PRICE_OFF as 'PRICE OFF',BUDGET,CLUSTER,QUANTITY,TEMPLATE_TYPE as 'TEMPLATE TYPE',USER_ID as 'USER ID',ERROR_MSG as 'ERROR MESSAGE' "
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
