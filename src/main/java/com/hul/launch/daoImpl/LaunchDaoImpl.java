package com.hul.launch.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.hul.launch.dao.LaunchDao;
import com.hul.launch.dao.LaunchFinalDao;
import com.hul.launch.dao.LoginDao;
import com.hul.launch.model.LaunchClusterData;
import com.hul.launch.model.User;
import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.AcceptByTmeRequestByUpload;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.request.SaveFinalLaunchRequest;
import com.hul.launch.request.SaveLaunchSubmitRequest;
import com.hul.launch.response.CoeDocDownloadResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.launch.response.LaunchCoeClusterResponse;
import com.hul.launch.response.LaunchCoeFinalPageResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamInputsResponse;
import com.hul.launch.response.LaunchKamQueriesAnsweredResponse;
import com.hul.launch.service.LaunchFinalService;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchDaoImpl implements LaunchDao {

	@Autowired
	private SessionFactory sessionFactory;

	//kiran - added @Lazy for ciruclar dependency fix
	@Autowired
	@Lazy
	private LoginDao loginDao;

	//kiran - added @Lazy for ciruclar dependency fix
	@Autowired
	@Lazy
	LaunchFinalService launchFinalService;

	//kiran - added @Lazy for ciruclar dependency fix
	@Autowired
	@Lazy
	LaunchFinalDao launchFinalDao;

	private final static String TBL_LAUNCH_STAGE_STATUS = "INSERT INTO MODTRD.TBL_LAUNCH_STAGE_STATUS (LAUNCH_ID, LAUNCH_DETAILS, "
			+ "LAUNCH_FINAL_STATUS, CREATED_BY, CREATED_DATE) VALUES(?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_REQUEST_HISTORY = "INSERT INTO MODTRD.TBL_LAUNCH_REQUEST_HISTORY "
			+ "(REQ_ID, ACTION_TAKEN_BY, ACTION_TAKEN_ON, ACTION_TAKEN, ACTION_REMARKS) VALUES(?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_MOC_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_MOC_KAM "
			+ "(LAUNCH_ID, LAUNCH_MOC, LAUNCH_ACCOUNT, CREATED_BY, CREATED_DATE) VALUES(?, ?, ?, ?, ?) ";

	private final static String TBL_LAUNCH_SAMPLE_SHARED_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_SAMPLE_SHARED_KAM "
			+ "(LAUNCH_ID, LAUNCH_SAMPLE_SHARED, LAUNCH_ACCOUNT, CREATED_BY, CREATED_DATE) VALUES(?, ?, ?, ?, ?) ";

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
//Q1 sprint 2021 feb kavitha
	@Override
	//public List<LaunchDataResponse> getAllLaunchData(String userid) 
	public List<LaunchDataResponse> getAllLaunchData(String userId, String launchMOC,String launchName)
	{
		List<LaunchDataResponse> liLaunchData = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//kiran - translate changes
			/*stmt = sessionImpl.connection().prepareStatement(
					"SELECT tlc.LAUNCH_ID LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, "
							+ " LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, tlc.CREATED_BY CREATED_BY, "
							+ " tlc.CREATED_DATE CREATED_DATE, tlss.LAUNCH_FINAL_STATUS LAUNCH_FINAL_STATUS, "
							+ " LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss WHERE "
							+ " tlc.LAUNCH_ID = tlss.LAUNCH_ID AND DATE(TRANSLATE('GHIJ-DE-AB', LAUNCH_DATE, 'ABCDEFGHIJ')) > NOW()");*/
			
			//Kavitha Changes - Q1 Sprint Feb2021
			if (launchMOC.equalsIgnoreCase("All")) {
				launchMOC = "";
			}
			
			//Kavitha Changes - Q2 Sprint Feb2021
			if (launchName.equalsIgnoreCase("All")) {
				stmt = sessionImpl.connection().prepareStatement(
						/*"SELECT tlc.LAUNCH_ID LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, "
								+ " LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, tlc.CREATED_BY CREATED_BY, "
								+ " tlc.CREATED_DATE CREATED_DATE, tlss.LAUNCH_FINAL_STATUS LAUNCH_FINAL_STATUS, "
								+ " LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss WHERE "
								+ " tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
								+ " AND tlc.CREATED_BY = '" + userId + "' AND LAUNCH_MOC LIKE '%" + launchMOC + "%' ");*/
						
						"SELECT tlc.LAUNCH_ID AS LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2,"
						+ " LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, tlc.CREATED_BY CREATED_BY,tlc.CREATED_DATE CREATED_DATE, tlss.LAUNCH_FINAL_STATUS LAUNCH_FINAL_STATUS, LAUNCH_MOC ,"
						+ " IFNULL((SELECT GROUP_CONCAT(LAUNCH_KAM_ACCOUNT separator '; ') AS CHANGED_MOC FROM (SELECT CONCAT(LAUNCH_MOC_KAM, ' - ', GROUP_CONCAT(LAUNCH_KAM_ACCOUNT)) AS LAUNCH_KAM_ACCOUNT "
						+ " FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS KL WHERE KL.LAUNCH_ID = tlc.LAUNCH_ID AND IS_ACTIVE = 1 GROUP BY LAUNCH_MOC_KAM) A), '') CHANGED_MOC"
						+ " FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss"
						+ " WHERE tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
						+ " AND tlc.CREATED_BY = '" + userId + "' AND LAUNCH_MOC LIKE '%" + launchMOC + "%' ");
			}
			else
			{
				stmt = sessionImpl.connection().prepareStatement(
						/*"SELECT tlc.LAUNCH_ID LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, "
								+ " LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, tlc.CREATED_BY CREATED_BY, "
								+ " tlc.CREATED_DATE CREATED_DATE, tlss.LAUNCH_FINAL_STATUS LAUNCH_FINAL_STATUS, "
								+ " LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss WHERE "
								+ " tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
								+ " AND tlc.CREATED_BY = '" + userId + "' AND LAUNCH_MOC LIKE '%" + launchMOC + "%' AND LAUNCH_NAME = '" + launchName + "'");*/
				
						"SELECT tlc.LAUNCH_ID AS LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2,"
						+ " LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION, tlc.CREATED_BY CREATED_BY,tlc.CREATED_DATE CREATED_DATE, tlss.LAUNCH_FINAL_STATUS LAUNCH_FINAL_STATUS, LAUNCH_MOC ,"
						+ " IFNULL((SELECT GROUP_CONCAT(LAUNCH_KAM_ACCOUNT separator '; ') AS CHANGED_MOC FROM (SELECT CONCAT(LAUNCH_MOC_KAM, ' - ', GROUP_CONCAT(LAUNCH_KAM_ACCOUNT)) AS LAUNCH_KAM_ACCOUNT"
						+ " FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS KL WHERE KL.LAUNCH_ID = tlc.LAUNCH_ID AND IS_ACTIVE = 1 GROUP BY LAUNCH_MOC_KAM) A), '') CHANGED_MOC"
						+ " FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss"
						+ " WHERE tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
						+ " AND tlc.CREATED_BY = '" + userId + "' AND LAUNCH_MOC LIKE '%" + launchMOC + "%' AND LAUNCH_NAME = '" + launchName + "'");
			
			}

			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchDataResponse launchDataResponse = new LaunchDataResponse();
				launchDataResponse.setLaunchId(rs.getInt("LAUNCH_ID"));
				launchDataResponse.setLaunchName(rs.getString("LAUNCH_NAME"));
				launchDataResponse.setLaunchDate(rs.getString("LAUNCH_DATE"));
				launchDataResponse.setLaunchNature(rs.getString("LAUNCH_NATURE"));
				launchDataResponse.setLaunchNature2(rs.getString("LAUNCH_NATURE_2"));
				launchDataResponse.setLaunchBusinessCase(rs.getString("LAUNCH_BUSINESS_CASE"));
				launchDataResponse.setCategorySize(rs.getString("CATEGORY_SIZE"));
				launchDataResponse.setClassification(rs.getString("CLASSIFICATION"));
				launchDataResponse.setCreatedBy(rs.getString("CREATED_BY"));
				launchDataResponse.setCreatedDate(rs.getDate("CREATED_DATE"));
				launchDataResponse.setLaunchFinalStatus(rs.getString("LAUNCH_FINAL_STATUS"));
				launchDataResponse.setLaunchMoc(rs.getString("LAUNCH_MOC"));
				 launchDataResponse.setChangedMoc(rs.getString("CHANGED_MOC"));
				liLaunchData.add(launchDataResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchDataResponse launchDataResponse = new LaunchDataResponse();
			launchDataResponse.setError(ex.toString());
			liLaunchData.add(launchDataResponse);
			return liLaunchData;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return liLaunchData;
	}

	@Override
	public LaunchDataResponse getSpecificLaunchData(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		LaunchDataResponse launchDataResponse = new LaunchDataResponse();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_MOC_KAM FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " LAUNCH_ID = '" + launchId + "'");

			rs = stmt.executeQuery();
			while (rs.next()) {

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
				launchDataResponse.setCreatedBy(rs.getString(13));
				launchDataResponse.setCreatedDate(rs.getDate(14));
				launchDataResponse.setUpdatedBy(rs.getString(15));
				launchDataResponse.setUpdatedDate(rs.getDate(16));
				launchDataResponse.setLaunchMoc(rs.getString(17));
				launchDataResponse.setLaunchMocKam(rs.getString(18));
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			launchDataResponse.setError(ex.toString());
			return launchDataResponse;
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchDataResponse;
	}

	@Override
	public String updateLaunchData(LaunchDataResponse launchDataResponse, String userID) {
		int response = 0;
		String responseData = null;
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET ANNEXURE_DOCUMENT_NAME=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
			query2.setParameter(0, launchDataResponse.getAnnexureDocName());
			query2.setParameter(1, userID);
			query2.setParameter(2, new Timestamp(new Date().getTime()));
			query2.setParameter(3, launchDataResponse.getLaunchId());
			response = query2.executeUpdate();
			responseData = Integer.toString(response);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return responseData;
	}

	@Override
	public String updateArdWorkLaunchData(LaunchDataResponse launchDataResponse, String userID) {
		int response = 0;
		String responseData = null;
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET ARTWORK_PACKSHOTS_DOC_NAME=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
			query2.setParameter(0, launchDataResponse.getArtWorkPackShotsDocName());
			query2.setParameter(1, userID);
			query2.setParameter(2, new Timestamp(new Date().getTime()));
			query2.setParameter(3, launchDataResponse.getLaunchId());
			response = query2.executeUpdate();
			responseData = Integer.toString(response);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return responseData;
	}

	@Override
	public String updateMdgDocData(LaunchDataResponse launchDataResonse, String userID) {
		int response = 0;
		String responseData = null;
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET MDG_DECK_DOCUMENT_NAME=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
			query2.setParameter(0, launchDataResonse.getMdgDeckDocName());
			query2.setParameter(1, userID);
			query2.setParameter(2, new Timestamp(new Date().getTime()));
			query2.setParameter(3, launchDataResonse.getLaunchId());
			response = query2.executeUpdate();
			responseData = Integer.toString(response);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return responseData;
	}

	@SuppressWarnings("unused")
	@Override
	public Map<String, String> saveLaunchSubmit(SaveLaunchSubmitRequest saveLaunchSubmitRequest, String userId) {
		Map<String, String> mapOfSuccess = new HashMap<>();
		try {
			LaunchDataResponse launchDataResponse = getSpecificLaunchData(saveLaunchSubmitRequest.getLaunchId());
			if(saveLaunchSubmitRequest.getIsSampleShared() == 1) {
				String annexureDocName = launchDataResponse.getAnnexureDocName();
				String basePackDocName = launchDataResponse.getArtWorkPackShotsDocName();
				String mdgDeckDocName = launchDataResponse.getMdgDeckDocName();
				if (annexureDocName == null) {
					mapOfSuccess.put("Error", "Please upload Annexure Document first");
					return mapOfSuccess;
				} else if (basePackDocName == null) {
					mapOfSuccess.put("Error", "Please upload Artwork Packshots Document first");
					return mapOfSuccess;
				} else if (mdgDeckDocName == null) {
					mapOfSuccess.put("Error", "Please upload MDG Deck Document first");
					return mapOfSuccess;
				}
			}
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,LAUNCH_SUBMISSION_DATE=?1,UPDATED_BY=?2,UPDATED_DATE=?3 WHERE LAUNCH_ID=?4");  //Sarin - Added Parameters position
			query2.setParameter(0, saveLaunchSubmitRequest.getIsSampleShared());
			query2.setParameter(1, new Timestamp(new Date().getTime()));
			query2.setParameter(2, userId);
			query2.setParameter(3, new Timestamp(new Date().getTime()));
			query2.setParameter(4, saveLaunchSubmitRequest.getLaunchId());
			query2.executeUpdate();
			mapOfSuccess.put("Success", "Saved Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			mapOfSuccess.put("Error", e.toString());
			return mapOfSuccess;
		}
		return mapOfSuccess;
	}

	@Override
	public List<LaunchDataResponse> getAllCompletedLaunchData(String coeMOC) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchDataResponse> listOfCompletedLaunch = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//Q2 sprint march 2021 kavitha
			if (coeMOC.equalsIgnoreCase("All")) {
				coeMOC = "";
			}
			//kiran - translate changes
			/*stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' AND DATE(TRANSLATE('GHIJ-DE-AB', LAUNCH_DATE, 'ABCDEFGHIJ')) > NOW()");*/
			
			stmt = sessionImpl.connection().prepareStatement(
					/*"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' ");*/
					//+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()");
					
					//Kavitha Changes Q1Print1 Feb2021
					//Kavitha D Sprint4 changes
					/*"SELECT * FROM (SELECT tlc.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
                    + " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
                    + " tlc.CREATED_BY, tlc.CREATED_DATE,tlc.UPDATED_BY, tlc.UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE,"
                    + " CASE WHEN GROUP_CONCAT(DISTINCT TFC.MODIFIED_CHAIN) IS NULL THEN tlbt.CLUSTER_ACCOUNT ELSE GROUP_CONCAT(DISTINCT TFC.MODIFIED_CHAIN) END AS ACCOUNT_NAME "  
                    + " FROM TBL_LAUNCH_MASTER tlc "
                    + " LEFT OUTER JOIN TBL_LAUNCH_CLUSTERS tlbt ON tlbt.CLUSTER_LAUNCH_ID = tlc.LAUNCH_ID LEFT OUTER JOIN TBL_LAUNCH_TEMP_FINAL_CAL TFC ON TFC.LAUNCH_ID = tlc.LAUNCH_ID"
                    + " LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TFC.LAUNCH_ID AND TLK.LAUNCH_KAM_ACCOUNT = TFC.MODIFIED_CHAIN AND TLK.IS_ACTIVE = 1"
                    + " WHERE (SAMPLE_SHARED IS NOT NULL AND SAMPLE_SHARED <> '') AND LAUNCH_REJECTED != '2' AND TLK.LAUNCH_ID IS NULL AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()"
                    + " GROUP BY tlc.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,tlc.CREATED_BY, tlc.CREATED_DATE,tlc.UPDATED_BY, tlc.UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE, tlbt.CLUSTER_ACCOUNT"
					+ " UNION ALL SELECT TLM.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,"
					+ " ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED, TLM.CREATED_BY, TLM.CREATED_DATE, TLM.UPDATED_BY, TLM.UPDATED_DATE,"
					+ " TLK.LAUNCH_MOC_KAM AS LAUNCH_MOC, LAUNCH_SUBMISSION_DATE, GROUP_CONCAT(DISTINCT TLK.LAUNCH_KAM_ACCOUNT) AS ACCOUNT_NAME"
					+ " FROM TBL_LAUNCH_MASTER TLM INNER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TLM.LAUNCH_ID" 
					+ " WHERE (TLM.SAMPLE_SHARED IS NOT NULL AND TLM.SAMPLE_SHARED <> '') AND TLM.LAUNCH_REJECTED != '2' AND TLK.IS_ACTIVE = 1 AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()" 
					+ " GROUP BY TLM.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED, TLM.CREATED_BY, TLM.CREATED_DATE, TLM.UPDATED_BY, TLM.UPDATED_DATE, TLK.LAUNCH_MOC_KAM, LAUNCH_SUBMISSION_DATE"
					+ " ) A WHERE LAUNCH_MOC LIKE '%" + coeMOC + "%'  ORDER BY LAUNCH_ID");*/
					"SELECT * FROM (SELECT tlc.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
                    + " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
                    + " tlc.CREATED_BY, tlc.CREATED_DATE,tlc.UPDATED_BY, tlc.UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE,"
                    + " CASE WHEN GROUP_CONCAT(DISTINCT TFC.MODIFIED_CHAIN) IS NULL THEN tlbt.CLUSTER_ACCOUNT ELSE GROUP_CONCAT(DISTINCT TFC.MODIFIED_CHAIN) END AS ACCOUNT_NAME,LAUNCH_MOC AS ORIGINAL_LAUNCH_MOC "  
                    + " FROM TBL_LAUNCH_MASTER tlc "
                    + " LEFT OUTER JOIN TBL_LAUNCH_CLUSTERS tlbt ON tlbt.CLUSTER_LAUNCH_ID = tlc.LAUNCH_ID LEFT OUTER JOIN TBL_LAUNCH_TEMP_FINAL_CAL TFC ON TFC.LAUNCH_ID = tlc.LAUNCH_ID"
                    + " LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TFC.LAUNCH_ID AND TLK.LAUNCH_KAM_ACCOUNT = TFC.MODIFIED_CHAIN AND TLK.IS_ACTIVE = 1"
                    + " WHERE (SAMPLE_SHARED IS NOT NULL AND SAMPLE_SHARED <> '') AND LAUNCH_REJECTED != '2' AND TLK.LAUNCH_ID IS NULL AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()"
                    + " GROUP BY tlc.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,tlc.CREATED_BY, tlc.CREATED_DATE,tlc.UPDATED_BY, tlc.UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE, tlbt.CLUSTER_ACCOUNT"
					+ " UNION ALL SELECT TLM.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,"
					+ " ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED, TLM.CREATED_BY, TLM.CREATED_DATE, TLM.UPDATED_BY, TLM.UPDATED_DATE,"
					+ " TLK.LAUNCH_MOC_KAM AS LAUNCH_MOC, LAUNCH_SUBMISSION_DATE, GROUP_CONCAT(DISTINCT TLK.LAUNCH_KAM_ACCOUNT) AS ACCOUNT_NAME,LAUNCH_MOC AS ORIGINAL_LAUNCH_MOC"
					+ " FROM TBL_LAUNCH_MASTER TLM INNER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TLM.LAUNCH_ID" 
					+ " WHERE (TLM.SAMPLE_SHARED IS NOT NULL AND TLM.SAMPLE_SHARED <> '') AND TLM.LAUNCH_REJECTED != '2' AND TLK.IS_ACTIVE = 1 AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()" 
					+ " GROUP BY TLM.LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE, CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED, TLM.CREATED_BY, TLM.CREATED_DATE, TLM.UPDATED_BY, TLM.UPDATED_DATE, TLK.LAUNCH_MOC_KAM, LAUNCH_SUBMISSION_DATE"
					+ " ) A WHERE LAUNCH_MOC LIKE '%" + coeMOC + "%'  ORDER BY LAUNCH_ID");

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
				String launchAccounts = "";
				if (rs.getString(19).contains(":")) {
					String launchAcc[] = rs.getString(19).split(",");
					for (int i = 0; i < launchAcc.length; i++) {
						String[] acc = launchAcc[i].split(":");
						if (launchAccounts.equalsIgnoreCase("")) {
							launchAccounts = acc[0];
						} else {
							launchAccounts = launchAccounts + "," + acc[0];
						}
					}
				} else {
					launchAccounts = rs.getString(19);
				}
				launchDataResponse.setAccountName(launchAccounts);
				//launchDataResponse.setAccountName(rs.getString(19));
				launchDataResponse.setOriginalLaunchMoc(rs.getString(20));
				
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
	public List<LaunchCoeBasePackResponse> getAllCompletedLaunchData(List<String> listOfLaunchData) {
		List<LaunchCoeBasePackResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData) "
							+ "AND (tlb.BP_STATUS != 'REJECTED BY TME' OR tlb.BP_STATUS IS NULL)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchCoeBasePackResponse launchCoeBasePackResponse = new LaunchCoeBasePackResponse();
				launchCoeBasePackResponse.setLaunchName(obj[0].toString());
				launchCoeBasePackResponse.setSalesCat(obj[1].toString());
				launchCoeBasePackResponse.setPsaCat(obj[2].toString());
				launchCoeBasePackResponse.setBrand(obj[3].toString());
				launchCoeBasePackResponse.setBpCode(obj[4].toString());
				launchCoeBasePackResponse.setBpDisc(obj[5].toString());
				launchCoeBasePackResponse.setMrp(obj[6].toString());
				launchCoeBasePackResponse.setTur(obj[7].toString());
				launchCoeBasePackResponse.setGsv(obj[8].toString());
				launchCoeBasePackResponse.setCldConfig(obj[9].toString());
				launchCoeBasePackResponse.setGrammage(obj[10].toString());
				launchCoeBasePackResponse.setClassification(obj[11].toString());
				listOfCompletedLaunch.add(launchCoeBasePackResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchCoeBasePackResponse launchDataResponse = new LaunchCoeBasePackResponse();
			launchDataResponse.setError(ex.toString());
			listOfCompletedLaunch.add(launchDataResponse);
			return listOfCompletedLaunch;
		}
		return listOfCompletedLaunch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchCoeFinalPageResponse> getAllCompletedLaunchFinalData(List<String> listOfLaunchData) {
		List<LaunchCoeFinalPageResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME, tlfb.BUILDUP_SKU_NAME, tlfb.BUILDUP_BASEPACK_CODE, tlm.LAUNCH_MOC, "
							+ " tlfb.BUILDUP_LAUNCH_SELLIN_VALUE, tlfb.BUILDUP_LAUNCH_SELLIN_N1, tlfb.BUILDUP_LAUNCH_SELLIN_N2,"
							+ " tlfb.BUILDUP_LAUNCH_SELLIN_CLDS, tlfb.BUILDUP_LAUNCH_SELLIN_CLDS_N1, tlfb.BUILDUP_LAUNCH_SELLIN_CLDS_N2,"
							+ " tlfb.BUILDUP_LAUNCH_SELLIN_UNITS, tlfb.BUILDUP_LAUNCH_SELLIN_UNITS_N1, tlfb.BUILDUP_LAUNCH_SELLIN_UNITS_N2"
							+ " FROM TBL_LAUNCH_FINAL_BUILDUP tlfb, TBL_LAUNCH_MASTER tlm WHERE tlfb.BUILDUP_LAUNCH_ID = tlm.LAUNCH_ID "
							+ " AND tlm.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchCoeFinalPageResponse launchCoeFinalPageResponse = new LaunchCoeFinalPageResponse();
				launchCoeFinalPageResponse.setLaunchName(obj[0].toString());
				launchCoeFinalPageResponse.setSkuName(obj[1].toString());
				launchCoeFinalPageResponse.setBasepackCode(obj[2].toString());
				launchCoeFinalPageResponse.setMoc(obj[3].toString());
				launchCoeFinalPageResponse.setSellInValN(obj[4].toString());
				launchCoeFinalPageResponse.setSellInValN1(obj[5].toString());
				launchCoeFinalPageResponse.setSellInValN2(obj[6].toString());
				launchCoeFinalPageResponse.setSellInCldN(obj[7].toString());
				launchCoeFinalPageResponse.setSellInCldN1(obj[8].toString());
				launchCoeFinalPageResponse.setSellInCldN2(obj[9].toString());
				launchCoeFinalPageResponse.setSellInUnitN(obj[10].toString());
				launchCoeFinalPageResponse.setSellInUnitN1(obj[11].toString());
				launchCoeFinalPageResponse.setSellInUnitN2(obj[12].toString());
				listOfCompletedLaunch.add(launchCoeFinalPageResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchCoeFinalPageResponse launchDataResponse = new LaunchCoeFinalPageResponse();
			launchDataResponse.setError(ex.toString());
			listOfCompletedLaunch.add(launchDataResponse);
			return listOfCompletedLaunch;
		}
		return listOfCompletedLaunch;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchCoeClusterResponse> getAllCompletedListingTracker(List<String> listOfLaunchData) {
		List<LaunchCoeClusterResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT tlm.LAUNCH_NAME, tlm.LAUNCH_MOC, tlbt.SKU_NAME,	tlbt.BASEPACK_CODE,	tlbt.ACCOUNT_NAME, "
							+ "tlbt.CLUSTER, tlm.LAUNCH_ID FROM TBL_LAUNCH_BUILDUP_TEMP tlbt, TBL_LAUNCH_MASTER tlm WHERE "
							+ "tlbt.LAUNCH_ID = tlm.LAUNCH_ID AND tlbt.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
			
				Object[] obj1 = (Object[]) iterator.next();
				LaunchCoeClusterResponse launchCoeClusterResponse = new LaunchCoeClusterResponse();
				launchCoeClusterResponse.setLaunchName(obj1[0].toString());
				String mocDate = getModifiedMoc( obj1[6].toString(),  obj1[4].toString());
				if(mocDate!=null && !mocDate.isEmpty()) {
					launchCoeClusterResponse.setLaunchMoc(mocDate);
					
				}
				else {
					launchCoeClusterResponse.setLaunchMoc(obj1[1].toString());
				}
				launchCoeClusterResponse.setSkuName(obj1[2].toString());
				launchCoeClusterResponse.setBasepackCode(obj1[3].toString());
				launchCoeClusterResponse.setAccount(obj1[4].toString());
				launchCoeClusterResponse.setCluster(obj1[5].toString());
				listOfCompletedLaunch.add(launchCoeClusterResponse);
			}

		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchCoeClusterResponse launchCoeClusterResponse = new LaunchCoeClusterResponse();
			launchCoeClusterResponse.setError(ex.toString());
			listOfCompletedLaunch.add(launchCoeClusterResponse);
			return listOfCompletedLaunch;
		}
		return listOfCompletedLaunch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoeDocDownloadResponse> getCoeDocDownloadUrl(List<String> listOfLaunchData) {
		List<CoeDocDownloadResponse> listLaunchMstnClearanceResponse = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,LAUNCH_NAME FROM TBL_LAUNCH_MASTER tlm"
							+ " WHERE LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				CoeDocDownloadResponse coeDocDownloadResponse = new CoeDocDownloadResponse();
				coeDocDownloadResponse.setAnnexureFileName(obj[0].toString());
				coeDocDownloadResponse.setArtworkPackshotsFileName(obj[1].toString());
				coeDocDownloadResponse.setMdgDeckFileName(obj[2].toString());
				coeDocDownloadResponse.setLaunchName(obj[3].toString());
				listLaunchMstnClearanceResponse.add(coeDocDownloadResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CoeDocDownloadResponse coeDocDownloadResponse = new CoeDocDownloadResponse();
			coeDocDownloadResponse.setError(e.toString());
		}
		return listLaunchMstnClearanceResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArrayList<String>> getbasepackDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData) "
							+ " AND (tlb.BP_STATUS != 'REJECTED BY TME' OR tlb.BP_STATUS IS NULL)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> itr = query2.list().iterator();
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
			ArrayList<String> arrList = new ArrayList<>();
			arrList.add(ex.toString());
			downloadDataList.add(arrList);
			return downloadDataList;
		}
	}

	@Override
	public List<ArrayList<String>> getBuildUpDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			Iterator<Object> itr = new ArrayList<>().iterator();
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
			ArrayList<String> arrList = new ArrayList<>();
			arrList.add(ex.toString());
			downloadDataList.add(arrList);
			return downloadDataList;
		}
	}

	@Override
	public List<ArrayList<String>> getListingTrackerDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchCoeClusterResponse> listLaunchCoeClusterResponse = getListingTrackerDump(listOfLaunchData);
		downloadedData.add(headerDetail);
		for (LaunchCoeClusterResponse launchCoeClusterResponse : listLaunchCoeClusterResponse) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchCoeClusterResponse.getLaunchName());
			dataObj.add(launchCoeClusterResponse.getLaunchMoc());
			dataObj.add(launchCoeClusterResponse.getSkuName());
			dataObj.add(launchCoeClusterResponse.getBasepackCode());
			dataObj.add(launchCoeClusterResponse.getAccount());
			dataObj.add(launchCoeClusterResponse.getCluster());
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}
	
	// Harsha's Method to fetch MOC Date
	 public String getModifiedMoc(String launchId, String Account) {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			String launchMoc = "";
			try {
				PreparedStatement stmt = sessionImpl.connection()
						.prepareStatement("SELECT LAUNCH_MOC_KAM FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS"
								+ " WHERE IS_ACTIVE = 1 AND  LAUNCH_KAM_ACCOUNT= '" 
								+ Account + "'"  
						+ " AND LAUNCH_ID = '" + launchId + "'");		
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					launchMoc = rs.getString(1);
				}
				return launchMoc;
			} catch (Exception ex) {
				logger.debug("Exception :", ex);
				return ex.toString();
			}
		}
	
	
// Working on Q5 Story
	@SuppressWarnings("unchecked")
	private List<LaunchCoeClusterResponse> getListingTrackerDump(List<String> listOfLaunchData) {
		List<LaunchCoeClusterResponse> liReturn = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT distinct tlm.LAUNCH_NAME, tlm.LAUNCH_MOC, tlbt.SKU_NAME,tlbt.BASEPACK_CODE,	tlbt.ACCOUNT_NAME, "
							+ "tlbt.CLUSTER, tlm.LAUNCH_ID FROM TBL_LAUNCH_BUILDUP_TEMP tlbt, TBL_LAUNCH_MASTER tlm WHERE "
							+ "tlbt.LAUNCH_ID = tlm.LAUNCH_ID AND tlbt.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			int[] temp = { 1 };
			while (iterator.hasNext()) {
				Object[] obj1 = (Object[]) iterator.next();
				LaunchCoeClusterResponse launchCoeClusterResponse = new LaunchCoeClusterResponse();
				launchCoeClusterResponse.setLaunchName(obj1[0].toString());
				//Harsh's Modification for fetching Changed MOC for a launch
				String getmodifiedMoc = getModifiedMoc(obj1[6].toString(),obj1[4].toString()) ;
				if( getmodifiedMoc != null && !getmodifiedMoc.isEmpty()) {
					launchCoeClusterResponse.setLaunchMoc(getmodifiedMoc);
				}
				
				else {
					launchCoeClusterResponse.setLaunchMoc(obj1[1].toString());
				}	
				// Code ends here
				launchCoeClusterResponse.setBasepackCode(obj1[3].toString());
				launchCoeClusterResponse.setSkuName(obj1[2].toString());
				launchCoeClusterResponse.setCluster(obj1[4].toString());
				launchCoeClusterResponse.setAccount(obj1[5].toString());
				liReturn.add(launchCoeClusterResponse);
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchCoeClusterResponse launchBuildUpTemp = new LaunchCoeClusterResponse();
			launchBuildUpTemp.setError(e.toString());
			liReturn.add(launchBuildUpTemp);
		}
		return liReturn;
	}

	@Override
	public List<ArrayList<String>> getMSTNClearanceDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			Iterator<Object> itr = new ArrayList<>().iterator();
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
			ArrayList<String> arrList = new ArrayList<>();
			arrList.add(ex.toString());
			downloadDataList.add(arrList);
			return downloadDataList;
		}
	}
	


	// Existing implementation- work 2
	@Override
	@SuppressWarnings("unchecked")
	public List<ArrayList<String>> getLaunchStoreListDump(ArrayList<String> headerDetail, String userId,
			List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			
			
			/*  Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
                    "select DISTINCT LAUNCH_name,tlb.BP_CODE,tlb.BP_DESCRIPTION,launch_moc,abc.ACCOUNT_NAME ACCOUNT_NAME,ACCOUNT_NAME_L2,abc.depot,HUL_STORE_FORMAT,CLUSTER,REPORTING_CODE,(CASE WHEN tvcom.FMCG_CSP_CODE IS NOT NULL" +
                    "THEN tvcom.FMCG_CSP_CODE ELSE COLOUR_CSP_CODE END) AS DEPO_CODE" +
                    "from modtrd.tbl_launch_buildup_temp abc,TBL_VAT_COMM_OUTLET_MASTER tvcom,modtrd.TBL_LAUNCH_MASTER asd,TBL_LAUNCH_BASEPACK tlb WHERE " +
                    "tvcom.HUL_OUTLET_CODE = abc.HFS_CODE AND abc.LAUNCH_ID = asd.launch_id and  LAUNCH_REJECTED !='2' and asd.LAUNCH_ID = tlb.LAUNCH_ID" +
                    "AND (tlb.BP_STATUS != 'REJECTED BY TME' OR tlb.BP_STATUS IS NULL) and tlb.LAUNCH_ID IN (:listOfLaunchData)");
			 */
			downloadDataList.add(headerDetail);
			for(String launchId : listOfLaunchData) {
				
				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"select DISTINCT LAUNCH_name,tlb.BP_CODE,tlb.BP_DESCRIPTION,launch_moc,ACCOUNT_NAME_L2,abc.ACCOUNT_NAME ACCOUNT_NAME,abc.depot,HUL_STORE_FORMAT,CLUSTER,REPORTING_CODE,(CASE WHEN tvcom.FMCG_CSP_CODE IS NOT NULL"
								+" THEN tvcom.FMCG_CSP_CODE ELSE COLOUR_CSP_CODE END) AS DEPO_CODE"
								+" from MODTRD.TBL_LAUNCH_BUILDUP_TEMP abc,TBL_VAT_COMM_OUTLET_MASTER tvcom,MODTRD.TBL_LAUNCH_MASTER asd,TBL_LAUNCH_BASEPACK tlb WHERE "
								+" tvcom.HUL_OUTLET_CODE = abc.HFS_CODE AND abc.LAUNCH_ID = asd.launch_id and  LAUNCH_REJECTED !='2' and asd.LAUNCH_ID = tlb.LAUNCH_ID"
								+" AND (tlb.BP_STATUS != 'REJECTED BY TME' OR tlb.BP_STATUS IS NULL) and tlb.LAUNCH_ID IN (:listOfLaunchData)");

				query2.setParameter("listOfLaunchData", launchId);
				
				Iterator<Object> itr = query2.list().iterator();
				
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					ArrayList<String> dataObj = new ArrayList<String>();
					for (Object ob : obj) {
						String value = "";
						value = (ob == null) ? "" : ob.toString();
						dataObj.add(value.replaceAll("\\^", ","));
					}
					// Harsha'S logic Starts Here for Sprint Q5
							String modifiedMOC = getModifiedMoc(launchId,  dataObj.get(5));
							if(modifiedMOC!=null && !modifiedMOC.isEmpty()) {
								String replaceDate = modifiedMOC;
								dataObj.set(3, replaceDate);
							}
							else {
								dataObj.set(3, dataObj.get(3));
							}

					obj = null;
					downloadDataList.add(dataObj);
				}	
			}
			return downloadDataList;

		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			ArrayList<String> arrList = new ArrayList<>();
			arrList.add(ex.toString());
			downloadDataList.add(arrList);
			return downloadDataList;
		}
	}

	@Override
	public String saveLaunchStatus(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int launchStatusId = 0;
		ResultSet rs = null;
		PreparedStatement batchUpdate0 = null;
		try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_STAGE_STATUS,
				Statement.RETURN_GENERATED_KEYS)) {

			batchUpdate0 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_STAGE_STATUS where LAUNCH_ID=?");
			batchUpdate0.setString(1, launchId);
			batchUpdate0.executeUpdate();

			preparedStatement.setString(1, launchId);
			preparedStatement.setInt(2, 1);
			preparedStatement.setString(3, "LAUNCH_DETAILS");
			preparedStatement.setString(4, userId);
			preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs != null && rs.next()) {
				launchStatusId = rs.getInt(1);
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
		return Integer.toString(launchStatusId);
	}

	@Override
	public String updateLaunchStatus(String launchId, String userId, String pageName) {
		int updateStatus = 0;
		try {
			Query query2 = null;
			if (pageName.equals("LAUNCH_BASEPACKS")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_BASEPACKS='1',LAUNCH_FINAL_STATUS='LAUNCH_BASEPACKS',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			} else if (pageName.equals("LAUNCH_STORES")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_STORES='1',LAUNCH_FINAL_STATUS='LAUNCH_STORES',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			} else if (pageName.equals("LAUNCH_SELL_IN")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_SELL_IN='1',LAUNCH_FINAL_STATUS='LAUNCH_SELL_IN',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			} else if (pageName.equals("LAUNCH_VISI_PLANNING")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_VISI_PLANNING='1',LAUNCH_FINAL_STATUS='LAUNCH_VISI_PLANNING',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			} else if (pageName.equals("LAUNCH_FINAL_BUILDUP")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_FINAL_BUILDUP='1',LAUNCH_FINAL_STATUS='LAUNCH_FINAL_BUILDUP',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			} else if (pageName.equals("LAUNCH_SUBMIT")) {
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_STAGE_STATUS SET LAUNCH_SUBMIT='1',LAUNCH_FINAL_STATUS='LAUNCH_SUBMIT',UPDATED_BY='"
								+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + launchId + "'");
				updateStatus = query2.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.toString(updateStatus);
	}

	@Override
	public String getLaunchStatus(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String launchStageStatus = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_FINAL_STATUS FROM TBL_LAUNCH_STAGE_STATUS WHERE LAUNCH_ID = " + launchId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchStageStatus = rs.getString("LAUNCH_FINAL_STATUS");
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				return e.toString();
			}
		}
		return launchStageStatus;
	}

	@Override
	public LaunchClusterData getClusterDataByLaunchId(String launchId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LaunchClusterData launchClusterData = new LaunchClusterData();
		List<String> lstRejectedAccounts = null;
		try {
			lstRejectedAccounts = getKamRejectedLaunchAccounts(launchId);  //Sprint4Aug2021 changes - starts
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT CLUSTER_REGION,CLUSTER_ACCOUNT,CLUSTER_STORE_FORMAT,CLUSTER_CUST_STORE_FORMAT,TOTAL_STORES_TO_LAUNCH FROM TBL_LAUNCH_CLUSTERS tlc WHERE LAUNCH_PLANNED = 'Yes' and CLUSTER_LAUNCH_ID = "
							+ launchId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchClusterData.setCluster(rs.getString("CLUSTER_REGION"));
				//Sprint4Aug2021 changes - starts
				String launchClusterAccount = "";
				if ((lstRejectedAccounts != null) && (!lstRejectedAccounts.isEmpty())) {
					launchClusterAccount = rs.getString("CLUSTER_ACCOUNT");
					for (String rejectAccts: lstRejectedAccounts) {
						launchClusterAccount = launchClusterAccount.replaceAll(rejectAccts, "");
					}
					launchClusterAccount = launchClusterAccount.replaceAll(",,", ",");
					if (launchClusterAccount.endsWith(",")) {
						launchClusterAccount = launchClusterAccount.substring(0, launchClusterAccount.length() - 1);
					}
					launchClusterData.setAccount_string(launchClusterAccount);
				} else
				//Sprint4Aug2021 changes - Ends
					launchClusterData.setAccount_string(rs.getString("CLUSTER_ACCOUNT"));
				launchClusterData.setStore_Format(rs.getString("CLUSTER_STORE_FORMAT"));
				launchClusterData.setCustomer_Store_Format(rs.getString("CLUSTER_CUST_STORE_FORMAT"));
				launchClusterData.setTotalStoresToLaunch(rs.getString("TOTAL_STORES_TO_LAUNCH"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchClusterData.setError(e.toString());
		}
		return launchClusterData;
	}
	
	//Sprint4Aug2021 changes
	public List<String> getKamRejectedLaunchAccounts(String launchId) {
		List<String> lstKamRejectedAccounts = new ArrayList<String>();
		try {
			Query qryKamRejectAccts = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT CONCAT(OM.ACCOUNT_NAME , ':' , OM.DP_CHAIN) AS ACCOUNTCHAIN FROM TBL_VAT_COMM_OUTLET_MASTER OM INNER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS KM ON KM.LAUNCH_KAM_ACCOUNT = OM.ACCOUNT_NAME WHERE KM.IS_ACTIVE = 2 AND KM.LAUNCH_ID = '" + launchId + "' ");
			lstKamRejectedAccounts =  qryKamRejectAccts.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstKamRejectedAccounts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchKamInputsResponse> getLaunchKamInputs(String userId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<LaunchKamInputsResponse> launchKamInputsResponses = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT REQ_ID,tlm.LAUNCH_ID,tlm.LAUNCH_NAME LAUNCH_NAME,tlm.LAUNCH_MOC LAUNCH_MOC,"
							+ "CHANGES_REQUIRED,KAM_REMARKS,REQ_DATE,REQ_TYPE,tlr.CREATED_BY FROM TBL_LAUNCH_REQUEST tlr, TBL_LAUNCH_MASTER tlm WHERE "
							+ " tlm.LAUNCH_ID = tlr.LAUNCH_ID AND FINAL_STATUS = 'PENDING' AND tlm.CREATED_BY ='"
							+ userId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchKamInputsResponse launchKamInputsResponse = new LaunchKamInputsResponse();
				launchKamInputsResponse.setReqId(rs.getString("REQ_ID"));
				launchKamInputsResponse.setLaunchId(rs.getString("LAUNCH_ID"));
				launchKamInputsResponse.setLaunchName(rs.getString("LAUNCH_NAME"));
				launchKamInputsResponse.setLaunchMoc(rs.getString("LAUNCH_MOC"));
				String createdby = rs.getString("CREATED_BY");
				String RequestId= rs.getString("REQ_ID");
				
				String kamMailId = rs.getString("CREATED_BY").concat("@unilever.com").toUpperCase();
				//Query modified by Harsha for Q4 Sprint LAUNH REJECTED && MOC CHANGED
				Query query2;
				if(rs.getString("CHANGES_REQUIRED").equals("LAUNCH REJECTED")) {
					query2 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
							+ "WHERE IS_ACTIVE = 2 AND LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "'" 
									+"AND UPDATED_BY = '" + createdby + "'" +
									"AND REQ_ID = '" + RequestId + "'");
				}
				
				else if(rs.getString("CHANGES_REQUIRED").equals("MOC CHANGED")) {
					query2 = sessionFactory.getCurrentSession().createNativeQuery(
							"SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
							//+ "WHERE IS_ACTIVE = 1 AND LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "'" 
							+ "WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "'"
									+"AND UPDATED_BY = '" + createdby + "'" +
									"AND REQ_ID = '" + RequestId + "'");
				}//Harsha's Implementation ends here for MOC CHANGED .
				else{
				query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT ACCOUNT_NAME FROM MODTRD.TBL_VAT_COMM_OUTLET_MASTER WHERE UPPER(KAM_MAIL_ID) = '"
								+ kamMailId + "'");
				}
				
				String listOfAccounts = String.join(",", query2.list());
				launchKamInputsResponse.setAccount(listOfAccounts);
				launchKamInputsResponse.setName(rs.getString("CREATED_BY"));
				launchKamInputsResponse.setChangesRequired(rs.getString("CHANGES_REQUIRED"));
				launchKamInputsResponse.setKamRemarks(rs.getString("KAM_REMARKS"));
				launchKamInputsResponse.setRequestDate(rs.getString("REQ_DATE"));
				launchKamInputsResponse.setRequestStatus(rs.getString("REQ_TYPE"));
				launchKamInputsResponses.add(launchKamInputsResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchKamInputsResponse launchKamInputsResponse = new LaunchKamInputsResponse();
			launchKamInputsResponse.setError(e.toString());
		}
		return launchKamInputsResponses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String rejectKamInputs(RejectByTmeRequest rejectByTme, String userId) {
		PreparedStatement preparedStatement = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement batchUpdate0 = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			String[] reqIds = rejectByTme.getReqIds().split(",");

			for (int i = 0; i < reqIds.length; i++) {
				preparedStatement = sessionImpl.connection().prepareStatement(
						"SELECT REQ_ID,LAUNCH_ID,CHANGES_REQUIRED,REJECT_IDS,CREATED_BY FROM TBL_LAUNCH_REQUEST WHERE REQ_ID = '"
								+ reqIds[i] + "'");
				rs = preparedStatement.executeQuery();

				while (rs.next()) {
					if (rs.getString("CHANGES_REQUIRED").equals("LAUNCH REJECTED")) {
						Query query2 = sessionFactory.getCurrentSession()
								.createNativeQuery("DELETE from TBL_LAUNCH_STATUS_KAM where LAUNCH_ID='"
										+ rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT ='"
										+ rs.getString("CREATED_BY") + "'");
						query2.executeUpdate();
						// Harsha's Implementation for Reject by TME Sprint Q4
						Query query10 = sessionFactory.getCurrentSession()
								.createNativeQuery("SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
								+ " WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "' AND REQ_ID ='"
										+ rs.getString("REQ_ID") +
								"'" );
						
						List<String> nameOfAccounts =  query10.list();
						if(nameOfAccounts!=null && !nameOfAccounts.isEmpty()) {
							
						Query query3 = sessionFactory.getCurrentSession()
								.createNativeQuery("DELETE from TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS where LAUNCH_ID='"
										+ rs.getString("LAUNCH_ID") + "' AND REQ_ID ='"
										+ rs.getString("REQ_ID") + "'");
						query3.executeUpdate();
						}
						// Harsha's code ends here End of if loop 

					} 
					// Harsha's Code for rejecting Chnage in MOC start here Q4 Sprint
					
					else if (rs.getString("CHANGES_REQUIRED").equals("MOC CHANGED")) {
						
						Query query1 = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
								+ " WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "' AND REQ_ID = '" + rs.getString("REQ_ID") + "'" );
						List<String> nameOfAccounts =  query1.list();
						if(nameOfAccounts!=null && !nameOfAccounts.isEmpty()) {
								Query query2 = sessionFactory.getCurrentSession()
										.createNativeQuery("UPDATE MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE='0' '"
												+ "' WHERE IS_ACTIVE = '1' AND LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "' AND REQ_ID = '"
												+ rs.getString("REQ_ID") + "'");
								query2.executeUpdate();
					}
					}
					// Harsha's Code for rejecting Chnage in MOC end's here
					
					else if (rs.getString("CHANGES_REQUIRED").equals("BASEPACK REJECTED")) {
						try {
							String[] bpIds = rs.getString("REJECT_IDS").split(",");

							stmt = sessionImpl.connection().prepareStatement(
									"SELECT LAUNCH_BASEPACK FROM TBL_LAUNCH_BASEPACK_KAM WHERE LAUNCH_ID = '"
											+ rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '"
											+ rs.getString("CREATED_BY") + "'");
							rs1 = stmt.executeQuery();
							if (rs1.next()) {
								List<String> rejectedBps = Arrays.asList(rs1.getString("LAUNCH_BASEPACK").split(","));
								List<String> toSubstract = Arrays.asList(bpIds);
								List<String> finalStr = ListUtils.subtract(rejectedBps, toSubstract);

								String citiesCommaSeparated = String.join(",", finalStr);
								if (citiesCommaSeparated.isEmpty()) {
									try {
										batchUpdate0 = sessionImpl.connection().prepareStatement(
												"DELETE from TBL_LAUNCH_BASEPACK_KAM where LAUNCH_ID=? AND LAUNCH_ACCOUNT = ?");
										batchUpdate0.setString(1, rs.getString("LAUNCH_ID"));
										batchUpdate0.setString(2, rs.getString("CREATED_BY"));
										batchUpdate0.executeUpdate();
									} catch (Exception e1) {
										return e1.toString();
									} finally {
										batchUpdate0.close();
									}
								} else {
									Query query2 = sessionFactory.getCurrentSession()
											.createNativeQuery("UPDATE TBL_LAUNCH_BASEPACK_KAM SET LAUNCH_BASEPACK='"
													+ citiesCommaSeparated + "',UPDATED_BY='" + userId
													+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
													+ "' WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID")
													+ "' AND LAUNCH_ACCOUNT = '" + rs.getString("CREATED_BY") + "'");
									query2.executeUpdate();
								}
							}
						} catch (Exception e) {
							return e.toString();
						} finally {
							stmt.close();
							rs1.close();
						}
					} else if (rs.getString("CHANGES_REQUIRED").equals("STORE REJECTED")) {
						try {
							String[] storeIds = rs.getString("REJECT_IDS").split(",");

							stmt = sessionImpl.connection().prepareStatement(
									"SELECT LAUNCH_STORES FROM TBL_LAUNCH_STORE_KAM WHERE LAUNCH_ID = '"
											+ rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '"
											+ rs.getString("CREATED_BY") + "'");
							rs1 = stmt.executeQuery();
							if (rs1.next()) {
								List<String> rejectedStores = Arrays.asList(rs1.getString("LAUNCH_STORES").split(","));
								List<String> toSubstract = Arrays.asList(storeIds);
								List<String> finalStr = ListUtils.subtract(rejectedStores, toSubstract);

								String storesCommaSeparated = String.join(",", finalStr);
								if (storesCommaSeparated.isEmpty()) {
									try {
										batchUpdate0 = sessionImpl.connection().prepareStatement(
												"DELETE from TBL_LAUNCH_STORE_KAM where LAUNCH_ID=? AND LAUNCH_ACCOUNT = ?");
										batchUpdate0.setString(1, rs.getString("LAUNCH_ID"));
										batchUpdate0.setString(2, rs.getString("CREATED_BY"));
										batchUpdate0.executeUpdate();
									} catch (Exception e1) {
										return e1.toString();
									} finally {
										batchUpdate0.close();
									}
								} else {
									Query query2 = sessionFactory.getCurrentSession()
											.createNativeQuery("UPDATE TBL_LAUNCH_STORE_KAM SET LAUNCH_STORES='"
													+ storesCommaSeparated + "',UPDATED_BY='" + userId
													+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
													+ "' WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID")
													+ "' AND LAUNCH_ACCOUNT = '" + rs.getString("CREATED_BY") + "'");
									query2.executeUpdate();
								}
							}
						} catch (Exception e) {
							return e.toString();
						} finally {
							stmt.close();
							rs1.close();
						}
					}
				}

				Query query2 = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_LAUNCH_REQUEST SET REQ_TYPE='REJECTED BY TME',UPDATED_BY='" + userId
								+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "',FINAL_STATUS = 'REJECTED',TME_REMARKS = '" + rejectByTme.getRejectRemark()
								+ "' WHERE REQ_ID='" + reqIds[i] + "'");
				query2.executeUpdate();

				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setString(1, reqIds[i]);
					preparedStatementInside.setString(2, userId);
					preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
					preparedStatementInside.setString(4, "REJECTED BY TME");
					preparedStatementInside.setString(5, rejectByTme.getRejectRemark());
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				preparedStatement.close();
				rs.close();
			} catch (Exception e) {
				return e.toString();
			}
		}
		return "Rejected Successfully";
	}

	@Override
	public String acceptKamInputs(AcceptByTmeRequest acceptByTme, String userId) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		PreparedStatement preparedStatement1 = null;
		ResultSet rs1 = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			String[] reqIds = acceptByTme.getReqIds().split(",");
			for (int i = 0; i < reqIds.length; i++) {
				preparedStatement = sessionImpl.connection().prepareStatement( //Harsha added REQ_ID
						"SELECT REQ_ID,LAUNCH_ID,CHANGES_REQUIRED,REJECT_IDS,CREATED_BY FROM TBL_LAUNCH_REQUEST WHERE REQ_ID = '"
								+ reqIds[i] + "'");
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					if (rs.getString("CHANGES_REQUIRED").equals("LAUNCH REJECTED")) {
						Query query2 = sessionFactory.getCurrentSession()
								.createNativeQuery("UPDATE TBL_LAUNCH_STATUS_KAM SET LAUNCH_STATUS='2',UPDATED_BY='"
										+ userId + "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
										+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '"
										+ userId + "'");
						query2.executeUpdate();
						
						//Harsha's Modification for getting updated list on Dash board Working Sprint Q:-4
						
						Query query10 = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
								+ " WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "' AND REQ_ID = '" + rs.getString("REQ_ID") + "'" );
						List<String> nameOfAccounts =  query10.list();
						if(nameOfAccounts!=null && !nameOfAccounts.isEmpty()) {
							for(String nameAccounts : nameOfAccounts) {
								Query query3 = sessionFactory.getCurrentSession()
										.createNativeQuery("UPDATE MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE='0' '"
												+ "' WHERE IS_ACTIVE = '1' AND LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "' AND LAUNCH_KAM_ACCOUNT = '"
												+ nameAccounts + "'");
								query3.executeUpdate();
							}
							
						}
						
						//Sprint4Aug2021 changes
						Query qryDeleteSellIn = sessionFactory.getCurrentSession()
								.createNativeQuery("DELETE LS from TBL_LAUNCH_SELLIN LS INNER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS MD ON MD.LAUNCH_ID = LS.SELLIN_LAUNCH_ID AND MD.LAUNCH_KAM_ACCOUNT = LS.SELLIN_L2_CHAIN WHERE MD.IS_ACTIVE = 2 "
												 + " AND LS.SELLIN_LAUNCH_ID = " + rs.getString("LAUNCH_ID") + " AND REQ_ID = " + rs.getString("REQ_ID"));
						qryDeleteSellIn.executeUpdate();
						
						//Harsha's logic ends here 
						
						//Commented By Sarin - Sprint4Aug21 - for Launch Account wise Rejection
						/*Query query3 = sessionFactory.getCurrentSession()
								.createNativeQuery("UPDATE TBL_LAUNCH_MASTER SET LAUNCH_REJECTED='2',UPDATED_BY='" + userId
										+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
										+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "'");
						query3.executeUpdate(); */
						kamCalculationAfterLaunchReject(rs.getString("LAUNCH_ID"), userId, rs.getString("CREATED_BY"));
					} else if (rs.getString("CHANGES_REQUIRED").equals("MOC CHANGED")) {
						LaunchDataResponse launchDataResponse = getSpecificLaunchData(rs.getString("LAUNCH_ID"));
						preparedStatement1 = sessionImpl.connection().prepareStatement(
								"SELECT * FROM TBL_LAUNCH_MOC_KAM WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID")
								+ "' AND LAUNCH_ACCOUNT = '" + rs.getString("CREATED_BY") + "'");
						rs1 = preparedStatement1.executeQuery();
						if (rs1.next()) {
							Query query2 = sessionFactory.getCurrentSession()
									.createNativeQuery("UPDATE TBL_LAUNCH_MOC_KAM SET LAUNCH_MOC='"
											+ launchDataResponse.getLaunchMocKam() + "',UPDATED_BY='" + userId
											+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
											+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID")
											+ "' AND LAUNCH_ACCOUNT = '" + rs.getString("CREATED_BY") + "'");
							query2.executeUpdate();
						} else {
							try (PreparedStatement preparedStatementInside = sessionImpl.connection()
									.prepareStatement(TBL_LAUNCH_MOC_KAM, Statement.RETURN_GENERATED_KEYS)) {
								preparedStatementInside.setString(1, rs.getString("LAUNCH_ID"));
								preparedStatementInside.setString(2, launchDataResponse.getLaunchMocKam());
								preparedStatementInside.setString(3, rs.getString("CREATED_BY"));
								preparedStatementInside.setString(4, userId);
								preparedStatementInside.setTimestamp(5, new Timestamp(new Date().getTime()));
								preparedStatementInside.executeUpdate();
							} catch (Exception e) {
								logger.error("Exception: " + e);
								return e.toString();
							}
						}
						
						
						//Sprint4Aug2021 changes - starts
						Query queryKamMoc = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT LAUNCH_KAM_ACCOUNT FROM MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS "
								+ " WHERE LAUNCH_ID = '" + rs.getString("LAUNCH_ID") + "' AND REQ_ID = '" + rs.getString("REQ_ID") + "'" );
						List<String> kamApprovedAccounts =  queryKamMoc.list();
						if(kamApprovedAccounts!=null && !kamApprovedAccounts.isEmpty()) {
							for(String kamAccounts : kamApprovedAccounts) {
								Query query3 = sessionFactory.getCurrentSession()
										.createNativeQuery("UPDATE MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE='0' '"
												+ "' WHERE IS_ACTIVE = '1' AND LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "' AND LAUNCH_KAM_ACCOUNT = '"
												+ kamAccounts + "'");
								query3.executeUpdate();
							}
						}
						Query queryUpdateKamMocToActive = sessionFactory.getCurrentSession()
								.createNativeQuery("UPDATE MODTRD.TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE = 1 WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "' AND REQ_ID = '" + rs.getString("REQ_ID") + "'");
						queryUpdateKamMocToActive.executeUpdate();
						//Sprint4Aug2021 changes - ends
						
					} else if (rs.getString("CHANGES_REQUIRED").equals("BASEPACK REJECTED")) {
						basepackCalForKAM(userId, rs, session);
					} else if (rs.getString("CHANGES_REQUIRED").equals("STORE REJECTED")) {
						storeCalForKAM(userId, rs, session);
					} else if (rs.getString("CHANGES_REQUIRED").equals("LAUNCH SAMPLE SHARED CHANGED")) {
						LaunchDataResponse launchDataResponse = getSpecificLaunchData(rs.getString("LAUNCH_ID"));
						String sampleShared;
						if (launchDataResponse.getSampleShared().equals("0")) {
							sampleShared = "1";
						} else {
							sampleShared = "0";
						}

						preparedStatement1 = sessionImpl.connection()
								.prepareStatement("SELECT * FROM TBL_LAUNCH_SAMPLE_SHARED_KAM WHERE LAUNCH_ID='"
										+ rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '"
										+ rs.getString("CREATED_BY") + "'");
						rs1 = preparedStatement1.executeQuery();
						if (rs1.next()) {
							Query query2 = sessionFactory.getCurrentSession()
									.createNativeQuery("UPDATE TBL_LAUNCH_SAMPLE_SHARED_KAM SET LAUNCH_SAMPLE_SHARED='"
											+ sampleShared + "',UPDATED_BY='" + userId + "',UPDATED_DATE='"
											+ new Timestamp(new Date().getTime()) + "' WHERE LAUNCH_ID='"
											+ rs.getString("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '"
											+ rs.getString("CREATED_BY") + "'");
							query2.executeUpdate();
						} else {
							try (PreparedStatement preparedStatementInside = sessionImpl.connection()
									.prepareStatement(TBL_LAUNCH_SAMPLE_SHARED_KAM, Statement.RETURN_GENERATED_KEYS)) {
								preparedStatementInside.setString(1, rs.getString("LAUNCH_ID"));
								preparedStatementInside.setString(2, sampleShared);
								preparedStatementInside.setString(3, rs.getString("CREATED_BY"));
								preparedStatementInside.setString(4, userId);
								preparedStatementInside.setTimestamp(5, new Timestamp(new Date().getTime()));
								preparedStatementInside.executeUpdate();
							} catch (Exception e) {
								logger.error("Exception: " + e);
								return e.toString();
							}
						}
					}
				}
				Query query2 = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_LAUNCH_REQUEST SET REQ_TYPE='APPROVED BY TME',UPDATED_BY='" + userId
								+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "',FINAL_STATUS = 'APPROVED',TME_REMARKS = '" + acceptByTme.getAcceptRemark()
								+ "' WHERE REQ_ID='" + reqIds[i] + "'");
				query2.executeUpdate();

				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setString(1, reqIds[i]);
					preparedStatementInside.setString(2, userId);
					preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
					preparedStatementInside.setString(4, "APPROVED BY TME");
					preparedStatementInside.setString(5, acceptByTme.getAcceptRemark());
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				preparedStatement.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
		}
		return "Approved Successfully";
	}

	private String kamCalculationAfterLaunchReject(String launchId, String userId, String kamAcc) {
		try {
			List<LaunchFinalPlanResponse> listOfFinalResponse = launchFinalService
					.getLaunchFinalResposeEditKamAfterLaunchRej(launchId, userId, kamAcc);

			SaveFinalLaunchListRequest saveFinalLaunchListRequest = new SaveFinalLaunchListRequest();
			List<SaveFinalLaunchRequest> listOfSaveFinalLaunchRequest = new ArrayList<>();
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalResponse) {
				SaveFinalLaunchRequest saveFinalLaunchRequest = new SaveFinalLaunchRequest();
				saveFinalLaunchRequest.setSkuName(launchFinalPlanResponse.getSkuName());
				saveFinalLaunchRequest.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				saveFinalLaunchRequest.setLaunchSellInUnits(launchFinalPlanResponse.getLaunchSellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN1(launchFinalPlanResponse.getLaunchN1SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN2(launchFinalPlanResponse.getLaunchN2SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInValueClds(launchFinalPlanResponse.getLaunchSellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN1(launchFinalPlanResponse.getLaunchN1SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN2(launchFinalPlanResponse.getLaunchN2SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValue(launchFinalPlanResponse.getLaunchSellInValue());
				saveFinalLaunchRequest.setLaunchSellInValueN1(launchFinalPlanResponse.getLaunchN1SellInVal());
				saveFinalLaunchRequest.setLaunchSellInValueN2(launchFinalPlanResponse.getLaunchN2SellInVal());
				//saveFinalLaunchRequest.setLaunchStoreCount(Integer.parseInt(launchFinalPlanResponse.getStoreCount()));  //Commented & Added Below By Sarin - sprint4Aug2021
				saveFinalLaunchRequest.setLaunchStoreCount((int)Double.parseDouble(launchFinalPlanResponse.getStoreCount()));  //Added By Sarin - sprint4Aug2021
				listOfSaveFinalLaunchRequest.add(saveFinalLaunchRequest);
			}
			saveFinalLaunchListRequest.setLaunchId(launchId);
			saveFinalLaunchListRequest.setListOfFinalBuildUps(listOfSaveFinalLaunchRequest);
			String returnedVal = "";
			returnedVal = launchFinalDao.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
			if (!returnedVal.equals("Saved Successfully")) {
				throw new Exception(returnedVal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "Approved Successfully";
	}

	public String basepackCalForKAM(String userId, ResultSet rs, Session session) {
		try {
			List<LaunchFinalPlanResponse> listOfFinalResponse = launchFinalService.getLaunchFinalResposeAfterBPReject(
					rs.getString("LAUNCH_ID"), userId, rs.getString("CREATED_BY"), rs.getString("REJECT_IDS"));

			SaveFinalLaunchListRequest saveFinalLaunchListRequest = new SaveFinalLaunchListRequest();
			List<SaveFinalLaunchRequest> listOfSaveFinalLaunchRequest = new ArrayList<>();
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalResponse) {
				SaveFinalLaunchRequest saveFinalLaunchRequest = new SaveFinalLaunchRequest();
				saveFinalLaunchRequest.setSkuName(launchFinalPlanResponse.getSkuName());
				saveFinalLaunchRequest.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				saveFinalLaunchRequest.setLaunchSellInUnits(launchFinalPlanResponse.getLaunchSellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN1(launchFinalPlanResponse.getLaunchN1SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN2(launchFinalPlanResponse.getLaunchN2SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInValueClds(launchFinalPlanResponse.getLaunchSellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN1(launchFinalPlanResponse.getLaunchN1SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN2(launchFinalPlanResponse.getLaunchN2SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValue(launchFinalPlanResponse.getLaunchSellInValue());
				saveFinalLaunchRequest.setLaunchSellInValueN1(launchFinalPlanResponse.getLaunchN1SellInVal());
				saveFinalLaunchRequest.setLaunchSellInValueN2(launchFinalPlanResponse.getLaunchN2SellInVal());
				//saveFinalLaunchRequest.setLaunchStoreCount(Integer.parseInt(launchFinalPlanResponse.getStoreCount()));
				saveFinalLaunchRequest.setLaunchStoreCount((int)Double.parseDouble(launchFinalPlanResponse.getStoreCount()));
				listOfSaveFinalLaunchRequest.add(saveFinalLaunchRequest);
			}
			saveFinalLaunchListRequest.setLaunchId(rs.getString("LAUNCH_ID"));
			saveFinalLaunchListRequest.setListOfFinalBuildUps(listOfSaveFinalLaunchRequest);
			String returnedVal = "";
			returnedVal = launchFinalDao.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
			if (!returnedVal.equals("Saved Successfully")) {
				throw new Exception(returnedVal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "Approved Successfully";
	}

	public String storeCalForKAM(String userId, ResultSet rs, Session session) {
		try {
			List<LaunchFinalPlanResponse> listOfFinalResponse = launchFinalService
					.getLaunchFinalResposeAfterStoreReject(rs.getString("LAUNCH_ID"), userId,
							rs.getString("CREATED_BY"), rs.getString("REJECT_IDS"));

			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalResponse) {
				if (launchFinalPlanResponse.getError() != null) {
					if (launchFinalPlanResponse.getError().length() != 0) {
						throw new Exception(launchFinalPlanResponse.getError());
					}
				}
			}
			SaveFinalLaunchListRequest saveFinalLaunchListRequest = new SaveFinalLaunchListRequest();
			List<SaveFinalLaunchRequest> listOfSaveFinalLaunchRequest = new ArrayList<>();
			for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalResponse) {
				SaveFinalLaunchRequest saveFinalLaunchRequest = new SaveFinalLaunchRequest();
				saveFinalLaunchRequest.setSkuName(launchFinalPlanResponse.getSkuName());
				saveFinalLaunchRequest.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
				saveFinalLaunchRequest.setLaunchSellInUnits(launchFinalPlanResponse.getLaunchSellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN1(launchFinalPlanResponse.getLaunchN1SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInUnitsN2(launchFinalPlanResponse.getLaunchN2SellInUnit());
				saveFinalLaunchRequest.setLaunchSellInValueClds(launchFinalPlanResponse.getLaunchSellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN1(launchFinalPlanResponse.getLaunchN1SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValueCldsN2(launchFinalPlanResponse.getLaunchN2SellInCld());
				saveFinalLaunchRequest.setLaunchSellInValue(launchFinalPlanResponse.getLaunchSellInValue());
				saveFinalLaunchRequest.setLaunchSellInValueN1(launchFinalPlanResponse.getLaunchN1SellInVal());
				saveFinalLaunchRequest.setLaunchSellInValueN2(launchFinalPlanResponse.getLaunchN2SellInVal());
				saveFinalLaunchRequest.setLaunchStoreCount(Integer.parseInt(launchFinalPlanResponse.getStoreCount()));
				listOfSaveFinalLaunchRequest.add(saveFinalLaunchRequest);
			}
			saveFinalLaunchListRequest.setLaunchId(rs.getString("LAUNCH_ID"));
			saveFinalLaunchListRequest.setListOfFinalBuildUps(listOfSaveFinalLaunchRequest);
			String returnedVal = "";
			returnedVal = launchFinalDao.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
			if (!returnedVal.equals("Saved Successfully")) {
				throw new Exception(returnedVal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		return "Approved Successfully";
	}

	@Override
	public String acceptKamInputsByUpload(AcceptByTmeRequestByUpload acceptByTme, String userId) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			List<String> reqIdsList = acceptByTme.getReqIds();
			List<String> acceptRemarkList = acceptByTme.getAcceptRemark();
			for (int i = 0; i < reqIdsList.size(); i++) {
				preparedStatement = sessionImpl.connection()
						.prepareStatement("SELECT LAUNCH_ID,CHANGES_REQUIRED FROM TBL_LAUNCH_REQUEST WHERE REQ_ID = '"
								+ reqIdsList.get(0) + "'");
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					if (rs.getString("CHANGES_REQUIRED").equals("LAUNCH REJECTED")) {
						Query query2 = sessionFactory.getCurrentSession()
								.createNativeQuery("UPDATE TBL_LAUNCH_MASTER SET LAUNCH_REJECTED='2',UPDATED_BY='" + userId
										+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
										+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "'");
						query2.executeUpdate();
					} else if (rs.getString("CHANGES_REQUIRED").equals("MOC CHANGED")) {
						LaunchDataResponse launchDataResponse = getSpecificLaunchData(rs.getString("LAUNCH_ID"));
						Query query2 = sessionFactory.getCurrentSession()
								.createNativeQuery("UPDATE TBL_LAUNCH_MASTER SET LAUNCH_MOC='"
										+ launchDataResponse.getLaunchMocKam() + "',UPDATED_BY='" + userId
										+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
										+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "'");
						query2.executeUpdate();

					} else if (rs.getString("CHANGES_REQUIRED").equals("BASEPACK REJECTED")) {
						Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
								"UPDATE TBL_LAUNCH_BASEPACK SET BP_STATUS='REJECTED BY TME',BP_UPDATED_BY='" + userId
								+ "',BP_UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "' WHERE LAUNCH_ID='" + rs.getString("LAUNCH_ID") + "'");
						query2.executeUpdate();
						List<LaunchFinalPlanResponse> listOfFinalResponse = launchFinalService
								.getLaunchFinalRespose(rs.getString("LAUNCH_ID"), userId);

						SaveFinalLaunchListRequest saveFinalLaunchListRequest = new SaveFinalLaunchListRequest();
						for (LaunchFinalPlanResponse launchFinalPlanResponse : listOfFinalResponse) {
							SaveFinalLaunchRequest saveFinalLaunchRequest = new SaveFinalLaunchRequest();
							saveFinalLaunchRequest.setSkuName(launchFinalPlanResponse.getSkuName());
							saveFinalLaunchRequest.setBasepackCode(launchFinalPlanResponse.getBasepackCode());
							saveFinalLaunchRequest.setLaunchSellInUnits(launchFinalPlanResponse.getLaunchSellInUnit());
							saveFinalLaunchRequest
							.setLaunchSellInUnitsN1(launchFinalPlanResponse.getLaunchN1SellInUnit());
							saveFinalLaunchRequest
							.setLaunchSellInUnitsN2(launchFinalPlanResponse.getLaunchN2SellInUnit());
							saveFinalLaunchRequest
							.setLaunchSellInValueClds(launchFinalPlanResponse.getLaunchSellInCld());
							saveFinalLaunchRequest
							.setLaunchSellInValueCldsN1(launchFinalPlanResponse.getLaunchN1SellInCld());
							saveFinalLaunchRequest
							.setLaunchSellInValueCldsN2(launchFinalPlanResponse.getLaunchN2SellInCld());
							saveFinalLaunchRequest.setLaunchSellInValue(launchFinalPlanResponse.getLaunchSellInValue());
							saveFinalLaunchRequest
							.setLaunchSellInValueN1(launchFinalPlanResponse.getLaunchN1SellInVal());
							saveFinalLaunchRequest
							.setLaunchSellInValueN2(launchFinalPlanResponse.getLaunchN2SellInVal());
							saveFinalLaunchRequest
							.setLaunchStoreCount(Integer.parseInt(launchFinalPlanResponse.getStoreCount()));
						}
						saveFinalLaunchListRequest.setLaunchId(rs.getString("LAUNCH_ID"));
						launchFinalDao.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
					}
				}
				Query query2 = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_LAUNCH_REQUEST SET REQ_TYPE='APPROVED BY TME',UPDATED_BY='" + userId
								+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime())
								+ "',FINAL_STATUS = 'APPROVED',TME_REMARKS = '" + acceptRemarkList.get(i)
								+ "' WHERE REQ_ID='" + reqIdsList.get(i) + "'");
				query2.executeUpdate();

				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setString(1, reqIdsList.get(i));
					preparedStatementInside.setString(2, userId);
					preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
					preparedStatementInside.setString(4, "APPROVED BY TME");
					preparedStatementInside.setString(5, acceptRemarkList.get(i));
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				preparedStatement.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
		}
		return "Approved Successfully";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchKamQueriesAnsweredResponse> getLaunchQueriesAnswered(String userId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<LaunchKamQueriesAnsweredResponse> launchKamQueriesAnsweredResponses = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT tlm.LAUNCH_NAME LAUNCH_NAME, tlm.LAUNCH_MOC LAUNCH_MOC, REQ_DATE, CHANGES_REQUIRED,KAM_REMARKS,"
							+ "tlr.UPDATED_DATE RESPONSE_DATE, REQ_TYPE, tlr.CREATED_BY, TME_REMARKS FROM TBL_LAUNCH_REQUEST tlr,"
							+ "TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlr.LAUNCH_ID AND FINAL_STATUS IN ('APPROVED','REJECTED') AND "
							+ "tlm.CREATED_BY = '" + userId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchKamQueriesAnsweredResponse launchKamQueriesAnsweredResponse = new LaunchKamQueriesAnsweredResponse();
				launchKamQueriesAnsweredResponse.setLaunchName(rs.getString("LAUNCH_NAME"));
				launchKamQueriesAnsweredResponse.setLaunchMoc(rs.getString("LAUNCH_MOC"));
				launchKamQueriesAnsweredResponse.setReqDate(rs.getString("REQ_DATE"));
				launchKamQueriesAnsweredResponse.setChangesRequired(rs.getString("CHANGES_REQUIRED"));
				launchKamQueriesAnsweredResponse.setKamRemarks(rs.getString("KAM_REMARKS"));
				launchKamQueriesAnsweredResponse.setResponseDate(rs.getString("RESPONSE_DATE"));
				launchKamQueriesAnsweredResponse.setApprovalStatus(rs.getString("REQ_TYPE"));
				launchKamQueriesAnsweredResponse.setName(rs.getString("CREATED_BY"));
				launchKamQueriesAnsweredResponse.setTmeRemarks(rs.getString("TME_REMARKS"));

				String kamMailId = rs.getString("CREATED_BY").concat("@unilever.com").toUpperCase();
				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT ACCOUNT_NAME FROM MODTRD.TBL_VAT_COMM_OUTLET_MASTER WHERE UPPER(KAM_MAIL_ID) = '"
								+ kamMailId + "'");
				String listOfAccounts = String.join(",", query2.list());

				String accounts = "";
				if (!listOfAccounts.isEmpty()) {
					accounts = listOfAccounts.toString();
				}
				launchKamQueriesAnsweredResponse.setAccount(accounts);
				launchKamQueriesAnsweredResponses.add(launchKamQueriesAnsweredResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchKamQueriesAnsweredResponse launchKamQueriesAnsweredResponse = new LaunchKamQueriesAnsweredResponse();
			launchKamQueriesAnsweredResponse.setError(e.toString());
		}
		return launchKamQueriesAnsweredResponses;
	}

	@Override
	public String deleteAllNextPageData(String launchId, String currentPage, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate0 = null;
		PreparedStatement batchUpdate1 = null;
		PreparedStatement batchUpdate2 = null;
		PreparedStatement batchUpdate3 = null;
		PreparedStatement batchUpdate4 = null;
		try {
			if (currentPage.equals("Launch_details")) {
				batchUpdate0 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_BASEPACK where LAUNCH_ID=?");
				batchUpdate0.setString(1, launchId);
				batchUpdate0.executeUpdate();

				batchUpdate1 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_CLUSTERS where CLUSTER_LAUNCH_ID=?");
				batchUpdate1.setString(1, launchId);
				batchUpdate1.executeUpdate();

				batchUpdate2 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID=?");
				batchUpdate2.setString(1, launchId);
				batchUpdate2.executeUpdate();

				batchUpdate3 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
				batchUpdate3.setString(1, launchId);
				batchUpdate3.executeUpdate();

				batchUpdate4 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
				batchUpdate4.setString(1, launchId);
				batchUpdate4.executeUpdate();

				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else if (currentPage.equals("Launch_basepacks")) {
				batchUpdate0 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_CLUSTERS where CLUSTER_LAUNCH_ID=?");
				batchUpdate0.setString(1, launchId);
				batchUpdate0.executeUpdate();

				batchUpdate1 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID=?");
				batchUpdate1.setString(1, launchId);
				batchUpdate1.executeUpdate();

				batchUpdate2 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
				batchUpdate2.setString(1, launchId);
				batchUpdate2.executeUpdate();

				batchUpdate3 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
				batchUpdate3.setString(1, launchId);
				batchUpdate3.executeUpdate();

				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else if (currentPage.equals("Launch_clusters")) {
				batchUpdate0 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID=?");
				batchUpdate0.setString(1, launchId);
				batchUpdate0.executeUpdate();

				batchUpdate1 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
				batchUpdate1.setString(1, launchId);
				batchUpdate1.executeUpdate();

				batchUpdate2 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
				batchUpdate2.setString(1, launchId);
				batchUpdate2.executeUpdate();

				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else if (currentPage.equals("Launch_sellIn")) {
				batchUpdate0 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
				batchUpdate0.setString(1, launchId);
				batchUpdate0.executeUpdate();

				batchUpdate1 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
				batchUpdate1.setString(1, launchId);
				batchUpdate1.executeUpdate();

				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else if (currentPage.equals("Launch_visiplan")) {
				batchUpdate0 = sessionImpl.connection()
						.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
				batchUpdate0.setString(1, launchId);
				batchUpdate0.executeUpdate();

				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else if (currentPage.equals("Launch_buildUp")) {
				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_MASTER SET SAMPLE_SHARED=?0,UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3");  //Sarin - Added Parameters position
				query2.setParameter(0, null);
				query2.setParameter(1, userId);
				query2.setParameter(2, new Timestamp(new Date().getTime()));
				query2.setParameter(3, launchId);
				query2.executeUpdate();
			} else {
				throw new Exception("Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				if (batchUpdate0 != null)
					batchUpdate0.close();
				if (batchUpdate1 != null)
					batchUpdate1.close();
				if (batchUpdate2 != null)
					batchUpdate2.close();
				if (batchUpdate3 != null)
					batchUpdate3.close();
				if (batchUpdate4 != null)
					batchUpdate4.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
		}
		return "Success";
	}

	@Override
	public String deleteAllKamData(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate1 = null;
		PreparedStatement batchUpdate2 = null;
		String toReturn = null;
		try {
			batchUpdate1 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_MOC_KAM where LAUNCH_ID=?");
			batchUpdate1.setString(1, launchId);
			batchUpdate1.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_STATUS_KAM where LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_SAMPLE_SHARED_KAM where LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BASEPACK_KAM where LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_KAM_TEMP where LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_TEMP_FINAL_CAL_KAM where LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			batchUpdate2 = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP_KAM where BUILDUP_LAUNCH_ID=?");
			batchUpdate2.setString(1, launchId);
			batchUpdate2.executeUpdate();

			toReturn = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			toReturn = "Failure";
		} finally {
			try {
				batchUpdate1.close();
			} catch (SQLException e) {
				e.printStackTrace();
				toReturn = "Failure";
			}
		}
		return toReturn;
	}
	
	 //Q1 sprint kavitha feb2021 
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMoc(String userId) {
		try {
			
			Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc WHERE  "
					+ "date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
					+ "AND tlc.CREATED_BY = '" + userId + "' ORDER BY concat(substr(LAUNCH_MOC, 3, 4), substr(LAUNCH_MOC, 1, 2))");
				
			List<String> list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Q2 sprint kavitha feb2021 
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getAllLaunchName(String userId,String tmeMoc) {
			try {
				if(tmeMoc.equalsIgnoreCase("All")) {
					tmeMoc = "";
				}
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(
						" SELECT DISTINCT LAUNCH_NAME " + 
						" FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss WHERE " + 
						"tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()" + 
						"AND tlc.CREATED_BY = '" +userId+ "' AND LAUNCH_MOC LIKE '%" +tmeMoc+ "%' ORDER BY LAUNCH_NAME"
						);
					
				List<String> list = query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		 //Q1 sprint kavitha feb2021 
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getAllCOEMoc() {
			try {
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT LAUNCH_MOC FROM (SELECT DISTINCT LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc " + 
						"LEFT OUTER JOIN TBL_LAUNCH_CLUSTERS tlbt ON tlbt.CLUSTER_LAUNCH_ID = tlc.LAUNCH_ID LEFT OUTER JOIN TBL_LAUNCH_TEMP_FINAL_CAL TFC ON TFC.LAUNCH_ID = tlc.LAUNCH_ID " + 
						"LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TFC.LAUNCH_ID AND TLK.LAUNCH_KAM_ACCOUNT = TFC.MODIFIED_CHAIN AND TLK.IS_ACTIVE = 1 " + 
						"WHERE (SAMPLE_SHARED IS NOT NULL AND SAMPLE_SHARED <> '') AND LAUNCH_REJECTED != '2' AND TLK.LAUNCH_ID IS NULL AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() " + 
						"UNION ALL SELECT DISTINCT TLK.LAUNCH_MOC_KAM AS LAUNCH_MOC FROM TBL_LAUNCH_MASTER TLM INNER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID = TLM.LAUNCH_ID " + 
						"WHERE (TLM.SAMPLE_SHARED IS NOT NULL AND TLM.SAMPLE_SHARED <> '') AND TLM.LAUNCH_REJECTED != '2' AND TLK.IS_ACTIVE = 1 AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() " + 
						") A  ORDER BY concat(substr(LAUNCH_MOC, 3, 4), substr(LAUNCH_MOC, 1, 2))"
						);
					
				List<String> list = query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getLaunchNameBasedOnMoc(String userId,String tmeMoc) {
			String qryLaunchName = "SELECT LAUNCH_NAME FROM TBL_LAUNCH_MASTER tlc, TBL_LAUNCH_STAGE_STATUS tlss WHERE tlc.LAUNCH_ID = tlss.LAUNCH_ID AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()"
								  + "AND tlc.CREATED_BY = '" +userId+ "'";
			if (!tmeMoc.equalsIgnoreCase("All")) {
				qryLaunchName = qryLaunchName + " AND LAUNCH_MOC = '" +tmeMoc+ "'";
			}
			try 
			{
				Query query = sessionFactory.getCurrentSession().createNativeQuery(qryLaunchName);
				List<String> list = query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
	
}
