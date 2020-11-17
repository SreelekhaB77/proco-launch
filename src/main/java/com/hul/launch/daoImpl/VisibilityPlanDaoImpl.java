
package com.hul.launch.daoImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hul.launch.dao.VisibilityPlanDao;

//kiran - Replaced with ROW_NUMBER() in place of rownumber() wherever applicable
//kiran - Replaced fetch rows with LIMIT wherever applicable
@Repository
public class VisibilityPlanDaoImpl implements VisibilityPlanDao {

	static Logger logger = Logger.getLogger(VisibilityPlanDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllMoc() {
		try {
			//Garima - changes for VARCHAR_FORMAT
			//Query query = sessionFactory.getCurrentSession().createNativeQuery("select MOC from TBL_VAT_MOC_MASTER WHERE TIMESTAMP_FORMAT(MOC,'MMYYYY') >= (SELECT TIMESTAMP_FORMAT(VARCHAR_FORMAT(DATE (TIMESTAMP_FORMAT(MOC, 'MMYYYY')) - 5 MONTH , 'MMYYYY'),'MMYYYY') from TBL_VAT_MOC_MASTER where STATUS='Y' AND MOC_GROUP='GROUP_ONE' ) AND MOC_GROUP='GROUP_ONE' order by TIMESTAMP_FORMAT(MOC,'MMYYYY') LIMIT 24");
			Query query = sessionFactory.getCurrentSession().createNativeQuery("select MOC from TBL_VAT_MOC_MASTER WHERE DATE_FORMAT(STR_TO_DATE(concat('01',MOC),'%d%m%Y'), '%m%Y') >= (SELECT DATE_FORMAT(DATE_SUB(STR_TO_DATE(concat('01',MOC),'%d%m%Y'), INTERVAL 5 MONTH), '%m%Y') from TBL_VAT_MOC_MASTER where STATUS='Y' AND MOC_GROUP='GROUP_ONE' ) AND MOC_GROUP='GROUP_ONE' order by DATE_FORMAT(STR_TO_DATE(concat('01',MOC),'%d%m%Y'), '%m%Y') LIMIT 24");
			List<String> list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

