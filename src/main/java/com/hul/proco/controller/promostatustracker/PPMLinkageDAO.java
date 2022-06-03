package com.hul.proco.controller.promostatustracker;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

	private String INSERT_INTO_TEMP_TABLE = "INSERT INTO TBL_PROCO_MEASURE_REPORT_TEMP_V2 "
			+ " (VERSIONED_PROMOTION_ID, CHART_BY_TYPE, PROMOTION_CREATOR, PROMOTION_STATUS, PROMOTION_ID, PROMOTION_NAME, PROMOTION_SELL_IN_START_DATE, PROMOTION_SELL_IN_END_DATE, PROMOTION_MECHANICS, INVESTMENT_TYPE, CLUSTER_CODE, CLUSTER_NAME, BASEPACK, BASEPACK_NAME, CATEGORY, BRAND, SUB_BRAND, UOM, TAX, DISCOUNT, LIST_PRICE, PERCENT_PROMOTED_VOLUME, QUANTITY, BUDGET_HOLDER_NAME, FUND_TYPE, MOC, INVESTMENT_AMOUNT,USER_ID)"
			+ " VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27,?28)";

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public String addTotempTable(PPMLinkageBean[] beanArray, String userId) {

		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_MEASURE_REPORT_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();

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
			query.setString(19, String.valueOf((double) Math.round(Double.parseDouble(bean.getTax()) * 100) / 100));
			query.setString(20, bean.getDiscount());
			query.setString(21, bean.getList_price());
			query.setString(22, bean.getPercent_promoted_volume());
			query.setString(23, String.valueOf((double) Math.round(Double.parseDouble(bean.getQuantity()) * 100) / 100));
			query.setString(24, bean.getBudget_holder_name());
			query.setString(25, bean.getFund_type());
			query.setString(26, bean.getMoc());
			query.setString(27,String.valueOf((double) Math.round(Double.parseDouble(bean.getInvestment_amount()) * 100) / 100));
			query.setString(28, userId);

			query.executeUpdate();

		}

		Session session = sessionFactory.getCurrentSession();
		StoredProcedureQuery proc = session.createStoredProcedureQuery("PMRInsertIntoMainTable");
		proc.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
		proc.setParameter(0, userId);
		proc.execute();
		return "EXCEL_UPLOADED";
	}

}
