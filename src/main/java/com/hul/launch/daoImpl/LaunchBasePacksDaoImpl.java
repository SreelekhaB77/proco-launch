package com.hul.launch.daoImpl;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.TokenFilter.Trim;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.dao.LaunchBasePacksDao;
import com.hul.launch.model.LaunchClusterDataCustStoreForm;
import com.hul.launch.model.LaunchClusterDataStoreForm;
import com.hul.launch.model.TblLaunchBasebacks;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.model.TblLaunchRequest;
import com.hul.launch.request.DownloadLaunchClusterRequest;
import com.hul.launch.request.SaveLaunchBasepackRequest;
import com.hul.launch.request.SaveLaunchBasepacksListReq;
import com.hul.launch.request.SaveLaunchClustersRequest;
import com.hul.launch.request.SaveLaunchMasterRequest;
import com.hul.launch.response.LaunchBasePackResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.proco.controller.createpromo.CreatePromoService;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchBasePacksDaoImpl implements LaunchBasePacksDao {

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public CreatePromoService createPromoService;

	private final static String TBL_LAUNCH_MASTER = "INSERT INTO MODTRD.TBL_LAUNCH_MASTER(LAUNCH_NAME,LAUNCH_DATE, LAUNCH_NATURE,LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, CREATED_BY, CREATED_DATE, LAUNCH_MOC)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";

	private final static String TBL_LAUNCH_CLUSTERS = "INSERT INTO TBL_LAUNCH_CLUSTERS(CLUSTER_LAUNCH_ID,CLUSTER_REGION, CLUSTER_ACCOUNT,CLUSTER_STORE_FORMAT, CLUSTER_CUST_STORE_FORMAT, TOTAL_STORES_TO_LAUNCH,LAUNCH_PLANNED, CREATED_BY,  CREATED_DATE, INCLUDE_ALL_STORE_FORMAT)"  //Sarin Changes - Added new column INCLUDE_ALL_STORE_FORMAT Q1Sprint Feb2021 
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
	
	private final static String TBL_LAUNCH_CLUSTERS_TEMP = "INSERT INTO TBL_LAUNCH_CLUSTERS_TEMP(LAUNCH_ID, CLUSTER_REGION,CLUSTER_ACCOUNT_L1, CLUSTER_ACCOUNT_L2, CLUSTER_CUST_STORE_FORMAT, LAUNCH_PLANNED, TOTAL_STORES_TO_LAUNCH,TOTAL_TME_STORES_PLANED,ERROR_MSG,CREATED_BY,CREATED_DATE)"  //Sarin Changes - Added new column INCLUDE_ALL_STORE_FORMAT Q1Sprint Feb2021 
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
	
	private final static String TBL_LAUNCH_CLUSTERS_TEMP_STORE = "INSERT INTO TBL_LAUNCH_CLUSTERS_TEMP(LAUNCH_ID, CLUSTER_REGION,CLUSTER_ACCOUNT_L1, CLUSTER_ACCOUNT_L2, CLUSTER_STORE_FORMAT, LAUNCH_PLANNED, TOTAL_STORES_TO_LAUNCH,TOTAL_TME_STORES_PLANED,ERROR_MSG,CREATED_BY,CREATED_DATE)"  //Sarin Changes - Added new column INCLUDE_ALL_STORE_FORMAT Q1Sprint Feb2021 
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
	
	private final static String TBL_LAUNCH_CLUSTERS_DETAILS = "INSERT INTO TBL_LAUNCH_CLUSTERS_DETAILS(LAUNCH_ID, CLUSTER_REGION,CLUSTER_ACCOUNT_L1, CLUSTER_ACCOUNT_L2, CLUSTER_CUST_STORE_FORMAT, LAUNCH_PLANNED, TOTAL_STORES_TO_LAUNCH,TOTAL_TME_STORES_PLANED,CREATED_BY,CREATED_DATE)"  //Sarin Changes - Added new column INCLUDE_ALL_STORE_FORMAT Q1Sprint Feb2021 
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
	
	private final static String TBL_LAUNCH_CLUSTERS_DETAILS_STORE = "INSERT INTO TBL_LAUNCH_CLUSTERS_DETAILS(LAUNCH_ID, CLUSTER_REGION,CLUSTER_ACCOUNT_L1, CLUSTER_ACCOUNT_L2, CLUSTER_STORE_FORMAT, LAUNCH_PLANNED, TOTAL_STORES_TO_LAUNCH,TOTAL_TME_STORES_PLANED,CREATED_BY,CREATED_DATE)"  //Sarin Changes - Added new column INCLUDE_ALL_STORE_FORMAT Q1Sprint Feb2021 
			+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
	

	private final static String TBL_LAUNCH_BASEPACK = "INSERT INTO TBL_LAUNCH_BASEPACK "
			+ "(LAUNCH_ID, BP_SALES_CAT, BP_PSA_CAT, BP_BRAND, BP_CODE, BP_DESCRIPTION, BP_MRP, BP_TUR, BP_GSV, BP_CLD_CONFIG, BP_GRAMMAGE, BP_CLASSIFICATION, BP_CREATED_BY, BP_CREATED_DATE) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSalesCatData() {
		Query query;
		List<String> liReturn = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			query = session
					.createNativeQuery("SELECT DISTINCT CATEGORY AS sales_category FROM TBL_VAT_COMM_PRODUCT_MASTER WHERE PRODUCT_FLAG ='1' ORDER BY CATEGORY ");  //Sarin Changes - Added OrderBy
			liReturn = query.list();

		} catch (Exception e) {
			e.printStackTrace();
			liReturn.add(e.toString());
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblLaunchBasebacks> getPsaCatData(String salesCat) {
		List<TblLaunchBasebacks> listTblLaunchMaster = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT DISTINCT SS_CATEGORY FROM TBL_VAT_COMM_PRODUCT_MASTER AS tvcpm WHERE CATEGORY =  '"
							+ salesCat + "' AND SS_CATEGORY IS NOT NULL ORDER BY SS_CATEGORY ");  //Sarin Changes - Added By OrderBy
			List<String> listOfData = query.list();
			listOfData.forEach(item -> {
				TblLaunchBasebacks tblLaunchBasebacks = new TblLaunchBasebacks();
				tblLaunchBasebacks.setPsaCategory(item);
				listTblLaunchMaster.add(tblLaunchBasebacks);
			});
		} catch (Exception e) {
			e.printStackTrace();
			TblLaunchBasebacks tblLaunchBasebacks = new TblLaunchBasebacks();
			tblLaunchBasebacks.setError(e.toString());
		}
		return listTblLaunchMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblLaunchBasebacks> getBrandOnPsaCatData(String psaCat, String salesCat) {
		List<TblLaunchBasebacks> listTblLaunchMaster = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT DISTINCT BRAND FROM TBL_VAT_COMM_PRODUCT_MASTER AS tvcpm WHERE SS_CATEGORY = '" + psaCat
							+ "' AND CATEGORY = '"+salesCat+"' AND BRAND IS NOT NULL ORDER BY BRAND ");  //Sarin Changes - Added OrderBy
			List<String> listOfData = query.list();
			listOfData.forEach(item -> {
				TblLaunchBasebacks psaCatData = new TblLaunchBasebacks();
				psaCatData.setBrand(item);
				listTblLaunchMaster.add(psaCatData);
			});
		} catch (Exception e) {
			e.printStackTrace();
			TblLaunchBasebacks psaCatData = new TblLaunchBasebacks();
			psaCatData.setError(e.toString());
			listTblLaunchMaster.add(psaCatData);
		}
		return listTblLaunchMaster;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String saveLaunchDetails(SaveLaunchMasterRequest tblLaunchMaster, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int launchId = 0;
		ResultSet rs = null;
		try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_MASTER,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, tblLaunchMaster.getLaunchName());
			preparedStatement.setString(2, tblLaunchMaster.getLaunchDate());
			preparedStatement.setString(3, tblLaunchMaster.getLaunchNature());
			preparedStatement.setString(4, tblLaunchMaster.getLaunchNature2());
			preparedStatement.setString(5, tblLaunchMaster.getLaunchBusinessCase());
			preparedStatement.setString(6, tblLaunchMaster.getCategorySize());
			preparedStatement.setString(7, tblLaunchMaster.getClassification());
			preparedStatement.setString(8, userId);
			preparedStatement.setTimestamp(9, new Timestamp(new Date().getTime()));
			String[] dateMoc = tblLaunchMaster.getLaunchDate().split("/");
			preparedStatement.setString(10, dateMoc[1] + dateMoc[2]);
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs != null && rs.next()) {
				launchId = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Integer.toString(launchId);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public int saveLaunchBasepacks(SaveLaunchBasepacksListReq tblLaunchbasePacksList, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int result = 0;
		PreparedStatement batchUpdate = null;
		try {
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BASEPACK where LAUNCH_ID=?");
			batchUpdate.setString(1, tblLaunchbasePacksList.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveLaunchBasepackRequest> liRequests = tblLaunchbasePacksList.getListBasePacks();
			for (SaveLaunchBasepackRequest tblLaunchbasePacks : liRequests) {
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_BASEPACK)) {
					preparedStatement.setString(1, tblLaunchbasePacksList.getLaunchId());
					preparedStatement.setString(2, tblLaunchbasePacks.getSalesCategory());
					preparedStatement.setString(3, tblLaunchbasePacks.getPsaCategory());
					preparedStatement.setString(4, tblLaunchbasePacks.getBrand());
					preparedStatement.setString(5, tblLaunchbasePacks.getCode());
					String bpDesc = null;
					if (!tblLaunchbasePacks.getDescription().contains(":")) {
						bpDesc = tblLaunchbasePacks.getCode() + " : " + tblLaunchbasePacks.getDescription();
					} else {
						bpDesc = tblLaunchbasePacks.getDescription();
					}
					preparedStatement.setString(6, bpDesc);
					preparedStatement.setString(7, tblLaunchbasePacks.getMrp());
					preparedStatement.setString(8, tblLaunchbasePacks.getTur());
					preparedStatement.setString(9, tblLaunchbasePacks.getGsv());
					preparedStatement.setString(10, tblLaunchbasePacks.getCldConfig());
					preparedStatement.setString(11, tblLaunchbasePacks.getGrammage());
					preparedStatement.setString(12, tblLaunchbasePacks.getClassification());
					preparedStatement.setString(13, userId);
					preparedStatement.setTimestamp(14, new Timestamp(new Date().getTime()));
					
					result = preparedStatement.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getLaunchBasepackData(ArrayList<String> headerDetail, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			Iterator itr = new ArrayList<>().iterator();
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
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	@Override
	public TblLaunchMaster getStoreDetails(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			TblLaunchMaster tblLaunchMaster = null;
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_NATURE,LAUNCH_NATURE_2,CLASSIFICATION FROM TBL_LAUNCH_MASTER WHERE LAUNCH_ID = '"
							+ launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				tblLaunchMaster = new TblLaunchMaster();
				tblLaunchMaster.setLaunchNature(rs.getString("LAUNCH_NATURE"));
				tblLaunchMaster.setLaunchNature2(rs.getString("LAUNCH_NATURE_2"));
				tblLaunchMaster.setClassification(rs.getString("CLASSIFICATION"));
			}
			return tblLaunchMaster;
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

	@Override
	public List<String> getLaunchStores() {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<String> liStrings = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm ORDER BY UPPER(CURRENT_STORE_FORMAT)");
			rs = stmt.executeQuery();
			while (rs.next()) {
				liStrings.add(rs.getString(1));
			}
			return liStrings;
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
	public int storeCount(List<String> listOfL1) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (listOfL1.contains("ALL CUSTOMERS")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE'");
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:l1Chain)");
				queryToGetCustomeChainL1.setParameterList("l1Chain", listOfL1);
			}
			//kiran - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int storeCountByLaunchClass(String classification) {
		try {
			String launchClassification = "";
			if (classification.equals("Gold")) {
				launchClassification = "'GOLD','SILVER','BRONZE'";
			} else if (classification.equals("Silver")) {
				launchClassification = "'SILVER','BRONZE'";
			} else {
				launchClassification = "'BRONZE'";
			}
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND LAUNCH_FORMAT IN("
							+ launchClassification + ") ");
			//kiran - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCustomerStoreFormat(List<String> launchStore) {
		List<String> liStrings = null;
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACCOUNT_NAME != '' ORDER BY UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))");

			liStrings = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			liStrings.add(ex.toString());
		}
		return liStrings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStoreCount(List<String> account) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (account.isEmpty() || account.contains("ALL CUSTOMERS")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != ''");
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:account)");
				queryToGetCustomeChainL1.setParameterList("account", account);
			}
			//kiran - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			String storeCount = String.valueOf(count.get(0).intValue());
			return storeCount;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLaunchStores(List<String> accounts) {
		List<String> toReturn = null;
		try {
			Query queryToGetCustomeChainL1 = null;
			if (accounts.isEmpty() || accounts.contains("ALL CUSTOMERS")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACCOUNT_NAME != ''");
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm  WHERE ACCOUNT_NAME IN (:accounts)");
				queryToGetCustomeChainL1.setParameterList("accounts", accounts);
			}
			toReturn = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			toReturn.add(ex.toString());
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getStoreFormat() {
		List<String> liReturn = null;
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER where ACCOUNT_NAME != ''");
			liReturn = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			liReturn.add(ex.toString());
		}
		return liReturn;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTownSpecific() {
		List<String> liReturn = null;
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT TOWN_CLASSIFICATION FROM TBL_VAT_COMM_OUTLET_MASTER where ACCOUNT_NAME != '' GROUP BY TOWN_CLASSIFICATION");
			liReturn = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			liReturn.add(ex.toString());
		}
		return liReturn;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public int saveLaunchClustersAndAcc(SaveLaunchClustersRequest saveLaunchClustersRequest, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int clusterId = 0;
		Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
				.createNativeQuery("SELECT * FROM TBL_LAUNCH_CLUSTERS where CLUSTER_LAUNCH_ID = "
						+ saveLaunchClustersRequest.getLaunchId() + " and LAUNCH_PLANNED = '"
						+ saveLaunchClustersRequest.getLaunchPlanned() + "'");
		if (queryToGetCustomeChainL1.list().size() == 0) {
			ResultSet rs = null;
			try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_CLUSTERS,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, saveLaunchClustersRequest.getLaunchId());
				preparedStatement.setString(2, saveLaunchClustersRequest.getClusterString());
				preparedStatement.setString(3, saveLaunchClustersRequest.getAccountString());
				preparedStatement.setString(4, saveLaunchClustersRequest.getStoreFormat().replace("'", "").trim()); // Added By Harsha
				preparedStatement.setString(5, saveLaunchClustersRequest.getCustomerStoreFormat().replace("'", "").trim()); // Added By Harsha
	
				preparedStatement.setString(6, saveLaunchClustersRequest.getTotalStoresToLaunch());
				preparedStatement.setString(7, saveLaunchClustersRequest.getLaunchPlanned());
				preparedStatement.setString(8, userId);
				preparedStatement.setTimestamp(9, new Timestamp(new Date().getTime()));
				preparedStatement.setString(10, saveLaunchClustersRequest.getIncludeAllStoreFormats());  //Sarin Changes - Added for CustomStoreSelection Q1Sprint Feb2021
				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					clusterId = rs.getInt(1);
				}
			} catch (Exception e) {
				logger.error("Exception: " + e);
			} finally {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_CLUSTERS SET CLUSTER_REGION=?0,CLUSTER_ACCOUNT=?1,CLUSTER_STORE_FORMAT=?2,CLUSTER_CUST_STORE_FORMAT=?3,TOTAL_STORES_TO_LAUNCH=?4,UPDATED_BY=?5,UPDATED_DATE=?6, INCLUDE_ALL_STORE_FORMAT=?9 WHERE CLUSTER_LAUNCH_ID=?7 and LAUNCH_PLANNED = ?8");  //Sarin - Added Parameters position //Sarin Changes - Added for CustomStoreSelection Q1Sprint Feb2021
			query2.setParameter(0, saveLaunchClustersRequest.getClusterString());
			query2.setParameter(1, saveLaunchClustersRequest.getAccountString());
			query2.setParameter(2, saveLaunchClustersRequest.getStoreFormat().replace("'", "").trim());// Added  By Harsha
			query2.setParameter(3, saveLaunchClustersRequest.getCustomerStoreFormat().replace("'", "").trim());// Added  By Harsha
			query2.setParameter(4, saveLaunchClustersRequest.getTotalStoresToLaunch());
			query2.setParameter(5, userId);
			query2.setParameter(6, new Timestamp(new Date().getTime()));
			query2.setParameter(7, saveLaunchClustersRequest.getLaunchId());
			query2.setParameter(8, saveLaunchClustersRequest.getLaunchPlanned());
			query2.setParameter(9, saveLaunchClustersRequest.getIncludeAllStoreFormats());  //Sarin Changes - Added for CustomStoreSelection Q1Sprint Feb2021
			clusterId = query2.executeUpdate();
		}
		return clusterId;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String updateLaunchMaster(SaveLaunchMasterRequest tblLaunchMaster, String userId) {
		int toReturn = 0;
		String responseId = "";
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET LAUNCH_NAME=?0,LAUNCH_DATE=?1,LAUNCH_NATURE=?2,LAUNCH_NATURE_2=?3,LAUNCH_BUSINESS_CASE=?4,CATEGORY_SIZE=?5,CLASSIFICATION=?6,UPDATED_BY=?7,UPDATED_DATE=?8 WHERE LAUNCH_ID=?9");  //Sarin - Added Parameters position
			query2.setParameter(0, tblLaunchMaster.getLaunchName());
			query2.setParameter(1, tblLaunchMaster.getLaunchDate());
			query2.setParameter(2, tblLaunchMaster.getLaunchNature());
			query2.setParameter(3, tblLaunchMaster.getLaunchNature2());
			query2.setParameter(4, tblLaunchMaster.getLaunchBusinessCase());
			query2.setParameter(5, tblLaunchMaster.getCategorySize());
			query2.setParameter(6, tblLaunchMaster.getClassification());
			query2.setParameter(7, userId);
			query2.setParameter(8, new Timestamp(new Date().getTime()));
			query2.setParameter(9, tblLaunchMaster.getLaunchId());
			toReturn = query2.executeUpdate();
			if (toReturn != 1) {
				throw new Exception("Something went wrong while updating launch");
			}
			responseId = tblLaunchMaster.getLaunchId();
		} catch (Exception e) {
			return e.toString();
		}
		return responseId;
	}
	
	/*@Override // As Part of US 7 in sprint 7 Jan 22 
	public List<ArrayList<String>> getLaunchClusterDataforStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			downloadDataList.add(headerDetail);
			String[] l1l2CustomerList = downloadLaunchClusterRequest.getL1l2Cluster().split(",");
			List<String> listOfL1L2 = Arrays.asList(l1l2CustomerList);

			if (!listOfL1L2.contains("ALL CUSTOMERS")) {
				for (int i = 0; i < l1l2CustomerList.length; i++) {
					if (l1l2CustomerList[i].split(":").length > 1) {
						String custL1 = l1l2CustomerList[i].split(":")[0];
						String custL2 = l1l2CustomerList[i].split(":")[1];
						List<String> listOfL1 = new ArrayList<>();
						listOfL1.add(custL1);
						List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
						String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
						List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
						listOfAllForL1.retainAll(listOfSelectedForL1);
						String storeFormat = downloadLaunchClusterRequest.getStoreFormat();

						listOfSelectedForL1.forEach(data -> {
							ArrayList<String> dataObj = new ArrayList<>();
							dataObj.add(data);
							dataObj.add(custL1);
							dataObj.add(custL2);
							dataObj.add(storeFormat);
							dataObj.add("Yes");
							downloadDataList.add(dataObj);
						});
					}
				}
			} else {
				String custL1 = l1l2CustomerList[0];
				String custL2 = "";
				List<String> listOfL1 = new ArrayList<>();
				listOfL1.add(custL1);
				List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
				String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
				List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
				listOfAllForL1.retainAll(listOfSelectedForL1);
				String storeFormat = downloadLaunchClusterRequest.getStoreFormat();

				listOfSelectedForL1.forEach(data -> {
					ArrayList<String> dataObj = new ArrayList<>();
					dataObj.add(data);
					dataObj.add(custL1);
					dataObj.add(custL2);
					dataObj.add(storeFormat);
					dataObj.add("Yes");
					downloadDataList.add(dataObj);
				});
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}
*/
	
	

	@Override
	public List<ArrayList<String>> getLaunchClusterDataforStoreFormat(ArrayList<String> headerDetail, String userId,
			DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String wholeCluster = "";
		String wholeCuststrfrmt = "";
		try {// Harsha chnages starts here 
			downloadDataList.add(headerDetail);
			String[] l1l2CustomerList = downloadLaunchClusterRequest.getL1l2Cluster().split(",");
			String storeFormat12 = downloadLaunchClusterRequest.getStoreFormat();
			
			String StoreFormate = downloadLaunchClusterRequest.getSelStoreFormat();
			String launchId=downloadLaunchClusterRequest.getLaunchId();
			List<String> listOfL1L2 = Arrays.asList(l1l2CustomerList);
			String busiClasification = getClasisification(launchId);
			String unprocessedString = downloadLaunchClusterRequest.getCustStoreFormat();;
			String customerStoreFormat = unprocessedString.replaceAll("\'","");
			
		
			String [] items2 = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
			List<String> regionCluster = Arrays.asList(items2);
	
			if (!listOfL1L2.contains("ALL CUSTOMERS") 
					) {
				List<String> allIndiaCluster = new ArrayList<String>();
				if(downloadLaunchClusterRequest.getRegionCluster().toString().contains("ALL INDIA")) {
					
					for(String accountsNames : getClusters()) {
							allIndiaCluster.add(accountsNames);
					}
					
				}
				
		
				
				for (int i = 0; i < l1l2CustomerList.length; i++) {
					if (l1l2CustomerList[i].split(":").length > 1) {
						String custL1 = l1l2CustomerList[i].split(":")[0];
						String custL2 = l1l2CustomerList[i].split(":")[1];
						List<String> listOfL1 = new ArrayList<>();
						 List<String> custStorefmt = new ArrayList<String>();
						listOfL1.add(custL1);
						
						if(!customerStoreFormat.isEmpty()) {
							
							List<String> regionName = new ArrayList<String>();
							if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
								
								for(String accountsNames : getClusters()) {
									String[] stringarray = accountsNames.split(":");  
									
									if(!stringarray[0].contains("B")) {
										regionName.add("'"+stringarray[1].toString()+"'");
									}
								}
							}
							
							else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
								String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
								List<String> container = Arrays.asList(items);
								 LinkedHashSet<String> Account=new LinkedHashSet();
							
								for(String embeder:container) {
									String[] stringarray = embeder.split(":");  
									
									if(!stringarray[0].contains("B")) {
										regionName.add("'"+stringarray[1].toString()+"'");
									}
								}
							}
							
							
							
							
						
							
							
							String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
							List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
							
							for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
							
								custStorefmt.add("'"+valueCustomerStoreFormat.toString()+"'");
								
							}
							
						
							wholeCluster=regionName.toString();
							wholeCluster=wholeCluster.replace("[", "");
							wholeCluster=wholeCluster.replace("]", "");
						
							
							
							wholeCuststrfrmt=custStorefmt.toString();
							wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
							wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
							
							
						}
						String thing =wholeCluster;
						
						
						//System.out.println(listOfSelectedForL1);
						String custStoreFormat = wholeCuststrfrmt;
						List<String> listOfSelectedForL1;
						
						List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
						String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
						if(downloadLaunchClusterRequest.getRegionCluster().toString().contains("ALL INDIA")) {
							listOfSelectedForL1 = allIndiaCluster;
						}
						else {
							listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
						}
					
						listOfAllForL1.retainAll(listOfSelectedForL1);
						String thing2 =wholeCluster;
						
						String custStoreFormat2 = wholeCuststrfrmt;
						
						listOfSelectedForL1.forEach(data -> {
							ArrayList<String> dataObj = new ArrayList<>();
							dataObj.add(data);
							dataObj.add(custL1);
							dataObj.add(custL2);
							dataObj.add(storeFormat12);
							dataObj.add("Yes");
							dataObj.add(getCountofStores( data, custL1,  busiClasification,StoreFormate, custStoreFormat, thing));
							downloadDataList.add(dataObj);
						});
					}
				}
			} else {
				String custL1 = l1l2CustomerList[0];
				String custL2 = "";
				List<String> listOfL1 = new ArrayList<>();
				List<String> listOfL2 = new ArrayList<>();
				List<String> custStorefmt = new ArrayList<String>();
				listOfL1.add(custL1);
				
				if(!customerStoreFormat.isEmpty()) {
					
					List<String> regionName = new ArrayList<String>();
					if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
						
						for(String accountsNames : getClusters()) {
							String[] stringarray = accountsNames.split(":");  
							
							if(!stringarray[0].contains("B")) {
								regionName.add("'"+stringarray[1].toString()+"'");
							}
						}
					}
					
					else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
						String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
						List<String> container = Arrays.asList(items);
						 LinkedHashSet<String> Account=new LinkedHashSet();
					
						for(String embeder:container) {
							String[] stringarray = embeder.split(":");  
							
							if(!stringarray[0].contains("B")) {
								regionName.add("'"+stringarray[1].toString()+"'");
							}
						}
					}
					
					
					
					
				
					
					
					String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
					List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
					
					for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
					
						custStorefmt.add("'"+valueCustomerStoreFormat.toString()+"'");
						
					}
					
				
					wholeCluster=regionName.toString();
					wholeCluster=wholeCluster.replace("[", "");
					wholeCluster=wholeCluster.replace("]", "");
					
					
					wholeCuststrfrmt=custStorefmt.toString();
					wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
					wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
					
				}
				
				String thing2 =wholeCluster;
				
				String custStoreFormat2 = wholeCuststrfrmt;
				
				
				
				
				
				List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
				String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
				List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
				List<String> listOfSelectedForL2 = new ArrayList<String>();
				listOfAllForL1.retainAll(listOfSelectedForL1);
				String custStoreFormat = downloadLaunchClusterRequest.getCustStoreFormat();
				
				if(listOfL1.contains("ALL CUSTOMERS") && listOfSelectedForL1.contains("ALL INDIA")) {
					 LinkedHashSet<String> listCluster = new LinkedHashSet<String>();
					Set<String> lhs = new LinkedHashSet<String>();
					listCluster=getClusters();
					int wholeSize=listCluster.size();
					String[] arr = new String[wholeSize];
			        arr = listCluster.toArray(arr);
			        for (int j = 0; j < arr.length; j++) {
			        	String listOfaacc = arr[j];
			            
			            listOfSelectedForL2.add(listOfaacc.toString());
			        }
			      
			        listOfSelectedForL1=listOfSelectedForL2;
			      
			        
			    
			        	l1l2CustomerList=getDistinctAccounts().split(",");
			     
					
					for (int i = 0; i < l1l2CustomerList.length; i++) {
						if (l1l2CustomerList[i]!=null) {
							String custL11 = l1l2CustomerList[i].split(":")[0];
							String custL21 = l1l2CustomerList[i].split(":")[0];
							List<String> listOfL11 = new ArrayList<>();
							 List<String> custStorefmt1 = new ArrayList<String>();
							listOfL11.add(custL11);
							List<String> regionName = new ArrayList<String>();
							if(!customerStoreFormat.isEmpty()) {
								
								
								if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
									
									for(String accountsNames : getClusters()) {
										String[] stringarray = accountsNames.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								
								else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
									String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
									List<String> container = Arrays.asList(items);
									 LinkedHashSet<String> Account=new LinkedHashSet();
									
									for(String embeder:container) {
										String[] stringarray = embeder.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								
								
								
							
								
							
								String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
								List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
								
								for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
									
									custStorefmt1.add("'"+valueCustomerStoreFormat.toString()+"'");
									
								}
								
								
								wholeCluster=regionName.toString();
								wholeCluster=wholeCluster.replace("[", "");
								wholeCluster=wholeCluster.replace("]", "");
								
								
								wholeCuststrfrmt=custStorefmt1.toString();
								wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
								wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
								
							
								
								
								
							}
							String thing =wholeCluster;
						
							
							
							
							String custStoreFormat1 = wholeCuststrfrmt;
						
							
							List<String> listOfAllForL11 = createPromoService.getClusterOnCustomerList(listOfL11);
							
							String thing3 =wholeCluster;
						
							
							
							
							String custStoreFormat3 = wholeCuststrfrmt;
							
							if(((thing3!=null && custStoreFormat3!=null) && (!custStoreFormat3.isEmpty() && !thing3.isEmpty()) )) { //&& (StoreFormate.isEmpty() && StoreFormate.equals("''")) ) {// come here
								
								
							

							for(String namer : getClusters()) {
								
								
								
								
								
								String regionCluster12="";
								ArrayList<String> finallist = new ArrayList<>();
								
									Session session = sessionFactory.getCurrentSession();
									String distinctAccounts="";
									List<String> set = new ArrayList<String>();
									SessionImpl sessionImpl = (SessionImpl) session;
									PreparedStatement stmt = null;
									ResultSet rs = null;
									String just=namer;
									if(!just.contains("B0")) {
										String[] parts = just.split(":");
										regionCluster12 = parts[1];
										
										
										try {
											stmt = sessionImpl.connection().prepareStatement("Select ACCOUNT_NAME,FINAL_CLUSTER,COUNT(ACCOUNT_NAME) AS TOTAL from (SELECT * FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN (" + 
													"SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+thing3+")" + 
													"AND ACCOUNT_NAME != '' " +
													"AND CUSTOMER_STORE_FORMAT IN ("+ custStoreFormat3 +"))  AND FINAL_CLUSTER IN (" + thing3 +") ) as aa where aa.FINAL_CLUSTER IN ('" + regionCluster12 +"')  group by ACCOUNT_NAME");
											rs = stmt.executeQuery();
											
											while (rs.next()) {
												
											
											ArrayList<String> dataObj = new ArrayList<>();
											dataObj.add(namer);
											dataObj.add(rs.getString("ACCOUNT_NAME"));
											dataObj.add(rs.getString("ACCOUNT_NAME"));
											dataObj.add(storeFormat12);
											dataObj.add("Yes");
											dataObj.add(String.valueOf(rs.getInt("TOTAL")));
											
											downloadDataList.add(dataObj);
											}
										
										} catch (HibernateException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										
									}
									
									else {
										ArrayList<String> dataObj = new ArrayList<>();
										dataObj.add(namer);
										dataObj.add(" ");
										dataObj.add(" ");
										dataObj.add(storeFormat12);
										dataObj.add("Yes");
										dataObj.add("0");
										downloadDataList.add(dataObj);
									}
								
								
								
								
								
							} return downloadDataList;
						}
					
							listOfSelectedForL1.forEach(data -> {
								ArrayList<String> dataObj = new ArrayList<>();
								dataObj.add(data);
								dataObj.add(custL11);
								
								dataObj.add(custL21);
								dataObj.add(storeFormat12);
								dataObj.add("Yes");
								dataObj.add(getAllStoreCount( data, custL11,  busiClasification,StoreFormate, custStoreFormat3, thing3));
								downloadDataList.add(dataObj);
							});
						}
					}
				
			     
				}
				else if(listOfL1L2.contains("ALL CUSTOMERS")) {
				

					 LinkedHashSet<String> lstClusteracc = new LinkedHashSet<String>();
					Set<String> lhs = new LinkedHashSet<String>();
					lstClusteracc=getClusters();
					int wholeSize=lstClusteracc.size();
					String[] arr = new String[wholeSize];
			        arr = lstClusteracc.toArray(arr);
			        for (int j = 0; j < arr.length; j++) {
			        	String listofL2 = arr[j];
			            
			            listOfSelectedForL2.add(listofL2.toString());
			        }
			       
			        
			   
			        	l1l2CustomerList=getDistinctAccounts().split(",");
			    
					
					for (int i = 0; i < l1l2CustomerList.length; i++) {
						if (l1l2CustomerList[i]!=null) {
							String custL11 = l1l2CustomerList[i].split(":")[0];
							String custL21 = l1l2CustomerList[i].split(":")[0];
							List<String> listOfL11 = new ArrayList<>();
							 List<String> custStorefmt1 = new ArrayList<String>();
							listOfL11.add(custL11);
							
							if(!customerStoreFormat.isEmpty()) {
								
								List<String> regionName = new ArrayList<String>();
								if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
									
									for(String accountsNames : getClusters()) {
										String[] stringarray = accountsNames.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
									String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
									List<String> container = Arrays.asList(items);
									 LinkedHashSet<String> Account=new LinkedHashSet();
									
									for(String embeder:container) {
										String[] stringarray = embeder.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								
								
								
							
								
								
								String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
								List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
								
								for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
									
									custStorefmt1.add("'"+valueCustomerStoreFormat.toString()+"'");
									
								}
								
							
								wholeCluster=regionName.toString();
								wholeCluster=wholeCluster.replace("[", "");
								wholeCluster=wholeCluster.replace("]", "");
								
								wholeCuststrfrmt=custStorefmt1.toString();
								wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
								wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
								
								
							}
							String thing =wholeCluster;
							
							String custStoreFormat1 = wholeCuststrfrmt;
							
							
							List<String> listOfAllForL11 = createPromoService.getClusterOnCustomerList(listOfL11);
							
							String thing3 =wholeCluster;
							
							
							
							
							String custStoreFormat3 = wholeCuststrfrmt;
							
							
							if(((thing3!=null && custStoreFormat3!=null) && (!custStoreFormat3.isEmpty() && !thing3.isEmpty())  )) {
								for(String namer : regionCluster) {
									
									
									
									
									//ListArrayList<String> downloadDataList = new ArrayList<ArrayList<String>>();
									String regionCluster12="";
									ArrayList<String> finallist = new ArrayList<>();
									//	List<String> Clusters = new ArrayList<String>();
										Session session = sessionFactory.getCurrentSession();
										String distinctAccounts="";
										List<String> set = new ArrayList<String>();
										SessionImpl sessionImpl = (SessionImpl) session;
										PreparedStatement stmt = null;
										ResultSet rs = null;
										String just=namer;
										if(!just.contains("B0")) {
											String[] parts = just.split(":");
											regionCluster12 = parts[1];
											
											
											
											
											
											try {
												stmt = sessionImpl.connection().prepareStatement("Select ACCOUNT_NAME,FINAL_CLUSTER,COUNT(ACCOUNT_NAME) AS TOTAL from (SELECT * FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN (" + 
														"SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+thing3+")" + 
														"AND ACCOUNT_NAME != '' " +
														"AND CUSTOMER_STORE_FORMAT IN ("+ custStoreFormat3 +"))  AND FINAL_CLUSTER IN (" + thing3 +") ) as aa where aa.FINAL_CLUSTER IN ('" + regionCluster12 +"')  group by ACCOUNT_NAME");
												rs = stmt.executeQuery();
												
												while (rs.next()) {
													
												
												
												ArrayList<String> dataObj = new ArrayList<>();
												dataObj.add(just);
												dataObj.add(rs.getString("ACCOUNT_NAME"));
												dataObj.add(rs.getString("ACCOUNT_NAME"));
												dataObj.add(custStoreFormat1);
												dataObj.add("Yes");
												dataObj.add(String.valueOf(rs.getInt("TOTAL")));
												
												downloadDataList.add(dataObj);
												}
											
											} catch (HibernateException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											
										}
										
										else {
											ArrayList<String> dataObj = new ArrayList<>();
											dataObj.add(namer);
											dataObj.add(" ");
											dataObj.add(" ");
											dataObj.add(custStoreFormat1);
											dataObj.add("Yes");
											dataObj.add("0");
											downloadDataList.add(dataObj);
										}
									
									
									
									
									
								} return downloadDataList;
							}
							
							
							
							
							else {
							
								listOfSelectedForL1.forEach(data -> {
								ArrayList<String> dataObj = new ArrayList<>();
								dataObj.add(data);
								dataObj.add(custL11);
								dataObj.add(custL21);
								dataObj.add(storeFormat12);
								dataObj.add("Yes");
								dataObj.add(getAllStoreCount( data, custL11,  busiClasification,StoreFormate, custStoreFormat3, thing3));
								downloadDataList.add(dataObj);
							});
							}
						}
					}
		
				}

			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}
	/*
	 @Override // Commented As part of U7 Sprint 7 Jan22
	public List<ArrayList<String>> getLaunchClusterDataforCustomerStoreFormat(ArrayList<String> headerDetail,
			String userId, DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			downloadDataList.add(headerDetail);
			String[] l1l2CustomerList = downloadLaunchClusterRequest.getL1l2Cluster().split(",");
			List<String> listOfL1L2 = Arrays.asList(l1l2CustomerList);

			if (!listOfL1L2.contains("ALL CUSTOMERS")) {
				for (int i = 0; i < l1l2CustomerList.length; i++) {
					if (l1l2CustomerList[i].split(":").length > 1) {
						String custL1 = l1l2CustomerList[i].split(":")[0];
						String custL2 = l1l2CustomerList[i].split(":")[1];
						List<String> listOfL1 = new ArrayList<>();
						listOfL1.add(custL1);
						List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
						String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
						List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
						listOfAllForL1.retainAll(listOfSelectedForL1);
						String custStoreFormat = downloadLaunchClusterRequest.getCustStoreFormat();

						listOfSelectedForL1.forEach(data -> {
							ArrayList<String> dataObj = new ArrayList<>();
							dataObj.add(data);
							dataObj.add(custL1);
							dataObj.add(custL2);
							dataObj.add(custStoreFormat);
							dataObj.add("Yes");
							downloadDataList.add(dataObj);
						});
					}
				}
			} else {
				String custL1 = l1l2CustomerList[0];
				String custL2 = "";
				List<String> listOfL1 = new ArrayList<>();
				listOfL1.add(custL1);
				List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
				String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
				List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
				listOfAllForL1.retainAll(listOfSelectedForL1);
				String custStoreFormat = downloadLaunchClusterRequest.getCustStoreFormat();

				listOfSelectedForL1.forEach(data -> {
					ArrayList<String> dataObj = new ArrayList<>();
					dataObj.add(data);
					dataObj.add(custL1);
					dataObj.add(custL2);
					dataObj.add(custStoreFormat);
					dataObj.add("Yes");
					downloadDataList.add(dataObj);
				});
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	 */

	// Harsha chnages starts here - ends here
	@Override
	public List<ArrayList<String>> getLaunchClusterDataforCustomerStoreFormat(ArrayList<String> headerDetail,
			String userId, DownloadLaunchClusterRequest downloadLaunchClusterRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		String wholeCluster = "";
		String wholeCuststrfrmt = "";
		try {// Harsha's Implementation starts
			downloadDataList.add(headerDetail);
			String[] l1l2CustomerList = downloadLaunchClusterRequest.getL1l2Cluster().split(",");
			
			
			String StoreFormate = downloadLaunchClusterRequest.getSelStoreFormat();
			String launchId= downloadLaunchClusterRequest.getLaunchId();
			List<String> listOfL1L2 = Arrays.asList(l1l2CustomerList);
			String busiClasification = getClasisification(launchId);
			String customerStoreFormat = downloadLaunchClusterRequest.getCustStoreFormat();
			
			
			String [] items2 = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
			List<String> regionCluster = Arrays.asList(items2);
			
			
			
		
			
			

			if (!listOfL1L2.contains("ALL CUSTOMERS") //|| downloadLaunchClusterRequest.getRegionCluster().toString().contains("ALL INDIA")
					) {
				List<String> allIndiaCluster = new ArrayList<String>();
				if(downloadLaunchClusterRequest.getRegionCluster().toString().contains("ALL INDIA")) {
					
					for(String accountsNames : getClusters()) {
							allIndiaCluster.add(accountsNames);
					}
					
				}
				
				
				
				for (int i = 0; i < l1l2CustomerList.length; i++) {
					if (l1l2CustomerList[i].split(":").length > 1) {
						String custL1 = l1l2CustomerList[i].split(":")[0];
						String custL2 = l1l2CustomerList[i].split(":")[1];
						List<String> listOfL1 = new ArrayList<>();
						 List<String> custStorefmt = new ArrayList<String>();
						listOfL1.add(custL1);
						
						if(!customerStoreFormat.isEmpty()) {
							
							List<String> regionName = new ArrayList<String>();
							if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
								
								for(String accountsNames : getClusters()) {
									String[] stringarray = accountsNames.split(":");  
									
									if(!stringarray[0].contains("B")) {
										regionName.add("'"+stringarray[1].toString()+"'");
									}
								}
							}
							
							else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
								String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
								List<String> container = Arrays.asList(items);
								 LinkedHashSet<String> Account=new LinkedHashSet();
								
								for(String embeder:container) {
									String[] stringarray = embeder.split(":");  
									
									if(!stringarray[0].contains("B")) {
										regionName.add("'"+stringarray[1].toString()+"'");
									}
								}
							}
							
							
							
							
						
							
							
							String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
							List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
						
							for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
								
								custStorefmt.add("'"+valueCustomerStoreFormat.toString()+"'");
								
							}
							
							
							wholeCluster=regionName.toString();
							wholeCluster=wholeCluster.replace("[", "");
							wholeCluster=wholeCluster.replace("]", "");
							
							wholeCuststrfrmt=custStorefmt.toString();
							wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
							wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
							
							
						}
						String thing =wholeCluster;
						
						
						
						//System.out.println(listOfSelectedForL1);
						String custStoreFormat = wholeCuststrfrmt;
						List<String> listOfSelectedForL1;
						
						List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
						String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
						if(downloadLaunchClusterRequest.getRegionCluster().toString().contains("ALL INDIA")) {
							listOfSelectedForL1 = allIndiaCluster;
						}
						else {
							listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
						}
					
						listOfAllForL1.retainAll(listOfSelectedForL1);
						String thing2 =wholeCluster;
						
						String custStoreFormat2 = wholeCuststrfrmt;
						
						listOfSelectedForL1.forEach(data -> {
							ArrayList<String> dataObj = new ArrayList<>();
							dataObj.add(data);
							dataObj.add(custL1);
							dataObj.add(custL2);
							dataObj.add(custStoreFormat);
							dataObj.add("Yes");
							dataObj.add(getCountofStores( data, custL1,  busiClasification,StoreFormate, custStoreFormat, thing));
							downloadDataList.add(dataObj);
						});
					}
				}
			} else {
				String custL1 = l1l2CustomerList[0];
				String custL2 = "";
				List<String> listOfL1 = new ArrayList<>();
				List<String> listOfL2 = new ArrayList<>();
				List<String> custStorefmt = new ArrayList<String>();
				listOfL1.add(custL1);
				
				if(!customerStoreFormat.isEmpty()) {
				
					List<String> regionName = new ArrayList<String>();
					if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
						
						for(String accountsNames : getClusters()) {
							String[] stringarray = accountsNames.split(":");  
							
							if(!stringarray[0].contains("B")) {
								regionName.add("'"+stringarray[1].toString()+"'");
							}
						}
					}
					
					else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
						String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
						List<String> container = Arrays.asList(items);
						 LinkedHashSet<String> Account=new LinkedHashSet();
						
						for(String embeder:container) {
							String[] stringarray = embeder.split(":");  
							
							if(!stringarray[0].contains("B")) {
								regionName.add("'"+stringarray[1].toString()+"'");
							}
						}
					}
					
					
					
					
				
					
				
					String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
					List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
					
					for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
						
						custStorefmt.add("'"+valueCustomerStoreFormat.toString()+"'");
						
					}
					
				
					wholeCluster=regionName.toString();
					wholeCluster=wholeCluster.replace("[", "");
					wholeCluster=wholeCluster.replace("]", "");
					
					wholeCuststrfrmt=custStorefmt.toString();
					wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
					wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
					
					//getCustomerStoreFormate( regionName, accountName,  busiClasification, CustomerStoreFormate );
					
					
				}
				
				String thing2 =wholeCluster;
				
				String custStoreFormat2 = wholeCuststrfrmt;
				
				
				
				
				
				List<String> listOfAllForL1 = createPromoService.getClusterOnCustomerList(listOfL1);
				String[] ArrayOfSelectedForL1 = downloadLaunchClusterRequest.getRegionCluster().split(",");
				List<String> listOfSelectedForL1 = Arrays.asList(ArrayOfSelectedForL1);
				List<String> listOfSelectedForL2 = new ArrayList<String>();
				listOfAllForL1.retainAll(listOfSelectedForL1);
				String custStoreFormat = downloadLaunchClusterRequest.getCustStoreFormat();
				
				if(listOfL1.contains("ALL CUSTOMERS") && listOfSelectedForL1.contains("ALL INDIA")) {
					 LinkedHashSet<String> listClusteraccn = new LinkedHashSet<String>();
					Set<String> lhs = new LinkedHashSet<String>();
					listClusteraccn=getClusters();
					int wholeSize=listClusteraccn.size();
					String[] arr = new String[wholeSize];
			        arr = listClusteraccn.toArray(arr);
			        for (int j = 0; j < arr.length; j++) {
			        	String Selectedl1 = arr[j];
			           
			            listOfSelectedForL2.add(Selectedl1.toString());
			        }
			       
			        listOfSelectedForL1=listOfSelectedForL2;
			       
			      

			        
			        
			        
			        	l1l2CustomerList=getDistinctAccounts().split(",");
			 
					
					for (int i = 0; i < l1l2CustomerList.length; i++) {
						if (l1l2CustomerList[i]!=null) {
							String custL11 = l1l2CustomerList[i].split(":")[0];
							String custL21 = l1l2CustomerList[i].split(":")[0];
							List<String> listOfL11 = new ArrayList<>();
							 List<String> custStorefmt1 = new ArrayList<String>();
							listOfL11.add(custL11);
							
							if(!customerStoreFormat.isEmpty()) {
								
								List<String> regionName = new ArrayList<String>();
								if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
									
									for(String accountsNames : getClusters()) {
										String[] stringarray = accountsNames.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
									String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
									List<String> container = Arrays.asList(items);
									 LinkedHashSet<String> Account=new LinkedHashSet();
									
									for(String embeder:container) {
										String[] stringarray = embeder.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								
								
								
							
								
								
								String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
								List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
								
								for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
									
									custStorefmt1.add("'"+valueCustomerStoreFormat.toString()+"'");
									
								}
								
							
								wholeCluster=regionName.toString();
								wholeCluster=wholeCluster.replace("[", "");
								wholeCluster=wholeCluster.replace("]", "");
								
								wholeCuststrfrmt=custStorefmt1.toString();
								wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
								wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
								
							}
							String thing =wholeCluster;
							
							
							
							//System.out.println(listOfSelectedForL1);
							String custStoreFormat1 = wholeCuststrfrmt;
							
							
							List<String> listOfAllForL11 = createPromoService.getClusterOnCustomerList(listOfL11);
							
							String thing3 =wholeCluster;
							
							String custStoreFormat3 = wholeCuststrfrmt;
							if(((thing3!=null && custStoreFormat3!=null) && (!custStoreFormat3.isEmpty() && !thing3.isEmpty()) )) { //&& (StoreFormate.isEmpty() && StoreFormate.equals("''")) ) {// come here
								
								
							

							for(String namer : getClusters()) {
								
								
								
								
								//ListArrayList<String> downloadDataList = new ArrayList<ArrayList<String>>();
								String regionCluster12="";
								ArrayList<String> finallist = new ArrayList<>();
								//	List<String> Clusters = new ArrayList<String>();
									Session session = sessionFactory.getCurrentSession();
									String distinctAccounts="";
									List<String> set = new ArrayList<String>();
									SessionImpl sessionImpl = (SessionImpl) session;
									PreparedStatement stmt = null;
									ResultSet rs = null;
									String just=namer;
									if(!just.contains("B0")) {
										String[] parts = just.split(":");
										regionCluster12 = parts[1];
								
										
										try {
											stmt = sessionImpl.connection().prepareStatement("Select ACCOUNT_NAME,FINAL_CLUSTER,COUNT(ACCOUNT_NAME) AS TOTAL from (SELECT * FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN (" + 
													"SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+thing3+")" + 
													"AND ACCOUNT_NAME != '' " +
													"AND CUSTOMER_STORE_FORMAT IN ("+ custStoreFormat3 +"))  AND FINAL_CLUSTER IN (" + thing3 +") ) as aa where aa.FINAL_CLUSTER IN ('" + regionCluster12 +"')  group by ACCOUNT_NAME");
											rs = stmt.executeQuery();
											
											while (rs.next()) {
												
											
											ArrayList<String> dataObj = new ArrayList<>();
											dataObj.add(just);
											dataObj.add(rs.getString("ACCOUNT_NAME"));
											dataObj.add(rs.getString("ACCOUNT_NAME"));
											dataObj.add(custStoreFormat1);
											dataObj.add("Yes");
											dataObj.add(String.valueOf(rs.getInt("TOTAL")));
											
											downloadDataList.add(dataObj);
											}
										
										} catch (HibernateException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										
									}
									
									else {
										ArrayList<String> dataObj = new ArrayList<>();
										dataObj.add(just);
										dataObj.add(" ");
										dataObj.add(" ");
										dataObj.add(custStoreFormat1);
										dataObj.add("Yes");
										dataObj.add("0");
										downloadDataList.add(dataObj);
									}
								
								
								
								
								
							} return downloadDataList;
						}
					
							listOfSelectedForL1.forEach(data -> {
								ArrayList<String> dataObj = new ArrayList<>();
								dataObj.add(data);
								dataObj.add(custL11);
								
								dataObj.add(custL21);
								dataObj.add(custStoreFormat1);
								dataObj.add("Yes");
								dataObj.add(getAllStoreCount( data, custL11,  busiClasification,StoreFormate, custStoreFormat3, thing3));
								downloadDataList.add(dataObj);
							});
						}
					}
				
			     
				}
				else if(listOfL1L2.contains("ALL CUSTOMERS")) {
					
					

					 LinkedHashSet<String> listAcclcn2 = new LinkedHashSet<String>();
					Set<String> lhs = new LinkedHashSet<String>();
					listAcclcn2=getClusters();
					int wholeSize=listAcclcn2.size();
					String[] arr = new String[wholeSize];
			        arr = listAcclcn2.toArray(arr);
			        for (int j = 0; j < arr.length; j++) {
			        	String listofaccL2 = arr[j];
			            
			            listOfSelectedForL2.add(listofaccL2.toString());
			        }
			      
			        	l1l2CustomerList=getDistinctAccounts().split(",");
			 
					
					for (int i = 0; i < l1l2CustomerList.length; i++) {
						if (l1l2CustomerList[i]!=null) {
							String custL11 = l1l2CustomerList[i].split(":")[0];
							String custL21 = l1l2CustomerList[i].split(":")[0];
							List<String> listOfL11 = new ArrayList<>();
							 List<String> custStorefmt1 = new ArrayList<String>();
							listOfL11.add(custL11);
							
							if(!customerStoreFormat.isEmpty()) {
								
								List<String> regionName = new ArrayList<String>();
								if(downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")) {
									
									for(String accountsNames : getClusters()) {
										String[] stringarray = accountsNames.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								else if(!downloadLaunchClusterRequest.getRegionCluster().equals("ALL INDIA")){
									String [] items = downloadLaunchClusterRequest.getRegionCluster().split("\\s*,\\s*");
									List<String> container = Arrays.asList(items);
									 LinkedHashSet<String> Account=new LinkedHashSet();
								
									for(String embeder:container) {
										String[] stringarray = embeder.split(":");  
										
										if(!stringarray[0].contains("B")) {
											regionName.add("'"+stringarray[1].toString()+"'");
										}
									}
								}
								
								
								
								
							
								
								String [] multipleCustomerStoreFormat = customerStoreFormat.split("\\s*,\\s*");
								List<String> listofCustomerStoreFormat = Arrays.asList(multipleCustomerStoreFormat);
								for(String valueCustomerStoreFormat:listofCustomerStoreFormat) {  
									custStorefmt1.add("'"+valueCustomerStoreFormat.toString()+"'");
									
								}
								
								
								wholeCluster=regionName.toString();
								wholeCluster=wholeCluster.replace("[", "");
								wholeCluster=wholeCluster.replace("]", "");
								
								
								wholeCuststrfrmt=custStorefmt1.toString();
								wholeCuststrfrmt=wholeCuststrfrmt.replace("[", "");
								wholeCuststrfrmt=wholeCuststrfrmt.replace("]", "");
								
								
							}
							String thing =wholeCluster;
							
							String custStoreFormat1 = wholeCuststrfrmt;
							
							
							List<String> listOfAllForL11 = createPromoService.getClusterOnCustomerList(listOfL11);
							
							String thing3 =wholeCluster;
						
							String custStoreFormat3 = wholeCuststrfrmt;
							
							// this implementation for one cluster and all account from customer store
						
							if(((thing3!=null && custStoreFormat3!=null) && (!custStoreFormat3.isEmpty() && !thing3.isEmpty())  )) {
								for(String namer : regionCluster) {
									
									
									
									
									String regionCluster12="";
									ArrayList<String> finallist = new ArrayList<>();
										Session session = sessionFactory.getCurrentSession();
										String distinctAccounts="";
										List<String> set = new ArrayList<String>();
										SessionImpl sessionImpl = (SessionImpl) session;
										PreparedStatement stmt = null;
										ResultSet rs = null;
										String just=namer;
										if(!just.contains("B0")) {
											String[] parts = just.split(":");
											regionCluster12 = parts[1];
											
											
											try {
												stmt = sessionImpl.connection().prepareStatement("Select ACCOUNT_NAME,FINAL_CLUSTER,COUNT(ACCOUNT_NAME) AS TOTAL from (SELECT * FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN (" + 
														"SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+thing3+")" + 
														"AND ACCOUNT_NAME != '' " +
														"AND CUSTOMER_STORE_FORMAT IN ("+ custStoreFormat3 +"))  AND FINAL_CLUSTER IN (" + thing3 +") ) as aa where aa.FINAL_CLUSTER IN ('" + regionCluster12 +"')  group by ACCOUNT_NAME");
												rs = stmt.executeQuery();
												
												while (rs.next()) {
													
												ArrayList<String> dataObj = new ArrayList<>();
												dataObj.add(just);
												dataObj.add(rs.getString("ACCOUNT_NAME"));
												dataObj.add(rs.getString("ACCOUNT_NAME"));
												dataObj.add(custStoreFormat1);
												dataObj.add("Yes");
												dataObj.add(String.valueOf(rs.getInt("TOTAL")));
												downloadDataList.add(dataObj);
												}
											
											} catch (HibernateException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											
										}
										
										else {
											ArrayList<String> dataObj = new ArrayList<>();
											dataObj.add(just);
											dataObj.add(" ");
											dataObj.add(" ");
											dataObj.add(custStoreFormat1);
											dataObj.add("Yes");
											dataObj.add("0");
											downloadDataList.add(dataObj);
										}
									
									
									
									
									
								} return downloadDataList;
							}
							
							
							
							
							else {
							
								listOfSelectedForL1.forEach(data -> {
								ArrayList<String> dataObj = new ArrayList<>();
								dataObj.add(data);
								dataObj.add(custL11);
								
								dataObj.add(custL21);
								dataObj.add(custStoreFormat1);
								dataObj.add("Yes");
								dataObj.add(getAllStoreCount( data, custL11,  busiClasification,StoreFormate, custStoreFormat3, thing3));
								downloadDataList.add(dataObj);
							});
							}
						}
					}
				
			     
				
					
					
					
					
					
					
					
				}


				
					
				
				
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}
	
	// Harsha Added this code for big mike story Implementation Ends
	
	public LinkedHashSet<String> getClusters(){
	//	List<String> Clusters = new ArrayList<String>();
		Session session = sessionFactory.getCurrentSession();
		LinkedHashSet<String> set=new LinkedHashSet();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = sessionImpl.connection().prepareStatement("select distinct CONCAT(BRANCH_CODE,':',BRANCH) AS BRANCH ,CONCAT(CLUSTER_CODE,':',CLUSTER) AS CLUSTER  from TBL_PROCO_CUSTOMER_MASTER order by BRANCH ");
			rs = stmt.executeQuery();
			while (rs.next()) {
			set.add(rs.getString("BRANCH"));
				set.add(rs.getString("CLUSTER"));
			}
		
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return set;
	} 
	
	public String getDistinctAccounts(){
		//	List<String> Clusters = new ArrayList<String>();
			Session session = sessionFactory.getCurrentSession();
			String distinctAccounts="";
			List<String> set = new ArrayList<String>();
			SessionImpl sessionImpl = (SessionImpl) session;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				stmt = sessionImpl.connection().prepareStatement("SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER");
				rs = stmt.executeQuery();
				while (rs.next()) {
				set.add(rs.getString("ACCOUNT_NAME"));
				}
			
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			distinctAccounts=set.toString();
			distinctAccounts=distinctAccounts.replace("[", "");
			distinctAccounts=	distinctAccounts.replace("]", "");
			return distinctAccounts;
		} 

	

	
	
	
	
	public String getClasisification (String launchId) {
		String clasification="";

		List<String> bpCodes = null;
		try {
			Query queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT CLASSIFICATION FROM TBL_LAUNCH_MASTER WHERE LAUNCH_ID = '" + launchId + "'");
			bpCodes = queryToGetLaunchIDclasification.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String clasifi : bpCodes) {
			clasification=clasifi;
		}
		return clasification;
		
	}
	
	public String getCountofStores(String regionCluster,String accountName, String busiClasification, String StoreFormate, String  CustomerStoreFormate, String whoelString) {
		
		Session session = sessionFactory.getCurrentSession();
		LinkedHashSet<String> set=new LinkedHashSet();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String launchClassification="";
		List<BigInteger> storeCount = null;
		List<Integer> intList = new ArrayList<Integer>();
		String totalStoreCount="";
		String region ="";
		String place2="";
		String launchFormate;
		if(StoreFormate.equals("''") || StoreFormate.isEmpty()) {
			launchFormate=null;
		}
		else {
			launchFormate=StoreFormate;
		}
		String sqlBit="";
		
		
		if(regionCluster.equals("ALL INDIA")) // this loop whn all ALL INDIA
		{
			region="ALL INDIA";
			
			if (busiClasification.equals("Gold")) {
				launchClassification = "'GOLD','SILVER','BRONZE'";
				
			} else if (busiClasification.equals("Silver")) {
				launchClassification = "'BRONZE','SILVER'";
			} else {
				launchClassification = "'BRONZE'";
			}
			if(launchFormate!=null && !launchFormate.isEmpty() ) {
				//launchClassification=launchClassification+",'NA'";
				sqlBit=" AND CURRENT_STORE_FORMAT IN ("+launchFormate+")";
				}
			

			
			if(region!=null && !region.isEmpty()) {
				try {
					stmt = sessionImpl.connection().prepareStatement("select distinct CONCAT(BRANCH_CODE,':',BRANCH) AS BRANCH ,CONCAT(CLUSTER_CODE,':',CLUSTER) AS CLUSTER  from TBL_PROCO_CUSTOMER_MASTER order by BRANCH ");
					rs = stmt.executeQuery();
					while (rs.next()) {
					set.add(rs.getString("BRANCH"));
						set.add(rs.getString("CLUSTER"));
					}
					
					for(String place: set) {
						
							String[] parts = place.split(":");
							String clusterOrBranch = parts[0];
							
							if(!clusterOrBranch.contains("B")) {
								place2 = parts[1];
								Query queryToGetLaunchIDclasification;
								if(sqlBit.isEmpty() && (CustomerStoreFormate.isEmpty() &&  whoelString.isEmpty()) ) {
									 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
											.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
													+"AND ACCOUNT_NAME = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + place2 +"'" + "AND LAUNCH_FORMAT IN (" +launchClassification+")"  );
									
								}
								else if(!CustomerStoreFormate.isEmpty() &&  !whoelString.isEmpty()) {
									 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
											 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN " + 
											 		"(SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+whoelString+")" + 
											 		"AND ACCOUNT_NAME = '"+accountName+"'  AND ACCOUNT_NAME != '' "+ 
											 		"AND DP_CHAIN = '"+accountName+"'"+ 
											 		"AND  CUSTOMER_STORE_FORMAT IN ("+CustomerStoreFormate+"))"+ 
											 		"AND FINAL_CLUSTER = '"+place2+"'");
										
								}
								
								else {
									queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
											.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
													+ sqlBit+"AND ACCOUNT_NAME = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + place2 +"'");
									
								}
				
								
								storeCount = queryToGetLaunchIDclasification.list();
								for(BigInteger value : storeCount) {
									int counter=value.intValue();
									intList.add(counter);
								}
							}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			int sum=0;
			for (int i = 0; i < intList.size(); i++)
	            sum += intList.get(i);
	       totalStoreCount=String.valueOf(sum);
			
			
			
			if(storeCount==null) {
				return "0";
			}
				if(!totalStoreCount.equals("0") && totalStoreCount!=null) {
					return totalStoreCount;
				}
				else {
					return "0";
				}

			
		}
		
		else {
			if(launchFormate!=null && !launchFormate.isEmpty()) {// Checking correct flow
				
				sqlBit=" AND CURRENT_STORE_FORMAT IN ("+launchFormate+")";
				}
			
		

		String[] parts = regionCluster.split(":");
		String clusterOrBranch = parts[0];
		if(!clusterOrBranch.contains("B")) {
			region = parts[1];
		}
		else {
			region=null;
		}
		
		
		if (busiClasification.equals("Gold")) {
			launchClassification = "('GOLD','SILVER','BRONZE')";
			
		} else if (busiClasification.equals("Silver")) {
			launchClassification = "('BRONZE','SILVER')";
		} else {
			launchClassification = "('BRONZE')";
		}
		

		if(region!=null && !region.isEmpty()) {
			try {
				
				Query queryToGetLaunchIDclasification;
				if(sqlBit.isEmpty() && (CustomerStoreFormate.isEmpty() &&  whoelString.isEmpty())) {
					 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
							 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
										+ "AND ACCOUNT_NAME = '" + accountName + "'"+" AND DP_CHAIN = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
						
				}
				
				else if(!CustomerStoreFormate.isEmpty() &&  !whoelString.isEmpty()) {
					 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
							 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN " + 
							 		"(SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+whoelString+")" + 
							 		"AND ACCOUNT_NAME = '"+accountName+"'  AND ACCOUNT_NAME != '' "+ 
							 		"AND DP_CHAIN = '"+accountName+"'"+ 
							 		"AND  CUSTOMER_STORE_FORMAT IN ("+CustomerStoreFormate+"))"+ 
							 		"AND FINAL_CLUSTER = '"+region+"'");
						
				}
				
				else {
					queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
							.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
									+ sqlBit+"AND ACCOUNT_NAME = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + region +"'");
					
				}
				
				
				/*Query queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
								+ "AND ACCOUNT_NAME = '" + accountName + "'"+" AND DP_CHAIN = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
				*/
				storeCount = queryToGetLaunchIDclasification.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(storeCount==null) {
			return "0";
		}

	for (BigInteger count : storeCount) {
		totalStoreCount=String.valueOf(count);
			if(!totalStoreCount.equals("0") && totalStoreCount!=null) {
				return totalStoreCount;
			}
			else {
				return "0";
			}
		}
		
		return totalStoreCount;
		}
	}
	
	// TO GET COUNT OF ALL CUSTOMER's
	
public String getCountofALLCustomers(String regionCluster,String accountName, String busiClasification, String StoreFormate,String  CustomerStoreFormate, String whoelString ) {
		
	Session session = sessionFactory.getCurrentSession();
	SessionImpl sessionImpl = (SessionImpl) session;
	PreparedStatement stmt = null;
	ResultSet rs = null;	
	
	
	String launchFormate;
	if(StoreFormate.equals("''") || StoreFormate.isEmpty()) {
		launchFormate=null;
	}
	else {
		launchFormate=StoreFormate;
	}
	String sqlBit="";
	
	String launchClassification="";
		LinkedHashSet<String> set=new LinkedHashSet();
		List<BigInteger> storeCount = null;
		List<Integer> intList = new ArrayList<Integer>();
		List<Integer> Addwhole = new ArrayList<Integer>();
		List<String> Accountsnames = null;
		String totalStoreCount="";
		int fianltotal = 0;
		String region ="";
		
		
		if(!regionCluster.contains(":")) {
			region=regionCluster;
				if (busiClasification.equals("Gold")) {
					launchClassification = "('GOLD','SILVER','BRONZE')";
					
				} else if (busiClasification.equals("Silver")) {
					launchClassification = "('BRONZE','SILVER')";
				} else {
					launchClassification = "('BRONZE')";
				}
				
				if(launchFormate!=null) {
					//launchClassification=launchClassification+",'NA'";
					sqlBit=" AND CURRENT_STORE_FORMAT IN ("+launchFormate+")";
					}
				
				

				if(region!=null && !region.isEmpty()) {
				
					
					try {
						
						Query queryTogetDistinctAccountNames = sessionFactory.getCurrentSession()
								.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER");
						Accountsnames=queryTogetDistinctAccountNames.list();
						
						
						for(String allCustomers : Accountsnames) {
							Query queryToGetLaunchIDclasification;
							if(sqlBit.isEmpty() && (CustomerStoreFormate.isEmpty() &&  whoelString.isEmpty())) {
								queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
										.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
												+ "AND ACCOUNT_NAME = '" + allCustomers+ "'"
												+"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
								
							}
							
							else if(!CustomerStoreFormate.isEmpty() &&  !whoelString.isEmpty()) {
								 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
										 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN " + 
										 		"(SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+whoelString+")" + 
										 		"AND ACCOUNT_NAME = '"+allCustomers+"'  AND ACCOUNT_NAME != '' "+ 
										 		"AND DP_CHAIN = '"+allCustomers+"'"+ 
										 		"AND  CUSTOMER_STORE_FORMAT IN ("+CustomerStoreFormate+"))"+ 
										 		"AND FINAL_CLUSTER = '"+region+"'");
									
							}
							
							else {
								queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
										.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
												+ sqlBit+"AND ACCOUNT_NAME = '" + allCustomers + "'" +"AND FINAL_CLUSTER = '" + region +"'");
								
							}
							
							/*Query queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
									.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
											+ "AND ACCOUNT_NAME = '" + allCustomers+ "'"
											+"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
							*/
							storeCount=queryToGetLaunchIDclasification.list();
							for(BigInteger num : storeCount) {
								int pls = num.intValue();
								intList.add(pls);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				int sum =0;
				for (int i = 0; i < intList.size(); i++)
		            sum += intList.get(i);
		       totalStoreCount=String.valueOf(sum);
		
			return totalStoreCount;
		}
		
		
		
		
		
		
		
		
		
		
		String[] parts = regionCluster.split(":");
		String clusterOrBranch = parts[0];
		region=parts[1];
		if(launchFormate!=null) {
			//launchClassification=launchClassification+",'NA'";
			sqlBit=" AND CURRENT_STORE_FORMAT IN ("+launchFormate+")";
			}
		
		if(!clusterOrBranch.contains("B")) {
			parts[1]=region;
			
			if (busiClasification.equals("Gold")) {
				launchClassification = "('GOLD','SILVER','BRONZE')";
				
			} else if (busiClasification.equals("Silver")) {
				launchClassification = "('BRONZE','SILVER')";
			} else {
				launchClassification = "('BRONZE')";
			}
			
			

			
			if((region!=null && !region.isEmpty())|| region.equals("ALL INDIA")) {
				
				try {
					
					Query queryTogetDistinctAccountNames = sessionFactory.getCurrentSession()
							.createNativeQuery("SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER");
					Accountsnames=queryTogetDistinctAccountNames.list();
					
					for(String allCustomers : Accountsnames) {
						Query queryToGetLaunchIDclasification;
						if(sqlBit.isEmpty() && (CustomerStoreFormate.isEmpty() &&  whoelString.isEmpty())) {
							queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
									.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
											+ "AND ACCOUNT_NAME = '" + allCustomers+ "'"
											+"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
							
						}
						
						else if(!CustomerStoreFormate.isEmpty() &&  !whoelString.isEmpty()) {
							 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
									 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN " + 
									 		"(SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ('"+region+"')" + //whoelString
									 		"AND ACCOUNT_NAME = '"+allCustomers+"'  AND ACCOUNT_NAME != '' "+ 
									 		"AND DP_CHAIN = '"+allCustomers+"'"+ 
									 		"AND  CUSTOMER_STORE_FORMAT IN ("+CustomerStoreFormate+"))"+ 
									 		"AND FINAL_CLUSTER = '"+region+"'");
								
						}
						
						else {
							queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
									.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
											+ sqlBit+"AND ACCOUNT_NAME = '" + allCustomers + "'" +"AND FINAL_CLUSTER = '" + region +"'");
							
						}
						
						/*Query queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
								.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
										+ "AND ACCOUNT_NAME = '" + allCustomers+ "'"
										+"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
						*/
						storeCount=queryToGetLaunchIDclasification.list();
						for(BigInteger num : storeCount) {
							int pls = num.intValue();
							intList.add(pls);
						}
					}
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			int sum =0;
			for (int i = 0; i < intList.size(); i++)
	            sum += intList.get(i);
	       totalStoreCount=String.valueOf(sum);
		}
		else {
			return "0";
		}
		return totalStoreCount;
	}

	// To Get count of Customer Store Formate Harsha ekada kotha replacing method 

public String getAllStoreCount(String regionCluster,String accountName, String busiClasification, String StoreFormate, String  CustomerStoreFormate, String whoelString) {
	
	Session session = sessionFactory.getCurrentSession();
	LinkedHashSet<String> set=new LinkedHashSet();
	SessionImpl sessionImpl = (SessionImpl) session;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String launchClassification="";
	List<BigInteger> storeCount = null;
	List<Integer> intList = new ArrayList<Integer>();
	String totalStoreCount="";
	String region ="";
	String place2="";
	String launchFormate;
	if(StoreFormate.equals("''") || StoreFormate.isEmpty()) {
		launchFormate=null;
	}
	else {
		launchFormate=StoreFormate;
	}
	String sqlBit="";
	
	
	
		if(launchFormate!=null && !launchFormate.isEmpty()) {// Checking correct flow
			//launchClassification=launchClassification+",'NA'";
			sqlBit=" AND CURRENT_STORE_FORMAT IN ("+launchFormate+")";
			}
		
	

	String[] parts = regionCluster.split(":");
	String clusterOrBranch = parts[0];
	if(!clusterOrBranch.contains("B")) {
		region = parts[1];
	}
	else {
		region=null;
	}
	
	
	if (busiClasification.equals("Gold")) {
		launchClassification = "('GOLD','SILVER','BRONZE')";
		
	} else if (busiClasification.equals("Silver")) {
		launchClassification = "('BRONZE','SILVER')";
	} else {
		launchClassification = "('BRONZE')";
	}
	
	
	if(region!=null && !region.isEmpty()) {
		try {
			Query queryToGetLaunchIDclasification;
			if(sqlBit.isEmpty() && (CustomerStoreFormate.isEmpty() &&  whoelString.isEmpty())) {
				queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
								+ "AND ACCOUNT_NAME = '" + accountName.trim()+ "'"
								+"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
				
			
			}
			
			else if(!CustomerStoreFormate.isEmpty() &&  !whoelString.isEmpty()) {
				 queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
						 .createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN " + 
						 		"(SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN ("+whoelString+")" + 
						 		"AND ACCOUNT_NAME = '"+accountName.trim()+"'  AND ACCOUNT_NAME != '' "+ 
						 		//"AND DP_CHAIN = '"+accountName.trim()+"'"+ 
						 		"AND  CUSTOMER_STORE_FORMAT IN ("+CustomerStoreFormate+"))"+ 
						 		"AND FINAL_CLUSTER IN ("+whoelString+")");
					
			}
			
			else {
				queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
								+ sqlBit+"AND ACCOUNT_NAME = '" + accountName.trim() + "'" +"AND FINAL_CLUSTER = '" + region +"' AND LAUNCH_FORMAT !=''");
				
			}
			
			
			/*Query queryToGetLaunchIDclasification = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' " //+ " tlb.LAUNCH_ID IN (:launchIds)"
							+ "AND ACCOUNT_NAME = '" + accountName + "'"+" AND DP_CHAIN = '" + accountName + "'" +"AND FINAL_CLUSTER = '" + region +"'" + "AND LAUNCH_FORMAT IN " +launchClassification+"");
			*/
			storeCount = queryToGetLaunchIDclasification.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	if(storeCount==null) {
		return "0";
	}

for (BigInteger count : storeCount) {

		totalStoreCount=String.valueOf(count);
		if(!totalStoreCount.equals("0") && totalStoreCount!=null) {
			return totalStoreCount;
		}
		else {
			return "0";
		}
	}
	
	return totalStoreCount;
	
}



		
	
	
	
	// Harsha Ended this code for big mike story
	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchBasePackResponse> getLaunchBasePackDetails(String basepackCode) {
		List<LaunchBasePackResponse> liReturn = new ArrayList<>();
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT BASEPACK, BASEPACK_DESC, BP_CLASSIFICATION from TBL_VAT_COMM_PRODUCT_MASTER where BASEPACK = :bpCode");
			query.setParameter("bpCode", basepackCode);
			Iterator<Object> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchBasePackResponse launchBasePackResponse = new LaunchBasePackResponse();
				launchBasePackResponse.setBpCode(Integer.parseInt(obj[0].toString()));
				launchBasePackResponse.setBpDesc(obj[1].toString());
				if (obj[2] != null) {
					launchBasePackResponse.setBpClassification(obj[2].toString());
				} else {
					launchBasePackResponse.setBpClassification("");
				}

				liReturn.add(launchBasePackResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchBasePackResponse launchBasePackResponse = new LaunchBasePackResponse();
			launchBasePackResponse.setError(ex.toString());
			liReturn.add(launchBasePackResponse);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchCoeBasePackResponse> getLaunchFinalRespose(List<String> launchIds) {
		List<LaunchCoeBasePackResponse> liReturn = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,BP_SALES_CAT, BP_PSA_CAT, BP_BRAND, BP_CODE, BP_DESCRIPTION, BP_MRP, BP_TUR, BP_GSV, BP_CLD_CONFIG, BP_GRAMMAGE,"
							+ "BP_CLASSIFICATION FROM TBL_LAUNCH_BASEPACK tlb, TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND"
							+ " tlb.LAUNCH_ID IN (:launchIds)");
			query.setParameterList("launchIds", launchIds);
			Iterator<Object> itr = query.list().iterator();
			while (itr.hasNext()) {
				LaunchCoeBasePackResponse launchCoeBasePackResponse = new LaunchCoeBasePackResponse();
				Object[] obj = (Object[]) itr.next();
				launchCoeBasePackResponse.setLaunchName((String) obj[0]);
				launchCoeBasePackResponse.setSalesCat((String) obj[1]);
				launchCoeBasePackResponse.setPsaCat((String) obj[2]);
				launchCoeBasePackResponse.setBrand((String) obj[3]);
				launchCoeBasePackResponse.setBpCode((String) obj[4]);
				launchCoeBasePackResponse.setBpDisc((String) obj[5]);
				launchCoeBasePackResponse.setMrp((String) obj[6]);
				launchCoeBasePackResponse.setTur((String) obj[7]);
				launchCoeBasePackResponse.setGsv((String) obj[8]);
				launchCoeBasePackResponse.setCldConfig((String) obj[9]);
				launchCoeBasePackResponse.setGrammage((String) obj[10]);
				launchCoeBasePackResponse.setClassification((String) obj[11]);
				liReturn.add(launchCoeBasePackResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchCoeBasePackResponse launchCoeBasePackResponse = new LaunchCoeBasePackResponse();
			launchCoeBasePackResponse.setError(e.toString());
			liReturn.add(launchCoeBasePackResponse);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int storeCount(List<String> listOfL1, List<String> listOfL2) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (listOfL1.contains("ALL CUSTOMERS")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE'");
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:l1Chain) AND DP_CHAIN in (:l2Chain)");
				queryToGetCustomeChainL1.setParameterList("l1Chain", listOfL1);
				queryToGetCustomeChainL1.setParameterList("l2Chain", listOfL2);
			}

			//kiran  - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//return count.get(0);
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBpClassification() {
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT BP_CLASSIFICATION FROM TBL_VAT_COMM_PRODUCT_MASTER tvcpm WHERE BP_CLASSIFICATION <> '' ORDER BY BP_CLASSIFICATION ");  //Sarin Changes - Added OrderBy
			return queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStoreCountByClass(List<String> clusterList, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			Query queryToGetCustomeChainL1 = null;
			if (accountl1String.isEmpty() || accountl1String.contains("ALL CUSTOMERS")) {
				if (clusterList.isEmpty() || clusterList.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER IN (:clusterList) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("clusterList", clusterList);
				}
			} else {
				if (clusterList.isEmpty() || clusterList.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER IN (:clusterList) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
					queryToGetCustomeChainL1.setParameterList("clusterList", clusterList);
				}
			}
			//kiran - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//String storeCount = String.valueOf(count.get(0));
			String storeCount = String.valueOf(count.get(0).intValue());
			return storeCount;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLaunchStores(List<String> liCluster, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		List<String> toReturn = null;
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			Query queryToGetCustomeChainL1 = null;
			if (accountl1String.isEmpty() || accountl1String.contains("ALL CUSTOMERS")) {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(CURRENT_STORE_FORMAT)");
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER IN (:liCluster)  AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(CURRENT_STORE_FORMAT)");
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
				}
			} else {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER != ''  AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(CURRENT_STORE_FORMAT)");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(CURRENT_STORE_FORMAT) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER IN (:liCluster)  AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(CURRENT_STORE_FORMAT)");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
				}
			}
			toReturn = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			toReturn.add(ex.toString());
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getCustomerStoreFormat(List<String> liCluster, List<String> accountl1String,
			List<String> accountl2String, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		List<String> liStrings = null;
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			Query queryToGetCustomeChainL1 = null;
			if (accountl1String.isEmpty() || accountl1String.contains("ALL CUSTOMERS")) {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))");
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND FINAL_CLUSTER IN (:liCluster) AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))");
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
				}
			} else {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER != '' AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' ')) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm where ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME IN (:accountl1String) AND DP_CHAIN IN (:accountl2String) AND FINAL_CLUSTER IN (:liCluster) AND FINAL_CLUSTER != '' AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") ORDER BY UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
				}
			}
			liStrings = queryToGetCustomeChainL1.list();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return liStrings;
		}
		return liStrings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStoreCountOnCustSellIIn(String storeFormat, List<String> liCluster, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			List<String> listOfStoreFormat = Arrays.asList(storeFormat.split(","));
			Query queryToGetCustomeChainL1;

			if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) IN "
								+ "(:listOfStoreFormat) AND LAUNCH_FORMAT IN (" + launchClassification + ")");
				queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND FINAL_CLUSTER IN (:liCluster) AND "
								+ " UPPER(CURRENT_STORE_FORMAT) IN (:listOfStoreFormat) AND LAUNCH_FORMAT IN ("
								+ launchClassification + ")");
				queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
				queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);

			}
			//kiran - bigint to int changes
			//List<Object> check = queryToGetCustomeChainL1.list();
			List<BigInteger> check = queryToGetCustomeChainL1.list();
			return String.valueOf(check.get(0).intValue());
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStoreCountOnCust(String custStoreFormat, List<String> accountl1String,
			List<String> accountl2String, List<String> liCluster, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			List<String> listOfCustStoreForm = Arrays.asList(custStoreFormat.split("~"));
			Query queryToGetCustomeChainL1;
			if (accountl1String.isEmpty() || accountl1String.contains("ALL CUSTOMERS")) {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND ACCOUNT_NAME != '' AND CURRENT_STORE_FORMAT IN "
									+ " (SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE CUSTOMER_STORE_FORMAT IN (:custStoreFormat))");
									//+ " AND LAUNCH_FORMAT IN (" + launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("custStoreFormat", listOfCustStoreForm);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN ( "
									+ " SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN (:liCluster) "
									+ " AND ACCOUNT_NAME != '' AND CUSTOMER_STORE_FORMAT IN (:custStoreFormat))  AND FINAL_CLUSTER IN (:liCluster)");
							/*"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE CURRENT_STORE_FORMAT IN ( "
									+ " SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN (:liCluster) "
									+ " AND ACCOUNT_NAME != '' AND CUSTOMER_STORE_FORMAT IN (:custStoreFormat)) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") AND FINAL_CLUSTER IN (:liCluster)");*/
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
					queryToGetCustomeChainL1.setParameterList("custStoreFormat", listOfCustStoreForm);
				}
			} else {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							/*"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE CURRENT_STORE_FORMAT IN ( "
									+ "SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE"
									+ " ACCOUNT_NAME IN (:accountl1String) AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND "
									+ " CUSTOMER_STORE_FORMAT IN (:custStoreFormat)) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");*/
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN ( "
							+ "SELECT DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE"
							+ " ACCOUNT_NAME IN (:accountl1String) AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND "
							+ " CUSTOMER_STORE_FORMAT IN (:custStoreFormat))");
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
					queryToGetCustomeChainL1.setParameterList("custStoreFormat", listOfCustStoreForm);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							/*"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE CURRENT_STORE_FORMAT IN ( SELECT"
									+ " DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN (:liCluster) "
									+ " AND ACCOUNT_NAME IN (:accountl1String) AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND "
									+ " CUSTOMER_STORE_FORMAT IN (:custStoreFormat)) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ") AND FINAL_CLUSTER IN (:liCluster)");*/
							"SELECT COUNT(*) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND CURRENT_STORE_FORMAT IN ( SELECT"
							+ " DISTINCT CURRENT_STORE_FORMAT FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE FINAL_CLUSTER IN (:liCluster) "
							+ " AND ACCOUNT_NAME IN (:accountl1String) AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND "
							+ " CUSTOMER_STORE_FORMAT IN (:custStoreFormat))  AND FINAL_CLUSTER IN (:liCluster)");
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
					queryToGetCustomeChainL1.setParameterList("custStoreFormat", listOfCustStoreForm);
				}
			}

			//kiran - big int to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//return String.valueOf(count.get(0));
			return String.valueOf(count.get(0).intValue());
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getStoreCountOnStore(String storeFormat, List<String> accountl1String, List<String> accountl2String,
			List<String> liCluster, String classification
			, boolean isCustomStoreFormat) {  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
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
			if (isCustomStoreFormat) {
				launchClassification = "'GOLD','SILVER','BRONZE','NA'";
			}
			
			List<String> listOfStoreFormat = Arrays.asList(storeFormat.split(","));
			Query queryToGetCustomeChainL1;
			if (accountl1String.isEmpty() || accountl1String.contains("ALL CUSTOMERS")) {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) IN "
									+ "(:listOfStoreFormat) AND LAUNCH_FORMAT IN (" + launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND FINAL_CLUSTER IN (:liCluster) AND "
									+ " UPPER(CURRENT_STORE_FORMAT) IN (:listOfStoreFormat) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
					queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);

				}
			} else {
				if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) IN (:listOfStoreFormat)"
									+ "  AND ACCOUNT_NAME IN (:accountl1String) AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
				} else {
					queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND FINAL_CLUSTER IN (:liCluster) AND "
									+ " UPPER(CURRENT_STORE_FORMAT) IN (:listOfStoreFormat) AND ACCOUNT_NAME IN (:accountl1String) "
									+ " AND ACCOUNT_NAME != '' AND DP_CHAIN IN(:accountl2String) AND LAUNCH_FORMAT IN ("
									+ launchClassification + ")");
					queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
					queryToGetCustomeChainL1.setParameterList("listOfStoreFormat", listOfStoreFormat);
					queryToGetCustomeChainL1.setParameterList("accountl1String", accountl1String);
					queryToGetCustomeChainL1.setParameterList("accountl2String", accountl2String);
				}
			}

			//kiran - big int to int changes
			//List<Object> check = queryToGetCustomeChainL1.list();
			List<BigInteger> check = queryToGetCustomeChainL1.list();
			//return String.valueOf(check.get(0));
			return String.valueOf(check.get(0).intValue());
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int storeCount(String storeFormat, String accountL1, String accountL2, List<String> listOfCustStores,
			List<String> liCluster) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) = :storeFormat AND "
								+ "ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2 AND UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))"
								+ " IN (:listOfCustStores) and ACCOUNT_NAME != ''");
				queryToGetCustomeChainL1.setParameter("storeFormat", storeFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
				queryToGetCustomeChainL1.setParameterList("listOfCustStores", listOfCustStores);
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) = :storeFormat AND "
								+ "ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2 AND UPPER(REPLACE(CUSTOMER_STORE_FORMAT, '  ', ' '))"
								+ " IN (:listOfCustStores) and ACCOUNT_NAME != '' AND FINAL_CLUSTER IN (:liCluster)");
				queryToGetCustomeChainL1.setParameter("storeFormat", storeFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
				queryToGetCustomeChainL1.setParameterList("listOfCustStores", listOfCustStores);
				queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
			}
			//kiran - bigint to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//return count.get(0);
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int storeCount(String storeFormat, String accountL1, String accountL2, List<String> liCluster) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) = :storeFormat AND ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2");
				queryToGetCustomeChainL1.setParameter("storeFormat", storeFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CURRENT_STORE_FORMAT) = :storeFormat AND ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2 AND FINAL_CLUSTER IN (:liCluster)");
				queryToGetCustomeChainL1.setParameter("storeFormat", storeFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
				queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
			}
			//kiran - big int to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//return count.get(0);
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int storeCountCust(String custStoreFormat, String accountL1, String accountL2, List<String> liCluster) {
		try {
			Query queryToGetCustomeChainL1 = null;
			if (liCluster.isEmpty() || liCluster.contains("ALL INDIA")) {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CUSTOMER_STORE_FORMAT) = :custStoreFormat AND ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2");
				queryToGetCustomeChainL1.setParameter("custStoreFormat", custStoreFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
			} else {
				queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT COUNT(1) FROM TBL_VAT_COMM_OUTLET_MASTER tlsm WHERE ACTIVE_STATUS = 'ACTIVE' AND UPPER(CUSTOMER_STORE_FORMAT) = :custStoreFormat AND ACCOUNT_NAME = :accountL1 and DP_CHAIN = :accountL2 AND FINAL_CLUSTER IN (:liCluster)");
				queryToGetCustomeChainL1.setParameter("custStoreFormat", custStoreFormat);
				queryToGetCustomeChainL1.setParameter("accountL1", accountL1);
				queryToGetCustomeChainL1.setParameter("accountL2", accountL2);
				queryToGetCustomeChainL1.setParameterList("liCluster", liCluster);
			}
			//kiran - big int to int changes
			//List<Integer> count = queryToGetCustomeChainL1.list();
			List<BigInteger> count = queryToGetCustomeChainL1.list();
			//return count.get(0);
			return count.get(0).intValue();
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBasepackCodeOnLaunchId(String launchId) {
		List<String> bpCodes = null;
		try {
			Query queryToGetCustomeChainL1 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT BP_CODE FROM TBL_LAUNCH_BASEPACK tlb WHERE LAUNCH_ID = '" + launchId + "'");
			bpCodes = queryToGetCustomeChainL1.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpCodes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArrayList<String>> getKamInputDumpForLaunch(ArrayList<String> headerDetail, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			downloadDataList.add(headerDetail);
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT REQ_ID,tlm.LAUNCH_ID,tlm.LAUNCH_NAME LAUNCH_NAME,tlm.LAUNCH_MOC LAUNCH_MOC,"
							+ "CHANGES_REQUIRED,KAM_REMARKS,REQ_DATE,tlr.CREATED_BY,tlr.CREATED_DATE FROM "
							+ " TBL_LAUNCH_REQUEST tlr, TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlr.LAUNCH_ID AND "
							+ " FINAL_STATUS = 'PENDING'  AND  tlm.CREATED_BY ='" + userId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				
				
				Query query2;
				// Modified code by Harsha for Q4 Sprint
				if(rs.getString("CHANGES_REQUIRED").equals("LAUNCH REJECTED")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
						+ "WHERE IS_ACTIVE = 2 AND LAUNCH_ID = '"
								+ rs.getString("LAUNCH_ID") + "'" 
						+ "AND UPDATED_BY = '" + rs.getString("CREATED_BY") + "'" 
						+ "AND REQ_ID = '" + rs.getString("REQ_ID") + "'");
				}
				
				else if(rs.getString("CHANGES_REQUIRED").equals("MOC CHANGED")) {
					query2 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
							+ "WHERE IS_ACTIVE = 1 AND LAUNCH_ID = '"
									+ rs.getString("LAUNCH_ID") + "'" 
							+ "AND UPDATED_BY = '" + rs.getString("CREATED_BY") + "'" 
							+ "AND REQ_ID = '" + rs.getString("REQ_ID") + "'");
				}//Harsha's Implementation ends.
				
				else {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT tvcom.ACCOUNT_NAME FROM TBL_LAUNCH_REQUEST tlr, TBL_VAT_USER_DETAILS tvud, "
								+ " TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE UPPER(tvud.USERID) = UPPER(tlr.CREATED_BY) AND"
								+ " UPPER(tvcom.KAE_MAIL_ID) = UPPER(tvud.EMAILID) AND UPPER(tlr.CREATED_BY) = '"
								+ rs.getString("CREATED_BY") + "'");
				}
			
				
				List<String> listOfAccounts = query2.list();
				
				String accounts = "";
				if (!listOfAccounts.isEmpty()) {
					accounts = listOfAccounts.toString();
					accounts=accounts.replace("[", "");
					accounts=accounts.replace("]", ""); // Harsha
				}
				ArrayList<String> dataObj = new ArrayList<>();
				dataObj.add(rs.getString("REQ_ID"));
				dataObj.add(rs.getString("LAUNCH_NAME"));
				dataObj.add(rs.getString("LAUNCH_MOC"));
				dataObj.add(accounts);
				dataObj.add(rs.getString("CREATED_BY"));
				dataObj.add(rs.getString("CHANGES_REQUIRED"));
				dataObj.add(rs.getString("KAM_REMARKS"));
				dataObj.add(rs.getString("CREATED_DATE"));
				dataObj.add("");
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}

	@Override
	public TblLaunchRequest getReqDataByReqId(String req_Id) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TblLaunchRequest tblLaunchRequest = new TblLaunchRequest();
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT REQ_ID, LAUNCH_ID, CHANGES_REQUIRED, KAM_REMARKS, REQ_DATE, REQ_TYPE, CREATED_DATE, CREATED_BY, "
							+ " UPDATED_DATE, UPDATED_BY, FINAL_STATUS, TME_REMARKS FROM MODTRD.TBL_LAUNCH_REQUEST WHERE REQ_ID = '"
							+ req_Id + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				tblLaunchRequest.setREQ_ID(rs.getString("REQ_ID"));
				tblLaunchRequest.setLAUNCH_ID(rs.getString("LAUNCH_ID"));
				tblLaunchRequest.setCHANGES_REQUIRED(rs.getString("CHANGES_REQUIRED"));
				tblLaunchRequest.setKAM_REMARKS(rs.getString("KAM_REMARKS"));
				tblLaunchRequest.setREQ_DATE(rs.getString("REQ_DATE"));
				tblLaunchRequest.setREQ_TYPE(rs.getString("REQ_TYPE"));
				tblLaunchRequest.setCREATED_DATE(rs.getString("CREATED_DATE"));
				tblLaunchRequest.setCREATED_BY(rs.getString("CREATED_BY"));
				tblLaunchRequest.setUPDATED_DATE(rs.getString("UPDATED_DATE"));
				tblLaunchRequest.setUPDATED_BY(rs.getString("UPDATED_BY"));
				tblLaunchRequest.setFINAL_STATUS(rs.getString("FINAL_STATUS"));
				tblLaunchRequest.setTME_REMARKS(rs.getString("TME_REMARKS"));
			}
			return tblLaunchRequest;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			tblLaunchRequest.setError(ex.toString());
			return tblLaunchRequest;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				tblLaunchRequest.setError(e.toString());
				return tblLaunchRequest;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBasepackCodeOnBpId(List<String> bpId, String launchId) {
		Query queryToGetCustomeChainL1 = null;
		try {
			queryToGetCustomeChainL1 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT BP_CODE FROM MODTRD.TBL_LAUNCH_BASEPACK WHERE BP_ID IN(:bpId) AND LAUNCH_ID = :launchId");
			queryToGetCustomeChainL1.setParameter("launchId", launchId);
			queryToGetCustomeChainL1.setParameterList("bpId", bpId);
			List<String> bpCode = queryToGetCustomeChainL1.list();
			return bpCode;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String insertdiffCustomerStoreFormatesToTemp(List<Object> saveLaunchCluster, String launchId,String userId) {
		//List<String> liReturn = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		
		int clusterId = 0;
		try {
		Iterator<Object> iterator = saveLaunchCluster.iterator();
		
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("DELETE FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" ");
		preparedStatement1.execute();
		
		while (iterator.hasNext()) {
			
			LaunchClusterDataCustStoreForm obj = (LaunchClusterDataCustStoreForm) iterator.next();
	
			ResultSet rs = null;
			try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_CLUSTERS_TEMP,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, launchId);
				preparedStatement.setString(2, obj.getCluster());
				preparedStatement.setString(3, obj.getAccount_L1());
				preparedStatement.setString(4, obj.getAccount_L2());
				preparedStatement.setString(5, obj.getCustomer_Store_Format().toString().replace("'", "").trim()); // Added By Harsha
				preparedStatement.setString(6, obj.getLaunch_planned());
				preparedStatement.setString(7, obj.getTotal_Stores());
				preparedStatement.setString(8, obj.getMinimum_Target_Stores());
				preparedStatement.setString(9, errorMessage(obj.getTotal_Stores().trim(),obj.getMinimum_Target_Stores().trim()));
				preparedStatement.setString(10, userId);
				preparedStatement.setTimestamp(11, new Timestamp(new Date().getTime()));
				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					clusterId = rs.getInt(1);
					
				}
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			} 
			}
			
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		
		}
		return "Sucessfully Written to TEMP Table";
	}
	
	
	
	
	
	public String errorMessage(String totalStores, String tmeTotalStores) { // Added By Harsha
		String message="";
        boolean result = tmeTotalStores.matches("[a-zA-Z]+");
        boolean hasSpecial = tmeTotalStores.matches("[^a-zA-Z0-9]");

		
		int totalStore;
		int tmeTotalStore;
	
		if(result) {
			return message = "Targeted or Total Store cannot contain Alphabets";
		}
		
		else if(hasSpecial) {
			return message = "Targeted or Total Store cannot have Special characters";
		}
		
		else if ((!totalStores.equals("0") && tmeTotalStores.isEmpty())){
			return	message = "TME Selected stores cannot be blank";
		}
		
		else if (tmeTotalStores.contains("-")){
			return	message = "Targeted Store count cannot be a negtive number";
		}
		
		
		String tmeTotalStorestr="";
			if(totalStores.isEmpty()) {
				totalStore=0;
			}
			
			
			else {
				totalStore=Integer. parseInt(totalStores);
			}
			
			if(tmeTotalStores.isEmpty()) {
				return message="";
			}
			else {
				tmeTotalStore = Integer. parseInt(tmeTotalStores);
			}
			if(totalStores.equals("0")) {
				return message="";
			}
			
			
			else if (tmeTotalStore>totalStore){
				return message = "TME Selected stores count should not be greater than given Total Stores";
			}
			
			else if ((totalStore>=1 && tmeTotalStore<=0) || (totalStore>=1 && tmeTotalStores.isEmpty())){
				return	message = "TME Selected stores should not be less than 1 or blank";
			}
			
				return message;
	}
	


	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String getCountofErrorMSgfromCustmstorTblLaunchClusterTempAndinserttopermtbl(String launchId) {
		//List<String> liReturn = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		 ResultSet rst = null;
		int count = 0 ;
		String countofError="";
		try {
		
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("Select count(ERROR_MSG) FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" AND ERROR_MSG LIKE '%TME%' ");
		rst=preparedStatement1.executeQuery();
		
		while(rst.next()) {
            count=rst.getInt(1);
          //  return countofError=String.valueOf(rst.getInt(1));
         }
		
		
		if(count==0) {
			
			PreparedStatement preparedStatement2 = sessionImpl.connection().prepareStatement("DELETE FROM TBL_LAUNCH_CLUSTERS_DETAILS where LAUNCH_ID = "+launchId+" ");
			preparedStatement2.execute();
			
			PreparedStatement preparedStatement3 = sessionImpl.connection().prepareStatement("Select * FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" AND TOTAL_STORES_TO_LAUNCH>0");
			rst=preparedStatement3.executeQuery();
			
			while(rst.next()) {
	            ResultSet rs = null;
	            
	            PreparedStatement preparedStatement4 = sessionImpl.connection().prepareStatement(TBL_LAUNCH_CLUSTERS_DETAILS,
						Statement.RETURN_GENERATED_KEYS);
	            
	            preparedStatement4.setString(1, rst.getString("LAUNCH_ID"));
	            preparedStatement4.setString(2, rst.getString("CLUSTER_REGION"));
	            preparedStatement4.setString(3, rst.getString("CLUSTER_ACCOUNT_L1"));
	            preparedStatement4.setString(4, rst.getString("CLUSTER_ACCOUNT_L2"));
	            preparedStatement4.setString(5, rst.getString("CLUSTER_CUST_STORE_FORMAT").replace("'", "").toString().trim()); // Added By Harsha
	            preparedStatement4.setString(6, rst.getString("LAUNCH_PLANNED"));
	            preparedStatement4.setString(7, rst.getString("TOTAL_STORES_TO_LAUNCH"));
	            preparedStatement4.setString(8, rst.getString("TOTAL_TME_STORES_PLANED"));
				preparedStatement4.setString(9, rst.getString("CREATED_BY"));
	            preparedStatement4.setTimestamp(10, new Timestamp(new Date().getTime()));
	            preparedStatement4.executeUpdate();
				rs = preparedStatement4.getGeneratedKeys();
	            
	         }
			
		}
		
		else {
			return countofError=String.valueOf(count);
		}
		
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		
		}
		return "SUCESS";
	}
	
	
	// Added by harsha to get count of final stores -- starts
	@SuppressWarnings("unchecked")
	@Transactional(rollbackOn = Exception.class)
	public String getCountofStoreFixed(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		 ResultSet rst = null;
		String clusterId = "";
		try {
		
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("Select SUM(TOTAL_TME_STORES_PLANED) FROM TBL_LAUNCH_CLUSTERS_DETAILS where LAUNCH_ID = "+launchId+"");
		rst=preparedStatement1.executeQuery();
		
		while(rst.next()) {
            return clusterId=String.valueOf(rst.getInt(1));
         }
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		
		}
		return "SUCESS";
	} // Added by harsha to get count of final stores -- Ends
	
	//  Added by Harsha FOR STORE FORMATE -- Starts
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String getCountofErrorMSgfromTblLaunchClusterTempStroeFormate(String launchId) {
		//List<String> liReturn = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		 ResultSet rst = null;
		int count = 0 ;
		String countofError="";
		try {
		
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("Select count(ERROR_MSG) FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" AND ERROR_MSG LIKE '%TME%' ");
		rst=preparedStatement1.executeQuery();
		
		while(rst.next()) {
            count=rst.getInt(1);
         }
		
		
		if(count==0) {
			
			PreparedStatement preparedStatement2 = sessionImpl.connection().prepareStatement("DELETE FROM TBL_LAUNCH_CLUSTERS_DETAILS where LAUNCH_ID = "+launchId+" ");
			preparedStatement2.execute();
			
			PreparedStatement preparedStatement3 = sessionImpl.connection().prepareStatement("Select * FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" AND TOTAL_STORES_TO_LAUNCH>0");
			rst=preparedStatement3.executeQuery();
			
			while(rst.next()) {
	            ResultSet rs = null;
	            
	            PreparedStatement preparedStatement4 = sessionImpl.connection().prepareStatement(TBL_LAUNCH_CLUSTERS_DETAILS_STORE,
						Statement.RETURN_GENERATED_KEYS);
	            
	            preparedStatement4.setString(1, rst.getString("LAUNCH_ID"));
	            preparedStatement4.setString(2, rst.getString("CLUSTER_REGION"));
	            preparedStatement4.setString(3, rst.getString("CLUSTER_ACCOUNT_L1"));
	            preparedStatement4.setString(4, rst.getString("CLUSTER_ACCOUNT_L2"));
	            preparedStatement4.setString(5, rst.getString("CLUSTER_STORE_FORMAT").toString().replace("'", "").trim()); // Added By Harsha
	            preparedStatement4.setString(6, rst.getString("LAUNCH_PLANNED"));
	            preparedStatement4.setString(7, rst.getString("TOTAL_STORES_TO_LAUNCH"));
	            preparedStatement4.setString(8, rst.getString("TOTAL_TME_STORES_PLANED"));
				preparedStatement4.setString(9, rst.getString("CREATED_BY"));
	            preparedStatement4.setTimestamp(10, new Timestamp(new Date().getTime()));
	            preparedStatement4.executeUpdate();
				rs = preparedStatement4.getGeneratedKeys();
	            
	         }
			
		}
		
		else {
			return countofError=String.valueOf(count);
		}
		
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		
		}
		return "SUCESS";
	} //  Added by Harsha FOR STORE FORMATE -- End's

	
	// For customerStoreFormat
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<ArrayList<String>> getLaunchClusterErrorDataforCustomerStoreFormat(ArrayList<String> headerDetail,
	String userId, String launchID) {
		//String launchID=downloadLaunchClusterRequest.getLaunchId();
	List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
	downloadDataList.add(headerDetail);
	Session session = sessionFactory.getCurrentSession();
	SessionImpl sessionImpl = (SessionImpl) session;
	ResultSet rst = null;
	String clusterId = "";
	
	try {
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("Select * FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchID+"");
		rst=preparedStatement1.executeQuery();
		
		while(rst.next()) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(rst.getString("CLUSTER_REGION"));
			dataObj.add(rst.getString("CLUSTER_ACCOUNT_L1"));
			dataObj.add(rst.getString("CLUSTER_ACCOUNT_L2"));
			dataObj.add(rst.getString("CLUSTER_CUST_STORE_FORMAT").toString().replace("'", "").trim()); // Added By Harsha
			dataObj.add(rst.getString("LAUNCH_PLANNED"));
			dataObj.add(rst.getString("TOTAL_STORES_TO_LAUNCH"));
			dataObj.add(rst.getString("TOTAL_TME_STORES_PLANED"));
			dataObj.add(rst.getString("ERROR_MSG"));
			downloadDataList.add(dataObj);
         }
		
		
	} catch (Exception ex) {
	logger.debug("Exception :", ex);
	return downloadDataList;
	}
	return downloadDataList;
	}
	
	// for error files of store formate
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<ArrayList<String>> getLaunchClusterErrorDataforStoreFormat(ArrayList<String> headerDetail,
	String userId, String launchID) {
		//String launchID=downloadLaunchClusterRequest.getLaunchId();
	List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
	downloadDataList.add(headerDetail);
	Session session = sessionFactory.getCurrentSession();
	SessionImpl sessionImpl = (SessionImpl) session;
	ResultSet rst = null;
	String clusterId = "";
	
	try {
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("Select * FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchID+"");
		rst=preparedStatement1.executeQuery();
		
		while(rst.next()) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(rst.getString("CLUSTER_REGION"));
			dataObj.add(rst.getString("CLUSTER_ACCOUNT_L1"));
			dataObj.add(rst.getString("CLUSTER_ACCOUNT_L2"));
			dataObj.add(rst.getString("CLUSTER_CUST_STORE_FORMAT").toString().replace("'", "").trim()); // Added By Harsha
			dataObj.add(rst.getString("LAUNCH_PLANNED"));
			dataObj.add(rst.getString("TOTAL_STORES_TO_LAUNCH"));
			dataObj.add(rst.getString("TOTAL_TME_STORES_PLANED"));
			dataObj.add(rst.getString("ERROR_MSG"));
			downloadDataList.add(dataObj);
         }
		
		
	} catch (Exception ex) {
	logger.debug("Exception :", ex);
	return downloadDataList;
	}
	return downloadDataList;
	}
	
	// Added by Harsha for sptinr 7 us 7 -- start
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public String insertdiffStoreFormatesToTemp(List<Object> saveLaunchCluster, String launchId,String userId) {
		//List<String> liReturn = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		
		int clusterId = 0;
		try {
		Iterator<Object> iterator = saveLaunchCluster.iterator();
		
		PreparedStatement preparedStatement1 = sessionImpl.connection().prepareStatement("DELETE FROM TBL_LAUNCH_CLUSTERS_TEMP where LAUNCH_ID = "+launchId+" ");
		preparedStatement1.execute();
		
		while (iterator.hasNext()) {
			
			LaunchClusterDataStoreForm obj = (LaunchClusterDataStoreForm) iterator.next();
			
		
			ResultSet rs = null;
			try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_CLUSTERS_TEMP_STORE,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, launchId);
				preparedStatement.setString(2, obj.getCluster());
				preparedStatement.setString(3, obj.getAccount_L1());
				preparedStatement.setString(4, obj.getAccount_L2());
				preparedStatement.setString(5, obj.getStore_Format().replace("'", "").trim()); // Added By Harsha
				preparedStatement.setString(6, obj.getLaunch_planned());
				preparedStatement.setString(7, obj.getTotal_Stores());
				preparedStatement.setString(8, obj.getMinimum_Target_Stores());
				preparedStatement.setString(9, errorMessage(obj.getTotal_Stores().trim(),obj.getMinimum_Target_Stores().trim()));
				preparedStatement.setString(10, userId);
				preparedStatement.setTimestamp(11, new Timestamp(new Date().getTime()));
				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					clusterId = rs.getInt(1);
					
				}
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			} 
			}
			
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		
		}
		return "Sucessfully Written to TEMP Table";
	}// Added by Harsha for sptinr 7 us 7 -- End's
	
	
	
	 
	
}