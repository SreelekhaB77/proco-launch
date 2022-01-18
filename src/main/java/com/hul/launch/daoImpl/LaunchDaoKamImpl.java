package com.hul.launch.daoImpl;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.controller.masters.StoreMasterBean;
import com.hul.launch.dao.LaunchDaoKam;
import com.hul.launch.dao.LoginDao;
import com.hul.launch.exception.GlobleKamException;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.model.SaveUploadededLaunchStore;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.model.User;
import com.hul.launch.request.ChangeMocRequestKam;
import com.hul.launch.request.GetKamLaunchRejectRequest;
import com.hul.launch.request.MissingDetailsKamInput;
import com.hul.launch.request.RejectBasepackRequestKam;
import com.hul.launch.request.SampleSharedReqKam;
import com.hul.launch.request.SaveLaunchStore;
import com.hul.launch.request.SaveLaunchStoreList;
import com.hul.launch.request.SaveVisiRequestVatKam;
import com.hul.launch.request.SaveVisiRequestVatKamList;
import com.hul.launch.response.KamChangeReqRemarks;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamBasepackResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseKam;
import com.hul.launch.service.LaunchServiceKam;
import com.hul.proco.controller.createpromo.ClusterBean;

@Repository
public class LaunchDaoKamImpl implements LaunchDaoKam {

	@Autowired
	private SessionFactory sessionFactory;
	

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private LoginDao loginDao;
	

	private final static String TBL_LAUNCH_STORE = "INSERT INTO MODTRD.TBL_LAUNCH_STORE(L1_CHAIN,L2_CHAIN,STORE_FORMAT, CLUSTER, HUL_OL_CODE, KAM_REMARKS, CREATED_BY, CREATED_DATE,LAUNCH_ID)"
			+ " VALUES (?,?,?,?,?,?,?,?,?) ";

	private final static String TBL_LAUNCH_VAT_VISI_PLANNING = "INSERT INTO MODTRD.TBL_LAUNCH_VAT_VISI_PLANNING "
			+ "(VAT_VISI_LAUNCH_ID, VAT_VISI_STORES_PLANNED, VAT_VISI_ASSET_1, VAT_VISI_FACING_PER_SHELF_1, "
			+ "VAT_VISI_DEPTH_PER_SHELF_1, VAT_VISI_ASSET_2, VAT_VISI_FACING_PER_SHELF_2, VAT_VISI_DEPTH_PER_SHELF_2,"
			+ " VAT_VISI_ASSET_3, VAT_VISI_FACING_PER_SHELF_3, VAT_VISI_DEPTH_PER_SHELF_3, VAT_VISI_ASSET_4, "
			+ "VAT_VISI_FACING_PER_SHELF_4, VAT_VISI_DEPTH_PER_SHELF_4, VAT_VISI_ASSET_5, VAT_VISI_FACING_PER_SHELF_5, "
			+ "VAT_VISI_DEPTH_PER_SHELF_5, CREATED_BY, CREATED_DATE) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_REQUEST = "INSERT INTO MODTRD.TBL_LAUNCH_REQUEST (LAUNCH_ID, CHANGES_REQUIRED, "
			+ " KAM_REMARKS, REQ_DATE, REQ_TYPE, CREATED_DATE, CREATED_BY,FINAL_STATUS) VALUES(?,?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_STATUS_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_STATUS_KAM (LAUNCH_ID, LAUNCH_STATUS, "
			+ " LAUNCH_ACCOUNT, CREATED_BY, CREATED_DATE) VALUES(?,?, ?, ?, ?)";

	private final static String TBL_LAUNCH_REQUEST_FOR_BASEPACK = "INSERT INTO MODTRD.TBL_LAUNCH_REQUEST (LAUNCH_ID, CHANGES_REQUIRED, "
			+ " KAM_REMARKS, REQ_DATE, REQ_TYPE, CREATED_DATE, CREATED_BY,FINAL_STATUS,REJECT_IDS) VALUES(?,?, ?, ?, ?, ?, ?, ?,?)";

	private final static String TBL_LAUNCH_REQUEST_HISTORY = "INSERT INTO MODTRD.TBL_LAUNCH_REQUEST_HISTORY "
			+ "(REQ_ID, ACTION_TAKEN_BY, ACTION_TAKEN_ON, ACTION_TAKEN,ACTION_REMARKS) VALUES(?, ?, ?, ?,?)";

	private final static String TBL_LAUNCH_BASEPACK_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_BASEPACK_KAM (LAUNCH_ID, LAUNCH_BASEPACK, "
			+ " LAUNCH_ACCOUNT, CREATED_BY, CREATED_DATE) VALUES(?,?, ?, ?, ?)";

	private final static String TBL_LAUNCH_STORE_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_STORE_KAM (LAUNCH_ID, LAUNCH_STORES, "
			+ " LAUNCH_ACCOUNT, CREATED_BY, CREATED_DATE) VALUES(?,?, ?, ?, ?)";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchKamBasepackResponse> getTmeBasepackData(List<String> listOfLaunchData) {
		List<LaunchKamBasepackResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION,tlb.BP_ID FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchKamBasepackResponse launchKamBasepackResponse = new LaunchKamBasepackResponse();
				launchKamBasepackResponse.setLaunchName(obj[0].toString());
				launchKamBasepackResponse.setSalesCat(obj[1].toString());
				launchKamBasepackResponse.setPsaCat(obj[2].toString());
				launchKamBasepackResponse.setBrand(obj[3].toString());
				launchKamBasepackResponse.setBpCode(obj[4].toString());
				launchKamBasepackResponse.setBpDisc(obj[5].toString());
				launchKamBasepackResponse.setMrp(obj[6].toString());
				launchKamBasepackResponse.setTur(obj[7].toString());
				launchKamBasepackResponse.setGsv(obj[8].toString());
				launchKamBasepackResponse.setCldConfig(obj[9].toString());
				launchKamBasepackResponse.setGrammage(obj[10].toString());
				launchKamBasepackResponse.setClassification(obj[11].toString());
				launchKamBasepackResponse.setBasepackId(obj[12].toString());
				listOfCompletedLaunch.add(launchKamBasepackResponse);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_KAM_BASEPACK_DATA, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchKamBasepackResponse> getKamBasepackData(List<String> listOfLaunchData, String userId) {
		List<LaunchKamBasepackResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION,tlb.BP_ID,tlb.LAUNCH_ID FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();

				Query query3 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT LAUNCH_BASEPACK FROM TBL_LAUNCH_BASEPACK_KAM WHERE LAUNCH_ACCOUNT = '" + userId
								+ "' AND LAUNCH_ID = '" + obj[13].toString() + "'");
				List<String> listOfBp = query3.list();
				List<String> bpIds = new ArrayList<>();
				if (!listOfBp.isEmpty()) {
					bpIds = Arrays.asList(listOfBp.get(0).toString().split(","));
				}

				if (!bpIds.contains(obj[12].toString())) {
					LaunchKamBasepackResponse launchKamBasepackResponse = new LaunchKamBasepackResponse();
					launchKamBasepackResponse.setLaunchName(obj[0].toString());
					launchKamBasepackResponse.setSalesCat(obj[1].toString());
					launchKamBasepackResponse.setPsaCat(obj[2].toString());
					launchKamBasepackResponse.setBrand(obj[3].toString());
					launchKamBasepackResponse.setBpCode(obj[4].toString());
					launchKamBasepackResponse.setBpDisc(obj[5].toString());
					launchKamBasepackResponse.setMrp(obj[6].toString());
					launchKamBasepackResponse.setTur(obj[7].toString());
					launchKamBasepackResponse.setGsv(obj[8].toString());
					launchKamBasepackResponse.setCldConfig(obj[9].toString());
					launchKamBasepackResponse.setGrammage(obj[10].toString());
					launchKamBasepackResponse.setClassification(obj[11].toString());
					launchKamBasepackResponse.setBasepackId(obj[12].toString());
					listOfCompletedLaunch.add(launchKamBasepackResponse);
				}

			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_KAM_BASEPACK_DATA, ex.toString());
		}
	}

	@Override
	// public List<LaunchDataResponse> getAllCompletedKamLaunchData(String account)
	// {
	public List<LaunchDataResponse> getAllCompletedKamLaunchData(String account, String launchMOC) { // Sarin Changes -
																										// QiSprint
																										// Feb2021
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchDataResponse> listOfCompletedLaunch = new ArrayList<>();
		List<LaunchDataResponse> finallistOfCompletedLaunch = new ArrayList<>();
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		// PreparedStatement stmt2 = null; // Commeneted by Harsha for getting remaining accounts rejection lists
		PreparedStatement stmtSS = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		// ResultSet rs3 = null; // Commeneted by Harsha for getting remaining accounts rejection lists
		ResultSet rsSS = null;
		try {
			// kiran - translate changes
			/*
			 * stmt = sessionImpl.connection().prepareStatement(
			 * "SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
			 * +
			 * " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
			 * +
			 * " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
			 * +
			 * " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED NOT IN ('1','2') AND DATE(TRANSLATE('GHIJ-DE-AB', LAUNCH_DATE, 'ABCDEFGHIJ')) > NOW()"
			 * );
			 */

			// Sarin Changes - QiSprint Feb2021
			if (launchMOC.equalsIgnoreCase("All")) {
				launchMOC = "";
			}
			//Kavitha D changes-Sprint4 Aug2021
			stmt = sessionImpl.connection().prepareStatement(
					/*"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED NOT IN ('1','2') AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()" */
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
					+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
					+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE,"
					+ " IFNULL((SELECT GROUP_CONCAT(LAUNCH_KAM_ACCOUNT separator '; ') AS CHANGED_MOC FROM (SELECT CONCAT(LAUNCH_MOC_KAM, ' - ', GROUP_CONCAT(LAUNCH_KAM_ACCOUNT)) AS LAUNCH_KAM_ACCOUNT "  
					+ " FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS KL WHERE KL.LAUNCH_ID = tlc.LAUNCH_ID AND IS_ACTIVE = 1 GROUP BY LAUNCH_MOC_KAM) A), '') CHANGED_MOC "
					+ " FROM TBL_LAUNCH_MASTER tlc "
					+ " WHERE SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED NOT IN ('1','2') AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()"
					// + " AND LAUNCH_MOC LIKE '%" + launchMOC + "%'"
			);

			rs = stmt.executeQuery();
			while (rs.next()) {
				// Commeneted by Harsha for getting remaining accounts rejection lists
				// stmt2 = sessionImpl.connection()
					//	.prepareStatement("SELECT * FROM TBL_LAUNCH_STATUS_KAM WHERE LAUNCH_ACCOUNT = '" + account
						//		+ "' AND LAUNCH_ID = '" + rs.getInt("LAUNCH_ID") + "'");
				// rs3 = stmt2.executeQuery();
				//if (!rs3.next()) {
					LaunchDataResponse launchDataResponse = new LaunchDataResponse();
					launchDataResponse.setLaunchId(rs.getInt("LAUNCH_ID"));
					launchDataResponse.setLaunchName(rs.getString("LAUNCH_NAME"));
					launchDataResponse.setLaunchDate(rs.getString("LAUNCH_DATE"));
					launchDataResponse.setLaunchNature(rs.getString("LAUNCH_NATURE"));
					launchDataResponse.setLaunchNature2(rs.getString("LAUNCH_NATURE_2"));
					launchDataResponse.setLaunchBusinessCase(rs.getString("LAUNCH_BUSINESS_CASE"));
					launchDataResponse.setCategorySize(rs.getString("CATEGORY_SIZE"));
					launchDataResponse.setClassification(rs.getString("CLASSIFICATION"));
					launchDataResponse.setAnnexureDocName(rs.getString("ANNEXURE_DOCUMENT_NAME"));
					launchDataResponse.setArtWorkPackShotsDocName(rs.getString("ARTWORK_PACKSHOTS_DOC_NAME"));
					launchDataResponse.setMdgDeckDocName(rs.getString("MDG_DECK_DOCUMENT_NAME"));
					
					String sampleShared = null;
					stmtSS = sessionImpl.connection().prepareStatement(
							"SELECT LAUNCH_SAMPLE_SHARED FROM TBL_LAUNCH_SAMPLE_SHARED_KAM WHERE LAUNCH_ID = '"
									+ rs.getInt("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '" + account + "'");
					rsSS = stmtSS.executeQuery();
					if (rsSS.next()) {
						sampleShared = rsSS.getString("LAUNCH_SAMPLE_SHARED");
					} else {
						sampleShared = rs.getString("SAMPLE_SHARED");
					}
					launchDataResponse.setSampleShared(sampleShared);

					User user = loginDao.getUserById(rs.getString("CREATED_BY"));
					launchDataResponse.setCreatedBy(user.getFirstName() + " " + user.getLastName());
					launchDataResponse.setCreatedDate(rs.getDate("CREATED_DATE"));
					launchDataResponse.setUpdatedBy(rs.getString("UPDATED_BY"));
					launchDataResponse.setUpdatedDate(rs.getDate("UPDATED_DATE"));
					//Kavitha D changes-SPrint4 Aug2021
					/*String launchMoc = null;
					stmt1 = sessionImpl.connection()
							.prepareStatement("SELECT LAUNCH_MOC FROM TBL_LAUNCH_MOC_KAM WHERE LAUNCH_ID = '"
									+ rs.getInt("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '" + account + "'");
					rs1 = stmt1.executeQuery();
					if (rs1.next()) {
						launchMoc = rs1.getString("LAUNCH_MOC");
					} else {
						launchMoc = rs.getString("LAUNCH_MOC");
					}*/
					
					launchDataResponse.setLaunchMoc(rs.getString("LAUNCH_MOC"));
					launchDataResponse.setChangedMoc(rs.getString("CHANGED_MOC"));
					launchDataResponse.setLaunchSubmissionDate(rs.getString("LAUNCH_SUBMISSION_DATE"));
					if (launchMOC.equalsIgnoreCase("")
							|| launchDataResponse.getLaunchMoc().equalsIgnoreCase(launchMOC)) {
						listOfCompletedLaunch.add(launchDataResponse);
					}
					
				
			}//Added By Harsha for sprint Q7 to get Launch's based on user ID
			finallistOfCompletedLaunch = kamAccountFilter (listOfCompletedLaunch,  account);
			if(finallistOfCompletedLaunch==null) {
				return null;
			}
				return finallistOfCompletedLaunch;
					
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchDataResponse launchDataResponse = new LaunchDataResponse();
			launchDataResponse.setError(ex.toString());
			finallistOfCompletedLaunch.add(launchDataResponse);
		} finally {
			try {
				stmt.close();
			//	stmt1.close();
			//	stmt2.close();
				stmtSS.close();
				rs.close();
			//	rs1.close();
			//	rs3.close();
				rsSS.close();
			} catch (Exception e) {
				logger.debug("Exception :", e);
				LaunchDataResponse launchDataResponse = new LaunchDataResponse();
				launchDataResponse.setError(e.toString());
				 finallistOfCompletedLaunch.add(launchDataResponse);
			}
		}
		return finallistOfCompletedLaunch;
	}
	
	
	//Added By Harsha for sprint Q7 to get Launch's based on user ID
	public List<LaunchDataResponse> kamAccountFilter (List<LaunchDataResponse> listOfCompletedLaunch, String account) {
		List<LaunchDataResponse> finalListOfCompletedLaunch = new ArrayList<>();
		String userId=account;
		for(LaunchDataResponse name : listOfCompletedLaunch) {
			LaunchDataResponse launchDataResponsedub = new LaunchDataResponse();
			int launchId = name.getLaunchId();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String AccountNames = "";
		String VatUserAcc = "";
		try {
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT CLUSTER_ACCOUNT FROM TBL_LAUNCH_CLUSTERS WHERE CLUSTER_LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> accountslist=new ArrayList<>();
			ArrayList<String> vatUserDetails=new ArrayList<>();
			ArrayList<String> allCustomers=new ArrayList<>();
			allCustomers.add("ALL CUSTOMERS");
			while (rs.next()) {
				AccountNames = rs.getString("CLUSTER_ACCOUNT");
				String[] parts = AccountNames.split(",");
				for(String acc:parts){  
					String[] accountName = acc.split(":");
					String part1 = accountName[0];
					if(!part1.isEmpty()) {
						accountslist.add(part1.trim());
					}
					
					}
			
				PreparedStatement stmt3 = sessionImpl.connection()
						.prepareStatement("SELECT ACCOUNT_NAME FROM TBL_VAT_USER_DETAILS WHERE USERID = '" + userId + "'" );
				ResultSet rs3 = stmt3.executeQuery();
				while (rs3.next()) {
					VatUserAcc = rs3.getString("ACCOUNT_NAME");
					String[] part2 = VatUserAcc.split(",");
					for(String vatAcc:part2){  
						vatUserDetails.add(vatAcc);
						}
				}
				boolean var=CollectionUtils.containsAny(vatUserDetails,accountslist);
				boolean allCustomer = CollectionUtils.containsAny(allCustomers,accountslist);
				
				if(var || allCustomer) {
					launchDataResponsedub.setLaunchId(name.getLaunchId());
					launchDataResponsedub.setLaunchName(name.getLaunchName());
		        	launchDataResponsedub.setLaunchDate(name.getLaunchDate());
		        	launchDataResponsedub.setLaunchMoc(name.getLaunchMoc());
					launchDataResponsedub.setLaunchNature(name.getLaunchNature());
					launchDataResponsedub.setLaunchNature2(name.getLaunchNature2());
					launchDataResponsedub.setLaunchBusinessCase(name.getLaunchBusinessCase());
					launchDataResponsedub.setCategorySize(name.getCategorySize());
					launchDataResponsedub.setClassification(name.getClassification());
					launchDataResponsedub.setAnnexureDocName(name.getAnnexureDocName());
					launchDataResponsedub.setArtWorkPackShotsDocName(name.getArtWorkPackShotsDocName());
					launchDataResponsedub.setMdgDeckDocName(name.getMdgDeckDocName());
					launchDataResponsedub.setSampleShared(name.getSampleShared());
					launchDataResponsedub.setCreatedBy(name.getCreatedBy());
					launchDataResponsedub.setCreatedDate(name.getCreatedDate());
					launchDataResponsedub.setUpdatedBy(name.getUpdatedBy());
					launchDataResponsedub.setUpdatedDate(name.getUpdatedDate());
					launchDataResponsedub.setLaunchFinalStatus(name.getLaunchFinalStatus());
					launchDataResponsedub.setLaunchMocKam(name.getLaunchMocKam());
					launchDataResponsedub.setLaunchSubmissionDate(name.getLaunchSubmissionDate());
					launchDataResponsedub.setAccountName(name.getAccountName());
					launchDataResponsedub.setOriginalLaunchMoc(name.getOriginalLaunchMoc());
					launchDataResponsedub.setChangedMoc(name.getChangedMoc());
					finalListOfCompletedLaunch.add(launchDataResponsedub);
				}
				
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return (List<LaunchDataResponse>) ex;
		}
		
		}
		if(!finalListOfCompletedLaunch.isEmpty()) {
			return finalListOfCompletedLaunch; 
		}
		return null;
	}

	@Override
	public String getUpcomingLaunchMocByLaunchIdsKam(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String launchMoc = "";
		try {
		
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT LAUNCH_DATE FROM TBL_LAUNCH_MASTER WHERE LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				launchMoc = rs.getString("LAUNCH_DATE");
			}

			return launchMoc;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
	}
	
	
	
	/// Implementation to get details from modified table Added by Harsha for Sprint Q4
	public List<String> getModifiedMocdetailsforRejection(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String launch_moc_kam = "";
		String launch_kam_account = "";
		String updated_by = "";
		String whole="";
		List<String> modifiedDetails = new ArrayList<>();  
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"select TLM.LAUNCH_MOC, TLK.updated_date,TLK.launch_moc_kam,launch_kam_account,TLK.updated_by "
							+ "  from TBL_LAUNCH_MASTER TLM LEFT OUTER JOIN TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS TLK ON TLK.LAUNCH_ID= TLM.LAUNCH_ID "
							+ "  and TLK.is_active= 1 " + "  where TLM.LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				launch_moc_kam = rs.getString("launch_moc_kam");
				launch_kam_account = rs.getString("launch_kam_account");
				updated_by = rs.getString("updated_by");
				whole = launch_moc_kam + "-" + launch_kam_account + "-" + updated_by;
				modifiedDetails.add(whole);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
		}
		return modifiedDetails;
	}
	
	// Inserting values into table Added By Harsha
	public Boolean insertintoLaunchKamChangeMocDetails(String userId,List<String> validDetails) {
		boolean res = true;
		try {
			
			for(String read : validDetails) {
				String[] stringarray = read.split("-");
			
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			   LocalDateTime now = LocalDateTime.now();  
			   int LAUNCH_ID = Integer.parseInt(stringarray[3]);
			
			Query query = sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS"
					+ "(LAUNCH_ID,"
					+ "LAUNCH_MOC_KAM,"
					+ "LAUNCH_KAM_ACCOUNT,"
					+ "IS_ACTIVE,"
					+ "UPDATED_BY,"
					+ "UPDATED_DATE,REQ_ID)"
					+ "VALUES(?1,?2,?3,?4,?5,?6,?7)");
			query.setParameter(1, LAUNCH_ID);
			query.setParameter(2, stringarray[1]);
			query.setParameter(3, stringarray[0]);
			query.setParameter(4, 2);
			query.setParameter(5, stringarray[2]);
			query.setParameter(6, dtf.format(now));
			query.setParameter(7, stringarray[4]);
			query.executeUpdate(); 
			}
		} catch (Exception e) {
			logger.debug("Exception:",e);
			res=false;
		}
		return res;
	}
	
	
	
	
	
	
	

	@Override
	public String rejectLaunchByLaunchIdKam(GetKamLaunchRejectRequest getKamLaunchRejectRequest, String userId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			ResultSet rs = null;

			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT * FROM TBL_LAUNCH_STATUS_KAM WHERE LAUNCH_ID = '"
							+ getKamLaunchRejectRequest.getLaunchId() + "' AND LAUNCH_ACCOUNT = '" + userId + "'");
			ResultSet rs1 = stmt.executeQuery();
			if (rs1.next()) {
				Query query2 = sessionFactory.getCurrentSession()
						.createNativeQuery("UPDATE TBL_LAUNCH_STATUS_KAM SET LAUNCH_STATUS='1',UPDATED_BY='" + userId
								+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime()) + "' WHERE LAUNCH_ID='"
								+ getKamLaunchRejectRequest.getLaunchId() + "' AND LAUNCH_ACCOUNT = '" + userId + "'");
				query2.executeUpdate();
			} else {
				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_STATUS_KAM, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setString(1, getKamLaunchRejectRequest.getLaunchId());
					preparedStatementInside.setString(2, "1");
					preparedStatementInside.setString(3, userId);
					preparedStatementInside.setString(4, userId);
					preparedStatementInside.setTimestamp(5, new Timestamp(new Date().getTime()));
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}

			try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_REQUEST,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, getKamLaunchRejectRequest.getLaunchId());
				preparedStatement.setString(2, "LAUNCH REJECTED");
				preparedStatement.setString(3, getKamLaunchRejectRequest.getLaunchRejectRemark());
				preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
				preparedStatement.setString(5, "REJECTED BY KAM");
				preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
				preparedStatement.setString(7, userId);
				preparedStatement.setString(8, "PENDING");
				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				int reqId = 0;
				if (rs != null && rs.next()) {
					reqId = rs.getInt(1);
				}
				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setInt(1, reqId);
					preparedStatementInside.setString(2, userId);
					preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
					preparedStatementInside.setString(4, "REJECTED BY KAM");
					preparedStatementInside.setString(5, getKamLaunchRejectRequest.getLaunchRejectRemark());
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
				// Harsha's snippet Sprint Q4
				String launchId = getKamLaunchRejectRequest.getLaunchId();
				List<String> finalList = new ArrayList<>(); 
				String RequestedAccounts = getKamLaunchRejectRequest.getMocAccount();
				
				
				List<String> unmodifiedMOCDetails = new ArrayList<>();  
				List<String> modifiedMOCDetails = new ArrayList<>();
				
				String Date = getUpcomingLaunchMocByLaunchIdsKam(launchId); //getting original date 
				String[] dateString = Date.split("/");
				Date = dateString[1]+dateString[2];
				
				List<String> listofModifiedMoc = getModifiedMocdetailsforRejection(launchId);
				
				String[] stringarray = RequestedAccounts.split(","); 
				//Fetching details of modified Accounts
				for(int i=0; i< stringarray.length; i++)  
				{  
					for(String comapremodifid : listofModifiedMoc) {
						String[] ModifiedAcoounts = comapremodifid.split("-"); 
						if((ModifiedAcoounts[1].equals(stringarray[i])) && userId.equalsIgnoreCase(ModifiedAcoounts[2])) {
							
							modifiedMOCDetails.add(ModifiedAcoounts[1]);
						
							finalList.add(stringarray[i]+"-"+ModifiedAcoounts[0]+"-"+ModifiedAcoounts[2]+"-"+launchId+"-"+reqId);
						}		
						
					}
					unmodifiedMOCDetails.add(stringarray[i]);
				}  
				
				for (String A: modifiedMOCDetails) {
					  if (unmodifiedMOCDetails.contains(A))
						  unmodifiedMOCDetails.remove(A);
					}
				//Fetching details of non modified Accounts
				for (String unmodifiedResult : unmodifiedMOCDetails) {
						finalList.add(unmodifiedResult+"-"+Date+"-"+userId+"-"+launchId+"-"+reqId);
				}
				boolean response = insertintoLaunchKamChangeMocDetails (userId, finalList);
				
				
				// Harsha's code ends here

				
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			} finally {
				rs.close();
			}

		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		}
		return "Rejected Successfully";
	}

	@Override
	public String requestChengeMocByLaunchIdKam(ChangeMocRequestKam changeMocRequestKam, String userId) {

		String responseText = "";
		ResultSet rs = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_REQUEST,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, changeMocRequestKam.getLaunchId());
			preparedStatement.setString(2, "MOC CHANGED");
			preparedStatement.setString(3, changeMocRequestKam.getMocChangeRemark());
			preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
			preparedStatement.setString(5, "REJECTED BY KAM");
			preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
			preparedStatement.setString(7, userId);
			preparedStatement.setString(8, "PENDING");

			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			int reqId = 0;
			if (rs != null && rs.next()) {
				reqId = rs.getInt(1);
			}
			try (PreparedStatement preparedStatementInside = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatementInside.setInt(1, reqId);
				preparedStatementInside.setString(2, userId);
				preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
				preparedStatementInside.setString(4, "REJECTED BY KAM");
				preparedStatementInside.setString(5, changeMocRequestKam.getMocChangeRemark());

				preparedStatementInside.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			}

			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"UPDATE TBL_LAUNCH_MASTER SET LAUNCH_MOC_KAM=?0, UPDATED_BY=?1,UPDATED_DATE=?2 WHERE LAUNCH_ID=?3"); // Sarin - Added Parameters position
			query2.setParameter(0, changeMocRequestKam.getMocToChange());
			query2.setParameter(1, userId);
			query2.setParameter(2, new Timestamp(new Date().getTime()));
			// query2.setParameter(3, changeMocRequestKam.getMocAccount()); ,KAM_ACCOUNT=?3
			query2.setParameter(3, changeMocRequestKam.getLaunchId());
			query2.executeUpdate();

			// Sarin Changes - Q1Sprint Feb2021 - Starts
			String kamAccounts[];
			kamAccounts = changeMocRequestKam.getMocAccount().split(",");
			if (kamAccounts != null && kamAccounts.length > 0) {
				/* //Commented by Sarin - Sprint4Aug2021 changes
				Query qryKamAcc = sessionFactory.getCurrentSession().createNativeQuery(
						"UPDATE TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE = 0 WHERE LAUNCH_ID=?0 AND LAUNCH_MOC_KAM=?1 ");

				qryKamAcc.setParameter(0, changeMocRequestKam.getLaunchId());
				qryKamAcc.setParameter(1, changeMocRequestKam.getMocToChange());
				qryKamAcc.setParameter(2, userId);
				qryKamAcc.executeUpdate();
				*/

				PreparedStatement kamMOCUpdate = null;
				String insertStatementForKAMMOCAcc = "INSERT INTO TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS (LAUNCH_ID, LAUNCH_MOC_KAM, LAUNCH_KAM_ACCOUNT, IS_ACTIVE, UPDATED_BY, UPDATED_DATE, REQ_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
				for (int i = 0; i < kamAccounts.length; i++) {
					try (PreparedStatement psKamMocChange = sessionImpl.connection()
							.prepareStatement(insertStatementForKAMMOCAcc, Statement.RETURN_GENERATED_KEYS)) {
						
						//Sprint4Aug2021 changes - starts
						/* kamMOCUpdate = sessionImpl.connection()
								.prepareStatement("UPDATE TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS SET IS_ACTIVE = 0 WHERE LAUNCH_ID=? AND LAUNCH_KAM_ACCOUNT=? AND IS_ACTIVE = 1");
						kamMOCUpdate.setString(1, changeMocRequestKam.getLaunchId());
						kamMOCUpdate.setString(2, kamAccounts[i]);
						kamMOCUpdate.executeUpdate(); */
						//Sprint4Aug2021 changes - ends
						
						psKamMocChange.setString(1, changeMocRequestKam.getLaunchId());
						psKamMocChange.setString(2, changeMocRequestKam.getMocToChange());
						psKamMocChange.setString(3, kamAccounts[i]);
						//psKamMocChange.setInt(4, 1);  //Sprint4Aug2021 changes
						psKamMocChange.setInt(4, 0);    //Sprint4Aug2021 changes
						psKamMocChange.setString(5, userId);
						psKamMocChange.setTimestamp(6, new Timestamp(new Date().getTime()));
						psKamMocChange.setInt(7, reqId); // As part of Q4 Sprint inserting REQ_ID: Harsha
						psKamMocChange.executeUpdate();
					} catch (Exception e) {
						logger.error("Exception: " + e);
						return e.toString();
					}
				}
			}
			// Sarin Changes - Q1Sprint Feb2021 - Ends

			responseText = "Saved Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
		}
		return responseText;
	}
	// kavitha
	/*
	 * @Override public LaunchDataResponse requestChengeAccountByLaunchIdKam(int
	 * launchId,String userId) { Session session =
	 * sessionFactory.getCurrentSession(); SessionImpl sessionImpl = (SessionImpl)
	 * session; PreparedStatement stmt = null; ResultSet rs = null; try {
	 * LaunchDataResponse launchDataResponse = null; stmt =
	 * sessionImpl.connection().
	 * prepareStatement("SELECT ud.ACCOUNT_NAME,clu.CLUSTER_ACCOUNT "
	 * +" FROM tbl_vat_user_details ud,tbl_launch_clusters clu "
	 * +" WHERE ud.USERID='"+userId+"' AND clu.CLUSTER_LAUNCH_ID='" +launchId+ "'");
	 * rs = stmt.executeQuery(); while (rs.next()) { launchDataResponse = new
	 * LaunchDataResponse();
	 * launchDataResponse.setAccountName(rs.getString("ACCOUNT_NAME"));
	 * launchDataResponse.setKamAccount(rs.getString("CLUSTER_ACCOUNT")); } return
	 * launchDataResponse; } catch (Exception ex) { logger.debug("Exception :", ex);
	 * return null; } finally { try { stmt.close(); rs.close(); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 * 
	 * }
	 */
	// kavitha working code

	public List<String> getLaunchAccounts(String launchId, String userId) {

		List<String> listOfAccounts = new ArrayList<String>();
		String usrAccont = "";
		String lunchAccont = "";
		String[] usrAccountSplit = null;
		String[] kamAccountSplit;
		try {
			Query query3 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT ud.ACCOUNT_NAME,clu.CLUSTER_ACCOUNT "
							+ " FROM TBL_VAT_USER_DETAILS ud,TBL_LAUNCH_CLUSTERS clu " + " WHERE ud.USERID='" + userId
							+ "' AND clu.CLUSTER_LAUNCH_ID='" + launchId + "'");
			Iterator itr = query3.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = ((Object[]) itr.next());
				usrAccont = obj[0].toString();
				lunchAccont = obj[1].toString();
			}
			// System.out.println(usrAccont + ": " + lunchAccont);
			if (lunchAccont.equalsIgnoreCase("ALL CUSTOMERS")) {
				kamAccountSplit = usrAccont.split(",");
				for (int i = 0; i < kamAccountSplit.length; i++) {
					listOfAccounts.add(kamAccountSplit[i]);
				}
			} else {
				usrAccountSplit = usrAccont.split(",");
				kamAccountSplit = lunchAccont.split(",");
			}

			for (int i = 0; i < kamAccountSplit.length; i++) {
				// System.out.println(accountplit[i]);
				// System.out.println(accountsplit[0] + " " + accountsplit[1]);
				String[] accountsplit = kamAccountSplit[i].split(":");

				if ((usrAccountSplit != null) && (usrAccountSplit.length > 0)) {
					for (int j = 0; j < usrAccountSplit.length; j++) {
						if (usrAccountSplit[j].equalsIgnoreCase(accountsplit[0])) {
							listOfAccounts.add(accountsplit[0]);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}

		return listOfAccounts;

	}
	//Q4 Sprint --- For reject MOC option
	// Harsha's Implementation for removing already rejected store list after TME approval 
	
	public List<String> getLaunchAccountsforRejection(String launchId, String userId) { 
		 try {
			 
			 List<String> allAccounts = getLaunchAccounts(launchId,userId);
				Query query = sessionFactory.getCurrentSession().createNativeQuery(
						"select LAUNCH_KAM_ACCOUNT from TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS tlk , TBL_LAUNCH_REQUEST tlr " + 
						" where tlk.launch_id = tlr.LAUNCH_ID and tlk.req_id = tlr.req_id "
								+ "and IS_ACTIVE=2 and tlr.FINAL_STATUS='APPROVED' and tlr.CHANGES_REQUIRED = 'LAUNCH REJECTED' and tlr.launch_id = '"
								+ launchId + "'");

				List<String> list = query.list();
				if(!list.isEmpty() && list != null) {
					allAccounts.removeAll(list);
				}
				return allAccounts;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	
	

	// Q1 sprint kavitha feb2021
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMoc(List<LaunchDataResponse> listOfLaunch) {// Commented by Harsha As part of Sprint 7 changes
		try {

			
			/*Query query = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT LAUNCH_MOC FROM (SELECT CASE WHEN TLK.LAUNCH_MOC IS NULL THEN tlc.LAUNCH_MOC ELSE TLK.LAUNCH_MOC END AS LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc "
							+ "LEFT OUTER JOIN TBL_LAUNCH_MOC_KAM TLK ON TLK.LAUNCH_ID = tlc.LAUNCH_ID AND LAUNCH_ACCOUNT = '"
							+ userId + "' "
							+ "WHERE SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED NOT IN ('1','2') AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() )A "
							+ "ORDER BY concat(substr(LAUNCH_MOC, 3, 4), substr(LAUNCH_MOC, 1, 2))");*/
			// Commented by Harsha As part of Sprint 7 changes
			/*
			 * "SELECT DISTINCT LAUNCH_MOC FROM TBL_LAUNCH_MASTER tlc WHERE SAMPLE_SHARED IS NOT NULL "
			 * +
			 * " AND LAUNCH_REJECTED NOT IN ('1','2') AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW() "
			 * + " ORDER BY concat(substr(LAUNCH_MOC, 3, 4), substr(LAUNCH_MOC, 1, 2))");
			 */

			// List<String> list = query.list(); Commented by Harsha As part of Sprint 7 changes 
			List<String> finallist = new ArrayList<String>() ;
			
			for(LaunchDataResponse mocn : listOfLaunch) {
				finallist.add(mocn.getLaunchMoc());
			}
			
			Set<String> set = new LinkedHashSet<String>();
			set.addAll(finallist);
			finallist.clear();
			finallist.addAll(set);
			
			// return list; Previous implementation
			return finallist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String rejectBasepacksByBasepackIdsKam(RejectBasepackRequestKam rejectBasepackRequestKam, String userId) {
		String responseText = "";
		try {
			ResultSet rs = null;
			String[] basepackIds = rejectBasepackRequestKam.getBasePackIds();

			String basepackIdsStr = String.join(",", basepackIds);
			try {
				Session session = sessionFactory.getCurrentSession();
				SessionImpl sessionImpl = (SessionImpl) session;

				PreparedStatement stmt = sessionImpl.connection()
						.prepareStatement("SELECT LAUNCH_BASEPACK FROM TBL_LAUNCH_BASEPACK_KAM WHERE LAUNCH_ID = '"
								+ rejectBasepackRequestKam.getLaunchId() + "' AND LAUNCH_ACCOUNT = '" + userId + "'");
				ResultSet rs1 = stmt.executeQuery();
				if (rs1.next()) {
					String basepacksRejected = rs1.getString("LAUNCH_BASEPACK");
					List<String> a1 = Arrays.asList(basepacksRejected.split(","));
					List<String> a2 = Arrays.asList(basepackIds);
					List<String> finalList = new ArrayList<>();
					finalList.addAll(a1);
					finalList.addAll(a2);
					String citiesCommaSeparated = String.join(",", finalList);
					Query query2 = sessionFactory.getCurrentSession()
							.createNativeQuery("UPDATE TBL_LAUNCH_BASEPACK_KAM SET LAUNCH_BASEPACK='"
									+ citiesCommaSeparated + "',UPDATED_BY='" + userId + "',UPDATED_DATE='"
									+ new Timestamp(new Date().getTime()) + "' WHERE LAUNCH_ID='"
									+ rejectBasepackRequestKam.getLaunchId() + "' AND LAUNCH_ACCOUNT = '" + userId
									+ "'");
					query2.executeUpdate();
				} else {
					try (PreparedStatement preparedStatementInside = sessionImpl.connection()
							.prepareStatement(TBL_LAUNCH_BASEPACK_KAM, Statement.RETURN_GENERATED_KEYS)) {
						preparedStatementInside.setString(1, rejectBasepackRequestKam.getLaunchId());
						preparedStatementInside.setString(2, basepackIdsStr);
						preparedStatementInside.setString(3, userId);
						preparedStatementInside.setString(4, userId);
						preparedStatementInside.setTimestamp(5, new Timestamp(new Date().getTime()));
						preparedStatementInside.executeUpdate();
					} catch (Exception e) {
						logger.error("Exception: " + e);
						return e.toString();
					}
				}

				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_FOR_BASEPACK, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, rejectBasepackRequestKam.getLaunchId());
					preparedStatement.setString(2, "BASEPACK REJECTED");
					preparedStatement.setString(3, rejectBasepackRequestKam.getBasepackRejectComment());
					preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
					preparedStatement.setString(5, "REJECTED BY KAM");
					preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
					preparedStatement.setString(7, userId);
					preparedStatement.setString(8, "PENDING");
					preparedStatement.setString(9, basepackIdsStr);
					preparedStatement.executeUpdate();
					rs = preparedStatement.getGeneratedKeys();
					int reqId = 0;
					if (rs != null && rs.next()) {
						reqId = rs.getInt(1);
					}

					try (PreparedStatement preparedStatementInside = sessionImpl.connection()
							.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
						preparedStatementInside.setInt(1, reqId);
						preparedStatementInside.setString(2, userId);
						preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
						preparedStatementInside.setString(4, "REJECTED BY KAM");
						preparedStatementInside.setString(5, rejectBasepackRequestKam.getBasepackRejectComment());
						preparedStatementInside.executeUpdate();
					} catch (Exception e) {
						logger.error("Exception: " + e);
						return e.toString();
					}
					responseText = "REJECTED SUCCESSFULLY";
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				} finally {
					rs.close();
				}
			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			}
		} catch (Exception e) {
			responseText = e.toString();
			e.printStackTrace();
		}
		return responseText;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdKam(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchFinalPlanResponse> listOfGetLaunchBuildUpData = new ArrayList<>();
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"SELECT BUILDUP_SKU_NAME, BUILDUP_BASEPACK_CODE, STORE_COUNT, BUILDUP_LAUNCH_SELLIN_VALUE,"
							+ " BUILDUP_LAUNCH_SELLIN_N1, BUILDUP_LAUNCH_SELLIN_N2, BUILDUP_LAUNCH_SELLIN_CLDS,"
							+ " BUILDUP_LAUNCH_SELLIN_CLDS_N1, BUILDUP_LAUNCH_SELLIN_CLDS_N2, BUILDUP_LAUNCH_SELLIN_UNITS, "
							+ " BUILDUP_LAUNCH_SELLIN_UNITS_N1, BUILDUP_LAUNCH_SELLIN_UNITS_N2 FROM TBL_LAUNCH_FINAL_BUILDUP"
							+ " WHERE BUILDUP_LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchFinalPlanResponse getLaunchBuildUpData = new LaunchFinalPlanResponse();
				getLaunchBuildUpData.setSkuName(rs.getString("BUILDUP_SKU_NAME"));
				getLaunchBuildUpData.setBasepackCode(rs.getString("BUILDUP_BASEPACK_CODE"));
				getLaunchBuildUpData.setStoreCount(rs.getString("STORE_COUNT"));
				getLaunchBuildUpData.setLaunchSellInValue(rs.getString("BUILDUP_LAUNCH_SELLIN_VALUE"));
				getLaunchBuildUpData.setLaunchN1SellInVal(rs.getString("BUILDUP_LAUNCH_SELLIN_N1"));
				getLaunchBuildUpData.setLaunchN2SellInVal(rs.getString("BUILDUP_LAUNCH_SELLIN_N2"));
				getLaunchBuildUpData.setLaunchSellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS"));
				getLaunchBuildUpData.setLaunchN1SellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS_N1"));
				getLaunchBuildUpData.setLaunchN2SellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS_N2"));
				getLaunchBuildUpData.setLaunchSellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS"));
				getLaunchBuildUpData.setLaunchN1SellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS_N1"));
				getLaunchBuildUpData.setLaunchN2SellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS_N2"));
				listOfGetLaunchBuildUpData.add(getLaunchBuildUpData);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchFinalPlanResponse getLaunchBuildUpData = new LaunchFinalPlanResponse();
			getLaunchBuildUpData.setError(ex.toString());
			listOfGetLaunchBuildUpData.add(getLaunchBuildUpData);
		}
		return listOfGetLaunchBuildUpData;
	}

	@Override
	public List<LaunchFinalPlanResponse> getLaunchBuildUpByLaunchIdTme(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchFinalPlanResponse> listOfGetLaunchBuildUpData = new ArrayList<>();
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"SELECT BUILDUP_SKU_NAME, BUILDUP_BASEPACK_CODE, STORE_COUNT, BUILDUP_LAUNCH_SELLIN_VALUE,"
							+ " BUILDUP_LAUNCH_SELLIN_N1, BUILDUP_LAUNCH_SELLIN_N2, BUILDUP_LAUNCH_SELLIN_CLDS,"
							+ " BUILDUP_LAUNCH_SELLIN_CLDS_N1, BUILDUP_LAUNCH_SELLIN_CLDS_N2, BUILDUP_LAUNCH_SELLIN_UNITS, "
							+ " BUILDUP_LAUNCH_SELLIN_UNITS_N1, BUILDUP_LAUNCH_SELLIN_UNITS_N2 FROM TBL_LAUNCH_FINAL_BUILDUP"
							+ " WHERE BUILDUP_LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchFinalPlanResponse getLaunchBuildUpData = new LaunchFinalPlanResponse();
				getLaunchBuildUpData.setSkuName(rs.getString("BUILDUP_SKU_NAME"));
				getLaunchBuildUpData.setBasepackCode(rs.getString("BUILDUP_BASEPACK_CODE"));
				getLaunchBuildUpData.setStoreCount(rs.getString("STORE_COUNT"));
				getLaunchBuildUpData.setLaunchSellInValue(rs.getString("BUILDUP_LAUNCH_SELLIN_VALUE"));
				getLaunchBuildUpData.setLaunchN1SellInVal(rs.getString("BUILDUP_LAUNCH_SELLIN_N1"));
				getLaunchBuildUpData.setLaunchN2SellInVal(rs.getString("BUILDUP_LAUNCH_SELLIN_N2"));
				getLaunchBuildUpData.setLaunchSellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS"));
				getLaunchBuildUpData.setLaunchN1SellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS_N1"));
				getLaunchBuildUpData.setLaunchN2SellInCld(rs.getString("BUILDUP_LAUNCH_SELLIN_CLDS_N2"));
				getLaunchBuildUpData.setLaunchSellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS"));
				getLaunchBuildUpData.setLaunchN1SellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS_N1"));
				getLaunchBuildUpData.setLaunchN2SellInUnit(rs.getString("BUILDUP_LAUNCH_SELLIN_UNITS_N2"));
				listOfGetLaunchBuildUpData.add(getLaunchBuildUpData);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchFinalPlanResponse getLaunchBuildUpData = new LaunchFinalPlanResponse();
			getLaunchBuildUpData.setError(ex.toString());
			listOfGetLaunchBuildUpData.add(getLaunchBuildUpData);
		}
		return listOfGetLaunchBuildUpData;
	}
	
	// Harsha added method to check modified moc dates As part of Q4 Sprint
	public String getIfMOCisModified(String Account,String userId, String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String launchMoc = "";
		try {
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT LAUNCH_MOC_KAM FROM TBL_LAUNCH_KAM_CHANGE_MOC_DETAILS"
							+ " WHERE IS_ACTIVE = 1 AND  LAUNCH_KAM_ACCOUNT= '" 
							+ Account + "'"  
					+ " AND LAUNCH_ID = '" + launchId + "'" + " AND UPDATED_BY='" + userId +"'");		
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
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SaveLaunchStoreList> getLaunchStoresBuildUpByLaunchIdKam(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<SaveLaunchStoreList> listOfGetLaunchStoreData = new ArrayList<>();
		String kamMailId = userId.concat("@unilever.com").toUpperCase();
		
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"select DISTINCT tlm.LAUNCH_NAME LAUNCH_NAME,tlm.LAUNCH_MOC LAUNCH_MOC,abc.ACCOUNT_NAME ACCOUNT_NAME, ACCOUNT_NAME_L2, HUL_STORE_FORMAT, CLUSTER, REPORTING_CODE, tvcom.KAM_MAIL_ID "
							+ "from MODTRD.TBL_LAUNCH_BUILDUP_TEMP abc,TBL_VAT_COMM_OUTLET_MASTER tvcom ,TBL_LAUNCH_MASTER tlm WHERE "
							+ "tvcom.HUL_OUTLET_CODE = abc.HFS_CODE AND tlm.launch_id = abc.LAUNCH_ID AND abc.LAUNCH_ID = '" + launchId
							//+ "' AND UPPER(tvcom.KAM_MAIL_ID) = '" + kamMailId + "'");  //Commented & Added below By Sarin 13Oct2021
							+ "' AND UPPER(tvcom.KAM_MAIL_ID) LIKE '%" + kamMailId + "%'");  //Added By Sarin 13Oct2021
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Query query3 = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT LAUNCH_STORES FROM TBL_LAUNCH_STORE_KAM WHERE LAUNCH_ACCOUNT = '"
								+ userId + "' AND LAUNCH_ID = '" + launchId + "'");
				List<String> listOfStore = query3.list();
				List<String> storeIds = new ArrayList<>();
				if (!listOfStore.isEmpty()) {
					storeIds = Arrays.asList(listOfStore.get(0).toString().split(","));
				}

				
				SaveLaunchStoreList saveLaunchStoreList = new SaveLaunchStoreList();
				
				if (!storeIds.contains(rs.getString("REPORTING_CODE").toString())) {
					
					saveLaunchStoreList.setL1_Chain(rs.getString("ACCOUNT_NAME"));
					// Harsha's logic to pick modified date and Launch_Name
					saveLaunchStoreList.setLaunchName(rs.getString("LAUNCH_NAME"));
					String modifiedDate= getIfMOCisModified(rs.getString("ACCOUNT_NAME"),userId, launchId);
					if( modifiedDate!= null && !modifiedDate.isEmpty()) {
						saveLaunchStoreList.setMocDate(modifiedDate);
					}
					else {
						saveLaunchStoreList.setMocDate(rs.getString("LAUNCH_MOC"));
					}
					//Harsha's Logic ends here
					saveLaunchStoreList.setL2_Chain(rs.getString("ACCOUNT_NAME_L2"));
					saveLaunchStoreList.setStoreFormat(rs.getString("HUL_STORE_FORMAT"));
					saveLaunchStoreList.setCluster(rs.getString("CLUSTER"));
					saveLaunchStoreList.setHUL_OL_Code(rs.getString("REPORTING_CODE"));
					saveLaunchStoreList.setKam_Remarks("Accepted");
					listOfGetLaunchStoreData.add(saveLaunchStoreList);
				}
				
				else  {
					saveLaunchStoreList.setL1_Chain(rs.getString("ACCOUNT_NAME"));
					// Harsha's logic to pick modified date and Launch_Name
					saveLaunchStoreList.setLaunchName(rs.getString("LAUNCH_NAME"));
					String modifiedDate= getIfMOCisModified(rs.getString("ACCOUNT_NAME"),userId, launchId);
					if( modifiedDate!= null && !modifiedDate.isEmpty()) {
						saveLaunchStoreList.setMocDate(modifiedDate);
					}
					else {
						saveLaunchStoreList.setMocDate(rs.getString("LAUNCH_MOC"));
					}
					//Harsha's Logic ends here
					saveLaunchStoreList.setL2_Chain(rs.getString("ACCOUNT_NAME_L2"));
					saveLaunchStoreList.setStoreFormat(rs.getString("HUL_STORE_FORMAT"));
					saveLaunchStoreList.setCluster(rs.getString("CLUSTER"));
					saveLaunchStoreList.setHUL_OL_Code(rs.getString("REPORTING_CODE"));
					saveLaunchStoreList.setKam_Remarks("Rejected");
					listOfGetLaunchStoreData.add(saveLaunchStoreList);
				}
			}
			
		
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
		}
		return listOfGetLaunchStoreData;
	}

	@Override
	public String saveLaunchStores(String userId, SaveLaunchStore saveFinalLaunchListRequest, String launchId) {
		ResultSet rs = null;
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection().prepareStatement("DELETE from TBL_LAUNCH_STORE where LAUNCH_ID=?");
			batchUpdate.setString(1, launchId);
			batchUpdate.executeUpdate();
			List<SaveLaunchStoreList> listBuildUps = saveFinalLaunchListRequest.getListOfFinalLaunch();
			for (SaveLaunchStoreList SaveLaunchStoreList : listBuildUps) {
				try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_STORE,
						Statement.RETURN_GENERATED_KEYS)) {
					if (SaveLaunchStoreList.getKam_Remarks().equalsIgnoreCase("REJECTED")) {
						preparedStatement.setString(1, SaveLaunchStoreList.getL1_Chain());
						preparedStatement.setString(2, SaveLaunchStoreList.getL2_Chain());
						preparedStatement.setString(3, SaveLaunchStoreList.getStoreFormat());
						preparedStatement.setString(4, SaveLaunchStoreList.getCluster());
						preparedStatement.setString(5, SaveLaunchStoreList.getHUL_OL_Code());
						preparedStatement.setString(6, SaveLaunchStoreList.getKam_Remarks());
						preparedStatement.setString(7, userId);
						preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));
						preparedStatement.setString(9, launchId);
						preparedStatement.executeUpdate();
						rs = preparedStatement.getGeneratedKeys();
						if (rs != null && rs.next()) {
							System.out.println("Generated Emp Id: " + rs.getInt(1));
						}
					}
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		} finally {
			try {
				batchUpdate.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.toString();
			}
		}

		return "Saved Successfully";
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArrayList<String>> getUpdatedBaseFile(ArrayList<String> headerList, String launchId, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<>();
		
		
		try {
			String kamMailId = userId.concat("@unilever.com").toUpperCase();
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery( //Removing creator from select statement
					"select DISTINCT tlm.LAUNCH_NAME LAUNCH_NAME,tlm.LAUNCH_MOC LAUNCH_MOC,abc.ACCOUNT_NAME ACCOUNT_NAME, ACCOUNT_NAME_L2, HUL_STORE_FORMAT, CLUSTER, REPORTING_CODE "
							+ "from MODTRD.TBL_LAUNCH_BUILDUP_TEMP abc,TBL_VAT_COMM_OUTLET_MASTER tvcom ,TBL_LAUNCH_MASTER tlm WHERE "
							+ "tvcom.HUL_OUTLET_CODE = abc.HFS_CODE AND tlm.launch_id = abc.LAUNCH_ID AND abc.LAUNCH_ID = '" + launchId
							//+ "' AND UPPER(tvcom.KAM_MAIL_ID) = '" + kamMailId + "'");     //Commented & Added below By Sarin 13Oct2021
							+ "' AND UPPER(tvcom.KAM_MAIL_ID) LIKE '%" + kamMailId + "%'");  //Added By Sarin 13Oct2021

			Iterator<Object> itr = query2.list().iterator();
			ArrayList<String> s = new ArrayList<>();
			if (itr.hasNext()) {
				downloadDataList.add(headerList);
			} else {
				s.add("no data available for given launch key");
				downloadDataList.add(s);
			}

			Query query3 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT LAUNCH_STORES FROM TBL_LAUNCH_STORE_KAM WHERE UPPER(LAUNCH_ACCOUNT) = '"
							+ userId.toUpperCase() + "' AND LAUNCH_ID = '" + launchId + "'");
			List<String> listOfStore = query3.list();
			List<String> storeIds = new ArrayList<>();
			if (!listOfStore.isEmpty()) {
				storeIds = Arrays.asList(listOfStore.get(0).toString().split(","));
			}
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (!storeIds.contains(obj[4].toString())) {
					ArrayList<String> dataObj = new ArrayList<String>();
					for (Object ob : obj) {
						String value = "";
						value = (ob == null) ? "" : ob.toString();
						dataObj.add(value.replaceAll("\\^", ","));
					}
					// Harsha'S logic Starts Here for Sprint Q4
					int count =0;
					for(String answer : dataObj) {
						if(count == 2) {
							if(getIfMOCisModified( answer, userId,  launchId)!=null && !getIfMOCisModified( answer, userId,  launchId).isEmpty()) {
								String replaceDate = getIfMOCisModified( answer, userId,  launchId);
								dataObj.set(1, replaceDate);
							}
							else {
								String replaceDate = dataObj.set(1, dataObj.get(1));
							}
						}
						count++;
					}
					// Harsha's Logic Ends here
					obj = null;
					downloadDataList.add(dataObj);
				}
			}
			return downloadDataList;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}
	
	// Added by Harsha for Sprint 7 22 Jan - Starts
	
	public List<String> check (List<Object> saveLaunchStorelist) {
		
		Iterator<Object> iterator0 = saveLaunchStorelist.iterator();
		 HashSet<String> hashset = new HashSet<>();   
		 List<String> list=new ArrayList<String>();  
		 List<String> list1=new ArrayList<String>();
		 
		 while (iterator0.hasNext()) {
				SaveUploadededLaunchStore obj = (SaveUploadededLaunchStore) iterator0.next();
				String wholevalue="";
				wholevalue=obj.getL1_Chain()+":"+obj.getKam_Remarks()+":"+obj.getCluster();
				list1.add(wholevalue);
			}
		 
		Iterator<Object> iterator1 = saveLaunchStorelist.iterator();
		while (iterator1.hasNext()) {
			SaveUploadededLaunchStore obj = (SaveUploadededLaunchStore) iterator1.next();
			hashset.add(obj.getL1_Chain().toString()+":"+obj.getCluster().toString());
		}
		
		
		for(String account : hashset) {
			int count =0;
			String[] accountCluster=account.split(":");
			String accountName=accountCluster[0];
			String Cluster =accountCluster[1];
			 for(String values : list1) {
				 String[] words=values.split(":");//splits the string  
				 String kamRemarks = words[1].toLowerCase();

				 if(values.contains(accountName) && (!kamRemarks.equals("rejected")) && values.contains(Cluster)) {
					 count++; 
				 }
			 }
			
		
			list.add(account+":"+count);
		}
		
		return list;
		
	}


	
	public boolean checkLimitofStores(List<String> list1, String launchId) {
		for(String consider : list1) {
			
			String[] words=consider.split(":");
			
			String accountName = words[0].toString();
			
			String clusterName = words[1].toString();
			
			String totalCount=words[2].toString();
			
			int minmumStoresbyTME =ValidateSumof(launchId,accountName,clusterName);
			
			int KAMStores = Integer.valueOf(words[2].toString());
			if(minmumStoresbyTME>KAMStores) {
				return false;
			}

		}
		return true;
		
	}
	
	
	private int ValidateSumof(String launchId,String account, String clusterName) {
		Integer iValid = 0;
		Query query = null;
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		//String result = null;
		String qryValidate = "SELECT SUM(TOTAL_TME_STORES_PLANED) FROM TBL_LAUNCH_CLUSTERS_DETAILS WHERE CLUSTER_ACCOUNT_L1 IN ( '"+account+"') and LAUNCH_ID="+launchId+" AND CLUSTER_REGION LIKE ('%"+clusterName+"%')";
		try {
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement(qryValidate);
			ResultSet result = stmt.executeQuery();
		     result.next();
		     String sum = result.getString(1);
		     iValid = (int) Double.parseDouble(sum);
		     
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return iValid;
	}
	
	
	
	// Added by Harsha for Sprint 7 22 Jan - Ends
	
	@Override
	@Transactional
	public String saveStoreListByUpload(List<Object> saveLaunchStorelist, String userId, String status,
			boolean isCreate, boolean isFromUi, String launchId) throws Exception {
		Iterator<Object> iterator = saveLaunchStorelist.iterator();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String result = null;
		int count = 0;
		List<Object> valuesInput = saveLaunchStorelist;
		//check (saveLaunchStorelist);
		
		boolean KAMlimit=checkLimitofStores(check(valuesInput),launchId);// Added by harsha to check minimum stores are met

	
		
		if(KAMlimit) {		
			if (!saveLaunchStorelist.isEmpty()) {
			List<String> listOfStores = new ArrayList<>();
			while (iterator.hasNext()) {
				SaveUploadededLaunchStore obj = (SaveUploadededLaunchStore) iterator.next();
				if (obj.getKam_Remarks().equalsIgnoreCase("rejected")) {
					listOfStores.add(obj.getHUL_OL_Code());
				}
			}

			if (!listOfStores.isEmpty()) {
				String listOfStoresCommaSap = String.join(",", listOfStores);
				PreparedStatement stmt = sessionImpl.connection()
						.prepareStatement("SELECT LAUNCH_STORES FROM TBL_LAUNCH_STORE_KAM WHERE LAUNCH_ID = '"
								+ launchId + "' AND LAUNCH_ACCOUNT = '" + userId + "'");
				ResultSet rs1 = stmt.executeQuery();
				if (rs1.next()) {
					String storesRejected = rs1.getString("LAUNCH_STORES");
					List<String> a1 = Arrays.asList(storesRejected.split(","));
					List<String> finalList = new ArrayList<>();
					finalList.addAll(a1);
					finalList.addAll(listOfStores);
					String storesCommaSeparated = String.join(",", finalList);
					Query query2 = sessionFactory.getCurrentSession()
							.createNativeQuery("UPDATE TBL_LAUNCH_STORE_KAM SET LAUNCH_STORES='" + storesCommaSeparated
									+ "',UPDATED_BY='" + userId + "',UPDATED_DATE='"
									+ new Timestamp(new Date().getTime()) + "' WHERE LAUNCH_ID='" + launchId
									+ "' AND LAUNCH_ACCOUNT = '" + userId + "'");
					query2.executeUpdate();
				} else {
					try (PreparedStatement preparedStatementInside = sessionImpl.connection()
							.prepareStatement(TBL_LAUNCH_STORE_KAM, Statement.RETURN_GENERATED_KEYS)) {
						preparedStatementInside.setString(1, launchId);
						preparedStatementInside.setString(2, listOfStoresCommaSap);
						preparedStatementInside.setString(3, userId);
						preparedStatementInside.setString(4, userId);
						preparedStatementInside.setTimestamp(5, new Timestamp(new Date().getTime()));
						preparedStatementInside.executeUpdate();
					} catch (Exception e) {
						logger.error("Exception: " + e);
						return e.toString();
					}
				}

				ResultSet rs = null;
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_FOR_BASEPACK, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, launchId);
					preparedStatement.setString(2, "STORE REJECTED");
					preparedStatement.setString(3, "STORE REJECTED");
					preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
					preparedStatement.setString(5, "REJECTED BY KAM");
					preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
					preparedStatement.setString(7, userId);
					preparedStatement.setString(8, "PENDING");
					preparedStatement.setString(9, listOfStoresCommaSap);
					preparedStatement.executeUpdate();
					rs = preparedStatement.getGeneratedKeys();
					int reqId = 0;
					if (rs != null && rs.next()) {
						reqId = rs.getInt(1);
					}

					try (PreparedStatement preparedStatementInside = sessionImpl.connection()
							.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
						preparedStatementInside.setInt(1, reqId);
						preparedStatementInside.setString(2, userId);
						preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
						preparedStatementInside.setString(4, "REJECTED BY KAM");
						preparedStatementInside.setString(5, "STORE REJECTED");
						preparedStatementInside.executeUpdate();
					} catch (Exception e) {
						logger.error("Exception: " + e);
						return e.toString();
					}
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				} finally {
					rs.close();
				}
			}

			result = "Saved Successfully";
		}
		}
		
		else { //Added by Harsha
		result="Minimum targeted stores should be approved by KAM";
		}


		if (result.equals("Saved Successfully")) {
			return "SUCCESS_FILE";
		} 
		else if (result.equals("Minimum targeted stores should be approved by KAM")) {//Added by Harsha
			return "Minimum targeted stores should be approved by KAM";
		}
		
		else {
			return "ERROR";
		}
	}

	@Override
	public List<LaunchVisiPlanning> getLaunchVisiListByLaunchIdKam(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchVisiPlanning> listOfLaunchVisiPlanningData = new ArrayList<>();
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"SELECT VISIPLAN_STORES_PLANNED,VISIPLAN_VISI_ASSET_1,VISIPLAN_FACINGS_1,VISIPLAN_DEPTH_1, "
							+ "VISIPLAN_VISI_ASSET_2, VISIPLAN_FACINGS_2,VISIPLAN_DEPTH_2, VISIPLAN_VISI_ASSET_3, "
							+ "VISIPLAN_FACINGS_3, VISIPLAN_DEPTH_3, VISIPLAN_VISI_ASSET_4, VISIPLAN_FACINGS_4, VISIPLAN_DEPTH_4, "
							+ "VISIPLAN_VISI_ASSET_5, VISIPLAN_FACINGS_5, VISIPLAN_DEPTH_5 FROM TBL_LAUNCH_VISIPLAN tlv WHERE "
							+ "VISIPLAN_LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchVisiPlanning launchVisiPlanning = new LaunchVisiPlanning();
				launchVisiPlanning.setSTORES_PLANNED(rs.getString("VISIPLAN_STORES_PLANNED"));
				launchVisiPlanning.setVISI_ASSET_1(rs.getString("VISIPLAN_VISI_ASSET_1"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1(rs.getString("VISIPLAN_FACINGS_1"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1(rs.getString("VISIPLAN_DEPTH_1"));
				launchVisiPlanning.setVISI_ASSET_2(rs.getString("VISIPLAN_VISI_ASSET_2"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2(rs.getString("VISIPLAN_FACINGS_2"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2(rs.getString("VISIPLAN_DEPTH_2"));
				launchVisiPlanning.setVISI_ASSET_3(rs.getString("VISIPLAN_VISI_ASSET_3"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3(rs.getString("VISIPLAN_FACINGS_3"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3(rs.getString("VISIPLAN_DEPTH_3"));
				launchVisiPlanning.setVISI_ASSET_4(rs.getString("VISIPLAN_VISI_ASSET_4"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4(rs.getString("VISIPLAN_FACINGS_4"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4(rs.getString("VISIPLAN_DEPTH_4"));
				launchVisiPlanning.setVISI_ASSET_5(rs.getString("VISIPLAN_VISI_ASSET_5"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5(rs.getString("VISIPLAN_FACINGS_5"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5(rs.getString("VISIPLAN_DEPTH_5"));
				listOfLaunchVisiPlanningData.add(launchVisiPlanning);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchVisiPlanning launchVisiPlanning = new LaunchVisiPlanning();
			launchVisiPlanning.setError(ex.toString());
			listOfLaunchVisiPlanningData.add(launchVisiPlanning);
		}
		return listOfLaunchVisiPlanningData;
	}

	@Override
	public String saveLaunchVisiListByLaunchIdKam(SaveVisiRequestVatKamList saveVisiRequestVatKamList, String userId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			PreparedStatement batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_VAT_VISI_PLANNING where VAT_VISI_LAUNCH_ID=?");
			batchUpdate.setInt(1, saveVisiRequestVatKamList.getLaunchId());
			batchUpdate.executeUpdate();
			for (SaveVisiRequestVatKam saveVisiRequestVatKam : saveVisiRequestVatKamList
					.getListOfSaveVisiRequestVatKam()) {
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_VAT_VISI_PLANNING, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setInt(1, saveVisiRequestVatKamList.getLaunchId());
					preparedStatement.setString(2, saveVisiRequestVatKam.getStoresPlanned());
					preparedStatement.setString(3, saveVisiRequestVatKam.getVisiAsset1());
					preparedStatement.setString(4, saveVisiRequestVatKam.getFacingPerShelfPerSku1());
					preparedStatement.setString(5, saveVisiRequestVatKam.getDepthPerShelfPerSku1());
					preparedStatement.setString(6, saveVisiRequestVatKam.getVisiAsset2());
					preparedStatement.setString(7, saveVisiRequestVatKam.getFacingPerShelfPerSku2());
					preparedStatement.setString(8, saveVisiRequestVatKam.getDepthPerShelfPerSku2());
					preparedStatement.setString(9, saveVisiRequestVatKam.getVisiAsset3());
					preparedStatement.setString(10, saveVisiRequestVatKam.getFacingPerShelfPerSku3());
					preparedStatement.setString(11, saveVisiRequestVatKam.getDepthPerShelfPerSku3());
					preparedStatement.setString(12, saveVisiRequestVatKam.getVisiAsset4());
					preparedStatement.setString(13, saveVisiRequestVatKam.getFacingPerShelfPerSku4());
					preparedStatement.setString(14, saveVisiRequestVatKam.getDepthPerShelfPerSku4());
					preparedStatement.setString(15, saveVisiRequestVatKam.getVisiAsset5());
					preparedStatement.setString(16, saveVisiRequestVatKam.getFacingPerShelfPerSku5());
					preparedStatement.setString(17, saveVisiRequestVatKam.getDepthPerShelfPerSku5());
					preparedStatement.setString(18, userId);
					preparedStatement.setTimestamp(19, new Timestamp(new Date().getTime()));
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		}

		return "Saved Successfully";
	}

	@Override
	public String missingDetailsKamInput(MissingDetailsKamInput missingDetailsKamInput, String userId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			ResultSet rs = null;
			try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_REQUEST,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, missingDetailsKamInput.getLaunchId());
				preparedStatement.setString(2, "MISSING DETAILS");
				preparedStatement.setString(3, missingDetailsKamInput.getMissingDetails());
				preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
				preparedStatement.setString(5, "REJECTED BY KAM");
				preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
				preparedStatement.setString(7, userId);
				preparedStatement.setString(8, "PENDING");
				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				int reqId = 0;
				if (rs != null && rs.next()) {
					reqId = rs.getInt(1);
				}
				try (PreparedStatement preparedStatementInside = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatementInside.setInt(1, reqId);
					preparedStatementInside.setString(2, userId);
					preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
					preparedStatementInside.setString(4, "REQUESTED BY KAM");
					preparedStatementInside.setString(5, missingDetailsKamInput.getMissingDetails());
					preparedStatementInside.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
					return e.toString();
				}

			} catch (Exception e) {
				logger.error("Exception: " + e);
				return e.toString();
			} finally {
				rs.close();
			}

		} catch (Exception e) {
			logger.error("Exception: " + e);
			return e.toString();
		}
		return "Requested Successfully";
	}

	@SuppressWarnings("unchecked")
	@Override
	// Q2 sprint feb 2021 kavitha
	public List<KamChangeReqRemarks> getApprovalStatusKam(String userId, String approvalLaunchMOC,
			String approvalKamStauts, int FromApproval) {
		// public List<KamChangeReqRemarks> getApprovalStatusKam(String userId)
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<KamChangeReqRemarks> listOfKamChangeReqRemarks = new ArrayList<>();
		if (approvalLaunchMOC.equalsIgnoreCase("All")) {
			approvalLaunchMOC = "";
		}
		if (approvalKamStauts.equalsIgnoreCase("All")) {
			approvalKamStauts = "";
		}
		try {
			PreparedStatement stmt = sessionImpl.connection().prepareStatement(
					"SELECT tlm.LAUNCH_NAME,tlm.LAUNCH_MOC, DATE_FORMAT(REQ_DATE, '%b %d, %Y') AS REQ_DATE,CHANGES_REQUIRED CHANGES_REQUESTED,KAM_REMARKS,tlr.UPDATED_BY "
							+ " CMM, DATE_FORMAT(tlr.UPDATED_DATE, '%b %d, %Y') AS RESPONSE_DATE,tlr.FINAL_STATUS APPROVAL_STATUS,TME_REMARKS CMM_REMARKS, tlr.CREATED_BY,CASE WHEN tlr.UPDATED_DATE >= tmc.UPDATED_DATE THEN 'NEW' ELSE 'OLD' END AS LAUNCH_READ_STATUS FROM"
							+ " TBL_LAUNCH_REQUEST tlr,TBL_LAUNCH_MASTER tlm ,TBL_VAT_USER_NOTIFICATIONS tmc WHERE tlr.LAUNCH_ID = tlm.LAUNCH_ID AND tlr.CREATED_BY = '"
							+ userId + "' AND tmc.USER_ID = '" + userId + "' AND tlm.LAUNCH_MOC LIKE '%"
							+ approvalLaunchMOC + "%' AND tlr.FINAL_STATUS  LIKE '%" + approvalKamStauts
							+ "%' ORDER By tlr.UPDATED_DATE desc ");

			// updateConfigValue.executeUpdate();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				KamChangeReqRemarks kamChangeReqRemarks = new KamChangeReqRemarks();
				kamChangeReqRemarks.setLaunchName(rs.getString("LAUNCH_NAME"));
				kamChangeReqRemarks.setLaunchMoc(rs.getString("LAUNCH_MOC"));
				kamChangeReqRemarks.setReqDate(rs.getString("REQ_DATE"));
				String kamMailId = rs.getString("CREATED_BY").concat("@unilever.com").toUpperCase();
				Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
						"SELECT DISTINCT ACCOUNT_NAME FROM MODTRD.TBL_VAT_COMM_OUTLET_MASTER WHERE UPPER(KAM_MAIL_ID) = '"
								+ kamMailId + "'");
				String listOfAccounts = String.join(",", query2.list());

				String accounts = "";
				if (!listOfAccounts.isEmpty()) {
					accounts = listOfAccounts.toString();
				}
				kamChangeReqRemarks.setAccount(accounts);
				kamChangeReqRemarks.setChangeRequested(rs.getString("CHANGES_REQUESTED"));
				kamChangeReqRemarks.setKamRemarks(rs.getString("KAM_REMARKS"));
				kamChangeReqRemarks.setCmm(replaceNA(rs.getString("CMM")));
				kamChangeReqRemarks.setResponseDate(replaceNA(rs.getString("RESPONSE_DATE")));
				kamChangeReqRemarks.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
				kamChangeReqRemarks.setCmmRemarks(replaceNA(rs.getString("CMM_REMARKS")));
				kamChangeReqRemarks.setLaunchReadStatus(rs.getString("LAUNCH_READ_STATUS"));
				listOfKamChangeReqRemarks.add(kamChangeReqRemarks);
			}

			if (FromApproval == 1) {
				updateUserNotifications(userId);
			}

		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			KamChangeReqRemarks kamChangeReqRemarks = new KamChangeReqRemarks();
			kamChangeReqRemarks.setError(ex.toString());
			listOfKamChangeReqRemarks.add(kamChangeReqRemarks);
		}
		return listOfKamChangeReqRemarks;
	}

	@Override
	public String updateLaunchSampleShared(SampleSharedReqKam sampleSharedReqKam, String userId) {
		String toReturn = "";
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		ResultSet rs = null;
		try (PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement(TBL_LAUNCH_REQUEST,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, sampleSharedReqKam.getLaunchId());
			preparedStatement.setString(2, "LAUNCH SAMPLE SHARED CHANGED");
			preparedStatement.setString(3, sampleSharedReqKam.getRemark());
			preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
			preparedStatement.setString(5, "REJECTED BY KAM");
			preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
			preparedStatement.setString(7, userId);
			preparedStatement.setString(8, "PENDING");
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			int reqId = 0;
			if (rs != null && rs.next()) {
				reqId = rs.getInt(1);
			}
			try (PreparedStatement preparedStatementInside = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_REQUEST_HISTORY, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatementInside.setInt(1, reqId);
				preparedStatementInside.setString(2, userId);
				preparedStatementInside.setTimestamp(3, new Timestamp(new Date().getTime()));
				preparedStatementInside.setString(4, "REJECTED BY KAM");
				preparedStatementInside.setString(5, sampleSharedReqKam.getRemark());
				preparedStatementInside.executeUpdate();
			} catch (Exception e) {
				logger.error("Exception: " + e);
				toReturn = e.toString();
			}
			toReturn = "Saved Successfully";
		} catch (Exception e) {
			logger.error("Exception: " + e);
			toReturn = e.toString();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				toReturn = e.toString();
			}
		}
		return toReturn;
	}

	@Override
	public LaunchDataResponse getSpecificLaunchDataKam(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		LaunchDataResponse launchDataResponse = new LaunchDataResponse();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement stmt1 = null;
		ResultSet rs1 = null;
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

				String sampleShared = null;
				stmt1 = sessionImpl.connection().prepareStatement(
						"SELECT LAUNCH_SAMPLE_SHARED FROM TBL_LAUNCH_SAMPLE_SHARED_KAM WHERE LAUNCH_ID = '"
								+ rs.getInt("LAUNCH_ID") + "' AND LAUNCH_ACCOUNT = '" + userId + "'");
				rs1 = stmt1.executeQuery();
				if (rs1.next()) {
					sampleShared = rs1.getString("LAUNCH_SAMPLE_SHARED");
				} else {
					sampleShared = rs.getString(12);
				}
				launchDataResponse.setSampleShared(sampleShared);
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
	public List<LaunchMstnClearanceResponseKam> getMstnClearanceByLaunchIdKam(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchMstnClearanceResponseKam> listOfLaunchMstnClearanceResponseKam = new ArrayList<>();
		try {
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT BASEPACK_CODE, BASEPACK_DESCRIPTION, DEPOT, CLUSTER, MSTN_CLEARED, "
							+ " FINAL_CLD_FOR_N, FINAL_CLD_FOR_N1, FINAL_CLD_FOR_N2, CURRENT_ESTIMATES, CLEARANCE_DATE"
							+ " FROM TBL_LAUNCH_MSTN_CLEARANCE WHERE LAUNCH_ID = '" + launchId + "'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchMstnClearanceResponseKam launchMstnClearanceResponseKam = new LaunchMstnClearanceResponseKam();
				launchMstnClearanceResponseKam.setBpCode(rs.getString("BASEPACK_CODE"));
				launchMstnClearanceResponseKam.setBpDescription(rs.getString("BASEPACK_DESCRIPTION"));
				launchMstnClearanceResponseKam.setDepot(rs.getString("DEPOT"));
				launchMstnClearanceResponseKam.setCluster(rs.getString("CLUSTER"));
				launchMstnClearanceResponseKam.setMstnCleared(rs.getString("MSTN_CLEARED"));
				launchMstnClearanceResponseKam.setFinalCldN(rs.getString("FINAL_CLD_FOR_N"));
				launchMstnClearanceResponseKam.setFinalCldN1(rs.getString("FINAL_CLD_FOR_N1"));
				launchMstnClearanceResponseKam.setFinalCldN2(rs.getString("FINAL_CLD_FOR_N2"));
				launchMstnClearanceResponseKam.setCurrentEstimates(rs.getString("CURRENT_ESTIMATES"));
				launchMstnClearanceResponseKam.setClearanceDate(rs.getString("CLEARANCE_DATE"));
				listOfLaunchMstnClearanceResponseKam.add(launchMstnClearanceResponseKam);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchMstnClearanceResponseKam launchMstnClearanceResponseKam = new LaunchMstnClearanceResponseKam();
			launchMstnClearanceResponseKam.setError(ex.toString());
			listOfLaunchMstnClearanceResponseKam.add(launchMstnClearanceResponseKam);
		}
		return listOfLaunchMstnClearanceResponseKam;
	}

	// Q2 sprint kavitha feb2021
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMocApprovalStatus(String userId) {
		try {

			Query query = sessionFactory.getCurrentSession().createNativeQuery("SELECT DISTINCT LAUNCH_MOC  FROM "
					+ "TBL_LAUNCH_REQUEST tlr,TBL_LAUNCH_MASTER tlm WHERE tlr.LAUNCH_ID = tlm.LAUNCH_ID AND tlr.CREATED_BY = '"
					+ userId + "' ORDER BY LAUNCH_MOC ASC");
			List<String> list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Q2 sprint kavitha feb2021
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getKamApprovalStatus(String userId) {
		try {

			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT DISTINCT tlr.FINAL_STATUS APPROVAL_STATUS  FROM "
							+ "TBL_LAUNCH_REQUEST tlr,TBL_LAUNCH_MASTER tlm WHERE tlr.LAUNCH_ID = tlm.LAUNCH_ID AND tlr.CREATED_BY = '"
							+ userId + "' ORDER BY tlr.FINAL_STATUS");
			List<String> list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String replaceNA(String str) {
		if (str == null)
			return "";
		return "NA".equals(str) ? "" : str;
	}

	// Q1 Sprint3 Notification Changes - Kavitha D Starts
	private boolean updateUserNotifications(String userId) {
		boolean result = false;
		Query query = null;
		String userNotification = "UPDATE TBL_VAT_USER_NOTIFICATIONS SET UPDATED_DATE = NOW() WHERE USER_ID = :userId";
		String insertUserNotification = "INSERT INTO TBL_VAT_USER_NOTIFICATIONS (USER_ID,UPDATED_DATE) VALUES (:userId,NOW())";
		try {
			if (ValidateUserNotification(userId) > 0) {
				query = sessionFactory.getCurrentSession().createNativeQuery(userNotification);
				query.setParameter("userId", userId);
				query.executeUpdate();
			} else {
				query = sessionFactory.getCurrentSession().createNativeQuery(insertUserNotification);
				query.setParameter("userId", userId);
				query.executeUpdate();
			}
			result = true;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}

		return result;

	}

	private int ValidateUserNotification(String userId) {
		Integer iValid = 0;
		Query query = null;
		String qryValidate = "SELECT COUNT(1) FROM TBL_VAT_USER_NOTIFICATIONS WHERE USER_ID = :userId";
		try {
			query = sessionFactory.getCurrentSession().createNativeQuery(qryValidate);
			query.setParameter("userId", userId);
			iValid = ((BigInteger) query.uniqueResult()).intValue();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return iValid;
		// Q1 Sprint3 Notification Changes - Kavitha D ends
	}
	//Kavitha D implementation-SPRINT 7 2021 starts
	public String getLaunchName(String launchId) {
		
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		String launchName = "";
		try {
			PreparedStatement stmt = sessionImpl.connection()
					.prepareStatement("SELECT LAUNCH_NAME FROM TBL_LAUNCH_MASTER WHERE LAUNCH_ID = '" + launchId + "'" );		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				launchName = rs.getString(1);
			}
			//return launchName;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return ex.toString();
		}
		return launchName;
}
	//Kavitha D implementation-SPRINT 7 2021 ends

}

