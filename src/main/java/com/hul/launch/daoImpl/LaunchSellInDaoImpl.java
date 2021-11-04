package com.hul.launch.daoImpl;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hul.launch.dao.LaunchBasePacksDao;
import com.hul.launch.dao.LaunchDao;
import com.hul.launch.dao.LaunchSellInDao;
import com.hul.launch.dao.LaunchVisiPlanDao;
import com.hul.launch.model.LaunchSellIn;
import com.hul.launch.model.LaunchStoreData;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.DownloadSellInRequestList;
import com.hul.launch.request.SaveErroredLaunchSellInList;
import com.hul.launch.request.SaveErroredLaunchSellInRequest;
import com.hul.launch.request.SaveLaunchSellInRequest;
import com.hul.launch.request.SaveLaunchSellInRequestList;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchSellInEditReponse;
import com.hul.launch.response.LaunchSellInReponse;
import com.hul.launch.response.SellInResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchSellInDaoImpl implements LaunchSellInDao {

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private LaunchBasePacksDao launchBasePacksDao;

	@Autowired
	private LaunchVisiPlanDao launchVisiPlanDao;
	

	@Autowired
	LaunchDao launchDao;

	private final static String TBL_LAUNCH_SELLIN = "INSERT INTO TBL_LAUNCH_SELLIN (SELLIN_LAUNCH_ID,SELLIN_L1_CHAIN,SELLIN_L2_CHAIN,"
			+ "SELLIN_STORE_FORMAT,SELLIN_STORES_PLANNED,SELLIN_SKU1,SELLIN_SKU2,SELLIN_SKU3,SELLIN_SKU4,SELLIN_SKU5,SELLIN_SKU6,"
			+ "SELLIN_SKU7,SELLIN_SKU8,SELLIN_SKU9,SELLIN_SKU10,SELLIN_SKU11,SELLIN_SKU12,SELLIN_SKU13,SELLIN_SKU14,SELLIN_ROTATIONS,"
			+ "SELLIN_UPLIFT_N1,SELLIN_UPLIFT_N2,CREATED_BY,CREATED_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String TBL_LAUNCH_SELLIN_TEMP = "INSERT INTO TBL_LAUNCH_TEMP_SELLIN (SELLIN_LAUNCH_ID,SELLIN_L1_CHAIN,SELLIN_L2_CHAIN,"
			+ "SELLIN_STORE_FORMAT,SELLIN_STORES_PLANNED,SELLIN_SKU1,SELLIN_SKU2,SELLIN_SKU3,SELLIN_SKU4,SELLIN_SKU5,SELLIN_SKU6,"
			+ "SELLIN_SKU7,SELLIN_SKU8,SELLIN_SKU9,SELLIN_SKU10,SELLIN_SKU11,SELLIN_SKU12,SELLIN_SKU13,SELLIN_SKU14,SELLIN_ROTATIONS,"
			+ "SELLIN_UPLIFT_N1,SELLIN_UPLIFT_N2,CREATED_BY,CREATED_DATE,ERROR_MSG) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public String saveLaunchSellIn(SaveLaunchSellInRequestList saveLaunchSellInRequestList, String userId) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID=?");
			batchUpdate.setString(1, saveLaunchSellInRequestList.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveLaunchSellInRequest> listSellIn = saveLaunchSellInRequestList.getListOfSellIns();
			for (SaveLaunchSellInRequest saveLaunchSellInRequest : listSellIn) {
				try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_SELLIN,
						Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, saveLaunchSellInRequestList.getLaunchId());
					preparedStatement.setString(2, saveLaunchSellInRequest.getL1Chain());
					preparedStatement.setString(3, saveLaunchSellInRequest.getL2Chain());
					preparedStatement.setString(4, saveLaunchSellInRequest.getStoreFormat());
					preparedStatement.setString(5, saveLaunchSellInRequest.getStoresPlanned());
					preparedStatement.setString(6, saveLaunchSellInRequest.getSku1());
					preparedStatement.setString(7, saveLaunchSellInRequest.getSku2());
					preparedStatement.setString(8, saveLaunchSellInRequest.getSku3());
					preparedStatement.setString(9, saveLaunchSellInRequest.getSku4());
					preparedStatement.setString(10, saveLaunchSellInRequest.getSku5());
					preparedStatement.setString(11, saveLaunchSellInRequest.getSku6());
					preparedStatement.setString(12, saveLaunchSellInRequest.getSku7());
					preparedStatement.setString(13, saveLaunchSellInRequest.getSku8());
					preparedStatement.setString(14, saveLaunchSellInRequest.getSku9());
					preparedStatement.setString(15, saveLaunchSellInRequest.getSku10());
					preparedStatement.setString(16, saveLaunchSellInRequest.getSku11());
					preparedStatement.setString(17, saveLaunchSellInRequest.getSku12());
					preparedStatement.setString(18, saveLaunchSellInRequest.getSku13());
					preparedStatement.setString(19, saveLaunchSellInRequest.getSku14());
					preparedStatement.setString(20, saveLaunchSellInRequest.getRotations());
					preparedStatement.setString(21, saveLaunchSellInRequest.getUpliftN1());
					preparedStatement.setString(22, saveLaunchSellInRequest.getUpliftN2());
					preparedStatement.setString(23, userId);
					preparedStatement.setTimestamp(24, new Timestamp(new Date().getTime()));
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
				}
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Saved Successfully";
	}

	@SuppressWarnings("unchecked")
	@Override
	public LaunchSellInReponse getLaunchSellInDetails(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		LaunchSellInReponse launchSellInReponse = new LaunchSellInReponse();
		List<SellInResponse> liLaunchCluster = new ArrayList<>();
		List<String> listOfBpDesc = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean iIncludeAllSores = false; //Sarin Changes - Q1Sprint Feb2021
		LaunchDataResponse launchDataResponse = launchDao.getSpecificLaunchData(launchId);
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT * FROM TBL_LAUNCH_CLUSTERS tlc WHERE CREATED_BY = :userId AND LAUNCH_PLANNED = 'Yes' and CLUSTER_LAUNCH_ID = :launchId");
			queryToGetCustomeChainL1.setParameter("userId", userId);
			queryToGetCustomeChainL1.setParameter("launchId", launchId);
			Object[] obj = (Object[]) queryToGetCustomeChainL1.list().get(0);
			String[] accounts = {};
			List<String> account = new ArrayList<>();
			List<String> accountL2 = new ArrayList<>();
			if (!obj[3].equals("ALL CUSTOMERS")) {
				accounts = obj[3].toString().split(",");
			} else {
				//Garima - chnages for concatenation
				Query allCustL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT CONCAT(ACCOUNT_NAME , ':' , DP_CHAIN) AS ACCOUNTCHAIN FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != ''");
				//		"SELECT DISTINCT (ACCOUNT_NAME || ':' || DP_CHAIN) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != ''");
				List<String> allL1 = allCustL1.list();
				accounts = allL1.toArray(new String[allL1.size()]);
			}
			
			iIncludeAllSores = (obj[12].toString().equalsIgnoreCase("Yes")) ? true: false;  //Sarin Changes - Q1Sprint Feb2021 - Starts
			String[] splittedString = obj[2].toString().split(",");
			List<String> liClusterName = new ArrayList<>();
			for (String string2 : splittedString) {
				if (null != string2) {
					if (string2.split(":").length > 1) {
						liClusterName.add(string2.split(":")[1]);
					} else {
						liClusterName.add("ALL INDIA");
					}
				}
			}

			for (String string2 : accounts) {
				if (null != string2) {
					String[] custData = string2.split(":");
					if (custData.length != 0) {
						account.add(string2.split(":")[0]);
						if (custData.length == 1) {
							accountL2.add("");
						} else {
							accountL2.add(string2.split(":")[1]);
						}
					}
				}
			}
			List<String> listOfCustStores = Arrays.asList(obj[5].toString().trim().split(","));
			List<String> listOfStores = Arrays.asList(obj[4].toString().trim().split(","));
			if (obj[4].toString().trim().length() != 0 && obj[5].toString().trim().length() == 0) {
				for (int i = 0; i < account.size(); i++) {
					Query formatListQ = null;
					if (!account.get(i).equals("")) {
						String finalQuery = "SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm  WHERE ACCOUNT_NAME = :account AND DP_CHAIN = :accountL2 and ACCOUNT_NAME != '' "
								+ " and UPPER(CURRENT_STORE_FORMAT) IN (:storeFormat)";
						formatListQ = sessionFactory.getCurrentSession().createNativeQuery(finalQuery);
						formatListQ.setParameter("account", account.get(i));
						formatListQ.setParameter("accountL2", accountL2.get(i));
						formatListQ.setParameterList("storeFormat", listOfStores);
						List<Object> listOfCust = formatListQ.list();
						List<String> strings = listOfCust.stream().map(object -> Objects.toString(object, null))
								.collect(Collectors.toList());

						String[] storeFormats = {};

						if (!obj[4].equals("")) {
							storeFormats = obj[4].toString().split(",");
						} else {
							storeFormats = strings.toArray(new String[strings.size()]);
						}

						for (String storeFormat : storeFormats) {
							if (strings.contains(storeFormat)) {
								SellInResponse sellInResponse = new SellInResponse();
								List<String> listOfL1 = new ArrayList<>();
								String accountL1 = "";
								String accountDataL2 = "";
								if (accounts[i].contains(":")) {
									listOfL1.add(accounts[i].split(":")[0]);
									accountL1 = accounts[i].split(":")[0];
									String[] accArr = accounts[i].split(":");
									if (accArr.length == 1) {
										accountDataL2 = "";
									} else {
										accountDataL2 = accounts[i].split(":")[1];
									}
								} else {
									accountL1 = accounts[i];
									accountDataL2 = accounts[i];
									listOfL1.add(accounts[i]);
								}

								List<String> l1List = new ArrayList<>();
								List<String> l2List = new ArrayList<>();
								l1List.add(accountL1);
								l2List.add(accountDataL2);
								String countOfStores = launchBasePacksDao.getStoreCountOnStore(storeFormat, l1List,
										l2List, liClusterName, launchDataResponse.getClassification(), iIncludeAllSores);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
								if (!countOfStores.equals("0")) {
									sellInResponse.setStoresPlanned(countOfStores);
									sellInResponse.setL1Chain(accountL1);
									sellInResponse.setL2Chain(accountDataL2);
									sellInResponse.setStoreFormat(storeFormat);
									
									//Added By Sarin - sprint4Aug2021 - Starts
									Object[] objSkuSellIn = getSkuSellInDetails(launchId, accountL1, accountDataL2, storeFormat);
									if (objSkuSellIn != null) {
										sellInResponse.setSKU1_SELLIN(objSkuSellIn[0].toString());
										sellInResponse.setSKU2_SELLIN(objSkuSellIn[1].toString());
										sellInResponse.setSKU3_SELLIN(objSkuSellIn[2].toString());
										sellInResponse.setSKU4_SELLIN(objSkuSellIn[3].toString());
										sellInResponse.setSKU5_SELLIN(objSkuSellIn[4].toString());
										sellInResponse.setSKU6_SELLIN(objSkuSellIn[5].toString());
										sellInResponse.setSKU7_SELLIN(objSkuSellIn[6].toString());
										sellInResponse.setSKU8_SELLIN(objSkuSellIn[7].toString());
										sellInResponse.setSKU9_SELLIN(objSkuSellIn[8].toString());
										sellInResponse.setSKU10_SELLIN(objSkuSellIn[9].toString());
										sellInResponse.setSKU11_SELLIN(objSkuSellIn[10].toString());
										sellInResponse.setSKU12_SELLIN(objSkuSellIn[11].toString());
										sellInResponse.setSKU13_SELLIN(objSkuSellIn[12].toString());
										sellInResponse.setSKU14_SELLIN(objSkuSellIn[13].toString());
										sellInResponse.setROTATIONS(objSkuSellIn[14].toString());
										sellInResponse.setUPLIFT_N1(objSkuSellIn[15].toString());
										sellInResponse.setUPLIFT_N2(objSkuSellIn[16].toString());
									}
									//Added By Sarin - sprint4Aug2021 - Ends
									
									liLaunchCluster.add(sellInResponse);
								}
							}
						}
					} else {
						throw new Exception("Something went wrong");
					}
				}
			} else if (obj[4].toString().trim().length() == 0 && obj[5].toString().trim().length() != 0) {
				for (int i = 0; i < account.size(); i++) {
					Query formatListQ = null;
					if (!account.get(i).equals("")) {
						String finalQuery = "SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm  WHERE ACCOUNT_NAME = :account AND DP_CHAIN = :accountL2 and ACCOUNT_NAME != '' and CUSTOMER_STORE_FORMAT IN (:custStoreFormat)";

						formatListQ = sessionFactory.getCurrentSession().createNativeQuery(finalQuery);
						formatListQ.setParameter("account", account.get(i));
						formatListQ.setParameter("accountL2", accountL2.get(i));
						formatListQ.setParameterList("custStoreFormat", listOfCustStores);

						List<Object> listOfCust = formatListQ.list();
						List<String> strings = listOfCust.stream().map(object -> Objects.toString(object, null))
								.collect(Collectors.toList());

						String[] storeFormats = {};

						if (!obj[4].equals("")) {
							storeFormats = obj[4].toString().split(",");
						} else {
							storeFormats = strings.toArray(new String[strings.size()]);
						}

						for (String storeFormat : storeFormats) {
							if (strings.contains(storeFormat)) {
								SellInResponse sellInResponse = new SellInResponse();
								List<String> listOfL1 = new ArrayList<>();
								String accountL1 = "";
								String accountDataL2 = "";
								if (accounts[i].contains(":")) {
									listOfL1.add(accounts[i].split(":")[0]);
									accountL1 = accounts[i].split(":")[0];
									String[] accArr = accounts[i].split(":");
									if (accArr.length == 1) {
										accountDataL2 = "";
									} else {
										accountDataL2 = accounts[i].split(":")[1];
									}
								} else {
									accountL1 = accounts[i];
									accountDataL2 = accounts[i];
									listOfL1.add(accounts[i]);
								}

								List<String> l1List = new ArrayList<>();
								List<String> l2List = new ArrayList<>();
								l1List.add(accountL1);
								l2List.add(accountDataL2);
								String countOfStores = launchBasePacksDao.getStoreCountOnCustSellIIn(storeFormat,
										liClusterName, launchDataResponse.getClassification(), iIncludeAllSores);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
								if (!countOfStores.equals("0")) {
									sellInResponse.setStoresPlanned(countOfStores);
									sellInResponse.setL1Chain(accountL1);
									sellInResponse.setL2Chain(accountDataL2);
									sellInResponse.setStoreFormat(storeFormat);
									liLaunchCluster.add(sellInResponse);
								}
							}
						}
					} else {
						throw new Exception("Something went wrong");
					}
				}
			} else {
				for (int i = 0; i < account.size(); i++) {
					Query formatListQ = null;
					if (!account.get(i).equals("")) {
						String finalQuery = "SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm  WHERE ACCOUNT_NAME = :account AND DP_CHAIN = :accountL2 and ACCOUNT_NAME != ''";

						formatListQ = sessionFactory.getCurrentSession().createNativeQuery(finalQuery);
						formatListQ.setParameter("account", account.get(i));
						formatListQ.setParameter("accountL2", accountL2.get(i));

						List<Object> listOfCust = formatListQ.list();
						List<String> strings = listOfCust.stream().map(object -> Objects.toString(object, null))
								.collect(Collectors.toList());

						String[] storeFormats = {};
						storeFormats = strings.toArray(new String[strings.size()]);
						for (String storeFormat : storeFormats) {
							if (strings.contains(storeFormat)) {
								SellInResponse sellInResponse = new SellInResponse();
								List<String> listOfL1 = new ArrayList<>();
								String accountL1 = "";
								String accountDataL2 = "";
								if (accounts[i].contains(":")) {
									listOfL1.add(accounts[i].split(":")[0]);
									accountL1 = accounts[i].split(":")[0];
									String[] accArr = accounts[i].split(":");
									if (accArr.length == 1) {
										accountDataL2 = "";
									} else {
										accountDataL2 = accounts[i].split(":")[1];
									}
								} else {
									accountL1 = accounts[i];
									accountDataL2 = accounts[i];
									listOfL1.add(accounts[i]);
								}
								List<String> l1List = new ArrayList<>();
								List<String> l2List = new ArrayList<>();
								l1List.add(accountL1);
								l2List.add(accountDataL2);
								String storeCount = launchBasePacksDao.getStoreCountOnStore(storeFormat, l1List, l2List,
										liClusterName, launchDataResponse.getClassification(), iIncludeAllSores);  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection

								if (!storeCount.equals("0")) {
									sellInResponse.setL1Chain(accountL1);
									sellInResponse.setL2Chain(accountDataL2);
									sellInResponse.setStoreFormat(storeFormat);
									sellInResponse.setStoresPlanned(storeCount);
									
									//Added By Sarin - sprint4Aug2021 - Starts
									Object[] objSkuSellIn = getSkuSellInDetails(launchId, accountL1, accountDataL2, storeFormat);
									if (objSkuSellIn != null) {
										sellInResponse.setSKU1_SELLIN(objSkuSellIn[0].toString());
										sellInResponse.setSKU2_SELLIN(objSkuSellIn[1].toString());
										sellInResponse.setSKU3_SELLIN(objSkuSellIn[2].toString());
										sellInResponse.setSKU4_SELLIN(objSkuSellIn[3].toString());
										sellInResponse.setSKU5_SELLIN(objSkuSellIn[4].toString());
										sellInResponse.setSKU6_SELLIN(objSkuSellIn[5].toString());
										sellInResponse.setSKU7_SELLIN(objSkuSellIn[6].toString());
										sellInResponse.setSKU8_SELLIN(objSkuSellIn[7].toString());
										sellInResponse.setSKU9_SELLIN(objSkuSellIn[8].toString());
										sellInResponse.setSKU10_SELLIN(objSkuSellIn[9].toString());
										sellInResponse.setSKU11_SELLIN(objSkuSellIn[10].toString());
										sellInResponse.setSKU12_SELLIN(objSkuSellIn[11].toString());
										sellInResponse.setSKU13_SELLIN(objSkuSellIn[12].toString());
										sellInResponse.setSKU14_SELLIN(objSkuSellIn[13].toString());
										sellInResponse.setROTATIONS(objSkuSellIn[14].toString());
										sellInResponse.setUPLIFT_N1(objSkuSellIn[15].toString());
										sellInResponse.setUPLIFT_N2(objSkuSellIn[16].toString());
									}
									//Added By Sarin - sprint4Aug2021 - Ends
									
									liLaunchCluster.add(sellInResponse);
								}
							}
						}
					} else {
						throw new Exception("Something went wrong");
					}
				}
			}

			stmt = sessionImpl.connection().prepareStatement(
					"SELECT BP_DESCRIPTION FROM TBL_LAUNCH_BASEPACK WHERE LAUNCH_ID = '" + launchId + "'");

			rs = stmt.executeQuery();
			while (rs.next()) {
				listOfBpDesc.add(rs.getString("BP_DESCRIPTION"));
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			SellInResponse sellInResponse = new SellInResponse();
			sellInResponse.setError(ex.toString());
			liLaunchCluster.add(sellInResponse);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		launchSellInReponse.setSellInRecords(liLaunchCluster);
		launchSellInReponse.setBasepacksCreated(listOfBpDesc);
		return launchSellInReponse;
	}

	@Override
	public List<ArrayList<String>> getSellInDump(String userId, DownloadSellInRequestList downloadLaunchSellInRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT BP_DESCRIPTION FROM TBL_LAUNCH_BASEPACK WHERE LAUNCH_ID = '"
							+ downloadLaunchSellInRequest.getLaunchId() + "'");
			List<String> bpDesc = new ArrayList<>();
			rs = stmt.executeQuery();
			while (rs.next()) {
				bpDesc.add(rs.getString("BP_DESCRIPTION"));
			}

			ArrayList<String> headerDetail = new ArrayList<>();

			headerDetail.add("L1_CHAIN");
			headerDetail.add("L2_CHAIN");
			headerDetail.add("STORE_FORMAT");
			headerDetail.add("STORES_PLANNED");
			headerDetail.add("SKU1_SELLIN");
			headerDetail.add("SKU2_SELLIN");
			headerDetail.add("SKU3_SELLIN");
			headerDetail.add("SKU4_SELLIN");
			headerDetail.add("SKU5_SELLIN");
			headerDetail.add("SKU6_SELLIN");
			headerDetail.add("SKU7_SELLIN");
			headerDetail.add("SKU8_SELLIN");
			headerDetail.add("SKU9_SELLIN");
			headerDetail.add("SKU10_SELLIN");
			headerDetail.add("SKU11_SELLIN");
			headerDetail.add("SKU12_SELLIN");
			headerDetail.add("SKU13_SELLIN");
			headerDetail.add("SKU14_SELLIN");
			headerDetail.add("ROTATIONS");
			headerDetail.add("UPLIFT_N1");
			headerDetail.add("UPLIFT_N2");
			downloadDataList.add(headerDetail);
			LaunchSellInReponse launchSellInResponse = getLaunchSellInDetails(downloadLaunchSellInRequest.getLaunchId(),
					userId);
			List<SellInResponse> listOfSellIns = launchSellInResponse.getSellInRecords();
			for (int i = 0; i < listOfSellIns.size(); i++) {
				SellInResponse sellInRequest = listOfSellIns.get(i);
				ArrayList<String> dataObj = new ArrayList<>();
				dataObj.add(sellInRequest.getL2Chain());
				dataObj.add(sellInRequest.getL1Chain());
				dataObj.add(sellInRequest.getStoreFormat());
				dataObj.add(sellInRequest.getStoresPlanned());
				dataObj.add(sellInRequest.getSKU1_SELLIN());
				dataObj.add(sellInRequest.getSKU2_SELLIN());
				dataObj.add(sellInRequest.getSKU3_SELLIN());
				dataObj.add(sellInRequest.getSKU4_SELLIN());
				dataObj.add(sellInRequest.getSKU5_SELLIN());
				dataObj.add(sellInRequest.getSKU6_SELLIN());
				dataObj.add(sellInRequest.getSKU7_SELLIN());
				dataObj.add(sellInRequest.getSKU8_SELLIN());
				dataObj.add(sellInRequest.getSKU9_SELLIN());
				dataObj.add(sellInRequest.getSKU10_SELLIN());
				dataObj.add(sellInRequest.getSKU11_SELLIN());
				dataObj.add(sellInRequest.getSKU12_SELLIN());
				dataObj.add(sellInRequest.getSKU13_SELLIN());
				dataObj.add(sellInRequest.getSKU14_SELLIN());
				dataObj.add(sellInRequest.getROTATIONS());
				dataObj.add(sellInRequest.getUPLIFT_N1());
				dataObj.add(sellInRequest.getUPLIFT_N2());
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public LaunchSellInEditReponse getSellInByLaunchId(String launchId) {
		List<LaunchSellIn> listOfSellIn = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LaunchSellInEditReponse launchSellInEditReponse = new LaunchSellInEditReponse();
		try {
			List<String> listOfBpDesc = new ArrayList<>();
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT BP_DESCRIPTION FROM TBL_LAUNCH_BASEPACK WHERE LAUNCH_ID = '" + launchId + "'");

			rs = stmt.executeQuery();
			while (rs.next()) {
				listOfBpDesc.add(rs.getString("BP_DESCRIPTION"));
			}
			launchSellInEditReponse.setBasepacksCreated(listOfBpDesc);
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT * FROM TBL_LAUNCH_SELLIN WHERE SELLIN_LAUNCH_ID = :launchId");
			query.setParameter("launchId", launchId);
			Iterator<Object> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchSellIn launchSellIn = new LaunchSellIn();
				launchSellIn.setFile(null);
				launchSellIn.setSellInId(Integer.parseInt(obj[0].toString()));
				launchSellIn.setL1_CHAIN(obj[2].toString());
				launchSellIn.setL2_CHAIN(obj[3].toString());
				launchSellIn.setSTORE_FORMAT(obj[4].toString());
				launchSellIn.setSTORES_PLANNED(obj[5].toString());
				launchSellIn.setSKU1_SELLIN(obj[6].toString());
				launchSellIn.setSKU2_SELLIN(null == obj[7] ? "" : obj[7].toString());
				launchSellIn.setSKU3_SELLIN(null == obj[8] ? "" : obj[8].toString());
				launchSellIn.setSKU4_SELLIN(null == obj[9] ? "" : obj[9].toString());
				launchSellIn.setSKU5_SELLIN(null == obj[10] ? "" : obj[10].toString());
				launchSellIn.setSKU6_SELLIN(null == obj[11] ? "" : obj[11].toString());
				launchSellIn.setSKU7_SELLIN(null == obj[12] ? "" : obj[12].toString());
				launchSellIn.setSKU8_SELLIN(null == obj[13] ? "" : obj[13].toString());
				launchSellIn.setSKU9_SELLIN(null == obj[14] ? "" : obj[14].toString());
				launchSellIn.setSKU10_SELLIN(null == obj[15] ? "" : obj[15].toString());
				launchSellIn.setSKU11_SELLIN(null == obj[16] ? "" : obj[16].toString());
				launchSellIn.setSKU12_SELLIN(null == obj[17] ? "" : obj[17].toString());
				launchSellIn.setSKU13_SELLIN(null == obj[18] ? "" : obj[18].toString());
				launchSellIn.setSKU14_SELLIN(null == obj[19] ? "" : obj[19].toString());
				launchSellIn.setROTATIONS(obj[20].toString());
				launchSellIn.setUPLIFT_N1(obj[21].toString());
				launchSellIn.setUPLIFT_N2(obj[22].toString());
				listOfSellIn.add(launchSellIn);
			}
			launchSellInEditReponse.setLaunchSellInEditResponse(listOfSellIn);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return launchSellInEditReponse;
	}

	@Override
	public double getSellInForSellInN(int launchId, String Data) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		double toReturn = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement("SELECT SUM(SELLIN_STORES_PLANNED * " + Data
					+ " * SELLIN_ROTATIONS) SELLIN_UNITS_FOR_N FROM TBL_LAUNCH_SELLIN tls WHERE SELLIN_LAUNCH_ID = '"
					+ launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				toReturn = rs.getInt(1);
			}
			return toReturn;
		} catch (Exception e) {
			return 0;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public double getTotalUplift(String launchId, String whichUplift, String skuName) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int toReturn = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT SUM(SELLIN_STORES_PLANNED * " + skuName + " * SELLIN_ROTATIONS"
							+ whichUplift + ") TOTAL_UPLIFT FROM TBL_LAUNCH_SELLIN tls WHERE SELLIN_LAUNCH_ID = '"
							+ launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				toReturn = rs.getInt(1);
			}
			return toReturn;
		} catch (Exception e) {
			return 0;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getRevisedVisiSellInForSellInN(int launchId, String Data, List<String> visiSkuCal) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<String> listOfSellInCal = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement("SELECT SELLIN_STORES_PLANNED * " + Data
					+ " * SELLIN_ROTATIONS SELLIN_UNITS_FOR_N FROM TBL_LAUNCH_SELLIN tls WHERE SELLIN_LAUNCH_ID = '"
					+ launchId + "'");
			rs = stmt.executeQuery();
			int temp = 0;
			while (rs.next()) {
				listOfSellInCal.add(Double.toString(rs.getDouble(1) + Double.parseDouble(visiSkuCal.get(temp))));
				temp++;
			}
			return listOfSellInCal;
		} catch (Exception e) {
			listOfSellInCal.add(e.toString());
			return listOfSellInCal;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchSellIn> getSellInForSellInNFinal(int launchId, String Data) {
		List<LaunchSellIn> toReturn = new ArrayList<>();
		String dataToQuery = "";
		for (int i = 1; i <= Integer.parseInt(Data); i++) {
			if (i == Integer.parseInt(Data)) {
				dataToQuery = dataToQuery + "SELLIN_SKU" + i;
			} else {
				dataToQuery = dataToQuery + "SELLIN_SKU" + i + ",";
			}
		}
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT SELLIN_L1_CHAIN,SELLIN_L2_CHAIN,SELLIN_STORE_FORMAT, SELLIN_ROTATIONS,SELLIN_UPLIFT_N1,"
							+ "SELLIN_UPLIFT_N2,SELLIN_STORES_PLANNED," + dataToQuery
							+ " FROM TBL_LAUNCH_SELLIN tls WHERE SELLIN_LAUNCH_ID = '" + launchId + "'");
			Iterator<Object> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchSellIn launchSellIn = new LaunchSellIn();
				launchSellIn.setL1_CHAIN(obj[0].toString());
				launchSellIn.setL2_CHAIN(obj[1].toString());
				launchSellIn.setSTORE_FORMAT(obj[2].toString());
				launchSellIn.setROTATIONS(obj[3].toString());
				launchSellIn.setUPLIFT_N1(obj[4].toString());
				launchSellIn.setUPLIFT_N2(obj[5].toString());
				launchSellIn.setSTORES_PLANNED(obj[6].toString());
				String sku = obj[7].toString();
				int objLength = obj.length;

				if (objLength > 8) {
					sku = sku + "~" + obj[8].toString();
				}

				if (objLength > 9) {
					sku = sku + "~" + obj[9].toString();
				}

				if (objLength > 10) {
					sku = sku + "~" + obj[10].toString();
				}

				if (objLength > 11) {
					sku = sku + "~" + obj[11].toString();
				}

				if (objLength > 12) {
					sku = sku + "~" + obj[12].toString();
				}

				if (objLength > 13) {
					sku = sku + "~" + obj[13].toString();
				}

				if (objLength > 14) {
					sku = sku + "~" + obj[14].toString();
				}

				if (objLength > 15) {
					sku = sku + "~" + obj[15].toString();
				}

				if (objLength > 16) {
					sku = sku + "~" + obj[16].toString();
				}

				if (objLength > 17) {
					sku = sku + "~" + obj[17].toString();
				}

				if (objLength > 18) {
					sku = sku + "~" + obj[18].toString();
				}

				if (objLength > 19) {
					sku = sku + "~" + obj[19].toString();
				}

				if (objLength > 20) {
					sku = sku + "~" + obj[20].toString();
				}
				launchSellIn.setSKU_SELLIN(sku);
				toReturn.add(launchSellIn);
			}
			return toReturn;
		} catch (Exception e) {
			LaunchSellIn launchSellIn = new LaunchSellIn();
			launchSellIn.setError(e.toString());
			toReturn.add(launchSellIn);
			return toReturn;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchSellIn> getSellInForSellInNFinalEdit(int launchId,
			List<LaunchFinalPlanResponse> listOfBasepacks) {
		List<LaunchSellIn> toReturn = new ArrayList<>();
		String dataToQuery = "";

		int temp = 1;
		List<String> listOfSkuToFetch = new ArrayList<>();
		for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfBasepacks) {
			if (launchFinalPlanResponse.getBpStatus() != null) {
				if (!launchFinalPlanResponse.getBpStatus().equals("REJECTED BY TME")) {
					listOfSkuToFetch.add("SELLIN_SKU" + temp);
				}
			} else {
				listOfSkuToFetch.add("SELLIN_SKU" + temp);
			}
			temp++;
		}
		dataToQuery = String.join(",", listOfSkuToFetch);
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT SELLIN_L1_CHAIN,SELLIN_L2_CHAIN,SELLIN_STORE_FORMAT, SELLIN_ROTATIONS,SELLIN_UPLIFT_N1,"
							+ "SELLIN_UPLIFT_N2,SELLIN_STORES_PLANNED," + dataToQuery
							+ " FROM TBL_LAUNCH_SELLIN tls WHERE SELLIN_LAUNCH_ID = '" + launchId + "'");
			Iterator<Object> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchSellIn launchSellIn = new LaunchSellIn();
				launchSellIn.setL1_CHAIN(obj[0].toString());
				launchSellIn.setL2_CHAIN(obj[1].toString());
				launchSellIn.setSTORE_FORMAT(obj[2].toString());
				launchSellIn.setROTATIONS(obj[3].toString());
				launchSellIn.setUPLIFT_N1(obj[4].toString());
				launchSellIn.setUPLIFT_N2(obj[5].toString());
				launchSellIn.setSTORES_PLANNED(obj[6].toString());
				String sku = obj[7].toString();
				int objLength = obj.length;

				if (objLength > 8) {
					sku = sku + "~" + obj[8].toString();
				}

				if (objLength > 9) {
					sku = sku + "~" + obj[9].toString();
				}

				if (objLength > 10) {
					sku = sku + "~" + obj[10].toString();
				}

				if (objLength > 11) {
					sku = sku + "~" + obj[11].toString();
				}

				if (objLength > 12) {
					sku = sku + "~" + obj[12].toString();
				}

				if (objLength > 13) {
					sku = sku + "~" + obj[13].toString();
				}

				if (objLength > 14) {
					sku = sku + "~" + obj[14].toString();
				}

				if (objLength > 15) {
					sku = sku + "~" + obj[15].toString();
				}

				if (objLength > 16) {
					sku = sku + "~" + obj[16].toString();
				}

				if (objLength > 17) {
					sku = sku + "~" + obj[17].toString();
				}

				if (objLength > 18) {
					sku = sku + "~" + obj[18].toString();
				}

				if (objLength > 19) {
					sku = sku + "~" + obj[19].toString();
				}

				if (objLength > 20) {
					sku = sku + "~" + obj[20].toString();
				}
				launchSellIn.setSKU_SELLIN(sku);
				toReturn.add(launchSellIn);
			}
			return toReturn;
		} catch (Exception e) {
			LaunchSellIn launchSellIn = new LaunchSellIn();
			launchSellIn.setError(e.toString());
			toReturn.add(launchSellIn);
			return toReturn;
		}
	}
	
	//Sarin Changes - Commented & Added Below - Launch Issue Fix Feb2021
	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchStoreData> getListStoreData(LaunchSellIn launchSellIn, List<LaunchFinalPlanResponse> listOfFinal,
			LaunchVisiPlanning launchVisiPlanning, String classification, List<String> liClusterName) {
		List<LaunchStoreData> liStoreData = null;
		try {
			String launchClassification = "";
			if (classification.equals("Gold")) {
				launchClassification = "'GOLD','SILVER','BRONZE'";
			} else if (classification.equals("Silver")) {
				launchClassification = "'BRONZE','SILVER'";
			} else {
				launchClassification = "'BRONZE'";
			}
			Query query;
			if (!liClusterName.contains("ALL INDIA")) {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT()
								+ "' AND FINAL_CLUSTER IN(:liClusterName) AND LAUNCH_FORMAT IN(" + launchClassification
								+ ")");
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
				query.setParameterList("liClusterName", liClusterName);
			} else {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT() + "' AND LAUNCH_FORMAT IN(" + launchClassification
								+ ")");
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
			}

			Iterator<Object> itr = query.list().iterator();
			liStoreData = new ArrayList<>();
			String basepackCode = "";
			String basepackDesc = "";
			String cld = "";
			String mrp = "";
			String gsv = "";
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				basepackCode = basepackCode + launchFinalPlanResponse.getBasepackCode() + ",";
				basepackDesc = basepackDesc + launchFinalPlanResponse.getSkuName() + ",";
				cld = cld + launchFinalPlanResponse.getCld() + ",";
				mrp = mrp + launchFinalPlanResponse.getMrp() + ",";
				gsv = gsv + launchFinalPlanResponse.getGsv() + ",";
			}

			String[] basepackCodeArr = basepackCode.split(",");
			String[] basepackDescArr = basepackDesc.split(",");
			String[] cldArr = cld.split(",");
			String[] mrpArr = mrp.split(",");
			String[] gsvArr = gsv.split(",");

			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String[] sellInVal = launchSellIn.getSKU_SELLIN().split("~");
				for (int i = 0; i < sellInVal.length; i++) {
					LaunchStoreData launchStoreData = new LaunchStoreData();
					launchStoreData.setReportingCode(obj[0].toString());
					launchStoreData.setHfsCode(obj[1].toString());
					launchStoreData.setFmcgCspCode(obj[2].toString());
					launchStoreData.setFmcgSiteCode(obj[3].toString());
					launchStoreData.setModifiedChain(obj[4].toString());
					launchStoreData.setAccountFormatName(obj[4].toString());
					launchStoreData.setAccountNameL1(obj[4].toString());
					launchStoreData.setAccountNameL2(obj[5].toString());
					launchStoreData.setHulStoreFormat(obj[6].toString());
					launchStoreData.setCustomerStoreFormat(obj[7].toString());
					launchStoreData.setuKey(obj[8].toString());
					launchStoreData.setBranchCode(obj[9].toString());
					launchStoreData.setDepot(obj[10].toString());
					launchStoreData.setFinalCluster(obj[11].toString());
					launchStoreData.setUnitOfMeasurement(obj[12].toString());
					launchStoreData.setSkuFinalCount(sellInVal[i]);

					double sellInForN = Double.parseDouble(sellInVal[i])
							* Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN1 = Double.parseDouble(launchSellIn.getUPLIFT_N1())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN2 = Double.parseDouble(launchSellIn.getUPLIFT_N2())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());

					launchStoreData.setSellInN(Double.toString(sellInForN));
					launchStoreData.setSellInN1(Double.toString(sellInForN1));
					launchStoreData.setSellInN2(Double.toString(sellInForN2));

					launchStoreData.setSkuFinalName(basepackDescArr[i]);
					launchStoreData.setSkuFinalCode(basepackCodeArr[i]);
					launchStoreData.setSkuFinalCld(cldArr[i]);
					launchStoreData.setSkuFinalMrp(mrpArr[i]);
					launchStoreData.setSkuFinalGsv(gsvArr[i]);
					launchStoreData.setRotation(launchSellIn.getROTATIONS());
					if ((launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_5().equals("-1"))
							|| (launchVisiPlanning.getVISI_ASSET_1().equals("")
									&& launchVisiPlanning.getVISI_ASSET_2().equals("")
									&& launchVisiPlanning.getVISI_ASSET_3().equals("")
									&& launchVisiPlanning.getVISI_ASSET_4().equals("")
									&& launchVisiPlanning.getVISI_ASSET_5().equals(""))) {
						launchStoreData.setVisiCheck("0");
					} else {
						launchStoreData.setVisiCheck("1");
					}

					if (launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						launchStoreData.setVisiElement1("");
					} else {
						launchStoreData.setVisiElement1(launchVisiPlanning.getVISI_ASSET_1());
					}

					if (launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						launchStoreData.setVisiElement2("");
					} else {
						launchStoreData.setVisiElement2(launchVisiPlanning.getVISI_ASSET_2());
					}

					if (launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						launchStoreData.setVisiElement3("");
					} else {
						launchStoreData.setVisiElement3(launchVisiPlanning.getVISI_ASSET_3());
					}

					if (launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						launchStoreData.setVisiElement4("");
					} else {
						launchStoreData.setVisiElement4(launchVisiPlanning.getVISI_ASSET_4());
					}

					if (launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						launchStoreData.setVisiElement5("");
					} else {
						launchStoreData.setVisiElement5(launchVisiPlanning.getVISI_ASSET_5());
					}

					launchStoreData.setUpliftN1(launchSellIn.getUPLIFT_N1());
					launchStoreData.setUpliftN2(launchSellIn.getUPLIFT_N2());
					int noOfShelveForV1 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						noOfShelveForV1 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_1());
					}

					int noOfShelveForV2 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						noOfShelveForV2 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_2());
					}
					int noOfShelveForV3 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						noOfShelveForV3 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_3());
					}
					int noOfShelveForV4 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						noOfShelveForV4 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_4());
					}
					int noOfShelveForV5 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						noOfShelveForV5 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_5());
					}

					int depth1 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1().equals("")) {
						depth1 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1());
					}
					int facing1 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1().equals("")) {
						facing1 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1());
					}
					int depth2 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2().equals("")) {
						depth2 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2());
					}
					int facing2 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2().equals("")) {
						facing2 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2());
					}
					int depth3 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3().equals("")) {
						depth3 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3());
					}
					int facing3 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3().equals("")) {
						facing3 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3());
					}
					int depth4 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4().equals("")) {
						depth4 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4());
					}
					int facing4 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4().equals("")) {
						facing4 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4());
					}
					int depth5 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5().equals("")) {
						depth5 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5());
					}
					int facing5 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5().equals("")) {
						facing5 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5());
					}
					double finalVisiForN = (noOfShelveForV1 * depth1 * facing1) + (noOfShelveForV2 * depth2 * facing2)
							+ (noOfShelveForV3 * depth3 * facing3) + (noOfShelveForV4 * depth4 * facing4)
							+ (noOfShelveForV5 * depth5 * facing5);

					if (classification.equals("Gold")) {
						launchStoreData.setVisiSellInN(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					} else {
						launchStoreData.setVisiSellInN(Double.toString(0));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					}

					launchStoreData
							.setRevisedSellInForStoreN(Double.toString(Double.parseDouble(launchStoreData.getSellInN())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN()))));
					launchStoreData.setRevisedSellInForStoreN1(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN1())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN1()))));
					launchStoreData.setRevisedSellInForStoreN2(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN2())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN2()))));

					liStoreData.add(launchStoreData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liStoreData;
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchStoreData> getListStoreData(List<LaunchSellIn> lstlaunchSellIn, List<LaunchFinalPlanResponse> listOfFinal,
			List<LaunchVisiPlanning> lstlaunchVisiPlanning, String classification, List<String> liClusterName, String launchId) {
		List<LaunchStoreData> liStoreData = null;
		try {
			String launchClassification = "";
			if (classification.equals("Gold")) {
				launchClassification = "'GOLD','SILVER','BRONZE'";
			} else if (classification.equals("Silver")) {
				launchClassification = "'BRONZE','SILVER'";
			} else {
				launchClassification = "'BRONZE'";
			}
			//Sarin Changes - Q1Sprint Feb2021
			if (getIncludeAllStoreFormat(launchId).equalsIgnoreCase("Yes")) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			Query query;
			/*
			if (!liClusterName.contains("ALL INDIA")) {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT()
								+ "' AND FINAL_CLUSTER IN(:liClusterName) AND LAUNCH_FORMAT IN(" + launchClassification
								+ ")");
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
				query.setParameterList("liClusterName", liClusterName);
			} else {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT() + "' AND LAUNCH_FORMAT IN(" + launchClassification
								+ ")");
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
			}
			*/
			
			if (!liClusterName.contains("ALL INDIA")) {
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER A "
								+ " INNER JOIN TBL_LAUNCH_SELLIN S ON A.ACCOUNT_NAME = S.SELLIN_L2_CHAIN AND A.DP_CHAIN = S.SELLIN_L1_CHAIN "
								+ " AND UPPER(A.CURRENT_STORE_FORMAT) = S.SELLIN_STORE_FORMAT "
								+ " WHERE A.ACTIVE_STATUS = 'ACTIVE' AND LAUNCH_FORMAT IN (" + launchClassification + ") AND FINAL_CLUSTER IN(:liClusterName) AND S.SELLIN_LAUNCH_ID = " + launchId);
				query.setParameterList("liClusterName", liClusterName);
			} else {
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER A "
								+ " INNER JOIN TBL_LAUNCH_SELLIN S ON A.ACCOUNT_NAME = S.SELLIN_L2_CHAIN AND A.DP_CHAIN = S.SELLIN_L1_CHAIN "
								+ " AND UPPER(A.CURRENT_STORE_FORMAT) = S.SELLIN_STORE_FORMAT "
								+ " WHERE A.ACTIVE_STATUS = 'ACTIVE' AND LAUNCH_FORMAT IN (" + launchClassification + ") AND S.SELLIN_LAUNCH_ID = " + launchId);
			}
			
			Map<String, Integer> mapVisiAssetType = new HashMap<>();
			Query queryVisi = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT ASSET_TYPE, MAX(NO_OF_SHELVES) AS NO_OF_SHELVES FROM TBL_VAT_VISIBILITY_PLAN_MASTER WHERE NO_OF_SHELVES IS NOT NULL GROUP BY ASSET_TYPE ");
			Iterator<Object> itrVisi = queryVisi.list().iterator();
			while (itrVisi.hasNext()) {
				Object[] obj = (Object[]) itrVisi.next();
				mapVisiAssetType.put(obj[0].toString(), Integer.parseInt(obj[1].toString()));
			}
			
			Iterator<Object> itr = query.list().iterator();
			liStoreData = new ArrayList<>();
			String basepackCode = "";
			String basepackDesc = "";
			String cld = "";
			String mrp = "";
			String gsv = "";
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				basepackCode = basepackCode + launchFinalPlanResponse.getBasepackCode() + ",";
				basepackDesc = basepackDesc + launchFinalPlanResponse.getSkuName() + ",";
				cld = cld + launchFinalPlanResponse.getCld() + ",";
				mrp = mrp + launchFinalPlanResponse.getMrp() + ",";
				gsv = gsv + launchFinalPlanResponse.getGsv() + ",";
			}

			String[] basepackCodeArr = basepackCode.split(",");
			String[] basepackDescArr = basepackDesc.split(",");
			String[] cldArr = cld.split(",");
			String[] mrpArr = mrp.split(",");
			String[] gsvArr = gsv.split(",");

			while (itr.hasNext()) {
				
				LaunchSellIn launchSellIn = new LaunchSellIn();
				LaunchVisiPlanning launchVisiPlanning = new LaunchVisiPlanning();
				
				Object[] obj = (Object[]) itr.next();
				
				for (int i = 0; i < lstlaunchSellIn.size(); i++) {
					if ((obj[5].toString().equalsIgnoreCase(lstlaunchSellIn.get(i).getL1_CHAIN())) && 
							(obj[4].toString().equalsIgnoreCase(lstlaunchSellIn.get(i).getL2_CHAIN())) &&
							(obj[6].toString().equalsIgnoreCase(lstlaunchSellIn.get(i).getSTORE_FORMAT())) ) {
						launchSellIn = lstlaunchSellIn.get(i);
						break;
					}
				}
				
				if (lstlaunchVisiPlanning.size() == 0) {
					launchVisiPlanning.setACCOUNT("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4("");
					launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5("");
					launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5("");
					launchVisiPlanning.setSTORES_PLANNED("0");
					launchVisiPlanning.setSTORES_AVAILABLE("1");
					launchVisiPlanning.setVISI_ASSET_1("");
					launchVisiPlanning.setVISI_ASSET_2("");
					launchVisiPlanning.setVISI_ASSET_3("");
					launchVisiPlanning.setVISI_ASSET_4("");
					launchVisiPlanning.setVISI_ASSET_5("");
				} else {
					for (int i = 0; i < lstlaunchVisiPlanning.size(); i++) {
						if ( ((obj[5].toString() + ":" + obj[4].toString()).equalsIgnoreCase(lstlaunchVisiPlanning.get(i).getACCOUNT())) &&
								(obj[6].toString().equalsIgnoreCase(lstlaunchVisiPlanning.get(i).getFORMAT())) ) {
							launchVisiPlanning = lstlaunchVisiPlanning.get(i);
							break;
						}
					}
				}
				
				//System.out.println(launchSellIn.getSKU_SELLIN());
				String[] sellInVal = launchSellIn.getSKU_SELLIN().split("~");
				for (int i = 0; i < sellInVal.length; i++) {
					LaunchStoreData launchStoreData = new LaunchStoreData();
					launchStoreData.setReportingCode(obj[0].toString());
					launchStoreData.setHfsCode(obj[1].toString());
					launchStoreData.setFmcgCspCode(obj[2].toString());
					launchStoreData.setFmcgSiteCode(obj[3].toString());
					launchStoreData.setModifiedChain(obj[4].toString());
					launchStoreData.setAccountFormatName(obj[4].toString());
					launchStoreData.setAccountNameL1(obj[4].toString());
					launchStoreData.setAccountNameL2(obj[5].toString());
					launchStoreData.setHulStoreFormat(obj[6].toString());
					launchStoreData.setCustomerStoreFormat(obj[7].toString());
					launchStoreData.setuKey(obj[8].toString());
					launchStoreData.setBranchCode(obj[9].toString());
					launchStoreData.setDepot(obj[10].toString());
					launchStoreData.setFinalCluster(obj[11].toString());
					launchStoreData.setUnitOfMeasurement(obj[12].toString());
					launchStoreData.setSkuFinalCount(sellInVal[i]);

					double sellInForN = Double.parseDouble(sellInVal[i])
							* Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN1 = Double.parseDouble(launchSellIn.getUPLIFT_N1())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN2 = Double.parseDouble(launchSellIn.getUPLIFT_N2())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());

					launchStoreData.setSellInN(Double.toString(sellInForN));
					launchStoreData.setSellInN1(Double.toString(sellInForN1));
					launchStoreData.setSellInN2(Double.toString(sellInForN2));

					launchStoreData.setSkuFinalName(basepackDescArr[i]);
					launchStoreData.setSkuFinalCode(basepackCodeArr[i]);
					launchStoreData.setSkuFinalCld(cldArr[i]);
					launchStoreData.setSkuFinalMrp(mrpArr[i]);
					launchStoreData.setSkuFinalGsv(gsvArr[i]);
					launchStoreData.setRotation(launchSellIn.getROTATIONS());
					if ((launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_5().equals("-1"))
							|| (launchVisiPlanning.getVISI_ASSET_1().equals("")
									&& launchVisiPlanning.getVISI_ASSET_2().equals("")
									&& launchVisiPlanning.getVISI_ASSET_3().equals("")
									&& launchVisiPlanning.getVISI_ASSET_4().equals("")
									&& launchVisiPlanning.getVISI_ASSET_5().equals(""))) {
						launchStoreData.setVisiCheck("0");
					} else {
						launchStoreData.setVisiCheck("1");
					}

					if (launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						launchStoreData.setVisiElement1("");
					} else {
						launchStoreData.setVisiElement1(launchVisiPlanning.getVISI_ASSET_1());
					}

					if (launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						launchStoreData.setVisiElement2("");
					} else {
						launchStoreData.setVisiElement2(launchVisiPlanning.getVISI_ASSET_2());
					}

					if (launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						launchStoreData.setVisiElement3("");
					} else {
						launchStoreData.setVisiElement3(launchVisiPlanning.getVISI_ASSET_3());
					}

					if (launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						launchStoreData.setVisiElement4("");
					} else {
						launchStoreData.setVisiElement4(launchVisiPlanning.getVISI_ASSET_4());
					}

					if (launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						launchStoreData.setVisiElement5("");
					} else {
						launchStoreData.setVisiElement5(launchVisiPlanning.getVISI_ASSET_5());
					}

					launchStoreData.setUpliftN1(launchSellIn.getUPLIFT_N1());
					launchStoreData.setUpliftN2(launchSellIn.getUPLIFT_N2());
					int noOfShelveForV1 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						//noOfShelveForV1 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_1());
						noOfShelveForV1 = mapVisiAssetType.get(launchVisiPlanning.getVISI_ASSET_1());
					}

					int noOfShelveForV2 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						noOfShelveForV2 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_2());
					}
					int noOfShelveForV3 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						noOfShelveForV3 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_3());
					}
					int noOfShelveForV4 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						noOfShelveForV4 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_4());
					}
					int noOfShelveForV5 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						noOfShelveForV5 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_5());
					}

					int depth1 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1().equals("")) {
						depth1 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1());
					}
					int facing1 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1().equals("")) {
						facing1 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1());
					}
					int depth2 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2().equals("")) {
						depth2 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2());
					}
					int facing2 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2().equals("")) {
						facing2 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2());
					}
					int depth3 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3().equals("")) {
						depth3 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3());
					}
					int facing3 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3().equals("")) {
						facing3 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3());
					}
					int depth4 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4().equals("")) {
						depth4 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4());
					}
					int facing4 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4().equals("")) {
						facing4 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4());
					}
					int depth5 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5().equals("")) {
						depth5 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5());
					}
					int facing5 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5().equals("")) {
						facing5 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5());
					}
					double finalVisiForN = (noOfShelveForV1 * depth1 * facing1) + (noOfShelveForV2 * depth2 * facing2)
							+ (noOfShelveForV3 * depth3 * facing3) + (noOfShelveForV4 * depth4 * facing4)
							+ (noOfShelveForV5 * depth5 * facing5);

					if (classification.equals("Gold")) {
						launchStoreData.setVisiSellInN(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					} else {
						launchStoreData.setVisiSellInN(Double.toString(0));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					}

					launchStoreData
							.setRevisedSellInForStoreN(Double.toString(Double.parseDouble(launchStoreData.getSellInN())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN()))));
					launchStoreData.setRevisedSellInForStoreN1(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN1())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN1()))));
					launchStoreData.setRevisedSellInForStoreN2(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN2())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN2()))));

					liStoreData.add(launchStoreData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liStoreData;
	}
	
	@Override
	public String getClusterOnLaunchId(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String returnRegion = "";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT CLUSTER_REGION FROM TBL_LAUNCH_CLUSTERS tlc WHERE CLUSTER_LAUNCH_ID =  '" + launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				returnRegion = rs.getString("CLUSTER_REGION");
			}
			return returnRegion;
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchStoreData> getListStoreDataKAM(LaunchSellIn launchSellIn,
			List<LaunchFinalPlanResponse> listOfFinal, LaunchVisiPlanning launchVisiPlanning, String classification,
			List<String> liClusterName, String forWhichKam, String launchId) {

		List<LaunchStoreData> liStoreData = null;
		try {
			String launchClassification = "";
			if (classification.equals("Gold")) {
				launchClassification = "'GOLD','SILVER','BRONZE'";
			} else if (classification.equals("Silver")) {
				launchClassification = "'BRONZE','SILVER'";
			} else {
				launchClassification = "'BRONZE'";
			}
			//Sarin Changes - Q1Sprint Feb2021
			if (getIncludeAllStoreFormat(launchId).equalsIgnoreCase("Yes")) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			String upperKam = forWhichKam.concat("@unilever.com").toUpperCase();
			Query query;
			if (!liClusterName.contains("ALL INDIA")) {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT()
								+ "' AND FINAL_CLUSTER IN(:liClusterName) AND LAUNCH_FORMAT IN(" + launchClassification
								//+ ") AND UPPER(KAM_MAIL_ID) = '" + upperKam + "'");     //Commented & Added below By Sarin 13Oct2021
								+ ") AND UPPER(KAM_MAIL_ID) LIKE '%" + upperKam + "%'");  //Added By Sarin 13Oct2021
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
				query.setParameterList("liClusterName", liClusterName);
			} else {
				//Garima - changes for concatenation
				query = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT HUL_OUTLET_CODE reporting_Codes,HUL_OUTLET_CODE hfs_Code,CUSTOMER_CODE,"
								+ "CUSTOMER_CODE fmcg_Site_Code, ACCOUNT_NAME ACCOUNT_NAME_L1,DP_CHAIN ACCOUNT_NAME_L2, "
								+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, CONCAT(ACCOUNT_NAME , CUSTOMER_STORE_FORMAT) "
								+ "Ukey,BRANCH_CODE,DEPOT,FINAL_CLUSTER,'Units' unit_of_measurement FROM TBL_VAT_COMM_OUTLET_MASTER "
								+ " WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME = '" + launchSellIn.getL2_CHAIN() + "' AND DP_CHAIN = '"
								+ launchSellIn.getL1_CHAIN() + "' AND UPPER(CURRENT_STORE_FORMAT) = '"
								+ launchSellIn.getSTORE_FORMAT() + "' AND LAUNCH_FORMAT IN(" + launchClassification
								//+ ") AND UPPER(KAM_MAIL_ID) = '" + upperKam + "'");     //Commented & Added below By Sarin 13Oct2021
								+ ") AND UPPER(KAM_MAIL_ID) LIKE '%" + upperKam + "%'");  //Added By Sarin 13Oct2021
				//+ " UPPER(CURRENT_STORE_FORMAT) hul_Store_format,UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) CUSTOMER_STORE_FORMAT, ACCOUNT_NAME || CUSTOMER_STORE_FORMAT "
			}

			Iterator<Object> itr = query.list().iterator();
			liStoreData = new ArrayList<>();
			String basepackCode = "";
			String basepackDesc = "";
			String cld = "";
			String mrp = "";
			String gsv = "";
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinal) {
				basepackCode = basepackCode + launchFinalPlanResponse.getBasepackCode() + ",";
				basepackDesc = basepackDesc + launchFinalPlanResponse.getSkuName() + ",";
				cld = cld + launchFinalPlanResponse.getCld() + ",";
				mrp = mrp + launchFinalPlanResponse.getMrp() + ",";
				gsv = gsv + launchFinalPlanResponse.getGsv() + ",";
			}

			String[] basepackCodeArr = basepackCode.split(",");
			String[] basepackDescArr = basepackDesc.split(",");
			String[] cldArr = cld.split(",");
			String[] mrpArr = mrp.split(",");
			String[] gsvArr = gsv.split(",");

			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				String[] sellInVal = launchSellIn.getSKU_SELLIN().split("~");
				for (int i = 0; i < sellInVal.length; i++) {
					LaunchStoreData launchStoreData = new LaunchStoreData();
					launchStoreData.setReportingCode(obj[0].toString());
					launchStoreData.setHfsCode(obj[1].toString());
					launchStoreData.setFmcgCspCode(obj[2].toString());
					launchStoreData.setFmcgSiteCode(obj[3].toString());
					launchStoreData.setModifiedChain(obj[4].toString());
					launchStoreData.setAccountFormatName(obj[4].toString());
					launchStoreData.setAccountNameL1(obj[4].toString());
					launchStoreData.setAccountNameL2(obj[5].toString());
					launchStoreData.setHulStoreFormat(obj[6].toString());
					launchStoreData.setCustomerStoreFormat(obj[7].toString());
					launchStoreData.setuKey(obj[8].toString());
					launchStoreData.setBranchCode(obj[9].toString());
					launchStoreData.setDepot(obj[10].toString());
					launchStoreData.setFinalCluster(obj[11].toString());
					launchStoreData.setUnitOfMeasurement(obj[12].toString());
					launchStoreData.setSkuFinalCount(sellInVal[i]);

					double sellInForN = Double.parseDouble(sellInVal[i])
							* Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN1 = Double.parseDouble(launchSellIn.getUPLIFT_N1())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());
					double sellInForN2 = Double.parseDouble(launchSellIn.getUPLIFT_N2())
							* Double.parseDouble(sellInVal[i]) * Double.parseDouble(launchSellIn.getROTATIONS());

					launchStoreData.setSellInN(Double.toString(sellInForN));
					launchStoreData.setSellInN1(Double.toString(sellInForN1));
					launchStoreData.setSellInN2(Double.toString(sellInForN2));

					launchStoreData.setSkuFinalName(basepackDescArr[i]);
					launchStoreData.setSkuFinalCode(basepackCodeArr[i]);
					launchStoreData.setSkuFinalCld(cldArr[i]);
					launchStoreData.setSkuFinalMrp(mrpArr[i]);
					launchStoreData.setSkuFinalGsv(gsvArr[i]);
					launchStoreData.setRotation(launchSellIn.getROTATIONS());
					if ((launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& launchVisiPlanning.getVISI_ASSET_5().equals("-1"))
							|| (launchVisiPlanning.getVISI_ASSET_1().equals("")
									&& launchVisiPlanning.getVISI_ASSET_2().equals("")
									&& launchVisiPlanning.getVISI_ASSET_3().equals("")
									&& launchVisiPlanning.getVISI_ASSET_4().equals("")
									&& launchVisiPlanning.getVISI_ASSET_5().equals(""))) {
						launchStoreData.setVisiCheck("0");
					} else {
						launchStoreData.setVisiCheck("1");
					}

					if (launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						launchStoreData.setVisiElement1("");
					} else {
						launchStoreData.setVisiElement1(launchVisiPlanning.getVISI_ASSET_1());
					}

					if (launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						launchStoreData.setVisiElement2("");
					} else {
						launchStoreData.setVisiElement2(launchVisiPlanning.getVISI_ASSET_2());
					}

					if (launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						launchStoreData.setVisiElement3("");
					} else {
						launchStoreData.setVisiElement3(launchVisiPlanning.getVISI_ASSET_3());
					}

					if (launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						launchStoreData.setVisiElement4("");
					} else {
						launchStoreData.setVisiElement4(launchVisiPlanning.getVISI_ASSET_4());
					}

					if (launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							|| launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						launchStoreData.setVisiElement5("");
					} else {
						launchStoreData.setVisiElement5(launchVisiPlanning.getVISI_ASSET_5());
					}

					launchStoreData.setUpliftN1(launchSellIn.getUPLIFT_N1());
					launchStoreData.setUpliftN2(launchSellIn.getUPLIFT_N2());
					int noOfShelveForV1 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_1().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_1().equals("")) {
						noOfShelveForV1 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_1());
					}

					int noOfShelveForV2 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_2().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_2().equals("")) {
						noOfShelveForV2 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_2());
					}
					int noOfShelveForV3 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_3().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_3().equals("")) {
						noOfShelveForV3 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_3());
					}
					int noOfShelveForV4 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_4().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_4().equals("")) {
						noOfShelveForV4 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_4());
					}
					int noOfShelveForV5 = 0;
					if (!launchVisiPlanning.getVISI_ASSET_5().equals("-1")
							&& !launchVisiPlanning.getVISI_ASSET_5().equals("")) {
						noOfShelveForV5 = launchVisiPlanDao.getShelvesByVisiName(launchVisiPlanning.getVISI_ASSET_5());
					}

					int depth1 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1().equals("")) {
						depth1 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU1());
					}
					int facing1 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1().equals("")) {
						facing1 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU1());
					}
					int depth2 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2().equals("")) {
						depth2 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU2());
					}
					int facing2 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2().equals("")) {
						facing2 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU2());
					}
					int depth3 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3().equals("")) {
						depth3 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU3());
					}
					int facing3 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3().equals("")) {
						facing3 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU3());
					}
					int depth4 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4().equals("")) {
						depth4 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU4());
					}
					int facing4 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4().equals("")) {
						facing4 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU4());
					}
					int depth5 = 0;
					if (!launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5().equals("")) {
						depth5 = Integer.parseInt(launchVisiPlanning.getDEPTH_PER_SHELF_PER_SKU5());
					}
					int facing5 = 0;
					if (!launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5().equals("")) {
						facing5 = Integer.parseInt(launchVisiPlanning.getFACING_PER_SHELF_PER_SKU5());
					}
					double finalVisiForN = (noOfShelveForV1 * depth1 * facing1) + (noOfShelveForV2 * depth2 * facing2)
							+ (noOfShelveForV3 * depth3 * facing3) + (noOfShelveForV4 * depth4 * facing4)
							+ (noOfShelveForV5 * depth5 * facing5);

					if (classification.equals("Gold")) {
						launchStoreData.setVisiSellInN(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					} else {
						launchStoreData.setVisiSellInN(Double.toString(0));
						launchStoreData.setVisiSellInN1(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE())));
						launchStoreData.setVisiSellInN2(Double.toString(Integer.parseInt(launchStoreData.getVisiCheck())
								* finalVisiForN * Double.parseDouble(launchVisiPlanning.getSTORES_PLANNED())
								/ Double.parseDouble(launchVisiPlanning.getSTORES_AVAILABLE()) * 0.5));
					}

					launchStoreData
							.setRevisedSellInForStoreN(Double.toString(Double.parseDouble(launchStoreData.getSellInN())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN()))));
					launchStoreData.setRevisedSellInForStoreN1(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN1())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN1()))));
					launchStoreData.setRevisedSellInForStoreN2(
							Double.toString(Double.parseDouble(launchStoreData.getSellInN2())
									+ (Double.parseDouble(launchStoreData.getVisiSellInN2()))));

					liStoreData.add(launchStoreData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liStoreData;

	}
	
	//Sarin Changes - Added Q1Sprint Feb2021 for CustomStoreSelection to IncludeAllStoreFormats
	@SuppressWarnings("rawtypes")
	private String getIncludeAllStoreFormat(String launchId) {
		String sCustomeStoreSel = "No";
		Query queryCustomeStoreSel;
		try {
			queryCustomeStoreSel = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT INCLUDE_ALL_STORE_FORMAT FROM TBL_LAUNCH_CLUSTERS WHERE CLUSTER_LAUNCH_ID = " + launchId);
			List lstCustomeStoreSel = queryCustomeStoreSel.list();
			sCustomeStoreSel =  lstCustomeStoreSel.get(0).toString();
		} catch (Exception e) {
			return e.toString();
		}
		return sCustomeStoreSel;
	}
	
	//Sarin Changes - Added Sprint4 Aug2021
	@SuppressWarnings("rawtypes")
	private Object[] getSkuSellInDetails(String launchId, String l1Chain, String l2Chain, String storeFormat) {
		Query qrySkuSellIn;
		Object[] obj = null;
		try {
			qrySkuSellIn = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT IFNULL(SELLIN_SKU1, ''), IFNULL(SELLIN_SKU2, ''), IFNULL(SELLIN_SKU3, ''), IFNULL(SELLIN_SKU4, ''), IFNULL(SELLIN_SKU5, ''), IFNULL(SELLIN_SKU6, ''), IFNULL(SELLIN_SKU7, ''), IFNULL(SELLIN_SKU8, ''), IFNULL(SELLIN_SKU9, ''), IFNULL(SELLIN_SKU10, ''), IFNULL(SELLIN_SKU11, ''), IFNULL(SELLIN_SKU12, ''), IFNULL(SELLIN_SKU13, ''), IFNULL(SELLIN_SKU14, ''), IFNULL(SELLIN_ROTATIONS, ''), IFNULL(SELLIN_UPLIFT_N1, ''), IFNULL(SELLIN_UPLIFT_N2, '') FROM TBL_LAUNCH_SELLIN WHERE SELLIN_LAUNCH_ID = :launchId AND SELLIN_L1_CHAIN = :l1chain AND SELLIN_L2_CHAIN = :l2chain AND SELLIN_STORE_FORMAT = :strformat ");
			qrySkuSellIn.setParameter("launchId", launchId);
			qrySkuSellIn.setParameter("l1chain", l2Chain);
			qrySkuSellIn.setParameter("l2chain", l1Chain);
			qrySkuSellIn.setParameter("strformat", storeFormat);
			obj = (Object[]) qrySkuSellIn.list().get(0);
		} catch (Exception e) {
			//return e.toString();
		}
		return obj;
	}
	

	public int getCountofSKU(String launchId) { 
		 try {
			 Integer recCount =0;
				Query query1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(BP_CODE) FROM TBL_LAUNCH_BASEPACK WHERE LAUNCH_ID IN (:launchID) ");

				query1.setString("launchID", launchId);
				recCount = ((BigInteger)query1.uniqueResult()).intValue();
	
				return recCount;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}
	
	
	// Added By Harsha For Q6 fix
	@Override
	public String validateSellInByUploadImpl(List<Object> saveLaunchSellIn, String userID, String string, boolean b, boolean c,
			String launchId) {
		SaveErroredLaunchSellInList saveLaunchSellInRequestList = new SaveErroredLaunchSellInList();
		List<SaveErroredLaunchSellInRequest> listsellIn = new ArrayList<>();
		Iterator<Object> iterator = saveLaunchSellIn.iterator();
		int skuLimit = getCountofSKU(launchId);
		while (iterator.hasNext()) {
			LaunchSellIn obj = (LaunchSellIn) iterator.next();
			StringBuilder errorMsg1 = new StringBuilder();
			SaveErroredLaunchSellInRequest saveErroredLaunchSellInRequest = new SaveErroredLaunchSellInRequest();
			saveErroredLaunchSellInRequest.setL1Chain(obj.getL1_CHAIN());
			saveErroredLaunchSellInRequest.setL2Chain(obj.getL2_CHAIN());
			saveErroredLaunchSellInRequest.setStoreFormat(obj.getSTORE_FORMAT());
			saveErroredLaunchSellInRequest.setStoresPlanned(obj.getSTORES_PLANNED());
			
			if(skuLimit>=1) {
				if(obj.getSKU1_SELLIN()==null || obj.getSKU1_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku1(obj.getSKU1_SELLIN());
					errorMsg1.append("SKU1_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku1(obj.getSKU1_SELLIN());
				}
			}
			
			 if(skuLimit>=2) {
				if(obj.getSKU2_SELLIN()==null || obj.getSKU2_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku2(obj.getSKU2_SELLIN());
					errorMsg1.append(",SKU2_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku2(obj.getSKU2_SELLIN());
				}
			}
			
			
			 if(skuLimit>=3) {
				if(obj.getSKU3_SELLIN()==null || obj.getSKU3_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku3(obj.getSKU3_SELLIN());
					errorMsg1.append(",SKU3_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku3(obj.getSKU3_SELLIN());
				}
			}
			
			
			 if(skuLimit>=4) {
				if(obj.getSKU4_SELLIN()==null || obj.getSKU4_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku4(obj.getSKU4_SELLIN());
					
					errorMsg1.append(",SKU4_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku4(obj.getSKU4_SELLIN());
				}
			}
			
			
			 if(skuLimit>=5) {
				if(obj.getSKU5_SELLIN()==null || obj.getSKU5_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku5(obj.getSKU5_SELLIN());
					errorMsg1.append(",SKU5_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku5(obj.getSKU5_SELLIN());
				}
			}
			
			 if(skuLimit>=6) {
				if(obj.getSKU6_SELLIN()==null || obj.getSKU6_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku6(obj.getSKU6_SELLIN());
					errorMsg1.append(",SKU6_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku6(obj.getSKU6_SELLIN());
				}
			}
			
			 if(skuLimit>=7) {
				if(obj.getSKU7_SELLIN()==null || obj.getSKU7_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku7(obj.getSKU7_SELLIN());
					errorMsg1.append(",SKU7_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku7(obj.getSKU7_SELLIN());
				}
			}
			
			 if(skuLimit>=8) {
				if(obj.getSKU8_SELLIN()==null || obj.getSKU8_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku8(obj.getSKU8_SELLIN());

					errorMsg1.append(",SKU8_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku8(obj.getSKU8_SELLIN());
				}
			}
			
			if(skuLimit>=9) {
				if(obj.getSKU9_SELLIN()==null || obj.getSKU9_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku9(obj.getSKU9_SELLIN());
					errorMsg1.append(",SKU9_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku9(obj.getSKU9_SELLIN());
				}
			}
			
			if(skuLimit>=10) {
				if(obj.getSKU10_SELLIN()==null || obj.getSKU10_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku10(obj.getSKU10_SELLIN());
					errorMsg1.append(",SKU10_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku10(obj.getSKU10_SELLIN());
				}
			}
			
			if(skuLimit>=11) {
				if(obj.getSKU11_SELLIN()==null || obj.getSKU11_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku11(obj.getSKU11_SELLIN());
					errorMsg1.append(",SKU11_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku11(obj.getSKU11_SELLIN());
				}
			}
			if(skuLimit>=12) {
				if(obj.getSKU12_SELLIN()==null || obj.getSKU12_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku12(obj.getSKU12_SELLIN());
					errorMsg1.append(",SKU12_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku12(obj.getSKU12_SELLIN());
				}
			}
			
			if(skuLimit>=13) {
				if(obj.getSKU13_SELLIN()==null || obj.getSKU13_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku13(obj.getSKU13_SELLIN());
					errorMsg1.append(",SKU13_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku13(obj.getSKU13_SELLIN());
				}
			}
			
			if(skuLimit>=14) {
				if(obj.getSKU14_SELLIN()==null || obj.getSKU14_SELLIN().isEmpty()) {
					saveErroredLaunchSellInRequest.setSku14(obj.getSKU14_SELLIN());
					errorMsg1.append(",SKU14_SELLIN");
					
				}
				else {
				saveErroredLaunchSellInRequest.setSku14(obj.getSKU14_SELLIN());
				}
			}
			
			if(obj.getROTATIONS()==null || obj.getROTATIONS().isEmpty()) {
				saveErroredLaunchSellInRequest.setRotations(obj.getROTATIONS());
				errorMsg1.append(",ROTATIONS");
				
			}
			else {
				saveErroredLaunchSellInRequest.setRotations(obj.getROTATIONS());
			}
			
			if(obj.getUPLIFT_N1()==null || obj.getUPLIFT_N1().isEmpty()) {
				saveErroredLaunchSellInRequest.setUpliftN1(obj.getUPLIFT_N1());
				errorMsg1.append(",UPLIFT_N1");
				
			}
			else {
				saveErroredLaunchSellInRequest.setUpliftN1(obj.getUPLIFT_N1());
			}
			
			if(obj.getUPLIFT_N2()==null || obj.getUPLIFT_N2().isEmpty()) {
				saveErroredLaunchSellInRequest.setUpliftN2(obj.getUPLIFT_N2());
				errorMsg1.append(",UPLIFT_N2");
				
			}
			else {
				saveErroredLaunchSellInRequest.setUpliftN2(obj.getUPLIFT_N2());
			}
			String errorMessage = errorMsg1.toString();
			if(!errorMessage.isEmpty()) {
				String finalErrorMsg = "Mandatory details are missing : ".concat(errorMessage);  
				saveErroredLaunchSellInRequest.setErrorMsg(finalErrorMsg);
			}
			
			listsellIn.add(saveErroredLaunchSellInRequest);
		}
		
		saveLaunchSellInRequestList.setListOfSellIns(listsellIn);
		saveLaunchSellInRequestList.setLaunchId(launchId);
		String result = saveLaunchSellInErrorrecord(saveLaunchSellInRequestList, userID);
		if (result.equals("Saved Successfully")) {
			return "Wrong Data saved Sucessfully";
		} else {
			return "ERROR";
		}
	}
	
	// Implemented by Harsha for Q6
	public String saveLaunchSellInErrorrecord(SaveErroredLaunchSellInList saveLaunchSellInRequestList, String userId) {
		PreparedStatement batchUpdate = null;
		try {
			
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_TEMP_SELLIN where SELLIN_LAUNCH_ID=?");
			batchUpdate.setString(1, saveLaunchSellInRequestList.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveErroredLaunchSellInRequest> listSellIn = saveLaunchSellInRequestList.getListOfSellIns();
			for (SaveErroredLaunchSellInRequest saveLaunchSellInRequest : listSellIn) {
				try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_SELLIN_TEMP,
						Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, saveLaunchSellInRequestList.getLaunchId());
					preparedStatement.setString(2, saveLaunchSellInRequest.getL1Chain());
					preparedStatement.setString(3, saveLaunchSellInRequest.getL2Chain());
					preparedStatement.setString(4, saveLaunchSellInRequest.getStoreFormat());
					preparedStatement.setString(5, saveLaunchSellInRequest.getStoresPlanned());
					preparedStatement.setString(6, saveLaunchSellInRequest.getSku1());
					preparedStatement.setString(7, saveLaunchSellInRequest.getSku2());
					preparedStatement.setString(8, saveLaunchSellInRequest.getSku3());
					preparedStatement.setString(9, saveLaunchSellInRequest.getSku4());
					preparedStatement.setString(10, saveLaunchSellInRequest.getSku5());
					preparedStatement.setString(11, saveLaunchSellInRequest.getSku6());
					preparedStatement.setString(12, saveLaunchSellInRequest.getSku7());
					preparedStatement.setString(13, saveLaunchSellInRequest.getSku8());
					preparedStatement.setString(14, saveLaunchSellInRequest.getSku9());
					preparedStatement.setString(15, saveLaunchSellInRequest.getSku10());
					preparedStatement.setString(16, saveLaunchSellInRequest.getSku11());
					preparedStatement.setString(17, saveLaunchSellInRequest.getSku12());
					preparedStatement.setString(18, saveLaunchSellInRequest.getSku13());
					preparedStatement.setString(19, saveLaunchSellInRequest.getSku14());
					preparedStatement.setString(20, saveLaunchSellInRequest.getRotations());
					preparedStatement.setString(21, saveLaunchSellInRequest.getUpliftN1());
					preparedStatement.setString(22, saveLaunchSellInRequest.getUpliftN2());
					preparedStatement.setString(23, userId);
					preparedStatement.setTimestamp(24, new Timestamp(new Date().getTime()));
					preparedStatement.setString(25,saveLaunchSellInRequest.getErrorMsg());
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
				}
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Saved Successfully";
	}

	// Implemented by Harsha for Q6
	@Override
	public int getCountofErrorMessage(String launchID) { 
		 try {
			 String errorMsg="Mandatory details are missing"; 
			 Integer recCount =0;
				Query query1 = sessionFactory.getCurrentSession().createNativeQuery(
						"select count(*) from TBL_LAUNCH_TEMP_SELLIN where SELLIN_LAUNCH_ID IN (:launchID) AND ERROR_MSG LIKE '%"+errorMsg+"%' ");

				query1.setString("launchID", launchID);
				recCount = ((BigInteger)query1.uniqueResult()).intValue();
				return recCount;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	}
	// Implemented by Harsha for Q6
	@Override
	public List<ArrayList<String>> getErrorSellInDump(String userId, DownloadSellInRequestList downloadLaunchSellInRequest) {
		List<ArrayList<String>> downloadErrorDataList = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			ArrayList<String> headerDetail = new ArrayList<>();

			headerDetail.add("L1_CHAIN");
			headerDetail.add("L2_CHAIN");
			headerDetail.add("STORE_FORMAT");
			headerDetail.add("STORES_PLANNED");
			headerDetail.add("SKU1_SELLIN");
			headerDetail.add("SKU2_SELLIN");
			headerDetail.add("SKU3_SELLIN");
			headerDetail.add("SKU4_SELLIN");
			headerDetail.add("SKU5_SELLIN");
			headerDetail.add("SKU6_SELLIN");
			headerDetail.add("SKU7_SELLIN");
			headerDetail.add("SKU8_SELLIN");
			headerDetail.add("SKU9_SELLIN");
			headerDetail.add("SKU10_SELLIN");
			headerDetail.add("SKU11_SELLIN");
			headerDetail.add("SKU12_SELLIN");
			headerDetail.add("SKU13_SELLIN");
			headerDetail.add("SKU14_SELLIN");
			headerDetail.add("ROTATIONS");
			headerDetail.add("UPLIFT_N1");
			headerDetail.add("UPLIFT_N2");
			headerDetail.add("ERROR_MSG");
			downloadErrorDataList.add(headerDetail);
			// SQL to get details
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT * FROM TBL_LAUNCH_TEMP_SELLIN WHERE SELLIN_LAUNCH_ID = '"
							+ downloadLaunchSellInRequest.getLaunchId() + "'");
			List<String> bpDesc = new ArrayList<>();
			rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> dataObjresult = new ArrayList<>();
				dataObjresult.add(rs.getString("SELLIN_L1_CHAIN"));
				dataObjresult.add(rs.getString("SELLIN_L2_CHAIN"));
				dataObjresult.add(rs.getString("SELLIN_STORE_FORMAT"));
				dataObjresult.add(rs.getString("SELLIN_STORES_PLANNED"));
				dataObjresult.add(rs.getString("SELLIN_SKU1"));
				dataObjresult.add(rs.getString("SELLIN_SKU2"));
				dataObjresult.add(rs.getString("SELLIN_SKU3"));
				dataObjresult.add(rs.getString("SELLIN_SKU4"));
				dataObjresult.add(rs.getString("SELLIN_SKU5"));
				dataObjresult.add(rs.getString("SELLIN_SKU6"));
				dataObjresult.add(rs.getString("SELLIN_SKU7"));
				dataObjresult.add(rs.getString("SELLIN_SKU8"));
				dataObjresult.add(rs.getString("SELLIN_SKU9"));
				dataObjresult.add(rs.getString("SELLIN_SKU10"));
				dataObjresult.add(rs.getString("SELLIN_SKU11"));
				dataObjresult.add(rs.getString("SELLIN_SKU12"));
				dataObjresult.add(rs.getString("SELLIN_SKU13"));
				dataObjresult.add(rs.getString("SELLIN_SKU14"));
				dataObjresult.add(rs.getString("SELLIN_ROTATIONS"));
				dataObjresult.add(rs.getString("SELLIN_UPLIFT_N1"));
				dataObjresult.add(rs.getString("SELLIN_UPLIFT_N2"));//ERROR_MSG
				dataObjresult.add(rs.getString("ERROR_MSG"));
				downloadErrorDataList.add(dataObjresult);
			}
			return downloadErrorDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}