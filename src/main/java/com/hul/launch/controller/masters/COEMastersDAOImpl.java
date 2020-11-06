package com.hul.launch.controller.masters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class COEMastersDAOImpl implements COEMastersDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	Logger logger=Logger.getLogger(COEMastersDAOImpl.class);
	
	private static final String SQL_QUERY_INSERT_INTO_STORE_MASTER_TEMP = "INSERT INTO TBL_LAUNCH_STORE_MASTER_TEMP(HUL_OUTLET_CODE,CUSTOMER_CODE,SERVICING_TYPE,CUSTOMER_CHAIN_L1,HUL_STORE_FORMAT,CUSTOMER_STORE_FORMAT,BRANCH,STATE,TOWN_NAME,HUL_DEPOT,CLUSTER,ACTIVE,USER_ID) VALUES(?0,?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)";  //Sarin - Added Parameters position
	
	private static final String SQL_QUERY_INSERT_INTO_CLASSIFICATION_MASTER_TEMP = "INSERT INTO TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP(CUSTOMER_CHAIN_L1,CUSTOMER_STORE_FORMAT,GOLD,SILVER,BRONZE,VISIBILITY_ELIGIBILITY,USER_ID) VALUES(?0,?1,?2,?3,?4,?5,?6)";  //Sarin - Added Parameters position

	@Override
	public String insertStoreMaster(StoreMasterBean[] bean, String userId) {
		String response = null;
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_LAUNCH_STORE_MASTER_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			//kiran - bigint to int changes
			//Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();
			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_LAUNCH_STORE_MASTER_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery(SQL_QUERY_INSERT_INTO_STORE_MASTER_TEMP);
			for (int i = 0; i < bean.length; i++) {
				query.setString(0, bean[i].getHulOutletCode().trim().toUpperCase());
				query.setString(1, bean[i].getCustomerCode().trim().toUpperCase());
				query.setString(2, bean[i].getServicingType().trim().toUpperCase());
				query.setString(3, bean[i].getCustomerChainL1().trim().toUpperCase());
				query.setString(4, bean[i].getHulStoreFormat().trim().toUpperCase());
				query.setString(5, bean[i].getCustomerStoreFormat().trim().toUpperCase());
				query.setString(6, bean[i].getBranch().trim().toUpperCase());
				query.setString(7, bean[i].getState().trim().toUpperCase());
				query.setString(8, bean[i].getTownName().trim().toUpperCase());
				query.setString(9, bean[i].getHulDepot().trim().toUpperCase());
				query.setString(10, bean[i].getCluster().trim().toUpperCase());
				query.setString(11, bean[i].getActive().trim());
				query.setString(12, userId);
				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateStoreRecord(bean[i], userId, i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}
			if (!responseList.contains("ERROR_FILE")) {
				boolean savePromotionToMainTable = insertStoreMasterToMainTable(bean,userId);
				if (!savePromotionToMainTable) {
					response = "ERROR";
				} else {
					response = "SUCCESS_FILE";
				}
			} else {
				response = "ERROR_FILE";
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return response;
	
	}

	private boolean insertStoreMasterToMainTable(StoreMasterBean[] bean, String userId) {
		boolean res = true;
		try {
			//Garima - Changed Merge query
			//Query query = sessionFactory.getCurrentSession().createNativeQuery("MERGE INTO TBL_LAUNCH_STORE_MASTER AS A USING TBL_LAUNCH_STORE_MASTER_TEMP AS B ON A.HUL_OUTLET_CODE=B.HUL_OUTLET_CODE AND B.USER_ID=:userId WHEN MATCHED THEN UPDATE SET A.CUSTOMER_CODE=B.CUSTOMER_CODE,A.SERVICING_TYPE=B.SERVICING_TYPE,A.CUSTOMER_CHAIN_L1=B.CUSTOMER_CHAIN_L1,A.HUL_STORE_FORMAT=B.HUL_STORE_FORMAT, A.CUSTOMER_STORE_FORMAT=B.CUSTOMER_STORE_FORMAT,A.BRANCH=B.BRANCH,A.STATE=B.STATE,A.TOWN_NAME=B.TOWN_NAME,A.HUL_DEPOT=B.HUL_DEPOT,A.CLUSTER=B.CLUSTER,A.ACTIVE=B.ACTIVE WHEN NOT MATCHED THEN INSERT(HUL_OUTLET_CODE,CUSTOMER_CODE,SERVICING_TYPE,CUSTOMER_CHAIN_L1,HUL_STORE_FORMAT,CUSTOMER_STORE_FORMAT,BRANCH,STATE,TOWN_NAME,HUL_DEPOT,CLUSTER,ACTIVE) VALUES(B.HUL_OUTLET_CODE,B.CUSTOMER_CODE,B.SERVICING_TYPE,B.CUSTOMER_CHAIN_L1,B.HUL_STORE_FORMAT,B.CUSTOMER_STORE_FORMAT,B.BRANCH,B.STATE,B.TOWN_NAME,B.HUL_DEPOT,B.CLUSTER,B.ACTIVE)");
			
			Query qry1 = sessionFactory.getCurrentSession().createNativeQuery("UPDATE TBL_LAUNCH_STORE_MASTER AS A INNER JOIN TBL_LAUNCH_STORE_MASTER_TEMP AS B ON A.HUL_OUTLET_CODE = B.HUL_OUTLET_CODE AND B.USER_ID = :userId SET A.CUSTOMER_CODE=B.CUSTOMER_CODE,A.SERVICING_TYPE=B.SERVICING_TYPE,A.CUSTOMER_CHAIN_L1=B.CUSTOMER_CHAIN_L1,A.HUL_STORE_FORMAT=B.HUL_STORE_FORMAT, A.CUSTOMER_STORE_FORMAT=B.CUSTOMER_STORE_FORMAT,A.BRANCH=B.BRANCH,A.STATE=B.STATE,A.TOWN_NAME=B.TOWN_NAME,A.HUL_DEPOT=B.HUL_DEPOT,A.CLUSTER=B.CLUSTER,A.ACTIVE=B.ACTIVE");
			qry1.setParameter("userId", userId);
			qry1.executeUpdate();
			
			Query query = sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO TBL_LAUNCH_STORE_MASTER (HUL_OUTLET_CODE,CUSTOMER_CODE,SERVICING_TYPE,CUSTOMER_CHAIN_L1,HUL_STORE_FORMAT,CUSTOMER_STORE_FORMAT,BRANCH,STATE,TOWN_NAME,HUL_DEPOT,CLUSTER,ACTIVE) SELECT B.HUL_OUTLET_CODE,B.CUSTOMER_CODE,B.SERVICING_TYPE,B.CUSTOMER_CHAIN_L1,B.HUL_STORE_FORMAT,B.CUSTOMER_STORE_FORMAT,B.BRANCH,B.STATE,B.TOWN_NAME,B.HUL_DEPOT,B.CLUSTER, B.ACTIVE FROM TBL_LAUNCH_STORE_MASTER_TEMP AS B LEFT JOIN TBL_LAUNCH_STORE_MASTER AS A ON A.HUL_OUTLET_CODE = B.HUL_OUTLET_CODE AND B.USER_ID = :userId");
			query.setParameter("userId", userId);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception:",e);
			res=false;
		}
		return res;
	}

	private String validateStoreRecord(StoreMasterBean bean, String userId, int row) {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			Query qry=sessionFactory.getCurrentSession().createNativeQuery("SELECT COUNT(1) FROM TBL_LAUNCH_STORE_MASTER_TEMP WHERE HUL_OUTLET_CODE=:hulOutletCode AND USER_ID=:userId");
			qry.setString("hulOutletCode", bean.getHulOutletCode());
			qry.setString("userId", userId);
			//kiran - bigint to int changes
			//Integer hulOutletCodeCount = (Integer) qry.uniqueResult();
			Integer hulOutletCodeCount = ((BigInteger) qry.uniqueResult()).intValue();
			if(hulOutletCodeCount!=null && hulOutletCodeCount.intValue()>1) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "Duplicate HUL_OUTLET_CODE in file.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			
			if (bean.getHulOutletCode() == null || bean.getHulOutletCode().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "HUL_OUTLET_CODE cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getCustomerCode()==null || bean.getCustomerCode().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "CUSTOMER_CODE cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getServicingType()==null || bean.getServicingType().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "SERVICING_TYPE cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getCustomerChainL1()==null || bean.getCustomerChainL1().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "CUSTOMER_CHAIN_L1 cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getHulStoreFormat()==null || bean.getHulStoreFormat().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "HUL_STORE_FORMAT cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getBranch()==null || bean.getBranch().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "BRANCH cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getState()==null || bean.getState().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "STATE cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getHulDepot()==null || bean.getHulDepot().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "HUL_DEPOT cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getTownName()==null || bean.getTownName().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "TOWN_NAME cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getCluster()==null || bean.getCluster().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "CLUSTER cannot be blank.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if(bean.getActive()==null || bean.getActive().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "ACTIVE should be 0/1.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}else if(!bean.getActive().equals("1") || !bean.getActive().equals("0")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "ACTIVE should be 0/1.^";
				updateStoreMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return res;
	}

	private void updateStoreMasterErrorMessageInTemp(String errorMsg, String userId, int row) {
		try {
			String qry = "UPDATE TBL_LAUNCH_STORE_MASTER_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setInteger("row", row);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getStoreMasterErrorDetails(ArrayList<String> headerList, String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT HUL_OUTLET_CODE,CUSTOMER_CODE,SERVICING_TYPE,CUSTOMER_CHAIN_L1,HUL_STORE_FORMAT,CUSTOMER_STORE_FORMAT,BRANCH,STATE,TOWN_NAME,HUL_DEPOT,CLUSTER,ACTIVE,ERROR_MSG FROM TBL_LAUNCH_STORE_MASTER_TEMP WHERE USER_ID=:userId";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter("userId", userId);
			Iterator itr = query.list().iterator();
			downloadDataList.add(headerList);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value);
				}
				obj = null;
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}
	
	@Override
	public String insertLaunchPlanClassificationMaster(ClassificationBean[] bean, String userId) {
		String response = null;
		ArrayList<String> responseList = new ArrayList<String>();
		try {
			Query queryToCheck = sessionFactory.getCurrentSession()
					.createNativeQuery("select count(1) from TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP where USER_ID=:user");
			queryToCheck.setString("user", userId);
			//kiran - bigint to int changes
			//Integer recCount = (Integer) queryToCheck.uniqueResult();
			Integer recCount = ((BigInteger)queryToCheck.uniqueResult()).intValue();
			if (recCount.intValue() > 0) {
				Query queryToDelete = sessionFactory.getCurrentSession()
						.createNativeQuery("DELETE from TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP where USER_ID=:userId");
				queryToDelete.setString("userId", userId);
				queryToDelete.executeUpdate();
			}
			Query query = sessionFactory.getCurrentSession()
					.createNativeQuery(SQL_QUERY_INSERT_INTO_CLASSIFICATION_MASTER_TEMP);
			for (int i = 0; i < bean.length; i++) {
				query.setString(0, bean[i].getCustomerChainL1().trim().toUpperCase());
				query.setString(1, bean[i].getCustomerStoreFormat().trim().toUpperCase());
				query.setString(2, bean[i].getGold().trim());
				query.setString(3, bean[i].getSilver().trim());
				query.setString(4, bean[i].getBronze().trim());
				query.setString(5, bean[i].getVisibilityEligibility().trim());
				query.setString(6, userId);
				int executeUpdate = query.executeUpdate();
				if (executeUpdate > 0) {
					response = validateClassificationRecord(bean[i], userId, i);
					if (response.equals("ERROR_FILE")) {
						responseList.add(response);
					}
				}
			}
			if (!responseList.contains("ERROR_FILE")) {
				boolean savePromotionToMainTable = insertClassificationMasterToMainTable(bean,userId);
				if (!savePromotionToMainTable) {
					response = "ERROR";
				} else {
					response = "SUCCESS_FILE";
				}
			} else {
				response = "ERROR_FILE";
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return response;
	}

	private boolean insertClassificationMasterToMainTable(ClassificationBean[] classificationBeanArray, String userId) {
		boolean res=true;
		try {
			Query qryToUpdate=sessionFactory.getCurrentSession().createNativeQuery("UPDATE TBL_LAUNCH_CLASSIFICATION_MASTER SET GOLD=:gold,SILVER=:silver,BRONZE=:bronze,VISIBILITY_ELIGIBILITY=:ve WHERE CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_STORE_FORMAT=:customerStoreFormat");
			Query qryToInsert=sessionFactory.getCurrentSession().createNativeQuery("INSERT INTO TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP(CUSTOMER_CHAIN_L1,CUSTOMER_STORE_FORMAT,GOLD,SILVER,BRONZE,VISIBILITY_ELIGIBILITY) VALUES(?0,?1,?2,?3,?4,?5)");  //Sarin - Added Parameters position
			Query qryTocheck=sessionFactory.getCurrentSession().createNativeQuery("SELECT COUNT(1) FROM TBL_LAUNCH_CLASSIFICATION_MASTER WHERE CUSTOMER_CHAIN_L1=:customerChainL1 AND CUSTOMER_STORE_FORMAT=:customerStoreFormat");
			for (ClassificationBean bean : classificationBeanArray) {
				qryTocheck.setString("customerChainL1", bean.getCustomerChainL1().trim().toUpperCase());
				qryTocheck.setString("customerStoreFormat",bean.getCustomerStoreFormat().trim().toUpperCase());
				//kiran - bigint to int changes
				//Integer count = (Integer) qryTocheck.uniqueResult();
				Integer count = ((BigInteger)qryTocheck.uniqueResult()).intValue();
				if(count!=null && count.intValue()>0) {
					qryToUpdate.setInteger("gold", Integer.parseInt(bean.getGold().trim()));
					qryToUpdate.setInteger("silver", Integer.parseInt(bean.getSilver().trim()));
					qryToUpdate.setInteger("bronze", Integer.parseInt(bean.getBronze().trim()));
					qryToUpdate.setInteger("ve", Integer.parseInt(bean.getVisibilityEligibility().trim()));
					qryToUpdate.setString("customerChainL1", bean.getCustomerChainL1().trim().toUpperCase());
					qryToUpdate.setString("customerStoreFormat", bean.getCustomerStoreFormat().trim().toUpperCase());
					qryToUpdate.executeUpdate();
				}else {
					qryToInsert.setString(0, bean.getCustomerChainL1().trim().toUpperCase());
					qryToInsert.setString(1, bean.getCustomerStoreFormat().trim().toUpperCase());
					qryToInsert.setInteger(2, Integer.parseInt(bean.getGold().trim()));
					qryToInsert.setInteger(3, Integer.parseInt(bean.getSilver().trim()));
					qryToInsert.setInteger(4, Integer.parseInt(bean.getBronze().trim()));
					qryToInsert.setInteger(5, Integer.parseInt(bean.getVisibilityEligibility().trim()));
					qryToInsert.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.debug("Exception:",e);
			res=false;
		}
		return res;
	}

	private String validateClassificationRecord(ClassificationBean bean, String userId, int row) {
		String res = "SUCCESS_FILE";
		String errorMsg = "";
		try {
			if (bean.getCustomerChainL1() == null || bean.getCustomerChainL1().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "CUSTOMER_CHAIN_L1 cannot be blank.^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}
			if (bean.getCustomerStoreFormat() == null || bean.getCustomerStoreFormat().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "CUSTOMER_STORE_FORMAT cannot be blank.^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}

			if (bean.getGold() == null || bean.getGold().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "GOLD should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			} else if (!bean.getGold().equals("2") && !bean.getGold().equals("1") && !bean.getGold().equals("0")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "GOLD should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}

			if (bean.getSilver() == null || bean.getSilver().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "SILVER should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			} else if (!bean.getSilver().equals("2") && !bean.getSilver().equals("1")
					&& !bean.getSilver().equals("0")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "SILVER should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}

			if (bean.getBronze() == null || bean.getBronze().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "BRONZE should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			} else if (!bean.getBronze().equals("2") && !bean.getBronze().equals("1")
					&& !bean.getBronze().equals("0")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "BRONZE should be(2/1/0)(Must Launch/Can be Considered/No Launch).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}

			if (bean.getVisibilityEligibility() == null || bean.getVisibilityEligibility().trim().equals("")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "VISIBILITY_ELIGIBILITY should be(1/0)(Plan Visibility/No Visibility).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			} else if (!bean.getVisibilityEligibility().equals("1") && !bean.getVisibilityEligibility().equals("0")) {
				res = "ERROR_FILE";
				errorMsg = errorMsg + "VISIBILITY_ELIGIBILITY should be(1/0)(Plan Visibility/No Visibility).^";
				updateClassificationMasterErrorMessageInTemp(errorMsg, userId, row);
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return res;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ArrayList<String>> getLaunchPlanClassificationErrorDetails(ArrayList<String> headerList,
			String userId) {
		List<ArrayList<String>> downloadDataList = new ArrayList<ArrayList<String>>();
		try {
			String qry = "SELECT CUSTOMER_CHAIN_L1,CUSTOMER_STORE_FORMAT,GOLD,SILVER,BRONZE,VISIBILITY_ELIGIBILITY,ERROR_MSG FROM TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP WHERE USER_ID=:userId";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setParameter("userId", userId);
			Iterator itr = query.list().iterator();
			downloadDataList.add(headerList);
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				ArrayList<String> dataObj = new ArrayList<String>();
				for (Object ob : obj) {
					String value = "";
					value = (ob == null) ? "" : ob.toString();
					dataObj.add(value);
				}
				obj = null;
				downloadDataList.add(dataObj);
			}
			return downloadDataList;
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
		return downloadDataList;
	}
	
	private void updateClassificationMasterErrorMessageInTemp(String errorMsg, String userId, int row) {
		try {
			String qry = "UPDATE TBL_LAUNCH_CLASSIFICATION_MASTER_TEMP SET ERROR_MSG=:errorMsg WHERE USER_ID=:userId AND ROW_NO=:row";
			Query query = sessionFactory.getCurrentSession().createNativeQuery(qry);
			query.setString("errorMsg", errorMsg);
			query.setString("userId", userId);
			query.setInteger("row", row);
			query.executeUpdate();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
		}
	}
}
