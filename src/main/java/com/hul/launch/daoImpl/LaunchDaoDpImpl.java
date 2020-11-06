package com.hul.launch.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.dao.LaunchDaoDp;
import com.hul.launch.dao.LoginDao;
import com.hul.launch.exception.GlobleKamException;
import com.hul.launch.model.User;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchDpBasepackResponse;
import com.hul.launch.response.LaunchDpFinalBuildUpResponse;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchDaoDpImpl implements LaunchDaoDp {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private LoginDao loginDao;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public List<LaunchDataResponse> getAllCompletedDpLaunchData() {
		Session session = sessionFactory.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		List<LaunchDataResponse> listOfCompletedLaunch = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//kiran - changes for TRANSLATE
			/*stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' AND DATE(TRANSLATE('GHIJ-DE-AB', LAUNCH_DATE, 'ABCDEFGHIJ')) > NOW()");*/
			stmt = sessionImpl.connection().prepareStatement(
					"SELECT LAUNCH_ID, LAUNCH_NAME, LAUNCH_DATE, LAUNCH_NATURE, LAUNCH_NATURE_2, LAUNCH_BUSINESS_CASE, CATEGORY_SIZE,"
							+ " CLASSIFICATION,ANNEXURE_DOCUMENT_NAME,ARTWORK_PACKSHOTS_DOC_NAME,MDG_DECK_DOCUMENT_NAME,SAMPLE_SHARED,"
							+ " CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE,LAUNCH_MOC,LAUNCH_SUBMISSION_DATE FROM TBL_LAUNCH_MASTER tlc WHERE"
							+ " SAMPLE_SHARED IS NOT NULL AND LAUNCH_REJECTED != '2' AND date_format(str_to_date(LAUNCH_DATE,'%d/%m/%Y'),'%Y-%m-%d') > NOW()");
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
	public List<LaunchDpBasepackResponse> getDpBasepackData(List<String> listOfLaunchData) {
		List<LaunchDpBasepackResponse> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,tlb.BP_SALES_CAT,tlb.BP_PSA_CAT,tlb.BP_BRAND,tlb.BP_CODE,tlb.BP_DESCRIPTION,tlb.BP_MRP,"
							+ "tlb.BP_TUR,tlb.BP_GSV,tlb.BP_CLD_CONFIG,tlb.BP_GRAMMAGE,tlb.BP_CLASSIFICATION,tlb.BP_ID FROM TBL_LAUNCH_BASEPACK "
							+ "tlb,TBL_LAUNCH_MASTER tlm WHERE tlm.LAUNCH_ID = tlb.LAUNCH_ID AND tlb.LAUNCH_ID IN (:listOfLaunchData)"
							+ " AND (tlb.BP_STATUS IS NULL OR tlb.BP_STATUS NOT IN ('REJECTED BY KAM','REJECTED BY TME'))");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchDpBasepackResponse launchDpBasepackResponse = new LaunchDpBasepackResponse();
				launchDpBasepackResponse.setLaunchName(obj[0].toString());
				launchDpBasepackResponse.setSalesCat(obj[1].toString());
				launchDpBasepackResponse.setPsaCat(obj[2].toString());
				launchDpBasepackResponse.setBrand(obj[3].toString());
				launchDpBasepackResponse.setBpCode(obj[4].toString());
				launchDpBasepackResponse.setBpDisc(obj[5].toString());
				launchDpBasepackResponse.setMrp(obj[6].toString());
				launchDpBasepackResponse.setTur(obj[7].toString());
				launchDpBasepackResponse.setGsv(obj[8].toString());
				launchDpBasepackResponse.setCldConfig(obj[9].toString());
				launchDpBasepackResponse.setGrammage(obj[10].toString());
				launchDpBasepackResponse.setClassification(obj[11].toString());
				launchDpBasepackResponse.setBasepackId(obj[12].toString());
				listOfCompletedLaunch.add(launchDpBasepackResponse);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_DP_BASEPACK_DATA, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchDpFinalBuildUpResponse> getDpBuildUpData(List<String> listOfLaunchData) {
		List<LaunchDpFinalBuildUpResponse> listOfCompletedLaunch = new ArrayList<>();
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
				LaunchDpFinalBuildUpResponse launchDpFinalBuildUpResponse = new LaunchDpFinalBuildUpResponse();
				launchDpFinalBuildUpResponse.setLaunchName(obj[0].toString());
				launchDpFinalBuildUpResponse.setSkuName(obj[1].toString());
				launchDpFinalBuildUpResponse.setBasepackCode(obj[2].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInValueN(obj[3].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInValueN1(obj[4].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInValueN2(obj[5].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInCldValueN(obj[6].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInCldValueN1(obj[7].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInCldValueN2(obj[8].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInUnitsN(obj[9].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInUnitsN1(obj[10].toString());
				launchDpFinalBuildUpResponse.setLaunchSellInUnitsN2(obj[11].toString());
				listOfCompletedLaunch.add(launchDpFinalBuildUpResponse);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_GET_DP_FINAL_BUILDUP_DATA, ex.toString());
		}
	}

}