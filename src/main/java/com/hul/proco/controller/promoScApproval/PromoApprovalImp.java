package com.hul.proco.controller.promoScApproval;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.proco.controller.promocr.PromoCrBean;
import com.hul.proco.controller.promocr.PromoCrDAOImpl;

@Repository
public class PromoApprovalImp implements PromoApproval{
	
	private Logger logger = Logger.getLogger(PromoCrDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	public String insertToportalUsage(String userId, String roleID, String module) {
		String res = "";
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery("INSERT INTO TBL_VAT_AUDIT_TRAIL_DETAILS" + "(USER_ID," + "ROLE_ID,"
							+ "LOGGED_IN_TIME," + "MODULE)" + "VALUES(?1,?2,?3,?4)");
			query.setParameter(1, userId);
			query.setParameter(2, roleID);
			query.setParameter(3, dtf.format(now));
			query.setParameter(4, module);
			query.executeUpdate();

		} catch (Exception e) {
			logger.debug("Exception:", e);
			res = "500";
		}
		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getPromoListScRowCount(String userId, String roleId, String moc) {	
		List<BigInteger> list = null;
		try {

			String rowCount = " SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS  WHERE PM.STATUS IN('38','41') AND PM.MOC='"+moc+"'";
			
			Query query = sessionFactory.getCurrentSession().createNativeQuery(rowCount);
					
			list = query.list();
		} catch (Exception ex) {
			logger.debug("Exception: ", ex);
		}
		return (list != null && list.size() > 0) ? list.get(0).intValue() : 0;
	}
		

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PromoCrBean> getPromoScTableList(int pageDisplayStart, int pageDisplayLength, String userId,
			String roleId, String moc, String searchParameter) {
		List<PromoCrBean> promoList = new ArrayList<>();
		String promoQuery = "";
		try {
			promoQuery = " SELECT * FROM (SELECT PM.PROMO_ID AS UNIQUE_ID,PM.PROMO_ID AS ORIGINAL_ID,PM.START_DATE,PM.END_DATE,"
					+ " PM.MOC,PM.PPM_ACCOUNT,PM.BASEPACK_CODE AS BASEPACK,PM.OFFER_DESC,PM.OFFER_TYPE,PM.OFFER_MODALITY, "
					+ " PM.CLUSTER AS GEOGRAPHY,PM.QUANTITY,PM.PRICE_OFF AS OFFER_VALUE,PSM.STATUS, PM.CREATED_BY, SUBSTRING(PM.CREATED_DATE,1,10) AS CREATED_DATE, PM.TEMPLATE_TYPE AS REMARKS, "
					+ " 'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS,ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS  WHERE PM.STATUS IN('38','41') AND PM.MOC='"+moc+"' ";
							
			
				if(searchParameter!=null && searchParameter.length()>0){
					promoQuery +="AND UCASE(PM.PROMO_ID) LIKE UCASE('%"+searchParameter+"%')";
				}
			if (pageDisplayLength == 0) {
				promoQuery += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP";
			} else {
				promoQuery += " ORDER BY PM.PROMO_ID,PM.PPM_ACCOUNT) AS PROMO_TEMP WHERE ROW_NEXT BETWEEN " + pageDisplayStart + " AND " + pageDisplayLength + " ";
			}
			Query query = sessionFactory.getCurrentSession().createNativeQuery(promoQuery);

			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				PromoCrBean promoScBean = new PromoCrBean();
				
				promoScBean.setPromo_id(obj[0]== null ? "" :obj[0].toString());
				promoScBean.setOriginalId(obj[1]== null ? "" :obj[1].toString());
				promoScBean.setStartDate(obj[2]== null ? "" : obj[2].toString());
				promoScBean.setEndDate(obj[3]== null ? "" : obj[3].toString());
				promoScBean.setMoc(obj[4]== null ? "" : obj[4].toString());
				promoScBean.setCustomer_chain_l1(obj[5]== null ? "" :obj[5].toString());
				promoScBean.setBasepack(obj[6]== null ? "" :obj[6].toString());
				promoScBean.setOffer_desc(obj[7]== null ? "" :obj[7].toString());
				promoScBean.setOffer_type(obj[8]== null ? "" :obj[8].toString());
				promoScBean.setOffer_modality(obj[9]== null ? "" :obj[9].toString());
				promoScBean.setGeography(obj[10]== null ? "" :obj[10].toString());
				promoScBean.setQuantity((obj[11] == null || obj[11].toString().equals("")) ? "" : obj[11].toString());
				promoScBean.setOffer_value(obj[12]== null ? "" :obj[12].toString());
				promoScBean.setStatus(obj[13]== null ? "" :obj[13].toString());
				promoScBean.setUserId(obj[14]== null ? "" :obj[14].toString());
				promoScBean.setChangeDate(obj[15]== null ? "" :obj[15].toString());
				promoScBean.setRemark(obj[16]== null ? "" :obj[16].toString());
				promoScBean.setInvestmentType(obj[17]== null ? "" :obj[17].toString());
				promoScBean.setSolCode(obj[18]== null ? "" :obj[18].toString());
				promoScBean.setPromotionMechanics(obj[19]== null ? "" :obj[19].toString());
				promoScBean.setSolCodeStatus(obj[20]== null ? "" :obj[20].toString());
				promoList.add(promoScBean);
			}
		} catch (Exception ex) {
			logger.debug("Exception :", ex);
			return null;
		}

		return promoList;
	}

	@Override
	public String approvePromoSc(String promoId, String userId, String roleId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		String res = "";
		int status = 0;
		try {
			if (roleId.equalsIgnoreCase("SC")) {
			String[] split = promoId.split(",");
			for (int i = 0; i < split.length; i++) {
				String promo = split[i];				
				String approvalCrStatus=" UPDATE TBL_PROCO_PROMOTION_MASTER_V2  T1 SET T1.STATUS='40', T1.USER_ID='" + userId + "',T1.UPDATE_STAMP=' "+ dateFormat.format(date) + "'"
						+ " WHERE T1.STATUS IN('38','41') AND T1.ACTIVE=1 AND T1.PROMO_ID='" + promoId + "' ";
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(approvalCrStatus);
				query.executeUpdate();
				
			}
			res = "SUCCESS";
		}} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return res;
	}

	@Override
	public List<ArrayList<String>> getPromotionListingScDownload(ArrayList<String> headerList, String userId,
			String moc, String roleId) {
		{
			List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
			try {
				String downloadScQuery= " SELECT DISTINCT PROMO_ID,PPM_ACCOUNT,OFFER_DESC FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM WHERE PM.STATUS IN('38','41') AND PM.MOC= '"+moc+"'";
				Query query1  =sessionFactory.getCurrentSession().createNativeQuery(downloadScQuery);
			
				Iterator itr = query1.list().iterator();
				downloadDataList.add(headerList);
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
			} catch (Exception ex) {
				logger.debug("Exception :", ex);
				return null;
			
	}
		}
	}

	@Override
	public String uploadScApprovalData(PromoCrBean[] beanArray, String userId) throws Exception {
		String response = null;
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:user");
			queryToCheck.setString("user", userId);
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();

			if (recCount.intValue() > 0) {
		
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_PROMOTION_MASTER_TEMP_V2 where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();
			}
		Query query = sessionFactory.getCurrentSession().createNativeQuery(" INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 (PROMO_ID,PPM_ACCOUNT,OFFER_DESC,REMARK,STATUS,USER_ID) VALUES(?0,?1, ?2, ?3, ?4,?5)");
		
		for (int i = 0; i < beanArray.length; i++) {
			if (beanArray[i].getRemark().equalsIgnoreCase("ACCEPTED") || beanArray[i].getRemark().equalsIgnoreCase("APPROVED") || beanArray[i].getRemark().equalsIgnoreCase("REJECTED")) {
			query.setString(0, beanArray[i].getPromo_id());
			query.setString(1, beanArray[i].getCustomer_chain_l1());
			query.setString(2, beanArray[i].getOffer_desc());
			query.setString(3, beanArray[i].getRemark());
			
			if(beanArray[i].getRemark().equalsIgnoreCase("ACCEPTED") || beanArray[i].getRemark().equalsIgnoreCase("APPROVED") ) {
				query.setString(4,"40");
			}
			else if(beanArray[i].getRemark().equalsIgnoreCase("REJECTED")) {
				query.setString(4,"41");
			}
			
			query.setString(5,userId);

			int executeUpdate = query.executeUpdate();
			
	
			if (executeUpdate > 0) {
				response = validatePromo(beanArray[i], i);
				if (response.equals("EXCEL_NOT_UPLOADED")) {
					responseList.add(response);
				}
			}
		}
		}

		if (!responseList.contains("EXCEL_NOT_UPLOADED")) {
			if (!this.saveTotMainTable(userId)) {
				response = "ERROR";
			} else {
				response = "EXCEL_UPLOADED";
			}
		} else {
			response = "EXCEL_NOT_UPLOADED";
		}


	} catch (Exception e) {
		logger.debug("Exception:", e);
		throw new Exception();
	}
	if(responseList.contains("EXCEL_NOT_UPLOADED")) {
		response = "EXCEL_NOT_UPLOADED";
	}
	return response;
}
	
	private synchronized String validatePromo(PromoCrBean bean,  int row) throws Exception {
		String res = "EXCEL_UPLOADED";
		String errorMsg = "";
		try {
			Query queryToCheckvisiRefNo = sessionFactory.getCurrentSession().createNativeQuery(
					"SELECT COUNT(1) FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE PROMO_ID=:promoId");
			queryToCheckvisiRefNo.setString("promoId", bean.getPromo_id());
			Integer promoid = ((BigInteger) queryToCheckvisiRefNo.uniqueResult()).intValue();			

			if(promoid!=null && promoid == 0){
				res = "EXCEL_NOT_UPLOADED";
				errorMsg = errorMsg + "Entered promoid is not exist..";
				updateErrorMessageInTemp(errorMsg,  row);
			}
			
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new Exception();
		}
		return res;
	}
	private synchronized int updateErrorMessageInTemp(String errorMsg,  int row) {
		try {
			String qry = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 SET ERROR_MSG=:errorMsg WHERE ROW_ID=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setInteger("row", row);
			int executeUpdate = query.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return 0;
		}
	}
	

	public synchronized boolean saveTotMainTable(String userId) {
		try {
			insertIntoMaster(userId);
			return true;
		}catch (HibernateException e) {
			logger.error("Inside PROMOCRDAOImpl: saveToMainTable() : HibernateException : " + e.getMessage());
			return false;

		}
	}

	
	@Transactional(rollbackOn = {Exception.class})
	public void insertIntoMaster(String userId) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			String updateSql=" UPDATE TBL_PROCO_PROMOTION_MASTER_V2 A INNER JOIN TBL_PROCO_PROMOTION_MASTER_TEMP_V2 B ON A.PROMO_ID = B.PROMO_ID "
					+ " SET A.STATUS=B.STATUS,A.USER_ID='" + userId + "',A.UPDATE_STAMP=' "+ dateFormat.format(date) + "' "
					+ " WHERE B.USER_ID='" + userId + "' " ;
			Query queryUpdateExisting = sessionFactory.getCurrentSession().createNativeQuery(updateSql);
		queryUpdateExisting.executeUpdate();


	} catch (Exception e) {
		logger.error("Error in com.hul.proco.controller.promocrImpl.insertIntoTotMaster(String)", e);
	}

}
	//Added by Kavitha D for PromoApproval Upload ends-SPRINT 10

	


}
	
	
		
