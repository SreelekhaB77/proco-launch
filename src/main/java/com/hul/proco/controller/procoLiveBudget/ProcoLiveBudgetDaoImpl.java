package com.hul.proco.controller.procoLiveBudget;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.promostatustracker.PPMLinkageBean;

@Repository("ProcoLiveBudgetDao")
public class ProcoLiveBudgetDaoImpl implements ProcoLiveBudgetDao {
	
 static Logger logger=Logger.getLogger(ProcoLiveBudgetDaoImpl.class);
 @Autowired
	private SessionFactory sessionFactory;
 
 private String INSERT_INTO_TEMP_TABLE=" INSERT INTO TBL_PROCO_BUDGET_MASTER_TEMP"
		 			+ "(BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,"
		 			+ "UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,"
		 			+ "COMMITMENT_AMOUNT,REMAINING_AMOUNT,ACTUALS,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,"
		 			+ "POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE,REPORT_DOWNLOADED_BY,"
		 			+ "REPORT_DOWNLOADED_DATE,USERID ) VALUES (?0,?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14,"
		 			+ " ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24)";
 
 //Added by Kavitha D -Sprint 12 for budget file upload file implementation starts here
@SuppressWarnings("deprecation")
@Override
public String budgetHolderData(BudgetHolderBean[] beanArray,String userID) throws Exception {
	// TODO Auto-generated method stub
	
	Query queryToDelete = sessionFactory.getCurrentSession() .createNativeQuery("DELETE FROM TBL_PROCO_BUDGET_MASTER_TEMP where USERID=:userID");
	queryToDelete.setString("userID", userID);
	queryToDelete.executeUpdate();

	Query query = sessionFactory.getCurrentSession().createNativeQuery(INSERT_INTO_TEMP_TABLE);
	int flag=0;
	int global=0;
	try {
	for (BudgetHolderBean bean : beanArray) {
		query.setString(0, bean.getBudget_holder());
		query.setString(1, bean.getCategory());
		query.setString(2, bean.getProduct());
		query.setString(3, bean.getCustomer());
		query.setString(4, bean.getFund_type());
		query.setString(5, bean.getOriginal_amount());
		query.setString(6, bean.getAdjusted_amount());
		query.setString(7, bean.getRevised_amount());
		query.setString(8, bean.getUpdate_amount());
		query.setString(9, bean.getTransfer_in());
		query.setString(10, bean.getTransfer_out());
		query.setString(11, bean.getTransfer_pipeline());
		query.setString(12, bean.getTotal_amount());
		query.setString(13, bean.getPipeline_amount());
		query.setString(14, bean.getCommitment_amount());
		query.setString(15, bean.getRemaining_amount());
		query.setString(16, bean.getActuals());
		query.setString(17, bean.getAdjustment_against_actuals());
		query.setString(18, bean.getUsage());
		query.setString(19, bean.getPost_close_actual_amount());
		query.setString(20, bean.getPast_year_closed_promotions_amount());
		query.setString(21, bean.getTime_phase());
		query.setString(22, bean.getReport_downloaded_by());
		query.setString(23, bean.getReport_downlaoded_date());
		query.setString(24, userID);		
		query.executeUpdate();
		}	
	
	}
	catch (Exception e) {
	logger.debug("Exception:", e);
	flag = 1;
	throw new Exception();
	}if (flag == 1) {
	 global = 1;
	 }
 if (global == 1) {
	 flag = 0;
	 global = 0;
	 return "EXCEL_NOT_UPLOADED";
	 
 } else {
	 flag = 0;
	 global = 0;
 saveToMain(userID);
 return "EXCEL_UPLOADED";
}

}

private String saveToMain(String userID) {
	try {
		
		Query queryToDelete = sessionFactory.getCurrentSession() .createNativeQuery("DELETE FROM TBL_PROCO_BUDGET_MASTER WHERE USERID=:userID AND UPDATED_TIME_STAMP <current_date()");
		queryToDelete.setString("userID", userID);
		queryToDelete.executeUpdate();
		String insertMasterData= " INSERT INTO TBL_PROCO_BUDGET_MASTER (BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,COMMITMENT_AMOUNT,REMAINING_AMOUNT,ACTUALS ,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE ,REPORT_DOWNLOADED_BY ,REPORT_DOWNLOADED_DATE,UPDATED_TIME_STAMP,USERID)"
			+ " SELECT BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,COMMITMENT_AMOUNT,REMAINING_AMOUNT,ACTUALS ,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE ,REPORT_DOWNLOADED_BY ,REPORT_DOWNLOADED_DATE,UPDATED_TIME_STAMP,USERID "
			+ " FROM TBL_PROCO_BUDGET_MASTER_TEMP WHERE USERID=:userId ";
		Query query = sessionFactory.getCurrentSession().createNativeQuery(insertMasterData);
		query.setString("userId", userID);
		query.executeUpdate();
	} catch (Exception e) {
	logger.error("Exception: ", e);
	}
	return "0";
}
//Added by Kavitha D -Sprint 12 for budget file upload file implementation ends here


//Added by kavitha D Budget download file starts-sprint 12
@Override
public List<ArrayList<String>> procoLiveBudgetDownload(ArrayList<String> headerDetails) {
	// TODO Auto-generated method stub
	List<ArrayList<String>> downloadList = new ArrayList<ArrayList<String>>();
	try {
		
		String downloadMasterData= "SELECT BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,COMMITMENT_AMOUNT,REMAINING_AMOUNT,ACTUALS ,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE ,REPORT_DOWNLOADED_BY ,REPORT_DOWNLOADED_DATE,UPDATED_TIME_STAMP FROM TBL_PROCO_BUDGET_MASTER ";
		Query query = sessionFactory.getCurrentSession().createNativeQuery(downloadMasterData);
		Iterator itr = query.list().iterator();
		downloadList.add(headerDetails);
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			ArrayList<String> dataObj = new ArrayList<String>();
			for (Object ob : obj) {
				String value = "";
				value = (ob == null) ? "" : ob.toString();
				dataObj.add(value.replaceAll("\\^", ","));
			}
			obj = null;
			downloadList.add(dataObj);
		}
		return downloadList;	
	} catch (Exception e) {
		logger.debug("Exception: ", e);
	}
		return downloadList;
	}
//Added by kavitha D Budget download file ends-Sprint 12

@Override
public int getProcoBudgetRowCount(String searchParameter) {
	// TODO Auto-generated method stub
	List<BigInteger> list = null;
	try {

		String rowCount = "SELECT COUNT(1) FROM (SELECT BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,COMMITMENT_AMOUNT,"
				+ "	REMAINING_AMOUNT,ACTUALS ,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE ,REPORT_DOWNLOADED_BY ,REPORT_DOWNLOADED_DATE ,UPDATED_TIME_STAMP,ROW_NUMBER() OVER (ORDER BY UPDATED_TIME_STAMP) AS ROW_NEXT "
				+ " FROM TBL_PROCO_BUDGET_MASTER  ";
		
		if(searchParameter!=null && searchParameter.length()>0){
			rowCount +=" WHERE CONCAT(UCASE(BUDGET_HOLDER),UCASE(CUSTOMER),UCASE(CATEGORY),UCASE(PRODUCT),UCASE(FUND_TYPE),UCASE(TIME_PHASE), UCASE(REPORT_DOWNLOADED_BY)) LIKE UCASE('%"+searchParameter+"%')";
		}
		rowCount += " )AS BUDGET_TEMP ";

		Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
		list = query.list();
	} catch (Exception ex) {
		logger.debug("Exception: ", ex);
	}
	return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
}
	
//Added by Kavitha D for budget data pagination-SPRINT 12 starts
@Override
public List<BudgetHolderBean> getProcoBudgetTableList(int pageDisplayStart, int pageDisplayLength,
		String searchParameter) {
	List<BudgetHolderBean> promoList = new ArrayList<>();
	String promoQuery = "";
	try {
		
		promoQuery = " SELECT * FROM (SELECT BUDGET_HOLDER,CATEGORY,PRODUCT,CUSTOMER,FUND_TYPE,ORIGINAL_AMOUNT,ADJUSTED_AMOUNT,REVISED_AMOUNT,UPDATE_AMOUNT,TRANSFER_IN,TRANSFER_OUT,TRANSFER_PIPELINE,TOTAL_AMOUNT,PIPELINE_AMOUNT,COMMITMENT_AMOUNT,"
				+ " REMAINING_AMOUNT,ACTUALS ,ADJUSTMENT_AGAINST_ACTUALS,UTILIZATION,POST_CLOSE_ACTUAL_AMOUNT,PAST_YEAR_CLOSED_PROMOTIONS_AMOUNT,TIME_PHASE ,REPORT_DOWNLOADED_BY ,REPORT_DOWNLOADED_DATE ,UPDATED_TIME_STAMP,ROW_NUMBER() OVER (ORDER BY UPDATED_TIME_STAMP) AS ROW_NEXT"
				+ " FROM TBL_PROCO_BUDGET_MASTER ";
		
		if(searchParameter!=null && searchParameter.length()>0){
			promoQuery +=" WHERE CONCAT(UCASE(BUDGET_HOLDER),UCASE(CATEGORY),UCASE(PRODUCT),UCASE(FUND_TYPE),UCASE(TIME_PHASE), UCASE(REPORT_DOWNLOADED_BY)) LIKE UCASE('%"+searchParameter+"%')";
		}
		
		if (pageDisplayLength == 0) {
			promoQuery += " ORDER BY BUDGET_HOLDER,CATEGORY) AS BUDGET_TEMP";
		} else {
			promoQuery += " ORDER BY BUDGET_HOLDER,CATEGORY) AS BUDGET_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND " + pageDisplayLength + " ";
		}
		Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);

		List<Object[]> list = query.list();
		for (Object[] obj : list) {
			BudgetHolderBean budgetHolderBean = new BudgetHolderBean();
			budgetHolderBean.setBudget_holder(obj[0]== null ? "" :obj[0].toString());
			budgetHolderBean.setCategory(obj[1]== null ? "" :obj[1].toString());
			budgetHolderBean.setProduct(obj[2]== null ? "" :obj[2].toString());
			budgetHolderBean.setCustomer(obj[3]== null ? "" : obj[3].toString());
			budgetHolderBean.setFund_type(obj[4]== null ? "" : obj[4].toString());
			budgetHolderBean.setOriginal_amount(obj[5]== null ? "" : obj[5].toString());
			budgetHolderBean.setAdjusted_amount(obj[6]== null ? "" :obj[6].toString());
			budgetHolderBean.setRevised_amount(obj[7]== null ? "" :obj[7].toString());
			budgetHolderBean.setUpdate_amount(obj[8]== null ? "" :obj[8].toString());
			budgetHolderBean.setTransfer_in(obj[9]== null ? "" :obj[9].toString());
			budgetHolderBean.setTransfer_out(obj[10]== null ? "" :obj[10].toString());
			budgetHolderBean.setTransfer_pipeline(obj[11]== null ? "" :obj[11].toString());
			budgetHolderBean.setTotal_amount((obj[12] == null || obj[11].toString().equals("")) ? "" : obj[12].toString());
			budgetHolderBean.setPipeline_amount(obj[13]== null ? "" :obj[13].toString());
			budgetHolderBean.setCommitment_amount(obj[14]== null ? "" :obj[14].toString());
			budgetHolderBean.setRemaining_amount(obj[15]== null ? "" :obj[15].toString());
			budgetHolderBean.setActuals(obj[16]== null ? "" :obj[16].toString());
			budgetHolderBean.setAdjustment_against_actuals(obj[17]== null ? "" :obj[17].toString());
			budgetHolderBean.setUsage(obj[18]== null ? "" :obj[18].toString());
			budgetHolderBean.setPost_close_actual_amount(obj[19]== null ? "" :obj[19].toString());
			budgetHolderBean.setPast_year_closed_promotions_amount(obj[20]== null ? "" :obj[20].toString());
			budgetHolderBean.setTime_phase(obj[21]== null ? "" :obj[21].toString());
			budgetHolderBean.setReport_downloaded_by(obj[22]== null ? "" :obj[22].toString());
			budgetHolderBean.setReport_downlaoded_date(obj[23]== null ? "" :obj[23].toString());
			budgetHolderBean.setUploaded_timestamp(obj[24]== null ? "" :obj[24].toString());

			promoList.add(budgetHolderBean);
		}
	} catch (Exception ex) {
		logger.debug("Exception :", ex);
		return null;
	}

	return promoList;
}

//Added by Kavitha D for budget data pagination-SPRINT 12 ends



}

	
