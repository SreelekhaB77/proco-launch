package com.hul.launch.daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.dao.LaunchDaoCoe;
import com.hul.launch.exception.GlobleKamException;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@Repository
public class LaunchDaoCoeImpl implements LaunchDaoCoe {
	Logger logger = Logger.getLogger(LaunchBasePacksDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<TblLaunchMaster> getAllLaunchData(List<String> launchIds) {
		List<TblLaunchMaster> listOfCompletedLaunch = new ArrayList<>();
		try {

			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT ANNEXURE_DOCUMENT_NAME, ARTWORK_PACKSHOTS_DOC_NAME, MDG_DECK_DOCUMENT_NAME  FROM TBL_LAUNCH_MASTER"
							+ " tlc WHERE LAUNCH_ID IN (:launchIds)");
			query2.setParameterList("launchIds", launchIds);
			Iterator<Object> iterator = query2.list().iterator();

			while (iterator.hasNext()) {
				Object[] obj1 = (Object[]) iterator.next();
				TblLaunchMaster tblLaunchMaster = new TblLaunchMaster();
				tblLaunchMaster.setAnnexureDocName(obj1[0].toString());
				tblLaunchMaster.setArtworkPackshotsDocName(obj1[1].toString());
				tblLaunchMaster.setMdgDecName(obj1[2].toString());
				listOfCompletedLaunch.add(tblLaunchMaster);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			TblLaunchMaster launchDataResponse = new TblLaunchMaster();
			launchDataResponse.setError(ex.toString());
			listOfCompletedLaunch.add(launchDataResponse);
			return listOfCompletedLaunch;
		}
		return listOfCompletedLaunch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdCoe(List<String> listOfLaunchData,
			String userId) {
		List<LaunchMstnClearanceResponseCoe> listOfLaunchMstnClearanceResponseCoe = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession()
					.createNativeQuery("SELECT BASEPACK_CODE, BASEPACK_DESCRIPTION, DEPOT, CLUSTER, MSTN_CLEARED, "
							+ "FINAL_CLD_FOR_N, FINAL_CLD_FOR_N1, FINAL_CLD_FOR_N2, CURRENT_ESTIMATES, CLEARANCE_DATE, "
							+ "tlm.LAUNCH_NAME, tlm.LAUNCH_MOC, tlmc.ACCOUNT FROM TBL_LAUNCH_MSTN_CLEARANCE tlmc,TBL_LAUNCH_MASTER tlm "
							+ "WHERE tlmc.LAUNCH_ID = tlm.LAUNCH_ID AND tlm.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj1 = (Object[]) iterator.next();
				LaunchMstnClearanceResponseCoe launchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoe();
				launchMstnClearanceResponseCoe.setBpCode(obj1[0].toString());
				launchMstnClearanceResponseCoe.setBpDescription(obj1[1].toString());
				launchMstnClearanceResponseCoe.setDepot(obj1[2].toString());
				launchMstnClearanceResponseCoe.setCluster(obj1[3].toString());
				launchMstnClearanceResponseCoe.setMstnCleared(obj1[4].toString());
				launchMstnClearanceResponseCoe.setFinalCldN(obj1[5].toString());
				launchMstnClearanceResponseCoe.setFinalCldN1(obj1[6].toString());
				launchMstnClearanceResponseCoe.setFinalCldN2(obj1[7].toString());
				launchMstnClearanceResponseCoe.setCurrentEstimates(obj1[8].toString());
				launchMstnClearanceResponseCoe.setClearanceDate(obj1[9].toString());
				launchMstnClearanceResponseCoe.setLaunchName(obj1[10].toString());
				launchMstnClearanceResponseCoe.setLaunchMoc(obj1[11].toString());
				launchMstnClearanceResponseCoe.setAccount(obj1[12].toString());
				listOfLaunchMstnClearanceResponseCoe.add(launchMstnClearanceResponseCoe);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			LaunchMstnClearanceResponseCoe launchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoe();
			launchMstnClearanceResponseCoe.setError(ex.toString());
			listOfLaunchMstnClearanceResponseCoe.add(launchMstnClearanceResponseCoe);
		}
		return listOfLaunchMstnClearanceResponseCoe;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchMstnClearanceResponseCoe> getCoeMstnClearanceData(List<String> listOfLaunchData) {
		List<LaunchMstnClearanceResponseCoe> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,BASEPACK_CODE,BASEPACK_DESCRIPTION,tlm.LAUNCH_MOC,tlmc.CLUSTER, "
							+ "tlmc.DEPOT,tlmc.MSTN_CLEARED,tlmc.FINAL_CLD_FOR_N,tlmc.FINAL_CLD_FOR_N1,tlmc.FINAL_CLD_FOR_N2, "
							+ "tlmc.CURRENT_ESTIMATES,tlmc.CLEARANCE_DATE,tlmc.ACCOUNT  FROM TBL_LAUNCH_MSTN_CLEARANCE tlmc, TBL_LAUNCH_MASTER "
							+ "tlm WHERE tlmc.LAUNCH_ID = tlm.LAUNCH_ID AND tlmc.LAUNCH_ID IN (:listOfLaunchData)");
			query2.setParameterList("listOfLaunchData", listOfLaunchData);
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchMstnClearanceResponseCoe launchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoe();
				launchMstnClearanceResponseCoe.setLaunchName(obj[0].toString());
				launchMstnClearanceResponseCoe.setBpCode(obj[1].toString());
				launchMstnClearanceResponseCoe.setBpDescription(obj[2].toString());
				launchMstnClearanceResponseCoe.setLaunchMoc(obj[3].toString());
				launchMstnClearanceResponseCoe.setCluster(obj[4].toString());
				launchMstnClearanceResponseCoe.setDepot(obj[5].toString());
				launchMstnClearanceResponseCoe.setMstnCleared(obj[6].toString());
				launchMstnClearanceResponseCoe.setFinalCldN(obj[7].toString());
				launchMstnClearanceResponseCoe.setFinalCldN1(obj[8].toString());
				launchMstnClearanceResponseCoe.setFinalCldN2(obj[9].toString());
				launchMstnClearanceResponseCoe.setCurrentEstimates(obj[10].toString());
				launchMstnClearanceResponseCoe.setClearanceDate(obj[11].toString());
				launchMstnClearanceResponseCoe.setAccount(obj[12].toString());
				listOfCompletedLaunch.add(launchMstnClearanceResponseCoe);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_COE, ex.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaunchMstnClearanceResponseCoe> getMstnClearanceByLaunchIdTme(String userId) {
		List<LaunchMstnClearanceResponseCoe> listOfCompletedLaunch = new ArrayList<>();
		try {
			Query query2 = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT tlm.LAUNCH_NAME,BASEPACK_CODE,BASEPACK_DESCRIPTION,tlm.LAUNCH_MOC,tlmc.CLUSTER, "
							+ "tlmc.DEPOT,tlmc.MSTN_CLEARED,tlmc.FINAL_CLD_FOR_N,tlmc.FINAL_CLD_FOR_N1,tlmc.FINAL_CLD_FOR_N2, "
							+ "tlmc.CURRENT_ESTIMATES,tlmc.CLEARANCE_DATE FROM TBL_LAUNCH_MSTN_CLEARANCE tlmc, TBL_LAUNCH_MASTER "
							+ "tlm WHERE tlmc.LAUNCH_ID = tlm.LAUNCH_ID");
			Iterator<Object> iterator = query2.list().iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				LaunchMstnClearanceResponseCoe launchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoe();
				launchMstnClearanceResponseCoe.setLaunchName(obj[0].toString());
				launchMstnClearanceResponseCoe.setBpCode(obj[1].toString());
				launchMstnClearanceResponseCoe.setBpDescription(obj[2].toString());
				launchMstnClearanceResponseCoe.setLaunchMoc(obj[3].toString());
				launchMstnClearanceResponseCoe.setCluster(obj[4].toString());
				launchMstnClearanceResponseCoe.setDepot(obj[5].toString());
				launchMstnClearanceResponseCoe.setMstnCleared(obj[6].toString());
				launchMstnClearanceResponseCoe.setFinalCldN(obj[7].toString());
				launchMstnClearanceResponseCoe.setFinalCldN1(obj[8].toString());
				launchMstnClearanceResponseCoe.setFinalCldN2(obj[9].toString());
				launchMstnClearanceResponseCoe.setCurrentEstimates(obj[10].toString());
				launchMstnClearanceResponseCoe.setClearanceDate(obj[11].toString());
				listOfCompletedLaunch.add(launchMstnClearanceResponseCoe);
			}
			return listOfCompletedLaunch;
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			throw new GlobleKamException(ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_TME, ex.toString());
		}
	}
}