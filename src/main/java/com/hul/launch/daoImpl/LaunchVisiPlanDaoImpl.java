package com.hul.launch.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hul.launch.dao.LaunchVisiPlanDao;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.request.SaveLaunchVisiPlanRequest;
import com.hul.launch.request.SaveLaunchVisiPlanRequestList;
import com.hul.launch.response.LaunchVisiPlanResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchVisiPlanDaoImpl implements LaunchVisiPlanDao {

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private final static String TBL_LAUNCH_VISIPLAN = "INSERT INTO TBL_LAUNCH_VISIPLAN (VISIPLAN_LAUNCH_ID, VISIPLAN_ACCOUNT, "
			+ "VISIPLAN_FORMAT, VISIPLAN_STORES_AVAILABLE, VISIPLAN_STORES_PLANNED, VISIPLAN_VISI_ASSET_1, VISIPLAN_VISI_ASSET_2,"
			+ " VISIPLAN_VISI_ASSET_3, VISIPLAN_VISI_ASSET_4, VISIPLAN_VISI_ASSET_5, VISIPLAN_FACINGS_1, VISIPLAN_DEPTH_1, CREATED_BY, "
			+ "CREATED_DATE, VISIPLAN_FACINGS_2, VISIPLAN_DEPTH_2, VISIPLAN_FACINGS_3, VISIPLAN_DEPTH_3, VISIPLAN_FACINGS_4, VISIPLAN_DEPTH_4,"
			+ " VISIPLAN_FACINGS_5, VISIPLAN_DEPTH_5) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public List<LaunchVisiPlanResponse> getVisiPlanLandingScreen(String LaunchId) {
		List<LaunchVisiPlanResponse> liLaunchVisiPlanData = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//Garima - changes for concatenation
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT CONCAT(SELLIN_L1_CHAIN , ':' , SELLIN_L2_CHAIN) AS ACCOUNTS, SELLIN_STORE_FORMAT, SELLIN_STORES_PLANNED FROM TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID = '"
							+ LaunchId + "'");
			//		"SELECT SELLIN_L1_CHAIN || ':' || SELLIN_L2_CHAIN ACCOUNTS, SELLIN_STORE_FORMAT, SELLIN_STORES_PLANNED FROM TBL_LAUNCH_SELLIN where SELLIN_LAUNCH_ID = '"
			//		+ LaunchId + "'");

			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchVisiPlanResponse launchVisiPlanResponse = new LaunchVisiPlanResponse();
				launchVisiPlanResponse.setAccount(rs.getString(1));
				launchVisiPlanResponse.setFormat(rs.getString(2));
				launchVisiPlanResponse.setStoreAvailable(rs.getString(3));
				liLaunchVisiPlanData.add(launchVisiPlanResponse);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchVisiPlanResponse launchVisiPlanResponse = new LaunchVisiPlanResponse();
			launchVisiPlanResponse.setError(ex.toString());
			liLaunchVisiPlanData.add(launchVisiPlanResponse);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return liLaunchVisiPlanData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLaunchAssetType() {
		List<String> liReturn = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("SELECT DISTINCT ASSET_TYPE FROM TBL_VAT_VISIBILITY_PLAN_MASTER ORDER BY ASSET_TYPE ");
			liReturn = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			liReturn.add(e.toString());
		}
		return liReturn;
	}

	@Override
	public String saveLaunchVisiPlan(SaveLaunchVisiPlanRequestList saveLaunchVisiPlanRequestList, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate = null;
		try {
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
			batchUpdate.setInt(1, saveLaunchVisiPlanRequestList.getLaunchId());
			batchUpdate.executeUpdate();
			List<SaveLaunchVisiPlanRequest> liRequests = saveLaunchVisiPlanRequestList.getListOfVisiPlans();
			for (SaveLaunchVisiPlanRequest tblLaunchVisiPlan : liRequests) {
				try (PreparedStatement preparedStatement = sessionImpl.connection()
						.prepareStatement(TBL_LAUNCH_VISIPLAN)) {
					preparedStatement.setInt(1, saveLaunchVisiPlanRequestList.getLaunchId());
					preparedStatement.setString(2, tblLaunchVisiPlan.getAccount());
					preparedStatement.setString(3, tblLaunchVisiPlan.getFormat());
					preparedStatement.setInt(4, tblLaunchVisiPlan.getStoresAvailable());
					preparedStatement.setInt(5, tblLaunchVisiPlan.getStoresPlanned());
					preparedStatement.setString(6, tblLaunchVisiPlan.getVisiAsset1());
					preparedStatement.setString(7, tblLaunchVisiPlan.getVisiAsset2());
					preparedStatement.setString(8, tblLaunchVisiPlan.getVisiAsset3());
					preparedStatement.setString(9, tblLaunchVisiPlan.getVisiAsset4());
					preparedStatement.setString(10, tblLaunchVisiPlan.getVisiAsset5());
					preparedStatement.setString(11, tblLaunchVisiPlan.getFacingsPerShelf1());
					preparedStatement.setString(12, tblLaunchVisiPlan.getDepthPerShelf1());
					preparedStatement.setString(13, userId);
					preparedStatement.setTimestamp(14, new Timestamp(new Date().getTime()));
					preparedStatement.setString(15, tblLaunchVisiPlan.getFacingsPerShelf2());
					preparedStatement.setString(16, tblLaunchVisiPlan.getDepthPerShelf2());
					preparedStatement.setString(17, tblLaunchVisiPlan.getFacingsPerShelf3());
					preparedStatement.setString(18, tblLaunchVisiPlan.getDepthPerShelf3());
					preparedStatement.setString(19, tblLaunchVisiPlan.getFacingsPerShelf4());
					preparedStatement.setString(20, tblLaunchVisiPlan.getDepthPerShelf4());
					preparedStatement.setString(21, tblLaunchVisiPlan.getFacingsPerShelf5());
					preparedStatement.setString(22, tblLaunchVisiPlan.getDepthPerShelf5());
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					logger.error("Exception: " + e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public List<ArrayList<String>> getVisiPlanDump(ArrayList<String> headerDetail, String userId,
			SaveLaunchVisiPlanRequestList downloadLaunchVisiPlanRequest) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			downloadDataList.add(headerDetail);
			List<SaveLaunchVisiPlanRequest> listOfVisiPlan = downloadLaunchVisiPlanRequest.getListOfVisiPlans();
			for (int i = 0; i < listOfVisiPlan.size(); i++) {
				SaveLaunchVisiPlanRequest downloadVisiPlanRequest = listOfVisiPlan.get(i);
				ArrayList<String> dataObj = new ArrayList<String>();
				dataObj.add(downloadVisiPlanRequest.getAccount());
				dataObj.add(downloadVisiPlanRequest.getFormat());
				dataObj.add(Integer.toString(downloadVisiPlanRequest.getStoresAvailable()));
				dataObj.add("");
				dataObj.add(downloadVisiPlanRequest.getVisiAsset1());
				dataObj.add("");
				dataObj.add("");
				dataObj.add(downloadVisiPlanRequest.getVisiAsset2());
				dataObj.add("");
				dataObj.add("");
				dataObj.add(downloadVisiPlanRequest.getVisiAsset3());
				dataObj.add("");
				dataObj.add("");
				dataObj.add(downloadVisiPlanRequest.getVisiAsset4());
				dataObj.add("");
				dataObj.add("");
				dataObj.add(downloadVisiPlanRequest.getVisiAsset5());
				dataObj.add("");
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
	public String getVisiByLaunch(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		int skuVisi1 = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT * FROM TBL_LAUNCH_VISIPLAN tlv WHERE VISIPLAN_LAUNCH_ID =  '" + launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int facing = rs.getInt(11);
				int depth = rs.getInt(12);
				int visi1 = 0;
				if (!rs.getString(6).equals("")) {
					visi1 = getShelvesByVisiName(rs.getString(6));
				}

				int visi2 = 0;
				if (!rs.getString(7).equals("")) {
					visi2 = getShelvesByVisiName(rs.getString(7));
				}

				int visi3 = 0;

				if (!rs.getString(8).equals("")) {
					visi3 = getShelvesByVisiName(rs.getString(8));
				}

				int visi4 = 0;
				if (!rs.getString(9).equals("")) {
					visi4 = getShelvesByVisiName(rs.getString(9));
				}

				int visi5 = 0;
				if (!rs.getString(10).equals("")) {
					visi5 = getShelvesByVisiName(rs.getString(10));
				}

				skuVisi1 = skuVisi1 + (visi1 * depth * facing) + (visi2 * depth * facing) + (visi3 * depth * facing)
						+ (visi4 * depth * facing) + (visi5 * depth * facing);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Integer.toString(skuVisi1);
	}

	@Override
	public int getShelvesByVisiName(String visiName) {
		String toReturn = "0";
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT DISTINCT NO_OF_SHELVES FROM TBL_VAT_VISIBILITY_PLAN_MASTER WHERE NO_OF_SHELVES IS NOT NULL AND ASSET_TYPE =  '" + visiName
							+ "'");

			rs = stmt.executeQuery();
			while (rs.next()) {
				toReturn = rs.getString("NO_OF_SHELVES");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception :", ex);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Integer.parseInt(toReturn);
	}

	@Override
	public List<String> getVisiByLaunchInd(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<String> listofString = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT * FROM TBL_LAUNCH_VISIPLAN tlv WHERE VISIPLAN_LAUNCH_ID =  '" + launchId + "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int facing = rs.getInt(11);
				int depth = rs.getInt(12);
				int visi1 = 0;
				int visi2 = 0;
				int visi3 = 0;
				int visi4 = 0;
				int visi5 = 0;
				if (!rs.getString(6).equals("")) {
					visi1 = getShelvesByVisiName(rs.getString(6));
				}
				if (!rs.getString(7).equals("")) {
					visi2 = getShelvesByVisiName(rs.getString(7));
				}
				if (!rs.getString(8).equals("")) {
					visi3 = getShelvesByVisiName(rs.getString(8));
				}
				if (!rs.getString(9).equals("")) {
					visi4 = getShelvesByVisiName(rs.getString(9));
				}
				if (!rs.getString(10).equals("")) {
					visi5 = getShelvesByVisiName(rs.getString(10));
				}
				int visi1Cal = visi1 * depth * facing;
				int visi2Cal = visi2 * depth * facing;
				int visi3Cal = visi3 * depth * facing;
				int visi4Cal = visi4 * depth * facing;
				int visi5Cal = visi5 * depth * facing;
				int noOfStoreAvl = rs.getInt(4);
				int noOfStorePln = rs.getInt(5);
				listofString.add(Integer.toString(
						(visi1Cal + visi2Cal + visi3Cal + visi4Cal + visi5Cal) * (noOfStoreAvl / noOfStorePln)));
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listofString;
	}

	@Override
	public List<LaunchVisiPlanning> getVisiByLaunchId(String launchId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchVisiPlanning> listOfVisi = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT VISIPLAN_ACCOUNT,VISIPLAN_FORMAT,VISIPLAN_STORES_AVAILABLE,VISIPLAN_STORES_PLANNED,"
							+ "VISIPLAN_VISI_ASSET_1,VISIPLAN_VISI_ASSET_2,VISIPLAN_VISI_ASSET_3,VISIPLAN_VISI_ASSET_4,"
							+ "VISIPLAN_VISI_ASSET_5,VISIPLAN_FACINGS_1,VISIPLAN_DEPTH_1,VISIPLAN_FACINGS_2,VISIPLAN_DEPTH_2,"
							+ "VISIPLAN_FACINGS_3,VISIPLAN_DEPTH_3,VISIPLAN_FACINGS_4,VISIPLAN_DEPTH_4,VISIPLAN_FACINGS_5,"
							+ "VISIPLAN_DEPTH_5 FROM TBL_LAUNCH_VISIPLAN tlv WHERE VISIPLAN_LAUNCH_ID =  '" + launchId
							+ "'");
			rs = stmt.executeQuery();
			while (rs.next()) {
				LaunchVisiPlanning launchVisiPlanning = new LaunchVisiPlanning();
				launchVisiPlanning.setACCOUNT(rs.getString("VISIPLAN_ACCOUNT"));
				launchVisiPlanning.setFORMAT(rs.getString("VISIPLAN_FORMAT"));
				launchVisiPlanning.setSTORES_AVAILABLE(rs.getString("VISIPLAN_STORES_AVAILABLE"));
				launchVisiPlanning.setSTORES_PLANNED(rs.getString("VISIPLAN_STORES_PLANNED"));
				launchVisiPlanning.setVISI_ASSET_1(rs.getString("VISIPLAN_VISI_ASSET_1"));
				launchVisiPlanning.setVISI_ASSET_2(rs.getString("VISIPLAN_VISI_ASSET_2"));
				launchVisiPlanning.setVISI_ASSET_3(rs.getString("VISIPLAN_VISI_ASSET_3"));
				launchVisiPlanning.setVISI_ASSET_4(rs.getString("VISIPLAN_VISI_ASSET_4"));
				launchVisiPlanning.setVISI_ASSET_5(rs.getString("VISIPLAN_VISI_ASSET_5"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU1(rs.getString("VISIPLAN_FACINGS_1"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU1(rs.getString("VISIPLAN_DEPTH_1"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU2(rs.getString("VISIPLAN_FACINGS_2"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU2(rs.getString("VISIPLAN_DEPTH_2"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU3(rs.getString("VISIPLAN_FACINGS_3"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU3(rs.getString("VISIPLAN_DEPTH_3"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU4(rs.getString("VISIPLAN_FACINGS_4"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU4(rs.getString("VISIPLAN_DEPTH_4"));
				launchVisiPlanning.setFACING_PER_SHELF_PER_SKU5(rs.getString("VISIPLAN_FACINGS_5"));
				launchVisiPlanning.setDEPTH_PER_SHELF_PER_SKU5(rs.getString("VISIPLAN_DEPTH_5"));
				listOfVisi.add(launchVisiPlanning);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listOfVisi;
	}

	@Override
	public String saveLaunchNoVisiPlan(String launchId, String userId) {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		PreparedStatement batchUpdate = null;
		try {
			batchUpdate = sessionImpl.connection()
					.prepareStatement("DELETE from TBL_LAUNCH_VISIPLAN where VISIPLAN_LAUNCH_ID=?");
			batchUpdate.setString(1, launchId);
			batchUpdate.executeUpdate();
			return "Saved Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				batchUpdate.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}