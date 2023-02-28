package com.hul.proco.controller.procoTimeline;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ProcoTimeLineService")
@Transactional
public class ProcoTimeLineServiceImpl implements ProcoTimeLineService {
	
	@Autowired
	public ProcoTimeLineDao procoTimeLineDao;

	@Override
	public List<UserBean> getUsers() {
		return procoTimeLineDao.getUsers();
	}

	@Override
	public List<String> getTMELockDays() {
		return procoTimeLineDao.getTMELockDays();
	}

	@Override
	public List<String> getKAMLockDays() {
		return procoTimeLineDao.getKAMLockDays();
	}

	@Override
	public Map<String, String> getGlobalLocks() {
		return procoTimeLineDao.getGlobalLocks();
	}

	@Override
	public boolean saveProcoTimeLines(UserBean userBean) {
		return procoTimeLineDao.saveProcoTimeLines(userBean);
	}
	
	

}
