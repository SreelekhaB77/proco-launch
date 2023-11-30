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

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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

	private String error_msg = "";
	private int flag = 0;
	private int globle_flag = 0;
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
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS  WHERE PM.STATUS IN('38','41','40','44','45') AND PM.MOC='"+moc+"'";
			
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
					+ " 'NA' AS INVESTMENT_TYPE, 'NA' AS SOL_CODE, 'NA' AS PROMOTION_MECHANICS, 'NA' AS SOL_CODE_STATUS,PM.SALES_CATEGORY, PM.TEMPLATE_TYPE, PM.CR_SOL_TYPE,"
					+ " (CASE WHEN PM.STATUS=40 THEN 'ACCEPTED' WHEN PM.STATUS=41 THEN 'REJECTED' WHEN PM.STATUS=44 THEN 'PENDING' END) AS REMARK,"
					+ " ROW_NUMBER() OVER (ORDER BY PM.UPDATE_STAMP DESC) AS ROW_NEXT "
					+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 PM "
					+ " INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 CM ON CM.PPM_ACCOUNT = PM.PPM_ACCOUNT AND CM.CHANNEL_NAME = PM.CHANNEL_NAME "
					+ " INNER JOIN TBL_PROCO_STATUS_MASTER PSM ON PSM.STATUS_ID = PM.STATUS  WHERE PM.STATUS IN('38','41','40','44','45') AND PM.MOC='"+moc+"' ";
							
			
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
				promoScBean.setRemark(obj[16]== null ? "" :obj[16].toString());// commented by Kajal G
				promoScBean.setInvestmentType(obj[17]== null ? "" :obj[17].toString());
				promoScBean.setSolCode(obj[18]== null ? "" :obj[18].toString());
				promoScBean.setPromotionMechanics(obj[19]== null ? "" :obj[19].toString());
				promoScBean.setSolCodeStatus(obj[20]== null ? "" :obj[20].toString());
				promoScBean.setCategory(obj[21]== null ? "" :obj[21].toString());
				promoScBean.setTemplatetype(obj[22]== null ? "" :obj[22].toString());
				promoScBean.setSoltype(obj[23]== null ? "" :obj[23].toString());
				promoScBean.setRemark(obj[24]== null ? "" :obj[24].toString());
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
				String approvalCrStatus=" UPDATE TBL_PROCO_PROMOTION_MASTER_V2  T1 SET T1.STATUS='40', T1.USER_ID='" + userId + "',T1.UPDATE_STAMP='"+ dateFormat.format(date) +"'"
						+ " WHERE T1.STATUS IN('38','41','44','45') AND T1.ACTIVE=1 AND T1.PROMO_ID='" + promoId + "' ";
				
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
				String downloadScQuery= /*" SELECT DISTINCT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_MODALITY,"
						+ "PRICE_OFF,DP_QUANTITY,QUANTITY,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE FROM TBL_PROCO_PROMOTION_MASTER_V2 AS PM WHERE PM.STATUS IN('38','41','44') AND PM.MOC= '"+moc+"'";*/
				
				//SC APPROVAL QTY & BUDGET ADDED BY KAVITHA D-SPRINT 18
				//Kajal G changes start here for SPRINT-18		
				" CREATE TEMPORARY TABLE TT_SC_PROMO_DOWNLOAD SELECT DISTINCT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,"
				+ "OFFER_MODALITY,PRICE_OFF,REGULAR_PROMO_QUANTITY,SC_APPROVED_QTY,QUANTITY,BUDGET AS FIXED_BUDGET,REGULAR_PROMO_BUDGET,SC_APPROVED_BDG,CLUSTER,"
//				+ "TEMPLATE_TYPE,CR_SOL_TYPE,"
				+ " CASE WHEN TEMPLATE_TYPE = 'R' THEN 'Regular' WHEN TEMPLATE_TYPE = 'NE' THEN 'New Entry' ELSE TEMPLATE_TYPE END AS PROMO_ENTRY_TYPE," // Added by KAJAL G in SPRINT-18
				+ " CASE WHEN TEMPLATE_TYPE = 'R' THEN 'Regular' WHEN TEMPLATE_TYPE = 'NE' THEN 'New Entry' ELSE CR_SOL_TYPE END AS CR_SOL_TYPE," // Added by KAJAL G in SPRINT-18
				+ " CASE WHEN TEMPLATE_TYPE = 'R' THEN 'REG' WHEN TEMPLATE_TYPE = 'NE' THEN 'NE' ELSE '     ' END AS CR_SOL_TYPE_SHORTKEY,"  // Added by KAJAL G in SPRINT-18
				+ "INCREMENTAL_BUDGET,STOCK_AVAILABILITY,'YES' AS NCMM_REMARKS "
				+ "FROM TBL_PROCO_PROMOTION_MASTER_V2 WHERE STATUS IN('38','41','44','45') AND MOC= '"+moc+"'"; //Added by Kavitha D-SPRINT 16 changes
				
				Query query = sessionFactory.getCurrentSession().createNativeQuery(downloadScQuery);
				query.executeUpdate();

				String updateTempTable = "UPDATE TT_SC_PROMO_DOWNLOAD LR INNER JOIN TBL_PROCO_SOL_TYPE CR ON CR.SOL_REMARK= LR.CR_SOL_TYPE "
						+ "SET LR.CR_SOL_TYPE_SHORTKEY=CR.SOL_TYPE";
				
				Query query1 = sessionFactory.getCurrentSession().createNativeQuery(updateTempTable);
				query1.executeUpdate();
				
				String downloadNcmmEntry = "SELECT DISTINCT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,"
						+ " REGULAR_PROMO_QUANTITY,SC_APPROVED_QTY,QUANTITY,FIXED_BUDGET,REGULAR_PROMO_BUDGET,SC_APPROVED_BDG,CLUSTER,PROMO_ENTRY_TYPE,CR_SOL_TYPE,CR_SOL_TYPE_SHORTKEY,"
						+ " INCREMENTAL_BUDGET,STOCK_AVAILABILITY,NCMM_REMARKS FROM TT_SC_PROMO_DOWNLOAD";
				
				Query query2  = sessionFactory.getCurrentSession().createNativeQuery(downloadNcmmEntry);
			
				Iterator itr = query2.list().iterator();
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
				Query dropTable = sessionFactory.getCurrentSession().createNativeQuery("DROP TEMPORARY TABLE TT_SC_PROMO_DOWNLOAD");
				dropTable.executeUpdate();
				//Kajal G changes END here for SPRINT-18	
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
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();*/
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()); //Kavitha D changes-SPRINT 22

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
		Query query = sessionFactory.getCurrentSession().createNativeQuery(
				/*" INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 (CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_MODALITY,"
					+ "PRICE_OFF,DP_QUANTITY,QUANTITY,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,REMARK,STATUS,USER_ID) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16)"*/
				//Changed by Kavitha D-SPRINT 16 
				/*"INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 (CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,REGULAR_PROMO_QUANTITY,QUANTITY,BUDGET,REGULAR_PROMO_BUDGET,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,INCREMENTAL_BUDGET,STOCK_AVAILABILITY,"
				+ "SIGNED_OFF_WITH_CM,REMARK,STATUS,USER_ID) VALUES(?0,?1, ?2, ?3, ?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22)");*/
		
				//Added by Kavitha D-SPRINT 18 
				"INSERT INTO TBL_PROCO_PROMOTION_MASTER_TEMP_V2 (CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,REGULAR_PROMO_QUANTITY,SC_APPROVED_QTY,QUANTITY,BUDGET,REGULAR_PROMO_BUDGET,SC_APPROVED_BDG,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,INCREMENTAL_BUDGET,STOCK_AVAILABILITY,"
				+ "SIGNED_OFF_WITH_CM,REMARK,STATUS,USER_ID,SC_DOA,ERROR_MSG) VALUES(?0,?1, ?2, ?3, ?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26)");

		for (int i = 0; i < beanArray.length; i++) {
			if (beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("ACCEPTED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("APPROVED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("REJECTED") ||beanArray[i].getSignedOffWithAvailability().isEmpty() ||beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("PARTIAL APPROVED")) {
		
			query.setString(0, beanArray[i].getChannel()); //Changed by Kajal G-SPRINT 15 changes
			query.setString(1, beanArray[i].getMoc());
			query.setString(2, beanArray[i].getCategory());
			query.setString(3, beanArray[i].getPpmaccount());
			query.setString(4, beanArray[i].getPromo_id());
			query.setString(5, beanArray[i].getOffer_desc());
			query.setString(6, beanArray[i].getBasepack());
			query.setString(7, beanArray[i].getOffer_type());
			query.setString(8, beanArray[i].getOffer_modality());
			query.setString(9, beanArray[i].getPriceoff());
			query.setString(10, beanArray[i].getRegularPromoQuantity());
			if(beanArray[i].getTemplatetype().equalsIgnoreCase("CR") && (beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("APPROVED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("PARTIAL APPROVED"))){
			if(!beanArray[i].getScApprovedQty().isEmpty()) {
				boolean numeric = true;
		        try {
		            int num = Integer.parseInt(beanArray[i].getScApprovedQty());
		        } catch (NumberFormatException e) {
		            numeric = false;
		        }
		        if(!numeric){
                    if (flag == 1)
                        error_msg = error_msg + ",Invalid Sc Approved Quantity/ Sc Approved Qunatity Value should not be in decimal";
                    else
                        error_msg = error_msg + "Invalid Sc Approved Quantity/ Sc Approved Qunatity Value should not be in decimal";
                    flag=1;
                }else {
                	if(Integer.parseInt(beanArray[i].getScApprovedQty()) <= 0) {
						if (flag == 1)
							error_msg = error_msg + ",Sc Approved Quantity should not be 0 or negative value";
						else
							error_msg = error_msg + "Sc Approved Quantity should not be 0 or negative value";
						flag = 1;
					}    
                	
                	if(Integer.parseInt(beanArray[i].getScApprovedQty())>Integer.parseInt(beanArray[i].getQuantity())) {
                		if (flag == 1)
							error_msg = error_msg + ",Sc Approved Quantity should not exceed tme uploaded value";
						else
							error_msg = error_msg + "Sc Approved Quantity should not exceed tme uploaded value";
						flag = 1;
                	}
                	
                }
		        
		        }else {
				if (flag == 1)
					error_msg = error_msg + ",Mandatory input for Sc Approved Quantity";
				else
					error_msg = error_msg + "Mandatory input for Sc Approved Quantity";
				flag = 1;
			}	
			query.setString(11, beanArray[i].getScApprovedQty());
			}
			else {
				query.setString(11, "");
			}
			query.setString(12, beanArray[i].getQuantity());
			query.setString(13, beanArray[i].getBudget());
			query.setString(14, beanArray[i].getRegularPromoBudget());
			if(beanArray[i].getTemplatetype().equalsIgnoreCase("CR") && (beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("APPROVED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("PARTIAL APPROVED"))) {
			if(!beanArray[i].getScApprovedBdg().isEmpty()) {
				boolean numeric = true;
		        try {
		            int num = Integer.parseInt(beanArray[i].getScApprovedBdg());
		        } catch (NumberFormatException e) {
		            numeric = false;
		        }
		        if(!numeric){
                    if (flag == 1)
                        error_msg = error_msg + ",Invalid Sc Approved Budget/Sc Approved Budget Value should not be in decimal";
                    else
                        error_msg = error_msg + "Invalid Sc Approved Budget/ Sc Approved Budget Value should not be in decimal";
                    flag=1;
                }else {
                	if(Integer.parseInt(beanArray[i].getScApprovedBdg()) <= 0) {
						if (flag == 1)
							error_msg = error_msg + ",Sc Approved Budget should not be 0 or negative value";
						else
							error_msg = error_msg + " Sc Approved Budget should not be 0 or negative value";
						flag = 1;
					} 
                	
                	if(Double.parseDouble(beanArray[i].getScApprovedBdg())>Double.parseDouble(beanArray[i].getBudget())) {
                		if (flag == 1)
							error_msg = error_msg + ",Sc Approved Budget should not exceed tme uploaded value";
						else
							error_msg = error_msg + "Sc Approved Budget should not exceed tme uploaded value";
						flag = 1;
                	}
                	
                }
		        
		        }else {
				if (flag == 1)
					error_msg = error_msg + ",Mandatory input for Sc Approved Budget";
				else
					error_msg = error_msg + "Mandatory input for Sc Approved Budget";
				flag = 1;
			}	
			
			query.setString(15, beanArray[i].getScApprovedBdg());
			}
			else {
				query.setString(15, "");
			}
			query.setString(16, beanArray[i].getCluster());
			query.setString(17, beanArray[i].getTemplatetype());
			query.setString(18, beanArray[i].getSoltype());
			query.setString(19, beanArray[i].getIncrementalBudget());
			query.setString(20, beanArray[i].getStockAvailability());
			query.setString(21, beanArray[i].getSignedOffWithCM());
			query.setString(22, beanArray[i].getSignedOffWithAvailability());
			//Added by Kavitha D-SPRINT 18 changes
			if(beanArray[i].getTemplatetype().equalsIgnoreCase("CR")) {
			if((beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("ACCEPTED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("APPROVED")) &&(Integer.parseInt(beanArray[i].getScApprovedQty())== Integer.parseInt(beanArray[i].getQuantity()))) {
				query.setString(23,"40");
				query.setString(25,timeStamp);

			}
			else if(beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("REJECTED")) {
				query.setString(23,"41");
				query.setString(25,null);

			}
			else if(beanArray[i].getSignedOffWithAvailability().isEmpty()){ //Added by Kajal G-SPRINT 15 changes 
				query.setString(23,"44");
				query.setString(25,null);

			}
			else if(beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("PARTIAL APPROVED"))  {
				if (beanArray[i].getScApprovedQty().isEmpty()) {
					query.setString(23,"38");
					query.setString(25,null); 
				}
				else {
					if (Integer.parseInt(beanArray[i].getScApprovedQty()) < Integer.parseInt(beanArray[i].getQuantity())) {
						query.setString(23,"45");
						query.setString(25,timeStamp); 						
					}
					else {
						query.setString(23,"38");
						query.setString(25,null); 
						
					}

				}
			}
			}
			else if(beanArray[i].getTemplatetype().equalsIgnoreCase("New Entry") || beanArray[i].getTemplatetype().equalsIgnoreCase("Regular")) {

				if(beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("ACCEPTED") || beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("APPROVED")) {
					query.setString(23,"40");
					query.setString(25,timeStamp);

				}
				else if(beanArray[i].getSignedOffWithAvailability().equalsIgnoreCase("REJECTED")) {
					query.setString(23,"41");
					query.setString(25,null);

				}
				else if(beanArray[i].getSignedOffWithAvailability().isEmpty()){  
					query.setString(23,"44");
					query.setString(25,null);

				}
				
			}
			
			query.setString(24,userId);
			
			if (flag == 1)
				globle_flag = 1;
			query.setString(26, error_msg);
			
			int executeUpdate = query.executeUpdate();
			error_msg = "";
			flag = 0;
			}
		}
			if (globle_flag == 0) {
				
				saveToMainTable(userId);
				globle_flag = 0;
				response= "EXCEL_UPLOADED";

			} else {
				globle_flag = 0;
				response= "EXCEL_NOT_UPLOADED";
			}
			}catch(Exception e) {
				logger.info(e);
			}
		return response;
		}

	public synchronized boolean saveToMainTable(String userId) {
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
					+ " SET A.SC_DOA=B.SC_DOA,A.STATUS=B.STATUS,A.SIGNED_OFF_WITH_CM=B.SIGNED_OFF_WITH_CM,"
					+ " A.USER_ID='" + userId + "',A.UPDATE_STAMP='"+ dateFormat.format(date) +"', "
					+ " A.SC_APPROVED_QTY=B.SC_APPROVED_QTY,A.SC_APPROVED_BDG=B.SC_APPROVED_BDG "   //Added by Kavitha D-SPRINT 18 changes
					+ " WHERE B.USER_ID='" + userId + "' " ;
			logger.info("Updated query in sc:"+updateSql);
			Query queryUpdateExisting = sessionFactory.getCurrentSession().createNativeQuery(updateSql);
			queryUpdateExisting.executeUpdate();


	} catch (Exception e) {
		logger.error("Error in com.hul.proco.controller.promocrImpl.insertIntoTotMaster(String)", e);
	}

} 	//Added by Kavitha D for PromoApproval Upload ends-SPRINT 10


	//Added by Kavitha D for PromoApproval error file download-SPRINT 18

	@Override
	public List<ArrayList<String>> getPromotionApprovalScErrorDetails(ArrayList<String> headerList, String userId,
			String roleId) {
		// TODO Auto-generated method stub
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "";
			qry=" CREATE TEMPORARY TABLE TT_SC_PROMO_ERROR_DOWNLOAD SELECT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,REGULAR_PROMO_QUANTITY,"
					+ "SC_APPROVED_QTY,QUANTITY,BUDGET,REGULAR_PROMO_BUDGET,SC_APPROVED_BDG,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,"
					+ " CASE WHEN TEMPLATE_TYPE = 'Regular' THEN 'REG' WHEN TEMPLATE_TYPE = 'New Entry' THEN 'NE' ELSE '     ' END AS CR_SOL_TYPE_SHORTKEY,"  // Added by KAJAL G in SPRINT-18
					+ "INCREMENTAL_BUDGET,STOCK_AVAILABILITY,"
					+ "SIGNED_OFF_WITH_CM,REMARK,ERROR_MSG FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID=:userId";

			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter("userId", userId);
			query.executeUpdate();

			String updateTempTable = "UPDATE TT_SC_PROMO_ERROR_DOWNLOAD LR INNER JOIN TBL_PROCO_SOL_TYPE CR ON CR.SOL_REMARK= LR.CR_SOL_TYPE "
					+ "SET LR.CR_SOL_TYPE_SHORTKEY=CR.SOL_TYPE";
			
			Query query1 = sessionFactory.getCurrentSession().createNativeQuery(updateTempTable);
			query1.executeUpdate();
			
			String downloadScEntry = "SELECT DISTINCT CHANNEL_NAME,MOC,SALES_CATEGORY,PPM_ACCOUNT,PROMO_ID,OFFER_DESC,BASEPACK_CODE,OFFER_TYPE,OFFER_MODALITY,PRICE_OFF,"
					+ " REGULAR_PROMO_QUANTITY,SC_APPROVED_QTY,QUANTITY,BUDGET,REGULAR_PROMO_BUDGET,SC_APPROVED_BDG,CLUSTER,TEMPLATE_TYPE,CR_SOL_TYPE,CR_SOL_TYPE_SHORTKEY,"
					+ " INCREMENTAL_BUDGET,STOCK_AVAILABILITY,SIGNED_OFF_WITH_CM,REMARK,ERROR_MSG FROM TT_SC_PROMO_ERROR_DOWNLOAD";

		Query query2 = sessionFactory.getCurrentSession().createNativeQuery(downloadScEntry);

		Iterator itr = query2.list().iterator();
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

	} catch (Exception e) {
		logger.debug(e);
	}
	return downloadDataList;
}
	

}

	
	



	
	
		

