package com.hul.proco.controller.collaboration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import com.google.gson.GsonBuilder;
import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.promocr.PromoCrService;
import com.hul.proco.controller.volumeupload.VolumeUploadBean;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class CollaborationController {

	@Autowired
	private CollaborationService collaborationService;

	@Autowired
	private CreatePromoService createPromoService;
	
	@Autowired
	private PromoCrService promoCrService;

	private Logger logger = Logger.getLogger(CollaborationController.class);

	@RequestMapping(value = "collaborationPagination.htm", method = RequestMethod.GET, produces = "application/json", headers = "Accept=*/*")
	public @ResponseBody String promoListingPagination(@RequestParam("category") String cagetoryValue,
			@RequestParam("brand") String brandValue, @RequestParam("custChainL1") String custChainL1Value,
			@RequestParam("offerType") String offerTypeValue, @RequestParam("modality") String modalityValue,
			@RequestParam("year") String yearValue, @RequestParam("custChainL2") String custChainL2Value,
			@RequestParam("basepack") String basepackValue, @RequestParam("moc") String mocValue,
			HttpServletRequest request) {

		String userId = (String) request.getSession().getAttribute("UserID");
		Integer pageDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Integer pageNumber = (pageDisplayStart / pageDisplayLength) + 1;

		String cagetory = "", brand = "", basepack = "", custChainL1 = "", custChainL2 = "";
		String offerType = "", modality = "", year = "", moc = "";

		if (cagetoryValue.isEmpty() || null == cagetoryValue || (cagetoryValue.equalsIgnoreCase("undefined"))
				|| (cagetoryValue.equalsIgnoreCase("ALL CATEGORIES"))) {
			cagetory = "all";
		} else {
			cagetory = cagetoryValue;
		}
		if (brandValue.isEmpty() || null == brandValue || (brandValue.equalsIgnoreCase("undefined"))
				|| (brandValue.equalsIgnoreCase("ALL BRANDS"))) {
			brand = "all";
		} else {
			brand = brandValue;
		}
		if (basepackValue.isEmpty() || null == basepackValue || (basepackValue.equalsIgnoreCase("undefined"))
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
		if (offerTypeValue.isEmpty() || null == offerTypeValue || (offerTypeValue.equalsIgnoreCase("undefined"))
				|| (offerTypeValue.equalsIgnoreCase("ALL TYPES"))) {
			offerType = "all";
		} else {
			offerType = offerTypeValue;
		}
		if (modalityValue.isEmpty() || null == modalityValue || (modalityValue.equalsIgnoreCase("undefined"))
				|| (modalityValue.equalsIgnoreCase("ALL MODALITIES"))) {
			modality = "all";
		} else {
			modality = modalityValue;
		}
		if (yearValue.isEmpty() || null == yearValue || (yearValue.equalsIgnoreCase("undefined"))
				|| (yearValue.equalsIgnoreCase("ALL YEAR"))) {
			year = "all";
		} else {
			year = yearValue;
		}
		if (mocValue.isEmpty() || null == mocValue || (mocValue.equalsIgnoreCase("undefined"))
				|| (mocValue.equalsIgnoreCase("FULL YEAR"))) {
			moc = "all";
		} else {
			moc = mocValue;
		}

		int rowCount = collaborationService.getCollaborationRowCount(cagetory, brand, basepack, custChainL1,
				custChainL2, offerType, modality, year, moc, userId);
		List<DisplayCollaborationBean> promoList = collaborationService.getCollaborationTableList(
				(pageDisplayStart + 1), (pageNumber * pageDisplayLength), cagetory, brand, basepack, custChainL1,
				custChainL2, offerType, modality, year, moc, userId);

		CollaborationJsonObject jsonObj = new CollaborationJsonObject();
		jsonObj.setAaData(promoList);
		jsonObj.setiTotalDisplayRecords(rowCount);
		jsonObj.setiTotalRecords(rowCount);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(jsonObj);
		return json;
	}

	@RequestMapping(value = "downloadPromosForKamUpload.htm", method = RequestMethod.POST)
	public ModelAndView downloadPromosForKamUpload(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		logger.info("START downloadPromosForKamUpload():");
		String userId = (String) request.getSession().getAttribute("UserID");
		try {
			String[] promoId = request.getParameterValues("promoId");
			if(promoId==null || promoId.length==0){
				model.addAttribute("errorMsg", "Select atleast 1 Promo for Collaboration.");
				setModelAttributes(model,userId);
				return new ModelAndView("proco/promo_collaboration");
			}
			String levelFromPage = request.getParameter("level");
			String level = "";
			if (levelFromPage == null || levelFromPage.equals("")) {
				level="L1";
			}else if (levelFromPage.equalsIgnoreCase("L1 Depot level")) {
				level = "L1";
			} else {
				level = "L2";
			}
			InputStream is;
			String downloadLink = "", absoluteFilePath = "";
			List<ArrayList<String>> downloadedData = null;
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = "KamFile_"+CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS();
			String downloadFileName = absoluteFilePath + fileName;
			if(level.equals("L1")){
				ArrayList<String> headerList = collaborationService.getHeaderListForKamTemplateDownload();
				downloadedData = collaborationService.getL1DepotDisaggregation(headerList, userId, promoId);
				UploadUtil.writeKamL1DepotXLSFile(downloadFileName, downloadedData, ".xls");
				downloadLink = downloadFileName + ".xls";
				fileName="KamL1DepotCollaboration_";
			}else{
				ArrayList<String> headerList = collaborationService.getHeaderListForKamL2TemplateDownload();
				downloadedData = collaborationService.getL2DepotDisaggregation(headerList, userId, promoId);
				UploadUtil.writeKamL2DepotXLSFile(downloadFileName, downloadedData, ".xls");
				downloadLink = downloadFileName + ".xls";
				fileName="KamL2DepotCollaboration_";
			}
			
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName
					+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			return null;
		}
		return null;
	}

	@RequestMapping(value = "uploadKam.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadKamL1File(@ModelAttribute("L1CollaborationBean") L1CollaborationBean bean,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) request.getSession().getAttribute("UserID");
		String levelFromPage = request.getParameter("level");
		String level = "";
		if (levelFromPage == null || levelFromPage.equals("")) {
			level="L1";
		}else if (levelFromPage.equalsIgnoreCase("L1 Depot level")) {
			level = "L1";
		} else {
			level = "L2";
		}
		MultipartFile file = bean.getFile();
		String fileName = file.getOriginalFilename();
		L1CollaborationBean[] beanArrayL1 = null;
		L2CollaborationBean[] beanArrayL2 = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map=null;
						if(level.equals("L1")){
							map = ExOM.mapFromExcel(new File(fileName))
									.to(L1CollaborationBean.class).map(12, false, null);
						}else{
							map = ExOM.mapFromExcel(new File(fileName))
									.to(L2CollaborationBean.class).map(15, false, null);
						}
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							setModelAttributes(model,userId);
							return new ModelAndView("proco/promo_collaboration");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							setModelAttributes(model,userId);
							return new ModelAndView("proco/promo_collaboration");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							if(level.equals("L1")){
								beanArrayL1 = (L1CollaborationBean[]) list.toArray(new L1CollaborationBean[list.size()]);
								savedData = collaborationService.uploadKamL1(beanArrayL1, userId);
							}else{
								beanArrayL2 = (L2CollaborationBean[]) list.toArray(new L2CollaborationBean[list.size()]);
								savedData = collaborationService.uploadKamL2(beanArrayL2, userId);
							}
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								model.addAttribute("errorMsg", "File Uploaded with errors");
								model.addAttribute("level", level);
							}
							setModelAttributes(model,userId);
						}
					}
				}
			} else {
				model.addAttribute("message", commUtils.getProperty("File.Empty"));
			}
			if (savedData.equals("ERROR_FILE")) {
				model.addAttribute("FILE_STATUS", "ERROR_FILE");
				setModelAttributes(model,userId);
				return new ModelAndView("proco/promo_collaboration");
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				setModelAttributes(model,userId);
				return new ModelAndView("proco/promo_collaboration");
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId);
			return new ModelAndView("proco/promo_collaboration");
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId);
			return new ModelAndView("proco/promo_collaboration");
		}
		return new ModelAndView("proco/promo_collaboration");
	}

	@RequestMapping(value = "downloadKamErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionVolumeErrorFile(
			@RequestParam("level") String level, @ModelAttribute("VolumeUploadBean") VolumeUploadBean volumeUploadBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userId = (String) request.getSession().getAttribute("UserID");
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = "KamErrorFile_"+
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS();
		String downloadFileName = absoluteFilePath + fileName;
		if(level.equals("L1")){
			ArrayList<String> headerDetail = collaborationService.getHeaderListForKamL1ErrorFileDownload();
			downloadedData = collaborationService.getKamL1ErrorDetails(headerDetail, userId);
		}else{
			ArrayList<String> headerDetail = collaborationService.getHeaderListForKamL2ErrorFileDownload();
			downloadedData = collaborationService.getKamL2ErrorDetails(headerDetail, userId);
		}
		
		// UploadUtil.writeFileWithData(downloadFileName, downloadedData,
		// ".csv");
		// downloadLink = downloadFileName + ".csv";
		try {
			if(level.equals("L1")){
				UploadUtil.writeKamL1DepotXLSFile(downloadFileName, downloadedData, ".xls");
				fileName="KamL1ErrorFile_";
			}else{
				UploadUtil.writeKamL2DepotXLSFile(downloadFileName, downloadedData, ".xls");
				fileName="KamL2ErrorFile_";
			}
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			/*
			 * String actualFileName =
			 * downloadLink.substring(downloadLink.lastIndexOf("/") + 1,
			 * downloadLink.indexOf("."));
			 */
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName
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
	private void setModelAttributes(Model model,String userId) {
		List<String> customerChainL1 = createPromoService.getCustomerChainL1(userId);
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		List<String> category = promoCrService.getAllCategories();
		List<String> brand = promoCrService.getAllBrands();
		List<String> basepacks = promoCrService.getAllBasepacks();
		List<String> yearList = (List<String>) yearAndMoc.get("years");
		String mocJson = (String) yearAndMoc.get("moc");
		model.addAttribute("mocJson", mocJson);
		model.addAttribute("years", yearList);
		model.addAttribute("customerChainL1", customerChainL1);
		model.addAttribute("offerTypes", offerTypes);
		model.addAttribute("geographyJson", geographyJson);
		model.addAttribute("modality", modality);
		model.addAttribute("categories", category);
		model.addAttribute("brands", brand);
		model.addAttribute("basepacks", basepacks);
	}
}
