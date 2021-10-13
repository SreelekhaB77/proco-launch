package com.hul.launch.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.dao.LaunchDao;
import com.hul.launch.dao.LaunchDaoSc;
import com.hul.launch.dao.LoginDao;
import com.hul.launch.exception.GlobleKamException;
import com.hul.launch.model.LaunchMstnClearance;
import com.hul.launch.model.User;
import com.hul.launch.request.RequestMstnClearance;
import com.hul.launch.request.RequestMstnClearanceList;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchScBasepackResponse;
import com.hul.launch.response.LaunchScFinalBuildUpResponse;
import com.hul.launch.response.LaunchScMstnClearanceResponse;
import com.hul.launch.web.util.CommonUtils;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchScDaoImpl implements LaunchDaoSc {

	// Chagnes done By Harsha added remarks to below insert Statement
	private final static String TBL_LAUNCH_MSTN_CLEARANCE = "INSERT INTO MODTRD.TBL_LAUNCH_MSTN_CLEARANCE (LAUNCH_ID, LAUNCH_MOC, "
			+ " BASEPACK_CODE, BASEPACK_DESCRIPTION, DEPOT, CLUSTER, MSTN_CLEARED, FINAL_CLD_FOR_N, FINAL_CLD_FOR_N1, "
			+ " FINAL_CLD_FOR_N2,ACCOUNT, CURRENT_ESTIMATES, CLEARANCE_DATE, CREATED_DATE, CREATED_BY,REMARKS) "
			+ " VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private LaunchDao launchDao;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	//Q2 sprint feb 2021 kavitha
	//public List<LaunchDataResponse> getAllCompletedScLaunchData() {
	public List<LaunchDataResponse> getAllCompletedScLaunchData(String scMoc)
	{
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchDataResponse> listOfCompletedLaunch = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//Kavitha Changes - Q2 Sprint Feb2021
			if (scMoc.equalsIgnoreCase("All")) {
				scMoc = "";
			}
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC, LAUNCH_SUBMISSION_DATE "
							+ " , IFNULL((SELECT GROUP_CONCAT(LAUNCH_KAM_ACCOUNT separator '; ') AS CHANGED_MOC FROM (SELECT CONCAT(LAUNCH_MOC_KAM, ' - ', GROUP_CONCAT(LAUNCH_KAM_ACCOUNT)) AS LAUNCH_KAM_ACCOUNT " //Added By Sarin - sprint5Sep2021
							+ " 		FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS KL WHERE KL.LAUNCH_ID = tlc.LAUNCH_ID AND IS_ACTIVE = 1 GROUP BY LAUNCH_MOC_KAM) A), '') CHANGED_MOC "                                //Added By Sarin - sprint5Sep2021
							+ "FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' AND LAUNCH_MOC LIKE '%" + scMoc + "%'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchDataResponse launchDataResponse = new LaunchDataResponse();
				launchDataResponse.setLaunchId(rs.getInt(1));
				launchDataResponse.setLaunchName(rs.getString(2));
				launchDataResponse.setLaunchDate(rs.getString(3));
				launchDataResponse.setLaunchNature(rs.getString(4));
				launchDataResponse.setLaunchNature2(rs.getString(5));
				launchDataResponse.setLaunchBusinessCase(rs.getString(6));
				launchDataResponse.setCategorySize(rs.getString(7));
				launchDataResponse.setClassification(rs.getString(8));
				launchDataResponse.setAnnexureDocName(rs.getString(9));
				launchDataResponse.setArtWorkPackShotsDocName(rs.getString(10));
				launchDataResponse.setMdgDeckDocName(rs.getString(11));
				launchDataResponse.setSampleShared(rs.getString(12));
				User user = loginDao.getUserById(rs.getString(13));
				launchDataResponse.setCreatedBy(user.getFirstName() + " " + user.getLastName());
				launchDataResponse.setCreatedDate(rs.getDate(14));
				launchDataResponse.setUpdatedBy(rs.getString(15));
				launchDataResponse.setUpdatedDate(rs.getDate(16));
				launchDataResponse.setLaunchMoc(rs.getString(17));
				launchDataResponse.setLaunchSubmissionDate(rs.getString(18));
				launchDataResponse.setChangedMoc(rs.getString(19));  //Added By Sarin - sprint5Sep2021
				listOfCompletedLaunch.add(launchDataResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchDataResponse launchDataResponse = new LaunchDataResponse();
			launchDataResponse.setError(ex.toString());
			listOfCompletedLaunch.add(launchDataResponse);
			return listOfCompletedLaunch;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listOfCompletedLaunch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchScBasepackResponse> getScBasepackData(List<String> launchIds) {
		List<LaunchScBasepackResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION,tlb.BP_ID FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData)"
							+ " AND (tlb.BP_STATUS IS NULL OR tlb.BP_STATUS NOT IN ('REJECTED BY KAM','REJECTED BY TME'))");
			query2.setParameterList("listOfLaunchData", launchIds);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchScBasepackResponse launchScBasepackResponse = new LaunchScBasepackResponse();
				launchScBasepackResponse.setLaunchName(obj[0].toString());
				launchScBasepackResponse.setSalesCat(obj[1].toString());
				launchScBasepackResponse.setPsaCat(obj[2].toString());
				launchScBasepackResponse.setBrand(obj[3].toString());
				launchScBasepackResponse.setBpCode(obj[4].toString());
				launchScBasepackResponse.setBpDisc(obj[5].toString());
				launchScBasepackResponse.setMrp(obj[6].toString());
				launchScBasepackResponse.setTur(obj[7].toString());
				launchScBasepackResponse.setGsv(obj[8].toString());
				launchScBasepackResponse.setCldConfig(obj[9].toString());
				launchScBasepackResponse.setGrammage(obj[10].toString());
				launchScBasepackResponse.setClassification(obj[11].toString());
				launchScBasepackResponse.setBasepackId(obj[12].toString());
				listOfCompletedLaunch.add(launchScBasepackResponse);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_SC_BASEPACK_DATA, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchScFinalBuildUpResponse> getScBuildUpData(List<String> listOfLaunchData) {
		List<LaunchScFinalBuildUpResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME, BUILDUP_SKU_NAME, BUILDUP_BASEPACK_CODE, BUILDUP_LAUNCH_SELLIN_VALUE,"
							+ "BUILDUP_LAUNCH_SELLIN_N1, BUILDUP_LAUNCH_SELLIN_N2, BUILDUP_LAUNCH_SELLIN_CLDS, "
							+ "BUILDUP_LAUNCH_SELLIN_CLDS_N1, BUILDUP_LAUNCH_SELLIN_CLDS_N2, BUILDUP_LAUNCH_SELLIN_UNITS,"
							+ "BUILDUP_LAUNCH_SELLIN_UNITS_N1, BUILDUP_LAUNCH_SELLIN_UNITS_N2"
							+ " FROM MODTRD.TBL_LAUNCH_FINAL_BUILDUP tlfb, MODTRD.TBL_LAUNCH_MASTER tlm WHERE "
							+ "tlfb.BUILDUP_LAUNCH_ID = tlm.LAUNCH_ID AND BUILDUP_LAUNCH_ID IN (:listOfLaunchData) and "
							+ "SAMPLE_SHARED IS NOT NULL AND SAMPLE_SHARED IS NOT NULL");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchScFinalBuildUpResponse launchScBasepackResponse = new LaunchScFinalBuildUpResponse();
				launchScBasepackResponse.setLaunchName(obj[0].toString());
				launchScBasepackResponse.setSkuName(obj[1].toString());
				launchScBasepackResponse.setBasepackCode(obj[2].toString());
				launchScBasepackResponse.setLaunchSellInValueN(obj[3].toString());
				launchScBasepackResponse.setLaunchSellInValueN1(obj[4].toString());
				launchScBasepackResponse.setLaunchSellInValueN2(obj[5].toString());
				launchScBasepackResponse.setLaunchSellInCldValueN(obj[6].toString());
				launchScBasepackResponse.setLaunchSellInCldValueN1(obj[7].toString());
				launchScBasepackResponse.setLaunchSellInCldValueN2(obj[8].toString());
				launchScBasepackResponse.setLaunchSellInUnitsN(obj[9].toString());
				launchScBasepackResponse.setLaunchSellInUnitsN1(obj[10].toString());
				launchScBasepackResponse.setLaunchSellInUnitsN2(obj[11].toString());
				listOfCompletedLaunch.add(launchScBasepackResponse);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_SC_FINAL_BUILDUP_DATA, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchScMstnClearanceResponse> getScMstnClearanceData(List<String> listOfLaunchData) {
		List<LaunchScMstnClearanceResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT * FROM TBL_LAUNCH_MSTN_CLEARANCE WHERE LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			List<Object> ifSavedAlready = query2.list();
			if (ifSavedAlready.isEmpty()) {
				Query query1 = sessionFactory.getCurrentSession().createNativeQuery(
						//Commented By Sarin & Added Below - sprint5Sep2021
						/*"SELECT tlm.LAUNCH_NAME,tlm.LAUNCH_MOC,tltfc.BP_NAME,DEPOT,CLUSTER,tltfc.FINAL_CLD_N,tltfc.FINAL_CLD_N1, "
								+ "tltfc.FINAL_CLD_N2,tlm.LAUNCH_ID, tltfc.MODIFIED_CHAIN FROM TBL_LAUNCH_TEMP_FINAL_CAL tltfc, "
								+ "TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tltfc.LAUNCH_ID AND SAMPLE_SHARED IS NOT NULL AND " */
						//Added By Sarin - sprint5Sep2021
						"SELECT tlm.LAUNCH_NAME, CASE WHEN TLK.LAUNCH_MOC_KAM IS NULL THEN tlm.LAUNCH_MOC ELSE TLK.LAUNCH_MOC_KAM END AS LAUNCH_MOC, "
						+ " tltfc.BP_NAME,DEPOT,CLUSTER,tltfc.FINAL_CLD_N,tltfc.FINAL_CLD_N1, tltfc.FINAL_CLD_N2,tlm.LAUNCH_ID, tltfc.MODIFIED_CHAIN "
						+ " FROM TBL_LAUNCH_TEMP_FINAL_CAL tltfc INNER JOIN TBL_LAUNCH_MASTER tlm ON tlm.LAUNCH_ID = tltfc.LAUNCH_ID "
						+ " LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = tlm.LAUNCH_ID AND TLK.LAUNCH_KAM_ACCOUNT = tltfc.MODIFIED_CHAIN AND TLK.IS_ACTIVE = 1 "
						+ " WHERE SAMPLE_SHARED IS NOT NULL AND tltfc.LAUNCH_ID IN (:listOfLaunchData)");
				query1.setParameterList("listOfLaunchData", listOfLaunchData);
				Iterator<Object> iterator = query1.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					LaunchScMstnClearanceResponse launchScMstnClearanceResponse = new LaunchScMstnClearanceResponse();
					launchScMstnClearanceResponse.setLaunchName(obj[0].toString());
					launchScMstnClearanceResponse.setLaunchMoc(obj[1].toString());
					launchScMstnClearanceResponse.setBasepackCode(obj[2].toString().split(":")[0].trim());
					launchScMstnClearanceResponse.setBasepackDesc(obj[2].toString().split(":")[1].trim());
					launchScMstnClearanceResponse.setDepot(obj[3].toString());
					launchScMstnClearanceResponse.setCluster(obj[4].toString());
					launchScMstnClearanceResponse.setFinalCldN(obj[5].toString());
					launchScMstnClearanceResponse.setFinalCldN1(obj[6].toString());
					launchScMstnClearanceResponse.setFinalCldN2(obj[7].toString());
					launchScMstnClearanceResponse.setLaunchId(obj[8].toString());
					launchScMstnClearanceResponse.setAccount(obj[9].toString());
					listOfCompletedLaunch.add(launchScMstnClearanceResponse);
				}
			} else {
				Iterator<Object> iterator = ifSavedAlready.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					LaunchDataResponse specificLaunchData = launchDao.getSpecificLaunchData(obj[1].toString());
					LaunchScMstnClearanceResponse launchScMstnClearanceResponse = new LaunchScMstnClearanceResponse();
					launchScMstnClearanceResponse.setLaunchName(specificLaunchData.getLaunchName());
					launchScMstnClearanceResponse.setLaunchMoc(obj[2].toString());
					launchScMstnClearanceResponse.setBasepackCode(obj[3].toString());
					launchScMstnClearanceResponse.setBasepackDesc(obj[4].toString());
					launchScMstnClearanceResponse.setDepot(obj[5].toString());
					launchScMstnClearanceResponse.setCluster(obj[6].toString());
					launchScMstnClearanceResponse.setMstnCleared(obj[7].toString());
					launchScMstnClearanceResponse.setFinalCldN(obj[8].toString());
					launchScMstnClearanceResponse.setFinalCldN1(obj[9].toString());
					launchScMstnClearanceResponse.setFinalCldN2(obj[10].toString());
					launchScMstnClearanceResponse.setCurrentEstimates(obj[11].toString());
					launchScMstnClearanceResponse.setClearanceDate(obj[12].toString());
					launchScMstnClearanceResponse.setLaunchId(obj[1].toString());
					launchScMstnClearanceResponse.setAccount(obj[13].toString());
					listOfCompletedLaunch.add(launchScMstnClearanceResponse);
				}
			}

			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_SC, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchScMstnClearanceResponse> getScMstnClearanceDataDump(List<String> listOfLaunchData) {
		List<LaunchScMstnClearanceResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession() // Added By Harsha for picking values after upload
					.createNativeQuery("select A.LAUNCH_ID, B.LAUNCH_NAME, " + 
							" A.LAUNCH_MOC,A.BASEPACK_CODE,A.BASEPACK_DESCRIPTION,A.DEPOT,A.CLUSTER,A.FINAL_CLD_FOR_N, " + 
							" A.FINAL_CLD_FOR_N1,A.FINAL_CLD_FOR_N2,A.ACCOUNT,A.MSTN_CLEARED,A.CURRENT_ESTIMATES, " + 
							" A.CLEARANCE_DATE,A.REMARKS from TBL_LAUNCH_MSTN_CLEARANCE A INNER JOIN " + 
							" TBL_LAUNCH_MASTER B on A.LAUNCH_ID = B.LAUNCH_ID where A.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			List<Object> ifSavedAlready = query2.list();
			if (ifSavedAlready.isEmpty()) {
				Query query1 = sessionFactory.getCurrentSession().createNativeQuery(
						//Commented By Sarin & Added Below - sprint5Sep2021
						/*"SELECT tlm.LAUNCH_NAME,tlm.LAUNCH_MOC,tltfc.BP_NAME,DEPOT,CLUSTER,tltfc.FINAL_CLD_N,tltfc.FINAL_CLD_N1, "
								+ "tltfc.FINAL_CLD_N2,tlm.LAUNCH_ID, tltfc.MODIFIED_CHAIN FROM TBL_LAUNCH_TEMP_FINAL_CAL tltfc, "
								+ "TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tltfc.LAUNCH_ID AND SAMPLE_SHARED IS NOT NULL AND " */
						//Added By Sarin - sprint5Sep2021
						"SELECT tlm.LAUNCH_NAME, CASE WHEN TLK.LAUNCH_MOC_KAM IS NULL THEN tlm.LAUNCH_MOC ELSE TLK.LAUNCH_MOC_KAM END AS LAUNCH_MOC, "
						+ " tltfc.BP_NAME,DEPOT,CLUSTER,tltfc.FINAL_CLD_N,tltfc.FINAL_CLD_N1, tltfc.FINAL_CLD_N2,tlm.LAUNCH_ID, tltfc.MODIFIED_CHAIN "
						+ " FROM TBL_LAUNCH_TEMP_FINAL_CAL tltfc INNER JOIN TBL_LAUNCH_MASTER tlm ON tlm.LAUNCH_ID = tltfc.LAUNCH_ID "
						+ " LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = tlm.LAUNCH_ID AND TLK.LAUNCH_KAM_ACCOUNT = tltfc.MODIFIED_CHAIN AND TLK.IS_ACTIVE = 1 "
						+ " WHERE SAMPLE_SHARED IS NOT NULL AND tltfc.LAUNCH_ID IN (:listOfLaunchData)");
				query1.setParameterList("listOfLaunchData", listOfLaunchData);
				Iterator<Object> iterator = query1.list().iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					LaunchScMstnClearanceResponse launchScMstnClearanceResponse = new LaunchScMstnClearanceResponse();
					launchScMstnClearanceResponse.setLaunchName(obj[0].toString());
					launchScMstnClearanceResponse.setLaunchMoc(obj[1].toString());
					launchScMstnClearanceResponse.setBasepackCode(obj[2].toString().split(":")[0].trim());
					launchScMstnClearanceResponse.setBasepackDesc(obj[2].toString().split(":")[1].trim());
					launchScMstnClearanceResponse.setDepot(obj[3].toString());
					launchScMstnClearanceResponse.setCluster(obj[4].toString());
					launchScMstnClearanceResponse.setFinalCldN(obj[5].toString());
					launchScMstnClearanceResponse.setFinalCldN1(obj[6].toString());
					launchScMstnClearanceResponse.setFinalCldN2(obj[7].toString());
					launchScMstnClearanceResponse.setLaunchId(obj[8].toString());
					launchScMstnClearanceResponse.setAccount(obj[9].toString());
					listOfCompletedLaunch.add(launchScMstnClearanceResponse);
				}
			} else { // Else part was added by Harsha to print uploaded values to excel
				Iterator<Object> iterator = ifSavedAlready.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					LaunchDataResponse specificLaunchData = launchDao.getSpecificLaunchData(obj[1].toString());
					LaunchScMstnClearanceResponse launchScMstnClearanceResponse = new LaunchScMstnClearanceResponse();
					launchScMstnClearanceResponse.setLaunchName(obj[1].toString());
					launchScMstnClearanceResponse.setLaunchMoc(obj[2].toString());
					launchScMstnClearanceResponse.setBasepackCode(obj[3].toString());
					launchScMstnClearanceResponse.setBasepackDesc(obj[4].toString());
					launchScMstnClearanceResponse.setDepot(obj[5].toString());
					launchScMstnClearanceResponse.setCluster(obj[6].toString());
					launchScMstnClearanceResponse.setMstnCleared(obj[11].toString());
					launchScMstnClearanceResponse.setFinalCldN(obj[7].toString());
					launchScMstnClearanceResponse.setFinalCldN1(obj[8].toString());
					launchScMstnClearanceResponse.setFinalCldN2(obj[9].toString());
					launchScMstnClearanceResponse.setCurrentEstimates(obj[12].toString());
					launchScMstnClearanceResponse.setClearanceDate(obj[13].toString());
					launchScMstnClearanceResponse.setLaunchId(obj[0].toString());
					launchScMstnClearanceResponse.setAccount(obj[10].toString());
					launchScMstnClearanceResponse.setRemarks(obj[14].toString());
					listOfCompletedLaunch.add(launchScMstnClearanceResponse);
				}
			}

			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_SC, ex.toString());
		}
	}

	@Override
	public String saveMstnClearanceByLaunchIdsSc(RequestMstnClearanceList requestMstnClearanceList, String userId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			Set<String> listOfLaunches = new HashSet<>();
			for (RequestMstnClearance reqMstnCleared : requestMstnClearanceList.getListOfRequestMstnClearance()) {
				listOfLaunches.add(reqMstnCleared.getLaunchId());
			}
			List<String> aList = new ArrayList<String>();
			aList.addAll(listOfLaunches);
			String finalString = "";
			for (int i = 0; i < aList.size(); i++) {
				if ((i + 1) >= aList.size()) {
					finalString = finalString + "'" + aList.get(i) + "'";
				} else {
					finalString = "'" + aList.get(i) + "'," + finalString;
				}
			}for (RequestMstnClearance saveLaunchStoreData1 : requestMstnClearanceList
					.getListOfRequestMstnClearance()) {
				String startDate ="";
				//kiran - changes for TO_DATE and VARCHAR_FORMAT
			//Query query =sessionFactory.getCurrentSession().createNativeQuery("select VARCHAR_FORMAT(TO_DATE(LAUNCH_DATE, 'DD-MM-YYYY') - 5, 'DD/MM/YYYY') from TBL_LAUNCH_MASTER where launch_id=:ref");
				Query query =sessionFactory.getCurrentSession().createNativeQuery("select DATE_FORMAT(DATE_SUB(STR_TO_DATE(LAUNCH_DATE, '%d/%m/%Y'), INTERVAL 5 DAY),'%d/%m/%Y') from TBL_LAUNCH_MASTER where launch_id=:ref");
			query.setString("ref",saveLaunchStoreData1.getLaunchId() );
			List list=query.list();
			startDate=  list.get(0).toString();
			Date mocStartDate=CommonUtils.dateFromater(startDate);
			String pattern = "dd/MM/yyyy";
			String todaysDate = new SimpleDateFormat(pattern).format(new Date());
			Date today=CommonUtils.dateFromater(todaysDate);
			/*
			 * if(today.compareTo(mocStartDate) >= 0) { return "Wrong date"; }
			 */
			}
			PreparedStatement batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_MSTN_CLEARANCE where LAUNCH_ID IN (" + finalString + ")");
			batchUpdate.executeUpdate();

			try (PreparedStatement preparedStatement = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_MSTN_CLEARANCE, Statement.RETURN_GENERATED_KEYS)) {
				for (RequestMstnClearance saveLaunchStoreData : requestMstnClearanceList
						.getListOfRequestMstnClearance()) {
					preparedStatement.setString(1, saveLaunchStoreData.getLaunchId());
					preparedStatement.setString(2, saveLaunchStoreData.getLaunchMoc());
					preparedStatement.setString(3, saveLaunchStoreData.getBasepackCode());
					preparedStatement.setString(4, saveLaunchStoreData.getBasepackDescription());
					preparedStatement.setString(5, saveLaunchStoreData.getDepot());
					preparedStatement.setString(6, saveLaunchStoreData.getCluster());
					preparedStatement.setString(7, saveLaunchStoreData.getMstnCleared());
					preparedStatement.setString(8, saveLaunchStoreData.getFinalCldForN());
					preparedStatement.setString(9, saveLaunchStoreData.getFinalCldForN1());
					preparedStatement.setString(10, saveLaunchStoreData.getFinalCldForN2());
					preparedStatement.setString(11, saveLaunchStoreData.getAccount());
					preparedStatement.setString(12, saveLaunchStoreData.getCurrentEstimates());
					preparedStatement.setString(13, saveLaunchStoreData.getClearanceDate());
					preparedStatement.setTimestamp(14, new Timestamp(new Date().getTime()));
					preparedStatement.setString(15, userId);
					preparedStatement.setString(16,saveLaunchStoreData.getRemarks());
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		}

		return "Saved Successfully";
	}
	// Added By Harsha for input validation 
	public String getEstimates(String mSTNCleareance, String finalCldn, String currentEstimates) {
		double finalCldvalue = Double.parseDouble(finalCldn);
		double currentEstimate;
		mSTNCleareance = mSTNCleareance.toLowerCase();
		if(currentEstimates == null || currentEstimates.isEmpty()) {
			currentEstimate = 0;
		}
		else {
			currentEstimate = Double.parseDouble(currentEstimates);
		}
		
		if((!mSTNCleareance.isEmpty() && (mSTNCleareance.equals("y") || mSTNCleareance.equals("yes"))) &&
				(currentEstimate > 0.000 && (currentEstimate*100)/finalCldvalue >= 80.0)) {
			return "MSTN Cleared";
		}
		else if((!mSTNCleareance.isEmpty() && (mSTNCleareance.equals("y") || mSTNCleareance.equals("yes"))) && 
				(currentEstimate > 0.000 && (currentEstimate*100)/finalCldvalue < 80.0)) {
			return "Estimate Insufficient";
		}
		else if (mSTNCleareance.contains("n") || mSTNCleareance.equals("no")) {
			return "MSTN Not Cleared";
		}
		
		return "Not a valid input from SC user";
		
		}

	@Override
	public String uploadMstnClearanceByLaunchIdSc(List<Object> listOfMstnClearance, String userID) {
		RequestMstnClearanceList requestMstnClearanceList = new RequestMstnClearanceList();
		List<RequestMstnClearance> listOfRequestMstnClearance = new ArrayList<>();

		Iterator<Object> iterator = listOfMstnClearance.iterator();
		while (iterator.hasNext()) {
			LaunchMstnClearance obj = (LaunchMstnClearance) iterator.next();
			RequestMstnClearance requestMstnClearance = new RequestMstnClearance();
			if (obj.getLAUNCH_ID().equals("") || obj.getLAUNCH_ID() == null) {
				throw new NullPointerException("Launch Id Can not be null");
			}
			requestMstnClearance.setLaunchId(obj.getLAUNCH_ID());
			requestMstnClearance.setLaunchMoc(obj.getLAUNCH_MOC());
			requestMstnClearance.setBasepackCode(obj.getBASEPACK_CODE());
			requestMstnClearance.setBasepackDescription(obj.getBASEPACK_DESCRIPTION());
			requestMstnClearance.setDepot(obj.getDEPOT());
			requestMstnClearance.setCluster(obj.getCLUSTER());
			requestMstnClearance.setFinalCldForN(obj.getFINAL_CLD_N());
			requestMstnClearance.setFinalCldForN1(obj.getFINAL_CLD_N1());
			requestMstnClearance.setFinalCldForN2(obj.getFINAL_CLD_N2());
			requestMstnClearance.setCurrentEstimates(obj.getCURRENT_ESTIMATES());
			requestMstnClearance.setClearanceDate(obj.getCLEARANCE_DATE());
			requestMstnClearance.setAccount(obj.getACCOUNT());
			requestMstnClearance.setMstnCleared(obj.getMSTN_CLEARED().toUpperCase());
			requestMstnClearance.setRemarks(obj.getREMARKS());
			requestMstnClearance.setRemarks(getEstimates(obj.getMSTN_CLEARED(), obj.getFINAL_CLD_N(),obj.getCURRENT_ESTIMATES()));
			listOfRequestMstnClearance.add(requestMstnClearance);
		}

		requestMstnClearanceList.setListOfRequestMstnClearance(listOfRequestMstnClearance);
		String result = saveMstnClearanceByLaunchIdsSc(requestMstnClearanceList, userID);
		if (result.equals("Saved Successfully")) {
			return "SUCCESS_FILE";
		}
		else if (result.equals("Wrong date")) {
			return "Wrong date";
		}
		else {
			return "ERROR";
		}
	}
	//Q2 sprint kavitha feb2021 
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getAllMoc() {
			try {
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT LAUNCH_MOC " + 
						" FROM TBL_LAUNCH_MASTER tlc " + 
						"WHERE SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' ORDER BY concat(substr(LAUNCH_MOC, 3, 4), substr(LAUNCH_MOC, 1, 2))"
						);
					
				List<String> list = query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
}