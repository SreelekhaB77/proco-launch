package com.hul.proco.controller.promostatustracker;

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
import org.springframework.web.bind.annotation.GetMapping;
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
import com.hul.proco.controller.collaboration.CollaborationJsonObject;
import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.RegularPromoCreateController;
import com.hul.proco.excelreader.exom.ExOM;

@Controller
public class PPMLinkageController {

	static Logger logger = Logger.getLogger(PPMLinkageController.class);
	String ModRes;
	
	@Autowired
	PPMLinkageService linkageService;
	
	@Autowired
	ProcoStatusTrackerService procoStatusTrackerService;

	@RequestMapping(value = "ppminkageupload.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadPPMLinkageFile(@ModelAttribute("PPMLinkageBean") PPMLinkageBean ppmlinkageBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = ppmlinkageBean.getFile();
		String fileName = file.getOriginalFilename();
		PPMLinkageBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isMearsureReportFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
					model.addAttribute("ModRes", "File Size Exceeds");
					return "File Size Exceeds";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if (excelColumnCount == 63) {
							Map<String, List<Object>> map = null;
							map = ExOM.mapFromExcel(new File(fileName)).to(PPMLinkageBean.class).map(63, false, null);
							
							if (map.isEmpty()) {
								model.addAttribute("ModRes", "FILE_EMPTY");
								return "FILE_EMPTY";
							}
							if (map.containsKey("ERROR")) {
								model.addAttribute("ModRes", "CHECK_COL_MISMATCH");

								return "CHECK_COL_MISMATCH";
							} else if (map.containsKey("DATA")) {
								List<?> datafromexcel = map.get("DATA");
								beanArray = (PPMLinkageBean[]) datafromexcel
										.toArray(new PPMLinkageBean[datafromexcel.size()]);
								
								//save_data = createCRPromo.createCRPromo(beanArray, userId, template);
								save_data=linkageService.addTotempTable(beanArray, userId);
								
							}
							
							if(save_data.equals("EXCEL_UPLOADED"))
							{
								model.addAttribute("ModRes", "EXCEL_UPLOADED");
								return"EXCEL_UPLOADED";
							}else
							{
								model.addAttribute("ModRes", "EXCEL_NOT_UPLOADED");
								return"EXCEL_NOT_UPLOADED";
							}
							
							
						} else {
							model.addAttribute("ModRes", "Column count is not match with expected");
							return "Column count is not match with expected";
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error(e);
		}
		catch (Throwable e) {
			logger.error(e);
		}
		return save_data;
	}
	
	@RequestMapping(value = "downloadDPMOC.htm", method = RequestMethod.GET)
	public @ResponseBody String getMOCforDP(  HttpServletResponse response,
			Model model)
	{
		List<String> moctypes=linkageService.getBasedonMOC();
		model.addAttribute("mocList",moctypes);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(moctypes);
		return json;
	}

	@GetMapping(value="dpMesureDownloadBasedOnMoc.htm")
	public @ResponseBody ModelAndView dpMesureDownloadBasedOnMoc(
			@ModelAttribute("PPMLinkageBean") PPMLinkageBean ppmLinkageBean, Model model, @RequestParam("moc") String moc,
			HttpServletRequest request, HttpServletResponse response)  {
		try {	
			List<String> headers = procoStatusTrackerService.getPromoMeasureDownload();
			List<ArrayList<String>> downloadData=linkageService.getDownloadData(headers,moc);
		if (headers != null) {
			String downloadFileName = null;
			UploadUtil.writeDeletePromoXLSFile(downloadFileName, downloadData, null, ".xls");
			String downloadLink = downloadFileName + ".xls";
			FileInputStream is = new FileInputStream(new File(downloadLink));
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromoMeasureDownloadFile"
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
	
	
	//Added by Kavitha D for PPM COEREMARKS UPLOAD_SPRINT 9 Starts
	@RequestMapping(value = "ppmcoeremarksupload.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadPPMCoeRemarksFile(@ModelAttribute("PPMLinkageBean") PPMLinkageBean ppmlinkageBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;
		CommonPropUtils commUtils = CommonPropUtils.getInstance();
		//String userId = (String) httpServletRequest.getSession().getAttribute("UserID");
		MultipartFile file = ppmlinkageBean.getFile();
		String fileName = file.getOriginalFilename();
		PPMLinkageBean[] beanArray = null;
		String filepath = FilePaths.FILE_TEMPUPLOAD_PATH;
		fileName = filepath + fileName;
		try {
			if (!CommonUtils.isFileEmpty(file)) {
				if (CommonUtils.isMearsureReportFileSizeExceeds(file)) {
					model.addAttribute("errorMsg", commUtils.getProperty("File.Size.Exceeds"));
					model.addAttribute("ModRes", "File Size Exceeds");
					return "File Size Exceeds";
				} else {
					if (UploadUtil.movefile(file, fileName)) {
						int excelColumnCount = UploadUtil.readExcelCellCount(fileName);
						if (excelColumnCount == 2) {
							Map<String, List<Object>> map = null;
							map = ExOM.mapFromExcel(new File(fileName)).to(PPMLinkageBean.class).map(2, false, null);
							
							if (map.isEmpty()) {
								model.addAttribute("ModRes", "FILE_EMPTY");
								return "FILE_EMPTY";
							}
							if (map.containsKey("ERROR")) {
								model.addAttribute("ModRes", "CHECK_COL_MISMATCH");

								return "CHECK_COL_MISMATCH";
							} else if (map.containsKey("DATA")) {
								List<?> datafromexcel = map.get("DATA");
								beanArray = (PPMLinkageBean[]) datafromexcel
										.toArray(new PPMLinkageBean[datafromexcel.size()]);
								
								//save_data = createCRPromo.createCRPromo(beanArray, userId, template);
								save_data=linkageService.ppmCoeRemarks(beanArray);
								
							}
							
							if(save_data.equals("EXCEL_UPLOADED"))
							{
								model.addAttribute("ModRes", "EXCEL_UPLOADED");
								return"EXCEL_UPLOADED";
							}else
							{
								model.addAttribute("ModRes", "EXCEL_NOT_UPLOADED");
								return"EXCEL_NOT_UPLOADED";
							}
							
							
						} else {
							model.addAttribute("ModRes", "Column count is not match with expected");
							return "Column count is not match with expected";
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error(e);
		}
		catch (Throwable e) {
			logger.error(e);
		}
		return save_data;
	}
	//Added by Kavitha D for PPM COEREARKS UPLOAD_SPRINT 9 ends

	//Added by kavitha D-SPRINT 9
	@GetMapping(value="ppmCoeRemarksDownloadTemplate.htm")
	public @ResponseBody ModelAndView ppmCoeRemarksDownloadTemplateDownloadTemplate(
			@ModelAttribute("PPMLinkageBean") PPMLinkageBean ppmLinkageBean, Model model,
			HttpServletRequest request, HttpServletResponse response)  {
		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		

			
			absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
			String fileName = UploadUtil.getFileName("PPM.COE.REMARKS.file", "",
					CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
			String downloadFileName = absoluteFilePath + fileName;
			ArrayList<String> headerDetails = procoStatusTrackerService.ppmCoeRemarksDownloadHeaderList();

			 downloadedData = procoStatusTrackerService.ppmCoeRemarksDownload(headerDetails);

		try {
			 if (downloadedData != null) {
				UploadUtil.writeXLSFile(downloadFileName, downloadedData, null,".xls");
				downloadLink = downloadFileName + ".xls";
				is = new FileInputStream(new File(downloadLink));
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=PPMCOEREMARKSDOWNLOADFILE"
						+ CommonUtils.getCurrDateTime_YYYY_MM_DD_HH_MM_SS_WithOutA() + ".xls");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}} catch (FileNotFoundException e) {
				logger.error("Exception: ", e);
				// e.printStackTrace();
				return null;
			} catch (IOException e) {
				logger.error("Exception: ", e);
				// e.printStackTrace();
				return null;
		
			}
		return null;
		
	}
	
	//Added by kavitha D-SPRINT 9
	@RequestMapping(value = "ProcoPpmCoeRemarks.htm", method = RequestMethod.GET)
	public ModelAndView getProcoMeasureReportUploadPage(HttpServletRequest request, Model model) {
		String roleId = (String) request.getSession().getAttribute("roleId");
		model.addAttribute("roleId", roleId);		
		//model.addAttribute( "DownloadMocList", procoStatusTrackerService.getMocList());
		
		return new ModelAndView("proco/ppm_upload");
	}
}
