package com.hul.launch.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.dao.LoginDao;
import com.hul.launch.model.User;

@Repository
public class LoginDaoImpl implements LoginDao {

	static Logger logger = Logger.getLogger(LoginDaoImpl.class);
	
	@Autowired
	private  SessionFactory sessionFactory;
	
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User getUserById(String id) {

		Query query = sessionFactory.getCurrentSession().createNativeQuery(
				"select USERID, ACCOUNT_LOCK, ACTIVE, EMAILID, FIRSTNAME,LASTNAME, MOBILENUMBER, PASSWORD, USER_ROLE_ID,CATEGORY  FROM TBL_VAT_USER_DETAILS where USERID=:userId");
		query.setParameter("userId", id);

		List<Object[]> list =query.list();
		User user =new User();
		for (Object[] row: list) {
			user.setUserId((String)row[0]);
			user.setAccount_Lock((Integer)row[1]);
			user.setActive((String)row[2]);
			user.setEmailId((String)row[3]);
			user.setFirstName((String)row[4]);
			user.setLastName((String)row[5]);
			user.setMobileNumber((String)row[6]);
			user.setPassword((String)row[7]);
			user.setROLEID((Integer)row[8]);
			user.setCategoryName((String)row[9]);
		}
		
		return user;
	}

}
