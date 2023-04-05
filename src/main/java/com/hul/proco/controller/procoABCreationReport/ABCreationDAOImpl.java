package com.hul.proco.controller.procoABCreationReport;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ABCreationDAOImpl implements ABCreationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	static Logger logger = Logger.getLogger(ABCreationBean.class);

	private static String SQL_QUERY_INSERT_INTO_PROCO_AB_CREATION_REPORT_MASTER_TEMP = "INSERT INTO TBL_PROCO_AB_CREATION_REPORT_MASTER_TEMP"
			+ "(ACTIVITY_CODE,STATUS_IN_CENTRAL_UNIFY,TME_SUBMIT_DATE,AUDITOR_SUBMIT_DATE,USER_ID)"
			+ "VALUES" + "(?1, ?2, ?3, ?4, ?5)";

	//Kajal G Added for SPRINT 12
	@Override
	public String uploadABCreationReport(ABCreationBean[] beanArray, String userId) throws Exception {
		
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_AB_CREATION_REPORT_MASTER_TEMP where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();

		int flag = 0;
		int global = 0;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROCO_AB_CREATION_REPORT_MASTER_TEMP);
			
			for (ABCreationBean bean : beanArray) {		
				
				query.setString(1, bean.getActivity_code());
				query.setString(2, bean.getStatus_in_central_unify());
				query.setString(3, bean.getTme_submit_date());
				query.setString(4, bean.getAuditor_submit_date());
				query.setString(5, userId);
				
				query.executeUpdate();

			}

		}catch (Exception e) {
			 logger.debug("Exception:", e);
			 flag = 1;
			 throw new Exception();
		}
		if (flag == 1) {
			global = 1;
		}

		if (global == 1) {
			flag = 0;
			global = 0;
			return "EXCEL_NOT_UPLOADED";
		} else {
			flag = 0;
			global = 0;
			saveToMain(userId);
			return "EXCEL_UPLOADED";
		}
		
	}
	
	//Kajal G Added for SPRINT 12
	public void saveToMain(String uid) throws Exception {
		try {
			String insertString= "INSERT INTO TBL_PROCO_AB_CREATION_REPORT_MASTER (ACTIVITY_CODE,STATUS_IN_CENTRAL_UNIFY,"
					+ "TME_SUBMIT_DATE,AUDITOR_SUBMIT_DATE,USER_ID) SELECT ACTIVITY_CODE,STATUS_IN_CENTRAL_UNIFY,TME_SUBMIT_DATE,"
					+ "AUDITOR_SUBMIT_DATE,USER_ID FROM TBL_PROCO_AB_CREATION_REPORT_MASTER_TEMP WHERE USER_ID='" + uid + "'";
			
			sessionFactory.getCurrentSession().createNativeQuery(insertString).executeUpdate();
		}catch (Exception e) {
			 logger.debug("Exception:", e);
			 throw new Exception();
		}
	}
	
}
