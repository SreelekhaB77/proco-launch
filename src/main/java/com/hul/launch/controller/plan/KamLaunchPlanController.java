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
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.model.SaveUploadededLaunchStore;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.request.ChangeMocRequestKam;
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

	@RequestMapping(value = "getAllCompletedLaunchDataKam.htm", method = RequestMethod.GET)
	public ModelAndView getAllCompletedLaunchData(HttpServletRequest request, Model model) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceKam.getAllCompletedLaunchData(userId);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			model.addAttribute("listOfLaunch", listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/kam_launchplan");
	}

	@RequestMapping(value = "getApprovalStatusKam.htm", method = RequestMethod.GET)
	public ModelAndView getApprovalStatusKam(HttpServletRequest request, Model model) {
		List<KamChangeReqRemarks> listOfKamChangeReq = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfKamChangeReq = launchServiceKam.getApprovalStatusKam(userId);
			model.addAttribute("kamApprovalList", listOfKamChangeReq);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/kam_launchplan_approvalstatus");
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

	@RequestMapping(value = "rejectLaunchByLaunchIdKam.htm", method = RequestMethod.POST)
	public String rejectLaunchByLaunchIdKam(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String successREsponse = "";
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			GetKamLaunchRejectRequest getKamLaunchRejectRequest = gson.fromJson(jsonBody,
					GetKamLaunchRejectRequest.class);
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
		String successREsponse = "";
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
	public ModelAndView downloadUpdatedBaseFile(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Store.Download.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
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
								.to(SaveUploadededLaunchStore.class).map(6, false, null);
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
			} else {
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
	public @ResponseBody ModelAndView downloadAnnexureListDataKam(@PathVariable("launchId") String launchId,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream is;
			String downloadLink = "";
			String absoluteFilePath = FilePaths.LAUNCH_ANNEXURE_UPLOAD_FILE_PATH;
			String appendString = File.separator;
			absoluteFilePath = absoluteFilePath + appendString;

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
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
		}
		return new ModelAndView("productsPage");
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
					System.out.println("in tblLaunchMaster "+tblLaunchMaster.getMdgDecName());
					String downloadFileName = absoluteFilePath + tblLaunchMaster.getMdgDecName();
					downloadLink = downloadFileName;
					System.out.println("downloadlink "+downloadLink);
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