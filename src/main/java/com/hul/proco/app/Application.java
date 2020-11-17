package com.hul.proco.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class}) public
class Application { 


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args); 
	} 
}



/*@SpringBootApplication(exclude={HibernateJpaAutoConfiguration.class}) public
 class Application implements CommandLineRunner {

 @Autowired MyService myService;


 public static void main(String[] args) {
 SpringApplication.run(Application.class, args); }

 @Override

 @Transactional public void run(String... args) throws Exception {
 //System.out.println(myDao.getBasePack("BG_BB01 : ORAL CARE"));
	 myService.testMethod1(); } }*/
