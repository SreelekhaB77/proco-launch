package com.hul.launch.controller.plan;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.hul.launch.constants.ResponseCodeConstants;
import com.hul.launch.constants.ResponseConstants;
import com.hul.launch.model.LaunchBaseplan;
import com.hul.launch.model.LaunchClusterDataCustStoreForm;
import com.hul.launch.model.LaunchClusterDataStoreForm;
import com.hul.launch.model.LaunchSellIn;
import com.hul.launch.model.LaunchVisiPlanning;
import com.hul.launch.model.TblLaunchBasebacks;
import com.hul.launch.request.AcceptByTmeRequest;
import com.hul.launch.request.AccountRequest;
import com.hul.launch.request.AccountRequestList;
import com.hul.launch.request.ClusterRequest;
import com.hul.launch.request.DownloadLaunchClusterRequest;
import com.hul.launch.request.DownloadSellInRequestList;
import com.hul.launch.request.RejectByTmeRequest;
import com.hul.launch.request.SaveFinalLaunchListRequest;
import com.hul.launch.request.SaveLaunchBasepacksListReq;
import com.hul.launch.request.SaveLaunchClustersRequest;
import com.hul.launch.request.SaveLaunchMasterRequest;
import com.hul.launch.request.SaveLaunchSellInRequestList;
import com.hul.launch.request.SaveLaunchSubmitRequest;
import com.hul.launch.request.SaveLaunchVisiPlanRequestList;
import com.hul.launch.request.StoreCountRequest;
import com.hul.launch.request.UploadKamInputs;
import com.hul.launch.response.GlobleResponse;
import com.hul.launch.response.LaunchBasePackResponse;
import com.hul.launch.response.LaunchDataResponse;
import com.hul.launch.response.LaunchFinalPlanResponse;
import com.hul.launch.response.LaunchFinalPlanResponseList;
import com.hul.launch.response.LaunchKamInputListResponse;
import com.hul.launch.response.LaunchKamInputsResponse;
import com.hul.launch.response.LaunchKamQueriesAnsweredResponse;
import com.hul.launch.response.LaunchMstnClearanceResponseCoe;
import com.hul.launch.response.LaunchMstnClearanceResponseCoeList;
import com.hul.launch.response.LaunchSellInReponse;
import com.hul.launch.response.LaunchVisiPlanList;
import com.hul.launch.response.LaunchVisiPlanResponse;
import com.hul.launch.response.ListLaunchKamQueriesAnsweredResponse;
import com.hul.launch.response.StoreDetailsResponse;
import com.hul.launch.response.SuccessResponse;
import com.hul.launch.response.SuccessResponseSaveClusters;
import com.hul.launch.response.SuccessResponseSaveLaunchDetails;
import com.hul.launch.service.LaunchBasepacksService;
import com.hul.launch.service.LaunchFinalService;
import com.hul.launch.service.LaunchSellInService;
import com.hul.launch.service.LaunchService;
import com.hul.launch.service.LaunchServiceCoe;
import com.hul.launch.service.LaunchVisiPlanService;
import com.hul.launch.service.VisibilityPlanService;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.excelreader.exom.ExOM;


/**
 * 
 * @author anshuman.shrivastava
 *
 **/

@RestController
public class TMELaunchPlanController {

	Logger logger = Logger.getLogger(TMELaunchPlanController.class);

	@Autowired
	public VisibilityPlanService visibilityPlanService;

	@Autowired
	public CreatePromoService createPromoService;

	@Autowired
	public LaunchBasepacksService launchBasepacksService;

	@Autowired
	public LaunchFinalService launchFinalPlanService;

	@Autowired
	public LaunchSellInService launchSellInService;

	@Autowired
	public LaunchService launchService;

	@Autowired
	public LaunchVisiPlanService launchVisiPlanService;
	
	@Autowired
	public LaunchServiceCoe launchServiceCoe;

	@RequestMapping(value = "getAllLaunchData.htm", method = RequestMethod.GET)
	public ModelAndView allLaunchData(HttpServletRequest request, Model model) {
		//Q1 sprint kavitha 2021
		String tmeMoc = "All";
		String launchName="All";
		List<LaunchDataResponse> listOfLaunch = null;
		try {
			//listOfLaunch = launchService.getAllLaunchData();
			String userId = (String) request.getSession().getAttribute("UserID");
			//listOfLaunch = launchService.getAllLaunchData(userId);
			listOfLaunch = launchService.getAllLaunchData(userId,tmeMoc,launchName);
			//Q1 sprint kavitha 2021
			List<String> tmemoclist=launchService.getAllMoc(userId);
			model.addAttribute("tmemoclist",tmemoclist);
			
			//Q2 sprint kavitha 2021
			List<String> tmeLaunchNamelist=launchService.getAllLaunchName(userId,tmeMoc);
			model.addAttribute("tmeLaunchNamelist",tmeLaunchNamelist);
			
			if (!listOfLaunch.isEmpty()) {
				if (null != listOfLaunch.get(0).getError()) {
					throw new Exception(listOfLaunch.get(0).getError());
				}
			}
			
			model.addAttribute("listOfLaunchData", listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
		}
		return new ModelAndView("launchplan/tme_launchplan_editapprove");
	}
	//Q1 sprint kavitha 2021
	@RequestMapping(value = "getAllLaunchtmeData.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String allLaunchtmeData(HttpServletRequest request, Model model,
			@RequestParam("tmeMoc") String tmeMoc) {
		List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
		String launchName="All";
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchService.getAllLaunchData(userId, tmeMoc,launchName);
			
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
	
	//Q2 sprint kavitha 2021
		@RequestMapping(value = "getAlltmeLaunchName.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
		public @ResponseBody String getAlltmeLaunchName(HttpServletRequest request, Model model,
				@RequestParam("tmeLaunchName") String tmeLaunchName) {
			List<LaunchDataResponse> listOfLaunch = new ArrayList<>();
			String launchName="All";
			String tmeMoc="All";
			try {
				String userId = (String) request.getSession().getAttribute("UserID");
				listOfLaunch = launchService.getAllLaunchData(userId, tmeMoc,launchName);
				
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
	
	
	@RequestMapping(value = "getEditLaunchDetails.htm", method = RequestMethod.GET)
	public ModelAndView existingLaunchDetails(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {

		Gson gson = new Gson();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}

			List<String> moc = visibilityPlanService.getAllMoc();
			List<String> storeFormat = launchBasepacksService.getStoreFormat();
			List<String> townSpcific = launchBasepacksService.getTownSpecificData();
			List<String> customerChainL1 = createPromoService.getCustomerChainL1ForLaunch();

			model.addAttribute("moc", moc);
			model.addAttribute("AllEditData", gson.toJson(launchService.getAllLaunchDataByLaunchId(launchId)));
			model.addAttribute("CustomerJson", createPromoService.getCustomerChainL2WithCluster());
			model.addAttribute("geographyJson", createPromoService.getGeography(false));
			model.addAttribute("customerChainL1", customerChainL1);
			model.addAttribute("storeFormat", storeFormat);
			model.addAttribute("townSpcific", townSpcific);
			model.addAttribute("duplicate", true);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
		}

		return new ModelAndView("launchplan/tme_launchplan");
	}

	@RequestMapping(value = "getLaunchPlanPage.htm", method = RequestMethod.GET)
	public ModelAndView mainPageForm(HttpServletRequest request, Model model) {
		try {
			List<String> moc = visibilityPlanService.getAllMoc();
			List<String> customerChainL1 = createPromoService.getCustomerChainL1ForLaunch();
			List<String> storeFormat = launchBasepacksService.getStoreFormat();
			List<String> townSpcific = launchBasepacksService.getTownSpecificData();

			model.addAttribute("moc", moc);
			model.addAttribute("CustomerJson", createPromoService.getCustomerChainL2WithCluster());
			model.addAttribute("geographyJson", createPromoService.getGeography(false));
			model.addAttribute("customerChainL1", customerChainL1);
			model.addAttribute("storeFormat", storeFormat);
			model.addAttribute("townSpcific", townSpcific);
			model.addAttribute("duplicate", true);
			model.addAttribute("AllEditData", "");
		} catch (Exception e) {
			logger.error("Exception: ", e);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
		}
		return new ModelAndView("launchplan/tme_launchplan");
	}

	@RequestMapping(value = "getLaunchVisiPlan.htm", method = RequestMethod.GET)
	public String getLaunchVisiPlanData(@RequestParam("launchId") String launchId, HttpServletRequest request,
			Model model) {
		LaunchVisiPlanList visiPlanList = new LaunchVisiPlanList();
		Gson gson = new Gson();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			List<LaunchVisiPlanResponse> listOfLaunch = null;
			List<String> listOfAssets = null;
			listOfLaunch = launchVisiPlanService.getVisiPlanLandingScreen(launchId);
			visiPlanList.setListOfVisiPlans(listOfLaunch);
			listOfAssets = launchVisiPlanService.getLaunchAssetType();
			visiPlanList.setListOfAssetType(listOfAssets);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(visiPlanList);
	}

	@RequestMapping(value = "getLaunchKamInputs.htm", method = RequestMethod.GET)
	public String getLaunchKamInputs(HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<LaunchKamInputsResponse> listOfKamInputs = null;
		LaunchKamInputListResponse launchKamInputListResponses = new LaunchKamInputListResponse();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfKamInputs = launchService.getLaunchKamInputs(userId);
			launchKamInputListResponses.setLaunchKamInputsResponses(listOfKamInputs);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(launchKamInputListResponses);
	}

	@RequestMapping(value = "getLaunchQueriesAnswered.htm", method = RequestMethod.GET)
	public String getLaunchQueriesAnswered(HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		List<LaunchKamQueriesAnsweredResponse> listQueriesAnsweredResponse = null;
		ListLaunchKamQueriesAnsweredResponse listLaunchKamQueriesAnsweredResponse = new ListLaunchKamQueriesAnsweredResponse();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listQueriesAnsweredResponse = launchService.getLaunchQueriesAnswered(userId);
			listLaunchKamQueriesAnsweredResponse.setListOfKamQueriesAnswered(listQueriesAnsweredResponse);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(listLaunchKamQueriesAnsweredResponse);
	}

	@RequestMapping(value = "rejectKamInputs.htm", method = RequestMethod.POST)
	public String rejectKamInputs(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String rejectResponse = null;
		try {
			RejectByTmeRequest rejectByTme = gson.fromJson(jsonBody, RejectByTmeRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			rejectResponse = launchService.rejectKamInputs(rejectByTme, userId);
			if (!rejectResponse.equals("Rejected Successfully")) {
				throw new Exception(rejectResponse);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(rejectResponse);
	}

	@RequestMapping(value = "acceptKamInputs.htm", method = RequestMethod.POST)
	public String acceptKamInputs(@RequestBody String jsonBody, HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		String acceptResponse = null;
		try {
			AcceptByTmeRequest acceptByTme = gson.fromJson(jsonBody, AcceptByTmeRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			acceptResponse = launchService.acceptKamInputs(acceptByTme, userId);
			if (!acceptResponse.equals("Approved Successfully")) {
				throw new Exception(acceptResponse);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(acceptResponse);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "getPsaCategory.htm", method = RequestMethod.POST)
	public @ResponseBody String getPsaCat(@RequestParam("salesCategory") String salesCategory,
			HttpServletRequest request, Model model) {
		List<TblLaunchBasebacks> launchSaleCat = new ArrayList();
		Gson gson = new Gson();
		try {
			if (salesCategory.equals("")) {
				throw new Exception("Sales category can not be blank");
			}
			launchSaleCat = launchBasepacksService.getPsaCategory(salesCategory);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(launchSaleCat);
	}

	@RequestMapping(value = "getSalesCatOnBasepack.htm", method = RequestMethod.POST)
	public @ResponseBody String getSalesCatOnBasepack(HttpServletRequest request, Model model) {
		List<String> launchSaleCat = null;
		List<String> launchBpClassification = null;
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		try {
			launchSaleCat = launchBasepacksService.getSalesCategory();
			launchBpClassification = launchBasepacksService.getBpClassification();
			map.put("SalesCategory", launchSaleCat);
			map.put("BpClassification", launchBpClassification);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			map.put("Error", e.toString());
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_BP_CLASSIFICATION_TME, map));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_BP_CLASSIFICATION_TME, map));
	}

	@RequestMapping(value = "getLaunchSellIn.htm", method = RequestMethod.GET)
	public @ResponseBody String getLaunchSellIn(@RequestParam("launchId") String launchId, HttpServletRequest request) {
		LaunchSellInReponse launchSellInReponse = null;
		Gson gson = new Gson();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			launchSellInReponse = launchSellInService.getLaunchSellIn(launchId, userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_GET_LAUNCH_SELLIN_TME, map));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_GET_LAUNCH_SELLIN_TME, launchSellInReponse));
	}

	@RequestMapping(value = "getLaunchStores.htm", method = RequestMethod.GET)
	public @ResponseBody String getLaunchStores(@RequestParam("launchId") String launchId, HttpServletRequest request) {
		StoreDetailsResponse launchSaleCat = null;
		Gson gson = new Gson();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			launchSaleCat = launchBasepacksService.getLaunchStores(launchId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(launchSaleCat);
	}

	@RequestMapping(value = "getStoreDataOnClus.htm", method = RequestMethod.POST)
	public @ResponseBody String getStoreDataOnClus(@RequestBody String jsonBody, HttpServletRequest request) {
		Map<String, Object> mapOfString = new HashMap<>();
		Gson gson = new Gson();
		try {
			ClusterRequest clusterRequest = gson.fromJson(jsonBody, ClusterRequest.class);
			String[] splittedString = clusterRequest.getCluster().split(",");
			List<String> liClusterName = new ArrayList<>();
			for (String string2 : splittedString) {
				if (null != string2) {
					if (string2.split(":").length > 1) {
						liClusterName.add(string2.split(":")[1]);
					} else {
						liClusterName.add("ALL INDIA");
					}
				}
			}
			String custList = clusterRequest.getAccount();

			String[] customers = custList.split(",");

			List<String> accountl1String = new ArrayList<>();
			List<String> accountl2String = new ArrayList<>();
			for (String string2 : customers) {
				if (null != string2) {
					String[] l1l2 = string2.split(":");
					accountl1String.add(l1l2[0]);
					if (l1l2.length > 1) {
						accountl2String.add(l1l2[1]);
					}
				}
			}
			LaunchDataResponse launchDataResponse = launchService.getSpecificLaunchData(clusterRequest.getLaunchId());
			//Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
			//String storeCount = launchBasepacksService.getStoreCountByClass(liClusterName, accountl1String, accountl2String, launchDataResponse.getClassification());
			String storeCount = launchBasepacksService.getStoreCountByClass(liClusterName, accountl1String,
					accountl2String, launchDataResponse.getClassification(), clusterRequest.isIscustomstoreformatChecked());
			mapOfString.put("storeCount", storeCount);
			List<String> listOfStores = launchBasepacksService.getLaunchStores(liClusterName, accountl1String,
					accountl2String, launchDataResponse.getClassification()
					, clusterRequest.isIscustomstoreformatChecked());  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
			mapOfString.put("listOfStores", listOfStores);
			mapOfString.put("listCustomerStore", launchBasepacksService.getCustomerStoreFormat(liClusterName,
					accountl1String, accountl2String, launchDataResponse.getClassification()
					, clusterRequest.isIscustomstoreformatChecked()));  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(mapOfString);
	}

	@RequestMapping(value = "getStoreData.htm", method = RequestMethod.POST)
	public @ResponseBody String getStoreData(@RequestBody String jsonBody, HttpServletRequest request) {
		Map<String, Object> mapOfString = new HashMap<>();
		Gson gson = new Gson();
		try {
			AccountRequestList account = gson.fromJson(jsonBody, AccountRequestList.class);
			List<AccountRequest> listAccount = account.getListOfAccounts();
			Set<String> accountL1Set = new HashSet<>();
			List<AccountRequest> accountL1 = new ArrayList<>();
			for (AccountRequest string2 : listAccount) {
				if (null != string2) {
					AccountRequest accountRequest = new AccountRequest();
					accountRequest.setAccount(string2.getAccount().split(":")[0]);
					accountL1Set.add(string2.getAccount().split(":")[0]);
					accountL1.add(accountRequest);
				}
			}
			List<String> aList = new ArrayList<String>();
			aList.addAll(accountL1Set);
			String storeCount = launchBasepacksService.getStoreCount(accountL1);
			mapOfString.put("storeCount", storeCount);

			List<String> listOfStores = launchBasepacksService.getLaunchStores(aList);
			mapOfString.put("listOfStores", listOfStores);
			mapOfString.put("listCustomerStore", launchBasepacksService.getCustomerStoreFormat(listOfStores));
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(mapOfString);
	}

	@RequestMapping(value = "getStoreDataOnStore.htm", method = RequestMethod.POST)
	public @ResponseBody String getStoreDataOnStore(@RequestBody String jsonBody, HttpServletRequest request) {
		Map<String, Object> mapOfString = new HashMap<>();
		Gson gson = new Gson();
		try {
			StoreCountRequest account = gson.fromJson(jsonBody, StoreCountRequest.class);
			String custList = account.getAccountList();

			String[] customers = custList.split(",");
			List<String> accountl1String = new ArrayList<>();
			List<String> accountl2String = new ArrayList<>();
			for (String customerStr : customers) {
				if (null != customerStr) {
					String[] l1l2 = customerStr.split(":");
					accountl1String.add(l1l2[0]);
					if (l1l2.length > 1) {
						accountl2String.add(l1l2[1]);
					}
				}
			}

			String[] splittedString = account.getCluster().split(",");
			List<String> liClusterName = new ArrayList<>();
			for (String string2 : splittedString) {
				if (null != string2) {
					if (string2.split(":").length > 1) {
						liClusterName.add(string2.split(":")[1]);
					} else {
						liClusterName.add("ALL INDIA");
					}
				}
			}

			String storeCount = null;
			LaunchDataResponse launchDataResponse = launchService.getSpecificLaunchData(account.getLaunchId());
			if (account.getCustStoreFormat().equals("") && account.getStoreFormat().equals("")) {
				throw new Exception("Both parameters can not be null");
			} else if (!account.getCustStoreFormat().equals("") && account.getStoreFormat().equals("")) {
				storeCount = launchBasepacksService.getStoreCountOnCust(account.getCustStoreFormat(), accountl1String,
						accountl2String, liClusterName, launchDataResponse.getClassification(), account.isIscustomstoreformatChecked());  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
			} else if (account.getCustStoreFormat().equals("") && !account.getStoreFormat().equals("")) {
				storeCount = launchBasepacksService.getStoreCountOnStore(account.getStoreFormat(), accountl1String,
						accountl2String, liClusterName, launchDataResponse.getClassification(), account.isIscustomstoreformatChecked());  //Sarin Changes - Q1Sprint Feb2021 - Include All StoreFormats based on Custom Store Selection
			} else {
				throw new Exception("Please Send single parameter");
			}
			if (storeCount.contains("exception")) {
				throw new Exception(storeCount);
			} else {
				mapOfString.put("storeCount", storeCount);
			}

		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(mapOfString);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "getBrandOnPsaCat.htm", method = RequestMethod.POST)
	public @ResponseBody String getBrands(@RequestParam("psaCategory") String psaCategory, @RequestParam("salesCategory") String salesCategory, HttpServletRequest request,
			Model model) {
		List<TblLaunchBasebacks> launchSaleBrand = new ArrayList();
		Gson gson = new Gson();
		try {
			if (psaCategory.equals("")) {
				throw new Exception("PSA category can not be null");
			} else if (salesCategory.equals("")) {
				throw new Exception("Sales Category can not be null");
			}
			launchSaleBrand = launchBasepacksService.getBrandPsaCategory(psaCategory, salesCategory);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(launchSaleBrand);
	}

	@RequestMapping(value = "getAllMstnClearanceTme.htm", method = RequestMethod.GET)
	public String getAllMstnClearanceTme(HttpServletRequest request, Model model) {
		Gson gson = new Gson();
		LaunchMstnClearanceResponseCoeList getLaunchMstnClearanceResponseCoe = new LaunchMstnClearanceResponseCoeList();
		List<LaunchMstnClearanceResponseCoe> listOfLaunch = new ArrayList<>();
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfLaunch = launchServiceCoe.getMstnClearanceByLaunchIdTme(userId);
			getLaunchMstnClearanceResponseCoe.setLaunchMstnClearanceResponseCoeList(listOfLaunch);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_BUILD_UP_KAM, e.toString()));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
				ResponseCodeConstants.STATUS_SUCCESS_MSTN_CLEARED_GET_KAM, getLaunchMstnClearanceResponseCoe));
	}
	
	@RequestMapping(value = "getLaunchFinalPlan.htm", method = RequestMethod.GET)
	public @ResponseBody String getLaunchFinalPlan(@RequestParam("launchId") String launchId,
			HttpServletRequest request) {
		LaunchFinalPlanResponseList launchFinalPlanResponseList = new LaunchFinalPlanResponseList();
		List<LaunchFinalPlanResponse> listOfFinalPlans = new ArrayList<>();
		Gson gson = new Gson();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			String userId = (String) request.getSession().getAttribute("UserID");
			listOfFinalPlans = launchFinalPlanService.getLaunchFinalRespose(launchId, userId);
			launchFinalPlanResponseList.setListOfFinalPlans(listOfFinalPlans);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(launchFinalPlanResponseList);
	}

	@RequestMapping(value = "getBasePackData.htm", method = RequestMethod.GET)
	public @ResponseBody String getBasePackData(@RequestParam("basepackCode") String basepackCode,
			HttpServletRequest request) {
		List<LaunchBasePackResponse> bacePackData = new ArrayList<>();
		Gson gson = new Gson();
		try {
			if (basepackCode.equals("")) {
				throw new Exception("Basepack code can not be null");
			}
			bacePackData = launchBasepacksService.getLaunchBasePackDetails(basepackCode);
			if (!bacePackData.isEmpty()) {
				return gson.toJson(bacePackData.get(0));
			} else {
				return gson.toJson(new ArrayList<>());
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}

	}

	@RequestMapping(value = "getLaunchData.htm", method = RequestMethod.GET)
	public String getLaunchData(@RequestParam("launchId") String launchId, HttpServletRequest request, Model model) {
		LaunchVisiPlanList visiPlanList = new LaunchVisiPlanList();
		Gson gson = new Gson();
		Map<String, LaunchDataResponse> map = new HashMap<>();
		try {
			if (launchId.equals("")) {
				throw new Exception("Launch Id can not be null");
			}
			LaunchDataResponse launchDataResonse = launchService.getSpecificLaunchData(launchId);
			map.put("launchData", launchDataResonse);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			LaunchDataResponse launchDataResonse = new LaunchDataResponse();
			launchDataResonse.setError(e.toString());
			map.put("Error", launchDataResonse);
			return gson.toJson(map);
		}
		return gson.toJson(visiPlanList);
	}

	@RequestMapping(value = "saveLaunchDetails.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchDetails(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponseSaveLaunchDetails successResponseSaveLaunchDetails = new SuccessResponseSaveLaunchDetails();
		String result = "";
		Gson gson = new Gson();
		try {
			SaveLaunchMasterRequest tblLaunchMaster = gson.fromJson(jsonBody, SaveLaunchMasterRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			if (tblLaunchMaster.getLaunchId().equals("")) {
				result = launchBasepacksService.saveLaunchDetails(tblLaunchMaster, userId);
			} else {
				result = launchBasepacksService.updateLaunchDetails(tblLaunchMaster, userId);
			}

			if (result.contains("Exception")) {
				throw new Exception(result);
			} else {
				if (Integer.parseInt(result) > 0) {
					successResponseSaveLaunchDetails.setLaunchId(result);
					launchService.saveLaunchStatus(result, userId);
					launchService.deleteAllNextPageData(result, "Launch_details", userId);
				}
			}
		} catch (Exception e) {
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponseSaveLaunchDetails);
	}

	@RequestMapping(value = "saveLaunchClustersAndAccounts.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchClustersAndAccounts(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponseSaveClusters successResponseSaveClusters = new SuccessResponseSaveClusters();
		int result = 0;
		Gson gson = new Gson();
		try {
			SaveLaunchClustersRequest saveLaunchClustersRequest = gson.fromJson(jsonBody,
					SaveLaunchClustersRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchBasepacksService.saveLaunchClustersAndAcc(saveLaunchClustersRequest, userId);
			if (result > 0) {
				successResponseSaveClusters.setClusterId(Integer.toString(result));
				launchService.updateLaunchStatus("LAUNCH_STORES", userId, saveLaunchClustersRequest.getLaunchId());
				launchService.deleteAllNextPageData(saveLaunchClustersRequest.getLaunchId(), "Launch_clusters", userId);
			}
		} catch (Exception e) {
			result = 0;
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponseSaveClusters);
	}

	@RequestMapping(value = "saveLaunchBasepack.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchBasepacks(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		int result = 0;
		try {
			SaveLaunchBasepacksListReq tblLaunchbasePacks = gson.fromJson(jsonBody, SaveLaunchBasepacksListReq.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchBasepacksService.saveLaunchBasepacks(tblLaunchbasePacks, userId);
			if (result == 1) {
				successResponse.setResponseMessage("Saved Successfully");
				launchService.updateLaunchStatus("LAUNCH_BASEPACKS", userId, tblLaunchbasePacks.getLaunchId());
				launchService.deleteAllNextPageData(tblLaunchbasePacks.getLaunchId(), "Launch_basepacks", userId);
			} else {
				successResponse.setResponseMessage("Didn't Save Successfully");
				throw new Exception("Something went wrong,Didn't Save Successfully");
			}
		} catch (Exception e) {
			result = 0;
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponse);
	}

	@RequestMapping(value = "saveLaunchSellIn.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchSellIn(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			SaveLaunchSellInRequestList saveLaunchSellInRequest = gson.fromJson(jsonBody,
					SaveLaunchSellInRequestList.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchSellInService.saveLaunchSellIn(saveLaunchSellInRequest, userId);
			if (result.equals("Saved Successfully")) {
				successResponse.setResponseMessage(result);
				launchService.updateLaunchStatus("LAUNCH_SELL_IN", userId, saveLaunchSellInRequest.getLaunchId());
				launchService.deleteAllNextPageData(saveLaunchSellInRequest.getLaunchId(), "Launch_sellIn", userId);
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

	@RequestMapping(value = "saveLaunchVisiPlan.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchVisiPlan(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			SaveLaunchVisiPlanRequestList saveLaunchVisiPlanRequest = gson.fromJson(jsonBody,
					SaveLaunchVisiPlanRequestList.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchVisiPlanService.saveLaunchVisiPlan(saveLaunchVisiPlanRequest, userId);
			if (result.equals("Saved Successfully")) {
				successResponse.setResponseMessage(result);
				launchService.updateLaunchStatus("LAUNCH_VISI_PLANNING", userId,
						Integer.toString(saveLaunchVisiPlanRequest.getLaunchId()));
				launchService.deleteAllNextPageData(Integer.toString(saveLaunchVisiPlanRequest.getLaunchId()),
						"Launch_visiplan", userId);
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

	@RequestMapping(value = "{launchId}/saveLaunchNoVisiPlan.htm", method = RequestMethod.GET)
	public @ResponseBody String saveLaunchNoVisiPlan(@PathVariable("launchId") String launchId,
			HttpServletRequest request, HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchVisiPlanService.saveLaunchNoVisiPlan(launchId, userId);
			if (result.equals("Saved Successfully")) {
				successResponse.setResponseMessage(result);
				launchService.updateLaunchStatus("LAUNCH_VISI_PLANNING", userId, launchId);
				launchService.deleteAllNextPageData(launchId, "Launch_visiplan", userId);
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

	@RequestMapping(value = "saveLaunchFinal.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchFinal(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		String result = "";
		try {
			SaveFinalLaunchListRequest saveFinalLaunchListRequest = gson.fromJson(jsonBody,
					SaveFinalLaunchListRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchFinalPlanService.saveLaunchFinalBuildUp(saveFinalLaunchListRequest, userId);
			if (result.equals("Saved Successfully")) {
				launchService.deleteAllNextPageData(saveFinalLaunchListRequest.getLaunchId(), "Launch_buildUp", userId);
				successResponse.setResponseMessage(result);
				launchService.updateLaunchStatus("LAUNCH_FINAL_BUILDUP", userId,
						saveFinalLaunchListRequest.getLaunchId());
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

	@RequestMapping(value = "saveLaunchSubmit.htm", method = RequestMethod.POST)
	public @ResponseBody String saveLaunchSubmit(@RequestBody String jsonBody, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		SuccessResponse successResponse = new SuccessResponse();
		Gson gson = new Gson();
		Map<String, String> result = null;
		try {
			SaveLaunchSubmitRequest saveLaunchSubmitRequest = gson.fromJson(jsonBody, SaveLaunchSubmitRequest.class);
			String userId = (String) request.getSession().getAttribute("UserID");
			result = launchService.saveLaunchSubmit(saveLaunchSubmitRequest, userId);
			if (result.containsKey("Error")) {
				throw new Exception(result.get("Error"));
			} else if (result.containsKey("Success")) {
				launchService.deleteAllKamData(saveLaunchSubmitRequest.getLaunchId());
				successResponse.setResponseMessage(result.get("Success"));
				launchService.updateLaunchStatus("LAUNCH_SUBMIT", userId, saveLaunchSubmitRequest.getLaunchId());
			}
			successResponse.setResponseMessage(result.get("Success"));
		} catch (Exception e) {
			logger.error("Exception: " + e);
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		return gson.toJson(successResponse);
	}

	@RequestMapping(value = "downloadLaunchBasepackTemplate.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadLaunchBasepackTemplate(Model model, HttpServletRequest request, //Sarin
	public @ResponseBody String downloadLaunchBasepackTemplate(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Basepack.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
		try {
			ArrayList<String> headerDetail = getBasepackHeaders();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			List<ArrayList<String>> listDownload = launchBasepacksService.getbasepackDump(headerDetail, userId);
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

	@RequestMapping(value = "downloadClusterTemplateforStoreformat.htm", method = RequestMethod.POST)
	public @ResponseBody String downloadClusterTemplateforStoreformat(@RequestBody String jsonBody, Model model,
			HttpServletRequest request) {
		String absoluteFilePath = "";
		Gson gson = new Gson();
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		DownloadLaunchClusterRequest downloadLaunchClusterRequest = gson.fromJson(jsonBody,
				DownloadLaunchClusterRequest.class);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Cluster.StoreFormat.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		ArrayList<String> headerDetail = getClusterHeadersforStoreFormat();
		downloadedData.add(headerDetail);
		String userId = (String) request.getSession().getAttribute("UserID");
		List<ArrayList<String>> listDownload = launchBasepacksService.getClusterDumpForStoreFormat(headerDetail, userId,
				downloadLaunchClusterRequest);
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
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

	@RequestMapping(value = "downloadKamInputs.htm", method = RequestMethod.GET)
	//public @ResponseBody ModelAndView downloadKamInputs(Model model, HttpServletRequest request,  //Sarin
	public @ResponseBody String downloadKamInputs(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		String absoluteFilePath = "";
		String downloadLink = "";
		InputStream is;
		ModelAndView modelAndView = new ModelAndView();
		
			List<ArrayList<String>> downloadedData = new ArrayList<>();
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("KamQueriesList.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
		try {
			ArrayList<String> headerDetail = getKamInputsDownload();
			downloadedData.add(headerDetail);
			String userId = (String) request.getSession().getAttribute("UserID");
			List<ArrayList<String>> listDownload = launchBasepacksService.getKamInputDumpForLaunch(headerDetail,
					userId);
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=KamQueriesListTemplate"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}

		} catch (Exception e) {
			logger.error("Exception: ", e);
			/*
			modelAndView.addObject("Error", e.toString());
			return modelAndView;
			*/
			Map<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return gson.toJson(map);
		}
		//return modelAndView;
		Map<String, String> map = new HashMap<>();
		map.put("FileToDownload", fileName);
		return gson.toJson(map);
	}

	@RequestMapping(value = "downloadClusterTempforCustStoreformat.htm", method = RequestMethod.POST)
	public @ResponseBody String downloadClusterTemplateforCustomerStoreformat(@RequestBody String jsonBody, Model model,
			HttpServletRequest request) {
		String absoluteFilePath = "";
		Gson gson = new Gson();
		List<ArrayList<String>> downloadedData = new ArrayList<>();
		DownloadLaunchClusterRequest downloadLaunchClusterRequest = gson.fromJson(jsonBody,
				DownloadLaunchClusterRequest.class);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Cluster.CustStoreFormat.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		ArrayList<String> headerDetail = getClusterHeadersforCustomerStoreformat();
		downloadedData.add(headerDetail);
		String userId = (String) request.getSession().getAttribute("UserID");
		List<ArrayList<String>> listDownload = launchBasepacksService.getClusterDumpforCustomerStoreformat(headerDetail,
				userId, downloadLaunchClusterRequest);
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
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

	@RequestMapping(value = "downloadSellInTemplate.htm", method = RequestMethod.POST)
	public @ResponseBody String downloadSellInTemplate(@RequestBody String jsonBody, Model model,
			HttpServletRequest request) {
		String absoluteFilePath = "";
		Gson gson = new Gson();
		DownloadSellInRequestList downloadLaunchSellInRequest = gson.fromJson(jsonBody,
				DownloadSellInRequestList.class);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("SellIn.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		String userId = (String) request.getSession().getAttribute("UserID");
		List<ArrayList<String>> listDownload = launchSellInService.getSellInDump(userId, downloadLaunchSellInRequest);
		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
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

	@RequestMapping(value = "downloadVisiPlanTemplate.htm", method = RequestMethod.POST)
	public @ResponseBody String downloadVisiPlanTemplate(@RequestBody String jsonBody, Model model,
			HttpServletRequest request) {
		String absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		Gson gson = new Gson();
		SaveLaunchVisiPlanRequestList downloadLaunchVisiPlanRequest = gson.fromJson(jsonBody,
				SaveLaunchVisiPlanRequestList.class);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("VisiPlan.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		ArrayList<String> headerDetail = getVisiPlansHeaders();
		downloadedData.add(headerDetail);
		String userId = (String) request.getSession().getAttribute("UserID");
		List<ArrayList<String>> listDownload = launchVisiPlanService.getVisiPlanDump(headerDetail, userId,
				downloadLaunchVisiPlanRequest);

		try {
			if (listDownload != null) {
				UploadUtil.writeXLSFile(downloadFileName, listDownload, null, ".xls");
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

	@RequestMapping(value = "{launchId}/downloadFinalBuildUpTemplate.htm", method = RequestMethod.GET)
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
			List<ArrayList<String>> listDownload = launchFinalPlanService.getFinalBuildUpDumpNew(userId, launchId);
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

	@RequestMapping(value = "{fileName}/downloadFileTemplate.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadSellInTemplateGet(@PathVariable("fileName") String fileName, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";

		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String downloadFileName = absoluteFilePath + fileName;
		Gson gson = new Gson();
		try {
			if (fileName.equals("")) {
				throw new Exception("File name can not be blank");
			}
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
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

	@RequestMapping(value = "{launchId}/uploadLaunchBasepack.htm", method = RequestMethod.POST)
	public String uploadLaunchBasepack(@PathVariable("launchId") String launchId, MultipartHttpServletRequest multi,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String successMessage = "";
		Boolean flg = true;
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
					flg = false;
					successMessage = "File size exceeded";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(LaunchBaseplan.class)
								.map(11, false, null);
						if (map.isEmpty()) {
							flg = false;
							successMessage = "File does not contain data";
						}
						if (map.containsKey("ERROR")) {
							flg = false;
							List<Object> errorList = map.get("ERROR");
							successMessage = (String) errorList.get(0);
							//savedData = "ERROR";
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchBasepacksService.saveBasepackByUpload(list, userID, "CREATED BY TME",
									true, false, launchId);
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								successMessage = commUtils.getProperty("File.Upload.Success");
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								flg = false;
								successMessage = "File Uploaded with errors";
							} else if (savedData != null && savedData.equals("ERROR")) {
								flg = false;
								successMessage = "Error while uploading file";
							} else {
								flg = false;
								successMessage = savedData;
							}

						}
					}
				}
			} else {
				flg = false;
				successMessage = commUtils.getProperty("File.Empty");
			}
			if (savedData.equals("ERROR_FILE")) {
				flg = false;
				successMessage = "ERROR_FILE";
			} else if (savedData.equals("ERROR")) {
				flg = false;
				successMessage = "File Upload is UnSuccessful.";
			} else if (!savedData.equals("Basepack Code can not repeat")
					&& !savedData.equals("Basepack Description can not repeat")) {
				successMessage = "SUCCESS_FILE";
			}
		} catch (Exception e) {
			flg = false;
			logger.error("Exception: ", e);
			mapResponse.put("Error", e.toString());
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_UPLOAD_LAUNCH_BASEPACK_TME, e.toString()));
		} catch (Throwable e) {
			flg = false;
			logger.error("Exception: ", e);
			mapResponse.put("Error", e.toString());
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
					ResponseCodeConstants.STATUS_FAILURE_UPLOAD_LAUNCH_BASEPACK_TME, e.toString()));
		}

		if (flg) {
			return gson.toJson(new GlobleResponse(ResponseConstants.MSG_SUCCESS_RESPONSE,
					ResponseCodeConstants.STATUS_SUCCESS_UPLOAD_LAUNCH_BASEPACK_TME, successMessage));
		}
		return gson.toJson(new GlobleResponse(ResponseConstants.MSG_FAILURE_RESPONSE,
				ResponseCodeConstants.STATUS_FAILURE_UPLOAD_LAUNCH_BASEPACK_TME, successMessage));
	}

	@RequestMapping(value = "{launchId}/{isReUpload}/uploadAnnexureDoc.htm", method = RequestMethod.POST)
	public String uploadAnnexureDoc(@PathVariable("launchId") String launchId,
			@PathVariable("isReUpload") String isReUpload, MultipartHttpServletRequest multi, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String successMessage = "";
		Gson gson = new Gson();
		Map<String, String> mapResponse = new HashMap<>();
		try {
			CommonPropUtils commUtils = CommonPropUtils.getInstance();
			String userID = (String) request.getSession().getAttribute("UserID");
			MultipartFile file = multi.getFile("file");
			LaunchDataResponse launchDataResonse = launchService.getSpecificLaunchData(launchId);
			String fileName = file.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

			if (!("Annexure_".concat(launchDataResonse.getLaunchName().concat("." + fileExt)).equals(fileName))) {
				mapResponse.put("Error", "Name format mismatch");
				mapResponse.put("UploadedFileName", fileName);
				mapResponse.put("Format", "Annexure_{Launch_Name}");
				throw new Exception("Name format mismatch");
			}

			boolean isUpload = false;
			if (isReUpload.equals("0")) {
				if ((launchDataResonse.getAnnexureDocName() != null)
						&& (!launchDataResonse.getAnnexureDocName().equals(""))) {
					mapResponse.put("Error", "Document already uploaded");
					mapResponse.put("UploadedFileName", fileName);
					return gson.toJson(mapResponse);
				} else {
					isUpload = true;
				}
			} else if (isReUpload.equals("1")) {
				isUpload = true;
			} else {
				throw new Exception("Value of isReUpload can be either 1 or 0");
			}
			boolean isSuccess = false;
			if (isUpload) {
				List<String> extList = new ArrayList<>();
				extList.add("pdf");
				extList.add("xlsx");
				extList.add("xls");
				extList.add("docx");
				extList.add("doc");
				extList.add("pptx");

				if (extList.contains(fileExt)) {
					if (!CommonUtils.isFileEmpty(file)) {
						if (CommonUtils.isFileSizeExceeds(file)) {
							throw new Exception("File size exceeded");
						} else {
							String filePath = FilePaths.LAUNCH_ANNEXURE_UPLOAD_FILE_PATH;
							String appendString = File.separator;
							filePath = filePath + appendString;
							UploadUtil.createFolder(filePath);
							filePath = filePath + fileName;
							UploadUtil.movefile(file, filePath);
							isSuccess = true;
						}
					} else {
						throw new Exception(commUtils.getProperty("File.Empty"));
					}
					if (isSuccess) {
						successMessage = "SUCCESS_FILE";
						launchDataResonse.setAnnexureDocName(fileName);
						launchService.updateLaunchData(launchDataResonse, userID);
					} else {
						throw new Exception("An exception accured while uploading");
					}
				} else {
					throw new Exception("Invalid Document");
				}

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

	@RequestMapping(value = "{launchId}/uploadArtWorkDoc.htm", method = RequestMethod.POST)
	public String uploadArtWorkDoc(@PathVariable("launchId") String launchId,
			@RequestParam("file") List<MultipartFile> files, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String successMessage = "";
		Gson gson = new Gson();
		Map<String, String> mapResponse = new HashMap<>();
		try {
			CommonPropUtils commUtils = CommonPropUtils.getInstance();
			String userID = (String) request.getSession().getAttribute("UserID");
			LaunchDataResponse launchDataResonse = launchService.getSpecificLaunchData(launchId);
			String fileNameToUpdate = "";
			List<String> listOfBasePacks = launchBasepacksService.getBasepackCodeOnLaunchId(launchId);

			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

				String[] bpCode = fileName.split("_");

				if (listOfBasePacks.contains(bpCode[0])) {
					if (!(bpCode[0] + "_" + launchDataResonse.getLaunchName()).concat("." + fileExt).equals(fileName)) {
						mapResponse.put("Error", "Name format mismatch");
						mapResponse.put("UploadedFileName", fileName);
						mapResponse.put("Format", "{Basepack_Code}_{Launch_Name}");
						return gson.toJson(mapResponse);
					}
				} else {
					mapResponse.put("Error", "Name format mismatch");
					mapResponse.put("UploadedFileName", fileName);
					mapResponse.put("Format", "{Basepack_Code}_{Launch_Name}");
					return gson.toJson(mapResponse);
				}
			}

			for (MultipartFile file : files) {
				boolean isSuccess = false;
				String fileName = file.getOriginalFilename();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
				List<String> extList = new ArrayList<>();
				extList.add("PDF");
				extList.add("DOC");
				extList.add("DOCX");
				extList.add("PPTX");
				extList.add("JPEG");
				extList.add("JPG");
				extList.add("PNG");

				if (extList.contains(fileExt.toUpperCase())) {
					if (!CommonUtils.isFileEmpty(file)) {
						if (CommonUtils.isFileSizeExceeds(file)) {
							throw new Exception("File size exceeded");
						} else {
							String filePath = FilePaths.LAUNCH_ARTWORK_UPLOAD_FILE_PATH;
							String appendString = File.separator;
							filePath = filePath + appendString;
							UploadUtil.createFolder(filePath);
							filePath = filePath + fileName;
							UploadUtil.movefile(file, filePath);
							isSuccess = true;
						}
					} else {
						throw new Exception(commUtils.getProperty("File.Empty"));
					}
					if (isSuccess) {
						successMessage = "SUCCESS_FILE";
						fileNameToUpdate = fileNameToUpdate + fileName + ",";
						launchDataResonse.setArtWorkPackShotsDocName(fileNameToUpdate);
						launchService.updateArdWorkLaunchData(launchDataResonse, userID);
					} else {
						throw new Exception("An exception accured while uploading");
					}
				} else {
					throw new Exception("Invalid Document");
				}
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

	@RequestMapping(value = "{launchId}/{isReUpload}/uploadMdgDoc.htm", method = RequestMethod.POST)
	public String uploadMdgDoc(@PathVariable("launchId") String launchId, @PathVariable("isReUpload") String isReUpload,
			MultipartHttpServletRequest multi, Model model, HttpServletRequest request, HttpServletResponse response) {
		String successMessage = "";
		Gson gson = new Gson();
		Map<String, String> mapResponse = new HashMap<>();
		try {
			CommonPropUtils commUtils = CommonPropUtils.getInstance();
			String userID = (String) request.getSession().getAttribute("UserID");
			MultipartFile file = multi.getFile("file");
			LaunchDataResponse launchDataResonse = launchService.getSpecificLaunchData(launchId);
			String fileName = file.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			String newFileName = "MDG_deck_" + launchDataResonse.getLaunchName() + "." + fileExt;
			boolean isUpload = false;
			if (!("MDG_deck_".concat(launchDataResonse.getLaunchName().concat("." + fileExt)).equals(fileName))) {
				mapResponse.put("Error", "Name format mismatch");
				mapResponse.put("UploadedFileName", fileName);
				mapResponse.put("Format", "Annexure_{Launch_Name}");
				throw new Exception("Name format mismatch");
			} else {
				isUpload = true;
			}

			boolean isSuccess = false;
			if (isUpload) {
				List<String> extList = new ArrayList<>();
				extList.add("xlsx");
				extList.add("xls");
				extList.add("pdf");
				extList.add("docx");
				extList.add("pptx");

				if (extList.contains(fileExt)) {
					if (!CommonUtils.isFileEmpty(file)) {
						if (CommonUtils.isFileSizeExceeds(file)) {
							throw new Exception("File size exceeded");
						} else {
							String filePath = FilePaths.LAUNCH_MDG_UPLOAD_FILE_PATH;
							String appendString = File.separator;
							filePath = filePath + appendString;
							UploadUtil.createFolder(filePath);
							filePath = filePath + newFileName;
							UploadUtil.movefile(file, filePath);
							isSuccess = true;
						}
					} else {
						throw new Exception(commUtils.getProperty("File.Empty"));
					}
					if (isSuccess) {
						successMessage = "SUCCESS_FILE";
						launchDataResonse.setMdgDeckDocName(fileName);
						launchService.updateMdgDocData(launchDataResonse, userID);
					} else {
						throw new Exception("An exception accured while uploading");
					}
				} else {
					throw new Exception("Invalid Document");
				}

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

	@RequestMapping(value = "{launchId}/uploadMdgDoc.htm", method = RequestMethod.POST)
	public String uploadMdgDoc(@PathVariable("launchId") String launchId, MultipartHttpServletRequest multi,
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
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(LaunchBaseplan.class)
								.map(11, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchBasepacksService.saveBasepackByUpload(list, userID, "CREATED BY TME",
									true, false, launchId);
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

	@RequestMapping(value = "{launchId}/uploadLaunchClusterForStoreForm.htm", method = RequestMethod.POST)
	public String uploadLaunchClusterForStoreForm(@PathVariable("launchId") String launchId,
			MultipartHttpServletRequest multi, Model model, HttpServletRequest request, HttpServletResponse response) {
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
								.to(LaunchClusterDataStoreForm.class).map(5, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchBasepacksService.saveClusterByUpload(list, userID, "CREATED BY TME", true,
									false, launchId);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
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

	@RequestMapping(value = "{launchId}/uploadLaunchClusterForCustStoreForm.htm", method = RequestMethod.POST)
	public String uploadLaunchClusterForCustStoreForm(@PathVariable("launchId") String launchId,
			MultipartHttpServletRequest multi, Model model, HttpServletRequest request, HttpServletResponse response) {
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
								.to(LaunchClusterDataCustStoreForm.class).map(5, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchBasepacksService.saveClusterByUploadForCluster(list, userID,
									"CREATED BY TME", true, false, launchId);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
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

	@RequestMapping(value = "uploadKamRequests.htm", method = RequestMethod.POST)
	public String uploadKamRequests(MultipartHttpServletRequest multi, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userID = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = multi.getFile("file");

		String filepath = FilePaths.LAUNCH_KAM_REQUESTS_UPLOAD;
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
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(UploadKamInputs.class)
								.map(10, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchBasepacksService.saveKamRequestByUpload(list, userID);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
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

	@RequestMapping(value = "{launchId}/uploadLaunchSellIn.htm", method = RequestMethod.POST)
	public String uploadLaunchSellIn(@PathVariable("launchId") String launchId, MultipartHttpServletRequest multi,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userID = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = multi.getFile("file");
		Map<String, String> apiResponse = new HashMap<>();
		Gson gson = new Gson();
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		String fileName = file.getOriginalFilename();
		fileName = filepath + fileName;
		String successMessage = "";
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					successMessage = "File size exceeded";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(LaunchSellIn.class)
								.map(21, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchSellInService.saveSellInByUpload(list, userID, "CREATED BY TME", true,
									false, launchId);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
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

	@RequestMapping(value = "{launchId}/uploadLaunchVisiPlaning.htm", method = RequestMethod.POST)
	public String uploadLaunchVisiPlan(@PathVariable("launchId") String launchId, MultipartHttpServletRequest multi,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userID = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = multi.getFile("file");
		Map<String, String> apiResponse = new HashMap<>();
		Gson gson = new Gson();
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		String fileName = file.getOriginalFilename();
		fileName = filepath + fileName;
		String successMessage = "";
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					successMessage = "File size exceeded";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(LaunchVisiPlanning.class).map(19, false, null);
						if (map.isEmpty()) {
							throw new Exception("File does not contain data");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							throw new Exception((String) errorList.get(0));
						} else if (map.containsKey("DATA")) {
							List<Object> list = map.get("DATA");
							savedData = launchVisiPlanService.saveVisiPlanByUpload(list, userID, "CREATED BY TME", true,
									false, launchId);
							if (!savedData.contains("Exception")) {
								if (savedData != null && savedData.equals("SUCCESS_FILE")) {
									successMessage = commUtils.getProperty("File.Upload.Success");
								} else if (savedData != null && savedData.equals("ERROR_FILE")) {
									throw new Exception("File Uploaded with errors");
								} else if (savedData != null && savedData.equals("ERROR")) {
									throw new Exception("Error while uploading file");
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

	private ArrayList<String> getBasepackHeaders() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("SALESCATEGORY");
		headerDetail.add("PSACATEGORY");
		headerDetail.add("BRAND");
		headerDetail.add("CODE");
		headerDetail.add("DESCRIPTION");
		headerDetail.add("MRP");
		headerDetail.add("TUR");
		headerDetail.add("GSV");
		headerDetail.add("CLDCONFIG");
		headerDetail.add("GRAMMAGE");
		headerDetail.add("CLASSIFICATION");
		return headerDetail;
	}

	private ArrayList<String> getClusterHeadersforCustomerStoreformat() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Cluster");
		headerDetail.add("Account_L1");
		headerDetail.add("Account_L2");
		headerDetail.add("Customer_Store_Format");
		headerDetail.add("Launch_planned");
		return headerDetail;
	}

	private ArrayList<String> getClusterHeadersforStoreFormat() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Cluster");
		headerDetail.add("Account_L1");
		headerDetail.add("Account_L2");
		headerDetail.add("Store_Format");
		headerDetail.add("Launch_planned");
		return headerDetail;
	}

	private ArrayList<String> getKamInputsDownload() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("Req_Id");
		headerDetail.add("Launch_Name");
		headerDetail.add("Launch_MOC");
		headerDetail.add("Account");
		headerDetail.add("Name");
		headerDetail.add("Changes_Requested");
		headerDetail.add("Kam_Remarks");
		headerDetail.add("Requested_Date");
		headerDetail.add("Tme_Remarks");
		headerDetail.add("Action_To_Take");
		return headerDetail;
	}

	private ArrayList<String> getVisiPlansHeaders() {
		ArrayList<String> headerDetail = new ArrayList<>();
		headerDetail.add("ACCOUNT");
		headerDetail.add("FORMAT");
		headerDetail.add("STORES_AVAILABLE");
		headerDetail.add("STORES_PLANNED");
		headerDetail.add("VISI_ASSET_1");
		headerDetail.add("FACING_PER_SHELF_PER_SKU1");
		headerDetail.add("DEPTH_PER_SHELF_PER_SKU1");
		headerDetail.add("VISI_ASSET_2");
		headerDetail.add("FACING_PER_SHELF_PER_SKU2");
		headerDetail.add("DEPTH_PER_SHELF_PER_SKU2");
		headerDetail.add("VISI_ASSET_3");
		headerDetail.add("FACING_PER_SHELF_PER_SKU3");
		headerDetail.add("DEPTH_PER_SHELF_PER_SKU3");
		headerDetail.add("VISI_ASSET_4");
		headerDetail.add("FACING_PER_SHELF_PER_SKU4");
		headerDetail.add("DEPTH_PER_SHELF_PER_SKU4");
		headerDetail.add("VISI_ASSET_5");
		headerDetail.add("FACING_PER_SHELF_PER_SKU5");
		headerDetail.add("DEPTH_PER_SHELF_PER_SKU5");
		return headerDetail;
	}
	
	
}