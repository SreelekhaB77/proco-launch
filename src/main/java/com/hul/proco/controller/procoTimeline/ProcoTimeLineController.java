package com.hul.proco.controller.procoTimeline;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;

@Controller
public class ProcoTimeLineController {
	
	
	@Autowired
	public ProcoTimeLineService procoTimeLineService;
	
	static Logger logger = Logger.getLogger(ProcoTimeLineController.class);

	
	@RequestMapping(value = "procoTimeline.htm", method = RequestMethod.GET)
	public ModelAndView mainPageForm(Model modelObj) {

		List<UserBean> userNames = procoTimeLineService.getUsers();
		List<String> lstTMELockDays = procoTimeLineService.getTMELockDays();
		List<String> lstKAMLockDays = procoTimeLineService.getKAMLockDays();
		Map<String, String> mapLocks = procoTimeLineService.getGlobalLocks();
		
		modelObj.addAttribute("userNames", userNames);
		modelObj.addAttribute("tmeLockDays", lstTMELockDays);
		modelObj.addAttribute("kamLockDays", lstKAMLockDays);
		modelObj.addAttribute("globalLocks", mapLocks); 
		return new ModelAndView("proco/proco_timeline"); 
	}
	
	
	@PostMapping(value="coeUpdateProcoTimeline.htm")
	public @ResponseBody String updateProcoLockDetails(@RequestBody String jsonProcoTimelines, Model model, HttpServletRequest request) {
		Gson gson = new Gson();
		UserBean procoTimeline = (UserBean) gson.fromJson(jsonProcoTimelines, UserBean.class);
		//System.out.println(vatTimeline);
	    boolean flag = procoTimeLineService.saveProcoTimeLines(procoTimeline);
	    if(!flag) {
			model.addAttribute("error", "Error while saving Proco Timelines");
		}else {
			model.addAttribute("success", "Proco Timelines saved successfully");
		}
		return "1";
	}
}



