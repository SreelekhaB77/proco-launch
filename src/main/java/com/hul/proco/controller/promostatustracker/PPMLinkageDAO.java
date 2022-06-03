package com.hul.proco.controller.promostatustracker;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

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

	private String INSERT_INTO_TEMP_TABLE = "INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP_V2 "
			+ " (VERSIONED_PROMOTION_ID, CHART_BY_TYPE, PROMOTION_CREATOR, PROMOTION_STATUS, PROMOTION_ID, PROMOTION_NAME, PROMOTION_SELL_IN_START_DATE, PROMOTION_SELL_IN_END_DATE, PROMOTION_MECHANICS, INVESTMENT_TYPE, CLUSTER_CODE, CLUSTER_NAME, BASEPACK, BASEPACK_NAME, CATEGORY, BRAND, SUB_BRAND, UOM, TAX, DISCOUNT, LIST_PRICE, PERCENT_PROMOTED_VOLUME, QUANTITY, BUDGET_HOLDER_NAME, FUND_TYPE, MOC, INVESTMENT_AMOUNT,USER_ID)"
			+ " VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27,?28)";

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public String addTotempTable(PPMLinkageBean[] beanArray, String userId) {

		Query query = sessionFactory.getCurrentSession().createNativeQuery(INSERT_INTO_TEMP_TABLE);

		for (PPMLinkageBean bean : beanArray) {
			query.setString(1, bean.getVersion_promo_id());
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
			query.setString(19, bean.getTax());
			query.setString(20, bean.getDiscount());
			query.setString(21, bean.getList_price());
			query.setString(22, bean.getPercent_promoted_volume());
			query.setString(23, bean.getQuantity());
			query.setString(24, bean.getBudget_holder_name());
			query.setString(25, bean.getFund_type());
			query.setString(26, bean.getMoc());
			query.setString(27, bean.getInvestment_amount());
			query.setString(28, userId);
			int count = checkExist(bean);
			System.out.println("count:"+count);
			if(count>0)
			{
				
			}else
			{
				query.executeUpdate();
				Session session =sessionFactory.getCurrentSession();
				StoredProcedureQuery proc=session.createStoredProcedureQuery("PPMinsertIntoMainTable");
				proc.execute();
				//sessionFactory.getCurrentSession().createSQLQuery("call insertIntoMainTable19()").addEntity(PPMLinkageBean.class);
			}
				
		}
		return "EXCEL_UPLOADED";
	}

	private int checkExist(PPMLinkageBean bean) {

	String checkdublicate = "SELECT COUNT(1) FROM TBL_PROCO_MEASURE_REPORT_TEMP_V2"
			+ " WHERE VERSIONED_PROMOTION_ID=?1  AND CHART_BY_TYPE=?2  AND PROMOTION_CREATOR=?3  AND PROMOTION_STATUS=?4  "
			+ "AND PROMOTION_ID=?5  AND PROMOTION_NAME=?6  AND PROMOTION_SELL_IN_START_DATE=?7  AND PROMOTION_SELL_IN_END_DATE=?8  "
			+ "AND PROMOTION_MECHANICS=?9  AND INVESTMENT_TYPE=?10  AND CLUSTER_CODE=?11  AND CLUSTER_NAME=?12  AND BASEPACK=?13"
			+ "  AND BASEPACK_NAME=?14  AND CATEGORY=?15  AND BRAND=?16  AND SUB_BRAND=?17  AND UOM=?18  AND TAX=?19  AND DISCOUNT=?20  "
			+ "AND LIST_PRICE=?21  AND PERCENT_PROMOTED_VOLUME=?22  AND QUANTITY=?23  AND BUDGET_HOLDER_NAME=?24  AND FUND_TYPE=?25  "
			+ "AND MOC=?26  AND INVESTMENT_AMOUNT=?27  ";
				
		
		
		Query query=sessionFactory.getCurrentSession().createNativeQuery(checkdublicate);
		
		query.setString(1, bean.getVersion_promo_id());
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
		query.setString(19, bean.getTax());
		query.setString(20, bean.getDiscount());
		query.setString(21, bean.getList_price());
		query.setString(22, bean.getPercent_promoted_volume());
		query.setString(23, bean.getQuantity());
		query.setString(24, bean.getBudget_holder_name());
		query.setString(25, bean.getFund_type());
		query.setString(26, bean.getMoc());
		query.setString(27, bean.getInvestment_amount());
		System.out.println("Query Exist:" + query.getQueryString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}

}
