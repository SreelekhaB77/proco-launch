package com.hul.launch.seviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hul.launch.dao.VisibilityPlanDao;
import com.hul.launch.service.VisibilityPlanService;

@Service("VisibilityPlanService")
@Transactional()
public class VisibilityPlanServiceImpl implements VisibilityPlanService {
	
	@Autowired
	private VisibilityPlanDao	visibilityPlanDao;
	
	static Logger				logger	= Logger.getLogger(VisibilityPlanServiceImpl.class);
	


	@Override
	public List<String> getAllMoc() {
		return visibilityPlanDao.getAllMoc();
	}

	

}
