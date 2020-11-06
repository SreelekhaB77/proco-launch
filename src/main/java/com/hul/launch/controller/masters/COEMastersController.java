package com.hul.launch.controller.masters;

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
import com.hul.proco.controller.createpromo.CreatePromotionBean;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class COEMastersController {

	Logger logger=Logger.getLogger(COEMastersController.class);
	
	@Autowired
	private COEMastersService coeMastersService;
	
	@RequestMapping(value = "uploadLaunchPlanStoreMaster.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadLaunchPlanStoreMaster(
			@ModelAttribute("StoreMasterBean") StoreMasterBean bean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = bean.getFile();
		String fileName = file.getOriginalFilename();
		StoreMasterBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(StoreMasterBean.class).map(11, false, null);
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							return new ModelAndView("proco/proco_create");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							return new ModelAndView("proco/proco_create");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							beanArray = (StoreMasterBean[]) list.toArray(new StoreMasterBean[list.size()]);
							savedData = coeMastersService.insertStoreMaster(beanArray, userId);
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
				return new ModelAndView("proco/proco_create");
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				return new ModelAndView("proco/proco_create");
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return new ModelAndView("proco/proco_create");
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return new ModelAndView("proco/proco_create");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}
	
	@RequestMapping(value = "downloadStoreMasterErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionErrorFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userID = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = coeMastersService.getHeaderListForStoreMasterErrorFileDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = coeMastersService.getStoreMasterErrorDetails(headerDetail, userID);
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
			response.setHeader("Content-Disposition", "attachment; filename=StoreMasterErrorFile_"
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

	@RequestMapping(value = "downloadStoreMasterTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionTemplateFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = coeMastersService.getHeaderListForStoreMasterTemplateDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData.add(headerDetail);
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=StoreMaster_"
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
	
	@RequestMapping(value = "uploadLaunchPlanClassification.htm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadLaunchPlanClassification(
			@ModelAttribute("ClassificationBean") ClassificationBean bean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String savedData = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) request.getSession().getAttribute("UserID");
		MultipartFile file = bean.getFile();
		String fileName = file.getOriginalFilename();
		ClassificationBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName; 
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						Map<String, List<Object>> map = ExOM.mapFromExcel(new File(fileName))
								.to(ClassificationBean.class).map(6, false, null);
						if (map.isEmpty()) {
							model.addAttribute("FILE_STATUS", "ERROR");
							model.addAttribute("errorMsg", "File does not contain data");
							return new ModelAndView("proco/proco_create");
						}
						if (map.containsKey("ERROR")) {
							List<Object> errorList = map.get("ERROR");
							String errorMsg = (String) errorList.get(0);
							model.addAttribute("errorMsg", errorMsg);
							return new ModelAndView("proco/proco_create");
						} else if (map.containsKey("DATA")) {
							List<?> list = map.get("DATA");
							beanArray = (ClassificationBean[]) list.toArray(new ClassificationBean[list.size()]);
							savedData = coeMastersService.insertLaunchPlanClassificationMaster(beanArray, userId);
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
				return new ModelAndView("proco/proco_create");
			} else if (savedData.equals("ERROR")) {
				model.addAttribute("errorMsg", "File Upload is UnSuccessful.");
				return new ModelAndView("proco/proco_create");
			} else {
				model.addAttribute("FILE_STATUS", "SUCCESS_FILE");
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return new ModelAndView("proco/proco_create");
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			model.addAttribute("errorMsg", "Error while uploading file");
			return new ModelAndView("proco/proco_create");
		}
		return new ModelAndView("proco/proco_promo_listing");
	}
	
	@RequestMapping(value = "downloadLaunchPlanClassificationErrorFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadLaunchPlanClassificationErrorFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userID = (String) request.getSession().getAttribute("UserID");
		ArrayList<String> headerDetail = coeMastersService.getHeaderListForLaunchPlanClassificationErrorFileDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData = coeMastersService.getLaunchPlanClassificationErrorDetails(headerDetail, userID);
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
			response.setHeader("Content-Disposition", "attachment; filename=ClassificationMasterErrorFile_"
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

	@RequestMapping(value = "downloadLaunchPlanClassificationTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadLaunchPlanClassificationTemplateFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = coeMastersService.getHeaderListLaunchPlanClassificationTemplateDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Template.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData.add(headerDetail);
		try {
			UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=ClassificationMaster_"
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
