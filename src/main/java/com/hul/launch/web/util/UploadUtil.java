package com.hul.launch.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;



public class UploadUtil {
	
	static Logger	logger	= Logger.getLogger(UploadUtil.class);
	
	public UploadUtil() {
		
	}
	
	public static boolean movefile(MultipartFile file, String fileName) {
		byte[] bytes;
		BufferedOutputStream bos = null;
		try {
			bytes = file.getBytes();
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			bos.write(bytes);
		} catch (IOException e) {
			logger.error("Error Occured ", e);
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (IOException e) {
				logger.error("Error Occured ", e);
			}
		}
		return true;
	}
	
	public static boolean movefileforPromotion(File file, String fileName) {
		//
		BufferedOutputStream bos = null;
		try {
			byte[] buffer = new byte[1024];
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			bos.write(buffer);
		} catch (IOException e) {
			logger.error("Error Occured ", e);
		} finally {
			try {
				bos.flush();
				bos.close();
			} catch (IOException e) {
				logger.error("Error Occured ", e);
			}
		}
		return true;
	}
	
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
	
	public static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
		}
		return null;
	}
	
	
	
	
	
	
	@SuppressWarnings("unused")
	public static String cellToString(Cell cell) {
		int type;
		Object result = null;
		type = cell.getCellType();
		
		switch (type) {
		
			case Cell.CELL_TYPE_NUMERIC: // numeric value in Excel
			case Cell.CELL_TYPE_FORMULA: // precomputed value based on formula
				result = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING: // String Value in Excel
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				result = "";
			case Cell.CELL_TYPE_BOOLEAN: // boolean value
				result: cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
			default:
				throw new RuntimeException(
						"There is no support for this type of cell");
		}
		
		return result.toString();
	}
	
	public static String getFileName(String fetchFileStr, String appender,
			String currDateTime) {
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String fileName = null;
		fileName = commUtils.getProperty(fetchFileStr);
		if (!(appender == null || appender.equals(""))) {
			fileName = fileName + "_" + currDateTime;
		} else {
			fileName = fileName+ appender + currDateTime;
		}
		return fileName;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean writeFileWithData(String absoluteFileName,
			List downloadDetails, String fileExtension) {
		ArrayList tempList = null;
		String valueForCell = null;
		BufferedWriter bufferedWriter = null;
		try {
			absoluteFileName = absoluteFileName + fileExtension;
			bufferedWriter = new BufferedWriter(new FileWriter(
					absoluteFileName, true));
			for (int i = 0; i < downloadDetails.size(); i++) {
				// Row-wise iteration to get data for one row
				tempList = (ArrayList) downloadDetails.get(i);
				if (tempList != null) {
					for (int j = 0; j < tempList.size(); j++) {
						// Column-wise iteration to populate cells
						if (j > 0) {
							bufferedWriter.write(",");
						}
						valueForCell = (String) tempList.get(j);
						if (valueForCell != null) {
							bufferedWriter.write("\"" + valueForCell + "\"");
						} else {
							bufferedWriter.write("\"\"");
						}
					}
					bufferedWriter.write("\n");
				}
			}
		} catch (IOException e) {
			logger.error("Error Occured ", e);
			return false;
		} finally {
			tempList = null;
			downloadDetails = null;
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				logger.error("Error Occured ", e);
				return false;
			}
		}
		return true;
	}
	
	
	public static String populateAppTypeToBean(Map<String, String> appTypeList,
			String selectedAppType) {
		String appTypeDecider = "";
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		if (selectedAppType.equals(commUtils.getProperty("apptype.epos"))) {
			appTypeDecider = appTypeList.get(selectedAppType);
		} else if (selectedAppType.equals(commUtils
				.getProperty("apptype.eretail"))) {
			appTypeDecider = appTypeList.get(selectedAppType);
			if (appTypeDecider.equalsIgnoreCase("E-RETAIL")) {
				appTypeDecider = "ERETAIL";
			}
		} else if (selectedAppType
				.equals(commUtils.getProperty("apptype.marg"))) {
			appTypeDecider = appTypeList.get(selectedAppType);
		} else if (selectedAppType.equals(commUtils
				.getProperty("apptype.vikrant"))) {
			appTypeDecider = appTypeList.get(selectedAppType);
		}
		return appTypeDecider;
	}
	
	public static boolean createFolder(String theFilePath) {
		boolean result = false;
		
		File directory = new File(theFilePath);
		
		if (directory.exists()) {
			return true;
		} else {
			result = directory.mkdirs();
		}
		
		return result;
	}
	

	
	   /**
     * Compress the given directory with all its files.
     */
    public static byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
    	//String zipFile = "C:\\MyFileDemoTest1.zip";
       // FileOutputStream baos = new FileOutputStream(zipFile);
        
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() + 
                "/"+ fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
    
    
    public static byte[] zipFilesNew(List<String> files, String zipRootFolder, String rootPath) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
    	//String zipFile = "C:\\MyFileDemoTest1.zip";
       // FileOutputStream baos = new FileOutputStream(zipFile);
        
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];
        
        for (String fileName : files) {
        	String filePath = zipRootFolder + File.separator + fileName.substring(5, fileName.length());
        	String zipNextEntry = filePath.substring(zipRootFolder.length(), filePath.length());
           
        	FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(zipNextEntry));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

		return baos.toByteArray();
	}

	public static boolean isNumeric(String string) {
		return string.matches("^?\\d+(\\.\\d+)?$");
	}

	@SuppressWarnings("resource")
	public static boolean writeAssetPlanningXLSFile(String filePath,Map<String, List<List<String>>> masters, String extension) throws IOException {
		FileOutputStream fileOut = null;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;

			if (masters != null && masters.size() > 0) {
				HSSFSheet sheet = wb.createSheet("ASSET");
				List<List<String>> sampleList = masters.get("ASSET");
				if (sampleList != null) {
					rowCount = 0;
					for (int r = 0; r < sampleList.size(); r++) {
						// iterating c number of columns
						List<String> al = sampleList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				HSSFSheet sheet1 = wb.createSheet("SUBCATERY_BRAND");
				List<List<String>> clusterList = masters.get("SUBCAT");
				if (clusterList != null) {
					rowCount = 0;
					for (int r = 0; r < clusterList.size(); r++) {
						// iterating c number of columns
						List<String> al = clusterList.get(r);
						HSSFRow row = sheet1.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet1.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

			}

			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	
	@SuppressWarnings("resource")
	public static boolean writeXLSFile(String filePath, List<ArrayList<String>> dataList,
			Map<String, List<List<String>>> masters, String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				List<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					sheet.setDefaultColumnStyle(c, textStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(al.get(c));
				}
			}

			if (masters != null && masters.size() > 0) {
				
				sheet = wb.createSheet("Sample");
				List<List<String>> sampleList = masters.get("SAMPLE");
				if (sampleList != null) {
					sampleList.add(0, dataList.get(0));
					rowCount = 0;
					for (int r = 0; r < sampleList.size(); r++) {
						// iterating c number of columns
						List<String> al = sampleList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				
				sheet = wb.createSheet("Masters-Cluster");
				List<List<String>> clusterList = masters.get("CLUSTER");
				if (clusterList != null) {
					rowCount = 0;
					for (int r = 0; r < clusterList.size(); r++) {
						// iterating c number of columns
						List<String> al = clusterList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Customer");
				List<List<String>> customerList = masters.get("CUSTOMER");
				if (customerList != null) {
					rowCount = 0;
					for (int r = 0; r < customerList.size(); r++) {
						// iterating c number of columns
						List<String> al = customerList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Reason");
				List<List<String>> reasonList = masters.get("REASON");
				if (reasonList != null) {
					rowCount = 0;
					for (int r = 0; r < reasonList.size(); r++) {
						// iterating c number of columns
						List<String> al = reasonList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Changes");
				List<List<String>> changeList = masters.get("CHANGE");
				if (changeList != null) {
					rowCount = 0;
					for (int r = 0; r < changeList.size(); r++) {
						// iterating c number of columns
						List<String> al = changeList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Modality");
				List<List<String>> modalityList = masters.get("MODALITY");
				if (modalityList != null) {
					rowCount = 0;
					for (int r = 0; r < modalityList.size(); r++) {
						// iterating c number of columns
						List<String> al = modalityList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

			}

			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}

	@SuppressWarnings("resource")
	public static boolean writeKamL1DepotXLSFile(String filePath, List<ArrayList<String>> dataList, String extension)
			throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("General"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				ArrayList<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					HSSFCell cell = row.createCell(c);
					if (r > 0 && (c == 5 || c == 10 || c == 11 )) {
						textStyle.setDataFormat(fmt.getFormat("0"));
						sheet.setDefaultColumnStyle(c, textStyle);
						if(!al.get(c).equals("")){
							cell.setCellValue(Double.parseDouble(al.get(c)));
						}else{
							cell.setCellValue(al.get(c));
						}
						
					} else if (r > 0 && (c == 4 || c == 9)) {
						textStyle.setDataFormat(fmt.getFormat("0.000%"));
						sheet.setDefaultColumnStyle(c, textStyle);
						if(!al.get(c).equals("")){
							cell.setCellValue(al.get(c)+"%");
						}else{
							cell.setCellValue(al.get(c));
						}
					} else {
						cell.setCellValue(al.get(c));
					}
				}
			}
			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	
	@SuppressWarnings("resource")
	public static boolean writeKamL2DepotXLSFile(String filePath, List<ArrayList<String>> dataList, String extension)
			throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("General"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				ArrayList<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					HSSFCell cell = row.createCell(c);
					if (r > 0 && (c == 5 || c==8 || c==13 || c==14)) {
						textStyle.setDataFormat(fmt.getFormat("0"));
						sheet.setDefaultColumnStyle(c, textStyle);
						if(!al.get(c).equals("")){
							cell.setCellValue(Double.parseDouble(al.get(c)));
						}else{
							cell.setCellValue(al.get(c));
						}
					} else if (r > 0 && (c == 4 || c==7 || c==12)) {
						textStyle.setDataFormat(fmt.getFormat("0.000%"));
						sheet.setDefaultColumnStyle(c, textStyle);
						if(!al.get(c).equals("")){
							cell.setCellValue(al.get(c)+"%");
						}else{
							cell.setCellValue(al.get(c));
						}
					} else {
						cell.setCellValue(al.get(c));
					}
				}
			}
			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	
	@SuppressWarnings({ "resource", "unused" })
	public static boolean writeXLSFileWithGeneralStyle(String filePath, List<ArrayList<String>> dataList,
			Map<String, List<List<String>>> masters, String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = null;
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle generalStyle = wb.createCellStyle();
			
			 Font fontName = wb.createFont();
			   fontName.setFontName("Calibri");
			   generalStyle.setFont(fontName);
			
			//textStyle.setDataFormat(fmt.getFormat("@"));
			   
			   generalStyle.setFont(fontName);
				//generalStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				//generalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				//generalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				//generalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				//generalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			int rowCount = 0;
			if (dataList != null && dataList.size() > 0) {	
				sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				List<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					sheet.setDefaultColumnStyle(c, generalStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(al.get(c));
				}
			}
		}
			
	if (masters != null && masters.size() > 0) {		

				sheet = wb.createSheet("Proposition � ASSET");
				List<List<String>> changeList = masters.get("SPL");
				if (changeList != null) {
					rowCount = 0;
					for (int r = 0; r < changeList.size(); r++) {
						// iterating c number of columns
						List<String> al = changeList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, generalStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Proposition - PRIMARY BAY BRAND ");
				List<List<String>> modalityList = masters.get("BAY");
				if (modalityList != null) {
					rowCount = 0;
					for (int r = 0; r < modalityList.size(); r++) {
						// iterating c number of columns
						List<String> al = modalityList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, generalStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

			}
			
			
			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	
	
	@SuppressWarnings("resource")
	public static boolean writeDeletePromoXLSFile(String filePath, List<ArrayList<String>> dataList,
			Map<String, List<List<String>>> masters, String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				List<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					sheet.setDefaultColumnStyle(c, textStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(al.get(c));
				}
			}

			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}

	public static int readExcelCellCount(String filePath) throws IOException{

		FileInputStream inputStream = new FileInputStream(new File(filePath));
		Workbook workbook = getWorkbook(inputStream, filePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rows = firstSheet.rowIterator();
		int cellNo = 0;
			while (rows.hasNext()) {
				Row nextRow = rows.next();
				if (nextRow.getRowNum() == 0) {
					cellNo = nextRow.getLastCellNum();
				}
			}
		return cellNo;
	}
	
	//Added by Kavitha D for promo templates starts -SPRINT 9
	@SuppressWarnings("resource")
	public static boolean writePromoXLSFile(String filePath, List<ArrayList<String>> dataList,
			Map<String, List<List<String>>> masters, String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				List<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					sheet.setDefaultColumnStyle(c, textStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(al.get(c));
				}
			}
				
				sheet = wb.createSheet("Masters-Cluster");
				List<List<String>> clusterList = masters.get("CLUSTER");
				if (clusterList != null) {
					rowCount = 0;
					for (int r = 0; r < clusterList.size(); r++) {
						// iterating c number of columns
						List<String> al = clusterList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Customer");
				List<List<String>> customerList = masters.get("CUSTOMER");
				if (customerList != null) {
					rowCount = 0;
					for (int r = 0; r < customerList.size(); r++) {
						// iterating c number of columns
						List<String> al = customerList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				
				sheet = wb.createSheet("Masters-Tdp");
				List<List<String>> tdpList = masters.get("TDP");
				if (tdpList != null) {
					rowCount = 0;
					for (int r = 0; r < tdpList.size(); r++) {
						// iterating c number of columns
						List<String> al = tdpList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				
				
				

				/*sheet = wb.createSheet("Masters-ABCREATION");
				List<List<String>> reasonList = masters.get("AB CREATION");
				if (reasonList != null) {
					rowCount = 0;
					for (int r = 0; r < reasonList.size(); r++) {
						// iterating c number of columns
						List<String> al = reasonList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
*/
				sheet = wb.createSheet("Masters-OFFERTYPE");
				List<List<String>> changeList = masters.get("OFFER TYPE");
				if (changeList != null) {
					rowCount = 0;
					for (int r = 0; r < changeList.size(); r++) {
						// iterating c number of columns
						List<String> al = changeList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Modality");
				List<List<String>> modalityList = masters.get("MODALITY");
				if (modalityList != null) {
					rowCount = 0;
					for (int r = 0; r < modalityList.size(); r++) {
						// iterating c number of columns
						List<String> al = modalityList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Channel");
				List<List<String>> channelList = masters.get("CHANNEL");
				if (channelList != null) {
					rowCount = 0;
					for (int r = 0; r < channelList.size(); r++) {
						// iterating c number of columns
						List<String> al = channelList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	//Added by Kavitha D for promo templates ends -SPRINT 9
	
	//Added by Kavitha D for promo measure template starts -SPRINT 9
	@SuppressWarnings("resource")
	public static void writeXLSFilePromo(String downloadFileName, List<String> downloadedData, Object masters,
			String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;
					if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < downloadedData.size(); c++) {
					sheet.setDefaultColumnStyle(c, textStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(downloadedData.get(c));
				}
			
		
			fileOut = new FileOutputStream(downloadFileName + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return;
	}
	

	//Added by Kavitha D for promo measure template ends-SPRINT 9
	
	//Added by Kavitha D for promo cr template starts-SPRINT 9
	
	@SuppressWarnings("resource")
	public static boolean writePromoCrXLSFile(String filePath, List<ArrayList<String>> dataList,
			Map<String, List<List<String>>> masters, String extension) throws IOException {
		String sheetName = "Sheet";// name of sheet
		FileOutputStream fileOut = null;
		int sheetCount = 1;
		boolean res = false;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
			// iterating r number of rows
			HSSFDataFormat fmt = wb.createDataFormat();
			HSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(fmt.getFormat("@"));
			int rowCount = 0;
			for (int r = 0; r < dataList.size(); r++) {
				// iterating c number of columns
				List<String> al = dataList.get(r);
				if (rowCount > 0 && rowCount % 65535 == 0) {
					sheetCount++;
					sheet = wb.createSheet(sheetName + String.valueOf(sheetCount));
					rowCount = 0;
				}
				HSSFRow row = sheet.createRow(rowCount);
				rowCount++;
				for (int c = 0; c < al.size(); c++) {
					sheet.setDefaultColumnStyle(c, textStyle);
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(al.get(c));
				}
			}
				
				sheet = wb.createSheet("Masters-Cluster");
				List<List<String>> clusterList = masters.get("CLUSTER");
				if (clusterList != null) {
					rowCount = 0;
					for (int r = 0; r < clusterList.size(); r++) {
						// iterating c number of columns
						List<String> al = clusterList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Customer");
				List<List<String>> customerList = masters.get("CUSTOMER");
				if (customerList != null) {
					rowCount = 0;
					for (int r = 0; r < customerList.size(); r++) {
						// iterating c number of columns
						List<String> al = customerList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-ABCREATION");
				List<List<String>> reasonList = masters.get("AB CREATION");
				if (reasonList != null) {
					rowCount = 0;
					for (int r = 0; r < reasonList.size(); r++) {
						// iterating c number of columns
						List<String> al = reasonList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Tdp");
				List<List<String>> tdpList = masters.get("TDP");
				if (tdpList != null) {
					rowCount = 0;
					for (int r = 0; r < tdpList.size(); r++) {
						// iterating c number of columns
						List<String> al = tdpList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				sheet = wb.createSheet("Masters-OFFERTYPE");
				List<List<String>> changeList = masters.get("OFFER TYPE");
				if (changeList != null) {
					rowCount = 0;
					for (int r = 0; r < changeList.size(); r++) {
						// iterating c number of columns
						List<String> al = changeList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Modality");
				List<List<String>> modalityList = masters.get("MODALITY");
				if (modalityList != null) {
					rowCount = 0;
					for (int r = 0; r < modalityList.size(); r++) {
						// iterating c number of columns
						List<String> al = modalityList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}

				sheet = wb.createSheet("Masters-Channel");
				List<List<String>> channelList = masters.get("CHANNEL");
				if (channelList != null) {
					rowCount = 0;
					for (int r = 0; r < channelList.size(); r++) {
						// iterating c number of columns
						List<String> al = channelList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
				
				sheet = wb.createSheet("Masters-SOLTYPES");
				List<List<String>> solList = masters.get("SOLTYPE");
				if (solList != null) {
					rowCount = 0;
					for (int r = 0; r < solList.size(); r++) {
						// iterating c number of columns
						List<String> al = solList.get(r);
						HSSFRow row = sheet.createRow(rowCount);
						rowCount++;
						for (int c = 0; c < al.size(); c++) {
							sheet.setDefaultColumnStyle(c, textStyle);
							HSSFCell cell = row.createCell(c);
							cell.setCellValue(al.get(c));
						}
					}
				}
			

			fileOut = new FileOutputStream(filePath + extension);
			// write this workbook to an Outputstream.
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			res = true;
		} catch (Exception e) {
			logger.error("Exception: ", e);
			// e.printStackTrace();
		} finally {
			if (fileOut != null) {
				fileOut.close();
			}
		}
		return res;
	}
	//Added by Kavitha D for promo cr template ends-SPRINT 9

}


		

