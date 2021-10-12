package com.hul.proco.controller.volumeupload;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.controller.createpromo.CreatePromoService;
import com.hul.proco.controller.listingPromo.PromoListingController;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class VolumeUploadController {

	private Logger logger = Logger.getLogger(PromoListingController.class);

	@Autowired
	private VolumeUploadService volumeUploadService;

	@Autowired
	private CreatePromoService createPromoService;

	@RequestMapping(value = "downloadPromosForVolumeUpload.htm", method = RequestMethod.POST)
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
					|| (custChainL1Value.equalsIgnoreCase("ALL CUSTOMERS"))
					|| (custChainL1Value.equalsIgnoreCase("ALL"))) {
				custChainL1 = "all";
			} else {
				custChainL1 = custChainL1Value;
			}
			if (custChainL2Value == null || custChainL2Value.isEmpty()
					|| (custChainL2Value.equalsIgnoreCase("undefined"))
					|| (custChainL2Value.equalsIgnoreCase("All CUSTOMERS"))
					|| (custChainL2Value.equalsIgnoreCase("ALL"))) {
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
			ArrayList<String> headerList = volumeUploadService.getHeaderListForVolumeUploadTemplateDownload();
			downloadedData = volumeUploadService.getPromoTableList(headerList, cagetory, brand, basepack, custChainL1,
					custChainL2, geography, offerType, modality, year, moc, userId, 1);
			if (downloadedData != null) {
				UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				// copy it to response's OutputStream
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=VolumeUploadFile_"
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

	@RequestMapping(value = "uploadPromoVolume.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadFile(@ModelAttribute("VolumeUploadBean") VolumeUploadBean volumeUploadBean,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = volumeUploadBean.getFile();
		String fileName = file.getOriginalFilename();
		VolumeUploadBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName)).to(VolumeUploadBean.class)
								//Kavitha D chnages for SPRINT 5
								.map(42, false, null);
								//.map(40, false, null);
								//.map(39, false, null);
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							setModelAttributes(model,userId);
							return new ModelAndView("proco/proco_volume_upload");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							setModelAttributes(model,userId);
							return new ModelAndView("proco/proco_volume_upload");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							beanArray = (VolumeUploadBean[]) list.toArray(new VolumeUploadBean[list.size()]);
							savedData = volumeUploadService.volumeUploadForPromotion(beanArray, userId);
							if (savedData != null && savedData.equals("SUCCESS_FILE")) {
								model.addAttribute("success", commUtils.getProperty("File.Upload.Success"));
							} else if (savedData != null && savedData.equals("ERROR_FILE")) {
								model.addAttribute("errorMsg", "File Uploaded with errors");
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
				return new ModelAndView("proco/proco_volume_upload");
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				setModelAttributes(model,userId);
				return new ModelAndView("proco/proco_volume_upload");
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId);
			return new ModelAndView("proco/proco_volume_upload");
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			setModelAttributes(model,userId);
			return new ModelAndView("proco/proco_volume_upload");
		}
		return new ModelAndView("proco/proco_volume_upload");
	}

	@SuppressWarnings("unchecked")
	private void setModelAttributes(Model model,String userId) {
		List<String> customerChainL1 = createPromoService.getCustomerChainL1();
		List<String> offerTypes = createPromoService.getOfferTypes();
		String geographyJson = createPromoService.getGeography(false);
		Map<Integer, String> modality = createPromoService.getModality();
		Map<String, Object> yearAndMoc = createPromoService.getYearAndMoc(false);
		List<String> category = createPromoService.getAllCategories(userId);
		List<String> brand = createPromoService.getAllBrands(userId);
		List<String> basepacks = createPromoService.getAllBasepacks(userId);
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

	@RequestMapping(value = "downloadPromotionVolumeErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionVolumeErrorFile(
			@ModelAttribute("VolumeUploadBean") VolumeUploadBean volumeUploadBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userId = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = volumeUploadService.getHeaderListForVolumeErrorFileDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = volumeUploadService.getVolumeErrorDetails(headerDetail, userId);
		// UploadUtil.writeFileWithData(downloadFileName, downloadedData,
		// ".csv");
		// downloadLink = downloadFileName + ".csv";
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			/*
			 * String actualFileName =
			 * downloadLink.substring(downloadLink.lastIndexOf("/") + 1,
			 * downloadLink.indexOf("."));
			 */
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=VolumeErrorFile_"
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
}
