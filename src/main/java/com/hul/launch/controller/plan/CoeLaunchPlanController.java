package com.hul.launch.controller.plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.constants.ResponseConstants;
import com.hul.launch.model.TblLaunchMaster;
import com.hul.launch.request.GetCoeLaunchDetailsRequest;
import com.hul.launch.response.CoeDocDownloadResponse;
import com.hul.launch.response.CoeStoreListJsonObject;
import com.hul.launch.response.GlobleResponse;
import com.hul.launch.response.LaunchCoeBasePackResponse;
import com.hul.launch.response.LaunchCoeClusterResponse;
import com.hul.launch.response.LaunchCoeFinalPageResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;
import com.hul.launch.response.LaunchMstnClearanceResponseCoeList;
import com.hul.launch.response.ListLaunchCoeClusterResponse;
import com.hul.launch.response.ListLaunchCoeFinalPageResponse;
import com.hul.launch.response.ListLaunchDataResponse;
import com.hul.launch.response.ListOfCoeDocDownloadResponse;
import com.hul.launch.response.ListOfCoeLaunchStoreListResponse;
import com.hul.launch.service.LaunchBasepacksService;
import com.hul.launch.service.LaunchFinalService;
import com.hul.launch.service.LaunchService;
import com.hul.launch.service.LaunchServiceCoe;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.listingPromo.PromoListingJsonObject;
import com.hul.proco.controller.promocr.PromoCrService;

/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@RestController
public class CoeLaunchPlanController {

	Logger logger = Logger.getLogger(CoeLaunchPlanController.class);

	@Autowired
	public LaunchService launchService;

	@Autowired
	public LaunchServiceCoe launchServiceCoe;

	@Autowired
	public LaunchBasepacksService launchBasepackService;

	@Autowired
	public LaunchFinalService launchFinalPlanService;
	
		@Autowired
		private PromoCrService promoCrService;

	@RequestMapping(value = "getAllCompletedLaunchData.htm", method = RequestMethod.GET)
	public ModelAndView getAllCompletedLaunchData(HttpServletRequest request, Model model) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		//Q2 sprint kavitha 2021
				String coeMoc = "All";
		try {
			
			//Harsha's implementation for logintool
			String id=(String)request.getSession().getAttribute("UserID");
			String role=(String)request.getSession().getAttribute("roleId");
			promoCrService.insertToportalUsage(id, role, "LAUNCH");
			//Harsha's Logic End's here 
			
			listOfLaunch = launchService.getAllCompletedLaunchData(coeMoc);
			//Q2 sprint kavitha
			List<String> coemoclist=launchService.getAllCOEMoc();
			model.addAttribute("coemoclist",coemoclist);
			
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			model.addAttribute("listOfLaunch", listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("Error", e.toString());
		}
		return new ModelAndView("launchplan/coe_launchplan");
	}
	
	//Q2 sprint kavitha feb 2021
		@RequestMapping(value = "getAllCoeMOCData.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
		public @ResponseBody String getAllCoeMOCData(HttpServletRequest request, Model model,
				@RequestParam("coeMoc") String coeMoc) {
			List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
			try {
				String userId = (String) request.getSession().getAttribute("UserID");
				listOfLaunch = launchService.getAllCompletedLaunchData(coeMoc);
				
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

	@RequestMapping(value = "getAllBasePackByLaunchIds.htm", method = RequestMethod.POST)
	public String getAllCompletedLaunchData(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		ListLaunchDataResponse listLaunchDataResponse = new ListLaunchDataResponse();
		List<LaunchCoeBasePackResponse> listOfLaunch = new ArrayList<>();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchService.getAllCompletedLaunchData(listOfLaunchData);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			listLaunchDataResponse.setListLaunchDataResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(listOfLaunch);
	}

	@RequestMapping(value = "getAllCompletedFinalLaunchData.htm", method = RequestMethod.POST)
	public String getAllCompletedFinalLaunchData(@RequestBody String jsonBody, HttpServletRequest request,
			Model model) {
		Gson gson = new Gson();
		ListLaunchCoeFinalPageResponse listLaunchCoeFinalPageResponse = new ListLaunchCoeFinalPageResponse();
		List<LaunchCoeFinalPageResponse> listOfLaunch = new ArrayList<>();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchService.getAllCompletedLaunchFinalData(listOfLaunchData);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			listLaunchCoeFinalPageResponse.setListLaunchCoeFinalPageResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_COMPLETED_FINAL_LAUNCH_DATA, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_COMPLETED_FINAL_LAUNCH_DATA, listLaunchCoeFinalPageResponse));
	}

	@RequestMapping(value = "getAllCompletedListingTracker.htm", method = RequestMethod.POST)
	public String getAllCompletedListingTracker(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<LaunchCoeClusterResponse> listOfLaunch = new ArrayList<>();
		ListLaunchCoeClusterResponse listLaunchCoeClusterResponse = new ListLaunchCoeClusterResponse();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			// Already Existing below line
			listOfLaunch = launchService.getAllCompletedListingTracker(listOfLaunchData);
			
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			listLaunchCoeClusterResponse.setListOfCoeClusterResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_COMPLETED_LISTING_TRACKER, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_COMPLETED_LISTING_TRACKER, listLaunchCoeClusterResponse));
	}

	@RequestMapping(value = "getCoeDocDownloadUrl.htm", method = RequestMethod.POST)
	public String getCoeDocDownloadUrl(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<CoeDocDownloadResponse> listOfLaunch = new ArrayList<>();
		ListOfCoeDocDownloadResponse listOfCoeDocDownloadResponse = new ListOfCoeDocDownloadResponse();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchService.getCoeDocDownloadUrl(listOfLaunchData);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			listOfCoeDocDownloadResponse.setCoeDocDownloadResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_COE_DOWNLOAD, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_COE_DOWNLOAD, listOfCoeDocDownloadResponse));
	}

	@RequestMapping(value = "getLaunchArtworkPackshotsDocument.htm", method = RequestMethod.GET)
	public String getLaunchArtworkPackshotsDocument(@RequestParam("launchId") String launchIds,
			HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<TblLaunchMaster> listOfLaunch = new ArrayList<>();
		Set<String> toReturn = new HashSet<>();
		try {

			String launchId[] = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchServiceCoe.getLaunchArtworkPackshotsDocument(listOfLaunchData);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}

			for (TblLaunchMaster artworksDocs : listOfLaunch) {
				String[] packShotsDocs = artworksDocs.getArtworkPackshotsDocName().split(",");
				for (int i = 0; i < packShotsDocs.length; i++) {
					if (packShotsDocs[i] != "") {
						toReturn.add(artworksDocs.getArtworkPackshotsDocName());
					}
				}
			}

		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_LAUNCH_STORE_LIST, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_LAUNCH_STORE_LIST, toReturn));
	}

	@RequestMapping(value = "getMstnClearanceByLaunchIdCoe.htm", method = RequestMethod.POST)
	public String getMstnClearanceByLaunchIdCoe(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		LaunchMstnClearanceResponseCoeList getLaunchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoeList();
		List<LaunchMstnClearanceResponseCoe> listOfLaunch = new ArrayList<>();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceCoe.getMstnClearanceByLaunchIdCoe(listOfLaunchData, userId);
			getLaunchMstnClearanceResponseCoe.setLaunchMstnClearanceResponseCoeList(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_BUILD_UP_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_GET_KAM, getLaunchMstnClearanceResponseCoe));
	}
	
	@RequestMapping(value = "{launchIds}/downloadMstnClearanceTemplateCoe.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadMstnClearanceTemplateCoe(@PathVariable("launchIds") String launchId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
		List<ArrayList<String>> listDownload = launchFinalPlanService.getMstnClearanceDataDumpCoe(userId,
				listOfLaunchData);
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
	
	@RequestMapping(value = "{fileName}/downloadFileTemplateCoe.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadSellInTemplateGet(@PathVariable("fileName") String fileName, Model model,
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

	@RequestMapping(value = "getLaunchStoreList.htm", method = RequestMethod.POST)
	public String getLaunchStoreList(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<LaunchCoeClusterResponse> listOfLaunch = new ArrayList<>();
		ListOfCoeLaunchStoreListResponse listOfCoeLaunchStoreListResponse = new ListOfCoeLaunchStoreListResponse();
		try {
			GetCoeLaunchDetailsRequest getCoeLaunchDetailsRequest = gson.fromJson(jsonBody,
					GetCoeLaunchDetailsRequest.class);
			String launchId[] = getCoeLaunchDetailsRequest.getLaunchIds().split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			listOfLaunch = launchService.getAllCompletedListingTracker(listOfLaunchData);
			if (null != listOfLaunch.get(0).getError()) {
				throw new Exception(listOfLaunch.get(0).getError());
			}
			listOfCoeLaunchStoreListResponse.setListOfCoeLaunchStoreListResponse(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_LAUNCH_STORE_LIST, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_LAUNCH_STORE_LIST, listOfCoeLaunchStoreListResponse));
	}

	@RequestMapping(value = "{launchIds}/downloadLaunchCoeBasepackTemplate.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadLaunchBasepackTemplate(@PathVariable("launchIds") String launchIds, //Sarin Prod Changes
	public @ResponseBody String downloadLaunchBasepackTemplate(@PathVariable("launchIds") String launchIds,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Basepack.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetail = getBasepackHeadersCoe();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
		try {
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<ArrayList<String>> listDownload = launchService.getbasepackDump(headerDetail, userId,
					listOfLaunchData);
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=BasepackTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}

	@RequestMapping(value = "{launchIds}/{launchMoc}/downloadLaunchBuildUpCoeTemplate.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadLaunchBuildUpCoeTemplate(@PathVariable("launchIds") String launchIds,
	public @ResponseBody String downloadLaunchBuildUpCoeTemplate(@PathVariable("launchIds") String launchIds,  //Sarin Prod Changes
			@PathVariable("launchMoc") String launchMocs,Model model, HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("BuildUp.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
		try {
			ArrayList<String> headerDetail = getLaunchBuildUpHeadersCoe();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			// Need to comment below part and add new method for it
			String[] launchId = launchIds.split(",");
			String[] launchMoc = launchMocs.split(",");
			List<ArrayList<String>> listDownload = launchFinalPlanService.getFinalBuildUpDumpNew(userId, launchId,launchMoc);
			// Harsha Code changes starts here
			
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=BuildUpTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}

	@RequestMapping(value = "{launchIds}/downloadLaunchListingTrackerCoeTemplate.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadLaunchListingTrackerCoeTemplate(
	public @ResponseBody String downloadLaunchListingTrackerCoeTemplate(
			@PathVariable("launchIds") String launchIds, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Listing.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetail = getLaunchListingTrackerCoe();
			downloadedData.add(headerDetail);
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<ArrayList<String>> listDownload = launchService.getListingTrackerDump(headerDetail, userId,
					listOfLaunchData);
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=ListingTrackerTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}

	@RequestMapping(value = "{launchIds}/downloadLaunchMSTNClearanceCoe.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadLaunchMSTNClearanceCoe(@PathVariable("launchIds") String launchIds,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("MSTNClearance.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetail = getLaunchMSTNClearanceCoe();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<ArrayList<String>> listDownload = launchService.getMSTNClearanceDump(headerDetail, userId,
					listOfLaunchData);
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
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
		}
		return new ModelAndView("productsPage");
	}
	
	@RequestMapping(value = "{launchIds}/getLaunchStoreListCoe.htm", method = RequestMethod.GET)
	public @ResponseBody String getLaunchStoreListCoe(@PathVariable("launchIds") String launchIds
			,Model model, HttpServletRequest request, HttpServletResponse response) {
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		CoeStoreListJsonObject jsonObj = new CoeStoreListJsonObject();
		List<ArrayList<String>> listOfLaunch = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = getLaunchStoreListCoe();
		//Added by Kavitha D for launch performance-SPRINT 13P1
		String userId = (String) request.getSession().getAttribute("UserID");
		//Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		//Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageDisplayStart =0;
		Integer pageDisplayLength =100;
		logger.info("Page Display Start:" +pageDisplayStart);
		logger.info("Page Display length:" +pageDisplayLength);		

		
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;
		String searchParameter = request.getParameter("sSearch");
		try {
			
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			//listOfLaunch = launchService.getLaunchStoreListDump(headerDetail, userId,listOfLaunchData);
			int rowCount = launchService.getLaunchListRowCountGrid(listOfLaunchData,searchParameter);
			listOfLaunch = launchService.getLaunchStoreListDumpPagination(headerDetail, userId,listOfLaunchData,(pageDisplayStart + 1),(pageNumber * pageDisplayLength),searchParameter);
			
			jsonObj.setJsonBean(listOfLaunch);
			jsonObj.setiTotalDisplayRecords(rowCount);
			jsonObj.setiTotalRecords(rowCount);

			//Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			
			
			if (null == listOfLaunch.get(0)) {
				throw new Exception("Record Not Found");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_LAUNCH_STORE_LIST, e.toString()));*/
		}
		/*return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_LAUNCH_STORE_LIST, listOfLaunch));*/
			String json = gson.toJson(jsonObj);
			return json;
		}
	

	@RequestMapping(value = "{launchIds}/downloadLaunchStoreListCoe.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadLaunchStoreListCoe(@PathVariable("launchIds") String launchIds,  //Sarin prod changes
	public @ResponseBody String downloadLaunchStoreListCoe(@PathVariable("launchIds") String launchIds,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		//Gson gson = new Gson();
		try {
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			//List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("LaunchStoreList.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetail = getLaunchStoreListCoe();
			//downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<ArrayList<String>> listDownload = launchService.getLaunchStoreListDump(headerDetail, userId,listOfLaunchData);
			if (listDownload != null) {
				//UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				UploadUtil.writeCoeXLSXFile(downloadFileName, listDownload,null, ".xlsx");//Changed file format by Kavitha D-sprint 13P1
				downloadLink = downloadFileName + ".xlsx";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=LaunchStoreListTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			//Commented and added by Kavitha D-sprint 13P1
			/*Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);*/
			return null;
		}
		//return new ModelAndView("productsPage");
		/*Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);*/
		return null;

	}
	
	// Added By Harsha as part of sprint 8 changes -- Starts 
	
	@RequestMapping(value = "{launchIds}/downloadLaunchStoreListLimitCoe.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadLaunchStoreListLimitCoe(@PathVariable("launchIds") String launchIds,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("LaunchStoreList.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
		try {
			ArrayList<String> headerDetail = getStoreslimitheader();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			String[] launchId = launchIds.split(",");
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<ArrayList<String>> listDownload = launchService.getLaunchStoreListLimitDump(headerDetail, userId,
					listOfLaunchData);
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=LaunchStoreListTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}

	
	
	// Sprint 8 changes ends
	

	@RequestMapping(value = "{launchId}/downloadAnnexureListDataCoe.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadAnnexureListDataCoe(@PathVariable("launchId") String launchId, //Sarin Prod changes
	public @ResponseBody String downloadAnnexureListDataCoe(@PathVariable("launchId") String launchId,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			String absoluteFilePath = FilePaths.LAUNCH_ANNEXURE_UPLOAD_FILE_PATH;
			String appendString = File.separator;
			absoluteFilePath = absoluteFilePath + appendString;
		try {
			ArrayList<String> headerDetail = getLaunchStoreListCoe();
			downloadedData.add(headerDetail);
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
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", downloadLink);
		return gson.toJson(map);
	}

	@RequestMapping(value = "{launchId}/downloadMdgDeckDataCoe.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadMdgDeckDataCoe(@PathVariable("launchId") String launchId, Model model,
	public @ResponseBody String downloadMdgDeckDataCoe(@PathVariable("launchId") String launchId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new Gson();
		
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<>();
			absoluteFilePath = FilePaths.LAUNCH_MDG_UPLOAD_FILE_PATH;
		try {
			ArrayList<String> headerDetail = getLaunchStoreListCoe();
			downloadedData.add(headerDetail);
			List<String> listOfLaunchData = Arrays.asList(launchId);
			List<TblLaunchMaster> listOfLaunchMaster = launchServiceCoe.getAllLaunchData(listOfLaunchData);
			if (!listOfLaunchMaster.isEmpty()) {
				for (TblLaunchMaster tblLaunchMaster : listOfLaunchMaster) {
					String downloadFileName = absoluteFilePath + tblLaunchMaster.getMdgDecName();
					downloadLink = downloadFileName;
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
			/*
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return new ModelAndView("productsPage");
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", downloadLink);
		return gson.toJson(map);
	}

	private ArrayList<String> getBasepackHeadersCoe() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Launch Name");
		headerDetail.add("Sales Category");
		headerDetail.add("PSA Category");
		headerDetail.add("Brand");
		headerDetail.add("Basepack Code");
		headerDetail.add("Basepack Description");
		headerDetail.add("MRP");
		headerDetail.add("TUR");
		headerDetail.add("GSV");
		headerDetail.add("CLD Configuration");
		headerDetail.add("Grammage");
		headerDetail.add("Classification");
		return headerDetail;
	}

	private ArrayList<String> getLaunchBuildUpHeadersCoe() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Launch Name");
		headerDetail.add("Sku Name");
		headerDetail.add("Basepack Code");
		headerDetail.add("MOC");
		headerDetail.add("Sell-In Value");
		headerDetail.add("Sell-In CLDs");
		headerDetail.add("Sell-In Units");
		return headerDetail;
	}

	private ArrayList<String> getLaunchListingTrackerCoe() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Launch Name");
		headerDetail.add("Launch MOC");
		headerDetail.add("Sku Name");
		headerDetail.add("Basepack Code");
		headerDetail.add("Cluster");
		headerDetail.add("Account");
		return headerDetail;
	}

	private ArrayList<String> getLaunchMSTNClearanceCoe() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Launch Name");
		headerDetail.add("Channel");
		headerDetail.add("Basepack Code");
		headerDetail.add("Basepack Description");
		headerDetail.add("Launch MOC");
		headerDetail.add("Cluster");
		headerDetail.add("Account");
		headerDetail.add("Depot");
		headerDetail.add("MSTN Cleared");
		headerDetail.add("Required Estimates");
		headerDetail.add("Current Estimates");
		headerDetail.add("Clearance Date");
		return headerDetail;
	}

	private ArrayList<String> getLaunchStoreListCoe() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Launch Name");
		headerDetail.add("Basepack Code");
		headerDetail.add("Basepack Description");
		headerDetail.add("Launch MOC");
		headerDetail.add("L1 Chain");
		headerDetail.add("L2 Chain");
		headerDetail.add("Depot");
		headerDetail.add("Store Format");
		headerDetail.add("Cluster");
		headerDetail.add("HUL OL Code");
		headerDetail.add("Customer Code");
		return headerDetail;
	}
	
	// Added By Harsha As part of sprint 8 changes -- Starts
	
	private ArrayList<String> getStoreslimitheader() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("CLUSTER_REGION");
		headerDetail.add("CLUSTER_ACCOUNT_L1");
		headerDetail.add("CLUSTER_ACCOUNT_L2");
		headerDetail.add("CLUSTER_STORE_FORMAT");
		headerDetail.add("CLUSTER_CUST_STORE_FORMAT");
		headerDetail.add("LAUNCH_PLANNED");
		headerDetail.add("TOTAL_STORES_TO_LAUNCH");
		headerDetail.add("TOTAL_TME_STORES_PLANED");
		return headerDetail;
	}
	// Added By Harsha As part of sprint 8 changes -- Ends

}