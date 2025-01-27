package com.hul.proco.app;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.mtapputil.CryptoUtil;
import com.mtapputil.PropertyUtil;
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.hul" })
public class HibernateConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://" + PropertyUtil.getPropertyUtil().getProperty("dbhost") + ":"
				+ PropertyUtil.getPropertyUtil().getProperty("dbport") + "/"
				+ PropertyUtil.getPropertyUtil().getProperty("database")
				+ "?zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true");
		dataSource.setUsername(PropertyUtil.getPropertyUtil().getProperty("dbuser"));
		dataSource.setPassword(CryptoUtil.decrypt(PropertyUtil.getPropertyUtil().getProperty("dbpassword")));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.hul");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		// hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
		// hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
