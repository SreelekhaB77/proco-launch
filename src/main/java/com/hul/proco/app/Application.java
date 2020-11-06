package com.hul.proco.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


  @SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class}) public
  class Application { //extends SpringBootServletInitializer {
  
  
		/*
		 * @Override protected SpringApplicationBuilder
		 * configure(SpringApplicationBuilder application) { return
		 * application.sources(Application.class); }
		 */
  
  public static void main(String[] args) {
  SpringApplication.run(Application.class, args); } }
 

/*
 * @SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class}) public
 * class Application implements CommandLineRunner {
 * 
 * @Autowired MyDao myDao;
 * 
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(Application.class, args); }
 * 
 * @Override
 * 
 * @Transactional public void run(String... args) throws Exception {
 * //System.out.println(myDao.getBasePack("BG_BB01 : ORAL CARE"));
 * myDao.testMethod1(); } }
 */