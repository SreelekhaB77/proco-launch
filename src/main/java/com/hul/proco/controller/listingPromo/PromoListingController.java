package com.hul.proco.controller.listingPromo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.controller.promocr.PromoCrBean;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class PromoListingController {

	private Logger logger = Logger.getLogger(PromoListingController.class);

	@Autowired
	private PromoListingService promoListingService;

	@Autowired
	public CreatePromoService createPromoService;

	@RequestMapping(value = "promoListingPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("geography") String geographyValue,
			@RequestParam("moc") String mocValue,@RequestParam("promobasepack") String procoBasepack,
			@RequestParam("ppmaccount") String ppmAccount,@RequestParam("procochannel") String procoChannel,
			@RequestParam("prococluster") String procoCluster, 
			@RequestParam(value="startDate1",required=false) @DateTimeFormat(pattern="MMddyyyy") String fromDate,
			@RequestParam(value="endDate1",required=false) @DateTimeFormat(pattern="MMddyyyy") String toDate,
			HttpServletRequest request) {
		
		logger.info("START DATE:" + fromDate + "END DATE:" + toDate);
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		String searchParameter = request.getParameter("sSearch");
		//Added Proco Sprint9 Changes - Starts
		String accountNames = (String) request.getSession().getAttribute("accountName");
		String[] kamAccountsArr = accountNames.split(",");
		//Added Proco Sprint9 Changes - Ends
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;
		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
		String offerType = "", modality = "", year = "", moc = "";
		String promobasepack="", ppmaccount="", procochannel="",prococluster="";	


		if (cagetoryValue == null || cagetoryValue.isEmpty() || (cagetoryValue.equalsIgnoreCase("undefined"))
				|| (cagetoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
			cagetory = "all";
		} else {
			cagetory = cagetoryValue;
		}
		if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
				|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
			brand = "all";
		} else {
			brand = brandValue;
		}
		if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
				|| (basepackValue.equalsIgnoreCase("All"))) {
			basepack = "all";
		} else {
			basepack = basepackValue;
		}
		if (custChainL1Value == null || custChainL1Value.isEmpty() || (custChainL1Value.equalsIgnoreCase("undefined"))
				|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS")) || (custChainL1Value.equalsIgnoreCase("ALL"))) {
			custChainL1 = "all";
		} else {
			custChainL1 = custChainL1Value;
		}
		if (custChainL2Value == null || custChainL2Value.isEmpty() || (custChainL2Value.equalsIgnoreCase("undefined"))
				|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS")) || (custChainL2Value.equalsIgnoreCase("ALL"))) {
			custChainL2 = "all";
		} else {
			custChainL2 = custChainL2Value;
		}
		if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
				|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
			geography = "all";
		} else {
			geography = geographyValue;
		}
		if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
				|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
			offerType = "all";
		} else {
			offerType = offerTypeValue;
		}
		if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
				|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
			modality = "all";
		} else {
			modality = modalityValue;
		}
		if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
				|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
			year = "all";
		} else {
			year = yearValue;
		}
		if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))) {
			moc = "all";
		} else {
			moc = mocValue;
		}

		//Added by Kavitha D-SPRINT 11
		if (procoBasepack == null || procoBasepack.isEmpty() || (procoBasepack.equalsIgnoreCase("undefined")) || (procoBasepack.equalsIgnoreCase("SELECT BASEPACK"))|| (procoBasepack.equalsIgnoreCase("ALL"))){
			promobasepack = "all";
		} else {
			promobasepack = procoBasepack;
		}
		if (ppmAccount == null || ppmAccount.isEmpty() || (ppmAccount.equalsIgnoreCase("undefined"))|| (ppmAccount.equalsIgnoreCase("SELECT PPM ACCOUNT"))|| (ppmAccount.equalsIgnoreCase("ALL"))) {
			ppmaccount = "all";
		} else {
			ppmaccount = ppmAccount;
		}
		if (procoChannel == null || procoChannel.isEmpty() || (procoChannel.equalsIgnoreCase("undefined"))|| (procoChannel.equalsIgnoreCase("SELECT CHANNEL"))|| (procoChannel.equalsIgnoreCase("ALL"))) {
			procochannel = "all";
		} else {
			procochannel = procoChannel;
		}
		if (procoCluster == null || procoCluster.isEmpty() || (procoCluster.equalsIgnoreCase("undefined"))|| (procoCluster.equalsIgnoreCase("SELCET CLUSTER"))|| (procoCluster.equalsIgnoreCase("ALL"))) {
			prococluster = "all";
		} else {
			prococluster = procoCluster;
		}
		
		//Added by Kajal G for promolisting changes-SPRINT 11
		if((fromDate == null || fromDate.isEmpty()) && (toDate == null || toDate.isEmpty())) {
			fromDate = null;
			toDate = null;
		}
		else {
			fromDate = fromDate;
			toDate = toDate;
		}
		//Added by kavitha D for promolisting changes-SPRINT 9
		/*int rowCount = promoListingService.getPromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2,
				geography, offerType, modality, year, moc, userId, 1,roleId);
		List<PromoListingBean> promoList = promoListingService.getPromoTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1, custChainL2, geography,
				offerType, modality, year, moc, userId, 1,roleId ,searchParameter);*/
		
		int rowCount = promoListingService.getPromoListRowCountGrid(userId,roleId,moc,promobasepack,ppmaccount,procochannel,prococluster,kamAccountsArr,fromDate,toDate);
		List<PromoListingBean> promoList = promoListingService.getPromoTableListGrid((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength),userId,roleId,moc,promobasepack,ppmaccount,procochannel,prococluster,searchParameter, kamAccountsArr,fromDate,toDate);
		
		
		//logger.info("LOGGER OUTPUT FOR PROMOLIST:" + promoList);

		PromoListingJsonObject jsonObj = new PromoListingJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}

	@RequestMapping(value = "editPromotion.htm", method = RequestMethod.GET)
	public ModelAndView editPromotion(@RequestParam("promoid") String promoId, Model model,
			HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String moc="";
		
		CreatePromotionBean promoList = promoListingService.getPromotions(promoId).get(0);
		if(promoList.getMoc()!=null) {
		moc = promoList.getMoc();
		}
		if(moc.length()==4) {
			moc=moc.substring(3,4);
			moc="0"+moc+promoList.getYear();
			promoList.setMoc(moc);
		}
		if(moc.length()==5) {
			moc=moc.substring(3,5);
			moc=moc+promoList.getYear();
			promoList.setMoc(moc);
		}
		
		model.addAttribute("CreatePromotionBean", promoList);
		model.addAttribute("roleId", roleId);
		setModelAttributes(model,userId,request);
		return new ModelAndView("proco/promo_edit");
	}

	@RequestMapping(value = "deletePromotion.htm", method = RequestMethod.POST)
	public ModelAndView deletePromotion(Model model, HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String promoId = request.getParameter("promoIdList");
		String remark = request.getParameter("remark");
		promoListingService.deletePromotion(promoId,userId,remark);
		model.addAttribute("roleId", roleId);
		setModelAttributes(model,userId,request);
		model.addAttribute("success", "Promotion deleted successfully. ");
		return new ModelAndView("proco/proco_promo_listing");
	}
	
	
	@RequestMapping(value = "checkDropDate.htm", method = RequestMethod.POST)
	public @ResponseBody String kamDropPlanDate(HttpServletRequest request, Model model,@RequestParam("checkValues")String checkValues) {
			String result = promoListingService.promoDeleteDate(checkValues);	
		return result;
	}
	

	@RequestMapping(value = "duplicatePromotion.htm", method = RequestMethod.GET)
	public ModelAndView createDuplicatePromotion(@RequestParam("promoid") String promoId, Model model,
			HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("UserID");
		String roleId = (String) request.getSession().getAttribute("roleId");
		CreatePromotionBean promoList = promoListingService.getPromotions(promoId).get(0);
		model.addAttribute("CreatePromotionBean", promoList);
		model.addAttribute("roleId", roleId);
		model.addAttribute("duplicate", true);
		setModelAttributes(model,userId,request);

		return new ModelAndView("proco/proco_create");
	}

	@RequestMapping(value = "updatePromotion.htm", method = RequestMethod.POST)
	public ModelAndView updatePromotion(@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean,
			HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		try {
			createPromotionBean.setOfferValue(createPromotionBean.getOfferValue().concat(createPromotionBean.getOfferValDrop()));
			String reason = (String) request.getParameter("reasonText");
			String remark = (String) request.getParameter("remarkText");
			String changesMade=(String) request.getParameter("changesMadeText");
			String[] customerChainL2FromPage = request.getParameterValues("cust-chain");
			String[] customerChainL1FromPage = request.getParameterValues("customerChainL1");
			String customerChainL2 = "";
			String customerChainL1 = "";
			if (customerChainL1FromPage != null) {
				for (int i = 0; i < customerChainL1FromPage.length; i++) {
					if (i != (customerChainL1FromPage.length - 1)) {
						customerChainL1 = customerChainL1 + customerChainL1FromPage[i] + ",";
					} else {
						customerChainL1 = customerChainL1 + customerChainL1FromPage[i];
					}
				}
			} else {
				customerChainL1 = "ALL CUSTOMERS";
			}
			if (customerChainL2FromPage != null) {
				for (int i = 0; i < customerChainL2FromPage.length; i++) {
					if (i != (customerChainL2FromPage.length - 1)) {
						customerChainL2 = customerChainL2 + customerChainL2FromPage[i] + ",";
					} else {
						customerChainL2 = customerChainL2 + customerChainL2FromPage[i];
					}
				}
			} else {
				customerChainL2 = "ALL CUSTOMERS";
			}
			createPromotionBean.setCustomerChainL1(customerChainL1);
			createPromotionBean.setCustomerChainL2(customerChainL2);
			createPromotionBean.setReason(reason);
			createPromotionBean.setRemark(remark);
			createPromotionBean.setChangesMade(changesMade);
			CreatePromotionBean[] beans= new CreatePromotionBean[1];
			beans[0]=createPromotionBean;
			String createPromotion = promoListingService.updatePromotion(beans, userId,true);
			if (!createPromotion.equals("SUCCESS_FILE")) {
				if (createPromotion.equals("ERROR_FILE")) {
					String errorMsg = createPromoService.getErrorMsg(userId);
					model.addAttribute("errorMsg", errorMsg);
					setModelAttributes(model,userId,request);
					return new ModelAndView("proco/promo_edit");
				} else if (createPromotion.equals("ERROR")) {
					model.addAttribute("errorMsg", "Unhandled exception: Failed to update promotion");
					setModelAttributes(model,userId,request);
					return new ModelAndView("proco/promo_edit");
				}

			} else {
				model.addAttribute("success", "Promotion updated successfully.");
			}
			model.addAttribute("roleId", roleId);
			setModelAttributes(model,userId,request);
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			model.addAttribute("errorMsg", "Failed to update promotion");
			setModelAttributes(model,userId,request);
			return new ModelAndView("proco/promo_edit");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}

	@SuppressWarnings("unchecked")
	private void setModelAttributes(Model model,String userId,HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
		List<String> changesMadeListForEdit = promoListingService.getChangesMadeListForEdit();
		List<String> reasonListForEdit = promoListingService.getReasonListForEdit();
		List<String> mocValue = promoListingService.getPromoMoc();
		//Kavitha D changes for filters-SPROINT 11 starts
		List<String> procoBasepack = promoListingService.getProcoBasepack();
		List<String> ppmAccount = promoListingService.getPpmAccount(userId,roleId);
		List<String> procoChannel = promoListingService.getProcoChannel();
		List<String> procoCluster = promoListingService.getProcoCluster();
	

		//Kavitha D changes for filters-SPRINT 11 ends
				
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("modality", modality);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		model.addAttribute("changesMadeList", changesMadeListForEdit);
		model.addAttribute("reasonList", reasonListForEdit);
		model.addAttribute("mocList", mocValue);
		model.addAttribute("procoBasepacks", procoBasepack);
		model.addAttribute("ppmAccountList", ppmAccount);
		model.addAttribute("procoChannelList", procoChannel);
		model.addAttribute("procoClusterList", procoCluster);


	}

	@RequestMapping(value = "downloadPromoFromListing.htm", method = RequestMethod.POST)
	public ModelAndView downloadPromosForVolumeUpload(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("START downloadPromosForVolumeUpload():");
		try {
			String categoryValue = (String) request.getParameter("category");
			String brandValue = (String) request.getParameter("brand");
			String custChainL1Value = "";
			String custChainL2Value = "";
			if (request.getParameterValues("customerChainL1") != null) {
				String[] custL1 = request.getParameterValues("customerChainL1");
				for (int i = 0; i < custL1.length; i++) {
					if (i < custL1.length - 1) {
						custChainL1Value = custChainL1Value + custL1[i] + ",";
					} else {
						custChainL1Value = custChainL1Value + custL1[i];
					}
				}
			} else {
				custChainL1Value = null;
			}

			if (request.getParameterValues("cust-chain") != null) {
				String[] custL2 = request.getParameterValues("cust-chain");
				for (int i = 0; i < custL2.length; i++) {
					if (i < custL2.length - 1) {
						custChainL2Value = custChainL2Value + custL2[i] + ",";
					} else {
						custChainL2Value = custChainL2Value + custL2[i];
					}
				}
			} else {
				custChainL2Value = null;
			}
			String basepackValue = (String) request.getParameter("promoBasepack");
			String offerTypeValue = (String) request.getParameter("offerType");
			String modalityValue = (String) request.getParameter("modality");
			String geographyValue = (String) request.getParameter("geography");
			String yearValue = (String) request.getParameter("year");
			String mocValue = (String) request.getParameter("moc");

			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			String userId = (String) request.getSession().getAttribute("UserID");
			String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
			String offerType = "", modality = "", year = "", moc = "";
			if (categoryValue == null || categoryValue.isEmpty() || (categoryValue.equalsIgnoreCase("undefined"))
					|| (categoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
				cagetory = "all";
			} else {
				cagetory = categoryValue;
			}
			if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
					|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
				brand = "all";
			} else {
				brand = brandValue;
			}
			if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
					|| (basepackValue.equalsIgnoreCase("All"))) {
				basepack = "all";
			} else {
				basepack = basepackValue;
			}
			if (custChainL1Value == null || custChainL1Value.isEmpty()
					|| (custChainL1Value.equalsIgnoreCase("undefined"))
					|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS"))) {
				custChainL1 = "all";
			} else {
				custChainL1 = custChainL1Value;
			}
			if (custChainL2Value == null || custChainL2Value.isEmpty()
					|| (custChainL2Value.equalsIgnoreCase("undefined"))
					|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS"))) {
				custChainL2 = "all";
			} else {
				custChainL2 = custChainL2Value;
			}
			if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
					|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
				geography = "all";
			} else {
				geography = geographyValue;
			}
			if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
					|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
				offerType = "all";
			} else {
				offerType = offerTypeValue;
			}
			if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
					|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
				modality = "all";
			} else {
				modality = modalityValue;
			}
			if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
					|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
				year = "all";
			} else {
				year = yearValue;
			}
			if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))
					|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
				moc = "all";
			} else {
				moc = mocValue;
			}
			String roleId = (String) request.getSession().getAttribute("roleId");
			ArrayList<String> headerList = promoListingService.getHeaderListForPromoDumpDownload(roleId);
			downloadedData = promoListingService.getPromotionDump(headerList, cagetory, brand, basepack, custChainL1,
					custChainL2, geography, offerType, modality, year, moc, userId, 1);
			Map<String, List<List<String>>> mastersForTemplate = promoListingService.getMastersForTemplate();
			if (downloadedData != null) {
				UploadUtil.writeXLSFile(downloadFileName, downloadedData, mastersForTemplate,".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PromotionDumpFile"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}

	@RequestMapping(value = "uploadPromoEdit.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("PromoListingController.uploadFile()");
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = createPromotionBean.getFile();
		String fileName = file.getOriginalFilename();
		CreatePromotionBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(CreatePromotionBean.class).map(42, false, null);
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							setModelAttributes(model,userId,request);
							return new ModelAndView("proco/proco_promo_listing");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							setModelAttributes(model,userId,request);
							return new ModelAndView("proco/proco_promo_listing");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							beanArray = (CreatePromotionBean[]) list.toArray(new CreatePromotionBean[list.size()]);
							savedData = promoListingService.promoEditUpload(beanArray, userId,false);
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
								model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								model.addAttribute("errorMsg", "File Uploaded with errors");
								model.addAttribute("FILE_STATUS", "ERROR_FILE");
							} else if (savedData != null && savedData.equals("ERROR")) {
								model.addAttribute("errorMsg", "Error while updating promos");
							}
							setModelAttributes(model,userId,request);
						}
					}
				}
			} else {
				model.addAttribute("message", commUtils.getProperty("File.Empty"));
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId,request);
			return new ModelAndView("proco/proco_promo_listing");
		} catch (Throwable e) {
			logger.debug("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId,request);
			return new ModelAndView("proco/proco_promo_listing");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}

	@RequestMapping(value = "downloadPromotionEditErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionVolumeErrorFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean volumeUploadBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userId = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = promoListingService.getHeaderListForPromoEditErrorFileDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = promoListingService.getPromoEditErrorDetails(headerDetail, userId);
		Map<String, List<List<String>>> mastersForTemplate = promoListingService.getMastersForTemplate();
		try {
			if (downloadedData != null) {
				UploadUtil.writeXLSFile(downloadFileName, downloadedData, mastersForTemplate,".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PromoEditErrorFile_"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (FileNotFoundException e) {
			logger.debug("Exception: ",e);
			return null;
		} catch (IOException e) {
			logger.debug("Exception: ",e);
			return null;
		}
		return null;
	}
	
	@RequestMapping(value = "getReasonListForEdit.htm", method = RequestMethod.POST)
	public @ResponseBody String getReasonListForEdit() {
		List<String> reasonList = new ArrayList<>();
		Gson gson=new Gson();
		try {
			reasonList = promoListingService.getReasonListForEdit();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return gson.toJson(reasonList);
	}
	
	@RequestMapping(value = "promoDeletePagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoDeletePagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("geography") String geographyValue,
			@RequestParam("moc") String mocValue, HttpServletRequest request) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		String userId = (String) request.getSession().getAttribute("UserID");
		String searchParameter = request.getParameter("sSearch");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
		String offerType = "", modality = "", year = "", moc = "";

		if (cagetoryValue == null || cagetoryValue.isEmpty() || (cagetoryValue.equalsIgnoreCase("undefined"))
				|| (cagetoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
			cagetory = "all";
		} else {
			cagetory = cagetoryValue;
		}
		if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
				|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
			brand = "all";
		} else {
			brand = brandValue;
		}
		if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
				|| (basepackValue.equalsIgnoreCase("All"))) {
			basepack = "all";
		} else {
			basepack = basepackValue;
		}
		if (custChainL1Value == null || custChainL1Value.isEmpty() || (custChainL1Value.equalsIgnoreCase("undefined"))
				|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS")) || (custChainL1Value.equalsIgnoreCase("ALL"))) {
			custChainL1 = "all";
		} else {
			custChainL1 = custChainL1Value;
		}
		if (custChainL2Value == null || custChainL2Value.isEmpty() || (custChainL2Value.equalsIgnoreCase("undefined"))
				|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS")) || (custChainL2Value.equalsIgnoreCase("ALL"))) {
			custChainL2 = "all";
		} else {
			custChainL2 = custChainL2Value;
		}
		if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
				|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
			geography = "all";
		} else {
			geography = geographyValue;
		}
		if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
				|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
			offerType = "all";
		} else {
			offerType = offerTypeValue;
		}
		if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
				|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
			modality = "all";
		} else {
			modality = modalityValue;
		}
		if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
				|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
			year = "all";
		} else {
			year = yearValue;
		}
		if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))
				|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
			moc = "all";
		} else {
			moc = mocValue;
		}

		/*int rowCount = promoListingService.getDeletePromoListRowCount(cagetory, brand, basepack, custChainL1, custChainL2,
				geography, offerType, modality, year, moc, userId, 1,roleId);
		List<PromoListingBean> promoList = promoListingService.getDeletePromoTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1, custChainL2, geography,
				offerType, modality, year, moc, userId, 1,roleId,searchParameter); */
		
		//Added by kavitha D-SPRINT 9

		int rowCount = promoListingService.getDeletePromoListRowCount(moc, userId,roleId);
		List<PromoListingBean> promoList = promoListingService.getDeletePromoTableList((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength), moc,userId,roleId,searchParameter);

		PromoListingJsonObject jsonObj = new PromoListingJsonObject();
		jsonObj.setJsonBean(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}
	
	@RequestMapping(value = "{moc}/downloadDeletedPromo.htm", method = RequestMethod.GET)
	public ModelAndView downloadDeletedPromo(@PathVariable("moc") String moc,
			Model model,HttpServletRequest request, HttpServletResponse response) {
		logger.info("START downloadPromos for Dropped Offer:");
		try {
			 /*String categoryValue = (String) request.getParameter("category");
			String brandValue = (String) request.getParameter("brand");
			String custChainL1Value = "";
			String custChainL2Value = "";
			if (request.getParameterValues("customerChainL1") != null) {
				String[] custL1 = request.getParameterValues("customerChainL1");
				for (int i = 0; i < custL1.length; i++) {
					if (i < custL1.length - 1) {
						custChainL1Value = custChainL1Value + custL1[i] + ",";
					} else {
						custChainL1Value = custChainL1Value + custL1[i];
					}
				}
			} else {
				custChainL1Value = null;
			}

			if (request.getParameterValues("cust-chain") != null) {
				String[] custL2 = request.getParameterValues("cust-chain");
				for (int i = 0; i < custL2.length; i++) {
					if (i < custL2.length - 1) {
						custChainL2Value = custChainL2Value + custL2[i] + ",";
					} else {
						custChainL2Value = custChainL2Value + custL2[i];
					}
				}
			} else {
				custChainL2Value = null;
			}
			String basepackValue = (String) request.getParameter("promoBasepack");
			String offerTypeValue = (String) request.getParameter("offerType");
			String modalityValue = (String) request.getParameter("modality");
			String geographyValue = (String) request.getParameter("geography");
			String yearValue = (String) request.getParameter("year");
			String mocValue = (String) request.getParameter("moc"); */

			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Promotion.Download.Dropped.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			String userId = (String) request.getSession().getAttribute("UserID");
			/*String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "", geography = "";
			String offerType = "", modality = "", year = "", ;
			if (categoryValue == null || categoryValue.isEmpty() || (categoryValue.equalsIgnoreCase("undefined"))
					|| (categoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
				cagetory = "all";
			} else {
				cagetory = categoryValue;
			}
			if (brandValue == null || brandValue.isEmpty() || (brandValue.equalsIgnoreCase("undefined"))
					|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
				brand = "all";
			} else {
				brand = brandValue;
			}
			if (basepackValue == null || basepackValue.isEmpty() || (basepackValue.equalsIgnoreCase("undefined"))
					|| (basepackValue.equalsIgnoreCase("All"))) {
				basepack = "all";
			} else {
				basepack = basepackValue;
			}
			if (custChainL1Value == null || custChainL1Value.isEmpty()
					|| (custChainL1Value.equalsIgnoreCase("undefined"))
					|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS"))) {
				custChainL1 = "all";
			} else {
				custChainL1 = custChainL1Value;
			}
			if (custChainL2Value == null || custChainL2Value.isEmpty()
					|| (custChainL2Value.equalsIgnoreCase("undefined"))
					|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS"))) {
				custChainL2 = "all";
			} else {
				custChainL2 = custChainL2Value;
			}
			if (geographyValue == null || geographyValue.isEmpty() || (geographyValue.equalsIgnoreCase("undefined"))
					|| (geographyValue.equalsIgnoreCase("ALL INDIA"))) {
				geography = "all";
			} else {
				geography = geographyValue;
			}
			if (offerTypeValue == null || offerTypeValue.isEmpty() || (offerTypeValue.equalsIgnoreCase("undefined"))
					|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
				offerType = "all";
			} else {
				offerType = offerTypeValue;
			}
			if (modalityValue == null || modalityValue.isEmpty() || (modalityValue.equalsIgnoreCase("undefined"))
					|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
				modality = "all";
			} else {
				modality = modalityValue;
			}
			if (yearValue == null || yearValue.isEmpty() || (yearValue.equalsIgnoreCase("undefined"))
					|| (yearValue.equalsIgnoreCase("ALL YEARS"))) {
				year = "all";
			} else {
				year = yearValue;
			}
			if (mocValue == null || mocValue.isEmpty() || (mocValue.equalsIgnoreCase("undefined"))
					|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
				moc = "all";
			} else {
				moc = mocValue;
			} */
			String roleId = (String) request.getSession().getAttribute("roleId");
			ArrayList<String> headerList = promoListingService.getHeaderListForPromoDumpDownload(roleId);
			
			//downloadedData = promoListingService.getDeletePromotionDump(headerList, cagetory, brand, basepack, custChainL1,custChainL2, geography, offerType, modality, year, moc, userId, 1, roleId);
			//Added by kavitha D-Sprint 9
			downloadedData = promoListingService.getDeletePromotionDump(headerList, moc, userId,roleId);
			//Map<String, List<List<String>>> mastersForTemplate = promoListingService.getMastersForTemplate();
			if (downloadedData != null) {
				UploadUtil.writeDeletePromoXLSXFile(downloadFileName, downloadedData, null,".xlsx");
				downloadLink = downloadFileName + ".xlsx";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=DroppedPromotionFile"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}
	
	//Added by kavitha D for promo dropped offer upload starts-SPRINT 10
			@RequestMapping(value = "promoDroppedOfferUpload.htm", method = RequestMethod.POST)
			public @ResponseBody String promoDroppedOfferUpload(@ModelAttribute("PromoCrBean") PromoCrBean promoCrBean,
					Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)throws Exception {
				String save_data = null;

				CommonPropUtils commUtils = CommonPropUtils.getInstance();
				String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
				MultipartFile file = promoCrBean.getFile();
				String fileName = file.getOriginalFilename();
				PromoCrBean[] beanArray = null;
				String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
				fileName = filepath + fileName;
				try {
					if (!CommonUtils.isFileEmpty(file)) {
						if (CommonUtils.isFileProcoSizeExceeds(file)) {
							model.addAttribute("FILE_STATUS", "FILE_SIZE_EXCEED");
							return "FILE_SIZE_EXCEED";
						} else if (UploadUtil.movefile(file, fileName)) {
							Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(PromoCrBean.class).map(4, false, null);

							if (map.isEmpty()) {
								model.addAttribute("FILE_STAUS", "FILE_EMPTY");
								return "FILE_EMPTY";
							}
							if (map.containsKey("ERROR")) {
								model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");

								return "CHECK_COL_MISMATCH";
							}
							//Added by Kajal G for empty row in excel
							else if (map.containsKey("EMPTY_ROW")) {
								model.addAttribute("FILE_STAUS", "EMPTY_ROW");
								return "EMPTY_ROW";
							}
							else if (map.containsKey("DATA")) {
								List<?> datafromexcel = map.get("DATA");
								beanArray = (PromoCrBean[]) datafromexcel.toArray(new PromoCrBean[datafromexcel.size()]);
								save_data = promoListingService.uploadDroppedOfferApprovalData(beanArray, userId);
							}
						} else {

							model.addAttribute("FILE_STAUS", "FILE_EMPTY");
							return "FILE_EMPTY";
						}
					} else {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}

					if (save_data.equals("EXCEL_UPLOADED")) {
						model.addAttribute("FILE_STAUS", "EXCEL_UPLOADED");
					} else {
						model.addAttribute("FILE_STAUS", "EXCEL_NOT_UPLOADED");
						save_data = "EXCEL_NOT_UPLOADED";
					}
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				} catch (Throwable t) {
					logger.error(t);
					t.printStackTrace();
				}
				return save_data;
			}
			
			//Added by kavitha D for promo dropped offer upload ends-SPRINT 10
	
	
	
	

	//Added by Kavitha D for promo listing download starts-SPRINT 9

	@RequestMapping(value = "{moc}/{promobasepack}/{ppmaccount}/{procochannel}/{prococluster}/{startDate1}/{endDate1}/downloadPromoListing.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadPromosForListing(@ModelAttribute("CreateBeanRegular") CreateBeanRegular createBeanRegular,@PathVariable("moc") String moc,
			@PathVariable("promobasepack") String procoBasepack,@PathVariable("ppmaccount") String ppmAccount,@PathVariable("procochannel") String procoChannel,@PathVariable("prococluster") String procoCluster,
			@PathVariable("startDate1") @DateTimeFormat(pattern="MMddyyyy") String fromDate,@PathVariable("endDate1") @DateTimeFormat(pattern="MMddyyyy") String toDate,Model model,HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream is;
			String roleId = (String) request.getSession().getAttribute("roleId");
			//Added Proco Sprint9 Changes - Starts
			String accountNames = (String) request.getSession().getAttribute("accountName");
			String[] kamAccounts = accountNames.split(",");
			//Added Proco Sprint9 Changes - Ends
			
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Promotion.Download.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			String userId = (String) request.getSession().getAttribute("UserID");
			String promobasepack="", ppmaccount="", procochannel="", prococluster="";

			if (procoBasepack == null || procoBasepack.isEmpty() || (procoBasepack.equalsIgnoreCase("undefined")) || (procoBasepack.equalsIgnoreCase("SELECT BASEPACK"))|| (procoBasepack.equalsIgnoreCase("ALL")))
			{
				promobasepack = "all";
			} else {
				promobasepack = procoBasepack;
			}
			if (ppmAccount == null || ppmAccount.isEmpty() || (ppmAccount.equalsIgnoreCase("undefined"))|| (ppmAccount.equalsIgnoreCase("SELECT PPM ACCOUNT"))|| (ppmAccount.equalsIgnoreCase("ALL"))) {
				ppmaccount = "all";
			} else {
				ppmaccount = ppmAccount;
			}
			if (procoChannel == null || procoChannel.isEmpty() || (procoChannel.equalsIgnoreCase("undefined"))|| (procoChannel.equalsIgnoreCase("SELECT CHANNEL"))|| (procoChannel.equalsIgnoreCase("ALL"))) {
				procochannel = "all";
			} else {
				procochannel = procoChannel;
			}
			if (procoCluster == null || procoCluster.isEmpty() || (procoCluster.equalsIgnoreCase("undefined"))|| (procoCluster.equalsIgnoreCase("SELCET CLUSTER"))|| (procoCluster.equalsIgnoreCase("ALL"))) {
				prococluster = "all";
			} else {
				prococluster = procoCluster;
			}
			
			if((fromDate == null || fromDate.isEmpty()) && (toDate == null || toDate.isEmpty())) {
				fromDate = null;
				toDate = null;
			}
			else {
				fromDate = fromDate;
				toDate = toDate;
			}
			
			ArrayList<String> headerList = promoListingService.getHeaderListForPromoDownloadListing();
			downloadedData = promoListingService.getPromotionListingDownload(headerList, userId,moc,promobasepack,ppmaccount,procochannel,prococluster,roleId, kamAccounts,fromDate,toDate);
			//logger.info("START downloadPromos for listing():" + downloadedData);

			if (downloadedData != null) {
				UploadUtil.writeXLSXFile(downloadFileName, downloadedData, null,".xlsx");
				downloadLink = downloadFileName + ".xlsx";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PromotionListingDownloadFile"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}
	
	//Added by Kajal G for Download for KAM Volume Upload SPRINT 10
	@RequestMapping(value = "{moc}/{primarychannelValue}/downloadKAMDPUploadPrimaryChannelwise.htm", method = RequestMethod.GET)
	public @ResponseBody String downloadKAMDPUploadPrimaryChannelwise(@ModelAttribute("CreateBeanRegular") CreateBeanRegular createBeanRegular,@PathVariable("moc") String moc,@PathVariable("primarychannelValue") String primarychannelValue,
			Model model,HttpServletRequest request, HttpServletResponse response) {
		logger.info("START downloadPromos for listing():");
		try {
			InputStream is;
			String roleId = (String) request.getSession().getAttribute("roleId");
			//Added Proco Sprint9 Changes - Starts
			String accountNames = (String) request.getSession().getAttribute("accountName");
			String[] kamAccounts = accountNames.split(",");
			//Added Proco Sprint9 Changes - Ends
			
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("Promotion.Download.Template.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			String userId = (String) request.getSession().getAttribute("UserID");
			
			ArrayList<String> headerList = promoListingService.getHeaderForPromoDownloadListing(primarychannelValue);
			downloadedData = promoListingService.getPromotionListDownload(headerList, moc, primarychannelValue);
			if (downloadedData != null) {
				UploadUtil.writeXLSXFile(downloadFileName, downloadedData, null,".xlsx");
				downloadLink = downloadFileName + ".xlsx";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=KAMUploadDownloadFile"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}

	//Added by Kajal G for KAM Volume Upload SPRINT 10
	@RequestMapping(value = "kamVolumeUpload.htm", method = RequestMethod.POST)
	public @ResponseBody String kamVolumeUpload(@ModelAttribute("createKAMVolumeBean") CreateKAMVolumeBean createKAMVolumeBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;
		
		System.out.println("Start KAM Upload file");
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = createKAMVolumeBean.getFile();
		String fileName = file.getOriginalFilename();
		CreateKAMVolumeBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileProcoSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
					return "FILE_SIZE_EXCEED";
				} else if (UploadUtil.movefile(file, fileName)) {
					List<List<String>> excelData = new ArrayList();
					excelData = UploadUtil.readExcelContent(fileName);
					if (excelData.isEmpty()) {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
					if (excelData.contains("ERROR")) {
						model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");

						return "CHECK_COL_MISMATCH";
					} else if (excelData.size() > 0) {
						save_data = promoListingService.kamVolumeUpload(excelData, userId);
					}
				} else {

					model.addAttribute("FILE_STAUS", "FILE_EMPTY");
					return "FILE_EMPTY";
				}
			} else {
				model.addAttribute("FILE_STAUS", "FILE_EMPTY");
				return "FILE_EMPTY";
			}

			if (save_data.equals("EXCEL_UPLOADED")) {
				model.addAttribute("FILE_STAUS", "EXCEL_UPLOADED");
			} else {
				model.addAttribute("FILE_STAUS", "EXCEL_NOT_UPLOADED");
				save_data = "EXCEL_NOT_UPLOADED";
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
		}
		return save_data;
	}
	
	////Added by Kajal G for KAM Volume Upload Error file SPRINT 10
	@RequestMapping(value = "downloadKamUploadErrorFilensp.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadKamUploadErrorFile(
			@ModelAttribute("CreateKAMVolumeBean") CreateKAMVolumeBean createKAMVolumeBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userId = (String) request.getSession().getAttribute("UserID");
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("KAM.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = promoListingService.getKAMErrorDetails(userId);
		try {
			UploadUtil.writeXLSXFile(downloadFileName, downloadedData, null,".xlsx");
			downloadLink = downloadFileName + ".xlsx";
			is = new FileInputStream(new File(downloadLink));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=KAMVolumeErrorFile_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xlsx");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
		return null;
	}
	//Added by Kavitha D for Moc  
	/*
	 * @RequestMapping(value = "getMocPromo.htm", method = RequestMethod.GET)
	 * public @ResponseBody String getMocPromo(HttpServletRequest request, Model
	 * model, HttpServletResponse response) { ArrayList<String> mocValue = new
	 * ArrayList(); mocValue = promoListingService.getMoc();
	 * //System.out.println("mocValue:" + mocValue.toString()); Gson gson = new
	 * GsonBuilder().setPrettyPrinting().create(); String json =
	 * gson.toJson(mocValue); model.addAttribute("mocList", json); return json;
	 * 
	 * }
	 */
	
	
	
	//Added by Kavitha D for promo listing download ends-SPRINT 9
	
	
	//@RequestMapping(value = "promoListingPaginationGrid.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	/*public @ResponseBody String promoListingPaginationGrid( HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		String searchParameter = request.getParameter("sSearch");
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		int rowCount = promoListingService.getPromoListRowCountGrid(userId);
		List<CreateBeanRegular> promoList = promoListingService.getPromoTableListGrid((pageDisplayStart + 1),
				(pageNumber * pageDisplayLength),userId ,searchParameter);

		PromoListingJsonObject jsonObj = new PromoListingJsonObject();
		jsonObj.setJsonBean1(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		System.out.println(json);
		return json;
	} 
	*/

}
