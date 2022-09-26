package com.hul.proco.controller.volumeupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.DataFromTable;
import com.hul.proco.controller.createpromo.RegularPromoCreateController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@Repository
public class DPVolumeUploadDAO implements DPVolumeUpload {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	DataFromTable dataFromTable;

	static Logger logger = Logger.getLogger(DPVolumeUploadDAO.class);

	private static String SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP = "INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2"
			+ "(CHANNEL_NAME,MOC,MOC_YEAR,MOC_NAME,PROMO_ID,PPM_ACCOUNT,PROMO_TIMEPERIOD,BASEPACK_CODE,BASEPACK_DESC,CHILD_BASEPACK_CODE,OFFER_DESC,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,QUANTITY,BRANCH,CLUSTER,TEMPLATE_TYPE,USER_ID,ERROR_MSG,BUDGET)"
			+ "VALUES" + "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20,?21)";

	@Override
	public List<ArrayList<String>> getDetailsofDP(ArrayList<String> header) {

		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();

		try {
			/*
			String query_list = "SELECT PM.CHANNEL_NAME, PM.MOC, PM.PROMO_ID, CM.SECONDARY_CHANNEL, CM.PPM_ACCOUNT,"
					+ "PM.PROMO_TIMEPERIOD, CM.AB_CREATION, PM.BASEPACK_CODE, PM.BASEPACK_DESC, PM.CHILD_BASEPACK_CODE, PM.OFFER_DESC,"
					+ "PM.OFFER_TYPE, PM.OFFER_MODALITY, PM.PRICE_OFF, '' AS QUANTITY, PM.BRANCH, PM.CLUSTER,PM.TEMPLATE_TYPE AS REMARK "
					+ "FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "INNER JOIN (SELECT MOC FROM TBL_VAT_MOC_MASTER WHERE STATUS = 'Y' LIMIT 1) MM ON PM.MOC >= concat(substr(MM.MOC, 3, 4), substr(MM.MOC, 1, 2)) "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.CUSTOMER_CHAIN_L2 "
					+ "WHERE PM.TEMPLATE_TYPE = 'Regular' AND PM.STATUS = 1"; */
			
			String query_list ="SELECT PM.CHANNEL_NAME,PM.MOC,PM.MOC_YEAR, PM.MOC_NAME, PM.PROMO_ID, CM.PPM_ACCOUNT, "
					+ "PM.PROMO_TIMEPERIOD, PM.BASEPACK_CODE, PM.BASEPACK_DESC, PM.CHILD_BASEPACK_CODE, PM.OFFER_DESC, "
					+ "PM.OFFER_TYPE, PM.OFFER_MODALITY, PM.PRICE_OFF,BUDGET, '' AS QUANTITY, PM.BRANCH, PM.CLUSTER,PM.TEMPLATE_TYPE AS REMARK "
					+ "FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ "INNER JOIN (SELECT CONCAT(SUBSTRING(MOC, 3, 4), SUBSTRING(MOC, 1, 2)) AS MOC FROM TBL_VAT_MOC_MASTER WHERE STATUS = 'Y' LIMIT 1) MM ON CONCAT(SUBSTRING(PM.MOC, 3, 4), SUBSTRING(PM.MOC, 1, 2)) >= MM.MOC "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ "WHERE PM.TEMPLATE_TYPE = 'R' AND PM.STATUS = 1";
			
			Query query = sessionFactory.getCurrentSession().createNativeQuery(query_list);
			downloadDataList.add(header);
			Iterator itr = query.list().iterator();

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

		} catch (Exception e) {
			logger.debug(e);
		}
		return downloadDataList;

	}

	@Override
	public String uploadVolumeData(CreateBeanRegular[] beanArray, String userId) {

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();

		String error_msg = "";
		int flag = 0;
		int gloabal = 0;
		Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROMOTION_MASTER_TEMP);
		Map<String, String> calculateBudget= new HashMap<String, String>();
		getMRP(calculateBudget);
		
		for (CreateBeanRegular bean : beanArray) {
			
			if (bean.getQuantity().isEmpty() || !bean.getQuantity().matches("\\d+")) {
				error_msg = "Quantity should be a numeric value";
				flag = 1;
			}
			
			if (!bean.getQuantity().isEmpty()) {
				Integer quantity = Integer.parseInt(bean.getQuantity());
				if (quantity < 9) {
					error_msg =error_msg+ "Quantity should be greater than or equal to 10";
					flag = 1;
				}
			}
			query.setString(1, bean.getChannel());
			query.setString(2, bean.getMoc());
			query.setString(3, bean.getYear());
			//query.setString(3, bean.getSecondary_channel());
			query.setString(4, bean.getMoc_name_dp());
			query.setString(5, bean.getPromo_id());
			query.setString(6, bean.getPpm_account());
			query.setString(7, bean.getPromo_time_period());
			query.setString(8, bean.getBasepack_code());
			query.setString(9, bean.getBaseback_desc());
			query.setString(10, bean.getC_pack_code());
			query.setString(11, bean.getOffer_desc());
			query.setString(12, bean.getOfr_type());
			query.setString(13, bean.getOffer_mod());
			query.setString(14, bean.getPrice_off());
			query.setString(15, bean.getQuantity());
			query.setString(16, bean.getBranch());
			query.setString(17, bean.getCluster());
			query.setString(18, bean.getRemark());
			query.setString(19, userId);
			query.setString(20, error_msg);
			if(flag == 0) {
			String budget=dataFromTable.calculateBudget(bean.getChannel(), bean.getQuantity(), bean.getPrice_off(), bean.getBudget(),
					bean.getBasepack_code(), calculateBudget);
			query.setString(21, budget);
			}else
			{
			query.setString(21, bean.getBudget());
			}
			query.executeUpdate();
			error_msg = "";

		}

		if (flag == 1) {
			gloabal = 1;
		}

		if (gloabal == 1) {
			flag = 0;
			gloabal = 0;
			return "EXCEL_NOT_UPLOADED";
		} else {
			flag = 0;
			gloabal = 0;
			updateQuantity(userId);
			return "EXCEL_UPLOADED";
		}

	}

	private void getMRP(Map<String, String> commanmap)
	{
		String sale_cat="SELECT BASEPACK,SALES_CATEGORY,BP_MRP FROM TBL_PROCO_PRODUCT_MASTER_V2 WHERE IS_ACTIVE=1";
		
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(sale_cat).list();
		
		for(Object[] obj:list)
		{
			commanmap.put(String.valueOf(obj[0]).toUpperCase()+"_MRP", String.valueOf(obj[2]).toUpperCase());
		}
	}
	
	private int updateQuantity(String userId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		String updateQuantity = "UPDATE TBL_PROCO_PROMOTION_MASTER_V2  T1 "
				+ "INNER JOIN TBL_PROCO_PROMOTION_MASTER_TEMP_V2 T2 ON T1.PROMO_ID=T2.PROMO_ID AND T1.MOC=T2.MOC AND T1.BASEPACK_CODE=T2.BASEPACK_CODE "
				+ " AND T1.OFFER_DESC=T2.OFFER_DESC AND T1.BRANCH=T2.BRANCH AND T1.CLUSTER=T2.CLUSTER AND T1.PPM_ACCOUNT=T2.PPM_ACCOUNT"
				+ " SET T1.QUANTITY=T2.QUANTITY,T1.BUDGET=T2.BUDGET, " + "STATUS='3', T1.USER_ID='" + userId + "',T2.UPDATE_STAMP='"
				+ dateFormat.format(date) + "'" + "WHERE T1.STATUS='1' AND T1.ACTIVE=1 AND T2.USER_ID='" + userId + "'";
		System.out.println("updateQuantity:"+updateQuantity);
		return sessionFactory.getCurrentSession().createNativeQuery(updateQuantity).executeUpdate();
	}

}
