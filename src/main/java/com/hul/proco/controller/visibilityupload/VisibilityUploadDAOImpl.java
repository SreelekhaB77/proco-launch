package com.hul.proco.controller.visibilityupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VisibilityUploadDAOImpl implements VisibilityUploadDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	static Logger logger = Logger.getLogger(VisibilityBean.class);

	private static String SQL_QUERY_INSERT_INTO_PROCO_VISIBILITY_MASTER_TEMP = "INSERT INTO TBL_PROCO_VISIBILITY_MASTER_TEMP"
			+ "(VISI_REF_NO,START_DATE,END_DATE,MOC,HFS_CONNECTIVITY,NEW_CONTINUED,MADE_BY,ACCOUNT_NAME,SPLIT_REQUIRE,"
			+ "PPM_ACCOUNT_NAME,DESCRIPTION_1,PPM_DESC,REGION,STATE,CITY,BASEPACK,BASEPACK_DESC,VISIBILITY_DESC,ASSET_DESC,"
			+ "ASSET_TYPE,ASSET_REMARK,POP_CLASS,UNIT_PER_STORE,NO_OF_STORES,AMOUNT_PER_STORE_PER_MOC,AMOUNT_PER_BASEPACK_PER_MOC,"
			+ "COMMENTS,HHT_TRACKING,CATEGORY,MIGRATED_CATEGORY,SUB_ELEMENTS,MBQ,BRAND,TOTAL_NO_OF_ASSET,VISIBILITY_AMOUNT,"
			+ "OUTLET_CODE,OUTLET_NAME,MAPPED_POP_CLASS,STATUS,DATE_OF_CREATION,LAST_EDITED,CLASSIFICATION,EDIT_DELETE_REASON,"
			+ "VISIBILITY_PAYOUT_CODE,USER_ID)"
			+ "VALUES" + "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20,?21,?22,?23,?24,"
			+ "?25,?26,?27,?28,?29,?30,?31,?32,?33,?34,?35,?36,?37,?38,?39,?40,?41,?42,?43,?44,?45)";

	//Kajal G Added for SPRINT 12
	@Override
	public String uploadVisibilityData(VisibilityBean[] beanArray, String userId) throws Exception {
		
		Query queryToDelete = sessionFactory.getCurrentSession()
				.createNativeQuery("DELETE from TBL_PROCO_VISIBILITY_MASTER_TEMP where USER_ID=:userId");
		queryToDelete.setString("userId", userId);
		queryToDelete.executeUpdate();

		int flag = 0;
		int global = 0;
		try {
			Query query = sessionFactory.getCurrentSession().createNativeQuery(SQL_QUERY_INSERT_INTO_PROCO_VISIBILITY_MASTER_TEMP);
			
			for (VisibilityBean bean : beanArray) {		
				
				SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
			    sdfrmt.setLenient(false);
			    try
			    {
			        Date startDate = sdfrmt.parse(bean.getStart_date()); 
			        Date endDate = sdfrmt.parse(bean.getEnd_date());
			    }
			    catch (ParseException e)
			    {
			        return "START_DATE_AND_END_DATE_ERROR";
			    }
			    
				query.setString(1, bean.getVisi_ref_no());
				query.setString(2, bean.getStart_date());
				query.setString(3, bean.getEnd_date());
				query.setString(4, bean.getMoc());
				query.setString(5, bean.getHfs_connectivity());
				query.setString(6, bean.getNew_continued());
				query.setString(7, bean.getMade_by());
				query.setString(8, bean.getAccount_name());
				query.setString(9, bean.getSplit_require());
				query.setString(10, bean.getPpm_account_name());
				query.setString(11, bean.getDesc_1());
				query.setString(12, bean.getPpm_desc());
				query.setString(13, bean.getRegion());
				query.setString(14, bean.getState());
				query.setString(15, bean.getCity());
				query.setString(16, bean.getBasepack());
				query.setString(17, bean.getBasepack_desc());
				query.setString(18, bean.getVisibility_desc());
				query.setString(19, bean.getAsset_desc());
				query.setString(20, bean.getAsset_type());
				query.setString(21, bean.getAsset_remark());
				query.setString(22, bean.getPop_class());
				query.setString(23, bean.getUnit_per_store());
				query.setString(24, bean.getNo_of_stores());
				query.setString(25, bean.getAmount_per_store_per_moc());
				query.setString(26, bean.getAmount_per_basepack_per_moc());
				query.setString(27, bean.getComments());
				query.setString(28, bean.getHht_tracking());
				query.setString(29, bean.getCategory());
				query.setString(30, bean.getMigrated_category());
				query.setString(31, bean.getSub_elements());
				query.setString(32, bean.getMbq());
				query.setString(33, bean.getBrand());
				query.setString(34, bean.getTotal_no_of_asset());
				query.setString(35, bean.getVisibility_amount());
				query.setString(36, bean.getOutlet_code());
				query.setString(37, bean.getOutlet_name());
				query.setString(38, bean.getMapped_pop_class());
				query.setString(39, bean.getStatus());
				query.setString(40, bean.getDate_of_creation());
				query.setString(41, bean.getLast_edited());
				query.setString(42, bean.getClassification());
				query.setString(43, bean.getEdit_delete_reason());
				query.setString(44, bean.getVisibility_payout_code());
				query.setString(45, userId);
				
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
			String insertString= "INSERT INTO TBL_PROCO_VISIBILITY_MASTER (VISI_REF_NO,START_DATE,END_DATE,MOC,"
					+ "HFS_CONNECTIVITY,NEW_CONTINUED,MADE_BY,ACCOUNT_NAME,SPLIT_REQUIRE,PPM_ACCOUNT_NAME,DESCRIPTION_1,"
					+ "PPM_DESC,REGION,STATE,CITY,BASEPACK,BASEPACK_DESC,VISIBILITY_DESC,ASSET_DESC,ASSET_TYPE,"
					+ "ASSET_REMARK,POP_CLASS,UNIT_PER_STORE,NO_OF_STORES,AMOUNT_PER_STORE_PER_MOC,AMOUNT_PER_BASEPACK_PER_MOC,"
					+ "COMMENTS,HHT_TRACKING,CATEGORY,MIGRATED_CATEGORY,SUB_ELEMENTS,MBQ,BRAND,TOTAL_NO_OF_ASSET,VISIBILITY_AMOUNT,"
					+ "OUTLET_CODE,OUTLET_NAME,MAPPED_POP_CLASS,STATUS,DATE_OF_CREATION,LAST_EDITED,CLASSIFICATION,EDIT_DELETE_REASON,"
					+ "VISIBILITY_PAYOUT_CODE,USER_ID) SELECT VISI_REF_NO,START_DATE,END_DATE,MOC,HFS_CONNECTIVITY,NEW_CONTINUED,MADE_BY,"
					+ "ACCOUNT_NAME,SPLIT_REQUIRE,PPM_ACCOUNT_NAME,DESCRIPTION_1,PPM_DESC,REGION,STATE,CITY,BASEPACK,BASEPACK_DESC,"
					+ "VISIBILITY_DESC,ASSET_DESC,ASSET_TYPE,ASSET_REMARK,POP_CLASS,UNIT_PER_STORE,NO_OF_STORES,AMOUNT_PER_STORE_PER_MOC,"
					+ "AMOUNT_PER_BASEPACK_PER_MOC,COMMENTS,HHT_TRACKING,CATEGORY,MIGRATED_CATEGORY,SUB_ELEMENTS,MBQ,BRAND,TOTAL_NO_OF_ASSET,"
					+ "VISIBILITY_AMOUNT,OUTLET_CODE,OUTLET_NAME,MAPPED_POP_CLASS,STATUS,DATE_OF_CREATION,LAST_EDITED,CLASSIFICATION,EDIT_DELETE_REASON,"
					+ "VISIBILITY_PAYOUT_CODE,USER_ID FROM TBL_PROCO_VISIBILITY_MASTER_TEMP WHERE USER_ID='" + uid + "'";
			
			sessionFactory.getCurrentSession().createNativeQuery(insertString).executeUpdate();
		}catch (Exception e) {
			 logger.debug("Exception:", e);
			 throw new Exception();
		}
		

	}

	//Kajal G Added for SPRINT 12
	@Override
	public List<VisibilityBean> readVisibilityBean(String excelFilePath) throws IOException {
		
		List<VisibilityBean> list = new ArrayList<VisibilityBean>();
		FileInputStream inputStream = new FileInputStream(new File(	excelFilePath));
		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		int noOfColumns = firstSheet.getRow(0).getLastCellNum();
		Iterator<Row> rows = firstSheet.rowIterator();
		if (noOfColumns == 44) {
			while (rows.hasNext()) {

				Row nextRow = rows.next();
				if (nextRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				VisibilityBean visibean = new VisibilityBean();
				while (cellIterator.hasNext()) {

					Cell nextCell = cellIterator.next();
					DataFormatter formatter = new DataFormatter();
					String valueOfCell = formatter.formatCellValue(nextCell);
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

						case 0: 
							visibean.setVisi_ref_no(valueOfCell);
							break;
						case 1: 
							try {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									sdf.setLenient(false);
									Date date = sdf.parse(nextCell.getDateCellValue().toString());
									sdf = new SimpleDateFormat("MM/dd/yyyy");
									visibean.setStart_date(sdf.format(date).toString());
								} catch (Exception e) {
									String stdate = nextCell.toString();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
									sdf.setLenient(false);
									Date date = sdf.parse(stdate);
									sdf = new SimpleDateFormat("MM/dd/yyyy");
									visibean.setStart_date(sdf.format(date).toString());

								}
							}catch (Exception e) {
								visibean.setStart_date(nextCell.toString().trim().replace('-', '/'));
						 	}
							break;
						case 2: 
							try {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									sdf.setLenient(false);
									Date date = sdf.parse(nextCell.getDateCellValue().toString());
									sdf = new SimpleDateFormat("MM/dd/yyyy");
									visibean.setEnd_date(sdf.format(date).toString());
								} catch (Exception e) {
									String stdate = nextCell.toString();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
									sdf.setLenient(false);
									Date date = sdf.parse(stdate);
									sdf = new SimpleDateFormat("MM/dd/yyyy");
									visibean.setEnd_date(sdf.format(date).toString());

								}
							}catch (Exception e) {
								visibean.setEnd_date(nextCell.toString().trim().replace('-', '/'));
						 	}
							break;
						case 3: 
							visibean.setMoc(valueOfCell);
							break;
						case 4: 
							visibean.setHfs_connectivity(valueOfCell);
							break;
						case 5: 
							visibean.setNew_continued(valueOfCell);
							break;
						case 6: 
							visibean.setMade_by(valueOfCell);
							break;
						case 7: 
							visibean.setAccount_name(valueOfCell);
							break;
						case 8: 
							visibean.setSplit_require(valueOfCell);
							break;
						case 9: 
							visibean.setPpm_account_name(valueOfCell);
							break;
						case 10: 
							visibean.setDesc_1(valueOfCell);
							break;
						case 11: 
							visibean.setPpm_desc(valueOfCell);
							break;
						case 12: 
							visibean.setRegion(valueOfCell);
							break;
						case 13: 
							visibean.setState(valueOfCell);
							break;
						case 14: 
							visibean.setCity(valueOfCell);
							break;	
						case 15: 
							visibean.setBasepack(valueOfCell);
							break;
						case 16: 
							visibean.setBasepack_desc(valueOfCell);
							break;	
						case 17: 
							visibean.setVisibility_desc(valueOfCell);
							break;
						case 18: 
							visibean.setAsset_desc(valueOfCell);
							break;
						case 19: 
							visibean.setAsset_type(valueOfCell);
							break;
						case 20: 
							visibean.setAsset_remark(valueOfCell);
							break;
						case 21: 
							visibean.setPop_class(valueOfCell);
							break;						
						case 22: 
							visibean.setUnit_per_store(valueOfCell);
							break;
						case 23: 
							visibean.setNo_of_stores(valueOfCell);
							break;	
						case 24:
							visibean.setAmount_per_store_per_moc(valueOfCell);
							break;
						case 25:
							visibean.setAmount_per_basepack_per_moc(valueOfCell);
							break;
						case 26:
							visibean.setComments(valueOfCell);
							break;
						case 27:
							visibean.setHht_tracking(valueOfCell);
							break;
						case 28:
							visibean.setCategory(valueOfCell);
							break;
						case 29:
							visibean.setMigrated_category(valueOfCell);
							break;
						case 30:
							visibean.setSub_elements(valueOfCell);
							break;
						case 31:
							visibean.setMbq(valueOfCell);
							break;
						case 32:
							visibean.setBrand(valueOfCell);
							break;	
						case 33:
							visibean.setTotal_no_of_asset(valueOfCell);
							break;	
						case 34: 
							visibean.setVisibility_amount(valueOfCell);
							break;
						case 35: 
							visibean.setOutlet_code(valueOfCell);
							break;
						case 36: 
							visibean.setOutlet_name(valueOfCell);
							break;	
						case 37: 
							visibean.setMapped_pop_class(valueOfCell);
							break;						
						case 38: 
							visibean.setStatus(valueOfCell);
							break;						
						case 39: 
							visibean.setDate_of_creation(valueOfCell);
							break;						
						case 40: 
							visibean.setLast_edited(valueOfCell);
							break;						
						case 41: 
							visibean.setClassification(valueOfCell);
							break;						
						case 42: 
							visibean.setEdit_delete_reason(valueOfCell);
							break;						
						case 43: 
							visibean.setVisibility_payout_code(valueOfCell);
							break;						
						}
					}
					list.add(visibean);
				}
			}
		}
		return list;
		
	}

	//Kajal G Added for SPRINT 12
	public static Workbook getWorkbook(FileInputStream inputStream,
			String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException(
					"The specified file is not Excel file");
		}
		return workbook;
	}
}
