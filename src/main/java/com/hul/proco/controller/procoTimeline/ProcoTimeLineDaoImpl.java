package com.hul.proco.controller.procoTimeline;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ProcoTimeLineDao")
public class ProcoTimeLineDaoImpl implements ProcoTimeLineDao {
	
	static Logger logger = Logger.getLogger(ProcoTimeLineDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public List<UserBean> getUsers() {
		List<UserBean> userList = new ArrayList<UserBean>();
		Query qryUserList = sessionFactory.getCurrentSession().createNativeQuery("SELECT USERID, CONCAT(A.FIRSTNAME,' ',A.LASTNAME) AS USER_NAME FROM TBL_VAT_USER_DETAILS AS A WHERE A.USER_ROLE_ID IN (1, 5) AND A.ACTIVE='Y' AND USER_FLAG = 1");
		List lstUsers = qryUserList.list();
		Iterator itrUsers = lstUsers.iterator();
		while(itrUsers.hasNext()) {
			UserBean ub = new UserBean();
			Object[] obj = (Object[])itrUsers.next();
			ub.setUserId((String)obj[0]);
			ub.setFirstName((String)obj[1]);
			userList.add(ub);
		}
		return userList;	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getTMELockDays() {
		List<String> lstTMELock = new ArrayList<String>();
		Query mocquery =  sessionFactory.getCurrentSession().createNativeQuery("SELECT CONFIG_VALUE FROM TBL_VAT_MASTER_CONFIG WHERE ID = 1"); 
		String tmeDays = (String)mocquery.uniqueResult();	
		String sTMELockDays[] = tmeDays.split(",");
		for(int i = 0; i < sTMELockDays.length; i++) {
			lstTMELock.add(sTMELockDays[i]);
		}
		
		return lstTMELock;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getKAMLockDays() {
		List<String> lstKAMLock = new ArrayList<String>();
		Query mocquery =  sessionFactory.getCurrentSession().createNativeQuery("SELECT CONFIG_VALUE FROM TBL_VAT_MASTER_CONFIG WHERE ID = 2"); 
		String kamDays = (String)mocquery.uniqueResult();
		String sKAMLockDays[] = kamDays.split(",");
		for(int i = 0; i < sKAMLockDays.length; i++) {
			lstKAMLock.add(sKAMLockDays[i]);
		}
		
		return lstKAMLock;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> getGlobalLocks() {
		Map<String, String> lockValues = new LinkedHashMap<String, String>();
		Query qryLock = sessionFactory.getCurrentSession().createNativeQuery("SELECT TME_LOCK, KAM_LOCK, TME_LOCK_DAYS, KAM_LOCK_DAYS FROM TBL_VAT_LOCK LIMIT 1");
		List lstLock = qryLock.list();
		Iterator itrLock = lstLock.iterator();
		while(itrLock.hasNext()) {
			Object[] obj = (Object[])itrLock.next();
			lockValues.put("TMELock", (String)obj[0]);
			lockValues.put("KAMLock", (String)obj[1]);
			lockValues.put("TMELockDay", ((Integer)obj[2]).toString());
			lockValues.put("KAMLockDay", ((Integer)obj[3]).toString());
		}
		return lockValues;
	}

	@Override
	public boolean saveProcoTimeLines(UserBean userBean) {
		boolean result = false;
		if (userBean.getUserId() != null) {
			result = saveProcoUserLock(userBean);
		} else {
			result = saveProcoTimelineDetails(userBean);
		}
		
		return result;
	
	}
	@SuppressWarnings("rawtypes")
	private boolean saveProcoTimelineDetails(UserBean userBean) {
		boolean result = false;
		Query query = null;
		String qryProcoTimeline = "UPDATE TBL_VAT_LOCK SET TME_LOCK = :tmelock, KAM_LOCK = :kamlock, TME_LOCK_DAYS = :tmelockdays, KAM_LOCK_DAYS = :kamlockdays";
		String qryProcoTmeUserLock = "UPDATE TBL_VAT_USER_LOCK UL INNER JOIN TBL_VAT_USER_DETAILS UD ON UD.USERID = UL.USER_ID AND UD.USER_ROLE_ID = 1 SET VAT_LOCK = :userlock, UPDATED_DATE = NOW()";
		String qryProcoKamUserLock = "UPDATE TBL_VAT_USER_LOCK UL INNER JOIN TBL_VAT_USER_DETAILS UD ON UD.USERID = UL.USER_ID AND UD.USER_ROLE_ID = 5 SET VAT_LOCK = :userlock, UPDATED_DATE = NOW()";
		try {
			query = sessionFactory.getCurrentSession().createNativeQuery(qryProcoTimeline);
			query.setParameter("tmelock", userBean.getTmeLock());
			query.setParameter("kamlock", userBean.getKamLock());
			query.setParameter("tmelockdays", userBean.getTmeLockDays());
			query.setParameter("kamlockdays", userBean.getKamLockDays());
			query.executeUpdate();
			
			query = sessionFactory.getCurrentSession().createNativeQuery(qryProcoTmeUserLock);
			query.setParameter("userlock", userBean.getTmeLock());
			query.executeUpdate();
			
			query = sessionFactory.getCurrentSession().createNativeQuery(qryProcoKamUserLock);
			query.setParameter("userlock", userBean.getKamLock());
			query.executeUpdate();
			
			result = true;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean saveProcoUserLock(UserBean userBean) {
		boolean result = false;
		Query query = null;
		String qryProcoUserLock = "UPDATE TBL_VAT_USER_LOCK SET VAT_LOCK = :userlock, UPDATED_DATE = NOW() WHERE USER_ID = :userId";
		String qryInsertUserLock = "INSERT INTO TBL_VAT_USER_LOCK (USER_ID, VAT_LOCK, UPDATED_DATE) VALUES (:userId, :userlock, NOW())";
		try {
			if (ValidateUserLock(userBean.getUserId()) > 0) {
				query = sessionFactory.getCurrentSession().createNativeQuery(qryProcoUserLock);
				query.setParameter("userlock", userBean.getUserLock());
				query.setParameter("userId", userBean.getUserId());
				query.executeUpdate();
			} else {
				query = sessionFactory.getCurrentSession().createNativeQuery(qryInsertUserLock);
				query.setParameter("userId", userBean.getUserId());
				query.setParameter("userlock", userBean.getUserLock());
				query.executeUpdate();
			}
			result = true;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private int ValidateUserLock(String userId) {
		Integer iValid = 0;
		Query query = null;
		String qryValidate = "SELECT COUNT(1) FROM TBL_VAT_USER_LOCK WHERE USER_ID = :userId";
		try {
			query = sessionFactory.getCurrentSession().createNativeQuery(qryValidate);
			query.setParameter("userId", userId);
			iValid = ((BigInteger)query.uniqueResult()).intValue();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return iValid;
	}


}
