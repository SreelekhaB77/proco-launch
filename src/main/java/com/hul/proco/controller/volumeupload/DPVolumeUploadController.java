package com.hul.proco.controller.volumeupload;

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
import com.hul.proco.controller.createpromo.CreateBeanRegular;
import com.hul.proco.controller.createpromo.RegularPromoCreateController;
import com.hul.proco.controller.createpromo.RegularPromoService;
import com.hul.proco.excelreader.exom.ExOM;

import org.apache.log4j.Logger;

@Controller
public class DPVolumeUploadController {

	@Autowired
	DPVolumeUploadService dpVolumeUploadService;

	@Autowired
	RegularPromoService createCRPromo;

	static Logger logger = Logger.getLogger(DPVolumeUploadController.class);

	@RequestMapping(value = "downloadDpVolume.htm", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPromotionRegularTemplateFile(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		InputStream is;
		String downloadLink = "", absoluteFilePath = "";
		List<ArrayList<String>> downloadedData = null;
		String userID = (String) request.getSession().getAttribute("UserID");

		ArrayList<String> headerDetail = dpVolumeUploadService.getHeaderForDPVolumeUpload();
		absoluteFilePath = FilePaths.FILE_TEMPDOWNLOAD_PATH;
		String fileName = UploadUtil.getFileName("Promotion.Error.file", "",
				CommonUtils.getCurrDateTime_YYYY_MM_DD_HHMMSS());
		String downloadFileName = absoluteFilePath + fileName;

		downloadedData = dpVolumeUploadService.getDetailsofDP(headerDetail);

		//Map<String, List<List<String>>> mastersForNewTemplate = createCRPromo.getMastersForNewTemplate();
		
		Map<String, List<List<String>>> mastersForNewTemplate = new HashMap<String, List<List<String>>>();
		
		
		try {
			UploadUtil.writeDeletePromoXLSFile(downloadFileName, downloadedData, mastersForNewTemplate, ".xls");
			downloadLink = downloadFileName + ".xls";
			is = new FileInputStream(new File(downloadLink));
			/*
			 * String actualFileName = downloadLink.substring(downloadLink.lastIndexOf("/")
			 * + 1, downloadLink.indexOf("."));
			 */
			// copy it to response's OutputStream
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=PromotionDPFile_"
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

	@RequestMapping(value = "dpVolumeUpload.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadCRFile(@ModelAttribute("CreateBeanRegular") CreateBeanRegular createCRBean,
			Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String save_data = null;

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
					map = ExOM.mapFromExcel(new File(fileName)).to(CreateBeanRegular.class).map(18, false, null);

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
						save_data = dpVolumeUploadService.uploadVolumeData(beanArray, userId);
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
		} catch (Throwable t) {
			logger.error(t);
		}
		return save_data;
	}

}
