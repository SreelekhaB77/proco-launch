package com.hul.proco.controller.createpromo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;

/**
 * DataFromTable will return all the data from the table based of condition.
 * 
 * @author Mayur chavhan
 * 
 */
@Component
public class DataFromTable {

	@Autowired
	SessionFactory sessionFactory;

	private static String TBL_PROCO_CUSTOMER_MASTER_V2_STRING = "SELECT CHANNEL_NAME,PPM_ACCOUNT,MOC_GROUP FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE IS_ACTIVE='Y'";

	/**
	 * This updatePPMDescStage will update PPM_DESC_STAGE for every entry made by
	 * user
	 * 
	 * @param uid
	 * @return void
	 */
	public void updatePPMDescStage(String uid, String template_type)
	{
		String update_ppm_desc = "";
		if (template_type.equalsIgnoreCase("cr")) {
			update_ppm_desc = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 T "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 A ON A.PPM_ACCOUNT=T.PPM_ACCOUNT AND A.CHANNEL_NAME=T.CHANNEL_NAME "
					+ "INNER JOIN TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 B ON B.OFFER_MODALITY = T.OFFER_MODALITY AND B.OFFER_TYPE = T.OFFER_TYPE "
					+ "INNER JOIN  (SELECT BASEPACK,SALES_CATEGORY FROM (SELECT ROW_NUMBER() OVER (PARTITION BY BASEPACK ORDER BY BASEPACK,SALES_CATEGORY) AS ROW_NUM,BASEPACK,SALES_CATEGORY FROM TBL_PROCO_PRODUCT_MASTER_V2 A WHERE IS_ACTIVE=1 GROUP BY BASEPACK,SALES_CATEGORY) A WHERE ROW_NUM=1) C ON C.BASEPACK=T.BASEPACK_CODE "
					+ " " + "SET "
					+ "PPM_DESC_STAGE=CONCAT(A.ACCOUNT_TYPE ,':',  B.MODALITY_KEY,':',T.PPM_ACCOUNT,':',C.SALES_CATEGORY,':',T.OFFER_DESC,':',T.PROMOTION_ID,IF (A.NON_UNIFY <> '', concat(':', A.NON_UNIFY), ''))  WHERE USER_ID='"
					+ uid + "'  " + "";
		} else {
			
			update_ppm_desc = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 T "
					+ "INNER JOIN TBL_PROCO_CUSTOMER_MASTER_V2 A ON A.PPM_ACCOUNT=T.PPM_ACCOUNT AND A.CHANNEL_NAME=T.CHANNEL_NAME "
					+ "INNER JOIN TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 B ON B.OFFER_MODALITY = T.OFFER_MODALITY AND B.OFFER_TYPE = T.OFFER_TYPE "
					+ "INNER JOIN  (SELECT BASEPACK,SALES_CATEGORY FROM (SELECT ROW_NUMBER() OVER (PARTITION BY BASEPACK ORDER BY BASEPACK,SALES_CATEGORY) AS ROW_NUM,BASEPACK,SALES_CATEGORY FROM TBL_PROCO_PRODUCT_MASTER_V2 A WHERE IS_ACTIVE=1 GROUP BY BASEPACK,SALES_CATEGORY) A WHERE ROW_NUM=1) C ON C.BASEPACK=T.BASEPACK_CODE "
					+ " " + "SET "
					+ "PPM_DESC_STAGE=CONCAT(A.ACCOUNT_TYPE ,':',  B.MODALITY_KEY,':',T.PPM_ACCOUNT,':',C.SALES_CATEGORY,':',T.OFFER_DESC,IF (A.NON_UNIFY <> '', concat(':', A.NON_UNIFY), ''))  WHERE USER_ID='"
					+ uid + "'  " + "";
		}
		sessionFactory.getCurrentSession().createNativeQuery(update_ppm_desc).executeUpdate();

	}
	
	public Map<String,String> handlingASMLAc()
	{
		String asmlString="SELECT DISTINCT OFFER_MODALITY,OFFER_TYPE FROM TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 WHERE PPM_ACCOUNT ='ASML' AND IS_ACTIVE=1";
		List<Object[]> mapdata_list = sessionFactory.getCurrentSession().createNativeQuery(asmlString).list();
		Map<String,String> asmlMap= new HashMap<String, String>();
		for(Object[] obj: mapdata_list)
		{
			asmlMap.put(String.valueOf(obj[0]).toUpperCase()+String.valueOf(obj[1]).toUpperCase(),"");
		}
		return asmlMap;
	}
	
	public Map<String,String> getAllOffetTypeAndOfferMod()
	{
		Map<String,String> mod_map = new HashMap<String, String>();
		String offer_mod="SELECT DISTINCT OFFER_MODALITY,OFFER_TYPE FROM TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 WHERE IS_ACTIVE=1";
		List<Object[]> mapdata_list = sessionFactory.getCurrentSession().createNativeQuery(offer_mod).list();
		for(Object[] obj: mapdata_list)
		{
			mod_map.put(String.valueOf(obj[0]).toUpperCase(), String.valueOf(obj[1]).toUpperCase());
		}
		
		return mod_map;
		
	}
	
	public void updatePromoIdInTemp(String promoid, String moc_name, String ppm_account, String basepack_code,String pid,String year) {
	String updateintemp="UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 SET PROMO_ID='"+promoid+"',PID='"+pid+"',PPM_DESC=CONCAT('"+promoid+"',':',PPM_DESC_STAGE) WHERE MOC_NAME='"+moc_name+"' AND PPM_ACCOUNT='"+ppm_account+"'"+
				" AND BASEPACK_CODE='"+basepack_code+"' AND MOC_YEAR='"+year+"'";
	sessionFactory.getCurrentSession().createNativeQuery(updateintemp).executeUpdate();
	}
	/**
	 * 
	 * @param pid
	 * @param ppm_account
	 * @param year
	 * @param moc
	 */
	public void updatePIdInTemp(String pid, String ppm_account,String year,String moc)
	{
		String pid_update="UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 SET pid='"+pid+"' WHERE MOC_NAME='"+moc+"' AND MOC_YEAR='"+year+"' AND PPM_ACCOUNT='"+ppm_account+"'"; 
		sessionFactory.getCurrentSession().createNativeQuery(pid_update).executeUpdate();
	}
	public void getConbination(Map<String,String> h)
	{
		//bean.getMoc_name() + bean.getPpm_account() + bean.getYear() + sale_cate
		String q_String="SELECT MOC_NAME,MOC_YEAR,PPM_ACCOUNT,BASEPACK_CODE,CREATED_BY,CREATED_DATE,CLUSTER,CR_SOL_TYPE FROM TBL_PROCO_PROMOTION_MASTER_V2 ";
		List<Object[]> mapdata_list = sessionFactory.getCurrentSession().createNativeQuery(q_String).list();
		for (Object[] data : mapdata_list) {
			h.put(String.valueOf(data[0]).toUpperCase() + String.valueOf(data[1]).toUpperCase()
					+ String.valueOf(data[2]).toUpperCase() + String.valueOf(data[3]).toUpperCase(),
					String.valueOf(data[4]) + " " + String.valueOf(data[5]));
			
			h.put(String.valueOf(data[0]).toUpperCase() + String.valueOf(data[1]).toUpperCase()
					+ String.valueOf(data[2]).toUpperCase() + String.valueOf(data[3]).toUpperCase()+String.valueOf(data[6]).toUpperCase(),
					String.valueOf(data[4]) + " " + String.valueOf(data[5]));
			
			h.put(String.valueOf(data[0]).toUpperCase() + String.valueOf(data[1]).toUpperCase()
					+ String.valueOf(data[2]).toUpperCase() + String.valueOf(data[3]).toUpperCase()+String.valueOf(data[6]).toUpperCase()+String.valueOf(data[7]).toUpperCase(),
					String.valueOf(data[4]) + " " + String.valueOf(data[5])); 
			
		}
		
	}
	
	public void getAllClusterBasedOnPPM(Map<String,ArrayList<String>> clusterandppm)
	{
		String cluster_ppm="SELECT DISTINCT PPM_ACCOUNT,CLUSTER FROM TBL_PROCO_CLUSTER_MASTER_V2 WHERE IS_ACTIVE=1";
		List<Object[]> mapdata_list = sessionFactory.getCurrentSession().createNativeQuery(cluster_ppm).list();
		for(Object[] obj: mapdata_list)
		{
			if(clusterandppm.containsKey(String.valueOf(obj[0]).toUpperCase()))
			{
				ArrayList<String> arr=clusterandppm.get(String.valueOf(obj[0]).toUpperCase());
				arr.add(String.valueOf(obj[1]).toUpperCase());
				clusterandppm.put(String.valueOf(obj[0]).toUpperCase(), arr);
			}else
			{
				ArrayList<String> arr =new ArrayList<String>();
				arr.add(String.valueOf(obj[1]).toUpperCase());
			   clusterandppm.put(String.valueOf(obj[0]).toUpperCase(), arr);
			}
		}
	}

	public Set<String> updateExistingPromoId(String uid) {
		String getPromo = "UPDATE TBL_PROCO_PROMOTION_MASTER_TEMP_V2 A  "
				+ "INNER JOIN TBL_PROCO_PROMOTION_MASTER_V2 B ON A.MOC_NAME=B.MOC_NAME   "
				+ "AND A.BASEPACK_CODE=B.BASEPACK_CODE AND A.PPM_ACCOUNT=B.PPM_ACCOUNT   "
				+ "AND A.PPM_DESC_STAGE=B.PPM_DESC_STAGE  " + "SET   "
				+ "  A.PID=B.PID,A.PROMO_ID=B.PROMO_ID WHERE A.USER_ID='" + uid + "'";
		sessionFactory.getCurrentSession().createNativeQuery(getPromo).executeUpdate();

		String mapdata = "SELECT A.BASEPACK_CODE,A.MOC_NAME,A.PPM_ACCOUNT,B.PPM_DESC_STAGE,B.MOC_YEAR FROM TBL_PROCO_PROMOTION_MASTER_V2 A "
				+ "INNER JOIN TBL_PROCO_PROMOTION_MASTER_TEMP_V2 B ON A.MOC_NAME=B.MOC_NAME  "
				+ "AND A.BASEPACK_CODE=B.BASEPACK_CODE AND A.PPM_ACCOUNT=B.PPM_ACCOUNT  "
				+ "AND A.PPM_DESC_STAGE=B.PPM_DESC_STAGE WHERE A.USER_ID='" + uid + "'";
		List<Object[]> mapdata_list = sessionFactory.getCurrentSession().createNativeQuery(mapdata).list();
		HashSet<String> set = new HashSet<String>();
		for (Object[] data : mapdata_list) {
			set.add(String.valueOf(data[0]) + String.valueOf(data[1]) + String.valueOf(data[2])+String.valueOf(data[3])+String.valueOf(data[4]));
		}
		return set;
	}

	/**
	 *
	 * @param moc_name
	 * @param year
	 * @return MOC in format mmyyyy
	 */
	public String getMOC(String moc_name, String year) {
		String query = "SELECT DISTINCT MOC FROM TBL_VAT_MOC_MASTER WHERE MOC_YEAR='" + year + "' AND MOC_NAME='"
				+ moc_name + "'";
		Query session_query = sessionFactory.getCurrentSession().createNativeQuery(query);
		return session_query.uniqueResult().toString();
	}
    /**
     * 
     * @return map to send the dates by to controller implementation
     */
	public Map<String, String> handleDates() {
		String TBL_VAT_MOC_MASTER_STRING = "select MOC, START_DATE, END_DATE, MOC_NAME, MOC_YEAR, MOC_GROUP, STATUS,SUBSTRING(MOC_NAME,4,4) AS VALUE ,CURDATE() AS CURDATE  from TBL_VAT_MOC_MASTER where status='Y' UNION ALL "
				+ "select MOC, START_DATE, END_DATE, MOC_NAME, MOC_YEAR, MOC_GROUP, status,SUBSTRING(MOC_NAME,4,4),CURDATE() AS CURDATE from TBL_VAT_MOC_MASTER where MOC_YEAR IN ("
				+ getCurrentYear() + ", " + (getCurrentYear() + 1) + ") and start_date > current_timestamp ";
		Map<String, String> master_map = new HashMap<String, String>();

		List<Object[]> tbl_vat_moc_master_list = sessionFactory.getCurrentSession()
				.createNativeQuery(TBL_VAT_MOC_MASTER_STRING).list();
		List<Object[]> tbl_proco_customer_master_v2_list = sessionFactory.getCurrentSession()
				.createNativeQuery(TBL_PROCO_CUSTOMER_MASTER_V2_STRING).list();

		for (Object[] tbl_proco_customer_master : tbl_proco_customer_master_v2_list) {
			master_map.put(
					String.valueOf(tbl_proco_customer_master[0]).toUpperCase() + "_" + String.valueOf(tbl_proco_customer_master[1]).toUpperCase(),
					String.valueOf(tbl_proco_customer_master[2]).toUpperCase()); // adding moc group base on CHANNEL_NAME and
																	// ppm_account
			master_map.put(String.valueOf(tbl_proco_customer_master[0]).toUpperCase() + String.valueOf(tbl_proco_customer_master[1]).toUpperCase(), ""); // for matching ppm and channel
		}
		
		for (Object[] tbl_vat_moc_master : tbl_vat_moc_master_list) {
			master_map.put(
					String.valueOf(tbl_vat_moc_master[3]).toUpperCase() + String.valueOf(tbl_vat_moc_master[4]).toUpperCase()
							+ String.valueOf(tbl_vat_moc_master[5]).toUpperCase() + "_start_date",
					String.valueOf(tbl_vat_moc_master[1]).toUpperCase());// adding start_date based on
															// MOC_NAME+MOC_YEAR+MOC_GROUP_"start_date"
			master_map.put(
					String.valueOf(tbl_vat_moc_master[3]).toUpperCase() + String.valueOf(tbl_vat_moc_master[4]).toUpperCase()
							+ String.valueOf(tbl_vat_moc_master[5]).toUpperCase() + "_end_date",
					String.valueOf(tbl_vat_moc_master[2]).toUpperCase());// adding start_date based on
															// MOC_NAME+MOC_YEAR+MOC_GROUP_"end_date"

			if (String.valueOf(tbl_vat_moc_master[6]).equalsIgnoreCase("Y")) {
				master_map.put(String.valueOf(tbl_vat_moc_master[3]).toUpperCase() + String.valueOf(tbl_vat_moc_master[4]).toUpperCase()
						+ String.valueOf(tbl_vat_moc_master[6]).toUpperCase(), String.valueOf(tbl_vat_moc_master[8]).toUpperCase()); // adding if
																											// moc_name
																											// is
																											// current
			}
		}
		
		return master_map;
	}

	/**
	 * if year is from excel current year and next then return true else false
	 * 
	 * @param yearfromexcel
	 * @return boolean
	 */
	public boolean validateYear(String yearfromexcel, String moc) {
	    
		String s=moc.replaceAll("[^0-9]", "");
		int lastchar = Integer.parseInt(s);
		if (yearfromexcel.length() == 4) {
			int year = Integer.parseInt(yearfromexcel);
			if (year == getCurrentYear() && lastchar < 6) {
				return true;
			} else if (year == (getCurrentYear() + 1) || (year == getCurrentYear() && lastchar >= 6)) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}
    /**
     * 
     * @return current year
     */
	private int getCurrentYear() {
		Date d = new Date();
		int year = d.getYear();
		int currentYear = year + 1900;
		return currentYear;
	}
	
	/**
	 * 
	 * @return Map<String, ArrayList<String>>
	 */
	public Map<String, ArrayList<String>> getAllValidationRecords() {
		Map<String, ArrayList<String>> validationmap = new HashMap<String, ArrayList<String>>();
		validationmap.put("SOL TYPE", getSOLType());
		validationmap.put("Channel name", getValidChannels());
		validationmap.put("PPM Account", getValidPPMAccount());
		// validationmap.put("AB creation", getABCreation());
		validationmap.put("baseback", getValidBasepack());
		validationmap.put("offer type", getValidOfferType());
		validationmap.put("Offer modality", getValidOfferModality());
		// validationmap.put("branch", getValidBranch());
		// validationmap.put("Secondary", getValidSec());
		validationmap.put("cluster", getValidCluster());
		return validationmap;
	}

	private ArrayList<String> getSOLType() {
		String SOLType = "SELECT DISTINCT SOL_TYPE FROM TBL_PROCO_SOL_TYPE";

		return (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(SOLType).list();
	}

	private ArrayList<String> getValidChannels() {

		String query = "SELECT DISTINCT CHANNEL_NAME FROM TBL_PROCO_CLUSTER_MASTER_V2 WHERE IS_ACTIVE=1";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(query).list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	public ArrayList<String> getValidPPMAccount() {
		String ppm_qury = "SELECT DISTINCT PPM_ACCOUNT FROM TBL_PROCO_CUSTOMER_MASTER_V2 WHERE  IS_ACTIVE='Y'";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(ppm_qury)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());

	}

	private ArrayList<String> getValidBasepack() {
		String basepack = "SELECT DISTINCT BASEPACK FROM TBL_PROCO_PRODUCT_MASTER_V2 WHERE IS_ACTIVE=1 AND PPM_STATUS='YES'";
		return (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(basepack).list();
	}

	private ArrayList<String> getValidOfferType() {
		String str_query = "SELECT DISTINCT OFFER_TYPE FROM TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 WHERE IS_ACTIVE=1";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(str_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	private ArrayList<String> getValidOfferModality() {
		String mod_query = "SELECT DISTINCT OFFER_MODALITY FROM TBL_PROCO_INVESTMENT_TYPE_MASTER_V2 WHERE IS_ACTIVE=1";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(mod_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}

	public ArrayList<String> getValidCluster() {
		String cluster_query = "SELECT DISTINCT CLUSTER FROM TBL_PROCO_CLUSTER_MASTER_V2 WHERE IS_ACTIVE=1";
		ArrayList<String> ar = (ArrayList<String>) sessionFactory.getCurrentSession().createNativeQuery(cluster_query)
				.list();
		return (ArrayList<String>) ar.stream().map(String::toUpperCase).collect(Collectors.toList());
	}
	public void basePackAndSaleCategory(Map<String, String> commanmap)
	{
		String sale_cat = "SELECT BASEPACK,SALES_CATEGORY,BP_MRP FROM (SELECT ROW_NUMBER() OVER (PARTITION BY BASEPACK ORDER BY BASEPACK,SALES_CATEGORY) AS ROW_NUM,BASEPACK,SALES_CATEGORY,BP_MRP FROM TBL_PROCO_PRODUCT_MASTER_V2 A WHERE IS_ACTIVE=1 GROUP BY BASEPACK,SALES_CATEGORY,BP_MRP) A WHERE ROW_NUM=1";
		
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(sale_cat).list();
		
		for(Object[] obj:list)
		{
			commanmap.put(String.valueOf(obj[0]).toUpperCase(), String.valueOf(obj[1]).toUpperCase());
			commanmap.put(String.valueOf(obj[0]).toUpperCase()+"_MRP", String.valueOf(obj[2]).toUpperCase());
		}
	}
	public void mapPPMandChannel(Map<String, String> commanmap) {
		// TODO Auto-generated method stub
		String strquery="SELECT DISTINCT PPM_ACCOUNT,CHANNEL_NAME FROM TBL_PROCO_CLUSTER_MASTER_V2 WHERE IS_ACTIVE=1";
		
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(strquery).list();
		
		for(Object[] obj:list)
		{
			commanmap.put(String.valueOf(obj[0]).toUpperCase(), String.valueOf(obj[1]).toUpperCase());
		}
		
	}
	/**
	 * 
	 * @param crEntries
	 */
	public void getCREntries(Map<String, String> crEntries) {
		// TODO Auto-generated method stub
		String stringquesry="SELECT DISTINCT MOC_NAME,BASEPACK_CODE,PPM_ACCOUNT,CR_SOL_TYPE FROM TBL_PROCO_PROMOTION_MASTER_V2";
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(stringquesry).list();
		for(Object[] obj:list)
		{
			crEntries.put(String.valueOf(obj[0]).toUpperCase()+String.valueOf(obj[1]).toUpperCase()+
					String.valueOf(obj[2]).toUpperCase()+String.valueOf(obj[3]).toUpperCase(), String.valueOf(obj[3]).toUpperCase());
		}
		
		
	}

	public void getAllSOLtype(Map<String, String> crEntries) {
		// TODO Auto-generated method stub
		String str="SELECT DISTINCT SOL_REMARK,SOL_TYPE FROM TBL_PROCO_SOL_TYPE";
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(str).list();
		for(Object[] obj:list)
		{
			crEntries.put(String.valueOf(obj[0]).toUpperCase(),"");
		}
	}
	
	public boolean specialChar(String ofr_desc)
	{
		String specialCharacters="(‘@’.^$*!/&)";
		boolean found = false;
		for(int i=0; i<specialCharacters.length(); i++){
		    
		    //Checking if the input string contain any of the specified Characters
		    if(ofr_desc.contains(Character.toString(specialCharacters.charAt(i)))){
		        found = true;
		        break;
		    }
		}
		return found;
				  
	}
	
	public String calculateBudget(String channel,String quantity,String price_off,String budget,String basepack,Map<String,String> map)
	{
		if(channel.equalsIgnoreCase("CNC") ||channel.equalsIgnoreCase("HUL3") )
		{
			return budget;
		}
		else
		{

			if(!price_off.contains("%"))
			{
				Double price=Double.valueOf(price_off);
				Double quanti=Double.valueOf(quantity);
				return String.valueOf(price*quanti);
			}else
			{
				Double price=Double.valueOf(price_off.substring(0,price_off.length()-1));
				Double quanti=Double.valueOf(quantity);
				return  String.valueOf(price*quanti*Double.parseDouble(map.get(basepack+"_MRP")));
			}
		}
		
	}
	
	/**
	 * To handle all CR template 
	 * @param crEntries
	 */
	public void getAllSOLCodeAndPromoId(Map<String,String> crEntries,Map<String,String> date_extension,Map<String,ArrayList<String>> check_existing_sol) {
		// TODO Auto-generated method stub
		String query="SELECT B.PROMOTION_ID,A.PROMO_ID,A.MOC_NAME,A.PPM_ACCOUNT,A.BASEPACK_CODE,A.CLUSTER,CASE WHEN LOCATE('%', A.PRICE_OFF) > 0 THEN A.PRICE_OFF ELSE ROUND(A.PRICE_OFF, 0) END AS PRICE_OFF,A.START_DATE,A.END_DATE,A.PROMO_TIMEPERIOD,A.MOC_YEAR"
				+ " FROM TBL_PROCO_PROMOTION_MASTER_V2 A INNER JOIN TBL_PROCO_MEASURE_MASTER_V2 B "
				+ "	ON "
				+ "	A.PROMO_ID=B.PROMO_ID "
				+ "	WHERE B.PROMOTION_STATUS IN ('APPROVED','AMEND APPROVED','SUBMITTED','AMEND SUBMITTED')";
		
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(query	).list();
		
		ArrayList<String> list_of_sol=new ArrayList<String>();
		
		for(Object[] obj: list)
		{
			crEntries.put(String.valueOf(obj[0]).toUpperCase(), String.valueOf(obj[0]));
			//PROMOTION_ID-0, PROMO_ID-1, MOC_NAME-2, PPM_ACCOUNT-3, BASEPACK_CODE-4, CLUSTER-5,PRICE_OFF-6, START_DATE-7, END_DATE-8, PROMO_TIMEPERIOD-9, MOC_YEAR -10
			
			//KEY: YEAR,MOC_NAME,PPM_ACCOUNT,BASEPACK_CODE,CLUSTER VALUE:PROMOTIME PERIOD
			date_extension.put(String.valueOf(obj[10]).toUpperCase() + String.valueOf(obj[2]).toUpperCase()
					+ String.valueOf(obj[3]).toUpperCase() + String.valueOf(obj[4]).toUpperCase()
					+ String.valueOf(obj[5]).toUpperCase(), String.valueOf(obj[9]).toUpperCase()); // CHECK FOR TPD  
			
			//KEY : PROMOTION_ID VALUE : PPM_ACCOUNT,BASEPACK_CODE,CLUSTER
			list_of_sol.add(String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase());
			check_existing_sol.put(String.valueOf(obj[0]).toUpperCase(), list_of_sol); //check if moc and year change for date extension 
			
			//KEY : PROMOTION_ID VALUE : PPM_ACCOUNT,BASEPACK_CODE,CLUSTER_MOC_NAME
			date_extension.put(String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase()+"_MOC_NAME",String.valueOf(obj[2]).toUpperCase()); //check if moc and year change for date extension 
			
			//KEY : PROMOTION_ID VALUE : PPM_ACCOUNT,BASEPACK_CODE,CLUSTER_MOC_YEAR
			date_extension.put(String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase()+"_MOC_YEAR",String.valueOf(obj[10]).toUpperCase());
			
			crEntries.put(String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase()
					+ String.valueOf(obj[6]).toUpperCase()+String.valueOf(obj[10]).toUpperCase(), ""); // Budget Extension,Additional Quantity,Liquidation
			
		/*	crEntries.put(String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase()
					+ String.valueOf(obj[6]).toUpperCase() + String.valueOf(obj[9]).toUpperCase()
					+ String.valueOf(obj[10]).toUpperCase(), "");*/
			// Date extension

			crEntries.put(String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
					+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase()
					+ String.valueOf(obj[6]).toUpperCase() + String.valueOf(obj[7]).toUpperCase(), "");
			//PROMOTION_ID-0, PROMO_ID-1, MOC_NAME-2, PPM_ACCOUNT-3
			crEntries.put(String.valueOf(obj[0]).toUpperCase() + String.valueOf(obj[2]).toUpperCase()
					+ String.valueOf(obj[3]).toUpperCase() + "_start_date",
					String.valueOf(obj[7]).toUpperCase()); // to get the start date from existing

			crEntries.put(String.valueOf(obj[0]) + String.valueOf(obj[2]).toUpperCase()
					+ String.valueOf(obj[3]).toUpperCase() + "_end_date",
					String.valueOf(obj[8]).toUpperCase());
 /*
			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
							+ String.valueOf(obj[5]).toUpperCase() + String.valueOf(obj[6]).toUpperCase(),
					String.valueOf(obj[4]).toUpperCase()); */// for Basepack Addition

			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
							+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[5]).toUpperCase(),
					String.valueOf(obj[4]).toUpperCase()); // for TOP UP
			//B.PROMOTION_ID,A.PROMO_ID,A.MOC_NAME,A.PPM_ACCOUNT,A.BASEPACK_CODE,A.CLUSTER,A.PRICE_OFF,A.START_DATE,A.END_DATE,A.PROMO_TIMEPERIOD,A.MOC_YEAR
			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[10]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
							+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[6]).toUpperCase() + String.valueOf(obj[5]).toUpperCase(),
					String.valueOf(obj[5]).toUpperCase()); // for Missing geo and basepack addition if present
			
			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[10]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
							+ String.valueOf(obj[4]).toUpperCase() + String.valueOf(obj[6]).toUpperCase(),
					String.valueOf(obj[5]).toUpperCase());  // for Missing geo
			
			//B.PROMOTION_ID,A.PROMO_ID,A.MOC_NAME,A.PPM_ACCOUNT,A.BASEPACK_CODE,A.CLUSTER,A.PRICE_OFF,A.START_DATE,A.END_DATE,A.PROMO_TIMEPERIOD,A.MOC_YEAR
			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[10]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
						+ String.valueOf(obj[5]).toUpperCase()+String.valueOf(obj[6]).toUpperCase(),
					String.valueOf(obj[4]).toUpperCase()); // Addition basepack 
			
			//PROMOTION_ID-0, PROMO_ID-1, MOC_NAME-2, PPM_ACCOUNT-3, BASEPACK_CODE-4, CLUSTER-5,PRICE_OFF-6, START_DATE-7, END_DATE-8, PROMO_TIMEPERIOD-9, MOC_YEAR -10
			
			crEntries.put(
					String.valueOf(obj[2]).toUpperCase() + String.valueOf(obj[10]).toUpperCase() + String.valueOf(obj[3]).toUpperCase()
						+ String.valueOf(obj[4]).toUpperCase()+String.valueOf(obj[5]).toUpperCase(),
					String.valueOf(obj[6]).toUpperCase()); // top up where Price off will not be checked 
			
		}
		
	}
	
	protected boolean isPromoTimeisValid(String promotime) {
		Date date = null;
		String format = "dd/MM/yyyy";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(promotime);
			if (!promotime.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}
	/**
	 * 
	 * @param d1 = entered by user
	 * @param d2 = from db
	 * @return boolean
	 */
	public boolean compareDates(String d1,String d2)
    {
        try{
        	
        	d2=d2.replaceAll("-", "-");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            
            if(date1.after(date2)){
                return true;
            }
            
            if(date1.before(date2)){
                return false;
            }

            return false;

        }
        catch(ParseException ex){
            ex.printStackTrace();
            return false;
        }
    }
	

	public boolean getFutureDatedMOC(int mocfrom_map,int moc_fromexcel)
	{
		if(mocfrom_map>moc_fromexcel)
		{
			return true;
		}
		return false;
	}
	

	
	public void getppmDescStage(Map<String, String> commanmap,String uid) {
		String ppmdesc="SELECT PPM_DESC_STAGE,CHANNEL_NAME,MOC_NAME,PPM_ACCOUNT,BASEPACK_CODE,SALES_CATEGORY FROM TBL_PROCO_PROMOTION_MASTER_TEMP_V2 WHERE USER_ID='"+uid+"'";
		List<Object[]> list=sessionFactory.getCurrentSession().createNativeQuery(ppmdesc).list();
		for(Object[] obj: list)
		{
			commanmap.put(String.valueOf(obj[1])+String.valueOf(obj[2])+String.valueOf(obj[3])+String.valueOf(obj[4]), String.valueOf(obj[0])); 
			commanmap.put(String.valueOf(obj[1])+String.valueOf(obj[2])+String.valueOf(obj[3])+String.valueOf(obj[4])+"sale", String.valueOf(obj[5])); 
		}
	}
	
	public boolean validateMod(String mod)
	{
		if(mod.equalsIgnoreCase("Ground ops in %"))
		{
			return true;
		}
		if(mod.equalsIgnoreCase("Ground Ops in Rs."))
		{
			return true;
		}
		
		return false;
	}
	
}
