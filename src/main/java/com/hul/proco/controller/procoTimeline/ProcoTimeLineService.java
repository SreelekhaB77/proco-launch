package com.hul.proco.controller.procoTimeline;

import java.util.List;
import java.util.Map;


public interface ProcoTimeLineService {
	
	public List<UserBean> getUsers();
	public List<String> getTMELockDays();
	public List<String> getKAMLockDays();
	public Map<String, String> getGlobalLocks();
	public boolean saveProcoTimeLines(UserBean userBean);
	

}
