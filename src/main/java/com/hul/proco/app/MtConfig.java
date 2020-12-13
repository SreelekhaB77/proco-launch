package com.hul.proco.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hul.launch.web.interceptor.SessionInterceptor;

@Configuration
public class MtConfig extends WebMvcConfigurerAdapter{
	
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionInterceptor());
	}
}
