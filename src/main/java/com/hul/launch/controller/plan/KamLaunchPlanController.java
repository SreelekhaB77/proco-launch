package com.hul.launch.controller.plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.hul.launch.daoImpl.LaunchDaoKamImpl;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.model.SaveUploadededLaunchStore;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.request.ChangeMocRequestKam;
import com.hul.launch.request.ClusterRequest;
import com.hul.launch.request.GetKamLaunchDetailsRequest;
import com.hul.launch.request.GetKamLaunchRejectRequest;
import com.hul.launch.request.MissingDetailsKamInput;
import com.hul.launch.request.RejectBasepackRequestKam;
import com.hul.launch.request.SampleSharedReqKam;
import com.hul.launch.request.SaveLaunchStore;
import com.hul.launch.request.SaveLaunchStoreList;
import com.hul.launch.request.SaveVisiRequestVatKamList;
import com.hul.launch.response.GetLaunchMocForKamResponse;
import com.hul.launch.response.GetLaunchStoreResponseKamList;
import com.hul.launch.response.GetListLaunchBuildUpResponse;
import com.hul.launch.response.GetListLaunchVisiPlanningResponse;
import com.hul.launch.response.GlobleResponse;
import com.hul.launch.response.KamChangeReqRemarks;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchKamBasepackResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseKam;
import com.hul.launch.response.LaunchMstnClearanceResponseKamList;
import com.hul.launch.response.ListBasepackDataKamResponse;
import com.hul.launch.response.SuccessResponse;
import com.hul.launch.service.LaunchFinalService;
import com.hul.launch.service.LaunchService;
import com.hul.launch.service.LaunchServiceCoe;
import com.hul.launch.service.LaunchServiceKam;
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
public class KamLaunchPlanController {
	Logger logger = Logger.getLogger(KamLaunchPlanController.class);

	@Autowired
	LaunchServiceKam launchServiceKam;

	@Autowired
	public LaunchService launchService;

	@Autowired
	public LaunchServiceCoe launchServiceCoe;

	@Autowired
	public LaunchFinalService launchFinalPlanService;
	
	@Autowired
	private PromoCrService promoCrService;
	

	@RequestMapping(value = "getAllCompletedLaunchDataKam.htm", method = RequestMethod.GET)
	public ModelAndView getAllCompletedLaunchData(HttpServletRequest request, Model model) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		String kamMoc = "All";
		String approvalKamMoc = "All";
		String approvalKamStauts = "All";
		List<KamChangeReqRemarks> listOfKamChangeReqRemarks = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			//Harsha's implementation for logintool
			String role=(String)request.getSession().getAttribute("roleId");
			promoCrService.insertToportalUsage(userId, role, "LAUNCH");
			//Harsha's Logic End's here 
			
			//listOfLaunch = launchServiceKam.getAllCompletedLaunchData(userId);
			listOfLaunch = launchServiceKam.getAllCompletedLaunchData(userId, kamMoc);
			
			int  launchId =listOfLaunch.get(0).getLaunchId();
			//Q1 sprint kavitha
			//List<String> kammoclist=launchServiceKam.getAllMoc(userId, kamMoc,listOfLaunch); --- previous implementation before Q7
			List<String> kammoclist=launchServiceKam.getAllMoc(listOfLaunch);// Modified by Harsha for Q7 sprint
			model.addAttribute("kammoclist",kammoclist);
			//model.addAttribute("kamApprovalStatusNotification",listOfKamChangeReq.stream().map(n->n.getLaunchReadStatus()=="NEW").count());
			
			//Q1 Sprint3 Notification Changes - Kavitha D Starts
			listOfKamChangeReqRemarks = launchServiceKam.getApprovalStatusKam(userId,approvalKamMoc,approvalKamStauts, 0);
			int count=0;
            for (KamChangeReqRemarks status : listOfKamChangeReqRemarks) {
                if(status.getLaunchReadStatus().equalsIgnoreCase("NEW")) {
                    count++;
                } 
            }
            model.addAttribute("kamApprovalStatusNotification",count);
            //Q1 Sprint3 Notification Changes - Kavitha D Ends
			//for display records
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			model.addAttribute("listOfLaunch", listOfLaunch);
			//model.addAttribute("kamApprovalList", listOfKamChangeReq);
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/kam_launchplan");
	}
	
	//Sarin Changes - Q1Sprint feb2021
	@RequestMapping(value = "getAllCompletedLaunchKamData.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String getAllCompletedLaunchKamData(HttpServletRequest request, Model model,
			@RequestParam("kamMoc") String kamMoc) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceKam.getAllCompletedLaunchData(userId, kamMoc);
			
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		
		HashMap<String, Object> tableObj = new HashMap<String, Object>();
		//tableObj.put("iTotalRecords", 10);
		//tableObj.put("iTotalDisplayRecords", 10);
		tableObj.put("aaData", listOfLaunch);
		Gson sLaunch =  new Gson();
		String launchList = sLaunch.toJson(tableObj);
		return launchList;
	}

	@RequestMapping(value = "getApprovalStatusKam.htm", method = RequestMethod.GET)
	public ModelAndView getApprovalStatusKam(HttpServletRequest request, Model model) {
		String approvalKamMoc = "All";
		String approvalKamStauts = "All";
		List<KamChangeReqRemarks> listOfKamChangeReq = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfKamChangeReq = launchServiceKam.getApprovalStatusKam(userId,approvalKamMoc,approvalKamStauts, 0);
			//Q2 sprint kavitha feb 2021
			List<String> kamApprovalMoclist=launchServiceKam.getAllMocApprovalStatus(userId);
			model.addAttribute("kamApprovalMoclist",kamApprovalMoclist);
			
			List<String> kamApprovalStatuslist=launchServiceKam.getKamApprovalStatus(userId);
			model.addAttribute("kamApprovalStatuslist",kamApprovalStatuslist);
			
			model.addAttribute("kamApprovalList", listOfKamChangeReq);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/kam_launchplan_approvalstatus");
	}
	//Q2 sprint feb 2021 kavitha
	@RequestMapping(value = "getApprovalStatusMocKam.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String getApprovalStatusMOCKam(HttpServletRequest request, Model model,
			@RequestParam("approvalKamMoc") String approvalKamMoc,@RequestParam("approvalKamStauts") String approvalKamStauts) {
		
		List<KamChangeReqRemarks> listOfKamChangeReq = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfKamChangeReq = launchServiceKam.getApprovalStatusKam(userId,approvalKamMoc,approvalKamStauts, 1);
		}
			catch (Exception e) {
				logger.error("Exception: ", e);
				model.addAttribute("Error", e.toString());
			}
			
			HashMap<String, Object> tableObj = new HashMap<String, Object>();
			
			tableObj.put("aaData", listOfKamChangeReq);
			Gson sLaunch =  new Gson();
			String launchList = sLaunch.toJson(tableObj);
			return launchList;
	}
	
	
	@RequestMapping(value = "getAllBasePackByLaunchIdsKam.htm", method = RequestMethod.POST)
	public String getAllCompletedLaunchData(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		ListBasepackDataKamResponse listBasepackDataKamResponse = new ListBasepackDataKamResponse();
		List<LaunchKamBasepackResponse> listOfLaunch = new ArrayList<>();
		
		try {
			GetKamLaunchDetailsRequest getKamLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetKamLaunchDetailsRequest.class);
			String launchId[] = getKamLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceKam.getKamBasepackData(listOfLaunchData, userId);

			listBasepackDataKamResponse.setListLaunchDataResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_KAM_BASEPACK_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_KAM_BASEPACK_DATA, listBasepackDataKamResponse));
	}

	@RequestMapping(value = "getUpcomingLaunchMocByLaunchIdsKam.htm", method = RequestMethod.GET)
	public String getUpcomingLaunchMocByLaunchIdsKam(@RequestParam("launchId") String launchId,
			HttpServletRequest request, Model model) {

		Gson gson = new Gson();
		GetLaunchMocForKamResponse getLaunchMocForKamResponse = new GetLaunchMocForKamResponse();
		// kavitha
		String userId = (String) request.getSession().getAttribute("UserID");
		// Existing flow
		//List<String> listKamAccounts = launchServiceKam.getLaunchAccounts(launchId, userId);
		
		//Modified by Harsha to get account names which are not rejected by TME
		List<String> listKamAccounts = launchServiceKam.getLaunchAccountsforRejection(launchId, userId);
				
		
		getLaunchMocForKamResponse.setLisOfAcc(listKamAccounts);

		List<String> listOfLaunch = new ArrayList<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			listOfLaunch = launchServiceKam.getUpcomingLaunchMocByLaunchIdsKam(launchId);
			getLaunchMocForKamResponse.setListOfMoc(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_MOC_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_MOC_DATA, getLaunchMocForKamResponse));
	}
	
	

	// Modifications for Q4 Sprint added by Harsha.
	@RequestMapping(value = "getRejectionAccountIdKam.htm", method = RequestMethod.GET)
	public String getRejectionAccountIdKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {

		Gson gson = new Gson();
		GetLaunchMocForKamResponse getLaunchMocForKamResponse = new GetLaunchMocForKamResponse();
		
		String userId = (String) request.getSession().getAttribute("UserID");
		//Existing implementation
		// List<String> listKamAccounts = launchServiceKam.getLaunchAccounts(launchId, userId);
		
		//Added by Harsha to get account names which are not approved for rejection by TME
		List<String> listKamaccounts = launchServiceKam.getLaunchAccountsforRejection(launchId, userId);
		
		getLaunchMocForKamResponse.setLisOfAcc(listKamaccounts);
		List<String> listOfLaunch = new ArrayList<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			listOfLaunch = launchServiceKam.getUpcomingLaunchMocByLaunchIdsKam(launchId);
			getLaunchMocForKamResponse.setListOfMoc(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_MOC_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_MOC_DATA, getLaunchMocForKamResponse));
	}
	
	// Modifications for Q4 Sprint added by Harsha
	@RequestMapping(value = "rejectLaunchByLaunchIdKam.htm", method = RequestMethod.POST)
	public String rejectLaunchByLaunchIdKam(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String successREsponse = "";
		
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			GetKamLaunchRejectRequest getKamLaunchRejectRequest = gson.fromJson(jsonBody,
					GetKamLaunchRejectRequest.class);
			String launchId = getKamLaunchRejectRequest.getLaunchId();
			//Existing Implementation
			successREsponse = launchServiceKam.rejectLaunchByLaunchIdKam(getKamLaunchRejectRequest, userId);
			
			
			if (!successREsponse.equals("Rejected Successfully")) {
				throw new Exception(successREsponse);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_REJECT_LAUNCH_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_REJECT_LAUNCH_KAM, successREsponse));
	}

	@RequestMapping(value = "requestChengeMocByLaunchIdKam.htm", method = RequestMethod.POST)
	public String requestChengeMocByLaunchIdKam(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String successREsponse = " ";

		List<String> accounts = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			ChangeMocRequestKam changeMocRequestKam = gson.fromJson(jsonBody, ChangeMocRequestKam.class);
			successREsponse = launchServiceKam.requestChengeMocByLaunchIdKam(changeMocRequestKam, userId);

		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_REQUEST_CHANGE_MOC_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_REQUEST_CHANGE_MOC_KAM, successREsponse));
	}

	@RequestMapping(value = "rejectBasepacksByBasepackIdsKam.htm", method = RequestMethod.POST)
	public String rejectBasepacksByBasepackIdsKam(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		String successREsponse = "";
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			RejectBasepackRequestKam rejectBasepackRequestKam = gson.fromJson(jsonBody, RejectBasepackRequestKam.class);
			successREsponse = launchServiceKam.rejectBasepacksByBasepackIdsKam(rejectBasepackRequestKam, userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_REJECT_BASEPACK_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_REJECT_BASEPACK_KAM, successREsponse));
	}

	@RequestMapping(value = "missingDetailsKamInput.htm", method = RequestMethod.POST)
	public String missingDetailsKamInput(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String successREsponse = "";
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			MissingDetailsKamInput missingDetailsKamInput = gson.fromJson(jsonBody, MissingDetailsKamInput.class);
			successREsponse = launchServiceKam.missingDetailsKamInput(missingDetailsKamInput, userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_REJECT_BASEPACK_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_REJECT_BASEPACK_KAM, successREsponse));
	}

	@RequestMapping(value = "getLaunchBuildUpByLaunchIdKam.htm", method = RequestMethod.GET)
	public String getLaunchBuildUpByLaunchIdKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		GetListLaunchBuildUpResponse getListLaunchBuildUpResponse = new GetListLaunchBuildUpResponse();
		List<LaunchFinalPlanResponse> listOfLaunch = new ArrayList<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchFinalPlanService.getLaunchFinalResposeEditKAM(launchId, userId);
			getListLaunchBuildUpResponse.setGetListLaunchBuildUpData(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_BUILD_UP_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_BUILD_UP_KAM, getListLaunchBuildUpResponse));
	}

	@RequestMapping(value = "getMstnClearanceByLaunchIdKam.htm", method = RequestMethod.GET)
	public String getMstnClearanceByLaunchIdKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		LaunchMstnClearanceResponseKamList getLaunchMstnClearanceResponseKam = new LaunchMstnClearanceResponseKamList();
		List<LaunchMstnClearanceResponseKam> listOfLaunch = new ArrayList<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceKam.getMstnClearanceByLaunchIdKam(launchId, userId);
			getLaunchMstnClearanceResponseKam.setLaunchMstnClearanceResponseKams(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_BUILD_UP_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_GET_KAM, getLaunchMstnClearanceResponseKam));
	}

	@RequestMapping(value = "{launchId}/downloadFinalBuildUpKamTemplate.htm", method = RequestMethod.GET)
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
		try {
			List<ArrayList<String>> listDownload = launchFinalPlanService.getFinalBuildUpDumpNewKam(userId, launchId);
		
			
			
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

	@RequestMapping(value = "saveLaunchStores.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchStores(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {

		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			SaveLaunchStore saveFinalLaunchListRequest = gson.fromJson(jsonBody, SaveLaunchStore.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchServiceKam.saveLaunchStores(saveFinalLaunchListRequest, userId,
					saveFinalLaunchListRequest.getLaunchId());
			if (result.equals("Saved Successfully")) {
				successResponse.setResponseMessage(result);

			} else {
				successResponse.setResponseMessage(result);
				throw new Exception("Something went wrong,Didn't Save Successfully");
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponse);
	}

	@RequestMapping(value = "updateLaunchSampleShared.htm", method = RequestMethod.POST)
	public @ResponseBody String updateLaunchSampleShared(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {

		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			SampleSharedReqKam sampleSharedReqKam = gson.fromJson(jsonBody, SampleSharedReqKam.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchServiceKam.updateLaunchSampleShared(sampleSharedReqKam, userId);
			if (result.equals("Saved Successfully")) {
				successResponse.setResponseMessage(result);

			} else {
				successResponse.setResponseMessage(result);
				throw new Exception("Something went wrong,Didn't Save Successfully");
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponse);
	}
	
	@RequestMapping(value = "getLaunchStoreListByLaunchKam.htm", method = RequestMethod.GET)
	public String GetLaunchStoreListByLaunchIdKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		GetLaunchStoreResponseKamList getLaunchStoreResponseKamList = new GetLaunchStoreResponseKamList();
		List<SaveLaunchStoreList> getLaunchStoreResponseKam;
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			getLaunchStoreResponseKam = launchServiceKam.getLaunchStoresBuildUpByLaunchIdKam(launchId, userId);
			getLaunchStoreResponseKamList.setListSaveLaunchStoreList(getLaunchStoreResponseKam);
		} catch (Exception e) { 
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_LAUNCH_STORE_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_LAUNCH_STORE_KAM, getLaunchStoreResponseKamList));
	}

	@RequestMapping(value = "downloadStoreListFile.htm", method = RequestMethod.GET)
	// public ModelAndView downloadUpdatedBaseFile(@RequestParam("launchId") String
	// launchId, HttpServletRequest request,
	public ModelAndView downloadUpdatedBaseFile(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Store.Download.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		//Added by Kavitha D Sprint 7-DEC2021
		String launchName=launchServiceKam.getLaunchName(launchId);
		String downloadFileName = absoluteFilePath + fileName + launchName;
		ArrayList<String> headerList = launchServiceKam.getHeaderListForBaseFile();
		// String KamRemarks =request.getParameter(arg0)
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			 downloadedData = launchServiceKam.getUpdatedBaseFile(headerList, launchId, userId);
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null, ".xls");

			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			//response.setHeader("Content-Disposition", "attachment; filename=Store.Download.file_" + CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
			//Kavitha D changes for storelist download file name
			response.setHeader("Content-Disposition", "attachment; filename=Store.Download.file_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + "_" +launchName + ".xls");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return null;
	}
	
	// Harsha's changes for Downloading Stores list limit -- Starts
	@RequestMapping(value = "downloadStoreLimitFile.htm", method = RequestMethod.GET)
	public ModelAndView downloadStoreLimitFile(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Store.Download.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String launchName=launchServiceKam.getLaunchName(launchId);
		String downloadFileName = absoluteFilePath + fileName + launchName;
		ArrayList<String> headerList = launchServiceKam.getHeaderListForStorelimitFile();
		
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			downloadedData = launchServiceKam.getStoreLimitFile(headerList, launchId, userId);
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null, ".xls");

			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=Store.Download.file_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + "_" +launchName + ".xls");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return null;
	}
	// Harsha's changes for Downloading Stores list limit -- Ends

	

	@RequestMapping(value = "downloadVisiListFileKam.htm", method = RequestMethod.GET)
	public ModelAndView downloadVisiListFileKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("VisiList.Download.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		try {
			downloadedData = launchServiceKam.getUpdatedVisiFile(launchId);
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null, ".xls");

			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=Store.Download.file_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	@RequestMapping(value = "{launchId}/uploadLaunchStore.htm", method = RequestMethod.POST)
	public String uploadLaunchStore(@PathVariable("launchId") String launchId, MultipartHttpServletRequest multi,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String successMessage = "";
		Gson gson = new Gson();
		Map<String, String> mapResponse = new HashMap<>();
		try {
			String savedData = null;
			CommonPropUtils commUtils = CommonPropUtils.getInstance();
			String userID = (String) request.getSession().getAttribute("UserID");
			MultipartFile file = multi.getFile("file");
			String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
			String fileName = file.getOriginalFilename();
			fileName = filepath + fileName;
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					throw new Exception("File size exceeded");
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(SaveUploadededLaunchStore.class).map(8, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchServiceKam.saveStoreListByUpload(list, userID, "CREATED BY TME", true,
									false, launchId);
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								successMessage = commUtils.getProperty("File.Upload.Success");
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								throw new Exception("File Uploaded with errors");
							} else if (savedData != null && savedData.equals("ERROR")) {
								throw new Exception("Error while uploading file");
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
			} 
			else if (savedData != null && savedData.equals("Minimum targeted stores should be approved by KAM")) {//Added By Harsha as part of US7 Jan 22
				throw new Exception("Minimum targeted stores should be approved by KAM");
			}
			else {
				successMessage = "SUCCESS_FILE";
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			mapResponse.put("Error", e.toString());
			return gson.toJson(mapResponse);
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			mapResponse.put("Error", e.toString());
			return gson.toJson(mapResponse);
		}
		return successMessage;
	}

	@RequestMapping(value = "getLaunchVisiListByLaunchIdKam.htm", method = RequestMethod.GET)
	public String getLaunchVisiListByLaunchIdKam(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		GetListLaunchVisiPlanningResponse getListLaunchVisiPlanningResponse = new GetListLaunchVisiPlanningResponse();
		List<LaunchVisiPlanning> listOfVisiPlanningLaunch = new ArrayList<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			listOfVisiPlanningLaunch = launchServiceKam.getLaunchVisiListByLaunchIdKam(launchId);
			getListLaunchVisiPlanningResponse.setGetListLaunchVisiPlanningResponse(listOfVisiPlanningLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_VISI_LIST_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_VISI_LIST_KAM, getListLaunchVisiPlanningResponse));
	}
	
	
	@RequestMapping(value = "saveLaunchVisiListByLaunchIdKam.htm", method = RequestMethod.POST)
	public String saveLaunchVisiListByLaunchIdKam(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		SaveVisiRequestVatKamList saveVisiRequestVatKamList = gson.fromJson(jsonBody, SaveVisiRequestVatKamList.class);
		String listOfVisiPlanningLaunch = null;
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfVisiPlanningLaunch = launchServiceKam.saveLaunchVisiListByLaunchIdKam(saveVisiRequestVatKamList,
					userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_SAVE_VISI_LIST_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_SAVE_VISI_LIST_KAM, listOfVisiPlanningLaunch));
	}

	@RequestMapping(value = "getLaunchDocDetailsByLaunchIdKam.htm", method = RequestMethod.GET)
	public String getLaunchDocDetailsByLaunchIdKam(@RequestParam("launchId") String launchId,
			HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		LaunchDataResponse launchDataResonse = null;
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			launchDataResonse = launchServiceKam.getSpecificLaunchDataKam(launchId, userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_LAUNCH_DOC_DETAILS_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_LAUNCH_DOC_DETAILS_KAM, launchDataResonse));
	}

	@RequestMapping(value = "{fileName}/downloadFileTemplateKam.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadFileTemplateKam(@PathVariable("fileName") String fileName, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String absoluteFilePath = "";

		absoluteFilePath = FilePaths.LAUNCH_ARTWORK_UPLOAD_FILE_PATH;
		String downloadFileName = absoluteFilePath + fileName;
		Gson gson = new Gson();
		try {
			if (fileName.equals("")) {
				throw new Exception("File name can not be blank");
			}

			is = new FileInputStream(new File(downloadFileName));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson("Downloaded");
	}

	@RequestMapping(value = "{launchId}/downloadAnnexureListDataKam.htm", method = RequestMethod.GET)
	// public @ResponseBody ModelAndView
	// downloadAnnexureListDataKam(@PathVariable("launchId") String launchId,
	// //Sarin
	public @ResponseBody String downloadAnnexureListDataKam(@PathVariable("launchId") String launchId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();

		InputStream is;
		String downloadLink = "";
		String absoluteFilePath = FilePaths.LAUNCH_ANNEXURE_UPLOAD_FILE_PATH;
		String appendString = File.separator;
		absoluteFilePath = absoluteFilePath + appendString;
		try {
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<TblLaunchMaster> listOfLaunchMaster = launchServiceCoe.getAllLaunchData(listOfLaunchData);
			if (!listOfLaunchMaster.isEmpty()) {
				for (TblLaunchMaster tblLaunchMaster : listOfLaunchMaster) {
					String downloadFileName = absoluteFilePath + tblLaunchMaster.getAnnexureDocName();
					downloadLink = downloadFileName;
					is = new FileInputStream(new File(downloadLink));
					response.setContentType("application/force-download");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + tblLaunchMaster.getAnnexureDocName());
					IOUtils.copy(is, response.getOutputStream());
					response.flushBuffer();
				}

			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			 * ModelAndView modelAndView = new ModelAndView();
			 * modelAndView.addObject("Error", e.toString()); return modelAndView;
			 */
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		// return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", downloadLink);
		return gson.toJson(map);
	}

	@RequestMapping(value = "{launchId}/downloadMdgDeckDataKam.htm", method = RequestMethod.GET)
	public ModelAndView downloadMdgDeckDataKam(@PathVariable("launchId") String launchId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in downloadMdgDeckDataKam");
		try {
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			absoluteFilePath = FilePaths.LAUNCH_MDG_UPLOAD_FILE_PATH;

			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<TblLaunchMaster> listOfLaunchMaster = launchServiceCoe.getAllLaunchData(listOfLaunchData);
			if (!listOfLaunchMaster.isEmpty()) {
				for (TblLaunchMaster tblLaunchMaster : listOfLaunchMaster) {
					System.out.println("in tblLaunchMaster " + tblLaunchMaster.getMdgDecName());
					String downloadFileName = absoluteFilePath + tblLaunchMaster.getMdgDecName();
					downloadLink = downloadFileName;
					System.out.println("downloadlink " + downloadLink);
					is = new FileInputStream(new File(downloadLink));
					response.setContentType("application/force-download");
					response.setHeader("Content-Disposition",
							"attachment; filename=" + tblLaunchMaster.getMdgDecName());
					IOUtils.copy(is, response.getOutputStream());
					response.flushBuffer();
				}
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return null;
		}
		return null;
	}
}