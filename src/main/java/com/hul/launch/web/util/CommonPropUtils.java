package com.hul.launch.web.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CommonPropUtils extends PropertyPlaceholderConfigurer {
	static Logger					logger		= Logger.getLogger(CommonPropUtils.class);
	private static CommonPropUtils	instance	= null;
	public static Properties		prop		= new Properties();
	static InputStream				in			= null;
	public static String			machineType	= null;
	
	protected CommonPropUtils() {
		// Exists only to defeat instantiation.
	}
	
	static {
		if (((String) System.getProperty("os.name")).startsWith("Win")) {
			machineType = GlobalConstants_en.MACHINE_TYPE_WIN;
		} else {
			machineType = GlobalConstants_en.MACHINE_TYPE_LNX;
		}
	}
	
	public static CommonPropUtils getInstance() {
		if (instance == null) {
			instance = new CommonPropUtils();
		}
		try {
			in = CommonPropUtils.class
					.getResourceAsStream("/com/hul/visibility/web/resource/ApplicationProperties.properties");
			prop.load(in);
		} catch (Exception e) {
			logger.error("Error in getInstance()", e);
		}
		return instance;
	}
	
	public String getProperty(String propertyName) {
		return prop.getProperty(propertyName);
	}
}
