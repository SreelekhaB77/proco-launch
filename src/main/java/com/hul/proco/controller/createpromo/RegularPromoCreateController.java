package com.hul.proco.controller.createpromo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.hul.launch.web.util.CommonPropUtils;
import com.hul.launch.web.util.CommonUtils;
import com.hul.launch.web.util.FilePaths;
import com.hul.launch.web.util.UploadUtil;
import com.hul.proco.excelreader.exom.ExOM;

/*
 *  Change name: Creating new file for CR promo upload
 *  Description: This controller will handle all the new template request 
 *  Developer: Mayur Chavhan
 *  Date: 19/05/2022 
 */
@Controller
public class RegularPromoCreateController {
	@Autowired
	RegularPromoService createCRPromo;
	
	static Logger logger = Logger.getLogger(RegularPromoCreateController.class);

	@RequestMapping(value = "createCRBean.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadCRFile(@ModelAttribute("CreateBeanRegular") CreateBeanRegular createCRBean,
			@RequestParam(name = "template") String template, Model model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String save_data = null;
		String categories=(String) httpServletRequest.getSession().getAttribute("categoryName");
		
		
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = createCRBean.getFile();
		String fileName = file.getOriginalFilename();
		CreateBeanRegular[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isFileProcoSizeExceeds(file)) {
					model.addAttribute("FILE_STAUS", "FILE_SIZE_EXCEED");
					return "FILE_SIZE_EXCEED";
				} else if (UploadUtil.movefile(file, fileName)) {
					Map<String, List<Object>> map = null;
					if (template.equalsIgnoreCase("regular")) {
						map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(14, false, null);
					} else if (template.equalsIgnoreCase("new")) {
						map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(15, false, null);
					} else if (template.equalsIgnoreCase("cr")) {

						map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(21, false, null);

					}

					if (map.isEmpty()) {
						model.addAttribute("FILE_STAUS", "FILE_EMPTY");
						return "FILE_EMPTY";
					}
					if (map.containsKey("ERROR")) {
						model.addAttribute("FILE_STAUS", "CHECK_COL_MISMATCH");

						return "CHECK_COL_MISMATCH";
					} else if (map.containsKey("DATA")) {
						List<?> datafromexcel = map.get("DATA");
						beanArray = (CreateBeanRegular[]) datafromexcel
								.toArray(new CreateBeanRegular[datafromexcel.size()]);

						save_data = createCRPromo.createCRPromo(beanArray, userId, template,categories);

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
			logger.error("Exception: ", e);
		} catch (Throwable e) {
			logger.error("Exception: ", e);
			e.printStackTrace();
		}

		return save_data;

	}

	// Added by Kavitha D for downloading promo regular template starts-SPRINT 9

	@RequestMapping(value = "downloadPromotionRegularTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionRegularTemplateFile(
			@ModelAttribute("CreatePromotionBeanRegular") CreateBeanRegular createPromotionBeanRegular, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = createCRPromo.getHeaderListForPromotionRegularTemplateDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.RegularTemplate.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData.add(headerDetail);
		Map<String, List<List<String>>> mastersForRegularTemplate = createCRPromo.getMastersForRegularTemplate();
		try {
			UploadUtil.writePromoXLSFile(downloadFileName, downloadedData, mastersForRegularTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionRegularFile_"
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
	// Added by Kavitha D for downloading promo regular template ends-SPRINT 9

	// Added by Kavitha D for downloading promo new template starts-SPRINT 9

	@RequestMapping(value = "downloadPromotionNewTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionNewTemplateFile(
			@ModelAttribute("CreatePromotionBeanRegular") CreateBeanRegular createPromotionBeanNew, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = createCRPromo.getHeaderListForPromotionNewTemplateDownload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.RegularTemplate.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		downloadedData.add(headerDetail);
		Map<String, List<List<String>>> mastersForNewTemplate = createCRPromo.getMastersForNewTemplate();
		try {
			UploadUtil.writePromoXLSFile(downloadFileName, downloadedData, mastersForNewTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionNewFile_"
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
	// Added by Kavitha D for downloading promo new template ends-SPRINT 9

	// Added by Kavitha D for downloading promo CR template starts-SPRINT 9

	@RequestMapping(value = "downloadPromotionCrTemplateFile.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionCrTemplateFile(
			@ModelAttribute("CreatePromotionBeanRegular") CreateBeanRegular createPromotionBeanCr, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		String userId = (String) request.getSession().getAttribute("UserID");
		List<ArrayList<String>> downloadedData = new ArrayList<ArrayList<String>>();
		ArrayList<String> headerDetail = createCRPromo.getHeaderListForPromotionCrTemplateDownload();
		downloadedData = createCRPromo.getPromotionDownloadCR(headerDetail, userId);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.RegularTemplate.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;
		Map<String, List<List<String>>> mastersForCrTemplate = createCRPromo.getMastersForCrTemplate();
		try {
			UploadUtil.writePromoCrXLSFile(downloadFileName, downloadedData, mastersForCrTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionCrFile_"
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
	// Added by Kavitha D for downloading promo Cr template ends-SPRINT 9

	@RequestMapping(value = "downloadPromotionErrorFilensp.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionErrorFile(
			@ModelAttribute("CreatePromotionBean") CreatePromotionBean createPromotionBean, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userID = (String) request.getSession().getAttribute("UserID");
		String error_template=createCRPromo.getTemplateType(userID);
		String roleID=(String)request.getSession().getAttribute("roleId");
		ArrayList<String> headerDetail = createCRPromo.getHeaderListForPromotionErrorDownload(error_template,roleID);
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;

		downloadedData = createCRPromo.getPromotionErrorDetails(headerDetail, userID,error_template,roleID);
		
		Map<String, List<List<String>>> mastersForNewTemplate = createCRPromo.getMastersForNewTemplate();
		try {
			UploadUtil.writePromoXLSFile(downloadFileName, downloadedData, mastersForNewTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			/*
			 * String actualFileName = downloadLink.substring(downloadLink.lastIndexOf("/")
			 * + 1, downloadLink.indexOf("."));
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
	}

}
