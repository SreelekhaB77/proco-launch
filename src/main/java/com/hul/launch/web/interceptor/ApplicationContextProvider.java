package com.hul.launch.web.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext	context;
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		context = ac;
	}
}