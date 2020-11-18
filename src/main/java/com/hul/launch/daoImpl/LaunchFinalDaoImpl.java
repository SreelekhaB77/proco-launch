package com.hul.launch.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.dao.LaunchDaoCoe;
import com.hul.launch.dao.LaunchDaoSc;
import com.hul.launch.dao.LaunchFinalDao;
import com.hul.launch.dao.LaunchSellInDao;
import com.hul.launch.model.LaunchBuildUpTemp;
import com.hul.launch.model.LaunchBuildUpTempCal;
import com.hul.launch.model.LaunchFinalCalVO;
import com.hul.launch.model.LaunchStoreData;
import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.request.SaveFinalLaunchRequest;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;
import com.hul.launch.response.LaunchScMstnClearanceResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchFinalDaoImpl implements LaunchFinalDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	LaunchSellInDao launchSellInDao;

	@Autowired
	LaunchDaoSc launchDaoSc;

	@Autowired
	LaunchDaoCoe launchDaoCoe;

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	private final static String TBL_LAUNCH_FINAL_BUILDUP = "INSERT INTO TBL_LAUNCH_FINAL_BUILDUP "
			+ "(BUILDUP_LAUNCH_ID, BUILDUP_SKU_NAME, BUILDUP_BASEPACK_CODE, BUILDUP_LAUNCH_SELLIN_VALUE, BUILDUP_LAUNCH_SELLIN_N1,"
			+ " BUILDUP_LAUNCH_SELLIN_N2, BUILDUP_LAUNCH_SELLIN_CLDS, BUILDUP_LAUNCH_SELLIN_CLDS_N1, BUILDUP_LAUNCH_SELLIN_CLDS_N2, "
			+ "BUILDUP_LAUNCH_SELLIN_UNITS, BUILDUP_LAUNCH_SELLIN_UNITS_N1, BUILDUP_LAUNCH_SELLIN_UNITS_N2, CREATED_BY, CREATED_DATE, STORE_COUNT) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_FINAL_BUILDUP_KAM = "INSERT INTO TBL_LAUNCH_FINAL_BUILDUP_KAM "
			+ "(BUILDUP_LAUNCH_ID, BUILDUP_SKU_NAME, BUILDUP_BASEPACK_CODE, BUILDUP_LAUNCH_SELLIN_VALUE, BUILDUP_LAUNCH_SELLIN_N1,"
			+ " BUILDUP_LAUNCH_SELLIN_N2, BUILDUP_LAUNCH_SELLIN_CLDS, BUILDUP_LAUNCH_SELLIN_CLDS_N1, BUILDUP_LAUNCH_SELLIN_CLDS_N2, "
			+ "BUILDUP_LAUNCH_SELLIN_UNITS, BUILDUP_LAUNCH_SELLIN_UNITS_N1, BUILDUP_LAUNCH_SELLIN_UNITS_N2, CREATED_BY, CREATED_DATE, STORE_COUNT,BUILDUP_ACCOUNT) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_BUILDUP_TEMP = "INSERT INTO TBL_LAUNCH_BUILDUP_TEMP"
			+ " (LAUNCH_ID, REPORTING_CODE, HFS_CODE, FMCG_CSP_CODE, FMCG_SITE_CODE, MODIFIED_CHAIN, CHANNEL, ACCOUNT_FORMAT_NAME, "
			+ " ACCOUNT_NAME, HUL_STORE_FORMAT, CUSTOMER_STORE_FORMAT, UKEY, FMCG_SUPPLY_TYPE, BRANCH_CODE, STATE_CODE, TOWN_NAME, "
			+ " DEPOT, CLUSTER, UNIT_OF_MEASUREMENT, SKU_COUNT, SKU_NAME, CLD_SIZE, MRP, GSV, BASEPACK_CODE, REMARKS, ROTATIONS, "
			+ " VISI_CHECK, VISI_ELEMENT_1, VISI_ELEMENT_2, VISI_ELEMENT_3, VISI_ELEMENT_4, VISI_ELEMENT_5, UPLIFTN1, UPLIFTN2, "
			+ " SELLIN_N, SELLIN_N1, SELLIN_N2, VISI_SELLIN_N, VISI_SELLIN_N1, VISI_SELLIN_N2, REVISED_SELLIN_FOR_STORE_N, "
			+ " REVISED_SELLIN_FOR_STORE_N1, REVISED_SELLIN_FOR_STORE_N2, SELLIN_VALUE_N, SELLIN_VALUE_N1, SELLIN_VALUE_N2, "
			+ " CREATED_BY, CREATED_DATE, ACCOUNT_NAME_L2) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_BUILDUP_KAM_TEMP = "INSERT INTO TBL_LAUNCH_BUILDUP_KAM_TEMP"
			+ " (LAUNCH_ID, REPORTING_CODE, HFS_CODE, FMCG_CSP_CODE, FMCG_SITE_CODE, MODIFIED_CHAIN, CHANNEL, ACCOUNT_FORMAT_NAME, "
			+ " ACCOUNT_NAME, HUL_STORE_FORMAT, CUSTOMER_STORE_FORMAT, UKEY, FMCG_SUPPLY_TYPE, BRANCH_CODE, STATE_CODE, TOWN_NAME, "
			+ " DEPOT, CLUSTER, UNIT_OF_MEASUREMENT, SKU_COUNT, SKU_NAME, CLD_SIZE, MRP, GSV, BASEPACK_CODE, REMARKS, ROTATIONS, "
			+ " VISI_CHECK, VISI_ELEMENT_1, VISI_ELEMENT_2, VISI_ELEMENT_3, VISI_ELEMENT_4, VISI_ELEMENT_5, UPLIFTN1, UPLIFTN2, "
			+ " SELLIN_N, SELLIN_N1, SELLIN_N2, VISI_SELLIN_N, VISI_SELLIN_N1, VISI_SELLIN_N2, REVISED_SELLIN_FOR_STORE_N, "
			+ " REVISED_SELLIN_FOR_STORE_N1, REVISED_SELLIN_FOR_STORE_N2, SELLIN_VALUE_N, SELLIN_VALUE_N1, SELLIN_VALUE_N2, "
			+ " CREATED_BY, CREATED_DATE, ACCOUNT_NAME_L2,BUILDUP_ACCOUNT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	private final static String TBL_LAUNCH_TEMP_FINAL_CAL = "INSERT INTO MODTRD.TBL_LAUNCH_TEMP_FINAL_CAL (LAUNCH_ID, DEPOT, "
			+ "BP_NAME, FMCG_CSP_CODE, MODIFIED_CHAIN, SUM_OF_REVISED_SELL_IN_N, SUM_OF_REVISED_SELL_IN_N1, "
			+ "SUM_OF_REVISED_SELL_IN_N2, FINAL_CLD_N, FINAL_CLD_N1, FINAL_CLD_N2, FINAL_UNITS_N, FINAL_UNITS_N1, FINAL_UNITS_N2, FINAL_VALUE_N, "
			+ "FINAL_VALUE_N1, FINAL_VALUE_N2,APPLIED_STORES,CLUSTER) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final static String TBL_LAUNCH_TEMP_FINAL_CAL_KAM = "INSERT INTO MODTRD.TBL_LAUNCH_TEMP_FINAL_CAL_KAM (LAUNCH_ID, DEPOT, "
			+ "BP_NAME, FMCG_CSP_CODE, MODIFIED_CHAIN, SUM_OF_REVISED_SELL_IN_N, SUM_OF_REVISED_SELL_IN_N1, "
			+ "SUM_OF_REVISED_SELL_IN_N2, FINAL_CLD_N, FINAL_CLD_N1, FINAL_CLD_N2, FINAL_UNITS_N, FINAL_UNITS_N1, FINAL_UNITS_N2, FINAL_VALUE_N, "
			+ "FINAL_VALUE_N1, FINAL_VALUE_N2,APPLIED_STORES,CLUSTER,FINAL_CAL_ACCOUNT) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalRespose(String launchId) {
		List<LaunchFinalPlanResponse> liReturn = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("SELECT * FROM TBL_LAUNCH_BASEPACK tlb WHERE LAUNCH_ID = :launchId");
			query.setParameter("launchId", launchId);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				LaunchFinalPlanResponse launchFinalPlanResponse = new LaunchFinalPlanResponse();
				Object[] obj = (Object[]) itr.next();
				launchFinalPlanResponse.setBasePackId((int) obj[0]);
				launchFinalPlanResponse.setBasepackCode((String) obj[5]);
				launchFinalPlanResponse.setSkuName((String) obj[6]);
				launchFinalPlanResponse.setLaunchSellInValue("");
				launchFinalPlanResponse.setLaunchN1SellInVal("");
				launchFinalPlanResponse.setLaunchN2SellInVal("");
				launchFinalPlanResponse.setLaunchSellInCld("");
				launchFinalPlanResponse.setLaunchN1SellInCld("");
				launchFinalPlanResponse.setLaunchN2SellInCld("");
				launchFinalPlanResponse.setLaunchSellInUnit("");
				launchFinalPlanResponse.setLaunchN1SellInUnit("");
				launchFinalPlanResponse.setLaunchN2SellInUnit("");
				launchFinalPlanResponse.setGsv((String) obj[9]);
				launchFinalPlanResponse.setCld((String) obj[10]);
				launchFinalPlanResponse.setMrp((String) obj[7]);
				launchFinalPlanResponse.setBpStatus((String) obj[17]);
				liReturn.add(launchFinalPlanResponse);
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchFinalPlanResponse launchFinalPlanResponse = new LaunchFinalPlanResponse();
			launchFinalPlanResponse.setError(e.toString());
			liReturn.add(launchFinalPlanResponse);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchFinalPlanResponse> getLaunchFinalResposeKAM(String launchId, String forWhichKam) {
		List<LaunchFinalPlanResponse> liReturn = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("SELECT * FROM TBL_LAUNCH_BASEPACK tlb WHERE LAUNCH_ID = :launchId");
			query.setParameter("launchId", launchId);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				Query query3 = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT LAUNCH_BASEPACK FROM TBL_LAUNCH_BASEPACK_KAM WHERE LAUNCH_ACCOUNT = '"
								+ forWhichKam + "' AND LAUNCH_ID = '" + launchId + "'");
				List<String> listOfBp = query3.list();
				List<String> bpIds = new ArrayList<>();
				if (!listOfBp.isEmpty()) {
					bpIds = Arrays.asList(listOfBp.get(0).toString().split(","));
				}
				Object[] obj = (Object[]) itr.next();
				if (!bpIds.contains(obj[0].toString())) {
					LaunchFinalPlanResponse launchFinalPlanResponse = new LaunchFinalPlanResponse();
					launchFinalPlanResponse.setBasePackId((int) obj[0]);
					launchFinalPlanResponse.setBasepackCode((String) obj[5]);
					launchFinalPlanResponse.setSkuName((String) obj[6]);
					launchFinalPlanResponse.setLaunchSellInValue("");
					launchFinalPlanResponse.setLaunchN1SellInVal("");
					launchFinalPlanResponse.setLaunchN2SellInVal("");
					launchFinalPlanResponse.setLaunchSellInCld("");
					launchFinalPlanResponse.setLaunchN1SellInCld("");
					launchFinalPlanResponse.setLaunchN2SellInCld("");
					launchFinalPlanResponse.setLaunchSellInUnit("");
					launchFinalPlanResponse.setLaunchN1SellInUnit("");
					launchFinalPlanResponse.setLaunchN2SellInUnit("");
					launchFinalPlanResponse.setGsv((String) obj[9]);
					launchFinalPlanResponse.setCld((String) obj[10]);
					launchFinalPlanResponse.setMrp((String) obj[7]);
					launchFinalPlanResponse.setBpStatus((String) obj[17]);
					liReturn.add(launchFinalPlanResponse);
				}

				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchFinalPlanResponse launchFinalPlanResponse = new LaunchFinalPlanResponse();
			launchFinalPlanResponse.setError(e.toString());
			liReturn.add(launchFinalPlanResponse);
		}
		return liReturn;
	}

	@Override
	public String saveLaunchFinalBuildUp(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP where BUILDUP_LAUNCH_ID=?");
			batchUpdate.setString(1, saveFinalLaunchListRequest.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveFinalLaunchRequest> listBuildUps = saveFinalLaunchListRequest.getListOfFinalBuildUps();
			for (SaveFinalLaunchRequest saveLaunchFinalRequest : listBuildUps) {
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_FINAL_BUILDUP, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, saveFinalLaunchListRequest.getLaunchId());
					preparedStatement.setString(2, saveLaunchFinalRequest.getSkuName());
					preparedStatement.setString(3, saveLaunchFinalRequest.getBasepackCode());
					preparedStatement.setString(4, saveLaunchFinalRequest.getLaunchSellInValue());
					preparedStatement.setString(5, saveLaunchFinalRequest.getLaunchSellInValueN1());
					preparedStatement.setString(6, saveLaunchFinalRequest.getLaunchSellInValueN2());
					preparedStatement.setString(7, saveLaunchFinalRequest.getLaunchSellInValueClds());
					preparedStatement.setString(8, saveLaunchFinalRequest.getLaunchSellInValueCldsN1());
					preparedStatement.setString(9, saveLaunchFinalRequest.getLaunchSellInValueCldsN2());
					preparedStatement.setString(10, saveLaunchFinalRequest.getLaunchSellInUnits());
					preparedStatement.setString(11, saveLaunchFinalRequest.getLaunchSellInUnitsN1());
					preparedStatement.setString(12, saveLaunchFinalRequest.getLaunchSellInUnitsN2());
					preparedStatement.setString(13, userId);
					preparedStatement.setTimestamp(14, new Timestamp(new Date().getTime()));
					preparedStatement.setInt(15, saveLaunchFinalRequest.getLaunchStoreCount());
					preparedStatement.executeUpdate();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Saved Successfully";
	}

	@Override
	public String saveLaunchFinalBuildUpKAM(SaveFinalLaunchListRequest saveFinalLaunchListRequest, String userId,
			String forWhichKAM) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_FINAL_BUILDUP_KAM where BUILDUP_LAUNCH_ID=?");
			batchUpdate.setString(1, saveFinalLaunchListRequest.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveFinalLaunchRequest> listBuildUps = saveFinalLaunchListRequest.getListOfFinalBuildUps();
			for (SaveFinalLaunchRequest saveLaunchFinalRequest : listBuildUps) {
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_FINAL_BUILDUP_KAM, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, saveFinalLaunchListRequest.getLaunchId());
					preparedStatement.setString(2, saveLaunchFinalRequest.getSkuName());
					preparedStatement.setString(3, saveLaunchFinalRequest.getBasepackCode());
					preparedStatement.setString(4, saveLaunchFinalRequest.getLaunchSellInValue());
					preparedStatement.setString(5, saveLaunchFinalRequest.getLaunchSellInValueN1());
					preparedStatement.setString(6, saveLaunchFinalRequest.getLaunchSellInValueN2());
					preparedStatement.setString(7, saveLaunchFinalRequest.getLaunchSellInValueClds());
					preparedStatement.setString(8, saveLaunchFinalRequest.getLaunchSellInValueCldsN1());
					preparedStatement.setString(9, saveLaunchFinalRequest.getLaunchSellInValueCldsN2());
					preparedStatement.setString(10, saveLaunchFinalRequest.getLaunchSellInUnits());
					preparedStatement.setString(11, saveLaunchFinalRequest.getLaunchSellInUnitsN1());
					preparedStatement.setString(12, saveLaunchFinalRequest.getLaunchSellInUnitsN2());
					preparedStatement.setString(13, userId);
					preparedStatement.setTimestamp(14, new Timestamp(new Date().getTime()));
					preparedStatement.setInt(15, saveLaunchFinalRequest.getLaunchStoreCount());
					preparedStatement.setString(16, forWhichKAM);
					preparedStatement.executeUpdate();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Saved Successfully";
	}

	@Override
	public String saveLaunchBuildUpTempKAM(List<List<LaunchStoreData>> listLaunchStoreData, String launchId,
			String forWhichKam) {
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			try (PreparedStatement preparedStatement = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_BUILDUP_KAM_TEMP, Statement.RETURN_GENERATED_KEYS)) {
				for (List<LaunchStoreData> saveLaunchStoreData : listLaunchStoreData) {
					for (LaunchStoreData saveLaunchStoreDataList : saveLaunchStoreData) {
						preparedStatement.setString(1, launchId);
						preparedStatement.setString(2, saveLaunchStoreDataList.getReportingCode());
						preparedStatement.setString(3, saveLaunchStoreDataList.getHfsCode());
						preparedStatement.setString(4, saveLaunchStoreDataList.getFmcgCspCode());
						preparedStatement.setString(5, saveLaunchStoreDataList.getFmcgSiteCode());
						preparedStatement.setString(6, saveLaunchStoreDataList.getModifiedChain());
						preparedStatement.setString(7, saveLaunchStoreDataList.getChannel());
						preparedStatement.setString(8, saveLaunchStoreDataList.getAccountFormatName());
						preparedStatement.setString(9, saveLaunchStoreDataList.getAccountNameL1());
						preparedStatement.setString(10, saveLaunchStoreDataList.getHulStoreFormat());
						preparedStatement.setString(11, saveLaunchStoreDataList.getCustomerStoreFormat());
						preparedStatement.setString(12, saveLaunchStoreDataList.getuKey());
						preparedStatement.setString(13, saveLaunchStoreDataList.getFmcgSupplyType());
						preparedStatement.setString(14, saveLaunchStoreDataList.getBranchCode());
						preparedStatement.setString(15, saveLaunchStoreDataList.getStateCode());
						preparedStatement.setString(16, saveLaunchStoreDataList.getTownName());
						preparedStatement.setString(17, saveLaunchStoreDataList.getDepot());
						preparedStatement.setString(18, saveLaunchStoreDataList.getFinalCluster());
						preparedStatement.setString(19, saveLaunchStoreDataList.getUnitOfMeasurement());
						preparedStatement.setString(20, saveLaunchStoreDataList.getSkuFinalCount());
						preparedStatement.setString(21, saveLaunchStoreDataList.getSkuFinalName());
						preparedStatement.setString(22, saveLaunchStoreDataList.getSkuFinalCld());
						preparedStatement.setString(23, saveLaunchStoreDataList.getSkuFinalMrp());
						preparedStatement.setString(24, saveLaunchStoreDataList.getSkuFinalGsv());
						preparedStatement.setString(25, saveLaunchStoreDataList.getSkuFinalCode());
						preparedStatement.setString(26, saveLaunchStoreDataList.getRemarks());
						preparedStatement.setString(27, saveLaunchStoreDataList.getRotation());
						preparedStatement.setString(28, saveLaunchStoreDataList.getVisiCheck());
						preparedStatement.setString(29, saveLaunchStoreDataList.getVisiElement1());
						preparedStatement.setString(30, saveLaunchStoreDataList.getVisiElement2());
						preparedStatement.setString(31, saveLaunchStoreDataList.getVisiElement3());
						preparedStatement.setString(32, saveLaunchStoreDataList.getVisiElement4());
						preparedStatement.setString(33, saveLaunchStoreDataList.getVisiElement5());
						preparedStatement.setString(34, saveLaunchStoreDataList.getUpliftN1());
						preparedStatement.setString(35, saveLaunchStoreDataList.getUpliftN2());
						preparedStatement.setString(36, saveLaunchStoreDataList.getSellInN());
						preparedStatement.setString(37, saveLaunchStoreDataList.getSellInN1());
						preparedStatement.setString(38, saveLaunchStoreDataList.getSellInN2());
						preparedStatement.setString(39, saveLaunchStoreDataList.getVisiSellInN());
						preparedStatement.setString(40, saveLaunchStoreDataList.getVisiSellInN1());
						preparedStatement.setString(41, saveLaunchStoreDataList.getVisiSellInN2());
						preparedStatement.setString(42, saveLaunchStoreDataList.getRevisedSellInForStoreN());
						preparedStatement.setString(43, saveLaunchStoreDataList.getRevisedSellInForStoreN1());
						preparedStatement.setString(44, saveLaunchStoreDataList.getRevisedSellInForStoreN2());
						preparedStatement.setString(45, saveLaunchStoreDataList.getSellInValueN());
						preparedStatement.setString(46, saveLaunchStoreDataList.getSellInValueN1());
						preparedStatement.setString(47, saveLaunchStoreDataList.getSellInValueN2());
						preparedStatement.setString(48, forWhichKam);
						preparedStatement.setTimestamp(49, new Timestamp(new Date().getTime()));
						preparedStatement.setString(50, saveLaunchStoreDataList.getAccountNameL2());
						preparedStatement.setString(51, forWhichKam);
						preparedStatement.addBatch();
					}
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

	@Override
	public String saveLaunchBuildUpTemp(List<List<LaunchStoreData>> listLaunchStoreData, String launchId,
			String userId) {
		PreparedStatement batchUpdate = null;  //Sarin Changes 18Nov2020

		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;

			//Sarin Changes 18Nov2020
			batchUpdate = sessionImpl.connection().prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_TEMP where LAUNCH_ID= ?");
			batchUpdate.setString(1, launchId);
			batchUpdate.executeUpdate();
			//Sarin Changes 18Nov2020 - ends

			try (PreparedStatement preparedStatement = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_BUILDUP_TEMP, Statement.RETURN_GENERATED_KEYS)) {
				for (List<LaunchStoreData> saveLaunchStoreData : listLaunchStoreData) {
					for (LaunchStoreData saveLaunchStoreDataList : saveLaunchStoreData) {
						preparedStatement.setString(1, launchId);
						preparedStatement.setString(2, saveLaunchStoreDataList.getReportingCode());
						preparedStatement.setString(3, saveLaunchStoreDataList.getHfsCode());
						preparedStatement.setString(4, saveLaunchStoreDataList.getFmcgCspCode());
						preparedStatement.setString(5, saveLaunchStoreDataList.getFmcgSiteCode());
						preparedStatement.setString(6, saveLaunchStoreDataList.getModifiedChain());
						preparedStatement.setString(7, saveLaunchStoreDataList.getChannel());
						preparedStatement.setString(8, saveLaunchStoreDataList.getAccountFormatName());
						preparedStatement.setString(9, saveLaunchStoreDataList.getAccountNameL1());
						preparedStatement.setString(10, saveLaunchStoreDataList.getHulStoreFormat());
						preparedStatement.setString(11, saveLaunchStoreDataList.getCustomerStoreFormat());
						preparedStatement.setString(12, saveLaunchStoreDataList.getuKey());
						preparedStatement.setString(13, saveLaunchStoreDataList.getFmcgSupplyType());
						preparedStatement.setString(14, saveLaunchStoreDataList.getBranchCode());
						preparedStatement.setString(15, saveLaunchStoreDataList.getStateCode());
						preparedStatement.setString(16, saveLaunchStoreDataList.getTownName());
						preparedStatement.setString(17, saveLaunchStoreDataList.getDepot());
						preparedStatement.setString(18, saveLaunchStoreDataList.getFinalCluster());
						preparedStatement.setString(19, saveLaunchStoreDataList.getUnitOfMeasurement());
						preparedStatement.setString(20, saveLaunchStoreDataList.getSkuFinalCount());
						preparedStatement.setString(21, saveLaunchStoreDataList.getSkuFinalName());
						preparedStatement.setString(22, saveLaunchStoreDataList.getSkuFinalCld());
						preparedStatement.setString(23, saveLaunchStoreDataList.getSkuFinalMrp());
						preparedStatement.setString(24, saveLaunchStoreDataList.getSkuFinalGsv());
						preparedStatement.setString(25, saveLaunchStoreDataList.getSkuFinalCode());
						preparedStatement.setString(26, saveLaunchStoreDataList.getRemarks());
						preparedStatement.setString(27, saveLaunchStoreDataList.getRotation());
						preparedStatement.setString(28, saveLaunchStoreDataList.getVisiCheck());
						preparedStatement.setString(29, saveLaunchStoreDataList.getVisiElement1());
						preparedStatement.setString(30, saveLaunchStoreDataList.getVisiElement2());
						preparedStatement.setString(31, saveLaunchStoreDataList.getVisiElement3());
						preparedStatement.setString(32, saveLaunchStoreDataList.getVisiElement4());
						preparedStatement.setString(33, saveLaunchStoreDataList.getVisiElement5());
						preparedStatement.setString(34, saveLaunchStoreDataList.getUpliftN1());
						preparedStatement.setString(35, saveLaunchStoreDataList.getUpliftN2());
						preparedStatement.setString(36, saveLaunchStoreDataList.getSellInN());
						preparedStatement.setString(37, saveLaunchStoreDataList.getSellInN1());
						preparedStatement.setString(38, saveLaunchStoreDataList.getSellInN2());
						preparedStatement.setString(39, saveLaunchStoreDataList.getVisiSellInN());
						preparedStatement.setString(40, saveLaunchStoreDataList.getVisiSellInN1());
						preparedStatement.setString(41, saveLaunchStoreDataList.getVisiSellInN2());
						preparedStatement.setString(42, saveLaunchStoreDataList.getRevisedSellInForStoreN());
						preparedStatement.setString(43, saveLaunchStoreDataList.getRevisedSellInForStoreN1());
						preparedStatement.setString(44, saveLaunchStoreDataList.getRevisedSellInForStoreN2());
						preparedStatement.setString(45, saveLaunchStoreDataList.getSellInValueN());
						preparedStatement.setString(46, saveLaunchStoreDataList.getSellInValueN1());
						preparedStatement.setString(47, saveLaunchStoreDataList.getSellInValueN2());
						preparedStatement.setString(48, userId);
						preparedStatement.setTimestamp(49, new Timestamp(new Date().getTime()));
						preparedStatement.setString(50, saveLaunchStoreDataList.getAccountNameL2());
						preparedStatement.addBatch();
					}
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

	@Override
	public List<ArrayList<String>> getFinalBuildUpDumptNew(String userId, String launchId) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchBuildUpTempCal> listOfFinalBuildups = getFinalBuildUpTempDataNew(launchId);
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("LAUNCH_NAME");
		headerDetail.add("LAUNCH_MOC");
		headerDetail.add("DEPOT");
		headerDetail.add("SKU_NAME");
		headerDetail.add("FMCG_CSP_CODE");
		headerDetail.add("MODIFIED_CHAIN");
		headerDetail.add("CLUSTER");
		headerDetail.add("FINAL_CLD_N");
		headerDetail.add("FINAL_CLD_N+1");
		headerDetail.add("FINAL_CLD_N+2");
		headerDetail.add("FINAL_UNITS_N");
		headerDetail.add("FINAL_UNITS_N+1");
		headerDetail.add("FINAL_UNITS_N+2");
		headerDetail.add("SELL_IN_VALUE_N");
		headerDetail.add("SELL_IN_VALUE_N+1");
		headerDetail.add("SELL_IN_VALUE_N+2");
		downloadedData.add(headerDetail);
		for (LaunchBuildUpTempCal launchBuildUpTemp : listOfFinalBuildups) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchBuildUpTemp.getLAUNCH_NAME());
			dataObj.add(launchBuildUpTemp.getLAUNCH_MOC());
			dataObj.add(launchBuildUpTemp.getDEPOT());
			dataObj.add(launchBuildUpTemp.getSKU_NAME());
			dataObj.add(launchBuildUpTemp.getFMCG_CSP_CODE());
			dataObj.add(launchBuildUpTemp.getMODIFIED_CHAIN());
			dataObj.add(launchBuildUpTemp.getCLUSTER());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N2());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N2());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N2());
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}

	@SuppressWarnings("unchecked")
	public List<LaunchBuildUpTemp> getFinalBuildUpTempData(String launchId) {
		List<LaunchBuildUpTemp> liReturn = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT REPORTING_CODE, HFS_CODE, FMCG_CSP_CODE, FMCG_SITE_CODE, MODIFIED_CHAIN, CHANNEL,"
							+ "ACCOUNT_FORMAT_NAME, ACCOUNT_NAME, HUL_STORE_FORMAT, CUSTOMER_STORE_FORMAT, UKEY, FMCG_SUPPLY_TYPE, "
							+ "BRANCH_CODE, STATE_CODE, TOWN_NAME, DEPOT, CLUSTER, UNIT_OF_MEASUREMENT, SKU_COUNT, SKU_NAME, CLD_SIZE,"
							+ "MRP, GSV, BASEPACK_CODE, REMARKS, ROTATIONS, VISI_CHECK, VISI_ELEMENT_1, VISI_ELEMENT_2, VISI_ELEMENT_3,"
							+ "VISI_ELEMENT_4, VISI_ELEMENT_5, UPLIFTN1, UPLIFTN2, SELLIN_N, SELLIN_N1, SELLIN_N2, VISI_SELLIN_N,"
							+ "VISI_SELLIN_N1, VISI_SELLIN_N2, REVISED_SELLIN_FOR_STORE_N, REVISED_SELLIN_FOR_STORE_N1, "
							+ "REVISED_SELLIN_FOR_STORE_N2, SELLIN_VALUE_N, SELLIN_VALUE_N1, SELLIN_VALUE_N2"
							+ " FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = :launchId");
			query.setParameter("launchId", launchId);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
				Object[] obj = (Object[]) itr.next();
				launchBuildUpTemp.setREPORTING_CODE((String) obj[0]);
				launchBuildUpTemp.setHFS_CODE((String) obj[1]);
				launchBuildUpTemp.setFMCG_CSP_CODE((String) obj[2]);
				launchBuildUpTemp.setFMCG_SITE_CODE((String) obj[3]);
				launchBuildUpTemp.setMODIFIED_CHAIN((String) obj[4]);
				launchBuildUpTemp.setCHANNEL((String) obj[5]);
				launchBuildUpTemp.setACCOUNT_FORMAT_NAME((String) obj[6]);
				launchBuildUpTemp.setACCOUNT_NAME((String) obj[7]);
				launchBuildUpTemp.setHUL_STORE_FORMAT((String) obj[8]);
				launchBuildUpTemp.setCUSTOMER_STORE_FORMAT((String) obj[9]);
				launchBuildUpTemp.setUKEY((String) obj[10]);
				launchBuildUpTemp.setFMCG_SUPPLY_TYPE((String) obj[11]);
				launchBuildUpTemp.setBRANCH_CODE((String) obj[12]);
				launchBuildUpTemp.setSTATE_CODE((String) obj[13]);
				launchBuildUpTemp.setTOWN_NAME((String) obj[14]);
				launchBuildUpTemp.setDEPOT((String) obj[15]);
				launchBuildUpTemp.setCLUSTER((String) obj[16]);
				launchBuildUpTemp.setUNIT_OF_MEASUREMENT((String) obj[17]);
				launchBuildUpTemp.setSKU_COUNT((String) obj[18]);
				launchBuildUpTemp.setSKU_NAME((String) obj[19]);
				launchBuildUpTemp.setCLD_SIZE((String) obj[20]);
				launchBuildUpTemp.setMRP((String) obj[21]);
				launchBuildUpTemp.setGSV((String) obj[22]);
				launchBuildUpTemp.setBASEPACK_CODE((String) obj[23]);
				launchBuildUpTemp.setREMARKS((String) obj[24]);
				launchBuildUpTemp.setROTATIONS((String) obj[25]);
				launchBuildUpTemp.setVISI_CHECK((String) obj[26]);
				launchBuildUpTemp.setVISI_ELEMENT_1((String) obj[27]);
				launchBuildUpTemp.setVISI_ELEMENT_2((String) obj[28]);
				launchBuildUpTemp.setVISI_ELEMENT_3((String) obj[29]);
				launchBuildUpTemp.setVISI_ELEMENT_4((String) obj[30]);
				launchBuildUpTemp.setVISI_ELEMENT_5((String) obj[31]);
				launchBuildUpTemp.setUPLIFTN1((String) obj[32]);
				launchBuildUpTemp.setUPLIFTN2((String) obj[33]);
				launchBuildUpTemp.setSELLIN_N((String) obj[34]);
				launchBuildUpTemp.setSELLIN_N1((String) obj[35]);
				launchBuildUpTemp.setSELLIN_N2((String) obj[36]);
				launchBuildUpTemp.setVISI_SELLIN_N((String) obj[37]);
				launchBuildUpTemp.setVISI_SELLIN_N1((String) obj[38]);
				launchBuildUpTemp.setVISI_SELLIN_N2((String) obj[39]);
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N((String) obj[40]);
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1((String) obj[41]);
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2((String) obj[42]);
				launchBuildUpTemp.setSELLIN_VALUE_N((String) obj[43]);
				launchBuildUpTemp.setSELLIN_VALUE_N1((String) obj[44]);
				launchBuildUpTemp.setSELLIN_VALUE_N2((String) obj[45]);
				liReturn.add(launchBuildUpTemp);
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
			launchBuildUpTemp.setError(e.toString());
			liReturn.add(launchBuildUpTemp);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	public List<LaunchBuildUpTempCal> getFinalBuildUpTempDataNew(String launchId) {
		List<LaunchBuildUpTempCal> liReturn = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT DEPOT, BP_NAME, FMCG_CSP_CODE, MODIFIED_CHAIN, CLUSTER,FINAL_CLD_N,FINAL_CLD_N1,FINAL_CLD_N2,"
							+ "FINAL_UNITS_N,FINAL_UNITS_N1,FINAL_UNITS_N2,FINAL_VALUE_N,FINAL_VALUE_N1,FINAL_VALUE_N2,tlm.LAUNCH_MOC,tlm.LAUNCH_NAME FROM "
							+ "TBL_LAUNCH_TEMP_FINAL_CAL tltfc, TBL_LAUNCH_MASTER tlm WHERE tltfc.LAUNCH_ID = :launchId AND tltfc.LAUNCH_ID = tlm.LAUNCH_ID ");
			query.setParameter("launchId", launchId);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchBuildUpTempCal launchBuildUpTempCal = new LaunchBuildUpTempCal();
				launchBuildUpTempCal.setDEPOT((String) obj[0]);
				launchBuildUpTempCal.setSKU_NAME((String) obj[1]);
				launchBuildUpTempCal.setFMCG_CSP_CODE((String) obj[2]);
				launchBuildUpTempCal.setMODIFIED_CHAIN((String) obj[3]);
				launchBuildUpTempCal.setCLUSTER((String) obj[4]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N((String) obj[5]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N1((String) obj[6]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N2((String) obj[7]);
				launchBuildUpTempCal.setSELLIN_UNITS_N((String) obj[8]);
				launchBuildUpTempCal.setSELLIN_UNITS_N1((String) obj[9]);
				launchBuildUpTempCal.setSELLIN_UNITS_N2((String) obj[10]);
				launchBuildUpTempCal.setSELLIN_VALUE_N((String) obj[11]);
				launchBuildUpTempCal.setSELLIN_VALUE_N1((String) obj[12]);
				launchBuildUpTempCal.setSELLIN_VALUE_N2((String) obj[13]);

				Query query1 = session.createNativeQuery(
						"SELECT DISTINCT LOWER(KAM_MAIL_ID) FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE ACCOUNT_NAME = :accountName");
				query1.setParameter("accountName", (String) obj[3]);
				List<String> kamId = query1.list();
				String launchMoc = null;
				if (kamId.isEmpty()) {
					launchMoc = (String) obj[14];
				} else {
					String accountName = kamId.get(0).split("@")[0];
					Query query2 = session.createNativeQuery(
							"SELECT LAUNCH_MOC FROM MODTRD.TBL_LAUNCH_MOC_KAM WHERE UPPER(LAUNCH_ACCOUNT) = :accountName AND LAUNCH_ID = :launchId");
					query2.setParameter("accountName", accountName.toUpperCase());
					query2.setParameter("launchId", launchId);
					List<String> rejectedMoc = query2.list();
					if (rejectedMoc.isEmpty()) {
						launchMoc = (String) obj[14];
					} else {
						launchMoc = query2.list().get(0).toString();
					}
				}

				launchBuildUpTempCal.setLAUNCH_MOC(launchMoc);
				launchBuildUpTempCal.setLAUNCH_NAME((String) obj[15]);
				liReturn.add(launchBuildUpTempCal);
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchBuildUpTempCal launchBuildUpTemp = new LaunchBuildUpTempCal();
			launchBuildUpTemp.setError(e.toString());
			liReturn.add(launchBuildUpTemp);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	public List<LaunchBuildUpTempCal> getFinalBuildUpTempDataNew(String[] launchId) {
		List<LaunchBuildUpTempCal> liReturn = new ArrayList<>();
		List<String> launchIdList = Arrays.asList(launchId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT DEPOT, BP_NAME, FMCG_CSP_CODE, MODIFIED_CHAIN, CLUSTER,FINAL_CLD_N,FINAL_CLD_N1,FINAL_CLD_N2,"
							+ "FINAL_UNITS_N,FINAL_UNITS_N1,FINAL_UNITS_N2,FINAL_VALUE_N,FINAL_VALUE_N1,FINAL_VALUE_N2,"
							+ "tlm.LAUNCH_NAME, tlm.LAUNCH_MOC, tlm.LAUNCH_ID FROM TBL_LAUNCH_TEMP_FINAL_CAL tltfc, TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID"
							+ " IN (:launchId) AND tltfc.LAUNCH_ID = tlm.LAUNCH_ID");
			query.setParameterList("launchId", launchIdList);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				LaunchBuildUpTempCal launchBuildUpTempCal = new LaunchBuildUpTempCal();
				launchBuildUpTempCal.setDEPOT((String) obj[0]);
				launchBuildUpTempCal.setSKU_NAME((String) obj[1]);
				launchBuildUpTempCal.setFMCG_CSP_CODE((String) obj[2]);
				launchBuildUpTempCal.setMODIFIED_CHAIN((String) obj[3]);
				launchBuildUpTempCal.setCLUSTER((String) obj[4]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N((String) obj[5]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N1((String) obj[6]);
				launchBuildUpTempCal.setSELLIN_VALUE_CLD_N2((String) obj[7]);
				launchBuildUpTempCal.setSELLIN_UNITS_N((String) obj[8]);
				launchBuildUpTempCal.setSELLIN_UNITS_N1((String) obj[9]);
				launchBuildUpTempCal.setSELLIN_UNITS_N2((String) obj[10]);
				launchBuildUpTempCal.setSELLIN_VALUE_N((String) obj[11]);
				launchBuildUpTempCal.setSELLIN_VALUE_N1((String) obj[12]);
				launchBuildUpTempCal.setSELLIN_VALUE_N2((String) obj[13]);
				launchBuildUpTempCal.setLAUNCH_NAME((String) obj[14]);

				Query query1 = session.createNativeQuery(
						"SELECT DISTINCT LOWER(KAM_MAIL_ID) FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE ACCOUNT_NAME = :accountName");
				query1.setParameter("accountName", (String) obj[3]);
				List<String> kamId = query1.list();
				String launchMoc = null;
				if (kamId.isEmpty()) {
					launchMoc = (String) obj[15];
				} else {
					String accountName = kamId.get(0).split("@")[0];
					Query query2 = session.createNativeQuery(
							"SELECT LAUNCH_MOC FROM MODTRD.TBL_LAUNCH_MOC_KAM WHERE UPPER(LAUNCH_ACCOUNT) = :accountName AND LAUNCH_ID = :launchId");
					query2.setParameter("accountName", accountName.toUpperCase());
					query2.setParameter("launchId", Integer.toString((Integer) obj[16]));
					List<String> rejectedMoc = query2.list();
					if (rejectedMoc.isEmpty()) {
						launchMoc = (String) obj[15];
					} else {
						launchMoc = query2.list().get(0).toString();
					}
				}

				launchBuildUpTempCal.setLAUNCH_MOC(launchMoc);
				launchBuildUpTempCal.setLAUNCH_ID(Integer.toString((Integer) obj[16]));
				liReturn.add(launchBuildUpTempCal);
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchBuildUpTempCal launchBuildUpTemp = new LaunchBuildUpTempCal();
			launchBuildUpTemp.setError(e.toString());
			liReturn.add(launchBuildUpTemp);
		}
		return liReturn;
	}

	@Override
	public LaunchBuildUpTemp getFinalBuildUpDepoLevel(String depoCombo, String launchId) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			//stmt = sessionImpl.connection().prepareStatement(
			//		"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1)"
			//		+ " REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2, "
			//		+ " count(*) TOTAL_STORES FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
			//		+ "' AND DEPOT || ',' || SKU_NAME || ',' ||  FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' || CLUSTER = '"
			//		+ depoCombo + "'");
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1)"
							+ " REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2, "
							+ " count(*) TOTAL_STORES FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME,',' ,FMCG_CSP_CODE ,',', MODIFIED_CHAIN, ',' , CLUSTER) = '"
							+ depoCombo + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(rs.getString("REVISED_SELLIN_FOR_STORE_N"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(rs.getString("REVISED_SELLIN_FOR_STORE_N1"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(rs.getString("REVISED_SELLIN_FOR_STORE_N2"));
				launchBuildUpTemp.setSTORE_COUNT(rs.getString("TOTAL_STORES"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getFinalBuildUpDepoLevelKAM(String depoCombo, String launchId, String forWhichAcc) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			//stmt = sessionImpl.connection().prepareStatement(
			//		"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1)"
			//		+ " REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2, "
			//		+ " count(*) TOTAL_STORES FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
			//		+ "' AND DEPOT || ',' || SKU_NAME || ',' ||  FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' || CLUSTER = '"
			//		+ depoCombo + "' AND BUILDUP_ACCOUNT = '" + forWhichAcc + "'");
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1)"
							+ " REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2, "
							+ " count(*) TOTAL_STORES FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT,',', SKU_NAME, ',' ,FMCG_CSP_CODE,',' ,MODIFIED_CHAIN, ',' ,CLUSTER) = '"
							+ depoCombo + "' AND BUILDUP_ACCOUNT = '" + forWhichAcc + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(rs.getString("REVISED_SELLIN_FOR_STORE_N"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(rs.getString("REVISED_SELLIN_FOR_STORE_N1"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(rs.getString("REVISED_SELLIN_FOR_STORE_N2"));
				launchBuildUpTemp.setSTORE_COUNT(rs.getString("TOTAL_STORES"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public List<String> getFinalBuildUpDepoLevelDistinct(String launchId) {
		List<String> listOfStrings = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			//stmt = sessionImpl.connection().prepareStatement(
			//		"SELECT DISTINCT (DEPOT || ',' || SKU_NAME || ',' || FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' || CLUSTER) "
			//		+ " DEPOBASEPACK FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId + "'");
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT DISTINCT CONCAT(DEPOT,',', SKU_NAME, ',' ,FMCG_CSP_CODE , ',' , MODIFIED_CHAIN , ',' , CLUSTER) "
							+ " DEPOBASEPACK FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				listOfStrings.add(rs.getString("DEPOBASEPACK"));
			}
		} catch (Exception e) {
			listOfStrings = new ArrayList<>();
			listOfStrings.add(e.toString());
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listOfStrings;
	}

	@Override
	public List<String> getFinalBuildUpDepoLevelDistinctKAM(String launchId, String forWhichKam) {
		List<String> listOfStrings = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima- changes for concatenation
			//stmt = sessionImpl.connection().prepareStatement(
			//		"SELECT DISTINCT (DEPOT || ',' || SKU_NAME || ',' || FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' || CLUSTER) "
			//				+ " DEPOBASEPACK FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
			//				+ "' AND BUILDUP_ACCOUNT = '" + forWhichKam + "'");
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT DISTINCT CONCAT(DEPOT , ',' , SKU_NAME , ',' , FMCG_CSP_CODE, ',', MODIFIED_CHAIN , ',' , CLUSTER) "
							+ " DEPOBASEPACK FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND BUILDUP_ACCOUNT = '" + forWhichKam + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				listOfStrings.add(rs.getString("DEPOBASEPACK"));
			}
		} catch (Exception e) {
			listOfStrings = new ArrayList<>();
			listOfStrings.add(e.toString());
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listOfStrings;
	}

	@Override
	public LaunchBuildUpTemp getFinalBuildUpDepoLevelAll(String depoBasepack, String launchId) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1) "
							+ "REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2"
							+ " FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME) = '" + depoBasepack + "' ");
			//				+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "' ");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(rs.getString("REVISED_SELLIN_FOR_STORE_N"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(rs.getString("REVISED_SELLIN_FOR_STORE_N1"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(rs.getString("REVISED_SELLIN_FOR_STORE_N2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getFinalBuildUpDepoLevelAllKAM(String depoBasepack, String launchId, String forWhichKam) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT SUM(REVISED_SELLIN_FOR_STORE_N) REVISED_SELLIN_FOR_STORE_N, SUM(REVISED_SELLIN_FOR_STORE_N1) "
							+ "REVISED_SELLIN_FOR_STORE_N1,SUM(REVISED_SELLIN_FOR_STORE_N2) REVISED_SELLIN_FOR_STORE_N2"
							+ " FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME) = '" + depoBasepack + "'");
			//+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "'");
			// AND BUILDUP_ACCOUNT = '" + forWhichKam + "'
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N(rs.getString("REVISED_SELLIN_FOR_STORE_N"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N1(rs.getString("REVISED_SELLIN_FOR_STORE_N1"));
				launchBuildUpTemp.setREVISED_SELLIN_FOR_STORE_N2(rs.getString("REVISED_SELLIN_FOR_STORE_N2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getCldForDepoBasepack(String depoBasepack, String launchId) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT CLD_SIZE FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME) = '" + depoBasepack + "' ");
			//+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "' ");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setCLD_SIZE(rs.getString("CLD_SIZE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getCldForDepoBasepackKAM(String depoBasepack, String launchId, String forWhichKam) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT CLD_SIZE FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT, ',' , SKU_NAME) = '" + depoBasepack + "' AND BUILDUP_ACCOUNT = '"
							+ forWhichKam + "'");
			//				+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "' AND BUILDUP_ACCOUNT = '"
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setCLD_SIZE(rs.getString("CLD_SIZE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getGsvForDepoBasepack(String depoBasepack, String launchId) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT GSV FROM TBL_LAUNCH_BUILDUP_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME) = '" + depoBasepack + "' ");
			//				+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "' ");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setGSV(rs.getString("GSV"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchBuildUpTemp getGsvForDepoBasepackKAM(String depoBasepack, String launchId, String forWhichKam) {
		LaunchBuildUpTemp launchBuildUpTemp = new LaunchBuildUpTemp();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT GSV FROM TBL_LAUNCH_BUILDUP_KAM_TEMP WHERE LAUNCH_ID = '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME) = '" + depoBasepack + "' AND BUILDUP_ACCOUNT = '"
							+ forWhichKam + "' ");
			//				+ "' AND DEPOT || ',' || SKU_NAME = '" + depoBasepack + "' AND BUILDUP_ACCOUNT = '"
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setGSV(rs.getString("GSV"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public int updateFinalValue(String depoCombo, String launchId, LaunchBuildUpTemp launchBuildUpTemp, String userId) {
		int returnSuccess = 0;
		try {
			//Garima - changes for concatenation
			Query query2 = sessionFactory.getCurrentSession()
					.createNativeQuery("UPDATE TBL_LAUNCH_BUILDUP_TEMP SET SELLIN_VALUE_N='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N() + "',SELLIN_VALUE_N1='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N1() + "',SELLIN_VALUE_N2='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N2() + "',UPDATED_BY='" + userId + "',UPDATED_DATE='"
							+ new Timestamp(new Date().getTime()) + "'" + " WHERE LAUNCH_ID= '" + launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME , ',' ,  FMCG_CSP_CODE , ',' , MODIFIED_CHAIN , ',' ,CLUSTER) = '" + depoCombo + "'");
			//				+ "' AND DEPOT || ',' || SKU_NAME || ',' ||  FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' "
			//				+ " || CLUSTER = '" + depoCombo + "'");
			returnSuccess = query2.executeUpdate();
		} catch (Exception e) {
			returnSuccess = 0;
			e.printStackTrace();
		}
		return returnSuccess;
	}
	
	//Sarin Changes 18Nov2020 - Starts
	@Override
	public int updateFinalValue(List<LaunchFinalCalVO> launchFinalVoList) {
		int returnSuccess = 0;
		int counter = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			//Garima - changes for concatenation
			for(LaunchFinalCalVO launchFinalCalVo : launchFinalVoList) {
				counter++;
				PreparedStatement preparedStatement = sessionImpl.connection().prepareStatement("UPDATE TBL_LAUNCH_BUILDUP_TEMP SET SELLIN_VALUE_N='"
						+ launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N() + "',SELLIN_VALUE_N1='"
						+ launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N1() + "',SELLIN_VALUE_N2='"
						+ launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N2() + "',UPDATED_BY='" + launchFinalCalVo.getUserId() + "',UPDATED_DATE='"
						+ new Timestamp(new Date().getTime()) + "'" + " WHERE LAUNCH_ID= '" + launchFinalCalVo.getLaunchId()
						+ "' AND CONCAT(DEPOT , ',' , SKU_NAME , ',' ,  FMCG_CSP_CODE , ',' , MODIFIED_CHAIN , ',' ,CLUSTER) = '" + launchFinalCalVo.getDepoBasePack() + "'");
				preparedStatement.addBatch();
				
				if(counter%10==0 || counter == launchFinalVoList.size())
					preparedStatement.executeBatch();
				
				returnSuccess = 1;
			}
				
				/*
				 * query2 = sessionFactory.getCurrentSession()
				 * .createNativeQuery("UPDATE TBL_LAUNCH_BUILDUP_TEMP SET SELLIN_VALUE_N='" +
				 * launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N() +
				 * "',SELLIN_VALUE_N1='" +
				 * launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N1() +
				 * "',SELLIN_VALUE_N2='" +
				 * launchFinalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N2() +
				 * "',UPDATED_BY='" + launchFinalCalVo.getUserId() + "',UPDATED_DATE='" + new
				 * Timestamp(new Date().getTime()) + "'" + " WHERE LAUNCH_ID= '" +
				 * launchFinalCalVo.getLaunchId() +
				 * "' AND CONCAT(DEPOT , ',' , SKU_NAME , ',' ,  FMCG_CSP_CODE , ',' , MODIFIED_CHAIN , ',' ,CLUSTER) = '"
				 * + launchFinalCalVo.getDepoBasePack() + "'"); // +
				 * "' AND DEPOT || ',' || SKU_NAME || ',' ||  FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' "
				 * // + " || CLUSTER = '" + depoCombo + "'");
				 */				
				//returnSuccess = query2.executeUpdate();
			//}
			
		} catch (Exception e) {
			returnSuccess = 0;
			e.printStackTrace();
		}
		return returnSuccess;
	}
	//Sarin Changes 18Nov2020 - Ends

	@Override
	public int updateFinalValueKAM(String depoCombo, String launchId, LaunchBuildUpTemp launchBuildUpTemp,
			String forWhichKam) {
		int returnSuccess = 0;
		try {
			//Garima - changes for concatenation
			Query query2 = sessionFactory.getCurrentSession()
					.createNativeQuery("UPDATE TBL_LAUNCH_BUILDUP_KAM_TEMP SET SELLIN_VALUE_N='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N() + "',SELLIN_VALUE_N1='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N1() + "',SELLIN_VALUE_N2='"
							+ launchBuildUpTemp.getSELLIN_VALUE_N2() + "',UPDATED_BY='" + forWhichKam
							+ "',UPDATED_DATE='" + new Timestamp(new Date().getTime()) + "'" + " WHERE LAUNCH_ID= '"
							+ launchId
							+ "' AND CONCAT(DEPOT , ',' , SKU_NAME , ',' ,  FMCG_CSP_CODE , ',' , MODIFIED_CHAIN , ',' , CLUSTER) = '" + depoCombo + "' AND BUILDUP_ACCOUNT = '" + forWhichKam + "'");
			//				+ "' AND DEPOT || ',' || SKU_NAME || ',' ||  FMCG_CSP_CODE || ',' || MODIFIED_CHAIN || ',' "
			//				+ " || CLUSTER = '" + depoCombo + "' AND BUILDUP_ACCOUNT = '" + forWhichKam + "'");
			returnSuccess = query2.executeUpdate();
		} catch (Exception e) {
			returnSuccess = 0;
			e.printStackTrace();
		}
		return returnSuccess;
	}

	@Override
	public LaunchFinalPlanResponse getSumOfForDepoBasepack(String basepack, String launchId) {
		LaunchFinalPlanResponse launchBuildUpTemp = new LaunchFinalPlanResponse();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT SUM(FINAL_UNITS_N) SELLIN_UNITS_N, SUM(FINAL_UNITS_N1) SELLIN_UNITS_N1, "
							+ "SUM(FINAL_UNITS_N2) SELLIN_UNITS_N2, SUM(FINAL_VALUE_N) SELLIN_VALUE_N, "
							+ "SUM(FINAL_VALUE_N1) SELLIN_VALUE_N1, SUM(FINAL_VALUE_N2) SELLIN_VALUE_N2, "
							+ "SUM(FINAL_CLD_N) SELLIN_VALUE_CLD_N, SUM(FINAL_CLD_N1) SELLIN_VALUE_CLD_N1, "
							+ "SUM(FINAL_CLD_N2) SELLIN_VALUE_CLD_N2, SUM(APPLIED_STORES) APPLIED_STORES FROM "
							+ "TBL_LAUNCH_TEMP_FINAL_CAL WHERE LAUNCH_ID = '" + launchId + "' AND BP_NAME = '"
							+ basepack + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				String sellInUnitForN = rs.getString("SELLIN_UNITS_N") == null ? "0" : rs.getString("SELLIN_UNITS_N");
				launchBuildUpTemp.setLaunchSellInUnit(sellInUnitForN);
				String sellInUnitForN1 = rs.getString("SELLIN_UNITS_N1") == null ? "0"
						: rs.getString("SELLIN_UNITS_N1");
				launchBuildUpTemp.setLaunchN1SellInUnit(sellInUnitForN1);
				String sellInUnitForN2 = rs.getString("SELLIN_UNITS_N2") == null ? "0"
						: rs.getString("SELLIN_UNITS_N2");
				launchBuildUpTemp.setLaunchN2SellInUnit(sellInUnitForN2);
				String sellInValueForN = rs.getString("SELLIN_VALUE_N") == null ? "0" : rs.getString("SELLIN_VALUE_N");
				launchBuildUpTemp.setLaunchSellInValue(sellInValueForN);
				String sellInValueForN1 = rs.getString("SELLIN_VALUE_N1") == null ? "0"
						: rs.getString("SELLIN_VALUE_N1");
				launchBuildUpTemp.setLaunchN1SellInVal(sellInValueForN1);
				String sellInValueForN2 = rs.getString("SELLIN_VALUE_N2") == null ? "0"
						: rs.getString("SELLIN_VALUE_N2");
				launchBuildUpTemp.setLaunchN2SellInVal(sellInValueForN2);
				String sellInValueCLDN = rs.getString("SELLIN_VALUE_CLD_N") == null ? "0"
						: rs.getString("SELLIN_VALUE_CLD_N");
				launchBuildUpTemp.setLaunchSellInCld(sellInValueCLDN);
				String sellInValueCLDN1 = rs.getString("SELLIN_VALUE_CLD_N1") == null ? "0"
						: rs.getString("SELLIN_VALUE_CLD_N1");
				launchBuildUpTemp.setLaunchN1SellInCld(sellInValueCLDN1);
				String sellInValueCLDN2 = rs.getString("SELLIN_VALUE_CLD_N2") == null ? "0"
						: rs.getString("SELLIN_VALUE_CLD_N2");
				launchBuildUpTemp.setLaunchN2SellInCld(sellInValueCLDN2);
				String appliedStores = rs.getString("APPLIED_STORES") == null ? "0" : rs.getString("APPLIED_STORES");
				launchBuildUpTemp.setStoreCount(appliedStores);
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public LaunchFinalPlanResponse getSumOfForDepoBasepackKAM(String basepack, String launchId, String whichKam) {
		LaunchFinalPlanResponse launchBuildUpTemp = new LaunchFinalPlanResponse();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			stmt = sessionImpl.connection()
					.prepareStatement("SELECT SUM(FINAL_UNITS_N) SELLIN_UNITS_N, SUM(FINAL_UNITS_N1) SELLIN_UNITS_N1, "
							+ "SUM(FINAL_UNITS_N2) SELLIN_UNITS_N2, SUM(FINAL_VALUE_N) SELLIN_VALUE_N, "
							+ "SUM(FINAL_VALUE_N1) SELLIN_VALUE_N1, SUM(FINAL_VALUE_N2) SELLIN_VALUE_N2, "
							+ "SUM(FINAL_CLD_N) SELLIN_VALUE_CLD_N, SUM(FINAL_CLD_N1) SELLIN_VALUE_CLD_N1, "
							+ "SUM(FINAL_CLD_N2) SELLIN_VALUE_CLD_N2, SUM(APPLIED_STORES) APPLIED_STORES FROM "
							+ "TBL_LAUNCH_TEMP_FINAL_CAL_KAM WHERE LAUNCH_ID = '" + launchId + "' AND BP_NAME = '"
							+ basepack + "' AND FINAL_CAL_ACCOUNT = '" + whichKam + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				launchBuildUpTemp.setLaunchSellInUnit(rs.getString("SELLIN_UNITS_N"));
				launchBuildUpTemp.setLaunchN1SellInUnit(rs.getString("SELLIN_UNITS_N1"));
				launchBuildUpTemp.setLaunchN2SellInUnit(rs.getString("SELLIN_UNITS_N2"));
				launchBuildUpTemp.setLaunchSellInValue(rs.getString("SELLIN_VALUE_N"));
				launchBuildUpTemp.setLaunchN1SellInVal(rs.getString("SELLIN_VALUE_N1"));
				launchBuildUpTemp.setLaunchN2SellInVal(rs.getString("SELLIN_VALUE_N2"));
				launchBuildUpTemp.setLaunchSellInCld(rs.getString("SELLIN_VALUE_CLD_N"));
				launchBuildUpTemp.setLaunchN1SellInCld(rs.getString("SELLIN_VALUE_CLD_N1"));
				launchBuildUpTemp.setLaunchN2SellInCld(rs.getString("SELLIN_VALUE_CLD_N2"));
				launchBuildUpTemp.setStoreCount(rs.getString("APPLIED_STORES"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			launchBuildUpTemp.setError(e.toString());
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return launchBuildUpTemp;
	}

	@Override
	public List<ArrayList<String>> getFinalBuildUpDump(String userId, String[] launchId) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchBuildUpTempCal> listOfLaunchBuildUpTempCal = getFinalBuildUpTempDataNew(launchId);
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("LAUNCH NAME");
		headerDetail.add("DEPOT");
		headerDetail.add("SKU_NAME");
		headerDetail.add("FMCG_CSP_CODE");
		headerDetail.add("MODIFIED_CHAIN");
		headerDetail.add("CLUSTER");
		headerDetail.add("FINAL_CLD_N");
		headerDetail.add("FINAL_CLD_N+1");
		headerDetail.add("FINAL_CLD_N+2");
		headerDetail.add("FINAL_UNITS_N");
		headerDetail.add("FINAL_UNITS_N+1");
		headerDetail.add("FINAL_UNITS_N+2");
		headerDetail.add("SELL_IN_VALUE_N");
		headerDetail.add("SELL_IN_VALUE_N+1");
		headerDetail.add("SELL_IN_VALUE_N+2");
		downloadedData.add(headerDetail);
		for (LaunchBuildUpTempCal launchBuildUpTempCal : listOfLaunchBuildUpTempCal) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchBuildUpTempCal.getLAUNCH_NAME());
			dataObj.add(launchBuildUpTempCal.getDEPOT());
			dataObj.add(launchBuildUpTempCal.getSKU_NAME());
			dataObj.add(launchBuildUpTempCal.getFMCG_CSP_CODE());
			dataObj.add(launchBuildUpTempCal.getMODIFIED_CHAIN());
			dataObj.add(launchBuildUpTempCal.getCLUSTER());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_CLD_N());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_CLD_N1());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_CLD_N2());
			dataObj.add(launchBuildUpTempCal.getSELLIN_UNITS_N());
			dataObj.add(launchBuildUpTempCal.getSELLIN_UNITS_N1());
			dataObj.add(launchBuildUpTempCal.getSELLIN_UNITS_N2());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_N());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_N());
			dataObj.add(launchBuildUpTempCal.getSELLIN_VALUE_N());
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}

	@Override
	public String saveFinalValueKAM(String depoBasepackFmcgModifiedChainCombo, String launchId,
			LaunchBuildUpTemp launchBuildUpTemp, String forWhichKam) {
		try {
			String[] depoBasepackLevel = depoBasepackFmcgModifiedChainCombo.split(",");
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			try (PreparedStatement preparedStatement = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_TEMP_FINAL_CAL_KAM, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, launchId);
				preparedStatement.setString(2, depoBasepackLevel[0]);
				preparedStatement.setString(3, depoBasepackLevel[1]);
				preparedStatement.setString(4, depoBasepackLevel[2]);
				preparedStatement.setString(5, depoBasepackLevel[3]);
				preparedStatement.setString(6, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N());
				preparedStatement.setString(7, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N1());
				preparedStatement.setString(8, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N2());
				preparedStatement.setString(9, launchBuildUpTemp.getSELLIN_VALUE_CLD_N());
				preparedStatement.setString(10, launchBuildUpTemp.getSELLIN_VALUE_CLD_N1());
				preparedStatement.setString(11, launchBuildUpTemp.getSELLIN_VALUE_CLD_N2());
				preparedStatement.setString(12, launchBuildUpTemp.getSELLIN_UNITS_N());
				preparedStatement.setString(13, launchBuildUpTemp.getSELLIN_UNITS_N1());
				preparedStatement.setString(14, launchBuildUpTemp.getSELLIN_UNITS_N2());
				preparedStatement.setString(15, launchBuildUpTemp.getSELLIN_VALUE_N());
				preparedStatement.setString(16, launchBuildUpTemp.getSELLIN_VALUE_N1());
				preparedStatement.setString(17, launchBuildUpTemp.getSELLIN_VALUE_N2());
				preparedStatement.setString(18, launchBuildUpTemp.getSTORE_COUNT());
				preparedStatement.setString(19, depoBasepackLevel[4]);
				preparedStatement.setString(20, forWhichKam);
				preparedStatement.executeUpdate();
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

	@Override
	public String saveFinalValue(String depoBasepackFmcgModifiedChainCombo, String launchId,
			LaunchBuildUpTemp launchBuildUpTemp, String userId) {
		try {
			String[] depoBasepackLevel = depoBasepackFmcgModifiedChainCombo.split(",");
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			try (PreparedStatement preparedStatement = sessionImpl.connection()
					.prepareStatement(TBL_LAUNCH_TEMP_FINAL_CAL, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, launchId);
				preparedStatement.setString(2, depoBasepackLevel[0]);
				preparedStatement.setString(3, depoBasepackLevel[1]);
				preparedStatement.setString(4, depoBasepackLevel[2]);
				preparedStatement.setString(5, depoBasepackLevel[3]);
				preparedStatement.setString(6, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N());
				preparedStatement.setString(7, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N1());
				preparedStatement.setString(8, launchBuildUpTemp.getREVISED_SELLIN_FOR_STORE_N2());
				preparedStatement.setString(9, launchBuildUpTemp.getSELLIN_VALUE_CLD_N());
				preparedStatement.setString(10, launchBuildUpTemp.getSELLIN_VALUE_CLD_N1());
				preparedStatement.setString(11, launchBuildUpTemp.getSELLIN_VALUE_CLD_N2());
				preparedStatement.setString(12, launchBuildUpTemp.getSELLIN_UNITS_N());
				preparedStatement.setString(13, launchBuildUpTemp.getSELLIN_UNITS_N1());
				preparedStatement.setString(14, launchBuildUpTemp.getSELLIN_UNITS_N2());
				preparedStatement.setString(15, launchBuildUpTemp.getSELLIN_VALUE_N());
				preparedStatement.setString(16, launchBuildUpTemp.getSELLIN_VALUE_N1());
				preparedStatement.setString(17, launchBuildUpTemp.getSELLIN_VALUE_N2());
				preparedStatement.setString(18, launchBuildUpTemp.getSTORE_COUNT());
				preparedStatement.setString(19, depoBasepackLevel[4]);
				preparedStatement.executeUpdate();
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

	@Override
	public String saveFinalValue(List<LaunchFinalCalVO> launchFinalVoList) {
		int counter = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			for(LaunchFinalCalVO launchFunalCalVo : launchFinalVoList) {
				counter++;
				String[] depoBasepackLevel = launchFunalCalVo.getDepoBasePack().split(",");

				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_TEMP_FINAL_CAL, Statement.RETURN_GENERATED_KEYS)) {
					preparedStatement.setString(1, launchFunalCalVo.getLaunchId());
					preparedStatement.setString(2, depoBasepackLevel[0]);
					preparedStatement.setString(3, depoBasepackLevel[1]);
					preparedStatement.setString(4, depoBasepackLevel[2]);
					preparedStatement.setString(5, depoBasepackLevel[3]);
					preparedStatement.setString(6, launchFunalCalVo.getLaunchBuildUpTemp().getREVISED_SELLIN_FOR_STORE_N());
					preparedStatement.setString(7, launchFunalCalVo.getLaunchBuildUpTemp().getREVISED_SELLIN_FOR_STORE_N1());
					preparedStatement.setString(8, launchFunalCalVo.getLaunchBuildUpTemp().getREVISED_SELLIN_FOR_STORE_N2());
					preparedStatement.setString(9, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_CLD_N());
					preparedStatement.setString(10, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_CLD_N1());
					preparedStatement.setString(11, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_CLD_N2());
					preparedStatement.setString(12, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_UNITS_N());
					preparedStatement.setString(13, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_UNITS_N1());
					preparedStatement.setString(14, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_UNITS_N2());
					preparedStatement.setString(15, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N());
					preparedStatement.setString(16, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N1());
					preparedStatement.setString(17, launchFunalCalVo.getLaunchBuildUpTemp().getSELLIN_VALUE_N2());
					preparedStatement.setString(18, launchFunalCalVo.getLaunchBuildUpTemp().getSTORE_COUNT());
					preparedStatement.setString(19, depoBasepackLevel[4]);
					preparedStatement.addBatch();
					if(counter%10==0 || counter == launchFinalVoList.size())
						preparedStatement.executeBatch();

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
	public void deleteAllTempCal(String launchId) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_TEMP_FINAL_CAL where LAUNCH_ID=?");
			batchUpdate.setString(1, launchId);
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllTempCalKam(String launchId, String forWhichKam) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection().prepareStatement(
					"DELETE from TBL_LAUNCH_TEMP_FINAL_CAL_KAM where LAUNCH_ID=? AND FINAL_CAL_ACCOUNT =?");
			batchUpdate.setString(1, launchId);
			batchUpdate.setString(2, forWhichKam);
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllBuildUp(String launchId) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_TEMP where LAUNCH_ID= ?");
			batchUpdate.setString(1, launchId);
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllBuildUpKAM(String launchId, String forWhichKam) {
		PreparedStatement batchUpdate = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SessionImpl sessionImpl = (SessionImpl) session;
			batchUpdate = sessionImpl.connection().prepareStatement(
					"DELETE from TBL_LAUNCH_BUILDUP_KAM_TEMP where LAUNCH_ID= ? AND BUILDUP_ACCOUNT = ?");
			batchUpdate.setString(1, launchId);
			batchUpdate.setString(2, forWhichKam);
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ArrayList<String>> getFinalBuildUpDumptNew(String userId, String[] launchId) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchBuildUpTempCal> listOfFinalBuildups = getFinalBuildUpTempDataNew(launchId);
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("LAUNCH_ID");
		headerDetail.add("LAUNCH_NAME");
		headerDetail.add("LAUNCH_MOC");
		headerDetail.add("DEPOT");
		headerDetail.add("SKU_NAME");
		headerDetail.add("FMCG_CSP_CODE");
		headerDetail.add("MODIFIED_CHAIN");
		headerDetail.add("CLUSTER");
		headerDetail.add("FINAL_CLD_N");
		headerDetail.add("FINAL_CLD_N+1");
		headerDetail.add("FINAL_CLD_N+2");
		headerDetail.add("FINAL_UNITS_N");
		headerDetail.add("FINAL_UNITS_N+1");
		headerDetail.add("FINAL_UNITS_N+2");
		headerDetail.add("SELL_IN_VALUE_N");
		headerDetail.add("SELL_IN_VALUE_N+1");
		headerDetail.add("SELL_IN_VALUE_N+2");
		downloadedData.add(headerDetail);
		for (LaunchBuildUpTempCal launchBuildUpTemp : listOfFinalBuildups) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchBuildUpTemp.getLAUNCH_ID());
			dataObj.add(launchBuildUpTemp.getLAUNCH_NAME());
			dataObj.add(launchBuildUpTemp.getLAUNCH_MOC());
			dataObj.add(launchBuildUpTemp.getDEPOT());
			dataObj.add(launchBuildUpTemp.getSKU_NAME());
			dataObj.add(launchBuildUpTemp.getFMCG_CSP_CODE());
			dataObj.add(launchBuildUpTemp.getMODIFIED_CHAIN());
			dataObj.add(launchBuildUpTemp.getCLUSTER());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N2());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N2());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N1());
			dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N2());
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}

	@SuppressWarnings("resource")
	@Override
	public List<ArrayList<String>> getFinalBuildUpDumpNewKam(String userId, String launchId) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchBuildUpTempCal> listOfFinalBuildups = getFinalBuildUpTempDataNewKam(launchId, userId);
		ArrayList<String> headerDetail = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			headerDetail.add("LAUNCH_NAME");
			headerDetail.add("LAUNCH_MOC");
			headerDetail.add("DEPOT");
			headerDetail.add("SKU_NAME");
			headerDetail.add("FMCG_CSP_CODE");
			headerDetail.add("MODIFIED_CHAIN");
			headerDetail.add("CLUSTER");
			headerDetail.add("FINAL_CLD_N");
			headerDetail.add("FINAL_CLD_N+1");
			headerDetail.add("FINAL_CLD_N+2");
			headerDetail.add("FINAL_UNITS_N");
			headerDetail.add("FINAL_UNITS_N+1");
			headerDetail.add("FINAL_UNITS_N+2");
			headerDetail.add("SELL_IN_VALUE_N");
			headerDetail.add("SELL_IN_VALUE_N+1");
			headerDetail.add("SELL_IN_VALUE_N+2");
			downloadedData.add(headerDetail);
			for (LaunchBuildUpTempCal launchBuildUpTemp : listOfFinalBuildups) {
				ArrayList<String> dataObj = new ArrayList<>();
				dataObj.add(launchBuildUpTemp.getLAUNCH_NAME());
				String launchMoc = null;
				stmt = sessionImpl.connection()
						.prepareStatement("SELECT LAUNCH_MOC FROM TBL_LAUNCH_MOC_KAM WHERE LAUNCH_ID = '" + launchId
								+ "' AND LAUNCH_ACCOUNT = '" + userId + "'");
				rs = stmt.executeQuery();
				if (rs.next()) {
					launchMoc = rs.getString("LAUNCH_MOC");
				} else {
					launchMoc = launchBuildUpTemp.getLAUNCH_MOC();
				}
				dataObj.add(launchMoc);
				dataObj.add(launchBuildUpTemp.getDEPOT());
				dataObj.add(launchBuildUpTemp.getSKU_NAME());
				dataObj.add(launchBuildUpTemp.getFMCG_CSP_CODE());
				dataObj.add(launchBuildUpTemp.getMODIFIED_CHAIN());
				dataObj.add(launchBuildUpTemp.getCLUSTER());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N1());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_CLD_N2());
				dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N());
				dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N1());
				dataObj.add(launchBuildUpTemp.getSELLIN_UNITS_N2());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N1());
				dataObj.add(launchBuildUpTemp.getSELLIN_VALUE_N2());
				downloadedData.add(dataObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return downloadedData;
	}

	@SuppressWarnings("unchecked")
	private List<LaunchBuildUpTempCal> getFinalBuildUpTempDataNewKam(String launchId, String userId) {
		List<LaunchBuildUpTempCal> liReturn = new ArrayList<>();
		try {
			String userIdUpper = userId.toUpperCase();
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"SELECT DISTINCT DEPOT, BP_NAME, FMCG_CSP_CODE, MODIFIED_CHAIN, CLUSTER,FINAL_CLD_N,FINAL_CLD_N1,FINAL_CLD_N2, FINAL_UNITS_N,FINAL_UNITS_N1,FINAL_UNITS_N2,FINAL_VALUE_N,FINAL_VALUE_N1,FINAL_VALUE_N2,tlm.LAUNCH_MOC,tlm.LAUNCH_NAME,tlb.BP_ID FROM TBL_LAUNCH_TEMP_FINAL_CAL_KAM tltfc, TBL_LAUNCH_MASTER tlm, TBL_LAUNCH_BASEPACK tlb WHERE tltfc.LAUNCH_ID = :launchId AND tltfc.LAUNCH_ID = tlm.LAUNCH_ID AND tlb.LAUNCH_ID = tlm.LAUNCH_Id and tlb.BP_DESCRIPTION =tltfc.BP_NAME");
			query.setParameter("launchId", launchId);
			//query.setParameter("userIdUpper", userIdUpper);
			Iterator<Object> itr = query.list().iterator();
			int[] temp = { 1 };
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				Query query3 = sessionFactory.getCurrentSession()
						.createNativeQuery("SELECT LAUNCH_BASEPACK FROM TBL_LAUNCH_BASEPACK_KAM WHERE LAUNCH_ACCOUNT = '"
								+ userId + "' AND LAUNCH_ID = '" + launchId + "'");
				List<String> listOfBp = query3.list();
				List<String> bpIds = new ArrayList<>();
				if (!listOfBp.isEmpty()) {
					bpIds = Arrays.asList(listOfBp.get(0).toString().split(","));
				}

				if (!bpIds.contains(obj[16].toString())) {
					LaunchBuildUpTempCal launchBuildUpTempCal = new LaunchBuildUpTempCal();
					launchBuildUpTempCal.setDEPOT((String) obj[0]);
					launchBuildUpTempCal.setSKU_NAME((String) obj[1]);
					launchBuildUpTempCal.setFMCG_CSP_CODE((String) obj[2]);
					launchBuildUpTempCal.setMODIFIED_CHAIN((String) obj[3]);
					launchBuildUpTempCal.setCLUSTER((String) obj[4]);
					launchBuildUpTempCal.setSELLIN_VALUE_CLD_N((String) obj[5]);
					launchBuildUpTempCal.setSELLIN_VALUE_CLD_N1((String) obj[6]);
					launchBuildUpTempCal.setSELLIN_VALUE_CLD_N2((String) obj[7]);
					launchBuildUpTempCal.setSELLIN_UNITS_N((String) obj[8]);
					launchBuildUpTempCal.setSELLIN_UNITS_N1((String) obj[9]);
					launchBuildUpTempCal.setSELLIN_UNITS_N2((String) obj[10]);
					launchBuildUpTempCal.setSELLIN_VALUE_N((String) obj[11]);
					launchBuildUpTempCal.setSELLIN_VALUE_N1((String) obj[12]);
					launchBuildUpTempCal.setSELLIN_VALUE_N2((String) obj[13]);
					launchBuildUpTempCal.setLAUNCH_MOC((String) obj[14]);
					launchBuildUpTempCal.setLAUNCH_NAME((String) obj[15]);
					liReturn.add(launchBuildUpTempCal);
				}
				temp[0]++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LaunchBuildUpTempCal launchBuildUpTemp = new LaunchBuildUpTempCal();
			launchBuildUpTemp.setError(e.toString());
			liReturn.add(launchBuildUpTemp);
		}
		return liReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getKamAccount(String userId) {
		List<String> listOfAccounts = null;
		try {
			String kamMailId = userId.concat("@unilever.com").toUpperCase();
			Query query3 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT DISTINCT ACCOUNT_NAME FROM TBL_VAT_COMM_OUTLET_MASTER tvcom WHERE KAM_MAIL_ID = '"
							+ kamMailId + "'");
			listOfAccounts = query3.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfAccounts;
	}

	@Override
	public void deleteAllBuildUpKAM(String launchId, List<String> listOfKamAccounts) {

		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate = null;
		try {
			String finalString = "";
			for (int i = 0; i < listOfKamAccounts.size(); i++) {
				if ((i + 1) >= listOfKamAccounts.size()) {
					finalString = finalString + "'" + listOfKamAccounts.get(i) + "'";
				} else {
					finalString = "'" + listOfKamAccounts.get(i) + "'," + finalString;
				}
			}
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_TEMP where LAUNCH_ID = '" + launchId
							+ "'  AND ACCOUNT_NAME IN (" + finalString + ")");
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllBuildUpKAMBp(String launchId, List<String> listOfKamAccounts, List<String> bpCodes) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate = null;
		try {
			String finalString = "";
			for (int i = 0; i < listOfKamAccounts.size(); i++) {
				if ((i + 1) >= listOfKamAccounts.size()) {
					finalString = finalString + "'" + listOfKamAccounts.get(i) + "'";
				} else {
					finalString = "'" + listOfKamAccounts.get(i) + "'," + finalString;
				}
			}

			String basePacks = "";
			for (int i = 0; i < bpCodes.size(); i++) {
				if ((i + 1) >= bpCodes.size()) {
					basePacks = basePacks + "'" + bpCodes.get(i) + "'";
				} else {
					basePacks = "'" + bpCodes.get(i) + "'," + basePacks;
				}
			}
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_TEMP where LAUNCH_ID = '" + launchId
							+ "'  AND ACCOUNT_NAME IN (" + finalString + ") AND BASEPACK_CODE IN (" + basePacks + ")");
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAllBuildUpKAMStore(String launchId, List<String> listOfKamAccounts, List<String> storeIds) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate = null;
		try {
			String finalString = "";
			for (int i = 0; i < listOfKamAccounts.size(); i++) {
				if ((i + 1) >= listOfKamAccounts.size()) {
					finalString = finalString + "'" + listOfKamAccounts.get(i) + "'";
				} else {
					finalString = "'" + listOfKamAccounts.get(i) + "'," + finalString;
				}
			}

			String storeId = "";
			for (int i = 0; i < storeIds.size(); i++) {
				if ((i + 1) >= storeIds.size()) {
					storeId = storeId + "'" + storeIds.get(i) + "'";
				} else {
					storeId = "'" + storeIds.get(i) + "'," + storeId;
				}
			}
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_BUILDUP_TEMP where LAUNCH_ID = '" + launchId
							+ "'  AND ACCOUNT_NAME IN (" + finalString + ") AND REPORTING_CODE IN (" + storeId + ")");
			batchUpdate.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ArrayList<String>> getMstnClearanceDataDump(String userId, List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchScMstnClearanceResponse> listOfFinalBuildups = launchDaoSc.getScMstnClearanceDataDump(listOfLaunchData);
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("LAUNCH_ID");
		headerDetail.add("LAUNCH_NAME");
		headerDetail.add("LAUNCH_MOC");
		headerDetail.add("BASEPACK_CODE");
		headerDetail.add("BASEPACK_DESCRIPTION");
		headerDetail.add("DEPOT");
		headerDetail.add("CLUSTER");
		headerDetail.add("MSTN_CLEARED");
		headerDetail.add("FINAL_CLD_N");
		headerDetail.add("FINAL_CLD_N1");
		headerDetail.add("FINAL_CLD_N2");
		headerDetail.add("ACCOUNT");
		headerDetail.add("CURRENT_ESTIMATES");
		headerDetail.add("CLEARANCE_DATE");
		downloadedData.add(headerDetail);
		for (LaunchScMstnClearanceResponse launchScMstnClearanceResponse : listOfFinalBuildups) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchScMstnClearanceResponse.getLaunchId());
			dataObj.add(launchScMstnClearanceResponse.getLaunchName());
			dataObj.add(launchScMstnClearanceResponse.getLaunchMoc());
			dataObj.add(launchScMstnClearanceResponse.getBasepackCode());
			dataObj.add(launchScMstnClearanceResponse.getBasepackDesc());
			dataObj.add(launchScMstnClearanceResponse.getDepot());
			dataObj.add(launchScMstnClearanceResponse.getCluster());
			dataObj.add("");
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN());
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN1());
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN2());
			dataObj.add(launchScMstnClearanceResponse.getAccount());
			dataObj.add("");
			dataObj.add("");
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}

	@Override
	public List<ArrayList<String>> getMstnClearanceDataDumpCoe(String userId, List<String> listOfLaunchData) {
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		List<LaunchMstnClearanceResponseCoe> listOfFinalBuildups = launchDaoCoe
				.getCoeMstnClearanceData(listOfLaunchData);
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("LAUNCH_NAME");
		headerDetail.add("BASEPACK_CODE");
		headerDetail.add("BASEPACK_DESCRIPTION");
		headerDetail.add("LAUNCH_MOC");
		headerDetail.add("CLUSTER");
		headerDetail.add("ACCOUNT");
		headerDetail.add("DEPOT");
		headerDetail.add("ACCOUNT");
		headerDetail.add("MSTN_CLEARED");
		headerDetail.add("FINAL_CLD_N");
		headerDetail.add("FINAL_CLD_N1");
		headerDetail.add("FINAL_CLD_N2");
		headerDetail.add("CURRENT_ESTIMATES");
		headerDetail.add("CLEARANCE_DATE");
		downloadedData.add(headerDetail);
		for (LaunchMstnClearanceResponseCoe launchScMstnClearanceResponse : listOfFinalBuildups) {
			ArrayList<String> dataObj = new ArrayList<>();
			dataObj.add(launchScMstnClearanceResponse.getLaunchName());
			dataObj.add(launchScMstnClearanceResponse.getBpCode());
			dataObj.add(launchScMstnClearanceResponse.getBpDescription());
			dataObj.add(launchScMstnClearanceResponse.getLaunchMoc());
			dataObj.add(launchScMstnClearanceResponse.getCluster());
			dataObj.add(launchScMstnClearanceResponse.getAccount());
			dataObj.add(launchScMstnClearanceResponse.getDepot());
			dataObj.add(launchScMstnClearanceResponse.getAccount());
			dataObj.add(launchScMstnClearanceResponse.getMstnCleared());
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN());
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN1());
			dataObj.add(launchScMstnClearanceResponse.getFinalCldN2());
			dataObj.add(launchScMstnClearanceResponse.getCurrentEstimates());
			dataObj.add(launchScMstnClearanceResponse.getClearanceDate());
			downloadedData.add(dataObj);
		}
		return downloadedData;
	}
}
