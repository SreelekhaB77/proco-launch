package com.hul.launch.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtils {

	static Logger logger = Logger.getLogger(CommonUtils.class);

	public CommonUtils() {
	}

	public static final long maxFileSize = 1024 * 1024 * 2;
	public static final long maxFileSizeForProcMeasureReport = 1024 * 1024 * 4;
	public static final long  maxFileSizeForExcel = 1024 * 1024 * 25;
	public static final long  maxFileSizeForTOTExcel = 1024 * 1024 * 5;
	
	public static final long maxImgFileSize = 8388608;
	public static final int maxFileNameLen = 255;

	public static String getCurrentDateTime() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			long currentMillis = System.currentTimeMillis();
			date = new Date(currentMillis);
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static String getCurrentDateInddMMyyyy() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			long currentMillis = System.currentTimeMillis();
			date = new Date(currentMillis);
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static boolean isToday(Date date) {
		return isSameDay(date, Calendar.getInstance().getTime());
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	public static boolean checkFormatDate_YYYY_MM_DD(String strDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(strDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static String formatDate_YYYY_MM_DD(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ex = new SimpleDateFormat("dd-MM-yyyy");
		// java.util.Date date=new java.util.Date();
		String format = null;
		try {
			formatter.setLenient(false);
			ex.setLenient(false);
			Date d = ex.parse(string.trim());
			format = formatter.format(d);
			return format;
		} catch (Exception pe) {
			logger.error("Error Occured ", pe);
			return format;
		}
	}

	public static String getCurrDateTimeYYYYMMDD_HHMMSS() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			date = new Date();
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static String getCurrDateTime_YYYY_MM_DD_HH_MM_SS() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss a");
			date = new Date();
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static String getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			date = new Date();
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static String getCurrDateTime_YYYY_MM_DD_HHMMSS() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hhmmss");
			date = new Date();
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static String getCurrentDate_YYYY_MM_DD() {
		String dateString = new String();
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			date = new Date();
			dateString = dateFormat.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateString;
	}

	public static boolean isFileEmpty(MultipartFile file) {
		if (file.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFileSizeExceeds(MultipartFile file) {
		if (file.getSize() > (maxFileSize)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isMearsureReportFileSizeExceeds(MultipartFile file) {
		if (file.getSize() > (maxFileSizeForProcMeasureReport)) {
			return true;
		} else {
			return false;
		}
	}

	
	public static boolean isExcelFileSizeExceeds(MultipartFile file) {
		if (file.getSize() > (maxFileSizeForExcel)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean isTOTFileSizeExceeds(MultipartFile file) {
		if (file.getSize() > (maxFileSizeForTOTExcel)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean isProperFile(String file) {

		if (file.endsWith(".csv")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNull(String nullChkString) {
		if (nullChkString == null || nullChkString.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isImgFileSizeExceeds(MultipartFile file) {
		if (file.getSize() > (maxImgFileSize)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFileNameCharLenExceeds(MultipartFile file) {
		if (file.getOriginalFilename().length() > (maxFileNameLen)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isImgFileExtensionValid(MultipartFile file, String fileExtensions) {
		String fileExtn = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'),
				file.getOriginalFilename().length());
		if (fileExtensions.contains(fileExtn)) {
			return true;
		} else {
			return false;
		}
	}

	public static double getImgFileSizeforPromotion(File file, int exponent) {
		double fileSize = 0;
		long fileBytes = 0;
		fileBytes = file.length();
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(exponent);
		fileSize = Double.parseDouble(nf.format((fileBytes / 1024.0) / 1024.0));
		return fileSize;
	}

	public static double getImgFileSize(MultipartFile file, int exponent) {
		double fileSize = 0;
		long fileBytes = 0;
		fileBytes = file.getSize();
		NumberFormat nf = new DecimalFormat();
		nf.setMaximumFractionDigits(exponent);
		fileSize = Double.parseDouble(nf.format((fileBytes / 1024.0) / 1024.0));
		return fileSize;
	}

	public static boolean checkImgValidFileName(MultipartFile file) {
		boolean validFlag = true;
		String fileName = file.getOriginalFilename();
		String pattern = "[a-zA-Z0-9_.]*";
		if (fileName.matches(pattern)) {
			validFlag = true;
		} else {
			validFlag = false;
		}
		return validFlag;
	}

	public static boolean writeFileToPath(MultipartFile file, String destFilePath, String appendWithFile) {
		FileOutputStream imgFileOut = null;
		FileInputStream imgFilein = null;
		String finalFileName = "";
		try {
			if (!(appendWithFile == null || appendWithFile.equals(""))) {
				finalFileName = appendWithFile + file.getOriginalFilename();
			}
			imgFilein = new FileInputStream(file.getName());
			imgFileOut = new FileOutputStream(destFilePath + finalFileName);
			int read = 0;
			byte[] bytes = new byte[1024];
			try {
				while ((read = imgFilein.read(bytes)) != -1) {
					imgFileOut.write(bytes, 0, read);
				}
			} catch (IOException e) {
				logger.error("Error Occured ", e);
			}
		} catch (FileNotFoundException e) {
			logger.error("Error Occured ", e);
			return false;
		} finally {
			try {
				if (imgFilein != null) {
					imgFilein.close();
				}
				if (imgFileOut != null) {
					imgFileOut.close();
				}
			} catch (IOException e) {
				logger.error("Error Occured ", e);
				return false;
			}
		}
		return true;
	}

	public static synchronized boolean saveImage(String imageUrl, String path) throws IOException {
		URL url = new URL(imageUrl);
		String fileName = url.getFile();
		InputStream is = url.openStream();
		boolean isImageSaved = false;
		String imageName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.indexOf(";"));
		File file = new File(FilePaths.IMAGE_DOWNLOAD_PATH + path + "/" + "files/");
		if (!file.exists()) {
			file.mkdir();
		}
		String destName = FilePaths.IMAGE_DOWNLOAD_PATH + path + "/" + "files/" + imageName;
		OutputStream os = new FileOutputStream(destName);
		try {

			byte[] b = new byte[4096];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			isImageSaved = true;
		} catch (Exception e) {
			logger.error("Error Occured ", e);
			isImageSaved = false;
		} finally {
			is.close();
			os.close();
		}
		return isImageSaved;
	}

	static int startCutOff = 21;
	static int endCutOff = 20;
	static int startCutOffNextMonth = 31;
	static int startCutOffCurrentMonth = 5;
	static int endCutOffNextMonth = 31;
	static int endCutOffCurrentMonth = 5;
	static int endCutOffPreviousMonth = 5;
	static String dateDelimeter = "/";

	public static String getNumOfMOnths(String date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date usd = null;
		try {
			usd = sDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("Error Occured ", e);
		}

		// ///////////CURRRNT MONTH
		StringBuilder currMoth1date = new StringBuilder("20");
		Calendar currMonth = Calendar.getInstance();
		currMonth.setTime(usd);
		currMonth.setLenient(false);
		currMonth.add(Calendar.MONTH, 0);
		currMoth1date.append("/" + (currMonth.get(Calendar.MONTH) + 1) + "/" + currMonth.get(Calendar.YEAR));
		// System.out.println(currMoth1date.toString());

		// /////////////////LOWER MONTH
		StringBuilder currMoth = new StringBuilder("21");
		Calendar currMonth1 = Calendar.getInstance();
		currMonth1.setTime(usd);
		currMonth1.setLenient(false);
		currMonth1.add(Calendar.MONTH, 1);
		currMoth.append("/" + (currMonth1.get(Calendar.MONTH) + 1) + "/" + currMonth1.get(Calendar.YEAR));
		// System.out.println(currMoth.toString());

		String array[] = date.toString().split("/");

		if (Integer.parseInt(array[0]) <= 20) {
			return currMoth1date.toString();
		} else if (Integer.parseInt(array[0]) >= 21) {
			return currMoth.toString();
		}
		return null;
	}

	private static int differenceInMonths(Date d1, Date d2) {
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(d1);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(d2);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return diffMonth;
	}

	// get moc for proco
	public static int differenceInMonthsForProco(Date d1, Date d2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(d1);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(d2);

		StringBuilder currMoth1date = new StringBuilder("21");
		Calendar currMonth = Calendar.getInstance();
		currMonth.setTime(d2);
		currMonth.setLenient(false);
		currMonth.add(Calendar.MONTH, 0);
		currMoth1date.append("/" + (currMonth.get(Calendar.MONTH) + 1) + "/" + currMonth.get(Calendar.YEAR));
		java.util.Date planStartDate = null;
		try {
			planStartDate = sdf.parse(currMoth1date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int diffMonth = 0;
		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		if (d2.compareTo(planStartDate) >= 0) {
			diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) + 1;
		}

		else {
			diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		}
		return diffMonth;
	}

	// new - 12-2-16
	public static String getMocMonth(String startDate, String endDate) {
		// String obj[]={"20/11/2017","21/11/2016"};
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			java.util.Date usd = sdf.parse(startDate.toString());
			java.util.Date ued = sdf.parse(endDate.toString());

			if ((CommonUtils.isToday(usd) || usd.after(new Date()))
					&& (CommonUtils.isSameDate(usd, ued) || ued.after(usd))) {
				StringBuilder sb = new StringBuilder();
				String stdt = getNumOfMOnths(startDate.toString());
				// String enddt=getNumOfMOnths(endDate.toString());

				int diff = differenceInMonths(sdf.parse(stdt), sdf.parse(endDate.toString()));

				Calendar currMonthPr = null;

				if (diff >= 0) {
					for (int count = 0; count <= diff; count++) {
						currMonthPr = Calendar.getInstance();
						currMonthPr.setTime(sdf.parse(stdt));
						currMonthPr.setLenient(false);
						currMonthPr.add(Calendar.MONTH, 1);
						StringBuilder currMothPr = new StringBuilder("21");
						currMothPr.append(
								"/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
						stdt = currMothPr.toString();
						if (currMonthPr.get(Calendar.MONTH) == 0) {
							sb.append(12 + ",");
						} else {
							sb.append(currMonthPr.get(Calendar.MONTH) + ",");
						}
						// System.out.println(stdt);
					}
				} else {
					// /for(int count=0;count<=diff;count++)
					// {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
					stdt = currMothPr.toString();
					if (currMonthPr.get(Calendar.MONTH) == 0) {
						sb.append(12 + ",");
					} else {
						sb.append(currMonthPr.get(Calendar.MONTH) + ",");
					}
					// System.out.println(stdt);
					// }

				}
				return sb.substring(0, sb.length() - 1);
			}
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return null;
	}

	public static String getNumOfMOnthsForPPM(String date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date usd = null;
		try {
			usd = sDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("Error Occured ", e);
		}

		// ///////////CURRRNT MONTH
		StringBuilder currMoth1date = new StringBuilder("25");
		Calendar currMonth = Calendar.getInstance();
		currMonth.setTime(usd);
		currMonth.setLenient(false);
		currMonth.add(Calendar.MONTH, 0);
		currMoth1date.append("/" + (currMonth.get(Calendar.MONTH) + 1) + "/" + currMonth.get(Calendar.YEAR));
		// /////////////////LOWER MONTH
		StringBuilder currMoth = new StringBuilder("25");
		Calendar currMonth1 = Calendar.getInstance();
		currMonth1.setTime(usd);
		currMonth1.setLenient(false);
		currMonth1.add(Calendar.MONTH, 1);
		currMoth.append("/" + (currMonth1.get(Calendar.MONTH) + 1) + "/" + currMonth1.get(Calendar.YEAR));

		String array[] = date.toString().split("/");

		if (Integer.parseInt(array[0]) <= 24) {
			return currMoth1date.toString();
		} else if (Integer.parseInt(array[0]) >= 25) {
			return currMoth.toString();
		}
		return null;
	}

	public static String getMocMonthForPPM(String startDate, String endDate) {
		// String obj[]={"20/11/2017","21/11/2016"};
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			java.util.Date usd = sdf.parse(startDate.toString());
			java.util.Date ued = sdf.parse(endDate.toString());

			if ((CommonUtils.isToday(usd) || usd.after(new Date()))
					&& (CommonUtils.isSameDate(usd, ued) || ued.after(usd)))

			{
				StringBuilder sb = new StringBuilder();
				String stdt = getNumOfMOnths(startDate.toString());
				// String stdt=getNumOfMOnthsForPPM(startDate.toString());

				// String enddt=getNumOfMOnths(endDate.toString());

				int diff = differenceInMonths(sdf.parse(stdt), sdf.parse(endDate.toString()));

				Calendar currMonthPr = null;

				if (diff >= 0) {
					for (int count = 0; count <= diff; count++) {
						currMonthPr = Calendar.getInstance();
						currMonthPr.setTime(sdf.parse(stdt));
						currMonthPr.setLenient(false);
						currMonthPr.add(Calendar.MONTH, 1);
						StringBuilder currMothPr = new StringBuilder("21");
						currMothPr.append(
								"/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));

						stdt = currMothPr.toString();
						if ((currMonthPr.get(Calendar.MONTH) + 1) == 0) {
							sb.append(12 + ",");
						} else {
							sb.append((currMonthPr.get(Calendar.MONTH) + 1) + ",");
						}
						// System.out.println(stdt);
					}
				} else {
					// /for(int count=0;count<=diff;count++)
					// {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));

					stdt = currMothPr.toString();
					if ((currMonthPr.get(Calendar.MONTH) + 1) == 0) {
						sb.append(12 + ",");
					} else {
						sb.append((currMonthPr.get(Calendar.MONTH) + 1) + ",");
					}
					// System.out.println(stdt);
					// }

				}
				return sb.substring(0, sb.length() - 1);
			}
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return null;
	}

	//////////////////////////////////////////////////////// MOC CAL FOR KAM DASH
	//////////////////////////////////////////////////////// PENDING
	//////////////////////////////////////////////////////// PLAN//////////////////////////////////////////////////

	public static String getNumOfMOnthsForKAMDASHpendPlan(String date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date usd = null;
		try {
			usd = sDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("Error Occured ", e);
		}

		// ///////////CURRRNT MONTH
		StringBuilder currMoth1date = new StringBuilder("21");
		Calendar currMonth = Calendar.getInstance();
		currMonth.setTime(usd);
		currMonth.setLenient(false);
		currMonth.add(Calendar.MONTH, -1);
		currMoth1date.append("/" + (currMonth.get(Calendar.MONTH) + 1) + "/" + currMonth.get(Calendar.YEAR));
		// System.out.println(currMoth1date.toString());

		// /////////////////LOWER MONTH
		StringBuilder currMoth = new StringBuilder("20");
		Calendar currMonth1 = Calendar.getInstance();
		currMonth1.setTime(usd);
		currMonth1.setLenient(false);
		currMonth1.add(Calendar.MONTH, 0);
		currMoth.append("/" + (currMonth1.get(Calendar.MONTH) + 1) + "/" + currMonth1.get(Calendar.YEAR));
		// System.out.println(currMoth.toString());

		String array[] = date.toString().split("/");

		if (Integer.parseInt(array[0]) <= 20) {
			return currMoth1date.toString();
		} else if (Integer.parseInt(array[0]) >= 21) {
			return currMoth.toString();
		}
		return null;
	}

	public static String getMocMonthForKAMPendPLANmoc(String startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			StringBuilder sb = new StringBuilder();
			String stdt = getNumOfMOnthsForKAMDASHpendPlan(startDate.toString());

			int diff = differenceInMonths(sdf.parse(stdt), sdf.parse(startDate.toString()));

			Calendar currMonthPr = null;

			if (diff > 0) {
				for (int count = 0; count <= diff; count++) {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(startDate));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));

					startDate = currMothPr.toString();
					if ((currMonthPr.get(Calendar.MONTH) + 1) == 0) {
						sb.append(12);
						sb.append(currMonthPr.get(Calendar.YEAR) + ",");
					} else {
						if (((currMonthPr.get(Calendar.MONTH) + 1) + "").length() == 1) {
							sb.append("0");
						}
						sb.append((currMonthPr.get(Calendar.MONTH) + 1));
						sb.append(currMonthPr.get(Calendar.YEAR) + ",");
					}
				}
			} else if (diff == 0) {
				for (int count = 0; count <= diff; count++) {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 2);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));

					stdt = currMothPr.toString();
					if ((currMonthPr.get(Calendar.MONTH) + 1) == 0) {
						sb.append(12);
						sb.append(currMonthPr.get(Calendar.YEAR) + ",");
					} else {
						if (((currMonthPr.get(Calendar.MONTH) + 1) + "").length() == 1) {
							sb.append("0");
						}
						sb.append((currMonthPr.get(Calendar.MONTH) + 1));
						sb.append(currMonthPr.get(Calendar.YEAR) + ",");
					}
				}
			} else {
				currMonthPr = Calendar.getInstance();
				currMonthPr.setTime(sdf.parse(stdt));
				currMonthPr.setLenient(false);
				currMonthPr.add(Calendar.MONTH, 1);
				StringBuilder currMothPr = new StringBuilder("21");
				currMothPr.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));

				stdt = currMothPr.toString();
				if ((currMonthPr.get(Calendar.MONTH) + 1) == 0) {
					sb.append(12);
					sb.append(currMonthPr.get(Calendar.YEAR) + ",");
				} else {
					if (((currMonthPr.get(Calendar.MONTH) + 1) + "").length() == 1) {
						sb.append("0");
					}
					sb.append((currMonthPr.get(Calendar.MONTH) + 1));
					sb.append(currMonthPr.get(Calendar.YEAR) + ",");
				}
			}
			return sb.substring(0, sb.length() - 1);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return null;
	}

	public static String[] converToQuery(String str1) {
		String str[] = (str1.split(","));
		return str;
	}

	public static String converToQueryWithApostrophe(String str1) {
		str1 = str1.replace("'", "''");
		str1 = str1.replace(",", "','");

		return "'" + str1 + "'";
	}

	private static final long LIMIT = 100000000L;
	private static long last = 0;

	// 10 digits.

	public static String getUniqueVisiRefId() {
		String id = String.valueOf(getID());
		while (id.length() < 8) {
			id = String.valueOf(getID());
		}
		return "MT".concat(id);
	}

	public synchronized static long getID() {
		// 10 digits.
		Long id = System.currentTimeMillis() % LIMIT;
		int len = String.valueOf(id).length();
		if (len != 8) {
			id = System.currentTimeMillis() % 1000000000L;
			String str = String.valueOf(id).substring(0, String.valueOf(id).length() - 1);
			return Long.valueOf(str).longValue();
		}

		if (id <= last) {
			id = (last + 1) % LIMIT;
		}
		return last = id;
	}

	public static String urlEncode(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8");
	}

	public static boolean isSameDate(String date1) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		// dateFormat.format(date);
		return (dateFormat.format(date)).equals(date1);
	}

	public static boolean isSameDate(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date2);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);

		return calendar.getTime().equals(calendar1.getTime());
	}

	public static BigDecimal toBigDecimal(String args) throws ParseException {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		String pattern = "#.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(args);
		return bigDecimal.setScale(2, RoundingMode.CEILING);
	}

	public static String[] splitFirst(String source, String splitter) {
		// hold the results as we find them
		Vector<String> rv = new Vector<String>();
		int last = 0;
		int next = 0;

		// find first splitter in source
		next = source.indexOf(splitter, last);
		if (next != -1) {
			// isolate from last thru before next
			rv.add(source.substring(last, next));
			last = next + splitter.length();
		}

		if (last < source.length()) {
			rv.add(source.substring(last, source.length()));
		}

		// convert to array
		return (String[]) rv.toArray(new String[rv.size()]);
	}

	public static boolean isInteger(String str) {

		try {
			Integer.parseInt(str);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}

	}

	public static String getMocMonthYear(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//logger.info("startDate: " + startDate + " endDate: " + endDate);
		sdf.setLenient(false);
		try {
			java.util.Date usd = sdf.parse(startDate.toString());
			java.util.Date ued = sdf.parse(endDate.toString());

			if ((CommonUtils.isToday(usd) || usd.after(new Date()) || usd.before(new Date()))
					&& (CommonUtils.isSameDate(usd, ued) || ued.after(usd))) {
				StringBuilder sb = new StringBuilder();
				String stdt = CommonUtils.getNumOfMOnths(startDate.toString());
				int diff = CommonUtils.differenceInMonthsForProco(sdf.parse(stdt), sdf.parse(endDate.toString()));

				Calendar currMonthPr = null;

				if (diff >= 0) {
					for (int count = 0; count <= diff; count++) {
						currMonthPr = Calendar.getInstance();
						currMonthPr.setTime(sdf.parse(stdt));
						currMonthPr.setLenient(false);
						currMonthPr.add(Calendar.MONTH, 1);

						StringBuilder currMothPr = new StringBuilder("21");
						currMothPr.append(
								"/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
						stdt = currMothPr.toString();
						if (currMonthPr.get(Calendar.MONTH) == 0) {
							sb.append(12);
							sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
						} else {
							if (currMonthPr.get(Calendar.MONTH) < 10) {
								sb.append("0" + currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							} else {
								sb.append(currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							}
						}
					}
				} else {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
					stdt = currMothPr.toString();
					if (currMonthPr.get(Calendar.MONTH) == 0) {
						sb.append(12);
						sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
					} else {
						if (currMonthPr.get(Calendar.MONTH) < 10) {
							sb.append("0" + currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						} else {
							sb.append(currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						}

					}

				}
				if (sb.length() - 1 == 7) {
					return sb.substring(1, sb.length() - 1);
				} else {
					return sb.substring(0, sb.length() - 1);
				}
			}
		} catch (Exception e) {
			logger.error("Error Occured ", e);
			return null;
		}
		return null;
		
		// OLD MOC LOGIC
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			java.util.Date usd = sdf.parse(startDate.toString());
			java.util.Date ued = sdf.parse(endDate.toString());

			if ((CommonUtils.isToday(usd) || usd.after(new Date()) || usd.before(new Date()))
					&& (CommonUtils.isSameDate(usd, ued) || ued.after(usd))) {
				StringBuilder sb = new StringBuilder();
				String stdt = CommonUtils.getNumOfMOnths(startDate.toString());

				int diff = CommonUtils.differenceInMonths(sdf.parse(stdt), sdf.parse(endDate.toString()));

				Calendar currMonthPr = null;

				if (diff >= 0) {
					for (int count = 0; count <= diff; count++) {
						currMonthPr = Calendar.getInstance();
						currMonthPr.setTime(sdf.parse(stdt));
						currMonthPr.setLenient(false);
						currMonthPr.add(Calendar.MONTH, 1);

						StringBuilder currMothPr = new StringBuilder("21");
						currMothPr.append(
								"/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
						stdt = currMothPr.toString();
						if (currMonthPr.get(Calendar.MONTH) == 0) {
							sb.append(12);
							sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
						} else {
							if (currMonthPr.get(Calendar.MONTH) < 10) {
								sb.append("0" + currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							} else {
								sb.append(currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							}
						}
					}
				} else {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
					stdt = currMothPr.toString();
					if (currMonthPr.get(Calendar.MONTH) == 0) {
						sb.append(12);
						sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
					} else {
						if (currMonthPr.get(Calendar.MONTH) < 10) {
							sb.append("0" + currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						} else {
							sb.append(currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						}

					}

				}
				if (sb.length() - 1 == 7) {
					return sb.substring(1, sb.length() - 1);
				} else {
					return sb.substring(0, sb.length() - 1);
				}
			}
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return null;*/
	}

	// get getMocMonthYear for proco
	public static String getMocMonthYearForProco(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//logger.info("startDate: " + startDate + " endDate: " + endDate);
		sdf.setLenient(false);
		try {
			java.util.Date usd = sdf.parse(startDate.toString());
			java.util.Date ued = sdf.parse(endDate.toString());

			if ((CommonUtils.isToday(usd) || usd.after(new Date()) || usd.before(new Date()))
					&& (CommonUtils.isSameDate(usd, ued) || ued.after(usd))) {
				StringBuilder sb = new StringBuilder();
				String stdt = CommonUtils.getNumOfMOnths(startDate.toString());
				int diff = CommonUtils.differenceInMonthsForProco(sdf.parse(stdt), sdf.parse(endDate.toString()));

				Calendar currMonthPr = null;

				if (diff >= 0) {
					for (int count = 0; count <= diff; count++) {
						currMonthPr = Calendar.getInstance();
						currMonthPr.setTime(sdf.parse(stdt));
						currMonthPr.setLenient(false);
						currMonthPr.add(Calendar.MONTH, 1);

						StringBuilder currMothPr = new StringBuilder("21");
						currMothPr.append(
								"/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
						stdt = currMothPr.toString();
						if (currMonthPr.get(Calendar.MONTH) == 0) {
							sb.append(12);
							sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
						} else {
							if (currMonthPr.get(Calendar.MONTH) < 10) {
								sb.append("0" + currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							} else {
								sb.append(currMonthPr.get(Calendar.MONTH));
								sb.append(currMonthPr.get(Calendar.YEAR) + ",");
							}
						}
					}
				} else {
					currMonthPr = Calendar.getInstance();
					currMonthPr.setTime(sdf.parse(stdt));
					currMonthPr.setLenient(false);
					currMonthPr.add(Calendar.MONTH, 1);
					StringBuilder currMothPr = new StringBuilder("21");
					currMothPr
							.append("/" + (currMonthPr.get(Calendar.MONTH) + 1) + "/" + currMonthPr.get(Calendar.YEAR));
					stdt = currMothPr.toString();
					if (currMonthPr.get(Calendar.MONTH) == 0) {
						sb.append(12);
						sb.append(currMonthPr.get(Calendar.YEAR) - 1 + ",");
					} else {
						if (currMonthPr.get(Calendar.MONTH) < 10) {
							sb.append("0" + currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						} else {
							sb.append(currMonthPr.get(Calendar.MONTH));
							sb.append(currMonthPr.get(Calendar.YEAR) + ",");
						}

					}

				}
				if (sb.length() - 1 == 7) {
					return sb.substring(1, sb.length() - 1);
				} else {
					return sb.substring(0, sb.length() - 1);
				}
			}
		} catch (Exception e) {
			logger.error("Error Occured ", e);
			return null;
		}
		return null;
	}

	// getList of moc with year for moc master table
	public static List<String> getListOFMOC(int month, int year) {
		List<String> list = new ArrayList<String>();
		int k = month;
		String str = "";
		for (int i = 0; i <= 12 - month; i++) {

			if (k < 10)
				str = "0".concat(Integer.toString(k)).concat(Integer.toString(year));
			else
				str = (Integer.toString(k)).concat(Integer.toString(year));
			k++;
			list.add(str);
		}

		String str2 = "";
		for (int j = 1; j < (month); j++) {
			if (j < 10)
				str2 = "0".concat(Integer.toString(j)).concat(Integer.toString(year + 1));
			else
				str2 = (Integer.toString(j)).concat(Integer.toString(year + 1));
			list.add(str2);
		}
		return list;
	}

	public static Date dateFromater(String date) {
		Date dateVal = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = date;
		try {
			dateVal = formatter.parse(dateInString);
		} catch (ParseException e) {
			logger.error("Error Occured ", e);
		}
		return dateVal;
	}

	public static String getDateInMmDdYyyyFormat() {
		String dateVal = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date date = new Date();
			dateVal = formatter.format(date);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateVal;
	}
	
	public static String getDateInYyyyMmDdFormat(String date) {
		String dateVal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.setLenient(false);
			formatter.setLenient(false);
			Date parse = sdf.parse(date);
			dateVal = formatter.format(parse);
		} catch (Exception e) {
			logger.error("Error Occured ", e);
		}
		return dateVal;
	}

	public static boolean zipMultiplefile(List<String> srcFiles, String zippedPath) throws IOException {
		logger.info("START :: CommonUtils.zipMultiplefile()");
		FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
		try {
			fos = new FileOutputStream(zippedPath);
			zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
				
			}
		} catch (FileNotFoundException e) {
			logger.error("ERROR :: " + e);
			return false;
		} catch (IOException e) {
			logger.error("ERROR :: " + e);
			return false;
		} finally {
			try {
				if(zipOut!=null ) {
					zipOut.close();
				}
				if (fos != null)
					fos.close();
			} catch (Exception ex) {
				logger.error("ERROR :: " + ex);
			}
		}
		logger.info("END :: CommonUtils.zipMultiplefile()");
		return true;
	}
	
	public static long getDiffInDaysBetweenCurrentAndGivenDate(String date) {
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date startDateObj = sdFormat.parse(date);
			Date endDate = new Date();
			long timeDiff = startDateObj.getTime() - endDate.getTime();
			long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
			return daysDiff;
		} catch (Exception e) {
			logger.debug("Exception:",e);
		}
		return 0;
	}
	
	public static String arrayToString(String[] theAray) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < theAray.length; i++) {
			String item = theAray[i];
			if(item!=null && !item.trim().equals("") && !item.trim().equals(",")) {
				sb.append(item);
			}
			if (i < theAray.length-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	} 
	
	public static String getDateinDDMMYYYFormat(String date) {
		String date2=null;
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		    date2=sdf.format(sdf2.parse(date));
		} catch (ParseException e) {
		   logger.debug("Exception: ",e);
		   return null;
		}
		return date2;
	}
	
	public synchronized static String generateTID(String moc) {
		Random random=new Random();
		String id = String.format("%04d", random.nextInt(10000));
		return "CD"+moc+id;
	}
	
	public static List<String> stringToList(String input){
		List<String> list=new ArrayList<>();
		if(input!=null) {
			String[] split = input.split(",");
			list=Arrays.asList(split);
		}
		return list;
	}
}
