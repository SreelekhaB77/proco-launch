package com.hul.proco.controller.promostatustracker;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository
public class PPMLinkageDAO implements PPMLinkageInterface {

	private String INSERT_INTO_TEMP_TABLE = 
			/*"INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP_V2 "
			+ " (VERSIONED_PROMOTION_ID, CHART_BY_TYPE, PROMOTION_CREATOR, PROMOTION_STATUS, PROMOTION_ID, PROMOTION_NAME, PROMOTION_SELL_IN_START_DATE, PROMOTION_SELL_IN_END_DATE, PROMOTION_MECHANICS, INVESTMENT_TYPE, CLUSTER_CODE, CLUSTER_NAME, BASEPACK, BASEPACK_NAME, CATEGORY, BRAND, SUB_BRAND, UOM, TAX, DISCOUNT, LIST_PRICE, PERCENT_PROMOTED_VOLUME, QUANTITY, BUDGET_HOLDER_NAME, FUND_TYPE, MOC, INVESTMENT_AMOUNT,USER_ID)"
			+ " VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27,?28)";*/
			
			//Kavitha D changes for SPRINT 9
			" INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP_V2 "
			+ " (PROMOTION_ID,PROMOTION_NAME,CREATED_BY,CREATED_ON,PROJECT_ID,PROJECT_NAME,BUNDLE_ID,BUNDLE_NAME,PROMOTION_QUALIFICATION,PROMOTION_OBJECTIVE,MARKETING_OBJECTIVE,PROMOTION_MECHANICS,PROMOTION_START_DATE,PROMOTION_END_DATE,PRE_DIP_START_DATE,POST_DIP_END_DATE,CUSTOMER,BUSINESS,DIVISION,PRODUCT,CATEGORY,BRAND,SUB_BRAND,PROMOTION_STATUS,INVESTMENT_TYPE,MOC,SUBMISSION_DATE,APPROVED_DATE,MODIFIED_DATE,PROMOTION_TYPE,DURATION,FREE_PRODUCT_NAME,PRICE_OFF,BASELINE_QUANTITY,BASELINE_GSV,BASELINE_TURNOVER,BASELINE_GROSS_PROFIT,PROMOTION_VOLUME_BEFORE,PROMOTION_VOLUME_DURING,PROMOTION_VOLUME_AFTER,PLANNED_GSV,PLANNED_TURNOVER,PLANNED_INVESTMENT_AMOUNT,PLANNED_UPLIFT,PLANNED_INCREMENTAL_GROSS_PROFIT,PLANNED_GROSS_PROFIT,PLANNED_INCREMENTAL_TURNOVER,PLANNED_CUSTOMER_ROI,PLANNED_COST_PRICE_BASED_ROI,PLANNED_PROMOTION_ROI,ACTUAL_QUANTITY,ACTUAL_GSV,ACTUAL_TURNOVER,ACTUAL_INVESTMENT_AMOUNT,ACTUAL_UPLIFT,ACTUAL_INCREMENTAL_GROSS_PROFIT,ACTUAL_GROSS_PROFIT,ACTUAL_INCREMENTAL_TURNOVER,ACTUAL_CUSTOMER_ROI,ACTUAL_COST_PRICE_BASED_ROI,ACTUAL_PROMOTION_ROI,UPLOAD_REFERENCE_NUMBER,IS_DUPLICATE,PROMO_ID,USER_ID) "
			+ " VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27, ?28, ?29, ?30, ?31, ?32, ?33, ?34, ?35, ?36, ?37, ?38, ?39, ?40, ?41, ?42, ?43, ?44, ?45, ?46, ?47, ?48, ?49, ?50, ?51, ?52, ?53, ?54, ?55, ?56, ?57, ?58, ?59, ?60, ?61, ?62, ?63, ?64, ?65)";

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public String addTotempTable(PPMLinkageBean[] beanArray, String userId) {

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_MEASURE_REPORT_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();

		Query query = sessionFactory.getCurrentSession().createNativeQuery(INSERT_INTO_TEMP_TABLE);
		try {
		for (PPMLinkageBean bean : beanArray) {
			/*query.setString(1, bean.getVersion_promo_id());
			query.setString(2, bean.getChart_by_type());
			query.setString(3, bean.getPromo_creator());
			query.setString(4, bean.getPromo_status());
			query.setString(5, bean.getPromo_id());
			query.setString(6, bean.getPromo_name());
			query.setString(7, bean.getSell_in_start_date());
			query.setString(8, bean.getSell_in_end_date());
			query.setString(9, bean.getPromo_mechanics());
			query.setString(10, bean.getInvestment_type());
			query.setString(11, bean.getCluster_code());
			query.setString(12, bean.getCluster_name());
			query.setString(13, bean.getBasepack_code());
			query.setString(14, bean.getBasepack_name());
			query.setString(15, bean.getCategory());
			query.setString(16, bean.getBrand());
			query.setString(17, bean.getSub_brand());
			query.setString(18, bean.getUom());
			query.setString(19, String.valueOf((double) Math.round(Double.parseDouble(bean.getTax()) * 100) / 100));
			query.setString(20, bean.getDiscount());
			query.setString(21, bean.getList_price());
			query.setString(22, bean.getPercent_promoted_volume());
			query.setString(23, String.valueOf((double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100));
			query.setString(24, bean.getBudget_holder_name());
			query.setString(25, bean.getFund_type());
			query.setString(26, bean.getMoc());
			query.setString(27,String.valueOf((double) Math.round(Double.parseDouble(bean.getInvestment_amount()) * 100) / 100));
			query.setString(28, userId);*/
			
			
			query.setString(1, bean.getPROMOTION_ID() !=null? bean.getPROMOTION_ID(): "");
			query.setString(2, bean.getPROMOTION_NAME()!=null? bean.getPROMOTION_NAME(): "");
			query.setString(3, bean.getCREATED_BY()!=null? bean.getCREATED_BY(): "");
			query.setString(4, bean.getCREATED_ON()!=null? bean.getCREATED_ON(): "");
			query.setString(5, bean.getPROJECT_ID()!=null? bean.getPROJECT_ID(): "");
			query.setString(6, bean.getPROJECT_NAME()!=null? bean.getPROJECT_NAME(): "");
			query.setString(7, bean.getBUNDLE_ID()!=null? bean.getBUNDLE_ID(): "");
			query.setString(8, bean.getBUNDLE_NAME()!=null? bean.getBUNDLE_NAME(): "");
			query.setString(9, bean.getPROMOTION_QUALIFICATION()!=null? bean.getPROMOTION_QUALIFICATION(): "");
			query.setString(10, bean.getPROMOTION_OBJECTIVE()!=null? bean.getPROMOTION_OBJECTIVE(): "");
			query.setString(11, bean.getMARKETING_OBJECTIVE()!=null? bean.getMARKETING_OBJECTIVE(): "");
			query.setString(12, bean.getPROMOTION_MECHANICS()!=null? bean.getPROMOTION_MECHANICS(): "");
			query.setString(13, bean.getPROMOTION_START_DATE()!=null? bean.getPROMOTION_START_DATE(): "");
			query.setString(14, bean.getPROMOTION_END_DATE()!=null? bean.getPROMOTION_END_DATE(): "");
			query.setString(15, bean.getPRE_DIP_START_DATE()!=null? bean.getPRE_DIP_START_DATE(): "");
			query.setString(16, bean.getPOST_DIP_END_DATE()!=null? bean.getPOST_DIP_END_DATE(): "");
			query.setString(17, bean.getCUSTOMER()!=null? bean.getCUSTOMER(): "");
			query.setString(18, bean.getBUSINESS()!=null? bean.getBUSINESS(): "");
			query.setString(19, bean.getDIVISION()!=null? bean.getDIVISION(): "");
			query.setString(20, bean.getPRODUCT()!=null? bean.getPRODUCT(): "");
			query.setString(21, bean.getCATEGORY()!=null? bean.getCATEGORY(): "");
			query.setString(22, bean.getBrand()!=null? bean.getBrand(): "");
			query.setString(23, bean.getSub_brand()!=null? bean.getSub_brand(): "");
			query.setString(24, bean.getPROMOTION_STATUS()!=null? bean.getPROMOTION_STATUS(): "");
			query.setString(25, bean.getINVESTMENT_TYPE()!=null? bean.getINVESTMENT_TYPE(): "");
			query.setString(26, bean.getMOC());
			query.setString(27,bean.getSUBMISSION_DATE()!=null?bean.getSUBMISSION_DATE(): "");
			query.setString(28, bean.getAPPROVED_DATE()!=null? bean.getAPPROVED_DATE(): "");
			query.setString(29, bean.getMODIFIED_DATE()!=null? bean.getMODIFIED_DATE(): "");
			query.setString(30, bean.getPROMOTION_TYPE()!=null? bean.getPROMOTION_TYPE(): "");
			query.setString(31, bean.getDURATION()!=null? bean.getDURATION(): "");
			query.setString(32, bean.getFREE_PRODUCT_NAME()!=null? bean.getFREE_PRODUCT_NAME(): "");
			query.setString(33, bean.getPRICE_OFF()!=null? bean.getPRICE_OFF(): "");
			query.setString(34, bean.getBASELINE_QUANTITY()!=null? bean.getBASELINE_QUANTITY(): "");
			query.setString(35, bean.getBASELINE_GSV()!=null? bean.getBASELINE_GSV(): "");
			query.setString(36, bean.getBASELINE_TURNOVER()!=null? bean.getBASELINE_TURNOVER(): "");
			query.setString(37, bean.getBASELINE_GROSS_PROFIT()!=null? bean.getBASELINE_GROSS_PROFIT(): "");
			query.setString(38, String.valueOf((double) Math.round(Double.parseDouble(bean.getPROMOTION_VOLUME_BEFORE()!=null? bean.getPROMOTION_VOLUME_BEFORE(): "") * 100) / 100));
			query.setString(39, String.valueOf((double) Math.round(Double.parseDouble(bean.getPROMOTION_VOLUME_DURING()!=null? bean.getPROMOTION_VOLUME_DURING(): "") * 100) / 100));
			query.setString(40, String.valueOf((double) Math.round(Double.parseDouble(bean.getPROMOTION_VOLUME_AFTER()!=null? bean.getPROMOTION_VOLUME_AFTER(): "") * 100) / 100));
			query.setString(41, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_GSV()!=null? bean.getPLANNED_GSV(): "") * 100) / 100));
			query.setString(42, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_TURNOVER()!=null? bean.getPLANNED_TURNOVER(): "") * 100) / 100));
			query.setString(43, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_INVESTMENT_AMOUNT()!=null? bean.getPLANNED_INVESTMENT_AMOUNT(): "") * 100) / 100));
			query.setString(44, bean.getPLANNED_UPLIFT()!=null? bean.getPLANNED_UPLIFT(): "");
			query.setString(45, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_INCREMENTAL_GROSS_PROFIT()!=null? bean.getPLANNED_INCREMENTAL_GROSS_PROFIT(): "") * 100) / 100));
			query.setString(46, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_GROSS_PROFIT()!=null? bean.getPLANNED_GROSS_PROFIT(): "") * 100) / 100));
			query.setString(47, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_INCREMENTAL_TURNOVER()!=null? bean.getPLANNED_INCREMENTAL_TURNOVER(): "") * 100) / 100));
			query.setString(48, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_CUSTOMER_ROI()!=null? bean.getPLANNED_CUSTOMER_ROI(): "") * 100) / 100));
			query.setString(49, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_COST_PRICE_BASED_ROI()!=null? bean.getPLANNED_COST_PRICE_BASED_ROI(): "") * 100) / 100));
			query.setString(50, String.valueOf((double) Math.round(Double.parseDouble(bean.getPLANNED_PROMOTION_ROI()!=null? bean.getPLANNED_PROMOTION_ROI(): "") * 100) / 100));
			query.setString(51, bean.getACTUAL_QUANTITY()!=null? bean.getACTUAL_QUANTITY(): "");
			query.setString(52, String.valueOf((double) Math.round(Double.parseDouble(bean.getACTUAL_GSV()!=null? bean.getACTUAL_GSV(): "") * 100) / 100));
			query.setString(53, bean.getACTUAL_TURNOVER()!=null? bean.getACTUAL_TURNOVER(): "");
			query.setString(54, String.valueOf((double) Math.round(Double.parseDouble(bean.getACTUAL_INVESTMENT_AMOUNT()!=null? bean.getACTUAL_INVESTMENT_AMOUNT(): "") * 100) / 100));
			query.setString(55,bean.getACTUAL_UPLIFT()!=null? bean.getACTUAL_UPLIFT(): "");
			query.setString(56, bean.getACTUAL_INCREMENTAL_GROSS_PROFIT()!=null? bean.getACTUAL_INCREMENTAL_GROSS_PROFIT(): "");
			query.setString(57, bean.getACTUAL_GROSS_PROFIT()!=null? bean.getACTUAL_GROSS_PROFIT(): "");
			query.setString(58, bean.getACTUAL_INCREMENTAL_TURNOVER()!=null? bean.getACTUAL_INCREMENTAL_TURNOVER(): "");
			query.setString(59, bean.getACTUAL_CUSTOMER_ROI()!=null? bean.getACTUAL_CUSTOMER_ROI(): "");
			query.setString(60, bean.getACTUAL_COST_PRICE_BASED_ROI()!=null? bean.getACTUAL_COST_PRICE_BASED_ROI(): "");
			query.setString(61, bean.getACTUAL_PROMOTION_ROI()!=null? bean.getACTUAL_PROMOTION_ROI(): "");
			query.setString(62, bean.getUPLOAD_REFERENCE_NUMBER()!=null? bean.getUPLOAD_REFERENCE_NUMBER(): "");
			query.setString(63, bean.getIS_DUPLICATE()!=null? bean.getIS_DUPLICATE(): "");
			query.setString(64, bean.getPROMO_ID());
			query.setString(65, userId);
			
			query.executeUpdate();

		}}
		catch(Exception e) {
			System.out.print(e);
		}
		
		

		Session session = sessionFactory.getCurrentSession();
		StoredProcedureQuery proc = session.createStoredProcedureQuery("PMRInsertIntoMainTable");
		proc.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
		proc.setParameter(0, userId);
		proc.execute();
		return "EXCEL_UPLOADED";
	}
	
	
	public List<String> getBasedonMOC() {
		String q="SELECT DISTINCT MOC FROM TBL_PROCO_MEASURE_MASTER_V2 ORDER BY MOC";
		
		return sessionFactory.getCurrentSession().createNativeQuery(q).list();
	}

	public List<ArrayList<String>> getDownloadData(List<String> headers,String moc) {
		String queryString=/*"SELECT VERSIONED_PROMOTION_ID, CHART_BY_TYPE, PROMOTION_CREATOR, PROMOTION_STATUS, PROMOTION_ID, PROMOTION_NAME, PROMOTION_SELL_IN_START_DATE, PROMOTION_SELL_IN_END_DATE, PROMOTION_MECHANICS, INVESTMENT_TYPE, CLUSTER_CODE, CLUSTER_NAME, BASEPACK, BASEPACK_NAME, CATEGORY, BRAND, SUB_BRAND, UOM, TAX, DISCOUNT, LIST_PRICE, PERCENT_PROMOTED_VOLUME, QUANTITY, BUDGET_HOLDER_NAME, FUND_TYPE, MOC, INVESTMENT_AMOUNT FROM TBL_PROCO_MEASURE_MASTER_V2 "
				+ " WHERE PROMOTION_ID IS NOT NULL AND MOC = '"+moc+"'";*/
		
		"SELECT PROMOTION_ID,PROMOTION_NAME,CREATED_BY,CREATED_ON,PROJECT_ID,PROJECT_NAME,BUNDLE_ID,BUNDLE_NAME,PROMOTION_QUALIFICATION,PROMOTION_OBJECTIVE,MARKETING_OBJECTIVE,PROMOTION_MECHANICS,PROMOTION_START_DATE,PROMOTION_END_DATE,PRE_DIP_START_DATE,POST_DIP_END_DATE,CUSTOMER,BUSINESS,DIVISION,PRODUCT,CATEGORY,BRAND,SUB_BRAND,PROMOTION_STATUS,INVESTMENT_TYPE,MOC,SUBMISSION_DATE,"
		+ "APPROVED_DATE,MODIFIED_DATE,PROMOTION_TYPE,DURATION,FREE_PRODUCT_NAME,PRICE_OFF,BASELINE_QUANTITY,BASELINE_GSV,BASELINE_TURNOVER,BASELINE_GROSS_PROFIT,PROMOTION_VOLUME_BEFORE,PROMOTION_VOLUME_DURING,PROMOTION_VOLUME_AFTER,PLANNED_GSV,PLANNED_TURNOVER,PLANNED_INVESTMENT_AMOUNT,PLANNED_UPLIFT,PLANNED_INCREMENTAL_GROSS_PROFIT,PLANNED_GROSS_PROFIT,PLANNED_INCREMENTAL_TURNOVER,"
		+ "PLANNED_CUSTOMER_ROI,PLANNED_COST_PRICE_BASED_ROI,PLANNED_PROMOTION_ROI,ACTUAL_QUANTITY,ACTUAL_GSV,ACTUAL_TURNOVER,ACTUAL_INVESTMENT_AMOUNT,ACTUAL_UPLIFT,ACTUAL_INCREMENTAL_GROSS_PROFIT,ACTUAL_GROSS_PROFIT,ACTUAL_INCREMENTAL_TURNOVER,ACTUAL_CUSTOMER_ROI,ACTUAL_COST_PRICE_BASED_ROI,ACTUAL_PROMOTION_ROI,UPLOAD_REFERENCE_NUMBER,IS_DUPLICATE FROM TBL_PROCO_MEASURE_MASTER_V2 WHERE PROMOTION_ID IS NOT NULL AND MOC = '"+moc+"'";
		
		Query query = sessionFactory.getCurrentSession().createNativeQuery(queryString);
		Iterator itr = query.list().iterator();
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		downloadDataList.add((ArrayList<String>) headers);
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
	}

}
