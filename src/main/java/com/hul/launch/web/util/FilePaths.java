package com.hul.launch.web.util;

import java.io.File;

import org.apache.log4j.Logger;

public class FilePaths {
	private static Logger log = Logger.getLogger(FilePaths.class.getName());

	public FilePaths() {
	}

	public static String FILE_TEMPUPLOAD_PATH;
	public static String FILE_TEMPDOWNLOAD_PATH;
	public static String FILE_TEMPDOWNLOAD_PATH_STORE_PERMISSION;
	public static String FILE_TEMP_DOWNLOAD_PATH_UPDATE_STORE_PERMISSION;
	public static String TEMPLT_PATH;
	public static String IMAGE_UPLOAD_PATH;
	public static String IMAGE_DOWNLOAD_PATH;
	public static String MART_JACK_PROMO_IMAGE_PATH;
	public static String VIDEO_DOWNLOAD_PATH;
	public static String BRAND_THUBNAIL;
	public static String BRAND_ASSET;
	public static String BUNDLE_IMAGE_UPLD_PATH;
	public static String FILE_PPMDOWNLOAD_PATH;
	public static String FILE_PAID_VISIBILITY;
	public static String FILE_IMAGE_PATH;
	public static String MAIN_PATH_FOR_ALL;
	public static String KAM_UPLOAD_LETTER_FILE_PATH;
	public static String CPS_KAM_UPLOAD_FILE_PATH;
	public static String CPS_KAM_FILE_DOWNLOAD_PATH;
	public static String COE_DOWNLOAD_ZIPPED_LETTER_FILE_PATH;
	public static String LAUNCH_ANNEXURE_UPLOAD_FILE_PATH;
	public static String LAUNCH_ARTWORK_UPLOAD_FILE_PATH;
	public static String LAUNCH_MDG_UPLOAD_FILE_PATH;
	public static String LAUNCH_KAM_REQUESTS_UPLOAD;
	public static String CREATIVES_TME_FILES;
	public static String CUSTOMERPORTALSTATUSEXP;

	static {
		try {
			log.info("FilePaths - Loading File paths Details ");
			String appendString = File.separator;

			if (CommonPropUtils.machineType.equalsIgnoreCase(GlobalConstants_en.MACHINE_TYPE_WIN)) {
				MAIN_PATH_FOR_ALL = CommonPropUtils.getInstance().getProperty("COMMON_PATH_FOR_WIN");
			} else {
				MAIN_PATH_FOR_ALL = CommonPropUtils.getInstance().getProperty("COMMON_PATH_FOR_LNX");
			}
			
			CUSTOMERPORTALSTATUSEXP = MAIN_PATH_FOR_ALL + appendString + "CUSTOMER_PORTAL" + appendString;
			CREATIVES_TME_FILES = MAIN_PATH_FOR_ALL + appendString + "CREATIVE" + appendString;
			
			log.info("FilePaths - MAIN_PATH_FOR_ALL :  " + MAIN_PATH_FOR_ALL.toString());
			log.info("FilePaths - Setting File Paths Details");
			FILE_TEMPUPLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "uploads".trim() + appendString + "files"
					+ appendString;
			FILE_TEMPDOWNLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "downloads".trim() + appendString + "files"
					+ appendString;
			FILE_TEMPDOWNLOAD_PATH_STORE_PERMISSION = MAIN_PATH_FOR_ALL + appendString + "storePermission".trim()
					+ appendString + "letters" + appendString + "downloads" + appendString;
			FILE_TEMP_DOWNLOAD_PATH_UPDATE_STORE_PERMISSION = MAIN_PATH_FOR_ALL + appendString
					+ "storePermission".trim() + appendString + "update_expiry_date" + appendString + "downloads"
					+ appendString;

			IMAGE_UPLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "imageUploads".trim() + appendString + "files"
					+ appendString;
			IMAGE_DOWNLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "imageDownloads".trim() + appendString;
			VIDEO_DOWNLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "videoUpload".trim() + appendString
					+ "brandVideo".trim() + appendString;
			FILE_PPMDOWNLOAD_PATH = "//145.17.49.124/ppm_input_vat/";
			FILE_PAID_VISIBILITY = MAIN_PATH_FOR_ALL + appendString + "PAID" + appendString;
			KAM_UPLOAD_LETTER_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "storePermission".trim() + appendString
					+ "letters" + appendString;
			CPS_KAM_UPLOAD_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "cps".trim() + appendString + "uploads"
					+ appendString;
			LAUNCH_ANNEXURE_UPLOAD_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "launch".trim() + appendString
					+ "uploads" + appendString;
			LAUNCH_ARTWORK_UPLOAD_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "launch".trim() + appendString
					+ "uploads" + appendString;
			LAUNCH_MDG_UPLOAD_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "launch".trim() + appendString + "uploads"
					+ appendString;
			LAUNCH_KAM_REQUESTS_UPLOAD = MAIN_PATH_FOR_ALL + appendString + "launch".trim() + appendString
					+ "uploads" + appendString;
			CPS_KAM_FILE_DOWNLOAD_PATH = MAIN_PATH_FOR_ALL + appendString + "cps".trim() + appendString + "uploads"
					+ appendString;
			COE_DOWNLOAD_ZIPPED_LETTER_FILE_PATH = MAIN_PATH_FOR_ALL + appendString + "storePermission".trim()
					+ appendString + "letters" + appendString + "zipped" + appendString;
			FILE_IMAGE_PATH = MAIN_PATH_FOR_ALL + appendString + "IMAGE" + appendString;

			log.info("FilePaths - File paths Details - End ");
		} catch (Exception e) {
			log.info("FilePaths - Loading File paths Details - Excepiton - While Loading File Path"
					+ e.getLocalizedMessage());
		}
		File file = new File(FILE_TEMPUPLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(FILE_TEMPDOWNLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(FILE_TEMPDOWNLOAD_PATH_STORE_PERMISSION);
		if(!file.exists())
			file.mkdirs();
		file = new File(FILE_TEMP_DOWNLOAD_PATH_UPDATE_STORE_PERMISSION);
		if(!file.exists())
			file.mkdirs();
		file = new File(IMAGE_UPLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(IMAGE_DOWNLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(VIDEO_DOWNLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(FILE_PAID_VISIBILITY);
		if(!file.exists())
			file.mkdirs();
		file = new File(KAM_UPLOAD_LETTER_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(CPS_KAM_UPLOAD_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(LAUNCH_ANNEXURE_UPLOAD_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(LAUNCH_ARTWORK_UPLOAD_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(LAUNCH_MDG_UPLOAD_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(LAUNCH_KAM_REQUESTS_UPLOAD);
		if(!file.exists())
			file.mkdirs();
		file = new File(CPS_KAM_FILE_DOWNLOAD_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(COE_DOWNLOAD_ZIPPED_LETTER_FILE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(FILE_IMAGE_PATH);
		if(!file.exists())
			file.mkdirs();
		file = new File(CUSTOMERPORTALSTATUSEXP);
		if(!file.exists())
			file.mkdirs();
		file = new File(CREATIVES_TME_FILES);
		if(!file.exists())
			file.mkdirs();
	}
}
