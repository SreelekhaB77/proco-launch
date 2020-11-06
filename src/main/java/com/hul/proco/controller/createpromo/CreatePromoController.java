package com.hul.proco.controller.createpromo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.homeController.ProcoHomeController;
import com.hul.proco.controller.listingPromo.PromoListingService;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class CreatePromoController {

	@Autowired
	public CreatePromoService createPromoService;

	@Autowired
	public ProcoHomeController homeController;
	
	@Autowired
	public PromoListingService promoListingService;
	

	static Logger logger = Logger.getLogger(CreatePromoController.class);

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value = "getCustomerChainL2.htm", method = RequestMethod.POST)
	public @ResponseBody String getCustomerChainL2(@RequestParam("customerChainL1") String customerChainL1,
			HttpServletRequest request, Model model) {
		List<String> customerChainL2 = new ArrayList();
		Gson gson = new Gson();
		try {
			customerChainL2 = createPromoService.getCustomerChainL2(customerChainL1);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return gson.toJson(customerChainL2);
	}

	@RequestMapping(value = "getGeography.htm", method = RequestMethod.GET)
	public @ResponseBody String getGeography(HttpServletRequest request, Model model) {
		String geographyJson = "";
		try {
			geographyJson = createPromoService.getGeography(true);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return geographyJson;
	}

	@RequestMapping(value = "getBasepackDetails.htm", method = RequestMethod.GET)
	public @ResponseBody String getBasepackDetails(@RequestParam String basepack, HttpServletRequest request,
			Model model) {
		Map<String, String> basepackDetails = new HashMap<>();
		String userId = (String) request.getSession().getAttribute("UserID");
		Gson gson = new Gson();
		try {
			basepackDetails = createPromoService.getBasepackDetails(basepack,userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return gson.toJson(basepackDetails);
	}
	
	@RequestMapping(value = "getChildBasepackDetails.htm", method = RequestMethod.GET)
	public @ResponseBody String getChildBasepackDetails(@RequestParam String basepack, HttpServletRequest request,
			Model model) {
		Map<String, String> basepackDetails = new HashMap<>();
		Gson gson = new Gson();
		try {
			basepackDetails = createPromoService.getChildBasepackDetails(basepack);
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return gson.toJson(basepackDetails);
	}

	@RequestMapping(value = "createPromotion.htm", method = RequestMethod.POST)
	public ModelAndView createPromotion(@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean,
			HttpServletRequest request, Model model) {
		CreatePromotionBean[] beans = { createPromotionBean };
		String userId = (String) request.getSession().getAttribute("UserID");
		try {
			createPromotionBean.setOfferValue(createPromotionBean.getOfferValue().concat(createPromotionBean.getOfferValDrop()));
			String roleId = (String) request.getSession().getAttribute("roleId");
			String[] customerChainL2FromPage = request.getParameterValues("cust-chain");
			String[] customerChainL1FromPage = request.getParameterValues("customerChainL1");
			String reason = (String) request.getParameter("reasonText");
			String remark = (String) request.getParameter("remarkText");
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
			String createPromotion = createPromoService.createPromotion(beans, userId, "CREATED BY TME", true,true);
			if (!createPromotion.equals("SUCCESS_FILE")) {
				if (createPromotion.equals("ERROR_FILE")) {
					String errorMsg = createPromoService.getErrorMsg(userId);
					model.addAttribute("errorMsg", errorMsg);
					setModelAttributes(model, true,userId);
					return new ModelAndView("proco/proco_create");
				} else if (createPromotion.equals("ERROR")) {
					model.addAttribute("errorMsg", "Failed to create promotion: Something went wrong");
					setModelAttributes(model, true,userId);
					return new ModelAndView("proco/proco_create");
				}
			}
			model.addAttribute("success", "Promotion successfully created.");
			model.addAttribute("roleId", roleId);
			setModelAttributes(model, false,userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", e.getMessage());
			setModelAttributes(model, true,userId);
			return new ModelAndView("proco/proco_create");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}

	@RequestMapping(value = "uploadPromoCreation.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
								.to(CreatePromotionBean.class).map(38, false, null);
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							return new ModelAndView("proco/proco_create");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							setModelAttributes(model, true,userId);
							return new ModelAndView("proco/proco_create");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							beanArray = (CreatePromotionBean[]) list.toArray(new CreatePromotionBean[list.size()]);
							savedData = createPromoService.createPromotion(beanArray, userId, "CREATED BY TME", true,true);
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								model.addAttribute("errorMsg", "File Uploaded with errors");
							} else if (savedData != null && savedData.equals("ERROR")) {
								model.addAttribute("errorMsg", "Error while uploading file");
							}
						}
					}
				}
			} else {
				model.addAttribute("message", commUtils.getProperty("File.Empty"));
			}
			if (savedData.equals("ERROR_FILE")) {
				model.addAttribute("FILE_STATUS", "ERROR_FILE");
				setModelAttributes(model, true,userId);
				return new ModelAndView("proco/proco_create");
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				setModelAttributes(model, true,userId);
				return new ModelAndView("proco/proco_create");
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
			}
			setModelAttributes(model, false,userId);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model, true,userId);
			return new ModelAndView("proco/proco_create");
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model, true,userId);
			return new ModelAndView("proco/proco_create");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}

	@RequestMapping(value = "downloadPromotionErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionErrorFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userID = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = createPromoService.getHeaderListForPromotionErrorDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = createPromoService.getPromotionErrorDetails(headerDetail, userID);
		Map<String, List<List<String>>> mastersForTemplate = promoListingService.getMastersForTemplate();
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, mastersForTemplate,".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			/*
			 * String actualFileName =
			 * downloadLink.substring(downloadLink.lastIndexOf("/") + 1,
			 * downloadLink.indexOf("."));
			 */
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionErrorFile_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
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
		// return new ModelAndView("productsPage");
	}

	@RequestMapping(value = "downloadPromotionTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionTemplateFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = createPromoService.getHeaderListForPromotionTemplateDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData.add(headerDetail);
		Map<String, List<List<String>>> mastersForTemplate = promoListingService.getMastersForTemplate();
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, mastersForTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionFile_"
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
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
		// return new ModelAndView("productsPage");
	}

	@SuppressWarnings("unchecked")
	private void setModelAttributes(Model model, boolean isCreatePage,String userId) {
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(true);
		Map<Integer, String> modality = createPromoService.getModality();
		if (!isCreatePage) {
			Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(true);
			List<String> yearList = (List<String>) yearAndMoc.get("years");
			String mocJson = (String) yearAndMoc.get("moc");
			model.addAttribute("mocJson", mocJson);
			model.addAttribute("years", yearList);
		}
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
		List<String> reasonListForEdit = promoListingService.getReasonListForEdit();
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("modality", modality);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
		model.addAttribute("reasonList", reasonListForEdit);
	}
	
	@RequestMapping(value = "getMocMonthForProco.htm",   method = RequestMethod.POST)
	public @ResponseBody String getMocMonthForProco(HttpServletRequest req){
		logger.info("CreatePromoController.getMocMonthForProco()");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		if(!startDate.equals("") && !endDate.equals("")){
			return 	CommonUtils.getMocMonthYearForProco(startDate,endDate);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "getDifferenceInDays.htm",   method = RequestMethod.GET)
	public @ResponseBody String getDifferenceInDays(@RequestParam("moc") String moc){
		logger.info("CreatePromoController.getDifferenceInDays()");
		int diffInDays=0;
		Gson gson=new Gson();
		if(moc!=null && !moc.equals("")){
			diffInDays=createPromoService.getDifferenceInDays(moc);
		}else{
			diffInDays=0;
		}
		return gson.toJson(String.valueOf(diffInDays));
	}

}
