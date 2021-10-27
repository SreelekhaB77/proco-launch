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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.constants.ResponseConstants;
import com.hul.launch.model.LaunchMstnClearance;
import com.hul.launch.request.GetScLaunchDetailsRequest;
import com.hul.launch.request.RequestMstnClearanceList;
import com.hul.launch.response.GlobleResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchScBasepackResponse;
import com.hul.launch.response.LaunchScFinalBuildUpResponse;
import com.hul.launch.response.LaunchScMstnClearanceResponse;
import com.hul.launch.response.ListBasepackDataScResponse;
import com.hul.launch.response.ListFinalBuildUpDataScResponse;
import com.hul.launch.response.ListMstnClearanceScResponse;
import com.hul.launch.service.LaunchFinalService;
import com.hul.launch.service.LaunchServiceSc;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.promocr.PromoCrService;
import com.hul.proco.excelreader.exom.ExOM;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@RestController
public class ScLaunchPlanController {
	Logger logger = Logger.getLogger(ScLaunchPlanController.class);

	@Autowired
	LaunchServiceSc launchServiceSc;

	@Autowired
	public LaunchFinalService launchFinalPlanService;
	
	@Autowired
	private PromoCrService promoCrService;

	@RequestMapping(value = "getAllCompletedLaunchDataSc.htm", method = RequestMethod.GET)
	public ModelAndView getAllCompletedLaunchData(HttpServletRequest request, Model model) {
		//Q2 sprint kavitha
		String scMoc = "All";
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		try {
			
			//Harsha's implementation for logintool
			String id=(String)request.getSession().getAttribute("UserID");
			String role=(String)request.getSession().getAttribute("roleId");
			promoCrService.insertToportalUsage(id, role, "LAUNCH");
			//Harsha's Logic End's here 
			
			//listOfLaunch = launchServiceSc.getAllCompletedLaunchData();
			listOfLaunch = launchServiceSc.getAllCompletedLaunchData(scMoc);
			List<String> scMoclist=launchServiceSc.getAllMoc();
			model.addAttribute("scMoclist",scMoclist);
			
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			model.addAttribute("listOfLaunch", listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/sc_launchplan");
	}
	
	//Q2 Sprint 2021 feb kavitha
		@RequestMapping(value = "getAllCompletedLaunchScData.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
		public @ResponseBody String getAllCompletedLaunchScData(HttpServletRequest request, Model model,
				@RequestParam("sCMoc") String sCMoc) {
			List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
			try {
				String userId = (String) request.getSession().getAttribute("UserID");
				listOfLaunch = launchServiceSc.getAllCompletedLaunchData(sCMoc);
				
				if (null != listOfLaunch.get(0).getError()) {
					throw new Exception(listOfLaunch.get(0).getError());
				}
			} catch (Exception e) {
				logger.error("Exception: ", e);
				model.addAttribute("Error", e.toString());
			}
			
			HashMap<String, Object> tableObj = new HashMap<String, Object>();
			tableObj.put("aaData", listOfLaunch);
			Gson sLaunch =  new Gson();
			String launchList = sLaunch.toJson(tableObj);
			return launchList;
		}

	@RequestMapping(value = "getAllBasePackByLaunchIdsSc.htm", method = RequestMethod.POST)
	public String getAllBasePackByLaunchIdsSc(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		ListBasepackDataScResponse listBasepackDataScResponse = new ListBasepackDataScResponse();
		List<LaunchScBasepackResponse> listOfLaunch = new ArrayList<>();
		try {
			GetScLaunchDetailsRequest getScLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetScLaunchDetailsRequest.class);
			String launchId[] = getScLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchServiceSc.getScBasepackData(listOfLaunchData);
			listBasepackDataScResponse.setListLaunchDataResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_SC_BASEPACK_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_SC_BASEPACK_DATA, listBasepackDataScResponse));
	}

	@RequestMapping(value = "getAllFinalBuildUpByLaunchIdsSc.htm", method = RequestMethod.POST)
	public String getAllFinalBuildUpByLaunchIdsSc(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		ListFinalBuildUpDataScResponse listBuildUpDataScResponse = new ListFinalBuildUpDataScResponse();
		List<LaunchScFinalBuildUpResponse> listOfLaunch = new ArrayList<>();
		try {
			GetScLaunchDetailsRequest getScLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetScLaunchDetailsRequest.class);
			String launchId[] = getScLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchServiceSc.getScBuildUpData(listOfLaunchData);
			listBuildUpDataScResponse.setLaunchScFinalBuildUpResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_SC_FINAL_BUILDUP_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_SC_FINAL_BUILDUP_DATA, listBuildUpDataScResponse));
	}

	@RequestMapping(value = "getAllMstnClearanceByLaunchIdsSc.htm", method = RequestMethod.POST)
	public String getAllMstnClearanceByLaunchIdsSc(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		ListMstnClearanceScResponse listMstnClearanceScResponse = new ListMstnClearanceScResponse();
		List<LaunchScMstnClearanceResponse> listOfLaunch = new ArrayList<>();
		try {
			GetScLaunchDetailsRequest getScLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetScLaunchDetailsRequest.class);
			String launchId[] = getScLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<String> GetdistinctMOC = launchServiceSc.getScMstnClearanceDataChange(listOfLaunchData);//Added by Harsha in Q6 to get details in MOC dropdown
			listOfLaunch = launchServiceSc.getScMstnClearanceData(listOfLaunchData);// Loads data with ALL condition
			listMstnClearanceScResponse.setGetMoc(GetdistinctMOC);
			listMstnClearanceScResponse.setLaunchScMstnClearanceResponseList(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_SC, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_SC, listMstnClearanceScResponse));
	}

	@RequestMapping(value = "saveMstnClearanceByLaunchIdsSc.htm", method = RequestMethod.POST) 
	public @ResponseBody String saveMstnClearanceByLaunchIdsSc(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		String result = null;
		Gson gson = new Gson();
		try {
			RequestMstnClearanceList requestMstnClearanceList = gson.fromJson(jsonBody, RequestMstnClearanceList.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchServiceSc.saveMstnClearanceByLaunchIdsSc(requestMstnClearanceList, userId);
			if (!result.equals("Saved Successfully")) {
				throw new Exception(result);
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
					ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_SAVE_SC, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_SAVE_SC, "Saved Successfully"));
	}

	@RequestMapping(value = "uploadMstnClearanceByLaunchIdSc.htm", method = RequestMethod.POST)
	public String uploadMstnClearanceByLaunchIdSc(MultipartHttpServletRequest multi, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userID = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = multi.getFile("file");

		String filepath = FilePaths.LAUNCH_MDG_UPLOAD_FILE_PATH;
		String fileName = file.getOriginalFilename();
		fileName = filepath + fileName;
		String successMessage = "";
		Map<String, String> apiResponse = new HashMap<>();
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					throw new Exception("File size exceeded");
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(LaunchMstnClearance.class).map(15, false, null); // Modified by Harsha for Q5 sprint from 14 to 15
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchServiceSc.uploadMstnClearanceByLaunchIdSc(list, userID, "CREATED BY TME",
									true, false);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
								}
								else if (savedData != null && savedData.equals("Wrong date")) {
									throw new Exception("MSTN CLEARENCE DATE CLOSED");
								}
							} else {
								throw new Exception(savedData);
							}
						}
					}
				}
			} else {
				throw new Exception(commUtils.getProperty("File.Empty"));
			}
			if (savedData.equals("ERROR_FILE")) {
				throw new Exception("ERROR_FILE");
			} else if (savedData.equals("ERROR")) {
				throw new Exception("File Upload is UnSuccessful.");
			} else {
				successMessage = "SUCCESS_FILE";
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			apiResponse.put("Error", e.toString());
			return gson.toJson(apiResponse);
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			apiResponse.put("Error", e.toString());
			return gson.toJson(apiResponse);
		}
		apiResponse.put("Success", successMessage);
		return gson.toJson(apiResponse);
	}

	@RequestMapping(value = "{launchId}/downloadFinalBuildUpTemplateSc.htm", method = RequestMethod.GET)
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

	@RequestMapping(value = "{launchIds}/{requiredMoc}/downloadMstnClearanceTemplateSc.htm", method = RequestMethod.GET) // Added MOC parameter for downloading excel based on MOC and launchId
	public @ResponseBody String downloadMstnClTemplate(@PathVariable("launchIds") String launchId,
			@PathVariable("requiredMoc") String requiredMoc, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String moc = requiredMoc;
		String absoluteFilePath = "";
		InputStream is;
		String downloadLink = "";
		Gson gson = new Gson();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("MSTNClearance.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		String userId = (String) request.getSession().getAttribute("UserID");
		String launchIds[] = launchId.split(",");
		List<String> listOfLaunchData = Arrays.asList(launchIds);
		List<ArrayList<String>> listDownload = launchFinalPlanService.getMstnClearanceDataDump(userId,
				listOfLaunchData, moc);
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=MSTNClearanceTemplate"
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
	
	// Added By Harsha for selecting desired MOC in drop down implementation 
	
	@RequestMapping(value = "getSelectedMstnClearanceByLaunchIdsSc.htm", method = RequestMethod.POST)
	public String getSelectedMstnClearanceByLaunchIdsSc(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		ListMstnClearanceScResponse listMstnClearanceScResponse = new ListMstnClearanceScResponse();
		List<LaunchScMstnClearanceResponse> listOfLaunch = new ArrayList<>();
		try {
			List<String> launchId = new ArrayList<String>();
			List<String> moc = new ArrayList<String>();
			String jsonInString = String.valueOf(jsonBody) ;
			jsonInString=jsonInString.replace("\"", "");
			jsonInString = jsonInString.replaceAll("[{}]", " ").trim();
			
			String[] stringarray = jsonInString.split(","); 
			for(int i=0; i< stringarray.length; i++)  
			{
			String res = stringarray[i];
			res=res.trim();
			if(res.contains("launchId:")){
			   launchId.add(res.substring(9));
			} 
			else if(res.contains("mocSelect:")){
			   moc.add(res.substring(10));
			} 
			}
			listOfLaunch = launchServiceSc.getScMstnClearanceDataByfilter(launchId, moc);// will fetch selected MOC data 
			listMstnClearanceScResponse.setLaunchScMstnClearanceResponseList(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_MSTN_CLEARED_SC, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_SC, listMstnClearanceScResponse));
	}


}