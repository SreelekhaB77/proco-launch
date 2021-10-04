package com.hul.launch.controller.plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.constants.ResponseConstants;
import com.hul.launch.request.GetScLaunchDetailsRequest;
import com.hul.launch.response.GlobleResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchDpBasepackResponse;
import com.hul.launch.response.LaunchDpFinalBuildUpResponse;
import com.hul.launch.response.ListBasepackDataDpResponse;
import com.hul.launch.response.ListFinalBuildUpDataDpResponse;
import com.hul.launch.service.LaunchFinalService;
import com.hul.launch.service.LaunchServiceDp;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.promocr.PromoCrService;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@RestController
public class DpLaunchPlanController {
	Logger logger = Logger.getLogger(DpLaunchPlanController.class);

	@Autowired
	LaunchServiceDp launchServiceDp;
	
	@Autowired
	public LaunchFinalService launchFinalPlanService;
	
	@Autowired
	private PromoCrService promoCrService;

	@RequestMapping(value = "getAllCompletedLaunchDataDp.htm", method = RequestMethod.GET)
	public ModelAndView getAllCompletedLaunchData(HttpServletRequest request, Model model) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		try {
			//Harsha's implementation for logintool
			String id=(String)request.getSession().getAttribute("UserID");
			String role=(String)request.getSession().getAttribute("roleId");
			promoCrService.insertToportalUsage(id, role, "LAUNCH");
			//Harsha's Logic End's here 
			listOfLaunch = launchServiceDp.getAllCompletedLaunchData();
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			model.addAttribute("listOfLaunch", listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/dp_launchplan");
	}

	@RequestMapping(value = "getAllBasePackByLaunchIdsDp.htm", method = RequestMethod.POST)
	public String getAllBasePackByLaunchIdsDp(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		ListBasepackDataDpResponse listBasepackDataDpResponse = new ListBasepackDataDpResponse();
		List<LaunchDpBasepackResponse> listOfLaunch = new ArrayList<>();
		try {
			GetScLaunchDetailsRequest getScLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetScLaunchDetailsRequest.class);
			String launchId[] = getScLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchServiceDp.getDpBasepackData(listOfLaunchData);
			listBasepackDataDpResponse.setListLaunchDataResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_DP_BASEPACK_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_DP_BASEPACK_DATA, listBasepackDataDpResponse));
	}

	@RequestMapping(value = "getAllFinalBuildUpByLaunchIdsDp.htm", method = RequestMethod.POST)
	public String getAllFinalBuildUpByLaunchIdsDp(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		ListFinalBuildUpDataDpResponse listBuildUpDataDpResponse = new ListFinalBuildUpDataDpResponse();
		List<LaunchDpFinalBuildUpResponse> listOfLaunch = new ArrayList<>();
		try {
			GetScLaunchDetailsRequest getScLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetScLaunchDetailsRequest.class);
			String launchId[] = getScLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchServiceDp.getDpBuildUpData(listOfLaunchData);
			listBuildUpDataDpResponse.setLaunchDpFinalBuildUpResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_DP_FINAL_BUILDUP_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_DP_FINAL_BUILDUP_DATA, listBuildUpDataDpResponse));
	}
	
	@RequestMapping(value = "{launchId}/downloadFinalBuildUpTemplateDp.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadFinalBuildUpTemplate(@PathVariable("launchId") String launchId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String absoluteFilePath = "";
		InputStream is;
		String downloadLink = "";
		Gson gson = new Gson();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("FinalBuildUp.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		String userId = (String) request.getSession().getAttribute("UserID");
		String[] launchIds = launchId.split(",");
		String[] launchMoc =null;
		
		
		List<ArrayList<String>> listDownload = launchFinalPlanService.getFinalBuildUpDumpNew(userId, launchIds,launchMoc);
		
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=LaunchFinalBuildUpTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}
	// Added by harsha for excel download
	@RequestMapping(value = "{promoId}/downloadDisaggregatedByDP.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadDisaggregatedByDP(@PathVariable("promoId") String promoId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String absoluteFilePath = "";
		InputStream is;
		String downloadLink = "";
		Gson gson = new Gson();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("DisaggregatedBYDP.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		String userId = (String) request.getSession().getAttribute("UserID");
		String[] promoIds = promoId.split(",");
		
		List<ArrayList<String>> listDownload = launchFinalPlanService.getDisaggregatedByDp(promoIds);
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=DisaggregatedBYDPTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}


}